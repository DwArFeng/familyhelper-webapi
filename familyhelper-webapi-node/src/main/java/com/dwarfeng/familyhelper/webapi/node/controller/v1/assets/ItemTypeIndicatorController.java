package com.dwarfeng.familyhelper.webapi.node.controller.v1.assets;

import com.dwarfeng.familyhelper.assets.sdk.bean.entity.FastJsonItemTypeIndicator;
import com.dwarfeng.familyhelper.assets.sdk.bean.entity.WebInputItemTypeIndicator;
import com.dwarfeng.familyhelper.assets.stack.bean.entity.ItemTypeIndicator;
import com.dwarfeng.familyhelper.webapi.stack.service.assets.ItemTypeIndicatorResponseService;
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
 * 项目类型指示器控制器。
 *
 * @author DwArFeng
 * @since 1.0.2
 */
@RestController
@RequestMapping("/api/v1/assets")
public class ItemTypeIndicatorController {

    private final ItemTypeIndicatorResponseService service;
    private final ServiceExceptionMapper sem;

    private final BeanTransformer<ItemTypeIndicator, FastJsonItemTypeIndicator> beanTransformer;

    public ItemTypeIndicatorController(
            ItemTypeIndicatorResponseService service, ServiceExceptionMapper sem,
            BeanTransformer<ItemTypeIndicator, FastJsonItemTypeIndicator> beanTransformer
    ) {
        this.service = service;
        this.sem = sem;
        this.beanTransformer = beanTransformer;
    }

    @GetMapping("/item-type-indicator/{id}/exists")
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

    @GetMapping("/item-type-indicator/{id}")
    @BehaviorAnalyse
    @LoginRequired
    public FastJsonResponseData<FastJsonItemTypeIndicator> get(HttpServletRequest request, @PathVariable("id") String id) {
        try {
            ItemTypeIndicator itemTypeIndicator = service.get(new StringIdKey(id));
            return FastJsonResponseData.of(ResponseDataUtil.good(FastJsonItemTypeIndicator.of(itemTypeIndicator)));
        } catch (Exception e) {
            return FastJsonResponseData.of(ResponseDataUtil.bad(FastJsonItemTypeIndicator.class, e, sem));
        }
    }

    @PostMapping("/item-type-indicator")
    @BehaviorAnalyse
    @BindingCheck
    @LoginRequired
    public FastJsonResponseData<FastJsonStringIdKey> insert(
            HttpServletRequest request,
            @RequestBody @Validated(Insert.class) WebInputItemTypeIndicator webInputItemTypeIndicator,
            BindingResult bindingResult
    ) {
        try {
            ItemTypeIndicator itemTypeIndicator = WebInputItemTypeIndicator.toStackBean(webInputItemTypeIndicator);
            StringIdKey insert = service.insert(itemTypeIndicator);
            return FastJsonResponseData.of(ResponseDataUtil.good(FastJsonStringIdKey.of(insert)));
        } catch (Exception e) {
            return FastJsonResponseData.of(ResponseDataUtil.bad(FastJsonStringIdKey.class, e, sem));
        }
    }

    @PatchMapping("/item-type-indicator")
    @BehaviorAnalyse
    @BindingCheck
    @LoginRequired
    public FastJsonResponseData<Object> update(
            HttpServletRequest request,
            @RequestBody @Validated WebInputItemTypeIndicator webInputItemTypeIndicator,
            BindingResult bindingResult
    ) {
        try {
            service.update(WebInputItemTypeIndicator.toStackBean(webInputItemTypeIndicator));
            return FastJsonResponseData.of(ResponseDataUtil.good(null));
        } catch (Exception e) {
            return FastJsonResponseData.of(ResponseDataUtil.bad(Object.class, e, sem));
        }
    }

    @DeleteMapping("/item-type-indicator/{id}")
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

    @GetMapping("/item-type-indicator/all")
    @BehaviorAnalyse
    @SkipRecord
    @LoginRequired
    public FastJsonResponseData<JSFixedFastJsonPagedData<FastJsonItemTypeIndicator>> all(
            HttpServletRequest request, @RequestParam("page") int page, @RequestParam("rows") int rows) {
        try {
            PagedData<ItemTypeIndicator> all = service.all(new PagingInfo(page, rows));
            PagedData<FastJsonItemTypeIndicator> transform = PagingUtil.transform(all, beanTransformer);
            return FastJsonResponseData.of(ResponseDataUtil.good(JSFixedFastJsonPagedData.of(transform)));
        } catch (Exception e) {
            return FastJsonResponseData.of(ResponseDataUtil.bad(JSFixedFastJsonPagedData.class, e, sem));
        }
    }
}
