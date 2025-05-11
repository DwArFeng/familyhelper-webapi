package com.dwarfeng.familyhelper.webapi.impl.service.clannad;

import com.dwarfeng.familyhelper.clannad.stack.bean.dto.*;
import com.dwarfeng.familyhelper.clannad.stack.bean.entity.Message;
import com.dwarfeng.familyhelper.clannad.stack.service.MessageMaintainService;
import com.dwarfeng.familyhelper.clannad.stack.service.MessageOperateService;
import com.dwarfeng.familyhelper.webapi.stack.bean.clannad.disp.DispMessage;
import com.dwarfeng.familyhelper.webapi.stack.bean.system.disp.DispAccount;
import com.dwarfeng.familyhelper.webapi.stack.service.clannad.MessageResponseService;
import com.dwarfeng.familyhelper.webapi.stack.service.system.AccountResponseService;
import com.dwarfeng.subgrade.stack.bean.dto.PagedData;
import com.dwarfeng.subgrade.stack.bean.dto.PagingInfo;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.bean.key.StringIdKey;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class MessageResponseServiceImpl implements MessageResponseService {

    private final MessageMaintainService messageMaintainService;
    private final MessageOperateService messageOperateService;

    private final AccountResponseService accountResponseService;

    public MessageResponseServiceImpl(
            @Qualifier("familyhelperClannadMessageMaintainService")
            MessageMaintainService messageMaintainService,
            @Qualifier("familyhelperClannadMessageOperateService")
            MessageOperateService messageOperateService,
            AccountResponseService accountResponseService
    ) {
        this.messageMaintainService = messageMaintainService;
        this.messageOperateService = messageOperateService;
        this.accountResponseService = accountResponseService;
    }

    @Override
    public boolean exists(LongIdKey key) throws ServiceException {
        return messageMaintainService.exists(key);
    }

    @Override
    public Message get(LongIdKey key) throws ServiceException {
        return messageMaintainService.get(key);
    }

    @Override
    public PagedData<Message> all(PagingInfo pagingInfo) throws ServiceException {
        return messageMaintainService.lookup(pagingInfo);
    }

    @Override
    public PagedData<Message> childForSendUser(StringIdKey sendUserKey, PagingInfo pagingInfo)
            throws ServiceException {
        return messageMaintainService.lookup(
                MessageMaintainService.CHILD_FOR_SEND_USER,
                new Object[]{sendUserKey},
                pagingInfo
        );
    }

    @Override
    public PagedData<Message> childForReceiveUser(StringIdKey receiveUserKey, PagingInfo pagingInfo)
            throws ServiceException {
        return messageMaintainService.lookup(
                MessageMaintainService.CHILD_FOR_RECEIVE_USER,
                new Object[]{receiveUserKey},
                pagingInfo
        );
    }

    @Override
    public PagedData<Message> childForSendUserDisplay(StringIdKey sendUserKey, PagingInfo pagingInfo)
            throws ServiceException {
        return messageMaintainService.lookup(
                MessageMaintainService.CHILD_FOR_SEND_USER_DISPLAY,
                new Object[]{sendUserKey},
                pagingInfo
        );
    }

    @Override
    public PagedData<Message> childForSendUserDisplayStatusEq(
            StringIdKey sendUserKey, int status, PagingInfo pagingInfo
    ) throws ServiceException {
        return messageMaintainService.lookup(
                MessageMaintainService.CHILD_FOR_SEND_USER_DISPLAY_STATUS_EQ,
                new Object[]{sendUserKey, status},
                pagingInfo
        );
    }

    @Override
    public PagedData<Message> childForReceiveUserDisplay(StringIdKey receiveUserKey, PagingInfo pagingInfo)
            throws ServiceException {
        return messageMaintainService.lookup(
                MessageMaintainService.CHILD_FOR_RECEIVE_USER_DISPLAY,
                new Object[]{receiveUserKey},
                pagingInfo
        );
    }

    @Override
    public DispMessage getDisp(LongIdKey key, StringIdKey inspectAccountKey) throws ServiceException {
        Message message = get(key);
        return toDisp(message, inspectAccountKey);
    }

    @Override
    public PagedData<DispMessage> allDisp(PagingInfo pagingInfo, StringIdKey inspectAccountKey)
            throws ServiceException {
        PagedData<Message> lookup = all(pagingInfo);
        return toDispPagedData(lookup, inspectAccountKey);
    }

    @Override
    public PagedData<DispMessage> childForSendUserDisp(
            StringIdKey sendUserKey, PagingInfo pagingInfo, StringIdKey inspectAccountKey
    ) throws ServiceException {
        PagedData<Message> lookup = childForSendUser(sendUserKey, pagingInfo);
        return toDispPagedData(lookup, inspectAccountKey);
    }

    @Override
    public PagedData<DispMessage> childForReceiveUserDisp(
            StringIdKey receiveUserKey, PagingInfo pagingInfo, StringIdKey inspectAccountKey
    ) throws ServiceException {
        PagedData<Message> lookup = childForReceiveUser(receiveUserKey, pagingInfo);
        return toDispPagedData(lookup, inspectAccountKey);
    }

    @Override
    public PagedData<DispMessage> childForSendUserDisplayDisp(
            StringIdKey sendUserKey, PagingInfo pagingInfo, StringIdKey inspectAccountKey
    ) throws ServiceException {
        PagedData<Message> lookup = childForSendUserDisplay(sendUserKey, pagingInfo);
        return toDispPagedData(lookup, inspectAccountKey);
    }

    @Override
    public PagedData<DispMessage> childForSendUserDisplayStatusEqDisp(
            StringIdKey sendUserKey, int status, PagingInfo pagingInfo, StringIdKey inspectAccountKey
    ) throws ServiceException {
        PagedData<Message> lookup = childForSendUserDisplayStatusEq(sendUserKey, status, pagingInfo);
        return toDispPagedData(lookup, inspectAccountKey);
    }

    @Override
    public PagedData<DispMessage> childForReceiveUserDisplayDisp(
            StringIdKey receiveUserKey, PagingInfo pagingInfo, StringIdKey inspectAccountKey
    ) throws ServiceException {
        PagedData<Message> lookup = childForReceiveUserDisplay(receiveUserKey, pagingInfo);
        return toDispPagedData(lookup, inspectAccountKey);
    }

    private DispMessage toDisp(Message message, StringIdKey inspectAccountKey) throws ServiceException {
        DispAccount sendAccount = null;
        if (Objects.nonNull(message.getSendUserKey())) {
            sendAccount = accountResponseService.getDisp(message.getSendUserKey(), inspectAccountKey);
        }
        DispAccount receiveAccount = null;
        if (Objects.nonNull(message.getReceiveUserKey())) {
            receiveAccount = accountResponseService.getDisp(message.getReceiveUserKey(), inspectAccountKey);
        }

        return DispMessage.of(message, sendAccount, receiveAccount);
    }

    private PagedData<DispMessage> toDispPagedData(PagedData<Message> lookup, StringIdKey inspectAccountKey)
            throws ServiceException {
        List<DispMessage> dispMessages = new ArrayList<>();
        Map<StringIdKey, DispAccount> cachedDispAccountMap = new HashMap<>();
        for (Message message : lookup.getData()) {
            dispMessages.add(toDispWithCache(message, inspectAccountKey, cachedDispAccountMap));
        }
        return new PagedData<>(
                lookup.getCurrentPage(), lookup.getTotalPages(), lookup.getRows(), lookup.getCount(),
                dispMessages
        );
    }

    private DispMessage toDispWithCache(
            Message message, StringIdKey inspectAccountKey, Map<StringIdKey, DispAccount> cachedDispAccountMap
    ) throws ServiceException {
        DispAccount sendAccount = null;
        if (Objects.nonNull(message.getSendUserKey())) {
            sendAccount = cachedDispAccountMap.get(message.getSendUserKey());
            if (Objects.isNull(sendAccount)) {
                sendAccount = accountResponseService.getDisp(message.getSendUserKey(), inspectAccountKey);
                cachedDispAccountMap.put(message.getSendUserKey(), sendAccount);
            }
        }
        DispAccount receiveAccount = null;
        if (Objects.nonNull(message.getReceiveUserKey())) {
            receiveAccount = cachedDispAccountMap.get(message.getReceiveUserKey());
            if (Objects.isNull(receiveAccount)) {
                receiveAccount = accountResponseService.getDisp(message.getReceiveUserKey(), inspectAccountKey);
                cachedDispAccountMap.put(message.getReceiveUserKey(), receiveAccount);
            }
        }
        return DispMessage.of(message, sendAccount, receiveAccount);
    }

    @Override
    public LongIdKey create(MessageCreateInfo info, StringIdKey operateUserKey) throws ServiceException {
        return messageOperateService.create(info, operateUserKey);
    }

    @Override
    public void update(MessageUpdateInfo info, StringIdKey operateUserKey) throws ServiceException {
        messageOperateService.update(info, operateUserKey);
    }

    @Override
    public void remove(MessageRemoveInfo info, StringIdKey operateUserKey) throws ServiceException {
        messageOperateService.remove(info, operateUserKey);
    }

    @Override
    public void markSent(MessageMarkSentInfo info, StringIdKey operateUserKey) throws ServiceException {
        messageOperateService.markSent(info, operateUserKey);
    }

    @Override
    public void markReceived(MessageMarkReceivedInfo info, StringIdKey operateUserKey) throws ServiceException {
        messageOperateService.markReceived(info, operateUserKey);
    }

    @Override
    public void markReceiveUserHide(MessageMarkReceiveUserHideInfo info, StringIdKey operateUserKey)
            throws ServiceException {
        messageOperateService.markReceiveUserHide(info, operateUserKey);
    }
}
