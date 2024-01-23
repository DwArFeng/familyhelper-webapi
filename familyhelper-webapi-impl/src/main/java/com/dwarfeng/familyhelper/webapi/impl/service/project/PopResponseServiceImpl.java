package com.dwarfeng.familyhelper.webapi.impl.service.project;

import com.dwarfeng.familyhelper.project.stack.bean.entity.Pop;
import com.dwarfeng.familyhelper.project.stack.bean.key.PopKey;
import com.dwarfeng.familyhelper.project.stack.service.PopMaintainService;
import com.dwarfeng.familyhelper.webapi.stack.bean.disp.project.DispPop;
import com.dwarfeng.familyhelper.webapi.stack.bean.disp.project.DispProject;
import com.dwarfeng.familyhelper.webapi.stack.bean.disp.system.DispAccount;
import com.dwarfeng.familyhelper.webapi.stack.service.project.PopResponseService;
import com.dwarfeng.familyhelper.webapi.stack.service.project.ProjectResponseService;
import com.dwarfeng.familyhelper.webapi.stack.service.system.AccountResponseService;
import com.dwarfeng.subgrade.stack.bean.dto.PagedData;
import com.dwarfeng.subgrade.stack.bean.dto.PagingInfo;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.bean.key.StringIdKey;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PopResponseServiceImpl implements PopResponseService {

    private final PopMaintainService popMaintainService;

    private final ProjectResponseService projectResponseService;
    private final AccountResponseService accountResponseService;

    public PopResponseServiceImpl(
            @Qualifier("familyhelperProjectPopMaintainService") PopMaintainService popMaintainService,
            ProjectResponseService projectResponseService,
            AccountResponseService accountResponseService
    ) {
        this.popMaintainService = popMaintainService;
        this.projectResponseService = projectResponseService;
        this.accountResponseService = accountResponseService;
    }

    @Override
    public boolean exists(PopKey key) throws ServiceException {
        return popMaintainService.exists(key);
    }

    @Override
    public Pop get(PopKey key) throws ServiceException {
        return popMaintainService.get(key);
    }

    @Override
    public DispPop getDisp(PopKey key, StringIdKey inspectAccountKey) throws ServiceException {
        Pop pop = popMaintainService.get(key);
        return dispPopFromPop(pop, inspectAccountKey);
    }

    @Override
    public PagedData<Pop> childForProject(LongIdKey projectKey, PagingInfo pagingInfo)
            throws ServiceException {
        return popMaintainService.lookup(
                PopMaintainService.CHILD_FOR_PROJECT, new Object[]{projectKey}, pagingInfo
        );
    }

    @Override
    public PagedData<DispPop> childForProjectDisp(
            LongIdKey projectKey, PagingInfo pagingInfo, StringIdKey inspectAccountKey
    ) throws ServiceException {
        PagedData<Pop> lookup = popMaintainService.lookup(
                PopMaintainService.CHILD_FOR_PROJECT, new Object[]{projectKey}, pagingInfo
        );
        List<DispPop> dispPops = new ArrayList<>();
        for (Pop pop : lookup.getData()) {
            dispPops.add(dispPopFromPop(pop, inspectAccountKey));
        }
        return new PagedData<>(
                lookup.getCurrentPage(), lookup.getTotalPages(), lookup.getRows(), lookup.getCount(), dispPops
        );
    }

    private DispPop dispPopFromPop(Pop pop, StringIdKey inspectAccountKey) throws ServiceException {
        DispProject project = projectResponseService.getDisp(
                new LongIdKey(pop.getKey().getLongId()), inspectAccountKey
        );
        DispAccount account = accountResponseService.getDisp(
                new StringIdKey(pop.getKey().getStringId()), inspectAccountKey
        );
        return DispPop.of(pop, project, account);
    }
}
