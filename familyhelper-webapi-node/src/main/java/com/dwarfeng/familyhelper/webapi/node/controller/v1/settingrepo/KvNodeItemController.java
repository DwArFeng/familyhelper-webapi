package com.dwarfeng.familyhelper.webapi.node.controller.v1.settingrepo;

import com.dwarfeng.familyhelper.webapi.stack.service.settingrepo.KvNodeItemResponseService;
import com.dwarfeng.settingrepo.sdk.bean.entity.FastJsonKvNodeItem;
import com.dwarfeng.settingrepo.stack.bean.entity.KvNodeItem;
import com.dwarfeng.settingrepo.stack.bean.key.KvNodeItemKey;
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
 * 键值对节点条目控制器。
 *
 * @author DwArFeng
 * @since 2.0.1
 */
@RestController
@RequestMapping("/api/v1/settingrepo")
public class KvNodeItemController {

    private static final Logger LOGGER = LoggerFactory.getLogger(KvNodeItemController.class);

    private final KvNodeItemResponseService service;
    private final ServiceExceptionMapper sem;

    private final BeanTransformer<KvNodeItem, FastJsonKvNodeItem> beanTransformer;

    public KvNodeItemController(
            KvNodeItemResponseService service,
            ServiceExceptionMapper sem,
            BeanTransformer<KvNodeItem, FastJsonKvNodeItem> beanTransformer
    ) {
        this.service = service;
        this.sem = sem;
        this.beanTransformer = beanTransformer;
    }

    @GetMapping("/kv-node-item/{nodeId}/{itemStringId}/exists")
    @BehaviorAnalyse
    @LoginRequired
    @PermissionRequired("webapi.controller_permitted.settingrepo.kv_node_item.exists")
    public FastJsonResponseData<Boolean> exists(
            HttpServletRequest request,
            @PathVariable("nodeId") String nodeId,
            @PathVariable("itemStringId") String itemStringId
    ) {
        try {
            boolean exists = service.exists(new KvNodeItemKey(nodeId, itemStringId));
            return FastJsonResponseData.of(ResponseDataUtil.good(exists));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @GetMapping("/kv-node-item/{nodeId}/{itemStringId}")
    @BehaviorAnalyse
    @LoginRequired
    @PermissionRequired("webapi.controller_permitted.settingrepo.kv_node_item.get")
    public FastJsonResponseData<FastJsonKvNodeItem> get(
            HttpServletRequest request,
            @PathVariable("nodeId") String nodeId,
            @PathVariable("itemStringId") String itemStringId
    ) {
        try {
            KvNodeItem kvNodeItem = service.get(new KvNodeItemKey(nodeId, itemStringId));
            return FastJsonResponseData.of(ResponseDataUtil.good(FastJsonKvNodeItem.of(kvNodeItem)));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @GetMapping("/kv-node-item/all")
    @BehaviorAnalyse
    @SkipRecord
    @LoginRequired
    @PermissionRequired("webapi.controller_permitted.settingrepo.kv_node_item.all")
    public FastJsonResponseData<JSFixedFastJsonPagedData<FastJsonKvNodeItem>> all(
            HttpServletRequest request, @RequestParam("page") int page, @RequestParam("rows") int rows
    ) {
        try {
            PagedData<KvNodeItem> all = service.all(new PagingInfo(page, rows));
            PagedData<FastJsonKvNodeItem> transform = PagingUtil.transform(all, beanTransformer);
            return FastJsonResponseData.of(ResponseDataUtil.good(JSFixedFastJsonPagedData.of(transform)));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @GetMapping("/kv-node/{nodeId}/kv-node-item")
    @BehaviorAnalyse
    @SkipRecord
    @LoginRequired
    @PermissionRequired("webapi.controller_permitted.settingrepo.kv_node_item.child_for_node")
    public FastJsonResponseData<JSFixedFastJsonPagedData<FastJsonKvNodeItem>> childForNode(
            HttpServletRequest request,
            @PathVariable("nodeId") String nodeId,
            @RequestParam("page") int page,
            @RequestParam("rows") int rows
    ) {
        try {
            PagedData<KvNodeItem> childForNode = service.childForNode(
                    new StringIdKey(nodeId), new PagingInfo(page, rows)
            );
            PagedData<FastJsonKvNodeItem> transform = PagingUtil.transform(childForNode, beanTransformer);
            return FastJsonResponseData.of(ResponseDataUtil.good(JSFixedFastJsonPagedData.of(transform)));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }
}
