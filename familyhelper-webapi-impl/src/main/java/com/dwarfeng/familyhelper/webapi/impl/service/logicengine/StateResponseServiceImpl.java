package com.dwarfeng.familyhelper.webapi.impl.service.logicengine;

import com.dwarfeng.familyhelper.webapi.stack.bean.logicengine.disp.DispState;
import com.dwarfeng.familyhelper.webapi.stack.service.logicengine.StateResponseService;
import com.dwarfeng.logicengine.stack.bean.entity.Section;
import com.dwarfeng.logicengine.stack.bean.entity.State;
import com.dwarfeng.logicengine.stack.bean.key.StateKey;
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
public class StateResponseServiceImpl implements StateResponseService {

    private final StateMaintainService stateMaintainService;
    private final SectionMaintainService sectionMaintainService;

    public StateResponseServiceImpl(
            @Qualifier("logicengineStateMaintainService") StateMaintainService stateMaintainService,
            @Qualifier("logicengineSectionMaintainService") SectionMaintainService sectionMaintainService
    ) {
        this.stateMaintainService = stateMaintainService;
        this.sectionMaintainService = sectionMaintainService;
    }

    @Override
    public boolean exists(StateKey key) throws ServiceException {
        return stateMaintainService.exists(key);
    }

    @Override
    public State get(StateKey key) throws ServiceException {
        return stateMaintainService.get(key);
    }

    @Override
    public StateKey insert(State state) throws ServiceException {
        return stateMaintainService.insert(state);
    }

    @Override
    public void update(State state) throws ServiceException {
        stateMaintainService.update(state);
    }

    @Override
    public void delete(StateKey key) throws ServiceException {
        stateMaintainService.delete(key);
    }

    @Override
    public PagedData<State> all(PagingInfo pagingInfo) throws ServiceException {
        return stateMaintainService.lookup(pagingInfo);
    }

    @Override
    public PagedData<State> childForSection(LongIdKey sectionKey, PagingInfo pagingInfo) throws ServiceException {
        return stateMaintainService.lookup(
                StateMaintainService.CHILD_FOR_SECTION, new Object[]{sectionKey}, pagingInfo
        );
    }

    @Override
    public DispState getDisp(StateKey key) throws ServiceException {
        State state = stateMaintainService.get(key);
        return toDisp(state);
    }

    @Override
    public PagedData<DispState> allDisp(PagingInfo pagingInfo) throws ServiceException {
        PagedData<State> lookup = stateMaintainService.lookup(pagingInfo);
        return toDispPagedData(lookup);
    }

    @Override
    public PagedData<DispState> childForSectionDisp(LongIdKey sectionKey, PagingInfo pagingInfo)
            throws ServiceException {
        PagedData<State> lookup = stateMaintainService.lookup(
                StateMaintainService.CHILD_FOR_SECTION, new Object[]{sectionKey}, pagingInfo
        );
        return toDispPagedData(lookup);
    }

    private DispState toDisp(State state) throws ServiceException {
        if (Objects.isNull(state)) {
            return null;
        }
        StateKey key = state.getKey();
        LongIdKey sectionKey = Objects.nonNull(key) && Objects.nonNull(key.getSectionLongId())
                ? new LongIdKey(key.getSectionLongId())
                : null;
        Section section = null;
        if (Objects.nonNull(sectionKey)) {
            section = sectionMaintainService.getIfExists(sectionKey);
        }
        return DispState.of(state, section);
    }

    private PagedData<DispState> toDispPagedData(PagedData<State> pagedData) throws ServiceException {
        List<DispState> dispStates = new ArrayList<>(pagedData.getData().size());
        for (State state : pagedData.getData()) {
            dispStates.add(toDisp(state));
        }
        return new PagedData<>(
                pagedData.getCurrentPage(), pagedData.getTotalPages(), pagedData.getRows(), pagedData.getCount(),
                dispStates
        );
    }
}
