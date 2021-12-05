package com.jhr.license.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 许可证授权服务启动类
 *
 * @author xukun
 * @since 1.0.0.RELEASE
 */
@SpringBootApplication
public class LicenseServerApplication {
    public static void main(String[] args) {
        SpringApplication.run(LicenseServerApplication.class, args);
    }
}
