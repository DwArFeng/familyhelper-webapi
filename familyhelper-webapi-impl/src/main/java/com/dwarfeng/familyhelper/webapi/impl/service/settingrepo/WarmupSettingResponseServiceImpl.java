package com.dwarfeng.familyhelper.webapi.impl.service.settingrepo;

import com.dwarfeng.familyhelper.webapi.stack.service.settingrepo.WarmupSettingResponseService;
import com.dwarfeng.settingrepo.stack.bean.entity.WarmupSetting;
import com.dwarfeng.settingrepo.stack.service.WarmupSettingMaintainService;
import com.dwarfeng.subgrade.stack.bean.dto.PagedData;
import com.dwarfeng.subgrade.stack.bean.dto.PagingInfo;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class WarmupSettingResponseServiceImpl implements WarmupSettingResponseService {

    private final WarmupSettingMaintainService warmupSettingMaintainService;

    public WarmupSettingResponseServiceImpl(
            @Qualifier("settingrepoWarmupSettingMaintainService")
            WarmupSettingMaintainService warmupSettingMaintainService
    ) {
        this.warmupSettingMaintainService = warmupSettingMaintainService;
    }

    @Override
    public boolean exists(LongIdKey key) throws ServiceException {
        return warmupSettingMaintainService.exists(key);
    }

    @Override
    public WarmupSetting get(LongIdKey key) throws ServiceException {
        return warmupSettingMaintainService.get(key);
    }

    @Override
    public LongIdKey insert(WarmupSetting warmupSetting) throws ServiceException {
        return warmupSettingMaintainService.insert(warmupSetting);
    }

    @Override
    public void update(WarmupSetting warmupSetting) throws ServiceException {
        warmupSettingMaintainService.update(warmupSetting);
    }

    @Override
    public void delete(LongIdKey key) throws ServiceException {
        warmupSettingMaintainService.delete(key);
    }

    @Override
    public PagedData<WarmupSetting> all(PagingInfo pagingInfo) throws ServiceException {
        return warmupSettingMaintainService.lookup(pagingInfo);
    }

    @Override
    public PagedData<WarmupSetting> enabled(PagingInfo pagingInfo) throws ServiceException {
        return warmupSettingMaintainService.lookup(
                WarmupSettingMaintainService.ENABLED, new Object[0], pagingInfo
        );
    }
}
