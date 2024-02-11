package com.dwarfeng.familyhelper.webapi.node.controller.v1.system;

import com.dwarfeng.acckeeper.sdk.bean.entity.JSFixedFastJsonLoginParamRecord;
import com.dwarfeng.acckeeper.stack.bean.entity.LoginParamRecord;
import com.dwarfeng.acckeeper.stack.bean.key.RecordKey;
import com.dwarfeng.familyhelper.webapi.stack.service.system.LoginParamRecordResponseService;
import com.dwarfeng.subgrade.sdk.bean.dto.FastJsonResponseData;
import com.dwarfeng.subgrade.sdk.bean.dto.JSFixedFastJsonPagedData;
import com.dwarfeng.subgrade.sdk.bean.dto.PagingUtil;
import com.dwarfeng.subgrade.sdk.bean.dto.ResponseDataUtil;
import com.dwarfeng.subgrade.sdk.interceptor.analyse.BehaviorAnalyse;
import com.dwarfeng.subgrade.sdk.interceptor.login.LoginRequired;
import com.dwarfeng.subgrade.stack.bean.BeanTransformer;
import com.dwarfeng.subgrade.stack.bean.dto.PagedData;
import com.dwarfeng.subgrade.stack.bean.dto.PagingInfo;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.exception.ServiceExceptionMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * 登录参数记录控制器。
 *
 * @author DwArFeng
 * @since 1.2.1
 */
@RestController
@RequestMapping("/api/v1/system")
public class LoginParamRecordController {

    private static final Logger LOGGER = LoggerFactory.getLogger(LoginParamRecordController.class);

    private final LoginParamRecordResponseService loginParamRecordResponseService;

    private final ServiceExceptionMapper sem;

    private final BeanTransformer<LoginParamRecord, JSFixedFastJsonLoginParamRecord> beanTransformer;

    public LoginParamRecordController(
            LoginParamRecordResponseService loginParamRecordResponseService,
            ServiceExceptionMapper sem,
            BeanTransformer<LoginParamRecord, JSFixedFastJsonLoginParamRecord> beanTransformer
    ) {
        this.loginParamRecordResponseService = loginParamRecordResponseService;
        this.sem = sem;
        this.beanTransformer = beanTransformer;
    }

    @GetMapping("/login-param-record/{loginHistoryId}&{recordId}/exists")
    @BehaviorAnalyse
    @LoginRequired
    public FastJsonResponseData<Boolean> exists(
            HttpServletRequest request,
            @PathVariable("loginHistoryId") Long loginHistoryId, @PathVariable("recordId") String recordId
    ) {
        try {
            boolean exists = loginParamRecordResponseService.exists(new RecordKey(loginHistoryId, recordId));
            return FastJsonResponseData.of(ResponseDataUtil.good(exists));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @GetMapping("/login-param-record/{loginHistoryId}&{recordId}")
    @BehaviorAnalyse
    @LoginRequired
    public FastJsonResponseData<JSFixedFastJsonLoginParamRecord> get(
            HttpServletRequest request,
            @PathVariable("loginHistoryId") Long loginHistoryId, @PathVariable("recordId") String recordId
    ) {
        try {
            LoginParamRecord loginParamRecord = loginParamRecordResponseService.get(
                    new RecordKey(loginHistoryId, recordId)
            );
            return FastJsonResponseData.of(
                    ResponseDataUtil.good(JSFixedFastJsonLoginParamRecord.of(loginParamRecord))
            );
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @GetMapping("/login-param-record/all")
    @BehaviorAnalyse
    @LoginRequired
    public FastJsonResponseData<JSFixedFastJsonPagedData<JSFixedFastJsonLoginParamRecord>> all(
            HttpServletRequest request,
            @RequestParam("page") int page, @RequestParam("rows") int rows
    ) {
        try {
            PagedData<LoginParamRecord> lookup = loginParamRecordResponseService.all(new PagingInfo(page, rows));
            PagedData<JSFixedFastJsonLoginParamRecord> transform = PagingUtil.transform(
                    lookup, beanTransformer
            );
            return FastJsonResponseData.of(ResponseDataUtil.good(JSFixedFastJsonPagedData.of(transform)));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @GetMapping(value = {
            "/login-history/{loginHistoryId}/login-param-record", "/login-history//login-param-record"
    })
    @BehaviorAnalyse
    @LoginRequired
    public FastJsonResponseData<JSFixedFastJsonPagedData<JSFixedFastJsonLoginParamRecord>> childForLoginHistory(
            HttpServletRequest request,
            @PathVariable(required = false, value = "loginHistoryId") Long loginHistoryId,
            @RequestParam("page") int page, @RequestParam("rows") int rows
    ) {
        try {
            LongIdKey loginHistoryKey = null;
            if (loginHistoryId != null) {
                loginHistoryKey = new LongIdKey(loginHistoryId);
            }
            PagedData<LoginParamRecord> lookup = loginParamRecordResponseService.childForLoginHistory(
                    loginHistoryKey, new PagingInfo(page, rows)
            );
            PagedData<JSFixedFastJsonLoginParamRecord> transform = PagingUtil.transform(
                    lookup, beanTransformer
            );
            return FastJsonResponseData.of(ResponseDataUtil.good(JSFixedFastJsonPagedData.of(transform)));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }
}
