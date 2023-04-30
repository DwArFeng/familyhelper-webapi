package com.dwarfeng.familyhelper.webapi.impl.service.life;

import com.dwarfeng.familyhelper.life.stack.bean.dto.PbNodeCreateInfo;
import com.dwarfeng.familyhelper.life.stack.bean.dto.PbNodeUpdateInfo;
import com.dwarfeng.familyhelper.life.stack.bean.entity.PbNode;
import com.dwarfeng.familyhelper.life.stack.service.PbItemMaintainService;
import com.dwarfeng.familyhelper.life.stack.service.PbNodeMaintainService;
import com.dwarfeng.familyhelper.life.stack.service.PbNodeOperateService;
import com.dwarfeng.familyhelper.webapi.stack.bean.disp.life.DispPbNode;
import com.dwarfeng.familyhelper.webapi.stack.bean.disp.life.DispPbSet;
import com.dwarfeng.familyhelper.webapi.stack.service.life.PbNodeResponseService;
import com.dwarfeng.familyhelper.webapi.stack.service.life.PbSetResponseService;
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
public class PbNodeResponseServiceImpl implements PbNodeResponseService {

    private final PbNodeMaintainService pbNodeMaintainService;
    private final PbNodeOperateService pbNodeOperateService;
    private final PbItemMaintainService pbItemMaintainService;

    private final PbSetResponseService pbSetResponseService;

    public PbNodeResponseServiceImpl(
            @Qualifier("familyhelperLifePbNodeMaintainService") PbNodeMaintainService pbNodeMaintainService,
            @Qualifier("familyhelperLifePbNodeOperateService") PbNodeOperateService pbNodeOperateService,
            @Qualifier("familyhelperLifePbItemMaintainService") PbItemMaintainService pbItemMaintainService,
            PbSetResponseService pbSetResponseService
    ) {
        this.pbNodeMaintainService = pbNodeMaintainService;
        this.pbNodeOperateService = pbNodeOperateService;
        this.pbItemMaintainService = pbItemMaintainService;
        this.pbSetResponseService = pbSetResponseService;
    }

    @Override
    public boolean exists(LongIdKey key) throws ServiceException {
        return pbNodeMaintainService.exists(key);
    }

    @Override
    public PbNode get(LongIdKey key) throws ServiceException {
        return pbNodeMaintainService.get(key);
    }

    @Override
    public PagedData<PbNode> all(PagingInfo pagingInfo) throws ServiceException {
        return pbNodeMaintainService.lookup(pagingInfo);
    }

    @Override
    public PagedData<PbNode> childForPbSet(LongIdKey pbSetKey, PagingInfo pagingInfo)
            throws ServiceException {
        return pbNodeMaintainService.lookup(
                PbNodeMaintainService.CHILD_FOR_SET, new Object[]{pbSetKey}, pagingInfo
        );
    }

    @Override
    public PagedData<PbNode> childForPbSetRoot(LongIdKey pbSetKey, PagingInfo pagingInfo) throws ServiceException {
        return pbNodeMaintainService.lookup(
                PbNodeMaintainService.CHILD_FOR_SET_ROOT, new Object[]{pbSetKey}, pagingInfo
        );
    }

    @Override
    public PagedData<PbNode> childForParent(LongIdKey parentKey, PagingInfo pagingInfo) throws ServiceException {
        return pbNodeMaintainService.lookup(
                PbNodeMaintainService.CHILD_FOR_PARENT, new Object[]{parentKey}, pagingInfo
        );
    }

    @Override
    public PagedData<PbNode> childForPbSetNameLike(LongIdKey pbSetKey, String pattern, PagingInfo pagingInfo)
            throws ServiceException {
        return pbNodeMaintainService.lookup(
                PbNodeMaintainService.CHILD_FOR_SET_NAME_LIKE, new Object[]{pbSetKey, pattern}, pagingInfo
        );
    }

    @Override
    public DispPbNode getDisp(LongIdKey key, StringIdKey inspectAccountKey) throws ServiceException {
        PbNode pbNode = pbNodeMaintainService.get(key);
        return toDisp(pbNode, inspectAccountKey);
    }

    @Override
    public PagedData<DispPbNode> allDisp(StringIdKey accountKey, PagingInfo pagingInfo) throws ServiceException {
        PagedData<PbNode> lookup = all(pagingInfo);
        return toDispPagedData(lookup, accountKey);
    }

