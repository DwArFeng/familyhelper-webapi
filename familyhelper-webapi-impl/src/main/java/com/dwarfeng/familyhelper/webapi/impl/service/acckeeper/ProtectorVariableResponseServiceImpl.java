package com.dwarfeng.familyhelper.webapi.impl.service.acckeeper;

import com.dwarfeng.acckeeper.stack.bean.entity.ProtectorInfo;
import com.dwarfeng.acckeeper.stack.bean.entity.ProtectorVariable;
import com.dwarfeng.acckeeper.stack.bean.key.ProtectorVariableKey;
import com.dwarfeng.acckeeper.stack.service.ProtectorInfoMaintainService;
import com.dwarfeng.acckeeper.stack.service.ProtectorVariableMaintainService;
import com.dwarfeng.familyhelper.webapi.stack.bean.acckeeper.disp.DispProtectorVariable;
import com.dwarfeng.familyhelper.webapi.stack.service.acckeeper.ProtectorVariableResponseService;
import com.dwarfeng.subgrade.stack.bean.dto.PagedData;
import com.dwarfeng.subgrade.stack.bean.dto.PagingInfo;
import com.dwarfeng.subgrade.stack.bean.key.StringIdKey;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class ProtectorVariableResponseServiceImpl implements ProtectorVariableResponseService {

    private final ProtectorVariableMaintainService protectorVariableMaintainService;
    private final ProtectorInfoMaintainService protectorInfoMaintainService;

    public ProtectorVariableResponseServiceImpl(
            @Qualifier("acckeeperProtectorVariableMaintainService")
            ProtectorVariableMaintainService protectorVariableMaintainService,
            @Qualifier("acckeeperProtectorInfoMaintainService")
            ProtectorInfoMaintainService protectorInfoMaintainService
    ) {
        this.protectorVariableMaintainService = protectorVariableMaintainService;
        this.protectorInfoMaintainService = protectorInfoMaintainService;
    }

    @Override
    public boolean exists(ProtectorVariableKey key) throws ServiceException {
        return protectorVariableMaintainService.exists(key);
    }

    @Override
    public ProtectorVariable get(ProtectorVariableKey key) throws ServiceException {
        return protectorVariableMaintainService.get(key);
    }

    @Override
    public ProtectorVariableKey insert(ProtectorVariable protectorVariable) throws ServiceException {
        return protectorVariableMaintainService.insert(protectorVariable);
    }

    @Override
    public void update(ProtectorVariable protectorVariable) throws ServiceException {
        protectorVariableMaintainService.update(protectorVariable);
    }

    @Override
    public void delete(ProtectorVariableKey key) throws ServiceException {
        protectorVariableMaintainService.delete(key);
    }

    @Override
    public PagedData<ProtectorVariable> all(PagingInfo pagingInfo) throws ServiceException {
        return protectorVariableMaintainService.lookup(pagingInfo);
    }

    @Override
    public PagedData<ProtectorVariable> childForProtectorInfo(StringIdKey protectorInfoKey, PagingInfo pagingInfo)
            throws ServiceException {
        return protectorVariableMaintainService.lookup(
                ProtectorVariableMaintainService.CHILD_FOR_PROTECTOR_INFO,
                new Object[]{protectorInfoKey},
                pagingInfo
        );
    }

    @Override
    public DispProtectorVariable getDisp(ProtectorVariableKey key) throws ServiceException {
        ProtectorVariable protectorVariable = protectorVariableMaintainService.get(key);
        return toDisp(protectorVariable);
    }

    @Override
    public PagedData<DispProtectorVariable> allDisp(PagingInfo pagingInfo) throws ServiceException {
        PagedData<ProtectorVariable> lookup = protectorVariableMaintainService.lookup(pagingInfo);
        return toDispPagedData(lookup);
    }

    @Override
    public PagedData<DispProtectorVariable> childForProtectorInfoDisp(
            StringIdKey protectorInfoKey, PagingInfo pagingInfo
    ) throws ServiceException {
        PagedData<ProtectorVariable> lookup = protectorVariableMaintainService.lookup(
                ProtectorVariableMaintainService.CHILD_FOR_PROTECTOR_INFO,
                new Object[]{protectorInfoKey},
                pagingInfo
        );
        return toDispPagedData(lookup);
    }

    private DispProtectorVariable toDisp(ProtectorVariable protectorVariable) throws ServiceException {
        if (Objects.isNull(protectorVariable)) {
            return null;
        }
        ProtectorVariableKey key = protectorVariable.getKey();
        StringIdKey protectorInfoKey = Objects.nonNull(key) && Objects.nonNull(key.getProtectorInfoId())
                ? new StringIdKey(key.getProtectorInfoId())
                : null;
        ProtectorInfo protectorInfo = null;
        if (Objects.nonNull(protectorInfoKey)) {
            protectorInfo = protectorInfoMaintainService.getIfExists(protectorInfoKey);
        }
        return DispProtectorVariable.of(protectorVariable, protectorInfo);
    }

    private PagedData<DispProtectorVariable> toDispPagedData(PagedData<ProtectorVariable> pagedData)
            throws ServiceException {
        List<DispProtectorVariable> dispProtectorVariables = new ArrayList<>(pagedData.getData().size());
        for (ProtectorVariable protectorVariable : pagedData.getData()) {
            dispProtectorVariables.add(toDisp(protectorVariable));
        }
        return new PagedData<>(
                pagedData.getCurrentPage(), pagedData.getTotalPages(), pagedData.getRows(), pagedData.getCount(),
                dispProtectorVariables
        );
    }
}
