package com.jhr.license.client.core;

import cn.hutool.core.date.DateUtil;
import com.jhr.license.client.config.LicenseProperties;
import com.jhr.license.common.model.CustomKeyStoreParam;
import com.jhr.license.common.util.Constants;
import com.jhr.license.common.util.LicenseUtils;
import de.schlichtherle.license.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.File;
import java.util.prefs.Preferences;

/**
 * 许可证操作
 *
 * @author xukun
 * @since 1.0.0.RELEASE
 */
public class LicenseService {

    private static final Logger LOGGER = LoggerFactory.getLogger(LicenseService.class);

    @Autowired
    private LicenseProperties properties;

    /**
     * 安装License许可证
     */
    public void install() throws Exception {
        LicenseManager licenseManager = LicenseManagerHolder.getInstance(initLicenseParam());
        // 先卸载许可证
        licenseManager.uninstall();
        // 安装许可证
        LicenseContent result = licenseManager.install(new File(LicenseUtils.getConfPath(properties.getLicensePath(), Constants.LICENSE_FILE_NAME)));
        LOGGER.info("许可证安装成功，许可证有效期：{} - {}", DateUtil.format(result.getNotBefore(), "yyyy-MM-dd HH:mm:ss"),
                DateUtil.format(result.getNotAfter(), "yyyy-MM-dd HH:mm:ss"));
    }

    /**
     * 校验License许可证
     */
    public boolean verify() {
        try {
            LicenseManager licenseManager = LicenseManagerHolder.getInstance(null);
            LicenseContent licenseContent = licenseManager.verify();
            LOGGER.debug("许可证校验通过，许可证有效期：{} - {}",
                    DateUtil.format(licenseContent.getNotBefore(), "yyyy-MM-dd HH:mm:ss"),
                    DateUtil.format(licenseContent.getNotAfter(), "yyyy-MM-dd HH:mm:ss"));
            return true;
        } catch (Exception e) {
            LOGGER.error("许可证校验失败！", e);
            return false;
        }
    }

    /**
     * 初始化许可证参数
     */
    private LicenseParam initLicenseParam() {
        Preferences preferences = Preferences.userNodeForPackage(LicenseService.class);
        CipherParam cipherParam = new DefaultCipherParam(properties.getStorePass());
        KeyStoreParam publicStoreParam = new CustomKeyStoreParam(LicenseService.class,
                LicenseUtils.getConfPath(properties.getStorePath(), Constants.PUBLIC_KEY_NAME),
                properties.getAlias(), properties.getStorePass(), null);
        return new DefaultLicenseParam(properties.getSubject(), preferences, publicStoreParam, cipherParam);
    }
}
