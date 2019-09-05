package com.test;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.config.ConfigurationSource;
import org.apache.logging.log4j.core.config.Configurator;
import org.mybatis.generator.api.MyBatisGenerator;
import org.mybatis.generator.config.Configuration;
import org.mybatis.generator.config.xml.ConfigurationParser;
import org.mybatis.generator.internal.DefaultShellCallback;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

public class GeneratorSqlmap {
    private Logger logger;

    // 执行main方法以生成代码
    public static void main(String[] args) {
        try {
            GeneratorSqlmap generatorSqlmap = new GeneratorSqlmap();
            generatorSqlmap.InitLog4jConfig();
            generatorSqlmap.generator();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e.toString());
        }
    }

    //指定log4j配置文件路径
    private void InitLog4jConfig() {
//        LoggerContext logContext = (LoggerContext) LogManager.getContext(false);
        File conFile = new File("src/main/resources/config/log4j2.xml");
//        logContext.setConfigLocation(conFile.toURI());
//        logContext.reconfigure();

        ConfigurationSource source = null;
        try {
            source = new ConfigurationSource(new FileInputStream(conFile), conFile);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        Configurator.initialize(null, source);
        logger = LogManager.getLogger(GeneratorSqlmap.class.getName());
        logger.info("初始化成功\n[{}]", "log4j");
    }

    //指定mybatis-generator配置文件路径
    private void generator() throws Exception {
        List<String> warnings = new ArrayList<String>();
        boolean overwrite = true;
        // 指定配置文件
        File configFile = new File("src/main/resources/config/generatorConfig.xml");
        ConfigurationParser cp = new ConfigurationParser(warnings);
        Configuration config = cp.parseConfiguration(configFile);
        DefaultShellCallback callback = new DefaultShellCallback(overwrite);
        MyBatisGenerator myBatisGenerator = new MyBatisGenerator(config, callback, warnings);
        myBatisGenerator.generate(null);
        logger.info("初始化成功\n[{}]", "mybatis");
    }
}