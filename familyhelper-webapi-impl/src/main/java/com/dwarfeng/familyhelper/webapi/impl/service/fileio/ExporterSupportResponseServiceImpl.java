package com.dwarfeng.familyhelper.webapi.impl.service.fileio;

import com.dwarfeng.familyhelper.webapi.stack.service.fileio.ExporterSupportResponseService;
import com.dwarfeng.fileio.stack.bean.entity.ExporterSupport;
import com.dwarfeng.fileio.stack.service.ExporterSupportMaintainService;
import com.dwarfeng.subgrade.stack.bean.dto.PagedData;
import com.dwarfeng.subgrade.stack.bean.dto.PagingInfo;
import com.dwarfeng.subgrade.stack.bean.key.StringIdKey;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class ExporterSupportResponseServiceImpl implements ExporterSupportResponseService {

    private final ExporterSupportMaintainService exporterSupportMaintainService;

    public ExporterSupportResponseServiceImpl(
            @Qualifier("fileioExporterSupportMaintainService")
            ExporterSupportMaintainService exporterSupportMaintainService
    ) {
        this.exporterSupportMaintainService = exporterSupportMaintainService;
    }

    @Override
    public boolean exists(StringIdKey key) throws ServiceException {
        return exporterSupportMaintainService.exists(key);
    }

    @Override
    public ExporterSupport get(StringIdKey key) throws ServiceException {
        return exporterSupportMaintainService.get(key);
    }

    @Override
    public PagedData<ExporterSupport> all(PagingInfo pagingInfo) throws ServiceException {
        return exporterSupportMaintainService.lookup(pagingInfo);
    }

    @Override
    public PagedData<ExporterSupport> idLike(String pattern, PagingInfo pagingInfo) throws ServiceException {
        return exporterSupportMaintainService.lookup(
                ExporterSupportMaintainService.ID_LIKE, new Object[]{pattern}, pagingInfo
        );
    }
}
