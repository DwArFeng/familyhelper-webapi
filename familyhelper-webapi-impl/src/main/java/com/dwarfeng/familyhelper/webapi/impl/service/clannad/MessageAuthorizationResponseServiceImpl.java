package com.dwarfeng.familyhelper.webapi.impl.service.clannad;

import com.dwarfeng.familyhelper.clannad.stack.bean.dto.MessageAuthorizationCreateInfo;
import com.dwarfeng.familyhelper.clannad.stack.bean.dto.MessageAuthorizationRemoveInfo;
import com.dwarfeng.familyhelper.clannad.stack.bean.dto.MessageAuthorizationUpdateInfo;
import com.dwarfeng.familyhelper.clannad.stack.bean.entity.MessageAuthorization;
import com.dwarfeng.familyhelper.clannad.stack.bean.key.MessageAuthorizationKey;
import com.dwarfeng.familyhelper.clannad.stack.service.MessageAuthorizationMaintainService;
import com.dwarfeng.familyhelper.clannad.stack.service.MessageAuthorizationOperateService;
import com.dwarfeng.familyhelper.webapi.stack.bean.disp.clannad.DispMessageAuthorization;
import com.dwarfeng.familyhelper.webapi.stack.bean.disp.system.DispAccount;
import com.dwarfeng.familyhelper.webapi.stack.service.clannad.MessageAuthorizationResponseService;
import com.dwarfeng.familyhelper.webapi.stack.service.system.AccountResponseService;
import com.dwarfeng.subgrade.stack.bean.dto.PagedData;
import com.dwarfeng.subgrade.stack.bean.dto.PagingInfo;
import com.dwarfeng.subgrade.stack.bean.key.StringIdKey;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class MessageAuthorizationResponseServiceImpl implements MessageAuthorizationResponseService {

    private final MessageAuthorizationMaintainService messageAuthorizationMaintainService;
    private final MessageAuthorizationOperateService messageAuthorizationOperateService;

    private final AccountResponseService accountResponseService;

    public MessageAuthorizationResponseServiceImpl(
            @Qualifier("familyhelperClannadMessageAuthorizationMaintainService")
            MessageAuthorizationMaintainService messageAuthorizationMaintainService,
            @Qualifier("familyhelperClannadMessageAuthorizationOperateService")
            MessageAuthorizationOperateService messageAuthorizationOperateService,
            AccountResponseService accountResponseService
    ) {
        this.messageAuthorizationMaintainService = messageAuthorizationMaintainService;
        this.messageAuthorizationOperateService = messageAuthorizationOperateService;
        this.accountResponseService = accountResponseService;
    }

    @Override
    public boolean exists(MessageAuthorizationKey key) throws ServiceException {
        return messageAuthorizationMaintainService.exists(key);
    }

    @Override
    public MessageAuthorization get(MessageAuthorizationKey key) throws ServiceException {
        return messageAuthorizationMaintainService.get(key);
    }

    @Override
    public PagedData<MessageAuthorization> all(PagingInfo pagingInfo) throws ServiceException {
        return messageAuthorizationMaintainService.lookup(pagingInfo);
    }

    @Override
    public PagedData<MessageAuthorization> childForReceiveUser(StringIdKey receiveUserKey, PagingInfo pagingInfo)
            throws ServiceException {
        return messageAuthorizationMaintainService.lookup(
                MessageAuthorizationMaintainService.CHILD_FOR_RECEIVE_USER,
                new Object[]{receiveUserKey},
                pagingInfo
        );
    }

    @Override
    public PagedData<MessageAuthorization> childForAuthorizedSendUser(
            StringIdKey authorizedSendUserKey, PagingInfo pagingInfo
    ) throws ServiceException {
        return messageAuthorizationMaintainService.lookup(
                MessageAuthorizationMaintainService.CHILD_FOR_AUTHORIZED_SEND_USER,
                new Object[]{authorizedSendUserKey},
                pagingInfo
        );
    }

    @Override
    public PagedData<MessageAuthorization> childForAuthorizedSendUserIdLike(
            StringIdKey authorizedSendUserKey, String pattern, PagingInfo pagingInfo
    ) throws ServiceException {
        return messageAuthorizationMaintainService.lookup(
                MessageAuthorizationMaintainService.CHILD_FOR_AUTHORIZED_SEND_USER_ID_LIKE,
                new Object[]{authorizedSendUserKey, pattern},
                pagingInfo
        );
    }

    @Override
    public DispMessageAuthorization getDisp(MessageAuthorizationKey key, StringIdKey inspectAccountKey)
            throws ServiceException {
        MessageAuthorization messageAuthorization = get(key);
        return toDisp(messageAuthorization, inspectAccountKey);
    }

    @Override
    public PagedData<DispMessageAuthorization> allDisp(PagingInfo pagingInfo, StringIdKey inspectAccountKey)
            throws ServiceException {
        PagedData<MessageAuthorization> lookup = all(pagingInfo);
        return toDispPagedData(lookup, inspectAccountKey);
    }

    @Override
    public PagedData<DispMessageAuthorization> childForReceiveUserDisp(
            StringIdKey receiveUserKey, PagingInfo pagingInfo, StringIdKey inspectAccountKey
    ) throws ServiceException {
        PagedData<MessageAuthorization> lookup = childForReceiveUser(receiveUserKey, pagingInfo);
        return toDispPagedData(lookup, inspectAccountKey);
    }

    @Override
    public PagedData<DispMessageAuthorization> childForAuthorizedSendUserDisp(
            StringIdKey authorizedSendUserKey, PagingInfo pagingInfo, StringIdKey inspectAccountKey
    ) throws ServiceException {
        PagedData<MessageAuthorization> lookup = childForAuthorizedSendUser(authorizedSendUserKey, pagingInfo);
        return toDispPagedData(lookup, inspectAccountKey);
    }

    @Override
    public PagedData<DispMessageAuthorization> childForAuthorizedSendUserIdLikeDisp(
            StringIdKey authorizedSendUserKey, String pattern, PagingInfo pagingInfo,
            StringIdKey inspectAccountKey
    ) throws ServiceException {
        PagedData<MessageAuthorization> lookup = childForAuthorizedSendUserIdLike(
                authorizedSendUserKey, pattern, pagingInfo
        );
        return toDispPagedData(lookup, inspectAccountKey);
    }

    private DispMessageAuthorization toDisp(MessageAuthorization messageAuthorization, StringIdKey inspectAccountKey)
            throws ServiceException {
        DispAccount receiveAccount = null;
        StringIdKey receiveUserKey = Optional.ofNullable(messageAuthorization)
                .map(MessageAuthorization::getKey)
                .map(MessageAuthorizationKey::getReceiveUserId)
                .map(StringIdKey::new)
                .orElse(null);
        if (Objects.nonNull(receiveUserKey)) {
            receiveAccount = accountResponseService.getDisp(receiveUserKey, inspectAccountKey);
        }
        DispAccount authorizedSendAccount = null;
        StringIdKey authorizedSendUserKey = Optional.ofNullable(messageAuthorization)
                .map(MessageAuthorization::getKey)
                .map(MessageAuthorizationKey::getAuthorizedSendUserId)
                .map(StringIdKey::new)
                .orElse(null);
        if (Objects.nonNull(authorizedSendUserKey)) {
            authorizedSendAccount = accountResponseService.getDisp(authorizedSendUserKey, inspectAccountKey);
        }

        return DispMessageAuthorization.of(messageAuthorization, receiveAccount, authorizedSendAccount);
    }

    private PagedData<DispMessageAuthorization> toDispPagedData(
            PagedData<MessageAuthorization> lookup, StringIdKey inspectAccountKey
    ) throws ServiceException {
        List<DispMessageAuthorization> dispMessageAuthorizations = new ArrayList<>();
        Map<StringIdKey, DispAccount> cachedDispAccountMap = new HashMap<>();
        for (MessageAuthorization messageAuthorization : lookup.getData()) {
            dispMessageAuthorizations.add(toDispWithCache(messageAuthorization, inspectAccountKey, cachedDispAccountMap));
        }
        return new PagedData<>(
                lookup.getCurrentPage(), lookup.getTotalPages(), lookup.getRows(), lookup.getCount(),
                dispMessageAuthorizations
        );
    }

    @SuppressWarnings("DuplicatedCode")
    private DispMessageAuthorization toDispWithCache(
            MessageAuthorization messageAuthorization, StringIdKey inspectAccountKey,
            Map<StringIdKey, DispAccount> cachedDispAccountMap
    ) throws ServiceException {
        DispAccount receiveAccount = null;
        StringIdKey receiveUserKey = Optional.ofNullable(messageAuthorization)
                .map(MessageAuthorization::getKey)
                .map(MessageAuthorizationKey::getReceiveUserId)
                .map(StringIdKey::new)
                .orElse(null);
        if (Objects.nonNull(receiveUserKey)) {
            receiveAccount = cachedDispAccountMap.get(receiveUserKey);
            if (Objects.isNull(receiveAccount)) {
                receiveAccount = accountResponseService.getDisp(receiveUserKey, inspectAccountKey);
                cachedDispAccountMap.put(receiveUserKey, receiveAccount);
            }
        }
        DispAccount authorizedSendAccount = null;
        StringIdKey authorizedSendUserKey = Optional.ofNullable(messageAuthorization)
                .map(MessageAuthorization::getKey)
                .map(MessageAuthorizationKey::getAuthorizedSendUserId)
                .map(StringIdKey::new)
                .orElse(null);
        if (Objects.nonNull(authorizedSendUserKey)) {
            authorizedSendAccount = cachedDispAccountMap.get(authorizedSendUserKey);
            if (Objects.isNull(authorizedSendAccount)) {
                authorizedSendAccount = accountResponseService.getDisp(authorizedSendUserKey, inspectAccountKey);
                cachedDispAccountMap.put(authorizedSendUserKey, authorizedSendAccount);
            }
        }
        return DispMessageAuthorization.of(messageAuthorization, receiveAccount, authorizedSendAccount);
    }

    @Override
    public MessageAuthorizationKey create(MessageAuthorizationCreateInfo info) throws ServiceException {
        return messageAuthorizationOperateService.create(info);
    }

    @Override
    public void update(MessageAuthorizationUpdateInfo info) throws ServiceException {
        messageAuthorizationOperateService.update(info);
    }

    @Override
    public void remove(MessageAuthorizationRemoveInfo info) throws ServiceException {
        messageAuthorizationOperateService.remove(info);
    }
}
