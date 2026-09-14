package com.dwarfeng.familyhelper.webapi.node.controller.v1.audit;

import com.dwarfeng.audit.sdk.bean.entity.JSFixedFastJsonInspectionTaskEvent;
import com.dwarfeng.audit.stack.bean.entity.InspectionTaskEvent;
import com.dwarfeng.familyhelper.webapi.sdk.bean.audit.disp.JSFixedFastJsonDispInspectionTaskEvent;
import com.dwarfeng.familyhelper.webapi.stack.bean.audit.disp.DispInspectionTaskEvent;
import com.dwarfeng.familyhelper.webapi.stack.service.audit.InspectionTaskEventResponseService;
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
 * 自动审计任务事件控制器。
 *
 * @author DwArFeng
 * @since 2.2.0
 */
@RestController
@RequestMapping("/api/v1/audit")
public class InspectionTaskEventController {

    private static final Logger LOGGER = LoggerFactory.getLogger(InspectionTaskEventController.class);

    private final InspectionTaskEventResponseService service;
    private final ServiceExceptionMapper sem;

    private final BeanTransformer<InspectionTaskEvent, JSFixedFastJsonInspectionTaskEvent> beanTransformer;
    private final BeanTransformer<DispInspectionTaskEvent, JSFixedFastJsonDispInspectionTaskEvent>
            dispBeanTransformer;

    public InspectionTaskEventController(
            InspectionTaskEventResponseService service,
            ServiceExceptionMapper sem,
            BeanTransformer<InspectionTaskEvent, JSFixedFastJsonInspectionTaskEvent> beanTransformer,
            BeanTransformer<DispInspectionTaskEvent, JSFixedFastJsonDispInspectionTaskEvent> dispBeanTransformer
    ) {
        this.service = service;
        this.sem = sem;
        this.beanTransformer = beanTransformer;
        this.dispBeanTransformer = dispBeanTransformer;
    }

    @GetMapping("/inspection-task-event/{id}/exists")
    @BehaviorAnalyse
    @LoginRequired
    @PermissionRequired("webapi.controller_permitted.audit.inspection_task_event.exists")
    public FastJsonResponseData<Boolean> exists(HttpServletRequest request, @PathVariable("id") Long id) {
        try {
            boolean exists = service.exists(new LongIdKey(id));
            return FastJsonResponseData.of(ResponseDataUtil.good(exists));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @GetMapping("/inspection-task-event/{id}")
    @BehaviorAnalyse
    @LoginRequired
    @PermissionRequired("webapi.controller_permitted.audit.inspection_task_event.get")
    public FastJsonResponseData<JSFixedFastJsonInspectionTaskEvent> get(
            HttpServletRequest request, @PathVariable("id") Long id
    ) {
        try {
            InspectionTaskEvent inspectionTaskEvent = service.get(new LongIdKey(id));
            return FastJsonResponseData.of(
                    ResponseDataUtil.good(JSFixedFastJsonInspectionTaskEvent.of(inspectionTaskEvent))
            );
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @GetMapping("/inspection-task-event/all")
    @BehaviorAnalyse
    @SkipRecord
    @LoginRequired
    @PermissionRequired("webapi.controller_permitted.audit.inspection_task_event.all")
    public FastJsonResponseData<JSFixedFastJsonPagedData<JSFixedFastJsonInspectionTaskEvent>> all(
            HttpServletRequest request, @RequestParam("page") int page, @RequestParam("rows") int rows
    ) {
        try {
            PagedData<InspectionTaskEvent> all = service.all(new PagingInfo(page, rows));
            PagedData<JSFixedFastJsonInspectionTaskEvent> transform = PagingUtil.transform(all, beanTransformer);
            return FastJsonResponseData.of(ResponseDataUtil.good(JSFixedFastJsonPagedData.of(transform)));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @GetMapping("/inspection-task-event/child-for-inspection-task/{inspectionTaskId}")
    @BehaviorAnalyse
    @SkipRecord
    @LoginRequired
    @PermissionRequired("webapi.controller_permitted.audit.inspection_task_event.child_for_inspection_task")
    public FastJsonResponseData<JSFixedFastJsonPagedData<JSFixedFastJsonInspectionTaskEvent>> childForInspectionTask(
            HttpServletRequest request,
            @PathVariable("inspectionTaskId") Long inspectionTaskId,
            @RequestParam("page") int page, @RequestParam("rows") int rows
    ) {
        try {
            PagedData<InspectionTaskEvent> childForInspectionTask = service.childForInspectionTask(
                    new LongIdKey(inspectionTaskId), new PagingInfo(page, rows)
            );
            PagedData<JSFixedFastJsonInspectionTaskEvent> transform = PagingUtil.transform(
                    childForInspectionTask, beanTransformer
            );
            return FastJsonResponseData.of(ResponseDataUtil.good(JSFixedFastJsonPagedData.of(transform)));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @GetMapping("/inspection-task-event/{id}/disp")
    @BehaviorAnalyse
    @SkipRecord
    @LoginRequired
    @PermissionRequired("webapi.controller_permitted.audit.inspection_task_event.get_disp")
    public FastJsonResponseData<JSFixedFastJsonDispInspectionTaskEvent> getDisp(
            HttpServletRequest request, @PathVariable("id") Long id
    ) {
        try {
            DispInspectionTaskEvent dispInspectionTaskEvent = service.getDisp(new LongIdKey(id));
            return FastJsonResponseData.of(
                    ResponseDataUtil.good(JSFixedFastJsonDispInspectionTaskEvent.of(dispInspectionTaskEvent))
            );
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @GetMapping("/inspection-task-event/all/disp")
    @BehaviorAnalyse
    @SkipRecord
    @LoginRequired
    @PermissionRequired("webapi.controller_permitted.audit.inspection_task_event.all_disp")
    public FastJsonResponseData<JSFixedFastJsonPagedData<JSFixedFastJsonDispInspectionTaskEvent>> allDisp(
            HttpServletRequest request, @RequestParam("page") int page, @RequestParam("rows") int rows
    ) {
        try {
            PagedData<DispInspectionTaskEvent> allDisp = service.allDisp(new PagingInfo(page, rows));
            PagedData<JSFixedFastJsonDispInspectionTaskEvent> transform = PagingUtil.transform(
                    allDisp, dispBeanTransformer
            );
            return FastJsonResponseData.of(ResponseDataUtil.good(JSFixedFastJsonPagedData.of(transform)));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @GetMapping("/inspection-task-event/child-for-inspection-task/{inspectionTaskId}/disp")
    @BehaviorAnalyse
    @SkipRecord
    @LoginRequired
    @PermissionRequired("webapi.controller_permitted.audit.inspection_task_event.child_for_inspection_task_disp")
    public FastJsonResponseData<JSFixedFastJsonPagedData<JSFixedFastJsonDispInspectionTaskEvent>>
    childForInspectionTaskDisp(
            HttpServletRequest request,
            @PathVariable("inspectionTaskId") Long inspectionTaskId,
            @RequestParam("page") int page, @RequestParam("rows") int rows
    ) {
        try {
            PagedData<DispInspectionTaskEvent> childForInspectionTaskDisp = service.childForInspectionTaskDisp(
                    new LongIdKey(inspectionTaskId), new PagingInfo(page, rows)
            );
            PagedData<JSFixedFastJsonDispInspectionTaskEvent> transform = PagingUtil.transform(
                    childForInspectionTaskDisp, dispBeanTransformer
            );
            return FastJsonResponseData.of(ResponseDataUtil.good(JSFixedFastJsonPagedData.of(transform)));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }
}
