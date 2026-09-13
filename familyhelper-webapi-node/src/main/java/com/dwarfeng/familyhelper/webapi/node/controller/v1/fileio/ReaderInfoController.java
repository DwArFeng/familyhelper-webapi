package com.dwarfeng.familyhelper.webapi.node.controller.v1.fileio;

import com.dwarfeng.familyhelper.webapi.stack.service.fileio.ReaderInfoResponseService;
import com.dwarfeng.fileio.sdk.bean.entity.JSFixedFastJsonReaderInfo;
import com.dwarfeng.fileio.sdk.bean.entity.WebInputReaderInfo;
import com.dwarfeng.fileio.sdk.bean.key.JSFixedFastJsonTaskSettingItemKey;
import com.dwarfeng.fileio.stack.bean.entity.ReaderInfo;
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
 * 读取器信息控制器。
 *
 * @author diaocl
 * @since 2.1.0
 */
@RestController("fileioReaderInfoController")
@RequestMapping("/api/v1/fileio")
public class ReaderInfoController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ReaderInfoController.class);

    private final ReaderInfoResponseService service;
    private final ServiceExceptionMapper sem;

    private final BeanTransformer<ReaderInfo, JSFixedFastJsonReaderInfo> beanTransformer;

    public ReaderInfoController(
            ReaderInfoResponseService service,
            ServiceExceptionMapper sem,
            BeanTransformer<ReaderInfo, JSFixedFastJsonReaderInfo> beanTransformer
    ) {
        this.service = service;
        this.sem = sem;
        this.beanTransformer = beanTransformer;
    }

    @GetMapping("/reader-info/{taskSettingId}&{identifier}/exists")
    @BehaviorAnalyse
    @LoginRequired
    @PermissionRequired("webapi.controller_permitted.fileio.reader_info.exists")
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

    @GetMapping("/reader-info/{taskSettingId}&{identifier}")
    @BehaviorAnalyse
    @LoginRequired
    @PermissionRequired("webapi.controller_permitted.fileio.reader_info.get")
    public FastJsonResponseData<JSFixedFastJsonReaderInfo> get(
            HttpServletRequest request,
            @PathVariable("taskSettingId") long taskSettingId,
            @PathVariable("identifier") String identifier
    ) {
        try {
            ReaderInfo writerInfo = service.get(new TaskSettingItemKey(taskSettingId, identifier));
            return FastJsonResponseData.of(ResponseDataUtil.good(
                    JSFixedFastJsonReaderInfo.of(writerInfo)
            ));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常，信息如下：", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @PostMapping("/reader-info")
    @BehaviorAnalyse
    @LoginRequired
    @PermissionRequired("webapi.controller_permitted.fileio.reader_info.insert")
    public FastJsonResponseData<JSFixedFastJsonTaskSettingItemKey> insert(
            HttpServletRequest request,
            @RequestBody @Validated(Insert.class) WebInputReaderInfo webInputReaderInfo
    ) {
        try {
            ReaderInfo writerInfo = WebInputReaderInfo.toStackBean(webInputReaderInfo);
            TaskSettingItemKey insert = service.insert(writerInfo);
            return FastJsonResponseData.of(ResponseDataUtil.good(JSFixedFastJsonTaskSettingItemKey.of(insert)));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常，信息如下：", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @PatchMapping("/reader-info")
    @BehaviorAnalyse
    @BindingCheck
    @LoginRequired
    @PermissionRequired("webapi.controller_permitted.fileio.reader_info.update")
    public FastJsonResponseData<Object> update(
            HttpServletRequest request,
            @RequestBody @Validated WebInputReaderInfo webInputReaderInfo,
            BindingResult bindingResult
    ) {
        try {
            service.update(WebInputReaderInfo.toStackBean(webInputReaderInfo));
            return FastJsonResponseData.of(ResponseDataUtil.good(null));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常，信息如下：", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @DeleteMapping("/reader-info/{taskSettingId}&{identifier}")
    @BehaviorAnalyse
    @LoginRequired
    @PermissionRequired("webapi.controller_permitted.fileio.reader_info.delete")
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

    @GetMapping("/reader-info/all")
    @BehaviorAnalyse
    @SkipRecord
    @LoginRequired
    @PermissionRequired("webapi.controller_permitted.fileio.reader_info.all")
    public FastJsonResponseData<JSFixedFastJsonPagedData<JSFixedFastJsonReaderInfo>> all(
            HttpServletRequest request, @RequestParam("page") int page, @RequestParam("rows") int rows
    ) {
        try {
            PagedData<ReaderInfo> all = service.all(new PagingInfo(page, rows));
            PagedData<JSFixedFastJsonReaderInfo> transform = PagingUtil.transform(all, beanTransformer);
            return FastJsonResponseData.of(ResponseDataUtil.good(JSFixedFastJsonPagedData.of(transform)));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常，信息如下：", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @GetMapping("/task-setting/{taskSettingId}/reader-info")
    @BehaviorAnalyse
    @SkipRecord
    @LoginRequired
    @PermissionRequired("webapi.controller_permitted.fileio.reader_info.child_for_task_setting")
    public FastJsonResponseData<JSFixedFastJsonPagedData<JSFixedFastJsonReaderInfo>> childForTaskSetting(
            HttpServletRequest request,
            @PathVariable Long taskSettingId,
            @RequestParam("page") int page, @RequestParam("rows") int rows
    ) {
        try {
            PagedData<ReaderInfo> childForTaskSetting = service.childForTaskSetting(
                    new LongIdKey(taskSettingId), new PagingInfo(page, rows)
            );
            PagedData<JSFixedFastJsonReaderInfo> transform = PagingUtil.transform(
                    childForTaskSetting, beanTransformer
            );
            return FastJsonResponseData.of(ResponseDataUtil.good(JSFixedFastJsonPagedData.of(transform)));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常，信息如下：", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }
}
