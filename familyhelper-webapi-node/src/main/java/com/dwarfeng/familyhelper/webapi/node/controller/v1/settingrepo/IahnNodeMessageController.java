package com.dwarfeng.familyhelper.webapi.node.controller.v1.settingrepo;

import com.dwarfeng.familyhelper.webapi.stack.service.settingrepo.IahnNodeMessageResponseService;
import com.dwarfeng.settingrepo.sdk.bean.entity.FastJsonIahnNodeMessage;
import com.dwarfeng.settingrepo.stack.bean.entity.IahnNodeMessage;
import com.dwarfeng.settingrepo.stack.bean.key.IahnNodeLocaleKey;
import com.dwarfeng.settingrepo.stack.bean.key.IahnNodeMekKey;
import com.dwarfeng.settingrepo.stack.bean.key.IahnNodeMessageKey;
import com.dwarfeng.subgrade.sdk.bean.dto.FastJsonResponseData;
import com.dwarfeng.subgrade.sdk.bean.dto.JSFixedFastJsonPagedData;
import com.dwarfeng.subgrade.sdk.bean.dto.PagingUtil;
import com.dwarfeng.subgrade.sdk.bean.dto.ResponseDataUtil;
import com.dwarfeng.subgrade.sdk.interceptor.analyse.BehaviorAnalyse;
import com.dwarfeng.subgrade.sdk.interceptor.analyse.SkipRecord;
import com.dwarfeng.subgrade.sdk.interceptor.login.LoginRequired;
import com.dwarfeng.subgrade.sdk.interceptor.permission.PermissionRequired;
import com.dwarfeng.subgrade.stack.bean.BeanTransformer;
import com.dwarfeng.subgrade.stack.bean.dto.PagedData;
import com.dwarfeng.subgrade.stack.bean.dto.PagingInfo;
import com.dwarfeng.subgrade.stack.bean.key.StringIdKey;
import com.dwarfeng.subgrade.stack.exception.ServiceExceptionMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * 国际化节点消息控制器。
 *
 * @author DwArFeng
 * @since 2.4.1
 */
@RestController
@RequestMapping("/api/v1/settingrepo")
public class IahnNodeMessageController {

    private static final Logger LOGGER = LoggerFactory.getLogger(IahnNodeMessageController.class);

    private final IahnNodeMessageResponseService service;
    private final ServiceExceptionMapper sem;

    private final BeanTransformer<IahnNodeMessage, FastJsonIahnNodeMessage> beanTransformer;

    public IahnNodeMessageController(
            IahnNodeMessageResponseService service, ServiceExceptionMapper sem,
            BeanTransformer<IahnNodeMessage, FastJsonIahnNodeMessage> beanTransformer
    ) {
        this.service = service;
        this.sem = sem;
        this.beanTransformer = beanTransformer;
    }

