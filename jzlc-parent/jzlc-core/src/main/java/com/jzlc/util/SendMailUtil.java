package com.jzlc.util;

import java.util.Date;
import java.util.Properties;

import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class SendMailUtil {

	public static boolean sendMail(String toMail,long id, String code) {
		/** 1. 创建参数配置, 用于连接邮件服务器的参数配置 */
		Properties props = new Properties();
		/** 使用的协议（JavaMail规范要求） */
		props.setProperty("mail.transport.protocol", "smtp");
		/** 发件人的邮箱的 SMTP 服务器地址 */
		props.setProperty("mail.smtp.host", "smtp.qq.com");
		/** 需要请求认证 */
		props.setProperty("mail.smtp.auth", "true"); // 需要请求认证

		/** QQ邮箱的SMTP(SLL)端口 */
		final String smtpPort = "465";

		props.setProperty("mail.smtp.port", smtpPort);
		props.setProperty("mail.smtp.socketFactory.class",
				"javax.net.ssl.SSLSocketFactory");
		props.setProperty("mail.smtp.socketFactory.fallback", "false");
		props.setProperty("mail.smtp.socketFactory.port", smtpPort);

		/** 根据配置创建会话对象, 用于和邮件服务器交互 */
		Session session = Session.getInstance(props);
		/** 设置为debug模式, 可以查看详细的发送 log */
		session.setDebug(true);

		/** 根据 Session 获取邮件传输对象 */
		Transport transport = null;
		try {
			transport = session.getTransport();
			/** 创建一封邮件 */
			MimeMessage message = mailModel(session, toMail,id, code);
			/**使用 邮箱账号 和 密码 连接邮件服务器, 这里认证的邮箱必须与 message 中的发件人邮箱一致, 密码为授权码,否则报错*/
			transport.connect("874827792@qq.com", "tuszgsfwfsxmbebe");
			/**发送邮件, 发到所有的收件地址, message.getAllRecipients() 获取到的是在创建邮件对象时添加的所有收件人, 抄送人,*/
			transport.sendMessage(message, message.getAllRecipients());
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}finally {
			/**关闭连接*/
			if(transport != null) transport = null;
		}
	}

	private static MimeMessage mailModel(Session session, String toMail,long id, String code)
			throws Exception {
		MimeMessage message = new MimeMessage(session);
		/** From 发件箱 */
		message.setFrom(new InternetAddress("874827792@qq.com", "Eloan",
				"UTF-8"));
		/** To 收件箱 */
		message.setRecipient(MimeMessage.RecipientType.TO, new InternetAddress(
				toMail, "尊敬的XX用户", "UTF-8"));
		/** Subject: 邮件主题 */
		message.setSubject("绑定邮箱", "UTF-8");
		/** 邮箱中可点击的超链接 */
		String url = "http://10.1.12.66/webSite/verifyMail.action?id=" + id
				+ "&code=" + code;
		;
		/** 邮件模板文本字符串 */
		String mail = "<h2>用户你好,你正在进行蓝源的邮箱绑定验证,两小时内有效,如非本人操作请忽略!<br/>激活链接:</h2><a href='"
				+ url + "'>http:www.eloan.com/validationemail</a>";
		/** 设置Content: 邮件正文（可以使用html标签） */
		message.setContent(mail, "text/html;charset=UTF-8");
		/** 设置发送时间 */
		message.setSentDate(new Date());
		/** 保存设置 */
		message.saveChanges();
		return message;

	}
}
