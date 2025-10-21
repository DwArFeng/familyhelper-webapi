package com.dwarfeng.familyhelper.webapi.node.controller.v1.life;

import com.dwarfeng.familyhelper.life.sdk.bean.entity.JSFixedFastJsonPopb;
import com.dwarfeng.familyhelper.life.stack.bean.entity.Popb;
import com.dwarfeng.familyhelper.life.stack.bean.key.PopbKey;
import com.dwarfeng.familyhelper.webapi.sdk.bean.life.disp.JSFixedFastJsonDispPopb;
import com.dwarfeng.familyhelper.webapi.stack.bean.life.disp.DispPopb;
import com.dwarfeng.familyhelper.webapi.stack.handler.system.TokenHandler;
import com.dwarfeng.familyhelper.webapi.stack.service.life.PopbResponseService;
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
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.bean.key.StringIdKey;
import com.dwarfeng.subgrade.stack.exception.ServiceExceptionMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * 个人最佳权限控制器。
 *
 * @author DwArFeng
 * @since 1.0.7
 */
@RestController
@RequestMapping("/api/v1/life")
public class PopbController {

    private static final Logger LOGGER = LoggerFactory.getLogger(PopbController.class);

    private final PopbResponseService service;

    private final ServiceExceptionMapper sem;

    private final BeanTransformer<Popb, JSFixedFastJsonPopb> popbBeanTransformer;
    private final BeanTransformer<DispPopb, JSFixedFastJsonDispPopb> dispPopbBeanTransformer;

    private final TokenHandler tokenHandler;

    public PopbController(
            PopbResponseService service,
            ServiceExceptionMapper sem,
            BeanTransformer<Popb, JSFixedFastJsonPopb> popbBeanTransformer,
            BeanTransformer<DispPopb, JSFixedFastJsonDispPopb> dispPopbBeanTransformer,
            TokenHandler tokenHandler
    ) {
        this.service = service;
        this.sem = sem;
        this.popbBeanTransformer = popbBeanTransformer;
        this.dispPopbBeanTransformer = dispPopbBeanTransformer;
        this.tokenHandler = tokenHandler;
    }

    @GetMapping("/popb/{longId}&{stringId}/exists")
    @BehaviorAnalyse
    @LoginRequired
    @PermissionRequired("webapi.controller_permitted.life.popb.exists")
    public FastJsonResponseData<Boolean> exists(
            HttpServletRequest request, @PathVariable("longId") Long longId, @PathVariable("stringId") String stringId
    ) {
        try {
            boolean exists = service.exists(new PopbKey(longId, stringId));
            return FastJsonResponseData.of(ResponseDataUtil.good(exists));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @GetMapping("/popb/{longId}&{stringId}")
    @BehaviorAnalyse
    @LoginRequired
    @PermissionRequired("webapi.controller_permitted.life.popb.get")
    public FastJsonResponseData<JSFixedFastJsonPopb> get(
            HttpServletRequest request, @PathVariable("longId") Long longId, @PathVariable("stringId") String stringId
    ) {
        try {
            Popb popb = service.get(new PopbKey(longId, stringId));
            return FastJsonResponseData.of(ResponseDataUtil.good(JSFixedFastJsonPopb.of(popb)));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @GetMapping("/popb/{longId}&{stringId}/disp")
    @BehaviorAnalyse
    @LoginRequired
    @PermissionRequired("webapi.controller_permitted.life.popb.get_disp")
    public FastJsonResponseData<JSFixedFastJsonDispPopb> getDisp(
            HttpServletRequest request, @PathVariable("longId") Long longId, @PathVariable("stringId") String stringId
    ) {
        try {
            StringIdKey inspectAccountKey = tokenHandler.getUserKey(request);
            DispPopb dispPopb = service.getDisp(new PopbKey(longId, stringId), inspectAccountKey);
            return FastJsonResponseData.of(ResponseDataUtil.good(JSFixedFastJsonDispPopb.of(dispPopb)));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @GetMapping("/pb-set/{pbSetId}/popb")
    @BehaviorAnalyse
    @SkipRecord
    @LoginRequired
    @PermissionRequired("webapi.controller_permitted.life.popb.child_for_pb_set")
    public FastJsonResponseData<JSFixedFastJsonPagedData<JSFixedFastJsonPopb>> childForPbSet(
            HttpServletRequest request, @PathVariable("pbSetId") Long pbSetId,
            @RequestParam("page") int page, @RequestParam("rows") int rows) {
        try {
            PagedData<Popb> childForPbSet = service.childForPbSet(
                    new LongIdKey(pbSetId), new PagingInfo(page, rows)
            );
            PagedData<JSFixedFastJsonPopb> transform = PagingUtil.transform(childForPbSet, popbBeanTransformer);
            return FastJsonResponseData.of(ResponseDataUtil.good(JSFixedFastJsonPagedData.of(transform)));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @GetMapping("/pb-set/{pbSetId}/popb/disp")
    @BehaviorAnalyse
    @SkipRecord
    @LoginRequired
    @PermissionRequired("webapi.controller_permitted.life.popb.child_for_pb_set_disp")
    public FastJsonResponseData<JSFixedFastJsonPagedData<JSFixedFastJsonDispPopb>> childForPbSetDisp(
            HttpServletRequest request, @PathVariable("pbSetId") Long pbSetId,
            @RequestParam("page") int page, @RequestParam("rows") int rows) {
        try {
            StringIdKey inspectAccountKey = tokenHandler.getUserKey(request);
            PagedData<DispPopb> childForPbSetDisp = service.childForPbSetDisp(
                    new LongIdKey(pbSetId), new PagingInfo(page, rows), inspectAccountKey
            );
            PagedData<JSFixedFastJsonDispPopb> transform = PagingUtil.transform(
                    childForPbSetDisp, dispPopbBeanTransformer
            );
            return FastJsonResponseData.of(ResponseDataUtil.good(JSFixedFastJsonPagedData.of(transform)));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }
}
