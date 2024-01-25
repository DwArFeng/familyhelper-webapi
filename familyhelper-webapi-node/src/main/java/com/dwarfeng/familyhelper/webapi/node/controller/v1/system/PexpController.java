package com.dwarfeng.familyhelper.webapi.node.controller.v1.system;

import com.dwarfeng.familyhelper.webapi.stack.service.system.PexpResponseService;
import com.dwarfeng.rbacds.sdk.bean.entity.JSFixedFastJsonPexp;
import com.dwarfeng.rbacds.sdk.bean.entity.WebInputPexp;
import com.dwarfeng.rbacds.stack.bean.entity.Pexp;
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
import com.dwarfeng.subgrade.stack.bean.key.StringIdKey;
import com.dwarfeng.subgrade.stack.exception.ServiceExceptionMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * 权限表达式控制器。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
@RestController
@RequestMapping("/api/v1/system")
public class PexpController {

    private static final Logger LOGGER = LoggerFactory.getLogger(PexpController.class);

    private final PexpResponseService service;
    private final ServiceExceptionMapper sem;

    private final BeanTransformer<Pexp, JSFixedFastJsonPexp> beanTransformer;

    public PexpController(
            PexpResponseService service, ServiceExceptionMapper sem, BeanTransformer<Pexp,
            JSFixedFastJsonPexp> beanTransformer
    ) {
        this.service = service;
        this.sem = sem;
        this.beanTransformer = beanTransformer;
    }

    @GetMapping("/pexp/{id}/exists")
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

    @GetMapping("/pexp/{id}")
    @BehaviorAnalyse
    @LoginRequired
    public FastJsonResponseData<JSFixedFastJsonPexp> get(HttpServletRequest request, @PathVariable("id") long id) {
        try {
            Pexp pexp = service.get(new LongIdKey(id));
            return FastJsonResponseData.of(ResponseDataUtil.good(JSFixedFastJsonPexp.of(pexp)));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @PostMapping("/pexp")
    @BehaviorAnalyse
    @BindingCheck
    @LoginRequired
    public FastJsonResponseData<FastJsonLongIdKey> insert(
            HttpServletRequest request,
            @RequestBody @Validated(Insert.class) WebInputPexp webInputPexp, BindingResult bindingResult) {
        try {
            Pexp pexp = WebInputPexp.toStackBean(webInputPexp);
            LongIdKey insert = service.insert(pexp);
            return FastJsonResponseData.of(ResponseDataUtil.good(FastJsonLongIdKey.of(insert)));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @PatchMapping("/pexp")
    @BehaviorAnalyse
    @BindingCheck
    @LoginRequired
    public FastJsonResponseData<Object> update(
            HttpServletRequest request,
            @RequestBody @Validated WebInputPexp webInputPexp, BindingResult bindingResult
    ) {
        try {
            service.update(WebInputPexp.toStackBean(webInputPexp));
            return FastJsonResponseData.of(ResponseDataUtil.good(null));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @DeleteMapping("/pexp/{id}")
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

    @GetMapping("/role/{roleId}/pexp")
    @BehaviorAnalyse
    @SkipRecord
    @LoginRequired
    public FastJsonResponseData<JSFixedFastJsonPagedData<JSFixedFastJsonPexp>> childForRole(
            HttpServletRequest request,
            @PathVariable("roleId") String roleId, @RequestParam("page") int page, @RequestParam("rows") int rows
    ) {
        try {
            PagedData<Pexp> all = service.childForRole(new StringIdKey(roleId), new PagingInfo(page, rows));
            PagedData<JSFixedFastJsonPexp> transform = PagingUtil.transform(all, beanTransformer);
            return FastJsonResponseData.of(ResponseDataUtil.good(JSFixedFastJsonPagedData.of(transform)));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }
}
