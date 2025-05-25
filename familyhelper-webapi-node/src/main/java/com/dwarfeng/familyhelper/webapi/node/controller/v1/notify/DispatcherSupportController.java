package com.dwarfeng.familyhelper.webapi.node.controller.v1.notify;

import com.dwarfeng.familyhelper.webapi.stack.service.notify.DispatcherSupportResponseService;
import com.dwarfeng.notify.sdk.bean.entity.FastJsonDispatcherSupport;
import com.dwarfeng.notify.stack.bean.entity.DispatcherSupport;
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
import com.dwarfeng.subgrade.stack.bean.key.StringIdKey;
import com.dwarfeng.subgrade.stack.exception.ServiceExceptionMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * 调度器支持控制器。
 *
 * @author DwArFeng
 * @since 1.0.7
 */
@RestController
@RequestMapping("/api/v1/notify")
public class DispatcherSupportController {

    private static final Logger LOGGER = LoggerFactory.getLogger(DispatcherSupportController.class);

    private final DispatcherSupportResponseService service;
    private final ServiceExceptionMapper sem;

    private final BeanTransformer<DispatcherSupport, FastJsonDispatcherSupport> beanTransformer;

    public DispatcherSupportController(
            DispatcherSupportResponseService service, ServiceExceptionMapper sem,
            BeanTransformer<DispatcherSupport, FastJsonDispatcherSupport> beanTransformer
    ) {
        this.service = service;
        this.sem = sem;
        this.beanTransformer = beanTransformer;
    }

    @GetMapping("/dispatcher-support/{id}/exists")
    @BehaviorAnalyse
    @LoginRequired
    @PermissionRequired("webapi.controller_permitted.notify.dispatcher_support.exists")
    public FastJsonResponseData<Boolean> exists(HttpServletRequest request, @PathVariable("id") String id) {
        try {
            boolean exists = service.exists(new StringIdKey(id));
            return FastJsonResponseData.of(ResponseDataUtil.good(exists));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @GetMapping("/dispatcher-support/{id}")
    @BehaviorAnalyse
    @LoginRequired
    @PermissionRequired("webapi.controller_permitted.notify.dispatcher_support.get")
    public FastJsonResponseData<FastJsonDispatcherSupport> get(HttpServletRequest request, @PathVariable("id") String id) {
        try {
            DispatcherSupport dispatcherSupport = service.get(new StringIdKey(id));
            return FastJsonResponseData.of(ResponseDataUtil.good(FastJsonDispatcherSupport.of(dispatcherSupport)));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @GetMapping("/dispatcher-support/all")
    @BehaviorAnalyse
    @SkipRecord
    @LoginRequired
    @PermissionRequired("webapi.controller_permitted.notify.dispatcher_support.all")
    public FastJsonResponseData<JSFixedFastJsonPagedData<FastJsonDispatcherSupport>> all(
            HttpServletRequest request, @RequestParam("page") int page, @RequestParam("rows") int rows
    ) {
        try {
            PagedData<DispatcherSupport> all = service.all(new PagingInfo(page, rows));
            PagedData<FastJsonDispatcherSupport> transform = PagingUtil.transform(all, beanTransformer);
            return FastJsonResponseData.of(ResponseDataUtil.good(JSFixedFastJsonPagedData.of(transform)));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @GetMapping("/dispatcher-support/id-like")
    @BehaviorAnalyse
    @SkipRecord
    @LoginRequired
    @PermissionRequired("webapi.controller_permitted.notify.dispatcher_support.id_like")
    public FastJsonResponseData<JSFixedFastJsonPagedData<FastJsonDispatcherSupport>> idLike(
            HttpServletRequest request,
            @RequestParam("pattern") String pattern, @RequestParam("page") int page, @RequestParam("rows") int rows
    ) {
        try {
            PagedData<DispatcherSupport> all = service.idLike(pattern, new PagingInfo(page, rows));
            PagedData<FastJsonDispatcherSupport> transform = PagingUtil.transform(all, beanTransformer);
            return FastJsonResponseData.of(ResponseDataUtil.good(JSFixedFastJsonPagedData.of(transform)));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }
}
