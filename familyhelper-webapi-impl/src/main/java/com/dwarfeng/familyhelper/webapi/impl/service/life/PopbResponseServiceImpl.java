package com.dwarfeng.familyhelper.webapi.impl.service.life;

import com.dwarfeng.familyhelper.life.stack.bean.entity.Popb;
import com.dwarfeng.familyhelper.life.stack.bean.key.PopbKey;
import com.dwarfeng.familyhelper.life.stack.service.PopbMaintainService;
import com.dwarfeng.familyhelper.webapi.stack.bean.life.disp.DispPbSet;
import com.dwarfeng.familyhelper.webapi.stack.bean.life.disp.DispPopb;
import com.dwarfeng.familyhelper.webapi.stack.bean.system.disp.DispAccount;
import com.dwarfeng.familyhelper.webapi.stack.service.life.PbSetResponseService;
import com.dwarfeng.familyhelper.webapi.stack.service.life.PopbResponseService;
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
public class PopbResponseServiceImpl implements PopbResponseService {

    private final PopbMaintainService popbMaintainService;

    private final PbSetResponseService pbSetResponseService;
    private final AccountResponseService accountResponseService;

    public PopbResponseServiceImpl(
            @Qualifier("familyhelperLifePopbMaintainService") PopbMaintainService popbMaintainService,
            PbSetResponseService pbSetResponseService,
            AccountResponseService accountResponseService
    ) {
        this.popbMaintainService = popbMaintainService;
        this.pbSetResponseService = pbSetResponseService;
        this.accountResponseService = accountResponseService;
    }

    @Override
    public boolean exists(PopbKey key) throws ServiceException {
        return popbMaintainService.exists(key);
    }

    @Override
    public Popb get(PopbKey key) throws ServiceException {
        return popbMaintainService.get(key);
    }

    @Override
    public DispPopb getDisp(PopbKey key, StringIdKey inspectAccountKey) throws ServiceException {
        Popb popb = popbMaintainService.get(key);
        return dispPopbFromPopb(popb, inspectAccountKey);
    }

    @Override
    public PagedData<Popb> childForPbSet(LongIdKey pbSetKey, PagingInfo pagingInfo)
            throws ServiceException {
        return popbMaintainService.lookup(
                PopbMaintainService.CHILD_FOR_PB_SET, new Object[]{pbSetKey}, pagingInfo
        );
    }

    @Override
    public PagedData<DispPopb> childForPbSetDisp(
            LongIdKey pbSetKey, PagingInfo pagingInfo, StringIdKey inspectAccountKey
    ) throws ServiceException {
        PagedData<Popb> lookup = popbMaintainService.lookup(
                PopbMaintainService.CHILD_FOR_PB_SET, new Object[]{pbSetKey}, pagingInfo
        );
        List<DispPopb> dispPopbs = new ArrayList<>();
        for (Popb popb : lookup.getData()) {
            dispPopbs.add(dispPopbFromPopb(popb, inspectAccountKey));
        }
        return new PagedData<>(
                lookup.getCurrentPage(), lookup.getTotalPages(), lookup.getRows(), lookup.getCount(), dispPopbs
        );
    }

    private DispPopb dispPopbFromPopb(Popb popb, StringIdKey inspectAccountKey) throws ServiceException {
        DispPbSet pbSet = pbSetResponseService.getDisp(
                new LongIdKey(popb.getKey().getPbLongId()), inspectAccountKey
        );
        DispAccount account = accountResponseService.getDisp(
                new StringIdKey(popb.getKey().getUserStringId()), inspectAccountKey
        );
        return DispPopb.of(popb, pbSet, account);
    }
}
