package com.dwarfeng.familyhelper.webapi.node.controller.v1.settingrepo;

import com.dwarfeng.familyhelper.webapi.sdk.bean.settingrepo.dto.WebInputPublicNavigationNodeInspectInfo;
import com.dwarfeng.familyhelper.webapi.sdk.bean.settingrepo.dto.WebInputPublicNavigationNodeSizeInfo;
import com.dwarfeng.familyhelper.webapi.stack.service.settingrepo.NavigationNodeResponseService;
import com.dwarfeng.settingrepo.sdk.bean.dto.*;
import com.dwarfeng.settingrepo.sdk.bean.entity.FastJsonNavigationNode;
import com.dwarfeng.settingrepo.stack.bean.dto.NavigationNodeInspectResult;
import com.dwarfeng.settingrepo.stack.bean.dto.NavigationNodeItemInsertResult;
import com.dwarfeng.settingrepo.stack.bean.dto.NavigationNodeSizeResult;
import com.dwarfeng.settingrepo.stack.bean.entity.NavigationNode;
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
 * 导航节点控制器。
 *
 * @author DwArFeng
 * @since 1.8.2
 */
@RestController
@RequestMapping("/api/v1/settingrepo")
public class NavigationNodeController {

    private static final Logger LOGGER = LoggerFactory.getLogger(NavigationNodeController.class);

    private final NavigationNodeResponseService service;
    private final ServiceExceptionMapper sem;

    private final BeanTransformer<NavigationNode, FastJsonNavigationNode> beanTransformer;

    public NavigationNodeController(
            NavigationNodeResponseService service,
            ServiceExceptionMapper sem,
            BeanTransformer<NavigationNode, FastJsonNavigationNode> beanTransformer
    ) {
        this.service = service;
        this.sem = sem;
        this.beanTransformer = beanTransformer;
    }

