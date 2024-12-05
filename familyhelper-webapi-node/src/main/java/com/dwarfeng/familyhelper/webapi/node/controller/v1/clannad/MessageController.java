package com.dwarfeng.familyhelper.webapi.node.controller.v1.clannad;

import com.dwarfeng.familyhelper.clannad.sdk.bean.dto.*;
import com.dwarfeng.familyhelper.clannad.sdk.bean.entity.JSFixedFastJsonMessage;
import com.dwarfeng.familyhelper.clannad.stack.bean.entity.Message;
import com.dwarfeng.familyhelper.webapi.sdk.bean.disp.clannad.JSFixedFastJsonDispMessage;
import com.dwarfeng.familyhelper.webapi.stack.bean.disp.clannad.DispMessage;
import com.dwarfeng.familyhelper.webapi.stack.handler.system.TokenHandler;
import com.dwarfeng.familyhelper.webapi.stack.service.clannad.MessageResponseService;
import com.dwarfeng.subgrade.sdk.bean.dto.FastJsonResponseData;
import com.dwarfeng.subgrade.sdk.bean.dto.JSFixedFastJsonPagedData;
import com.dwarfeng.subgrade.sdk.bean.dto.PagingUtil;
import com.dwarfeng.subgrade.sdk.bean.dto.ResponseDataUtil;
import com.dwarfeng.subgrade.sdk.bean.key.JSFixedFastJsonLongIdKey;
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
public class MessageController {

    private static final Logger LOGGER = LoggerFactory.getLogger(MessageController.class);

    private final MessageResponseService service;

    private final ServiceExceptionMapper sem;

    private final BeanTransformer<Message, JSFixedFastJsonMessage> messageBeanTransformer;
    private final BeanTransformer<DispMessage, JSFixedFastJsonDispMessage> dispMessageBeanTransformer;

    private final TokenHandler tokenHandler;

    public MessageController(
            MessageResponseService service, ServiceExceptionMapper sem,
            BeanTransformer<Message, JSFixedFastJsonMessage> messageBeanTransformer,
            BeanTransformer<DispMessage, JSFixedFastJsonDispMessage> dispMessageBeanTransformer,
            TokenHandler tokenHandler
    ) {
        this.service = service;
        this.sem = sem;
        this.messageBeanTransformer = messageBeanTransformer;
        this.dispMessageBeanTransformer = dispMessageBeanTransformer;
        this.tokenHandler = tokenHandler;
    }

    @GetMapping("/message/{id}/exists")
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

