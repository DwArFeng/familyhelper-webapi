package com.dwarfeng.familyhelper.webapi.node.controller.v1.life;

import com.dwarfeng.familyhelper.life.sdk.bean.dto.WebInputPbItemCreateInfo;
import com.dwarfeng.familyhelper.life.sdk.bean.dto.WebInputPbItemUpdateInfo;
import com.dwarfeng.familyhelper.life.sdk.bean.entity.JSFixedFastJsonPbItem;
import com.dwarfeng.familyhelper.life.stack.bean.entity.PbItem;
import com.dwarfeng.familyhelper.webapi.sdk.bean.disp.life.JSFixedFastJsonDispPbItem;
import com.dwarfeng.familyhelper.webapi.stack.bean.disp.life.DispPbItem;
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
import com.dwarfeng.subgrade.stack.bean.BeanTransformer;
import com.dwarfeng.subgrade.stack.bean.dto.PagedData;
import com.dwarfeng.subgrade.stack.bean.dto.PagingInfo;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.bean.key.StringIdKey;
import com.dwarfeng.subgrade.stack.exception.ServiceExceptionMapper;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 个人最佳项目控制器。
 *
 * @author DwArFeng
 * @since 1.0.7
 */
@RestController
@RequestMapping("/api/v1/life")
public class PbItemController {

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
    public FastJsonResponseData<Boolean> exists(HttpServletRequest request, @PathVariable("id") Long id) {
        try {
            boolean exists = service.exists(new LongIdKey(id));
            return FastJsonResponseData.of(ResponseDataUtil.good(exists));
        } catch (Exception e) {
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @GetMapping("/pb-item/{id}")
    @BehaviorAnalyse
    @LoginRequired
    public FastJsonResponseData<JSFixedFastJsonPbItem> get(HttpServletRequest request, @PathVariable("id") Long id) {
        try {
            PbItem pbItem = service.get(new LongIdKey(id));
            return FastJsonResponseData.of(ResponseDataUtil.good(JSFixedFastJsonPbItem.of(pbItem)));
        } catch (Exception e) {
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @GetMapping("/pb-item/all")
    @BehaviorAnalyse
    @SkipRecord
    @LoginRequired
    public FastJsonResponseData<JSFixedFastJsonPagedData<JSFixedFastJsonPbItem>> all(
            HttpServletRequest request, @RequestParam("page") int page, @RequestParam("rows") int rows
    ) {
        try {
            PagedData<PbItem> all = service.all(new PagingInfo(page, rows));
            PagedData<JSFixedFastJsonPbItem> transform = PagingUtil.transform(all, pbItemBeanTransformer);
            return FastJsonResponseData.of(ResponseDataUtil.good(JSFixedFastJsonPagedData.of(transform)));
        } catch (Exception e) {
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @GetMapping(value = {
            "/pb-node/{pbNodeId}/pb-item", "/pb-node//pb-item"
    })
    @BehaviorAnalyse
    @SkipRecord
    @LoginRequired
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
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @GetMapping("/pb-set/{pbSetId}/pb-item/root")
    @BehaviorAnalyse
    @SkipRecord
    @LoginRequired
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
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @GetMapping("/pb-set/{pbSetId}/pb-item/name-like")
    @BehaviorAnalyse
    @SkipRecord
    @LoginRequired
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
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @GetMapping("/pb-item/{id}/disp")
    @BehaviorAnalyse
    @SkipRecord
    @LoginRequired
    public FastJsonResponseData<JSFixedFastJsonDispPbItem> getDisp(
            HttpServletRequest request, @PathVariable("id") Long id
    ) {
        try {
            StringIdKey inspectAccountKey = tokenHandler.getAccountKey(request);
            DispPbItem dispPbItem = service.getDisp(new LongIdKey(id), inspectAccountKey);
            return FastJsonResponseData.of(ResponseDataUtil.good(JSFixedFastJsonDispPbItem.of(dispPbItem)));
        } catch (Exception e) {
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @GetMapping("/pb-item/all/disp")
    @BehaviorAnalyse
    @SkipRecord
    @LoginRequired
    public FastJsonResponseData<JSFixedFastJsonPagedData<JSFixedFastJsonDispPbItem>> allDisp(
            HttpServletRequest request, @RequestParam("page") int page, @RequestParam("rows") int rows) {
        try {
            StringIdKey accountKey = tokenHandler.getAccountKey(request);
            PagedData<DispPbItem> allDisp = service.allDisp(accountKey, new PagingInfo(page, rows));
            PagedData<JSFixedFastJsonDispPbItem> transform = PagingUtil.transform(
                    allDisp, dispPbItemBeanTransformer);
            return FastJsonResponseData.of(ResponseDataUtil.good(JSFixedFastJsonPagedData.of(transform)));
        } catch (Exception e) {
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @GetMapping(value = {
            "/pb-node/{pbNodeId}/pb-item/disp", "/pb-node//pb-item/disp"
    })
    @BehaviorAnalyse
    @SkipRecord
    @LoginRequired
    public FastJsonResponseData<JSFixedFastJsonPagedData<JSFixedFastJsonDispPbItem>> childForPbNodeDisp(
            HttpServletRequest request,
            @PathVariable(required = false, value = "pbNodeId") Long pbNodeId,
            @RequestParam("page") int page, @RequestParam("rows") int rows
    ) {
        try {
            StringIdKey accountKey = tokenHandler.getAccountKey(request);
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
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @GetMapping("/pb-set/{pbSetId}/pb-item/root/disp")
    @BehaviorAnalyse
    @SkipRecord
    @LoginRequired
    public FastJsonResponseData<JSFixedFastJsonPagedData<JSFixedFastJsonDispPbItem>> childForPbSetRootDisp(
            HttpServletRequest request,
            @PathVariable("pbSetId") long pbSetId,
            @RequestParam("page") int page, @RequestParam("rows") int rows
    ) {
        try {
            StringIdKey accountKey = tokenHandler.getAccountKey(request);
            PagedData<DispPbItem> childForPbSetRoot = service.childForPbSetRootDisp(
                    accountKey, new LongIdKey(pbSetId), new PagingInfo(page, rows)
            );
            PagedData<JSFixedFastJsonDispPbItem> transform = PagingUtil.transform(
                    childForPbSetRoot, dispPbItemBeanTransformer
            );
            return FastJsonResponseData.of(ResponseDataUtil.good(JSFixedFastJsonPagedData.of(transform)));
        } catch (Exception e) {
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @GetMapping("/pb-set/{pbSetId}/pb-item/name-like/disp")
    @BehaviorAnalyse
    @SkipRecord
    @LoginRequired
    public FastJsonResponseData<JSFixedFastJsonPagedData<JSFixedFastJsonDispPbItem>> childForPbBookNameLikeDisp(
            HttpServletRequest request,
            @PathVariable("pbSetId") long pbSetId, @RequestParam("pattern") String pattern,
            @RequestParam("page") int page, @RequestParam("rows") int rows
    ) {
        try {
            StringIdKey accountKey = tokenHandler.getAccountKey(request);
            PagedData<DispPbItem> childForPbBookRoot = service.childForPbSetNameLikeDisp(
                    accountKey, new LongIdKey(pbSetId), pattern, new PagingInfo(page, rows)
            );
            PagedData<JSFixedFastJsonDispPbItem> transform = PagingUtil.transform(
                    childForPbBookRoot, dispPbItemBeanTransformer
            );
            return FastJsonResponseData.of(ResponseDataUtil.good(JSFixedFastJsonPagedData.of(transform)));
        } catch (Exception e) {
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @PostMapping("/pb-item/create")
    @BehaviorAnalyse
    @BindingCheck
    public FastJsonResponseData<JSFixedFastJsonLongIdKey> createPbItem(
            HttpServletRequest request,
            @RequestBody @Validated WebInputPbItemCreateInfo pbItemCreateInfo, BindingResult bindingResult
    ) {
        try {
            StringIdKey accountKey = tokenHandler.getAccountKey(request);
            LongIdKey result = service.createPbItem(
                    accountKey, WebInputPbItemCreateInfo.toStackBean(pbItemCreateInfo)
            );
            return FastJsonResponseData.of(ResponseDataUtil.good(JSFixedFastJsonLongIdKey.of(result)));
        } catch (Exception e) {
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @PostMapping("/pb-item/update")
    @BehaviorAnalyse
    @BindingCheck
    public FastJsonResponseData<Object> updatePbItem(
            HttpServletRequest request,
            @RequestBody @Validated WebInputPbItemUpdateInfo webInputPbItemUpdateInfo, BindingResult bindingResult
    ) {
        try {
            StringIdKey accountKey = tokenHandler.getAccountKey(request);
            service.updatePbItem(
                    accountKey, WebInputPbItemUpdateInfo.toStackBean(webInputPbItemUpdateInfo)
            );
            return FastJsonResponseData.of(ResponseDataUtil.good(null));
        } catch (Exception e) {
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @PostMapping("/pb-item/remove")
    @BehaviorAnalyse
    @BindingCheck
    public FastJsonResponseData<Object> removePbItem(
            HttpServletRequest request,
            @RequestBody @Validated WebInputLongIdKey pbItemKey, BindingResult bindingResult
    ) {
        try {
            StringIdKey accountKey = tokenHandler.getAccountKey(request);
            service.removePbItem(accountKey, WebInputLongIdKey.toStackBean(pbItemKey));
            return FastJsonResponseData.of(ResponseDataUtil.good(null));
        } catch (Exception e) {
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @SuppressWarnings("DuplicatedCode")
    @GetMapping("/pb-item/{id}/path-from-root")
    @BehaviorAnalyse
    @BindingCheck
    public FastJsonResponseData<List<JSFixedFastJsonLongIdKey>> pathFromRoot(
            HttpServletRequest request, @PathVariable("id") Long id
    ) {
        try {
            List<LongIdKey> longIdKeys = service.pathFromRoot(new LongIdKey(id));
            return FastJsonResponseData.of(ResponseDataUtil.good(
                    longIdKeys.stream().map(JSFixedFastJsonLongIdKey::of).collect(Collectors.toList())
            ));
        } catch (Exception e) {
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }
}
