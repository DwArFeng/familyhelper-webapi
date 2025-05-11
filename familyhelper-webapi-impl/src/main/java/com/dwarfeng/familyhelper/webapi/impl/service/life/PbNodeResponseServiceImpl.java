package com.dwarfeng.familyhelper.webapi.impl.service.life;

import com.dwarfeng.familyhelper.life.stack.bean.dto.PbNodeCreateInfo;
import com.dwarfeng.familyhelper.life.stack.bean.dto.PbNodeUpdateInfo;
import com.dwarfeng.familyhelper.life.stack.bean.entity.PbItem;
import com.dwarfeng.familyhelper.life.stack.bean.entity.PbNode;
import com.dwarfeng.familyhelper.life.stack.service.PbItemMaintainService;
import com.dwarfeng.familyhelper.life.stack.service.PbNodeMaintainService;
import com.dwarfeng.familyhelper.life.stack.service.PbNodeOperateService;
import com.dwarfeng.familyhelper.webapi.stack.bean.life.disp.DispPbNode;
import com.dwarfeng.familyhelper.webapi.stack.bean.life.disp.DispPbSet;
import com.dwarfeng.familyhelper.webapi.stack.service.life.PbNodeResponseService;
import com.dwarfeng.familyhelper.webapi.stack.service.life.PbSetResponseService;
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

    @Override
    public PagedData<PbNode> nodePathFromRoot(LongIdKey key) throws ServiceException {
        // 获取当前的个人最佳节点作为锚点。
        PbNode anchor = pbNodeMaintainService.get(key);

        // 定义结果列表。
        List<PbNode> result = new ArrayList<>();

        // 如果锚点的父键不为 null，则循环。
        while (Objects.nonNull(anchor.getParentKey())) {
            // 获取锚点的父键对应的权限组。
            anchor = pbNodeMaintainService.get(anchor.getParentKey());
            // 将锚点加入结果列表。
            result.add(anchor);
        }

        // 将结果列表反转。
        Collections.reverse(result);

        // 返回结果。
        return PagingUtil.pagedData(result);
    }

    @Override
    public PagedData<DispPbNode> nodePathFromRootDisp(StringIdKey accountKey, LongIdKey key) throws ServiceException {
        PagedData<PbNode> pathFromRoot = nodePathFromRoot(key);
        return toDispPagedData(pathFromRoot, accountKey);
    }

    @Override
    public PagedData<PbNode> itemPathFromRoot(LongIdKey itemKey) throws ServiceException {
        // 获取当前的个人最佳项目。
        PbItem pbItem = pbItemMaintainService.get(itemKey);

        // 如果项目的节点键为 null，则返回空的分页数据。
        if (Objects.isNull(pbItem.getNodeKey())) {
            return PagingUtil.pagedData(Collections.emptyList());
        }

        // 获取当前的个人最佳节点作为锚点。
        PbNode anchor = pbNodeMaintainService.get(pbItem.getNodeKey());

        // 定义结果列表。
        List<PbNode> result = new ArrayList<>();
        result.add(anchor);

        // 如果锚点的父键不为 null，则循环。
        while (Objects.nonNull(anchor.getParentKey())) {
            // 获取锚点的父键对应的权限组。
            anchor = pbNodeMaintainService.get(anchor.getParentKey());
            // 将锚点加入结果列表。
            result.add(anchor);
        }

        // 将结果列表反转。
        Collections.reverse(result);

        // 返回结果。
        return PagingUtil.pagedData(result);
    }

    @Override
    public PagedData<DispPbNode> itemPathFromRootDisp(StringIdKey accountKey, LongIdKey itemKey) throws ServiceException {
        PagedData<PbNode> pathFromRoot = itemPathFromRoot(itemKey);
        return toDispPagedData(pathFromRoot, accountKey);
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
}
