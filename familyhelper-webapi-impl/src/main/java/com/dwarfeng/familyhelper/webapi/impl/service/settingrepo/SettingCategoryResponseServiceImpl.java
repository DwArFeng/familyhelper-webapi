package com.dwarfeng.familyhelper.webapi.impl.service.settingrepo;

import com.dwarfeng.familyhelper.webapi.stack.service.settingrepo.SettingCategoryResponseService;
import com.dwarfeng.settingrepo.stack.bean.entity.SettingCategory;
import com.dwarfeng.settingrepo.stack.service.SettingCategoryMaintainService;
import com.dwarfeng.subgrade.stack.bean.dto.PagedData;
import com.dwarfeng.subgrade.stack.bean.dto.PagingInfo;
import com.dwarfeng.subgrade.stack.bean.key.StringIdKey;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class SettingCategoryResponseServiceImpl implements SettingCategoryResponseService {

    private final SettingCategoryMaintainService settingCategoryMaintainService;

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    public SettingCategoryResponseServiceImpl(
            @Qualifier("settingrepoSettingCategoryMaintainService")
                    SettingCategoryMaintainService settingCategoryMaintainService
    ) {
        this.settingCategoryMaintainService = settingCategoryMaintainService;
    }

    @Override
    public boolean exists(StringIdKey key) throws ServiceException {
        return settingCategoryMaintainService.exists(key);
    }

    @Override
    public SettingCategory get(StringIdKey key) throws ServiceException {
        return settingCategoryMaintainService.get(key);
    }

    @Override
    public StringIdKey insert(SettingCategory settingCategory) throws ServiceException {
        return settingCategoryMaintainService.insert(settingCategory);
    }

    @Override
    public void update(SettingCategory settingCategory) throws ServiceException {
        settingCategoryMaintainService.update(settingCategory);
    }

    @Override
    public void delete(StringIdKey key) throws ServiceException {
        settingCategoryMaintainService.delete(key);
    }

    @Override
    public PagedData<SettingCategory> all(PagingInfo pagingInfo) throws ServiceException {
        return settingCategoryMaintainService.lookup(pagingInfo);
    }

    @Override
    public PagedData<SettingCategory> idLike(String pattern, PagingInfo pagingInfo) throws ServiceException {
        return settingCategoryMaintainService.lookup(
                SettingCategoryMaintainService.ID_LIKE, new Object[]{pattern}, pagingInfo
        );
    }
}
