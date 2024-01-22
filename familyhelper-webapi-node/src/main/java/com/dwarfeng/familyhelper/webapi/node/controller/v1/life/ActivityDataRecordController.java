package com.dwarfeng.familyhelper.webapi.node.controller.v1.life;

import com.dwarfeng.familyhelper.life.sdk.bean.dto.WebInputActivityDataRecordCreateInfo;
import com.dwarfeng.familyhelper.life.sdk.bean.dto.WebInputActivityDataRecordUpdateInfo;
import com.dwarfeng.familyhelper.life.sdk.bean.entity.JSFixedFastJsonActivityDataRecord;
import com.dwarfeng.familyhelper.life.stack.bean.entity.ActivityDataRecord;
import com.dwarfeng.familyhelper.webapi.sdk.bean.disp.life.JSFixedFastJsonDispActivityDataRecord;
import com.dwarfeng.familyhelper.webapi.stack.bean.disp.life.DispActivityDataRecord;
import com.dwarfeng.familyhelper.webapi.stack.handler.system.TokenHandler;
import com.dwarfeng.familyhelper.webapi.stack.service.life.ActivityDataRecordResponseService;
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
 * 活动数据记录控制器。
 *
 * @author DwArFeng
 * @since 1.0.10
 */
@RestController
@RequestMapping("/api/v1/life")
public class ActivityDataRecordController {

    private final ActivityDataRecordResponseService service;

    private final ServiceExceptionMapper sem;

    private final BeanTransformer<ActivityDataRecord, JSFixedFastJsonActivityDataRecord> beanTransformer;
    private final BeanTransformer<DispActivityDataRecord, JSFixedFastJsonDispActivityDataRecord> dispBeanTransformer;

    private final TokenHandler tokenHandler;

    public ActivityDataRecordController(
            ActivityDataRecordResponseService service,
            ServiceExceptionMapper sem,
            BeanTransformer<ActivityDataRecord, JSFixedFastJsonActivityDataRecord> beanTransformer,
            BeanTransformer<DispActivityDataRecord, JSFixedFastJsonDispActivityDataRecord> dispBeanTransformer,
            TokenHandler tokenHandler
    ) {
        this.service = service;
        this.sem = sem;
        this.beanTransformer = beanTransformer;
        this.dispBeanTransformer = dispBeanTransformer;
        this.tokenHandler = tokenHandler;
    }

    @GetMapping("/activity-data-record/{id}/exists")
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