    @GetMapping("/navigation-node/{id}/exists")
    @BehaviorAnalyse
    @LoginRequired
    @PermissionRequired("webapi.controller_permitted.settingrepo.navigation_node.exists")
    public FastJsonResponseData<Boolean> exists(HttpServletRequest request, @PathVariable("id") String id) {
        try {
            boolean exists = service.exists(new StringIdKey(id));
            return FastJsonResponseData.of(ResponseDataUtil.good(exists));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @GetMapping("/navigation-node/{id}")
    @BehaviorAnalyse
    @LoginRequired
    @PermissionRequired("webapi.controller_permitted.settingrepo.navigation_node.get")
    public FastJsonResponseData<FastJsonNavigationNode> get(
            HttpServletRequest request, @PathVariable("id") String id
    ) {
        try {
            NavigationNode navigationNode = service.get(new StringIdKey(id));
            return FastJsonResponseData.of(ResponseDataUtil.good(FastJsonNavigationNode.of(navigationNode)));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @GetMapping("/navigation-node/all")
    @BehaviorAnalyse
    @SkipRecord
    @LoginRequired
    @PermissionRequired("webapi.controller_permitted.settingrepo.navigation_node.all")
    public FastJsonResponseData<JSFixedFastJsonPagedData<FastJsonNavigationNode>> all(
            HttpServletRequest request, @RequestParam("page") int page, @RequestParam("rows") int rows
    ) {
        try {
            PagedData<NavigationNode> all = service.all(new PagingInfo(page, rows));
            PagedData<FastJsonNavigationNode> transform = PagingUtil.transform(all, beanTransformer);
            return FastJsonResponseData.of(ResponseDataUtil.good(JSFixedFastJsonPagedData.of(transform)));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @PostMapping("/navigation-node/size")
    @BehaviorAnalyse
    @BindingCheck
    @LoginRequired
    @PermissionRequired("webapi.controller_permitted.settingrepo.navigation_node.size")
    public FastJsonResponseData<FastJsonNavigationNodeSizeResult> size(
            HttpServletRequest request,
            @RequestBody @Validated WebInputNavigationNodeSizeInfo webInputNavigationNodeSizeInfo,
            BindingResult bindingResult
    ) {
        try {
            NavigationNodeSizeResult size = service.size(
                    WebInputNavigationNodeSizeInfo.toStackBean(webInputNavigationNodeSizeInfo)
            );
            return FastJsonResponseData.of(ResponseDataUtil.good(FastJsonNavigationNodeSizeResult.of(size)));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @PostMapping("/navigation-node/inspect")
    @BehaviorAnalyse
    @BindingCheck
    @LoginRequired
    @PermissionRequired("webapi.controller_permitted.settingrepo.navigation_node.inspect")
    public FastJsonResponseData<JSFixedFastJsonNavigationNodeInspectResult> inspect(
            HttpServletRequest request,
            @RequestBody @Validated WebInputNavigationNodeInspectInfo webInputNavigationNodeInspectInfo,
            BindingResult bindingResult
    ) {
        try {
            NavigationNodeInspectResult inspect = service.inspect(
                    WebInputNavigationNodeInspectInfo.toStackBean(webInputNavigationNodeInspectInfo)
            );
            return FastJsonResponseData.of(ResponseDataUtil.good(
                    JSFixedFastJsonNavigationNodeInspectResult.of(inspect)
            ));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @PostMapping("/navigation-node/update-node")
    @BehaviorAnalyse
    @BindingCheck
    @LoginRequired
    @PermissionRequired("webapi.controller_permitted.settingrepo.navigation_node.update_node")
    public FastJsonResponseData<Void> updateNode(
            HttpServletRequest request,
            @RequestBody @Validated WebInputNavigationNodeUpdateInfo webInputNavigationNodeUpdateInfo,
            BindingResult bindingResult
    ) {
        try {
            service.updateNode(
                    WebInputNavigationNodeUpdateInfo.toStackBean(webInputNavigationNodeUpdateInfo)
            );
            return FastJsonResponseData.of(ResponseDataUtil.good(null));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @PostMapping("/navigation-node/insert-item")
    @BehaviorAnalyse
    @BindingCheck
    @LoginRequired
    @PermissionRequired("webapi.controller_permitted.settingrepo.navigation_node.insert_item")
    public FastJsonResponseData<JSFixedFastJsonNavigationNodeItemInsertResult> insertItem(
            HttpServletRequest request,
            @RequestBody @Validated WebInputNavigationNodeItemInsertInfo webInputNavigationNodeItemInsertInfo,
            BindingResult bindingResult
    ) {
        try {
            NavigationNodeItemInsertResult insertItem = service.insertItem(
                    WebInputNavigationNodeItemInsertInfo.toStackBean(webInputNavigationNodeItemInsertInfo)
            );
            return FastJsonResponseData.of(ResponseDataUtil.good(
                    JSFixedFastJsonNavigationNodeItemInsertResult.of(insertItem)
            ));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @PostMapping("/navigation-node/update-item")
    @BehaviorAnalyse
    @BindingCheck
    @LoginRequired
    @PermissionRequired("webapi.controller_permitted.settingrepo.navigation_node.update_item")
    public FastJsonResponseData<Void> updateItem(
            HttpServletRequest request,
            @RequestBody @Validated WebInputNavigationNodeItemUpdateInfo webInputNavigationNodeItemUpdateInfo,
            BindingResult bindingResult
    ) {
        try {
            service.updateItem(
                    WebInputNavigationNodeItemUpdateInfo.toStackBean(webInputNavigationNodeItemUpdateInfo)
            );
            return FastJsonResponseData.of(ResponseDataUtil.good(null));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @PostMapping("/navigation-node/remove-item")
    @BehaviorAnalyse
    @BindingCheck
    @LoginRequired
    @PermissionRequired("webapi.controller_permitted.settingrepo.navigation_node.remove_item")
    public FastJsonResponseData<Void> removeItem(
            HttpServletRequest request,
            @RequestBody @Validated WebInputNavigationNodeItemRemoveInfo webInputNavigationNodeItemRemoveInfo,
            BindingResult bindingResult
    ) {
        try {
            service.removeItem(
                    WebInputNavigationNodeItemRemoveInfo.toStackBean(webInputNavigationNodeItemRemoveInfo)
            );
            return FastJsonResponseData.of(ResponseDataUtil.good(null));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @PostMapping("/navigation-node/format-index")
    @BehaviorAnalyse
    @BindingCheck
    @LoginRequired
    @PermissionRequired("webapi.controller_permitted.settingrepo.navigation_node.format_index")
    public FastJsonResponseData<Void> formatIndex(
            HttpServletRequest request,
            @RequestBody @Validated WebInputNavigationNodeFormatIndexInfo webInputNavigationNodeFormatIndexInfo,
            BindingResult bindingResult
    ) {
        try {
            service.formatIndex(
                    WebInputNavigationNodeFormatIndexInfo.toStackBean(webInputNavigationNodeFormatIndexInfo)
            );
            return FastJsonResponseData.of(ResponseDataUtil.good(null));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @PostMapping("/navigation-node/size-for-public")
    @BehaviorAnalyse
    @BindingCheck
    public FastJsonResponseData<FastJsonNavigationNodeSizeResult> sizeForPublic(
            HttpServletRequest request,
            @RequestBody @Validated WebInputPublicNavigationNodeSizeInfo webInputPublicNavigationNodeSizeInfo,
            BindingResult bindingResult
    ) {
        try {
            NavigationNodeSizeResult size = service.sizeForPublic(
                    WebInputPublicNavigationNodeSizeInfo.toStackBean(webInputPublicNavigationNodeSizeInfo)
            );
            return FastJsonResponseData.of(ResponseDataUtil.good(FastJsonNavigationNodeSizeResult.of(size)));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @PostMapping("/navigation-node/inspect-for-public")
    @BehaviorAnalyse
    @BindingCheck
    public FastJsonResponseData<JSFixedFastJsonNavigationNodeInspectResult> inspectForPublic(
            HttpServletRequest request,
            @RequestBody @Validated WebInputPublicNavigationNodeInspectInfo inspectInfo,
            BindingResult bindingResult
    ) {
        try {
            NavigationNodeInspectResult inspect = service.inspectForPublic(
                    WebInputPublicNavigationNodeInspectInfo.toStackBean(inspectInfo)
            );
            return FastJsonResponseData.of(ResponseDataUtil.good(
                    JSFixedFastJsonNavigationNodeInspectResult.of(inspect)
            ));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }
}
