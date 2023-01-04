package com.dwarfeng.familyhelper.webapi.node.controller.v1.settingrepo;

import com.dwarfeng.familyhelper.webapi.stack.service.settingrepo.ResetResponseService;
import com.dwarfeng.subgrade.sdk.bean.dto.FastJsonResponseData;
import com.dwarfeng.subgrade.sdk.bean.dto.ResponseDataUtil;
import com.dwarfeng.subgrade.sdk.interceptor.analyse.BehaviorAnalyse;
import com.dwarfeng.subgrade.sdk.interceptor.login.LoginRequired;
import com.dwarfeng.subgrade.stack.exception.ServiceExceptionMapper;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * 重置控制器。
 *
 * @author DwArFeng
 * @since 1.0.8
 */
@RestController("settingrepoResetController")
@RequestMapping("/api/v1/settingrepo")
public class ResetController {

    private final ResetResponseService service;

    private final ServiceExceptionMapper sem;

    public ResetController(ResetResponseService service, ServiceExceptionMapper sem) {
        this.service = service;
        this.sem = sem;
    }

    @PostMapping("/reset-format")
    @BehaviorAnalyse
    @LoginRequired
    public FastJsonResponseData<Object> resetFormat(HttpServletRequest request) {
        try {
            service.resetFormat();
            return FastJsonResponseData.of(ResponseDataUtil.good(null));
        } catch (Exception e) {
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }
}
