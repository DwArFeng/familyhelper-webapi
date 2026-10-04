package com.dwarfeng.familyhelper.webapi.impl.service.logicengine;

import com.dwarfeng.familyhelper.webapi.stack.service.logicengine.SectionResponseService;
import com.dwarfeng.logicengine.stack.bean.entity.Section;
import com.dwarfeng.logicengine.stack.service.SectionMaintainService;
import com.dwarfeng.subgrade.stack.bean.dto.PagedData;
import com.dwarfeng.subgrade.stack.bean.dto.PagingInfo;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class SectionResponseServiceImpl implements SectionResponseService {

    private final SectionMaintainService sectionMaintainService;

    public SectionResponseServiceImpl(
            @Qualifier("logicengineSectionMaintainService") SectionMaintainService sectionMaintainService
    ) {
        this.sectionMaintainService = sectionMaintainService;
    }

    @Override
    public boolean exists(LongIdKey key) throws ServiceException {
        return sectionMaintainService.exists(key);
    }

    @Override
    public Section get(LongIdKey key) throws ServiceException {
        return sectionMaintainService.get(key);
    }

    @Override
    public LongIdKey insert(Section section) throws ServiceException {
        return sectionMaintainService.insert(section);
    }

    @Override
    public void update(Section section) throws ServiceException {
        sectionMaintainService.update(section);
    }

    @Override
    public void delete(LongIdKey key) throws ServiceException {
        sectionMaintainService.delete(key);
    }

    @Override
    public PagedData<Section> all(PagingInfo pagingInfo) throws ServiceException {
        return sectionMaintainService.lookup(pagingInfo);
    }

    @Override
    public PagedData<Section> enabled(PagingInfo pagingInfo) throws ServiceException {
        return sectionMaintainService.lookup(SectionMaintainService.ENABLED, new Object[]{}, pagingInfo);
    }
}
