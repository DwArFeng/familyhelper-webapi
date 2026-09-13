package com.dwarfeng.familyhelper.webapi.node.controller.v1.fileio;

import com.dwarfeng.familyhelper.webapi.stack.service.fileio.ResetResponseService;
import com.dwarfeng.subgrade.sdk.bean.dto.FastJsonResponseData;
import com.dwarfeng.subgrade.sdk.bean.dto.ResponseDataUtil;
import com.dwarfeng.subgrade.sdk.interceptor.analyse.BehaviorAnalyse;
import com.dwarfeng.subgrade.sdk.interceptor.login.LoginRequired;
import com.dwarfeng.subgrade.sdk.interceptor.permission.PermissionRequired;
import com.dwarfeng.subgrade.stack.exception.ServiceExceptionMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * 重置控制器。
 *
 * @author diaocl
 * @since 2.1.0
 */
@RestController("fileioResetController")
@RequestMapping("/api/v1/fileio")
public class ResetController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ResetController.class);

    private final ResetResponseService service;

    private final ServiceExceptionMapper sem;

    public ResetController(ResetResponseService service, ServiceExceptionMapper sem) {
        this.service = service;
        this.sem = sem;
    }

    @PostMapping("/reset-export")
    @BehaviorAnalyse
    @LoginRequired
    @PermissionRequired("webapi.controller_permitted.fileio.reset.reset_export")
    public FastJsonResponseData<Object> resetExport(HttpServletRequest request) {
        try {
            service.resetExport();
            return FastJsonResponseData.of(ResponseDataUtil.good(null));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常，信息如下：", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @PostMapping("/reset-import")
    @BehaviorAnalyse
    @LoginRequired
    @PermissionRequired("webapi.controller_permitted.fileio.reset.reset_import")
    public FastJsonResponseData<Object> resetImport(HttpServletRequest request) {
        try {
            service.resetImport();
            return FastJsonResponseData.of(ResponseDataUtil.good(null));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常，信息如下：", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }
}
