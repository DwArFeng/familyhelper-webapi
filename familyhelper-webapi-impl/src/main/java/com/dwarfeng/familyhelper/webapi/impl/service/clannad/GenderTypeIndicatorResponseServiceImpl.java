package com.dwarfeng.familyhelper.webapi.impl.service.clannad;

import com.dwarfeng.familyhelper.clannad.stack.bean.entity.GenderTypeIndicator;
import com.dwarfeng.familyhelper.clannad.stack.service.GenderTypeIndicatorMaintainService;
import com.dwarfeng.familyhelper.webapi.stack.service.clannad.GenderTypeIndicatorResponseService;
import com.dwarfeng.subgrade.stack.bean.dto.PagedData;
import com.dwarfeng.subgrade.stack.bean.dto.PagingInfo;
import com.dwarfeng.subgrade.stack.bean.key.StringIdKey;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
@SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
public class GenderTypeIndicatorResponseServiceImpl implements GenderTypeIndicatorResponseService {

    private final GenderTypeIndicatorMaintainService genderTypeIndicatorMaintainService;

    public GenderTypeIndicatorResponseServiceImpl(
            @Qualifier("familyhelperClannadGenderTypeIndicatorMaintainService")
                    GenderTypeIndicatorMaintainService genderTypeIndicatorMaintainService
    ) {
        this.genderTypeIndicatorMaintainService = genderTypeIndicatorMaintainService;
    }

    @Override
    public boolean exists(StringIdKey key) throws ServiceException {
        return genderTypeIndicatorMaintainService.exists(key);
    }

    @Override
    public GenderTypeIndicator get(StringIdKey key) throws ServiceException {
        return genderTypeIndicatorMaintainService.get(key);
    }

    @Override
    public StringIdKey insert(GenderTypeIndicator genderTypeIndicator) throws ServiceException {
        return genderTypeIndicatorMaintainService.insert(genderTypeIndicator);
    }

    @Override
    public void update(GenderTypeIndicator genderTypeIndicator) throws ServiceException {
        genderTypeIndicatorMaintainService.update(genderTypeIndicator);
    }

    @Override
    public void delete(StringIdKey key) throws ServiceException {
        genderTypeIndicatorMaintainService.delete(key);
    }

    @Override
    public PagedData<GenderTypeIndicator> all(PagingInfo pagingInfo) throws ServiceException {
        return genderTypeIndicatorMaintainService.lookup(pagingInfo);
    }
}
