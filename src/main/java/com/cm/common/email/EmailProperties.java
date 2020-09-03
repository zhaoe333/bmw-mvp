package com.cm.common.email;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.io.Serializable;

/**
 * 邮件相关配置类
 */
@ConfigurationProperties(prefix = "benz.mail")
@Data
public class EmailProperties  implements Serializable {
    /**邮箱地址*/
    private String user;
    /**smtp*/
    private String host;
    /**邮箱地址*/
    private String email;
    /**密码*/
    private String password;
    /**是否验证*/
    private String auth;

}
