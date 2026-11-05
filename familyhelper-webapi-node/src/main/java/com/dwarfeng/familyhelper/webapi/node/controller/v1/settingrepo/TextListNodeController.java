package com.dwarfeng.familyhelper.webapi.node.controller.v1.settingrepo;

import com.dwarfeng.familyhelper.webapi.stack.service.settingrepo.TextListNodeResponseService;
import com.dwarfeng.settingrepo.sdk.bean.dto.*;
import com.dwarfeng.settingrepo.sdk.bean.entity.FastJsonTextListNode;
import com.dwarfeng.settingrepo.stack.bean.dto.TextListNodeInspectResult;
import com.dwarfeng.settingrepo.stack.bean.dto.TextListNodeSizeResult;
import com.dwarfeng.settingrepo.stack.bean.dto.TextListNodeTextFetchResult;
import com.dwarfeng.settingrepo.stack.bean.entity.TextListNode;
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
 * 文本列表节点控制器。
 *
 * @author DwArFeng
 * @since 2.4.1
 */
@RestController
@RequestMapping("/api/v1/settingrepo")
public class TextListNodeController {

    private static final Logger LOGGER = LoggerFactory.getLogger(TextListNodeController.class);

    private final TextListNodeResponseService service;
    private final ServiceExceptionMapper sem;

    private final BeanTransformer<TextListNode, FastJsonTextListNode> beanTransformer;

    public TextListNodeController(
            TextListNodeResponseService service, ServiceExceptionMapper sem,
            BeanTransformer<TextListNode, FastJsonTextListNode> beanTransformer
    ) {
        this.service = service;
        this.sem = sem;
        this.beanTransformer = beanTransformer;
    }

