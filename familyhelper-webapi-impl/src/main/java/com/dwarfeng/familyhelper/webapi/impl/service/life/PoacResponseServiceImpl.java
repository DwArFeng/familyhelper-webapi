package com.dwarfeng.familyhelper.webapi.impl.service.life;

import com.dwarfeng.familyhelper.life.stack.bean.entity.Poac;
import com.dwarfeng.familyhelper.life.stack.bean.key.PoacKey;
import com.dwarfeng.familyhelper.life.stack.service.PoacMaintainService;
import com.dwarfeng.familyhelper.webapi.stack.bean.life.disp.DispActivity;
import com.dwarfeng.familyhelper.webapi.stack.bean.life.disp.DispPoac;
import com.dwarfeng.familyhelper.webapi.stack.bean.system.disp.DispAccount;
import com.dwarfeng.familyhelper.webapi.stack.service.life.ActivityResponseService;
import com.dwarfeng.familyhelper.webapi.stack.service.life.PoacResponseService;
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

@Service("lifePoacResponseServiceImpl")
public class PoacResponseServiceImpl implements PoacResponseService {

    private final PoacMaintainService poacMaintainService;

    private final ActivityResponseService activityResponseService;
    private final AccountResponseService accountResponseService;

    public PoacResponseServiceImpl(
            @Qualifier("familyhelperLifePoacMaintainService") PoacMaintainService poacMaintainService,
            ActivityResponseService activityResponseService,
            AccountResponseService accountResponseService
    ) {
        this.poacMaintainService = poacMaintainService;
        this.activityResponseService = activityResponseService;
        this.accountResponseService = accountResponseService;
    }

    @Override
    public boolean exists(PoacKey key) throws ServiceException {
        return poacMaintainService.exists(key);
    }

    @Override
    public Poac get(PoacKey key) throws ServiceException {
        return poacMaintainService.get(key);
    }

    @Override
    public DispPoac getDisp(PoacKey key, StringIdKey inspectAccountKey) throws ServiceException {
        Poac poac = poacMaintainService.get(key);
        return dispPoacFromPoac(poac, inspectAccountKey);
    }

    @Override
    public PagedData<Poac> childForActivity(LongIdKey activityKey, PagingInfo pagingInfo) throws ServiceException {
        return poacMaintainService.lookup(
                PoacMaintainService.CHILD_FOR_ACTIVITY, new Object[]{activityKey}, pagingInfo
        );
    }

    @SuppressWarnings("DuplicatedCode")
    @Override
    public PagedData<DispPoac> childForActivityDisp(
            LongIdKey activityKey, PagingInfo pagingInfo, StringIdKey inspectAccountKey
    ) throws ServiceException {
        PagedData<Poac> lookup = poacMaintainService.lookup(
                PoacMaintainService.CHILD_FOR_ACTIVITY, new Object[]{activityKey}, pagingInfo
        );
        List<DispPoac> dispPoacs = new ArrayList<>();
        for (Poac poac : lookup.getData()) {
            dispPoacs.add(dispPoacFromPoac(poac, inspectAccountKey));
        }
        return new PagedData<>(
                lookup.getCurrentPage(), lookup.getTotalPages(), lookup.getRows(), lookup.getCount(), dispPoacs
        );
    }

    private DispPoac dispPoacFromPoac(Poac poac, StringIdKey inspectAccountKey) throws ServiceException {
        DispActivity activity = activityResponseService.getDisp(
                new LongIdKey(poac.getKey().getActivityLongId()), inspectAccountKey
        );
        DispAccount account = accountResponseService.getDisp(
                new StringIdKey(poac.getKey().getUserStringId()), inspectAccountKey
        );
        return DispPoac.of(poac, activity, account);
    }
}
