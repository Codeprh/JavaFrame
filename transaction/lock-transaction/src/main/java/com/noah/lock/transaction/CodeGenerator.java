package com.noah.lock.transaction;

import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.OutputFile;
import com.baomidou.mybatisplus.generator.config.rules.DateType;
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
     * 交互式生成
     */
    public static void main(String[] args) throws SQLException {

        mainV2();
    }

    /**
     * 快速生成
     */
    public static void mainV2() {
        String projectPath = new File(System.getProperty("user.dir")).getParent() + "/JavaFrame/transaction/lock-transaction/src/main/java";
        String resourePath = new File(System.getProperty("user.dir")).getParent() + "/JavaFrame/transaction/lock-transaction/src/main/";

        FastAutoGenerator.create("jdbc:mysql://127.0.0.1:3306/noah?useUnicode=true&characterEncoding=utf8&autoReconnect=true&failOverReadOnly=false&allowMultiQueries=true&useSSL=false&serverTimezone=Asia/Shanghai&useAffectedRows=true",
                "root", "Noah123@")
                .globalConfig(builder -> {
                    builder.author("noah") // 设置作者
                            .enableSwagger() // 开启 swagger 模式
                            .fileOverride() // 覆盖已生成文件
                            .disableOpenDir()
                            .dateType(DateType.ONLY_DATE)
                            .outputDir(projectPath); // 指定输出目录
                })
                .packageConfig(builder -> {
                    builder.parent("com.noah.lock.transaction") // 设置父包名
                            .moduleName("") // 设置父包模块名
                            .pathInfo(Collections.singletonMap(OutputFile.mapperXml, resourePath + "/resources/mapper")); // 设置mapperXml生成路径
                })
                .strategyConfig(builder -> {
                    builder.addInclude("order_info")
                            .controllerBuilder()
                    ; // 设置需要生成的表名

                })
                .templateEngine(new FreemarkerTemplateEngine()) // 使用Freemarker引擎模板，默认的是Velocity引擎模板
                .execute();

    }


}
