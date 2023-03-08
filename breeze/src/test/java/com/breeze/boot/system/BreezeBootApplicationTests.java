package com.breeze.boot.system;

import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.OutputFile;
import com.breeze.boot.system.domain.SysPlatform;
import com.breeze.boot.system.mapper.SysPlatformMapper;
import com.breeze.mail.MailSender;
import com.google.common.collect.Lists;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.File;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SpringBootTest
class BreezeBootApplicationTests {

    @Autowired
    private DataSourceProperties ds;

    @Autowired
    private SysPlatformMapper sysPlatformMapper;

    @Autowired
    private MailSender mailSender;

    @Test
    void contextLoads() {
        FastAutoGenerator.create(ds.getUrl(), ds.getUsername(), ds.getPassword())
                .globalConfig(builder -> {
                    builder.author("gwx") // 设置作者
                            .enableSwagger()// 开启 swagger 模式
                            .outputDir("D:\\breeze-boot\\breeze-system\\src\\main\\java"); // 指定输出目录
                })
                .packageConfig(builder -> {
                    builder.parent("com.breeze.boot") // 设置父包名
                            .moduleName("system") // 设置父包模块名
                            .pathInfo(Collections.singletonMap(OutputFile.xml, "D:\\breeze-boot\\breeze-system\\src\\main\\resources\\mapper")); // 设置mapperXml生成路径
                })
                .strategyConfig(builder -> {
                    builder.addInclude("t_test") // 设置需要生成的表名
                            .addTablePrefix("t_", "c_"); // 设置过滤表前缀
                })
                .execute();
    }

    @Test
    void saveAllBatch() {
        List<SysPlatform> list = Lists.newArrayList(
                SysPlatform.builder().platformCode("0").build(),
                SysPlatform.builder().platformCode("1").build(),
                SysPlatform.builder().platformCode("2").build(),
                SysPlatform.builder().platformCode("3").build()
        );
        this.sysPlatformMapper.insertAllBatch(list);
    }

    /**
     * 测试文本邮件发送（无附件）
     */
    @Test
    void sendStringEmail() {
        String title = "文本邮件主题";
        String content = "文本邮件内容";
        String to = "@qq.com";
        mailSender.sendMessage(to, title, content);
    }

    /**
     * 测试单个附件邮件发送
     */
    @Test
    void sendFileEmail() {
        String title = "单附件邮件主题";
        String content = "单附件邮件内容";
        String to = "@qq.com";
        File file = new File("D:\\collect\\demo-mail.zip");
        mailSender.sendMessageCarryFile(to, title, content, file);
    }

    /**
     * 测试多个附件邮件发送
     */
    @Test
    void sendFilesEmail() {
        String title = "多附件邮件主题";
        String content = "多附件邮件内容";
        String to = "@qq.com";
        String cc = "@qq.com";
        File[] files = new File[2];
        files[0] = new File("D:\\collect\\demo-mail.zip");
        files[1] = new File("D:\\collect\\apache-maven-3.6.3-bin.zip");
        mailSender.sendMessageCarryFile(to, cc, title, content, new HashMap<>(), files);
    }

    /**
     * 测试多个附件邮件和Html图片发送
     */
    @Test
    void sendPicFilesEmail() {
        String title = "HTML邮件";
        String to = "@qq.com";
        Map<String, File> imageMap = new HashMap<>();
        imageMap.put("NO1", new File("C:\\Users\\JAVA_DEVELOPER\\Desktop\\文档\\test.png"));
        // 内嵌图片
        StringBuilder sb = new StringBuilder();
        sb.append("<h2>HTML邮件</h2>")
                .append("<p style='text-align:left'>HTML邮件...</p>")
                .append("<img src=\"cid:NO1\"><br/><br/>")
                .append("<p> 时间为：").append(LocalDateTime.now()).append("</p>");

        File[] files = new File[2];
        files[0] = new File("D:\\collect\\demo-mail.zip");
        files[1] = new File("D:\\collect\\apache-maven-3.6.3-bin.zip");
        mailSender.sendMessageCarryFile(to, "", title, sb.toString(), imageMap, files);
    }

}
