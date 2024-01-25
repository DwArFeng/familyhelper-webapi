package com.dwarfeng.familyhelper.webapi.node.controller.v1.project;

import com.dwarfeng.familyhelper.project.sdk.bean.dto.WebInputTaskCreateInfo;
import com.dwarfeng.familyhelper.project.sdk.bean.dto.WebInputTaskUpdateInfo;
import com.dwarfeng.familyhelper.project.sdk.bean.entity.JSFixedFastJsonTask;
import com.dwarfeng.familyhelper.project.stack.bean.entity.Task;
import com.dwarfeng.familyhelper.webapi.sdk.bean.disp.project.JSFixedFastJsonDispTask;
import com.dwarfeng.familyhelper.webapi.stack.bean.disp.project.DispTask;
import com.dwarfeng.familyhelper.webapi.stack.handler.system.TokenHandler;
import com.dwarfeng.familyhelper.webapi.stack.service.project.TaskResponseService;
import com.dwarfeng.subgrade.sdk.bean.dto.FastJsonResponseData;
import com.dwarfeng.subgrade.sdk.bean.dto.JSFixedFastJsonPagedData;
import com.dwarfeng.subgrade.sdk.bean.dto.PagingUtil;
import com.dwarfeng.subgrade.sdk.bean.dto.ResponseDataUtil;
import com.dwarfeng.subgrade.sdk.bean.key.JSFixedFastJsonLongIdKey;
import com.dwarfeng.subgrade.sdk.bean.key.WebInputLongIdKey;
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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

/**
 * 任务控制器。
 *
 * @author DwArFeng
 * @since 1.0.3
 */
@RestController
@RequestMapping("/api/v1/project")
public class TaskController {

    private static final Logger LOGGER = LoggerFactory.getLogger(TaskController.class);

    private final TaskResponseService service;

    private final ServiceExceptionMapper sem;

    private final BeanTransformer<Task, JSFixedFastJsonTask> beanTransformer;
    private final BeanTransformer<DispTask, JSFixedFastJsonDispTask> dispBeanTransformer;

    private final TokenHandler tokenHandler;

    public TaskController(
            TaskResponseService service, ServiceExceptionMapper sem,
            BeanTransformer<Task, JSFixedFastJsonTask> beanTransformer,
            BeanTransformer<DispTask, JSFixedFastJsonDispTask> dispBeanTransformer,
            TokenHandler tokenHandler
    ) {
        this.service = service;
        this.sem = sem;
        this.beanTransformer = beanTransformer;
        this.dispBeanTransformer = dispBeanTransformer;
        this.tokenHandler = tokenHandler;
    }

