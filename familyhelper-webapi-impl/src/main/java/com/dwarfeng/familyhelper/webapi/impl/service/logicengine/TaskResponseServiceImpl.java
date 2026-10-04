package com.dwarfeng.familyhelper.webapi.impl.service.logicengine;

import com.dwarfeng.familyhelper.webapi.stack.bean.logicengine.disp.DispTask;
import com.dwarfeng.familyhelper.webapi.stack.service.logicengine.TaskResponseService;
import com.dwarfeng.logicengine.stack.bean.entity.Section;
import com.dwarfeng.logicengine.stack.bean.entity.State;
import com.dwarfeng.logicengine.stack.bean.entity.Task;
import com.dwarfeng.logicengine.stack.bean.key.StateKey;
import com.dwarfeng.logicengine.stack.service.SectionMaintainService;
import com.dwarfeng.logicengine.stack.service.StateMaintainService;
import com.dwarfeng.logicengine.stack.service.TaskMaintainService;
import com.dwarfeng.subgrade.stack.bean.dto.PagedData;
import com.dwarfeng.subgrade.stack.bean.dto.PagingInfo;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service("logicengineTaskResponseServiceImpl")
public class TaskResponseServiceImpl implements TaskResponseService {

    private final TaskMaintainService taskMaintainService;
    private final SectionMaintainService sectionMaintainService;
    private final StateMaintainService stateMaintainService;

    public TaskResponseServiceImpl(
            @Qualifier("logicengineTaskMaintainService") TaskMaintainService taskMaintainService,
            @Qualifier("logicengineSectionMaintainService") SectionMaintainService sectionMaintainService,
            @Qualifier("logicengineStateMaintainService") StateMaintainService stateMaintainService
    ) {
        this.taskMaintainService = taskMaintainService;
        this.sectionMaintainService = sectionMaintainService;
        this.stateMaintainService = stateMaintainService;
    }

    @Override
    public boolean exists(LongIdKey key) throws ServiceException {
        return taskMaintainService.exists(key);
    }

    @Override
    public Task get(LongIdKey key) throws ServiceException {
        return taskMaintainService.get(key);
    }

    @Override
    public PagedData<Task> all(PagingInfo pagingInfo) throws ServiceException {
        return taskMaintainService.lookup(TaskMaintainService.CREATED_DATE_DESC, new Object[0], pagingInfo);
    }

    @Override
    public PagedData<Task> childForSection(LongIdKey sectionKey, PagingInfo pagingInfo) throws ServiceException {
        return taskMaintainService.lookup(
                TaskMaintainService.CHILD_FOR_SECTION, new Object[]{sectionKey}, pagingInfo
        );
    }

    @Override
    public PagedData<Task> childForCurrentState(StateKey currentStateKey, PagingInfo pagingInfo)
            throws ServiceException {
        return taskMaintainService.lookup(
                TaskMaintainService.CHILD_FOR_CURRENT_STATE, new Object[]{currentStateKey}, pagingInfo
        );
    }

    @Override
    public DispTask getDisp(LongIdKey key) throws ServiceException {
        Task task = taskMaintainService.get(key);
        return toDisp(task);
    }

    @Override
    public PagedData<DispTask> allDisp(PagingInfo pagingInfo) throws ServiceException {
        PagedData<Task> lookup = taskMaintainService.lookup(
                TaskMaintainService.CREATED_DATE_DESC, new Object[0], pagingInfo
        );
        return toDispPagedData(lookup);
    }

    @Override
    public PagedData<DispTask> childForSectionDisp(LongIdKey sectionKey, PagingInfo pagingInfo)
            throws ServiceException {
        PagedData<Task> lookup = taskMaintainService.lookup(
                TaskMaintainService.CHILD_FOR_SECTION, new Object[]{sectionKey}, pagingInfo
        );
        return toDispPagedData(lookup);
    }

    @Override
    public PagedData<DispTask> childForCurrentStateDisp(StateKey currentStateKey, PagingInfo pagingInfo)
            throws ServiceException {
        PagedData<Task> lookup = taskMaintainService.lookup(
                TaskMaintainService.CHILD_FOR_CURRENT_STATE, new Object[]{currentStateKey}, pagingInfo
        );
        return toDispPagedData(lookup);
    }

    private DispTask toDisp(Task task) throws ServiceException {
        if (Objects.isNull(task)) {
            return null;
        }
        LongIdKey sectionKey = task.getSectionKey();
        Section section = null;
        if (Objects.nonNull(sectionKey)) {
            section = sectionMaintainService.getIfExists(sectionKey);
        }
        StateKey currentStateKey = task.getCurrentStateKey();
        State currentState = null;
        if (Objects.nonNull(currentStateKey)) {
            currentState = stateMaintainService.getIfExists(currentStateKey);
        }
        return DispTask.of(task, section, currentState);
    }

    private PagedData<DispTask> toDispPagedData(PagedData<Task> pagedData) throws ServiceException {
        List<DispTask> dispTasks = new ArrayList<>(pagedData.getData().size());
        for (Task task : pagedData.getData()) {
            dispTasks.add(toDisp(task));
        }
        return new PagedData<>(
                pagedData.getCurrentPage(), pagedData.getTotalPages(), pagedData.getRows(), pagedData.getCount(),
                dispTasks
        );
    }
}