    @GetMapping("/message/{id}")
    @BehaviorAnalyse
    @LoginRequired
    public FastJsonResponseData<JSFixedFastJsonMessage> get(
            HttpServletRequest request, @PathVariable("id") Long id
    ) {
        try {
            Message message = service.get(new LongIdKey(id));
            return FastJsonResponseData.of(ResponseDataUtil.good(JSFixedFastJsonMessage.of(message)));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @GetMapping("/message/all")
    @BehaviorAnalyse
    @SkipRecord
    @LoginRequired
    public FastJsonResponseData<JSFixedFastJsonPagedData<JSFixedFastJsonMessage>> all(
            HttpServletRequest request, @RequestParam("page") int page, @RequestParam("rows") int rows) {
        try {
            PagedData<Message> all = service.all(new PagingInfo(page, rows));
            PagedData<JSFixedFastJsonMessage> transform = PagingUtil.transform(all, messageBeanTransformer);
            return FastJsonResponseData.of(ResponseDataUtil.good(JSFixedFastJsonPagedData.of(transform)));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @GetMapping("/account/send/{accountId}/message")
    @BehaviorAnalyse
    @SkipRecord
    @LoginRequired
    public FastJsonResponseData<JSFixedFastJsonPagedData<JSFixedFastJsonMessage>> childForSendAccount(
            HttpServletRequest request, @PathVariable("accountId") String accountId,
            @RequestParam("page") int page, @RequestParam("rows") int rows
    ) {
        try {
            StringIdKey sendUserKey = new StringIdKey(accountId);
            PagedData<Message> childForSendUser = service.childForSendUser(sendUserKey, new PagingInfo(page, rows));
            PagedData<JSFixedFastJsonMessage> transform = PagingUtil.transform(
                    childForSendUser, messageBeanTransformer
            );
            return FastJsonResponseData.of(ResponseDataUtil.good(JSFixedFastJsonPagedData.of(transform)));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @GetMapping("/account/receive/{accountId}/message")
    @BehaviorAnalyse
    @SkipRecord
    @LoginRequired
    public FastJsonResponseData<JSFixedFastJsonPagedData<JSFixedFastJsonMessage>> childForReceiveAccount(
            HttpServletRequest request, @PathVariable("accountId") String accountId,
            @RequestParam("page") int page, @RequestParam("rows") int rows
    ) {
        try {
            StringIdKey receiveUserKey = new StringIdKey(accountId);
            PagedData<Message> childForReceiveUser = service.childForReceiveUser(
                    receiveUserKey, new PagingInfo(page, rows)
            );
            PagedData<JSFixedFastJsonMessage> transform = PagingUtil.transform(
                    childForReceiveUser, messageBeanTransformer
            );
            return FastJsonResponseData.of(ResponseDataUtil.good(JSFixedFastJsonPagedData.of(transform)));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @GetMapping("/account/send/{accountId}/message/display")
    @BehaviorAnalyse
    @SkipRecord
    @LoginRequired
    public FastJsonResponseData<JSFixedFastJsonPagedData<JSFixedFastJsonMessage>> childForSendAccountDisplay(
            HttpServletRequest request, @PathVariable("accountId") String accountId,
            @RequestParam("page") int page, @RequestParam("rows") int rows
    ) {
        try {
            StringIdKey sendUserKey = new StringIdKey(accountId);
            PagedData<Message> childForSendUserDisplay = service.childForSendUserDisplay(
                    sendUserKey, new PagingInfo(page, rows)
            );
            PagedData<JSFixedFastJsonMessage> transform = PagingUtil.transform(
                    childForSendUserDisplay, messageBeanTransformer
            );
            return FastJsonResponseData.of(ResponseDataUtil.good(JSFixedFastJsonPagedData.of(transform)));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @GetMapping("/account/send/{accountId}/message/display/status-eq")
    @BehaviorAnalyse
    @SkipRecord
    @LoginRequired
    public FastJsonResponseData<JSFixedFastJsonPagedData<JSFixedFastJsonMessage>> childForSendAccountDisplayStatusEq(
            HttpServletRequest request, @PathVariable("accountId") String accountId,
            @RequestParam("status") int status, @RequestParam("page") int page, @RequestParam("rows") int rows
    ) {
        try {
            StringIdKey sendUserKey = new StringIdKey(accountId);
            PagedData<Message> childForSendUserDisplayStatusEq = service.childForSendUserDisplayStatusEq(
                    sendUserKey, status, new PagingInfo(page, rows)
            );
            PagedData<JSFixedFastJsonMessage> transform = PagingUtil.transform(
                    childForSendUserDisplayStatusEq, messageBeanTransformer
            );
            return FastJsonResponseData.of(ResponseDataUtil.good(JSFixedFastJsonPagedData.of(transform)));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @GetMapping("/account/receive/{accountId}/message/display")
    @BehaviorAnalyse
    @SkipRecord
    @LoginRequired
    public FastJsonResponseData<JSFixedFastJsonPagedData<JSFixedFastJsonMessage>> childForReceiveAccountDisplay(
            HttpServletRequest request, @PathVariable("accountId") String accountId,
            @RequestParam("page") int page, @RequestParam("rows") int rows
    ) {
        try {
            StringIdKey receiveUserKey = new StringIdKey(accountId);
            PagedData<Message> childForReceiveUserDisplay = service.childForReceiveUserDisplay(
                    receiveUserKey, new PagingInfo(page, rows)
            );
            PagedData<JSFixedFastJsonMessage> transform = PagingUtil.transform(
                    childForReceiveUserDisplay, messageBeanTransformer
            );
            return FastJsonResponseData.of(ResponseDataUtil.good(JSFixedFastJsonPagedData.of(transform)));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @GetMapping("/message/{id}/disp")
    @BehaviorAnalyse
    @LoginRequired
    public FastJsonResponseData<JSFixedFastJsonDispMessage> getDisp(
            HttpServletRequest request, @PathVariable("id") Long id
    ) {
        try {
            StringIdKey inspectAccountKey = tokenHandler.getAccountKey(request);
            DispMessage dispMessage = service.getDisp(new LongIdKey(id), inspectAccountKey);
            return FastJsonResponseData.of(ResponseDataUtil.good(JSFixedFastJsonDispMessage.of(dispMessage)));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @GetMapping("/message/all/disp")
    @BehaviorAnalyse
    @SkipRecord
    @LoginRequired
    public FastJsonResponseData<JSFixedFastJsonPagedData<JSFixedFastJsonDispMessage>> allDisp(
            HttpServletRequest request, @RequestParam("page") int page, @RequestParam("rows") int rows
    ) {
        try {
            StringIdKey inspectAccountKey = tokenHandler.getAccountKey(request);
            PagedData<DispMessage> allDisp = service.allDisp(new PagingInfo(page, rows), inspectAccountKey);
            PagedData<JSFixedFastJsonDispMessage> transform = PagingUtil.transform(allDisp, dispMessageBeanTransformer);
            return FastJsonResponseData.of(ResponseDataUtil.good(JSFixedFastJsonPagedData.of(transform)));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @GetMapping("/account/send/{accountId}/message/disp")
    @BehaviorAnalyse
    @SkipRecord
    @LoginRequired
    public FastJsonResponseData<JSFixedFastJsonPagedData<JSFixedFastJsonDispMessage>> childForSendAccountDisp(
            HttpServletRequest request, @PathVariable("accountId") String accountId,
            @RequestParam("page") int page, @RequestParam("rows") int rows
    ) {
        try {
            StringIdKey sendUserKey = new StringIdKey(accountId);
            StringIdKey inspectAccountKey = tokenHandler.getAccountKey(request);
            PagedData<DispMessage> childForSendUserDisp = service.childForSendUserDisp(
                    sendUserKey, new PagingInfo(page, rows), inspectAccountKey
            );
            PagedData<JSFixedFastJsonDispMessage> transform = PagingUtil.transform(
                    childForSendUserDisp, dispMessageBeanTransformer
            );
            return FastJsonResponseData.of(ResponseDataUtil.good(JSFixedFastJsonPagedData.of(transform)));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @GetMapping("/account/receive/{accountId}/message/disp")
    @BehaviorAnalyse
    @SkipRecord
    @LoginRequired
    public FastJsonResponseData<JSFixedFastJsonPagedData<JSFixedFastJsonDispMessage>> childForReceiveAccountDisp(
            HttpServletRequest request, @PathVariable("accountId") String accountId,
            @RequestParam("page") int page, @RequestParam("rows") int rows
    ) {
        try {
            StringIdKey receiveUserKey = new StringIdKey(accountId);
            StringIdKey inspectAccountKey = tokenHandler.getAccountKey(request);
            PagedData<DispMessage> childForReceiveUserDisp = service.childForReceiveUserDisp(
                    receiveUserKey, new PagingInfo(page, rows), inspectAccountKey
            );
            PagedData<JSFixedFastJsonDispMessage> transform = PagingUtil.transform(
                    childForReceiveUserDisp, dispMessageBeanTransformer
            );
            return FastJsonResponseData.of(ResponseDataUtil.good(JSFixedFastJsonPagedData.of(transform)));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @GetMapping("/account/send/{accountId}/message/display/disp")
    @BehaviorAnalyse
    @SkipRecord
    @LoginRequired
    public FastJsonResponseData<JSFixedFastJsonPagedData<JSFixedFastJsonDispMessage>> childForSendAccountDisplayDisp(
            HttpServletRequest request, @PathVariable("accountId") String accountId,
            @RequestParam("page") int page, @RequestParam("rows") int rows
    ) {
        try {
            StringIdKey sendUserKey = new StringIdKey(accountId);
            StringIdKey inspectAccountKey = tokenHandler.getAccountKey(request);
            PagedData<DispMessage> childForSendUserDisplayDisp = service.childForSendUserDisplayDisp(
                    sendUserKey, new PagingInfo(page, rows), inspectAccountKey
            );
            PagedData<JSFixedFastJsonDispMessage> transform = PagingUtil.transform(
                    childForSendUserDisplayDisp, dispMessageBeanTransformer
            );
            return FastJsonResponseData.of(ResponseDataUtil.good(JSFixedFastJsonPagedData.of(transform)));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @GetMapping("/account/send/{accountId}/message/display/status-eq/disp")
    @BehaviorAnalyse
    @SkipRecord
    @LoginRequired
    public FastJsonResponseData<JSFixedFastJsonPagedData<JSFixedFastJsonDispMessage>>
    childForSendAccountDisplayStatusEqDisp(
            HttpServletRequest request, @PathVariable("accountId") String accountId,
            @RequestParam("status") int status, @RequestParam("page") int page, @RequestParam("rows") int rows
    ) {
        try {
            StringIdKey sendUserKey = new StringIdKey(accountId);
            StringIdKey inspectAccountKey = tokenHandler.getAccountKey(request);
            PagedData<DispMessage> childForSendUserDisplayStatusEqDisp = service.childForSendUserDisplayStatusEqDisp(
                    sendUserKey, status, new PagingInfo(page, rows), inspectAccountKey
            );
            PagedData<JSFixedFastJsonDispMessage> transform = PagingUtil.transform(
                    childForSendUserDisplayStatusEqDisp, dispMessageBeanTransformer
            );
            return FastJsonResponseData.of(ResponseDataUtil.good(JSFixedFastJsonPagedData.of(transform)));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, ��息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @GetMapping("/account/receive/{accountId}/message/display/disp")
    @BehaviorAnalyse
    @SkipRecord
    @LoginRequired
    public FastJsonResponseData<JSFixedFastJsonPagedData<JSFixedFastJsonDispMessage>>
    childForReceiveAccountDisplayDisp(
            HttpServletRequest request, @PathVariable("accountId") String accountId,
            @RequestParam("page") int page, @RequestParam("rows") int rows
    ) {
        try {
            StringIdKey receiveUserKey = new StringIdKey(accountId);
            StringIdKey inspectAccountKey = tokenHandler.getAccountKey(request);
            PagedData<DispMessage> childForReceiveUserDisplayDisp = service.childForReceiveUserDisplayDisp(
                    receiveUserKey, new PagingInfo(page, rows), inspectAccountKey
            );
            PagedData<JSFixedFastJsonDispMessage> transform = PagingUtil.transform(
                    childForReceiveUserDisplayDisp, dispMessageBeanTransformer
            );
            return FastJsonResponseData.of(ResponseDataUtil.good(JSFixedFastJsonPagedData.of(transform)));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @PostMapping("/message/create")
    @BehaviorAnalyse
    @BindingCheck
    public FastJsonResponseData<JSFixedFastJsonLongIdKey> create(
            HttpServletRequest request,
            @RequestBody WebInputMessageCreateInfo info
    ) {
        try {
            StringIdKey operateUserKey = tokenHandler.getAccountKey(request);
            LongIdKey result = service.create(
                    WebInputMessageCreateInfo.toStackBean(info),
                    operateUserKey
            );
            return FastJsonResponseData.of(ResponseDataUtil.good(JSFixedFastJsonLongIdKey.of(result)));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @PostMapping("/message/update")
    @BehaviorAnalyse
    @BindingCheck
    public FastJsonResponseData<Object> update(
            HttpServletRequest request,
            @RequestBody WebInputMessageUpdateInfo info
    ) {
        try {
            StringIdKey operateUserKey = tokenHandler.getAccountKey(request);
            service.update(
                    WebInputMessageUpdateInfo.toStackBean(info),
                    operateUserKey
            );
            return FastJsonResponseData.of(ResponseDataUtil.good(null));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }


    @PostMapping("/message/remove")
    @BehaviorAnalyse
    @BindingCheck
    public FastJsonResponseData<Object> remove(
            HttpServletRequest request,
            @RequestBody WebInputMessageRemoveInfo info
    ) {
        try {
            StringIdKey operateUserKey = tokenHandler.getAccountKey(request);
            service.remove(
                    WebInputMessageRemoveInfo.toStackBean(info),
                    operateUserKey
            );
            return FastJsonResponseData.of(ResponseDataUtil.good(null));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @PostMapping("/message/mark-sent")
    @BehaviorAnalyse
    @BindingCheck
    public FastJsonResponseData<Object> markSent(
            HttpServletRequest request,
            @RequestBody WebInputMessageMarkSentInfo info
    ) {
        try {
            StringIdKey operateUserKey = tokenHandler.getAccountKey(request);
            service.markSent(
                    WebInputMessageMarkSentInfo.toStackBean(info),
                    operateUserKey
            );
            return FastJsonResponseData.of(ResponseDataUtil.good(null));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @PostMapping("/message/mark-received")
    @BehaviorAnalyse
    @BindingCheck
    public FastJsonResponseData<Object> markReceived(
            HttpServletRequest request,
            @RequestBody WebInputMessageMarkReceivedInfo info
    ) {
        try {
            StringIdKey operateUserKey = tokenHandler.getAccountKey(request);
            service.markReceived(
                    WebInputMessageMarkReceivedInfo.toStackBean(info),
                    operateUserKey
            );
            return FastJsonResponseData.of(ResponseDataUtil.good(null));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @PostMapping("/message/mark-receive-user-hide")
    @BehaviorAnalyse
    @BindingCheck
    public FastJsonResponseData<Object> markReceiveUserHide(
            HttpServletRequest request,
            @RequestBody WebInputMessageMarkReceiveUserHideInfo info
    ) {
        try {
            StringIdKey operateUserKey = tokenHandler.getAccountKey(request);
            service.markReceiveUserHide(
                    WebInputMessageMarkReceiveUserHideInfo.toStackBean(info),
                    operateUserKey
            );
            return FastJsonResponseData.of(ResponseDataUtil.good(null));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }
}
