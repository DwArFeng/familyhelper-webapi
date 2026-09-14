package com.dwarfeng.familyhelper.webapi.node.controller.v1.audit;

import com.dwarfeng.audit.sdk.bean.entity.JSFixedFastJsonInspectionTask;
import com.dwarfeng.audit.stack.bean.entity.InspectionTask;
import com.dwarfeng.familyhelper.webapi.sdk.bean.audit.disp.JSFixedFastJsonDispInspectionTask;
import com.dwarfeng.familyhelper.webapi.stack.bean.audit.disp.DispInspectionTask;
import com.dwarfeng.familyhelper.webapi.stack.service.audit.InspectionTaskResponseService;
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
 * 自动审计任务控制器。
 *
 * @author DwArFeng
 * @since 2.2.0
 */
@RestController
@RequestMapping("/api/v1/audit")
public class InspectionTaskController {

    private static final Logger LOGGER = LoggerFactory.getLogger(InspectionTaskController.class);

    private final InspectionTaskResponseService service;
    private final ServiceExceptionMapper sem;

    private final BeanTransformer<InspectionTask, JSFixedFastJsonInspectionTask> beanTransformer;
    private final BeanTransformer<DispInspectionTask, JSFixedFastJsonDispInspectionTask> dispBeanTransformer;

    public InspectionTaskController(
            InspectionTaskResponseService service,
            ServiceExceptionMapper sem,
            BeanTransformer<InspectionTask, JSFixedFastJsonInspectionTask> beanTransformer,
            BeanTransformer<DispInspectionTask, JSFixedFastJsonDispInspectionTask> dispBeanTransformer
    ) {
        this.service = service;
        this.sem = sem;
        this.beanTransformer = beanTransformer;
        this.dispBeanTransformer = dispBeanTransformer;
    }

    @GetMapping("/inspection-task/{id}/exists")
    @BehaviorAnalyse
    @LoginRequired
    @PermissionRequired("webapi.controller_permitted.audit.inspection_task.exists")
    public FastJsonResponseData<Boolean> exists(HttpServletRequest request, @PathVariable("id") Long id) {
        try {
            boolean exists = service.exists(new LongIdKey(id));
            return FastJsonResponseData.of(ResponseDataUtil.good(exists));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @GetMapping("/inspection-task/{id}")
    @BehaviorAnalyse
    @LoginRequired
    @PermissionRequired("webapi.controller_permitted.audit.inspection_task.get")
    public FastJsonResponseData<JSFixedFastJsonInspectionTask> get(
            HttpServletRequest request, @PathVariable("id") Long id
    ) {
        try {
            InspectionTask inspectionTask = service.get(new LongIdKey(id));
            return FastJsonResponseData.of(
                    ResponseDataUtil.good(JSFixedFastJsonInspectionTask.of(inspectionTask))
            );
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @GetMapping("/inspection-task/all")
    @BehaviorAnalyse
    @SkipRecord
    @LoginRequired
    @PermissionRequired("webapi.controller_permitted.audit.inspection_task.all")
    public FastJsonResponseData<JSFixedFastJsonPagedData<JSFixedFastJsonInspectionTask>> all(
            HttpServletRequest request, @RequestParam("page") int page, @RequestParam("rows") int rows
    ) {
        try {
            PagedData<InspectionTask> all = service.all(new PagingInfo(page, rows));
            PagedData<JSFixedFastJsonInspectionTask> transform = PagingUtil.transform(all, beanTransformer);
            return FastJsonResponseData.of(ResponseDataUtil.good(JSFixedFastJsonPagedData.of(transform)));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @GetMapping("/inspection-task/child-for-inspection/{inspectionId}")
    @BehaviorAnalyse
    @SkipRecord
    @LoginRequired
    @PermissionRequired("webapi.controller_permitted.audit.inspection_task.child_for_inspection")
    public FastJsonResponseData<JSFixedFastJsonPagedData<JSFixedFastJsonInspectionTask>> childForInspection(
            HttpServletRequest request,
            @PathVariable("inspectionId") Long inspectionId,
            @RequestParam("page") int page, @RequestParam("rows") int rows
    ) {
        try {
            PagedData<InspectionTask> childForInspection = service.childForInspection(
                    new LongIdKey(inspectionId), new PagingInfo(page, rows)
            );
            PagedData<JSFixedFastJsonInspectionTask> transform = PagingUtil.transform(childForInspection, beanTransformer);
            return FastJsonResponseData.of(ResponseDataUtil.good(JSFixedFastJsonPagedData.of(transform)));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @GetMapping("/inspection-task/{id}/disp")
    @BehaviorAnalyse
    @SkipRecord
    @LoginRequired
    @PermissionRequired("webapi.controller_permitted.audit.inspection_task.get_disp")
    public FastJsonResponseData<JSFixedFastJsonDispInspectionTask> getDisp(
            HttpServletRequest request, @PathVariable("id") Long id
    ) {
        try {
            DispInspectionTask dispInspectionTask = service.getDisp(new LongIdKey(id));
            return FastJsonResponseData.of(
                    ResponseDataUtil.good(JSFixedFastJsonDispInspectionTask.of(dispInspectionTask))
            );
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @GetMapping("/inspection-task/all/disp")
    @BehaviorAnalyse
    @SkipRecord
    @LoginRequired
    @PermissionRequired("webapi.controller_permitted.audit.inspection_task.all_disp")
    public FastJsonResponseData<JSFixedFastJsonPagedData<JSFixedFastJsonDispInspectionTask>> allDisp(
            HttpServletRequest request, @RequestParam("page") int page, @RequestParam("rows") int rows
    ) {
        try {
            PagedData<DispInspectionTask> allDisp = service.allDisp(new PagingInfo(page, rows));
            PagedData<JSFixedFastJsonDispInspectionTask> transform = PagingUtil.transform(
                    allDisp, dispBeanTransformer
            );
            return FastJsonResponseData.of(ResponseDataUtil.good(JSFixedFastJsonPagedData.of(transform)));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @GetMapping("/inspection-task/child-for-inspection/{inspectionId}/disp")
    @BehaviorAnalyse
    @SkipRecord
    @LoginRequired
    @PermissionRequired("webapi.controller_permitted.audit.inspection_task.child_for_inspection_disp")
    public FastJsonResponseData<JSFixedFastJsonPagedData<JSFixedFastJsonDispInspectionTask>>
    childForInspectionDisp(
            HttpServletRequest request,
            @PathVariable("inspectionId") Long inspectionId,
            @RequestParam("page") int page, @RequestParam("rows") int rows
    ) {
        try {
            PagedData<DispInspectionTask> childForInspectionDisp = service.childForInspectionDisp(
                    new LongIdKey(inspectionId), new PagingInfo(page, rows)
            );
            PagedData<JSFixedFastJsonDispInspectionTask> transform = PagingUtil.transform(
                    childForInspectionDisp, dispBeanTransformer
            );
            return FastJsonResponseData.of(ResponseDataUtil.good(JSFixedFastJsonPagedData.of(transform)));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }
}
