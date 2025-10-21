package com.dwarfeng.familyhelper.webapi.node.controller.v1.life;

import com.dwarfeng.familyhelper.life.sdk.bean.dto.WebInputActivityDataItemCreateInfo;
import com.dwarfeng.familyhelper.life.sdk.bean.dto.WebInputActivityDataItemUpdateInfo;
import com.dwarfeng.familyhelper.life.sdk.bean.entity.JSFixedFastJsonActivityDataItem;
import com.dwarfeng.familyhelper.life.stack.bean.entity.ActivityDataItem;
import com.dwarfeng.familyhelper.webapi.sdk.bean.life.disp.JSFixedFastJsonDispActivityDataItem;
import com.dwarfeng.familyhelper.webapi.stack.bean.life.disp.DispActivityDataItem;
import com.dwarfeng.familyhelper.webapi.stack.handler.system.TokenHandler;
import com.dwarfeng.familyhelper.webapi.stack.service.life.ActivityDataItemResponseService;
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
import com.dwarfeng.subgrade.sdk.interceptor.permission.PermissionRequired;
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
 * 活动数据项目控制器。
 *
 * @author DwArFeng
 * @since 1.0.10
 */
@RestController
@RequestMapping("/api/v1/life")
public class ActivityDataItemController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ActivityDataItemController.class);

    private final ActivityDataItemResponseService service;

    private final ServiceExceptionMapper sem;

    private final BeanTransformer<ActivityDataItem, JSFixedFastJsonActivityDataItem>
            activityDataItemBeanTransformer;
    private final BeanTransformer<DispActivityDataItem, JSFixedFastJsonDispActivityDataItem>
            dispActivityDataItemBeanTransformer;

    private final TokenHandler tokenHandler;

    public ActivityDataItemController(
            ActivityDataItemResponseService service, ServiceExceptionMapper sem,
            BeanTransformer<ActivityDataItem, JSFixedFastJsonActivityDataItem> activityDataItemBeanTransformer,
            BeanTransformer<DispActivityDataItem, JSFixedFastJsonDispActivityDataItem>
                    dispActivityDataItemBeanTransformer,
            TokenHandler tokenHandler
    ) {
        this.service = service;
        this.sem = sem;
        this.activityDataItemBeanTransformer = activityDataItemBeanTransformer;
        this.dispActivityDataItemBeanTransformer = dispActivityDataItemBeanTransformer;
        this.tokenHandler = tokenHandler;
    }

    @GetMapping("/activity-data-item/{id}/exists")
    @BehaviorAnalyse
    @LoginRequired
    @PermissionRequired("webapi.controller_permitted.life.activity_data_item_controller.exists")
    public FastJsonResponseData<Boolean> exists(HttpServletRequest request, @PathVariable("id") Long id) {
        try {
            boolean exists = service.exists(new LongIdKey(id));
            return FastJsonResponseData.of(ResponseDataUtil.good(exists));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @GetMapping("/activity-data-item/{id}")
    @BehaviorAnalyse
    @LoginRequired
    @PermissionRequired("webapi.controller_permitted.life.activity_data_item_controller.get")
    public FastJsonResponseData<JSFixedFastJsonActivityDataItem> get(
            HttpServletRequest request, @PathVariable("id") Long id
    ) {
        try {
            ActivityDataItem activityDataItem = service.get(new LongIdKey(id));
            return FastJsonResponseData.of(ResponseDataUtil.good(JSFixedFastJsonActivityDataItem.of(activityDataItem)));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @GetMapping("/activity-data-item/all")
    @BehaviorAnalyse
    @SkipRecord
    @LoginRequired
    @PermissionRequired("webapi.controller_permitted.life.activity_data_item_controller.all")
    public FastJsonResponseData<JSFixedFastJsonPagedData<JSFixedFastJsonActivityDataItem>> all(
            HttpServletRequest request, @RequestParam("page") int page, @RequestParam("rows") int rows
    ) {
        try {
            PagedData<ActivityDataItem> all = service.all(new PagingInfo(page, rows));
            PagedData<JSFixedFastJsonActivityDataItem> transform = PagingUtil.transform(
                    all, activityDataItemBeanTransformer
            );
            return FastJsonResponseData.of(ResponseDataUtil.good(JSFixedFastJsonPagedData.of(transform)));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @GetMapping(value = {
            "/activity-data-node/{activityDataNodeId}/activity-data-item",
            "/activity-data-node//activity-data-item"
    })
    @BehaviorAnalyse
    @SkipRecord
    @LoginRequired
    @PermissionRequired("webapi.controller_permitted.life.activity_data_item_controller.child_for_activity_data_node")
    public FastJsonResponseData<JSFixedFastJsonPagedData<JSFixedFastJsonActivityDataItem>> childForActivityDataNode(
            @PathVariable(required = false, value = "activityDataNodeId") Long activityDataNodeId,
            @RequestParam("page") int page, @RequestParam("rows") int rows
    ) {
        try {
            LongIdKey activityDataNodeKey = null;
            if (Objects.nonNull(activityDataNodeId)) {
                activityDataNodeKey = new LongIdKey(activityDataNodeId);
            }
            PagedData<ActivityDataItem> childForActivityDataNode = service.childForActivityDataNode(
                    activityDataNodeKey, new PagingInfo(page, rows));
            PagedData<JSFixedFastJsonActivityDataItem> transform = PagingUtil.transform(
                    childForActivityDataNode, activityDataItemBeanTransformer);
            return FastJsonResponseData.of(ResponseDataUtil.good(JSFixedFastJsonPagedData.of(transform)));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @GetMapping("/activity-data-set/{activityDataSetId}/activity-data-item/root")
    @BehaviorAnalyse
    @SkipRecord
    @LoginRequired
    @PermissionRequired("webapi.controller_permitted.life.activity_data_item_controller.child_for_activity_data_set_root")
    public FastJsonResponseData<JSFixedFastJsonPagedData<JSFixedFastJsonActivityDataItem>> childForActivityDataSetRoot(
            HttpServletRequest request,
            @PathVariable("activityDataSetId") long activityDataSetId,
            @RequestParam("page") int page, @RequestParam("rows") int rows
    ) {
        try {
            PagedData<ActivityDataItem> childForActivityDataSetRoot = service.childForActivityDataSetRoot(
                    new LongIdKey(activityDataSetId), new PagingInfo(page, rows)
            );
            PagedData<JSFixedFastJsonActivityDataItem> transform = PagingUtil.transform(
                    childForActivityDataSetRoot, activityDataItemBeanTransformer
            );
            return FastJsonResponseData.of(ResponseDataUtil.good(JSFixedFastJsonPagedData.of(transform)));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @GetMapping("/activity-data-set/{activityDataSetId}/activity-data-item/name-like")
    @BehaviorAnalyse
    @SkipRecord
    @LoginRequired
    @PermissionRequired("webapi.controller_permitted.life.activity_data_item_controller.child_for_activity_data_set_name_like")
    public FastJsonResponseData<JSFixedFastJsonPagedData<JSFixedFastJsonActivityDataItem>>
    childForActivityDataSetNameLike(
            HttpServletRequest request,
            @PathVariable("activityDataSetId") long activityDataSetId, @RequestParam("pattern") String pattern,
            @RequestParam("page") int page, @RequestParam("rows") int rows
    ) {
        try {
            PagedData<ActivityDataItem> childForActivityDataSetRoot = service.childForActivityDataSetNameLike(
                    new LongIdKey(activityDataSetId), pattern, new PagingInfo(page, rows)
            );
            PagedData<JSFixedFastJsonActivityDataItem> transform = PagingUtil.transform(
                    childForActivityDataSetRoot, activityDataItemBeanTransformer
            );
            return FastJsonResponseData.of(ResponseDataUtil.good(JSFixedFastJsonPagedData.of(transform)));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @GetMapping("/activity-data-item/{id}/disp")
    @BehaviorAnalyse
    @SkipRecord
    @LoginRequired
    @PermissionRequired("webapi.controller_permitted.life.activity_data_item_controller.get_disp")
    public FastJsonResponseData<JSFixedFastJsonDispActivityDataItem> getDisp(
            HttpServletRequest request, @PathVariable("id") Long id
    ) {
        try {
            StringIdKey inspectAccountKey = tokenHandler.getUserKey(request);
            DispActivityDataItem dispActivityDataItem = service.getDisp(new LongIdKey(id), inspectAccountKey);
            return FastJsonResponseData.of(
                    ResponseDataUtil.good(JSFixedFastJsonDispActivityDataItem.of(dispActivityDataItem))
            );
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @GetMapping("/activity-data-item/all/disp")
    @BehaviorAnalyse
    @SkipRecord
    @LoginRequired
    @PermissionRequired("webapi.controller_permitted.life.activity_data_item_controller.all_disp")
    public FastJsonResponseData<JSFixedFastJsonPagedData<JSFixedFastJsonDispActivityDataItem>> allDisp(
            HttpServletRequest request, @RequestParam("page") int page, @RequestParam("rows") int rows) {
        try {
            StringIdKey accountKey = tokenHandler.getUserKey(request);
            PagedData<DispActivityDataItem> allDisp = service.allDisp(accountKey, new PagingInfo(page, rows));
            PagedData<JSFixedFastJsonDispActivityDataItem> transform = PagingUtil.transform(
                    allDisp, dispActivityDataItemBeanTransformer);
            return FastJsonResponseData.of(ResponseDataUtil.good(JSFixedFastJsonPagedData.of(transform)));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @GetMapping(value = {
            "/activity-data-node/{activityDataNodeId}/activity-data-item/disp",
            "/activity-data-node//activity-data-item/disp"
    })
    @BehaviorAnalyse
    @SkipRecord
    @LoginRequired
    @PermissionRequired("webapi.controller_permitted.life.activity_data_item_controller.child_for_activity_data_node_disp")
    public FastJsonResponseData<JSFixedFastJsonPagedData<JSFixedFastJsonDispActivityDataItem>>
    childForActivityDataNodeDisp(
            HttpServletRequest request,
            @PathVariable(required = false, value = "activityDataNodeId") Long activityDataNodeId,
            @RequestParam("page") int page, @RequestParam("rows") int rows
    ) {
        try {
            StringIdKey accountKey = tokenHandler.getUserKey(request);
            LongIdKey activityDataNodeKey = null;
            if (Objects.nonNull(activityDataNodeId)) {
                activityDataNodeKey = new LongIdKey(activityDataNodeId);
            }
            PagedData<DispActivityDataItem> childForActivityDataNodeDisp = service.childForActivityDataNodeDisp(
                    accountKey, activityDataNodeKey, new PagingInfo(page, rows));
            PagedData<JSFixedFastJsonDispActivityDataItem> transform = PagingUtil.transform(
                    childForActivityDataNodeDisp, dispActivityDataItemBeanTransformer);
            return FastJsonResponseData.of(ResponseDataUtil.good(JSFixedFastJsonPagedData.of(transform)));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @GetMapping("/activity-data-set/{activityDataSetId}/activity-data-item/root/disp")
    @BehaviorAnalyse
    @SkipRecord
    @LoginRequired
    @PermissionRequired("webapi.controller_permitted.life.activity_data_item_controller.child_for_activity_data_set_root_disp")
    public FastJsonResponseData<JSFixedFastJsonPagedData<JSFixedFastJsonDispActivityDataItem>>
    childForActivityDataSetRootDisp(
            HttpServletRequest request,
            @PathVariable("activityDataSetId") long activityDataSetId,
            @RequestParam("page") int page, @RequestParam("rows") int rows
    ) {
        try {
            StringIdKey accountKey = tokenHandler.getUserKey(request);
            PagedData<DispActivityDataItem> childForActivityDataSetRoot = service.childForActivityDataSetRootDisp(
                    accountKey, new LongIdKey(activityDataSetId), new PagingInfo(page, rows)
            );
            PagedData<JSFixedFastJsonDispActivityDataItem> transform = PagingUtil.transform(
                    childForActivityDataSetRoot, dispActivityDataItemBeanTransformer
            );
            return FastJsonResponseData.of(ResponseDataUtil.good(JSFixedFastJsonPagedData.of(transform)));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @GetMapping("/activity-data-set/{activityDataSetId}/activity-data-item/name-like/disp")
    @BehaviorAnalyse
    @SkipRecord
    @LoginRequired
    @PermissionRequired("webapi.controller_permitted.life.activity_data_item_controller.child_for_activity_data_set_name_like_disp")
    public FastJsonResponseData<JSFixedFastJsonPagedData<JSFixedFastJsonDispActivityDataItem>>
    childForActivityDataSetNameLikeDisp(
            HttpServletRequest request,
            @PathVariable("activityDataSetId") long activityDataSetId, @RequestParam("pattern") String pattern,
            @RequestParam("page") int page, @RequestParam("rows") int rows
    ) {
        try {
            StringIdKey accountKey = tokenHandler.getUserKey(request);
            PagedData<DispActivityDataItem> childForActivityDataSetRoot = service.childForActivityDataSetNameLikeDisp(
                    accountKey, new LongIdKey(activityDataSetId), pattern, new PagingInfo(page, rows)
            );
            PagedData<JSFixedFastJsonDispActivityDataItem> transform = PagingUtil.transform(
                    childForActivityDataSetRoot, dispActivityDataItemBeanTransformer
            );
            return FastJsonResponseData.of(ResponseDataUtil.good(JSFixedFastJsonPagedData.of(transform)));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @PostMapping("/activity-data-item/create")
    @BehaviorAnalyse
    @BindingCheck
    public FastJsonResponseData<JSFixedFastJsonLongIdKey> createActivityDataItem(
            HttpServletRequest request,
            @RequestBody @Validated WebInputActivityDataItemCreateInfo activityDataItemCreateInfo,
            BindingResult bindingResult
    ) {
        try {
            StringIdKey accountKey = tokenHandler.getUserKey(request);
            LongIdKey result = service.createActivityDataItem(
                    accountKey, WebInputActivityDataItemCreateInfo.toStackBean(activityDataItemCreateInfo)
            );
            return FastJsonResponseData.of(ResponseDataUtil.good(JSFixedFastJsonLongIdKey.of(result)));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @PostMapping("/activity-data-item/update")
    @BehaviorAnalyse
    @BindingCheck
    public FastJsonResponseData<Object> updateActivityDataItem(
            HttpServletRequest request,
            @RequestBody @Validated WebInputActivityDataItemUpdateInfo webInputActivityDataItemUpdateInfo,
            BindingResult bindingResult
    ) {
        try {
            StringIdKey accountKey = tokenHandler.getUserKey(request);
            service.updateActivityDataItem(
                    accountKey, WebInputActivityDataItemUpdateInfo.toStackBean(webInputActivityDataItemUpdateInfo)
            );
            return FastJsonResponseData.of(ResponseDataUtil.good(null));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @PostMapping("/activity-data-item/remove")
    @BehaviorAnalyse
    @BindingCheck
    public FastJsonResponseData<Object> removeActivityDataItem(
            HttpServletRequest request,
            @RequestBody @Validated WebInputLongIdKey activityDataItemKey, BindingResult bindingResult
    ) {
        try {
            StringIdKey accountKey = tokenHandler.getUserKey(request);
            service.removeActivityDataItem(accountKey, WebInputLongIdKey.toStackBean(activityDataItemKey));
            return FastJsonResponseData.of(ResponseDataUtil.good(null));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }
}
