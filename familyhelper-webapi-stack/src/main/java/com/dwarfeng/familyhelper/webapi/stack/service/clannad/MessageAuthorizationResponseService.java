package com.dwarfeng.familyhelper.webapi.stack.service.clannad;

import com.dwarfeng.familyhelper.clannad.stack.bean.dto.MessageAuthorizationCreateInfo;
import com.dwarfeng.familyhelper.clannad.stack.bean.dto.MessageAuthorizationRemoveInfo;
import com.dwarfeng.familyhelper.clannad.stack.bean.dto.MessageAuthorizationUpdateInfo;
import com.dwarfeng.familyhelper.clannad.stack.bean.entity.MessageAuthorization;
import com.dwarfeng.familyhelper.clannad.stack.bean.key.MessageAuthorizationKey;
import com.dwarfeng.familyhelper.webapi.stack.bean.disp.clannad.DispMessageAuthorization;
import com.dwarfeng.subgrade.stack.bean.dto.PagedData;
import com.dwarfeng.subgrade.stack.bean.dto.PagingInfo;
import com.dwarfeng.subgrade.stack.bean.key.StringIdKey;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import com.dwarfeng.subgrade.stack.service.Service;

/**
 * 留言授权响应服务。
 *
 * @author DwArFeng
 * @since 1.4.0
 */
public interface MessageAuthorizationResponseService extends Service {

    boolean exists(MessageAuthorizationKey key) throws ServiceException;

    MessageAuthorization get(MessageAuthorizationKey key) throws ServiceException;

    PagedData<MessageAuthorization> all(PagingInfo pagingInfo) throws ServiceException;

    PagedData<MessageAuthorization> childForReceiveUser(StringIdKey receiveUserKey, PagingInfo pagingInfo)
            throws ServiceException;

    PagedData<MessageAuthorization> childForAuthorizedSendUser(StringIdKey authorizedSendUserKey, PagingInfo pagingInfo)
            throws ServiceException;

    PagedData<MessageAuthorization> childForAuthorizedSendUserIdLike(
            StringIdKey authorizedSendUserKey, String pattern, PagingInfo pagingInfo
    ) throws ServiceException;

    DispMessageAuthorization getDisp(MessageAuthorizationKey key, StringIdKey inspectAccountKey)
            throws ServiceException;

    PagedData<DispMessageAuthorization> allDisp(PagingInfo pagingInfo, StringIdKey inspectAccountKey)
            throws ServiceException;

    PagedData<DispMessageAuthorization> childForReceiveUserDisp(
            StringIdKey receiveUserKey, PagingInfo pagingInfo, StringIdKey inspectAccountKey
    ) throws ServiceException;

    PagedData<DispMessageAuthorization> childForAuthorizedSendUserDisp(
            StringIdKey authorizedSendUserKey, PagingInfo pagingInfo, StringIdKey inspectAccountKey
    ) throws ServiceException;

    PagedData<DispMessageAuthorization> childForAuthorizedSendUserIdLikeDisp(
            StringIdKey authorizedSendUserKey, String pattern, PagingInfo pagingInfo, StringIdKey inspectAccountKey
    ) throws ServiceException;

    MessageAuthorizationKey create(MessageAuthorizationCreateInfo info) throws ServiceException;

    void update(MessageAuthorizationUpdateInfo info) throws ServiceException;

    void remove(MessageAuthorizationRemoveInfo info) throws ServiceException;
}
