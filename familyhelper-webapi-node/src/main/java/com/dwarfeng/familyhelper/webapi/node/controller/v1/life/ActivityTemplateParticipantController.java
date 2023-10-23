package com.dwarfeng.familyhelper.webapi.node.controller.v1.life;

import com.dwarfeng.familyhelper.life.sdk.bean.dto.WebInputActivityTemplateParticipantCreateInfo;
import com.dwarfeng.familyhelper.life.sdk.bean.dto.WebInputActivityTemplateParticipantRemoveInfo;
import com.dwarfeng.familyhelper.life.sdk.bean.dto.WebInputActivityTemplateParticipantUpdateInfo;
import com.dwarfeng.familyhelper.life.sdk.bean.entity.JSFixedFastJsonActivityTemplateParticipant;
import com.dwarfeng.familyhelper.life.sdk.bean.key.JSFixedFastJsonActivityTemplateParticipantKey;
import com.dwarfeng.familyhelper.life.stack.bean.entity.ActivityTemplateParticipant;
import com.dwarfeng.familyhelper.life.stack.bean.key.ActivityTemplateParticipantKey;
import com.dwarfeng.familyhelper.webapi.sdk.bean.disp.life.JSFixedFastJsonDispActivityTemplateParticipant;
import com.dwarfeng.familyhelper.webapi.stack.bean.disp.life.DispActivityTemplateParticipant;
import com.dwarfeng.familyhelper.webapi.stack.handler.system.TokenHandler;
import com.dwarfeng.familyhelper.webapi.stack.service.life.ActivityTemplateParticipantResponseService;
import com.dwarfeng.subgrade.sdk.bean.dto.FastJsonResponseData;
import com.dwarfeng.subgrade.sdk.bean.dto.JSFixedFastJsonPagedData;
import com.dwarfeng.subgrade.sdk.bean.dto.PagingUtil;
import com.dwarfeng.subgrade.sdk.bean.dto.ResponseDataUtil;
import com.dwarfeng.subgrade.sdk.interceptor.analyse.BehaviorAnalyse;
import com.dwarfeng.subgrade.sdk.interceptor.analyse.SkipRecord;
import com.dwarfeng.subgrade.sdk.interceptor.http.BindingCheck;
import com.dwarfeng.subgrade.sdk.interceptor.login.LoginRequired;
import com.dwarfeng.subgrade.stack.bean.BeanTransformer;
import com.dwarfeng.subgrade.stack.bean.dto.PagedData;
import com.dwarfeng.subgrade.stack.bean.dto.PagingInfo;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.bean.key.StringIdKey;
import com.dwarfeng.subgrade.stack.exception.ServiceExceptionMapper;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * 活动模板参与者控制器。
 *
 * @author DwArFeng
 * @since 1.0.10
 */
@RestController
@RequestMapping("/api/v1/life")
public class ActivityTemplateParticipantController {

    private final ActivityTemplateParticipantResponseService service;

    private final ServiceExceptionMapper sem;

    private final BeanTransformer<ActivityTemplateParticipant, JSFixedFastJsonActivityTemplateParticipant>
            beanTransformer;
    private final BeanTransformer<DispActivityTemplateParticipant, JSFixedFastJsonDispActivityTemplateParticipant>
            dispBeanTransformer;

    private final TokenHandler tokenHandler;

    public ActivityTemplateParticipantController(
            ActivityTemplateParticipantResponseService service,
            ServiceExceptionMapper sem,
            BeanTransformer<ActivityTemplateParticipant, JSFixedFastJsonActivityTemplateParticipant>
                    beanTransformer,
            BeanTransformer<DispActivityTemplateParticipant, JSFixedFastJsonDispActivityTemplateParticipant>
                    dispBeanTransformer,
            TokenHandler tokenHandler
    ) {
        this.service = service;
        this.sem = sem;
        this.beanTransformer = beanTransformer;
        this.dispBeanTransformer = dispBeanTransformer;
        this.tokenHandler = tokenHandler;
    }

