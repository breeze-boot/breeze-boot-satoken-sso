package com.breeze.boot.system;

import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.OutputFile;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Collections;

@SpringBootTest
class BreezeBootApplicationTests {

    @Autowired
    private DataSourceProperties ds;

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

}
