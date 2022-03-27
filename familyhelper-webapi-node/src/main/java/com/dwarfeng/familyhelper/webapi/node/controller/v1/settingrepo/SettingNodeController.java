package com.dwarfeng.familyhelper.webapi.node.controller.v1.settingrepo;

import com.dwarfeng.familyhelper.webapi.stack.service.settingrepo.SettingNodeResponseService;
import com.dwarfeng.settingrepo.sdk.bean.dto.WebInputSettingNodeInspectInfo;
import com.dwarfeng.settingrepo.sdk.bean.dto.WebInputSettingNodePutInfo;
import com.dwarfeng.settingrepo.sdk.bean.dto.WebInputSettingNodeRemoveInfo;
import com.dwarfeng.settingrepo.sdk.bean.entity.FastJsonSettingNode;
import com.dwarfeng.settingrepo.sdk.bean.entity.WebInputSettingNode;
import com.dwarfeng.settingrepo.stack.bean.entity.SettingNode;
import com.dwarfeng.subgrade.sdk.bean.dto.FastJsonResponseData;
import com.dwarfeng.subgrade.sdk.bean.dto.JSFixedFastJsonPagedData;
import com.dwarfeng.subgrade.sdk.bean.dto.PagingUtil;
import com.dwarfeng.subgrade.sdk.bean.dto.ResponseDataUtil;
import com.dwarfeng.subgrade.sdk.bean.key.FastJsonStringIdKey;
import com.dwarfeng.subgrade.sdk.interceptor.analyse.BehaviorAnalyse;
import com.dwarfeng.subgrade.sdk.interceptor.analyse.SkipRecord;
import com.dwarfeng.subgrade.sdk.interceptor.http.BindingCheck;
import com.dwarfeng.subgrade.sdk.interceptor.login.LoginRequired;
import com.dwarfeng.subgrade.sdk.validation.group.Insert;
import com.dwarfeng.subgrade.stack.bean.BeanTransformer;
import com.dwarfeng.subgrade.stack.bean.dto.PagedData;
import com.dwarfeng.subgrade.stack.bean.dto.PagingInfo;
import com.dwarfeng.subgrade.stack.bean.key.StringIdKey;
import com.dwarfeng.subgrade.stack.exception.ServiceExceptionMapper;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * 设置节点控制器。
 *
 * @author DwArFeng
 * @since 1.0.6
 */
@RestController
@RequestMapping("/api/v1/settingrepo")
public class SettingNodeController {

    private final SettingNodeResponseService service;
    private final ServiceExceptionMapper sem;

    private final BeanTransformer<SettingNode, FastJsonSettingNode> beanTransformer;

    public SettingNodeController(
            SettingNodeResponseService service, ServiceExceptionMapper sem,
            BeanTransformer<SettingNode, FastJsonSettingNode> beanTransformer
    ) {
        this.service = service;
        this.sem = sem;
        this.beanTransformer = beanTransformer;
    }

    @GetMapping("/setting-node/{id}/exists")
    @BehaviorAnalyse
    @LoginRequired
    public FastJsonResponseData<Boolean> exists(HttpServletRequest request, @PathVariable("id") String id) {
        try {
            boolean exists = service.exists(new StringIdKey(id));
            return FastJsonResponseData.of(ResponseDataUtil.good(exists));
        } catch (Exception e) {
            return FastJsonResponseData.of(ResponseDataUtil.bad(Boolean.class, e, sem));
        }
    }

    @GetMapping("/setting-node/{id}")
    @BehaviorAnalyse
    @LoginRequired
    public FastJsonResponseData<FastJsonSettingNode> get(HttpServletRequest request, @PathVariable("id") String id) {
        try {
            SettingNode settingNode = service.get(new StringIdKey(id));
            return FastJsonResponseData.of(ResponseDataUtil.good(FastJsonSettingNode.of(settingNode)));
        } catch (Exception e) {
            return FastJsonResponseData.of(ResponseDataUtil.bad(FastJsonSettingNode.class, e, sem));
        }
    }

    @PostMapping("/setting-node")
    @BehaviorAnalyse
    @BindingCheck
    @LoginRequired
    public FastJsonResponseData<FastJsonStringIdKey> insert(
            HttpServletRequest request,
            @RequestBody @Validated(Insert.class) WebInputSettingNode webInputSettingNode, BindingResult bindingResult
    ) {
        try {
            SettingNode settingNode = WebInputSettingNode.toStackBean(webInputSettingNode);
            StringIdKey insert = service.insert(settingNode);
            return FastJsonResponseData.of(ResponseDataUtil.good(FastJsonStringIdKey.of(insert)));
        } catch (Exception e) {
            return FastJsonResponseData.of(ResponseDataUtil.bad(FastJsonStringIdKey.class, e, sem));
        }
    }

