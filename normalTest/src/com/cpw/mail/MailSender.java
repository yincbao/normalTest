package com.cpw.mail;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.Authenticator;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.MimeUtility;

import org.apache.log4j.Logger;


public class MailSender {
	private final Logger logger = Logger.getLogger(MailSender.class);
	private MimeMessage message;
	private Session session;
	private Transport transport;

	private static String host;
	private static boolean auth;
	private static boolean starttlsEnable;
	private static String port;
	private static String type;
	private static String user;
	private static String psd;
	private static String receiveList;
	private static String bccListStr;

	static {
		host = "smtp.sina.com";
		auth =   true;
		starttlsEnable =  true;
		port = "25";
		type = "smtp";
		user = "yinchangbao6611@sina.com";
		psd ="2y0c6b1l7w3t44";
		receiveList = "li_kunkun@hoperun.com";
		bccListStr = "yin_changbao@hoperun.com";
	}

	/*
	 * 初始化方法
	 */
	public MailSender(boolean debug) {
		Properties props = System.getProperties();
		props.put("mail.smtp.host", host);
		// pass the validate
		props.put("mail.smtp.auth", auth);
		props.put("mail.smtp.starttls.enable", starttlsEnable);
		props.put("mail.smtp.port", port);
		props.put("mail.smtp.user", user);
		props.put("mail.smtp.password", psd);

		MyAuthenticator auth = new MyAuthenticator(user, psd);
		session = Session.getDefaultInstance(props, auth);
		// debug flag
		session.setDebug(debug);
		message = new MimeMessage(session);
	}

	
	
	
	/**
	 * 发送邮件
	 * 
	 * @param subject
	 *            邮件主题
	 * @param sendHtml
	 *            邮件内容
	 * @param receiveUser
	 *            收件人地址 
	 * @param attachment
	 *            附件
	 */
	public void sendEmailWithAttachment(String subject, String sendHtml,
			File attachment) {
		try {
			// 发件人
			InternetAddress from = new InternetAddress(user);
			message.setFrom(from);

			// 收件人
			InternetAddress[] toList = InternetAddress.parse(receiveList);
			message.setRecipients(Message.RecipientType.TO, toList);

			//
			InternetAddress[] bccList = InternetAddress.parse(bccListStr);
			message.setRecipients(Message.RecipientType.BCC, bccList);
			
			// 邮件主题
			message.setSubject(subject);

			// 向multipart对象中添加邮件的各个部分内容，包括文本内容和附件
			Multipart multipart = new MimeMultipart();

			// 添加邮件正文
			BodyPart contentPart = new MimeBodyPart();
			contentPart.setContent(sendHtml, "text/html;charset=ISO-8859-1");
			multipart.addBodyPart(contentPart);

			// 添加附件的内容
			if (attachment != null) {
				BodyPart attachmentBodyPart = new MimeBodyPart();
				DataSource source = new FileDataSource(attachment);
				attachmentBodyPart.setDataHandler(new DataHandler(source));

				// 网上流传的解决文件名乱码的方法，其实用MimeUtility.encodeWord就可以很方便的搞定
				// 这里很重要，通过下面的Base64编码的转换可以保证你的中文附件标题名在发送时不会变成乱码
				// sun.misc.BASE64Encoder enc = new sun.misc.BASE64Encoder();
				// messageBodyPart.setFileName("=?GBK?B?" +
				// enc.encode(attachment.getName().getBytes()) + "?=");

				// MimeUtility.encodeWord可以避免文件名乱码
				attachmentBodyPart.setFileName(MimeUtility
						.encodeWord(attachment.getName()));
				multipart.addBodyPart(attachmentBodyPart);
			}

			// 将multipart对象放到message中
			message.setContent(multipart);
			// 保存邮件
			message.saveChanges();

			transport = session.getTransport(type);
			// smtp验证，就是你用来发邮件的邮箱用户名密码
			transport.connect(host, user, psd);
			// 发送
			transport.sendMessage(message, message.getAllRecipients());

			logger.info("send success!");
		} catch (Exception e) {
			logger.error(e.toString());
		} finally {
			if (transport != null) {
				try {
					transport.close();
				} catch (MessagingException e) {
					logger.error(e.toString());
				}
			}
		}
	}

