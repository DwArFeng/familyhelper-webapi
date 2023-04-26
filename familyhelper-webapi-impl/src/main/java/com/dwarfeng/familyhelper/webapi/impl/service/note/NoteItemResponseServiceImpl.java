package com.dwarfeng.familyhelper.webapi.impl.service.note;

import com.dwarfeng.familyhelper.note.stack.bean.dto.NoteFile;
import com.dwarfeng.familyhelper.note.stack.bean.dto.NoteFileUploadInfo;
import com.dwarfeng.familyhelper.note.stack.bean.dto.NoteItemCreateInfo;
import com.dwarfeng.familyhelper.note.stack.bean.dto.NoteItemUpdateInfo;
import com.dwarfeng.familyhelper.note.stack.bean.entity.NoteItem;
import com.dwarfeng.familyhelper.note.stack.bean.entity.NoteNode;
import com.dwarfeng.familyhelper.note.stack.service.NoteItemMaintainService;
import com.dwarfeng.familyhelper.note.stack.service.NoteItemOperateService;
import com.dwarfeng.familyhelper.note.stack.service.NoteNodeMaintainService;
import com.dwarfeng.familyhelper.webapi.stack.bean.disp.note.DispNoteBook;
import com.dwarfeng.familyhelper.webapi.stack.bean.disp.note.DispNoteItem;
import com.dwarfeng.familyhelper.webapi.stack.bean.disp.note.DispNoteNode;
import com.dwarfeng.familyhelper.webapi.stack.service.note.NoteBookResponseService;
import com.dwarfeng.familyhelper.webapi.stack.service.note.NoteItemResponseService;
import com.dwarfeng.familyhelper.webapi.stack.service.note.NoteNodeResponseService;
import com.dwarfeng.subgrade.stack.bean.dto.PagedData;
import com.dwarfeng.subgrade.stack.bean.dto.PagingInfo;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.bean.key.StringIdKey;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Service
public class NoteItemResponseServiceImpl implements NoteItemResponseService {

    private final NoteItemMaintainService noteItemMaintainService;
    private final NoteItemOperateService noteItemOperateService;
    private final NoteNodeMaintainService noteNodeMaintainService;

    private final NoteBookResponseService noteBookResponseService;
    private final NoteNodeResponseService noteNodeResponseService;

    public NoteItemResponseServiceImpl(
            @Qualifier("familyhelperNoteNoteItemMaintainService") NoteItemMaintainService noteItemMaintainService,
            @Qualifier("familyhelperNoteNoteItemOperateService") NoteItemOperateService noteItemOperateService,
            @Qualifier("familyhelperNoteNoteNodeMaintainService") NoteNodeMaintainService noteNodeMaintainService,
            NoteBookResponseService noteBookResponseService,
            NoteNodeResponseService noteNodeResponseService
    ) {
        this.noteItemMaintainService = noteItemMaintainService;
        this.noteItemOperateService = noteItemOperateService;
        this.noteNodeMaintainService = noteNodeMaintainService;
        this.noteBookResponseService = noteBookResponseService;
        this.noteNodeResponseService = noteNodeResponseService;
    }

    @Override
    public boolean exists(LongIdKey key) throws ServiceException {
        return noteItemMaintainService.exists(key);
    }

    @Override
    public NoteItem get(LongIdKey key) throws ServiceException {
        return noteItemMaintainService.get(key);
    }

    @Override
    public PagedData<NoteItem> all(PagingInfo pagingInfo) throws ServiceException {
        return noteItemMaintainService.lookup(pagingInfo);
    }

    @Override
    public PagedData<NoteItem> childForNoteNode(LongIdKey noteNodeKey, PagingInfo pagingInfo)
            throws ServiceException {
        return noteItemMaintainService.lookup(
                NoteItemMaintainService.CHILD_FOR_NODE_INDEX_ASC, new Object[]{noteNodeKey}, pagingInfo
        );
    }

    @Override
    public PagedData<NoteItem> childForNoteBookRoot(LongIdKey noteBookKey, PagingInfo pagingInfo)
            throws ServiceException {
        return noteItemMaintainService.lookup(
                NoteItemMaintainService.CHILD_FOR_BOOK_ROOT_INDEX_ASC, new Object[]{noteBookKey}, pagingInfo
        );
    }

    @Override
    public PagedData<NoteItem> childForNoteBookNameLike(LongIdKey noteBookKey, String pattern, PagingInfo pagingInfo)
            throws ServiceException {
        return noteItemMaintainService.lookup(
                NoteItemMaintainService.CHILD_FOR_BOOK_NAME_LIKE, new Object[]{noteBookKey, pattern}, pagingInfo
        );
    }

    @Override
    public DispNoteItem getDisp(LongIdKey key, StringIdKey inspectAccountKey) throws ServiceException {
        NoteItem noteItem = noteItemMaintainService.get(key);
        return toDisp(noteItem, inspectAccountKey);
    }

