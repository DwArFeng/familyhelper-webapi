package com.dwarfeng.familyhelper.webapi.node.controller.v1.notify;

import com.dwarfeng.familyhelper.webapi.stack.service.notify.MetaIndicatorResponseService;
import com.dwarfeng.notify.sdk.bean.entity.FastJsonMetaIndicator;
import com.dwarfeng.notify.sdk.bean.entity.WebInputMetaIndicator;
import com.dwarfeng.notify.sdk.bean.key.FastJsonMetaIndicatorKey;
import com.dwarfeng.notify.stack.bean.entity.MetaIndicator;
import com.dwarfeng.notify.stack.bean.key.MetaIndicatorKey;
import com.dwarfeng.subgrade.sdk.bean.dto.FastJsonResponseData;
import com.dwarfeng.subgrade.sdk.bean.dto.JSFixedFastJsonPagedData;
import com.dwarfeng.subgrade.sdk.bean.dto.PagingUtil;
import com.dwarfeng.subgrade.sdk.bean.dto.ResponseDataUtil;
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
import java.util.List;
import java.util.stream.Collectors;

/**
 * 元数据指示器控制器。
 *
 * @author DwArFeng
 * @since 1.0.7
 */
@RestController("notifyMetaIndicatorController")
@RequestMapping("/api/v1/notify")
public class MetaIndicatorController {

    private static final Logger LOGGER = LoggerFactory.getLogger(MetaIndicatorController.class);

    private final MetaIndicatorResponseService service;
    private final ServiceExceptionMapper sem;

    private final BeanTransformer<MetaIndicator, FastJsonMetaIndicator> beanTransformer;

    public MetaIndicatorController(
            MetaIndicatorResponseService service, ServiceExceptionMapper sem,
            BeanTransformer<MetaIndicator, FastJsonMetaIndicator> beanTransformer
    ) {
        this.service = service;
        this.sem = sem;
        this.beanTransformer = beanTransformer;
    }

    @GetMapping("/meta-indicator/{topicId}&{metaId}/exists")
    @BehaviorAnalyse
    @LoginRequired
    public FastJsonResponseData<Boolean> exists(
            HttpServletRequest request,
            @PathVariable("topicId") String topicId, @PathVariable("metaId") String metaId
    ) {
        try {
            boolean exists = service.exists(new MetaIndicatorKey(topicId, metaId));
            return FastJsonResponseData.of(ResponseDataUtil.good(exists));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @GetMapping("/meta-indicator/{topicId}&{metaId}")
    @BehaviorAnalyse
    @LoginRequired
    public FastJsonResponseData<FastJsonMetaIndicator> get(
            HttpServletRequest request,
            @PathVariable("topicId") String topicId, @PathVariable("metaId") String metaId
    ) {
        try {
            MetaIndicator metaIndicator = service.get(new MetaIndicatorKey(topicId, metaId));
            return FastJsonResponseData.of(ResponseDataUtil.good(
                    FastJsonMetaIndicator.of(metaIndicator)
            ));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @PostMapping("/meta-indicator")
    @BehaviorAnalyse
    @BindingCheck
    @LoginRequired
    public FastJsonResponseData<FastJsonMetaIndicatorKey> insert(
            HttpServletRequest request,
            @RequestBody @Validated(Insert.class) WebInputMetaIndicator webInputMetaIndicator,
            BindingResult bindingResult
    ) {
        try {
            MetaIndicator metaIndicator = WebInputMetaIndicator.toStackBean(
                    webInputMetaIndicator
            );
            MetaIndicatorKey insert = service.insert(metaIndicator);
            return FastJsonResponseData.of(ResponseDataUtil.good(FastJsonMetaIndicatorKey.of(insert)));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @PatchMapping("/meta-indicator")
    @BehaviorAnalyse
    @BindingCheck
    @LoginRequired
    public FastJsonResponseData<Object> update(
            HttpServletRequest request,
            @RequestBody @Validated WebInputMetaIndicator webInputMetaIndicator,
            BindingResult bindingResult
    ) {
        try {
            service.update(WebInputMetaIndicator.toStackBean(webInputMetaIndicator));
            return FastJsonResponseData.of(ResponseDataUtil.good(null));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @DeleteMapping("/meta-indicator/{topicId}&{metaId}")
    @BehaviorAnalyse
    @LoginRequired
    public FastJsonResponseData<Object> delete(
            HttpServletRequest request,
            @PathVariable("topicId") String topicId, @PathVariable("metaId") String metaId
    ) {
        try {
            service.delete(new MetaIndicatorKey(topicId, metaId));
            return FastJsonResponseData.of(ResponseDataUtil.good(null));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @GetMapping("/meta-indicator/all")
    @BehaviorAnalyse
    @SkipRecord
    @LoginRequired
    public FastJsonResponseData<JSFixedFastJsonPagedData<FastJsonMetaIndicator>> all(
            HttpServletRequest request, @RequestParam("page") int page, @RequestParam("rows") int rows
    ) {
        try {
            PagedData<MetaIndicator> all = service.all(new PagingInfo(page, rows));
            PagedData<FastJsonMetaIndicator> transform = PagingUtil.transform(all, beanTransformer);
            return FastJsonResponseData.of(ResponseDataUtil.good(JSFixedFastJsonPagedData.of(transform)));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @GetMapping("/topic/{topicId}/meta-indicator")
    @BehaviorAnalyse
    @SkipRecord
    @LoginRequired
    public FastJsonResponseData<JSFixedFastJsonPagedData<FastJsonMetaIndicator>> childForTopic(
            HttpServletRequest request,
            @PathVariable("topicId") String topicId,
            @RequestParam("page") int page, @RequestParam("rows") int rows
    ) {
        try {
            PagedData<MetaIndicator> typeEquals = service.childForTopic(
                    new StringIdKey(topicId), new PagingInfo(page, rows)
            );
            PagedData<FastJsonMetaIndicator> transform = PagingUtil.transform(typeEquals, beanTransformer);
            return FastJsonResponseData.of(ResponseDataUtil.good(JSFixedFastJsonPagedData.of(transform)));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @GetMapping("/meta-indicator/meta-missing")
    @BehaviorAnalyse
    @SkipRecord
    @LoginRequired
    public FastJsonResponseData<List<FastJsonMetaIndicator>> metaMissing(
            HttpServletRequest request,
            @RequestParam("notify-setting-id") long notifySettingId, @RequestParam("topic-id") String topicId,
            @RequestParam("user-id") String userId
    ) {
        try {
            List<MetaIndicator> lookup = service.metaMissing(
                    new LongIdKey(notifySettingId), new StringIdKey(topicId), new StringIdKey(userId)
            );
            List<FastJsonMetaIndicator> transform = lookup.stream()
                    .map(FastJsonMetaIndicator::of).collect(Collectors.toList());
            return FastJsonResponseData.of(ResponseDataUtil.good(transform));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }
}
