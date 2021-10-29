package com.dwarfeng.familyhelper.webapi.node.controller.v1.clannad;

import com.dwarfeng.familyhelper.clannad.sdk.bean.entity.FastJsonGenderTypeIndicator;
import com.dwarfeng.familyhelper.clannad.sdk.bean.entity.WebInputGenderTypeIndicator;
import com.dwarfeng.familyhelper.clannad.stack.bean.entity.GenderTypeIndicator;
import com.dwarfeng.familyhelper.webapi.stack.service.clannad.GenderTypeIndicatorResponseService;
import com.dwarfeng.subgrade.sdk.bean.dto.FastJsonResponseData;
import com.dwarfeng.subgrade.sdk.bean.dto.JSFixedFastJsonPagedData;
import com.dwarfeng.subgrade.sdk.bean.dto.PagingUtil;
import com.dwarfeng.subgrade.sdk.bean.dto.ResponseDataUtil;
import com.dwarfeng.subgrade.sdk.bean.key.FastJsonStringIdKey;
import com.dwarfeng.subgrade.sdk.interceptor.analyse.BehaviorAnalyse;
import com.dwarfeng.subgrade.sdk.interceptor.analyse.SkipRecord;
import com.dwarfeng.subgrade.sdk.interceptor.http.BindingCheck;
import com.dwarfeng.subgrade.sdk.interceptor.login.LoginRequired;
import com.dwarfeng.subgrade.sdk.validation.group.Insert;
import com.dwarfeng.subgrade.stack.bean.BeanTransformer;
import com.dwarfeng.subgrade.stack.bean.dto.PagedData;
import com.dwarfeng.subgrade.stack.bean.dto.PagingInfo;
import com.dwarfeng.subgrade.stack.bean.key.StringIdKey;
import com.dwarfeng.subgrade.stack.exception.ServiceExceptionMapper;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * 性别类型指示器控制器。
 *
 * @author DwArFeng
 * @since 1.0.1
 */
@RestController
@RequestMapping("/api/v1/clannad")
public class GenderTypeIndicatorController {

    private final GenderTypeIndicatorResponseService service;
    private final ServiceExceptionMapper sem;

    private final BeanTransformer<GenderTypeIndicator, FastJsonGenderTypeIndicator> beanTransformer;

    public GenderTypeIndicatorController(
            GenderTypeIndicatorResponseService service, ServiceExceptionMapper sem,
            BeanTransformer<GenderTypeIndicator, FastJsonGenderTypeIndicator> beanTransformer
    ) {
        this.service = service;
        this.sem = sem;
        this.beanTransformer = beanTransformer;
    }

    @GetMapping("/gender-type-indicator/{id}/exists")
    @BehaviorAnalyse
    @LoginRequired
    public FastJsonResponseData<Boolean> exists(HttpServletRequest request, @PathVariable("id") String id) {
        try {
            boolean exists = service.exists(new StringIdKey(id));
            return FastJsonResponseData.of(ResponseDataUtil.good(exists));
        } catch (Exception e) {
            return FastJsonResponseData.of(ResponseDataUtil.bad(Boolean.class, e, sem));
        }
    }

    @GetMapping("/gender-type-indicator/{id}")
    @BehaviorAnalyse
    @LoginRequired
    public FastJsonResponseData<FastJsonGenderTypeIndicator> get(HttpServletRequest request, @PathVariable("id") String id) {
        try {
            GenderTypeIndicator genderTypeIndicator = service.get(new StringIdKey(id));
            return FastJsonResponseData.of(ResponseDataUtil.good(FastJsonGenderTypeIndicator.of(genderTypeIndicator)));
        } catch (Exception e) {
            return FastJsonResponseData.of(ResponseDataUtil.bad(FastJsonGenderTypeIndicator.class, e, sem));
        }
    }

    @PostMapping("/gender-type-indicator")
    @BehaviorAnalyse
    @BindingCheck
    @LoginRequired
    public FastJsonResponseData<FastJsonStringIdKey> insert(
            HttpServletRequest request,
            @RequestBody @Validated(Insert.class) WebInputGenderTypeIndicator webInputGenderTypeIndicator,
            BindingResult bindingResult
    ) {
        try {
            GenderTypeIndicator genderTypeIndicator = WebInputGenderTypeIndicator.toStackBean(webInputGenderTypeIndicator);
            StringIdKey insert = service.insert(genderTypeIndicator);
            return FastJsonResponseData.of(ResponseDataUtil.good(FastJsonStringIdKey.of(insert)));
        } catch (Exception e) {
            return FastJsonResponseData.of(ResponseDataUtil.bad(FastJsonStringIdKey.class, e, sem));
        }
    }

    @PatchMapping("/gender-type-indicator")
    @BehaviorAnalyse
    @BindingCheck
    @LoginRequired
    public FastJsonResponseData<Object> update(
            HttpServletRequest request,
            @RequestBody @Validated WebInputGenderTypeIndicator webInputGenderTypeIndicator,
            BindingResult bindingResult
    ) {
        try {
            service.update(WebInputGenderTypeIndicator.toStackBean(webInputGenderTypeIndicator));
            return FastJsonResponseData.of(ResponseDataUtil.good(null));
        } catch (Exception e) {
            return FastJsonResponseData.of(ResponseDataUtil.bad(Object.class, e, sem));
        }
    }

    @DeleteMapping("/gender-type-indicator/{id}")
    @BehaviorAnalyse
    @LoginRequired
    public FastJsonResponseData<Object> delete(HttpServletRequest request, @PathVariable("id") String id) {
        try {
            service.delete(new StringIdKey(id));
            return FastJsonResponseData.of(ResponseDataUtil.good(null));
        } catch (Exception e) {
            return FastJsonResponseData.of(ResponseDataUtil.bad(Object.class, e, sem));
        }
    }

    @GetMapping("/gender-type-indicator/all")
    @BehaviorAnalyse
    @SkipRecord
    @LoginRequired
    public FastJsonResponseData<JSFixedFastJsonPagedData<FastJsonGenderTypeIndicator>> all(
            HttpServletRequest request, @RequestParam("page") int page, @RequestParam("rows") int rows) {
        try {
            PagedData<GenderTypeIndicator> all = service.all(new PagingInfo(page, rows));
            PagedData<FastJsonGenderTypeIndicator> transform = PagingUtil.transform(all, beanTransformer);
            return FastJsonResponseData.of(ResponseDataUtil.good(JSFixedFastJsonPagedData.of(transform)));
        } catch (Exception e) {
            return FastJsonResponseData.of(ResponseDataUtil.bad(JSFixedFastJsonPagedData.class, e, sem));
        }
    }
}
