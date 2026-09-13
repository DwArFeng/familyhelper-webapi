package com.dwarfeng.familyhelper.webapi.node.controller.v1.fileio;

import com.dwarfeng.familyhelper.webapi.sdk.bean.fileio.disp.JSFixedFastJsonDispExportTask;
import com.dwarfeng.familyhelper.webapi.stack.bean.fileio.disp.DispExportTask;
import com.dwarfeng.familyhelper.webapi.stack.handler.system.TokenHandler;
import com.dwarfeng.familyhelper.webapi.stack.service.fileio.ExportTaskResponseService;
import com.dwarfeng.fileio.sdk.bean.entity.JSFixedFastJsonExportTask;
import com.dwarfeng.fileio.stack.bean.entity.ExportTask;
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
import com.dwarfeng.subgrade.stack.bean.key.StringIdKey;
import com.dwarfeng.subgrade.stack.exception.ServiceExceptionMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * 导出任务控制器。
 *
 * @author diaocl
 * @author DwArFeng
 * @since 2.1.0
 */
@RestController("fileioExportTaskController")
@RequestMapping("/api/v1/fileio")
public class ExportTaskController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ExportTaskController.class);

    private final ExportTaskResponseService service;
    private final ServiceExceptionMapper sem;
    private final TokenHandler tokenHandler;

    private final BeanTransformer<ExportTask, JSFixedFastJsonExportTask> beanTransformer;
    private final BeanTransformer<DispExportTask, JSFixedFastJsonDispExportTask> dispBeanTransformer;

    public ExportTaskController(
            ExportTaskResponseService service,
            ServiceExceptionMapper sem,
            TokenHandler tokenHandler,
            BeanTransformer<ExportTask, JSFixedFastJsonExportTask> beanTransformer,
            @Qualifier("fileio.dispExportTaskBeanTransformer")
            BeanTransformer<DispExportTask, JSFixedFastJsonDispExportTask> dispBeanTransformer
    ) {
        this.service = service;
        this.sem = sem;
        this.tokenHandler = tokenHandler;
        this.beanTransformer = beanTransformer;
        this.dispBeanTransformer = dispBeanTransformer;
    }

    @GetMapping("/export-task/{id}/exists")
    @BehaviorAnalyse
    @LoginRequired
    @PermissionRequired("webapi.controller_permitted.fileio.export_task.exists")
    public FastJsonResponseData<Boolean> exists(HttpServletRequest request, @PathVariable("id") long id) {
        try {
            boolean exists = service.exists(new LongIdKey(id));
            return FastJsonResponseData.of(ResponseDataUtil.good(exists));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常，信息如下：", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @GetMapping("/export-task/{id}")
    @BehaviorAnalyse
    @LoginRequired
    @PermissionRequired("webapi.controller_permitted.fileio.export_task.get")
    public FastJsonResponseData<JSFixedFastJsonExportTask> get(
            HttpServletRequest request, @PathVariable("id") long id
    ) {
        try {
            ExportTask exportTask = service.get(new LongIdKey(id));
            return FastJsonResponseData.of(ResponseDataUtil.good(JSFixedFastJsonExportTask.of(exportTask)));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常，信息如下：", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @GetMapping("/export-task/all")
    @BehaviorAnalyse
    @SkipRecord
    @LoginRequired
    @PermissionRequired("webapi.controller_permitted.fileio.export_task.all")
    public FastJsonResponseData<JSFixedFastJsonPagedData<JSFixedFastJsonExportTask>> all(
            HttpServletRequest request, @RequestParam("page") int page, @RequestParam("rows") int rows
    ) {
        try {
            PagedData<ExportTask> all = service.all(new PagingInfo(page, rows));
            PagedData<JSFixedFastJsonExportTask> transform = PagingUtil.transform(all, beanTransformer);
            return FastJsonResponseData.of(ResponseDataUtil.good(JSFixedFastJsonPagedData.of(transform)));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常，信息如下：", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @GetMapping("/task-setting/{taskSettingId}/export-task")
    @BehaviorAnalyse
    @SkipRecord
    @LoginRequired
    @PermissionRequired("webapi.controller_permitted.fileio.export_task.child_for_task_setting")
    public FastJsonResponseData<JSFixedFastJsonPagedData<JSFixedFastJsonExportTask>> childForTaskSetting(
            HttpServletRequest request,
            @PathVariable Long taskSettingId,
            @RequestParam("page") int page, @RequestParam("rows") int rows
    ) {
        try {
            PagedData<ExportTask> childForTaskSetting = service.childForTaskSetting(
                    new LongIdKey(taskSettingId), new PagingInfo(page, rows)
            );
            PagedData<JSFixedFastJsonExportTask> transform = PagingUtil.transform(
                    childForTaskSetting, beanTransformer
            );
            return FastJsonResponseData.of(ResponseDataUtil.good(JSFixedFastJsonPagedData.of(transform)));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常，信息如下：", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @GetMapping("/export-task/create-date-desc")
    @BehaviorAnalyse
    @SkipRecord
    @LoginRequired
    @PermissionRequired("webapi.controller_permitted.fileio.export_task.create_date_desc")
    public FastJsonResponseData<JSFixedFastJsonPagedData<JSFixedFastJsonExportTask>> createDateDesc(
            HttpServletRequest request, @RequestParam("page") int page, @RequestParam("rows") int rows
    ) {
        try {
            PagedData<ExportTask> createDateDesc = service.createDateDesc(new PagingInfo(page, rows));
            PagedData<JSFixedFastJsonExportTask> transform = PagingUtil.transform(createDateDesc, beanTransformer);
            return FastJsonResponseData.of(ResponseDataUtil.good(JSFixedFastJsonPagedData.of(transform)));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常，信息如下：", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @GetMapping("/user/{userId}/export-task")
    @BehaviorAnalyse
    @SkipRecord
    @LoginRequired
    @PermissionRequired("webapi.controller_permitted.fileio.export_task.child_for_user")
    public FastJsonResponseData<JSFixedFastJsonPagedData<JSFixedFastJsonExportTask>> childForUser(
            HttpServletRequest request,
            @PathVariable String userId, @RequestParam("page") int page, @RequestParam("rows") int rows
    ) {
        try {
            PagedData<ExportTask> childForUser = service.childForUser(
                    new StringIdKey(userId), new PagingInfo(page, rows)
            );
            PagedData<JSFixedFastJsonExportTask> transform = PagingUtil.transform(childForUser, beanTransformer);
            return FastJsonResponseData.of(ResponseDataUtil.good(JSFixedFastJsonPagedData.of(transform)));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常，信息如下：", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @GetMapping("/me/export-task")
    @BehaviorAnalyse
    @SkipRecord
    @LoginRequired
    @PermissionRequired("webapi.controller_permitted.fileio.export_task.child_for_me")
    public FastJsonResponseData<JSFixedFastJsonPagedData<JSFixedFastJsonExportTask>> childForMe(
            HttpServletRequest request, @RequestParam("page") int page, @RequestParam("rows") int rows
    ) {
        try {
            StringIdKey accountKey = new StringIdKey(tokenHandler.getUserId(request));
            PagedData<ExportTask> childForMe = service.childForUser(accountKey, new PagingInfo(page, rows));
            PagedData<JSFixedFastJsonExportTask> transform = PagingUtil.transform(childForMe, beanTransformer);
            return FastJsonResponseData.of(ResponseDataUtil.good(JSFixedFastJsonPagedData.of(transform)));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常，信息如下：", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @GetMapping("/export-task/{id}/disp")
    @BehaviorAnalyse
    @LoginRequired
    @PermissionRequired("webapi.controller_permitted.fileio.export_task.get_disp")
    public FastJsonResponseData<JSFixedFastJsonDispExportTask> getDisp(
            HttpServletRequest request,
            @PathVariable("id") long id
    ) {
        try {
            StringIdKey inspectAccountKey = new StringIdKey(tokenHandler.getUserId(request));
            DispExportTask disp = service.getDisp(new LongIdKey(id), inspectAccountKey);
            return FastJsonResponseData.of(ResponseDataUtil.good(JSFixedFastJsonDispExportTask.of(disp)));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常，信息如下：", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @GetMapping("/export-task/all/disp")
    @BehaviorAnalyse
    @SkipRecord
    @LoginRequired
    @PermissionRequired("webapi.controller_permitted.fileio.export_task.all_disp")
    public FastJsonResponseData<JSFixedFastJsonPagedData<JSFixedFastJsonDispExportTask>> allDisp(
            HttpServletRequest request, @RequestParam("page") int page, @RequestParam("rows") int rows
    ) {
        try {
            StringIdKey inspectAccountKey = new StringIdKey(tokenHandler.getUserId(request));
            PagedData<DispExportTask> all = service.allDisp(new PagingInfo(page, rows), inspectAccountKey);
            PagedData<JSFixedFastJsonDispExportTask> transform = PagingUtil.transform(all, dispBeanTransformer);
            return FastJsonResponseData.of(ResponseDataUtil.good(JSFixedFastJsonPagedData.of(transform)));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常，信息如下：", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @GetMapping("/task-setting/{taskSettingId}/export-task/disp")
    @BehaviorAnalyse
    @SkipRecord
    @LoginRequired
    @PermissionRequired("webapi.controller_permitted.fileio.export_task.child_for_task_setting_disp")
    public FastJsonResponseData<JSFixedFastJsonPagedData<JSFixedFastJsonDispExportTask>> childForTaskSettingDisp(
            HttpServletRequest request,
            @PathVariable Long taskSettingId,
            @RequestParam("page") int page, @RequestParam("rows") int rows
    ) {
        try {
            StringIdKey inspectAccountKey = new StringIdKey(tokenHandler.getUserId(request));
            PagedData<DispExportTask> child = service.childForTaskSettingDisp(
                    new LongIdKey(taskSettingId), new PagingInfo(page, rows), inspectAccountKey
            );
            PagedData<JSFixedFastJsonDispExportTask> transform = PagingUtil.transform(child, dispBeanTransformer);
            return FastJsonResponseData.of(ResponseDataUtil.good(JSFixedFastJsonPagedData.of(transform)));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常，信息如下：", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @GetMapping("/export-task/create-date-desc/disp")
    @BehaviorAnalyse
    @SkipRecord
    @LoginRequired
    @PermissionRequired("webapi.controller_permitted.fileio.export_task.create_date_desc_disp")
    public FastJsonResponseData<JSFixedFastJsonPagedData<JSFixedFastJsonDispExportTask>> createDateDescDisp(
            HttpServletRequest request, @RequestParam("page") int page, @RequestParam("rows") int rows
    ) {
        try {
            StringIdKey inspectAccountKey = new StringIdKey(tokenHandler.getUserId(request));
            PagedData<DispExportTask> result = service.createDateDescDisp(new PagingInfo(page, rows), inspectAccountKey);
            PagedData<JSFixedFastJsonDispExportTask> transform = PagingUtil.transform(result, dispBeanTransformer);
            return FastJsonResponseData.of(ResponseDataUtil.good(JSFixedFastJsonPagedData.of(transform)));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常，信息如下：", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @GetMapping("/user/{userId}/export-task/disp")
    @BehaviorAnalyse
    @SkipRecord
    @LoginRequired
    @PermissionRequired("webapi.controller_permitted.fileio.export_task.child_for_user_disp")
    public FastJsonResponseData<JSFixedFastJsonPagedData<JSFixedFastJsonDispExportTask>> childForUserDisp(
            HttpServletRequest request,
            @PathVariable String userId, @RequestParam("page") int page, @RequestParam("rows") int rows
    ) {
        try {
            StringIdKey inspectAccountKey = new StringIdKey(tokenHandler.getUserId(request));
            PagedData<DispExportTask> child = service.childForUserDisp(
                    new StringIdKey(userId), new PagingInfo(page, rows), inspectAccountKey
            );
            PagedData<JSFixedFastJsonDispExportTask> transform = PagingUtil.transform(child, dispBeanTransformer);
            return FastJsonResponseData.of(ResponseDataUtil.good(JSFixedFastJsonPagedData.of(transform)));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常，信息如下：", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @GetMapping("/me/export-task/disp")
    @BehaviorAnalyse
    @SkipRecord
    @LoginRequired
    @PermissionRequired("webapi.controller_permitted.fileio.export_task.child_for_me_disp")
    public FastJsonResponseData<JSFixedFastJsonPagedData<JSFixedFastJsonDispExportTask>> childForMeDisp(
            HttpServletRequest request, @RequestParam("page") int page, @RequestParam("rows") int rows
    ) {
        try {
            StringIdKey inspectAccountKey = new StringIdKey(tokenHandler.getUserId(request));
            PagedData<DispExportTask> child = service.childForMeDisp(new PagingInfo(page, rows), inspectAccountKey);
            PagedData<JSFixedFastJsonDispExportTask> transform = PagingUtil.transform(child, dispBeanTransformer);
            return FastJsonResponseData.of(ResponseDataUtil.good(JSFixedFastJsonPagedData.of(transform)));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常，信息如下：", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }
}
