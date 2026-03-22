package com.dwarfeng.familyhelper.webapi.node.controller.v1.acckeeper;

import com.dwarfeng.acckeeper.sdk.bean.entity.JSFixedFastJsonProtectDetailRecord;
import com.dwarfeng.acckeeper.stack.bean.entity.ProtectDetailRecord;
import com.dwarfeng.acckeeper.stack.bean.key.RecordKey;
import com.dwarfeng.familyhelper.webapi.sdk.bean.acckeeper.disp.JSFixedFastJsonDispProtectDetailRecord;
import com.dwarfeng.familyhelper.webapi.stack.bean.acckeeper.disp.DispProtectDetailRecord;
import com.dwarfeng.familyhelper.webapi.stack.service.acckeeper.ProtectDetailRecordResponseService;
import com.dwarfeng.subgrade.sdk.bean.dto.FastJsonResponseData;
import com.dwarfeng.subgrade.sdk.bean.dto.JSFixedFastJsonPagedData;
import com.dwarfeng.subgrade.sdk.bean.dto.PagingUtil;
import com.dwarfeng.subgrade.sdk.bean.dto.ResponseDataUtil;
import com.dwarfeng.subgrade.sdk.interceptor.analyse.BehaviorAnalyse;
import com.dwarfeng.subgrade.sdk.interceptor.analyse.SkipRecord;
import com.dwarfeng.subgrade.sdk.interceptor.login.LoginRequired;
import com.dwarfeng.subgrade.sdk.interceptor.permission.PermissionRequired;
import com.dwarfeng.subgrade.stack.bean.BeanTransformer;
import com.dwarfeng.subgrade.stack.bean.dto.PagedData;
import com.dwarfeng.subgrade.stack.bean.dto.PagingInfo;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.exception.ServiceExceptionMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * 保护详细信息记录控制器。
 *
 * @author DwArFeng
 * @since 2.0.0
 */
