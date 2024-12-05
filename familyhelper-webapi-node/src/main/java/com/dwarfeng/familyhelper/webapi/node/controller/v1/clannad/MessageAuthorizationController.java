package com.dwarfeng.familyhelper.webapi.node.controller.v1.clannad;

import com.dwarfeng.familyhelper.clannad.sdk.bean.dto.WebInputMessageAuthorizationCreateInfo;
import com.dwarfeng.familyhelper.clannad.sdk.bean.dto.WebInputMessageAuthorizationRemoveInfo;
import com.dwarfeng.familyhelper.clannad.sdk.bean.dto.WebInputMessageAuthorizationUpdateInfo;
import com.dwarfeng.familyhelper.clannad.sdk.bean.entity.FastJsonMessageAuthorization;
import com.dwarfeng.familyhelper.clannad.sdk.bean.key.FastJsonMessageAuthorizationKey;
import com.dwarfeng.familyhelper.clannad.stack.bean.entity.MessageAuthorization;
import com.dwarfeng.familyhelper.clannad.stack.bean.key.MessageAuthorizationKey;
import com.dwarfeng.familyhelper.webapi.sdk.bean.disp.clannad.FastJsonDispMessageAuthorization;
import com.dwarfeng.familyhelper.webapi.stack.bean.disp.clannad.DispMessageAuthorization;
import com.dwarfeng.familyhelper.webapi.stack.handler.system.TokenHandler;
import com.dwarfeng.familyhelper.webapi.stack.service.clannad.MessageAuthorizationResponseService;
import com.dwarfeng.subgrade.sdk.bean.dto.FastJsonPagedData;
import com.dwarfeng.subgrade.sdk.bean.dto.FastJsonResponseData;
import com.dwarfeng.subgrade.sdk.bean.dto.PagingUtil;
import com.dwarfeng.subgrade.sdk.bean.dto.ResponseDataUtil;
import com.dwarfeng.subgrade.sdk.interceptor.analyse.BehaviorAnalyse;
import com.dwarfeng.subgrade.sdk.interceptor.analyse.SkipRecord;
import com.dwarfeng.subgrade.sdk.interceptor.http.BindingCheck;
import com.dwarfeng.subgrade.sdk.interceptor.login.LoginRequired;
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
 * 留言控制器。
 *
 * @author DwArFeng
 * @since 1.4.0
 */
@RestController
@RequestMapping("/api/v1/clannad")
public class MessageAuthorizationController {

    private static final Logger LOGGER = LoggerFactory.getLogger(MessageAuthorizationController.class);

    private final MessageAuthorizationResponseService service;

    private final ServiceExceptionMapper sem;

    private final BeanTransformer<MessageAuthorization, FastJsonMessageAuthorization> messageAuthorizationBeanTransformer;
    private final BeanTransformer<DispMessageAuthorization, FastJsonDispMessageAuthorization> dispMessageAuthorizationBeanTransformer;

    private final TokenHandler tokenHandler;

    public MessageAuthorizationController(
            MessageAuthorizationResponseService service, ServiceExceptionMapper sem,
            BeanTransformer<MessageAuthorization, FastJsonMessageAuthorization> messageAuthorizationBeanTransformer,
            BeanTransformer<DispMessageAuthorization, FastJsonDispMessageAuthorization> dispMessageAuthorizationBeanTransformer,
            TokenHandler tokenHandler
    ) {
        this.service = service;
        this.sem = sem;
        this.messageAuthorizationBeanTransformer = messageAuthorizationBeanTransformer;
        this.dispMessageAuthorizationBeanTransformer = dispMessageAuthorizationBeanTransformer;
        this.tokenHandler = tokenHandler;
    }

