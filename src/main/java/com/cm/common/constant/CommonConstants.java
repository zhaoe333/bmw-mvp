package com.cm.common.constant;
/**
 * 通用常量
 * @author yunlu
 *
 */
public class CommonConstants {
	/**
	 * 删除标识
	 */
	public static final int DELETED=1;
	public static final int NO_DELETED=0;
	
	/**
	 * 启用表示
	 * */
	public static final int FREEZE=1;
	public static final int NO_FREEZE=0;
	/**
	 * 用户状态 在线
	 */
	public static final int ONLINE=0;
	/**
	 * 用户状态 离线
	 */
	public static final int OFFLINE=1;

	/**
	 * dashboard
	 */
	public static final String DASHBOARD_CACHE="dashboard2";
	
	/**
	 * 用户默认密码
	 * */
	public static final String USER_DEFAULT_PASSWORD = "fm123456";

	/**
	 * token相关key
	 */
	public static final String TOKEN_ROLECODE_KEY="roleCode";
	
	public static final String TOKEN_USER_KEY="userId";
	
	public static final String TOKEN_TYPE="type";
	
	public static final String TOKEN_LOGINNAME_KEY="loginName";
	
	public static final String TOKEN_LOGINIP_KEY="loginIP";
	
	public static final String TOKEN_REPORT_EMAIL="reportEmail";
	
	public static final String TOKEN_USER_RESOURCES = "userResources";
	
	public static final String TOKEN_USER_CHECK_URL = "userCheckUrl";
	
	public static final String TOKEN_ROLECODES_KEY = "roleCodes";

	public static final String TOKEN_OPENID_KEY = "openId";

	public static final String TOKEN_SESSION_KEY = "sessionKey";
	
	/**验证用户菜单URL*/
	public static final String USER_CHECK_RESOURCE_URL = "check_res_url";
	/**验证用户方法URL*/
	public static final String USER_CHECK_ACTION_URL = "check_action_url";
	
	public static final String ADMIN_ID = "ADMIN_ID";

	public static final String ADMIN_ROLE_ID = "A";
	
	/**日志相关*/
	@Deprecated
	public enum LOG_TYPE{
		ADD(1,"新增",1),
		UPDATE(2,"修改",1),
		DELETE(3,"删除",1),
		QUERY(4,"查询",0),
		EXPORT(5,"导出",1),
		SEND(6,"发送命令",1),
		LOGIN(7,"登陆",1),
		LOGINOUT(8,"登出",1),
		SIGNUP(9,"注册",1),
		DOWN(10,"下载",1);
		
		private int key;
		private String content;
		private int level; 
		
		private LOG_TYPE(int key,String content,int level){
			this.key=key;
			this.content=content;
			this.level=level;
		}
		
		public int key(){
			return key;
		}
		public String content(){
			return content;
		}
		public int level(){
			return level;
		}
	}
	
}