    @GetMapping("/activity-template-participant/{longId}&{stringId}/exists")
    @BehaviorAnalyse
    @LoginRequired
    public FastJsonResponseData<Boolean> exists(
            HttpServletRequest request, @PathVariable("longId") Long longId, @PathVariable("stringId") String stringId
    ) {
        try {
            boolean exists = service.exists(new ActivityTemplateParticipantKey(longId, stringId));
            return FastJsonResponseData.of(ResponseDataUtil.good(exists));
        } catch (Exception e) {
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @GetMapping("/activity-template-participant/{longId}&{stringId}")
    @BehaviorAnalyse
    @LoginRequired
    public FastJsonResponseData<JSFixedFastJsonActivityTemplateParticipant> get(
            HttpServletRequest request, @PathVariable("longId") Long longId, @PathVariable("stringId") String stringId
    ) {
        try {
            ActivityTemplateParticipant activityTemplateParticipant = service.get(
                    new ActivityTemplateParticipantKey(longId, stringId)
            );
            return FastJsonResponseData.of(ResponseDataUtil.good(
                    JSFixedFastJsonActivityTemplateParticipant.of(activityTemplateParticipant)
            ));
        } catch (Exception e) {
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @GetMapping("/activity-template-participant/{longId}&{stringId}/disp")
    @BehaviorAnalyse
    @LoginRequired
    public FastJsonResponseData<JSFixedFastJsonDispActivityTemplateParticipant> getDisp(
            HttpServletRequest request, @PathVariable("longId") Long longId, @PathVariable("stringId") String stringId
    ) {
        try {
            StringIdKey inspectAccountKey = tokenHandler.getAccountKey(request);
            DispActivityTemplateParticipant dispActivityTemplateParticipant = service.getDisp(
                    new ActivityTemplateParticipantKey(longId, stringId), inspectAccountKey
            );
            return FastJsonResponseData.of(ResponseDataUtil.good(
                    JSFixedFastJsonDispActivityTemplateParticipant.of(dispActivityTemplateParticipant)
            ));
        } catch (Exception e) {
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @GetMapping("/activity-template/{activityTemplateId}/activity-template-participant")
    @BehaviorAnalyse
    @SkipRecord
    @LoginRequired
    public FastJsonResponseData<JSFixedFastJsonPagedData<JSFixedFastJsonActivityTemplateParticipant>>
    childForActivityTemplate(
            HttpServletRequest request, @PathVariable("activityTemplateId") Long activityTemplateId,
            @RequestParam("page") int page, @RequestParam("rows") int rows
    ) {
        try {
            PagedData<ActivityTemplateParticipant> childForActivityTemplate = service.childForActivityTemplate(
                    new LongIdKey(activityTemplateId), new PagingInfo(page, rows)
            );
            PagedData<JSFixedFastJsonActivityTemplateParticipant> transform = PagingUtil.transform(
                    childForActivityTemplate, beanTransformer
            );
            return FastJsonResponseData.of(ResponseDataUtil.good(JSFixedFastJsonPagedData.of(transform)));
        } catch (Exception e) {
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @GetMapping("/activity-template/{activityTemplateId}/activity-template-participant/disp")
    @BehaviorAnalyse
    @SkipRecord
    @LoginRequired
    public FastJsonResponseData<JSFixedFastJsonPagedData<JSFixedFastJsonDispActivityTemplateParticipant>>
    childForActivityTemplateDisp(
            HttpServletRequest request, @PathVariable("activityTemplateId") Long activityTemplateId,
            @RequestParam("page") int page, @RequestParam("rows") int rows
    ) {
        try {
            StringIdKey inspectAccountKey = tokenHandler.getAccountKey(request);
            PagedData<DispActivityTemplateParticipant> childForActivityTemplateDisp = service.childForActivityTemplateDisp(
                    inspectAccountKey, new LongIdKey(activityTemplateId), new PagingInfo(page, rows)
            );
            PagedData<JSFixedFastJsonDispActivityTemplateParticipant> transform = PagingUtil.transform(
                    childForActivityTemplateDisp, dispBeanTransformer
            );
            return FastJsonResponseData.of(ResponseDataUtil.good(JSFixedFastJsonPagedData.of(transform)));
        } catch (Exception e) {
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @PostMapping("/activity-template-participant/create")
    @BehaviorAnalyse
    @BindingCheck
    @LoginRequired
    public FastJsonResponseData<JSFixedFastJsonActivityTemplateParticipantKey> create(
            HttpServletRequest request,
            @RequestBody @Validated WebInputActivityTemplateParticipantCreateInfo createInfo,
            BindingResult bindingResult
    ) {
        try {
            StringIdKey accountKey = tokenHandler.getAccountKey(request);
            ActivityTemplateParticipantKey result = service.create(
                    accountKey,
                    WebInputActivityTemplateParticipantCreateInfo.toStackBean(createInfo)
            );
            return FastJsonResponseData.of(ResponseDataUtil.good(
                    JSFixedFastJsonActivityTemplateParticipantKey.of(result)
            ));
        } catch (Exception e) {
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @PostMapping("/activity-template-participant/update")
    @BehaviorAnalyse
    @BindingCheck
    @LoginRequired
    public FastJsonResponseData<Object> update(
            HttpServletRequest request,
            @RequestBody @Validated WebInputActivityTemplateParticipantUpdateInfo updateInfo,
            BindingResult bindingResult
    ) {
        try {
            StringIdKey accountKey = tokenHandler.getAccountKey(request);
            service.update(
                    accountKey,
                    WebInputActivityTemplateParticipantUpdateInfo.toStackBean(updateInfo)
            );
            return FastJsonResponseData.of(ResponseDataUtil.good(null));
        } catch (Exception e) {
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @PostMapping("/activity-template-participant/remove")
    @BehaviorAnalyse
    @BindingCheck
    @LoginRequired
    public FastJsonResponseData<Object> removeActivityTemplateParticipant(
            HttpServletRequest request,
            @RequestBody @Validated WebInputActivityTemplateParticipantRemoveInfo removeInfo,
            BindingResult bindingResult
    ) {
        try {
            StringIdKey accountKey = tokenHandler.getAccountKey(request);
            service.remove(accountKey, WebInputActivityTemplateParticipantRemoveInfo.toStackBean(removeInfo));
            return FastJsonResponseData.of(ResponseDataUtil.good(null));
        } catch (Exception e) {
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }
}
