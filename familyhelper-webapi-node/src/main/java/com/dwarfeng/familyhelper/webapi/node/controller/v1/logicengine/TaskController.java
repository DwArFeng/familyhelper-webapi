package com.dwarfeng.familyhelper.webapi.node.controller.v1.logicengine;

import com.dwarfeng.familyhelper.webapi.sdk.bean.logicengine.disp.JSFixedFastJsonDispTask;
import com.dwarfeng.familyhelper.webapi.stack.bean.logicengine.disp.DispTask;
import com.dwarfeng.familyhelper.webapi.stack.service.logicengine.TaskResponseService;
import com.dwarfeng.logicengine.sdk.bean.entity.JSFixedFastJsonTask;
import com.dwarfeng.logicengine.stack.bean.entity.Task;
import com.dwarfeng.logicengine.stack.bean.key.StateKey;
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
 * 任务控制器。
 *
 * @author DwArFeng
 * @since 2.4.0
 */
@RestController("logicengineTaskController")
@RequestMapping("/api/v1/logicengine")
public class TaskController {

    private static final Logger LOGGER = LoggerFactory.getLogger(TaskController.class);

    private final TaskResponseService service;
    private final ServiceExceptionMapper sem;

    private final BeanTransformer<Task, JSFixedFastJsonTask> beanTransformer;
    private final BeanTransformer<DispTask, JSFixedFastJsonDispTask> dispBeanTransformer;

    public TaskController(
            TaskResponseService service,
            ServiceExceptionMapper sem,
            BeanTransformer<Task, JSFixedFastJsonTask> beanTransformer,
            BeanTransformer<DispTask, JSFixedFastJsonDispTask> dispBeanTransformer
    ) {
        this.service = service;
        this.sem = sem;
        this.beanTransformer = beanTransformer;
        this.dispBeanTransformer = dispBeanTransformer;
    }

