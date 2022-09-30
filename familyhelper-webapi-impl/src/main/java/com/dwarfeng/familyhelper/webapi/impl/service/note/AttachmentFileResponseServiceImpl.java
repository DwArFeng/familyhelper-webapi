package com.dwarfeng.familyhelper.webapi.impl.service.note;

import com.dwarfeng.familyhelper.note.stack.bean.dto.AttachmentFile;
import com.dwarfeng.familyhelper.note.stack.bean.dto.AttachmentFileUploadInfo;
import com.dwarfeng.familyhelper.note.stack.bean.entity.AttachmentFileInfo;
import com.dwarfeng.familyhelper.note.stack.service.AttachmentFileInfoMaintainService;
import com.dwarfeng.familyhelper.note.stack.service.AttachmentFileOperateService;
import com.dwarfeng.familyhelper.webapi.stack.service.note.AttachmentFileResponseService;
import com.dwarfeng.subgrade.stack.bean.dto.PagedData;
import com.dwarfeng.subgrade.stack.bean.dto.PagingInfo;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.bean.key.StringIdKey;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class AttachmentFileResponseServiceImpl implements AttachmentFileResponseService {

    private final AttachmentFileInfoMaintainService attachmentFileInfoMaintainService;
    private final AttachmentFileOperateService attachmentFileOperateService;

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    public AttachmentFileResponseServiceImpl(
            @Qualifier("familyhelperNoteAttachmentFileInfoMaintainService")
            AttachmentFileInfoMaintainService attachmentFileInfoMaintainService,
            @Qualifier("familyhelperNoteAttachmentFileOperateService")
            AttachmentFileOperateService attachmentFileOperateService
    ) {
        this.attachmentFileInfoMaintainService = attachmentFileInfoMaintainService;
        this.attachmentFileOperateService = attachmentFileOperateService;
    }

    @Override
    public boolean exists(LongIdKey key) throws ServiceException {
        return attachmentFileInfoMaintainService.exists(key);
    }

    @Override
    public AttachmentFileInfo get(LongIdKey key) throws ServiceException {
        return attachmentFileInfoMaintainService.get(key);
    }

    @Override
    public PagedData<AttachmentFileInfo> childForNoteItem(
            LongIdKey noteItemKey, PagingInfo pagingInfo
    ) throws ServiceException {
        return attachmentFileInfoMaintainService.lookup(
                AttachmentFileInfoMaintainService.CHILD_FOR_NOTE_ITEM, new Object[]{noteItemKey}, pagingInfo
        );
    }

    @Override
    public PagedData<AttachmentFileInfo> childForNoteItemInspectedDateDesc(
            LongIdKey noteItemKey, PagingInfo pagingInfo
    ) throws ServiceException {
        return attachmentFileInfoMaintainService.lookup(
                AttachmentFileInfoMaintainService.CHILD_FOR_NOTE_ITEM_INSPECTED_DATE_DESC,
                new Object[]{noteItemKey},
                pagingInfo
        );
    }

    @Override
    public PagedData<AttachmentFileInfo> childForNoteItemOriginNameAsc(
            LongIdKey noteItemKey, PagingInfo pagingInfo
    ) throws ServiceException {
        return attachmentFileInfoMaintainService.lookup(
                AttachmentFileInfoMaintainService.CHILD_FOR_NOTE_ITEM_ORIGIN_NAME_ASC,
                new Object[]{noteItemKey},
                pagingInfo
        );
    }

    @Override
    public PagedData<AttachmentFileInfo> childForNoteItemModifiedDateAsc(
            LongIdKey noteItemKey, PagingInfo pagingInfo
    ) throws ServiceException {
        return attachmentFileInfoMaintainService.lookup(
                AttachmentFileInfoMaintainService.CHILD_FOR_NOTE_ITEM_MODIFIED_DATE_ASC,
                new Object[]{noteItemKey},
                pagingInfo
        );
    }

    @Override
    public AttachmentFile downloadAttachmentFile(StringIdKey userKey, LongIdKey attachmentFileKey)
            throws ServiceException {
        return attachmentFileOperateService.downloadAttachmentFile(userKey, attachmentFileKey);
    }

    @Override
    public void uploadAttachmentFile(StringIdKey userKey, AttachmentFileUploadInfo attachmentFileUploadInfo)
            throws ServiceException {
        attachmentFileOperateService.uploadAttachmentFile(userKey, attachmentFileUploadInfo);
    }

    @Override
    public void removeAttachmentFile(StringIdKey userKey, LongIdKey attachmentFileKey) throws ServiceException {
        attachmentFileOperateService.removeAttachmentFile(userKey, attachmentFileKey);
    }
}
