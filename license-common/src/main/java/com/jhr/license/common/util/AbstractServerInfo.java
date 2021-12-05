package com.jhr.license.common.util;

import cn.hutool.core.collection.CollUtil;
import com.jhr.license.common.model.LicenseContentExtra;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 通用获取客户服务器的基本信息，IP、MAC地址、CPU序列号、主板序列号等
 *
 * @author xukun
 * @since 1.0.0.RELEASE
 */
public abstract class AbstractServerInfo {

    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractServerInfo.class);

    /**
     * 获取许可证校验附加参数
     *
     * @return LicenseContentExtra
     */
    public LicenseContentExtra getLicenseContentExtra() {
        LicenseContentExtra licenseContentExtra = new LicenseContentExtra();
        licenseContentExtra.setIpAddressList(this.listIpAddress());
        licenseContentExtra.setMacAddressList(this.listMacAddress());
        licenseContentExtra.setCpuSerial(this.getCpuSerial());
        licenseContentExtra.setMainBoardSerial(this.getMainBoardSerial());
        return licenseContentExtra;
    }

    /**
     * 获取所有IP地址
     *
     * @return IP地址集合
     */
    public List<String> listIpAddress() {
        List<String> ipAddressList = null;
        // 获取所有网络接口
        List<InetAddress> inetAddressList = listLocalInetAddress();
        if (CollUtil.isNotEmpty(inetAddressList)) {
            ipAddressList = inetAddressList.stream().map(InetAddress::getHostAddress).distinct()
                    .map(String::toLowerCase).collect(Collectors.toList());
        }
        return ipAddressList;
    }

    /**
     * 获取所有Mac地址
     *
     * @return mac地址集合
     */
    public List<String> listMacAddress() {
        List<String> macAddressList = null;
        // 获取所有网络接口
        List<InetAddress> inetAddressList = listLocalInetAddress();
        if (CollUtil.isNotEmpty(inetAddressList)) {
            // 获取所有网络接口的Mac地址
            macAddressList = inetAddressList.stream().map(this::getMacByInetAddress).distinct().collect(Collectors.toList());
        }
        return macAddressList;
    }

    /**
     * 获取CPU序列号
     *
     * @return CPU序列号
     */
    protected abstract String getCpuSerial();

    /**
     * 获取主板序列号
     *
     * @return 主板序列号
     */
    protected abstract String getMainBoardSerial();

    /**
     * 获取当前服务器所有符合条件的InetAddress
     *
     * @return InetAddress集合
     */
    protected List<InetAddress> listLocalInetAddress() {
        try {
            List<InetAddress> inetAddressList = new ArrayList<>();
            // 遍历所有的网络接口
            for (Enumeration<NetworkInterface> networkInterfaces = NetworkInterface.getNetworkInterfaces();
                 networkInterfaces.hasMoreElements(); ) {
                NetworkInterface networkInterface = networkInterfaces.nextElement();
                // 在所有的接口下再遍历IP
                for (Enumeration<InetAddress> inetAddresses = networkInterface.getInetAddresses();
                     inetAddresses.hasMoreElements(); ) {
                    InetAddress inetAddress = inetAddresses.nextElement();
                    // 排除LoopbackAddress、SiteLocalAddress、LinkLocalAddress、MulticastAddress类型的IP地址
                    if (!inetAddress.isLoopbackAddress()
                            /*&& !inetAddress.isSiteLocalAddress()*/
                            && !inetAddress.isLinkLocalAddress() && !inetAddress.isMulticastAddress()) {
                        inetAddressList.add(inetAddress);
                    }
                }
            }
            return inetAddressList;
        } catch (SocketException e) {
            LOGGER.error("获取当前服务器所有符合条件的InetAddress失败", e);
        }
        return null;
    }

    /**
     * 根据网络接口获取Mac地址
     *
     * @param inetAddress 网络接口
     * @return Mac地址
     */
    private String getMacByInetAddress(InetAddress inetAddress) {
        try {
            byte[] mac = NetworkInterface.getByInetAddress(inetAddress).getHardwareAddress();
            StringBuilder stringBuilder = new StringBuilder();
            for (int i = 0; i < mac.length; i++) {
                if (i != 0) {
                    stringBuilder.append("-");
                }
                // 将十六进制byte转化为字符串
                String temp = Integer.toHexString(mac[i] & 0xff);
                if (temp.length() == 1) {
                    stringBuilder.append("0").append(temp);
                } else {
                    stringBuilder.append(temp);
                }
            }
            return stringBuilder.toString().toUpperCase();
        } catch (SocketException e) {
            LOGGER.error("根据网络接口获取Mac地址", e);
        }
        return null;
    }
}
