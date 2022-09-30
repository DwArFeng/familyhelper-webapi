package com.dwarfeng.familyhelper.webapi.stack.service.note;

import com.dwarfeng.familyhelper.note.stack.bean.dto.NoteFile;
import com.dwarfeng.familyhelper.note.stack.bean.dto.NoteFileUploadInfo;
import com.dwarfeng.familyhelper.note.stack.bean.dto.NoteItemCreateInfo;
import com.dwarfeng.familyhelper.note.stack.bean.dto.NoteItemUpdateInfo;
import com.dwarfeng.familyhelper.note.stack.bean.entity.NoteItem;
import com.dwarfeng.familyhelper.webapi.stack.bean.disp.note.DispNoteItem;
import com.dwarfeng.subgrade.stack.bean.dto.PagedData;
import com.dwarfeng.subgrade.stack.bean.dto.PagingInfo;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.bean.key.StringIdKey;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import com.dwarfeng.subgrade.stack.service.Service;

/**
 * 笔记项目响应服务。
 *
 * @author DwArFeng
 * @since 1.0.7
 */
public interface NoteItemResponseService extends Service {

    boolean exists(LongIdKey key) throws ServiceException;

    NoteItem get(LongIdKey key) throws ServiceException;

    PagedData<NoteItem> all(PagingInfo pagingInfo) throws ServiceException;

    PagedData<NoteItem> childForNoteNode(LongIdKey noteNodeKey, PagingInfo pagingInfo)
            throws ServiceException;

    PagedData<NoteItem> childForNoteBookRoot(LongIdKey noteBookKey, PagingInfo pagingInfo)
            throws ServiceException;

    DispNoteItem getDisp(LongIdKey key, StringIdKey inspectAccountKey) throws ServiceException;

    PagedData<DispNoteItem> allDisp(StringIdKey accountKey, PagingInfo pagingInfo) throws ServiceException;

    PagedData<DispNoteItem> childForNoteNodeDisp(
            StringIdKey accountKey, LongIdKey noteNodeKey, PagingInfo pagingInfo
    ) throws ServiceException;

    PagedData<DispNoteItem> childForNoteBookRootDisp(
            StringIdKey accountKey, LongIdKey noteBookKey, PagingInfo pagingInfo
    ) throws ServiceException;

    LongIdKey createNoteItem(StringIdKey userKey, NoteItemCreateInfo noteItemCreateInfo)
            throws ServiceException;

    void updateNoteItem(StringIdKey userKey, NoteItemUpdateInfo noteItemUpdateInfo) throws ServiceException;

    void removeNoteItem(StringIdKey userKey, LongIdKey noteItemKey) throws ServiceException;

    NoteFile downloadNoteFile(StringIdKey userKey, LongIdKey noteItemKey) throws ServiceException;

    void uploadNoteFile(StringIdKey userKey, NoteFileUploadInfo noteFileUploadInfo) throws ServiceException;
}
