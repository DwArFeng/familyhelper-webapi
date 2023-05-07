package com.dwarfeng.familyhelper.webapi.node.controller.v1.life;

import com.dwarfeng.familyhelper.life.sdk.bean.dto.WebInputPbNodeCreateInfo;
import com.dwarfeng.familyhelper.life.sdk.bean.dto.WebInputPbNodeUpdateInfo;
import com.dwarfeng.familyhelper.life.sdk.bean.entity.JSFixedFastJsonPbNode;
import com.dwarfeng.familyhelper.life.stack.bean.entity.PbNode;
import com.dwarfeng.familyhelper.webapi.sdk.bean.disp.life.JSFixedFastJsonDispPbNode;
import com.dwarfeng.familyhelper.webapi.stack.bean.disp.life.DispPbNode;
import com.dwarfeng.familyhelper.webapi.stack.handler.system.TokenHandler;
import com.dwarfeng.familyhelper.webapi.stack.service.life.PbNodeResponseService;
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
import java.util.Objects;

/**
 * 个人最佳节点控制器。
 *
 * @author DwArFeng
 * @since 1.0.7
 */
@RestController
@RequestMapping("/api/v1/life")
public class PbNodeController {

    private final PbNodeResponseService service;

    private final ServiceExceptionMapper sem;

    private final BeanTransformer<PbNode, JSFixedFastJsonPbNode> pbNodeBeanTransformer;
    private final BeanTransformer<DispPbNode, JSFixedFastJsonDispPbNode> dispPbNodeBeanTransformer;

    private final TokenHandler tokenHandler;

    public PbNodeController(
            PbNodeResponseService service, ServiceExceptionMapper sem,
            BeanTransformer<PbNode, JSFixedFastJsonPbNode> pbNodeBeanTransformer,
            BeanTransformer<DispPbNode, JSFixedFastJsonDispPbNode> dispPbNodeBeanTransformer,
            TokenHandler tokenHandler
    ) {
        this.service = service;
        this.sem = sem;
        this.pbNodeBeanTransformer = pbNodeBeanTransformer;
        this.dispPbNodeBeanTransformer = dispPbNodeBeanTransformer;
        this.tokenHandler = tokenHandler;
    }

    @GetMapping("/pb-node/{id}/exists")
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

