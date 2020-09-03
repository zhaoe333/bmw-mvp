package com.cm.common.http;

/**
 * 响应码
 * 200为正常响应
 * 1XX为业务响应码
 * 3XX为通用响应码
 * 4XX为系统异常响应码
 *
 * @author yunlu
 */
public enum FMResponseCode {
    //系统操作
    normal("200", "Successful.", "操作成功"),
    syserror("400", "System is busy!", "系统繁忙"),

    //用户相关
    accountnotexist("101", "User does not exist.", "用户不存在"),
    accountremoved("102", "User has been disabled.", "用户已停用"),
    passworderror("103", "Account or password error.", "账号或密码错误"),
    noroleforaccount("104", "User has no role.", "用户没有任何的角色"),
    propertyUsed("105", "This value has been taken.", "该值已被占用"),
    infonotmatch("106", "Information do not match", "信息不匹配"),
    choiceUserError("107", "Please select the user", "请选择用户"),
    fiveresult("108", "已经有五条记录", "已经有五条记录"),
    hasNoId("108", "has no user id!", "没有用户id"),
    isBlacked("109", "has added black list", "用户被加入黑名单，请联系管理员"),

    //操作相关
    paramserror("301", "Requested parameter error!", "请求参数错误"),
    tokenerror("302", "No Token!", "无token"),
    tokenfail("303", "Token validation failed!", "token验证失败"),
    adminError("304", "permission restrictions!", "超级管理员权限限制"),
    resourceError("305", "No permission", "没有该功能权限"),
    invalidlink("306", "Invalid link", "无效的链接"),

    //文件相关
    filetimeout("316", "File was invalid!", "文件已失效"),
    filenoexist("317", "File doesn't exist!", "文件不存在"),
    filetypenotallow("318"
            , "This type of file is not allowed to be uploaded!"
            , "文件的格式不符合发布规范"),
    //广场相关
    likeyet("330", "likeyet", "已经点过赞了"),
    nolike("331", "nolike", "没有点过赞"),
    favoriteyet("332", "favoriteyet", "已经收藏过了"),
    nofavorite("333", "favoriteyet", "没有收藏过"),
    infoNotAllowed("334", "not allowed word!", "内容不负责发布规范"),
    operationfailed("335", "operationfailed", "操作失败"),
    cantdisableself("336", "cantdisableself", "不能禁用自己的文章或者作者" ),
    //圈子,
    hasNoGroup("340", "has not group!", "没有圈子"),
    wrongOperate("342", "has wrong operate！", "自己不能关注自己"),
    hasNoNum("341", "usernum <0!", "数量小于0"),

    //关注
    hasRelation("350", "has relation！", "已经关注，不能再关注"),
    hasNoRelation("351", "has no relation！", "已经没有关系，不能再取消关注"),
//    hasNoGroup("340", "not allowed word!", "没有圈子");
//    hasNoGroup("340", "not allowed word!", "没有圈子");
    //用户
hasNoIdAndType("360", "has wrong id and type！", "id和type参数不对"),

    // 组队
    teamDoesntExist("370", "Invalid Team Id", "组队Id无效"),
    articleDoesntExist("371", "Invalid Article Id", "文章Id无效"),
    eventDoesntExist("372", "Invalid Event Id", "活动Id无效"),
    numOfPeopleShouldAboveZero("373", "Invalid The Number Of People (should be greater zero)", "组队人数须大于0"),
    teamAlreadyFull("374", "Team already full", "组队活动已满"),
    teamUpdateFailed("375", "Team update failed", "组队活动更新失败"),
    endBeforeStart("376", "End time before start time", "开始时间必须先于结束时间"),
    timeConflict("377", "time conflict", "时间冲突"),
    teamRemoved("378", "team has been removed", "组队活动已删除"),


    repeatsubmit("888888", "repeat data", "操作频繁，请稍后再试"),
    ttttt("999999", "not allowed word!", "放到最后方便");


    private String code;
    private String msg;
    private String cnMsg;

    private FMResponseCode(String code, String msg, String cnMsg) {
        this.code = code;
        this.msg = msg;
        this.cnMsg = cnMsg;
    }

    public String getCode() {
        return this.code;
    }

    public String getMsg() {
        return this.msg;
    }

    public String getCnMsg() {
        return this.cnMsg;
    }

    public String toString() {
        return this.code;
    }
}
