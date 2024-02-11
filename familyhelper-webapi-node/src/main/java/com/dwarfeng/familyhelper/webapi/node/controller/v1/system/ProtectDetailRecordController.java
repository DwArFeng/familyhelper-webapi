package com.dwarfeng.familyhelper.webapi.node.controller.v1.system;

import com.dwarfeng.acckeeper.sdk.bean.entity.JSFixedFastJsonProtectDetailRecord;
import com.dwarfeng.acckeeper.stack.bean.entity.ProtectDetailRecord;
import com.dwarfeng.acckeeper.stack.bean.key.RecordKey;
import com.dwarfeng.familyhelper.webapi.stack.service.system.ProtectDetailRecordResponseService;
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
 * 保护详细信息记录控制器。
 *
 * @author DwArFeng
 * @since 1.2.1
 */
@RestController
@RequestMapping("/api/v1/system")
public class ProtectDetailRecordController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProtectDetailRecordController.class);

    private final ProtectDetailRecordResponseService protectDetailRecordResponseService;

    private final ServiceExceptionMapper sem;

    private final BeanTransformer<ProtectDetailRecord, JSFixedFastJsonProtectDetailRecord> beanTransformer;

    public ProtectDetailRecordController(
            ProtectDetailRecordResponseService protectDetailRecordResponseService,
            ServiceExceptionMapper sem,
            BeanTransformer<ProtectDetailRecord, JSFixedFastJsonProtectDetailRecord> beanTransformer
    ) {
        this.protectDetailRecordResponseService = protectDetailRecordResponseService;
        this.sem = sem;
        this.beanTransformer = beanTransformer;
    }

    @GetMapping("/protect-detail-record/{loginHistoryId}&{recordId}/exists")
    @BehaviorAnalyse
    @LoginRequired
    public FastJsonResponseData<Boolean> exists(
            HttpServletRequest request,
            @PathVariable("loginHistoryId") Long loginHistoryId, @PathVariable("recordId") String recordId
    ) {
        try {
            boolean exists = protectDetailRecordResponseService.exists(new RecordKey(loginHistoryId, recordId));
            return FastJsonResponseData.of(ResponseDataUtil.good(exists));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @GetMapping("/protect-detail-record/{loginHistoryId}&{recordId}")
    @BehaviorAnalyse
    @LoginRequired
    public FastJsonResponseData<JSFixedFastJsonProtectDetailRecord> get(
            HttpServletRequest request,
            @PathVariable("loginHistoryId") Long loginHistoryId, @PathVariable("recordId") String recordId
    ) {
        try {
            ProtectDetailRecord protectDetailRecord = protectDetailRecordResponseService.get(
                    new RecordKey(loginHistoryId, recordId)
            );
            return FastJsonResponseData.of(
                    ResponseDataUtil.good(JSFixedFastJsonProtectDetailRecord.of(protectDetailRecord))
            );
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @GetMapping("/protect-detail-record/all")
    @BehaviorAnalyse
    @LoginRequired
    public FastJsonResponseData<JSFixedFastJsonPagedData<JSFixedFastJsonProtectDetailRecord>> all(
            HttpServletRequest request,
            @RequestParam("page") int page, @RequestParam("rows") int rows
    ) {
        try {
            PagedData<ProtectDetailRecord> lookup = protectDetailRecordResponseService.all(new PagingInfo(page, rows));
            PagedData<JSFixedFastJsonProtectDetailRecord> transform = PagingUtil.transform(
                    lookup, beanTransformer
            );
            return FastJsonResponseData.of(ResponseDataUtil.good(JSFixedFastJsonPagedData.of(transform)));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @GetMapping(value = {
            "/login-history/{loginHistoryId}/protect-detail-record", "/login-history//protect-detail-record"
    })
    @BehaviorAnalyse
    @LoginRequired
    public FastJsonResponseData<JSFixedFastJsonPagedData<JSFixedFastJsonProtectDetailRecord>> childForLoginHistory(
            HttpServletRequest request,
            @PathVariable(required = false, value = "loginHistoryId") Long loginHistoryId,
            @RequestParam("page") int page, @RequestParam("rows") int rows
    ) {
        try {
            LongIdKey loginHistoryKey = null;
            if (loginHistoryId != null) {
                loginHistoryKey = new LongIdKey(loginHistoryId);
            }
            PagedData<ProtectDetailRecord> lookup = protectDetailRecordResponseService.childForLoginHistory(
                    loginHistoryKey, new PagingInfo(page, rows)
            );
            PagedData<JSFixedFastJsonProtectDetailRecord> transform = PagingUtil.transform(
                    lookup, beanTransformer
            );
            return FastJsonResponseData.of(ResponseDataUtil.good(JSFixedFastJsonPagedData.of(transform)));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }
}
