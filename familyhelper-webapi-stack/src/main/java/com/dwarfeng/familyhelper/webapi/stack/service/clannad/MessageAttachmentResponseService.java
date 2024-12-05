package com.dwarfeng.familyhelper.webapi.stack.service.clannad;

import com.dwarfeng.familyhelper.clannad.stack.bean.dto.*;
import com.dwarfeng.familyhelper.clannad.stack.bean.entity.MessageAttachmentInfo;
import com.dwarfeng.subgrade.stack.bean.dto.PagedData;
import com.dwarfeng.subgrade.stack.bean.dto.PagingInfo;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.bean.key.StringIdKey;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import com.dwarfeng.subgrade.stack.service.Service;

/**
 * 留言附件响应服务。
 *
 * @author DwArFeng
 * @since 1.4.0
 */
public interface MessageAttachmentResponseService extends Service {

    boolean exists(LongIdKey key) throws ServiceException;

    MessageAttachmentInfo get(LongIdKey key) throws ServiceException;

    PagedData<MessageAttachmentInfo> childForMessage(LongIdKey messageKey, PagingInfo pagingInfo) throws ServiceException;

    PagedData<MessageAttachmentInfo> childForMessageOriginNameAsc(LongIdKey messageKey, PagingInfo pagingInfo)
            throws ServiceException;

    PagedData<MessageAttachmentInfo> childForMessageUploadDateDesc(LongIdKey messageKey, PagingInfo pagingInfo)
            throws ServiceException;

    MessageAttachment downloadMessageAttachment(StringIdKey userKey, LongIdKey messageAttachmentKey)
            throws ServiceException;

    LongIdKey requestMessageAttachmentStreamVoucher(StringIdKey userKey, LongIdKey messageAttachmentKey)
            throws ServiceException;

    MessageAttachmentStream downloadMessageAttachmentStreamByVoucher(LongIdKey voucherKey) throws ServiceException;

    void uploadMessageAttachment(StringIdKey userKey, MessageAttachmentUploadInfo messageAttachmentUploadInfo)
            throws ServiceException;

    void uploadMessageAttachmentStream(
            StringIdKey userKey, MessageAttachmentStreamUploadInfo messageAttachmentStreamUploadInfo
    ) throws ServiceException;

    void updateMessageAttachment(StringIdKey userKey, MessageAttachmentUpdateInfo messageAttachmentUpdateInfo)
            throws ServiceException;

    void updateMessageAttachmentStream(
            StringIdKey userKey, MessageAttachmentStreamUpdateInfo messageAttachmentStreamUpdateInfo
    ) throws ServiceException;

    void removeMessageAttachment(StringIdKey userKey, LongIdKey messageAttachmentKey) throws ServiceException;
}
