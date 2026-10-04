package com.dwarfeng.familyhelper.webapi.impl.service.logicengine;

import com.dwarfeng.familyhelper.webapi.stack.bean.logicengine.disp.DispGuarderInfo;
import com.dwarfeng.familyhelper.webapi.stack.service.logicengine.GuarderInfoResponseService;
import com.dwarfeng.logicengine.stack.bean.entity.GuarderInfo;
import com.dwarfeng.logicengine.stack.bean.entity.Section;
import com.dwarfeng.logicengine.stack.bean.entity.State;
import com.dwarfeng.logicengine.stack.bean.key.StateKey;
import com.dwarfeng.logicengine.stack.service.GuarderInfoMaintainService;
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
public class GuarderInfoResponseServiceImpl implements GuarderInfoResponseService {

    private final GuarderInfoMaintainService guarderInfoMaintainService;
    private final SectionMaintainService sectionMaintainService;
    private final StateMaintainService stateMaintainService;

    public GuarderInfoResponseServiceImpl(
            @Qualifier("logicengineGuarderInfoMaintainService") GuarderInfoMaintainService guarderInfoMaintainService,
            @Qualifier("logicengineSectionMaintainService") SectionMaintainService sectionMaintainService,
            @Qualifier("logicengineStateMaintainService") StateMaintainService stateMaintainService
    ) {
        this.guarderInfoMaintainService = guarderInfoMaintainService;
        this.sectionMaintainService = sectionMaintainService;
        this.stateMaintainService = stateMaintainService;
    }

    @Override
    public boolean exists(LongIdKey key) throws ServiceException {
        return guarderInfoMaintainService.exists(key);
    }

    @Override
    public GuarderInfo get(LongIdKey key) throws ServiceException {
        return guarderInfoMaintainService.get(key);
    }

    @Override
    public LongIdKey insert(GuarderInfo guarderInfo) throws ServiceException {
        return guarderInfoMaintainService.insert(guarderInfo);
    }

    @Override
    public void update(GuarderInfo guarderInfo) throws ServiceException {
        guarderInfoMaintainService.update(guarderInfo);
    }

    @Override
    public void delete(LongIdKey key) throws ServiceException {
        guarderInfoMaintainService.delete(key);
    }

    @Override
    public PagedData<GuarderInfo> all(PagingInfo pagingInfo) throws ServiceException {
        return guarderInfoMaintainService.lookup(
                GuarderInfoMaintainService.SECTION_KEY_ASC_INDEX_ASC, new Object[]{}, pagingInfo
        );
    }

    @Override
    public PagedData<GuarderInfo> childForSection(LongIdKey sectionKey, PagingInfo pagingInfo) throws ServiceException {
        return guarderInfoMaintainService.lookup(
                GuarderInfoMaintainService.CHILD_FOR_SECTION_INDEX_ASC, new Object[]{sectionKey}, pagingInfo
        );
    }

    @Override
    public PagedData<GuarderInfo> childForAnchorState(StateKey anchorStateKey, PagingInfo pagingInfo)
            throws ServiceException {
        return guarderInfoMaintainService.lookup(
                GuarderInfoMaintainService.CHILD_FOR_ANCHOR_STATE, new Object[]{anchorStateKey}, pagingInfo
        );
    }

    @Override
    public PagedData<GuarderInfo> childForTargetState(StateKey targetStateKey, PagingInfo pagingInfo)
            throws ServiceException {
        return guarderInfoMaintainService.lookup(
                GuarderInfoMaintainService.CHILD_FOR_TARGET_STATE, new Object[]{targetStateKey}, pagingInfo
        );
    }

    @Override
    public DispGuarderInfo getDisp(LongIdKey key) throws ServiceException {
        GuarderInfo guarderInfo = guarderInfoMaintainService.get(key);
        return toDisp(guarderInfo);
    }

    @Override
    public PagedData<DispGuarderInfo> allDisp(PagingInfo pagingInfo) throws ServiceException {
        PagedData<GuarderInfo> lookup = guarderInfoMaintainService.lookup(
                GuarderInfoMaintainService.SECTION_KEY_ASC_INDEX_ASC, new Object[]{}, pagingInfo
        );
        return toDispPagedData(lookup);
    }

    @Override
    public PagedData<DispGuarderInfo> childForSectionDisp(LongIdKey sectionKey, PagingInfo pagingInfo)
            throws ServiceException {
        PagedData<GuarderInfo> lookup = guarderInfoMaintainService.lookup(
                GuarderInfoMaintainService.CHILD_FOR_SECTION_INDEX_ASC, new Object[]{sectionKey}, pagingInfo
        );
        return toDispPagedData(lookup);
    }

    @Override
    public PagedData<DispGuarderInfo> childForAnchorStateDisp(StateKey anchorStateKey, PagingInfo pagingInfo)
            throws ServiceException {
        PagedData<GuarderInfo> lookup = guarderInfoMaintainService.lookup(
                GuarderInfoMaintainService.CHILD_FOR_ANCHOR_STATE, new Object[]{anchorStateKey}, pagingInfo
        );
        return toDispPagedData(lookup);
    }

    @Override
    public PagedData<DispGuarderInfo> childForTargetStateDisp(StateKey targetStateKey, PagingInfo pagingInfo)
            throws ServiceException {
        PagedData<GuarderInfo> lookup = guarderInfoMaintainService.lookup(
                GuarderInfoMaintainService.CHILD_FOR_TARGET_STATE, new Object[]{targetStateKey}, pagingInfo
        );
        return toDispPagedData(lookup);
    }

    @SuppressWarnings("DuplicatedCode")
    private DispGuarderInfo toDisp(GuarderInfo guarderInfo) throws ServiceException {
        if (Objects.isNull(guarderInfo)) {
            return null;
        }
        LongIdKey sectionKey = guarderInfo.getSectionKey();
        Section section = null;
        if (Objects.nonNull(sectionKey)) {
            section = sectionMaintainService.getIfExists(sectionKey);
        }
        StateKey anchorStateKey = guarderInfo.getAnchorStateKey();
        State anchorState = null;
        if (Objects.nonNull(anchorStateKey)) {
            anchorState = stateMaintainService.getIfExists(anchorStateKey);
        }
        StateKey targetStateKey = guarderInfo.getTargetStateKey();
        State targetState = null;
        if (Objects.nonNull(targetStateKey)) {
            targetState = stateMaintainService.getIfExists(targetStateKey);
        }
        return DispGuarderInfo.of(guarderInfo, section, anchorState, targetState);
    }

    private PagedData<DispGuarderInfo> toDispPagedData(PagedData<GuarderInfo> pagedData) throws ServiceException {
        List<DispGuarderInfo> dispGuarderInfos = new ArrayList<>(pagedData.getData().size());
        for (GuarderInfo guarderInfo : pagedData.getData()) {
            dispGuarderInfos.add(toDisp(guarderInfo));
        }
        return new PagedData<>(
                pagedData.getCurrentPage(), pagedData.getTotalPages(), pagedData.getRows(), pagedData.getCount(),
                dispGuarderInfos
        );
    }
}
