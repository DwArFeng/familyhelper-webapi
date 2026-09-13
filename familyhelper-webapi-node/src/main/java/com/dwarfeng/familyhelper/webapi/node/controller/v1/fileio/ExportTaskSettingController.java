package com.dwarfeng.familyhelper.webapi.node.controller.v1.fileio;

import com.dwarfeng.familyhelper.webapi.stack.service.fileio.ExportTaskSettingResponseService;
import com.dwarfeng.fileio.sdk.bean.entity.JSFixedFastJsonExportTaskSetting;
import com.dwarfeng.fileio.sdk.bean.entity.WebInputExportTaskSetting;
import com.dwarfeng.fileio.stack.bean.entity.ExportTaskSetting;
import com.dwarfeng.subgrade.sdk.bean.dto.FastJsonResponseData;
import com.dwarfeng.subgrade.sdk.bean.dto.JSFixedFastJsonPagedData;
import com.dwarfeng.subgrade.sdk.bean.dto.PagingUtil;
import com.dwarfeng.subgrade.sdk.bean.dto.ResponseDataUtil;
import com.dwarfeng.subgrade.sdk.bean.key.JSFixedFastJsonLongIdKey;
import com.dwarfeng.subgrade.sdk.interceptor.analyse.BehaviorAnalyse;
import com.dwarfeng.subgrade.sdk.interceptor.analyse.SkipRecord;
import com.dwarfeng.subgrade.sdk.interceptor.http.BindingCheck;
import com.dwarfeng.subgrade.sdk.interceptor.login.LoginRequired;
import com.dwarfeng.subgrade.sdk.interceptor.permission.PermissionRequired;
import com.dwarfeng.subgrade.sdk.validation.group.Insert;
import com.dwarfeng.subgrade.stack.bean.BeanTransformer;
import com.dwarfeng.subgrade.stack.bean.dto.PagedData;
import com.dwarfeng.subgrade.stack.bean.dto.PagingInfo;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.exception.ServiceExceptionMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * 导出任务设置控制器。
 *
 * @author diaocl
 * @since 2.1.0
 */
@RestController("fileioExportTaskSettingController")
@RequestMapping("/api/v1/fileio")
public class ExportTaskSettingController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ExportTaskSettingController.class);

    private final ExportTaskSettingResponseService service;
    private final ServiceExceptionMapper sem;

    private final BeanTransformer<ExportTaskSetting, JSFixedFastJsonExportTaskSetting> beanTransformer;

    public ExportTaskSettingController(
            ExportTaskSettingResponseService service,
            ServiceExceptionMapper sem,
            BeanTransformer<ExportTaskSetting, JSFixedFastJsonExportTaskSetting> beanTransformer
    ) {
        this.service = service;
        this.sem = sem;
        this.beanTransformer = beanTransformer;
    }

    @GetMapping("/export-task-setting/{id}/exists")
    @BehaviorAnalyse
    @LoginRequired
    @PermissionRequired("webapi.controller_permitted.fileio.export_task_setting.exists")
    public FastJsonResponseData<Boolean> exists(
            HttpServletRequest request,
            @PathVariable("id") long id
    ) {
        try {
            boolean exists = service.exists(new LongIdKey(id));
            return FastJsonResponseData.of(ResponseDataUtil.good(exists));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常，信息如下：", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @GetMapping("/export-task-setting/{id}")
    @BehaviorAnalyse
    @LoginRequired
    @PermissionRequired("webapi.controller_permitted.fileio.export_task_setting.get")
    public FastJsonResponseData<JSFixedFastJsonExportTaskSetting> get(
            HttpServletRequest request,
            @PathVariable("id") long id
    ) {
        try {
            ExportTaskSetting exportTaskSetting = service.get(new LongIdKey(id));
            return FastJsonResponseData.of(ResponseDataUtil.good(
                    JSFixedFastJsonExportTaskSetting.of(exportTaskSetting)
            ));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常，信息如下：", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @PostMapping("/export-task-setting")
    @BehaviorAnalyse
    @LoginRequired
    @PermissionRequired("webapi.controller_permitted.fileio.export_task_setting.insert")
    public FastJsonResponseData<JSFixedFastJsonLongIdKey> insert(
            HttpServletRequest request,
            @RequestBody @Validated(Insert.class) WebInputExportTaskSetting webInputExportTaskSetting
    ) {
        try {
            ExportTaskSetting exportTaskSetting = WebInputExportTaskSetting.toStackBean(webInputExportTaskSetting);
            LongIdKey insert = service.insert(exportTaskSetting);
            return FastJsonResponseData.of(ResponseDataUtil.good(JSFixedFastJsonLongIdKey.of(insert)));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常，信息如下：", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @PatchMapping("/export-task-setting")
    @BehaviorAnalyse
    @BindingCheck
    @LoginRequired
    @PermissionRequired("webapi.controller_permitted.fileio.export_task_setting.update")
    public FastJsonResponseData<Object> update(
            HttpServletRequest request,
            @RequestBody @Validated WebInputExportTaskSetting webInputExportTaskSetting,
            BindingResult bindingResult
    ) {
        try {
            service.update(WebInputExportTaskSetting.toStackBean(webInputExportTaskSetting));
            return FastJsonResponseData.of(ResponseDataUtil.good(null));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常，信息如下：", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @DeleteMapping("/export-task-setting/{id}")
    @BehaviorAnalyse
    @LoginRequired
    @PermissionRequired("webapi.controller_permitted.fileio.export_task_setting.delete")
    public FastJsonResponseData<Object> delete(
            HttpServletRequest request,
            @PathVariable("id") long id
    ) {
        try {
            service.delete(new LongIdKey(id));
            return FastJsonResponseData.of(ResponseDataUtil.good(null));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常，信息如下：", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @GetMapping("/export-task-setting/all")
    @BehaviorAnalyse
    @SkipRecord
    @LoginRequired
    @PermissionRequired("webapi.controller_permitted.fileio.export_task_setting.all")
    public FastJsonResponseData<JSFixedFastJsonPagedData<JSFixedFastJsonExportTaskSetting>> all(
            HttpServletRequest request, @RequestParam("page") int page, @RequestParam("rows") int rows
    ) {
        try {
            PagedData<ExportTaskSetting> all = service.all(new PagingInfo(page, rows));
            PagedData<JSFixedFastJsonExportTaskSetting> transform = PagingUtil.transform(all, beanTransformer);
            return FastJsonResponseData.of(ResponseDataUtil.good(JSFixedFastJsonPagedData.of(transform)));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常，信息如下：", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }
}
