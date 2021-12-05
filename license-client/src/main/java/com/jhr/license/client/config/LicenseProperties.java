package com.jhr.license.client.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * License客户端属性
 *
 * @author xukun
 * @since 1.0.0.RELEASE
 */
@ConfigurationProperties(prefix = LicenseProperties.LICENSE_PREFIX)
public class LicenseProperties {

    public static final String LICENSE_PREFIX = "license";

    /**
     * 许可证subject
     */
    private String subject;

    /**
     * 公钥别称
     */
    private String alias;

    /**
     * 公钥密码
     */
    private String storePass;

    /**
     * 许可证路径
     */
    private String licensePath;

    /**
     * 公钥路径
     */
    private String storePath;

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public String getStorePass() {
        return storePass;
    }

    public void setStorePass(String storePass) {
        this.storePass = storePass;
    }

    public String getLicensePath() {
        return licensePath;
    }

    public void setLicensePath(String licensePath) {
        this.licensePath = licensePath;
    }

    public String getStorePath() {
        return storePath;
    }

    public void setStorePath(String storePath) {
        this.storePath = storePath;
    }
}
