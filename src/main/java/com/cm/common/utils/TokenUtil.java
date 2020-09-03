package com.cm.common.utils;

import com.cm.common.constant.CommonConstants;
import com.cm.common.exception.FMException;
import com.cm.common.http.FMResponseCode;
import com.cm.sys.entity.SysResources;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;
import java.util.concurrent.TimeUnit;

@Component
public class TokenUtil {
    /**
     * token时效  单位 分钟
     */
    @Value("${babi.token.expire}")
    private int expire;
    @Value("${babi.wx.token.expire}")
    private int wxExpire;

    @Resource
    private RedisTemplate<String, Object> redisTemplate;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;


    public static final String TOKEN_PRE_WX = "wx";

    public static final String TOKEN_PRE_PC = "pc";

    public static final String TOKEN_PRE_APP = "app";

    /**
     * 创建token
     *
     * @param userId    用户id
     * @param loginName 用户名称
     * @return
     * @throws Exception
     */
    public String create(String pre, String userId, String loginName) throws Exception {
        String key = buildKey(pre, userId);
        String token = DESUtils.encrypt(key);
        Map<String, Object> params = new HashMap<String, Object>();
        params.put(CommonConstants.TOKEN_USER_KEY, userId);
        params.put(CommonConstants.TOKEN_LOGINNAME_KEY, loginName);
        redisTemplate.opsForValue().set(key, params);
        expireKey(key, expire);
        return token;
    }

    public String create(String pre, String userId, String loginName, String loginIP, String roleCode,
                         List<SysResources> systemResourceList, List<Map<String, String>> checkMap,
                         List<String> roleCodes) throws Exception {
        String key = buildKey(pre, userId);
        String token = DESUtils.encrypt(key);
        Map<String, Object> params = new HashMap<String, Object>();
        params.put(CommonConstants.TOKEN_USER_KEY, userId);
        params.put(CommonConstants.TOKEN_LOGINNAME_KEY, loginName);
        params.put(CommonConstants.TOKEN_LOGINIP_KEY, loginIP);
        params.put(CommonConstants.TOKEN_ROLECODE_KEY, roleCode);
        //如果登陆用户为超级管理员则保存列表为null
        if (roleCode != null && roleCode.equals(CommonConstants.ADMIN_ROLE_ID)) {
            roleCodes = new ArrayList<>();
        }
        //将角色code列表转化为二进制保存
        params.put(CommonConstants.TOKEN_ROLECODES_KEY, roleCodes);
        //将菜单列表中转化为二进制保存
        params.put(CommonConstants.TOKEN_USER_RESOURCES, systemResourceList);
        //将验证菜单列表转为二进制保存
        params.put(CommonConstants.TOKEN_USER_CHECK_URL, checkMap);
        //将Token对应信息转化为二进制存储
        redisTemplate.opsForValue().set(key, params);
        expireKey(key, expire);
        return token;
    }


    @Transactional
    public String createForWX(String pre, String userId, String openId, String sessionKey) throws Exception {
        String key = buildKey(pre, userId);

        Set<String> keys = redisTemplate.keys(pre + "*" + userId);
        if (!keys.isEmpty()) {
            key = keys.iterator().next();
        }

        String token = DESUtils.encrypt(key);
        Map<String, Object> params = new HashMap<String, Object>();
        params.put(CommonConstants.TOKEN_USER_KEY, userId);
        params.put(CommonConstants.TOKEN_OPENID_KEY, openId);
        params.put(CommonConstants.TOKEN_SESSION_KEY, sessionKey);
        redisTemplate.opsForValue().set(key, params);
        expireKey(key, wxExpire);
        return token;
    }

    private String buildKey(String pre, String userId) {
        StringBuilder tokenBuilder = new StringBuilder(pre);
        tokenBuilder.append("-");
        tokenBuilder.append(UUIDUtils.generateUUID());
        tokenBuilder.append("-");
        tokenBuilder.append(userId);
        return tokenBuilder.toString();
    }

    /**
     * 销毁Token
     *
     * @param token
     * @author liujie
     */
    public void remove(String token) throws Exception {
        token = DESUtils.decrypt(token);
        redisTemplate.delete(token);
    }

    /**
     * 处理token
     *
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    public void checkToken(String token) throws Exception {
        if (LogicUtil.isNullOrEmpty(token)) {
            FMException.throwEx(FMResponseCode.tokenerror);
        } else {
            //TODO 验证token有效性 这里考虑后续同msaggerconvert里面的统一
            token = DESUtils.decrypt(token);
            Object map = redisTemplate.opsForValue().get(token);
            if (map == null) {
                FMException.throwEx(FMResponseCode.tokenfail);
            } else {
                Map<String, Object> tokenMap = (Map<String, Object>) map;
                if (LogicUtil.isNullOrEmpty(tokenMap)) {
                    FMException.throwEx(FMResponseCode.tokenfail);
                }
            }
        }
    }

    public Object get(String key) {
        return redisTemplate.opsForValue().get(key);
    }

    public boolean hasKey(String key) {
        return stringRedisTemplate.hasKey(key);
    }

    public void clearKeyValue(String userId) {
        for (String key : stringRedisTemplate.keys("*" + userId)) {
            stringRedisTemplate.delete(key);
        }
    }

    public int numKeys(String userId) {
        return stringRedisTemplate.keys("*" + userId).size();
    }

    private void expireKey(String key, int expire) {
        if (0 < expire) {
            redisTemplate.expire(key, expire * 60, TimeUnit.MINUTES);
        } else {
            //默认设置七天的失效时间 避免redis中产生过量的key
            redisTemplate.expire(key, 7 * 24 * 60, TimeUnit.MINUTES);
        }
    }
}
