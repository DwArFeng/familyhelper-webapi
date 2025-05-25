package com.dwarfeng.familyhelper.webapi.node.controller.v1.notify;

import com.dwarfeng.familyhelper.webapi.stack.service.notify.RouterSupportResponseService;
import com.dwarfeng.notify.sdk.bean.entity.FastJsonRouterSupport;
import com.dwarfeng.notify.stack.bean.entity.RouterSupport;
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
 * 路由器支持控制器。
 *
 * @author DwArFeng
 * @since 1.0.7
 */
@RestController("notifyRouterSupportController")
@RequestMapping("/api/v1/notify")
public class RouterSupportController {

    private static final Logger LOGGER = LoggerFactory.getLogger(RouterSupportController.class);

    private final RouterSupportResponseService service;
    private final ServiceExceptionMapper sem;

    private final BeanTransformer<RouterSupport, FastJsonRouterSupport> beanTransformer;

    public RouterSupportController(
            RouterSupportResponseService service, ServiceExceptionMapper sem,
            BeanTransformer<RouterSupport, FastJsonRouterSupport> beanTransformer
    ) {
        this.service = service;
        this.sem = sem;
        this.beanTransformer = beanTransformer;
    }

    @GetMapping("/router-support/{id}/exists")
    @BehaviorAnalyse
    @LoginRequired
    @PermissionRequired("webapi.controller_permitted.notify.router_support.exists")
    public FastJsonResponseData<Boolean> exists(HttpServletRequest request, @PathVariable("id") String id) {
        try {
            boolean exists = service.exists(new StringIdKey(id));
            return FastJsonResponseData.of(ResponseDataUtil.good(exists));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @GetMapping("/router-support/{id}")
    @BehaviorAnalyse
    @LoginRequired
    @PermissionRequired("webapi.controller_permitted.notify.router_support.get")
    public FastJsonResponseData<FastJsonRouterSupport> get(HttpServletRequest request, @PathVariable("id") String id) {
        try {
            RouterSupport routerSupport = service.get(new StringIdKey(id));
            return FastJsonResponseData.of(ResponseDataUtil.good(FastJsonRouterSupport.of(routerSupport)));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @GetMapping("/router-support/all")
    @BehaviorAnalyse
    @SkipRecord
    @LoginRequired
    @PermissionRequired("webapi.controller_permitted.notify.router_support.all")
    public FastJsonResponseData<JSFixedFastJsonPagedData<FastJsonRouterSupport>> all(
            HttpServletRequest request, @RequestParam("page") int page, @RequestParam("rows") int rows
    ) {
        try {
            PagedData<RouterSupport> all = service.all(new PagingInfo(page, rows));
            PagedData<FastJsonRouterSupport> transform = PagingUtil.transform(all, beanTransformer);
            return FastJsonResponseData.of(ResponseDataUtil.good(JSFixedFastJsonPagedData.of(transform)));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @GetMapping("/router-support/id-like")
    @BehaviorAnalyse
    @SkipRecord
    @LoginRequired
    @PermissionRequired("webapi.controller_permitted.notify.router_support.id_like")
    public FastJsonResponseData<JSFixedFastJsonPagedData<FastJsonRouterSupport>> idLike(
            HttpServletRequest request,
            @RequestParam("pattern") String pattern, @RequestParam("page") int page, @RequestParam("rows") int rows
    ) {
        try {
            PagedData<RouterSupport> all = service.idLike(pattern, new PagingInfo(page, rows));
            PagedData<FastJsonRouterSupport> transform = PagingUtil.transform(all, beanTransformer);
            return FastJsonResponseData.of(ResponseDataUtil.good(JSFixedFastJsonPagedData.of(transform)));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }
}
