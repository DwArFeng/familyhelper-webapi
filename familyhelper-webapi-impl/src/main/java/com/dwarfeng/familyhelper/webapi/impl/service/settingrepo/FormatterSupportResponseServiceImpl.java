package com.dwarfeng.familyhelper.webapi.impl.service.settingrepo;

import com.dwarfeng.familyhelper.webapi.stack.service.settingrepo.FormatterSupportResponseService;
import com.dwarfeng.settingrepo.stack.bean.entity.FormatterSupport;
import com.dwarfeng.settingrepo.stack.service.FormatterSupportMaintainService;
import com.dwarfeng.subgrade.stack.bean.dto.PagedData;
import com.dwarfeng.subgrade.stack.bean.dto.PagingInfo;
import com.dwarfeng.subgrade.stack.bean.key.StringIdKey;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class FormatterSupportResponseServiceImpl implements FormatterSupportResponseService {

    private final FormatterSupportMaintainService formatterSupportMaintainService;

    public FormatterSupportResponseServiceImpl(
            @Qualifier("settingrepoFormatterSupportMaintainService")
            FormatterSupportMaintainService formatterSupportMaintainService
    ) {
        this.formatterSupportMaintainService = formatterSupportMaintainService;
    }

    @Override
    public boolean exists(StringIdKey key) throws ServiceException {
        return formatterSupportMaintainService.exists(key);
    }

    @Override
    public FormatterSupport get(StringIdKey key) throws ServiceException {
        return formatterSupportMaintainService.get(key);
    }

    @Override
    public PagedData<FormatterSupport> all(PagingInfo pagingInfo) throws ServiceException {
        return formatterSupportMaintainService.lookup(pagingInfo);
    }

    @Override
    public PagedData<FormatterSupport> idLike(String pattern, PagingInfo pagingInfo) throws ServiceException {
        return formatterSupportMaintainService.lookup(
                FormatterSupportMaintainService.ID_LIKE, new Object[]{pattern}, pagingInfo
        );
    }
}