    @Override
    public PagedData<DispNoteItem> allDisp(StringIdKey accountKey, PagingInfo pagingInfo) throws ServiceException {
        PagedData<NoteItem> lookup = noteItemMaintainService.lookup(pagingInfo);
        return toDispPagedData(lookup, accountKey);
    }

    @Override
    public PagedData<DispNoteItem> childForNoteNodeDisp(
            StringIdKey accountKey, LongIdKey noteNodeKey, PagingInfo pagingInfo
    ) throws ServiceException {
        PagedData<NoteItem> lookup = childForNoteNode(noteNodeKey, pagingInfo);
        return toDispPagedData(lookup, accountKey);
    }

    @Override
    public PagedData<DispNoteItem> childForNoteBookRootDisp(
            StringIdKey accountKey, LongIdKey noteBookKey, PagingInfo pagingInfo
    ) throws ServiceException {
        PagedData<NoteItem> lookup = childForNoteBookRoot(noteBookKey, pagingInfo);
        return toDispPagedData(lookup, accountKey);
    }

    @Override
    public PagedData<DispNoteItem> childForNoteBookNameLikeDisp(
            StringIdKey accountKey, LongIdKey noteBookKey, String pattern, PagingInfo pagingInfo
    ) throws ServiceException {
        PagedData<NoteItem> lookup = childForNoteBookNameLike(noteBookKey, pattern, pagingInfo);
        return toDispPagedData(lookup, accountKey);
    }

    private DispNoteItem toDisp(NoteItem noteItem, StringIdKey inspectAccountKey) throws ServiceException {
        DispNoteBook book = null;
        if (Objects.nonNull(noteItem.getBookKey())) {
            book = noteBookResponseService.getDisp(noteItem.getBookKey(), inspectAccountKey);
        }
        DispNoteNode node = null;
        if (Objects.nonNull(noteItem.getNodeKey())) {
            node = noteNodeResponseService.getDisp(noteItem.getNodeKey(), inspectAccountKey);
        }

        return DispNoteItem.of(noteItem, book, node);
    }

    private PagedData<DispNoteItem> toDispPagedData(PagedData<NoteItem> lookup, StringIdKey inspectAccountKey)
            throws ServiceException {
        List<DispNoteItem> dispNoteItems = new ArrayList<>();
        for (NoteItem noteItem : lookup.getData()) {
            dispNoteItems.add(toDisp(noteItem, inspectAccountKey));
        }
        return new PagedData<>(
                lookup.getCurrentPage(), lookup.getTotalPages(), lookup.getRows(), lookup.getCount(),
                dispNoteItems
        );
    }

    @Override
    public LongIdKey createNoteItem(StringIdKey userKey, NoteItemCreateInfo noteItemCreateInfo) throws
            ServiceException {
        return noteItemOperateService.createNoteItem(userKey, noteItemCreateInfo);
    }

    @Override
    public void updateNoteItem(StringIdKey userKey, NoteItemUpdateInfo noteItemUpdateInfo) throws ServiceException {
        noteItemOperateService.updateNoteItem(userKey, noteItemUpdateInfo);
    }

    @Override
    public void removeNoteItem(StringIdKey userKey, LongIdKey noteItemKey) throws ServiceException {
        noteItemOperateService.removeNoteItem(userKey, noteItemKey);
    }

    @Override
    public NoteFile downloadNoteFile(StringIdKey userKey, LongIdKey noteItemKey) throws ServiceException {
        return noteItemOperateService.downloadNoteFile(userKey, noteItemKey);
    }

    @Override
    public void uploadNoteFile(StringIdKey userKey, NoteFileUploadInfo noteFileUploadInfo) throws ServiceException {
        noteItemOperateService.uploadNoteFile(userKey, noteFileUploadInfo);
    }

    @Override
    public List<LongIdKey> pathFromRoot(LongIdKey key) throws ServiceException {
        // 获取当前的笔记项目。
        NoteItem noteItem = noteItemMaintainService.get(key);

        // 如果笔记项目没有父节点，则返回空列表。
        if (Objects.isNull(noteItem.getNodeKey())) {
            return Collections.emptyList();
        }

        // 定义结果列表。
        List<LongIdKey> result = new ArrayList<>();

        // 获取当前笔记本项目的节点主键，作为当前节点主键。
        LongIdKey anchorKey = noteItem.getNodeKey();

        // 将当前节点主键添加到结果列表中。
        result.add(anchorKey);

        // 循环获取父节点，直到根节点。
        while (true) {
            // 获取当前节点。
            NoteNode noteNode = noteNodeMaintainService.get(anchorKey);

            // 获取当前节点的父节点主键。
            LongIdKey parentKey = noteNode.getParentKey();

            // 如果当前节点没有父节点，则跳出循环。
            if (Objects.isNull(parentKey)) {
                break;
            }

            // 将父节点主键添加到结果列表中。
            result.add(parentKey);

            // 将父节点主键作为当前节点主键。
            anchorKey = parentKey;
        }

        // 将结果列表反转，并返回。
        Collections.reverse(result);
        return result;
    }
}