    @PatchMapping("/setting-node")
    @BehaviorAnalyse
    @BindingCheck
    @LoginRequired
    public FastJsonResponseData<Object> update(
            HttpServletRequest request,
            @RequestBody @Validated WebInputSettingNode webInputSettingNode, BindingResult bindingResult
    ) {
        try {
            service.update(WebInputSettingNode.toStackBean(webInputSettingNode));
            return FastJsonResponseData.of(ResponseDataUtil.good(null));
        } catch (Exception e) {
            return FastJsonResponseData.of(ResponseDataUtil.bad(Object.class, e, sem));
        }
    }

    @DeleteMapping("/setting-node/{id}")
    @BehaviorAnalyse
    @LoginRequired
    public FastJsonResponseData<Object> delete(HttpServletRequest request, @PathVariable("id") String id) {
        try {
            service.delete(new StringIdKey(id));
            return FastJsonResponseData.of(ResponseDataUtil.good(null));
        } catch (Exception e) {
            return FastJsonResponseData.of(ResponseDataUtil.bad(Object.class, e, sem));
        }
    }

    @GetMapping("/setting-node/all")
    @BehaviorAnalyse
    @SkipRecord
    @LoginRequired
    public FastJsonResponseData<JSFixedFastJsonPagedData<FastJsonSettingNode>> all(
            HttpServletRequest request, @RequestParam("page") int page, @RequestParam("rows") int rows
    ) {
        try {
            PagedData<SettingNode> all = service.all(new PagingInfo(page, rows));
            PagedData<FastJsonSettingNode> transform = PagingUtil.transform(all, beanTransformer);
            return FastJsonResponseData.of(ResponseDataUtil.good(JSFixedFastJsonPagedData.of(transform)));
        } catch (Exception e) {
            return FastJsonResponseData.of(ResponseDataUtil.bad(JSFixedFastJsonPagedData.class, e, sem));
        }
    }

    @GetMapping("/setting-node/id-like")
    @BehaviorAnalyse
    @SkipRecord
    @LoginRequired
    public FastJsonResponseData<JSFixedFastJsonPagedData<FastJsonSettingNode>> idLike(
            HttpServletRequest request,
            @RequestParam("pattern") String pattern, @RequestParam("page") int page, @RequestParam("rows") int rows
    ) {
        try {
            PagedData<SettingNode> all = service.idLike(pattern, new PagingInfo(page, rows));
            PagedData<FastJsonSettingNode> transform = PagingUtil.transform(all, beanTransformer);
            return FastJsonResponseData.of(ResponseDataUtil.good(JSFixedFastJsonPagedData.of(transform)));
        } catch (Exception e) {
            return FastJsonResponseData.of(ResponseDataUtil.bad(JSFixedFastJsonPagedData.class, e, sem));
        }
    }

    @PostMapping("/setting-node/inspect")
    @BehaviorAnalyse
    @BindingCheck
    @LoginRequired
    public FastJsonResponseData<FastJsonSettingNode> inspect(
            HttpServletRequest request,
            @RequestBody @Validated WebInputSettingNodeInspectInfo webInputSettingNodeInspectInfo,
            BindingResult bindingResult
    ) {
        try {
            SettingNode settingNode = service.inspect(
                    WebInputSettingNodeInspectInfo.toStackBean(webInputSettingNodeInspectInfo)
            );
            return FastJsonResponseData.of(ResponseDataUtil.good(FastJsonSettingNode.of(settingNode)));
        } catch (Exception e) {
            return FastJsonResponseData.of(ResponseDataUtil.bad(FastJsonSettingNode.class, e, sem));
        }
    }

    @PostMapping("/setting-node/put")
    @BehaviorAnalyse
    @BindingCheck
    @LoginRequired
    public FastJsonResponseData<Object> put(
            HttpServletRequest request,
            @RequestBody @Validated WebInputSettingNodePutInfo webInputSettingNodePutInfo,
            BindingResult bindingResult
    ) {
        try {
            service.put(WebInputSettingNodePutInfo.toStackBean(webInputSettingNodePutInfo));
            return FastJsonResponseData.of(ResponseDataUtil.good(null));
        } catch (Exception e) {
            return FastJsonResponseData.of(ResponseDataUtil.bad(Object.class, e, sem));
        }
    }

    @PostMapping("/setting-node/remove")
    @BehaviorAnalyse
    @BindingCheck
    @LoginRequired
    public FastJsonResponseData<Object> remove(
            HttpServletRequest request,
            @RequestBody @Validated WebInputSettingNodeRemoveInfo webInputSettingNodeRemoveInfo,
            BindingResult bindingResult
    ) {
        try {
            service.remove(WebInputSettingNodeRemoveInfo.toStackBean(webInputSettingNodeRemoveInfo));
            return FastJsonResponseData.of(ResponseDataUtil.good(null));
        } catch (Exception e) {
            return FastJsonResponseData.of(ResponseDataUtil.bad(Object.class, e, sem));
        }
    }
}
