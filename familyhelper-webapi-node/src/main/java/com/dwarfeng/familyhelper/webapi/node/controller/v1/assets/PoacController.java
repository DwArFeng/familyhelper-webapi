package com.dwarfeng.familyhelper.webapi.node.controller.v1.assets;

import com.dwarfeng.familyhelper.assets.sdk.bean.entity.JSFixedFastJsonPoac;
import com.dwarfeng.familyhelper.assets.stack.bean.entity.Poac;
import com.dwarfeng.familyhelper.assets.stack.bean.key.PoacKey;
import com.dwarfeng.familyhelper.webapi.sdk.bean.disp.assets.JSFixedFastJsonDispPoac;
import com.dwarfeng.familyhelper.webapi.stack.bean.disp.assets.DispPoac;
import com.dwarfeng.familyhelper.webapi.stack.handler.system.TokenHandler;
import com.dwarfeng.familyhelper.webapi.stack.service.assets.PoacResponseService;
import com.dwarfeng.subgrade.sdk.bean.dto.FastJsonResponseData;
import com.dwarfeng.subgrade.sdk.bean.dto.JSFixedFastJsonPagedData;
import com.dwarfeng.subgrade.sdk.bean.dto.PagingUtil;
import com.dwarfeng.subgrade.sdk.bean.dto.ResponseDataUtil;
import com.dwarfeng.subgrade.sdk.interceptor.analyse.BehaviorAnalyse;
import com.dwarfeng.subgrade.sdk.interceptor.analyse.SkipRecord;
import com.dwarfeng.subgrade.sdk.interceptor.login.LoginRequired;
import com.dwarfeng.subgrade.stack.bean.BeanTransformer;
import com.dwarfeng.subgrade.stack.bean.dto.PagedData;
import com.dwarfeng.subgrade.stack.bean.dto.PagingInfo;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.bean.key.StringIdKey;
import com.dwarfeng.subgrade.stack.exception.ServiceExceptionMapper;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * 资产目录权限控制器。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
@RestController
@RequestMapping("/api/v1/assets")
public class PoacController {

    private final PoacResponseService service;

    private final ServiceExceptionMapper sem;

    private final BeanTransformer<Poac, JSFixedFastJsonPoac> poacBeanTransformer;
    private final BeanTransformer<DispPoac, JSFixedFastJsonDispPoac> dispPoacBeanTransformer;

    private final TokenHandler tokenHandler;

    public PoacController(
            PoacResponseService service,
            ServiceExceptionMapper sem,
            BeanTransformer<Poac, JSFixedFastJsonPoac> poacBeanTransformer,
            BeanTransformer<DispPoac, JSFixedFastJsonDispPoac> dispPoacBeanTransformer,
            TokenHandler tokenHandler
    ) {
        this.service = service;
        this.sem = sem;
        this.poacBeanTransformer = poacBeanTransformer;
        this.dispPoacBeanTransformer = dispPoacBeanTransformer;
        this.tokenHandler = tokenHandler;
    }

    @GetMapping("/poac/{longId}&{stringId}/exists")
    @BehaviorAnalyse
    @LoginRequired
    public FastJsonResponseData<Boolean> exists(
            HttpServletRequest request, @PathVariable("longId") Long longId, @PathVariable("stringId") String stringId
    ) {
        try {
            boolean exists = service.exists(new PoacKey(longId, stringId));
            return FastJsonResponseData.of(ResponseDataUtil.good(exists));
        } catch (Exception e) {
            return FastJsonResponseData.of(ResponseDataUtil.bad(Boolean.class, e, sem));
        }
    }

    @GetMapping("/poac/{longId}&{stringId}")
    @BehaviorAnalyse
    @LoginRequired
    public FastJsonResponseData<JSFixedFastJsonPoac> get(
            HttpServletRequest request, @PathVariable("longId") Long longId, @PathVariable("stringId") String stringId
    ) {
        try {
            Poac poac = service.get(new PoacKey(longId, stringId));
            return FastJsonResponseData.of(ResponseDataUtil.good(JSFixedFastJsonPoac.of(poac)));
        } catch (Exception e) {
            return FastJsonResponseData.of(ResponseDataUtil.bad(JSFixedFastJsonPoac.class, e, sem));
        }
    }

    @GetMapping("/poac/{longId}&{stringId}/disp")
    @BehaviorAnalyse
    @LoginRequired
    public FastJsonResponseData<JSFixedFastJsonDispPoac> getDisp(
            HttpServletRequest request, @PathVariable("longId") Long longId, @PathVariable("stringId") String stringId
    ) {
        try {
            StringIdKey inspectAccountKey = tokenHandler.getAccountKey(request);
            DispPoac dispPoac = service.getDisp(new PoacKey(longId, stringId), inspectAccountKey);
            return FastJsonResponseData.of(ResponseDataUtil.good(JSFixedFastJsonDispPoac.of(dispPoac)));
        } catch (Exception e) {
            return FastJsonResponseData.of(ResponseDataUtil.bad(JSFixedFastJsonDispPoac.class, e, sem));
        }
    }

    @GetMapping("/asset-catalog/{assetCatalogId}/poac")
    @BehaviorAnalyse
    @SkipRecord
    @LoginRequired
    public FastJsonResponseData<JSFixedFastJsonPagedData<JSFixedFastJsonPoac>> childForAssetCatalog(
            HttpServletRequest request, @PathVariable("assetCatalogId") Long assetCatalogId,
            @RequestParam("page") int page, @RequestParam("rows") int rows) {
        try {
            PagedData<Poac> childForAssetCatalog = service.childForAssetCatalog(
                    new LongIdKey(assetCatalogId), new PagingInfo(page, rows)
            );
            PagedData<JSFixedFastJsonPoac> transform = PagingUtil.transform(childForAssetCatalog, poacBeanTransformer);
            return FastJsonResponseData.of(ResponseDataUtil.good(JSFixedFastJsonPagedData.of(transform)));
        } catch (Exception e) {
            return FastJsonResponseData.of(ResponseDataUtil.bad(JSFixedFastJsonPagedData.class, e, sem));
        }
    }

    @GetMapping("/asset-catalog/{assetCatalogId}/poac/disp")
    @BehaviorAnalyse
    @SkipRecord
    @LoginRequired
    public FastJsonResponseData<JSFixedFastJsonPagedData<JSFixedFastJsonDispPoac>> childForAssetCatalogDisp(
            HttpServletRequest request, @PathVariable("assetCatalogId") Long assetCatalogId,
            @RequestParam("page") int page, @RequestParam("rows") int rows) {
        try {
            StringIdKey inspectAccountKey = tokenHandler.getAccountKey(request);
            PagedData<DispPoac> childForAssetCatalogDisp = service.childForAssetCatalogDisp(
                    new LongIdKey(assetCatalogId), new PagingInfo(page, rows), inspectAccountKey
            );
            PagedData<JSFixedFastJsonDispPoac> transform = PagingUtil.transform(
                    childForAssetCatalogDisp, dispPoacBeanTransformer
            );
            return FastJsonResponseData.of(ResponseDataUtil.good(JSFixedFastJsonPagedData.of(transform)));
        } catch (Exception e) {
            return FastJsonResponseData.of(ResponseDataUtil.bad(JSFixedFastJsonPagedData.class, e, sem));
        }
    }
}
