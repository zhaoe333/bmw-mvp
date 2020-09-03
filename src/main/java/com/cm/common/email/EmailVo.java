package com.cm.common.email;

import lombok.Data;

import java.io.Serializable;

@Data
public class EmailVo implements Serializable {
	private static final long serialVersionUID = 4093856373128929717L;
	/***如果是多个收件人,请用逗号隔开***/
	private String receiveEmail;
	private String title;
	private String content;

}