    @GetMapping("/message-authorization/{receiveUserId}&{authorizedSendUserId}/exists")
    @BehaviorAnalyse
    @LoginRequired
    public FastJsonResponseData<Boolean> exists(
            HttpServletRequest request,
            @PathVariable("receiveUserId") String receiveUserId,
            @PathVariable("authorizedSendUserId") String authorizedSendUserId
    ) {
        try {
            boolean exists = service.exists(new MessageAuthorizationKey(receiveUserId, authorizedSendUserId));
            return FastJsonResponseData.of(ResponseDataUtil.good(exists));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @GetMapping("/message-authorization/{receiveUserId}&{authorizedSendUserId}")
    @BehaviorAnalyse
    @LoginRequired
    public FastJsonResponseData<FastJsonMessageAuthorization> get(
            HttpServletRequest request,
            @PathVariable("receiveUserId") String receiveUserId,
            @PathVariable("authorizedSendUserId") String authorizedSendUserId
    ) {
        try {
            MessageAuthorization messageAuthorization = service.get(
                    new MessageAuthorizationKey(receiveUserId, authorizedSendUserId)
            );
            return FastJsonResponseData.of(ResponseDataUtil.good(
                    FastJsonMessageAuthorization.of(messageAuthorization)
            ));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @GetMapping("/message-authorization/all")
    @BehaviorAnalyse
    @SkipRecord
    @LoginRequired
    public FastJsonResponseData<FastJsonPagedData<FastJsonMessageAuthorization>> all(
            HttpServletRequest request, @RequestParam("page") int page, @RequestParam("rows") int rows
    ) {
        try {
            PagedData<MessageAuthorization> all = service.all(new PagingInfo(page, rows));
            PagedData<FastJsonMessageAuthorization> transform = PagingUtil.transform(
                    all, messageAuthorizationBeanTransformer
            );
            return FastJsonResponseData.of(ResponseDataUtil.good(FastJsonPagedData.of(transform)));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @GetMapping("/account/receive/{accountId}/message-authorization")
    @BehaviorAnalyse
    @SkipRecord
    @LoginRequired
    public FastJsonResponseData<FastJsonPagedData<FastJsonMessageAuthorization>> childForReceiveUser(
            HttpServletRequest request, @PathVariable("accountId") String accountId,
            @RequestParam("page") int page, @RequestParam("rows") int rows
    ) {
        try {
            StringIdKey receiveUserKey = new StringIdKey(accountId);
            PagedData<MessageAuthorization> childForReceiveUser = service.childForReceiveUser(
                    receiveUserKey, new PagingInfo(page, rows)
            );
            PagedData<FastJsonMessageAuthorization> transform = PagingUtil.transform(
                    childForReceiveUser, messageAuthorizationBeanTransformer
            );
            return FastJsonResponseData.of(ResponseDataUtil.good(FastJsonPagedData.of(transform)));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @GetMapping("/account/authorized-send/{accountId}/message-authorization")
    @BehaviorAnalyse
    @SkipRecord
    @LoginRequired
    public FastJsonResponseData<FastJsonPagedData<FastJsonMessageAuthorization>> childForAuthorizedSendUser(
            HttpServletRequest request, @PathVariable("accountId") String accountId,
            @RequestParam("page") int page, @RequestParam("rows") int rows
    ) {
        try {
            StringIdKey authorizedSendUserKey = new StringIdKey(accountId);
            PagedData<MessageAuthorization> childForAuthorizedSendUser = service.childForAuthorizedSendUser(
                    authorizedSendUserKey, new PagingInfo(page, rows)
            );
            PagedData<FastJsonMessageAuthorization> transform = PagingUtil.transform(
                    childForAuthorizedSendUser, messageAuthorizationBeanTransformer
            );
            return FastJsonResponseData.of(ResponseDataUtil.good(FastJsonPagedData.of(transform)));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @GetMapping("/account/authorized-send/{accountId}/message-authorization/id-like")
    @BehaviorAnalyse
    @SkipRecord
    @LoginRequired
    public FastJsonResponseData<FastJsonPagedData<FastJsonMessageAuthorization>> childForAuthorizedSendUserIdLike(
            HttpServletRequest request,
            @PathVariable("accountId") String accountId, @RequestParam("pattern") String pattern,
            @RequestParam("page") int page, @RequestParam("rows") int rows
    ) {
        try {
            StringIdKey authorizedSendUserKey = new StringIdKey(accountId);
            PagedData<MessageAuthorization> childForAuthorizedSendUserIdLike = service.childForAuthorizedSendUserIdLike(
                    authorizedSendUserKey, pattern, new PagingInfo(page, rows)
            );
            PagedData<FastJsonMessageAuthorization> transform = PagingUtil.transform(
                    childForAuthorizedSendUserIdLike, messageAuthorizationBeanTransformer
            );
            return FastJsonResponseData.of(ResponseDataUtil.good(FastJsonPagedData.of(transform)));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }


    @GetMapping("/message-authorization/{receiveUserId}&{authorizedSendUserId}/disp")
    @BehaviorAnalyse
    @LoginRequired
    public FastJsonResponseData<FastJsonDispMessageAuthorization> getDisp(
            HttpServletRequest request,
            @PathVariable("receiveUserId") String receiveUserId,
            @PathVariable("authorizedSendUserId") String authorizedSendUserId
    ) {
        try {
            StringIdKey inspectAccountKey = tokenHandler.getAccountKey(request);
            DispMessageAuthorization disp = service.getDisp(
                    new MessageAuthorizationKey(receiveUserId, authorizedSendUserId), inspectAccountKey
            );
            return FastJsonResponseData.of(ResponseDataUtil.good(FastJsonDispMessageAuthorization.of(disp)));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @GetMapping("/message-authorization/all/disp")
    @BehaviorAnalyse
    @SkipRecord
    @LoginRequired
    public FastJsonResponseData<FastJsonPagedData<FastJsonDispMessageAuthorization>> allDisp(
            HttpServletRequest request, @RequestParam("page") int page, @RequestParam("rows") int rows
    ) {
        try {
            StringIdKey inspectAccountKey = tokenHandler.getAccountKey(request);
            PagedData<DispMessageAuthorization> allDisp = service.allDisp(new PagingInfo(page, rows), inspectAccountKey);
            PagedData<FastJsonDispMessageAuthorization> transform = PagingUtil.transform(
                    allDisp, dispMessageAuthorizationBeanTransformer
            );
            return FastJsonResponseData.of(ResponseDataUtil.good(FastJsonPagedData.of(transform)));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @GetMapping("/account/receive/{accountId}/message-authorization/disp")
    @BehaviorAnalyse
    @SkipRecord
    @LoginRequired
    public FastJsonResponseData<FastJsonPagedData<FastJsonDispMessageAuthorization>> childForReceiveUserDisp(
            HttpServletRequest request, @PathVariable("accountId") String accountId,
            @RequestParam("page") int page, @RequestParam("rows") int rows
    ) {
        try {
            StringIdKey receiveUserKey = new StringIdKey(accountId);
            StringIdKey inspectAccountKey = tokenHandler.getAccountKey(request);
            PagedData<DispMessageAuthorization> childForReceiveUserDisp = service.childForReceiveUserDisp(
                    receiveUserKey, new PagingInfo(page, rows), inspectAccountKey
            );
            PagedData<FastJsonDispMessageAuthorization> transform = PagingUtil.transform(
                    childForReceiveUserDisp, dispMessageAuthorizationBeanTransformer
            );
            return FastJsonResponseData.of(ResponseDataUtil.good(FastJsonPagedData.of(transform)));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @GetMapping("/account/authorized-send/{accountId}/message-authorization/disp")
    @BehaviorAnalyse
    @SkipRecord
    @LoginRequired
    public FastJsonResponseData<FastJsonPagedData<FastJsonDispMessageAuthorization>> childForAuthorizedSendUserDisp(
            HttpServletRequest request, @PathVariable("accountId") String accountId,
            @RequestParam("page") int page, @RequestParam("rows") int rows
    ) {
        try {
            StringIdKey authorizedSendUserKey = new StringIdKey(accountId);
            StringIdKey inspectAccountKey = tokenHandler.getAccountKey(request);
            PagedData<DispMessageAuthorization> childForAuthorizedSendUserDisp = service.childForAuthorizedSendUserDisp(
                    authorizedSendUserKey, new PagingInfo(page, rows), inspectAccountKey
            );
            PagedData<FastJsonDispMessageAuthorization> transform = PagingUtil.transform(
                    childForAuthorizedSendUserDisp, dispMessageAuthorizationBeanTransformer
            );
            return FastJsonResponseData.of(ResponseDataUtil.good(FastJsonPagedData.of(transform)));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @GetMapping("/account/authorized-send/{accountId}/message-authorization/id-like/disp")
    @BehaviorAnalyse
    @SkipRecord
    @LoginRequired
    public FastJsonResponseData<FastJsonPagedData<FastJsonDispMessageAuthorization>> childForAuthorizedSendUserIdLikeDisp(
            HttpServletRequest request,
            @PathVariable("accountId") String accountId, @RequestParam("pattern") String pattern,
            @RequestParam("page") int page, @RequestParam("rows") int rows
    ) {
        try {
            StringIdKey authorizedSendUserKey = new StringIdKey(accountId);
            StringIdKey inspectAccountKey = tokenHandler.getAccountKey(request);
            PagedData<DispMessageAuthorization> childForAuthorizedSendUserIdLikeDisp = service.childForAuthorizedSendUserIdLikeDisp(
                    authorizedSendUserKey, pattern, new PagingInfo(page, rows), inspectAccountKey
            );
            PagedData<FastJsonDispMessageAuthorization> transform = PagingUtil.transform(
                    childForAuthorizedSendUserIdLikeDisp, dispMessageAuthorizationBeanTransformer
            );
            return FastJsonResponseData.of(ResponseDataUtil.good(FastJsonPagedData.of(transform)));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @PostMapping("/message-authorization/create")
    @BehaviorAnalyse
    @BindingCheck
    public FastJsonResponseData<FastJsonMessageAuthorizationKey> create(
            HttpServletRequest request, @RequestBody WebInputMessageAuthorizationCreateInfo info
    ) {
        try {
            MessageAuthorizationKey result = service.create(WebInputMessageAuthorizationCreateInfo.toStackBean(info));
            return FastJsonResponseData.of(ResponseDataUtil.good(FastJsonMessageAuthorizationKey.of(result)));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @PostMapping("/message-authorization/update")
    @BehaviorAnalyse
    @BindingCheck
    public FastJsonResponseData<Object> update(
            HttpServletRequest request, @RequestBody WebInputMessageAuthorizationUpdateInfo info
    ) {
        try {
            service.update(WebInputMessageAuthorizationUpdateInfo.toStackBean(info));
            return FastJsonResponseData.of(ResponseDataUtil.good(null));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }


    @PostMapping("/message-authorization/remove")
    @BehaviorAnalyse
    @BindingCheck
    public FastJsonResponseData<Object> remove(
            HttpServletRequest request, @RequestBody WebInputMessageAuthorizationRemoveInfo info
    ) {
        try {
            service.remove(WebInputMessageAuthorizationRemoveInfo.toStackBean(info));
            return FastJsonResponseData.of(ResponseDataUtil.good(null));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }
}
