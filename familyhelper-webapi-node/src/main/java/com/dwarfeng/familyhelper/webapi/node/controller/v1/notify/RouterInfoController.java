package com.dwarfeng.familyhelper.webapi.node.controller.v1.notify;

import com.dwarfeng.familyhelper.webapi.stack.service.notify.RouterInfoResponseService;
import com.dwarfeng.notify.sdk.bean.entity.JSFixedFastJsonRouterInfo;
import com.dwarfeng.notify.sdk.bean.entity.WebInputRouterInfo;
import com.dwarfeng.notify.stack.bean.entity.RouterInfo;
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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * 路由器信息控制器。
 *
 * @author DwArFeng
 * @since 1.0.7
 */
@RestController("notifyRouterInfoController")
@RequestMapping("/api/v1/notify")
public class RouterInfoController {

    private static final Logger LOGGER = LoggerFactory.getLogger(RouterInfoController.class);

    private final RouterInfoResponseService service;
    private final ServiceExceptionMapper sem;

    private final BeanTransformer<RouterInfo, JSFixedFastJsonRouterInfo> beanTransformer;

    public RouterInfoController(
            RouterInfoResponseService service, ServiceExceptionMapper sem,
            BeanTransformer<RouterInfo, JSFixedFastJsonRouterInfo> beanTransformer
    ) {
        this.service = service;
        this.sem = sem;
        this.beanTransformer = beanTransformer;
    }

    @GetMapping("/router-info/{id}/exists")
    @BehaviorAnalyse
    @LoginRequired
    public FastJsonResponseData<Boolean> exists(HttpServletRequest request, @PathVariable("id") long id) {
        try {
            boolean exists = service.exists(new LongIdKey(id));
            return FastJsonResponseData.of(ResponseDataUtil.good(exists));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @GetMapping("/router-info/{id}")
    @BehaviorAnalyse
    @LoginRequired
    public FastJsonResponseData<JSFixedFastJsonRouterInfo> get(
            HttpServletRequest request, @PathVariable("id") long id
    ) {
        try {
            RouterInfo routerInfo = service.get(new LongIdKey(id));
            return FastJsonResponseData.of(ResponseDataUtil.good(
                    JSFixedFastJsonRouterInfo.of(routerInfo)
            ));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @PostMapping("/router-info")
    @BehaviorAnalyse
    @BindingCheck
    @LoginRequired
    public FastJsonResponseData<FastJsonLongIdKey> insert(
            HttpServletRequest request,
            @RequestBody @Validated(Insert.class) WebInputRouterInfo webInputRouterInfo,
            BindingResult bindingResult
    ) {
        try {
            RouterInfo routerInfo = WebInputRouterInfo.toStackBean(webInputRouterInfo);
            LongIdKey insert = service.insert(routerInfo);
            return FastJsonResponseData.of(ResponseDataUtil.good(FastJsonLongIdKey.of(insert)));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @PatchMapping("/router-info")
    @BehaviorAnalyse
    @BindingCheck
    @LoginRequired
    public FastJsonResponseData<Object> update(
            HttpServletRequest request,
            @RequestBody @Validated WebInputRouterInfo webInputRouterInfo,
            BindingResult bindingResult
    ) {
        try {
            service.update(WebInputRouterInfo.toStackBean(webInputRouterInfo));
            return FastJsonResponseData.of(ResponseDataUtil.good(null));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @DeleteMapping("/router-info/{id}")
    @BehaviorAnalyse
    @LoginRequired
    public FastJsonResponseData<Object> delete(HttpServletRequest request, @PathVariable("id") long id) {
        try {
            service.delete(new LongIdKey(id));
            return FastJsonResponseData.of(ResponseDataUtil.good(null));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @GetMapping("/router-info/all")
    @BehaviorAnalyse
    @SkipRecord
    @LoginRequired
    public FastJsonResponseData<JSFixedFastJsonPagedData<JSFixedFastJsonRouterInfo>> all(
            HttpServletRequest request, @RequestParam("page") int page, @RequestParam("rows") int rows
    ) {
        try {
            PagedData<RouterInfo> all = service.all(new PagingInfo(page, rows));
            PagedData<JSFixedFastJsonRouterInfo> transform = PagingUtil.transform(all, beanTransformer);
            return FastJsonResponseData.of(ResponseDataUtil.good(JSFixedFastJsonPagedData.of(transform)));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @GetMapping("/router-info/type-equals")
    @BehaviorAnalyse
    @SkipRecord
    @LoginRequired
    public FastJsonResponseData<JSFixedFastJsonPagedData<JSFixedFastJsonRouterInfo>> typeEquals(
            HttpServletRequest request,
            @RequestParam("pattern") String pattern, @RequestParam("page") int page, @RequestParam("rows") int rows
    ) {
        try {
            PagedData<RouterInfo> typeEquals = service.typeEquals(pattern, new PagingInfo(page, rows));
            PagedData<JSFixedFastJsonRouterInfo> transform = PagingUtil.transform(typeEquals, beanTransformer);
            return FastJsonResponseData.of(ResponseDataUtil.good(JSFixedFastJsonPagedData.of(transform)));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }
}