    @Override
    public PagedData<DispPbNode> childForPbSetDisp(
            StringIdKey accountKey, LongIdKey pbSetKey, PagingInfo pagingInfo
    ) throws ServiceException {
        PagedData<PbNode> lookup = childForPbSet(pbSetKey, pagingInfo);
        return toDispPagedData(lookup, accountKey);
    }

    @Override
    public PagedData<DispPbNode> childForPbSetRootDisp(
            StringIdKey accountKey, LongIdKey pbSetKey, PagingInfo pagingInfo
    ) throws ServiceException {
        PagedData<PbNode> lookup = childForPbSetRoot(pbSetKey, pagingInfo);
        return toDispPagedData(lookup, accountKey);
    }

    @Override
    public PagedData<DispPbNode> childForParentDisp(
            StringIdKey accountKey, LongIdKey parentKey, PagingInfo pagingInfo
    ) throws ServiceException {
        PagedData<PbNode> lookup = childForParent(parentKey, pagingInfo);
        return toDispPagedData(lookup, accountKey);
    }

    @Override
    public PagedData<DispPbNode> childForPbSetNameLikeDisp(
            StringIdKey accountKey, LongIdKey pbSetKey, String pattern, PagingInfo pagingInfo
    ) throws ServiceException {
        PagedData<PbNode> lookup = childForPbSetNameLike(pbSetKey, pattern, pagingInfo);
        return toDispPagedData(lookup, accountKey);
    }

    private DispPbNode toDisp(PbNode pbNode, StringIdKey inspectAccountKey)
            throws ServiceException {
        DispPbSet set = null;
        if (Objects.nonNull(pbNode.getSetKey())) {
            set = pbSetResponseService.getDisp(pbNode.getSetKey(), inspectAccountKey);
        }

        boolean hasNoChild = pbNodeMaintainService.lookup(
                PbNodeMaintainService.CHILD_FOR_PARENT, new Object[]{pbNode.getKey()}, new PagingInfo(0, 1)
        ).getCount() <= 0;
        if (hasNoChild) {
            hasNoChild = pbItemMaintainService.lookup(
                    PbItemMaintainService.CHILD_FOR_NODE, new Object[]{pbNode.getKey()}, new PagingInfo(0, 1)
            ).getCount() <= 0;
        }

        return DispPbNode.of(pbNode, set, hasNoChild);
    }

    private PagedData<DispPbNode> toDispPagedData(PagedData<PbNode> lookup, StringIdKey inspectAccountKey)
            throws ServiceException {
        List<DispPbNode> dispPbNodes = new ArrayList<>();
        for (PbNode pbNode : lookup.getData()) {
            dispPbNodes.add(toDisp(pbNode, inspectAccountKey));
        }
        return new PagedData<>(
                lookup.getCurrentPage(), lookup.getTotalPages(), lookup.getRows(), lookup.getCount(), dispPbNodes
        );
    }

    @Override
    public LongIdKey createPbNode(StringIdKey userKey, PbNodeCreateInfo pbNodeCreateInfo) throws
            ServiceException {
        return pbNodeOperateService.createPbNode(userKey, pbNodeCreateInfo);
    }

    @Override
    public void updatePbNode(StringIdKey userKey, PbNodeUpdateInfo pbNodeUpdateInfo) throws ServiceException {
        pbNodeOperateService.updatePbNode(userKey, pbNodeUpdateInfo);
    }

    @Override
    public void removePbNode(StringIdKey userKey, LongIdKey pbNodeKey) throws ServiceException {
        pbNodeOperateService.removePbNode(userKey, pbNodeKey);
    }

    @SuppressWarnings("DuplicatedCode")
    @Override
    public List<LongIdKey> pathFromRoot(LongIdKey key) throws ServiceException {
        // 获取当前的笔记节点。
        PbNode pbNode = pbNodeMaintainService.get(key);

        // 如果当前笔记节点没有父节点，则返回空列表。
        if (Objects.isNull(pbNode.getParentKey())) {
            return Collections.emptyList();
        }

        // 定义结果列表。
        List<LongIdKey> result = new ArrayList<>();

        // 获取当前笔记节点的父节点主键，作为当前节点主键。
        LongIdKey anchorKey = pbNode.getParentKey();

        // 将当前节点主键添加到结果列表中。
        result.add(anchorKey);

        // 循环获取父节点的父节点，直到父节点为空。
        while (Objects.nonNull(anchorKey)) {
            // 获取当前节点。
            pbNode = pbNodeMaintainService.get(anchorKey);

            // 获取当前节点的父节点主键。
            LongIdKey parentKey = pbNode.getParentKey();

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
