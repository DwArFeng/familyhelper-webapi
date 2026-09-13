package com.dwarfeng.familyhelper.webapi.impl.service.fileio;

import com.dwarfeng.familyhelper.webapi.stack.service.fileio.ImporterSupportResponseService;
import com.dwarfeng.fileio.stack.bean.entity.ImporterSupport;
import com.dwarfeng.fileio.stack.service.ImporterSupportMaintainService;
import com.dwarfeng.subgrade.stack.bean.dto.PagedData;
import com.dwarfeng.subgrade.stack.bean.dto.PagingInfo;
import com.dwarfeng.subgrade.stack.bean.key.StringIdKey;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class ImporterSupportResponseServiceImpl implements ImporterSupportResponseService {

    private final ImporterSupportMaintainService importerSupportMaintainService;

    public ImporterSupportResponseServiceImpl(
            @Qualifier("fileioImporterSupportMaintainService")
            ImporterSupportMaintainService importerSupportMaintainService
    ) {
        this.importerSupportMaintainService = importerSupportMaintainService;
    }

    @Override
    public boolean exists(StringIdKey key) throws ServiceException {
        return importerSupportMaintainService.exists(key);
    }

    @Override
    public ImporterSupport get(StringIdKey key) throws ServiceException {
        return importerSupportMaintainService.get(key);
    }

    @Override
    public PagedData<ImporterSupport> all(PagingInfo pagingInfo) throws ServiceException {
        return importerSupportMaintainService.lookup(pagingInfo);
    }

    @Override
    public PagedData<ImporterSupport> idLike(String pattern, PagingInfo pagingInfo) throws ServiceException {
        return importerSupportMaintainService.lookup(
                ImporterSupportMaintainService.ID_LIKE, new Object[]{pattern}, pagingInfo
        );
    }
}
