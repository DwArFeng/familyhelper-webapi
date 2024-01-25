package com.dwarfeng.familyhelper.webapi.node.controller.v1.project;

import com.dwarfeng.familyhelper.project.sdk.bean.entity.JSFixedFastJsonPop;
import com.dwarfeng.familyhelper.project.stack.bean.entity.Pop;
import com.dwarfeng.familyhelper.project.stack.bean.key.PopKey;
import com.dwarfeng.familyhelper.webapi.sdk.bean.disp.project.JSFixedFastJsonDispPop;
import com.dwarfeng.familyhelper.webapi.stack.bean.disp.project.DispPop;
import com.dwarfeng.familyhelper.webapi.stack.handler.system.TokenHandler;
import com.dwarfeng.familyhelper.webapi.stack.service.project.PopResponseService;
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
 * 资产目录权限控制器。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
@RestController
@RequestMapping("/api/v1/project")
public class PopController {

    private static final Logger LOGGER = LoggerFactory.getLogger(PopController.class);

    private final PopResponseService service;

    private final ServiceExceptionMapper sem;

    private final BeanTransformer<Pop, JSFixedFastJsonPop> popBeanTransformer;
    private final BeanTransformer<DispPop, JSFixedFastJsonDispPop> dispPopBeanTransformer;

    private final TokenHandler tokenHandler;

    public PopController(
            PopResponseService service,
            ServiceExceptionMapper sem,
            BeanTransformer<Pop, JSFixedFastJsonPop> popBeanTransformer,
            BeanTransformer<DispPop, JSFixedFastJsonDispPop> dispPopBeanTransformer,
            TokenHandler tokenHandler
    ) {
        this.service = service;
        this.sem = sem;
        this.popBeanTransformer = popBeanTransformer;
        this.dispPopBeanTransformer = dispPopBeanTransformer;
        this.tokenHandler = tokenHandler;
    }

    @GetMapping("/pop/{longId}&{stringId}/exists")
    @BehaviorAnalyse
    @LoginRequired
    public FastJsonResponseData<Boolean> exists(
            HttpServletRequest request, @PathVariable("longId") Long longId, @PathVariable("stringId") String stringId
    ) {
        try {
            boolean exists = service.exists(new PopKey(longId, stringId));
            return FastJsonResponseData.of(ResponseDataUtil.good(exists));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @GetMapping("/pop/{longId}&{stringId}")
    @BehaviorAnalyse
    @LoginRequired
    public FastJsonResponseData<JSFixedFastJsonPop> get(
            HttpServletRequest request, @PathVariable("longId") Long longId, @PathVariable("stringId") String stringId
    ) {
        try {
            Pop pop = service.get(new PopKey(longId, stringId));
            return FastJsonResponseData.of(ResponseDataUtil.good(JSFixedFastJsonPop.of(pop)));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @GetMapping("/pop/{longId}&{stringId}/disp")
    @BehaviorAnalyse
    @LoginRequired
    public FastJsonResponseData<JSFixedFastJsonDispPop> getDisp(
            HttpServletRequest request, @PathVariable("longId") Long longId, @PathVariable("stringId") String stringId
    ) {
        try {
            StringIdKey inspectAccountKey = tokenHandler.getAccountKey(request);
            DispPop dispPop = service.getDisp(new PopKey(longId, stringId), inspectAccountKey);
            return FastJsonResponseData.of(ResponseDataUtil.good(JSFixedFastJsonDispPop.of(dispPop)));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @GetMapping("/project/{projectId}/pop")
    @BehaviorAnalyse
    @SkipRecord
    @LoginRequired
    public FastJsonResponseData<JSFixedFastJsonPagedData<JSFixedFastJsonPop>> childForProject(
            HttpServletRequest request, @PathVariable("projectId") Long projectId,
            @RequestParam("page") int page, @RequestParam("rows") int rows) {
        try {
            PagedData<Pop> childForProject = service.childForProject(
                    new LongIdKey(projectId), new PagingInfo(page, rows)
            );
            PagedData<JSFixedFastJsonPop> transform = PagingUtil.transform(childForProject, popBeanTransformer);
            return FastJsonResponseData.of(ResponseDataUtil.good(JSFixedFastJsonPagedData.of(transform)));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @GetMapping("/project/{projectId}/pop/disp")
    @BehaviorAnalyse
    @SkipRecord
    @LoginRequired
    public FastJsonResponseData<JSFixedFastJsonPagedData<JSFixedFastJsonDispPop>> childForProjectDisp(
            HttpServletRequest request, @PathVariable("projectId") Long projectId,
            @RequestParam("page") int page, @RequestParam("rows") int rows) {
        try {
            StringIdKey inspectAccountKey = tokenHandler.getAccountKey(request);
            PagedData<DispPop> childForProjectDisp = service.childForProjectDisp(
                    new LongIdKey(projectId), new PagingInfo(page, rows), inspectAccountKey
            );
            PagedData<JSFixedFastJsonDispPop> transform = PagingUtil.transform(
                    childForProjectDisp, dispPopBeanTransformer
            );
            return FastJsonResponseData.of(ResponseDataUtil.good(JSFixedFastJsonPagedData.of(transform)));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }
}
