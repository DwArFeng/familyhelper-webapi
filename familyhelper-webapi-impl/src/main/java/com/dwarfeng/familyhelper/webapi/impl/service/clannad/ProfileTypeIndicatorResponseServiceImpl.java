package com.dwarfeng.familyhelper.webapi.impl.service.clannad;

import com.dwarfeng.familyhelper.clannad.stack.bean.entity.ProfileTypeIndicator;
import com.dwarfeng.familyhelper.clannad.stack.bean.key.ProfileTypeIndicatorKey;
import com.dwarfeng.familyhelper.clannad.stack.service.ProfileTypeIndicatorMaintainService;
import com.dwarfeng.familyhelper.webapi.stack.service.clannad.ProfileTypeIndicatorResponseService;
import com.dwarfeng.subgrade.stack.bean.dto.PagedData;
import com.dwarfeng.subgrade.stack.bean.dto.PagingInfo;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class ProfileTypeIndicatorResponseServiceImpl implements ProfileTypeIndicatorResponseService {

    private final ProfileTypeIndicatorMaintainService profileTypeIndicatorMaintainService;

    public ProfileTypeIndicatorResponseServiceImpl(
            @Qualifier("familyhelperClannadProfileTypeIndicatorMaintainService")
                    ProfileTypeIndicatorMaintainService profileTypeIndicatorMaintainService
    ) {
        this.profileTypeIndicatorMaintainService = profileTypeIndicatorMaintainService;
    }

    @Override
    public boolean exists(ProfileTypeIndicatorKey key) throws ServiceException {
        return profileTypeIndicatorMaintainService.exists(key);
    }

    @Override
    public ProfileTypeIndicator get(ProfileTypeIndicatorKey key) throws ServiceException {
        return profileTypeIndicatorMaintainService.get(key);
    }

    @Override
    public ProfileTypeIndicatorKey insert(ProfileTypeIndicator profileTypeIndicator) throws ServiceException {
        return profileTypeIndicatorMaintainService.insert(profileTypeIndicator);
    }

    @Override
    public void update(ProfileTypeIndicator profileTypeIndicator) throws ServiceException {
        profileTypeIndicatorMaintainService.update(profileTypeIndicator);
    }

    @Override
    public void delete(ProfileTypeIndicatorKey key) throws ServiceException {
        profileTypeIndicatorMaintainService.delete(key);
    }

    @Override
    public PagedData<ProfileTypeIndicator> childForCategory(String category, PagingInfo pagingInfo)
            throws ServiceException {
        return profileTypeIndicatorMaintainService.lookup(
                ProfileTypeIndicatorMaintainService.CHILD_FOR_CATEGORY, new Object[]{category}, pagingInfo
        );
    }
}
