package com.dwarfeng.familyhelper.webapi.node.controller.v1.settingrepo;

import com.dwarfeng.familyhelper.webapi.stack.service.settingrepo.IahnNodeMekResponseService;
import com.dwarfeng.settingrepo.sdk.bean.entity.FastJsonIahnNodeMek;
import com.dwarfeng.settingrepo.stack.bean.entity.IahnNodeMek;
import com.dwarfeng.settingrepo.stack.bean.key.IahnNodeMekKey;
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
 * 国际化节点 Mek 控制器。
 *
 * @author DwArFeng
 * @since 2.4.1
 */
@RestController
@RequestMapping("/api/v1/settingrepo")
public class IahnNodeMekController {

    private static final Logger LOGGER = LoggerFactory.getLogger(IahnNodeMekController.class);

    private final IahnNodeMekResponseService service;
    private final ServiceExceptionMapper sem;

    private final BeanTransformer<IahnNodeMek, FastJsonIahnNodeMek> beanTransformer;

    public IahnNodeMekController(
            IahnNodeMekResponseService service, ServiceExceptionMapper sem,
            BeanTransformer<IahnNodeMek, FastJsonIahnNodeMek> beanTransformer
    ) {
        this.service = service;
        this.sem = sem;
        this.beanTransformer = beanTransformer;
    }

    @GetMapping("/iahn-node-mek/{nodeId}/{mekId}/exists")
    @BehaviorAnalyse
    @LoginRequired
    @PermissionRequired("webapi.controller_permitted.settingrepo.iahn_node_mek.exists")
    public FastJsonResponseData<Boolean> exists(
            HttpServletRequest request,
            @PathVariable("nodeId") String nodeId,
            @PathVariable("mekId") String mekId
    ) {
        try {
            boolean exists = service.exists(new IahnNodeMekKey(nodeId, mekId));
            return FastJsonResponseData.of(ResponseDataUtil.good(exists));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @GetMapping("/iahn-node-mek/{nodeId}/{mekId}")
    @BehaviorAnalyse
    @LoginRequired
    @PermissionRequired("webapi.controller_permitted.settingrepo.iahn_node_mek.get")
    public FastJsonResponseData<FastJsonIahnNodeMek> get(
            HttpServletRequest request,
            @PathVariable("nodeId") String nodeId,
            @PathVariable("mekId") String mekId
    ) {
        try {
            IahnNodeMek iahnNodeMek = service.get(new IahnNodeMekKey(nodeId, mekId));
            return FastJsonResponseData.of(ResponseDataUtil.good(FastJsonIahnNodeMek.of(iahnNodeMek)));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @GetMapping("/iahn-node-mek/all")
    @BehaviorAnalyse
    @SkipRecord
    @LoginRequired
    @PermissionRequired("webapi.controller_permitted.settingrepo.iahn_node_mek.all")
    public FastJsonResponseData<JSFixedFastJsonPagedData<FastJsonIahnNodeMek>> all(
            HttpServletRequest request, @RequestParam("page") int page, @RequestParam("rows") int rows
    ) {
        try {
            PagedData<IahnNodeMek> all = service.all(new PagingInfo(page, rows));
            PagedData<FastJsonIahnNodeMek> transform = PagingUtil.transform(all, beanTransformer);
            return FastJsonResponseData.of(ResponseDataUtil.good(JSFixedFastJsonPagedData.of(transform)));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @GetMapping("/iahn-node/{nodeId}/iahn-node-mek")
    @BehaviorAnalyse
    @SkipRecord
    @LoginRequired
    @PermissionRequired("webapi.controller_permitted.settingrepo.iahn_node_mek.child_for_node")
    public FastJsonResponseData<JSFixedFastJsonPagedData<FastJsonIahnNodeMek>> childForNode(
            HttpServletRequest request,
            @PathVariable("nodeId") String nodeId,
            @RequestParam("page") int page,
            @RequestParam("rows") int rows
    ) {
        try {
            PagedData<IahnNodeMek> childForNode = service.childForNode(
                    new StringIdKey(nodeId), new PagingInfo(page, rows)
            );
            PagedData<FastJsonIahnNodeMek> transform = PagingUtil.transform(childForNode, beanTransformer);
            return FastJsonResponseData.of(ResponseDataUtil.good(JSFixedFastJsonPagedData.of(transform)));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @GetMapping("/iahn-node/{nodeId}/iahn-node-mek/ordered")
    @BehaviorAnalyse
    @SkipRecord
    @LoginRequired
    @PermissionRequired("webapi.controller_permitted.settingrepo.iahn_node_mek.child_for_node_ordered")
    public FastJsonResponseData<JSFixedFastJsonPagedData<FastJsonIahnNodeMek>> childForNodeOrdered(
            HttpServletRequest request,
            @PathVariable("nodeId") String nodeId,
            @RequestParam("page") int page,
            @RequestParam("rows") int rows
    ) {
        try {
            PagedData<IahnNodeMek> childForNodeOrdered = service.childForNodeOrdered(
                    new StringIdKey(nodeId), new PagingInfo(page, rows)
            );
            PagedData<FastJsonIahnNodeMek> transform = PagingUtil.transform(childForNodeOrdered, beanTransformer);
            return FastJsonResponseData.of(ResponseDataUtil.good(JSFixedFastJsonPagedData.of(transform)));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }
}
