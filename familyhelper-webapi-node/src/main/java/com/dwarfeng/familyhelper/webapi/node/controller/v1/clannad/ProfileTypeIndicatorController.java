package com.dwarfeng.familyhelper.webapi.node.controller.v1.clannad;

import com.dwarfeng.familyhelper.clannad.sdk.bean.entity.FastJsonProfileTypeIndicator;
import com.dwarfeng.familyhelper.clannad.sdk.bean.entity.WebInputProfileTypeIndicator;
import com.dwarfeng.familyhelper.clannad.sdk.bean.key.FastJsonProfileTypeIndicatorKey;
import com.dwarfeng.familyhelper.clannad.stack.bean.entity.ProfileTypeIndicator;
import com.dwarfeng.familyhelper.clannad.stack.bean.key.ProfileTypeIndicatorKey;
import com.dwarfeng.familyhelper.webapi.stack.service.clannad.ProfileTypeIndicatorResponseService;
import com.dwarfeng.subgrade.sdk.bean.dto.FastJsonResponseData;
import com.dwarfeng.subgrade.sdk.bean.dto.JSFixedFastJsonPagedData;
import com.dwarfeng.subgrade.sdk.bean.dto.PagingUtil;
import com.dwarfeng.subgrade.sdk.bean.dto.ResponseDataUtil;
import com.dwarfeng.subgrade.sdk.interceptor.analyse.BehaviorAnalyse;
import com.dwarfeng.subgrade.sdk.interceptor.http.BindingCheck;
import com.dwarfeng.subgrade.sdk.interceptor.login.LoginRequired;
import com.dwarfeng.subgrade.sdk.validation.group.Insert;
import com.dwarfeng.subgrade.stack.bean.BeanTransformer;
import com.dwarfeng.subgrade.stack.bean.dto.PagedData;
import com.dwarfeng.subgrade.stack.bean.dto.PagingInfo;
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
public class ProfileTypeIndicatorController {

    private final ProfileTypeIndicatorResponseService service;
    private final ServiceExceptionMapper sem;

    private final BeanTransformer<ProfileTypeIndicator, FastJsonProfileTypeIndicator> beanTransformer;

    public ProfileTypeIndicatorController(
            ProfileTypeIndicatorResponseService service, ServiceExceptionMapper sem,
            BeanTransformer<ProfileTypeIndicator, FastJsonProfileTypeIndicator> beanTransformer
    ) {
        this.service = service;
        this.sem = sem;
        this.beanTransformer = beanTransformer;
    }

    @GetMapping("/profile-type-indicator/{category}&{id}/exists")
    @BehaviorAnalyse
    @LoginRequired
    public FastJsonResponseData<Boolean> exists(
            HttpServletRequest request, @PathVariable("category") String category, @PathVariable("id") String id
    ) {
        try {
            boolean exists = service.exists(new ProfileTypeIndicatorKey(category, id));
            return FastJsonResponseData.of(ResponseDataUtil.good(exists));
        } catch (Exception e) {
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @GetMapping("/profile-type-indicator/{category}&{id}")
    @BehaviorAnalyse
    @LoginRequired
    public FastJsonResponseData<FastJsonProfileTypeIndicator> get(
            HttpServletRequest request, @PathVariable("category") String category, @PathVariable("id") String id
    ) {
        try {
            ProfileTypeIndicator profileTypeIndicator = service.get(new ProfileTypeIndicatorKey(category, id));
            return FastJsonResponseData.of(ResponseDataUtil.good(FastJsonProfileTypeIndicator.of(profileTypeIndicator)));
        } catch (Exception e) {
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @PostMapping("/profile-type-indicator")
    @BehaviorAnalyse
    @BindingCheck
    @LoginRequired
    public FastJsonResponseData<FastJsonProfileTypeIndicatorKey> insert(
            HttpServletRequest request,
            @RequestBody @Validated(Insert.class) WebInputProfileTypeIndicator webInputProfileTypeIndicator,
            BindingResult bindingResult
    ) {
        try {
            ProfileTypeIndicator profileTypeIndicator = WebInputProfileTypeIndicator.toStackBean(webInputProfileTypeIndicator);
            ProfileTypeIndicatorKey insert = service.insert(profileTypeIndicator);
            return FastJsonResponseData.of(ResponseDataUtil.good(FastJsonProfileTypeIndicatorKey.of(insert)));
        } catch (Exception e) {
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @PatchMapping("/profile-type-indicator")
    @BehaviorAnalyse
    @BindingCheck
    @LoginRequired
    public FastJsonResponseData<Object> update(
            HttpServletRequest request,
            @RequestBody @Validated WebInputProfileTypeIndicator webInputProfileTypeIndicator,
            BindingResult bindingResult
    ) {
        try {
            service.update(WebInputProfileTypeIndicator.toStackBean(webInputProfileTypeIndicator));
            return FastJsonResponseData.of(ResponseDataUtil.good(null));
        } catch (Exception e) {
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @DeleteMapping("/profile-type-indicator/{category}&{id}")
    @BehaviorAnalyse
    @LoginRequired
    public FastJsonResponseData<Object> delete(
            HttpServletRequest request, @PathVariable("category") String category, @PathVariable("id") String id
    ) {
        try {
            service.delete(new ProfileTypeIndicatorKey(category, id));
            return FastJsonResponseData.of(ResponseDataUtil.good(null));
        } catch (Exception e) {
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @GetMapping("/profile-type-indicator/child-for-category")
    @BehaviorAnalyse
    @LoginRequired
    public FastJsonResponseData<JSFixedFastJsonPagedData<FastJsonProfileTypeIndicator>> childForCategory(
            HttpServletRequest request,
            @RequestParam("category") String category, @RequestParam("page") int page, @RequestParam("rows") int rows
    ) {
        try {
            PagedData<ProfileTypeIndicator> childForCategory = service.childForCategory(
                    category, new PagingInfo(page, rows)
            );
            PagedData<FastJsonProfileTypeIndicator> transform = PagingUtil.transform(childForCategory, beanTransformer);
            return FastJsonResponseData.of(ResponseDataUtil.good(JSFixedFastJsonPagedData.of(transform)));
        } catch (Exception e) {
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }
}
