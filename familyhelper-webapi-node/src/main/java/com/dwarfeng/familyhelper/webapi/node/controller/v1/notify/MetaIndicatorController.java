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
import com.dwarfeng.subgrade.sdk.interceptor.audit.AuditInvocationContribute;
import com.dwarfeng.subgrade.sdk.interceptor.audit.AuditParamContribute;
import com.dwarfeng.subgrade.sdk.interceptor.audit.AuditRecord;
import com.dwarfeng.subgrade.sdk.interceptor.audit.AuditResultContribute;
import com.dwarfeng.subgrade.sdk.interceptor.http.BindingCheck;
import com.dwarfeng.subgrade.sdk.interceptor.login.LoginRequired;
import com.dwarfeng.subgrade.sdk.interceptor.permission.PermissionRequired;
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
    @PermissionRequired("webapi.controller_permitted.notify.meta_indicator.exists")
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
    @PermissionRequired("webapi.controller_permitted.notify.meta_indicator.get")
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
    @PermissionRequired("webapi.controller_permitted.notify.meta_indicator.insert")
    @AuditRecord("webapi.notify.meta_indicator.insert")
    @AuditInvocationContribute(contributor = "system.methodContributor", prefix = "method.")
    @AuditResultContribute(contributor = "notify.metaIndicatorInsertResultContributor", prefix = "result.")
    public FastJsonResponseData<FastJsonMetaIndicatorKey> insert(
            @AuditParamContribute(contributor = "system.requestRequestContributor", prefix = "request.request.")
            @AuditParamContribute(contributor = "system.requestTokenContributor", prefix = "request.token.")
            HttpServletRequest request,
            @RequestBody @Validated(Insert.class)
            @AuditParamContribute(
                    contributor = "notify.metaIndicatorInsertMetaIndicatorParamContributor",
                    prefix = "param.meta_indicator."
            )
            WebInputMetaIndicator metaIndicator,
            BindingResult bindingResult
    ) {
        try {
            MetaIndicatorKey insert = service.insert(WebInputMetaIndicator.toStackBean(metaIndicator));
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
    @PermissionRequired("webapi.controller_permitted.notify.meta_indicator.update")
    @AuditRecord("webapi.notify.meta_indicator.update")
    @AuditInvocationContribute(contributor = "system.methodContributor", prefix = "method.")
    @AuditResultContribute(contributor = "notify.metaIndicatorUpdateResultContributor", prefix = "result.")
    public FastJsonResponseData<Object> update(
            @AuditParamContribute(contributor = "system.requestRequestContributor", prefix = "request.request.")
            @AuditParamContribute(contributor = "system.requestTokenContributor", prefix = "request.token.")
            HttpServletRequest request,
            @RequestBody @Validated
            @AuditParamContribute(
                    contributor = "notify.metaIndicatorUpdateMetaIndicatorParamContributor",
                    prefix = "param.meta_indicator."
            )
            WebInputMetaIndicator metaIndicator,
            BindingResult bindingResult
    ) {
        try {
            service.update(WebInputMetaIndicator.toStackBean(metaIndicator));
            return FastJsonResponseData.of(ResponseDataUtil.good(null));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @DeleteMapping("/meta-indicator/{topicId}&{metaId}")
    @BehaviorAnalyse
    @LoginRequired
    @PermissionRequired("webapi.controller_permitted.notify.meta_indicator.delete")
    @AuditRecord("webapi.notify.meta_indicator.delete")
    @AuditInvocationContribute(contributor = "system.methodContributor", prefix = "method.")
    @AuditResultContribute(contributor = "notify.metaIndicatorDeleteResultContributor", prefix = "result.")
    public FastJsonResponseData<Object> delete(
            @AuditParamContribute(contributor = "system.requestRequestContributor", prefix = "request.request.")
            @AuditParamContribute(contributor = "system.requestTokenContributor", prefix = "request.token.")
            HttpServletRequest request,
            @PathVariable("topicId")
            @AuditParamContribute(
                    contributor = "notify.metaIndicatorDeleteTopicIdParamContributor",
                    prefix = "param.topic_id."
            )
            String topicId,
            @PathVariable("metaId")
            @AuditParamContribute(
                    contributor = "notify.metaIndicatorDeleteMetaIdParamContributor",
                    prefix = "param.meta_id."
            )
            String metaId
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
    @PermissionRequired("webapi.controller_permitted.notify.meta_indicator.all")
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
    @PermissionRequired("webapi.controller_permitted.notify.meta_indicator.child_for_topic")
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
    @PermissionRequired("webapi.controller_permitted.notify.meta_indicator.meta_missing")
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