    @GetMapping("/iahn-node-message/{nodeId}/{language}/{mekId}/exists")
    @BehaviorAnalyse
    @LoginRequired
    @PermissionRequired("webapi.controller_permitted.settingrepo.iahn_node_message.exists")
    public FastJsonResponseData<Boolean> exists(
            HttpServletRequest request,
            @PathVariable("nodeId") String nodeId,
            @PathVariable("language") String language,
            @PathVariable("mekId") String mekId,
            @RequestParam(value = "country", required = false, defaultValue = "") String country,
            @RequestParam(value = "variant", required = false, defaultValue = "") String variant
    ) {
        try {
            boolean exists = service.exists(new IahnNodeMessageKey(nodeId, language, country, variant, mekId));
            return FastJsonResponseData.of(ResponseDataUtil.good(exists));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @GetMapping("/iahn-node-message/{nodeId}/{language}/{mekId}")
    @BehaviorAnalyse
    @LoginRequired
    @PermissionRequired("webapi.controller_permitted.settingrepo.iahn_node_message.get")
    public FastJsonResponseData<FastJsonIahnNodeMessage> get(
            HttpServletRequest request,
            @PathVariable("nodeId") String nodeId,
            @PathVariable("language") String language,
            @PathVariable("mekId") String mekId,
            @RequestParam(value = "country", required = false, defaultValue = "") String country,
            @RequestParam(value = "variant", required = false, defaultValue = "") String variant
    ) {
        try {
            IahnNodeMessage iahnNodeMessage = service.get(
                    new IahnNodeMessageKey(nodeId, language, country, variant, mekId)
            );
            return FastJsonResponseData.of(ResponseDataUtil.good(FastJsonIahnNodeMessage.of(iahnNodeMessage)));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @GetMapping("/iahn-node-message/all")
    @BehaviorAnalyse
    @SkipRecord
    @LoginRequired
    @PermissionRequired("webapi.controller_permitted.settingrepo.iahn_node_message.all")
    public FastJsonResponseData<JSFixedFastJsonPagedData<FastJsonIahnNodeMessage>> all(
            HttpServletRequest request, @RequestParam("page") int page, @RequestParam("rows") int rows
    ) {
        try {
            PagedData<IahnNodeMessage> all = service.all(new PagingInfo(page, rows));
            PagedData<FastJsonIahnNodeMessage> transform = PagingUtil.transform(all, beanTransformer);
            return FastJsonResponseData.of(ResponseDataUtil.good(JSFixedFastJsonPagedData.of(transform)));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @GetMapping("/iahn-node/{nodeId}/iahn-node-message")
    @BehaviorAnalyse
    @SkipRecord
    @LoginRequired
    @PermissionRequired("webapi.controller_permitted.settingrepo.iahn_node_message.child_for_node")
    public FastJsonResponseData<JSFixedFastJsonPagedData<FastJsonIahnNodeMessage>> childForNode(
            HttpServletRequest request,
            @PathVariable("nodeId") String nodeId,
            @RequestParam("page") int page,
            @RequestParam("rows") int rows
    ) {
        try {
            PagedData<IahnNodeMessage> childForNode = service.childForNode(
                    new StringIdKey(nodeId), new PagingInfo(page, rows)
            );
            PagedData<FastJsonIahnNodeMessage> transform = PagingUtil.transform(childForNode, beanTransformer);
            return FastJsonResponseData.of(ResponseDataUtil.good(JSFixedFastJsonPagedData.of(transform)));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @GetMapping("/iahn-node-locale/{nodeId}/{language}/iahn-node-message")
    @BehaviorAnalyse
    @SkipRecord
    @LoginRequired
    @PermissionRequired("webapi.controller_permitted.settingrepo.iahn_node_message.child_for_locale")
    public FastJsonResponseData<JSFixedFastJsonPagedData<FastJsonIahnNodeMessage>> childForLocale(
            HttpServletRequest request,
            @PathVariable("nodeId") String nodeId,
            @PathVariable("language") String language,
            @RequestParam(value = "country", required = false, defaultValue = "") String country,
            @RequestParam(value = "variant", required = false, defaultValue = "") String variant,
            @RequestParam("page") int page,
            @RequestParam("rows") int rows
    ) {
        try {
            PagedData<IahnNodeMessage> childForLocale = service.childForLocale(
                    new IahnNodeLocaleKey(nodeId, language, country, variant), new PagingInfo(page, rows)
            );
            PagedData<FastJsonIahnNodeMessage> transform = PagingUtil.transform(childForLocale, beanTransformer);
            return FastJsonResponseData.of(ResponseDataUtil.good(JSFixedFastJsonPagedData.of(transform)));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @GetMapping("/iahn-node-mek/{nodeId}/{mekId}/iahn-node-message")
    @BehaviorAnalyse
    @SkipRecord
    @LoginRequired
    @PermissionRequired("webapi.controller_permitted.settingrepo.iahn_node_message.child_for_mek")
    public FastJsonResponseData<JSFixedFastJsonPagedData<FastJsonIahnNodeMessage>> childForMek(
            HttpServletRequest request,
            @PathVariable("nodeId") String nodeId,
            @PathVariable("mekId") String mekId,
            @RequestParam("page") int page,
            @RequestParam("rows") int rows
    ) {
        try {
            PagedData<IahnNodeMessage> childForMek = service.childForMek(
                    new IahnNodeMekKey(nodeId, mekId), new PagingInfo(page, rows)
            );
            PagedData<FastJsonIahnNodeMessage> transform = PagingUtil.transform(childForMek, beanTransformer);
            return FastJsonResponseData.of(ResponseDataUtil.good(JSFixedFastJsonPagedData.of(transform)));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }
}
