package com.jhr.license.server.service;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.IoUtil;
import com.jhr.license.common.model.CustomKeyStoreParam;
import com.jhr.license.common.model.LicenseContentExtra;
import com.jhr.license.common.util.Constants;
import com.jhr.license.common.util.LicenseUtils;
import com.jhr.license.server.config.LicenseProperties;
import de.schlichtherle.license.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.security.auth.x500.X500Principal;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedInputStream;
import java.io.File;
import java.util.Date;
import java.util.prefs.Preferences;

/**
 * License生成类
 *
 * @author xukun
 * @since 1.0.0.RELEASE
 */
@Service
public class LicenseService {

    private static final Logger LOGGER = LoggerFactory.getLogger(LicenseService.class);

    private final LicenseProperties properties;

    private final X500Principal principal;

    public LicenseService(LicenseProperties properties) {
        this.properties = properties;
        principal = new X500Principal(properties.getHolderAndIssuer());
    }

    /**
     * 生成License许可证
     */
    public boolean generateLicense(Boolean dev, Date issuedTime, Date expiryTime, HttpServletResponse response) {
        File tempFile = null;
        BufferedInputStream inputStream = null;
        try (ServletOutputStream outputStream = response.getOutputStream()) {
            LicenseManager licenseManager = new LicenseManager(initLicenseParam());
            if (issuedTime == null) {
                // 生效时间默认当前时间
                issuedTime = new Date();
            }
            LicenseContent licenseContent = initLicenseContent(dev, issuedTime, expiryTime);
            // 生成到临时文件
            tempFile = FileUtil.createTempFile(FileUtil.getTmpDir());
            licenseManager.store(licenseContent, tempFile);
            // 进行下载
            response.setContentType("application/octet-stream");
            response.setHeader("Content-Disposition", "inline;filename=" + Constants.LICENSE_FILE_NAME);
            inputStream = FileUtil.getInputStream(tempFile);
            IoUtil.copy(inputStream, outputStream);
            return true;
        } catch (Exception e) {
            LOGGER.error("许可证生成失败！", e);
        } finally {
            // 释放资源
            IoUtil.close(inputStream);
            FileUtil.del(tempFile);
        }
        return false;
    }

    /**
     * 初始化许可证生成参数
     */
    private LicenseParam initLicenseParam() {
        Preferences preferences = Preferences.userNodeForPackage(LicenseService.class);
        // 设置对许可证内容加密的秘钥
        CipherParam cipherParam = new DefaultCipherParam(properties.getStorePass());
        KeyStoreParam privateStoreParam = new CustomKeyStoreParam(LicenseService.class, LicenseUtils.getConfPath(properties.getStorePath(), Constants.PRIVATE_KEY_NAME),
                properties.getAlias(), properties.getStorePass(), properties.getKeyPass());
        return new DefaultLicenseParam(properties.getSubject(), preferences, privateStoreParam, cipherParam);
    }

    /**
     * 设置许可证生成内容
     */
    private LicenseContent initLicenseContent(Boolean dev, Date issuedTime, Date expiryTime) {
        LicenseContent licenseContent = new LicenseContent();
        licenseContent.setHolder(principal);
        licenseContent.setIssuer(principal);
        licenseContent.setSubject(properties.getSubject());
        licenseContent.setIssued(issuedTime);
        licenseContent.setNotBefore(issuedTime);
        licenseContent.setNotAfter(expiryTime);
        licenseContent.setConsumerType(properties.getConsumerType());
        licenseContent.setConsumerAmount(properties.getConsumerAmount());
        licenseContent.setInfo(properties.getDescription());
        LicenseContentExtra target;
        if (!dev) {
            // 生产环境
            target = LicenseUtils.getLicenseContentExtra();
        } else {
            // 开发环境
            target = new LicenseContentExtra();
        }
        // 扩展校验服务器硬件信息
        licenseContent.setExtra(target);
        return licenseContent;
    }
}
