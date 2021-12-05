package com.jhr.license.client.core;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.jhr.license.common.model.LicenseContentExtra;
import com.jhr.license.common.util.LicenseUtils;
import de.schlichtherle.license.*;
import de.schlichtherle.xml.GenericCertificate;

import java.util.List;

/**
 * 自定义LicenseManager，用于增加额外的服务器硬件信息校验
 *
 * @author xukun
 * @since 1.0.0.RELEASE
 */
public class CustomLicenseManager extends LicenseManager {

    public CustomLicenseManager() {
    }

    public CustomLicenseManager(LicenseParam param) {
        super(param);
    }

    @Override
    protected synchronized LicenseContent verify(final LicenseNotary notary) throws Exception {
        byte[] key = getLicenseKey();
        if (null == key) {
            throw new NoLicenseInstalledException(getLicenseParam().getSubject());
        } else {
            GenericCertificate certificate = getPrivacyGuard().key2cert(key);
            notary.verify(certificate);
            LicenseContent content = (LicenseContent) certificate.getContent();
            this.validate(content);
            setCertificate(certificate);
            return content;
        }
    }

    @Override
    protected synchronized void validate(LicenseContent content) throws LicenseContentException {
        // 调用父类的validate方法
        super.validate(content);
        // 校验自定义的License参数
        // License中可被允许的参数信息
        LicenseContentExtra expectedLicenseVerifier = (LicenseContentExtra) content.getExtra();
        // 当前服务器真实的参数信息
        LicenseContentExtra currentLicenseVerifier = LicenseUtils.getLicenseContentExtra();
        if (expectedLicenseVerifier != null && currentLicenseVerifier != null) {
            // 校验IP地址
            if (verifyAddressIllegal(expectedLicenseVerifier.getIpAddressList(), currentLicenseVerifier.getIpAddressList())) {
                throw new LicenseContentException("当前服务器的IP没在授权范围内");
            }
            // 校验MAC地址
            if (verifyAddressIllegal(expectedLicenseVerifier.getMacAddressList(), currentLicenseVerifier.getMacAddressList())) {
                throw new LicenseContentException("当前服务器的Mac地址没在授权范围内");
            }
            // 校验主板序列号
            if (verifySerialIllegal(expectedLicenseVerifier.getMainBoardSerial(), currentLicenseVerifier.getMainBoardSerial())) {
                throw new LicenseContentException("当前服务器的主板序列号没在授权范围内");
            }
            // 校验CPU序列号
            if (verifySerialIllegal(expectedLicenseVerifier.getCpuSerial(), currentLicenseVerifier.getCpuSerial())) {
                throw new LicenseContentException("当前服务器的CPU序列号没在授权范围内");
            }
        } else {
            throw new LicenseContentException("不能获取服务器硬件信息");
        }
    }

    /**
     * 校验当前服务器的IP/Mac地址是否在可被允许的IP范围内
     * 如果存在IP在可被允许的IP/Mac地址范围内，则返回true
     *
     * @param expectedList 可被允许的IP范围
     * @param serverList   当前服务器的IP范围
     * @return 是否非法
     */
    private boolean verifyAddressIllegal(List<String> expectedList, List<String> serverList) {
        if (CollUtil.isNotEmpty(expectedList)) {
            if (CollUtil.isNotEmpty(serverList)) {
                return serverList.stream().noneMatch(expectedList::contains);
            }
        }
        // 期望地址都为空则代表所有地址都可以安装
        return false;
    }

    /**
     * 校验当前服务器硬件（主板、CPU等）序列号是否在可允许范围内
     *
     * @param expectedSerial 可被允许的序列号
     * @param serverSerial   当前服务器的序列号
     * @return 是否非法
     */
    private boolean verifySerialIllegal(String expectedSerial, String serverSerial) {
        if (StrUtil.isNotBlank(expectedSerial)) {
            if (StrUtil.isNotBlank(serverSerial)) {
                return !expectedSerial.equals(serverSerial);
            }
        }
        // 期望序列号都为空则代表所有序列号都可以安装
        return false;
    }
}
