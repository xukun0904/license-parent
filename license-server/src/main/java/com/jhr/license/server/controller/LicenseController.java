package com.jhr.license.server.controller;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.jhr.license.common.model.LicenseContentExtra;
import com.jhr.license.common.util.LicenseUtils;
import com.jhr.license.server.service.LicenseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

/**
 * 许可证颁发API
 *
 * @author xukun
 * @since 1.0.0.RELEASE
 */
@RestController
@RequestMapping("license")
public class LicenseController {

    @Autowired
    private LicenseService licenseService;

    /**
     * 获取服务器硬件信息
     *
     * @return LicenseContentExtra
     */
    @GetMapping(value = "server")
    public ResponseEntity<LicenseContentExtra> getServerInfo(@RequestParam(value = "osName", required = false) String osName) {
        return ResponseEntity.ok(LicenseUtils.getLicenseContentExtra(osName));
    }

    /**
     * 生成许可证
     *
     * @param dev           是否为开发环境
     * @param issuedTimeStr 生效时间字符串
     * @param expiryTimeStr 过期时间字符串
     * @return ResponseEntity
     */
    @PostMapping(value = "generator")
    public ResponseEntity<String> generateLicense(@RequestParam(value = "dev", required = false, defaultValue = "false") Boolean dev,
                                                  @RequestParam(value = "issuedTime", required = false) String issuedTimeStr,
                                                  @RequestParam(value = "expiryTime") String expiryTimeStr, HttpServletResponse response) {
        DateTime issuedTime = null;
        if (StrUtil.isNotEmpty(issuedTimeStr)) {
            issuedTime = DateUtil.parse(issuedTimeStr, "yyyy-MM-dd HH:mm:ss");
        }
        DateTime expiryTime = DateUtil.parse(expiryTimeStr, "yyyy-MM-dd HH:mm:ss");
        boolean isSuccess = licenseService.generateLicense(dev, issuedTime, expiryTime, response);
        if (isSuccess) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("生成许可证失败！");
    }
}
