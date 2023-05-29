package com.dwarfeng.familyhelper.webapi.impl.service.life;

import com.dwarfeng.familyhelper.life.stack.bean.entity.Poatac;
import com.dwarfeng.familyhelper.life.stack.bean.key.PoatacKey;
import com.dwarfeng.familyhelper.life.stack.service.PoatacMaintainService;
import com.dwarfeng.familyhelper.webapi.stack.bean.disp.life.DispActivityTemplate;
import com.dwarfeng.familyhelper.webapi.stack.bean.disp.life.DispPoatac;
import com.dwarfeng.familyhelper.webapi.stack.bean.disp.system.DispAccount;
import com.dwarfeng.familyhelper.webapi.stack.service.life.ActivityTemplateResponseService;
import com.dwarfeng.familyhelper.webapi.stack.service.life.PoatacResponseService;
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
public class PoatacResponseServiceImpl implements PoatacResponseService {

    private final PoatacMaintainService poatacMaintainService;

    private final ActivityTemplateResponseService activityTemplateResponseService;
    private final AccountResponseService accountResponseService;

    public PoatacResponseServiceImpl(
            @Qualifier("familyhelperLifePoatacMaintainService") PoatacMaintainService poatacMaintainService,
            ActivityTemplateResponseService activityTemplateResponseService,
            AccountResponseService accountResponseService
    ) {
        this.poatacMaintainService = poatacMaintainService;
        this.activityTemplateResponseService = activityTemplateResponseService;
        this.accountResponseService = accountResponseService;
    }

    @Override
    public boolean exists(PoatacKey key) throws ServiceException {
        return poatacMaintainService.exists(key);
    }

    @Override
    public Poatac get(PoatacKey key) throws ServiceException {
        return poatacMaintainService.get(key);
    }

    @Override
    public DispPoatac getDisp(PoatacKey key, StringIdKey inspectAccountKey) throws ServiceException {
        Poatac poatac = poatacMaintainService.get(key);
        return dispPoatacFromPoatac(poatac, inspectAccountKey);
    }

    @Override
    public PagedData<Poatac> childForActivityTemplate(LongIdKey activityTemplateKey, PagingInfo pagingInfo)
            throws ServiceException {
        return poatacMaintainService.lookup(
                PoatacMaintainService.CHILD_FOR_ACTIVITY_TEMPLATE, new Object[]{activityTemplateKey}, pagingInfo
        );
    }

    @Override
    public PagedData<DispPoatac> childForActivityTemplateDisp(
            LongIdKey activityTemplateKey, PagingInfo pagingInfo, StringIdKey inspectAccountKey
    ) throws ServiceException {
        PagedData<Poatac> lookup = poatacMaintainService.lookup(
                PoatacMaintainService.CHILD_FOR_ACTIVITY_TEMPLATE, new Object[]{activityTemplateKey}, pagingInfo
        );
        List<DispPoatac> dispPoatacs = new ArrayList<>();
        for (Poatac poatac : lookup.getData()) {
            dispPoatacs.add(dispPoatacFromPoatac(poatac, inspectAccountKey));
        }
        return new PagedData<>(
                lookup.getCurrentPage(), lookup.getTotalPages(), lookup.getRows(), lookup.getCount(), dispPoatacs
        );
    }

    private DispPoatac dispPoatacFromPoatac(Poatac poatac, StringIdKey inspectAccountKey) throws ServiceException {
        DispActivityTemplate activityTemplate = activityTemplateResponseService.getDisp(
                new LongIdKey(poatac.getKey().getActivityTemplateLongId()), inspectAccountKey
        );
        DispAccount account = accountResponseService.getDisp(
                new StringIdKey(poatac.getKey().getUserStringId()), inspectAccountKey
        );
        return DispPoatac.of(poatac, activityTemplate, account);
    }
}
