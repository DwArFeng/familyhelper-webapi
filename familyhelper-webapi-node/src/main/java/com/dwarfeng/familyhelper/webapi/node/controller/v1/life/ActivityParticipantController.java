package com.dwarfeng.familyhelper.webapi.node.controller.v1.life;

import com.dwarfeng.familyhelper.life.sdk.bean.dto.WebInputActivityParticipantCreateInfo;
import com.dwarfeng.familyhelper.life.sdk.bean.dto.WebInputActivityParticipantRemoveInfo;
import com.dwarfeng.familyhelper.life.sdk.bean.dto.WebInputActivityParticipantUpdateInfo;
import com.dwarfeng.familyhelper.life.sdk.bean.entity.JSFixedFastJsonActivityParticipant;
import com.dwarfeng.familyhelper.life.sdk.bean.key.JSFixedFastJsonActivityParticipantKey;
import com.dwarfeng.familyhelper.life.stack.bean.entity.ActivityParticipant;
import com.dwarfeng.familyhelper.life.stack.bean.key.ActivityParticipantKey;
import com.dwarfeng.familyhelper.webapi.sdk.bean.life.disp.JSFixedFastJsonDispActivityParticipant;
import com.dwarfeng.familyhelper.webapi.stack.bean.life.disp.DispActivityParticipant;
import com.dwarfeng.familyhelper.webapi.stack.handler.system.TokenHandler;
import com.dwarfeng.familyhelper.webapi.stack.service.life.ActivityParticipantResponseService;
import com.dwarfeng.subgrade.sdk.bean.dto.FastJsonResponseData;
import com.dwarfeng.subgrade.sdk.bean.dto.JSFixedFastJsonPagedData;
import com.dwarfeng.subgrade.sdk.bean.dto.PagingUtil;
import com.dwarfeng.subgrade.sdk.bean.dto.ResponseDataUtil;
import com.dwarfeng.subgrade.sdk.interceptor.analyse.BehaviorAnalyse;
import com.dwarfeng.subgrade.sdk.interceptor.analyse.SkipRecord;
import com.dwarfeng.subgrade.sdk.interceptor.http.BindingCheck;
import com.dwarfeng.subgrade.sdk.interceptor.login.LoginRequired;
import com.dwarfeng.subgrade.sdk.interceptor.permission.PermissionRequired;
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
 * 活动参与者控制器。
 *
 * @author DwArFeng
 * @since 1.0.10
 */
