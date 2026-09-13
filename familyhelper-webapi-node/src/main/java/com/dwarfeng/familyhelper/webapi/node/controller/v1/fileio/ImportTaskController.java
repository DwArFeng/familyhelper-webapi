package com.dwarfeng.familyhelper.webapi.node.controller.v1.fileio;

import com.dwarfeng.familyhelper.webapi.sdk.bean.fileio.disp.JSFixedFastJsonDispImportTask;
import com.dwarfeng.familyhelper.webapi.stack.bean.fileio.disp.DispImportTask;
import com.dwarfeng.familyhelper.webapi.stack.handler.system.TokenHandler;
import com.dwarfeng.familyhelper.webapi.stack.service.fileio.ImportTaskResponseService;
import com.dwarfeng.fileio.sdk.bean.entity.JSFixedFastJsonImportTask;
import com.dwarfeng.fileio.stack.bean.entity.ImportTask;
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
 * 导入任务控制器。
 *
 * @author diaocl
 * @author DwArFeng
 * @since 2.1.0
 */
@RestController("fileioImportTaskController")
@RequestMapping("/api/v1/fileio")
public class ImportTaskController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ImportTaskController.class);

    private final ImportTaskResponseService service;
    private final ServiceExceptionMapper sem;
    private final TokenHandler tokenHandler;

    private final BeanTransformer<ImportTask, JSFixedFastJsonImportTask> beanTransformer;
    private final BeanTransformer<DispImportTask, JSFixedFastJsonDispImportTask> dispBeanTransformer;

    public ImportTaskController(
            ImportTaskResponseService service,
            ServiceExceptionMapper sem,
            TokenHandler tokenHandler,
            BeanTransformer<ImportTask, JSFixedFastJsonImportTask> beanTransformer,
            @Qualifier("fileio.dispImportTaskBeanTransformer")
            BeanTransformer<DispImportTask, JSFixedFastJsonDispImportTask> dispBeanTransformer
    ) {
        this.service = service;
        this.sem = sem;
        this.tokenHandler = tokenHandler;
        this.beanTransformer = beanTransformer;
        this.dispBeanTransformer = dispBeanTransformer;
    }

    @GetMapping("/import-task/{id}/exists")
    @BehaviorAnalyse
    @LoginRequired
    @PermissionRequired("webapi.controller_permitted.fileio.import_task.exists")
    public FastJsonResponseData<Boolean> exists(HttpServletRequest request, @PathVariable("id") long id) {
        try {
            boolean exists = service.exists(new LongIdKey(id));
            return FastJsonResponseData.of(ResponseDataUtil.good(exists));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常，信息如下：", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @GetMapping("/import-task/{id}")
    @BehaviorAnalyse
    @LoginRequired
    @PermissionRequired("webapi.controller_permitted.fileio.import_task.get")
    public FastJsonResponseData<JSFixedFastJsonImportTask> get(
            HttpServletRequest request, @PathVariable("id") long id
    ) {
        try {
            ImportTask importTask = service.get(new LongIdKey(id));
            return FastJsonResponseData.of(ResponseDataUtil.good(JSFixedFastJsonImportTask.of(importTask)));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常，信息如下：", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @GetMapping("/import-task/all")
    @BehaviorAnalyse
    @SkipRecord
    @LoginRequired
    @PermissionRequired("webapi.controller_permitted.fileio.import_task.all")
    public FastJsonResponseData<JSFixedFastJsonPagedData<JSFixedFastJsonImportTask>> all(
            HttpServletRequest request, @RequestParam("page") int page, @RequestParam("rows") int rows
    ) {
        try {
            PagedData<ImportTask> all = service.all(new PagingInfo(page, rows));
            PagedData<JSFixedFastJsonImportTask> transform = PagingUtil.transform(all, beanTransformer);
            return FastJsonResponseData.of(ResponseDataUtil.good(JSFixedFastJsonPagedData.of(transform)));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常，信息如下：", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @GetMapping("/task-setting/{taskSettingId}/import-task")
    @BehaviorAnalyse
    @SkipRecord
    @LoginRequired
    @PermissionRequired("webapi.controller_permitted.fileio.import_task.child_for_task_setting")
    public FastJsonResponseData<JSFixedFastJsonPagedData<JSFixedFastJsonImportTask>> childForTaskSetting(
            HttpServletRequest request,
            @PathVariable Long taskSettingId,
            @RequestParam("page") int page, @RequestParam("rows") int rows
    ) {
        try {
            PagedData<ImportTask> childForTaskSetting = service.childForTaskSetting(
                    new LongIdKey(taskSettingId), new PagingInfo(page, rows)
            );
            PagedData<JSFixedFastJsonImportTask> transform = PagingUtil.transform(
                    childForTaskSetting, beanTransformer
            );
            return FastJsonResponseData.of(ResponseDataUtil.good(JSFixedFastJsonPagedData.of(transform)));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常，信息如下：", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @GetMapping("/import-task/create-date-desc")
    @BehaviorAnalyse
    @SkipRecord
    @LoginRequired
    @PermissionRequired("webapi.controller_permitted.fileio.import_task.create_date_desc")
    public FastJsonResponseData<JSFixedFastJsonPagedData<JSFixedFastJsonImportTask>> createDateDesc(
            HttpServletRequest request, @RequestParam("page") int page, @RequestParam("rows") int rows
    ) {
        try {
            PagedData<ImportTask> createDateDesc = service.createDateDesc(new PagingInfo(page, rows));
            PagedData<JSFixedFastJsonImportTask> transform = PagingUtil.transform(
                    createDateDesc, beanTransformer
            );
            return FastJsonResponseData.of(ResponseDataUtil.good(JSFixedFastJsonPagedData.of(transform)));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常，信息如下：", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @GetMapping("/import-task/user/{userId}")
    @BehaviorAnalyse
    @SkipRecord
    @LoginRequired
    @PermissionRequired("webapi.controller_permitted.fileio.import_task.child_for_user")
    public FastJsonResponseData<JSFixedFastJsonPagedData<JSFixedFastJsonImportTask>> childForUser(
            HttpServletRequest request,
            @PathVariable String userId, @RequestParam("page") int page, @RequestParam("rows") int rows
    ) {
        try {
            PagedData<ImportTask> childForUser = service.childForUser(
                    new StringIdKey(userId), new PagingInfo(page, rows)
            );
            PagedData<JSFixedFastJsonImportTask> transform = PagingUtil.transform(childForUser, beanTransformer);
            return FastJsonResponseData.of(ResponseDataUtil.good(JSFixedFastJsonPagedData.of(transform)));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常，信息如下：", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @GetMapping("/me/import-task")
    @BehaviorAnalyse
    @SkipRecord
    @LoginRequired
    @PermissionRequired("webapi.controller_permitted.fileio.import_task.child_for_me")
    public FastJsonResponseData<JSFixedFastJsonPagedData<JSFixedFastJsonImportTask>> childForMe(
            HttpServletRequest request, @RequestParam("page") int page, @RequestParam("rows") int rows
    ) {
        try {
            StringIdKey accountKey = new StringIdKey(tokenHandler.getUserId(request));
            PagedData<ImportTask> childForMe = service.childForUser(accountKey, new PagingInfo(page, rows));
            PagedData<JSFixedFastJsonImportTask> transform = PagingUtil.transform(childForMe, beanTransformer);
            return FastJsonResponseData.of(ResponseDataUtil.good(JSFixedFastJsonPagedData.of(transform)));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常，信息如下：", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @GetMapping("/import-task/{id}/disp")
    @BehaviorAnalyse
    @LoginRequired
    @PermissionRequired("webapi.controller_permitted.fileio.import_task.get_disp")
    public FastJsonResponseData<JSFixedFastJsonDispImportTask> getDisp(
            HttpServletRequest request,
            @PathVariable("id") long id
    ) {
        try {
            StringIdKey inspectAccountKey = new StringIdKey(tokenHandler.getUserId(request));
            DispImportTask disp = service.getDisp(new LongIdKey(id), inspectAccountKey);
            return FastJsonResponseData.of(ResponseDataUtil.good(JSFixedFastJsonDispImportTask.of(disp)));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常，信息如下：", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @GetMapping("/import-task/all/disp")
    @BehaviorAnalyse
    @SkipRecord
    @LoginRequired
    @PermissionRequired("webapi.controller_permitted.fileio.import_task.all_disp")
    public FastJsonResponseData<JSFixedFastJsonPagedData<JSFixedFastJsonDispImportTask>> allDisp(
            HttpServletRequest request, @RequestParam("page") int page, @RequestParam("rows") int rows
    ) {
        try {
            StringIdKey inspectAccountKey = new StringIdKey(tokenHandler.getUserId(request));
            PagedData<DispImportTask> all = service.allDisp(new PagingInfo(page, rows), inspectAccountKey);
            PagedData<JSFixedFastJsonDispImportTask> transform = PagingUtil.transform(all, dispBeanTransformer);
            return FastJsonResponseData.of(ResponseDataUtil.good(JSFixedFastJsonPagedData.of(transform)));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常，信息如下：", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @GetMapping("/task-setting/{taskSettingId}/import-task/disp")
    @BehaviorAnalyse
    @SkipRecord
    @LoginRequired
    @PermissionRequired("webapi.controller_permitted.fileio.import_task.child_for_task_setting_disp")
    public FastJsonResponseData<JSFixedFastJsonPagedData<JSFixedFastJsonDispImportTask>> childForTaskSettingDisp(
            HttpServletRequest request,
            @PathVariable Long taskSettingId,
            @RequestParam("page") int page, @RequestParam("rows") int rows
    ) {
        try {
            StringIdKey inspectAccountKey = new StringIdKey(tokenHandler.getUserId(request));
            PagedData<DispImportTask> child = service.childForTaskSettingDisp(
                    new LongIdKey(taskSettingId), new PagingInfo(page, rows), inspectAccountKey
            );
            PagedData<JSFixedFastJsonDispImportTask> transform = PagingUtil.transform(child, dispBeanTransformer);
            return FastJsonResponseData.of(ResponseDataUtil.good(JSFixedFastJsonPagedData.of(transform)));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常，信息如下：", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @GetMapping("/import-task/create-date-desc/disp")
    @BehaviorAnalyse
    @SkipRecord
    @LoginRequired
    @PermissionRequired("webapi.controller_permitted.fileio.import_task.create_date_desc_disp")
    public FastJsonResponseData<JSFixedFastJsonPagedData<JSFixedFastJsonDispImportTask>> createDateDescDisp(
            HttpServletRequest request, @RequestParam("page") int page, @RequestParam("rows") int rows
    ) {
        try {
            StringIdKey inspectAccountKey = new StringIdKey(tokenHandler.getUserId(request));
            PagedData<DispImportTask> result = service.createDateDescDisp(new PagingInfo(page, rows), inspectAccountKey);
            PagedData<JSFixedFastJsonDispImportTask> transform = PagingUtil.transform(result, dispBeanTransformer);
            return FastJsonResponseData.of(ResponseDataUtil.good(JSFixedFastJsonPagedData.of(transform)));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常，信息如下：", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @GetMapping("/import-task/user/{userId}/disp")
    @BehaviorAnalyse
    @SkipRecord
    @LoginRequired
    @PermissionRequired("webapi.controller_permitted.fileio.import_task.child_for_user_disp")
    public FastJsonResponseData<JSFixedFastJsonPagedData<JSFixedFastJsonDispImportTask>> childForUserDisp(
            HttpServletRequest request,
            @PathVariable String userId, @RequestParam("page") int page, @RequestParam("rows") int rows
    ) {
        try {
            StringIdKey inspectAccountKey = new StringIdKey(tokenHandler.getUserId(request));
            PagedData<DispImportTask> child = service.childForUserDisp(
                    new StringIdKey(userId), new PagingInfo(page, rows), inspectAccountKey
            );
            PagedData<JSFixedFastJsonDispImportTask> transform = PagingUtil.transform(child, dispBeanTransformer);
            return FastJsonResponseData.of(ResponseDataUtil.good(JSFixedFastJsonPagedData.of(transform)));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常，信息如下：", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @GetMapping("/me/import-task/disp")
    @BehaviorAnalyse
    @SkipRecord
    @LoginRequired
    @PermissionRequired("webapi.controller_permitted.fileio.import_task.child_for_me_disp")
    public FastJsonResponseData<JSFixedFastJsonPagedData<JSFixedFastJsonDispImportTask>> childForMeDisp(
            HttpServletRequest request, @RequestParam("page") int page, @RequestParam("rows") int rows
    ) {
        try {
            StringIdKey inspectAccountKey = new StringIdKey(tokenHandler.getUserId(request));
            PagedData<DispImportTask> child = service.childForMeDisp(new PagingInfo(page, rows), inspectAccountKey);
            PagedData<JSFixedFastJsonDispImportTask> transform = PagingUtil.transform(child, dispBeanTransformer);
            return FastJsonResponseData.of(ResponseDataUtil.good(JSFixedFastJsonPagedData.of(transform)));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常，信息如下：", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }
}
