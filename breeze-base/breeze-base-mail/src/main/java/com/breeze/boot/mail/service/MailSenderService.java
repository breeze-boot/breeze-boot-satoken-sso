/*
 * Copyright (c) 2023, gaoweixuan (breeze-cloud@foxmail.com).
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.breeze.boot.mail.service;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.toolkit.ArrayUtils;
import com.breeze.boot.mail.config.MailProperties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.MapUtils;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.MessagingException;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.io.File;
import java.util.Date;
import java.util.Map;
import java.util.Set;

/**
 * 邮件发送服务
 *
 * @author gaoweixuan
 * @since 2023/04/15
 */
@Slf4j
@Component
@RequiredArgsConstructor
@EnableConfigurationProperties(MailProperties.class)
public class MailSenderService {

    /**
     * 发送发邮箱地址
     */
    private final MailProperties mailProperties;

    /**
     * 邮件发送者
     */
    private final JavaMailSender javaMailSender;

    /**
     * 自动配置模版引擎
     */
    private final TemplateEngine templateEngine;

    /**
     * 发送纯文本邮件信息
     *
     * @param to      接收方
     * @param subject 邮件主题
     * @param content 邮件内容
     */
    public void sendMessage(String to, String subject, String content) {
        // 创建一个邮件对象
        SimpleMailMessage msg = new SimpleMailMessage();
        // 设置发送方
        msg.setFrom(mailProperties.getFromAddress());
        // 设置接收方
        msg.setTo(to);
        // 设置邮件主题
        msg.setSubject(subject);
        // 设置邮件内容
        msg.setText(content);
        // 发送邮件
        this.javaMailSender.send(msg);
    }

    /**
     * 发送带附件的邮件信息
     *
     * @param to      接收方
     * @param subject 邮件主题
     * @param content 邮件内容
     * @param files   文件可变数组
     */
    public void sendMessageCarryFile(String to, String subject, String content, File... files) {
        MimeMessage mimeMessage = this.javaMailSender.createMimeMessage();
        try {
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
            // 设置发送方
            mimeMessageHelper.setFrom(mailProperties.getFromAddress());
            // 设置接收方
            mimeMessageHelper.setTo(to);
            // 设置邮件主题
            mimeMessageHelper.setSubject(subject);
            // 设置邮件内容
            mimeMessageHelper.setText(content);
            //设置显示的发件时间
            mimeMessageHelper.setSentDate(new Date());
            if (files != null) {
                // 添加附件（多个）
                for (File file : files) {
                    mimeMessageHelper.addAttachment(file.getName(), file);
                }
            }
        } catch (MessagingException e) {
            log.error("邮件消息发送失败", e);
        }
        // 发送邮件
        this.javaMailSender.send(mimeMessage);
    }

    /**
     * 发送且抄送带附件的邮件信息
     *
     * @param to      接收方
     * @param cc      抄送
     * @param subject 邮件主题
     * @param content 邮件内容
     * @param files   文件可变数组
     */
    public void sendMessageCarryFile(String to, String cc, String subject, String content, Map<String, File> imageMap, File... files) {
        MimeMessage mimeMessage = this.javaMailSender.createMimeMessage();
        try {
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
            // 设置发送方
            mimeMessageHelper.setFrom(mailProperties.getFromAddress());
            // 设置接收方
            mimeMessageHelper.setTo(to);
            // 设置抄送
            mimeMessageHelper.setCc(cc);
            // 设置邮件主题
            mimeMessageHelper.setSubject(subject);
            // 设置邮件内容
            mimeMessageHelper.setText(content);
            //设置显示的发件时间
            mimeMessageHelper.setSentDate(new Date());

            MimeMultipart allMultipart = new MimeMultipart();
            //创建代表邮件正文和附件的各个MimeBodyPart对象
            MimeBodyPart contentPart = this.createContent(content, imageMap);
            allMultipart.addBodyPart(contentPart);
            if (files != null) {
                //创建用于组合邮件正文和附件的MimeMultipart对象
                for (File file : files) {
                    allMultipart.addBodyPart(this.createAttachment(file));
                }
            }
            //设置整个邮件内容为最终组合出的MimeMultipart对象
            mimeMessage.setContent(allMultipart);
            mimeMessage.saveChanges();
        } catch (MessagingException e) {
            log.error("邮件消息发送失败", e);
        }
        // 发送邮件
        this.javaMailSender.send(mimeMessage);
    }