    @GetMapping("/activity-data-record/{id}")
    @BehaviorAnalyse
    @LoginRequired
    public FastJsonResponseData<JSFixedFastJsonActivityDataRecord> get(
            HttpServletRequest request, @PathVariable("id") Long id
    ) {
        try {
            ActivityDataRecord activityDataRecord = service.get(new LongIdKey(id));
            return FastJsonResponseData.of(ResponseDataUtil.good(
                    JSFixedFastJsonActivityDataRecord.of(activityDataRecord)
            ));
        } catch (Exception e) {
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @GetMapping("/activity-data-record/all")
    @BehaviorAnalyse
    @SkipRecord
    @LoginRequired
    public FastJsonResponseData<JSFixedFastJsonPagedData<JSFixedFastJsonActivityDataRecord>> all(
            HttpServletRequest request, @RequestParam("page") int page, @RequestParam("rows") int rows
    ) {
        try {
            PagedData<ActivityDataRecord> all = service.all(new PagingInfo(page, rows));
            PagedData<JSFixedFastJsonActivityDataRecord> transform = PagingUtil.transform(
                    all, beanTransformer
            );
            return FastJsonResponseData.of(ResponseDataUtil.good(JSFixedFastJsonPagedData.of(transform)));
        } catch (Exception e) {
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @GetMapping("/activity-data-item/{activityDataItemId}/activity-data-record/recorded-date-asc")
    @BehaviorAnalyse
    @SkipRecord
    @LoginRequired
    public FastJsonResponseData<JSFixedFastJsonPagedData<JSFixedFastJsonActivityDataRecord>>
    childForItemRecordedDateAsc(
            @PathVariable("activityDataItemId") Long activityDataItemId,
            @RequestParam("page") int page, @RequestParam("rows") int rows
    ) {
        try {
            PagedData<ActivityDataRecord> childForActivityDataNode = service.childForItemRecordedDateAsc(
                    new LongIdKey(activityDataItemId), new PagingInfo(page, rows)
            );
            PagedData<JSFixedFastJsonActivityDataRecord> transform = PagingUtil.transform(
                    childForActivityDataNode, beanTransformer
            );
            return FastJsonResponseData.of(ResponseDataUtil.good(JSFixedFastJsonPagedData.of(transform)));
        } catch (Exception e) {
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @GetMapping("/activity-data-item/{activityDataItemId}/activity-data-record/recorded-date-desc")
    @BehaviorAnalyse
    @SkipRecord
    @LoginRequired
    public FastJsonResponseData<JSFixedFastJsonPagedData<JSFixedFastJsonActivityDataRecord>>
    childForItemRecordedDateDesc(
            @PathVariable("activityDataItemId") Long activityDataItemId,
            @RequestParam("page") int page, @RequestParam("rows") int rows
    ) {
        try {
            PagedData<ActivityDataRecord> childForActivityDataNode = service.childForItemRecordedDateDesc(
                    new LongIdKey(activityDataItemId), new PagingInfo(page, rows)
            );
            PagedData<JSFixedFastJsonActivityDataRecord> transform = PagingUtil.transform(
                    childForActivityDataNode, beanTransformer
            );
            return FastJsonResponseData.of(ResponseDataUtil.good(JSFixedFastJsonPagedData.of(transform)));
        } catch (Exception e) {
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @GetMapping("/activity/{activityId}/activity-data-record/recorded-date-asc")
    @BehaviorAnalyse
    @SkipRecord
    @LoginRequired
    public FastJsonResponseData<JSFixedFastJsonPagedData<JSFixedFastJsonActivityDataRecord>>
    childForActivityRecordedDateAsc(
            @PathVariable("activityId") Long activityId,
            @RequestParam("page") int page, @RequestParam("rows") int rows
    ) {
        try {
            PagedData<ActivityDataRecord> childForActivityDataNode = service.childForActivityRecordedDateAsc(
                    new LongIdKey(activityId), new PagingInfo(page, rows)
            );
            PagedData<JSFixedFastJsonActivityDataRecord> transform = PagingUtil.transform(
                    childForActivityDataNode, beanTransformer
            );
            return FastJsonResponseData.of(ResponseDataUtil.good(JSFixedFastJsonPagedData.of(transform)));
        } catch (Exception e) {
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @GetMapping("/activity/{activityId}/activity-data-record/recorded-date-desc")
    @BehaviorAnalyse
    @SkipRecord
    @LoginRequired
    public FastJsonResponseData<JSFixedFastJsonPagedData<JSFixedFastJsonActivityDataRecord>>
    childForActivityRecordedDateDesc(
            @PathVariable("activityId") Long activityId,
            @RequestParam("page") int page, @RequestParam("rows") int rows
    ) {
        try {
            PagedData<ActivityDataRecord> childForActivityDataNode = service.childForActivityRecordedDateDesc(
                    new LongIdKey(activityId), new PagingInfo(page, rows)
            );
            PagedData<JSFixedFastJsonActivityDataRecord> transform = PagingUtil.transform(
                    childForActivityDataNode, beanTransformer
            );
            return FastJsonResponseData.of(ResponseDataUtil.good(JSFixedFastJsonPagedData.of(transform)));
        } catch (Exception e) {
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @GetMapping("/activity-data-record/{id}/disp")
    @BehaviorAnalyse
    @SkipRecord
    @LoginRequired
    public FastJsonResponseData<JSFixedFastJsonDispActivityDataRecord> getDisp(
            HttpServletRequest request, @PathVariable("id") Long id
    ) {
        try {
            StringIdKey inspectAccountKey = tokenHandler.getAccountKey(request);
            DispActivityDataRecord dispActivityDataRecord = service.getDisp(new LongIdKey(id), inspectAccountKey);
            return FastJsonResponseData.of(
                    ResponseDataUtil.good(JSFixedFastJsonDispActivityDataRecord.of(dispActivityDataRecord))
            );
        } catch (Exception e) {
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @GetMapping("/activity-data-record/all/disp")
    @BehaviorAnalyse
    @SkipRecord
    @LoginRequired
    public FastJsonResponseData<JSFixedFastJsonPagedData<JSFixedFastJsonDispActivityDataRecord>> allDisp(
            HttpServletRequest request, @RequestParam("page") int page, @RequestParam("rows") int rows) {
        try {
            StringIdKey accountKey = tokenHandler.getAccountKey(request);
            PagedData<DispActivityDataRecord> allDisp = service.allDisp(accountKey, new PagingInfo(page, rows));
            PagedData<JSFixedFastJsonDispActivityDataRecord> transform = PagingUtil.transform(
                    allDisp, dispBeanTransformer);
            return FastJsonResponseData.of(ResponseDataUtil.good(JSFixedFastJsonPagedData.of(transform)));
        } catch (Exception e) {
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @GetMapping("/activity-data-item/{activityDataItemId}/activity-data-record/recorded-date-asc/disp")
    @BehaviorAnalyse
    @SkipRecord
    @LoginRequired
    public FastJsonResponseData<JSFixedFastJsonPagedData<JSFixedFastJsonDispActivityDataRecord>>
    childForItemRecordedDateAscDisp(
            HttpServletRequest request,
            @PathVariable("activityDataItemId") Long activityDataItemId,
            @RequestParam("page") int page, @RequestParam("rows") int rows
    ) {
        try {
            StringIdKey accountKey = tokenHandler.getAccountKey(request);
            PagedData<DispActivityDataRecord> childForActivityDataNode = service.childForItemRecordedDateAscDisp(
                    accountKey, new LongIdKey(activityDataItemId), new PagingInfo(page, rows)
            );
            PagedData<JSFixedFastJsonDispActivityDataRecord> transform = PagingUtil.transform(
                    childForActivityDataNode, dispBeanTransformer
            );
            return FastJsonResponseData.of(ResponseDataUtil.good(JSFixedFastJsonPagedData.of(transform)));
        } catch (Exception e) {
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @GetMapping("/activity-data-item/{activityDataItemId}/activity-data-record/recorded-date-desc/disp")
    @BehaviorAnalyse
    @SkipRecord
    @LoginRequired
    public FastJsonResponseData<JSFixedFastJsonPagedData<JSFixedFastJsonDispActivityDataRecord>>
    childForItemRecordedDateDescDisp(
            HttpServletRequest request,
            @PathVariable("activityDataItemId") Long activityDataItemId,
            @RequestParam("page") int page, @RequestParam("rows") int rows
    ) {
        try {
            StringIdKey accountKey = tokenHandler.getAccountKey(request);
            PagedData<DispActivityDataRecord> childForActivityDataNode = service.childForItemRecordedDateDescDisp(
                    accountKey, new LongIdKey(activityDataItemId), new PagingInfo(page, rows)
            );
            PagedData<JSFixedFastJsonDispActivityDataRecord> transform = PagingUtil.transform(
                    childForActivityDataNode, dispBeanTransformer
            );
            return FastJsonResponseData.of(ResponseDataUtil.good(JSFixedFastJsonPagedData.of(transform)));
        } catch (Exception e) {
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @GetMapping("/activity/{activityId}/activity-data-record/recorded-date-asc/disp")
    @BehaviorAnalyse
    @SkipRecord
    @LoginRequired
    public FastJsonResponseData<JSFixedFastJsonPagedData<JSFixedFastJsonDispActivityDataRecord>>
    childForActivityRecordedDateAscDisp(
            HttpServletRequest request,
            @PathVariable("activityId") Long activityId,
            @RequestParam("page") int page, @RequestParam("rows") int rows
    ) {
        try {
            StringIdKey accountKey = tokenHandler.getAccountKey(request);
            PagedData<DispActivityDataRecord> childForActivityDataNode = service.childForActivityRecordedDateAscDisp(
                    accountKey, new LongIdKey(activityId), new PagingInfo(page, rows)
            );
            PagedData<JSFixedFastJsonDispActivityDataRecord> transform = PagingUtil.transform(
                    childForActivityDataNode, dispBeanTransformer
            );
            return FastJsonResponseData.of(ResponseDataUtil.good(JSFixedFastJsonPagedData.of(transform)));
        } catch (Exception e) {
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @GetMapping("/activity/{activityId}/activity-data-record/recorded-date-desc/disp")
    @BehaviorAnalyse
    @SkipRecord
    @LoginRequired
    public FastJsonResponseData<JSFixedFastJsonPagedData<JSFixedFastJsonDispActivityDataRecord>>
    childForActivityRecordedDateDescDisp(
            HttpServletRequest request,
            @PathVariable("activityId") Long activityId,
            @RequestParam("page") int page, @RequestParam("rows") int rows
    ) {
        try {
            StringIdKey accountKey = tokenHandler.getAccountKey(request);
            PagedData<DispActivityDataRecord> childForActivityDataNode = service.childForActivityRecordedDateDescDisp(
                    accountKey, new LongIdKey(activityId), new PagingInfo(page, rows)
            );
            PagedData<JSFixedFastJsonDispActivityDataRecord> transform = PagingUtil.transform(
                    childForActivityDataNode, dispBeanTransformer
            );
            return FastJsonResponseData.of(ResponseDataUtil.good(JSFixedFastJsonPagedData.of(transform)));
        } catch (Exception e) {
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @PostMapping("/activity-data-record/create")
    @BehaviorAnalyse
    @BindingCheck
    @LoginRequired
    public FastJsonResponseData<JSFixedFastJsonLongIdKey> create(
            HttpServletRequest request,
            @RequestBody @Validated WebInputActivityDataRecordCreateInfo createInfo,
            BindingResult bindingResult
    ) {
        try {
            StringIdKey accountKey = tokenHandler.getAccountKey(request);
            LongIdKey result = service.create(
                    accountKey, WebInputActivityDataRecordCreateInfo.toStackBean(createInfo)
            );
            return FastJsonResponseData.of(ResponseDataUtil.good(JSFixedFastJsonLongIdKey.of(result)));
        } catch (Exception e) {
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @PostMapping("/activity-data-record/update")
    @BehaviorAnalyse
    @BindingCheck
    @LoginRequired
    public FastJsonResponseData<Void> update(
            HttpServletRequest request,
            @RequestBody @Validated WebInputActivityDataRecordUpdateInfo updateInfo,
            BindingResult bindingResult
    ) {
        try {
            StringIdKey accountKey = tokenHandler.getAccountKey(request);
            service.update(accountKey, WebInputActivityDataRecordUpdateInfo.toStackBean(updateInfo));
            return FastJsonResponseData.of(ResponseDataUtil.good(null));
        } catch (Exception e) {
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @PostMapping("/activity-data-record/remove")
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
