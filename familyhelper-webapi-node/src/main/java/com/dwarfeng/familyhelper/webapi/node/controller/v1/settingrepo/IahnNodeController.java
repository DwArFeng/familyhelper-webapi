package com.dwarfeng.familyhelper.webapi.node.controller.v1.settingrepo;

import com.dwarfeng.familyhelper.webapi.stack.bean.settingrepo.dto.*;
import com.dwarfeng.familyhelper.webapi.stack.service.settingrepo.IahnNodeResponseService;
import com.dwarfeng.settingrepo.sdk.bean.dto.*;
import com.dwarfeng.settingrepo.sdk.bean.entity.FastJsonIahnNode;
import com.dwarfeng.settingrepo.stack.bean.entity.IahnNode;
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
 * 国际化节点控制器。
 *
 * @author DwArFeng
 * @since 1.7.0
 */
@RestController
@RequestMapping("/api/v1/settingrepo")
public class IahnNodeController {

    private static final Logger LOGGER = LoggerFactory.getLogger(IahnNodeController.class);

    private final IahnNodeResponseService service;
    private final ServiceExceptionMapper sem;
    private final BeanTransformer<IahnNode, FastJsonIahnNode> beanTransformer;

    public IahnNodeController(
            IahnNodeResponseService service,
            ServiceExceptionMapper sem,
            BeanTransformer<IahnNode, FastJsonIahnNode> beanTransformer
    ) {
        this.service = service;
        this.sem = sem;
        this.beanTransformer = beanTransformer;
    }

