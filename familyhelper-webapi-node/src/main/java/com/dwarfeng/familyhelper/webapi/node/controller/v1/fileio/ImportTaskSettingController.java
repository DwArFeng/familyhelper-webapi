package com.dwarfeng.familyhelper.webapi.node.controller.v1.fileio;

import com.dwarfeng.familyhelper.webapi.stack.service.fileio.ImportTaskSettingResponseService;
import com.dwarfeng.fileio.sdk.bean.entity.JSFixedFastJsonImportTaskSetting;
import com.dwarfeng.fileio.sdk.bean.entity.WebInputImportTaskSetting;
import com.dwarfeng.fileio.stack.bean.entity.ImportTaskSetting;
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
 * 导入任务设置控制器。
 *
 * @author diaocl
 * @since 2.1.0
 */
@RestController("fileioImportTaskSettingController")
@RequestMapping("/api/v1/fileio")
public class ImportTaskSettingController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ImportTaskSettingController.class);

    private final ImportTaskSettingResponseService service;
    private final ServiceExceptionMapper sem;

    private final BeanTransformer<ImportTaskSetting, JSFixedFastJsonImportTaskSetting> beanTransformer;

    public ImportTaskSettingController(
            ImportTaskSettingResponseService service,
            ServiceExceptionMapper sem,
            BeanTransformer<ImportTaskSetting, JSFixedFastJsonImportTaskSetting> beanTransformer
    ) {
        this.service = service;
        this.sem = sem;
        this.beanTransformer = beanTransformer;
    }

    @GetMapping("/import-task-setting/{id}/exists")
    @BehaviorAnalyse
    @LoginRequired
    @PermissionRequired("webapi.controller_permitted.fileio.import_task_setting.exists")
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

    @GetMapping("/import-task-setting/{id}")
    @BehaviorAnalyse
    @LoginRequired
    @PermissionRequired("webapi.controller_permitted.fileio.import_task_setting.get")
    public FastJsonResponseData<JSFixedFastJsonImportTaskSetting> get(
            HttpServletRequest request,
            @PathVariable("id") long id
    ) {
        try {
            ImportTaskSetting importTaskSetting = service.get(new LongIdKey(id));
            return FastJsonResponseData.of(ResponseDataUtil.good(
                    JSFixedFastJsonImportTaskSetting.of(importTaskSetting)
            ));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常，信息如下：", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @PostMapping("/import-task-setting")
    @BehaviorAnalyse
    @LoginRequired
    @PermissionRequired("webapi.controller_permitted.fileio.import_task_setting.insert")
    public FastJsonResponseData<JSFixedFastJsonLongIdKey> insert(
            HttpServletRequest request,
            @RequestBody @Validated(Insert.class) WebInputImportTaskSetting webInputImportTaskSetting
    ) {
        try {
            ImportTaskSetting importTaskSetting = WebInputImportTaskSetting.toStackBean(webInputImportTaskSetting);
            LongIdKey insert = service.insert(importTaskSetting);
            return FastJsonResponseData.of(ResponseDataUtil.good(JSFixedFastJsonLongIdKey.of(insert)));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常，信息如下：", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @PatchMapping("/import-task-setting")
    @BehaviorAnalyse
    @BindingCheck
    @LoginRequired
    @PermissionRequired("webapi.controller_permitted.fileio.import_task_setting.update")
    public FastJsonResponseData<Object> update(
            HttpServletRequest request,
            @RequestBody @Validated WebInputImportTaskSetting webInputImportTaskSetting,
            BindingResult bindingResult
    ) {
        try {
            service.update(WebInputImportTaskSetting.toStackBean(webInputImportTaskSetting));
            return FastJsonResponseData.of(ResponseDataUtil.good(null));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常，信息如下：", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @DeleteMapping("/import-task-setting/{id}")
    @BehaviorAnalyse
    @LoginRequired
    @PermissionRequired("webapi.controller_permitted.fileio.import_task_setting.delete")
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

    @GetMapping("/import-task-setting/all")
    @BehaviorAnalyse
    @SkipRecord
    @LoginRequired
    @PermissionRequired("webapi.controller_permitted.fileio.import_task_setting.all")
    public FastJsonResponseData<JSFixedFastJsonPagedData<JSFixedFastJsonImportTaskSetting>> all(
            HttpServletRequest request, @RequestParam("page") int page, @RequestParam("rows") int rows
    ) {
        try {
            PagedData<ImportTaskSetting> all = service.all(new PagingInfo(page, rows));
            PagedData<JSFixedFastJsonImportTaskSetting> transform = PagingUtil.transform(all, beanTransformer);
            return FastJsonResponseData.of(ResponseDataUtil.good(JSFixedFastJsonPagedData.of(transform)));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常，信息如下：", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }
}