    @GetMapping("/text-list-node/{id}/exists")
    @BehaviorAnalyse
    @LoginRequired
    @PermissionRequired("webapi.controller_permitted.settingrepo.text_list_node.exists")
    public FastJsonResponseData<Boolean> exists(HttpServletRequest request, @PathVariable("id") String id) {
        try {
            boolean exists = service.exists(new StringIdKey(id));
            return FastJsonResponseData.of(ResponseDataUtil.good(exists));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @GetMapping("/text-list-node/{id}")
    @BehaviorAnalyse
    @LoginRequired
    @PermissionRequired("webapi.controller_permitted.settingrepo.text_list_node.get")
    public FastJsonResponseData<FastJsonTextListNode> get(HttpServletRequest request, @PathVariable("id") String id) {
        try {
            TextListNode textListNode = service.get(new StringIdKey(id));
            return FastJsonResponseData.of(ResponseDataUtil.good(FastJsonTextListNode.of(textListNode)));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @GetMapping("/text-list-node/all")
    @BehaviorAnalyse
    @SkipRecord
    @LoginRequired
    @PermissionRequired("webapi.controller_permitted.settingrepo.text_list_node.all")
    public FastJsonResponseData<JSFixedFastJsonPagedData<FastJsonTextListNode>> all(
            HttpServletRequest request, @RequestParam("page") int page, @RequestParam("rows") int rows
    ) {
        try {
            PagedData<TextListNode> all = service.all(new PagingInfo(page, rows));
            PagedData<FastJsonTextListNode> transform = PagingUtil.transform(all, beanTransformer);
            return FastJsonResponseData.of(ResponseDataUtil.good(JSFixedFastJsonPagedData.of(transform)));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @PostMapping("/text-list-node/size")
    @BehaviorAnalyse
    @BindingCheck
    @LoginRequired
    @PermissionRequired("webapi.controller_permitted.settingrepo.text_list_node.size")
    public FastJsonResponseData<FastJsonTextListNodeSizeResult> size(
            HttpServletRequest request,
            @RequestBody @Validated WebInputTextListNodeSizeInfo webInputTextListNodeSizeInfo,
            BindingResult bindingResult
    ) {
        try {
            TextListNodeSizeResult size = service.size(
                    WebInputTextListNodeSizeInfo.toStackBean(webInputTextListNodeSizeInfo)
            );
            return FastJsonResponseData.of(ResponseDataUtil.good(FastJsonTextListNodeSizeResult.of(size)));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @PostMapping("/text-list-node/inspect")
    @BehaviorAnalyse
    @BindingCheck
    @LoginRequired
    @PermissionRequired("webapi.controller_permitted.settingrepo.text_list_node.inspect")
    public FastJsonResponseData<FastJsonTextListNodeInspectResult> inspect(
            HttpServletRequest request,
            @RequestBody @Validated WebInputTextListNodeInspectInfo webInputTextListNodeInspectInfo,
            BindingResult bindingResult
    ) {
        try {
            TextListNodeInspectResult inspect = service.inspect(
                    WebInputTextListNodeInspectInfo.toStackBean(webInputTextListNodeInspectInfo)
            );
            return FastJsonResponseData.of(ResponseDataUtil.good(FastJsonTextListNodeInspectResult.of(inspect)));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @PostMapping("/text-list-node/fetch-text")
    @BehaviorAnalyse
    @BindingCheck
    @LoginRequired
    @PermissionRequired("webapi.controller_permitted.settingrepo.text_list_node.fetch_text")
    public FastJsonResponseData<FastJsonTextListNodeTextFetchResult> fetchText(
            HttpServletRequest request,
            @RequestBody @Validated WebInputTextListNodeTextFetchInfo webInputTextListNodeTextFetchInfo,
            BindingResult bindingResult
    ) {
        try {
            TextListNodeTextFetchResult fetchText = service.fetchText(
                    WebInputTextListNodeTextFetchInfo.toStackBean(webInputTextListNodeTextFetchInfo)
            );
            return FastJsonResponseData.of(ResponseDataUtil.good(FastJsonTextListNodeTextFetchResult.of(fetchText)));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @PostMapping("/text-list-node/insert-text")
    @BehaviorAnalyse
    @BindingCheck
    @LoginRequired
    @PermissionRequired("webapi.controller_permitted.settingrepo.text_list_node.insert_text")
    public FastJsonResponseData<Object> insertText(
            HttpServletRequest request,
            @RequestBody @Validated WebInputTextListNodeTextInsertInfo webInputTextListNodeTextInsertInfo,
            BindingResult bindingResult
    ) {
        try {
            service.insertText(WebInputTextListNodeTextInsertInfo.toStackBean(webInputTextListNodeTextInsertInfo));
            return FastJsonResponseData.of(ResponseDataUtil.good(null));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @PostMapping("/text-list-node/update-text")
    @BehaviorAnalyse
    @BindingCheck
    @LoginRequired
    @PermissionRequired("webapi.controller_permitted.settingrepo.text_list_node.update_text")
    public FastJsonResponseData<Object> updateText(
            HttpServletRequest request,
            @RequestBody @Validated WebInputTextListNodeTextUpdateInfo webInputTextListNodeTextUpdateInfo,
            BindingResult bindingResult
    ) {
        try {
            service.updateText(WebInputTextListNodeTextUpdateInfo.toStackBean(webInputTextListNodeTextUpdateInfo));
            return FastJsonResponseData.of(ResponseDataUtil.good(null));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @PostMapping("/text-list-node/change-order")
    @BehaviorAnalyse
    @BindingCheck
    @LoginRequired
    @PermissionRequired("webapi.controller_permitted.settingrepo.text_list_node.change_order")
    public FastJsonResponseData<Object> changeOrder(
            HttpServletRequest request,
            @RequestBody @Validated WebInputTextListNodeChangeOrderInfo webInputTextListNodeChangeOrderInfo,
            BindingResult bindingResult
    ) {
        try {
            service.changeOrder(
                    WebInputTextListNodeChangeOrderInfo.toStackBean(webInputTextListNodeChangeOrderInfo)
            );
            return FastJsonResponseData.of(ResponseDataUtil.good(null));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @PostMapping("/text-list-node/remove")
    @BehaviorAnalyse
    @BindingCheck
    @LoginRequired
    @PermissionRequired("webapi.controller_permitted.settingrepo.text_list_node.remove")
    public FastJsonResponseData<Object> remove(
            HttpServletRequest request,
            @RequestBody @Validated WebInputTextListNodeRemoveInfo webInputTextListNodeRemoveInfo,
            BindingResult bindingResult
    ) {
        try {
            service.remove(WebInputTextListNodeRemoveInfo.toStackBean(webInputTextListNodeRemoveInfo));
            return FastJsonResponseData.of(ResponseDataUtil.good(null));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }
}
