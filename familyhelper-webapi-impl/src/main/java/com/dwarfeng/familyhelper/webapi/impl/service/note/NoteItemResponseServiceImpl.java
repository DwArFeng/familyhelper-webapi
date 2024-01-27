package com.dwarfeng.familyhelper.webapi.impl.service.note;

import com.dwarfeng.familyhelper.note.stack.bean.dto.NoteFile;
import com.dwarfeng.familyhelper.note.stack.bean.dto.NoteFileUploadInfo;
import com.dwarfeng.familyhelper.note.stack.bean.dto.NoteItemCreateInfo;
import com.dwarfeng.familyhelper.note.stack.bean.dto.NoteItemUpdateInfo;
import com.dwarfeng.familyhelper.note.stack.bean.entity.NoteItem;
import com.dwarfeng.familyhelper.note.stack.service.NoteItemMaintainService;
import com.dwarfeng.familyhelper.note.stack.service.NoteItemOperateService;
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

import java.util.*;

@Service
public class NoteItemResponseServiceImpl implements NoteItemResponseService {

    private final NoteItemMaintainService noteItemMaintainService;
    private final NoteItemOperateService noteItemOperateService;

    private final NoteBookResponseService noteBookResponseService;
    private final NoteNodeResponseService noteNodeResponseService;

    public NoteItemResponseServiceImpl(
            @Qualifier("familyhelperNoteNoteItemMaintainService") NoteItemMaintainService noteItemMaintainService,
            @Qualifier("familyhelperNoteNoteItemOperateService") NoteItemOperateService noteItemOperateService,
            NoteBookResponseService noteBookResponseService,
            NoteNodeResponseService noteNodeResponseService
    ) {
        this.noteItemMaintainService = noteItemMaintainService;
        this.noteItemOperateService = noteItemOperateService;
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
        DispNoteBook noteBook = null;
        if (Objects.nonNull(noteItem.getBookKey())) {
            noteBook = noteBookResponseService.getDisp(noteItem.getBookKey(), inspectAccountKey);
        }
        DispNoteNode noteNode = null;
        if (Objects.nonNull(noteItem.getNodeKey())) {
            noteNode = noteNodeResponseService.getDisp(noteItem.getNodeKey(), inspectAccountKey);
        }

        return DispNoteItem.of(noteItem, noteBook, noteNode);
    }

    private DispNoteItem toDispWithCache(
            NoteItem noteItem, StringIdKey inspectAccountKey,
            Map<LongIdKey, DispNoteBook> cachedNoteBookMap, Map<LongIdKey, DispNoteNode> cachedNoteNodeMap
    ) throws ServiceException {
        DispNoteBook noteBook = toDispNoteBookWithCache(noteItem, inspectAccountKey, cachedNoteBookMap);
        DispNoteNode noteNode = toDispNoteNodeWithCache(noteItem, inspectAccountKey, cachedNoteNodeMap);
        return DispNoteItem.of(noteItem, noteBook, noteNode);
    }

    @SuppressWarnings("DuplicatedCode")
    private DispNoteBook toDispNoteBookWithCache(
            NoteItem noteItem, StringIdKey inspectAccountKey, Map<LongIdKey, DispNoteBook> cachedNoteBookMap
    ) throws ServiceException {
        LongIdKey bookKey = noteItem.getBookKey();
        if (Objects.isNull(bookKey)) {
            return null;
        }
        DispNoteBook noteBook = cachedNoteBookMap.getOrDefault(bookKey, null);
        if (Objects.isNull(noteBook)) {
            noteBook = noteBookResponseService.getDisp(bookKey, inspectAccountKey);
            cachedNoteBookMap.put(bookKey, noteBook);
        }
        return noteBook;
    }

    private DispNoteNode toDispNoteNodeWithCache(
            NoteItem noteItem, StringIdKey inspectAccountKey, Map<LongIdKey, DispNoteNode> cachedNoteNodeMap
    ) throws ServiceException {
        LongIdKey nodeKey = noteItem.getNodeKey();
        if (Objects.isNull(nodeKey)) {
            return null;
        }
        DispNoteNode noteNode = cachedNoteNodeMap.getOrDefault(nodeKey, null);
        if (Objects.isNull(noteNode)) {
            noteNode = noteNodeResponseService.getDisp(nodeKey, inspectAccountKey);
            cachedNoteNodeMap.put(nodeKey, noteNode);
        }
        return noteNode;
    }

    private PagedData<DispNoteItem> toDispPagedData(PagedData<NoteItem> lookup, StringIdKey inspectAccountKey)
            throws ServiceException {
        List<DispNoteItem> dispNoteItems = new ArrayList<>();
        Map<LongIdKey, DispNoteBook> cachedNoteBookMap = new HashMap<>();
        Map<LongIdKey, DispNoteNode> cachedNoteNodeMap = new HashMap<>();
        for (NoteItem noteItem : lookup.getData()) {
            dispNoteItems.add(toDispWithCache(noteItem, inspectAccountKey, cachedNoteBookMap, cachedNoteNodeMap));
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
}
