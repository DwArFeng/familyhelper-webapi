package com.dwarfeng.familyhelper.webapi.node.controller.v1.life;

import com.dwarfeng.familyhelper.life.sdk.bean.dto.WebInputPbItemCreateInfo;
import com.dwarfeng.familyhelper.life.sdk.bean.dto.WebInputPbItemUpdateInfo;
import com.dwarfeng.familyhelper.life.sdk.bean.entity.JSFixedFastJsonPbItem;
import com.dwarfeng.familyhelper.life.stack.bean.entity.PbItem;
import com.dwarfeng.familyhelper.webapi.sdk.bean.life.disp.JSFixedFastJsonDispPbItem;
import com.dwarfeng.familyhelper.webapi.stack.bean.life.disp.DispPbItem;
import com.dwarfeng.familyhelper.webapi.stack.handler.system.TokenHandler;
import com.dwarfeng.familyhelper.webapi.stack.service.life.PbItemResponseService;
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
 * 个人最佳项目控制器。
 *
 * @author DwArFeng
 * @since 1.0.7
 */
@RestController
@RequestMapping("/api/v1/life")
public class PbItemController {

    private static final Logger LOGGER = LoggerFactory.getLogger(PbItemController.class);

    private final PbItemResponseService service;

    private final ServiceExceptionMapper sem;

    private final BeanTransformer<PbItem, JSFixedFastJsonPbItem> pbItemBeanTransformer;
    private final BeanTransformer<DispPbItem, JSFixedFastJsonDispPbItem> dispPbItemBeanTransformer;

    private final TokenHandler tokenHandler;

    public PbItemController(
            PbItemResponseService service, ServiceExceptionMapper sem,
            BeanTransformer<PbItem, JSFixedFastJsonPbItem> pbItemBeanTransformer,
            BeanTransformer<DispPbItem, JSFixedFastJsonDispPbItem> dispPbItemBeanTransformer,
            TokenHandler tokenHandler
    ) {
        this.service = service;
        this.sem = sem;
        this.pbItemBeanTransformer = pbItemBeanTransformer;
        this.dispPbItemBeanTransformer = dispPbItemBeanTransformer;
        this.tokenHandler = tokenHandler;
    }

