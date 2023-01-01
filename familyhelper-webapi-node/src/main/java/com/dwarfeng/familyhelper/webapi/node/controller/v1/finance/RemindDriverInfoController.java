package com.dwarfeng.familyhelper.webapi.node.controller.v1.finance;

import com.dwarfeng.familyhelper.finance.sdk.bean.entity.JSFixedFastJsonRemindDriverInfo;
import com.dwarfeng.familyhelper.finance.sdk.bean.entity.WebInputRemindDriverInfo;
import com.dwarfeng.familyhelper.finance.stack.bean.entity.RemindDriverInfo;
import com.dwarfeng.familyhelper.webapi.stack.service.finance.RemindDriverInfoResponseService;
import com.dwarfeng.subgrade.sdk.bean.dto.FastJsonResponseData;
import com.dwarfeng.subgrade.sdk.bean.dto.JSFixedFastJsonPagedData;
import com.dwarfeng.subgrade.sdk.bean.dto.PagingUtil;
import com.dwarfeng.subgrade.sdk.bean.dto.ResponseDataUtil;
import com.dwarfeng.subgrade.sdk.bean.key.FastJsonLongIdKey;
import com.dwarfeng.subgrade.sdk.interceptor.analyse.BehaviorAnalyse;
import com.dwarfeng.subgrade.sdk.interceptor.analyse.SkipRecord;
import com.dwarfeng.subgrade.sdk.interceptor.http.BindingCheck;
import com.dwarfeng.subgrade.sdk.interceptor.login.LoginRequired;
import com.dwarfeng.subgrade.sdk.validation.group.Insert;
import com.dwarfeng.subgrade.stack.bean.BeanTransformer;
import com.dwarfeng.subgrade.stack.bean.dto.PagedData;
import com.dwarfeng.subgrade.stack.bean.dto.PagingInfo;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.exception.ServiceExceptionMapper;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * 提醒驱动器信息控制器。
 *
 * @author DwArFeng
 * @since 1.0.7
 */
@RestController("notifyRemindDriverInfoController")
@RequestMapping("/api/v1/finance")
public class RemindDriverInfoController {

    private final RemindDriverInfoResponseService service;
    private final ServiceExceptionMapper sem;

    private final BeanTransformer<RemindDriverInfo, JSFixedFastJsonRemindDriverInfo> beanTransformer;

    public RemindDriverInfoController(
            RemindDriverInfoResponseService service, ServiceExceptionMapper sem,
            BeanTransformer<RemindDriverInfo, JSFixedFastJsonRemindDriverInfo> beanTransformer
    ) {
        this.service = service;
        this.sem = sem;
        this.beanTransformer = beanTransformer;
    }

    @GetMapping("/remind-driver-info/{id}/exists")
    @BehaviorAnalyse
    @LoginRequired
    public FastJsonResponseData<Boolean> exists(HttpServletRequest request, @PathVariable("id") long id) {
        try {
            boolean exists = service.exists(new LongIdKey(id));
            return FastJsonResponseData.of(ResponseDataUtil.good(exists));
        } catch (Exception e) {
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @GetMapping("/remind-driver-info/{id}")
    @BehaviorAnalyse
    @LoginRequired
    public FastJsonResponseData<JSFixedFastJsonRemindDriverInfo> get(
            HttpServletRequest request, @PathVariable("id") long id
    ) {
        try {
            RemindDriverInfo remindDriverInfo = service.get(new LongIdKey(id));
            return FastJsonResponseData.of(ResponseDataUtil.good(
                    JSFixedFastJsonRemindDriverInfo.of(remindDriverInfo)
            ));
        } catch (Exception e) {
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @PostMapping("/remind-driver-info")
    @BehaviorAnalyse
    @BindingCheck
    @LoginRequired
    public FastJsonResponseData<FastJsonLongIdKey> insert(
            HttpServletRequest request,
            @RequestBody @Validated(Insert.class) WebInputRemindDriverInfo webInputRemindDriverInfo,
            BindingResult bindingResult
    ) {
        try {
            RemindDriverInfo remindDriverInfo = WebInputRemindDriverInfo.toStackBean(webInputRemindDriverInfo);
            LongIdKey insert = service.insert(remindDriverInfo);
            return FastJsonResponseData.of(ResponseDataUtil.good(FastJsonLongIdKey.of(insert)));
        } catch (Exception e) {
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @PatchMapping("/remind-driver-info")
    @BehaviorAnalyse
    @BindingCheck
    @LoginRequired
    public FastJsonResponseData<Object> update(
            HttpServletRequest request,
            @RequestBody @Validated WebInputRemindDriverInfo webInputRemindDriverInfo,
            BindingResult bindingResult
    ) {
        try {
            service.update(WebInputRemindDriverInfo.toStackBean(webInputRemindDriverInfo));
            return FastJsonResponseData.of(ResponseDataUtil.good(null));
        } catch (Exception e) {
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @DeleteMapping("/remind-driver-info/{id}")
    @BehaviorAnalyse
    @LoginRequired
    public FastJsonResponseData<Object> delete(HttpServletRequest request, @PathVariable("id") long id) {
        try {
            service.delete(new LongIdKey(id));
            return FastJsonResponseData.of(ResponseDataUtil.good(null));
        } catch (Exception e) {
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @GetMapping("/account-book/{accountBookId}/remind-driver-info")
    @BehaviorAnalyse
    @SkipRecord
    @LoginRequired
    public FastJsonResponseData<JSFixedFastJsonPagedData<JSFixedFastJsonRemindDriverInfo>> childForAccountBook(
            HttpServletRequest request, @PathVariable("accountBookId") Long accountBookId,
            @RequestParam("page") int page, @RequestParam("rows") int rows
    ) {
        try {
            PagedData<RemindDriverInfo> all = service.childForAccountBook(
                    new LongIdKey(accountBookId), new PagingInfo(page, rows)
            );
            PagedData<JSFixedFastJsonRemindDriverInfo> transform = PagingUtil.transform(all, beanTransformer);
            return FastJsonResponseData.of(ResponseDataUtil.good(JSFixedFastJsonPagedData.of(transform)));
        } catch (Exception e) {
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }
}
