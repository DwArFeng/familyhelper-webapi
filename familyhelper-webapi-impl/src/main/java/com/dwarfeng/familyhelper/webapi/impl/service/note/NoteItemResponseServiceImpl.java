package com.dwarfeng.familyhelper.webapi.impl.service.note;

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

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class NoteItemResponseServiceImpl implements NoteItemResponseService {

    private final NoteItemMaintainService noteItemMaintainService;
    private final NoteItemOperateService noteItemOperateService;

    private final NoteBookResponseService noteBookResponseService;
    private final NoteNodeResponseService noteNodeResponseService;

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
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
                NoteItemMaintainService.CHILD_FOR_NODE, new Object[]{noteNodeKey}, pagingInfo
        );
    }

    @Override
    public PagedData<NoteItem> childForNoteBookRoot(LongIdKey noteBookKey, PagingInfo pagingInfo) throws ServiceException {
        return noteItemMaintainService.lookup(
                NoteItemMaintainService.CHILD_FOR_BOOK_ROOT, new Object[]{noteBookKey}, pagingInfo
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
        PagedData<NoteItem> lookup = noteItemMaintainService.lookup(
                NoteItemMaintainService.CHILD_FOR_NODE, new Object[]{noteNodeKey}, pagingInfo
        );
        return toDispPagedData(lookup, accountKey);
    }

    @Override
    public PagedData<DispNoteItem> childForNoteBookRootDisp(
            StringIdKey accountKey, LongIdKey noteBookKey, PagingInfo pagingInfo
    ) throws ServiceException {
        PagedData<NoteItem> lookup = noteItemMaintainService.lookup(
                NoteItemMaintainService.CHILD_FOR_BOOK_ROOT, new Object[]{noteBookKey}, pagingInfo
        );
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
}
