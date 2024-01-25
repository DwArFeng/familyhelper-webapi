package com.dwarfeng.familyhelper.webapi.node.controller.v1.life;

import com.dwarfeng.familyhelper.life.sdk.bean.dto.WebInputActivityDataNodeCreateInfo;
import com.dwarfeng.familyhelper.life.sdk.bean.dto.WebInputActivityDataNodeUpdateInfo;
import com.dwarfeng.familyhelper.life.sdk.bean.entity.JSFixedFastJsonActivityDataNode;
import com.dwarfeng.familyhelper.life.stack.bean.entity.ActivityDataNode;
import com.dwarfeng.familyhelper.webapi.sdk.bean.disp.life.JSFixedFastJsonDispActivityDataNode;
import com.dwarfeng.familyhelper.webapi.stack.bean.disp.life.DispActivityDataNode;
import com.dwarfeng.familyhelper.webapi.stack.handler.system.TokenHandler;
import com.dwarfeng.familyhelper.webapi.stack.service.life.ActivityDataNodeResponseService;
import com.dwarfeng.subgrade.sdk.bean.dto.FastJsonResponseData;
import com.dwarfeng.subgrade.sdk.bean.dto.JSFixedFastJsonPagedData;
import com.dwarfeng.subgrade.sdk.bean.dto.PagingUtil;
import com.dwarfeng.subgrade.sdk.bean.dto.ResponseDataUtil;
import com.dwarfeng.subgrade.sdk.bean.key.JSFixedFastJsonLongIdKey;
import com.dwarfeng.subgrade.sdk.bean.key.WebInputLongIdKey;
import com.dwarfeng.subgrade.sdk.interceptor.analyse.BehaviorAnalyse;
import com.dwarfeng.subgrade.sdk.interceptor.analyse.SkipRecord;
import com.dwarfeng.subgrade.sdk.interceptor.http.BindingCheck;
import com.dwarfeng.subgrade.sdk.interceptor.login.LoginRequired;
import com.dwarfeng.subgrade.stack.bean.BeanTransformer;
import com.dwarfeng.subgrade.stack.bean.dto.PagedData;
import com.dwarfeng.subgrade.stack.bean.dto.PagingInfo;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.bean.key.StringIdKey;
import com.dwarfeng.subgrade.stack.exception.ServiceExceptionMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

/**
 * 活动数据节点控制器。
 *
 * @author DwArFeng
 * @since 1.0.10
 */
