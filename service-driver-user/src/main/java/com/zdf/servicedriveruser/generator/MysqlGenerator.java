package com.zdf.servicedriveruser.generator;

import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.OutputFile;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;

import java.util.Collections;

public class MysqlGenerator
{
    public static void main(String[] args)
    {
        FastAutoGenerator.create("jdbc:mysql://localhost:3306/service-price?character-Encoding=utf-8&serverTimezone=GMT%2B8", "root", "zdf19941118.")
                .globalConfig(builder -> {
                    builder.author("zdf")
                            .fileOverride()
                            .outputDir("/Users/mrzhang/Desktop/Project/online-taxi-public/service-price/src/main/java");
                })
                .packageConfig(builder -> {
                    builder.parent("com.zdf.serviceprice")
                            .pathInfo(Collections.singletonMap(OutputFile.mapper, "/Users/mrzhang/Desktop/Project/online-taxi-public/service-price/src/main/java/com/zdf/serviceprice/mapper"));
                })
                .strategyConfig(builder -> {
                    builder.addInclude("price_rule");
                })
                .templateEngine(new FreemarkerTemplateEngine())
                .execute();
    }
}
