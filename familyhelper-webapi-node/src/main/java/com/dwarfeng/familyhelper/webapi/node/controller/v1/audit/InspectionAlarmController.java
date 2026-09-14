package com.dwarfeng.familyhelper.webapi.node.controller.v1.audit;

import com.dwarfeng.audit.sdk.bean.entity.JSFixedFastJsonInspectionAlarm;
import com.dwarfeng.audit.stack.bean.entity.InspectionAlarm;
import com.dwarfeng.familyhelper.webapi.sdk.bean.audit.disp.JSFixedFastJsonDispInspectionAlarm;
import com.dwarfeng.familyhelper.webapi.stack.bean.audit.disp.DispInspectionAlarm;
import com.dwarfeng.familyhelper.webapi.stack.service.audit.InspectionAlarmResponseService;
import com.dwarfeng.subgrade.sdk.bean.dto.FastJsonResponseData;
import com.dwarfeng.subgrade.sdk.bean.dto.JSFixedFastJsonPagedData;
import com.dwarfeng.subgrade.sdk.bean.dto.PagingUtil;
import com.dwarfeng.subgrade.sdk.bean.dto.ResponseDataUtil;
import com.dwarfeng.subgrade.sdk.interceptor.analyse.BehaviorAnalyse;
import com.dwarfeng.subgrade.sdk.interceptor.analyse.SkipRecord;
import com.dwarfeng.subgrade.sdk.interceptor.login.LoginRequired;
import com.dwarfeng.subgrade.sdk.interceptor.permission.PermissionRequired;
import com.dwarfeng.subgrade.stack.bean.BeanTransformer;
import com.dwarfeng.subgrade.stack.bean.dto.PagedData;
import com.dwarfeng.subgrade.stack.bean.dto.PagingInfo;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.exception.ServiceExceptionMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * 自动审计告警控制器。
 *
 * @author DwArFeng
 * @since 2.2.0
 */
@RestController
@RequestMapping("/api/v1/audit")
public class InspectionAlarmController {

    private static final Logger LOGGER = LoggerFactory.getLogger(InspectionAlarmController.class);

    private final InspectionAlarmResponseService service;
    private final ServiceExceptionMapper sem;

    private final BeanTransformer<InspectionAlarm, JSFixedFastJsonInspectionAlarm> beanTransformer;
    private final BeanTransformer<DispInspectionAlarm, JSFixedFastJsonDispInspectionAlarm> dispBeanTransformer;

    public InspectionAlarmController(
            InspectionAlarmResponseService service,
            ServiceExceptionMapper sem,
            BeanTransformer<InspectionAlarm, JSFixedFastJsonInspectionAlarm> beanTransformer,
            BeanTransformer<DispInspectionAlarm, JSFixedFastJsonDispInspectionAlarm> dispBeanTransformer
    ) {
        this.service = service;
        this.sem = sem;
        this.beanTransformer = beanTransformer;
        this.dispBeanTransformer = dispBeanTransformer;
    }

