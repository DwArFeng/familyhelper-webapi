package com.dwarfeng.familyhelper.webapi.node.controller.v1.fileio;

import com.dwarfeng.familyhelper.webapi.stack.service.fileio.ImportMetadataResponseService;
import com.dwarfeng.fileio.sdk.bean.entity.JSFixedFastJsonImportMetadata;
import com.dwarfeng.fileio.stack.bean.entity.ImportMetadata;
import com.dwarfeng.fileio.stack.bean.key.TaskItemKey;
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
 * 导入元数据控制器。
 *
 * @author diaocl
 * @since 2.1.0
 */
@RestController("fileioImportMetadataController")
@RequestMapping("/api/v1/fileio")
public class ImportMetadataController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ImportMetadataController.class);

    private final ImportMetadataResponseService service;
    private final ServiceExceptionMapper sem;

    private final BeanTransformer<ImportMetadata, JSFixedFastJsonImportMetadata> beanTransformer;

    public ImportMetadataController(
            ImportMetadataResponseService service,
            ServiceExceptionMapper sem,
            BeanTransformer<ImportMetadata, JSFixedFastJsonImportMetadata> beanTransformer
    ) {
        this.service = service;
        this.sem = sem;
        this.beanTransformer = beanTransformer;
    }

    @GetMapping("/import-metadata/{taskId}&{identifier}/exists")
    @BehaviorAnalyse
    @LoginRequired
    @PermissionRequired("webapi.controller_permitted.fileio.import_metadata.exists")
    public FastJsonResponseData<Boolean> exists(
            HttpServletRequest request,
            @PathVariable("taskId") long taskId,
            @PathVariable("identifier") String identifier
    ) {
        try {
            boolean exists = service.exists(new TaskItemKey(taskId, identifier));
            return FastJsonResponseData.of(ResponseDataUtil.good(exists));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常，信息如下：", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @GetMapping("/import-metadata/{taskId}&{identifier}")
    @BehaviorAnalyse
    @LoginRequired
    @PermissionRequired("webapi.controller_permitted.fileio.import_metadata.get")
    public FastJsonResponseData<JSFixedFastJsonImportMetadata> get(
            HttpServletRequest request,
            @PathVariable("taskId") long taskId,
            @PathVariable("identifier") String identifier
    ) {
        try {
            ImportMetadata importMetadata = service.get(new TaskItemKey(taskId, identifier));
            return FastJsonResponseData.of(ResponseDataUtil.good(
                    JSFixedFastJsonImportMetadata.of(importMetadata)
            ));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常，信息如下：", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @GetMapping("/import-metadata/all")
    @BehaviorAnalyse
    @SkipRecord
    @LoginRequired
    @PermissionRequired("webapi.controller_permitted.fileio.import_metadata.all")
    public FastJsonResponseData<JSFixedFastJsonPagedData<JSFixedFastJsonImportMetadata>> all(
            HttpServletRequest request, @RequestParam("page") int page, @RequestParam("rows") int rows
    ) {
        try {
            PagedData<ImportMetadata> all = service.all(new PagingInfo(page, rows));
            PagedData<JSFixedFastJsonImportMetadata> transform = PagingUtil.transform(all, beanTransformer);
            return FastJsonResponseData.of(ResponseDataUtil.good(JSFixedFastJsonPagedData.of(transform)));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常，信息如下：", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @GetMapping("/task/{taskId}/import-metadata")
    @BehaviorAnalyse
    @SkipRecord
    @LoginRequired
    @PermissionRequired("webapi.controller_permitted.fileio.import_metadata.child_for_task")
    public FastJsonResponseData<JSFixedFastJsonPagedData<JSFixedFastJsonImportMetadata>> childForTask(
            HttpServletRequest request,
            @PathVariable Long taskId,
            @RequestParam("page") int page, @RequestParam("rows") int rows
    ) {
        try {
            PagedData<ImportMetadata> childForTask = service.childForTask(
                    new LongIdKey(taskId), new PagingInfo(page, rows)
            );
            PagedData<JSFixedFastJsonImportMetadata> transform = PagingUtil.transform(
                    childForTask, beanTransformer
            );
            return FastJsonResponseData.of(ResponseDataUtil.good(JSFixedFastJsonPagedData.of(transform)));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常，信息如下：", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }
}