@RestController
@RequestMapping("/api/v1/life")
public class ActivityDataNodeController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ActivityDataNodeController.class);

    private final ActivityDataNodeResponseService service;

    private final ServiceExceptionMapper sem;

    private final BeanTransformer<ActivityDataNode, JSFixedFastJsonActivityDataNode>
            activityDataNodeBeanTransformer;
    private final BeanTransformer<DispActivityDataNode, JSFixedFastJsonDispActivityDataNode>
            dispActivityDataNodeBeanTransformer;

    private final TokenHandler tokenHandler;

    public ActivityDataNodeController(
            ActivityDataNodeResponseService service, ServiceExceptionMapper sem,
            BeanTransformer<ActivityDataNode, JSFixedFastJsonActivityDataNode> activityDataNodeBeanTransformer,
            BeanTransformer<DispActivityDataNode, JSFixedFastJsonDispActivityDataNode>
                    dispActivityDataNodeBeanTransformer,
            TokenHandler tokenHandler
    ) {
        this.service = service;
        this.sem = sem;
        this.activityDataNodeBeanTransformer = activityDataNodeBeanTransformer;
        this.dispActivityDataNodeBeanTransformer = dispActivityDataNodeBeanTransformer;
        this.tokenHandler = tokenHandler;
    }

    @GetMapping("/activity-data-node/{id}/exists")
    @BehaviorAnalyse
    @LoginRequired
    public FastJsonResponseData<Boolean> exists(HttpServletRequest request, @PathVariable("id") Long id) {
        try {
            boolean exists = service.exists(new LongIdKey(id));
            return FastJsonResponseData.of(ResponseDataUtil.good(exists));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @GetMapping("/activity-data-node/{id}")
    @BehaviorAnalyse
    @LoginRequired
    public FastJsonResponseData<JSFixedFastJsonActivityDataNode> get(
            HttpServletRequest request, @PathVariable("id") Long id
    ) {
        try {
            ActivityDataNode activityDataNode = service.get(new LongIdKey(id));
            return FastJsonResponseData.of(ResponseDataUtil.good(JSFixedFastJsonActivityDataNode.of(activityDataNode)));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @GetMapping("/activity-data-node/all")
    @BehaviorAnalyse
    @SkipRecord
    @LoginRequired
    public FastJsonResponseData<JSFixedFastJsonPagedData<JSFixedFastJsonActivityDataNode>> all(
            HttpServletRequest request, @RequestParam("page") int page, @RequestParam("rows") int rows) {
        try {
            PagedData<ActivityDataNode> all = service.all(new PagingInfo(page, rows));
            PagedData<JSFixedFastJsonActivityDataNode> transform = PagingUtil.transform(
                    all, activityDataNodeBeanTransformer
            );
            return FastJsonResponseData.of(ResponseDataUtil.good(JSFixedFastJsonPagedData.of(transform)));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @GetMapping(value = {
            "/activity-data-set/{activityDataSetId}/activity-data-node", "/activity-data-set//activity-data-node"
    })
    @BehaviorAnalyse
    @SkipRecord
    @LoginRequired
    public FastJsonResponseData<JSFixedFastJsonPagedData<JSFixedFastJsonActivityDataNode>> childForActivityDataSet(
            @PathVariable(required = false, value = "activityDataSetId") Long activityDataSetId,
            @RequestParam("page") int page, @RequestParam("rows") int rows
    ) {
        try {
            LongIdKey activityDataSetKey = null;
            if (Objects.nonNull(activityDataSetId)) {
                activityDataSetKey = new LongIdKey(activityDataSetId);
            }
            PagedData<ActivityDataNode> childForActivityDataSet = service.childForActivityDataSet(
                    activityDataSetKey, new PagingInfo(page, rows));
            PagedData<JSFixedFastJsonActivityDataNode> transform = PagingUtil.transform(
                    childForActivityDataSet, activityDataNodeBeanTransformer);
            return FastJsonResponseData.of(ResponseDataUtil.good(JSFixedFastJsonPagedData.of(transform)));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @GetMapping("/activity-data-set/{activityDataSetId}/activity-data-node/root")
    @BehaviorAnalyse
    @SkipRecord
    @LoginRequired
    public FastJsonResponseData<JSFixedFastJsonPagedData<JSFixedFastJsonActivityDataNode>> childForActivityDataSetRoot(
            HttpServletRequest request,
            @PathVariable("activityDataSetId") long activityDataSetId,
            @RequestParam("page") int page, @RequestParam("rows") int rows
    ) {
        try {
            PagedData<ActivityDataNode> childForActivityDataSetRoot = service.childForActivityDataSetRoot(
                    new LongIdKey(activityDataSetId), new PagingInfo(page, rows)
            );
            PagedData<JSFixedFastJsonActivityDataNode> transform = PagingUtil.transform(
                    childForActivityDataSetRoot, activityDataNodeBeanTransformer
            );
            return FastJsonResponseData.of(ResponseDataUtil.good(JSFixedFastJsonPagedData.of(transform)));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @GetMapping("/activity-data-node/{parentId}/child")
    @BehaviorAnalyse
    @SkipRecord
    @LoginRequired
    public FastJsonResponseData<JSFixedFastJsonPagedData<JSFixedFastJsonActivityDataNode>> childForParent(
            HttpServletRequest request,
            @PathVariable("parentId") long parentId,
            @RequestParam("page") int page, @RequestParam("rows") int rows
    ) {
        try {
            PagedData<ActivityDataNode> childForParent = service.childForParent(
                    new LongIdKey(parentId), new PagingInfo(page, rows)
            );
            PagedData<JSFixedFastJsonActivityDataNode> transform = PagingUtil.transform(
                    childForParent, activityDataNodeBeanTransformer
            );
            return FastJsonResponseData.of(ResponseDataUtil.good(JSFixedFastJsonPagedData.of(transform)));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @GetMapping(value = {
            "/activity-data-set/{activityDataSetId}/activity-data-node/name-like",
            "/activity-data-set//activity-data-node/name-like"
    })
    @BehaviorAnalyse
    @SkipRecord
    @LoginRequired
    public FastJsonResponseData<JSFixedFastJsonPagedData<JSFixedFastJsonActivityDataNode>>
    childForActivityDataSetNameLike(
            @PathVariable(required = false, value = "activityDataSetId") Long activityDataSetId,
            @RequestParam("pattern") String pattern,
            @RequestParam("page") int page, @RequestParam("rows") int rows
    ) {
        try {
            LongIdKey activityDataSetKey = null;
            if (Objects.nonNull(activityDataSetId)) {
                activityDataSetKey = new LongIdKey(activityDataSetId);
            }
            PagedData<ActivityDataNode> childForActivityDataSet = service.childForActivityDataSetNameLike(
                    activityDataSetKey, pattern, new PagingInfo(page, rows)
            );
            PagedData<JSFixedFastJsonActivityDataNode> transform = PagingUtil.transform(
                    childForActivityDataSet, activityDataNodeBeanTransformer);
            return FastJsonResponseData.of(ResponseDataUtil.good(JSFixedFastJsonPagedData.of(transform)));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @GetMapping("/activity-data-node/{id}/disp")
    @BehaviorAnalyse
    @SkipRecord
    @LoginRequired
    public FastJsonResponseData<JSFixedFastJsonDispActivityDataNode> getDisp(
            HttpServletRequest request, @PathVariable("id") Long id
    ) {
        try {
            StringIdKey inspectAccountKey = tokenHandler.getAccountKey(request);
            DispActivityDataNode dispActivityDataNode = service.getDisp(new LongIdKey(id), inspectAccountKey);
            return FastJsonResponseData.of(
                    ResponseDataUtil.good(JSFixedFastJsonDispActivityDataNode.of(dispActivityDataNode))
            );
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @GetMapping("/activity-data-node/all/disp")
    @BehaviorAnalyse
    @SkipRecord
    @LoginRequired
    public FastJsonResponseData<JSFixedFastJsonPagedData<JSFixedFastJsonDispActivityDataNode>> allDisp(
            HttpServletRequest request, @RequestParam("page") int page, @RequestParam("rows") int rows
    ) {
        try {
            StringIdKey accountKey = tokenHandler.getAccountKey(request);
            PagedData<DispActivityDataNode> allDisp = service.allDisp(accountKey, new PagingInfo(page, rows));
            PagedData<JSFixedFastJsonDispActivityDataNode> transform = PagingUtil.transform(
                    allDisp, dispActivityDataNodeBeanTransformer);
            return FastJsonResponseData.of(ResponseDataUtil.good(JSFixedFastJsonPagedData.of(transform)));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @GetMapping(value = {
            "/activity-data-set/{activityDataSetId}/activity-data-node/disp",
            "/activity-data-set//activity-data-node/disp"
    })
    @BehaviorAnalyse
    @SkipRecord
    @LoginRequired
    public FastJsonResponseData<JSFixedFastJsonPagedData<JSFixedFastJsonDispActivityDataNode>>
    childForActivityDataSetDisp(
            HttpServletRequest request,
            @PathVariable(required = false, value = "activityDataSetId") Long activityDataSetId,
            @RequestParam("page") int page, @RequestParam("rows") int rows
    ) {
        try {
            StringIdKey accountKey = tokenHandler.getAccountKey(request);
            LongIdKey activityDataSetKey = null;
            if (Objects.nonNull(activityDataSetId)) {
                activityDataSetKey = new LongIdKey(activityDataSetId);
            }
            PagedData<DispActivityDataNode> childForActivityDataSetDisp = service.childForActivityDataSetDisp(
                    accountKey, activityDataSetKey, new PagingInfo(page, rows));
            PagedData<JSFixedFastJsonDispActivityDataNode> transform = PagingUtil.transform(
                    childForActivityDataSetDisp, dispActivityDataNodeBeanTransformer);
            return FastJsonResponseData.of(ResponseDataUtil.good(JSFixedFastJsonPagedData.of(transform)));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @GetMapping("/activity-data-set/{activityDataSetId}/activity-data-node/root/disp")
    @BehaviorAnalyse
    @SkipRecord
    @LoginRequired
    public FastJsonResponseData<JSFixedFastJsonPagedData<JSFixedFastJsonDispActivityDataNode>>
    childForActivityDataSetRootDisp(
            HttpServletRequest request,
            @PathVariable("activityDataSetId") long activityDataSetId,
            @RequestParam("page") int page, @RequestParam("rows") int rows
    ) {
        try {
            StringIdKey accountKey = tokenHandler.getAccountKey(request);
            PagedData<DispActivityDataNode> childForActivityDataSetRoot = service.childForActivityDataSetRootDisp(
                    accountKey, new LongIdKey(activityDataSetId), new PagingInfo(page, rows)
            );
            PagedData<JSFixedFastJsonDispActivityDataNode> transform = PagingUtil.transform(
                    childForActivityDataSetRoot, dispActivityDataNodeBeanTransformer
            );
            return FastJsonResponseData.of(ResponseDataUtil.good(JSFixedFastJsonPagedData.of(transform)));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @GetMapping("/activity-data-node/{parentId}/child/disp")
    @BehaviorAnalyse
    @SkipRecord
    @LoginRequired
    public FastJsonResponseData<JSFixedFastJsonPagedData<JSFixedFastJsonDispActivityDataNode>> childForParentDisp(
            HttpServletRequest request,
            @PathVariable("parentId") long parentId,
            @RequestParam("page") int page, @RequestParam("rows") int rows
    ) {
        try {
            StringIdKey accountKey = tokenHandler.getAccountKey(request);
            PagedData<DispActivityDataNode> childForParent = service.childForParentDisp(
                    accountKey, new LongIdKey(parentId), new PagingInfo(page, rows)
            );
            PagedData<JSFixedFastJsonDispActivityDataNode> transform = PagingUtil.transform(
                    childForParent, dispActivityDataNodeBeanTransformer
            );
            return FastJsonResponseData.of(ResponseDataUtil.good(JSFixedFastJsonPagedData.of(transform)));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @GetMapping(value = {
            "/activity-data-set/{activityDataSetId}/activity-data-node/name-like/disp",
            "/activity-data-set//activity-data-node/name-like/disp"
    })
    @BehaviorAnalyse
    @SkipRecord
    @LoginRequired
    public FastJsonResponseData<JSFixedFastJsonPagedData<JSFixedFastJsonDispActivityDataNode>>
    childForActivityDataSetNameLikeDisp(
            HttpServletRequest request,
            @PathVariable(required = false, value = "activityDataSetId") Long activityDataSetId,
            @RequestParam("pattern") String pattern,
            @RequestParam("page") int page, @RequestParam("rows") int rows
    ) {
        try {
            StringIdKey accountKey = tokenHandler.getAccountKey(request);
            LongIdKey activityDataSetKey = null;
            if (Objects.nonNull(activityDataSetId)) {
                activityDataSetKey = new LongIdKey(activityDataSetId);
            }
            PagedData<DispActivityDataNode> childForActivityDataSet = service.childForActivityDataSetNameLikeDisp(
                    accountKey, activityDataSetKey, pattern, new PagingInfo(page, rows)
            );
            PagedData<JSFixedFastJsonDispActivityDataNode> transform = PagingUtil.transform(
                    childForActivityDataSet, dispActivityDataNodeBeanTransformer
            );
            return FastJsonResponseData.of(ResponseDataUtil.good(JSFixedFastJsonPagedData.of(transform)));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @GetMapping("/activity-data-node/{id}/path-from-root")
    @BehaviorAnalyse
    @SkipRecord
    @LoginRequired
    public FastJsonResponseData<JSFixedFastJsonPagedData<JSFixedFastJsonActivityDataNode>> nodePathFromRoot(
            HttpServletRequest request, @PathVariable(value = "id") Long id
    ) {
        try {
            PagedData<ActivityDataNode> pathFromRoot = service.nodePathFromRoot(new LongIdKey(id));
            PagedData<JSFixedFastJsonActivityDataNode> transform = PagingUtil.transform(
                    pathFromRoot, activityDataNodeBeanTransformer
            );
            return FastJsonResponseData.of(ResponseDataUtil.good(JSFixedFastJsonPagedData.of(transform)));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @GetMapping("/activity-data-node/{id}/path-from-root/disp")
    @BehaviorAnalyse
    @SkipRecord
    @LoginRequired
    public FastJsonResponseData<JSFixedFastJsonPagedData<JSFixedFastJsonDispActivityDataNode>> nodePathFromRootDisp(
            HttpServletRequest request, @PathVariable(value = "id") Long id
    ) {
        try {
            StringIdKey accountKey = tokenHandler.getAccountKey(request);
            PagedData<DispActivityDataNode> pathFromRoot = service.nodePathFromRootDisp(accountKey, new LongIdKey(id));
            PagedData<JSFixedFastJsonDispActivityDataNode> transform = PagingUtil.transform(
                    pathFromRoot, dispActivityDataNodeBeanTransformer
            );
            return FastJsonResponseData.of(ResponseDataUtil.good(JSFixedFastJsonPagedData.of(transform)));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @GetMapping("/activity-data-item/{itemId}/path-from-root")
    @BehaviorAnalyse
    @SkipRecord
    @LoginRequired
    public FastJsonResponseData<JSFixedFastJsonPagedData<JSFixedFastJsonActivityDataNode>> itemPathFromRoot(
            HttpServletRequest request, @PathVariable(value = "itemId") Long itemId
    ) {
        try {
            PagedData<ActivityDataNode> pathFromRoot = service.itemPathFromRoot(new LongIdKey(itemId));
            PagedData<JSFixedFastJsonActivityDataNode> transform = PagingUtil.transform(
                    pathFromRoot, activityDataNodeBeanTransformer
            );
            return FastJsonResponseData.of(ResponseDataUtil.good(JSFixedFastJsonPagedData.of(transform)));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @GetMapping("/activity-data-item/{itemId}/path-from-root/disp")
    @BehaviorAnalyse
    @SkipRecord
    @LoginRequired
    public FastJsonResponseData<JSFixedFastJsonPagedData<JSFixedFastJsonDispActivityDataNode>> itemPathFromRootDisp(
            HttpServletRequest request, @PathVariable(value = "itemId") Long itemId
    ) {
        try {
            StringIdKey accountKey = tokenHandler.getAccountKey(request);
            PagedData<DispActivityDataNode> pathFromRoot = service.itemPathFromRootDisp(
                    accountKey, new LongIdKey(itemId)
            );
            PagedData<JSFixedFastJsonDispActivityDataNode> transform = PagingUtil.transform(
                    pathFromRoot, dispActivityDataNodeBeanTransformer
            );
            return FastJsonResponseData.of(ResponseDataUtil.good(JSFixedFastJsonPagedData.of(transform)));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @PostMapping("/activity-data-node/create")
    @BehaviorAnalyse
    @BindingCheck
    public FastJsonResponseData<JSFixedFastJsonLongIdKey> createActivityDataNode(
            HttpServletRequest request,
            @RequestBody @Validated WebInputActivityDataNodeCreateInfo activityDataNodeCreateInfo,
            BindingResult bindingResult
    ) {
        try {
            StringIdKey accountKey = tokenHandler.getAccountKey(request);
            LongIdKey result = service.createActivityDataNode(
                    accountKey, WebInputActivityDataNodeCreateInfo.toStackBean(activityDataNodeCreateInfo)
            );
            return FastJsonResponseData.of(ResponseDataUtil.good(JSFixedFastJsonLongIdKey.of(result)));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @PostMapping("/activity-data-node/update")
    @BehaviorAnalyse
    @BindingCheck
    public FastJsonResponseData<Object> updateActivityDataNode(
            HttpServletRequest request,
            @RequestBody @Validated WebInputActivityDataNodeUpdateInfo webInputActivityDataNodeUpdateInfo,
            BindingResult bindingResult
    ) {
        try {
            StringIdKey accountKey = tokenHandler.getAccountKey(request);
            service.updateActivityDataNode(
                    accountKey, WebInputActivityDataNodeUpdateInfo.toStackBean(webInputActivityDataNodeUpdateInfo)
            );
            return FastJsonResponseData.of(ResponseDataUtil.good(null));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @PostMapping("/activity-data-node/remove")
    @BehaviorAnalyse
    @BindingCheck
    public FastJsonResponseData<Object> removeActivityDataNode(
            HttpServletRequest request,
            @RequestBody @Validated WebInputLongIdKey activityDataNodeKey, BindingResult bindingResult
    ) {
        try {
            StringIdKey accountKey = tokenHandler.getAccountKey(request);
            service.removeActivityDataNode(accountKey, WebInputLongIdKey.toStackBean(activityDataNodeKey));
            return FastJsonResponseData.of(ResponseDataUtil.good(null));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }
}
