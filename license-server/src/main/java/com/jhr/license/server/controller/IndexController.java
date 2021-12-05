package com.jhr.license.server.controller;

import com.jhr.license.common.util.LicenseUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * 跳转页面
 *
 * @author xukun
 * @since 1.0.0.RELEASE
 */
@Controller
public class IndexController {

    @GetMapping("/")
    public String index(Model model) {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        model.addAttribute("now", now.format(dtf));
        model.addAttribute("serverInfo", LicenseUtils.getLicenseContentExtra());
        return "index";
    }
}
