package com.dwarfeng.familyhelper.webapi.node.controller.v1.finance;

import com.dwarfeng.familyhelper.finance.sdk.bean.entity.JSFixedFastJsonPoab;
import com.dwarfeng.familyhelper.finance.stack.bean.entity.Poab;
import com.dwarfeng.familyhelper.finance.stack.bean.key.PoabKey;
import com.dwarfeng.familyhelper.webapi.sdk.bean.finance.disp.JSFixedFastJsonDispPoab;
import com.dwarfeng.familyhelper.webapi.stack.bean.finance.disp.DispPoab;
import com.dwarfeng.familyhelper.webapi.stack.handler.system.TokenHandler;
import com.dwarfeng.familyhelper.webapi.stack.service.finance.PoabResponseService;
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
 * 账本权限控制器。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
@RestController
@RequestMapping("/api/v1/finance")
public class PoabController {

    private static final Logger LOGGER = LoggerFactory.getLogger(PoabController.class);

    private final PoabResponseService service;

    private final ServiceExceptionMapper sem;

    private final BeanTransformer<Poab, JSFixedFastJsonPoab> poabBeanTransformer;
    private final BeanTransformer<DispPoab, JSFixedFastJsonDispPoab> dispPoabBeanTransformer;

    private final TokenHandler tokenHandler;

    public PoabController(
            PoabResponseService service,
            ServiceExceptionMapper sem,
            BeanTransformer<Poab, JSFixedFastJsonPoab> poabBeanTransformer,
            BeanTransformer<DispPoab, JSFixedFastJsonDispPoab> dispPoabBeanTransformer,
            TokenHandler tokenHandler
    ) {
        this.service = service;
        this.sem = sem;
        this.poabBeanTransformer = poabBeanTransformer;
        this.dispPoabBeanTransformer = dispPoabBeanTransformer;
        this.tokenHandler = tokenHandler;
    }

    @GetMapping("/poab/{longId}&{stringId}/exists")
    @BehaviorAnalyse
    @LoginRequired
    public FastJsonResponseData<Boolean> exists(
            HttpServletRequest request, @PathVariable("longId") Long longId, @PathVariable("stringId") String stringId
    ) {
        try {
            boolean exists = service.exists(new PoabKey(longId, stringId));
            return FastJsonResponseData.of(ResponseDataUtil.good(exists));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @GetMapping("/poab/{longId}&{stringId}")
    @BehaviorAnalyse
    @LoginRequired
    public FastJsonResponseData<JSFixedFastJsonPoab> get(
            HttpServletRequest request, @PathVariable("longId") Long longId, @PathVariable("stringId") String stringId
    ) {
        try {
            Poab poab = service.get(new PoabKey(longId, stringId));
            return FastJsonResponseData.of(ResponseDataUtil.good(JSFixedFastJsonPoab.of(poab)));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @GetMapping("/poab/{longId}&{stringId}/disp")
    @BehaviorAnalyse
    @LoginRequired
    public FastJsonResponseData<JSFixedFastJsonDispPoab> getDisp(
            HttpServletRequest request, @PathVariable("longId") Long longId, @PathVariable("stringId") String stringId
    ) {
        try {
            StringIdKey inspectAccountKey = tokenHandler.getUserKey(request);
            DispPoab dispPoab = service.getDisp(new PoabKey(longId, stringId), inspectAccountKey);
            return FastJsonResponseData.of(ResponseDataUtil.good(JSFixedFastJsonDispPoab.of(dispPoab)));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @GetMapping("/account-book/{accountBookId}/poab")
    @BehaviorAnalyse
    @SkipRecord
    @LoginRequired
    public FastJsonResponseData<JSFixedFastJsonPagedData<JSFixedFastJsonPoab>> childForAccountBook(
            HttpServletRequest request, @PathVariable("accountBookId") Long accountBookId,
            @RequestParam("page") int page, @RequestParam("rows") int rows) {
        try {
            PagedData<Poab> childForAccountBook = service.childForAccountBook(
                    new LongIdKey(accountBookId), new PagingInfo(page, rows)
            );
            PagedData<JSFixedFastJsonPoab> transform = PagingUtil.transform(childForAccountBook, poabBeanTransformer);
            return FastJsonResponseData.of(ResponseDataUtil.good(JSFixedFastJsonPagedData.of(transform)));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @GetMapping("/account-book/{accountBookId}/poab/disp")
    @BehaviorAnalyse
    @SkipRecord
    @LoginRequired
    public FastJsonResponseData<JSFixedFastJsonPagedData<JSFixedFastJsonDispPoab>> childForAccountBookDisp(
            HttpServletRequest request, @PathVariable("accountBookId") Long accountBookId,
            @RequestParam("page") int page, @RequestParam("rows") int rows) {
        try {
            StringIdKey inspectAccountKey = tokenHandler.getUserKey(request);
            PagedData<DispPoab> childForAccountBookDisp = service.childForAccountBookDisp(
                    new LongIdKey(accountBookId), new PagingInfo(page, rows), inspectAccountKey
            );
            PagedData<JSFixedFastJsonDispPoab> transform = PagingUtil.transform(
                    childForAccountBookDisp, dispPoabBeanTransformer
            );
            return FastJsonResponseData.of(ResponseDataUtil.good(JSFixedFastJsonPagedData.of(transform)));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }
}
