package com.dwarfeng.familyhelper.webapi.stack.service.clannad;

import com.dwarfeng.familyhelper.clannad.stack.bean.dto.*;
import com.dwarfeng.familyhelper.clannad.stack.bean.entity.Message;
import com.dwarfeng.familyhelper.webapi.stack.bean.clannad.disp.DispMessage;
import com.dwarfeng.subgrade.stack.bean.dto.PagedData;
import com.dwarfeng.subgrade.stack.bean.dto.PagingInfo;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.bean.key.StringIdKey;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import com.dwarfeng.subgrade.stack.service.Service;

/**
 * 留言响应服务。
 *
 * @author DwArFeng
 * @since 1.4.0
 */
public interface MessageResponseService extends Service {

    boolean exists(LongIdKey key) throws ServiceException;

    Message get(LongIdKey key) throws ServiceException;

    PagedData<Message> all(PagingInfo pagingInfo) throws ServiceException;

    PagedData<Message> childForSendUser(StringIdKey sendUserKey, PagingInfo pagingInfo) throws ServiceException;

    PagedData<Message> childForReceiveUser(StringIdKey receiveUserKey, PagingInfo pagingInfo) throws ServiceException;

    PagedData<Message> childForSendUserDisplay(StringIdKey sendUserKey, PagingInfo pagingInfo) throws ServiceException;

    PagedData<Message> childForSendUserDisplayStatusEq(StringIdKey sendUserKey, int status, PagingInfo pagingInfo)
            throws ServiceException;

    PagedData<Message> childForReceiveUserDisplay(StringIdKey receiveUserKey, PagingInfo pagingInfo)
            throws ServiceException;

    DispMessage getDisp(LongIdKey key, StringIdKey inspectAccountKey) throws ServiceException;

    PagedData<DispMessage> allDisp(PagingInfo pagingInfo, StringIdKey inspectAccountKey) throws ServiceException;

    PagedData<DispMessage> childForSendUserDisp(
            StringIdKey sendUserKey, PagingInfo pagingInfo, StringIdKey inspectAccountKey
    ) throws ServiceException;

    PagedData<DispMessage> childForReceiveUserDisp(
            StringIdKey receiveUserKey, PagingInfo pagingInfo, StringIdKey inspectAccountKey
    ) throws ServiceException;

    PagedData<DispMessage> childForSendUserDisplayDisp(
            StringIdKey sendUserKey, PagingInfo pagingInfo, StringIdKey inspectAccountKey
    ) throws ServiceException;

    PagedData<DispMessage> childForSendUserDisplayStatusEqDisp(
            StringIdKey sendUserKey, int status, PagingInfo pagingInfo, StringIdKey inspectAccountKey
    ) throws ServiceException;

    PagedData<DispMessage> childForReceiveUserDisplayDisp(
            StringIdKey receiveUserKey, PagingInfo pagingInfo, StringIdKey inspectAccountKey
    ) throws ServiceException;

    LongIdKey create(MessageCreateInfo info, StringIdKey operateUserKey) throws ServiceException;

    void update(MessageUpdateInfo info, StringIdKey operateUserKey) throws ServiceException;

    void remove(MessageRemoveInfo info, StringIdKey operateUserKey) throws ServiceException;

    void markSent(MessageMarkSentInfo info, StringIdKey operateUserKey) throws ServiceException;

    void markReceived(MessageMarkReceivedInfo info, StringIdKey operateUserKey) throws ServiceException;

    void markReceiveUserHide(MessageMarkReceiveUserHideInfo info, StringIdKey operateUserKey) throws ServiceException;
}
