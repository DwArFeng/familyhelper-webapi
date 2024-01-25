package com.dwarfeng.familyhelper.webapi.node.controller.v1.assets;

import com.dwarfeng.familyhelper.assets.sdk.bean.dto.WebInputAssetCatalogCreateInfo;
import com.dwarfeng.familyhelper.assets.sdk.bean.dto.WebInputAssetCatalogUpdateInfo;
import com.dwarfeng.familyhelper.assets.sdk.bean.dto.WebInputPermissionRemoveInfo;
import com.dwarfeng.familyhelper.assets.sdk.bean.dto.WebInputPermissionUpsertInfo;
import com.dwarfeng.familyhelper.assets.sdk.bean.entity.JSFixedFastJsonAssetCatalog;
import com.dwarfeng.familyhelper.assets.stack.bean.entity.AssetCatalog;
import com.dwarfeng.familyhelper.webapi.sdk.bean.disp.assets.JSFixedFastJsonDispAssetCatalog;
import com.dwarfeng.familyhelper.webapi.stack.bean.disp.assets.DispAssetCatalog;
import com.dwarfeng.familyhelper.webapi.stack.handler.system.TokenHandler;
import com.dwarfeng.familyhelper.webapi.stack.service.assets.AssetCatalogResponseService;
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

/**
 * 资产目录控制器。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
@RestController
@RequestMapping("/api/v1/assets")
public class AssetCatalogController {

    private static final Logger LOGGER = LoggerFactory.getLogger(AssetCatalogController.class);

    private final AssetCatalogResponseService service;

    private final ServiceExceptionMapper sem;

    private final BeanTransformer<AssetCatalog, JSFixedFastJsonAssetCatalog> assetCatalogBeanTransformer;
    private final BeanTransformer<DispAssetCatalog, JSFixedFastJsonDispAssetCatalog> dispAssetCatalogBeanTransformer;

    private final TokenHandler tokenHandler;

    public AssetCatalogController(
            AssetCatalogResponseService service, ServiceExceptionMapper sem,
            BeanTransformer<AssetCatalog, JSFixedFastJsonAssetCatalog> assetCatalogBeanTransformer,
            BeanTransformer<DispAssetCatalog, JSFixedFastJsonDispAssetCatalog> dispAssetCatalogBeanTransformer,
            TokenHandler tokenHandler
    ) {
        this.service = service;
        this.sem = sem;
        this.assetCatalogBeanTransformer = assetCatalogBeanTransformer;
        this.dispAssetCatalogBeanTransformer = dispAssetCatalogBeanTransformer;
        this.tokenHandler = tokenHandler;
    }

    @GetMapping("/asset-catalog/{id}/exists")
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

    @GetMapping("/asset-catalog/{id}")
    @BehaviorAnalyse
    @LoginRequired
    public FastJsonResponseData<JSFixedFastJsonAssetCatalog> get(HttpServletRequest request, @PathVariable("id") Long id) {
        try {
            AssetCatalog assetCatalog = service.get(new LongIdKey(id));
            return FastJsonResponseData.of(ResponseDataUtil.good(JSFixedFastJsonAssetCatalog.of(assetCatalog)));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @GetMapping("/asset-catalog/all")
    @BehaviorAnalyse
    @SkipRecord
    @LoginRequired
    public FastJsonResponseData<JSFixedFastJsonPagedData<JSFixedFastJsonAssetCatalog>> all(
            HttpServletRequest request, @RequestParam("page") int page, @RequestParam("rows") int rows) {
        try {
            PagedData<AssetCatalog> all = service.all(new PagingInfo(page, rows));
            PagedData<JSFixedFastJsonAssetCatalog> transform = PagingUtil.transform(all, assetCatalogBeanTransformer);
            return FastJsonResponseData.of(ResponseDataUtil.good(JSFixedFastJsonPagedData.of(transform)));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @GetMapping("/asset-catalog/{id}/disp")
    @BehaviorAnalyse
    @SkipRecord
    @LoginRequired
    public FastJsonResponseData<JSFixedFastJsonDispAssetCatalog> getDisp(
            HttpServletRequest request, @PathVariable("id") Long id
    ) {
        try {
            StringIdKey inspectAccountKey = tokenHandler.getAccountKey(request);
            DispAssetCatalog dispAssetCatalog = service.getDisp(new LongIdKey(id), inspectAccountKey);
            return FastJsonResponseData.of(ResponseDataUtil.good(JSFixedFastJsonDispAssetCatalog.of(dispAssetCatalog)));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @GetMapping("/asset-catalog/all-permitted/disp")
    @BehaviorAnalyse
    @SkipRecord
    @LoginRequired
    public FastJsonResponseData<JSFixedFastJsonPagedData<JSFixedFastJsonDispAssetCatalog>> allPermittedDisp(
            HttpServletRequest request, @RequestParam("page") int page, @RequestParam("rows") int rows) {
        try {
            StringIdKey accountKey = tokenHandler.getAccountKey(request);
            PagedData<DispAssetCatalog> allPermittedDisp = service.allPermittedDisp(
                    accountKey, new PagingInfo(page, rows));
            PagedData<JSFixedFastJsonDispAssetCatalog> transform = PagingUtil.transform(
                    allPermittedDisp, dispAssetCatalogBeanTransformer);
            return FastJsonResponseData.of(ResponseDataUtil.good(JSFixedFastJsonPagedData.of(transform)));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @GetMapping("/asset-catalog/all-owned/disp")
    @BehaviorAnalyse
    @SkipRecord
    @LoginRequired
    public FastJsonResponseData<JSFixedFastJsonPagedData<JSFixedFastJsonDispAssetCatalog>> allOwnedDisp(
            HttpServletRequest request, @RequestParam("page") int page, @RequestParam("rows") int rows) {
        try {
            StringIdKey accountKey = tokenHandler.getAccountKey(request);
            PagedData<DispAssetCatalog> allOwnedDisp = service.allOwnedDisp(
                    accountKey, new PagingInfo(page, rows));
            PagedData<JSFixedFastJsonDispAssetCatalog> transform = PagingUtil.transform(
                    allOwnedDisp, dispAssetCatalogBeanTransformer);
            return FastJsonResponseData.of(ResponseDataUtil.good(JSFixedFastJsonPagedData.of(transform)));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @PostMapping("/asset-catalog/create")
    @BehaviorAnalyse
    @BindingCheck
    public FastJsonResponseData<JSFixedFastJsonLongIdKey> createAssetCatalog(
            HttpServletRequest request,
            @RequestBody @Validated WebInputAssetCatalogCreateInfo assetCatalogCreateInfo, BindingResult bindingResult) {
        try {
            StringIdKey accountKey = tokenHandler.getAccountKey(request);
            LongIdKey result = service.createAssetCatalog(
                    accountKey, WebInputAssetCatalogCreateInfo.toStackBean(assetCatalogCreateInfo));
            return FastJsonResponseData.of(ResponseDataUtil.good(JSFixedFastJsonLongIdKey.of(result)));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @PostMapping("/asset-catalog/update")
    @BehaviorAnalyse
    @BindingCheck
    public FastJsonResponseData<Object> updateAssetCatalog(
            HttpServletRequest request,
            @RequestBody @Validated WebInputAssetCatalogUpdateInfo webInputAssetCatalogUpdateInfo,
            BindingResult bindingResult
    ) {
        try {
            StringIdKey accountKey = tokenHandler.getAccountKey(request);
            service.updateAssetCatalog(
                    accountKey, WebInputAssetCatalogUpdateInfo.toStackBean(webInputAssetCatalogUpdateInfo)
            );
            return FastJsonResponseData.of(ResponseDataUtil.good(null));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @PostMapping("/asset-catalog/remove")
    @BehaviorAnalyse
    @BindingCheck
    public FastJsonResponseData<Object> removeAssetCatalog(
            HttpServletRequest request,
            @RequestBody @Validated WebInputLongIdKey assetCatalogKey, BindingResult bindingResult
    ) {
        try {
            StringIdKey accountKey = tokenHandler.getAccountKey(request);
            service.removeAssetCatalog(accountKey, WebInputLongIdKey.toStackBean(assetCatalogKey));
            return FastJsonResponseData.of(ResponseDataUtil.good(null));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @SuppressWarnings("DuplicatedCode")
    @PostMapping("/asset-catalog/upsert-permission")
    @BehaviorAnalyse
    @BindingCheck
    public FastJsonResponseData<Object> upsertPermission(
            HttpServletRequest request,
            @RequestBody @Validated WebInputPermissionUpsertInfo webInputPermissionUpsertInfo,
            BindingResult bindingResult
    ) {
        try {
            StringIdKey accountKey = tokenHandler.getAccountKey(request);
            service.upsertPermission(
                    accountKey, WebInputPermissionUpsertInfo.toStackBean(webInputPermissionUpsertInfo)
            );
            return FastJsonResponseData.of(ResponseDataUtil.good(null));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @SuppressWarnings("DuplicatedCode")
    @PostMapping("/asset-catalog/remove-permission")
    @BehaviorAnalyse
    @BindingCheck
    public FastJsonResponseData<Object> removePermission(
            HttpServletRequest request,
            @RequestBody @Validated WebInputPermissionRemoveInfo webInputPermissionRemoveInfo,
            BindingResult bindingResult
    ) {
        try {
            StringIdKey accountKey = tokenHandler.getAccountKey(request);
            service.removePermission(
                    accountKey, WebInputPermissionRemoveInfo.toStackBean(webInputPermissionRemoveInfo)
            );
            return FastJsonResponseData.of(ResponseDataUtil.good(null));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }
}