    @GetMapping("/pb-node/{id}")
    @BehaviorAnalyse
    @LoginRequired
    public FastJsonResponseData<JSFixedFastJsonPbNode> get(HttpServletRequest request, @PathVariable("id") Long id) {
        try {
            PbNode pbNode = service.get(new LongIdKey(id));
            return FastJsonResponseData.of(ResponseDataUtil.good(JSFixedFastJsonPbNode.of(pbNode)));
        } catch (Exception e) {
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @GetMapping("/pb-node/all")
    @BehaviorAnalyse
    @SkipRecord
    @LoginRequired
    public FastJsonResponseData<JSFixedFastJsonPagedData<JSFixedFastJsonPbNode>> all(
            HttpServletRequest request, @RequestParam("page") int page, @RequestParam("rows") int rows) {
        try {
            PagedData<PbNode> all = service.all(new PagingInfo(page, rows));
            PagedData<JSFixedFastJsonPbNode> transform = PagingUtil.transform(all, pbNodeBeanTransformer);
            return FastJsonResponseData.of(ResponseDataUtil.good(JSFixedFastJsonPagedData.of(transform)));
        } catch (Exception e) {
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @GetMapping(value = {
            "/pb-set/{pbSetId}/pb-node", "/pb-set//pb-node"
    })
    @BehaviorAnalyse
    @SkipRecord
    @LoginRequired
    public FastJsonResponseData<JSFixedFastJsonPagedData<JSFixedFastJsonPbNode>> childForPbSet(
            @PathVariable(required = false, value = "pbSetId") Long pbSetId,
            @RequestParam("page") int page, @RequestParam("rows") int rows
    ) {
        try {
            LongIdKey pbSetKey = null;
            if (Objects.nonNull(pbSetId)) {
                pbSetKey = new LongIdKey(pbSetId);
            }
            PagedData<PbNode> childForPbSet = service.childForPbSet(
                    pbSetKey, new PagingInfo(page, rows));
            PagedData<JSFixedFastJsonPbNode> transform = PagingUtil.transform(
                    childForPbSet, pbNodeBeanTransformer);
            return FastJsonResponseData.of(ResponseDataUtil.good(JSFixedFastJsonPagedData.of(transform)));
        } catch (Exception e) {
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @GetMapping("/pb-set/{pbSetId}/pb-node/root")
    @BehaviorAnalyse
    @SkipRecord
    @LoginRequired
    public FastJsonResponseData<JSFixedFastJsonPagedData<JSFixedFastJsonPbNode>> childForPbSetRoot(
            HttpServletRequest request,
            @PathVariable("pbSetId") long pbSetId,
            @RequestParam("page") int page, @RequestParam("rows") int rows
    ) {
        try {
            PagedData<PbNode> childForPbSetRoot = service.childForPbSetRoot(
                    new LongIdKey(pbSetId), new PagingInfo(page, rows)
            );
            PagedData<JSFixedFastJsonPbNode> transform = PagingUtil.transform(
                    childForPbSetRoot, pbNodeBeanTransformer
            );
            return FastJsonResponseData.of(ResponseDataUtil.good(JSFixedFastJsonPagedData.of(transform)));
        } catch (Exception e) {
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @GetMapping("/pb-node/{parentId}/child")
    @BehaviorAnalyse
    @SkipRecord
    @LoginRequired
    public FastJsonResponseData<JSFixedFastJsonPagedData<JSFixedFastJsonPbNode>> childForParent(
            HttpServletRequest request,
            @PathVariable("parentId") long parentId,
            @RequestParam("page") int page, @RequestParam("rows") int rows
    ) {
        try {
            PagedData<PbNode> childForParent = service.childForParent(
                    new LongIdKey(parentId), new PagingInfo(page, rows)
            );
            PagedData<JSFixedFastJsonPbNode> transform = PagingUtil.transform(
                    childForParent, pbNodeBeanTransformer
            );
            return FastJsonResponseData.of(ResponseDataUtil.good(JSFixedFastJsonPagedData.of(transform)));
        } catch (Exception e) {
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @GetMapping(value = {
            "/pb-set/{pbSetId}/pb-node/name-like", "/pb-set//pb-node/name-like"
    })
    @BehaviorAnalyse
    @SkipRecord
    @LoginRequired
    public FastJsonResponseData<JSFixedFastJsonPagedData<JSFixedFastJsonPbNode>> childForPbSetNameLike(
            @PathVariable(required = false, value = "pbSetId") Long pbSetId,
            @RequestParam("pattern") String pattern,
            @RequestParam("page") int page, @RequestParam("rows") int rows
    ) {
        try {
            LongIdKey pbSetKey = null;
            if (Objects.nonNull(pbSetId)) {
                pbSetKey = new LongIdKey(pbSetId);
            }
            PagedData<PbNode> childForPbSet = service.childForPbSetNameLike(
                    pbSetKey, pattern, new PagingInfo(page, rows)
            );
            PagedData<JSFixedFastJsonPbNode> transform = PagingUtil.transform(
                    childForPbSet, pbNodeBeanTransformer);
            return FastJsonResponseData.of(ResponseDataUtil.good(JSFixedFastJsonPagedData.of(transform)));
        } catch (Exception e) {
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @GetMapping("/pb-node/{id}/disp")
    @BehaviorAnalyse
    @SkipRecord
    @LoginRequired
    public FastJsonResponseData<JSFixedFastJsonDispPbNode> getDisp(
            HttpServletRequest request, @PathVariable("id") Long id
    ) {
        try {
            StringIdKey inspectAccountKey = tokenHandler.getAccountKey(request);
            DispPbNode dispPbNode = service.getDisp(new LongIdKey(id), inspectAccountKey);
            return FastJsonResponseData.of(ResponseDataUtil.good(JSFixedFastJsonDispPbNode.of(dispPbNode)));
        } catch (Exception e) {
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @GetMapping("/pb-node/all/disp")
    @BehaviorAnalyse
    @SkipRecord
    @LoginRequired
    public FastJsonResponseData<JSFixedFastJsonPagedData<JSFixedFastJsonDispPbNode>> allDisp(
            HttpServletRequest request, @RequestParam("page") int page, @RequestParam("rows") int rows
    ) {
        try {
            StringIdKey accountKey = tokenHandler.getAccountKey(request);
            PagedData<DispPbNode> allDisp = service.allDisp(accountKey, new PagingInfo(page, rows));
            PagedData<JSFixedFastJsonDispPbNode> transform = PagingUtil.transform(
                    allDisp, dispPbNodeBeanTransformer);
            return FastJsonResponseData.of(ResponseDataUtil.good(JSFixedFastJsonPagedData.of(transform)));
        } catch (Exception e) {
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @GetMapping(value = {
            "/pb-set/{pbSetId}/pb-node/disp", "/pb-set//pb-node/disp"
    })
    @BehaviorAnalyse
    @SkipRecord
    @LoginRequired
    public FastJsonResponseData<JSFixedFastJsonPagedData<JSFixedFastJsonDispPbNode>> childForPbSetDisp(
            HttpServletRequest request,
            @PathVariable(required = false, value = "pbSetId") Long pbSetId,
            @RequestParam("page") int page, @RequestParam("rows") int rows
    ) {
        try {
            StringIdKey accountKey = tokenHandler.getAccountKey(request);
            LongIdKey pbSetKey = null;
            if (Objects.nonNull(pbSetId)) {
                pbSetKey = new LongIdKey(pbSetId);
            }
            PagedData<DispPbNode> childForPbSetDisp = service.childForPbSetDisp(
                    accountKey, pbSetKey, new PagingInfo(page, rows));
            PagedData<JSFixedFastJsonDispPbNode> transform = PagingUtil.transform(
                    childForPbSetDisp, dispPbNodeBeanTransformer);
            return FastJsonResponseData.of(ResponseDataUtil.good(JSFixedFastJsonPagedData.of(transform)));
        } catch (Exception e) {
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @GetMapping("/pb-set/{pbSetId}/pb-node/root/disp")
    @BehaviorAnalyse
    @SkipRecord
    @LoginRequired
    public FastJsonResponseData<JSFixedFastJsonPagedData<JSFixedFastJsonDispPbNode>> childForPbSetRootDisp(
            HttpServletRequest request,
            @PathVariable("pbSetId") long pbSetId,
            @RequestParam("page") int page, @RequestParam("rows") int rows
    ) {
        try {
            StringIdKey accountKey = tokenHandler.getAccountKey(request);
            PagedData<DispPbNode> childForPbSetRoot = service.childForPbSetRootDisp(
                    accountKey, new LongIdKey(pbSetId), new PagingInfo(page, rows)
            );
            PagedData<JSFixedFastJsonDispPbNode> transform = PagingUtil.transform(
                    childForPbSetRoot, dispPbNodeBeanTransformer
            );
            return FastJsonResponseData.of(ResponseDataUtil.good(JSFixedFastJsonPagedData.of(transform)));
        } catch (Exception e) {
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @GetMapping("/pb-node/{parentId}/child/disp")
    @BehaviorAnalyse
    @SkipRecord
    @LoginRequired
    public FastJsonResponseData<JSFixedFastJsonPagedData<JSFixedFastJsonDispPbNode>> childForParentDisp(
            HttpServletRequest request,
            @PathVariable("parentId") long parentId,
            @RequestParam("page") int page, @RequestParam("rows") int rows
    ) {
        try {
            StringIdKey accountKey = tokenHandler.getAccountKey(request);
            PagedData<DispPbNode> childForParent = service.childForParentDisp(
                    accountKey, new LongIdKey(parentId), new PagingInfo(page, rows)
            );
            PagedData<JSFixedFastJsonDispPbNode> transform = PagingUtil.transform(
                    childForParent, dispPbNodeBeanTransformer
            );
            return FastJsonResponseData.of(ResponseDataUtil.good(JSFixedFastJsonPagedData.of(transform)));
        } catch (Exception e) {
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @GetMapping(value = {
            "/pb-set/{pbSetId}/pb-node/name-like/disp", "/pb-set//pb-node/name-like/disp"
    })
    @BehaviorAnalyse
    @SkipRecord
    @LoginRequired
    public FastJsonResponseData<JSFixedFastJsonPagedData<JSFixedFastJsonDispPbNode>> childForPbSetNameLikeDisp(
            HttpServletRequest request,
            @PathVariable(required = false, value = "pbSetId") Long pbSetId,
            @RequestParam("pattern") String pattern,
            @RequestParam("page") int page, @RequestParam("rows") int rows
    ) {
        try {
            StringIdKey accountKey = tokenHandler.getAccountKey(request);
            LongIdKey pbSetKey = null;
            if (Objects.nonNull(pbSetId)) {
                pbSetKey = new LongIdKey(pbSetId);
            }
            PagedData<DispPbNode> childForPbSet = service.childForPbSetNameLikeDisp(
                    accountKey, pbSetKey, pattern, new PagingInfo(page, rows)
            );
            PagedData<JSFixedFastJsonDispPbNode> transform = PagingUtil.transform(
                    childForPbSet, dispPbNodeBeanTransformer
            );
            return FastJsonResponseData.of(ResponseDataUtil.good(JSFixedFastJsonPagedData.of(transform)));
        } catch (Exception e) {
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @GetMapping("/pb-node/{id}/path-from-root")
    @BehaviorAnalyse
    @SkipRecord
    @LoginRequired
    public FastJsonResponseData<JSFixedFastJsonPagedData<JSFixedFastJsonPbNode>> nodePathFromRoot(
            HttpServletRequest request, @PathVariable(value = "id") Long id
    ) {
        try {
            PagedData<PbNode> pathFromRoot = service.nodePathFromRoot(new LongIdKey(id));
            PagedData<JSFixedFastJsonPbNode> transform = PagingUtil.transform(
                    pathFromRoot, pbNodeBeanTransformer
            );
            return FastJsonResponseData.of(ResponseDataUtil.good(JSFixedFastJsonPagedData.of(transform)));
        } catch (Exception e) {
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @GetMapping("/pb-node/{id}/path-from-root/disp")
    @BehaviorAnalyse
    @SkipRecord
    @LoginRequired
    public FastJsonResponseData<JSFixedFastJsonPagedData<JSFixedFastJsonDispPbNode>> nodePathFromRootDisp(
            HttpServletRequest request, @PathVariable(value = "id") Long id
    ) {
        try {
            StringIdKey accountKey = tokenHandler.getAccountKey(request);
            PagedData<DispPbNode> pathFromRoot = service.nodePathFromRootDisp(accountKey, new LongIdKey(id));
            PagedData<JSFixedFastJsonDispPbNode> transform = PagingUtil.transform(
                    pathFromRoot, dispPbNodeBeanTransformer
            );
            return FastJsonResponseData.of(ResponseDataUtil.good(JSFixedFastJsonPagedData.of(transform)));
        } catch (Exception e) {
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @GetMapping("/pb-item/{itemId}/path-from-root")
    @BehaviorAnalyse
    @SkipRecord
    @LoginRequired
    public FastJsonResponseData<JSFixedFastJsonPagedData<JSFixedFastJsonPbNode>> itemPathFromRoot(
            HttpServletRequest request, @PathVariable(value = "itemId") Long itemId
    ) {
        try {
            PagedData<PbNode> pathFromRoot = service.itemPathFromRoot(new LongIdKey(itemId));
            PagedData<JSFixedFastJsonPbNode> transform = PagingUtil.transform(
                    pathFromRoot, pbNodeBeanTransformer
            );
            return FastJsonResponseData.of(ResponseDataUtil.good(JSFixedFastJsonPagedData.of(transform)));
        } catch (Exception e) {
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @GetMapping("/pb-item/{itemId}/path-from-root/disp")
    @BehaviorAnalyse
    @SkipRecord
    @LoginRequired
    public FastJsonResponseData<JSFixedFastJsonPagedData<JSFixedFastJsonDispPbNode>> itemPathFromRootDisp(
            HttpServletRequest request, @PathVariable(value = "itemId") Long itemId
    ) {
        try {
            StringIdKey accountKey = tokenHandler.getAccountKey(request);
            PagedData<DispPbNode> pathFromRoot = service.itemPathFromRootDisp(accountKey, new LongIdKey(itemId));
            PagedData<JSFixedFastJsonDispPbNode> transform = PagingUtil.transform(
                    pathFromRoot, dispPbNodeBeanTransformer
            );
            return FastJsonResponseData.of(ResponseDataUtil.good(JSFixedFastJsonPagedData.of(transform)));
        } catch (Exception e) {
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @PostMapping("/pb-node/create")
    @BehaviorAnalyse
    @BindingCheck
    public FastJsonResponseData<JSFixedFastJsonLongIdKey> createPbNode(
            HttpServletRequest request,
            @RequestBody @Validated WebInputPbNodeCreateInfo pbNodeCreateInfo, BindingResult bindingResult
    ) {
        try {
            StringIdKey accountKey = tokenHandler.getAccountKey(request);
            LongIdKey result = service.createPbNode(
                    accountKey, WebInputPbNodeCreateInfo.toStackBean(pbNodeCreateInfo)
            );
            return FastJsonResponseData.of(ResponseDataUtil.good(JSFixedFastJsonLongIdKey.of(result)));
        } catch (Exception e) {
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @PostMapping("/pb-node/update")
    @BehaviorAnalyse
    @BindingCheck
    public FastJsonResponseData<Object> updatePbNode(
            HttpServletRequest request,
            @RequestBody @Validated WebInputPbNodeUpdateInfo webInputPbNodeUpdateInfo, BindingResult bindingResult
    ) {
        try {
            StringIdKey accountKey = tokenHandler.getAccountKey(request);
            service.updatePbNode(
                    accountKey, WebInputPbNodeUpdateInfo.toStackBean(webInputPbNodeUpdateInfo)
            );
            return FastJsonResponseData.of(ResponseDataUtil.good(null));
        } catch (Exception e) {
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @PostMapping("/pb-node/remove")
    @BehaviorAnalyse
    @BindingCheck
    public FastJsonResponseData<Object> removePbNode(
            HttpServletRequest request,
            @RequestBody @Validated WebInputLongIdKey pbNodeKey, BindingResult bindingResult
    ) {
        try {
            StringIdKey accountKey = tokenHandler.getAccountKey(request);
            service.removePbNode(accountKey, WebInputLongIdKey.toStackBean(pbNodeKey));
            return FastJsonResponseData.of(ResponseDataUtil.good(null));
        } catch (Exception e) {
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }
}
