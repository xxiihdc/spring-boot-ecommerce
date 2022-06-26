package com.poly.ductr.app.serviceimpl;

import com.poly.ductr.app.domain.MailModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMailMessage;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.ArrayList;
import java.util.List;

@Service
public class MailerService {
    @Autowired
    private JavaMailSender javaMailSender;
    List<MimeMessage> queue = new ArrayList<>();

    public void push(String to, String subject, String body){
        MailModel mailModel = new MailModel(to,subject,body);
        this.push(mailModel);
    }

    public void push(MailModel mailModel) {
        MimeMessage msg = javaMailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(msg,true,"utf-8");
            helper.setTo(mailModel.getTo());
            helper.setSubject(mailModel.getSubject());
            helper.setText(mailModel.getBody(),true);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
        this.queue.add(msg);
    }
    @Scheduled(fixedDelay = 2000)
    public void run(){
        int success = 0;
        int error = 0;
        while (!queue.isEmpty()){
            MimeMessage mail = queue.remove(0);
            try{
                javaMailSender.send(mail);
                success++;
            }catch (Exception e){
                error++;
            }
        }
    }
}
