package com.jhr.license.client.interceptor;

import cn.hutool.http.HttpStatus;
import com.jhr.license.client.core.LicenseService;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

/**
 * 拦截请求并校验许可证是否合法
 *
 * @author xukun
 * @since 1.0.0.RELEASE
 */
public class LicenseCheckInterceptor extends HandlerInterceptorAdapter {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        LicenseService licenseVerify = new LicenseService();
        // 校验许可证是否合法
        boolean isLegal = licenseVerify.verify();
        if (!isLegal) {
            response.setContentType("text/html;charset=utf-8");
            response.setStatus(HttpStatus.HTTP_FORBIDDEN);
            try (PrintWriter writer = response.getWriter()) {
                writer.write("您的许可证无效，请核查服务器是否取得授权或重新申请许可证！");
            }
            return false;
        }
        return true;
    }
}
