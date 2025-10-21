package com.dwarfeng.familyhelper.webapi.node.controller.v1.life;

import com.dwarfeng.familyhelper.life.sdk.bean.dto.WebInputPbRecordCreateInfo;
import com.dwarfeng.familyhelper.life.sdk.bean.dto.WebInputPbRecordUpdateInfo;
import com.dwarfeng.familyhelper.life.sdk.bean.entity.JSFixedFastJsonPbRecord;
import com.dwarfeng.familyhelper.life.stack.bean.entity.PbRecord;
import com.dwarfeng.familyhelper.webapi.stack.handler.system.TokenHandler;
import com.dwarfeng.familyhelper.webapi.stack.service.life.PbRecordResponseService;
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
import com.dwarfeng.subgrade.sdk.interceptor.permission.PermissionRequired;
import com.dwarfeng.subgrade.stack.bean.BeanTransformer;
import com.dwarfeng.subgrade.stack.bean.dto.PagedData;
import com.dwarfeng.subgrade.stack.bean.dto.PagingInfo;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.bean.key.StringIdKey;
import com.dwarfeng.subgrade.stack.exception.ServiceExceptionMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

/**
 * 个人最佳记录控制器。
 *
 * @author DwArFeng
 * @since 1.0.7
 */
@RestController
@RequestMapping("/api/v1/life")
public class PbRecordController {

    private static final Logger LOGGER = LoggerFactory.getLogger(PbRecordController.class);

    private final PbRecordResponseService service;

    private final ServiceExceptionMapper sem;

    private final BeanTransformer<PbRecord, JSFixedFastJsonPbRecord> beanTransformer;

    private final TokenHandler tokenHandler;

    public PbRecordController(
            PbRecordResponseService service, ServiceExceptionMapper sem,
            BeanTransformer<PbRecord, JSFixedFastJsonPbRecord> beanTransformer,
            TokenHandler tokenHandler
    ) {
        this.service = service;
        this.sem = sem;
        this.beanTransformer = beanTransformer;
        this.tokenHandler = tokenHandler;
    }

