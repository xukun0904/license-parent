package com.jhr.license.common.util;

import cn.hutool.core.util.RuntimeUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * linux服务器信息
 *
 * @author xukun
 * @since 1.0.0.RELEASE
 */
public class LinuxServerInfo extends AbstractServerInfo {

    public static final Logger LOGGER = LoggerFactory.getLogger(LinuxServerInfo.class);

    @Override
    protected String getCpuSerial() {
        try {
            // 使用dmidecode命令获取CPU序列号
            return RuntimeUtil.execForStr("/bin/bash", "-c", "dmidecode -t 4 | grep 'ID' | awk -F ':' '{print $2}' | head -n 1");
        } catch (Exception e) {
            LOGGER.error("使用dmidecode命令获取CPU序列号失败！");
        }
        return null;
    }

    @Override
    protected String getMainBoardSerial() {
        try {
            // 使用dmidecode命令获取主板序列号
            return RuntimeUtil.execForStr("/bin/bash", "-c", "dmidecode | grep 'Serial Number' | awk -F ':' '{print $2}' | head -n 1");
        } catch (Exception e) {
            LOGGER.error("使用dmidecode命令获取主板序列号失败！");
        }
        return null;
    }
}
