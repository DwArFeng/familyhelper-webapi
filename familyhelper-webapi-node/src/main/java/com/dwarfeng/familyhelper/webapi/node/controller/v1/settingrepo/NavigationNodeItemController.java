package com.dwarfeng.familyhelper.webapi.node.controller.v1.settingrepo;

import com.dwarfeng.familyhelper.webapi.sdk.bean.settingrepo.disp.JSFixedFastJsonDispNavigationNodeItem;
import com.dwarfeng.familyhelper.webapi.stack.bean.settingrepo.disp.DispNavigationNodeItem;
import com.dwarfeng.familyhelper.webapi.stack.service.settingrepo.NavigationNodeItemResponseService;
import com.dwarfeng.settingrepo.sdk.bean.entity.FastJsonNavigationNodeItem;
import com.dwarfeng.settingrepo.stack.bean.entity.NavigationNodeItem;
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
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.bean.key.StringIdKey;
import com.dwarfeng.subgrade.stack.exception.ServiceExceptionMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

/**
 * 导航节点条目控制器。
 *
 * @author DwArFeng
 * @since 1.8.2
 */
@RestController
@RequestMapping("/api/v1/settingrepo")
public class NavigationNodeItemController {

    private static final Logger LOGGER = LoggerFactory.getLogger(NavigationNodeItemController.class);

    private final NavigationNodeItemResponseService service;
    private final ServiceExceptionMapper sem;

    private final BeanTransformer<NavigationNodeItem, FastJsonNavigationNodeItem> beanTransformer;
    private final BeanTransformer<DispNavigationNodeItem, JSFixedFastJsonDispNavigationNodeItem> dispBeanTransformer;

    public NavigationNodeItemController(
            NavigationNodeItemResponseService service, ServiceExceptionMapper sem,
            BeanTransformer<NavigationNodeItem, FastJsonNavigationNodeItem> beanTransformer,
            BeanTransformer<DispNavigationNodeItem, JSFixedFastJsonDispNavigationNodeItem> dispBeanTransformer
    ) {
        this.service = service;
        this.sem = sem;
        this.beanTransformer = beanTransformer;
        this.dispBeanTransformer = dispBeanTransformer;
    }

