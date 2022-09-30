package com.dwarfeng.familyhelper.webapi.impl.service.note;

import com.dwarfeng.familyhelper.note.stack.bean.dto.NoteNodeCreateInfo;
import com.dwarfeng.familyhelper.note.stack.bean.dto.NoteNodeUpdateInfo;
import com.dwarfeng.familyhelper.note.stack.bean.entity.NoteNode;
import com.dwarfeng.familyhelper.note.stack.service.NoteItemMaintainService;
import com.dwarfeng.familyhelper.note.stack.service.NoteNodeMaintainService;
import com.dwarfeng.familyhelper.note.stack.service.NoteNodeOperateService;
import com.dwarfeng.familyhelper.webapi.stack.bean.disp.note.DispNoteBook;
import com.dwarfeng.familyhelper.webapi.stack.bean.disp.note.DispNoteNode;
import com.dwarfeng.familyhelper.webapi.stack.service.note.NoteBookResponseService;
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
public class NoteNodeResponseServiceImpl implements NoteNodeResponseService {

    private final NoteNodeMaintainService noteNodeMaintainService;
    private final NoteNodeOperateService noteNodeOperateService;
    private final NoteItemMaintainService noteItemMaintainService;

    private final NoteBookResponseService noteBookResponseService;

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    public NoteNodeResponseServiceImpl(
            @Qualifier("familyhelperNoteNoteNodeMaintainService") NoteNodeMaintainService noteNodeMaintainService,
            @Qualifier("familyhelperNoteNoteNodeOperateService") NoteNodeOperateService noteNodeOperateService,
            @Qualifier("familyhelperNoteNoteItemMaintainService") NoteItemMaintainService noteItemMaintainService,
            NoteBookResponseService noteBookResponseService
    ) {
        this.noteNodeMaintainService = noteNodeMaintainService;
        this.noteNodeOperateService = noteNodeOperateService;
        this.noteItemMaintainService = noteItemMaintainService;
        this.noteBookResponseService = noteBookResponseService;
    }

    @Override
    public boolean exists(LongIdKey key) throws ServiceException {
        return noteNodeMaintainService.exists(key);
    }

    @Override
    public NoteNode get(LongIdKey key) throws ServiceException {
        return noteNodeMaintainService.get(key);
    }

    @Override
    public PagedData<NoteNode> all(PagingInfo pagingInfo) throws ServiceException {
        return noteNodeMaintainService.lookup(pagingInfo);
    }

    @Override
    public PagedData<NoteNode> childForNoteBook(LongIdKey noteBookKey, PagingInfo pagingInfo)
            throws ServiceException {
        return noteNodeMaintainService.lookup(
                NoteNodeMaintainService.CHILD_FOR_BOOK, new Object[]{noteBookKey}, pagingInfo
        );
    }

    @Override
    public PagedData<NoteNode> childForNoteBookRoot(LongIdKey noteBookKey, PagingInfo pagingInfo) throws ServiceException {
        return noteNodeMaintainService.lookup(
                NoteNodeMaintainService.CHILD_FOR_BOOK_ROOT, new Object[]{noteBookKey}, pagingInfo
        );
    }

    @Override
    public PagedData<NoteNode> childForParent(LongIdKey parentKey, PagingInfo pagingInfo) throws ServiceException {
        return noteNodeMaintainService.lookup(
                NoteNodeMaintainService.CHILD_FOR_PARENT, new Object[]{parentKey}, pagingInfo
        );
    }

    @Override
    public DispNoteNode getDisp(LongIdKey key, StringIdKey inspectAccountKey) throws ServiceException {
        NoteNode noteNode = noteNodeMaintainService.get(key);
        return dispNoteNodeFromNoteNode(noteNode, inspectAccountKey);
    }

    @SuppressWarnings("DuplicatedCode")
    @Override
    public PagedData<DispNoteNode> allDisp(StringIdKey accountKey, PagingInfo pagingInfo) throws ServiceException {
        PagedData<NoteNode> lookup = noteNodeMaintainService.lookup(pagingInfo);
        List<DispNoteNode> dispNoteNodes = new ArrayList<>();
        for (NoteNode noteNode : lookup.getData()) {
            dispNoteNodes.add(dispNoteNodeFromNoteNode(noteNode, accountKey));
        }
        return new PagedData<>(
                lookup.getCurrentPage(), lookup.getTotalPages(), lookup.getRows(), lookup.getCount(), dispNoteNodes
        );
    }

