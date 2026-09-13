package com.dwarfeng.familyhelper.webapi.node.controller.v1.fileio;

import com.dwarfeng.familyhelper.webapi.stack.service.fileio.ExportConfResponseService;
import com.dwarfeng.fileio.sdk.bean.entity.JSFixedFastJsonExportConf;
import com.dwarfeng.fileio.sdk.bean.entity.WebInputExportConf;
import com.dwarfeng.fileio.sdk.bean.key.JSFixedFastJsonTaskSettingItemKey;
import com.dwarfeng.fileio.stack.bean.entity.ExportConf;
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
 * 导出配置控制器。
 *
 * @author diaocl
 * @since 2.1.0
 */
@RestController("fileioExportConfController")
@RequestMapping("/api/v1/fileio")
public class ExportConfController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ExportConfController.class);

    private final ExportConfResponseService service;
    private final ServiceExceptionMapper sem;

    private final BeanTransformer<ExportConf, JSFixedFastJsonExportConf> beanTransformer;

    public ExportConfController(
            ExportConfResponseService service,
            ServiceExceptionMapper sem,
            BeanTransformer<ExportConf, JSFixedFastJsonExportConf> beanTransformer
    ) {
        this.service = service;
        this.sem = sem;
        this.beanTransformer = beanTransformer;
    }

    @GetMapping("/export-conf/{taskSettingId}&{identifier}/exists")
    @BehaviorAnalyse
    @LoginRequired
    @PermissionRequired("webapi.controller_permitted.fileio.export_conf.exists")
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

    @GetMapping("/export-conf/{taskSettingId}&{identifier}")
    @BehaviorAnalyse
    @LoginRequired
    @PermissionRequired("webapi.controller_permitted.fileio.export_conf.get")
    public FastJsonResponseData<JSFixedFastJsonExportConf> get(
            HttpServletRequest request,
            @PathVariable("taskSettingId") long taskSettingId,
            @PathVariable("identifier") String identifier
    ) {
        try {
            ExportConf exportConf = service.get(new TaskSettingItemKey(taskSettingId, identifier));
            return FastJsonResponseData.of(ResponseDataUtil.good(
                    JSFixedFastJsonExportConf.of(exportConf)
            ));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常，信息如下：", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @PostMapping("/export-conf")
    @BehaviorAnalyse
    @LoginRequired
    @PermissionRequired("webapi.controller_permitted.fileio.export_conf.insert")
    public FastJsonResponseData<JSFixedFastJsonTaskSettingItemKey> insert(
            HttpServletRequest request,
            @RequestBody @Validated(Insert.class) WebInputExportConf webInputExportConf
    ) {
        try {
            ExportConf exportConf = WebInputExportConf.toStackBean(webInputExportConf);
            TaskSettingItemKey insert = service.insert(exportConf);
            return FastJsonResponseData.of(ResponseDataUtil.good(JSFixedFastJsonTaskSettingItemKey.of(insert)));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常，信息如下：", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @PatchMapping("/export-conf")
    @BehaviorAnalyse
    @BindingCheck
    @LoginRequired
    @PermissionRequired("webapi.controller_permitted.fileio.export_conf.update")
    public FastJsonResponseData<Object> update(
            HttpServletRequest request,
            @RequestBody @Validated WebInputExportConf webInputExportConf,
            BindingResult bindingResult
    ) {
        try {
            service.update(WebInputExportConf.toStackBean(webInputExportConf));
            return FastJsonResponseData.of(ResponseDataUtil.good(null));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常，信息如下：", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @DeleteMapping("/export-conf/{taskSettingId}&{identifier}")
    @BehaviorAnalyse
    @LoginRequired
    @PermissionRequired("webapi.controller_permitted.fileio.export_conf.delete")
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

    @GetMapping("/export-conf/all")
    @BehaviorAnalyse
    @SkipRecord
    @LoginRequired
    @PermissionRequired("webapi.controller_permitted.fileio.export_conf.all")
    public FastJsonResponseData<JSFixedFastJsonPagedData<JSFixedFastJsonExportConf>> all(
            HttpServletRequest request, @RequestParam("page") int page, @RequestParam("rows") int rows
    ) {
        try {
            PagedData<ExportConf> all = service.all(new PagingInfo(page, rows));
            PagedData<JSFixedFastJsonExportConf> transform = PagingUtil.transform(all, beanTransformer);
            return FastJsonResponseData.of(ResponseDataUtil.good(JSFixedFastJsonPagedData.of(transform)));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常，信息如下：", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @GetMapping("/task-setting/{taskSettingId}/export-conf")
    @BehaviorAnalyse
    @SkipRecord
    @LoginRequired
    @PermissionRequired("webapi.controller_permitted.fileio.export_conf.child_for_task_setting")
    public FastJsonResponseData<JSFixedFastJsonPagedData<JSFixedFastJsonExportConf>> childForTaskSetting(
            HttpServletRequest request,
            @PathVariable Long taskSettingId,
            @RequestParam("page") int page, @RequestParam("rows") int rows
    ) {
        try {
            PagedData<ExportConf> childForTaskSetting = service.childForTaskSetting(
                    new LongIdKey(taskSettingId), new PagingInfo(page, rows)
            );
            PagedData<JSFixedFastJsonExportConf> transform = PagingUtil.transform(
                    childForTaskSetting, beanTransformer
            );
            return FastJsonResponseData.of(ResponseDataUtil.good(JSFixedFastJsonPagedData.of(transform)));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常，信息如下：", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }
}
