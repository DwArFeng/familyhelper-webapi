package com.dwarfeng.familyhelper.webapi.impl.service.audit;

import com.dwarfeng.audit.sdk.util.Constants;
import com.dwarfeng.audit.stack.bean.dto.InspectorVariableInspectInfo;
import com.dwarfeng.audit.stack.bean.dto.InspectorVariableInspectResult;
import com.dwarfeng.audit.stack.bean.dto.InspectorVariableRemoveInfo;
import com.dwarfeng.audit.stack.bean.entity.InspectorInfo;
import com.dwarfeng.audit.stack.bean.entity.InspectorVariable;
import com.dwarfeng.audit.stack.bean.key.InspectorVariableKey;
import com.dwarfeng.audit.stack.service.InspectorInfoMaintainService;
import com.dwarfeng.audit.stack.service.InspectorVariableMaintainService;
import com.dwarfeng.audit.stack.service.InspectorVariableOperateService;
import com.dwarfeng.familyhelper.webapi.stack.bean.audit.disp.DispInspectorVariable;
import com.dwarfeng.familyhelper.webapi.stack.bean.audit.dto.InspectorVariableUpsertInfo;
import com.dwarfeng.familyhelper.webapi.stack.service.audit.InspectorVariableResponseService;
import com.dwarfeng.subgrade.stack.bean.dto.PagedData;
import com.dwarfeng.subgrade.stack.bean.dto.PagingInfo;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Service
public class InspectorVariableResponseServiceImpl implements InspectorVariableResponseService {

    private final InspectorVariableMaintainService inspectorVariableMaintainService;
    private final InspectorVariableOperateService inspectorVariableOperateService;
    private final InspectorInfoMaintainService inspectorInfoMaintainService;

    public InspectorVariableResponseServiceImpl(
            @Qualifier("auditInspectorVariableMaintainService")
            InspectorVariableMaintainService inspectorVariableMaintainService,
            @Qualifier("auditInspectorVariableOperateService")
            InspectorVariableOperateService inspectorVariableOperateService,
            @Qualifier("auditInspectorInfoMaintainService") InspectorInfoMaintainService inspectorInfoMaintainService
    ) {
        this.inspectorVariableMaintainService = inspectorVariableMaintainService;
        this.inspectorVariableOperateService = inspectorVariableOperateService;
        this.inspectorInfoMaintainService = inspectorInfoMaintainService;
    }

    @Override
    public boolean exists(InspectorVariableKey key) throws ServiceException {
        return inspectorVariableMaintainService.exists(key);
    }

    @Override
    public InspectorVariable get(InspectorVariableKey key) throws ServiceException {
        return inspectorVariableMaintainService.get(key);
    }

    @Override
    public PagedData<InspectorVariable> all(PagingInfo pagingInfo) throws ServiceException {
        return inspectorVariableMaintainService.lookup(pagingInfo);
    }

    @Override
    public PagedData<InspectorVariable> childForInspectorInfo(LongIdKey inspectorInfoKey, PagingInfo pagingInfo)
            throws ServiceException {
        return inspectorVariableMaintainService.lookup(
                InspectorVariableMaintainService.CHILD_FOR_INSPECTOR_INFO,
                new Object[]{inspectorInfoKey},
                pagingInfo
        );
    }

    @Override
    public DispInspectorVariable getDisp(InspectorVariableKey key) throws ServiceException {
        InspectorVariable inspectorVariable = inspectorVariableMaintainService.get(key);
        return toDisp(inspectorVariable);
    }

    @Override
    public PagedData<DispInspectorVariable> allDisp(PagingInfo pagingInfo) throws ServiceException {
        PagedData<InspectorVariable> lookup = inspectorVariableMaintainService.lookup(pagingInfo);
        return toDispPagedData(lookup);
    }

    @Override
    public PagedData<DispInspectorVariable> childForInspectorInfoDisp(
            LongIdKey inspectorInfoKey, PagingInfo pagingInfo
    ) throws ServiceException {
        PagedData<InspectorVariable> lookup = inspectorVariableMaintainService.lookup(
                InspectorVariableMaintainService.CHILD_FOR_INSPECTOR_INFO,
                new Object[]{inspectorInfoKey},
                pagingInfo
        );
        return toDispPagedData(lookup);
    }

    @Override
    public InspectorVariableInspectResult inspect(InspectorVariableInspectInfo info) throws ServiceException {
        return inspectorVariableOperateService.inspect(info);
    }

    @Override
    public void upsert(InspectorVariableUpsertInfo info) throws ServiceException {
        LongIdKey inspectorInfoKey = info.getInspectorInfoKey();
        String inspectorVariableId = info.getInspectorVariableId();
        int valueType = info.getValueType();
        String valueString = info.getValueString();

        Object value;
        switch (valueType) {
            case Constants.INSPECTOR_VARIABLE_VALUE_TYPE_LONG:
                value = Long.parseLong(valueString);
                break;
            case Constants.INSPECTOR_VARIABLE_VALUE_TYPE_DOUBLE:
                value = Double.parseDouble(valueString);
                break;
            case Constants.INSPECTOR_VARIABLE_VALUE_TYPE_BOOLEAN:
                value = Boolean.parseBoolean(valueString);
                break;
            case Constants.INSPECTOR_VARIABLE_VALUE_TYPE_DATE:
                value = new Date(Long.parseLong(valueString));
                break;
            default:
                value = valueString;
        }

        inspectorVariableOperateService.upsert(
                new com.dwarfeng.audit.stack.bean.dto.InspectorVariableUpsertInfo(
                        inspectorInfoKey,
                        inspectorVariableId,
                        valueType,
                        value
                )
        );
    }

    @Override
    public void remove(InspectorVariableRemoveInfo info) throws ServiceException {
        inspectorVariableOperateService.remove(info);
    }

    private DispInspectorVariable toDisp(InspectorVariable inspectorVariable) throws ServiceException {
        if (Objects.isNull(inspectorVariable)) {
            return null;
        }
        InspectorVariableKey key = inspectorVariable.getKey();
        LongIdKey inspectorInfoKey = Objects.nonNull(key) && Objects.nonNull(key.getInspectorInfoLongId())
                ? new LongIdKey(key.getInspectorInfoLongId())
                : null;
        InspectorInfo inspectorInfo = null;
        if (Objects.nonNull(inspectorInfoKey)) {
            inspectorInfo = inspectorInfoMaintainService.getIfExists(inspectorInfoKey);
        }
        return DispInspectorVariable.of(inspectorVariable, inspectorInfo);
    }

    private PagedData<DispInspectorVariable> toDispPagedData(PagedData<InspectorVariable> pagedData)
            throws ServiceException {
        List<DispInspectorVariable> dispInspectorVariables = new ArrayList<>(pagedData.getData().size());
        for (InspectorVariable inspectorVariable : pagedData.getData()) {
            dispInspectorVariables.add(toDisp(inspectorVariable));
        }
        return new PagedData<>(
                pagedData.getCurrentPage(), pagedData.getTotalPages(), pagedData.getRows(), pagedData.getCount(),
                dispInspectorVariables
        );
    }
}
