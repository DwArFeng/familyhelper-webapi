package com.dwarfeng.familyhelper.webapi.impl.service.clannad;

import com.dwarfeng.familyhelper.clannad.stack.bean.dto.*;
import com.dwarfeng.familyhelper.clannad.stack.bean.entity.MessageAttachmentInfo;
import com.dwarfeng.familyhelper.clannad.stack.service.MessageAttachmentInfoMaintainService;
import com.dwarfeng.familyhelper.clannad.stack.service.MessageAttachmentOperateService;
import com.dwarfeng.familyhelper.plugin.clannad.bean.dto.DubboRestMessageAttachmentStream;
import com.dwarfeng.familyhelper.plugin.clannad.bean.dto.DubboRestMessageAttachmentStreamDownloadInfo;
import com.dwarfeng.familyhelper.plugin.clannad.bean.dto.DubboRestMessageAttachmentStreamUpdateInfo;
import com.dwarfeng.familyhelper.plugin.clannad.bean.dto.DubboRestMessageAttachmentStreamUploadInfo;
import com.dwarfeng.familyhelper.plugin.clannad.service.DubboRestMessageAttachmentOperateService;
import com.dwarfeng.familyhelper.plugin.commons.dto.VoucherIdWrapper;
import com.dwarfeng.familyhelper.webapi.stack.service.clannad.MessageAttachmentResponseService;
import com.dwarfeng.subgrade.stack.bean.dto.PagedData;
import com.dwarfeng.subgrade.stack.bean.dto.PagingInfo;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.bean.key.StringIdKey;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class MessageAttachmentResponseServiceImpl implements MessageAttachmentResponseService {

    private final MessageAttachmentInfoMaintainService messageAttachmentInfoMaintainService;
    private final MessageAttachmentOperateService messageAttachmentOperateService;
    private final DubboRestMessageAttachmentOperateService dubboRestMessageAttachmentOperateService;

    public MessageAttachmentResponseServiceImpl(
            @Qualifier("familyhelperClannadMessageAttachmentInfoMaintainService")
            MessageAttachmentInfoMaintainService messageAttachmentInfoMaintainService,
            @Qualifier("familyhelperClannadMessageAttachmentOperateService")
            MessageAttachmentOperateService messageAttachmentOperateService,
            @Qualifier("familyhelperPluginClannadDubboRestMessageAttachmentOperateService")
            DubboRestMessageAttachmentOperateService dubboRestMessageAttachmentOperateService
    ) {
        this.messageAttachmentInfoMaintainService = messageAttachmentInfoMaintainService;
        this.messageAttachmentOperateService = messageAttachmentOperateService;
        this.dubboRestMessageAttachmentOperateService = dubboRestMessageAttachmentOperateService;
    }

    @Override
    public boolean exists(LongIdKey key) throws ServiceException {
        return messageAttachmentInfoMaintainService.exists(key);
    }

    @Override
    public MessageAttachmentInfo get(LongIdKey key) throws ServiceException {
        return messageAttachmentInfoMaintainService.get(key);
    }

    @Override
    public PagedData<MessageAttachmentInfo> childForMessage(LongIdKey messageKey, PagingInfo pagingInfo)
            throws ServiceException {
        return messageAttachmentInfoMaintainService.lookup(
                MessageAttachmentInfoMaintainService.CHILD_FOR_MESSAGE, new Object[]{messageKey}, pagingInfo
        );
    }

    @Override
    public PagedData<MessageAttachmentInfo> childForMessageOriginNameAsc(LongIdKey messageKey, PagingInfo pagingInfo)
            throws ServiceException {
        return messageAttachmentInfoMaintainService.lookup(
                MessageAttachmentInfoMaintainService.CHILD_FOR_MESSAGE_ORIGIN_NAME_ASC,
                new Object[]{messageKey}, pagingInfo
        );
    }

    @Override
    public PagedData<MessageAttachmentInfo> childForMessageUploadDateDesc(LongIdKey messageKey, PagingInfo pagingInfo)
            throws ServiceException {
        return messageAttachmentInfoMaintainService.lookup(
                MessageAttachmentInfoMaintainService.CHILD_FOR_MESSAGE_UPLOAD_DATE_DESC,
                new Object[]{messageKey}, pagingInfo
        );
    }

    @Override
    public MessageAttachment downloadMessageAttachment(StringIdKey userKey, LongIdKey messageAttachmentKey)
            throws ServiceException {
        return messageAttachmentOperateService.download(userKey, messageAttachmentKey);
    }

    @Override
    public LongIdKey requestMessageAttachmentStreamVoucher(StringIdKey userKey, LongIdKey messageAttachmentKey)
            throws ServiceException {
        VoucherIdWrapper voucherIdWrapper =
                dubboRestMessageAttachmentOperateService.requestMessageAttachmentStreamVoucher(
                        new DubboRestMessageAttachmentStreamDownloadInfo(
                                userKey.getStringId(), messageAttachmentKey.getLongId()
                        )
                );
        return new LongIdKey(voucherIdWrapper.getVoucherId());
    }

    @Override
    public MessageAttachmentStream downloadMessageAttachmentStreamByVoucher(LongIdKey voucherKey)
            throws ServiceException {
        VoucherIdWrapper voucherIdWrapper = new VoucherIdWrapper(voucherKey.getLongId());
        DubboRestMessageAttachmentStream dubboRestMessageAttachmentStream =
                dubboRestMessageAttachmentOperateService.downloadMessageAttachmentStreamByVoucher(voucherIdWrapper);
        return new MessageAttachmentStream(
                dubboRestMessageAttachmentStream.getOriginName(), dubboRestMessageAttachmentStream.getLength(),
                dubboRestMessageAttachmentStream.getContent()
        );
    }

    @Override
    public void uploadMessageAttachment(StringIdKey userKey, MessageAttachmentUploadInfo messageAttachmentUploadInfo)
            throws ServiceException {
        messageAttachmentOperateService.upload(userKey, messageAttachmentUploadInfo);
    }

    @Override
    public void uploadMessageAttachmentStream(
            StringIdKey userKey, MessageAttachmentStreamUploadInfo messageAttachmentStreamUploadInfo
    ) throws ServiceException {
        dubboRestMessageAttachmentOperateService.uploadMessageAttachmentStream(
                new DubboRestMessageAttachmentStreamUploadInfo(
                        userKey.getStringId(),
                        messageAttachmentStreamUploadInfo.getMessageKey().getLongId(),
                        messageAttachmentStreamUploadInfo.getOriginName(),
                        messageAttachmentStreamUploadInfo.getLength(),
                        messageAttachmentStreamUploadInfo.getContent()
                )
        );
    }

    @Override
    public void updateMessageAttachment(StringIdKey userKey, MessageAttachmentUpdateInfo messageAttachmentUpdateInfo)
            throws ServiceException {
        messageAttachmentOperateService.update(userKey, messageAttachmentUpdateInfo);
    }

    @Override
    public void updateMessageAttachmentStream(
            StringIdKey userKey, MessageAttachmentStreamUpdateInfo messageAttachmentStreamUpdateInfo
    ) throws ServiceException {
        dubboRestMessageAttachmentOperateService.updateMessageAttachmentStream(
                new DubboRestMessageAttachmentStreamUpdateInfo(
                        userKey.getStringId(),
                        messageAttachmentStreamUpdateInfo.getMessageAttachmentInfoKey().getLongId(),
                        messageAttachmentStreamUpdateInfo.getOriginName(),
                        messageAttachmentStreamUpdateInfo.getLength(),
                        messageAttachmentStreamUpdateInfo.getContent()
                )
        );
    }

    @Override
    public void removeMessageAttachment(StringIdKey userKey, LongIdKey messageAttachmentKey) throws ServiceException {
        messageAttachmentOperateService.remove(userKey, messageAttachmentKey);
    }
}
