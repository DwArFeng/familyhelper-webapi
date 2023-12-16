package com.dwarfeng.familyhelper.webapi.node.controller.v1.life;

import com.dwarfeng.familyhelper.life.sdk.bean.dto.WebInputActivityTemplateDataInfoCreateInfo;
import com.dwarfeng.familyhelper.life.sdk.bean.dto.WebInputActivityTemplateDataInfoUpdateInfo;
import com.dwarfeng.familyhelper.life.sdk.bean.entity.JSFixedFastJsonActivityTemplateDataInfo;
import com.dwarfeng.familyhelper.life.stack.bean.entity.ActivityTemplateDataInfo;
import com.dwarfeng.familyhelper.webapi.sdk.bean.disp.life.JSFixedFastJsonDispActivityTemplateDataInfo;
import com.dwarfeng.familyhelper.webapi.stack.bean.disp.life.DispActivityTemplateDataInfo;
import com.dwarfeng.familyhelper.webapi.stack.handler.system.TokenHandler;
import com.dwarfeng.familyhelper.webapi.stack.service.life.ActivityTempalteDataInfoResponseService;
import com.dwarfeng.subgrade.sdk.bean.dto.FastJsonResponseData;
import com.dwarfeng.subgrade.sdk.bean.dto.JSFixedFastJsonPagedData;
import com.dwarfeng.subgrade.sdk.bean.dto.PagingUtil;
import com.dwarfeng.subgrade.sdk.bean.dto.ResponseDataUtil;
import com.dwarfeng.subgrade.sdk.bean.key.JSFixedFastJsonLongIdKey;
import com.dwarfeng.subgrade.sdk.bean.key.WebInputLongIdKey;
import com.dwarfeng.subgrade.sdk.interceptor.analyse.BehaviorAnalyse;
import com.dwarfeng.subgrade.sdk.interceptor.analyse.SkipRecord;
import com.dwarfeng.subgrade.sdk.interceptor.http.BindingCheck;
import com.dwarfeng.subgrade.sdk.interceptor.login.LoginRequired;
import com.dwarfeng.subgrade.stack.bean.BeanTransformer;
import com.dwarfeng.subgrade.stack.bean.dto.PagedData;
import com.dwarfeng.subgrade.stack.bean.dto.PagingInfo;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.bean.key.StringIdKey;
import com.dwarfeng.subgrade.stack.exception.ServiceExceptionMapper;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * 活动模板数据信息控制器。
 *
 * @author DwArFeng
 * @since 1.0.10
 */
@RestController
@RequestMapping("/api/v1/life")
public class ActivityTemplateDataInfoController {

    private final ActivityTempalteDataInfoResponseService service;

    private final ServiceExceptionMapper sem;

    private final BeanTransformer<ActivityTemplateDataInfo, JSFixedFastJsonActivityTemplateDataInfo>
            beanTransformer;
    private final BeanTransformer<DispActivityTemplateDataInfo, JSFixedFastJsonDispActivityTemplateDataInfo>
            dispBeanTransformer;

    private final TokenHandler tokenHandler;

    public ActivityTemplateDataInfoController(
            ActivityTempalteDataInfoResponseService service,
            ServiceExceptionMapper sem,
            BeanTransformer<ActivityTemplateDataInfo, JSFixedFastJsonActivityTemplateDataInfo> beanTransformer,
            BeanTransformer<DispActivityTemplateDataInfo, JSFixedFastJsonDispActivityTemplateDataInfo>
                    dispBeanTransformer,
            TokenHandler tokenHandler
    ) {
        this.service = service;
        this.sem = sem;
        this.beanTransformer = beanTransformer;
        this.dispBeanTransformer = dispBeanTransformer;
        this.tokenHandler = tokenHandler;
    }

