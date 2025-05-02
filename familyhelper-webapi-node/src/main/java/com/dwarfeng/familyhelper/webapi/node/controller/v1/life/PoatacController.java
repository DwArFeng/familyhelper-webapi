package com.dwarfeng.familyhelper.webapi.node.controller.v1.life;

import com.dwarfeng.familyhelper.life.sdk.bean.entity.JSFixedFastJsonPoatac;
import com.dwarfeng.familyhelper.life.stack.bean.entity.Poatac;
import com.dwarfeng.familyhelper.life.stack.bean.key.PoatacKey;
import com.dwarfeng.familyhelper.webapi.sdk.bean.life.disp.JSFixedFastJsonDispPoatac;
import com.dwarfeng.familyhelper.webapi.stack.bean.life.disp.DispPoatac;
import com.dwarfeng.familyhelper.webapi.stack.handler.system.TokenHandler;
import com.dwarfeng.familyhelper.webapi.stack.service.life.PoatacResponseService;
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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * 活动模板活动权限控制器。
 *
 * @author DwArFeng
 * @since 1.0.10
 */
@RestController
@RequestMapping("/api/v1/life")
public class PoatacController {

    private static final Logger LOGGER = LoggerFactory.getLogger(PoatacController.class);

    private final PoatacResponseService service;

    private final ServiceExceptionMapper sem;

    private final BeanTransformer<Poatac, JSFixedFastJsonPoatac> poatacBeanTransformer;
    private final BeanTransformer<DispPoatac, JSFixedFastJsonDispPoatac> dispPoatacBeanTransformer;

    private final TokenHandler tokenHandler;

    public PoatacController(
            PoatacResponseService service,
            ServiceExceptionMapper sem,
            BeanTransformer<Poatac, JSFixedFastJsonPoatac> poatacBeanTransformer,
            BeanTransformer<DispPoatac, JSFixedFastJsonDispPoatac> dispPoatacBeanTransformer,
            TokenHandler tokenHandler
    ) {
        this.service = service;
        this.sem = sem;
        this.poatacBeanTransformer = poatacBeanTransformer;
        this.dispPoatacBeanTransformer = dispPoatacBeanTransformer;
        this.tokenHandler = tokenHandler;
    }

    @GetMapping("/poatac/{longId}&{stringId}/exists")
    @BehaviorAnalyse
    @LoginRequired
    public FastJsonResponseData<Boolean> exists(
            HttpServletRequest request, @PathVariable("longId") Long longId, @PathVariable("stringId") String stringId
    ) {
        try {
            boolean exists = service.exists(new PoatacKey(longId, stringId));
            return FastJsonResponseData.of(ResponseDataUtil.good(exists));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @GetMapping("/poatac/{longId}&{stringId}")
    @BehaviorAnalyse
    @LoginRequired
    public FastJsonResponseData<JSFixedFastJsonPoatac> get(
            HttpServletRequest request, @PathVariable("longId") Long longId, @PathVariable("stringId") String stringId
    ) {
        try {
            Poatac poatac = service.get(new PoatacKey(longId, stringId));
            return FastJsonResponseData.of(ResponseDataUtil.good(JSFixedFastJsonPoatac.of(poatac)));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @GetMapping("/poatac/{longId}&{stringId}/disp")
    @BehaviorAnalyse
    @LoginRequired
    public FastJsonResponseData<JSFixedFastJsonDispPoatac> getDisp(
            HttpServletRequest request, @PathVariable("longId") Long longId, @PathVariable("stringId") String stringId
    ) {
        try {
            StringIdKey inspectAccountKey = tokenHandler.getUserKey(request);
            DispPoatac dispPoatac = service.getDisp(new PoatacKey(longId, stringId), inspectAccountKey);
            return FastJsonResponseData.of(ResponseDataUtil.good(JSFixedFastJsonDispPoatac.of(dispPoatac)));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @GetMapping("/activity-template/{activityTemplateId}/poatac")
    @BehaviorAnalyse
    @SkipRecord
    @LoginRequired
    public FastJsonResponseData<JSFixedFastJsonPagedData<JSFixedFastJsonPoatac>> childForActivityTemplate(
            HttpServletRequest request, @PathVariable("activityTemplateId") Long activityTemplateId,
            @RequestParam("page") int page, @RequestParam("rows") int rows) {
        try {
            PagedData<Poatac> childForActivityTemplate = service.childForActivityTemplate(
                    new LongIdKey(activityTemplateId), new PagingInfo(page, rows)
            );
            PagedData<JSFixedFastJsonPoatac> transform = PagingUtil.transform(
                    childForActivityTemplate, poatacBeanTransformer
            );
            return FastJsonResponseData.of(ResponseDataUtil.good(JSFixedFastJsonPagedData.of(transform)));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @GetMapping("/activity-template/{activityTemplateId}/poatac/disp")
    @BehaviorAnalyse
    @SkipRecord
    @LoginRequired
    public FastJsonResponseData<JSFixedFastJsonPagedData<JSFixedFastJsonDispPoatac>> childForActivityTemplateDisp(
            HttpServletRequest request, @PathVariable("activityTemplateId") Long activityTemplateId,
            @RequestParam("page") int page, @RequestParam("rows") int rows) {
        try {
            StringIdKey inspectAccountKey = tokenHandler.getUserKey(request);
            PagedData<DispPoatac> childForActivityTemplateDisp = service.childForActivityTemplateDisp(
                    new LongIdKey(activityTemplateId), new PagingInfo(page, rows), inspectAccountKey
            );
            PagedData<JSFixedFastJsonDispPoatac> transform = PagingUtil.transform(
                    childForActivityTemplateDisp, dispPoatacBeanTransformer
            );
            return FastJsonResponseData.of(ResponseDataUtil.good(JSFixedFastJsonPagedData.of(transform)));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }
}