    @GetMapping("/navigation-node-item/{nodeId}/{id}/exists")
    @BehaviorAnalyse
    @LoginRequired
    @PermissionRequired("webapi.controller_permitted.settingrepo.navigation_node_item.exists")
    public FastJsonResponseData<Boolean> exists(
            HttpServletRequest request,
            @PathVariable("nodeId") String nodeId,
            @PathVariable("id") Long id
    ) {
        try {
            boolean exists = service.exists(new LongIdKey(id));
            return FastJsonResponseData.of(ResponseDataUtil.good(exists));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @GetMapping("/navigation-node-item/{nodeId}/{id}")
    @BehaviorAnalyse
    @LoginRequired
    @PermissionRequired("webapi.controller_permitted.settingrepo.navigation_node_item.get")
    public FastJsonResponseData<FastJsonNavigationNodeItem> get(
            HttpServletRequest request,
            @PathVariable("nodeId") String nodeId,
            @PathVariable("id") Long id
    ) {
        try {
            NavigationNodeItem navigationNodeItem = service.get(new LongIdKey(id));
            return FastJsonResponseData.of(ResponseDataUtil.good(FastJsonNavigationNodeItem.of(navigationNodeItem)));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @GetMapping("/navigation-node/{nodeId}/navigation-node-item/name-like")
    @BehaviorAnalyse
    @SkipRecord
    @LoginRequired
    @PermissionRequired("webapi.controller_permitted.settingrepo.navigation_node_item.child_for_node_name_like")
    public FastJsonResponseData<JSFixedFastJsonPagedData<FastJsonNavigationNodeItem>> childForNodeNameLike(
            HttpServletRequest request,
            @PathVariable("nodeId") String nodeId,
            @RequestParam("pattern") String pattern,
            @RequestParam("page") int page,
            @RequestParam("rows") int rows
    ) {
        try {
            PagedData<NavigationNodeItem> nameLike = service.childForNodeNameLike(
                    new StringIdKey(nodeId), pattern, new PagingInfo(page, rows)
            );
            PagedData<FastJsonNavigationNodeItem> transform = PagingUtil.transform(nameLike, beanTransformer);
            return FastJsonResponseData.of(ResponseDataUtil.good(JSFixedFastJsonPagedData.of(transform)));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @GetMapping("/navigation-node/{nodeId}/navigation-node-item/root/")
    @BehaviorAnalyse
    @SkipRecord
    @LoginRequired
    @PermissionRequired("webapi.controller_permitted.settingrepo.navigation_node_item.child_for_node_child_for_root")
    public FastJsonResponseData<JSFixedFastJsonPagedData<FastJsonNavigationNodeItem>> childForNodeChildForRoot(
            HttpServletRequest request,
            @PathVariable("nodeId") String nodeId,
            @RequestParam("page") int page,
            @RequestParam("rows") int rows
    ) {
        try {
            PagedData<NavigationNodeItem> childForRoot = service.childForNodeChildForRoot(
                    new StringIdKey(nodeId), new PagingInfo(page, rows)
            );
            PagedData<FastJsonNavigationNodeItem> transform = PagingUtil.transform(childForRoot, beanTransformer);
            return FastJsonResponseData.of(ResponseDataUtil.good(JSFixedFastJsonPagedData.of(transform)));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @GetMapping(value = {
            "/navigation-node-item/{parentId}/child", "/navigation-node-item//child"
    })
    @BehaviorAnalyse
    @SkipRecord
    @LoginRequired
    @PermissionRequired("webapi.controller_permitted.settingrepo.navigation_node_item.child_for_parent")
    public FastJsonResponseData<JSFixedFastJsonPagedData<FastJsonNavigationNodeItem>> childForParent(
            HttpServletRequest request,
            @PathVariable(required = false, value = "parentId") Long parentId,
            @RequestParam("page") int page,
            @RequestParam("rows") int rows
    ) {
        try {
            LongIdKey parentKey = null;
            if (Objects.nonNull(parentId)) {
                parentKey = new LongIdKey(parentId);
            }
            PagedData<NavigationNodeItem> childForParent = service.childForParent(
                    parentKey, new PagingInfo(page, rows)
            );
            PagedData<FastJsonNavigationNodeItem> transform = PagingUtil.transform(childForParent, beanTransformer);
            return FastJsonResponseData.of(ResponseDataUtil.good(JSFixedFastJsonPagedData.of(transform)));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @GetMapping("/navigation-node-item/{nodeId}/{id}/path-from-root")
    @BehaviorAnalyse
    @BindingCheck
    @LoginRequired
    @PermissionRequired("webapi.controller_permitted.settingrepo.navigation_node_item.path_from_root")
    public FastJsonResponseData<JSFixedFastJsonPagedData<FastJsonNavigationNodeItem>> pathFromRoot(
            HttpServletRequest request,
            @PathVariable("nodeId") String nodeId,
            @PathVariable("id") Long id
    ) {
        try {
            PagedData<NavigationNodeItem> pathFromRoot = service.pathFromRoot(new LongIdKey(id));
            PagedData<FastJsonNavigationNodeItem> transform = PagingUtil.transform(pathFromRoot, beanTransformer);
            return FastJsonResponseData.of(ResponseDataUtil.good(JSFixedFastJsonPagedData.of(transform)));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @GetMapping("/navigation-node-item/{nodeId}/{id}/disp")
    @BehaviorAnalyse
    @LoginRequired
    @PermissionRequired("webapi.controller_permitted.settingrepo.navigation_node_item.get_disp")
    public FastJsonResponseData<JSFixedFastJsonDispNavigationNodeItem> getDisp(
            HttpServletRequest request,
            @PathVariable("nodeId") String nodeId,
            @PathVariable("id") Long id
    ) {
        try {
            DispNavigationNodeItem dispNavigationNodeItem = service.getDisp(new LongIdKey(id));
            return FastJsonResponseData.of(
                    ResponseDataUtil.good(JSFixedFastJsonDispNavigationNodeItem.of(dispNavigationNodeItem))
            );
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @GetMapping("/navigation-node/{nodeId}/navigation-node-item/name-like/disp")
    @BehaviorAnalyse
    @SkipRecord
    @LoginRequired
    @PermissionRequired("webapi.controller_permitted.settingrepo.navigation_node_item.child_for_node_name_like_disp")
    public FastJsonResponseData<JSFixedFastJsonPagedData<JSFixedFastJsonDispNavigationNodeItem>>
    childForNodeNameLikeDisp(
            HttpServletRequest request,
            @PathVariable("nodeId") String nodeId,
            @RequestParam("pattern") String pattern,
            @RequestParam("page") int page,
            @RequestParam("rows") int rows
    ) {
        try {
            PagedData<DispNavigationNodeItem> nameLike = service.childForNodeNameLikeDisp(
                    new StringIdKey(nodeId), pattern, new PagingInfo(page, rows)
            );
            PagedData<JSFixedFastJsonDispNavigationNodeItem> transform = PagingUtil.transform(
                    nameLike, dispBeanTransformer
            );
            return FastJsonResponseData.of(ResponseDataUtil.good(JSFixedFastJsonPagedData.of(transform)));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @GetMapping("/navigation-node/{nodeId}/navigation-node-item/root/disp")
    @BehaviorAnalyse
    @SkipRecord
    @LoginRequired
    @PermissionRequired(
            "webapi.controller_permitted.settingrepo.navigation_node_item.child_for_node_child_for_root_disp"
    )
    public FastJsonResponseData<JSFixedFastJsonPagedData<JSFixedFastJsonDispNavigationNodeItem>>
    childForNodeChildForRootDisp(
            HttpServletRequest request,
            @PathVariable("nodeId") String nodeId,
            @RequestParam("page") int page,
            @RequestParam("rows") int rows
    ) {
        try {
            PagedData<DispNavigationNodeItem> childForRoot = service.childForNodeChildForRootDisp(
                    new StringIdKey(nodeId), new PagingInfo(page, rows)
            );
            PagedData<JSFixedFastJsonDispNavigationNodeItem> transform = PagingUtil.transform(
                    childForRoot, dispBeanTransformer
            );
            return FastJsonResponseData.of(ResponseDataUtil.good(JSFixedFastJsonPagedData.of(transform)));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @GetMapping(value = {
            "/navigation-node-item/{parentId}/child/disp", "/navigation-node-item//child/disp"
    })
    @BehaviorAnalyse
    @SkipRecord
    @LoginRequired
    @PermissionRequired("webapi.controller_permitted.settingrepo.navigation_node_item.child_for_parent_disp")
    public FastJsonResponseData<JSFixedFastJsonPagedData<JSFixedFastJsonDispNavigationNodeItem>> childForParentDisp(
            HttpServletRequest request,
            @PathVariable(required = false, value = "parentId") Long parentId,
            @RequestParam("page") int page,
            @RequestParam("rows") int rows
    ) {
        try {
            LongIdKey parentKey = null;
            if (Objects.nonNull(parentId)) {
                parentKey = new LongIdKey(parentId);
            }
            PagedData<DispNavigationNodeItem> childForParent = service.childForParentDisp(
                    parentKey, new PagingInfo(page, rows)
            );
            PagedData<JSFixedFastJsonDispNavigationNodeItem> transform = PagingUtil.transform(
                    childForParent, dispBeanTransformer
            );
            return FastJsonResponseData.of(ResponseDataUtil.good(JSFixedFastJsonPagedData.of(transform)));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @GetMapping("/navigation-node-item/{nodeId}/{id}/path-from-root/disp")
    @BehaviorAnalyse
    @BindingCheck
    @LoginRequired
    @PermissionRequired("webapi.controller_permitted.settingrepo.navigation_node_item.path_from_root_disp")
    public FastJsonResponseData<JSFixedFastJsonPagedData<JSFixedFastJsonDispNavigationNodeItem>> pathFromRootDisp(
            HttpServletRequest request,
            @PathVariable("nodeId") String nodeId,
            @PathVariable("id") Long id
    ) {
        try {
            PagedData<DispNavigationNodeItem> pathFromRoot = service.pathFromRootDisp(new LongIdKey(id));
            PagedData<JSFixedFastJsonDispNavigationNodeItem> transform = PagingUtil.transform(
                    pathFromRoot, dispBeanTransformer
            );
            return FastJsonResponseData.of(ResponseDataUtil.good(JSFixedFastJsonPagedData.of(transform)));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }
}
