package com.jhr.license.common.util;

import cn.hutool.core.util.RuntimeUtil;
import cn.hutool.core.util.StrUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * windows服务器信息
 *
 * @author xukun
 * @since 1.0.0.RELEASE
 */
public class WindowsServerInfo extends AbstractServerInfo {

    public static final Logger LOGGER = LoggerFactory.getLogger(WindowsServerInfo.class);

    @Override
    protected String getCpuSerial() {
        // 使用WMIC获取CPU序列号
        try {
            List<String> resultLines = RuntimeUtil.execForLines("wmic", "cpu", "get", "processorid");
            return StrUtil.trim(resultLines.get(2));
        } catch (Exception e) {
            LOGGER.error("使用WMIC获取CPU序列号失败！");
        }
        return null;
    }

    @Override
    protected String getMainBoardSerial() {
        // 使用WMIC获取主板序列号
        try {
            List<String> resultLines = RuntimeUtil.execForLines("wmic", "baseboard", "get", "serialnumber");
            return StrUtil.trim(resultLines.get(2));
        } catch (Exception e) {
            LOGGER.error("使用WMIC获取主板序列号失败！");
        }
        return null;
    }
}