    @GetMapping("/task/{id}/exists")
    @BehaviorAnalyse
    @LoginRequired
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
    public FastJsonResponseData<JSFixedFastJsonPagedData<JSFixedFastJsonTask>> all(
            HttpServletRequest request, @RequestParam("page") int page, @RequestParam("rows") int rows) {
        try {
            PagedData<Task> all = service.all(new PagingInfo(page, rows));
            PagedData<JSFixedFastJsonTask> transform = PagingUtil.transform(all, beanTransformer);
            return FastJsonResponseData.of(ResponseDataUtil.good(JSFixedFastJsonPagedData.of(transform)));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @GetMapping(value = {"/project/{projectId}/task", "/project//task"})
    @BehaviorAnalyse
    @SkipRecord
    @LoginRequired
    public FastJsonResponseData<JSFixedFastJsonPagedData<JSFixedFastJsonTask>> childForProject(
            HttpServletRequest request,
            @PathVariable(required = false, value = "projectId") Long projectId,
            @RequestParam("page") int page, @RequestParam("rows") int rows
    ) {
        try {
            LongIdKey projectKey = null;
            if (Objects.nonNull(projectId)) {
                projectKey = new LongIdKey(projectId);
            }
            PagedData<Task> childForProject = service.childForProject(projectKey, new PagingInfo(page, rows));
            PagedData<JSFixedFastJsonTask> transform = PagingUtil.transform(
                    childForProject, beanTransformer);
            return FastJsonResponseData.of(ResponseDataUtil.good(JSFixedFastJsonPagedData.of(transform)));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @GetMapping(value = {"/task/{taskId}/pre-task", "/task//pre-task"})
    @BehaviorAnalyse
    @SkipRecord
    @LoginRequired
    public FastJsonResponseData<JSFixedFastJsonPagedData<JSFixedFastJsonTask>> childForPreTask(
            HttpServletRequest request,
            @PathVariable(required = false, value = "taskId") Long taskId,
            @RequestParam("page") int page, @RequestParam("rows") int rows
    ) {
        try {
            LongIdKey taskKey = null;
            if (Objects.nonNull(taskId)) {
                taskKey = new LongIdKey(taskId);
            }
            PagedData<Task> childForPreTask = service.childForPreTask(taskKey, new PagingInfo(page, rows));
            PagedData<JSFixedFastJsonTask> transform = PagingUtil.transform(
                    childForPreTask, beanTransformer);
            return FastJsonResponseData.of(ResponseDataUtil.good(JSFixedFastJsonPagedData.of(transform)));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @GetMapping(value = {"/task/{taskId}/post-task", "/task//post-task"})
    @BehaviorAnalyse
    @SkipRecord
    @LoginRequired
    public FastJsonResponseData<JSFixedFastJsonPagedData<JSFixedFastJsonTask>> childForPostTask(
            HttpServletRequest request,
            @PathVariable(required = false, value = "taskId") Long taskId,
            @RequestParam("page") int page, @RequestParam("rows") int rows
    ) {
        try {
            LongIdKey taskKey = null;
            if (Objects.nonNull(taskId)) {
                taskKey = new LongIdKey(taskId);
            }
            PagedData<Task> childForPostTask = service.childForPostTask(taskKey, new PagingInfo(page, rows));
            PagedData<JSFixedFastJsonTask> transform = PagingUtil.transform(
                    childForPostTask, beanTransformer);
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
    public FastJsonResponseData<JSFixedFastJsonDispTask> getDisp(
            HttpServletRequest request, @PathVariable("id") Long id
    ) {
        try {
            StringIdKey inspectAccountKey = tokenHandler.getAccountKey(request);
            DispTask dispTask = service.getDisp(new LongIdKey(id), inspectAccountKey);
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
    public FastJsonResponseData<JSFixedFastJsonPagedData<JSFixedFastJsonDispTask>> allDisp(
            HttpServletRequest request, @RequestParam("page") int page, @RequestParam("rows") int rows) {
        try {
            StringIdKey accountKey = tokenHandler.getAccountKey(request);
            PagedData<DispTask> allDisp = service.allDisp(accountKey, new PagingInfo(page, rows));
            PagedData<JSFixedFastJsonDispTask> transform = PagingUtil.transform(
                    allDisp, dispBeanTransformer);
            return FastJsonResponseData.of(ResponseDataUtil.good(JSFixedFastJsonPagedData.of(transform)));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @GetMapping(value = {"/project/{projectId}/task/disp", "/project//task/disp"})
    @BehaviorAnalyse
    @SkipRecord
    @LoginRequired
    public FastJsonResponseData<JSFixedFastJsonPagedData<JSFixedFastJsonDispTask>> childForProjectDisp(
            HttpServletRequest request,
            @PathVariable(required = false, value = "projectId") Long projectId,
            @RequestParam("page") int page, @RequestParam("rows") int rows
    ) {
        try {
            StringIdKey accountKey = tokenHandler.getAccountKey(request);
            LongIdKey projectKey = null;
            if (Objects.nonNull(projectId)) {
                projectKey = new LongIdKey(projectId);
            }
            PagedData<DispTask> childForProjectDisp = service.childForProjectDisp(
                    accountKey, projectKey, new PagingInfo(page, rows)
            );
            PagedData<JSFixedFastJsonDispTask> transform = PagingUtil.transform(childForProjectDisp, dispBeanTransformer);
            return FastJsonResponseData.of(ResponseDataUtil.good(JSFixedFastJsonPagedData.of(transform)));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @GetMapping(value = {"/task/{taskId}/pre-task/disp", "/task//pre-task/disp"})
    @BehaviorAnalyse
    @SkipRecord
    @LoginRequired
    public FastJsonResponseData<JSFixedFastJsonPagedData<JSFixedFastJsonDispTask>> childForPreTaskDisp(
            HttpServletRequest request,
            @PathVariable(required = false, value = "taskId") Long taskId,
            @RequestParam("page") int page, @RequestParam("rows") int rows
    ) {
        try {
            StringIdKey accountKey = tokenHandler.getAccountKey(request);
            LongIdKey taskKey = null;
            if (Objects.nonNull(taskId)) {
                taskKey = new LongIdKey(taskId);
            }
            PagedData<DispTask> childForPreTask = service.childForPreTaskDisp(
                    accountKey, taskKey, new PagingInfo(page, rows)
            );
            PagedData<JSFixedFastJsonDispTask> transform = PagingUtil.transform(childForPreTask, dispBeanTransformer);
            return FastJsonResponseData.of(ResponseDataUtil.good(JSFixedFastJsonPagedData.of(transform)));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @GetMapping(value = {"/task/{taskId}/post-task/disp", "/task//post-task/disp"})
    @BehaviorAnalyse
    @SkipRecord
    @LoginRequired
    public FastJsonResponseData<JSFixedFastJsonPagedData<JSFixedFastJsonDispTask>> childForPostTaskDisp(
            HttpServletRequest request,
            @PathVariable(required = false, value = "taskId") Long taskId,
            @RequestParam("page") int page, @RequestParam("rows") int rows
    ) {
        try {
            StringIdKey accountKey = tokenHandler.getAccountKey(request);
            LongIdKey taskKey = null;
            if (Objects.nonNull(taskId)) {
                taskKey = new LongIdKey(taskId);
            }
            PagedData<DispTask> childForPostTask = service.childForPostTaskDisp(
                    accountKey, taskKey, new PagingInfo(page, rows)
            );
            PagedData<JSFixedFastJsonDispTask> transform = PagingUtil.transform(childForPostTask, dispBeanTransformer);
            return FastJsonResponseData.of(ResponseDataUtil.good(JSFixedFastJsonPagedData.of(transform)));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @PostMapping("/task/create")
    @BehaviorAnalyse
    @BindingCheck
    public FastJsonResponseData<JSFixedFastJsonLongIdKey> createTask(
            HttpServletRequest request,
            @RequestBody @Validated WebInputTaskCreateInfo taskCreateInfo, BindingResult bindingResult
    ) {
        try {
            StringIdKey accountKey = tokenHandler.getAccountKey(request);
            LongIdKey result = service.createTask(
                    accountKey, WebInputTaskCreateInfo.toStackBean(taskCreateInfo)
            );
            return FastJsonResponseData.of(ResponseDataUtil.good(JSFixedFastJsonLongIdKey.of(result)));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @PostMapping("/task/update")
    @BehaviorAnalyse
    @BindingCheck
    public FastJsonResponseData<Object> updateTask(
            HttpServletRequest request,
            @RequestBody @Validated WebInputTaskUpdateInfo webInputTaskUpdateInfo, BindingResult bindingResult
    ) {
        try {
            StringIdKey accountKey = tokenHandler.getAccountKey(request);
            service.updateTask(
                    accountKey, WebInputTaskUpdateInfo.toStackBean(webInputTaskUpdateInfo)
            );
            return FastJsonResponseData.of(ResponseDataUtil.good(null));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @PostMapping("/task/remove")
    @BehaviorAnalyse
    @BindingCheck
    public FastJsonResponseData<Object> removeTask(
            HttpServletRequest request,
            @RequestBody @Validated WebInputLongIdKey taskKey, BindingResult bindingResult
    ) {
        try {
            StringIdKey accountKey = tokenHandler.getAccountKey(request);
            service.removeTask(accountKey, WebInputLongIdKey.toStackBean(taskKey));
            return FastJsonResponseData.of(ResponseDataUtil.good(null));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }
}
