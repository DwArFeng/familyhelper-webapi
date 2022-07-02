package com.dwarfeng.familyhelper.webapi.node.controller.v1.notify;

import com.dwarfeng.familyhelper.webapi.sdk.bean.disp.notify.JSFixedFastJsonDispSenderRelation;
import com.dwarfeng.familyhelper.webapi.stack.bean.disp.notify.DispSenderRelation;
import com.dwarfeng.familyhelper.webapi.stack.service.notify.SenderRelationResponseService;
import com.dwarfeng.notify.sdk.bean.entity.JSFixedFastJsonSenderRelation;
import com.dwarfeng.notify.sdk.bean.entity.WebInputSenderRelation;
import com.dwarfeng.notify.sdk.bean.entity.key.FastJsonSenderRelationKey;
import com.dwarfeng.notify.stack.bean.entity.SenderRelation;
import com.dwarfeng.notify.stack.bean.entity.key.SenderRelationKey;
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
import com.dwarfeng.subgrade.stack.exception.ServiceExceptionMapper;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * 发送器关联控制器。
 *
 * @author DwArFeng
 * @since 1.0.6
 */
@RestController
@RequestMapping("/api/v1/notify")
public class SenderRelationController {

    private final SenderRelationResponseService service;
    private final ServiceExceptionMapper sem;

    private final BeanTransformer<SenderRelation, JSFixedFastJsonSenderRelation> beanTransformer;
    private final BeanTransformer<DispSenderRelation, JSFixedFastJsonDispSenderRelation> dispBeanTransformer;

    public SenderRelationController(
            SenderRelationResponseService service, ServiceExceptionMapper sem,
            BeanTransformer<SenderRelation, JSFixedFastJsonSenderRelation> beanTransformer,
            BeanTransformer<DispSenderRelation, JSFixedFastJsonDispSenderRelation> dispBeanTransformer
    ) {
        this.service = service;
        this.sem = sem;
        this.beanTransformer = beanTransformer;
        this.dispBeanTransformer = dispBeanTransformer;
    }

    @GetMapping("/sender-relation/{notifySettingId}&{topicId}/exists")
    @BehaviorAnalyse
    @LoginRequired
    public FastJsonResponseData<Boolean> exists(
            HttpServletRequest request,
            @PathVariable("notifySettingId") long notifySettingId, @PathVariable("topicId") String topicId
    ) {
        try {
            boolean exists = service.exists(new SenderRelationKey(notifySettingId, topicId));
            return FastJsonResponseData.of(ResponseDataUtil.good(exists));
        } catch (Exception e) {
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @GetMapping("/sender-relation/{notifySettingId}&{topicId}")
    @BehaviorAnalyse
    @LoginRequired
    public FastJsonResponseData<JSFixedFastJsonSenderRelation> get(
            HttpServletRequest request,
            @PathVariable("notifySettingId") long notifySettingId, @PathVariable("topicId") String topicId
    ) {
        try {
            SenderRelation senderRelation = service.get(new SenderRelationKey(notifySettingId, topicId));
            return FastJsonResponseData.of(ResponseDataUtil.good(
                    JSFixedFastJsonSenderRelation.of(senderRelation)
            ));
        } catch (Exception e) {
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @PostMapping("/sender-relation")
    @BehaviorAnalyse
    @BindingCheck
    @LoginRequired
    public FastJsonResponseData<FastJsonSenderRelationKey> insert(
            HttpServletRequest request,
            @RequestBody @Validated(Insert.class) WebInputSenderRelation webInputSenderRelation,
            BindingResult bindingResult
    ) {
        try {
            SenderRelation senderRelation = WebInputSenderRelation.toStackBean(
                    webInputSenderRelation
            );
            SenderRelationKey insert = service.insert(senderRelation);
            return FastJsonResponseData.of(ResponseDataUtil.good(FastJsonSenderRelationKey.of(insert)));
        } catch (Exception e) {
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @PatchMapping("/sender-relation")
    @BehaviorAnalyse
    @BindingCheck
    @LoginRequired
    public FastJsonResponseData<Object> update(
            HttpServletRequest request,
            @RequestBody @Validated WebInputSenderRelation webInputSenderRelation,
            BindingResult bindingResult
    ) {
        try {
            service.update(WebInputSenderRelation.toStackBean(webInputSenderRelation));
            return FastJsonResponseData.of(ResponseDataUtil.good(null));
        } catch (Exception e) {
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @DeleteMapping("/sender-relation/{notifySettingId}&{topicId}")
    @BehaviorAnalyse
    @LoginRequired
    public FastJsonResponseData<Object> delete(
            HttpServletRequest request,
            @PathVariable("notifySettingId") long notifySettingId, @PathVariable("topicId") String topicId
    ) {
        try {
            service.delete(new SenderRelationKey(notifySettingId, topicId));
            return FastJsonResponseData.of(ResponseDataUtil.good(null));
        } catch (Exception e) {
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @GetMapping("/sender-relation/all")
    @BehaviorAnalyse
    @SkipRecord
    @LoginRequired
    public FastJsonResponseData<JSFixedFastJsonPagedData<JSFixedFastJsonSenderRelation>> all(
            HttpServletRequest request, @RequestParam("page") int page, @RequestParam("rows") int rows
    ) {
        try {
            PagedData<SenderRelation> all = service.all(new PagingInfo(page, rows));
            PagedData<JSFixedFastJsonSenderRelation> transform = PagingUtil.transform(all, beanTransformer);
            return FastJsonResponseData.of(ResponseDataUtil.good(JSFixedFastJsonPagedData.of(transform)));
        } catch (Exception e) {
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @GetMapping("/sender-relation/{notifySettingId}&{topicId}/disp")
    @BehaviorAnalyse
    @LoginRequired
    public FastJsonResponseData<JSFixedFastJsonDispSenderRelation> getDisp(
            HttpServletRequest request,
            @PathVariable("notifySettingId") long notifySettingId, @PathVariable("topicId") String topicId
    ) {
        try {
            DispSenderRelation dispSenderRelation = service.getDisp(new SenderRelationKey(notifySettingId, topicId));
            return FastJsonResponseData.of(ResponseDataUtil.good(
                    JSFixedFastJsonDispSenderRelation.of(dispSenderRelation)
            ));
        } catch (Exception e) {
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @GetMapping("/sender-relation/all/disp")
    @BehaviorAnalyse
    @SkipRecord
    @LoginRequired
    public FastJsonResponseData<JSFixedFastJsonPagedData<JSFixedFastJsonDispSenderRelation>> allDisp(
            HttpServletRequest request, @RequestParam("page") int page, @RequestParam("rows") int rows
    ) {
        try {
            PagedData<DispSenderRelation> all = service.allDisp(new PagingInfo(page, rows));
            PagedData<JSFixedFastJsonDispSenderRelation> transform = PagingUtil.transform(all, dispBeanTransformer);
            return FastJsonResponseData.of(ResponseDataUtil.good(JSFixedFastJsonPagedData.of(transform)));
        } catch (Exception e) {
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }
}
