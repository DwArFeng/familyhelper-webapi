package com.dwarfeng.familyhelper.webapi.node.controller.v1.acckeeper;

import com.dwarfeng.acckeeper.sdk.bean.entity.JSFixedFastJsonLoginParamRecord;
import com.dwarfeng.acckeeper.stack.bean.entity.LoginParamRecord;
import com.dwarfeng.acckeeper.stack.bean.key.RecordKey;
import com.dwarfeng.familyhelper.webapi.sdk.bean.acckeeper.disp.JSFixedFastJsonDispLoginParamRecord;
import com.dwarfeng.familyhelper.webapi.stack.bean.acckeeper.disp.DispLoginParamRecord;
import com.dwarfeng.familyhelper.webapi.stack.service.acckeeper.LoginParamRecordResponseService;
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
 * 登录参数记录控制器。
 *
 * @author DwArFeng
 * @since 2.0.0
 */
@RestController
@RequestMapping("/api/v1/acckeeper")
public class LoginParamRecordController {

    private static final Logger LOGGER = LoggerFactory.getLogger(LoginParamRecordController.class);

    private final LoginParamRecordResponseService service;

    private final ServiceExceptionMapper sem;

    private final BeanTransformer<LoginParamRecord, JSFixedFastJsonLoginParamRecord> beanTransformer;
    private final BeanTransformer<DispLoginParamRecord, JSFixedFastJsonDispLoginParamRecord> dispBeanTransformer;

    public LoginParamRecordController(
            LoginParamRecordResponseService service,
            ServiceExceptionMapper sem,
            @Qualifier("acckeeper.loginParamRecordBeanTransformer")
            BeanTransformer<LoginParamRecord, JSFixedFastJsonLoginParamRecord> beanTransformer,
            @Qualifier("acckeeper.dispLoginParamRecordBeanTransformer")
            BeanTransformer<DispLoginParamRecord, JSFixedFastJsonDispLoginParamRecord> dispBeanTransformer
    ) {
        this.service = service;
        this.sem = sem;
        this.beanTransformer = beanTransformer;
        this.dispBeanTransformer = dispBeanTransformer;
    }

    @GetMapping("/login-param-record/{loginHistoryId}&{recordId}/exists")
    @BehaviorAnalyse
    @LoginRequired
    @PermissionRequired("webapi.controller_permitted.acckeeper.login_param_record.exists")
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

    @GetMapping("/login-param-record/{loginHistoryId}&{recordId}")
    @BehaviorAnalyse
    @LoginRequired
    @PermissionRequired("webapi.controller_permitted.acckeeper.login_param_record.get")
    public FastJsonResponseData<JSFixedFastJsonLoginParamRecord> get(
            HttpServletRequest request,
            @PathVariable("loginHistoryId") Long loginHistoryId,
            @PathVariable("recordId") String recordId
    ) {
        try {
            LoginParamRecord record = service.get(new RecordKey(loginHistoryId, recordId));
            return FastJsonResponseData.of(ResponseDataUtil.good(JSFixedFastJsonLoginParamRecord.of(record)));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @GetMapping("/login-param-record/all")
    @BehaviorAnalyse
    @SkipRecord
    @LoginRequired
    @PermissionRequired("webapi.controller_permitted.acckeeper.login_param_record.all")
    public FastJsonResponseData<JSFixedFastJsonPagedData<JSFixedFastJsonLoginParamRecord>> all(
            HttpServletRequest request, @RequestParam("page") int page, @RequestParam("rows") int rows
    ) {
        try {
            PagedData<LoginParamRecord> all = service.all(new PagingInfo(page, rows));
            PagedData<JSFixedFastJsonLoginParamRecord> transform = PagingUtil.transform(all, beanTransformer);
            return FastJsonResponseData.of(ResponseDataUtil.good(JSFixedFastJsonPagedData.of(transform)));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @GetMapping("/login-history/{loginHistoryId}/login-param-record")
    @BehaviorAnalyse
    @SkipRecord
    @LoginRequired
    @PermissionRequired("webapi.controller_permitted.acckeeper.login_param_record.child_for_login_history")
    public FastJsonResponseData<JSFixedFastJsonPagedData<JSFixedFastJsonLoginParamRecord>> childForLoginHistory(
            HttpServletRequest request,
            @PathVariable("loginHistoryId") Long loginHistoryId,
            @RequestParam("page") int page, @RequestParam("rows") int rows
    ) {
        try {
            PagedData<LoginParamRecord> child = service.childForLoginHistory(
                    new LongIdKey(loginHistoryId), new PagingInfo(page, rows)
            );
            PagedData<JSFixedFastJsonLoginParamRecord> transform = PagingUtil.transform(child, beanTransformer);
            return FastJsonResponseData.of(ResponseDataUtil.good(JSFixedFastJsonPagedData.of(transform)));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @GetMapping("/login-param-record/{loginHistoryId}&{recordId}/disp")
    @BehaviorAnalyse
    @LoginRequired
    @PermissionRequired("webapi.controller_permitted.acckeeper.login_param_record.get_disp")
    public FastJsonResponseData<JSFixedFastJsonDispLoginParamRecord> getDisp(
            HttpServletRequest request,
            @PathVariable("loginHistoryId") Long loginHistoryId,
            @PathVariable("recordId") String recordId
    ) {
        try {
            DispLoginParamRecord disp = service.getDisp(new RecordKey(loginHistoryId, recordId));
            return FastJsonResponseData.of(ResponseDataUtil.good(JSFixedFastJsonDispLoginParamRecord.of(disp)));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @GetMapping("/login-param-record/all/disp")
    @BehaviorAnalyse
    @SkipRecord
    @LoginRequired
    @PermissionRequired("webapi.controller_permitted.acckeeper.login_param_record.all_disp")
    public FastJsonResponseData<JSFixedFastJsonPagedData<JSFixedFastJsonDispLoginParamRecord>> allDisp(
            HttpServletRequest request, @RequestParam("page") int page, @RequestParam("rows") int rows
    ) {
        try {
            PagedData<DispLoginParamRecord> all = service.allDisp(new PagingInfo(page, rows));
            PagedData<JSFixedFastJsonDispLoginParamRecord> transform = PagingUtil.transform(all, dispBeanTransformer);
            return FastJsonResponseData.of(ResponseDataUtil.good(JSFixedFastJsonPagedData.of(transform)));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @GetMapping("/login-history/{loginHistoryId}/login-param-record/disp")
    @BehaviorAnalyse
    @SkipRecord
    @LoginRequired
    @PermissionRequired("webapi.controller_permitted.acckeeper.login_param_record.child_for_login_history_disp")
    public FastJsonResponseData<JSFixedFastJsonPagedData<JSFixedFastJsonDispLoginParamRecord>> childForLoginHistoryDisp(
            HttpServletRequest request,
            @PathVariable("loginHistoryId") Long loginHistoryId,
            @RequestParam("page") int page, @RequestParam("rows") int rows
    ) {
        try {
            PagedData<DispLoginParamRecord> child = service.childForLoginHistoryDisp(
                    new LongIdKey(loginHistoryId), new PagingInfo(page, rows)
            );
            PagedData<JSFixedFastJsonDispLoginParamRecord> transform = PagingUtil.transform(child, dispBeanTransformer);
            return FastJsonResponseData.of(ResponseDataUtil.good(JSFixedFastJsonPagedData.of(transform)));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }
}
