package com.dwarfeng.familyhelper.webapi.node.controller.v1.assets;

import com.dwarfeng.familyhelper.assets.sdk.bean.dto.WebInputItemCreateInfo;
import com.dwarfeng.familyhelper.assets.sdk.bean.dto.WebInputItemUpdateInfo;
import com.dwarfeng.familyhelper.assets.sdk.bean.entity.JSFixedFastJsonItem;
import com.dwarfeng.familyhelper.assets.stack.bean.entity.Item;
import com.dwarfeng.familyhelper.webapi.sdk.bean.assets.disp.JSFixedFastJsonDispItem;
import com.dwarfeng.familyhelper.webapi.stack.bean.assets.disp.DispItem;
import com.dwarfeng.familyhelper.webapi.stack.handler.system.TokenHandler;
import com.dwarfeng.familyhelper.webapi.stack.service.assets.ItemResponseService;
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
 * 项目控制器。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
@RestController
@RequestMapping("/api/v1/assets")
public class ItemController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ItemController.class);

    private final ItemResponseService service;

    private final ServiceExceptionMapper sem;

    private final BeanTransformer<Item, JSFixedFastJsonItem> itemBeanTransformer;
    private final BeanTransformer<DispItem, JSFixedFastJsonDispItem> dispItemBeanTransformer;

    private final TokenHandler tokenHandler;

    public ItemController(
            ItemResponseService service, ServiceExceptionMapper sem,
            BeanTransformer<Item, JSFixedFastJsonItem> itemBeanTransformer,
            BeanTransformer<DispItem, JSFixedFastJsonDispItem> dispItemBeanTransformer,
            TokenHandler tokenHandler
    ) {
        this.service = service;
        this.sem = sem;
        this.itemBeanTransformer = itemBeanTransformer;
        this.dispItemBeanTransformer = dispItemBeanTransformer;
        this.tokenHandler = tokenHandler;
    }

    @GetMapping("/item/{id}/exists")
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

    @GetMapping("/item/{id}")
    @BehaviorAnalyse
    @LoginRequired
    public FastJsonResponseData<JSFixedFastJsonItem> get(HttpServletRequest request, @PathVariable("id") Long id) {
        try {
            Item item = service.get(new LongIdKey(id));
            return FastJsonResponseData.of(ResponseDataUtil.good(JSFixedFastJsonItem.of(item)));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @GetMapping("/item/all")
    @BehaviorAnalyse
    @SkipRecord
    @LoginRequired
    public FastJsonResponseData<JSFixedFastJsonPagedData<JSFixedFastJsonItem>> all(
            HttpServletRequest request, @RequestParam("page") int page, @RequestParam("rows") int rows) {
        try {
            PagedData<Item> all = service.all(new PagingInfo(page, rows));
            PagedData<JSFixedFastJsonItem> transform = PagingUtil.transform(all, itemBeanTransformer);
            return FastJsonResponseData.of(ResponseDataUtil.good(JSFixedFastJsonPagedData.of(transform)));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @GetMapping(value = {
            "/asset-catalog/{assetCatalogId}/item", "/asset-catalog//item"
    })
    @BehaviorAnalyse
    @SkipRecord
    @LoginRequired
    public FastJsonResponseData<JSFixedFastJsonPagedData<JSFixedFastJsonItem>> childForAssetCatalog(
            @PathVariable(required = false, value = "assetCatalogId") Long assetCatalogId,
            @RequestParam("page") int page, @RequestParam("rows") int rows
    ) {
        try {
            LongIdKey assetCatalogKey = null;
            if (Objects.nonNull(assetCatalogId)) {
                assetCatalogKey = new LongIdKey(assetCatalogId);
            }
            PagedData<Item> childForAssetCatalog = service.childForAssetCatalog(
                    assetCatalogKey, new PagingInfo(page, rows));
            PagedData<JSFixedFastJsonItem> transform = PagingUtil.transform(
                    childForAssetCatalog, itemBeanTransformer);
            return FastJsonResponseData.of(ResponseDataUtil.good(JSFixedFastJsonPagedData.of(transform)));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @GetMapping("/asset-catalog/{assetCatalogId}/item/root")
    @BehaviorAnalyse
    @SkipRecord
    @LoginRequired
    public FastJsonResponseData<JSFixedFastJsonPagedData<JSFixedFastJsonItem>> childForAssetCatalogRoot(
            HttpServletRequest request,
            @PathVariable("assetCatalogId") long assetCatalogId,
            @RequestParam("page") int page, @RequestParam("rows") int rows
    ) {
        try {
            PagedData<Item> childForAssetCatalogRoot = service.childForAssetCatalogRoot(
                    new LongIdKey(assetCatalogId), new PagingInfo(page, rows)
            );
            PagedData<JSFixedFastJsonItem> transform = PagingUtil.transform(
                    childForAssetCatalogRoot, itemBeanTransformer
            );
            return FastJsonResponseData.of(ResponseDataUtil.good(JSFixedFastJsonPagedData.of(transform)));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @GetMapping("/item/{parentId}/child")
    @BehaviorAnalyse
    @SkipRecord
    @LoginRequired
    public FastJsonResponseData<JSFixedFastJsonPagedData<JSFixedFastJsonItem>> childForParent(
            HttpServletRequest request,
            @PathVariable("parentId") long parentId,
            @RequestParam("page") int page, @RequestParam("rows") int rows
    ) {
        try {
            PagedData<Item> childForParent = service.childForParent(
                    new LongIdKey(parentId), new PagingInfo(page, rows)
            );
            PagedData<JSFixedFastJsonItem> transform = PagingUtil.transform(
                    childForParent, itemBeanTransformer
            );
            return FastJsonResponseData.of(ResponseDataUtil.good(JSFixedFastJsonPagedData.of(transform)));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @GetMapping("/asset-catalog/{assetCatalogId}/item/name-like")
    @BehaviorAnalyse
    @SkipRecord
    @LoginRequired
    public FastJsonResponseData<JSFixedFastJsonPagedData<JSFixedFastJsonItem>> childForAssetCatalogNameLike(
            HttpServletRequest request,
            @PathVariable("assetCatalogId") long assetCatalogId, @RequestParam("pattern") String pattern,
            @RequestParam("page") int page, @RequestParam("rows") int rows
    ) {
        try {
            PagedData<Item> childForNoteBookRoot = service.childForAssetCatalogNameLike(
                    new LongIdKey(assetCatalogId), pattern, new PagingInfo(page, rows)
            );
            PagedData<JSFixedFastJsonItem> transform = PagingUtil.transform(
                    childForNoteBookRoot, itemBeanTransformer
            );
            return FastJsonResponseData.of(ResponseDataUtil.good(JSFixedFastJsonPagedData.of(transform)));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @GetMapping("/item/{id}/disp")
    @BehaviorAnalyse
    @SkipRecord
    @LoginRequired
    public FastJsonResponseData<JSFixedFastJsonDispItem> getDisp(
            HttpServletRequest request, @PathVariable("id") Long id
    ) {
        try {
            StringIdKey inspectAccountKey = tokenHandler.getAccountKey(request);
            DispItem dispItem = service.getDisp(new LongIdKey(id), inspectAccountKey);
            return FastJsonResponseData.of(ResponseDataUtil.good(JSFixedFastJsonDispItem.of(dispItem)));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @GetMapping("/item/all/disp")
    @BehaviorAnalyse
    @SkipRecord
    @LoginRequired
    public FastJsonResponseData<JSFixedFastJsonPagedData<JSFixedFastJsonDispItem>> allDisp(
            HttpServletRequest request, @RequestParam("page") int page, @RequestParam("rows") int rows) {
        try {
            StringIdKey accountKey = tokenHandler.getAccountKey(request);
            PagedData<DispItem> allDisp = service.allDisp(accountKey, new PagingInfo(page, rows));
            PagedData<JSFixedFastJsonDispItem> transform = PagingUtil.transform(
                    allDisp, dispItemBeanTransformer);
            return FastJsonResponseData.of(ResponseDataUtil.good(JSFixedFastJsonPagedData.of(transform)));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @GetMapping(value = {
            "/asset-catalog/{assetCatalogId}/item/disp", "/asset-catalog//item/disp"
    })
    @BehaviorAnalyse
    @SkipRecord
    @LoginRequired
    public FastJsonResponseData<JSFixedFastJsonPagedData<JSFixedFastJsonDispItem>> childForAssetCatalogDisp(
            HttpServletRequest request,
            @PathVariable(required = false, value = "assetCatalogId") Long assetCatalogId,
            @RequestParam("page") int page, @RequestParam("rows") int rows
    ) {
        try {
            StringIdKey accountKey = tokenHandler.getAccountKey(request);
            LongIdKey assetCatalogKey = null;
            if (Objects.nonNull(assetCatalogId)) {
                assetCatalogKey = new LongIdKey(assetCatalogId);
            }
            PagedData<DispItem> childForAssetCatalogDisp = service.childForAssetCatalogDisp(
                    accountKey, assetCatalogKey, new PagingInfo(page, rows));
            PagedData<JSFixedFastJsonDispItem> transform = PagingUtil.transform(
                    childForAssetCatalogDisp, dispItemBeanTransformer);
            return FastJsonResponseData.of(ResponseDataUtil.good(JSFixedFastJsonPagedData.of(transform)));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @GetMapping("/asset-catalog/{assetCatalogId}/item/root/disp")
    @BehaviorAnalyse
    @SkipRecord
    @LoginRequired
    public FastJsonResponseData<JSFixedFastJsonPagedData<JSFixedFastJsonDispItem>> childForAssetCatalogRootDisp(
            HttpServletRequest request,
            @PathVariable("assetCatalogId") long assetCatalogId,
            @RequestParam("page") int page, @RequestParam("rows") int rows
    ) {
        try {
            StringIdKey accountKey = tokenHandler.getAccountKey(request);
            PagedData<DispItem> childForAssetCatalogRoot = service.childForAssetCatalogRootDisp(
                    accountKey, new LongIdKey(assetCatalogId), new PagingInfo(page, rows)
            );
            PagedData<JSFixedFastJsonDispItem> transform = PagingUtil.transform(
                    childForAssetCatalogRoot, dispItemBeanTransformer
            );
            return FastJsonResponseData.of(ResponseDataUtil.good(JSFixedFastJsonPagedData.of(transform)));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @GetMapping("/item/{parentId}/child/disp")
    @BehaviorAnalyse
    @SkipRecord
    @LoginRequired
    public FastJsonResponseData<JSFixedFastJsonPagedData<JSFixedFastJsonDispItem>> childForParentDisp(
            HttpServletRequest request,
            @PathVariable("parentId") long parentId,
            @RequestParam("page") int page, @RequestParam("rows") int rows
    ) {
        try {
            StringIdKey accountKey = tokenHandler.getAccountKey(request);
            PagedData<DispItem> childForParent = service.childForParentDisp(
                    accountKey, new LongIdKey(parentId), new PagingInfo(page, rows)
            );
            PagedData<JSFixedFastJsonDispItem> transform = PagingUtil.transform(
                    childForParent, dispItemBeanTransformer
            );
            return FastJsonResponseData.of(ResponseDataUtil.good(JSFixedFastJsonPagedData.of(transform)));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @GetMapping("/asset-catalog/{assetCatalogId}/item/name-like/disp")
    @BehaviorAnalyse
    @SkipRecord
    @LoginRequired
    public FastJsonResponseData<JSFixedFastJsonPagedData<JSFixedFastJsonDispItem>> childForAssetCatalogNameLikeDisp(
            HttpServletRequest request,
            @PathVariable("assetCatalogId") long assetCatalogId, @RequestParam("pattern") String pattern,
            @RequestParam("page") int page, @RequestParam("rows") int rows
    ) {
        try {
            StringIdKey accountKey = tokenHandler.getAccountKey(request);
            PagedData<DispItem> childForNoteBookRoot = service.childForAssetCatalogNameLikeDisp(
                    accountKey, new LongIdKey(assetCatalogId), pattern, new PagingInfo(page, rows)
            );
            PagedData<JSFixedFastJsonDispItem> transform = PagingUtil.transform(
                    childForNoteBookRoot, dispItemBeanTransformer
            );
            return FastJsonResponseData.of(ResponseDataUtil.good(JSFixedFastJsonPagedData.of(transform)));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @GetMapping("/item/{id}/path-from-root")
    @BehaviorAnalyse
    @SkipRecord
    @LoginRequired
    public FastJsonResponseData<JSFixedFastJsonPagedData<JSFixedFastJsonItem>> pathFromRoot(
            HttpServletRequest request, @PathVariable("id") Long id
    ) {
        try {
            PagedData<Item> pathFromRoot = service.pathFromRoot(new LongIdKey(id));
            PagedData<JSFixedFastJsonItem> transform = PagingUtil.transform(
                    pathFromRoot, itemBeanTransformer
            );
            return FastJsonResponseData.of(ResponseDataUtil.good(JSFixedFastJsonPagedData.of(transform)));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @GetMapping("/item/{id}/path-from-root/disp")
    @BehaviorAnalyse
    @SkipRecord
    @LoginRequired
    public FastJsonResponseData<JSFixedFastJsonPagedData<JSFixedFastJsonDispItem>> pathFromRootDisp(
            HttpServletRequest request, @PathVariable("id") Long id
    ) {
        try {
            StringIdKey accountKey = tokenHandler.getAccountKey(request);
            PagedData<DispItem> pathFromRoot = service.pathFromRootDisp(accountKey, new LongIdKey(id));
            PagedData<JSFixedFastJsonDispItem> transform = PagingUtil.transform(
                    pathFromRoot, dispItemBeanTransformer
            );
            return FastJsonResponseData.of(ResponseDataUtil.good(JSFixedFastJsonPagedData.of(transform)));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @PostMapping("/item/create")
    @BehaviorAnalyse
    @BindingCheck
    public FastJsonResponseData<JSFixedFastJsonLongIdKey> createItem(
            HttpServletRequest request,
            @RequestBody @Validated WebInputItemCreateInfo itemCreateInfo, BindingResult bindingResult
    ) {
        try {
            StringIdKey accountKey = tokenHandler.getAccountKey(request);
            LongIdKey result = service.createItem(
                    accountKey, WebInputItemCreateInfo.toStackBean(itemCreateInfo)
            );
            return FastJsonResponseData.of(ResponseDataUtil.good(JSFixedFastJsonLongIdKey.of(result)));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @PostMapping("/item/update")
    @BehaviorAnalyse
    @BindingCheck
    public FastJsonResponseData<Object> updateItem(
            HttpServletRequest request,
            @RequestBody @Validated WebInputItemUpdateInfo webInputItemUpdateInfo, BindingResult bindingResult
    ) {
        try {
            StringIdKey accountKey = tokenHandler.getAccountKey(request);
            service.updateItem(
                    accountKey, WebInputItemUpdateInfo.toStackBean(webInputItemUpdateInfo)
            );
            return FastJsonResponseData.of(ResponseDataUtil.good(null));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @PostMapping("/item/remove")
    @BehaviorAnalyse
    @BindingCheck
    public FastJsonResponseData<Object> removeItem(
            HttpServletRequest request,
            @RequestBody @Validated WebInputLongIdKey itemKey, BindingResult bindingResult
    ) {
        try {
            StringIdKey accountKey = tokenHandler.getAccountKey(request);
            service.removeItem(accountKey, WebInputLongIdKey.toStackBean(itemKey));
            return FastJsonResponseData.of(ResponseDataUtil.good(null));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }
}
