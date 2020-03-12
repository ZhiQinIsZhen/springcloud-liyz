package com.liyz.cloud.service.sms.config;

import com.liyz.cloud.common.base.enums.CommonCodeEnum;
import com.liyz.cloud.common.model.bo.sms.EmailMessageBO;
import com.liyz.cloud.common.model.exception.sms.RemoteSmsServiceException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import javax.annotation.PostConstruct;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.util.List;
import java.util.Properties;

/**
 * 注释:
 *
 * @author liyangzhen
 * @version 1.0.0
 * @date 2019/9/6 10:02
 */
@Slf4j
@Component
public class EmailConfig {

    @Value("${spring.mail.host}")
    private  String HOST;
    @Value("${spring.mail.port}")
    private  String PORT;
    @Value("${spring.mail.username}")
    private  String NAME;
    @Value("${spring.mail.password}")
    private  String PWD;

    private static String emailHost;
    private static String emailPort;
    private static String emailName;
    private static String emailPwd;

    @PostConstruct
    public void init() {
        this.emailHost=this.HOST;
        this.emailPort=this.PORT;
        this.emailName=this.NAME;
        this.emailPwd=this.PWD;
    }

    public static void sendSmtpEmail(EmailMessageBO emailMessage) {
        try {
            // 使用环境属性和授权信息，创建邮件会话
            Session session = Session.getInstance(getEmailProperties(), buildAuthorization());
            session.setDebug(false);//代表启用debug模式，可以在控制台输出smtp协议应答的过程
            //创建一个MimeMessage格式的邮件
            MimeMessage message = new MimeMessage(session);

            //设置发送者
            Address fromAddress = new InternetAddress(emailName);//邮件地址
            message.setFrom(fromAddress);//设置发送的邮件地址

            //设置接收者
            Address toAddress = new InternetAddress(emailMessage.getAddress());//要接收邮件的邮箱
            message.setRecipient(Message.RecipientType.TO, toAddress);//设置接收者的地址

            //设置邮件的主题
            message.setSubject(emailMessage.getSubject());
            //设置邮件的内容
            String msgBody = getMessage(emailMessage.getParams(), emailMessage.getContent());

            Multipart multipart = new MimeMultipart();
            BodyPart html = new MimeBodyPart();

            html.setContent(msgBody, "text/html; charset=utf-8");
            multipart.addBodyPart(html);

            message.setContent(multipart);
            //保存邮件
            message.saveChanges();

            //得到发送邮件的服务器(这里用的是smtp服务器)
            Transport transport = session.getTransport("smtp");

            //发送者的账号连接到smtp服务器上
            transport.connect(emailHost, emailName, emailPwd);
            //发送信息
            transport.sendMessage(message, message.getAllRecipients());
            //关闭服务器通道
            transport.close();
            log.info("邮件发送完成，接收者：" + emailMessage.getAddress());
        } catch (Exception e) {
            log.error("发送邮件异常", e);
        }
    }

    public static Properties getEmailProperties() {
        Properties props = new Properties();                    // 参数配置
        props.setProperty("mail.transport.protocol", "smtp");   // 使用的协议（JavaMail规范要求）
        props.setProperty("mail.smtp.host", emailHost);   // 发件人的邮箱的 SMTP 服务器地址
        props.setProperty("mail.smtp.auth", "true");            // 需要请求认证
        props.setProperty("mail.smtp.ssl.enable", "true");
        props.setProperty("mail.smtp.port", emailPort);
        props.setProperty("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
//        props.setProperty("mail.smtp.socketFactory.fallback", "false");
        props.setProperty("mail.smtp.socketFactory.port", emailPort);
        props.setProperty("mail.user", emailName);
        props.setProperty("mail.password", emailPwd);
        return props;
    }

    private static Authenticator buildAuthorization() {
        return new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(emailName, emailPwd);
            }
        };
    }

    private static String getMessage(List<String> params, String template) {
        if (CollectionUtils.isEmpty(params) && template.indexOf("%s") != -1) {
            log.error("模板：'" + template + "' 参数不能为空");
            throw new RemoteSmsServiceException(CommonCodeEnum.ParameterError);
        }
        if (!CollectionUtils.isEmpty(params)) {
            for (int i = 0; i < params.size(); i++) {
                template = replaceIndex(template.indexOf("%s"), template, params.get(i));
            }
        }
        return template;
    }

    private static String replaceIndex(int index, String str, String var) {
        return str.substring(0, index) + var + str.substring(index + 2);
    }
}
