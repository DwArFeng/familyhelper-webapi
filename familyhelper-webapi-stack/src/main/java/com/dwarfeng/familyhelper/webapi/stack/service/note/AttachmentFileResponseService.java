package com.dwarfeng.familyhelper.webapi.stack.service.note;

import com.dwarfeng.familyhelper.note.stack.bean.dto.AttachmentFile;
import com.dwarfeng.familyhelper.note.stack.bean.dto.AttachmentFileUpdateInfo;
import com.dwarfeng.familyhelper.note.stack.bean.dto.AttachmentFileUploadInfo;
import com.dwarfeng.familyhelper.note.stack.bean.entity.AttachmentFileInfo;
import com.dwarfeng.subgrade.stack.bean.dto.PagedData;
import com.dwarfeng.subgrade.stack.bean.dto.PagingInfo;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.bean.key.StringIdKey;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import com.dwarfeng.subgrade.stack.service.Service;

/**
 * 附件文件响应服务。
 *
 * @author DwArFeng
 * @since 1.0.7
 */
public interface AttachmentFileResponseService extends Service {

    boolean exists(LongIdKey key) throws ServiceException;

    AttachmentFileInfo get(LongIdKey key) throws ServiceException;

    PagedData<AttachmentFileInfo> childForNoteItem(LongIdKey noteItemKey, PagingInfo pagingInfo)
            throws ServiceException;

    PagedData<AttachmentFileInfo> childForNoteItemInspectedDateDesc(LongIdKey noteItemKey, PagingInfo pagingInfo)
            throws ServiceException;

    PagedData<AttachmentFileInfo> childForNoteItemModifiedDateDesc(LongIdKey noteItemKey, PagingInfo pagingInfo)
            throws ServiceException;

    PagedData<AttachmentFileInfo> childForNoteItemOriginNameAsc(LongIdKey noteItemKey, PagingInfo pagingInfo)
            throws ServiceException;

    PagedData<AttachmentFileInfo> childForNoteItemCreatedDateAsc(LongIdKey noteItemKey, PagingInfo pagingInfo)
            throws ServiceException;

    AttachmentFile downloadAttachmentFile(StringIdKey userKey, LongIdKey attachmentFileKey) throws ServiceException;

    void uploadAttachmentFile(StringIdKey userKey, AttachmentFileUploadInfo attachmentFileUploadInfo)
            throws ServiceException;

    void updateAttachmentFile(StringIdKey userKey, AttachmentFileUpdateInfo attachmentFileUpdateInfo)
            throws ServiceException;

    void removeAttachmentFile(StringIdKey userKey, LongIdKey attachmentFileKey) throws ServiceException;
}
