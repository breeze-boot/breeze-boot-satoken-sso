package com.breeze.boot.system.controller.login;

import com.anji.captcha.model.common.ResponseModel;
import com.anji.captcha.model.vo.CaptchaVO;
import com.anji.captcha.service.CaptchaService;
import com.anji.captcha.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * 微风captcha控制器
 * <p>
 * 模仿 {@link com.anji.captcha.controller.CaptchaController } 重写请求路径
 *
 * @author gaoweixuan
 * @date 2022/09/18
 */
@RestController
@RequestMapping({"/captcha"})
public class BreezeCaptchaController {

    /**
     * 验证码服务
     */
    @Autowired
    private CaptchaService captchaService;

    public BreezeCaptchaController() {
    }

    /**
     * 获取远程id
     *
     * @param request 请求
     * @return {@link String}
     */
    public static String getRemoteId(HttpServletRequest request) {
        String xfwd = request.getHeader("X-Forwarded-For");
        String ip = getRemoteIpFromXfwd(xfwd);
        String ua = request.getHeader("user-agent");
        return StringUtils.isNotBlank(ip) ? ip + ua : request.getRemoteAddr() + ua;
    }

    /**
     * 从xfwd获得远程ip
     *
     * @param xfwd xfwd
     * @return {@link String}
     */
    private static String getRemoteIpFromXfwd(String xfwd) {
        if (StringUtils.isNotBlank(xfwd)) {
            String[] ipList = xfwd.split(",");
            return StringUtils.trim(ipList[0]);
        } else {
            return null;
        }
    }

    /**
     * 获取代码
     *
     * @param data    数据
     * @param request 请求
     * @return {@link ResponseModel}
     */
    @PostMapping({"/getCode"})
    public ResponseModel getCode(@RequestBody CaptchaVO data, HttpServletRequest request) {
        assert request.getRemoteHost() != null;
        data.setCaptchaType("blockPuzzle");
        data.setBrowserInfo(getRemoteId(request));
        return this.captchaService.get(data);
    }

    @PostMapping({"/checkCode"})
    public ResponseModel check(@RequestBody CaptchaVO data, HttpServletRequest request) {
        data.setBrowserInfo(getRemoteId(request));
        data.setCaptchaType("blockPuzzle");
        return this.captchaService.check(data);
    }
}
