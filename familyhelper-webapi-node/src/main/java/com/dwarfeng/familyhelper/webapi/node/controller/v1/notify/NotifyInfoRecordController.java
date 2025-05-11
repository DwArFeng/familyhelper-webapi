package com.dwarfeng.familyhelper.webapi.node.controller.v1.notify;

import com.dwarfeng.familyhelper.webapi.sdk.bean.notify.disp.JSFixedFastJsonDispNotifyInfoRecord;
import com.dwarfeng.familyhelper.webapi.stack.bean.notify.disp.DispNotifyInfoRecord;
import com.dwarfeng.familyhelper.webapi.stack.service.notify.NotifyInfoRecordResponseService;
import com.dwarfeng.notify.sdk.bean.entity.JSFixedFastJsonNotifyInfoRecord;
import com.dwarfeng.notify.stack.bean.entity.NotifyInfoRecord;
import com.dwarfeng.notify.stack.bean.key.NotifyInfoRecordKey;
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
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.exception.ServiceExceptionMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

/**
 * 通知信息记录控制器。
 *
 * @author DwArFeng
 * @since 1.0.9
 */
@RestController
@RequestMapping("/api/v1/notify")
public class NotifyInfoRecordController {

    private static final Logger LOGGER = LoggerFactory.getLogger(NotifyInfoRecordController.class);

    private final NotifyInfoRecordResponseService service;
    private final ServiceExceptionMapper sem;

    private final BeanTransformer<NotifyInfoRecord, JSFixedFastJsonNotifyInfoRecord> beanTransformer;
    private final BeanTransformer<DispNotifyInfoRecord, JSFixedFastJsonDispNotifyInfoRecord> dispBeanTransformer;

    public NotifyInfoRecordController(
            NotifyInfoRecordResponseService service, ServiceExceptionMapper sem,
            BeanTransformer<NotifyInfoRecord, JSFixedFastJsonNotifyInfoRecord> beanTransformer,
            BeanTransformer<DispNotifyInfoRecord, JSFixedFastJsonDispNotifyInfoRecord> dispBeanTransformer
    ) {
        this.service = service;
        this.sem = sem;
        this.beanTransformer = beanTransformer;
        this.dispBeanTransformer = dispBeanTransformer;
    }

    @GetMapping("/notify-info-record/{notifyHistoryId}&{type}&{recordId}/exists")
    @BehaviorAnalyse
    @LoginRequired
    public FastJsonResponseData<Boolean> exists(
            HttpServletRequest request,
            @PathVariable("notifyHistoryId") long notifyHistoryId, @PathVariable("type") int type,
            @PathVariable("recordId") String recordId
    ) {
        try {
            boolean exists = service.exists(new NotifyInfoRecordKey(notifyHistoryId, type, recordId));
            return FastJsonResponseData.of(ResponseDataUtil.good(exists));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @GetMapping("/notify-info-record/{notifyHistoryId}&{type}&{recordId}")
    @BehaviorAnalyse
    @LoginRequired
    public FastJsonResponseData<JSFixedFastJsonNotifyInfoRecord> get(
            HttpServletRequest request,
            @PathVariable("notifyHistoryId") long notifyHistoryId, @PathVariable("type") int type,
            @PathVariable("recordId") String recordId
    ) {
        try {
            NotifyInfoRecord notifyInfoRecord = service.get(new NotifyInfoRecordKey(notifyHistoryId, type, recordId));
            return FastJsonResponseData.of(ResponseDataUtil.good(
                    JSFixedFastJsonNotifyInfoRecord.of(notifyInfoRecord)
            ));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @DeleteMapping("/notify-info-record/{notifyHistoryId}&{type}&{recordId}")
    @BehaviorAnalyse
    @LoginRequired
    public FastJsonResponseData<Object> delete(
            HttpServletRequest request,
            @PathVariable("notifyHistoryId") long notifyHistoryId, @PathVariable("type") int type,
            @PathVariable("recordId") String recordId
    ) {
        try {
            service.delete(new NotifyInfoRecordKey(notifyHistoryId, type, recordId));
            return FastJsonResponseData.of(ResponseDataUtil.good(null));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @GetMapping(value = {
            "/notify-history/{notifyHistoryId}/notify-info-record", "/notify-history//notify-info-record"
    })
    @BehaviorAnalyse
    @SkipRecord
    @LoginRequired
    public FastJsonResponseData<JSFixedFastJsonPagedData<JSFixedFastJsonNotifyInfoRecord>> childForNotifyHistory(
            HttpServletRequest request,
            @PathVariable(required = false, value = "notifyHistoryId") Long notifySettingId,
            @RequestParam("page") int page, @RequestParam("rows") int rows
    ) {
        try {
            LongIdKey notifySettingKey = null;
            if (Objects.nonNull(notifySettingId)) {
                notifySettingKey = new LongIdKey(notifySettingId);
            }
            PagedData<NotifyInfoRecord> childForNotifySetting = service.childForNotifyHistory(
                    notifySettingKey, new PagingInfo(page, rows)
            );
            PagedData<JSFixedFastJsonNotifyInfoRecord> transform = PagingUtil.transform(
                    childForNotifySetting, beanTransformer
            );
            return FastJsonResponseData.of(ResponseDataUtil.good(JSFixedFastJsonPagedData.of(transform)));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @GetMapping("/notify-info-record/{notifyHistoryId}&{type}&{recordId}/disp")
    @BehaviorAnalyse
    @LoginRequired
    public FastJsonResponseData<JSFixedFastJsonDispNotifyInfoRecord> getDisp(
            HttpServletRequest request,
            @PathVariable("notifyHistoryId") long notifyHistoryId, @PathVariable("type") int type,
            @PathVariable("recordId") String recordId
    ) {
        try {
            DispNotifyInfoRecord notifyInfoRecord = service.getDisp(
                    new NotifyInfoRecordKey(notifyHistoryId, type, recordId)
            );
            return FastJsonResponseData.of(ResponseDataUtil.good(
                    JSFixedFastJsonDispNotifyInfoRecord.of(notifyInfoRecord)
            ));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @GetMapping(value = {
            "/notify-history/{notifyHistoryId}/notify-info-record/disp",
            "/notify-history//notify-info-record/disp"
    })
    @BehaviorAnalyse
    @SkipRecord
    @LoginRequired
    public FastJsonResponseData<JSFixedFastJsonPagedData<JSFixedFastJsonDispNotifyInfoRecord>>
    childForNotifyHistoryDisp(
            HttpServletRequest request,
            @PathVariable(required = false, value = "notifyHistoryId") Long notifySettingId,
            @RequestParam("page") int page, @RequestParam("rows") int rows
    ) {
        try {
            LongIdKey notifySettingKey = null;
            if (Objects.nonNull(notifySettingId)) {
                notifySettingKey = new LongIdKey(notifySettingId);
            }
            PagedData<DispNotifyInfoRecord> childForNotifySetting = service.childForNotifyHistoryDisp(
                    notifySettingKey, new PagingInfo(page, rows)
            );
            PagedData<JSFixedFastJsonDispNotifyInfoRecord> transform = PagingUtil.transform(
                    childForNotifySetting, dispBeanTransformer
            );
            return FastJsonResponseData.of(ResponseDataUtil.good(JSFixedFastJsonPagedData.of(transform)));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }
}