    @SuppressWarnings("DuplicatedCode")
    @Override
    public PagedData<DispNoteNode> childForNoteBookDisp(
            StringIdKey accountKey, LongIdKey noteBookKey, PagingInfo pagingInfo
    ) throws ServiceException {
        PagedData<NoteNode> lookup = noteNodeMaintainService.lookup(
                NoteNodeMaintainService.CHILD_FOR_BOOK, new Object[]{noteBookKey}, pagingInfo
        );
        List<DispNoteNode> dispNoteNodes = new ArrayList<>();
        for (NoteNode noteNode : lookup.getData()) {
            dispNoteNodes.add(dispNoteNodeFromNoteNode(noteNode, accountKey));
        }
        return new PagedData<>(
                lookup.getCurrentPage(), lookup.getTotalPages(), lookup.getRows(), lookup.getCount(), dispNoteNodes
        );
    }

    @SuppressWarnings("DuplicatedCode")
    @Override
    public PagedData<DispNoteNode> childForNoteBookRootDisp(
            StringIdKey accountKey, LongIdKey noteBookKey, PagingInfo pagingInfo
    ) throws ServiceException {
        PagedData<NoteNode> lookup = noteNodeMaintainService.lookup(
                NoteNodeMaintainService.CHILD_FOR_BOOK_ROOT, new Object[]{noteBookKey}, pagingInfo
        );
        List<DispNoteNode> dispNoteNodes = new ArrayList<>();
        for (NoteNode noteNode : lookup.getData()) {
            dispNoteNodes.add(dispNoteNodeFromNoteNode(noteNode, accountKey));
        }
        return new PagedData<>(
                lookup.getCurrentPage(), lookup.getTotalPages(), lookup.getRows(), lookup.getCount(), dispNoteNodes
        );
    }

    @SuppressWarnings("DuplicatedCode")
    @Override
    public PagedData<DispNoteNode> childForParentDisp(
            StringIdKey accountKey, LongIdKey parentKey, PagingInfo pagingInfo
    ) throws ServiceException {
        PagedData<NoteNode> lookup = noteNodeMaintainService.lookup(
                NoteNodeMaintainService.CHILD_FOR_PARENT, new Object[]{parentKey}, pagingInfo
        );
        List<DispNoteNode> dispNoteNodes = new ArrayList<>();
        for (NoteNode noteNode : lookup.getData()) {
            dispNoteNodes.add(dispNoteNodeFromNoteNode(noteNode, accountKey));
        }
        return new PagedData<>(
                lookup.getCurrentPage(), lookup.getTotalPages(), lookup.getRows(), lookup.getCount(), dispNoteNodes
        );
    }

    private DispNoteNode dispNoteNodeFromNoteNode(NoteNode noteNode, StringIdKey inspectAccountKey)
            throws ServiceException {
        DispNoteBook set = null;
        if (Objects.nonNull(noteNode.getBookKey())) {
            set = noteBookResponseService.getDisp(noteNode.getBookKey(), inspectAccountKey);
        }

        boolean hasNoChild = noteNodeMaintainService.lookup(
                NoteNodeMaintainService.CHILD_FOR_PARENT, new Object[]{noteNode.getKey()}, new PagingInfo(0, 1)
        ).getCount() <= 0;
        if (hasNoChild) {
            hasNoChild = noteItemMaintainService.lookup(
                    NoteItemMaintainService.CHILD_FOR_NODE, new Object[]{noteNode.getKey()}, new PagingInfo(0, 1)
            ).getCount() <= 0;
        }

        return DispNoteNode.of(noteNode, set, hasNoChild);
    }

    @Override
    public LongIdKey createNoteNode(StringIdKey userKey, NoteNodeCreateInfo noteNodeCreateInfo) throws
            ServiceException {
        return noteNodeOperateService.createNoteNode(userKey, noteNodeCreateInfo);
    }

    @Override
    public void updateNoteNode(StringIdKey userKey, NoteNodeUpdateInfo noteNodeUpdateInfo) throws ServiceException {
        noteNodeOperateService.updateNoteNode(userKey, noteNodeUpdateInfo);
    }

    @Override
    public void removeNoteNode(StringIdKey userKey, LongIdKey noteNodeKey) throws ServiceException {
        noteNodeOperateService.removeNoteNode(userKey, noteNodeKey);
    }
}
