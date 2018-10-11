package com.zhengtoon.framework.email.service;

import com.zhengtoon.framework.email.dto.EmailDTO;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring4.SpringTemplateEngine;

import javax.annotation.PostConstruct;
import javax.mail.Message;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

/**
 * @auther: qindaorong
 * @Date: 2018/7/31 15:58
 * @Description:
 */
@Service
@Log
public class MailService {
    private static final String ENCODING = "UTF-8";

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private SpringTemplateEngine templateEngine;

    private InputStream in = null;

    @Value("${spring.mail.username:}")
    public String userName;

    public void send(EmailDTO mail) throws Exception {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(userName);
        message.setTo(mail.getToList().toArray(new String[mail.getToList().size()]));
        message.setSubject(mail.getSubject());
        message.setText(mail.getContent());
        mailSender.send(message);
        log.info("Email has bean send!");
    }

    public void sendHTML(EmailDTO mail) throws Exception {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true,ENCODING);

        helper.setFrom(userName);
        helper.setTo(mail.getToList().toArray(new String[mail.getToList().size()]));

        //cc is not null
        if(!CollectionUtils.isEmpty(mail.getCcList())){
            helper.setCc(mail.getCcList().toArray(new String[mail.getCcList().size()]));
        }

        //bcc is not null
        if(!CollectionUtils.isEmpty(mail.getBccList())) {
            helper.setBcc(mail.getBccList().toArray(new String[mail.getBccList().size()]));
        }

        //attachment is not null
        if(!CollectionUtils.isEmpty(mail.getFileMap())) {
            for (Map.Entry<String, FileSystemResource> entry : mail.getFileMap().entrySet()) {
                helper.addAttachment(entry.getKey(), entry.getValue());
            }
        }

        helper.setSubject(mail.getSubject());

        Context context = new Context();
        this.buildContext(mail,context);

        String text = templateEngine.process(mail.getTemplate(), context);
        helper.setText(text, true);
        mailSender.send(message);
        log.info("Email has bean send!");
    }


    private void buildContext(EmailDTO mail,Context context){
        Map<String,String> map = mail.getKvMap();
        if(!CollectionUtils.isEmpty(map)){
            for (Map.Entry<String, String> entry : map.entrySet()) {
                context.setVariable(entry.getKey(), entry.getValue());
            }
        }
    }


}
