package com.jhr.license.common.util;

import cn.hutool.core.util.StrUtil;
import com.jhr.license.common.model.LicenseContentExtra;

import java.io.File;

/**
 * License工具类
 *
 * @author xukun
 * @since 1.0.0.RELEASE
 */
public class LicenseUtils {

    /**
     * 获取附加许可证校验参数
     *
     * @return LicenseContentExtra
     */
    public static LicenseContentExtra getLicenseContentExtra() {
        return getLicenseContentExtra(null);
    }

    /**
     * 根据系统名获取附加许可证校验参数
     *
     * @param osName 系统名
     * @return 许可证校验附加参数
     */
    public static LicenseContentExtra getLicenseContentExtra(String osName) {
        // 操作系统类型
        if (StrUtil.isBlank(osName)) {
            osName = System.getProperty(Constants.OS_NAME_KEY);
        }
        osName = osName.toLowerCase();
        AbstractServerInfo serverInfo;
        // 根据不同操作系统类型选择不同的数据获取方法
        if (osName.startsWith(Constants.OS_NAME_WINDOWS)) {
            serverInfo = new WindowsServerInfo();
        } else {
            // linux服务器
            serverInfo = new LinuxServerInfo();
        }
        return serverInfo.getLicenseContentExtra();
    }

    /**
     * 获取配置文件路径，不存在则为应用同层目录
     *
     * @param path 文件路径
     * @return 配置文件目录
     */
    public static String getConfPath(String path, String filename) {
        if (StrUtil.isBlank(path)) {
            path = System.getProperty(Constants.USER_DIR) + File.separator + filename;
        }
        return path;
    }
}
