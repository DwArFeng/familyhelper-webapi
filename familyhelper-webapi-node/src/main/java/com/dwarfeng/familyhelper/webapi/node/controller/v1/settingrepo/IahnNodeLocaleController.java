package com.dwarfeng.familyhelper.webapi.node.controller.v1.settingrepo;

import com.dwarfeng.familyhelper.webapi.stack.service.settingrepo.IahnNodeLocaleResponseService;
import com.dwarfeng.settingrepo.sdk.bean.entity.FastJsonIahnNodeLocale;
import com.dwarfeng.settingrepo.stack.bean.entity.IahnNodeLocale;
import com.dwarfeng.settingrepo.stack.bean.key.IahnNodeLocaleKey;
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
import com.dwarfeng.subgrade.stack.bean.key.StringIdKey;
import com.dwarfeng.subgrade.stack.exception.ServiceExceptionMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * 国际化节点地区控制器。
 *
 * @author DwArFeng
 * @since 2.4.1
 */
@RestController
@RequestMapping("/api/v1/settingrepo")
public class IahnNodeLocaleController {

    private static final Logger LOGGER = LoggerFactory.getLogger(IahnNodeLocaleController.class);

    private final IahnNodeLocaleResponseService service;
    private final ServiceExceptionMapper sem;

    private final BeanTransformer<IahnNodeLocale, FastJsonIahnNodeLocale> beanTransformer;

    public IahnNodeLocaleController(
            IahnNodeLocaleResponseService service, ServiceExceptionMapper sem,
            BeanTransformer<IahnNodeLocale, FastJsonIahnNodeLocale> beanTransformer
    ) {
        this.service = service;
        this.sem = sem;
        this.beanTransformer = beanTransformer;
    }

    @GetMapping("/iahn-node-locale/{nodeId}/{language}/exists")
    @BehaviorAnalyse
    @LoginRequired
    @PermissionRequired("webapi.controller_permitted.settingrepo.iahn_node_locale.exists")
    public FastJsonResponseData<Boolean> exists(
            HttpServletRequest request,
            @PathVariable("nodeId") String nodeId,
            @PathVariable("language") String language,
            @RequestParam(value = "country", required = false, defaultValue = "") String country,
            @RequestParam(value = "variant", required = false, defaultValue = "") String variant
    ) {
        try {
            boolean exists = service.exists(new IahnNodeLocaleKey(nodeId, language, country, variant));
            return FastJsonResponseData.of(ResponseDataUtil.good(exists));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @GetMapping("/iahn-node-locale/{nodeId}/{language}")
    @BehaviorAnalyse
    @LoginRequired
    @PermissionRequired("webapi.controller_permitted.settingrepo.iahn_node_locale.get")
    public FastJsonResponseData<FastJsonIahnNodeLocale> get(
            HttpServletRequest request,
            @PathVariable("nodeId") String nodeId,
            @PathVariable("language") String language,
            @RequestParam(value = "country", required = false, defaultValue = "") String country,
            @RequestParam(value = "variant", required = false, defaultValue = "") String variant
    ) {
        try {
            IahnNodeLocale iahnNodeLocale = service.get(new IahnNodeLocaleKey(nodeId, language, country, variant));
            return FastJsonResponseData.of(ResponseDataUtil.good(FastJsonIahnNodeLocale.of(iahnNodeLocale)));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @GetMapping("/iahn-node-locale/all")
    @BehaviorAnalyse
    @SkipRecord
    @LoginRequired
    @PermissionRequired("webapi.controller_permitted.settingrepo.iahn_node_locale.all")
    public FastJsonResponseData<JSFixedFastJsonPagedData<FastJsonIahnNodeLocale>> all(
            HttpServletRequest request, @RequestParam("page") int page, @RequestParam("rows") int rows
    ) {
        try {
            PagedData<IahnNodeLocale> all = service.all(new PagingInfo(page, rows));
            PagedData<FastJsonIahnNodeLocale> transform = PagingUtil.transform(all, beanTransformer);
            return FastJsonResponseData.of(ResponseDataUtil.good(JSFixedFastJsonPagedData.of(transform)));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @GetMapping("/iahn-node/{nodeId}/iahn-node-locale")
    @BehaviorAnalyse
    @SkipRecord
    @LoginRequired
    @PermissionRequired("webapi.controller_permitted.settingrepo.iahn_node_locale.child_for_node")
    public FastJsonResponseData<JSFixedFastJsonPagedData<FastJsonIahnNodeLocale>> childForNode(
            HttpServletRequest request,
            @PathVariable("nodeId") String nodeId,
            @RequestParam("page") int page,
            @RequestParam("rows") int rows
    ) {
        try {
            PagedData<IahnNodeLocale> childForNode = service.childForNode(
                    new StringIdKey(nodeId), new PagingInfo(page, rows)
            );
            PagedData<FastJsonIahnNodeLocale> transform = PagingUtil.transform(childForNode, beanTransformer);
            return FastJsonResponseData.of(ResponseDataUtil.good(JSFixedFastJsonPagedData.of(transform)));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @GetMapping("/iahn-node/{nodeId}/iahn-node-locale/ordered")
    @BehaviorAnalyse
    @SkipRecord
    @LoginRequired
    @PermissionRequired("webapi.controller_permitted.settingrepo.iahn_node_locale.child_for_node_ordered")
    public FastJsonResponseData<JSFixedFastJsonPagedData<FastJsonIahnNodeLocale>> childForNodeOrdered(
            HttpServletRequest request,
            @PathVariable("nodeId") String nodeId,
            @RequestParam("page") int page,
            @RequestParam("rows") int rows
    ) {
        try {
            PagedData<IahnNodeLocale> childForNodeOrdered = service.childForNodeOrdered(
                    new StringIdKey(nodeId), new PagingInfo(page, rows)
            );
            PagedData<FastJsonIahnNodeLocale> transform = PagingUtil.transform(childForNodeOrdered, beanTransformer);
            return FastJsonResponseData.of(ResponseDataUtil.good(JSFixedFastJsonPagedData.of(transform)));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }
}
