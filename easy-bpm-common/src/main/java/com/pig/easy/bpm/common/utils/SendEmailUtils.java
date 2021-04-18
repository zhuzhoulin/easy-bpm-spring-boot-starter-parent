package com.pig.easy.bpm.common.utils;

import com.sun.mail.util.MailSSLSocketFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;

import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeUtility;
import java.io.File;
import java.util.Date;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

/**
 * todo:
 *
 * @author : pig
 * @date : 2020/12/31 14:08
 */
public class SendEmailUtils {

    private static final Logger log = LoggerFactory.getLogger(SendEmailUtils.class);

    private volatile static SendEmailUtils instance = null;

    public static SendEmailUtils getInstance() {
        if (null == instance) {
            synchronized (SendEmailUtils.class) {
                if (null == instance) {
                    instance = new SendEmailUtils();
                }
            }
        }
        return instance;
    }

    private static final int TIMEOUT = 25 * 1000;
    private static final String DEFAULT_CHARSET = "UTF-8";
    private static final String MAIL_SMTP_AUTH = "mail.smtp.auth";
    private static final String MAIL_SMTP_STARTTLS_ENABLE = "mail.smtp.starttls.enable";
    private static final String MAIL_SMTP_TIMEOUT = "mail.smtp.timeout";
    private static final String MAIL_DEBUG = "mail.debug";
    private static final String MAIL_TRANSPORT_PROTOCOL = "mail.transport.protocol";
    private static final String MAIL_SMTP_SOCKETFACTORY_CLASS = "mail.smtp.socketFactory.class";
    private static final String MAIL_SMTP_SSL_SOCKETFACTORY = "mail.smtp.ssl.socketFactory";
    private static final String MAIL_SMTP_PORT = "mail.smtp.port";
    private static final String MAIL_SMTP_SOCKETFACTORY_PORT = "mail.smtp.socketFactory.port";




    public static void sendEmail(String smtpHost, Integer smtpPort, String senderUserName, String senderPassword, String[] toUsers, String[] ccUsers, String[] bccUsers, String subject, String content, Map<String, File> files) throws Exception {

        JavaMailSenderImpl mailSender = initSendEmail(smtpHost, smtpPort, senderUserName, senderPassword);

        if (toUsers == null) {
            throw new RuntimeException("argument error,toUsers not allow null ");
        }

        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true, DEFAULT_CHARSET);

        helper.setFrom(senderUserName);
        helper.setTo(toUsers);

        if (bccUsers != null) {
            helper.setBcc(bccUsers);
        }

        if (ccUsers != null) {
            helper.setCc(ccUsers);
        }

        helper.setSubject(subject);
        helper.setText(content, true);

        helper.setSentDate(new Date());
        try {

            if (files != null) {
                Set<Map.Entry<String, File>> fileSet = files.entrySet();
                for (Map.Entry f : fileSet) {
                    helper.addAttachment(MimeUtility.encodeText((String) f.getKey(), DEFAULT_CHARSET, "B"), (File) f.getValue());
                }
            }
            mailSender.send(message);

        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }

    }


    private static JavaMailSenderImpl initSendEmail(String smtpHost, Integer smtpPort, String senderUserName, String senderPassword) throws Exception {

        if (smtpHost == null || senderUserName == null || senderPassword == null || smtpPort == null) {
            throw new RuntimeException("argument error can't connect send email server");
        }

        try {

            JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
            mailSender.setHost(smtpHost);
            mailSender.setPort(smtpPort);
            mailSender.setUsername(senderUserName);
            mailSender.setPassword(senderPassword);
            MailSSLSocketFactory sf = new MailSSLSocketFactory();

            Properties javaMailProperties = new Properties();
            javaMailProperties.put(MAIL_SMTP_AUTH, true);
            javaMailProperties.put(MAIL_SMTP_STARTTLS_ENABLE, true);
            javaMailProperties.put(MAIL_SMTP_TIMEOUT, TIMEOUT);
            javaMailProperties.setProperty(MAIL_DEBUG, "false");
            javaMailProperties.setProperty(MAIL_TRANSPORT_PROTOCOL, "smtp");
            javaMailProperties.setProperty(MAIL_SMTP_SOCKETFACTORY_CLASS, "javax.net.ssl.SSLSocketFactory");
            javaMailProperties.put(MAIL_SMTP_SSL_SOCKETFACTORY, sf);
            javaMailProperties.put(MAIL_SMTP_PORT, smtpPort);
            javaMailProperties.put(MAIL_SMTP_SOCKETFACTORY_PORT, smtpPort);
            mailSender.setJavaMailProperties(javaMailProperties);

            return mailSender;
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    public static void sendEmail(String smtpHost, Integer smtpPort, String senderUserName, String senderPassword, String[] toUsers, String[] ccUsers, String[] bccUsers, String subject, String content) throws Exception {
        sendEmail(smtpHost, smtpPort, senderUserName, senderPassword, toUsers, ccUsers, bccUsers, subject, content, null);
    }

    public static void sendEmail(String smtpHost, Integer smtpPort, String senderUserName, String senderPassword, String[] toUsers, String subject, String content) throws Exception {
        sendEmail(smtpHost, smtpPort, senderUserName, senderPassword, toUsers, null, null, subject, content, null);
    }

    public static void sendEmail(String smtpHost, Integer smtpPort, String senderUserName, String senderPassword, String[] toUsers, String subject, String content, Map<String, File> files) throws Exception {
        sendEmail(smtpHost, smtpPort, senderUserName, senderPassword, toUsers, null, null, subject, content, files);
    }
}
