package com.dwarfeng.familyhelper.webapi.node.controller.v1.settingrepo;

import com.dwarfeng.familyhelper.webapi.sdk.bean.settingrepo.dto.WebInputPublicKvNodeCountInfo;
import com.dwarfeng.familyhelper.webapi.sdk.bean.settingrepo.dto.WebInputPublicKvNodeInspectInfo;
import com.dwarfeng.familyhelper.webapi.sdk.bean.settingrepo.dto.WebInputPublicKvNodeItemInspectInfo;
import com.dwarfeng.familyhelper.webapi.stack.service.settingrepo.KvNodeResponseService;
import com.dwarfeng.settingrepo.sdk.bean.dto.*;
import com.dwarfeng.settingrepo.sdk.bean.entity.FastJsonKvNode;
import com.dwarfeng.settingrepo.stack.bean.dto.KvNodeCountResult;
import com.dwarfeng.settingrepo.stack.bean.dto.KvNodeInspectResult;
import com.dwarfeng.settingrepo.stack.bean.dto.KvNodeItemInspectResult;
import com.dwarfeng.settingrepo.stack.bean.entity.KvNode;
import com.dwarfeng.subgrade.sdk.bean.dto.FastJsonResponseData;
import com.dwarfeng.subgrade.sdk.bean.dto.JSFixedFastJsonPagedData;
import com.dwarfeng.subgrade.sdk.bean.dto.PagingUtil;
import com.dwarfeng.subgrade.sdk.bean.dto.ResponseDataUtil;
import com.dwarfeng.subgrade.sdk.interceptor.analyse.BehaviorAnalyse;
import com.dwarfeng.subgrade.sdk.interceptor.analyse.SkipRecord;
import com.dwarfeng.subgrade.sdk.interceptor.http.BindingCheck;
import com.dwarfeng.subgrade.sdk.interceptor.login.LoginRequired;
import com.dwarfeng.subgrade.sdk.interceptor.permission.PermissionRequired;
import com.dwarfeng.subgrade.stack.bean.BeanTransformer;
import com.dwarfeng.subgrade.stack.bean.dto.PagedData;
import com.dwarfeng.subgrade.stack.bean.dto.PagingInfo;
import com.dwarfeng.subgrade.stack.bean.key.StringIdKey;
import com.dwarfeng.subgrade.stack.exception.ServiceExceptionMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * 键值对节点控制器。
 *
 * @author DwArFeng
 * @since 2.0.1
 */
@RestController
@RequestMapping("/api/v1/settingrepo")
public class KvNodeController {

    private static final Logger LOGGER = LoggerFactory.getLogger(KvNodeController.class);

    private final KvNodeResponseService service;
    private final ServiceExceptionMapper sem;

    private final BeanTransformer<KvNode, FastJsonKvNode> beanTransformer;

    public KvNodeController(
            KvNodeResponseService service,
            ServiceExceptionMapper sem,
            BeanTransformer<KvNode, FastJsonKvNode> beanTransformer
    ) {
        this.service = service;
        this.sem = sem;
        this.beanTransformer = beanTransformer;
    }

