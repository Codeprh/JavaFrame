package com.noah.spring.transaction;

import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.OutputFile;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;

import java.io.File;
import java.sql.SQLException;
import java.util.Collections;

/**
 * 描述:
 * 代码生成
 *
 * @author Noah
 * @create 2021-11-15 7:55 下午
 */
public class CodeGenerator {

    //private static final DataSourceConfig.Builder DATA_SOURCE_CONFIG = new DataSourceConfig
    //        .Builder("jdbc:h2:mem:test;DB_CLOSE_DELAY=-1;DB_CLOSE_ON_EXIT=FALSE;CASE_INSENSITIVE_IDENTIFIERS=TRUE;MODE=MYSQL", "sa", "");

    /**
     * 数据源配置
     */
    private static final DataSourceConfig.Builder DATA_SOURCE_CONFIG = new DataSourceConfig.
            Builder("127.0.0.1:3306/lettuce", "root", "123456");

    /**
     * 交互式生成
     */
    public static void main(String[] args) throws SQLException {

        mainV2();
        //FastAutoGenerator.create(DATA_SOURCE_CONFIG)
        //        // 全局配置
        //        .globalConfig((scanner, builder) -> builder.author(scanner.apply("请输入作者名称？")).fileOverride())
        //        // 包配置
        //        .packageConfig((scanner, builder) -> builder.parent(scanner.apply("请输入包名？")))
        //        // 策略配置
        //        .strategyConfig(builder -> builder.addInclude("t_simple"))
        //        /*
        //            模板引擎配置，默认 Velocity 可选模板引擎 Beetl 或 Freemarker
        //           .templateEngine(new BeetlTemplateEngine())
        //           .templateEngine(new FreemarkerTemplateEngine())
        //         */
        //        .execute();
    }

    /**
     * 快速生成
     */
    public static void mainV2() {

        String projectPath = new File(System.getProperty("user.dir")).getParent() + "/JavaFrame/transaction/spring-transaction/src/main/java";
        String resourePath = new File(System.getProperty("user.dir")).getParent() + "/JavaFrame/transaction/spring-transaction/src/main/";

        FastAutoGenerator.create("jdbc:mysql://127.0.0.1:3306/lettuce?useUnicode=true&characterEncoding=utf8&autoReconnect=true&failOverReadOnly=false&allowMultiQueries=true&useSSL=false&serverTimezone=Asia/Shanghai&useAffectedRows=true",
                "root", "123456")
                .globalConfig(builder -> {
                    builder.author("noah") // 设置作者
                            .enableSwagger() // 开启 swagger 模式
                            .fileOverride() // 覆盖已生成文件
                            .outputDir(projectPath); // 指定输出目录
                })
                .packageConfig(builder -> {
                    builder.parent("com.noah.spring.transaction") // 设置父包名
                            .moduleName("") // 设置父包模块名
                            .pathInfo(Collections.singletonMap(OutputFile.mapperXml, resourePath + "/resources/mapper")); // 设置mapperXml生成路径
                })
                .strategyConfig(builder -> {
                    builder.addInclude("lettuce_config"); // 设置需要生成的表名

                })
                .templateEngine(new FreemarkerTemplateEngine()) // 使用Freemarker引擎模板，默认的是Velocity引擎模板
                .execute();

    }


}
