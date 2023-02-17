package com.dwarfeng.familyhelper.webapi.node.controller.v1.notify;

import com.dwarfeng.familyhelper.webapi.stack.service.notify.SenderInfoResponseService;
import com.dwarfeng.notify.sdk.bean.entity.JSFixedFastJsonSenderInfo;
import com.dwarfeng.notify.sdk.bean.entity.WebInputSenderInfo;
import com.dwarfeng.notify.sdk.bean.key.FastJsonSenderInfoKey;
import com.dwarfeng.notify.stack.bean.entity.SenderInfo;
import com.dwarfeng.notify.stack.bean.key.SenderInfoKey;
import com.dwarfeng.subgrade.sdk.bean.dto.FastJsonResponseData;
import com.dwarfeng.subgrade.sdk.bean.dto.JSFixedFastJsonPagedData;
import com.dwarfeng.subgrade.sdk.bean.dto.PagingUtil;
import com.dwarfeng.subgrade.sdk.bean.dto.ResponseDataUtil;
import com.dwarfeng.subgrade.sdk.interceptor.analyse.BehaviorAnalyse;
import com.dwarfeng.subgrade.sdk.interceptor.analyse.SkipRecord;
import com.dwarfeng.subgrade.sdk.interceptor.http.BindingCheck;
import com.dwarfeng.subgrade.sdk.interceptor.login.LoginRequired;
import com.dwarfeng.subgrade.sdk.validation.group.Insert;
import com.dwarfeng.subgrade.stack.bean.BeanTransformer;
import com.dwarfeng.subgrade.stack.bean.dto.PagedData;
import com.dwarfeng.subgrade.stack.bean.dto.PagingInfo;
import com.dwarfeng.subgrade.stack.exception.ServiceExceptionMapper;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * 发送器信息控制器。
 *
 * @author DwArFeng
 * @since 1.0.7
 */
@RestController
@RequestMapping("/api/v1/notify")
public class SenderInfoController {

    private final SenderInfoResponseService service;
    private final ServiceExceptionMapper sem;

    private final BeanTransformer<SenderInfo, JSFixedFastJsonSenderInfo> beanTransformer;

    public SenderInfoController(
            SenderInfoResponseService service, ServiceExceptionMapper sem,
            BeanTransformer<SenderInfo, JSFixedFastJsonSenderInfo> beanTransformer
    ) {
        this.service = service;
        this.sem = sem;
        this.beanTransformer = beanTransformer;
    }

    @GetMapping("/sender-info/{notifySettingId}&{topicId}/exists")
    @BehaviorAnalyse
    @LoginRequired
    public FastJsonResponseData<Boolean> exists(
            HttpServletRequest request,
            @PathVariable("notifySettingId") long notifySettingId, @PathVariable("topicId") String topicId
    ) {
        try {
            boolean exists = service.exists(new SenderInfoKey(notifySettingId, topicId));
            return FastJsonResponseData.of(ResponseDataUtil.good(exists));
        } catch (Exception e) {
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @GetMapping("/sender-info/{notifySettingId}&{topicId}")
    @BehaviorAnalyse
    @LoginRequired
    public FastJsonResponseData<JSFixedFastJsonSenderInfo> get(
            HttpServletRequest request,
            @PathVariable("notifySettingId") long notifySettingId, @PathVariable("topicId") String topicId
    ) {
        try {
            SenderInfo senderInfo = service.get(new SenderInfoKey(notifySettingId, topicId));
            return FastJsonResponseData.of(ResponseDataUtil.good(
                    JSFixedFastJsonSenderInfo.of(senderInfo)
            ));
        } catch (Exception e) {
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @PostMapping("/sender-info")
    @BehaviorAnalyse
    @BindingCheck
    @LoginRequired
    public FastJsonResponseData<FastJsonSenderInfoKey> insert(
            HttpServletRequest request,
            @RequestBody @Validated(Insert.class) WebInputSenderInfo webInputSenderInfo,
            BindingResult bindingResult
    ) {
        try {
            SenderInfo senderInfo = WebInputSenderInfo.toStackBean(
                    webInputSenderInfo
            );
            SenderInfoKey insert = service.insert(senderInfo);
            return FastJsonResponseData.of(ResponseDataUtil.good(FastJsonSenderInfoKey.of(insert)));
        } catch (Exception e) {
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @PatchMapping("/sender-info")
    @BehaviorAnalyse
    @BindingCheck
    @LoginRequired
    public FastJsonResponseData<Object> update(
            HttpServletRequest request,
            @RequestBody @Validated WebInputSenderInfo webInputSenderInfo,
            BindingResult bindingResult
    ) {
        try {
            service.update(WebInputSenderInfo.toStackBean(webInputSenderInfo));
            return FastJsonResponseData.of(ResponseDataUtil.good(null));
        } catch (Exception e) {
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @DeleteMapping("/sender-info/{notifySettingId}&{topicId}")
    @BehaviorAnalyse
    @LoginRequired
    public FastJsonResponseData<Object> delete(
            HttpServletRequest request,
            @PathVariable("notifySettingId") long notifySettingId, @PathVariable("topicId") String topicId
    ) {
        try {
            service.delete(new SenderInfoKey(notifySettingId, topicId));
            return FastJsonResponseData.of(ResponseDataUtil.good(null));
        } catch (Exception e) {
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @GetMapping("/sender-info/all")
    @BehaviorAnalyse
    @SkipRecord
    @LoginRequired
    public FastJsonResponseData<JSFixedFastJsonPagedData<JSFixedFastJsonSenderInfo>> all(
            HttpServletRequest request, @RequestParam("page") int page, @RequestParam("rows") int rows
    ) {
        try {
            PagedData<SenderInfo> all = service.all(new PagingInfo(page, rows));
            PagedData<JSFixedFastJsonSenderInfo> transform = PagingUtil.transform(all, beanTransformer);
            return FastJsonResponseData.of(ResponseDataUtil.good(JSFixedFastJsonPagedData.of(transform)));
        } catch (Exception e) {
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @GetMapping("/sender-info/type-equals")
    @BehaviorAnalyse
    @SkipRecord
    @LoginRequired
    public FastJsonResponseData<JSFixedFastJsonPagedData<JSFixedFastJsonSenderInfo>> typeEquals(
            HttpServletRequest request,
            @RequestParam("pattern") String pattern, @RequestParam("page") int page, @RequestParam("rows") int rows
    ) {
        try {
            PagedData<SenderInfo> typeEquals = service.typeEquals(pattern, new PagingInfo(page, rows));
            PagedData<JSFixedFastJsonSenderInfo> transform = PagingUtil.transform(typeEquals, beanTransformer);
            return FastJsonResponseData.of(ResponseDataUtil.good(JSFixedFastJsonPagedData.of(transform)));
        } catch (Exception e) {
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }
}
