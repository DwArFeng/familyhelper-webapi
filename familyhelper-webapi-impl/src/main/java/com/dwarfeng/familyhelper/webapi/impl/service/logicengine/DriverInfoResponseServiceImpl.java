package com.dwarfeng.familyhelper.webapi.impl.service.logicengine;

import com.dwarfeng.familyhelper.webapi.stack.bean.logicengine.disp.DispDriverInfo;
import com.dwarfeng.familyhelper.webapi.stack.service.logicengine.DriverInfoResponseService;
import com.dwarfeng.logicengine.stack.bean.entity.DriverInfo;
import com.dwarfeng.logicengine.stack.bean.entity.Section;
import com.dwarfeng.logicengine.stack.service.DriverInfoMaintainService;
import com.dwarfeng.logicengine.stack.service.SectionMaintainService;
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
public class DriverInfoResponseServiceImpl implements DriverInfoResponseService {

    private final DriverInfoMaintainService driverInfoMaintainService;
    private final SectionMaintainService sectionMaintainService;

    public DriverInfoResponseServiceImpl(
            @Qualifier("logicengineDriverInfoMaintainService") DriverInfoMaintainService driverInfoMaintainService,
            @Qualifier("logicengineSectionMaintainService") SectionMaintainService sectionMaintainService
    ) {
        this.driverInfoMaintainService = driverInfoMaintainService;
        this.sectionMaintainService = sectionMaintainService;
    }

    @Override
    public boolean exists(LongIdKey key) throws ServiceException {
        return driverInfoMaintainService.exists(key);
    }

    @Override
    public DriverInfo get(LongIdKey key) throws ServiceException {
        return driverInfoMaintainService.get(key);
    }

    @Override
    public LongIdKey insert(DriverInfo driverInfo) throws ServiceException {
        return driverInfoMaintainService.insert(driverInfo);
    }

    @Override
    public void update(DriverInfo driverInfo) throws ServiceException {
        driverInfoMaintainService.update(driverInfo);
    }

    @Override
    public void delete(LongIdKey key) throws ServiceException {
        driverInfoMaintainService.delete(key);
    }

    @Override
    public PagedData<DriverInfo> all(PagingInfo pagingInfo) throws ServiceException {
        return driverInfoMaintainService.lookup(pagingInfo);
    }

    @Override
    public PagedData<DriverInfo> childForSection(LongIdKey sectionKey, PagingInfo pagingInfo) throws ServiceException {
        return driverInfoMaintainService.lookup(
                DriverInfoMaintainService.CHILD_FOR_SECTION, new Object[]{sectionKey}, pagingInfo
        );
    }

    @Override
    public PagedData<DriverInfo> childForSectionEnabled(LongIdKey sectionKey, PagingInfo pagingInfo)
            throws ServiceException {
        return driverInfoMaintainService.lookup(
                DriverInfoMaintainService.CHILD_FOR_SECTION_ENABLED, new Object[]{sectionKey}, pagingInfo
        );
    }

    @Override
    public DispDriverInfo getDisp(LongIdKey key) throws ServiceException {
        DriverInfo driverInfo = driverInfoMaintainService.get(key);
        return toDisp(driverInfo);
    }

    @Override
    public PagedData<DispDriverInfo> allDisp(PagingInfo pagingInfo) throws ServiceException {
        PagedData<DriverInfo> lookup = driverInfoMaintainService.lookup(pagingInfo);
        return toDispPagedData(lookup);
    }

    @Override
    public PagedData<DispDriverInfo> childForSectionDisp(LongIdKey sectionKey, PagingInfo pagingInfo)
            throws ServiceException {
        PagedData<DriverInfo> lookup = driverInfoMaintainService.lookup(
                DriverInfoMaintainService.CHILD_FOR_SECTION, new Object[]{sectionKey}, pagingInfo
        );
        return toDispPagedData(lookup);
    }

    @Override
    public PagedData<DispDriverInfo> childForSectionEnabledDisp(LongIdKey sectionKey, PagingInfo pagingInfo)
            throws ServiceException {
        PagedData<DriverInfo> lookup = driverInfoMaintainService.lookup(
                DriverInfoMaintainService.CHILD_FOR_SECTION_ENABLED, new Object[]{sectionKey}, pagingInfo
        );
        return toDispPagedData(lookup);
    }

    private DispDriverInfo toDisp(DriverInfo driverInfo) throws ServiceException {
        if (Objects.isNull(driverInfo)) {
            return null;
        }
        LongIdKey sectionKey = driverInfo.getSectionKey();
        Section section = null;
        if (Objects.nonNull(sectionKey)) {
            section = sectionMaintainService.getIfExists(sectionKey);
        }
        return DispDriverInfo.of(driverInfo, section);
    }

    private PagedData<DispDriverInfo> toDispPagedData(PagedData<DriverInfo> pagedData) throws ServiceException {
        List<DispDriverInfo> dispDriverInfos = new ArrayList<>(pagedData.getData().size());
        for (DriverInfo driverInfo : pagedData.getData()) {
            dispDriverInfos.add(toDisp(driverInfo));
        }
        return new PagedData<>(
                pagedData.getCurrentPage(), pagedData.getTotalPages(), pagedData.getRows(), pagedData.getCount(),
                dispDriverInfos
        );
    }
}