@RestController
@RequestMapping("/api/v1/life")
public class ActivityParticipantController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ActivityParticipantController.class);

    private final ActivityParticipantResponseService service;

    private final ServiceExceptionMapper sem;

    private final BeanTransformer<ActivityParticipant, JSFixedFastJsonActivityParticipant>
            beanTransformer;
    private final BeanTransformer<DispActivityParticipant, JSFixedFastJsonDispActivityParticipant>
            dispBeanTransformer;

    private final TokenHandler tokenHandler;

    public ActivityParticipantController(
            ActivityParticipantResponseService service,
            ServiceExceptionMapper sem,
            BeanTransformer<ActivityParticipant, JSFixedFastJsonActivityParticipant> beanTransformer,
            BeanTransformer<DispActivityParticipant, JSFixedFastJsonDispActivityParticipant> dispBeanTransformer,
            TokenHandler tokenHandler
    ) {
        this.service = service;
        this.sem = sem;
        this.beanTransformer = beanTransformer;
        this.dispBeanTransformer = dispBeanTransformer;
        this.tokenHandler = tokenHandler;
    }

    @GetMapping("/activity-participant/{longId}&{stringId}/exists")
    @BehaviorAnalyse
    @LoginRequired
    @PermissionRequired("webapi.controller_permitted.life.activity_participant_controller.exists")
    public FastJsonResponseData<Boolean> exists(
            HttpServletRequest request, @PathVariable("longId") Long longId, @PathVariable("stringId") String stringId
    ) {
        try {
            boolean exists = service.exists(new ActivityParticipantKey(longId, stringId));
            return FastJsonResponseData.of(ResponseDataUtil.good(exists));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @GetMapping("/activity-participant/{longId}&{stringId}")
    @BehaviorAnalyse
    @LoginRequired
    @PermissionRequired("webapi.controller_permitted.life.activity_participant_controller.get")
    public FastJsonResponseData<JSFixedFastJsonActivityParticipant> get(
            HttpServletRequest request, @PathVariable("longId") Long longId, @PathVariable("stringId") String stringId
    ) {
        try {
            ActivityParticipant activityParticipant = service.get(
                    new ActivityParticipantKey(longId, stringId)
            );
            return FastJsonResponseData.of(ResponseDataUtil.good(
                    JSFixedFastJsonActivityParticipant.of(activityParticipant)
            ));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @GetMapping("/activity-participant/{longId}&{stringId}/disp")
    @BehaviorAnalyse
    @LoginRequired
    @PermissionRequired("webapi.controller_permitted.life.activity_participant_controller.get_disp")
    public FastJsonResponseData<JSFixedFastJsonDispActivityParticipant> getDisp(
            HttpServletRequest request, @PathVariable("longId") Long longId, @PathVariable("stringId") String stringId
    ) {
        try {
            StringIdKey inspectAccountKey = tokenHandler.getUserKey(request);
            DispActivityParticipant dispActivityParticipant = service.getDisp(
                    new ActivityParticipantKey(longId, stringId), inspectAccountKey
            );
            return FastJsonResponseData.of(ResponseDataUtil.good(
                    JSFixedFastJsonDispActivityParticipant.of(dispActivityParticipant)
            ));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @GetMapping("/activity/{activityId}/activity-participant")
    @BehaviorAnalyse
    @SkipRecord
    @LoginRequired
    @PermissionRequired("webapi.controller_permitted.life.activity_participant_controller.child_for_activity")
    public FastJsonResponseData<JSFixedFastJsonPagedData<JSFixedFastJsonActivityParticipant>>
    childForActivity(
            HttpServletRequest request, @PathVariable("activityId") Long activityId,
            @RequestParam("page") int page, @RequestParam("rows") int rows
    ) {
        try {
            PagedData<ActivityParticipant> childForActivity = service.childForActivity(
                    new LongIdKey(activityId), new PagingInfo(page, rows)
            );
            PagedData<JSFixedFastJsonActivityParticipant> transform = PagingUtil.transform(
                    childForActivity, beanTransformer
            );
            return FastJsonResponseData.of(ResponseDataUtil.good(JSFixedFastJsonPagedData.of(transform)));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @GetMapping("/activity/{activityId}/activity-participant/disp")
    @BehaviorAnalyse
    @SkipRecord
    @LoginRequired
    @PermissionRequired("webapi.controller_permitted.life.activity_participant_controller.child_for_activity_disp")
    public FastJsonResponseData<JSFixedFastJsonPagedData<JSFixedFastJsonDispActivityParticipant>>
    childForActivityDisp(
            HttpServletRequest request, @PathVariable("activityId") Long activityId,
            @RequestParam("page") int page, @RequestParam("rows") int rows
    ) {
        try {
            StringIdKey inspectAccountKey = tokenHandler.getUserKey(request);
            PagedData<DispActivityParticipant> childForActivityDisp = service.childForActivityDisp(
                    inspectAccountKey, new LongIdKey(activityId), new PagingInfo(page, rows)
            );
            PagedData<JSFixedFastJsonDispActivityParticipant> transform = PagingUtil.transform(
                    childForActivityDisp, dispBeanTransformer
            );
            return FastJsonResponseData.of(ResponseDataUtil.good(JSFixedFastJsonPagedData.of(transform)));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @PostMapping("/activity-participant/create")
    @BehaviorAnalyse
    @BindingCheck
    @LoginRequired
    @PermissionRequired("webapi.controller_permitted.life.activity_participant_controller.create")
    public FastJsonResponseData<JSFixedFastJsonActivityParticipantKey> create(
            HttpServletRequest request,
            @RequestBody @Validated WebInputActivityParticipantCreateInfo createInfo,
            BindingResult bindingResult
    ) {
        try {
            StringIdKey accountKey = tokenHandler.getUserKey(request);
            ActivityParticipantKey result = service.create(
                    accountKey,
                    WebInputActivityParticipantCreateInfo.toStackBean(createInfo)
            );
            return FastJsonResponseData.of(ResponseDataUtil.good(
                    JSFixedFastJsonActivityParticipantKey.of(result)
            ));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @PostMapping("/activity-participant/update")
    @BehaviorAnalyse
    @BindingCheck
    @LoginRequired
    @PermissionRequired("webapi.controller_permitted.life.activity_participant_controller.update")
    public FastJsonResponseData<Object> update(
            HttpServletRequest request,
            @RequestBody @Validated WebInputActivityParticipantUpdateInfo updateInfo,
            BindingResult bindingResult
    ) {
        try {
            StringIdKey accountKey = tokenHandler.getUserKey(request);
            service.update(accountKey, WebInputActivityParticipantUpdateInfo.toStackBean(updateInfo));
            return FastJsonResponseData.of(ResponseDataUtil.good(null));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @PostMapping("/activity-participant/remove")
    @BehaviorAnalyse
    @BindingCheck
    @LoginRequired
    @PermissionRequired("webapi.controller_permitted.life.activity_participant_controller.remove_activity_participant")
    public FastJsonResponseData<Object> removeActivityParticipant(
            HttpServletRequest request,
            @RequestBody @Validated WebInputActivityParticipantRemoveInfo removeInfo,
            BindingResult bindingResult
    ) {
        try {
            StringIdKey accountKey = tokenHandler.getUserKey(request);
            service.remove(accountKey, WebInputActivityParticipantRemoveInfo.toStackBean(removeInfo));
            return FastJsonResponseData.of(ResponseDataUtil.good(null));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }
}
