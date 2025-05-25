package com.dwarfeng.familyhelper.webapi.node.controller.v1.notify;

import com.dwarfeng.familyhelper.webapi.sdk.bean.notify.disp.JSFixedFastJsonDispNotifyHistory;
import com.dwarfeng.familyhelper.webapi.stack.bean.notify.disp.DispNotifyHistory;
import com.dwarfeng.familyhelper.webapi.stack.service.notify.NotifyHistoryResponseService;
import com.dwarfeng.notify.sdk.bean.entity.JSFixedFastJsonNotifyHistory;
import com.dwarfeng.notify.stack.bean.entity.NotifyHistory;
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
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

/**
 * 通知历史控制器。
 *
 * @author DwArFeng
 * @since 1.0.9
 */
@RestController
@RequestMapping("/api/v1/notify")
public class NotifyHistoryController {

    private static final Logger LOGGER = LoggerFactory.getLogger(NotifyHistoryController.class);

    private final NotifyHistoryResponseService service;
    private final ServiceExceptionMapper sem;

    private final BeanTransformer<NotifyHistory, JSFixedFastJsonNotifyHistory> beanTransformer;
    private final BeanTransformer<DispNotifyHistory, JSFixedFastJsonDispNotifyHistory> dispBeanTransformer;

    public NotifyHistoryController(
            NotifyHistoryResponseService service, ServiceExceptionMapper sem,
            BeanTransformer<NotifyHistory, JSFixedFastJsonNotifyHistory> beanTransformer,
            BeanTransformer<DispNotifyHistory, JSFixedFastJsonDispNotifyHistory> dispBeanTransformer
    ) {
        this.service = service;
        this.sem = sem;
        this.beanTransformer = beanTransformer;
        this.dispBeanTransformer = dispBeanTransformer;
    }

