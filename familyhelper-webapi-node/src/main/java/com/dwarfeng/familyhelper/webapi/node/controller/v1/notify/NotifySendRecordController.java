package com.dwarfeng.familyhelper.webapi.node.controller.v1.notify;

import com.dwarfeng.familyhelper.webapi.sdk.bean.disp.notify.JSFixedFastJsonDispNotifySendRecord;
import com.dwarfeng.familyhelper.webapi.stack.bean.disp.notify.DispNotifySendRecord;
import com.dwarfeng.familyhelper.webapi.stack.handler.system.TokenHandler;
import com.dwarfeng.familyhelper.webapi.stack.service.notify.NotifySendRecordResponseService;
import com.dwarfeng.notify.sdk.bean.entity.JSFixedFastJsonNotifySendRecord;
import com.dwarfeng.notify.stack.bean.entity.NotifySendRecord;
import com.dwarfeng.notify.stack.bean.key.NotifySendRecordKey;
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
import com.dwarfeng.subgrade.stack.bean.key.StringIdKey;
import com.dwarfeng.subgrade.stack.exception.ServiceExceptionMapper;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

/**
 * 通知发送记录控制器。
 *
 * @author DwArFeng
 * @since 1.0.9
 */
@RestController
@RequestMapping("/api/v1/notify")
public class NotifySendRecordController {

    private final NotifySendRecordResponseService service;
    private final ServiceExceptionMapper sem;

    private final BeanTransformer<NotifySendRecord, JSFixedFastJsonNotifySendRecord> beanTransformer;
    private final BeanTransformer<DispNotifySendRecord, JSFixedFastJsonDispNotifySendRecord> dispBeanTransformer;

    private final TokenHandler tokenHandler;

    public NotifySendRecordController(
            NotifySendRecordResponseService service, ServiceExceptionMapper sem,
            BeanTransformer<NotifySendRecord, JSFixedFastJsonNotifySendRecord> beanTransformer,
            BeanTransformer<DispNotifySendRecord, JSFixedFastJsonDispNotifySendRecord> dispBeanTransformer,
            TokenHandler tokenHandler
    ) {
        this.service = service;
        this.sem = sem;
        this.beanTransformer = beanTransformer;
        this.dispBeanTransformer = dispBeanTransformer;
        this.tokenHandler = tokenHandler;
    }

