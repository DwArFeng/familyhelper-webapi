package com.dwarfeng.familyhelper.webapi.node.controller.v1.notify;

import com.dwarfeng.familyhelper.plugin.notify.bean.entity.FastJsonSendExecutorSupport;
import com.dwarfeng.familyhelper.plugin.notify.bean.entity.SendExecutorSupport;
import com.dwarfeng.familyhelper.webapi.stack.service.notify.SendExecutorSupportResponseService;
import com.dwarfeng.subgrade.sdk.bean.dto.FastJsonResponseData;
import com.dwarfeng.subgrade.sdk.bean.dto.JSFixedFastJsonPagedData;
import com.dwarfeng.subgrade.sdk.bean.dto.PagingUtil;
import com.dwarfeng.subgrade.sdk.bean.dto.ResponseDataUtil;
import com.dwarfeng.subgrade.sdk.interceptor.analyse.BehaviorAnalyse;
import com.dwarfeng.subgrade.sdk.interceptor.analyse.SkipRecord;
import com.dwarfeng.subgrade.sdk.interceptor.login.LoginRequired;
import com.dwarfeng.subgrade.stack.bean.BeanTransformer;
import com.dwarfeng.subgrade.stack.bean.dto.PagedData;
import com.dwarfeng.subgrade.stack.bean.dto.PagingInfo;
import com.dwarfeng.subgrade.stack.bean.key.StringIdKey;
import com.dwarfeng.subgrade.stack.exception.ServiceExceptionMapper;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * 发送执行器支持控制器。
 *
 * @author DwArFeng
 * @since 1.0.7
 */
@RestController
@RequestMapping("/api/v1/notify")
public class SendExecutorSupportController {

    private final SendExecutorSupportResponseService service;
    private final ServiceExceptionMapper sem;

    private final BeanTransformer<SendExecutorSupport, FastJsonSendExecutorSupport> beanTransformer;

    public SendExecutorSupportController(
            SendExecutorSupportResponseService service, ServiceExceptionMapper sem,
            BeanTransformer<SendExecutorSupport, FastJsonSendExecutorSupport> beanTransformer
    ) {
        this.service = service;
        this.sem = sem;
        this.beanTransformer = beanTransformer;
    }

    @GetMapping("/send-executor-support/{id}/exists")
    @BehaviorAnalyse
    @LoginRequired
    public FastJsonResponseData<Boolean> exists(HttpServletRequest request, @PathVariable("id") String id) {
        try {
            boolean exists = service.exists(new StringIdKey(id));
            return FastJsonResponseData.of(ResponseDataUtil.good(exists));
        } catch (Exception e) {
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @GetMapping("/send-executor-support/{id}")
    @BehaviorAnalyse
    @LoginRequired
    public FastJsonResponseData<FastJsonSendExecutorSupport> get(
            HttpServletRequest request, @PathVariable("id") String id
    ) {
        try {
            SendExecutorSupport sendExecutorSupport = service.get(new StringIdKey(id));
            return FastJsonResponseData.of(ResponseDataUtil.good(FastJsonSendExecutorSupport.of(sendExecutorSupport)));
        } catch (Exception e) {
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @GetMapping("/send-executor-support/all")
    @BehaviorAnalyse
    @SkipRecord
    @LoginRequired
    public FastJsonResponseData<JSFixedFastJsonPagedData<FastJsonSendExecutorSupport>> all(
            HttpServletRequest request, @RequestParam("page") int page, @RequestParam("rows") int rows
    ) {
        try {
            PagedData<SendExecutorSupport> all = service.all(new PagingInfo(page, rows));
            PagedData<FastJsonSendExecutorSupport> transform = PagingUtil.transform(all, beanTransformer);
            return FastJsonResponseData.of(ResponseDataUtil.good(JSFixedFastJsonPagedData.of(transform)));
        } catch (Exception e) {
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }
}
