package com.dwarfeng.familyhelper.webapi.node.controller.v1.notify;

import com.dwarfeng.familyhelper.webapi.sdk.bean.disp.notify.JSFixedFastJsonDispMeta;
import com.dwarfeng.familyhelper.webapi.stack.bean.disp.notify.DispMeta;
import com.dwarfeng.familyhelper.webapi.stack.service.notify.MetaResponseService;
import com.dwarfeng.notify.sdk.bean.entity.JSFixedFastJsonMeta;
import com.dwarfeng.notify.sdk.bean.entity.WebInputMeta;
import com.dwarfeng.notify.sdk.bean.key.JSFixedFastJsonMetaKey;
import com.dwarfeng.notify.stack.bean.entity.Meta;
import com.dwarfeng.notify.stack.bean.key.MetaKey;
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

/**
 * 元数据控制器。
 *
 * @author DwArFeng
 * @since 1.0.7
 */
@RestController("notifyMetaController")
@RequestMapping("/api/v1/notify")
public class MetaController {

    private static final Logger LOGGER = LoggerFactory.getLogger(MetaController.class);

    private final MetaResponseService service;
    private final ServiceExceptionMapper sem;

    private final BeanTransformer<Meta, JSFixedFastJsonMeta> beanTransformer;
    private final BeanTransformer<DispMeta, JSFixedFastJsonDispMeta> dispBeanTransformer;

    public MetaController(
            MetaResponseService service, ServiceExceptionMapper sem,
            BeanTransformer<Meta, JSFixedFastJsonMeta> beanTransformer,
            BeanTransformer<DispMeta, JSFixedFastJsonDispMeta> dispBeanTransformer
    ) {
        this.service = service;
        this.sem = sem;
        this.beanTransformer = beanTransformer;
        this.dispBeanTransformer = dispBeanTransformer;
    }