    @GetMapping("/notify-send-record/{notifyHistoryId}&{topicId}&{userId}/exists")
    @BehaviorAnalyse
    @LoginRequired
    public FastJsonResponseData<Boolean> exists(
            HttpServletRequest request,
            @PathVariable("notifyHistoryId") long notifyHistoryId, @PathVariable("topicId") String topicId,
            @PathVariable("userId") String userId
    ) {
        try {
            boolean exists = service.exists(new NotifySendRecordKey(notifyHistoryId, topicId, userId));
            return FastJsonResponseData.of(ResponseDataUtil.good(exists));
        } catch (Exception e) {
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @GetMapping("/notify-send-record/{notifyHistoryId}&{topicId}&{userId}")
    @BehaviorAnalyse
    @LoginRequired
    public FastJsonResponseData<JSFixedFastJsonNotifySendRecord> get(
            HttpServletRequest request,
            @PathVariable("notifyHistoryId") long notifyHistoryId, @PathVariable("topicId") String topicId,
            @PathVariable("userId") String userId
    ) {
        try {
            NotifySendRecord notifySendRecord = service.get(new NotifySendRecordKey(notifyHistoryId, topicId, userId));
            return FastJsonResponseData.of(ResponseDataUtil.good(
                    JSFixedFastJsonNotifySendRecord.of(notifySendRecord)
            ));
        } catch (Exception e) {
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @DeleteMapping("/notify-send-record/{notifyHistoryId}&{topicId}&{userId}")
    @BehaviorAnalyse
    @LoginRequired
    public FastJsonResponseData<Object> delete(
            HttpServletRequest request,
            @PathVariable("notifyHistoryId") long notifyHistoryId, @PathVariable("topicId") String topicId,
            @PathVariable("userId") String userId
    ) {
        try {
            service.delete(new NotifySendRecordKey(notifyHistoryId, topicId, userId));
            return FastJsonResponseData.of(ResponseDataUtil.good(null));
        } catch (Exception e) {
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @GetMapping(value = {
            "/notify-history/{notifyHistoryId}/notify-send-record", "/notify-history//notify-send-record"
    })
    @BehaviorAnalyse
    @SkipRecord
    @LoginRequired
    public FastJsonResponseData<JSFixedFastJsonPagedData<JSFixedFastJsonNotifySendRecord>> childForNotifyHistory(
            HttpServletRequest request,
            @PathVariable(required = false, value = "notifyHistoryId") Long notifySettingId,
            @RequestParam("page") int page, @RequestParam("rows") int rows
    ) {
        try {
            LongIdKey notifySettingKey = null;
            if (Objects.nonNull(notifySettingId)) {
                notifySettingKey = new LongIdKey(notifySettingId);
            }
            PagedData<NotifySendRecord> childForNotifySetting = service.childForNotifyHistory(
                    notifySettingKey, new PagingInfo(page, rows)
            );
            PagedData<JSFixedFastJsonNotifySendRecord> transform = PagingUtil.transform(
                    childForNotifySetting, beanTransformer
            );
            return FastJsonResponseData.of(ResponseDataUtil.good(JSFixedFastJsonPagedData.of(transform)));
        } catch (Exception e) {
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @GetMapping("/notify-send-record/{notifyHistoryId}&{topicId}&{userId}/disp")
    @BehaviorAnalyse
    @LoginRequired
    public FastJsonResponseData<JSFixedFastJsonDispNotifySendRecord> getDisp(
            HttpServletRequest request,
            @PathVariable("notifyHistoryId") long notifyHistoryId, @PathVariable("topicId") String topicId,
            @PathVariable("userId") String userId
    ) {
        try {
            StringIdKey inspectAccountKey = tokenHandler.getAccountKey(request);
            DispNotifySendRecord notifySendRecord = service.getDisp(
                    new NotifySendRecordKey(notifyHistoryId, topicId, userId), inspectAccountKey
            );
            return FastJsonResponseData.of(ResponseDataUtil.good(
                    JSFixedFastJsonDispNotifySendRecord.of(notifySendRecord)
            ));
        } catch (Exception e) {
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @GetMapping(value = {
            "/notify-history/{notifyHistoryId}/notify-send-record/disp",
            "/notify-history//notify-send-record/disp"
    })
    @BehaviorAnalyse
    @SkipRecord
    @LoginRequired
    public FastJsonResponseData<JSFixedFastJsonPagedData<JSFixedFastJsonDispNotifySendRecord>>
    childForNotifyHistoryDisp(
            HttpServletRequest request,
            @PathVariable(required = false, value = "notifyHistoryId") Long notifySettingId,
            @RequestParam("page") int page, @RequestParam("rows") int rows
    ) {
        try {
            StringIdKey inspectAccountKey = tokenHandler.getAccountKey(request);
            LongIdKey notifySettingKey = null;
            if (Objects.nonNull(notifySettingId)) {
                notifySettingKey = new LongIdKey(notifySettingId);
            }
            PagedData<DispNotifySendRecord> childForNotifySetting = service.childForNotifyHistoryDisp(
                    notifySettingKey, new PagingInfo(page, rows), inspectAccountKey
            );
            PagedData<JSFixedFastJsonDispNotifySendRecord> transform = PagingUtil.transform(
                    childForNotifySetting, dispBeanTransformer
            );
            return FastJsonResponseData.of(ResponseDataUtil.good(JSFixedFastJsonPagedData.of(transform)));
        } catch (Exception e) {
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }
}
