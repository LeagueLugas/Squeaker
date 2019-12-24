package com.squeaker.entry.utils;

import com.sendgrid.*;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class EmailService {

    private static final String API_KEY = "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";

    public static void sendMail(String email, String data) {
        Email from = new Email("squeaker@entry.com");
        String subject = "Squeaker SNS 회원가입 인증코드";
        Email to = new Email(email);
        Content content = new Content("text/html", createHTML(data));
        Mail mail = new Mail(from, subject, to, content);

        SendGrid sg = new SendGrid(API_KEY);
        Request request = new Request();
        try {
            request.setMethod(Method.POST);
            request.setEndpoint("mail/send");
            request.setBody(mail.build());
            sg.api(request);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private static String createHTML(String data) {
        ResourceLoader resourceLoader = new DefaultResourceLoader();
        Resource resource = resourceLoader.getResource("static/email.html");

        String html = data;
        StringBuffer stringBuffer = new StringBuffer();
        try {
            Object[] strings = new BufferedReader(new InputStreamReader(resource.getInputStream())).lines().toArray();

            for(Object object : strings) {
                stringBuffer.append(object);
            }
            html = stringBuffer.toString();
            html = html.replaceAll("%code%", data);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return html;
    }
}
