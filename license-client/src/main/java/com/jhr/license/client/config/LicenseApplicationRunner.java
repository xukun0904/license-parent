package com.jhr.license.client.config;

import com.jhr.license.client.core.LicenseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * 安装许可证启动类
 *
 * @author xukun
 * @since 1.0.0.RELEASE
 */
public class LicenseApplicationRunner implements ApplicationRunner {

    private static final Logger LOGGER = LoggerFactory.getLogger(LicenseApplicationRunner.class);

    @Autowired
    private ConfigurableApplicationContext context;

    @Autowired
    private LicenseService licenseVerify;

    @Override
    public void run(ApplicationArguments args) {
        try {
            LOGGER.info("--------------------开始安装许可证--------------------");
            // 安装许可证
            licenseVerify.install();
            LOGGER.info("--------------------安装许可证成功--------------------");
        } catch (Exception e) {
            LOGGER.error("安装许可证失败！", e);
            context.close();
        }
    }
}
