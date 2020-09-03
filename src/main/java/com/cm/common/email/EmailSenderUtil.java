package com.cm.common.email;

import com.sun.mail.util.MailSSLSocketFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Component;

import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

@Component
@EnableConfigurationProperties(EmailProperties.class)
public class EmailSenderUtil {

	@Autowired
	private EmailProperties properties;

	/**
	 * 发送邮件接口,可以发送多个
	 * 
	 * @param email
	 * @throws Exception
	 */
	public String sendEmail(EmailVo email) throws Exception {
		Properties prop = new Properties();
		// 开启debug调试，以便在控制台查看
		prop.setProperty("mail.debug", "false");
		// 设置邮件服务器主机名
		prop.setProperty("mail.host", properties.getHost());
		// 发送服务器需要身份验证
		prop.setProperty("mail.smtp.auth", properties.getAuth());
		// 发送邮件协议名称
		prop.setProperty("mail.transport.protocol", "smtp");

		// 开启SSL加密，否则会失败
		MailSSLSocketFactory sf = new MailSSLSocketFactory();
		sf.setTrustAllHosts(true);
		prop.put("mail.smtp.ssl.enable", "true");
		prop.put("mail.smtp.ssl.socketFactory", sf);

		// 创建session
		Session session = Session.getInstance(prop);
		// 通过session得到transport对象
		Transport ts = session.getTransport();
		// 连接邮件服务器：邮箱类型，帐号，授权码代替密码（更安全）
		ts.connect(properties.getHost(), properties.getEmail(),properties.getPassword());
		// 创建邮件
		Message message = createSimpleMail(session, email);
		// 发送邮件
		ts.sendMessage(message, message.getAllRecipients());
		ts.close();
		return properties.getEmail();
	}

	/**
	 * @Method: createSimpleMail
	 * @Description: 创建一封只包含文本的邮件
	 */
	private MimeMessage createSimpleMail(Session session, EmailVo emailVo) throws Exception {
		// 创建邮件对象
		MimeMessage message = new MimeMessage(session);
		// 指明邮件的发件人
		message.setFrom(new InternetAddress(properties.getUser()));
		// 指明邮件的收件人，现在发件人和收件人是一样的，那就是自己给自己发
		message.setRecipient(Message.RecipientType.TO, new InternetAddress(emailVo.getReceiveEmail()));
		// 邮件的标题
		message.setSubject(emailVo.getTitle());
		// 邮件的文本内容
		message.setContent(emailVo.getContent(), "text/html;charset=UTF-8");
		// 返回创建好的邮件对象
		return message;
	}
}
