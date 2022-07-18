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
import java.util.List;
import java.util.Objects;

@Service
public class PbNodeResponseServiceImpl implements PbNodeResponseService {

    private final PbNodeMaintainService pbNodeMaintainService;
    private final PbNodeOperateService pbNodeOperateService;
    private final PbItemMaintainService pbItemMaintainService;

    private final PbSetResponseService pbSetResponseService;

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
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
    public DispPbNode getDisp(LongIdKey key, StringIdKey inspectAccountKey) throws ServiceException {
        PbNode pbNode = pbNodeMaintainService.get(key);
        return dispPbNodeFromPbNode(pbNode, inspectAccountKey);
    }

    @SuppressWarnings("DuplicatedCode")
    @Override
    public PagedData<DispPbNode> allDisp(StringIdKey accountKey, PagingInfo pagingInfo) throws ServiceException {
        PagedData<PbNode> lookup = pbNodeMaintainService.lookup(pagingInfo);
        List<DispPbNode> dispPbNodes = new ArrayList<>();
        for (PbNode pbNode : lookup.getData()) {
            dispPbNodes.add(dispPbNodeFromPbNode(pbNode, accountKey));
        }
        return new PagedData<>(
                lookup.getCurrentPage(), lookup.getTotalPages(), lookup.getRows(), lookup.getCount(), dispPbNodes
        );
    }

    @SuppressWarnings("DuplicatedCode")
    @Override
    public PagedData<DispPbNode> childForPbSetDisp(
            StringIdKey accountKey, LongIdKey pbSetKey, PagingInfo pagingInfo
    ) throws ServiceException {
        PagedData<PbNode> lookup = pbNodeMaintainService.lookup(
                PbNodeMaintainService.CHILD_FOR_SET, new Object[]{pbSetKey}, pagingInfo
        );
        List<DispPbNode> dispPbNodes = new ArrayList<>();
        for (PbNode pbNode : lookup.getData()) {
            dispPbNodes.add(dispPbNodeFromPbNode(pbNode, accountKey));
        }
        return new PagedData<>(
                lookup.getCurrentPage(), lookup.getTotalPages(), lookup.getRows(), lookup.getCount(), dispPbNodes
        );
    }

    @SuppressWarnings("DuplicatedCode")
    @Override
    public PagedData<DispPbNode> childForPbSetRootDisp(
            StringIdKey accountKey, LongIdKey pbSetKey, PagingInfo pagingInfo
    ) throws ServiceException {
        PagedData<PbNode> lookup = pbNodeMaintainService.lookup(
                PbNodeMaintainService.CHILD_FOR_SET_ROOT, new Object[]{pbSetKey}, pagingInfo
        );
        List<DispPbNode> dispPbNodes = new ArrayList<>();
        for (PbNode pbNode : lookup.getData()) {
            dispPbNodes.add(dispPbNodeFromPbNode(pbNode, accountKey));
        }
        return new PagedData<>(
                lookup.getCurrentPage(), lookup.getTotalPages(), lookup.getRows(), lookup.getCount(), dispPbNodes
        );
    }

    @SuppressWarnings("DuplicatedCode")
    @Override
    public PagedData<DispPbNode> childForParentDisp(
            StringIdKey accountKey, LongIdKey parentKey, PagingInfo pagingInfo
    ) throws ServiceException {
        PagedData<PbNode> lookup = pbNodeMaintainService.lookup(
                PbNodeMaintainService.CHILD_FOR_PARENT, new Object[]{parentKey}, pagingInfo
        );
        List<DispPbNode> dispPbNodes = new ArrayList<>();
        for (PbNode pbNode : lookup.getData()) {
            dispPbNodes.add(dispPbNodeFromPbNode(pbNode, accountKey));
        }
        return new PagedData<>(
                lookup.getCurrentPage(), lookup.getTotalPages(), lookup.getRows(), lookup.getCount(), dispPbNodes
        );
    }

    private DispPbNode dispPbNodeFromPbNode(PbNode pbNode, StringIdKey inspectAccountKey)
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