    @GetMapping("/kv-node/{id}/exists")
    @BehaviorAnalyse
    @LoginRequired
    @PermissionRequired("webapi.controller_permitted.settingrepo.kv_node.exists")
    public FastJsonResponseData<Boolean> exists(HttpServletRequest request, @PathVariable("id") String id) {
        try {
            boolean exists = service.exists(new StringIdKey(id));
            return FastJsonResponseData.of(ResponseDataUtil.good(exists));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @GetMapping("/kv-node/{id}")
    @BehaviorAnalyse
    @LoginRequired
    @PermissionRequired("webapi.controller_permitted.settingrepo.kv_node.get")
    public FastJsonResponseData<FastJsonKvNode> get(HttpServletRequest request, @PathVariable("id") String id) {
        try {
            KvNode kvNode = service.get(new StringIdKey(id));
            return FastJsonResponseData.of(ResponseDataUtil.good(FastJsonKvNode.of(kvNode)));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @GetMapping("/kv-node/all")
    @BehaviorAnalyse
    @SkipRecord
    @LoginRequired
    @PermissionRequired("webapi.controller_permitted.settingrepo.kv_node.all")
    public FastJsonResponseData<JSFixedFastJsonPagedData<FastJsonKvNode>> all(
            HttpServletRequest request, @RequestParam("page") int page, @RequestParam("rows") int rows
    ) {
        try {
            PagedData<KvNode> all = service.all(new PagingInfo(page, rows));
            PagedData<FastJsonKvNode> transform = PagingUtil.transform(all, beanTransformer);
            return FastJsonResponseData.of(ResponseDataUtil.good(JSFixedFastJsonPagedData.of(transform)));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @PostMapping("/kv-node/count")
    @BehaviorAnalyse
    @BindingCheck
    @LoginRequired
    @PermissionRequired("webapi.controller_permitted.settingrepo.kv_node.count")
    public FastJsonResponseData<FastJsonKvNodeCountResult> count(
            HttpServletRequest request,
            @RequestBody @Validated WebInputKvNodeCountInfo webInputKvNodeCountInfo,
            BindingResult bindingResult
    ) {
        try {
            KvNodeCountResult count = service.count(WebInputKvNodeCountInfo.toStackBean(webInputKvNodeCountInfo));
            return FastJsonResponseData.of(ResponseDataUtil.good(FastJsonKvNodeCountResult.of(count)));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @PostMapping("/kv-node/inspect")
    @BehaviorAnalyse
    @BindingCheck
    @LoginRequired
    @PermissionRequired("webapi.controller_permitted.settingrepo.kv_node.inspect")
    public FastJsonResponseData<FastJsonKvNodeInspectResult> inspect(
            HttpServletRequest request,
            @RequestBody @Validated WebInputKvNodeInspectInfo webInputKvNodeInspectInfo,
            BindingResult bindingResult
    ) {
        try {
            KvNodeInspectResult inspect = service.inspect(
                    WebInputKvNodeInspectInfo.toStackBean(webInputKvNodeInspectInfo)
            );
            return FastJsonResponseData.of(ResponseDataUtil.good(FastJsonKvNodeInspectResult.of(inspect)));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @PostMapping("/kv-node/inspect-item")
    @BehaviorAnalyse
    @BindingCheck
    @LoginRequired
    @PermissionRequired("webapi.controller_permitted.settingrepo.kv_node.inspect_item")
    public FastJsonResponseData<FastJsonKvNodeItemInspectResult> inspectItem(
            HttpServletRequest request,
            @RequestBody @Validated WebInputKvNodeItemInspectInfo webInputKvNodeItemInspectInfo,
            BindingResult bindingResult
    ) {
        try {
            KvNodeItemInspectResult inspectItem = service.inspectItem(
                    WebInputKvNodeItemInspectInfo.toStackBean(webInputKvNodeItemInspectInfo)
            );
            return FastJsonResponseData.of(ResponseDataUtil.good(FastJsonKvNodeItemInspectResult.of(inspectItem)));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @PostMapping("/kv-node/put-item")
    @BehaviorAnalyse
    @BindingCheck
    @LoginRequired
    @PermissionRequired("webapi.controller_permitted.settingrepo.kv_node.put_item")
    public FastJsonResponseData<Void> putItem(
            HttpServletRequest request,
            @RequestBody @Validated WebInputKvNodeItemPutInfo webInputKvNodeItemPutInfo,
            BindingResult bindingResult
    ) {
        try {
            service.putItem(WebInputKvNodeItemPutInfo.toStackBean(webInputKvNodeItemPutInfo));
            return FastJsonResponseData.of(ResponseDataUtil.good(null));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @PostMapping("/kv-node/remove-item")
    @BehaviorAnalyse
    @BindingCheck
    @LoginRequired
    @PermissionRequired("webapi.controller_permitted.settingrepo.kv_node.remove_item")
    public FastJsonResponseData<Void> removeItem(
            HttpServletRequest request,
            @RequestBody @Validated WebInputKvNodeItemRemoveInfo webInputKvNodeItemRemoveInfo,
            BindingResult bindingResult
    ) {
        try {
            service.removeItem(WebInputKvNodeItemRemoveInfo.toStackBean(webInputKvNodeItemRemoveInfo));
            return FastJsonResponseData.of(ResponseDataUtil.good(null));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @PostMapping("/kv-node/clear")
    @BehaviorAnalyse
    @BindingCheck
    @LoginRequired
    @PermissionRequired("webapi.controller_permitted.settingrepo.kv_node.clear")
    public FastJsonResponseData<Void> clear(
            HttpServletRequest request,
            @RequestBody @Validated WebInputKvNodeClearInfo webInputKvNodeClearInfo,
            BindingResult bindingResult
    ) {
        try {
            service.clear(WebInputKvNodeClearInfo.toStackBean(webInputKvNodeClearInfo));
            return FastJsonResponseData.of(ResponseDataUtil.good(null));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @PostMapping("/kv-node/count-for-public")
    @BehaviorAnalyse
    @BindingCheck
    public FastJsonResponseData<FastJsonKvNodeCountResult> countForPublic(
            HttpServletRequest request,
            @RequestBody @Validated WebInputPublicKvNodeCountInfo webInputPublicKvNodeCountInfo,
            BindingResult bindingResult
    ) {
        try {
            KvNodeCountResult count = service.countForPublic(
                    WebInputPublicKvNodeCountInfo.toStackBean(webInputPublicKvNodeCountInfo)
            );
            return FastJsonResponseData.of(ResponseDataUtil.good(FastJsonKvNodeCountResult.of(count)));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @PostMapping("/kv-node/inspect-for-public")
    @BehaviorAnalyse
    @BindingCheck
    public FastJsonResponseData<FastJsonKvNodeInspectResult> inspectForPublic(
            HttpServletRequest request,
            @RequestBody @Validated WebInputPublicKvNodeInspectInfo webInputPublicKvNodeInspectInfo,
            BindingResult bindingResult
    ) {
        try {
            KvNodeInspectResult inspect = service.inspectForPublic(
                    WebInputPublicKvNodeInspectInfo.toStackBean(webInputPublicKvNodeInspectInfo)
            );
            return FastJsonResponseData.of(ResponseDataUtil.good(FastJsonKvNodeInspectResult.of(inspect)));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @PostMapping("/kv-node/inspect-item-for-public")
    @BehaviorAnalyse
    @BindingCheck
    public FastJsonResponseData<FastJsonKvNodeItemInspectResult> inspectItemForPublic(
            HttpServletRequest request,
            @RequestBody @Validated WebInputPublicKvNodeItemInspectInfo webInputPublicKvNodeItemInspectInfo,
            BindingResult bindingResult
    ) {
        try {
            KvNodeItemInspectResult inspectItem = service.inspectItemForPublic(
                    WebInputPublicKvNodeItemInspectInfo.toStackBean(webInputPublicKvNodeItemInspectInfo)
            );
            return FastJsonResponseData.of(ResponseDataUtil.good(FastJsonKvNodeItemInspectResult.of(inspectItem)));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }
}
