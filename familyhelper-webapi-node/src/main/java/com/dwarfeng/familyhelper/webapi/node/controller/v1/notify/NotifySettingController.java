package com.dwarfeng.familyhelper.webapi.node.controller.v1.notify;

import com.dwarfeng.familyhelper.webapi.stack.service.notify.NotifySettingResponseService;
import com.dwarfeng.notify.sdk.bean.entity.JSFixedFastJsonNotifySetting;
import com.dwarfeng.notify.sdk.bean.entity.WebInputNotifySetting;
import com.dwarfeng.notify.stack.bean.entity.NotifySetting;
import com.dwarfeng.subgrade.sdk.bean.dto.FastJsonResponseData;
import com.dwarfeng.subgrade.sdk.bean.dto.JSFixedFastJsonPagedData;
import com.dwarfeng.subgrade.sdk.bean.dto.PagingUtil;
import com.dwarfeng.subgrade.sdk.bean.dto.ResponseDataUtil;
import com.dwarfeng.subgrade.sdk.bean.key.FastJsonLongIdKey;
import com.dwarfeng.subgrade.sdk.interceptor.analyse.BehaviorAnalyse;
import com.dwarfeng.subgrade.sdk.interceptor.analyse.SkipRecord;
import com.dwarfeng.subgrade.sdk.interceptor.http.BindingCheck;
import com.dwarfeng.subgrade.sdk.interceptor.login.LoginRequired;
import com.dwarfeng.subgrade.stack.bean.BeanTransformer;
import com.dwarfeng.subgrade.stack.bean.dto.PagedData;
import com.dwarfeng.subgrade.stack.bean.dto.PagingInfo;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.exception.ServiceExceptionMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * 通知设置控制器。
 *
 * @author DwArFeng
 * @since 1.0.7
 */
@RestController
@RequestMapping("/api/v1/notify")
public class NotifySettingController {

    private static final Logger LOGGER = LoggerFactory.getLogger(NotifySettingController.class);

    private final NotifySettingResponseService service;
    private final ServiceExceptionMapper sem;

    private final BeanTransformer<NotifySetting, JSFixedFastJsonNotifySetting> beanTransformer;

    public NotifySettingController(
            NotifySettingResponseService service, ServiceExceptionMapper sem,
            BeanTransformer<NotifySetting, JSFixedFastJsonNotifySetting> beanTransformer
    ) {
        this.service = service;
        this.sem = sem;
        this.beanTransformer = beanTransformer;
    }

    @GetMapping("/notify-setting/{id}/exists")
    @BehaviorAnalyse
    @LoginRequired
    public FastJsonResponseData<Boolean> exists(HttpServletRequest request, @PathVariable("id") Long id) {
        try {
            boolean exists = service.exists(new LongIdKey(id));
            return FastJsonResponseData.of(ResponseDataUtil.good(exists));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @GetMapping("/notify-setting/{id}")
    @BehaviorAnalyse
    @LoginRequired
    public FastJsonResponseData<JSFixedFastJsonNotifySetting> get(
            HttpServletRequest request, @PathVariable("id") Long id
    ) {
        try {
            NotifySetting notifySetting = service.get(new LongIdKey(id));
            return FastJsonResponseData.of(ResponseDataUtil.good(
                    JSFixedFastJsonNotifySetting.of(notifySetting)
            ));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @PostMapping("/notify-setting")
    @BehaviorAnalyse
    @BindingCheck
    @LoginRequired
    public FastJsonResponseData<FastJsonLongIdKey> insert(
            HttpServletRequest request,
            @RequestBody @Validated WebInputNotifySetting webInputNotifySetting,
            BindingResult bindingResult
    ) {
        try {
            NotifySetting notifySetting = WebInputNotifySetting.toStackBean(
                    webInputNotifySetting
            );
            LongIdKey insert = service.insert(notifySetting);
            return FastJsonResponseData.of(ResponseDataUtil.good(FastJsonLongIdKey.of(insert)));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @PatchMapping("/notify-setting")
    @BehaviorAnalyse
    @BindingCheck
    @LoginRequired
    public FastJsonResponseData<Object> update(
            HttpServletRequest request,
            @RequestBody @Validated WebInputNotifySetting webInputNotifySetting,
            BindingResult bindingResult
    ) {
        try {
            service.update(WebInputNotifySetting.toStackBean(webInputNotifySetting));
            return FastJsonResponseData.of(ResponseDataUtil.good(null));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @DeleteMapping("/notify-setting/{id}")
    @BehaviorAnalyse
    @LoginRequired
    public FastJsonResponseData<Object> delete(HttpServletRequest request, @PathVariable("id") Long id) {
        try {
            service.delete(new LongIdKey(id));
            return FastJsonResponseData.of(ResponseDataUtil.good(null));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @GetMapping("/notify-setting/all")
    @BehaviorAnalyse
    @SkipRecord
    @LoginRequired
    public FastJsonResponseData<JSFixedFastJsonPagedData<JSFixedFastJsonNotifySetting>> all(
            HttpServletRequest request, @RequestParam("page") int page, @RequestParam("rows") int rows) {
        try {
            PagedData<NotifySetting> all = service.all(new PagingInfo(page, rows));
            PagedData<JSFixedFastJsonNotifySetting> transform = PagingUtil.transform(all, beanTransformer);
            return FastJsonResponseData.of(ResponseDataUtil.good(JSFixedFastJsonPagedData.of(transform)));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @GetMapping(value = "/notify-setting/label-like")
    @BehaviorAnalyse
    @SkipRecord
    @LoginRequired
    public FastJsonResponseData<JSFixedFastJsonPagedData<JSFixedFastJsonNotifySetting>> labelLike(
            HttpServletRequest request,
            @RequestParam("pattern") String pattern, @RequestParam("page") int page, @RequestParam("rows") int rows
    ) {
        try {
            PagedData<NotifySetting> labelLike = service.labelLike(pattern, new PagingInfo(page, rows));
            PagedData<JSFixedFastJsonNotifySetting> transform = PagingUtil.transform(labelLike, beanTransformer);
            return FastJsonResponseData.of(ResponseDataUtil.good(JSFixedFastJsonPagedData.of(transform)));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }
}