    @GetMapping("/notify-history/{id}/exists")
    @BehaviorAnalyse
    @LoginRequired
    @PermissionRequired("webapi.controller_permitted.notify.notify_history.exists")
    public FastJsonResponseData<Boolean> exists(HttpServletRequest request, @PathVariable("id") Long id) {
        try {
            boolean exists = service.exists(new LongIdKey(id));
            return FastJsonResponseData.of(ResponseDataUtil.good(exists));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @GetMapping("/notify-history/{id}")
    @BehaviorAnalyse
    @LoginRequired
    @PermissionRequired("webapi.controller_permitted.notify.notify_history.get")
    public FastJsonResponseData<JSFixedFastJsonNotifyHistory> get(
            HttpServletRequest request, @PathVariable("id") Long id
    ) {
        try {
            NotifyHistory notifyHistory = service.get(new LongIdKey(id));
            return FastJsonResponseData.of(ResponseDataUtil.good(
                    JSFixedFastJsonNotifyHistory.of(notifyHistory)
            ));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @DeleteMapping("/notify-history/{id}")
    @BehaviorAnalyse
    @LoginRequired
    @PermissionRequired("webapi.controller_permitted.notify.notify_history.delete")
    public FastJsonResponseData<Object> delete(HttpServletRequest request, @PathVariable("id") Long id) {
        try {
            service.delete(new LongIdKey(id));
            return FastJsonResponseData.of(ResponseDataUtil.good(null));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @GetMapping("/notify-history/all")
    @BehaviorAnalyse
    @SkipRecord
    @LoginRequired
    @PermissionRequired("webapi.controller_permitted.notify.notify_history.all")
    public FastJsonResponseData<JSFixedFastJsonPagedData<JSFixedFastJsonNotifyHistory>> all(
            HttpServletRequest request, @RequestParam("page") int page, @RequestParam("rows") int rows) {
        try {
            PagedData<NotifyHistory> all = service.all(new PagingInfo(page, rows));
            PagedData<JSFixedFastJsonNotifyHistory> transform = PagingUtil.transform(all, beanTransformer);
            return FastJsonResponseData.of(ResponseDataUtil.good(JSFixedFastJsonPagedData.of(transform)));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @GetMapping(value = {"/notify-setting/{notifySettingId}/notify-history", "/notify-setting//notify-history"})
    @BehaviorAnalyse
    @SkipRecord
    @LoginRequired
    @PermissionRequired("webapi.controller_permitted.notify.notify_history.child_for_notify_setting")
    public FastJsonResponseData<JSFixedFastJsonPagedData<JSFixedFastJsonNotifyHistory>> childForNotifySetting(
            HttpServletRequest request,
            @PathVariable(required = false, value = "notifySettingId") Long notifySettingId,
            @RequestParam("page") int page, @RequestParam("rows") int rows
    ) {
        try {
            LongIdKey notifySettingKey = null;
            if (Objects.nonNull(notifySettingId)) {
                notifySettingKey = new LongIdKey(notifySettingId);
            }
            PagedData<NotifyHistory> childForNotifySetting = service.childForNotifySetting(
                    notifySettingKey, new PagingInfo(page, rows)
            );
            PagedData<JSFixedFastJsonNotifyHistory> transform = PagingUtil.transform(
                    childForNotifySetting, beanTransformer
            );
            return FastJsonResponseData.of(ResponseDataUtil.good(JSFixedFastJsonPagedData.of(transform)));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @GetMapping("/notify-history/{id}/disp")
    @BehaviorAnalyse
    @LoginRequired
    @PermissionRequired("webapi.controller_permitted.notify.notify_history.get_disp")
    public FastJsonResponseData<JSFixedFastJsonDispNotifyHistory> getDisp(
            HttpServletRequest request, @PathVariable("id") Long id
    ) {
        try {
            DispNotifyHistory notifyHistory = service.getDisp(new LongIdKey(id));
            return FastJsonResponseData.of(ResponseDataUtil.good(
                    JSFixedFastJsonDispNotifyHistory.of(notifyHistory)
            ));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @GetMapping("/notify-history/all/disp")
    @BehaviorAnalyse
    @SkipRecord
    @LoginRequired
    @PermissionRequired("webapi.controller_permitted.notify.notify_history.all_disp")
    public FastJsonResponseData<JSFixedFastJsonPagedData<JSFixedFastJsonDispNotifyHistory>> allDisp(
            HttpServletRequest request, @RequestParam("page") int page, @RequestParam("rows") int rows) {
        try {
            PagedData<DispNotifyHistory> all = service.allDisp(new PagingInfo(page, rows));
            PagedData<JSFixedFastJsonDispNotifyHistory> transform = PagingUtil.transform(all, dispBeanTransformer);
            return FastJsonResponseData.of(ResponseDataUtil.good(JSFixedFastJsonPagedData.of(transform)));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @GetMapping(value = {
            "/notify-setting/{notifySettingId}/notify-history/disp", "/notify-setting//notify-history/disp"
    })
    @BehaviorAnalyse
    @SkipRecord
    @LoginRequired
    @PermissionRequired("webapi.controller_permitted.notify.notify_history.child_for_notify_setting_disp")
    public FastJsonResponseData<JSFixedFastJsonPagedData<JSFixedFastJsonDispNotifyHistory>> childForNotifySettingDisp(
            HttpServletRequest request,
            @PathVariable(required = false, value = "notifySettingId") Long notifySettingId,
            @RequestParam("page") int page, @RequestParam("rows") int rows
    ) {
        try {
            LongIdKey notifySettingKey = null;
            if (Objects.nonNull(notifySettingId)) {
                notifySettingKey = new LongIdKey(notifySettingId);
            }
            PagedData<DispNotifyHistory> childForNotifySetting = service.childForNotifySettingDisp(
                    notifySettingKey, new PagingInfo(page, rows)
            );
            PagedData<JSFixedFastJsonDispNotifyHistory> transform = PagingUtil.transform(
                    childForNotifySetting, dispBeanTransformer
            );
            return FastJsonResponseData.of(ResponseDataUtil.good(JSFixedFastJsonPagedData.of(transform)));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }
}
