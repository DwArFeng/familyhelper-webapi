package com.dwarfeng.familyhelper.webapi.node.controller.v1.clannad;

import com.dwarfeng.familyhelper.clannad.sdk.bean.entity.JSFixedFastJsonPoce;
import com.dwarfeng.familyhelper.clannad.stack.bean.entity.Poce;
import com.dwarfeng.familyhelper.clannad.stack.bean.key.PoceKey;
import com.dwarfeng.familyhelper.webapi.sdk.bean.clannad.disp.JSFixedFastJsonDispPoce;
import com.dwarfeng.familyhelper.webapi.stack.bean.clannad.disp.DispPoce;
import com.dwarfeng.familyhelper.webapi.stack.handler.system.TokenHandler;
import com.dwarfeng.familyhelper.webapi.stack.service.clannad.PoceResponseService;
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
 * 证件权限控制器。
 *
 * @author DwArFeng
 * @since 1.0.7
 */
@RestController
@RequestMapping("/api/v1/clannad")
public class PoceController {

    private static final Logger LOGGER = LoggerFactory.getLogger(PoceController.class);

    private final PoceResponseService service;

    private final ServiceExceptionMapper sem;

    private final BeanTransformer<Poce, JSFixedFastJsonPoce> poceBeanTransformer;
    private final BeanTransformer<DispPoce, JSFixedFastJsonDispPoce> dispPoceBeanTransformer;

    private final TokenHandler tokenHandler;

    public PoceController(
            PoceResponseService service,
            ServiceExceptionMapper sem,
            BeanTransformer<Poce, JSFixedFastJsonPoce> poceBeanTransformer,
            BeanTransformer<DispPoce, JSFixedFastJsonDispPoce> dispPoceBeanTransformer,
            TokenHandler tokenHandler
    ) {
        this.service = service;
        this.sem = sem;
        this.poceBeanTransformer = poceBeanTransformer;
        this.dispPoceBeanTransformer = dispPoceBeanTransformer;
        this.tokenHandler = tokenHandler;
    }

    @GetMapping("/poce/{certificateId}&{userId}/exists")
    @BehaviorAnalyse
    @LoginRequired
    public FastJsonResponseData<Boolean> exists(
            HttpServletRequest request,
            @PathVariable("certificateId") Long certificateId, @PathVariable("userId") String userId
    ) {
        try {
            boolean exists = service.exists(new PoceKey(certificateId, userId));
            return FastJsonResponseData.of(ResponseDataUtil.good(exists));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @GetMapping("/poce/{certificateId}&{userId}")
    @BehaviorAnalyse
    @LoginRequired
    public FastJsonResponseData<JSFixedFastJsonPoce> get(
            HttpServletRequest request,
            @PathVariable("certificateId") Long certificateId, @PathVariable("userId") String userId
    ) {
        try {
            Poce poce = service.get(new PoceKey(certificateId, userId));
            return FastJsonResponseData.of(ResponseDataUtil.good(JSFixedFastJsonPoce.of(poce)));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @GetMapping("/poce/{certificateId}&{userId}/disp")
    @BehaviorAnalyse
    @LoginRequired
    public FastJsonResponseData<JSFixedFastJsonDispPoce> getDisp(
            HttpServletRequest request,
            @PathVariable("certificateId") Long certificateId, @PathVariable("userId") String userId
    ) {
        try {
            StringIdKey inspectAccountKey = tokenHandler.getUserKey(request);
            DispPoce dispPoce = service.getDisp(new PoceKey(certificateId, userId), inspectAccountKey);
            return FastJsonResponseData.of(ResponseDataUtil.good(JSFixedFastJsonDispPoce.of(dispPoce)));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @GetMapping("/certificate/{certificateId}/poce")
    @BehaviorAnalyse
    @SkipRecord
    @LoginRequired
    public FastJsonResponseData<JSFixedFastJsonPagedData<JSFixedFastJsonPoce>> childForCertificate(
            HttpServletRequest request, @PathVariable("certificateId") Long certificateId,
            @RequestParam("page") int page, @RequestParam("rows") int rows) {
        try {
            PagedData<Poce> childForCertificate = service.childForCertificate(
                    new LongIdKey(certificateId), new PagingInfo(page, rows)
            );
            PagedData<JSFixedFastJsonPoce> transform = PagingUtil.transform(childForCertificate, poceBeanTransformer);
            return FastJsonResponseData.of(ResponseDataUtil.good(JSFixedFastJsonPagedData.of(transform)));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @GetMapping("/certificate/{certificateId}/poce/disp")
    @BehaviorAnalyse
    @SkipRecord
    @LoginRequired
    public FastJsonResponseData<JSFixedFastJsonPagedData<JSFixedFastJsonDispPoce>> childForCertificateDisp(
            HttpServletRequest request, @PathVariable("certificateId") Long certificateId,
            @RequestParam("page") int page, @RequestParam("rows") int rows) {
        try {
            StringIdKey inspectAccountKey = tokenHandler.getUserKey(request);
            PagedData<DispPoce> childForCertificateDisp = service.childForCertificateDisp(
                    new LongIdKey(certificateId), new PagingInfo(page, rows), inspectAccountKey
            );
            PagedData<JSFixedFastJsonDispPoce> transform = PagingUtil.transform(
                    childForCertificateDisp, dispPoceBeanTransformer
            );
            return FastJsonResponseData.of(ResponseDataUtil.good(JSFixedFastJsonPagedData.of(transform)));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }
}
