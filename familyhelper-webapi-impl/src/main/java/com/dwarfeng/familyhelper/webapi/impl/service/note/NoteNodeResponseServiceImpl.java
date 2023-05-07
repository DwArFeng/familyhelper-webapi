package com.dwarfeng.familyhelper.webapi.impl.service.note;

import com.dwarfeng.familyhelper.note.stack.bean.dto.NoteNodeCreateInfo;
import com.dwarfeng.familyhelper.note.stack.bean.dto.NoteNodeUpdateInfo;
import com.dwarfeng.familyhelper.note.stack.bean.entity.NoteItem;
import com.dwarfeng.familyhelper.note.stack.bean.entity.NoteNode;
import com.dwarfeng.familyhelper.note.stack.service.NoteItemMaintainService;
import com.dwarfeng.familyhelper.note.stack.service.NoteNodeMaintainService;
import com.dwarfeng.familyhelper.note.stack.service.NoteNodeOperateService;
import com.dwarfeng.familyhelper.webapi.stack.bean.disp.note.DispNoteBook;
import com.dwarfeng.familyhelper.webapi.stack.bean.disp.note.DispNoteNode;
import com.dwarfeng.familyhelper.webapi.stack.service.note.NoteBookResponseService;
import com.dwarfeng.familyhelper.webapi.stack.service.note.NoteNodeResponseService;
import com.dwarfeng.subgrade.sdk.bean.dto.PagingUtil;
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

    @Override
    public PagedData<NoteNode> nodePathFromRoot(LongIdKey key) throws ServiceException {
        // 获取当前的笔记节点作为锚点。
        NoteNode anchor = noteNodeMaintainService.get(key);

        // 定义结果列表。
        List<NoteNode> result = new ArrayList<>();

        // 如果锚点的父键不为 null，则循环。
        while (Objects.nonNull(anchor.getParentKey())) {
            // 获取锚点的父键对应的权限组。
            anchor = noteNodeMaintainService.get(anchor.getParentKey());
            // 将锚点加入结果列表。
            result.add(anchor);
        }

        // 将结果列表反转。
        Collections.reverse(result);

        // 返回结果。
        return PagingUtil.pagedData(result);
    }

    @Override
    public PagedData<DispNoteNode> nodePathFromRootDisp(StringIdKey accountKey, LongIdKey key) throws ServiceException {
        PagedData<NoteNode> pathFromRoot = nodePathFromRoot(key);
        return toDispPagedData(pathFromRoot, accountKey);
    }

    @Override
    public PagedData<NoteNode> itemPathFromRoot(LongIdKey itemKey) throws ServiceException {
        // 获取当前的笔记项目。
        NoteItem noteItem = noteItemMaintainService.get(itemKey);

        // 如果项目的节点键为 null，则返回空的分页数据。
        if (Objects.isNull(noteItem.getNodeKey())) {
            return PagingUtil.pagedData(Collections.emptyList());
        }

        // 获取当前的个人最佳节点作为锚点。
        NoteNode anchor = noteNodeMaintainService.get(noteItem.getNodeKey());

        // 定义结果列表。
        List<NoteNode> result = new ArrayList<>();
        result.add(anchor);

        // 如果锚点的父键不为 null，则循环。
        while (Objects.nonNull(anchor.getParentKey())) {
            // 获取锚点的父键对应的权限组。
            anchor = noteNodeMaintainService.get(anchor.getParentKey());
            // 将锚点加入结果列表。
            result.add(anchor);
        }

        // 将结果列表反转。
        Collections.reverse(result);

        // 返回结果。
        return PagingUtil.pagedData(result);
    }

    @Override
    public PagedData<DispNoteNode> itemPathFromRootDisp(StringIdKey accountKey, LongIdKey itemKey) throws ServiceException {
        PagedData<NoteNode> pathFromRoot = itemPathFromRoot(itemKey);
        return toDispPagedData(pathFromRoot, accountKey);
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
}
