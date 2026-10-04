package com.dwarfeng.familyhelper.webapi.node.controller.v1.logicengine;

import com.dwarfeng.familyhelper.webapi.sdk.bean.logicengine.disp.JSFixedFastJsonDispTaskVariable;
import com.dwarfeng.familyhelper.webapi.stack.bean.logicengine.disp.DispTaskVariable;
import com.dwarfeng.familyhelper.webapi.stack.service.logicengine.TaskVariableResponseService;
import com.dwarfeng.logicengine.sdk.bean.entity.JSFixedFastJsonTaskVariable;
import com.dwarfeng.logicengine.stack.bean.entity.TaskVariable;
import com.dwarfeng.logicengine.stack.bean.key.TaskVariableKey;
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
 * 任务变量控制器。
 *
 * @author DwArFeng
 * @since 2.4.0
 */
@RestController
@RequestMapping("/api/v1/logicengine")
public class TaskVariableController {

    private static final Logger LOGGER = LoggerFactory.getLogger(TaskVariableController.class);

    private final TaskVariableResponseService service;
    private final ServiceExceptionMapper sem;

    private final BeanTransformer<TaskVariable, JSFixedFastJsonTaskVariable> beanTransformer;
    private final BeanTransformer<DispTaskVariable, JSFixedFastJsonDispTaskVariable> dispBeanTransformer;

    public TaskVariableController(
            TaskVariableResponseService service,
            ServiceExceptionMapper sem,
            BeanTransformer<TaskVariable, JSFixedFastJsonTaskVariable> beanTransformer,
            BeanTransformer<DispTaskVariable, JSFixedFastJsonDispTaskVariable> dispBeanTransformer
    ) {
        this.service = service;
        this.sem = sem;
        this.beanTransformer = beanTransformer;
        this.dispBeanTransformer = dispBeanTransformer;
    }

    @GetMapping("/task-variable/{taskLongId}&{variableStringId}/exists")
    @BehaviorAnalyse
    @LoginRequired
    @PermissionRequired("webapi.controller_permitted.logicengine.task_variable.exists")
    public FastJsonResponseData<Boolean> exists(
            HttpServletRequest request,
            @PathVariable("taskLongId") Long taskLongId, @PathVariable("variableStringId") String variableStringId
    ) {
        try {
            boolean exists = service.exists(new TaskVariableKey(taskLongId, variableStringId));
            return FastJsonResponseData.of(ResponseDataUtil.good(exists));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @GetMapping("/task-variable/{taskLongId}&{variableStringId}")
    @BehaviorAnalyse
    @LoginRequired
    @PermissionRequired("webapi.controller_permitted.logicengine.task_variable.get")
    public FastJsonResponseData<JSFixedFastJsonTaskVariable> get(
            HttpServletRequest request,
            @PathVariable("taskLongId") Long taskLongId, @PathVariable("variableStringId") String variableStringId
    ) {
        try {
            TaskVariable taskVariable = service.get(new TaskVariableKey(taskLongId, variableStringId));
            return FastJsonResponseData.of(ResponseDataUtil.good(JSFixedFastJsonTaskVariable.of(taskVariable)));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @GetMapping("/task-variable/all")
    @BehaviorAnalyse
    @SkipRecord
    @LoginRequired
    @PermissionRequired("webapi.controller_permitted.logicengine.task_variable.all")
    public FastJsonResponseData<JSFixedFastJsonPagedData<JSFixedFastJsonTaskVariable>> all(
            HttpServletRequest request, @RequestParam("page") int page, @RequestParam("rows") int rows
    ) {
        try {
            PagedData<TaskVariable> all = service.all(new PagingInfo(page, rows));
            PagedData<JSFixedFastJsonTaskVariable> transform = PagingUtil.transform(all, beanTransformer);
            return FastJsonResponseData.of(ResponseDataUtil.good(JSFixedFastJsonPagedData.of(transform)));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @GetMapping("/task-variable/child-for-task/{taskId}/disp")
    @BehaviorAnalyse
    @SkipRecord
    @LoginRequired
    @PermissionRequired("webapi.controller_permitted.logicengine.task_variable.child_for_task_disp")
    public FastJsonResponseData<JSFixedFastJsonPagedData<JSFixedFastJsonDispTaskVariable>> childForTaskDisp(
            HttpServletRequest request,
            @PathVariable("taskId") Long taskId,
            @RequestParam("page") int page, @RequestParam("rows") int rows
    ) {
        try {
            PagedData<DispTaskVariable> childForTaskDisp = service.childForTaskDisp(
                    new LongIdKey(taskId), new PagingInfo(page, rows)
            );
            PagedData<JSFixedFastJsonDispTaskVariable> transform = PagingUtil.transform(
                    childForTaskDisp, dispBeanTransformer
            );
            return FastJsonResponseData.of(ResponseDataUtil.good(JSFixedFastJsonPagedData.of(transform)));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @GetMapping("/task-variable/{taskLongId}&{variableStringId}/disp")
    @BehaviorAnalyse
    @SkipRecord
    @LoginRequired
    @PermissionRequired("webapi.controller_permitted.logicengine.task_variable.get_disp")
    public FastJsonResponseData<JSFixedFastJsonDispTaskVariable> getDisp(
            HttpServletRequest request,
            @PathVariable("taskLongId") Long taskLongId, @PathVariable("variableStringId") String variableStringId
    ) {
        try {
            DispTaskVariable dispTaskVariable = service.getDisp(new TaskVariableKey(taskLongId, variableStringId));
            return FastJsonResponseData.of(
                    ResponseDataUtil.good(JSFixedFastJsonDispTaskVariable.of(dispTaskVariable))
            );
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @GetMapping("/task-variable/all/disp")
    @BehaviorAnalyse
    @SkipRecord
    @LoginRequired
    @PermissionRequired("webapi.controller_permitted.logicengine.task_variable.all_disp")
    public FastJsonResponseData<JSFixedFastJsonPagedData<JSFixedFastJsonDispTaskVariable>> allDisp(
            HttpServletRequest request, @RequestParam("page") int page, @RequestParam("rows") int rows
    ) {
        try {
            PagedData<DispTaskVariable> allDisp = service.allDisp(new PagingInfo(page, rows));
            PagedData<JSFixedFastJsonDispTaskVariable> transform = PagingUtil.transform(allDisp, dispBeanTransformer);
            return FastJsonResponseData.of(ResponseDataUtil.good(JSFixedFastJsonPagedData.of(transform)));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @GetMapping("/task-variable/child-for-task/{taskId}")
    @BehaviorAnalyse
    @SkipRecord
    @LoginRequired
    @PermissionRequired("webapi.controller_permitted.logicengine.task_variable.child_for_task")
    public FastJsonResponseData<JSFixedFastJsonPagedData<JSFixedFastJsonTaskVariable>> childForTask(
            HttpServletRequest request,
            @PathVariable("taskId") Long taskId,
            @RequestParam("page") int page, @RequestParam("rows") int rows
    ) {
        try {
            PagedData<TaskVariable> childForTask = service.childForTask(
                    new LongIdKey(taskId), new PagingInfo(page, rows)
            );
            PagedData<JSFixedFastJsonTaskVariable> transform = PagingUtil.transform(childForTask, beanTransformer);
            return FastJsonResponseData.of(ResponseDataUtil.good(JSFixedFastJsonPagedData.of(transform)));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }
}
