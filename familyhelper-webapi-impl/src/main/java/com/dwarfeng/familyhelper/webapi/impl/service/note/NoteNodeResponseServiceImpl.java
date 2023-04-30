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
import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Service
public class NoteNodeResponseServiceImpl implements NoteNodeResponseService {

    private final NoteNodeMaintainService noteNodeMaintainService;
    private final NoteNodeOperateService noteNodeOperateService;
    private final NoteItemMaintainService noteItemMaintainService;

    private final NoteBookResponseService noteBookResponseService;

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
    public PagedData<NoteNode> childForNoteBookNameLike(LongIdKey noteBookKey, String pattern, PagingInfo pagingInfo)
            throws ServiceException {
        return noteNodeMaintainService.lookup(
                NoteNodeMaintainService.CHILD_FOR_BOOK_NAME_LIKE, new Object[]{noteBookKey, pattern}, pagingInfo
        );
    }

    @Override
    public DispNoteNode getDisp(LongIdKey key, StringIdKey inspectAccountKey) throws ServiceException {
        NoteNode noteNode = noteNodeMaintainService.get(key);
        return toDisp(noteNode, inspectAccountKey);
    }

    @Override
    public PagedData<DispNoteNode> allDisp(StringIdKey accountKey, PagingInfo pagingInfo) throws ServiceException {
        PagedData<NoteNode> lookup = all(pagingInfo);
        return toDispPagedData(lookup, accountKey);
    }

    @Override
    public PagedData<DispNoteNode> childForNoteBookDisp(
            StringIdKey accountKey, LongIdKey noteBookKey, PagingInfo pagingInfo
    ) throws ServiceException {
        PagedData<NoteNode> lookup = childForNoteBook(noteBookKey, pagingInfo);
        return toDispPagedData(lookup, accountKey);
    }

    @Override
    public PagedData<DispNoteNode> childForNoteBookRootDisp(
            StringIdKey accountKey, LongIdKey noteBookKey, PagingInfo pagingInfo
    ) throws ServiceException {
        PagedData<NoteNode> lookup = childForNoteBookRoot(noteBookKey, pagingInfo);
        return toDispPagedData(lookup, accountKey);
    }

    @Override
    public PagedData<DispNoteNode> childForParentDisp(
            StringIdKey accountKey, LongIdKey parentKey, PagingInfo pagingInfo
    ) throws ServiceException {
        PagedData<NoteNode> lookup = childForParent(parentKey, pagingInfo);
        return toDispPagedData(lookup, accountKey);
    }

    @Override
    public PagedData<DispNoteNode> childForNoteBookNameLikeDisp(
            StringIdKey accountKey, LongIdKey noteBookKey, String pattern, PagingInfo pagingInfo
    ) throws ServiceException {
        PagedData<NoteNode> lookup = childForNoteBookNameLike(noteBookKey, pattern, pagingInfo);
        return toDispPagedData(lookup, accountKey);
    }

    private DispNoteNode toDisp(NoteNode noteNode, StringIdKey inspectAccountKey)
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

    private PagedData<DispNoteNode> toDispPagedData(PagedData<NoteNode> lookup, StringIdKey inspectAccountKey)
            throws ServiceException {
        List<DispNoteNode> dispNoteNodes = new ArrayList<>();
        for (NoteNode noteNode : lookup.getData()) {
            dispNoteNodes.add(toDisp(noteNode, inspectAccountKey));
        }
        return new PagedData<>(
                lookup.getCurrentPage(), lookup.getTotalPages(), lookup.getRows(), lookup.getCount(), dispNoteNodes
        );
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

    @SuppressWarnings("DuplicatedCode")
    @Override
    public List<LongIdKey> pathFromRoot(LongIdKey key) throws ServiceException {
        // 获取当前的笔记节点。
        NoteNode noteNode = noteNodeMaintainService.get(key);

        // 如果当前笔记节点没有父节点，则返回空列表。
        if (Objects.isNull(noteNode.getParentKey())) {
            return Collections.emptyList();
        }

        // 定义结果列表。
        List<LongIdKey> result = new ArrayList<>();

        // 获取当前笔记节点的父节点主键，作为当前节点主键。
        LongIdKey anchorKey = noteNode.getParentKey();

        // 将当前节点主键添加到结果列表中。
        result.add(anchorKey);

        // 循环获取父节点的父节点，直到父节点为空。
        while (Objects.nonNull(anchorKey)) {
            // 获取当前节点。
            noteNode = noteNodeMaintainService.get(anchorKey);

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