    @GetMapping("/pb-item/{id}/exists")
    @BehaviorAnalyse
    @LoginRequired
    @PermissionRequired("webapi.controller_permitted.life.pb_item.exists")
    public FastJsonResponseData<Boolean> exists(HttpServletRequest request, @PathVariable("id") Long id) {
        try {
            boolean exists = service.exists(new LongIdKey(id));
            return FastJsonResponseData.of(ResponseDataUtil.good(exists));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @GetMapping("/pb-item/{id}")
    @BehaviorAnalyse
    @LoginRequired
    @PermissionRequired("webapi.controller_permitted.life.pb_item.get")
    public FastJsonResponseData<JSFixedFastJsonPbItem> get(HttpServletRequest request, @PathVariable("id") Long id) {
        try {
            PbItem pbItem = service.get(new LongIdKey(id));
            return FastJsonResponseData.of(ResponseDataUtil.good(JSFixedFastJsonPbItem.of(pbItem)));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @GetMapping("/pb-item/all")
    @BehaviorAnalyse
    @SkipRecord
    @LoginRequired
    @PermissionRequired("webapi.controller_permitted.life.pb_item.all")
    public FastJsonResponseData<JSFixedFastJsonPagedData<JSFixedFastJsonPbItem>> all(
            HttpServletRequest request, @RequestParam("page") int page, @RequestParam("rows") int rows
    ) {
        try {
            PagedData<PbItem> all = service.all(new PagingInfo(page, rows));
            PagedData<JSFixedFastJsonPbItem> transform = PagingUtil.transform(all, pbItemBeanTransformer);
            return FastJsonResponseData.of(ResponseDataUtil.good(JSFixedFastJsonPagedData.of(transform)));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @GetMapping(value = {
            "/pb-node/{pbNodeId}/pb-item", "/pb-node//pb-item"
    })
    @BehaviorAnalyse
    @SkipRecord
    @LoginRequired
    @PermissionRequired("webapi.controller_permitted.life.pb_item.child_for_pb_node")
    public FastJsonResponseData<JSFixedFastJsonPagedData<JSFixedFastJsonPbItem>> childForPbNode(
            @PathVariable(required = false, value = "pbNodeId") Long pbNodeId,
            @RequestParam("page") int page, @RequestParam("rows") int rows
    ) {
        try {
            LongIdKey pbNodeKey = null;
            if (Objects.nonNull(pbNodeId)) {
                pbNodeKey = new LongIdKey(pbNodeId);
            }
            PagedData<PbItem> childForPbNode = service.childForPbNode(
                    pbNodeKey, new PagingInfo(page, rows));
            PagedData<JSFixedFastJsonPbItem> transform = PagingUtil.transform(
                    childForPbNode, pbItemBeanTransformer);
            return FastJsonResponseData.of(ResponseDataUtil.good(JSFixedFastJsonPagedData.of(transform)));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @GetMapping("/pb-set/{pbSetId}/pb-item/root")
    @BehaviorAnalyse
    @SkipRecord
    @LoginRequired
    @PermissionRequired("webapi.controller_permitted.life.pb_item.child_for_pb_set_root")
    public FastJsonResponseData<JSFixedFastJsonPagedData<JSFixedFastJsonPbItem>> childForPbSetRoot(
            HttpServletRequest request,
            @PathVariable("pbSetId") long pbSetId,
            @RequestParam("page") int page, @RequestParam("rows") int rows
    ) {
        try {
            PagedData<PbItem> childForPbSetRoot = service.childForPbSetRoot(
                    new LongIdKey(pbSetId), new PagingInfo(page, rows)
            );
            PagedData<JSFixedFastJsonPbItem> transform = PagingUtil.transform(
                    childForPbSetRoot, pbItemBeanTransformer
            );
            return FastJsonResponseData.of(ResponseDataUtil.good(JSFixedFastJsonPagedData.of(transform)));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @GetMapping("/pb-set/{pbSetId}/pb-item/name-like")
    @BehaviorAnalyse
    @SkipRecord
    @LoginRequired
    @PermissionRequired("webapi.controller_permitted.life.pb_item.child_for_pb_book_name_like")
    public FastJsonResponseData<JSFixedFastJsonPagedData<JSFixedFastJsonPbItem>> childForPbBookNameLike(
            HttpServletRequest request,
            @PathVariable("pbSetId") long pbSetId, @RequestParam("pattern") String pattern,
            @RequestParam("page") int page, @RequestParam("rows") int rows
    ) {
        try {
            PagedData<PbItem> childForPbBookRoot = service.childForPbSetNameLike(
                    new LongIdKey(pbSetId), pattern, new PagingInfo(page, rows)
            );
            PagedData<JSFixedFastJsonPbItem> transform = PagingUtil.transform(
                    childForPbBookRoot, pbItemBeanTransformer
            );
            return FastJsonResponseData.of(ResponseDataUtil.good(JSFixedFastJsonPagedData.of(transform)));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @GetMapping("/pb-item/{id}/disp")
    @BehaviorAnalyse
    @SkipRecord
    @LoginRequired
    @PermissionRequired("webapi.controller_permitted.life.pb_item.get_disp")
    public FastJsonResponseData<JSFixedFastJsonDispPbItem> getDisp(
            HttpServletRequest request, @PathVariable("id") Long id
    ) {
        try {
            StringIdKey inspectAccountKey = tokenHandler.getUserKey(request);
            DispPbItem dispPbItem = service.getDisp(new LongIdKey(id), inspectAccountKey);
            return FastJsonResponseData.of(ResponseDataUtil.good(JSFixedFastJsonDispPbItem.of(dispPbItem)));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @GetMapping("/pb-item/all/disp")
    @BehaviorAnalyse
    @SkipRecord
    @LoginRequired
    @PermissionRequired("webapi.controller_permitted.life.pb_item.all_disp")
    public FastJsonResponseData<JSFixedFastJsonPagedData<JSFixedFastJsonDispPbItem>> allDisp(
            HttpServletRequest request, @RequestParam("page") int page, @RequestParam("rows") int rows) {
        try {
            StringIdKey accountKey = tokenHandler.getUserKey(request);
            PagedData<DispPbItem> allDisp = service.allDisp(accountKey, new PagingInfo(page, rows));
            PagedData<JSFixedFastJsonDispPbItem> transform = PagingUtil.transform(
                    allDisp, dispPbItemBeanTransformer);
            return FastJsonResponseData.of(ResponseDataUtil.good(JSFixedFastJsonPagedData.of(transform)));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @GetMapping(value = {
            "/pb-node/{pbNodeId}/pb-item/disp", "/pb-node//pb-item/disp"
    })
    @BehaviorAnalyse
    @SkipRecord
    @LoginRequired
    @PermissionRequired("webapi.controller_permitted.life.pb_item.child_for_pb_node_disp")
    public FastJsonResponseData<JSFixedFastJsonPagedData<JSFixedFastJsonDispPbItem>> childForPbNodeDisp(
            HttpServletRequest request,
            @PathVariable(required = false, value = "pbNodeId") Long pbNodeId,
            @RequestParam("page") int page, @RequestParam("rows") int rows
    ) {
        try {
            StringIdKey accountKey = tokenHandler.getUserKey(request);
            LongIdKey pbNodeKey = null;
            if (Objects.nonNull(pbNodeId)) {
                pbNodeKey = new LongIdKey(pbNodeId);
            }
            PagedData<DispPbItem> childForPbNodeDisp = service.childForPbNodeDisp(
                    accountKey, pbNodeKey, new PagingInfo(page, rows));
            PagedData<JSFixedFastJsonDispPbItem> transform = PagingUtil.transform(
                    childForPbNodeDisp, dispPbItemBeanTransformer);
            return FastJsonResponseData.of(ResponseDataUtil.good(JSFixedFastJsonPagedData.of(transform)));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @GetMapping("/pb-set/{pbSetId}/pb-item/root/disp")
    @BehaviorAnalyse
    @SkipRecord
    @LoginRequired
    @PermissionRequired("webapi.controller_permitted.life.pb_item.child_for_pb_set_root_disp")
    public FastJsonResponseData<JSFixedFastJsonPagedData<JSFixedFastJsonDispPbItem>> childForPbSetRootDisp(
            HttpServletRequest request,
            @PathVariable("pbSetId") long pbSetId,
            @RequestParam("page") int page, @RequestParam("rows") int rows
    ) {
        try {
            StringIdKey accountKey = tokenHandler.getUserKey(request);
            PagedData<DispPbItem> childForPbSetRoot = service.childForPbSetRootDisp(
                    accountKey, new LongIdKey(pbSetId), new PagingInfo(page, rows)
            );
            PagedData<JSFixedFastJsonDispPbItem> transform = PagingUtil.transform(
                    childForPbSetRoot, dispPbItemBeanTransformer
            );
            return FastJsonResponseData.of(ResponseDataUtil.good(JSFixedFastJsonPagedData.of(transform)));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @GetMapping("/pb-set/{pbSetId}/pb-item/name-like/disp")
    @BehaviorAnalyse
    @SkipRecord
    @LoginRequired
    @PermissionRequired("webapi.controller_permitted.life.pb_item.child_for_pb_book_name_like_disp")
    public FastJsonResponseData<JSFixedFastJsonPagedData<JSFixedFastJsonDispPbItem>> childForPbBookNameLikeDisp(
            HttpServletRequest request,
            @PathVariable("pbSetId") long pbSetId, @RequestParam("pattern") String pattern,
            @RequestParam("page") int page, @RequestParam("rows") int rows
    ) {
        try {
            StringIdKey accountKey = tokenHandler.getUserKey(request);
            PagedData<DispPbItem> childForPbBookRoot = service.childForPbSetNameLikeDisp(
                    accountKey, new LongIdKey(pbSetId), pattern, new PagingInfo(page, rows)
            );
            PagedData<JSFixedFastJsonDispPbItem> transform = PagingUtil.transform(
                    childForPbBookRoot, dispPbItemBeanTransformer
            );
            return FastJsonResponseData.of(ResponseDataUtil.good(JSFixedFastJsonPagedData.of(transform)));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @PostMapping("/pb-item/create")
    @BehaviorAnalyse
    @BindingCheck
    @LoginRequired
    @PermissionRequired("webapi.controller_permitted.life.pb_item.create_pb_item")
    public FastJsonResponseData<JSFixedFastJsonLongIdKey> createPbItem(
            HttpServletRequest request,
            @RequestBody @Validated WebInputPbItemCreateInfo pbItemCreateInfo, BindingResult bindingResult
    ) {
        try {
            StringIdKey accountKey = tokenHandler.getUserKey(request);
            LongIdKey result = service.createPbItem(
                    accountKey, WebInputPbItemCreateInfo.toStackBean(pbItemCreateInfo)
            );
            return FastJsonResponseData.of(ResponseDataUtil.good(JSFixedFastJsonLongIdKey.of(result)));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @PostMapping("/pb-item/update")
    @BehaviorAnalyse
    @BindingCheck
    @LoginRequired
    @PermissionRequired("webapi.controller_permitted.life.pb_item.update_pb_item")
    public FastJsonResponseData<Object> updatePbItem(
            HttpServletRequest request,
            @RequestBody @Validated WebInputPbItemUpdateInfo webInputPbItemUpdateInfo, BindingResult bindingResult
    ) {
        try {
            StringIdKey accountKey = tokenHandler.getUserKey(request);
            service.updatePbItem(
                    accountKey, WebInputPbItemUpdateInfo.toStackBean(webInputPbItemUpdateInfo)
            );
            return FastJsonResponseData.of(ResponseDataUtil.good(null));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @PostMapping("/pb-item/remove")
    @BehaviorAnalyse
    @BindingCheck
    @LoginRequired
    @PermissionRequired("webapi.controller_permitted.life.pb_item.remove_pb_item")
    public FastJsonResponseData<Object> removePbItem(
            HttpServletRequest request,
            @RequestBody @Validated WebInputLongIdKey pbItemKey, BindingResult bindingResult
    ) {
        try {
            StringIdKey accountKey = tokenHandler.getUserKey(request);
            service.removePbItem(accountKey, WebInputLongIdKey.toStackBean(pbItemKey));
            return FastJsonResponseData.of(ResponseDataUtil.good(null));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }
}
