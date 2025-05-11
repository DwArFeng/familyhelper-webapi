package com.dwarfeng.familyhelper.webapi.impl.service.life;

import com.dwarfeng.familyhelper.life.stack.bean.entity.Poat;
import com.dwarfeng.familyhelper.life.stack.bean.key.PoatKey;
import com.dwarfeng.familyhelper.life.stack.service.PoatMaintainService;
import com.dwarfeng.familyhelper.webapi.stack.bean.life.disp.DispActivityTemplate;
import com.dwarfeng.familyhelper.webapi.stack.bean.life.disp.DispPoat;
import com.dwarfeng.familyhelper.webapi.stack.bean.system.disp.DispAccount;
import com.dwarfeng.familyhelper.webapi.stack.service.life.ActivityTemplateResponseService;
import com.dwarfeng.familyhelper.webapi.stack.service.life.PoatResponseService;
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
public class PoatResponseServiceImpl implements PoatResponseService {

    private final PoatMaintainService poatMaintainService;

    private final ActivityTemplateResponseService activityTemplateResponseService;
    private final AccountResponseService accountResponseService;

    public PoatResponseServiceImpl(
            @Qualifier("familyhelperLifePoatMaintainService") PoatMaintainService poatMaintainService,
            ActivityTemplateResponseService activityTemplateResponseService,
            AccountResponseService accountResponseService
    ) {
        this.poatMaintainService = poatMaintainService;
        this.activityTemplateResponseService = activityTemplateResponseService;
        this.accountResponseService = accountResponseService;
    }

    @Override
    public boolean exists(PoatKey key) throws ServiceException {
        return poatMaintainService.exists(key);
    }

    @Override
    public Poat get(PoatKey key) throws ServiceException {
        return poatMaintainService.get(key);
    }

    @Override
    public DispPoat getDisp(PoatKey key, StringIdKey inspectAccountKey) throws ServiceException {
        Poat poat = poatMaintainService.get(key);
        return dispPoatFromPoat(poat, inspectAccountKey);
    }

    @Override
    public PagedData<Poat> childForActivityTemplate(LongIdKey activityTemplateKey, PagingInfo pagingInfo)
            throws ServiceException {
        return poatMaintainService.lookup(
                PoatMaintainService.CHILD_FOR_ACTIVITY_TEMPLATE, new Object[]{activityTemplateKey}, pagingInfo
        );
    }

    @Override
    public PagedData<DispPoat> childForActivityTemplateDisp(
            LongIdKey activityTemplateKey, PagingInfo pagingInfo, StringIdKey inspectAccountKey
    ) throws ServiceException {
        PagedData<Poat> lookup = poatMaintainService.lookup(
                PoatMaintainService.CHILD_FOR_ACTIVITY_TEMPLATE, new Object[]{activityTemplateKey}, pagingInfo
        );
        List<DispPoat> dispPoats = new ArrayList<>();
        for (Poat poat : lookup.getData()) {
            dispPoats.add(dispPoatFromPoat(poat, inspectAccountKey));
        }
        return new PagedData<>(
                lookup.getCurrentPage(), lookup.getTotalPages(), lookup.getRows(), lookup.getCount(), dispPoats
        );
    }

    private DispPoat dispPoatFromPoat(Poat poat, StringIdKey inspectAccountKey) throws ServiceException {
        DispActivityTemplate activityTemplate = activityTemplateResponseService.getDisp(
                new LongIdKey(poat.getKey().getActivityTemplateLongId()), inspectAccountKey
        );
        DispAccount account = accountResponseService.getDisp(
                new StringIdKey(poat.getKey().getUserStringId()), inspectAccountKey
        );
        return DispPoat.of(poat, activityTemplate, account);
    }
}
