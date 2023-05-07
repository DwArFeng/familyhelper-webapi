package com.dwarfeng.familyhelper.webapi.stack.service.note;

import com.dwarfeng.familyhelper.note.stack.bean.dto.NoteNodeCreateInfo;
import com.dwarfeng.familyhelper.note.stack.bean.dto.NoteNodeUpdateInfo;
import com.dwarfeng.familyhelper.note.stack.bean.entity.NoteNode;
import com.dwarfeng.familyhelper.webapi.stack.bean.disp.note.DispNoteNode;
import com.dwarfeng.subgrade.stack.bean.dto.PagedData;
import com.dwarfeng.subgrade.stack.bean.dto.PagingInfo;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.bean.key.StringIdKey;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import com.dwarfeng.subgrade.stack.service.Service;

/**
 * 笔记节点响应服务。
 *
 * @author DwArFeng
 * @since 1.0.7
 */
public interface NoteNodeResponseService extends Service {

    boolean exists(LongIdKey key) throws ServiceException;

    NoteNode get(LongIdKey key) throws ServiceException;

    PagedData<NoteNode> all(PagingInfo pagingInfo) throws ServiceException;

    PagedData<NoteNode> childForNoteBook(LongIdKey noteBookKey, PagingInfo pagingInfo)
            throws ServiceException;

    PagedData<NoteNode> childForNoteBookRoot(LongIdKey noteBookKey, PagingInfo pagingInfo) throws ServiceException;

    PagedData<NoteNode> childForParent(LongIdKey parentKey, PagingInfo pagingInfo) throws ServiceException;

    PagedData<NoteNode> childForNoteBookNameLike(LongIdKey noteBookKey, String pattern, PagingInfo pagingInfo)
            throws ServiceException;

    DispNoteNode getDisp(LongIdKey key, StringIdKey inspectAccountKey) throws ServiceException;

    PagedData<DispNoteNode> allDisp(StringIdKey accountKey, PagingInfo pagingInfo) throws ServiceException;

    PagedData<DispNoteNode> childForNoteBookDisp(
            StringIdKey accountKey, LongIdKey noteBookKey, PagingInfo pagingInfo
    ) throws ServiceException;

    PagedData<DispNoteNode> childForNoteBookRootDisp(
            StringIdKey accountKey, LongIdKey noteBookKey, PagingInfo pagingInfo
    ) throws ServiceException;

    PagedData<DispNoteNode> childForParentDisp(
            StringIdKey accountKey, LongIdKey parentKey, PagingInfo pagingInfo
    ) throws ServiceException;

    PagedData<DispNoteNode> childForNoteBookNameLikeDisp(
            StringIdKey accountKey, LongIdKey noteBookKey, String pattern, PagingInfo pagingInfo
    ) throws ServiceException;

    PagedData<NoteNode> nodePathFromRoot(LongIdKey key) throws ServiceException;

    PagedData<DispNoteNode> nodePathFromRootDisp(StringIdKey accountKey, LongIdKey key) throws ServiceException;

    PagedData<NoteNode> itemPathFromRoot(LongIdKey itemKey) throws ServiceException;

    PagedData<DispNoteNode> itemPathFromRootDisp(StringIdKey accountKey, LongIdKey itemKey) throws ServiceException;

    LongIdKey createNoteNode(StringIdKey userKey, NoteNodeCreateInfo noteNodeCreateInfo)
            throws ServiceException;

    void updateNoteNode(StringIdKey userKey, NoteNodeUpdateInfo noteNodeUpdateInfo) throws ServiceException;

    void removeNoteNode(StringIdKey userKey, LongIdKey noteNodeKey) throws ServiceException;
}
