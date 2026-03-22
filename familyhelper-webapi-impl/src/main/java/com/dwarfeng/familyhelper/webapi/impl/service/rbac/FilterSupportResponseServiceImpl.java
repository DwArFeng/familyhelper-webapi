package com.dwarfeng.familyhelper.webapi.impl.service.rbac;

import com.dwarfeng.familyhelper.webapi.stack.service.rbac.FilterSupportResponseService;
import com.dwarfeng.rbacds.stack.bean.entity.FilterSupport;
import com.dwarfeng.rbacds.stack.service.FilterSupportMaintainService;
import com.dwarfeng.subgrade.stack.bean.dto.PagedData;
import com.dwarfeng.subgrade.stack.bean.dto.PagingInfo;
import com.dwarfeng.subgrade.stack.bean.key.StringIdKey;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class FilterSupportResponseServiceImpl implements FilterSupportResponseService {

    private final FilterSupportMaintainService filterSupportMaintainService;

    public FilterSupportResponseServiceImpl(
            @Qualifier("rbacFilterSupportMaintainService") FilterSupportMaintainService filterSupportMaintainService
    ) {
        this.filterSupportMaintainService = filterSupportMaintainService;
    }

    @Override
    public boolean exists(StringIdKey key) throws ServiceException {
        return filterSupportMaintainService.exists(key);
    }

    @Override
    public FilterSupport get(StringIdKey key) throws ServiceException {
        return filterSupportMaintainService.get(key);
    }

    @Override
    public PagedData<FilterSupport> all(PagingInfo pagingInfo) throws ServiceException {
        return filterSupportMaintainService.lookup(pagingInfo);
    }

    @Override
    public PagedData<FilterSupport> idLike(String pattern, PagingInfo pagingInfo) throws ServiceException {
        return filterSupportMaintainService.lookup(
                FilterSupportMaintainService.ID_LIKE,
                new Object[]{pattern},
                pagingInfo
        );
    }

    @Override
    public PagedData<FilterSupport> labelLike(String pattern, PagingInfo pagingInfo) throws ServiceException {
        return filterSupportMaintainService.lookup(
                FilterSupportMaintainService.LABEL_LIKE,
                new Object[]{pattern},
                pagingInfo
        );
    }
}