    @GetMapping("/meta/{notifySettingId}&{topicId}&{userId}&{metaId}/exists")
    @BehaviorAnalyse
    @LoginRequired
    public FastJsonResponseData<Boolean> exists(
            HttpServletRequest request,
            @PathVariable("notifySettingId") long notifySettingId, @PathVariable("topicId") String topicId,
            @PathVariable("userId") String userId, @PathVariable("metaId") String metaId
    ) {
        try {
            boolean exists = service.exists(new MetaKey(notifySettingId, topicId, userId, metaId));
            return FastJsonResponseData.of(ResponseDataUtil.good(exists));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @GetMapping("/meta/{notifySettingId}&{topicId}&{userId}&{metaId}")
    @BehaviorAnalyse
    @LoginRequired
    public FastJsonResponseData<JSFixedFastJsonMeta> get(
            HttpServletRequest request,
            @PathVariable("notifySettingId") long notifySettingId, @PathVariable("topicId") String topicId,
            @PathVariable("userId") String userId, @PathVariable("metaId") String metaId
    ) {
        try {
            Meta meta = service.get(new MetaKey(notifySettingId, topicId, userId, metaId));
            return FastJsonResponseData.of(ResponseDataUtil.good(
                    JSFixedFastJsonMeta.of(meta)
            ));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @PostMapping("/meta")
    @BehaviorAnalyse
    @BindingCheck
    @LoginRequired
    public FastJsonResponseData<JSFixedFastJsonMetaKey> insert(
            HttpServletRequest request,
            @RequestBody @Validated(Insert.class) WebInputMeta webInputMeta,
            BindingResult bindingResult
    ) {
        try {
            Meta meta = WebInputMeta.toStackBean(
                    webInputMeta
            );
            MetaKey insert = service.insert(meta);
            return FastJsonResponseData.of(ResponseDataUtil.good(JSFixedFastJsonMetaKey.of(insert)));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @PatchMapping("/meta")
    @BehaviorAnalyse
    @BindingCheck
    @LoginRequired
    public FastJsonResponseData<Object> update(
            HttpServletRequest request,
            @RequestBody @Validated WebInputMeta webInputMeta,
            BindingResult bindingResult
    ) {
        try {
            service.update(WebInputMeta.toStackBean(webInputMeta));
            return FastJsonResponseData.of(ResponseDataUtil.good(null));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @DeleteMapping("/meta/{notifySettingId}&{topicId}&{userId}&{metaId}")
    @BehaviorAnalyse
    @LoginRequired
    public FastJsonResponseData<Object> delete(
            HttpServletRequest request,
            @PathVariable("notifySettingId") long notifySettingId, @PathVariable("topicId") String topicId,
            @PathVariable("userId") String userId, @PathVariable("metaId") String metaId
    ) {
        try {
            service.delete(new MetaKey(notifySettingId, topicId, userId, metaId));
            return FastJsonResponseData.of(ResponseDataUtil.good(null));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @GetMapping("/meta/all")
    @BehaviorAnalyse
    @SkipRecord
    @LoginRequired
    public FastJsonResponseData<JSFixedFastJsonPagedData<JSFixedFastJsonMeta>> all(
            HttpServletRequest request, @RequestParam("page") int page, @RequestParam("rows") int rows
    ) {
        try {
            PagedData<Meta> all = service.all(new PagingInfo(page, rows));
            PagedData<JSFixedFastJsonMeta> transform = PagingUtil.transform(all, beanTransformer);
            return FastJsonResponseData.of(ResponseDataUtil.good(JSFixedFastJsonPagedData.of(transform)));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @GetMapping("/notify-setting/{notifySettingId}/topic/{topicId}/user/{userId}/meta")
    @BehaviorAnalyse
    @SkipRecord
    @LoginRequired
    public FastJsonResponseData<JSFixedFastJsonPagedData<JSFixedFastJsonMeta>>
    childForNotifySettingTopicUser(
            HttpServletRequest request,
            @PathVariable("notifySettingId") long notifySettingId, @PathVariable("topicId") String topicId,
            @PathVariable("userId") String userId,
            @RequestParam("page") int page, @RequestParam("rows") int rows
    ) {
        try {
            PagedData<Meta> typeEquals = service.childForNotifySettingTopicUser(
                    new LongIdKey(notifySettingId), new StringIdKey(topicId), new StringIdKey(userId),
                    new PagingInfo(page, rows)
            );
            PagedData<JSFixedFastJsonMeta> transform = PagingUtil.transform(typeEquals, beanTransformer);
            return FastJsonResponseData.of(ResponseDataUtil.good(JSFixedFastJsonPagedData.of(transform)));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @GetMapping("/meta/{notifySettingId}&{topicId}&{userId}&{metaId}/disp")
    @BehaviorAnalyse
    @LoginRequired
    public FastJsonResponseData<JSFixedFastJsonDispMeta> getDisp(
            HttpServletRequest request,
            @PathVariable("notifySettingId") long notifySettingId, @PathVariable("topicId") String topicId,
            @PathVariable("userId") String userId, @PathVariable("metaId") String metaId
    ) {
        try {
            DispMeta meta = service.getDisp(
                    new MetaKey(notifySettingId, topicId, userId, metaId)
            );
            return FastJsonResponseData.of(ResponseDataUtil.good(
                    JSFixedFastJsonDispMeta.of(meta)
            ));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @GetMapping("/meta/all/disp")
    @BehaviorAnalyse
    @SkipRecord
    @LoginRequired
    public FastJsonResponseData<JSFixedFastJsonPagedData<JSFixedFastJsonDispMeta>> allDisp(
            HttpServletRequest request, @RequestParam("page") int page, @RequestParam("rows") int rows
    ) {
        try {
            PagedData<DispMeta> all = service.allDisp(new PagingInfo(page, rows));
            PagedData<JSFixedFastJsonDispMeta> transform = PagingUtil.transform(all, dispBeanTransformer);
            return FastJsonResponseData.of(ResponseDataUtil.good(JSFixedFastJsonPagedData.of(transform)));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @GetMapping("/notify-setting/{notifySettingId}/topic/{topicId}/user/{userId}/meta/disp")
    @BehaviorAnalyse
    @SkipRecord
    @LoginRequired
    public FastJsonResponseData<JSFixedFastJsonPagedData<JSFixedFastJsonDispMeta>>
    childForNotifySettingTopicUserDisp(
            HttpServletRequest request,
            @PathVariable("notifySettingId") long notifySettingId, @PathVariable("topicId") String topicId,
            @PathVariable("userId") String userId,
            @RequestParam("page") int page, @RequestParam("rows") int rows
    ) {
        try {
            PagedData<DispMeta> typeEquals = service.childForNotifySettingTopicUserDisp(
                    new LongIdKey(notifySettingId), new StringIdKey(topicId), new StringIdKey(userId),
                    new PagingInfo(page, rows)
            );
            PagedData<JSFixedFastJsonDispMeta> transform = PagingUtil.transform(typeEquals, dispBeanTransformer);
            return FastJsonResponseData.of(ResponseDataUtil.good(JSFixedFastJsonPagedData.of(transform)));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }
}
