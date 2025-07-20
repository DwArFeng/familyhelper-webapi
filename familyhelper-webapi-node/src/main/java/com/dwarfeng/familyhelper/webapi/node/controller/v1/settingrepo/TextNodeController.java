package com.dwarfeng.familyhelper.webapi.node.controller.v1.settingrepo;

import com.dwarfeng.familyhelper.webapi.sdk.bean.settingrepo.dto.WebInputPublicTextNodeInspectInfo;
import com.dwarfeng.familyhelper.webapi.stack.service.settingrepo.TextNodeResponseService;
import com.dwarfeng.settingrepo.sdk.bean.dto.FastJsonTextNodeInspectResult;
import com.dwarfeng.settingrepo.sdk.bean.dto.WebInputTextNodeInspectInfo;
import com.dwarfeng.settingrepo.sdk.bean.dto.WebInputTextNodePutInfo;
import com.dwarfeng.settingrepo.sdk.bean.entity.FastJsonTextNode;
import com.dwarfeng.settingrepo.stack.bean.dto.TextNodeInspectResult;
import com.dwarfeng.settingrepo.stack.bean.entity.TextNode;
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
import com.dwarfeng.subgrade.stack.bean.key.StringIdKey;
import com.dwarfeng.subgrade.stack.exception.ServiceExceptionMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * 设置节点控制器。
 *
 * @author DwArFeng
 * @since 1.3.0
 */
@RestController
@RequestMapping("/api/v1/settingrepo")
public class TextNodeController {

    private static final Logger LOGGER = LoggerFactory.getLogger(TextNodeController.class);

    private final TextNodeResponseService service;
    private final ServiceExceptionMapper sem;

    private final BeanTransformer<TextNode, FastJsonTextNode> beanTransformer;

    public TextNodeController(
            TextNodeResponseService service, ServiceExceptionMapper sem,
            BeanTransformer<TextNode, FastJsonTextNode> beanTransformer
    ) {
        this.service = service;
        this.sem = sem;
        this.beanTransformer = beanTransformer;
    }

    @GetMapping("/text-node/{id}/exists")
    @BehaviorAnalyse
    @LoginRequired
    @PermissionRequired("webapi.controller_permitted.settingrepo.text_node.exists")
    public FastJsonResponseData<Boolean> exists(HttpServletRequest request, @PathVariable("id") String id) {
        try {
            boolean exists = service.exists(new StringIdKey(id));
            return FastJsonResponseData.of(ResponseDataUtil.good(exists));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @GetMapping("/text-node/{id}")
    @BehaviorAnalyse
    @LoginRequired
    @PermissionRequired("webapi.controller_permitted.settingrepo.text_node.get")
    public FastJsonResponseData<FastJsonTextNode> get(HttpServletRequest request, @PathVariable("id") String id) {
        try {
            TextNode textNode = service.get(new StringIdKey(id));
            return FastJsonResponseData.of(ResponseDataUtil.good(FastJsonTextNode.of(textNode)));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @GetMapping("/text-node/all")
    @BehaviorAnalyse
    @SkipRecord
    @LoginRequired
    @PermissionRequired("webapi.controller_permitted.settingrepo.text_node.all")
    public FastJsonResponseData<JSFixedFastJsonPagedData<FastJsonTextNode>> all(
            HttpServletRequest request, @RequestParam("page") int page, @RequestParam("rows") int rows
    ) {
        try {
            PagedData<TextNode> all = service.all(new PagingInfo(page, rows));
            PagedData<FastJsonTextNode> transform = PagingUtil.transform(all, beanTransformer);
            return FastJsonResponseData.of(ResponseDataUtil.good(JSFixedFastJsonPagedData.of(transform)));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @PostMapping("/text-node/inspect")
    @BehaviorAnalyse
    @BindingCheck
    @LoginRequired
    @PermissionRequired("webapi.controller_permitted.settingrepo.text_node.inspect")
    public FastJsonResponseData<FastJsonTextNodeInspectResult> inspect(
            HttpServletRequest request,
            @RequestBody @Validated WebInputTextNodeInspectInfo webInputTextNodeInspectInfo,
            BindingResult bindingResult
    ) {
        try {
            TextNodeInspectResult inspect = service.inspect(
                    WebInputTextNodeInspectInfo.toStackBean(webInputTextNodeInspectInfo)
            );
            return FastJsonResponseData.of(ResponseDataUtil.good(FastJsonTextNodeInspectResult.of(inspect)));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @PostMapping("/text-node/put")
    @BehaviorAnalyse
    @BindingCheck
    @LoginRequired
    @PermissionRequired("webapi.controller_permitted.settingrepo.text_node.put")
    public FastJsonResponseData<Object> put(
            HttpServletRequest request,
            @RequestBody @Validated WebInputTextNodePutInfo webInputTextNodePutInfo,
            BindingResult bindingResult
    ) {
        try {
            service.put(WebInputTextNodePutInfo.toStackBean(webInputTextNodePutInfo));
            return FastJsonResponseData.of(ResponseDataUtil.good(null));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    /**
     * @since 1.7.0
     */
    @PostMapping("/text-node/inspect-for-public")
    @BehaviorAnalyse
    @BindingCheck
    public FastJsonResponseData<FastJsonTextNodeInspectResult> inspectForPublic(
            HttpServletRequest request,
            @RequestBody @Validated WebInputPublicTextNodeInspectInfo inspectInfo,
            BindingResult bindingResult
    ) {
        try {
            TextNodeInspectResult inspect = service.inspectForPublic(
                    WebInputPublicTextNodeInspectInfo.toStackBean(inspectInfo)
            );
            return FastJsonResponseData.of(ResponseDataUtil.good(FastJsonTextNodeInspectResult.of(inspect)));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }
}
