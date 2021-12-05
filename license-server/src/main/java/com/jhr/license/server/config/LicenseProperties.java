package com.jhr.license.server.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * License服务端参数
 *
 * @author xukun
 * @since 1.0.0.RELEASE
 */
@Component
@ConfigurationProperties(prefix = "license")
public class LicenseProperties {
    /**
     * 许可证subject
     */
    private String subject;

    /**
     * 私钥别称
     */
    private String alias;

    /**
     * 私钥密码
     */
    private String keyPass;

    /**
     * 公钥密码
     */
    private String storePass;

    /**
     * 私钥路径路径
     */
    private String storePath;

    /**
     * 用户类型
     */
    private String consumerType;

    /**
     * 用户数量
     */
    private Integer consumerAmount;

    /**
     * 描述信息
     */
    private String description;

    /**
     * 发布及拥有证书者信息
     */
    private String holderAndIssuer;

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

    public String getKeyPass() {
        return keyPass;
    }

    public void setKeyPass(String keyPass) {
        this.keyPass = keyPass;
    }

    public String getStorePass() {
        return storePass;
    }

    public void setStorePass(String storePass) {
        this.storePass = storePass;
    }

    public String getStorePath() {
        return storePath;
    }

    public void setStorePath(String storePath) {
        this.storePath = storePath;
    }

    public String getConsumerType() {
        return consumerType;
    }

    public void setConsumerType(String consumerType) {
        this.consumerType = consumerType;
    }

    public Integer getConsumerAmount() {
        return consumerAmount;
    }

    public void setConsumerAmount(Integer consumerAmount) {
        this.consumerAmount = consumerAmount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getHolderAndIssuer() {
        return holderAndIssuer;
    }

    public void setHolderAndIssuer(String holderAndIssuer) {
        this.holderAndIssuer = holderAndIssuer;
    }
}
