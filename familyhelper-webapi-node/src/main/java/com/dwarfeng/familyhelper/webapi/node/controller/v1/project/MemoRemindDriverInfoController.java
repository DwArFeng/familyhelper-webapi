package com.dwarfeng.familyhelper.webapi.node.controller.v1.project;

import com.dwarfeng.familyhelper.project.sdk.bean.entity.JSFixedFastJsonMemoRemindDriverInfo;
import com.dwarfeng.familyhelper.project.sdk.bean.entity.WebInputMemoRemindDriverInfo;
import com.dwarfeng.familyhelper.project.stack.bean.entity.MemoRemindDriverInfo;
import com.dwarfeng.familyhelper.webapi.stack.service.project.MemoRemindDriverInfoResponseService;
import com.dwarfeng.subgrade.sdk.bean.dto.FastJsonResponseData;
import com.dwarfeng.subgrade.sdk.bean.dto.JSFixedFastJsonPagedData;
import com.dwarfeng.subgrade.sdk.bean.dto.PagingUtil;
import com.dwarfeng.subgrade.sdk.bean.dto.ResponseDataUtil;
import com.dwarfeng.subgrade.sdk.bean.key.FastJsonLongIdKey;
import com.dwarfeng.subgrade.sdk.interceptor.analyse.BehaviorAnalyse;
import com.dwarfeng.subgrade.sdk.interceptor.analyse.SkipRecord;
import com.dwarfeng.subgrade.sdk.interceptor.http.BindingCheck;
import com.dwarfeng.subgrade.sdk.interceptor.login.LoginRequired;
import com.dwarfeng.subgrade.sdk.validation.group.Insert;
import com.dwarfeng.subgrade.stack.bean.BeanTransformer;
import com.dwarfeng.subgrade.stack.bean.dto.PagedData;
import com.dwarfeng.subgrade.stack.bean.dto.PagingInfo;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.exception.ServiceExceptionMapper;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * 备忘录提醒驱动器信息控制器。
 *
 * @author DwArFeng
 * @since 1.0.9
 */
@RestController("notifyMemoRemindDriverInfoController")
@RequestMapping("/api/v1/project")
public class MemoRemindDriverInfoController {

    private final MemoRemindDriverInfoResponseService service;
    private final ServiceExceptionMapper sem;

    private final BeanTransformer<MemoRemindDriverInfo, JSFixedFastJsonMemoRemindDriverInfo> beanTransformer;

    public MemoRemindDriverInfoController(
            MemoRemindDriverInfoResponseService service, ServiceExceptionMapper sem,
            BeanTransformer<MemoRemindDriverInfo, JSFixedFastJsonMemoRemindDriverInfo> beanTransformer
    ) {
        this.service = service;
        this.sem = sem;
        this.beanTransformer = beanTransformer;
    }

    @GetMapping("/memo-remind-driver-info/{id}/exists")
    @BehaviorAnalyse
    @LoginRequired
    public FastJsonResponseData<Boolean> exists(HttpServletRequest request, @PathVariable("id") long id) {
        try {
            boolean exists = service.exists(new LongIdKey(id));
            return FastJsonResponseData.of(ResponseDataUtil.good(exists));
        } catch (Exception e) {
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @GetMapping("/memo-remind-driver-info/{id}")
    @BehaviorAnalyse
    @LoginRequired
    public FastJsonResponseData<JSFixedFastJsonMemoRemindDriverInfo> get(
            HttpServletRequest request, @PathVariable("id") long id
    ) {
        try {
            MemoRemindDriverInfo memoRemindDriverInfo = service.get(new LongIdKey(id));
            return FastJsonResponseData.of(ResponseDataUtil.good(
                    JSFixedFastJsonMemoRemindDriverInfo.of(memoRemindDriverInfo)
            ));
        } catch (Exception e) {
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @PostMapping("/memo-remind-driver-info")
    @BehaviorAnalyse
    @BindingCheck
    @LoginRequired
    public FastJsonResponseData<FastJsonLongIdKey> insert(
            HttpServletRequest request,
            @RequestBody @Validated(Insert.class) WebInputMemoRemindDriverInfo webInputMemoRemindDriverInfo,
            BindingResult bindingResult
    ) {
        try {
            MemoRemindDriverInfo memoRemindDriverInfo = WebInputMemoRemindDriverInfo.toStackBean(webInputMemoRemindDriverInfo);
            LongIdKey insert = service.insert(memoRemindDriverInfo);
            return FastJsonResponseData.of(ResponseDataUtil.good(FastJsonLongIdKey.of(insert)));
        } catch (Exception e) {
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @PatchMapping("/memo-remind-driver-info")
    @BehaviorAnalyse
    @BindingCheck
    @LoginRequired
    public FastJsonResponseData<Object> update(
            HttpServletRequest request,
            @RequestBody @Validated WebInputMemoRemindDriverInfo webInputMemoRemindDriverInfo,
            BindingResult bindingResult
    ) {
        try {
            service.update(WebInputMemoRemindDriverInfo.toStackBean(webInputMemoRemindDriverInfo));
            return FastJsonResponseData.of(ResponseDataUtil.good(null));
        } catch (Exception e) {
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @DeleteMapping("/memo-remind-driver-info/{id}")
    @BehaviorAnalyse
    @LoginRequired
    public FastJsonResponseData<Object> delete(HttpServletRequest request, @PathVariable("id") long id) {
        try {
            service.delete(new LongIdKey(id));
            return FastJsonResponseData.of(ResponseDataUtil.good(null));
        } catch (Exception e) {
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @GetMapping("/memo/{memoId}/memo-remind-driver-info")
    @BehaviorAnalyse
    @SkipRecord
    @LoginRequired
    public FastJsonResponseData<JSFixedFastJsonPagedData<JSFixedFastJsonMemoRemindDriverInfo>> childForMemo(
            HttpServletRequest request, @PathVariable("memoId") Long memoId,
            @RequestParam("page") int page, @RequestParam("rows") int rows
    ) {
        try {
            PagedData<MemoRemindDriverInfo> all = service.childForMemo(
                    new LongIdKey(memoId), new PagingInfo(page, rows)
            );
            PagedData<JSFixedFastJsonMemoRemindDriverInfo> transform = PagingUtil.transform(all, beanTransformer);
            return FastJsonResponseData.of(ResponseDataUtil.good(JSFixedFastJsonPagedData.of(transform)));
        } catch (Exception e) {
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }
}