    @GetMapping("/iahn-node/{id}/exists")
    @BehaviorAnalyse
    @LoginRequired
    @PermissionRequired("webapi.controller_permitted.settingrepo.iahn_node.exists")
    public FastJsonResponseData<Boolean> exists(HttpServletRequest request, @PathVariable("id") String id) {
        try {
            boolean exists = service.exists(new StringIdKey(id));
            return FastJsonResponseData.of(ResponseDataUtil.good(exists));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @GetMapping("/iahn-node/{id}")
    @BehaviorAnalyse
    @LoginRequired
    @PermissionRequired("webapi.controller_permitted.settingrepo.iahn_node.get")
    public FastJsonResponseData<FastJsonIahnNode> get(HttpServletRequest request, @PathVariable("id") String id) {
        try {
            IahnNode iahnNode = service.get(new StringIdKey(id));
            return FastJsonResponseData.of(ResponseDataUtil.good(FastJsonIahnNode.of(iahnNode)));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @GetMapping("/iahn-node/all")
    @BehaviorAnalyse
    @SkipRecord
    @LoginRequired
    @PermissionRequired("webapi.controller_permitted.settingrepo.iahn_node.all")
    public FastJsonResponseData<JSFixedFastJsonPagedData<FastJsonIahnNode>> all(
            HttpServletRequest request, @RequestParam("page") int page, @RequestParam("rows") int rows
    ) {
        try {
            PagedData<IahnNode> all = service.all(new PagingInfo(page, rows));
            PagedData<FastJsonIahnNode> transform = PagingUtil.transform(all, beanTransformer);
            return FastJsonResponseData.of(ResponseDataUtil.good(JSFixedFastJsonPagedData.of(transform)));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @PostMapping("/iahn-node/inspect-message")
    @BehaviorAnalyse
    @BindingCheck
    @LoginRequired
    @PermissionRequired("webapi.controller_permitted.settingrepo.iahn_node.inspect_message")
    public FastJsonResponseData<FastJsonIahnNodeMessageInspectResult> inspectMessage(
            HttpServletRequest request,
            @RequestBody @Validated WebInputIahnNodeMessageInspectInfo webInputIahnNodeMessageInspectInfo,
            BindingResult bindingResult
    ) {
        try {
            FastJsonIahnNodeMessageInspectResult result = FastJsonIahnNodeMessageInspectResult.of(
                    service.inspectMessage(
                            WebInputIahnNodeMessageInspectInfo.toStackBean(webInputIahnNodeMessageInspectInfo)
                    )
            );
            return FastJsonResponseData.of(ResponseDataUtil.good(result));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @PostMapping("/iahn-node/batch-inspect-message-by-locale")
    @BehaviorAnalyse
    @BindingCheck
    @LoginRequired
    @PermissionRequired("webapi.controller_permitted.settingrepo.iahn_node.batch_inspect_message_by_locale")
    public FastJsonResponseData<FastJsonIahnNodeMessageInspectByLocaleResult> batchInspectMessageByLocale(
            HttpServletRequest request,
            @RequestBody @Validated
            WebInputIahnNodeMessageInspectByLocaleInfo webInputIahnNodeMessageInspectByLocaleInfo,
            BindingResult bindingResult
    ) {
        try {
            FastJsonIahnNodeMessageInspectByLocaleResult result = FastJsonIahnNodeMessageInspectByLocaleResult.of(
                    service.batchInspectMessageByLocale(
                            WebInputIahnNodeMessageInspectByLocaleInfo.toStackBean(
                                    webInputIahnNodeMessageInspectByLocaleInfo
                            )
                    )
            );
            return FastJsonResponseData.of(ResponseDataUtil.good(result));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @PostMapping("/iahn-node/inspect-locale-list")
    @BehaviorAnalyse
    @BindingCheck
    @LoginRequired
    @PermissionRequired("webapi.controller_permitted.settingrepo.iahn_node.inspect_locale_list")
    public FastJsonResponseData<FastJsonIahnNodeLocaleListInspectResult> inspectLocaleList(
            HttpServletRequest request,
            @RequestBody @Validated WebInputIahnNodeLocaleListInspectInfo webInputIahnNodeLocaleListInspectInfo,
            BindingResult bindingResult
    ) {
        try {
            FastJsonIahnNodeLocaleListInspectResult result = FastJsonIahnNodeLocaleListInspectResult.of(
                    service.inspectLocaleList(
                            WebInputIahnNodeLocaleListInspectInfo.toStackBean(webInputIahnNodeLocaleListInspectInfo)
                    )
            );
            return FastJsonResponseData.of(ResponseDataUtil.good(result));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @PostMapping("/iahn-node/inspect-mek-list")
    @BehaviorAnalyse
    @BindingCheck
    @LoginRequired
    @PermissionRequired("webapi.controller_permitted.settingrepo.iahn_node.inspect_mek_list")
    public FastJsonResponseData<FastJsonIahnNodeMekListInspectResult> inspectMekList(
            HttpServletRequest request,
            @RequestBody @Validated WebInputIahnNodeMekListInspectInfo webInputIahnNodeMekListInspectInfo,
            BindingResult bindingResult
    ) {
        try {
            FastJsonIahnNodeMekListInspectResult result = FastJsonIahnNodeMekListInspectResult.of(
                    service.inspectMekList(
                            WebInputIahnNodeMekListInspectInfo.toStackBean(webInputIahnNodeMekListInspectInfo)
                    )
            );
            return FastJsonResponseData.of(ResponseDataUtil.good(result));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @PostMapping("/iahn-node/inspect-message-table")
    @BehaviorAnalyse
    @BindingCheck
    @LoginRequired
    @PermissionRequired("webapi.controller_permitted.settingrepo.iahn_node.inspect_message_table")
    public FastJsonResponseData<FastJsonIahnNodeMessageTableInspectResult> inspectMessageTable(
            HttpServletRequest request,
            @RequestBody @Validated WebInputIahnNodeMessageTableInspectInfo webInputIahnNodeMessageTableInspectInfo,
            BindingResult bindingResult
    ) {
        try {
            FastJsonIahnNodeMessageTableInspectResult result = FastJsonIahnNodeMessageTableInspectResult.of(
                    service.inspectMessageTable(
                            WebInputIahnNodeMessageTableInspectInfo.toStackBean(webInputIahnNodeMessageTableInspectInfo)
                    )
            );
            return FastJsonResponseData.of(ResponseDataUtil.good(result));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @PostMapping("/iahn-node/put-locale")
    @BehaviorAnalyse
    @BindingCheck
    @LoginRequired
    @PermissionRequired("webapi.controller_permitted.settingrepo.iahn_node.put_locale")
    public FastJsonResponseData<Object> putLocale(
            HttpServletRequest request,
            @RequestBody @Validated WebInputIahnNodeLocalePutInfo webInputIahnNodeLocalePutInfo,
            BindingResult bindingResult
    ) {
        try {
            service.putLocale(WebInputIahnNodeLocalePutInfo.toStackBean(webInputIahnNodeLocalePutInfo));
            return FastJsonResponseData.of(ResponseDataUtil.good(null));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @PostMapping("/iahn-node/remove-locale")
    @BehaviorAnalyse
    @BindingCheck
    @LoginRequired
    @PermissionRequired("webapi.controller_permitted.settingrepo.iahn_node.remove_locale")
    public FastJsonResponseData<Object> removeLocale(
            HttpServletRequest request,
            @RequestBody @Validated WebInputIahnNodeLocaleRemoveInfo webInputIahnNodeLocaleRemoveInfo,
            BindingResult bindingResult
    ) {
        try {
            service.removeLocale(WebInputIahnNodeLocaleRemoveInfo.toStackBean(webInputIahnNodeLocaleRemoveInfo));
            return FastJsonResponseData.of(ResponseDataUtil.good(null));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @PostMapping("/iahn-node/put-mek")
    @BehaviorAnalyse
    @BindingCheck
    @LoginRequired
    @PermissionRequired("webapi.controller_permitted.settingrepo.iahn_node.put_mek")
    public FastJsonResponseData<Object> putMek(
            HttpServletRequest request,
            @RequestBody @Validated WebInputIahnNodeMekPutInfo webInputIahnNodeMekPutInfo,
            BindingResult bindingResult
    ) {
        try {
            service.putMek(WebInputIahnNodeMekPutInfo.toStackBean(webInputIahnNodeMekPutInfo));
            return FastJsonResponseData.of(ResponseDataUtil.good(null));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @PostMapping("/iahn-node/remove-mek")
    @BehaviorAnalyse
    @BindingCheck
    @LoginRequired
    @PermissionRequired("webapi.controller_permitted.settingrepo.iahn_node.remove_mek")
    public FastJsonResponseData<Object> removeMek(
            HttpServletRequest request,
            @RequestBody @Validated WebInputIahnNodeMekRemoveInfo webInputIahnNodeMekRemoveInfo,
            BindingResult bindingResult
    ) {
        try {
            service.removeMek(WebInputIahnNodeMekRemoveInfo.toStackBean(webInputIahnNodeMekRemoveInfo));
            return FastJsonResponseData.of(ResponseDataUtil.good(null));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @PostMapping("/iahn-node/upsert-message")
    @BehaviorAnalyse
    @BindingCheck
    @LoginRequired
    @PermissionRequired("webapi.controller_permitted.settingrepo.iahn_node.upsert_message")
    public FastJsonResponseData<Object> upsertMessage(
            HttpServletRequest request,
            @RequestBody @Validated WebInputIahnNodeMessageUpsertInfo webInputIahnNodeMessageUpsertInfo,
            BindingResult bindingResult
    ) {
        try {
            service.upsertMessage(WebInputIahnNodeMessageUpsertInfo.toStackBean(webInputIahnNodeMessageUpsertInfo));
            return FastJsonResponseData.of(ResponseDataUtil.good(null));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @PostMapping("/iahn-node/batch-upsert-message-by-locale")
    @BehaviorAnalyse
    @BindingCheck
    @LoginRequired
    @PermissionRequired("webapi.controller_permitted.settingrepo.iahn_node.batch_upsert_message_by_locale")
    public FastJsonResponseData<Object> batchUpsertMessageByLocale(
            HttpServletRequest request,
            @RequestBody @Validated WebInputIahnNodeMessageUpsertByLocaleInfo webInputIahnNodeMessageUpsertByLocaleInfo,
            BindingResult bindingResult
    ) {
        try {
            service.batchUpsertMessageByLocale(
                    WebInputIahnNodeMessageUpsertByLocaleInfo.toStackBean(webInputIahnNodeMessageUpsertByLocaleInfo)
            );
            return FastJsonResponseData.of(ResponseDataUtil.good(null));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @PostMapping("/iahn-node/batch-upsert-message-by-mek")
    @BehaviorAnalyse
    @BindingCheck
    @LoginRequired
    @PermissionRequired("webapi.controller_permitted.settingrepo.iahn_node.batch_upsert_message_by_mek")
    public FastJsonResponseData<Object> batchUpsertMessageByMek(
            HttpServletRequest request,
            @RequestBody @Validated WebInputIahnNodeMessageUpsertByMekInfo webInputIahnNodeMessageUpsertByMekInfo,
            BindingResult bindingResult
    ) {
        try {
            service.batchUpsertMessageByMek(
                    WebInputIahnNodeMessageUpsertByMekInfo.toStackBean(webInputIahnNodeMessageUpsertByMekInfo)
            );
            return FastJsonResponseData.of(ResponseDataUtil.good(null));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @PostMapping("/iahn-node/inspect-message-for-public")
    @BehaviorAnalyse
    @BindingCheck
    public FastJsonResponseData<FastJsonIahnNodeMessageInspectResult> inspectMessageForPublic(
            HttpServletRequest request,
            @RequestBody @Validated PublicIahnNodeMessageInspectInfo publicIahnNodeMessageInspectInfo,
            BindingResult bindingResult
    ) {
        try {
            FastJsonIahnNodeMessageInspectResult result = FastJsonIahnNodeMessageInspectResult.of(
                    service.inspectMessageForPublic(publicIahnNodeMessageInspectInfo)
            );
            return FastJsonResponseData.of(ResponseDataUtil.good(result));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @PostMapping("/iahn-node/batch-inspect-message-by-locale-for-public")
    @BehaviorAnalyse
    @BindingCheck
    public FastJsonResponseData<FastJsonIahnNodeMessageInspectByLocaleResult> batchInspectMessageByLocaleForPublic(
            HttpServletRequest request,
            @RequestBody @Validated PublicIahnNodeMessageInspectByLocaleInfo publicIahnNodeMessageInspectByLocaleInfo,
            BindingResult bindingResult
    ) {
        try {
            FastJsonIahnNodeMessageInspectByLocaleResult result = FastJsonIahnNodeMessageInspectByLocaleResult.of(
                    service.batchInspectMessageByLocaleForPublic(publicIahnNodeMessageInspectByLocaleInfo)
            );
            return FastJsonResponseData.of(ResponseDataUtil.good(result));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @PostMapping("/iahn-node/inspect-locale-list-for-public")
    @BehaviorAnalyse
    @BindingCheck
    public FastJsonResponseData<FastJsonIahnNodeLocaleListInspectResult> inspectLocaleListForPublic(
            HttpServletRequest request,
            @RequestBody @Validated PublicIahnNodeLocaleListInspectInfo publicIahnNodeLocaleListInspectInfo,
            BindingResult bindingResult
    ) {
        try {
            FastJsonIahnNodeLocaleListInspectResult result = FastJsonIahnNodeLocaleListInspectResult.of(
                    service.inspectLocaleListForPublic(publicIahnNodeLocaleListInspectInfo)
            );
            return FastJsonResponseData.of(ResponseDataUtil.good(result));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @PostMapping("/iahn-node/inspect-mek-list-for-public")
    @BehaviorAnalyse
    @BindingCheck
    public FastJsonResponseData<FastJsonIahnNodeMekListInspectResult> inspectMekListForPublic(
            HttpServletRequest request,
            @RequestBody @Validated PublicIahnNodeMekListInspectInfo publicIahnNodeMekListInspectInfo,
            BindingResult bindingResult
    ) {
        try {
            FastJsonIahnNodeMekListInspectResult result = FastJsonIahnNodeMekListInspectResult.of(
                    service.inspectMekListForPublic(publicIahnNodeMekListInspectInfo)
            );
            return FastJsonResponseData.of(ResponseDataUtil.good(result));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @PostMapping("/iahn-node/inspect-message-table-for-public")
    @BehaviorAnalyse
    @BindingCheck
    public FastJsonResponseData<FastJsonIahnNodeMessageTableInspectResult> inspectMessageTableForPublic(
            HttpServletRequest request,
            @RequestBody @Validated PublicIahnNodeMessageTableInspectInfo publicIahnNodeMessageTableInspectInfo,
            BindingResult bindingResult
    ) {
        try {
            FastJsonIahnNodeMessageTableInspectResult result = FastJsonIahnNodeMessageTableInspectResult.of(
                    service.inspectMessageTableForPublic(publicIahnNodeMessageTableInspectInfo)
            );
            return FastJsonResponseData.of(ResponseDataUtil.good(result));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }
}
