package com.jhr.license.client;

import com.jhr.license.client.config.LicenseApplicationRunner;
import com.jhr.license.client.config.LicenseProperties;
import com.jhr.license.client.core.LicenseService;
import com.jhr.license.client.interceptor.LicenseCheckInterceptor;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * License客户端自动配置类
 *
 * @author xukun
 * @since 1.0.0.RELEASE
 */
@Configuration(proxyBeanMethods = false)
@EnableConfigurationProperties(LicenseProperties.class)
public class LicenseClientAutoConfiguration implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 添加拦截器
        registry.addInterceptor(new LicenseCheckInterceptor()).addPathPatterns("/**");
    }

    @Bean
    public LicenseApplicationRunner licenseApplicationRunner() {
        return new LicenseApplicationRunner();
    }

    @Bean
    public LicenseService licenseVerify() {
        return new LicenseService();
    }
}
