package com.dwarfeng.familyhelper.webapi.impl.service.settingrepo;

import com.dwarfeng.familyhelper.webapi.stack.bean.settingrepo.disp.DispWarmupDriverInfo;
import com.dwarfeng.familyhelper.webapi.stack.service.settingrepo.WarmupDriverInfoResponseService;
import com.dwarfeng.settingrepo.stack.bean.entity.WarmupDriverInfo;
import com.dwarfeng.settingrepo.stack.bean.entity.WarmupSetting;
import com.dwarfeng.settingrepo.stack.service.WarmupDriverInfoMaintainService;
import com.dwarfeng.settingrepo.stack.service.WarmupSettingMaintainService;
import com.dwarfeng.subgrade.stack.bean.dto.PagedData;
import com.dwarfeng.subgrade.stack.bean.dto.PagingInfo;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class WarmupDriverInfoResponseServiceImpl implements WarmupDriverInfoResponseService {

    private final WarmupDriverInfoMaintainService warmupDriverInfoMaintainService;
    private final WarmupSettingMaintainService warmupSettingMaintainService;

    public WarmupDriverInfoResponseServiceImpl(
            @Qualifier("settingrepoWarmupDriverInfoMaintainService")
            WarmupDriverInfoMaintainService warmupDriverInfoMaintainService,
            @Qualifier("settingrepoWarmupSettingMaintainService")
            WarmupSettingMaintainService warmupSettingMaintainService
    ) {
        this.warmupDriverInfoMaintainService = warmupDriverInfoMaintainService;
        this.warmupSettingMaintainService = warmupSettingMaintainService;
    }

    @Override
    public boolean exists(LongIdKey key) throws ServiceException {
        return warmupDriverInfoMaintainService.exists(key);
    }

    @Override
    public WarmupDriverInfo get(LongIdKey key) throws ServiceException {
        return warmupDriverInfoMaintainService.get(key);
    }

    @Override
    public LongIdKey insert(WarmupDriverInfo warmupDriverInfo) throws ServiceException {
        return warmupDriverInfoMaintainService.insert(warmupDriverInfo);
    }

    @Override
    public void update(WarmupDriverInfo warmupDriverInfo) throws ServiceException {
        warmupDriverInfoMaintainService.update(warmupDriverInfo);
    }

    @Override
    public void delete(LongIdKey key) throws ServiceException {
        warmupDriverInfoMaintainService.delete(key);
    }

    @Override
    public PagedData<WarmupDriverInfo> all(PagingInfo pagingInfo) throws ServiceException {
        return warmupDriverInfoMaintainService.lookup(pagingInfo);
    }

    @Override
    public PagedData<WarmupDriverInfo> childForWarmupSetting(LongIdKey warmupSettingKey, PagingInfo pagingInfo)
            throws ServiceException {
        return warmupDriverInfoMaintainService.lookup(
                WarmupDriverInfoMaintainService.CHILD_FOR_WARMUP_SETTING, new Object[]{warmupSettingKey}, pagingInfo
        );
    }

    @Override
    public PagedData<WarmupDriverInfo> childForWarmupSettingEnabled(LongIdKey warmupSettingKey, PagingInfo pagingInfo)
            throws ServiceException {
        return warmupDriverInfoMaintainService.lookup(
                WarmupDriverInfoMaintainService.CHILD_FOR_WARMUP_SETTING_ENABLED, new Object[]{warmupSettingKey},
                pagingInfo
        );
    }

    @Override
    public DispWarmupDriverInfo getDisp(LongIdKey key) throws ServiceException {
        WarmupDriverInfo warmupDriverInfo = warmupDriverInfoMaintainService.get(key);
        return toDisp(warmupDriverInfo);
    }

    @Override
    public PagedData<DispWarmupDriverInfo> allDisp(PagingInfo pagingInfo) throws ServiceException {
        PagedData<WarmupDriverInfo> lookup = warmupDriverInfoMaintainService.lookup(pagingInfo);
        return toDispPagedData(lookup);
    }

    @Override
    public PagedData<DispWarmupDriverInfo> childForWarmupSettingDisp(
            LongIdKey warmupSettingKey, PagingInfo pagingInfo
    ) throws ServiceException {
        PagedData<WarmupDriverInfo> lookup = childForWarmupSetting(warmupSettingKey, pagingInfo);
        return toDispPagedData(lookup);
    }

    @Override
    public PagedData<DispWarmupDriverInfo> childForWarmupSettingEnabledDisp(
            LongIdKey warmupSettingKey, PagingInfo pagingInfo
    ) throws ServiceException {
        PagedData<WarmupDriverInfo> lookup = childForWarmupSettingEnabled(warmupSettingKey, pagingInfo);
        return toDispPagedData(lookup);
    }

    private DispWarmupDriverInfo toDisp(WarmupDriverInfo warmupDriverInfo) throws ServiceException {
        if (Objects.isNull(warmupDriverInfo)) {
            return null;
        }
        LongIdKey warmupSettingKey = warmupDriverInfo.getWarmupSettingKey();
        WarmupSetting warmupSetting = null;
        if (Objects.nonNull(warmupSettingKey)) {
            warmupSetting = warmupSettingMaintainService.getIfExists(warmupSettingKey);
        }
        return DispWarmupDriverInfo.of(warmupDriverInfo, warmupSetting);
    }

    private PagedData<DispWarmupDriverInfo> toDispPagedData(PagedData<WarmupDriverInfo> pagedData)
            throws ServiceException {
        List<DispWarmupDriverInfo> dispWarmupDriverInfos = new ArrayList<>(pagedData.getData().size());
        for (WarmupDriverInfo warmupDriverInfo : pagedData.getData()) {
            dispWarmupDriverInfos.add(toDisp(warmupDriverInfo));
        }
        return new PagedData<>(
                pagedData.getCurrentPage(), pagedData.getTotalPages(), pagedData.getRows(), pagedData.getCount(),
                dispWarmupDriverInfos
        );
    }
}