	/**
	 * 发送邮件
	 * 
	 * @param emaiTo
	 *            发件人
	 * @param subject
	 *            邮件主题
	 * @param sendHtml
	 *            邮件内容
	 * @param receiveUser
	 *            收件人地址
	 * @param attachment
	 *            附件
	 */
	public void sendEmailWithAttachment(String emailTo, String subject,
			String sendHtml, File[] attachments) {
		try {
			// 发件人
			InternetAddress from = new InternetAddress(user);
			message.setFrom(from);

			// 收件人
			InternetAddress[] toList = InternetAddress.parse(emailTo);
			message.setRecipients(Message.RecipientType.TO, toList);

			// 邮件主题
			message.setSubject(subject);

			// 向multipart对象中添加邮件的各个部分内容，包括文本内容和附件
			Multipart multipart = new MimeMultipart();

			// 添加邮件正文
			BodyPart contentPart = new MimeBodyPart();
			contentPart.setContent(sendHtml, "text/html;charset=UTF-8");
			multipart.addBodyPart(contentPart);

			// 添加附件的内容
			if (null != attachments && attachments.length > 0) {
				for (int i = 0; i < attachments.length; i++) {
					if (attachments[i] != null) {
						BodyPart attachmentBodyPart = new MimeBodyPart();
						DataSource source = new FileDataSource(attachments[i]);
						attachmentBodyPart.setDataHandler(new DataHandler(
								source));

						// 网上流传的解决文件名乱码的方法，其实用MimeUtility.encodeWord就可以很方便的搞定
						// 这里很重要，通过下面的Base64编码的转换可以保证你的中文附件标题名在发送时不会变成乱码
						// sun.misc.BASE64Encoder enc = new
						// sun.misc.BASE64Encoder();
						// messageBodyPart.setFileName("=?GBK?B?" +
						// enc.encode(attachment.getName().getBytes()) + "?=");

						// MimeUtility.encodeWord可以避免文件名乱码
						attachmentBodyPart.setFileName(MimeUtility
								.encodeWord(attachments[i].getName()));
						multipart.addBodyPart(attachmentBodyPart);
					}
				}
			}

			// 将multipart对象放到message中
			message.setContent(multipart);
			// 保存邮件
			message.saveChanges();

			transport = session.getTransport(type);
			// smtp验证，就是你用来发邮件的邮箱用户名密码
			transport.connect(host, user, psd);
			// 发送
			transport.sendMessage(message, message.getAllRecipients());

			logger.info("send success!");

			for (int i = 0; i < attachments.length; i++) {
				File file = attachments[i];
				if (file.exists()) {
					file.delete();
				}
			}
		} catch (Exception e) {
			logger.error(e.toString());
		} finally {
			if (transport != null) {
				try {
					transport.close();
				} catch (MessagingException e) {
					logger.error(e.toString());
				}
			}
		}
	}

	class MyAuthenticator extends Authenticator {
		String name;
		String password;

		public MyAuthenticator(String name, String password) {
			this.name = name;
			this.password = password;
		}

		protected PasswordAuthentication getPasswordAuthentication() {
			return new PasswordAuthentication(name, password);
		}
	}

	private String getMailList(String[] mailArray) {
		StringBuffer toList = new StringBuffer();
		int length = mailArray.length;
		if (mailArray != null && length < 2) {
			toList.append(mailArray[0]);
		} else {
			for (int i = 0; i < length; i++) {
				toList.append(mailArray[i]);
				if (i != (length - 1)) {
					toList.append(",");
				}
			}
		}
		return toList.toString();
	}

	public static void main(String[] args) throws UnsupportedEncodingException {
		try{
			MailSender se = new MailSender(false);
			File affix =null;
			String content = "11111111111111111";
			
			se.sendEmailWithAttachment("tesing BCC ", new String(content.getBytes()), affix);//
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}
}
