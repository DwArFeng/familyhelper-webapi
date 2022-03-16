package com.dwarfeng.familyhelper.webapi.node.controller.v1.clannad;

import com.dwarfeng.familyhelper.clannad.sdk.bean.dto.WebInputNotificationCreateInfo;
import com.dwarfeng.familyhelper.clannad.sdk.bean.entity.JSFixedFastJsonNotification;
import com.dwarfeng.familyhelper.clannad.stack.bean.entity.Notification;
import com.dwarfeng.familyhelper.webapi.sdk.cna.ValidateList;
import com.dwarfeng.familyhelper.webapi.stack.service.clannad.NotificationResponseService;
import com.dwarfeng.subgrade.sdk.bean.dto.FastJsonResponseData;
import com.dwarfeng.subgrade.sdk.bean.dto.JSFixedFastJsonPagedData;
import com.dwarfeng.subgrade.sdk.bean.dto.PagingUtil;
import com.dwarfeng.subgrade.sdk.bean.dto.ResponseDataUtil;
import com.dwarfeng.subgrade.sdk.bean.key.JSFixedFastJsonLongIdKey;
import com.dwarfeng.subgrade.sdk.bean.key.WebInputLongIdKey;
import com.dwarfeng.subgrade.sdk.interceptor.analyse.BehaviorAnalyse;
import com.dwarfeng.subgrade.sdk.interceptor.analyse.SkipRecord;
import com.dwarfeng.subgrade.sdk.interceptor.http.BindingCheck;
import com.dwarfeng.subgrade.sdk.interceptor.login.LoginRequired;
import com.dwarfeng.subgrade.stack.bean.BeanTransformer;
import com.dwarfeng.subgrade.stack.bean.dto.PagedData;
import com.dwarfeng.subgrade.stack.bean.dto.PagingInfo;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.bean.key.StringIdKey;
import com.dwarfeng.subgrade.stack.exception.ServiceExceptionMapper;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 通知控制器。
 *
 * @author DwArFeng
 * @since 1.0.3
 */
@RestController
@RequestMapping("/api/v1/clannad")
public class NotificationController {

    private final NotificationResponseService service;

    private final ServiceExceptionMapper sem;

    private final BeanTransformer<Notification, JSFixedFastJsonNotification> notificationBeanTransformer;

    public NotificationController(
            NotificationResponseService service, ServiceExceptionMapper sem,
            BeanTransformer<Notification, JSFixedFastJsonNotification> notificationBeanTransformer
    ) {
        this.service = service;
        this.sem = sem;
        this.notificationBeanTransformer = notificationBeanTransformer;
    }

    @GetMapping("/notification/{id}/exists")
    @BehaviorAnalyse
    @LoginRequired
    public FastJsonResponseData<Boolean> exists(HttpServletRequest request, @PathVariable("id") Long id) {
        try {
            boolean exists = service.exists(new LongIdKey(id));
            return FastJsonResponseData.of(ResponseDataUtil.good(exists));
        } catch (Exception e) {
            return FastJsonResponseData.of(ResponseDataUtil.bad(Boolean.class, e, sem));
        }
    }

    @GetMapping("/notification/{id}")
    @BehaviorAnalyse
    @LoginRequired
    public FastJsonResponseData<JSFixedFastJsonNotification> get(
            HttpServletRequest request, @PathVariable("id") Long id
    ) {
        try {
            Notification notification = service.get(new LongIdKey(id));
            return FastJsonResponseData.of(ResponseDataUtil.good(JSFixedFastJsonNotification.of(notification)));
        } catch (Exception e) {
            return FastJsonResponseData.of(ResponseDataUtil.bad(JSFixedFastJsonNotification.class, e, sem));
        }
    }

    @DeleteMapping("/notification/{id}")
    @BehaviorAnalyse
    @LoginRequired
    public FastJsonResponseData<Object> delete(HttpServletRequest request, @PathVariable("id") Long id) {
        try {
            service.delete(new LongIdKey(id));
            return FastJsonResponseData.of(ResponseDataUtil.good(null));
        } catch (Exception e) {
            return FastJsonResponseData.of(ResponseDataUtil.bad(Object.class, e, sem));
        }
    }