    @GetMapping("/inspection-alarm/{id}/exists")
    @BehaviorAnalyse
    @LoginRequired
    @PermissionRequired("webapi.controller_permitted.audit.inspection_alarm.exists")
    public FastJsonResponseData<Boolean> exists(HttpServletRequest request, @PathVariable("id") Long id) {
        try {
            boolean exists = service.exists(new LongIdKey(id));
            return FastJsonResponseData.of(ResponseDataUtil.good(exists));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @GetMapping("/inspection-alarm/{id}")
    @BehaviorAnalyse
    @LoginRequired
    @PermissionRequired("webapi.controller_permitted.audit.inspection_alarm.get")
    public FastJsonResponseData<JSFixedFastJsonInspectionAlarm> get(
            HttpServletRequest request, @PathVariable("id") Long id
    ) {
        try {
            InspectionAlarm inspectionAlarm = service.get(new LongIdKey(id));
            return FastJsonResponseData.of(
                    ResponseDataUtil.good(JSFixedFastJsonInspectionAlarm.of(inspectionAlarm))
            );
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @GetMapping("/inspection-alarm/all")
    @BehaviorAnalyse
    @SkipRecord
    @LoginRequired
    @PermissionRequired("webapi.controller_permitted.audit.inspection_alarm.all")
    public FastJsonResponseData<JSFixedFastJsonPagedData<JSFixedFastJsonInspectionAlarm>> all(
            HttpServletRequest request, @RequestParam("page") int page, @RequestParam("rows") int rows
    ) {
        try {
            PagedData<InspectionAlarm> all = service.all(new PagingInfo(page, rows));
            PagedData<JSFixedFastJsonInspectionAlarm> transform = PagingUtil.transform(all, beanTransformer);
            return FastJsonResponseData.of(ResponseDataUtil.good(JSFixedFastJsonPagedData.of(transform)));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @GetMapping("/inspection-alarm/child-for-inspection/{inspectionId}")
    @BehaviorAnalyse
    @SkipRecord
    @LoginRequired
    @PermissionRequired("webapi.controller_permitted.audit.inspection_alarm.child_for_inspection")
    public FastJsonResponseData<JSFixedFastJsonPagedData<JSFixedFastJsonInspectionAlarm>> childForInspection(
            HttpServletRequest request,
            @PathVariable("inspectionId") Long inspectionId,
            @RequestParam("page") int page, @RequestParam("rows") int rows
    ) {
        try {
            PagedData<InspectionAlarm> childForInspection = service.childForInspection(
                    new LongIdKey(inspectionId), new PagingInfo(page, rows)
            );
            PagedData<JSFixedFastJsonInspectionAlarm> transform = PagingUtil.transform(childForInspection, beanTransformer);
            return FastJsonResponseData.of(ResponseDataUtil.good(JSFixedFastJsonPagedData.of(transform)));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @GetMapping("/inspection-alarm/child-for-inspection-task/{inspectionTaskId}")
    @BehaviorAnalyse
    @SkipRecord
    @LoginRequired
    @PermissionRequired("webapi.controller_permitted.audit.inspection_alarm.child_for_inspection_task")
    public FastJsonResponseData<JSFixedFastJsonPagedData<JSFixedFastJsonInspectionAlarm>> childForInspectionTask(
            HttpServletRequest request,
            @PathVariable("inspectionTaskId") Long inspectionTaskId,
            @RequestParam("page") int page, @RequestParam("rows") int rows
    ) {
        try {
            PagedData<InspectionAlarm> childForInspectionTask = service.childForInspectionTask(
                    new LongIdKey(inspectionTaskId), new PagingInfo(page, rows)
            );
            PagedData<JSFixedFastJsonInspectionAlarm> transform = PagingUtil.transform(
                    childForInspectionTask, beanTransformer
            );
            return FastJsonResponseData.of(ResponseDataUtil.good(JSFixedFastJsonPagedData.of(transform)));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @GetMapping("/inspection-alarm/child-for-inspector-info/{inspectorInfoId}")
    @BehaviorAnalyse
    @SkipRecord
    @LoginRequired
    @PermissionRequired("webapi.controller_permitted.audit.inspection_alarm.child_for_inspector_info")
    public FastJsonResponseData<JSFixedFastJsonPagedData<JSFixedFastJsonInspectionAlarm>> childForInspectorInfo(
            HttpServletRequest request,
            @PathVariable("inspectorInfoId") Long inspectorInfoId,
            @RequestParam("page") int page, @RequestParam("rows") int rows
    ) {
        try {
            PagedData<InspectionAlarm> childForInspectorInfo = service.childForInspectorInfo(
                    new LongIdKey(inspectorInfoId), new PagingInfo(page, rows)
            );
            PagedData<JSFixedFastJsonInspectionAlarm> transform = PagingUtil.transform(
                    childForInspectorInfo, beanTransformer
            );
            return FastJsonResponseData.of(ResponseDataUtil.good(JSFixedFastJsonPagedData.of(transform)));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @GetMapping("/inspection-alarm/{id}/disp")
    @BehaviorAnalyse
    @SkipRecord
    @LoginRequired
    @PermissionRequired("webapi.controller_permitted.audit.inspection_alarm.get_disp")
    public FastJsonResponseData<JSFixedFastJsonDispInspectionAlarm> getDisp(
            HttpServletRequest request, @PathVariable("id") Long id
    ) {
        try {
            DispInspectionAlarm dispInspectionAlarm = service.getDisp(new LongIdKey(id));
            return FastJsonResponseData.of(
                    ResponseDataUtil.good(JSFixedFastJsonDispInspectionAlarm.of(dispInspectionAlarm))
            );
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @GetMapping("/inspection-alarm/all/disp")
    @BehaviorAnalyse
    @SkipRecord
    @LoginRequired
    @PermissionRequired("webapi.controller_permitted.audit.inspection_alarm.all_disp")
    public FastJsonResponseData<JSFixedFastJsonPagedData<JSFixedFastJsonDispInspectionAlarm>> allDisp(
            HttpServletRequest request, @RequestParam("page") int page, @RequestParam("rows") int rows
    ) {
        try {
            PagedData<DispInspectionAlarm> allDisp = service.allDisp(new PagingInfo(page, rows));
            PagedData<JSFixedFastJsonDispInspectionAlarm> transform = PagingUtil.transform(
                    allDisp, dispBeanTransformer
            );
            return FastJsonResponseData.of(ResponseDataUtil.good(JSFixedFastJsonPagedData.of(transform)));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @GetMapping("/inspection-alarm/child-for-inspection/{inspectionId}/disp")
    @BehaviorAnalyse
    @SkipRecord
    @LoginRequired
    @PermissionRequired("webapi.controller_permitted.audit.inspection_alarm.child_for_inspection_disp")
    public FastJsonResponseData<JSFixedFastJsonPagedData<JSFixedFastJsonDispInspectionAlarm>>
    childForInspectionDisp(
            HttpServletRequest request,
            @PathVariable("inspectionId") Long inspectionId,
            @RequestParam("page") int page, @RequestParam("rows") int rows
    ) {
        try {
            PagedData<DispInspectionAlarm> childForInspectionDisp = service.childForInspectionDisp(
                    new LongIdKey(inspectionId), new PagingInfo(page, rows)
            );
            PagedData<JSFixedFastJsonDispInspectionAlarm> transform = PagingUtil.transform(
                    childForInspectionDisp, dispBeanTransformer
            );
            return FastJsonResponseData.of(ResponseDataUtil.good(JSFixedFastJsonPagedData.of(transform)));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @GetMapping("/inspection-alarm/child-for-inspection-task/{inspectionTaskId}/disp")
    @BehaviorAnalyse
    @SkipRecord
    @LoginRequired
    @PermissionRequired("webapi.controller_permitted.audit.inspection_alarm.child_for_inspection_task_disp")
    public FastJsonResponseData<JSFixedFastJsonPagedData<JSFixedFastJsonDispInspectionAlarm>>
    childForInspectionTaskDisp(
            HttpServletRequest request,
            @PathVariable("inspectionTaskId") Long inspectionTaskId,
            @RequestParam("page") int page, @RequestParam("rows") int rows
    ) {
        try {
            PagedData<DispInspectionAlarm> childForInspectionTaskDisp = service.childForInspectionTaskDisp(
                    new LongIdKey(inspectionTaskId), new PagingInfo(page, rows)
            );
            PagedData<JSFixedFastJsonDispInspectionAlarm> transform = PagingUtil.transform(
                    childForInspectionTaskDisp, dispBeanTransformer
            );
            return FastJsonResponseData.of(ResponseDataUtil.good(JSFixedFastJsonPagedData.of(transform)));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @GetMapping("/inspection-alarm/child-for-inspector-info/{inspectorInfoId}/disp")
    @BehaviorAnalyse
    @SkipRecord
    @LoginRequired
    @PermissionRequired("webapi.controller_permitted.audit.inspection_alarm.child_for_inspector_info_disp")
    public FastJsonResponseData<JSFixedFastJsonPagedData<JSFixedFastJsonDispInspectionAlarm>>
    childForInspectorInfoDisp(
            HttpServletRequest request,
            @PathVariable("inspectorInfoId") Long inspectorInfoId,
            @RequestParam("page") int page, @RequestParam("rows") int rows
    ) {
        try {
            PagedData<DispInspectionAlarm> childForInspectorInfoDisp = service.childForInspectorInfoDisp(
                    new LongIdKey(inspectorInfoId), new PagingInfo(page, rows)
            );
            PagedData<JSFixedFastJsonDispInspectionAlarm> transform = PagingUtil.transform(
                    childForInspectorInfoDisp, dispBeanTransformer
            );
            return FastJsonResponseData.of(ResponseDataUtil.good(JSFixedFastJsonPagedData.of(transform)));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }
}