    @GetMapping("/task/{id}/exists")
    @BehaviorAnalyse
    @LoginRequired
    @PermissionRequired("webapi.controller_permitted.logicengine.task.exists")
    public FastJsonResponseData<Boolean> exists(HttpServletRequest request, @PathVariable("id") Long id) {
        try {
            boolean exists = service.exists(new LongIdKey(id));
            return FastJsonResponseData.of(ResponseDataUtil.good(exists));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @GetMapping("/task/{id}")
    @BehaviorAnalyse
    @LoginRequired
    @PermissionRequired("webapi.controller_permitted.logicengine.task.get")
    public FastJsonResponseData<JSFixedFastJsonTask> get(HttpServletRequest request, @PathVariable("id") Long id) {
        try {
            Task task = service.get(new LongIdKey(id));
            return FastJsonResponseData.of(ResponseDataUtil.good(JSFixedFastJsonTask.of(task)));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @GetMapping("/task/all")
    @BehaviorAnalyse
    @SkipRecord
    @LoginRequired
    @PermissionRequired("webapi.controller_permitted.logicengine.task.all")
    public FastJsonResponseData<JSFixedFastJsonPagedData<JSFixedFastJsonTask>> all(
            HttpServletRequest request, @RequestParam("page") int page, @RequestParam("rows") int rows
    ) {
        try {
            PagedData<Task> all = service.all(new PagingInfo(page, rows));
            PagedData<JSFixedFastJsonTask> transform = PagingUtil.transform(all, beanTransformer);
            return FastJsonResponseData.of(ResponseDataUtil.good(JSFixedFastJsonPagedData.of(transform)));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @GetMapping("/task/child-for-section/{sectionId}/disp")
    @BehaviorAnalyse
    @SkipRecord
    @LoginRequired
    @PermissionRequired("webapi.controller_permitted.logicengine.task.child_for_section_disp")
    public FastJsonResponseData<JSFixedFastJsonPagedData<JSFixedFastJsonDispTask>> childForSectionDisp(
            HttpServletRequest request,
            @PathVariable("sectionId") Long sectionId,
            @RequestParam("page") int page, @RequestParam("rows") int rows
    ) {
        try {
            PagedData<DispTask> childForSectionDisp = service.childForSectionDisp(
                    new LongIdKey(sectionId), new PagingInfo(page, rows)
            );
            PagedData<JSFixedFastJsonDispTask> transform = PagingUtil.transform(
                    childForSectionDisp, dispBeanTransformer
            );
            return FastJsonResponseData.of(ResponseDataUtil.good(JSFixedFastJsonPagedData.of(transform)));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @GetMapping("/task/child-for-current-state/{sectionLongId}&{stateId}/disp")
    @BehaviorAnalyse
    @SkipRecord
    @LoginRequired
    @PermissionRequired("webapi.controller_permitted.logicengine.task.child_for_current_state_disp")
    public FastJsonResponseData<JSFixedFastJsonPagedData<JSFixedFastJsonDispTask>> childForCurrentStateDisp(
            HttpServletRequest request,
            @PathVariable("sectionLongId") Long sectionLongId, @PathVariable("stateId") String stateId,
            @RequestParam("page") int page, @RequestParam("rows") int rows
    ) {
        try {
            PagedData<DispTask> childForCurrentStateDisp = service.childForCurrentStateDisp(
                    new StateKey(sectionLongId, stateId), new PagingInfo(page, rows)
            );
            PagedData<JSFixedFastJsonDispTask> transform = PagingUtil.transform(
                    childForCurrentStateDisp, dispBeanTransformer
            );
            return FastJsonResponseData.of(ResponseDataUtil.good(JSFixedFastJsonPagedData.of(transform)));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @GetMapping("/task/{id}/disp")
    @BehaviorAnalyse
    @SkipRecord
    @LoginRequired
    @PermissionRequired("webapi.controller_permitted.logicengine.task.get_disp")
    public FastJsonResponseData<JSFixedFastJsonDispTask> getDisp(
            HttpServletRequest request, @PathVariable("id") Long id
    ) {
        try {
            DispTask dispTask = service.getDisp(new LongIdKey(id));
            return FastJsonResponseData.of(ResponseDataUtil.good(JSFixedFastJsonDispTask.of(dispTask)));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @GetMapping("/task/all/disp")
    @BehaviorAnalyse
    @SkipRecord
    @LoginRequired
    @PermissionRequired("webapi.controller_permitted.logicengine.task.all_disp")
    public FastJsonResponseData<JSFixedFastJsonPagedData<JSFixedFastJsonDispTask>> allDisp(
            HttpServletRequest request, @RequestParam("page") int page, @RequestParam("rows") int rows
    ) {
        try {
            PagedData<DispTask> allDisp = service.allDisp(new PagingInfo(page, rows));
            PagedData<JSFixedFastJsonDispTask> transform = PagingUtil.transform(allDisp, dispBeanTransformer);
            return FastJsonResponseData.of(ResponseDataUtil.good(JSFixedFastJsonPagedData.of(transform)));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @GetMapping("/task/child-for-section/{sectionId}")
    @BehaviorAnalyse
    @SkipRecord
    @LoginRequired
    @PermissionRequired("webapi.controller_permitted.logicengine.task.child_for_section")
    public FastJsonResponseData<JSFixedFastJsonPagedData<JSFixedFastJsonTask>> childForSection(
            HttpServletRequest request,
            @PathVariable("sectionId") Long sectionId,
            @RequestParam("page") int page, @RequestParam("rows") int rows
    ) {
        try {
            PagedData<Task> childForSection = service.childForSection(
                    new LongIdKey(sectionId), new PagingInfo(page, rows)
            );
            PagedData<JSFixedFastJsonTask> transform = PagingUtil.transform(childForSection, beanTransformer);
            return FastJsonResponseData.of(ResponseDataUtil.good(JSFixedFastJsonPagedData.of(transform)));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @GetMapping("/task/child-for-current-state/{sectionLongId}&{stateId}")
    @BehaviorAnalyse
    @SkipRecord
    @LoginRequired
    @PermissionRequired("webapi.controller_permitted.logicengine.task.child_for_current_state")
    public FastJsonResponseData<JSFixedFastJsonPagedData<JSFixedFastJsonTask>> childForCurrentState(
            HttpServletRequest request,
            @PathVariable("sectionLongId") Long sectionLongId, @PathVariable("stateId") String stateId,
            @RequestParam("page") int page, @RequestParam("rows") int rows
    ) {
        try {
            PagedData<Task> childForCurrentState = service.childForCurrentState(
                    new StateKey(sectionLongId, stateId), new PagingInfo(page, rows)
            );
            PagedData<JSFixedFastJsonTask> transform = PagingUtil.transform(childForCurrentState, beanTransformer);
            return FastJsonResponseData.of(ResponseDataUtil.good(JSFixedFastJsonPagedData.of(transform)));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }
}