    @GetMapping("/notification/all")
    @BehaviorAnalyse
    @SkipRecord
    @LoginRequired
    public FastJsonResponseData<JSFixedFastJsonPagedData<JSFixedFastJsonNotification>> all(
            HttpServletRequest request, @RequestParam("page") int page, @RequestParam("rows") int rows) {
        try {
            PagedData<Notification> all = service.all(new PagingInfo(page, rows));
            PagedData<JSFixedFastJsonNotification> transform = PagingUtil.transform(all, notificationBeanTransformer);
            return FastJsonResponseData.of(ResponseDataUtil.good(JSFixedFastJsonPagedData.of(transform)));
        } catch (Exception e) {
            return FastJsonResponseData.of(ResponseDataUtil.bad(JSFixedFastJsonPagedData.class, e, sem));
        }
    }

    @GetMapping(value = {"/user/{userId}/notification", "/user//notification"})
    @BehaviorAnalyse
    @SkipRecord
    @LoginRequired
    public FastJsonResponseData<JSFixedFastJsonPagedData<JSFixedFastJsonNotification>> childForUser(
            HttpServletRequest request,
            @PathVariable(required = false, value = "userId") String userId,
            @RequestParam("page") int page, @RequestParam("rows") int rows
    ) {
        try {
            StringIdKey userKey = null;
            if (Objects.nonNull(userId)) {
                userKey = new StringIdKey(userId);
            }
            PagedData<Notification> childForUser = service.childForUser(userKey, new PagingInfo(page, rows));
            PagedData<JSFixedFastJsonNotification> transform = PagingUtil.transform(
                    childForUser, notificationBeanTransformer
            );
            return FastJsonResponseData.of(ResponseDataUtil.good(JSFixedFastJsonPagedData.of(transform)));
        } catch (Exception e) {
            return FastJsonResponseData.of(ResponseDataUtil.bad(JSFixedFastJsonPagedData.class, e, sem));
        }
    }

    @GetMapping(value = {"/user/{userId}/notification/unread", "/user//notification/unread"})
    @BehaviorAnalyse
    @SkipRecord
    @LoginRequired
    public FastJsonResponseData<JSFixedFastJsonPagedData<JSFixedFastJsonNotification>> childForUserUnread(
            HttpServletRequest request,
            @PathVariable(required = false, value = "userId") String userId,
            @RequestParam("page") int page, @RequestParam("rows") int rows
    ) {
        try {
            StringIdKey userKey = null;
            if (Objects.nonNull(userId)) {
                userKey = new StringIdKey(userId);
            }
            PagedData<Notification> childForUser = service.childForUserUnread(userKey, new PagingInfo(page, rows));
            PagedData<JSFixedFastJsonNotification> transform = PagingUtil.transform(
                    childForUser, notificationBeanTransformer
            );
            return FastJsonResponseData.of(ResponseDataUtil.good(JSFixedFastJsonPagedData.of(transform)));
        } catch (Exception e) {
            return FastJsonResponseData.of(ResponseDataUtil.bad(JSFixedFastJsonPagedData.class, e, sem));
        }
    }

    @PostMapping("/notification/create")
    @BehaviorAnalyse
    @BindingCheck
    public FastJsonResponseData<JSFixedFastJsonLongIdKey> createNotification(
            HttpServletRequest request,
            @RequestBody @Validated WebInputNotificationCreateInfo notificationCreateInfo, BindingResult bindingResult
    ) {
        try {
            LongIdKey result = service.createNotification(
                    WebInputNotificationCreateInfo.toStackBean(notificationCreateInfo)
            );
            return FastJsonResponseData.of(ResponseDataUtil.good(JSFixedFastJsonLongIdKey.of(result)));
        } catch (Exception e) {
            return FastJsonResponseData.of(ResponseDataUtil.bad(Object.class, e, sem));
        }
    }

    @PostMapping("/notification/read")
    @BehaviorAnalyse
    @BindingCheck
    public FastJsonResponseData<Object> readNotification(
            HttpServletRequest request,
            @RequestBody @Validated ValidateList<WebInputLongIdKey> notificationKeys, BindingResult bindingResult
    ) {
        try {
            service.readNotification(
                    notificationKeys.stream().map(WebInputLongIdKey::toStackBean).collect(Collectors.toList())
            );
            return FastJsonResponseData.of(ResponseDataUtil.good(null));
        } catch (Exception e) {
            return FastJsonResponseData.of(ResponseDataUtil.bad(Object.class, e, sem));
        }
    }
}
