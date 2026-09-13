package com.dwarfeng.familyhelper.webapi.node.controller.v1.fileio;

import com.dwarfeng.familyhelper.webapi.stack.service.fileio.ImporterInfoResponseService;
import com.dwarfeng.fileio.sdk.bean.entity.JSFixedFastJsonImporterInfo;
import com.dwarfeng.fileio.sdk.bean.entity.WebInputImporterInfo;
import com.dwarfeng.fileio.sdk.bean.key.JSFixedFastJsonTaskSettingItemKey;
import com.dwarfeng.fileio.stack.bean.entity.ImporterInfo;
import com.dwarfeng.fileio.stack.bean.key.TaskSettingItemKey;
import com.dwarfeng.subgrade.sdk.bean.dto.FastJsonResponseData;
import com.dwarfeng.subgrade.sdk.bean.dto.JSFixedFastJsonPagedData;
import com.dwarfeng.subgrade.sdk.bean.dto.PagingUtil;
import com.dwarfeng.subgrade.sdk.bean.dto.ResponseDataUtil;
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
 * 导入器信息控制器。
 *
 * @author diaocl
 * @since 2.1.0
 */
@RestController("fileioImporterInfoController")
@RequestMapping("/api/v1/fileio")
public class ImporterInfoController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ImporterInfoController.class);

    private final ImporterInfoResponseService service;
    private final ServiceExceptionMapper sem;

    private final BeanTransformer<ImporterInfo, JSFixedFastJsonImporterInfo> beanTransformer;

    public ImporterInfoController(
            ImporterInfoResponseService service,
            ServiceExceptionMapper sem,
            BeanTransformer<ImporterInfo, JSFixedFastJsonImporterInfo> beanTransformer
    ) {
        this.service = service;
        this.sem = sem;
        this.beanTransformer = beanTransformer;
    }

    @GetMapping("/importer-info/{taskSettingId}&{identifier}/exists")
    @BehaviorAnalyse
    @LoginRequired
    @PermissionRequired("webapi.controller_permitted.fileio.importer_info.exists")
    public FastJsonResponseData<Boolean> exists(
            HttpServletRequest request,
            @PathVariable("taskSettingId") long taskSettingId,
            @PathVariable("identifier") String identifier
    ) {
        try {
            boolean exists = service.exists(new TaskSettingItemKey(taskSettingId, identifier));
            return FastJsonResponseData.of(ResponseDataUtil.good(exists));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常，信息如下：", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @GetMapping("/importer-info/{taskSettingId}&{identifier}")
    @BehaviorAnalyse
    @LoginRequired
    @PermissionRequired("webapi.controller_permitted.fileio.importer_info.get")
    public FastJsonResponseData<JSFixedFastJsonImporterInfo> get(
            HttpServletRequest request,
            @PathVariable("taskSettingId") long taskSettingId,
            @PathVariable("identifier") String identifier
    ) {
        try {
            ImporterInfo importerInfo = service.get(new TaskSettingItemKey(taskSettingId, identifier));
            return FastJsonResponseData.of(ResponseDataUtil.good(
                    JSFixedFastJsonImporterInfo.of(importerInfo)
            ));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常，信息如下：", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @PostMapping("/importer-info")
    @BehaviorAnalyse
    @LoginRequired
    @PermissionRequired("webapi.controller_permitted.fileio.importer_info.insert")
    public FastJsonResponseData<JSFixedFastJsonTaskSettingItemKey> insert(
            HttpServletRequest request,
            @RequestBody @Validated(Insert.class) WebInputImporterInfo webInputImporterInfo
    ) {
        try {
            ImporterInfo importerInfo = WebInputImporterInfo.toStackBean(webInputImporterInfo);
            TaskSettingItemKey insert = service.insert(importerInfo);
            return FastJsonResponseData.of(ResponseDataUtil.good(JSFixedFastJsonTaskSettingItemKey.of(insert)));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常，信息如下：", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @PatchMapping("/importer-info")
    @BehaviorAnalyse
    @BindingCheck
    @LoginRequired
    @PermissionRequired("webapi.controller_permitted.fileio.importer_info.update")
    public FastJsonResponseData<Object> update(
            HttpServletRequest request,
            @RequestBody @Validated WebInputImporterInfo webInputImporterInfo,
            BindingResult bindingResult
    ) {
        try {
            service.update(WebInputImporterInfo.toStackBean(webInputImporterInfo));
            return FastJsonResponseData.of(ResponseDataUtil.good(null));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常，信息如下：", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @DeleteMapping("/importer-info/{taskSettingId}&{identifier}")
    @BehaviorAnalyse
    @LoginRequired
    @PermissionRequired("webapi.controller_permitted.fileio.importer_info.delete")
    public FastJsonResponseData<Object> delete(
            HttpServletRequest request,
            @PathVariable("taskSettingId") long taskSettingId,
            @PathVariable("identifier") String identifier
    ) {
        try {
            service.delete(new TaskSettingItemKey(taskSettingId, identifier));
            return FastJsonResponseData.of(ResponseDataUtil.good(null));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常，信息如下：", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @GetMapping("/importer-info/all")
    @BehaviorAnalyse
    @SkipRecord
    @LoginRequired
    @PermissionRequired("webapi.controller_permitted.fileio.importer_info.all")
    public FastJsonResponseData<JSFixedFastJsonPagedData<JSFixedFastJsonImporterInfo>> all(
            HttpServletRequest request, @RequestParam("page") int page, @RequestParam("rows") int rows
    ) {
        try {
            PagedData<ImporterInfo> all = service.all(new PagingInfo(page, rows));
            PagedData<JSFixedFastJsonImporterInfo> transform = PagingUtil.transform(all, beanTransformer);
            return FastJsonResponseData.of(ResponseDataUtil.good(JSFixedFastJsonPagedData.of(transform)));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常，信息如下：", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @GetMapping("/task-setting/{taskSettingId}/importer-info")
    @BehaviorAnalyse
    @SkipRecord
    @LoginRequired
    @PermissionRequired("webapi.controller_permitted.fileio.importer_info.child_for_task_setting")
    public FastJsonResponseData<JSFixedFastJsonPagedData<JSFixedFastJsonImporterInfo>> childForTaskSetting(
            HttpServletRequest request,
            @PathVariable Long taskSettingId,
            @RequestParam("page") int page, @RequestParam("rows") int rows
    ) {
        try {
            PagedData<ImporterInfo> childForTaskSetting = service.childForTaskSetting(
                    new LongIdKey(taskSettingId), new PagingInfo(page, rows)
            );
            PagedData<JSFixedFastJsonImporterInfo> transform = PagingUtil.transform(
                    childForTaskSetting, beanTransformer
            );
            return FastJsonResponseData.of(ResponseDataUtil.good(JSFixedFastJsonPagedData.of(transform)));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常，信息如下：", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }
}