    @RequestMapping("/activity-template-data-info/{id}/exists")
    @BehaviorAnalyse
    @LoginRequired
    public FastJsonResponseData<Boolean> exists(HttpServletRequest request, @PathVariable("id") Long id) {
        try {
            boolean exists = service.exists(new LongIdKey(id));
            return FastJsonResponseData.of(ResponseDataUtil.good(exists));
        } catch (Exception e) {
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @RequestMapping("/activity-template-data-info/{id}")
    @BehaviorAnalyse
    @LoginRequired
    public FastJsonResponseData<JSFixedFastJsonActivityTemplateDataInfo> get(
            HttpServletRequest request, @PathVariable("id") Long id
    ) {
        try {
            ActivityTemplateDataInfo activityTemplateDataInfo = service.get(new LongIdKey(id));
            return FastJsonResponseData.of(ResponseDataUtil.good(beanTransformer.transform(activityTemplateDataInfo)));
        } catch (Exception e) {
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @GetMapping("/activity-template-data-info/all")
    @BehaviorAnalyse
    @SkipRecord
    @LoginRequired
    public FastJsonResponseData<JSFixedFastJsonPagedData<JSFixedFastJsonActivityTemplateDataInfo>> all(
            HttpServletRequest request, @RequestParam("page") int page, @RequestParam("rows") int rows
    ) {
        try {
            PagedData<ActivityTemplateDataInfo> all = service.all(new PagingInfo(page, rows));
            PagedData<JSFixedFastJsonActivityTemplateDataInfo> transform = PagingUtil.transform(
                    all, beanTransformer
            );
            return FastJsonResponseData.of(ResponseDataUtil.good(JSFixedFastJsonPagedData.of(transform)));
        } catch (Exception e) {
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @GetMapping("/activity-template/{activityTemplateId}/activity-template-data-info")
    @BehaviorAnalyse
    @SkipRecord
    @LoginRequired
    public FastJsonResponseData<JSFixedFastJsonPagedData<JSFixedFastJsonActivityTemplateDataInfo>>
    childForActivityTemplate(
            HttpServletRequest request, @PathVariable("activityTemplateId") Long activityTemplateId,
            @RequestParam("page") int page, @RequestParam("rows") int rows
    ) {
        try {
            PagedData<ActivityTemplateDataInfo> childForActivityTemplate = service.childForActivityTemplate(
                    new LongIdKey(activityTemplateId), new PagingInfo(page, rows)
            );
            PagedData<JSFixedFastJsonActivityTemplateDataInfo> transform = PagingUtil.transform(
                    childForActivityTemplate, beanTransformer
            );
            return FastJsonResponseData.of(ResponseDataUtil.good(JSFixedFastJsonPagedData.of(transform)));
        } catch (Exception e) {
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @RequestMapping("/activity-template-data-info/{id}/disp")
    @BehaviorAnalyse
    @LoginRequired
    public FastJsonResponseData<JSFixedFastJsonDispActivityTemplateDataInfo> getDisp(
            HttpServletRequest request, @PathVariable("id") Long id
    ) {
        try {
            StringIdKey inspectAccountKey = tokenHandler.getAccountKey(request);
            DispActivityTemplateDataInfo dispActivityTemplateDataInfo = service.getDisp(
                    new LongIdKey(id), inspectAccountKey
            );
            return FastJsonResponseData.of(ResponseDataUtil.good(
                    dispBeanTransformer.transform(dispActivityTemplateDataInfo)
            ));
        } catch (Exception e) {
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @GetMapping("/activity-template-data-info/all/disp")
    @BehaviorAnalyse
    @SkipRecord
    @LoginRequired
    public FastJsonResponseData<JSFixedFastJsonPagedData<JSFixedFastJsonDispActivityTemplateDataInfo>> allDisp(
            HttpServletRequest request, @RequestParam("page") int page, @RequestParam("rows") int rows
    ) {
        try {
            StringIdKey inspectAccountKey = tokenHandler.getAccountKey(request);
            PagedData<DispActivityTemplateDataInfo> allDisp = service.allDisp(
                    inspectAccountKey, new PagingInfo(page, rows)
            );
            PagedData<JSFixedFastJsonDispActivityTemplateDataInfo> transform = PagingUtil.transform(
                    allDisp, dispBeanTransformer
            );
            return FastJsonResponseData.of(ResponseDataUtil.good(JSFixedFastJsonPagedData.of(transform)));
        } catch (Exception e) {
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @GetMapping("/activity-template/{activityTemplateId}/activity-template-data-info/disp")
    @BehaviorAnalyse
    @SkipRecord
    @LoginRequired
    public FastJsonResponseData<JSFixedFastJsonPagedData<JSFixedFastJsonDispActivityTemplateDataInfo>>
    childForActivityTemplateDisp(
            HttpServletRequest request, @PathVariable("activityTemplateId") Long activityTemplateId,
            @RequestParam("page") int page, @RequestParam("rows") int rows
    ) {
        try {
            StringIdKey inspectAccountKey = tokenHandler.getAccountKey(request);
            PagedData<DispActivityTemplateDataInfo> childForActivityTemplateDisp = service.childForActivityTemplateDisp(
                    inspectAccountKey, new LongIdKey(activityTemplateId), new PagingInfo(page, rows)
            );
            PagedData<JSFixedFastJsonDispActivityTemplateDataInfo> transform = PagingUtil.transform(
                    childForActivityTemplateDisp, dispBeanTransformer
            );
            return FastJsonResponseData.of(ResponseDataUtil.good(JSFixedFastJsonPagedData.of(transform)));
        } catch (Exception e) {
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @PostMapping("/activity-template-data-info/create")
    @BehaviorAnalyse
    @BindingCheck
    @LoginRequired
    public FastJsonResponseData<JSFixedFastJsonLongIdKey> create(
            HttpServletRequest request,
            @RequestBody @Validated WebInputActivityTemplateDataInfoCreateInfo createInfo,
            BindingResult bindingResult
    ) {
        try {
            StringIdKey accountKey = tokenHandler.getAccountKey(request);
            LongIdKey result = service.create(
                    accountKey, WebInputActivityTemplateDataInfoCreateInfo.toStackBean(createInfo)
            );
            return FastJsonResponseData.of(ResponseDataUtil.good(JSFixedFastJsonLongIdKey.of(result)));
        } catch (Exception e) {
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @PostMapping("/activity-template-data-info/update")
    @BehaviorAnalyse
    @BindingCheck
    @LoginRequired
    public FastJsonResponseData<Void> update(
            HttpServletRequest request,
            @RequestBody @Validated WebInputActivityTemplateDataInfoUpdateInfo updateInfo,
            BindingResult bindingResult
    ) {
        try {
            StringIdKey accountKey = tokenHandler.getAccountKey(request);
            service.update(accountKey, WebInputActivityTemplateDataInfoUpdateInfo.toStackBean(updateInfo));
            return FastJsonResponseData.of(ResponseDataUtil.good(null));
        } catch (Exception e) {
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @PostMapping("/activity-template-data-info/remove")
    @BehaviorAnalyse
    @BindingCheck
    @LoginRequired
    public FastJsonResponseData<Void> remove(
            HttpServletRequest request,
            @RequestBody @Validated WebInputLongIdKey key,
            BindingResult bindingResult
    ) {
        try {
            StringIdKey accountKey = tokenHandler.getAccountKey(request);
            service.remove(accountKey, WebInputLongIdKey.toStackBean(key));
            return FastJsonResponseData.of(ResponseDataUtil.good(null));
        } catch (Exception e) {
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }
}
