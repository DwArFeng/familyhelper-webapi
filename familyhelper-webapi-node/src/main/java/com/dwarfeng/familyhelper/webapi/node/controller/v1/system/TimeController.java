package com.dwarfeng.familyhelper.webapi.node.controller.v1.system;

import com.dwarfeng.familyhelper.webapi.stack.service.system.TimeResponseService;
import com.dwarfeng.subgrade.sdk.bean.dto.FastJsonResponseData;
import com.dwarfeng.subgrade.sdk.bean.dto.ResponseDataUtil;
import com.dwarfeng.subgrade.sdk.interceptor.analyse.BehaviorAnalyse;
import com.dwarfeng.subgrade.stack.exception.ServiceExceptionMapper;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

/**
 * 时间控制器。
 *
 * @author DwArFeng
 * @since 1.0.1
 */
@RestController
@RequestMapping("/api/v1/system")
public class TimeController {

    private final TimeResponseService timeResponseService;

    private final ServiceExceptionMapper sem;

    public TimeController(TimeResponseService timeResponseService, ServiceExceptionMapper sem) {
        this.timeResponseService = timeResponseService;
        this.sem = sem;
    }

    @PostMapping("/current-date")
    @BehaviorAnalyse
    public FastJsonResponseData<Date> currentDate(HttpServletRequest request) {
        try {
            Date result = timeResponseService.currentDate();
            return FastJsonResponseData.of(ResponseDataUtil.good(result));
        } catch (Exception e) {
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }
}