    /**
     * 创建内容
     *
     * @param content html格式的文本内容
     * @param fileMap 图片集合
     * @return {@link MimeBodyPart}
     */
    public MimeBodyPart createContent(String content, Map<String, File> fileMap) {
        MimeBodyPart contentMimeBodyPart = new MimeBodyPart();
        try {
            // 创建用于保存HTML正文的MimeBodyPart对象，并将它保存到MimeMultipart中
            MimeBodyPart htmlMimeBodyPart = new MimeBodyPart();
            htmlMimeBodyPart.setContent(content, "text/html;charset=UTF-8");

            MimeMultipart contentMimeMultipart = new MimeMultipart("related");
            contentMimeMultipart.addBodyPart(htmlMimeBodyPart);

            if (CollUtil.isNotEmpty(fileMap)) {
                return contentMimeBodyPart;
            }

            Set<Map.Entry<String, File>> entrySet = fileMap.entrySet();
            for (Map.Entry<String, File> entry : entrySet) {
                MimeBodyPart picMimeBodyPart = new MimeBodyPart();
                //cid的值
                picMimeBodyPart.setContentID(entry.getKey());
                // 图片文件
                FileDataSource fileDataSource = new FileDataSource(entry.getValue());
                // 创建用于保存图片的MimeBodyPart对象，并将它保存到MimeMultipart中
                picMimeBodyPart.setDataHandler(new DataHandler(fileDataSource));
                contentMimeMultipart.addBodyPart(picMimeBodyPart);
            }
            // 将MimeMultipart对象保存到MimeBodyPart对象
            contentMimeBodyPart.setContent(contentMimeMultipart);
        } catch (MessagingException e) {
            log.error("邮件消息发送失败", e);
        }
        return contentMimeBodyPart;
    }

    /**
     * 创建附件
     *
     * @param file 文件
     * @return {@link MimeBodyPart}
     */
    public MimeBodyPart createAttachment(File file) {
        // 创建保存附件的MimeBodyPart对象，并加入附件内容和相应的信息
        MimeBodyPart mimeBodyPart = new MimeBodyPart();
        try {
            FileDataSource fileDataSource = new FileDataSource(file);
            mimeBodyPart.setDataHandler(new DataHandler(fileDataSource));
            mimeBodyPart.setFileName(fileDataSource.getName());
        } catch (MessagingException e) {
            log.error("邮件消息发送失败", e);
        }
        return mimeBodyPart;
    }

    /**
     * 发送模板邮件
     */
    public void sendThymeleafMail(String subject, String from, Map<String, Object> valueMap, String... toMails) {
        MimeMessage mimeMessage;
        if (ArrayUtils.isEmpty(toMails)) {
            return;
        }
        try {
            mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
            // 设置发件人邮箱
            helper.setFrom(from);
            // 设置收件人邮箱
            helper.setTo(toMails);
            // 设置邮件标题
            helper.setSubject(subject);
            // 设置邮件正文
            helper.setSubject(valueMap.get("content").toString());
            // 添加正文（使用thymeleaf模板）
            Context context = new Context();
            context.setVariables(valueMap);
            String content = this.templateEngine.process("mail", context);
            helper.setText(content, true);
            // 发送邮件
            javaMailSender.send(mimeMessage);
        } catch (MessagingException e) {
            log.error("发送失败");
        }
    }

}