@RestController
@RequestMapping("/api/v1/acckeeper")
public class ProtectDetailRecordController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProtectDetailRecordController.class);

    private final ProtectDetailRecordResponseService service;

    private final ServiceExceptionMapper sem;

    private final BeanTransformer<ProtectDetailRecord, JSFixedFastJsonProtectDetailRecord> beanTransformer;
    private final BeanTransformer<DispProtectDetailRecord, JSFixedFastJsonDispProtectDetailRecord> dispBeanTransformer;

    public ProtectDetailRecordController(
            ProtectDetailRecordResponseService service,
            ServiceExceptionMapper sem,
            @Qualifier("acckeeper.protectDetailRecordBeanTransformer")
            BeanTransformer<ProtectDetailRecord, JSFixedFastJsonProtectDetailRecord> beanTransformer,
            @Qualifier("acckeeper.dispProtectDetailRecordBeanTransformer")
            BeanTransformer<DispProtectDetailRecord, JSFixedFastJsonDispProtectDetailRecord> dispBeanTransformer
    ) {
        this.service = service;
        this.sem = sem;
        this.beanTransformer = beanTransformer;
        this.dispBeanTransformer = dispBeanTransformer;
    }

    @GetMapping("/protect-detail-record/{loginHistoryId}&{recordId}/exists")
    @BehaviorAnalyse
    @LoginRequired
    @PermissionRequired("webapi.controller_permitted.acckeeper.protect_detail_record.exists")
    public FastJsonResponseData<Boolean> exists(
            HttpServletRequest request,
            @PathVariable("loginHistoryId") Long loginHistoryId,
            @PathVariable("recordId") String recordId
    ) {
        try {
            boolean exists = service.exists(new RecordKey(loginHistoryId, recordId));
            return FastJsonResponseData.of(ResponseDataUtil.good(exists));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @GetMapping("/protect-detail-record/{loginHistoryId}&{recordId}")
    @BehaviorAnalyse
    @LoginRequired
    @PermissionRequired("webapi.controller_permitted.acckeeper.protect_detail_record.get")
    public FastJsonResponseData<JSFixedFastJsonProtectDetailRecord> get(
            HttpServletRequest request,
            @PathVariable("loginHistoryId") Long loginHistoryId,
            @PathVariable("recordId") String recordId
    ) {
        try {
            ProtectDetailRecord record = service.get(new RecordKey(loginHistoryId, recordId));
            return FastJsonResponseData.of(ResponseDataUtil.good(JSFixedFastJsonProtectDetailRecord.of(record)));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @GetMapping("/protect-detail-record/all")
    @BehaviorAnalyse
    @SkipRecord
    @LoginRequired
    @PermissionRequired("webapi.controller_permitted.acckeeper.protect_detail_record.all")
    public FastJsonResponseData<JSFixedFastJsonPagedData<JSFixedFastJsonProtectDetailRecord>> all(
            HttpServletRequest request, @RequestParam("page") int page, @RequestParam("rows") int rows
    ) {
        try {
            PagedData<ProtectDetailRecord> all = service.all(new PagingInfo(page, rows));
            PagedData<JSFixedFastJsonProtectDetailRecord> transform = PagingUtil.transform(all, beanTransformer);
            return FastJsonResponseData.of(ResponseDataUtil.good(JSFixedFastJsonPagedData.of(transform)));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @GetMapping("/login-history/{loginHistoryId}/protect-detail-record")
    @BehaviorAnalyse
    @SkipRecord
    @LoginRequired
    @PermissionRequired("webapi.controller_permitted.acckeeper.protect_detail_record.child_for_login_history")
    public FastJsonResponseData<JSFixedFastJsonPagedData<JSFixedFastJsonProtectDetailRecord>> childForLoginHistory(
            HttpServletRequest request,
            @PathVariable("loginHistoryId") Long loginHistoryId,
            @RequestParam("page") int page, @RequestParam("rows") int rows
    ) {
        try {
            PagedData<ProtectDetailRecord> child = service.childForLoginHistory(
                    new LongIdKey(loginHistoryId), new PagingInfo(page, rows)
            );
            PagedData<JSFixedFastJsonProtectDetailRecord> transform = PagingUtil.transform(child, beanTransformer);
            return FastJsonResponseData.of(ResponseDataUtil.good(JSFixedFastJsonPagedData.of(transform)));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @GetMapping("/protect-detail-record/{loginHistoryId}&{recordId}/disp")
    @BehaviorAnalyse
    @LoginRequired
    @PermissionRequired("webapi.controller_permitted.acckeeper.protect_detail_record.get_disp")
    public FastJsonResponseData<JSFixedFastJsonDispProtectDetailRecord> getDisp(
            HttpServletRequest request,
            @PathVariable("loginHistoryId") Long loginHistoryId,
            @PathVariable("recordId") String recordId
    ) {
        try {
            DispProtectDetailRecord disp = service.getDisp(new RecordKey(loginHistoryId, recordId));
            return FastJsonResponseData.of(ResponseDataUtil.good(JSFixedFastJsonDispProtectDetailRecord.of(disp)));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @GetMapping("/protect-detail-record/all/disp")
    @BehaviorAnalyse
    @SkipRecord
    @LoginRequired
    @PermissionRequired("webapi.controller_permitted.acckeeper.protect_detail_record.all_disp")
    public FastJsonResponseData<JSFixedFastJsonPagedData<JSFixedFastJsonDispProtectDetailRecord>> allDisp(
            HttpServletRequest request, @RequestParam("page") int page, @RequestParam("rows") int rows
    ) {
        try {
            PagedData<DispProtectDetailRecord> all = service.allDisp(new PagingInfo(page, rows));
            PagedData<JSFixedFastJsonDispProtectDetailRecord> transform =
                    PagingUtil.transform(all, dispBeanTransformer);
            return FastJsonResponseData.of(ResponseDataUtil.good(JSFixedFastJsonPagedData.of(transform)));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @GetMapping("/login-history/{loginHistoryId}/protect-detail-record/disp")
    @BehaviorAnalyse
    @SkipRecord
    @LoginRequired
    @PermissionRequired("webapi.controller_permitted.acckeeper.protect_detail_record.child_for_login_history_disp")
    public FastJsonResponseData<JSFixedFastJsonPagedData<JSFixedFastJsonDispProtectDetailRecord>>
    childForLoginHistoryDisp(
            HttpServletRequest request,
            @PathVariable("loginHistoryId") Long loginHistoryId,
            @RequestParam("page") int page, @RequestParam("rows") int rows
    ) {
        try {
            PagedData<DispProtectDetailRecord> child = service.childForLoginHistoryDisp(
                    new LongIdKey(loginHistoryId), new PagingInfo(page, rows)
            );
            PagedData<JSFixedFastJsonDispProtectDetailRecord> transform =
                    PagingUtil.transform(child, dispBeanTransformer);
            return FastJsonResponseData.of(ResponseDataUtil.good(JSFixedFastJsonPagedData.of(transform)));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }
}
