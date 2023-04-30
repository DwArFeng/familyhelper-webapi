package com.dwarfeng.familyhelper.webapi.impl.service.life;

import com.dwarfeng.familyhelper.life.stack.bean.dto.PbItemCreateInfo;
import com.dwarfeng.familyhelper.life.stack.bean.dto.PbItemUpdateInfo;
import com.dwarfeng.familyhelper.life.stack.bean.entity.PbItem;
import com.dwarfeng.familyhelper.life.stack.bean.entity.PbNode;
import com.dwarfeng.familyhelper.life.stack.service.PbItemMaintainService;
import com.dwarfeng.familyhelper.life.stack.service.PbItemOperateService;
import com.dwarfeng.familyhelper.life.stack.service.PbNodeMaintainService;
import com.dwarfeng.familyhelper.webapi.stack.bean.disp.life.DispPbItem;
import com.dwarfeng.familyhelper.webapi.stack.bean.disp.life.DispPbNode;
import com.dwarfeng.familyhelper.webapi.stack.service.life.PbItemResponseService;
import com.dwarfeng.familyhelper.webapi.stack.service.life.PbNodeResponseService;
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
public class PbItemResponseServiceImpl implements PbItemResponseService {

    private final PbItemMaintainService pbItemMaintainService;
    private final PbItemOperateService pbItemOperateService;
    private final PbNodeMaintainService pbNodeMaintainService;

    private final PbNodeResponseService pbNodeResponseService;

    public PbItemResponseServiceImpl(
            @Qualifier("familyhelperLifePbItemMaintainService") PbItemMaintainService pbItemMaintainService,
            @Qualifier("familyhelperLifePbItemOperateService") PbItemOperateService pbItemOperateService,
            @Qualifier("familyhelperLifePbNodeMaintainService") PbNodeMaintainService pbNodeMaintainService,
            PbNodeResponseService pbNodeResponseService
    ) {
        this.pbItemMaintainService = pbItemMaintainService;
        this.pbItemOperateService = pbItemOperateService;
        this.pbNodeMaintainService = pbNodeMaintainService;
        this.pbNodeResponseService = pbNodeResponseService;
    }

    @Override
    public boolean exists(LongIdKey key) throws ServiceException {
        return pbItemMaintainService.exists(key);
    }

    @Override
    public PbItem get(LongIdKey key) throws ServiceException {
        return pbItemMaintainService.get(key);
    }

    @Override
    public PagedData<PbItem> all(PagingInfo pagingInfo) throws ServiceException {
        return pbItemMaintainService.lookup(pagingInfo);
    }

    @Override
    public PagedData<PbItem> childForPbNode(LongIdKey pbNodeKey, PagingInfo pagingInfo)
            throws ServiceException {
        return pbItemMaintainService.lookup(
                PbItemMaintainService.CHILD_FOR_NODE, new Object[]{pbNodeKey}, pagingInfo
        );
    }

    @Override
    public PagedData<PbItem> childForPbSetRoot(LongIdKey pbSetKey, PagingInfo pagingInfo) throws ServiceException {
        return pbItemMaintainService.lookup(
                PbItemMaintainService.CHILD_FOR_SET_ROOT, new Object[]{pbSetKey}, pagingInfo
        );
    }

    @Override
    public PagedData<PbItem> childForPbSetNameLike(LongIdKey pbSetKey, String pattern, PagingInfo pagingInfo)
            throws ServiceException {
        return pbItemMaintainService.lookup(
                PbItemMaintainService.CHILD_FOR_SET_NAME_LIKE, new Object[]{pbSetKey, pattern}, pagingInfo
        );
    }

    @Override
    public DispPbItem getDisp(LongIdKey key, StringIdKey inspectAccountKey) throws ServiceException {
        PbItem pbItem = pbItemMaintainService.get(key);
        return toDisp(pbItem, inspectAccountKey);
    }

    @Override
    public PagedData<DispPbItem> allDisp(StringIdKey accountKey, PagingInfo pagingInfo) throws ServiceException {
        PagedData<PbItem> lookup = pbItemMaintainService.lookup(pagingInfo);
        return toDispPagedData(lookup, accountKey);
    }

    @Override
    public PagedData<DispPbItem> childForPbNodeDisp(
            StringIdKey accountKey, LongIdKey pbNodeKey, PagingInfo pagingInfo
    ) throws ServiceException {
        PagedData<PbItem> lookup = childForPbNode(pbNodeKey, pagingInfo);
        return toDispPagedData(lookup, accountKey);
    }

    @Override
    public PagedData<DispPbItem> childForPbSetRootDisp(StringIdKey accountKey, LongIdKey pbSetKey, PagingInfo pagingInfo) throws ServiceException {
        PagedData<PbItem> lookup = childForPbSetRoot(pbSetKey, pagingInfo);
        return toDispPagedData(lookup, accountKey);
    }

    @Override
    public PagedData<DispPbItem> childForPbSetNameLikeDisp(StringIdKey accountKey, LongIdKey pbSetKey, String pattern, PagingInfo pagingInfo) throws ServiceException {
        PagedData<PbItem> lookup = childForPbSetNameLike(pbSetKey, pattern, pagingInfo);
        return toDispPagedData(lookup, accountKey);
    }

    private DispPbItem toDisp(PbItem pbItem, StringIdKey inspectAccountKey) throws ServiceException {
        DispPbNode node = null;
        if (Objects.nonNull(pbItem.getNodeKey())) {
            node = pbNodeResponseService.getDisp(pbItem.getNodeKey(), inspectAccountKey);
        }

        return DispPbItem.of(pbItem, node);
    }

    private PagedData<DispPbItem> toDispPagedData(PagedData<PbItem> lookup, StringIdKey inspectAccountKey)
            throws ServiceException {
        List<DispPbItem> dispPbItems = new ArrayList<>();
        for (PbItem pbItem : lookup.getData()) {
            dispPbItems.add(toDisp(pbItem, inspectAccountKey));
        }
        return new PagedData<>(
                lookup.getCurrentPage(), lookup.getTotalPages(), lookup.getRows(), lookup.getCount(),
                dispPbItems
        );
    }

    @Override
    public LongIdKey createPbItem(StringIdKey userKey, PbItemCreateInfo pbItemCreateInfo) throws
            ServiceException {
        return pbItemOperateService.createPbItem(userKey, pbItemCreateInfo);
    }

    @Override
    public void updatePbItem(StringIdKey userKey, PbItemUpdateInfo pbItemUpdateInfo) throws ServiceException {
        pbItemOperateService.updatePbItem(userKey, pbItemUpdateInfo);
    }

    @Override
    public void removePbItem(StringIdKey userKey, LongIdKey pbItemKey) throws ServiceException {
        pbItemOperateService.removePbItem(userKey, pbItemKey);
    }

    @Override
    public List<LongIdKey> pathFromRoot(LongIdKey key) throws ServiceException {
        // 获取当前的笔记项目。
        PbItem pbItem = pbItemMaintainService.get(key);

        // 如果笔记项目没有父节点，则返回空列表。
        if (Objects.isNull(pbItem.getNodeKey())) {
            return Collections.emptyList();
        }

        // 定义结果列表。
        List<LongIdKey> result = new ArrayList<>();

        // 获取当前笔记本项目的节点主键，作为当前节点主键。
        LongIdKey anchorKey = pbItem.getNodeKey();

        // 将当前节点主键添加到结果列表中。
        result.add(anchorKey);

        // 循环获取父节点，直到根节点。
        while (true) {
            // 获取当前节点。
            PbNode pbNode = pbNodeMaintainService.get(anchorKey);

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
