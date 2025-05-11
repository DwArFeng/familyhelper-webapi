package com.dwarfeng.familyhelper.webapi.impl.service.life;

import com.dwarfeng.familyhelper.life.stack.bean.entity.Poad;
import com.dwarfeng.familyhelper.life.stack.bean.key.PoadKey;
import com.dwarfeng.familyhelper.life.stack.service.PoadMaintainService;
import com.dwarfeng.familyhelper.webapi.stack.bean.life.disp.DispActivityDataSet;
import com.dwarfeng.familyhelper.webapi.stack.bean.life.disp.DispPoad;
import com.dwarfeng.familyhelper.webapi.stack.bean.system.disp.DispAccount;
import com.dwarfeng.familyhelper.webapi.stack.service.life.ActivityDataSetResponseService;
import com.dwarfeng.familyhelper.webapi.stack.service.life.PoadResponseService;
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
public class PoadResponseServiceImpl implements PoadResponseService {

    private final PoadMaintainService poadMaintainService;

    private final ActivityDataSetResponseService activityDataSetResponseService;
    private final AccountResponseService accountResponseService;

    public PoadResponseServiceImpl(
            @Qualifier("familyhelperLifePoadMaintainService") PoadMaintainService poadMaintainService,
            ActivityDataSetResponseService activityDataSetResponseService,
            AccountResponseService accountResponseService
    ) {
        this.poadMaintainService = poadMaintainService;
        this.activityDataSetResponseService = activityDataSetResponseService;
        this.accountResponseService = accountResponseService;
    }

    @Override
    public boolean exists(PoadKey key) throws ServiceException {
        return poadMaintainService.exists(key);
    }

    @Override
    public Poad get(PoadKey key) throws ServiceException {
        return poadMaintainService.get(key);
    }

    @Override
    public DispPoad getDisp(PoadKey key, StringIdKey inspectAccountKey) throws ServiceException {
        Poad poad = poadMaintainService.get(key);
        return dispPoadFromPoad(poad, inspectAccountKey);
    }

    @Override
    public PagedData<Poad> childForActivityDataSet(LongIdKey activityDataSetKey, PagingInfo pagingInfo)
            throws ServiceException {
        return poadMaintainService.lookup(
                PoadMaintainService.CHILD_FOR_ACTIVITY_DATA_SET, new Object[]{activityDataSetKey}, pagingInfo
        );
    }

    @Override
    public PagedData<DispPoad> childForActivityDataSetDisp(
            LongIdKey activityDataSetKey, PagingInfo pagingInfo, StringIdKey inspectAccountKey
    ) throws ServiceException {
        PagedData<Poad> lookup = poadMaintainService.lookup(
                PoadMaintainService.CHILD_FOR_ACTIVITY_DATA_SET, new Object[]{activityDataSetKey}, pagingInfo
        );
        List<DispPoad> dispPoads = new ArrayList<>();
        for (Poad poad : lookup.getData()) {
            dispPoads.add(dispPoadFromPoad(poad, inspectAccountKey));
        }
        return new PagedData<>(
                lookup.getCurrentPage(), lookup.getTotalPages(), lookup.getRows(), lookup.getCount(), dispPoads
        );
    }

    private DispPoad dispPoadFromPoad(Poad poad, StringIdKey inspectAccountKey) throws ServiceException {
        DispActivityDataSet activityDataSet = activityDataSetResponseService.getDisp(
                new LongIdKey(poad.getKey().getActivityDataSetLongId()), inspectAccountKey
        );
        DispAccount account = accountResponseService.getDisp(
                new StringIdKey(poad.getKey().getUserStringId()), inspectAccountKey
        );
        return DispPoad.of(poad, activityDataSet, account);
    }
}