    @GetMapping("/pb-record/{id}/exists")
    @BehaviorAnalyse
    @LoginRequired
    @PermissionRequired("webapi.controller_permitted.life.pb_record.exists")
    public FastJsonResponseData<Boolean> exists(HttpServletRequest request, @PathVariable("id") Long id) {
        try {
            boolean exists = service.exists(new LongIdKey(id));
            return FastJsonResponseData.of(ResponseDataUtil.good(exists));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @GetMapping("/pb-record/{id}")
    @BehaviorAnalyse
    @LoginRequired
    @PermissionRequired("webapi.controller_permitted.life.pb_record.get")
    public FastJsonResponseData<JSFixedFastJsonPbRecord> get(HttpServletRequest request, @PathVariable("id") Long id) {
        try {
            PbRecord pbRecord = service.get(new LongIdKey(id));
            return FastJsonResponseData.of(ResponseDataUtil.good(JSFixedFastJsonPbRecord.of(pbRecord)));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @GetMapping("/pb-record/all")
    @BehaviorAnalyse
    @SkipRecord
    @LoginRequired
    @PermissionRequired("webapi.controller_permitted.life.pb_record.all")
    public FastJsonResponseData<JSFixedFastJsonPagedData<JSFixedFastJsonPbRecord>> all(
            HttpServletRequest request, @RequestParam("page") int page, @RequestParam("rows") int rows
    ) {
        try {
            PagedData<PbRecord> all = service.all(new PagingInfo(page, rows));
            PagedData<JSFixedFastJsonPbRecord> transform = PagingUtil.transform(all, beanTransformer);
            return FastJsonResponseData.of(ResponseDataUtil.good(JSFixedFastJsonPagedData.of(transform)));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @GetMapping(value = {
            "/pb-item/{pbItemId}/pb-record/recorded-date-asc", "/pb-item//pb-record/recorded-date-asc"
    })
    @BehaviorAnalyse
    @SkipRecord
    @LoginRequired
    @PermissionRequired("webapi.controller_permitted.life.pb_record.child_for_pb_item_recorded_date_asc")
    public FastJsonResponseData<JSFixedFastJsonPagedData<JSFixedFastJsonPbRecord>> childForPbItemRecordedDateAsc(
            HttpServletRequest request,
            @PathVariable(required = false, value = "pbItemId") Long pbItemId,
            @RequestParam("page") int page, @RequestParam("rows") int rows
    ) {
        try {
            LongIdKey pbItemKey = null;
            if (Objects.nonNull(pbItemId)) {
                pbItemKey = new LongIdKey(pbItemId);
            }
            PagedData<PbRecord> childForPbItem = service.childForPbItemRecordedDateAsc(
                    pbItemKey, new PagingInfo(page, rows));
            PagedData<JSFixedFastJsonPbRecord> transform = PagingUtil.transform(
                    childForPbItem, beanTransformer);
            return FastJsonResponseData.of(ResponseDataUtil.good(JSFixedFastJsonPagedData.of(transform)));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @GetMapping(value = {
            "/pb-item/{pbItemId}/pb-record/recorded-date-desc", "/pb-item//pb-record/recorded-date-desc"
    })
    @BehaviorAnalyse
    @SkipRecord
    @LoginRequired
    @PermissionRequired("webapi.controller_permitted.life.pb_record.child_for_pb_item_recorded_date_desc")
    public FastJsonResponseData<JSFixedFastJsonPagedData<JSFixedFastJsonPbRecord>> childForPbItemRecordedDateDesc(
            HttpServletRequest request,
            @PathVariable(required = false, value = "pbItemId") Long pbItemId,
            @RequestParam("page") int page, @RequestParam("rows") int rows
    ) {
        try {
            LongIdKey pbItemKey = null;
            if (Objects.nonNull(pbItemId)) {
                pbItemKey = new LongIdKey(pbItemId);
            }
            PagedData<PbRecord> childForPbItem = service.childForPbItemRecordedDateDesc(
                    pbItemKey, new PagingInfo(page, rows));
            PagedData<JSFixedFastJsonPbRecord> transform = PagingUtil.transform(
                    childForPbItem, beanTransformer);
            return FastJsonResponseData.of(ResponseDataUtil.good(JSFixedFastJsonPagedData.of(transform)));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @PostMapping("/pb-record/create")
    @BehaviorAnalyse
    @BindingCheck
    @LoginRequired
    @PermissionRequired("webapi.controller_permitted.life.pb_record.create_pb_record")
    public FastJsonResponseData<JSFixedFastJsonLongIdKey> createPbRecord(
            HttpServletRequest request,
            @RequestBody @Validated WebInputPbRecordCreateInfo pbRecordCreateInfo, BindingResult bindingResult
    ) {
        try {
            StringIdKey accountKey = tokenHandler.getUserKey(request);
            LongIdKey result = service.createPbRecord(
                    accountKey, WebInputPbRecordCreateInfo.toStackBean(pbRecordCreateInfo)
            );
            return FastJsonResponseData.of(ResponseDataUtil.good(JSFixedFastJsonLongIdKey.of(result)));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @PostMapping("/pb-record/update")
    @BehaviorAnalyse
    @BindingCheck
    @LoginRequired
    @PermissionRequired("webapi.controller_permitted.life.pb_record.update_pb_record")
    public FastJsonResponseData<Object> updatePbRecord(
            HttpServletRequest request,
            @RequestBody @Validated WebInputPbRecordUpdateInfo webInputPbRecordUpdateInfo, BindingResult bindingResult
    ) {
        try {
            StringIdKey accountKey = tokenHandler.getUserKey(request);
            service.updatePbRecord(
                    accountKey, WebInputPbRecordUpdateInfo.toStackBean(webInputPbRecordUpdateInfo)
            );
            return FastJsonResponseData.of(ResponseDataUtil.good(null));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @PostMapping("/pb-record/remove")
    @BehaviorAnalyse
    @BindingCheck
    @LoginRequired
    @PermissionRequired("webapi.controller_permitted.life.pb_record.remove_pb_record")
    public FastJsonResponseData<Object> removePbRecord(
            HttpServletRequest request,
            @RequestBody @Validated WebInputLongIdKey pbRecordKey, BindingResult bindingResult
    ) {
        try {
            StringIdKey accountKey = tokenHandler.getUserKey(request);
            service.removePbRecord(accountKey, WebInputLongIdKey.toStackBean(pbRecordKey));
            return FastJsonResponseData.of(ResponseDataUtil.good(null));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }
}
