package com.dwarfeng.familyhelper.webapi.impl.service.logicengine;

import com.dwarfeng.familyhelper.webapi.stack.bean.logicengine.disp.DispPerformerInfo;
import com.dwarfeng.familyhelper.webapi.stack.service.logicengine.PerformerInfoResponseService;
import com.dwarfeng.logicengine.stack.bean.entity.PerformerInfo;
import com.dwarfeng.logicengine.stack.bean.entity.Section;
import com.dwarfeng.logicengine.stack.bean.entity.State;
import com.dwarfeng.logicengine.stack.bean.key.StateKey;
import com.dwarfeng.logicengine.stack.service.PerformerInfoMaintainService;
import com.dwarfeng.logicengine.stack.service.SectionMaintainService;
import com.dwarfeng.logicengine.stack.service.StateMaintainService;
import com.dwarfeng.subgrade.stack.bean.dto.PagedData;
import com.dwarfeng.subgrade.stack.bean.dto.PagingInfo;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class PerformerInfoResponseServiceImpl implements PerformerInfoResponseService {

    private final PerformerInfoMaintainService performerInfoMaintainService;
    private final SectionMaintainService sectionMaintainService;
    private final StateMaintainService stateMaintainService;

    public PerformerInfoResponseServiceImpl(
            @Qualifier("logicenginePerformerInfoMaintainService")
            PerformerInfoMaintainService performerInfoMaintainService,
            @Qualifier("logicengineSectionMaintainService") SectionMaintainService sectionMaintainService,
            @Qualifier("logicengineStateMaintainService") StateMaintainService stateMaintainService
    ) {
        this.performerInfoMaintainService = performerInfoMaintainService;
        this.sectionMaintainService = sectionMaintainService;
        this.stateMaintainService = stateMaintainService;
    }

    @Override
    public boolean exists(LongIdKey key) throws ServiceException {
        return performerInfoMaintainService.exists(key);
    }

    @Override
    public PerformerInfo get(LongIdKey key) throws ServiceException {
        return performerInfoMaintainService.get(key);
    }

    @Override
    public LongIdKey insert(PerformerInfo performerInfo) throws ServiceException {
        return performerInfoMaintainService.insert(performerInfo);
    }

    @Override
    public void update(PerformerInfo performerInfo) throws ServiceException {
        performerInfoMaintainService.update(performerInfo);
    }

    @Override
    public void delete(LongIdKey key) throws ServiceException {
        performerInfoMaintainService.delete(key);
    }

    @Override
    public PagedData<PerformerInfo> all(PagingInfo pagingInfo) throws ServiceException {
        return performerInfoMaintainService.lookup(
                PerformerInfoMaintainService.SECTION_KEY_ASC_INDEX_ASC, new Object[]{}, pagingInfo
        );
    }

    @Override
    public PagedData<PerformerInfo> childForSection(LongIdKey sectionKey, PagingInfo pagingInfo)
            throws ServiceException {
        return performerInfoMaintainService.lookup(
                PerformerInfoMaintainService.CHILD_FOR_SECTION_INDEX_ASC, new Object[]{sectionKey}, pagingInfo
        );
    }

    @Override
    public PagedData<PerformerInfo> childForAnchorState(StateKey anchorStateKey, PagingInfo pagingInfo)
            throws ServiceException {
        return performerInfoMaintainService.lookup(
                PerformerInfoMaintainService.CHILD_FOR_ANCHOR_STATE, new Object[]{anchorStateKey}, pagingInfo
        );
    }

    @Override
    public PagedData<PerformerInfo> childForTargetState(StateKey targetStateKey, PagingInfo pagingInfo)
            throws ServiceException {
        return performerInfoMaintainService.lookup(
                PerformerInfoMaintainService.CHILD_FOR_TARGET_STATE, new Object[]{targetStateKey}, pagingInfo
        );
    }

    @Override
    public DispPerformerInfo getDisp(LongIdKey key) throws ServiceException {
        PerformerInfo performerInfo = performerInfoMaintainService.get(key);
        return toDisp(performerInfo);
    }

    @Override
    public PagedData<DispPerformerInfo> allDisp(PagingInfo pagingInfo) throws ServiceException {
        PagedData<PerformerInfo> lookup = performerInfoMaintainService.lookup(
                PerformerInfoMaintainService.SECTION_KEY_ASC_INDEX_ASC, new Object[]{}, pagingInfo
        );
        return toDispPagedData(lookup);
    }

    @Override
    public PagedData<DispPerformerInfo> childForSectionDisp(LongIdKey sectionKey, PagingInfo pagingInfo)
            throws ServiceException {
        PagedData<PerformerInfo> lookup = performerInfoMaintainService.lookup(
                PerformerInfoMaintainService.CHILD_FOR_SECTION_INDEX_ASC, new Object[]{sectionKey}, pagingInfo
        );
        return toDispPagedData(lookup);
    }

    @Override
    public PagedData<DispPerformerInfo> childForAnchorStateDisp(StateKey anchorStateKey, PagingInfo pagingInfo)
            throws ServiceException {
        PagedData<PerformerInfo> lookup = performerInfoMaintainService.lookup(
                PerformerInfoMaintainService.CHILD_FOR_ANCHOR_STATE, new Object[]{anchorStateKey}, pagingInfo
        );
        return toDispPagedData(lookup);
    }

    @Override
    public PagedData<DispPerformerInfo> childForTargetStateDisp(StateKey targetStateKey, PagingInfo pagingInfo)
            throws ServiceException {
        PagedData<PerformerInfo> lookup = performerInfoMaintainService.lookup(
                PerformerInfoMaintainService.CHILD_FOR_TARGET_STATE, new Object[]{targetStateKey}, pagingInfo
        );
        return toDispPagedData(lookup);
    }

    @SuppressWarnings("DuplicatedCode")
    private DispPerformerInfo toDisp(PerformerInfo performerInfo) throws ServiceException {
        if (Objects.isNull(performerInfo)) {
            return null;
        }
        LongIdKey sectionKey = performerInfo.getSectionKey();
        Section section = null;
        if (Objects.nonNull(sectionKey)) {
            section = sectionMaintainService.getIfExists(sectionKey);
        }
        StateKey anchorStateKey = performerInfo.getAnchorStateKey();
        State anchorState = null;
        if (Objects.nonNull(anchorStateKey)) {
            anchorState = stateMaintainService.getIfExists(anchorStateKey);
        }
        StateKey targetStateKey = performerInfo.getTargetStateKey();
        State targetState = null;
        if (Objects.nonNull(targetStateKey)) {
            targetState = stateMaintainService.getIfExists(targetStateKey);
        }
        return DispPerformerInfo.of(performerInfo, section, anchorState, targetState);
    }

    private PagedData<DispPerformerInfo> toDispPagedData(PagedData<PerformerInfo> pagedData) throws ServiceException {
        List<DispPerformerInfo> dispPerformerInfos = new ArrayList<>(pagedData.getData().size());
        for (PerformerInfo performerInfo : pagedData.getData()) {
            dispPerformerInfos.add(toDisp(performerInfo));
        }
        return new PagedData<>(
                pagedData.getCurrentPage(), pagedData.getTotalPages(), pagedData.getRows(), pagedData.getCount(),
                dispPerformerInfos
        );
    }
}
