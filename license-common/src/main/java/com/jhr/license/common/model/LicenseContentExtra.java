package com.jhr.license.common.model;

import java.util.List;

/**
 * 许可证校验附加参数
 *
 * @author xukun
 * @since 1.0.0.RELEASE
 */
public class LicenseContentExtra {

    /**
     * IP地址集合
     */
    private List<String> ipAddressList;

    /**
     * MAC地址集合
     */
    private List<String> macAddressList;

    /**
     * CPU序列号
     */
    private String cpuSerial;

    /**
     * 主板序列号
     */
    private String mainBoardSerial;

    public List<String> getIpAddressList() {
        return ipAddressList;
    }

    public void setIpAddressList(List<String> ipAddressList) {
        this.ipAddressList = ipAddressList;
    }

    public List<String> getMacAddressList() {
        return macAddressList;
    }

    public void setMacAddressList(List<String> macAddressList) {
        this.macAddressList = macAddressList;
    }

    public String getCpuSerial() {
        return cpuSerial;
    }

    public void setCpuSerial(String cpuSerial) {
        this.cpuSerial = cpuSerial;
    }

    public String getMainBoardSerial() {
        return mainBoardSerial;
    }

    public void setMainBoardSerial(String mainBoardSerial) {
        this.mainBoardSerial = mainBoardSerial;
    }

    @Override
    public String toString() {
        return "LicenseVerifier{" +
                "ipAddressList=" + ipAddressList +
                ", macAddressList=" + macAddressList +
                ", cpuSerial='" + cpuSerial + '\'' +
                ", mainBoardSerial='" + mainBoardSerial + '\'' +
                '}';
    }
}
