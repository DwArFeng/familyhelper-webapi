package com.dwarfeng.familyhelper.webapi.node.controller.v1.acckeeper;

import com.dwarfeng.acckeeper.sdk.bean.entity.FastJsonProtectorSupport;
import com.dwarfeng.acckeeper.stack.bean.entity.ProtectorSupport;
import com.dwarfeng.familyhelper.webapi.stack.service.acckeeper.ProtectorSupportResponseService;
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
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * 保护器支持控制器。
 *
 * @author DwArFeng
 * @since 2.0.0
 */
@RestController
@RequestMapping("/api/v1/acckeeper")
public class ProtectorSupportController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProtectorSupportController.class);

    private final ProtectorSupportResponseService service;

    private final ServiceExceptionMapper sem;

    private final BeanTransformer<ProtectorSupport, FastJsonProtectorSupport> beanTransformer;

    public ProtectorSupportController(
            ProtectorSupportResponseService service,
            ServiceExceptionMapper sem,
            @Qualifier("acckeeper.protectorSupportBeanTransformer")
            BeanTransformer<ProtectorSupport, FastJsonProtectorSupport> beanTransformer
    ) {
        this.service = service;
        this.sem = sem;
        this.beanTransformer = beanTransformer;
    }

    @GetMapping("/protector-support/{id}/exists")
    @BehaviorAnalyse
    @LoginRequired
    @PermissionRequired("webapi.controller_permitted.acckeeper.protector_support.exists")
    public FastJsonResponseData<Boolean> exists(HttpServletRequest request, @PathVariable("id") String id) {
        try {
            boolean exists = service.exists(new StringIdKey(id));
            return FastJsonResponseData.of(ResponseDataUtil.good(exists));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @GetMapping("/protector-support/{id}")
    @BehaviorAnalyse
    @LoginRequired
    @PermissionRequired("webapi.controller_permitted.acckeeper.protector_support.get")
    public FastJsonResponseData<FastJsonProtectorSupport> get(
            HttpServletRequest request, @PathVariable("id") String id
    ) {
        try {
            ProtectorSupport protectorSupport = service.get(new StringIdKey(id));
            return FastJsonResponseData.of(ResponseDataUtil.good(FastJsonProtectorSupport.of(protectorSupport)));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @GetMapping("/protector-support/all")
    @BehaviorAnalyse
    @SkipRecord
    @LoginRequired
    @PermissionRequired("webapi.controller_permitted.acckeeper.protector_support.all")
    public FastJsonResponseData<JSFixedFastJsonPagedData<FastJsonProtectorSupport>> all(
            HttpServletRequest request, @RequestParam("page") int page, @RequestParam("rows") int rows
    ) {
        try {
            PagedData<ProtectorSupport> all = service.all(new PagingInfo(page, rows));
            PagedData<FastJsonProtectorSupport> transform = PagingUtil.transform(all, beanTransformer);
            return FastJsonResponseData.of(ResponseDataUtil.good(JSFixedFastJsonPagedData.of(transform)));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @GetMapping("/protector-support/id-like")
    @BehaviorAnalyse
    @SkipRecord
    @LoginRequired
    @PermissionRequired("webapi.controller_permitted.acckeeper.protector_support.id_like")
    public FastJsonResponseData<JSFixedFastJsonPagedData<FastJsonProtectorSupport>> idLike(
            HttpServletRequest request,
            @RequestParam("pattern") String pattern,
            @RequestParam("page") int page, @RequestParam("rows") int rows
    ) {
        try {
            PagedData<ProtectorSupport> child = service.idLike(pattern, new PagingInfo(page, rows));
            PagedData<FastJsonProtectorSupport> transform = PagingUtil.transform(child, beanTransformer);
            return FastJsonResponseData.of(ResponseDataUtil.good(JSFixedFastJsonPagedData.of(transform)));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @GetMapping("/protector-support/label-like")
    @BehaviorAnalyse
    @SkipRecord
    @LoginRequired
    @PermissionRequired("webapi.controller_permitted.acckeeper.protector_support.label_like")
    public FastJsonResponseData<JSFixedFastJsonPagedData<FastJsonProtectorSupport>> labelLike(
            HttpServletRequest request,
            @RequestParam("pattern") String pattern,
            @RequestParam("page") int page, @RequestParam("rows") int rows
    ) {
        try {
            PagedData<ProtectorSupport> child = service.labelLike(pattern, new PagingInfo(page, rows));
            PagedData<FastJsonProtectorSupport> transform = PagingUtil.transform(child, beanTransformer);
            return FastJsonResponseData.of(ResponseDataUtil.good(JSFixedFastJsonPagedData.of(transform)));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }
}
