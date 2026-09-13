package com.dwarfeng.familyhelper.webapi.node.controller.v1.fileio;

import com.dwarfeng.familyhelper.webapi.stack.service.fileio.WriterSupportResponseService;
import com.dwarfeng.fileio.sdk.bean.entity.FastJsonWriterSupport;
import com.dwarfeng.fileio.stack.bean.entity.WriterSupport;
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
import com.dwarfeng.subgrade.stack.bean.key.StringIdKey;
import com.dwarfeng.subgrade.stack.exception.ServiceExceptionMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * 写入器支持控制器。
 *
 * @author diaocl
 * @since 2.1.0
 */
@RestController("fileioWriterSupportController")
@RequestMapping("/api/v1/fileio")
public class WriterSupportController {

    private static final Logger LOGGER = LoggerFactory.getLogger(WriterSupportController.class);

    private final WriterSupportResponseService service;
    private final ServiceExceptionMapper sem;

    private final BeanTransformer<WriterSupport, FastJsonWriterSupport> beanTransformer;

    public WriterSupportController(
            WriterSupportResponseService service,
            ServiceExceptionMapper sem,
            BeanTransformer<WriterSupport, FastJsonWriterSupport> beanTransformer
    ) {
        this.service = service;
        this.sem = sem;
        this.beanTransformer = beanTransformer;
    }

    @GetMapping("/writer-support/{id}/exists")
    @BehaviorAnalyse
    @LoginRequired
    @PermissionRequired("webapi.controller_permitted.fileio.writer_support.exists")
    public FastJsonResponseData<Boolean> exists(HttpServletRequest request, @PathVariable("id") String id) {
        try {
            boolean exists = service.exists(new StringIdKey(id));
            return FastJsonResponseData.of(ResponseDataUtil.good(exists));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常，信息如下：", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @GetMapping("/writer-support/{id}")
    @BehaviorAnalyse
    @LoginRequired
    @PermissionRequired("webapi.controller_permitted.fileio.writer_support.get")
    public FastJsonResponseData<FastJsonWriterSupport> get(HttpServletRequest request, @PathVariable("id") String id) {
        try {
            WriterSupport writerSupport = service.get(new StringIdKey(id));
            return FastJsonResponseData.of(ResponseDataUtil.good(FastJsonWriterSupport.of(writerSupport)));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常，信息如下：", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @GetMapping("/writer-support/all")
    @BehaviorAnalyse
    @SkipRecord
    @LoginRequired
    @PermissionRequired("webapi.controller_permitted.fileio.writer_support.all")
    public FastJsonResponseData<JSFixedFastJsonPagedData<FastJsonWriterSupport>> all(
            HttpServletRequest request, @RequestParam("page") int page, @RequestParam("rows") int rows
    ) {
        try {
            PagedData<WriterSupport> all = service.all(new PagingInfo(page, rows));
            PagedData<FastJsonWriterSupport> transform = PagingUtil.transform(all, beanTransformer);
            return FastJsonResponseData.of(ResponseDataUtil.good(JSFixedFastJsonPagedData.of(transform)));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常，信息如下：", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @GetMapping("/writer-support/id-like")
    @BehaviorAnalyse
    @SkipRecord
    @LoginRequired
    @PermissionRequired("webapi.controller_permitted.fileio.writer_support.id_like")
    public FastJsonResponseData<JSFixedFastJsonPagedData<FastJsonWriterSupport>> idLike(
            HttpServletRequest request,
            @RequestParam("pattern") String pattern, @RequestParam("page") int page, @RequestParam("rows") int rows
    ) {
        try {
            PagedData<WriterSupport> all = service.idLike(pattern, new PagingInfo(page, rows));
            PagedData<FastJsonWriterSupport> transform = PagingUtil.transform(all, beanTransformer);
            return FastJsonResponseData.of(ResponseDataUtil.good(JSFixedFastJsonPagedData.of(transform)));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常，信息如下：", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }
}
