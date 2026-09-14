package com.dwarfeng.familyhelper.webapi.impl.service.audit;

import com.dwarfeng.audit.stack.bean.entity.AuditCategory;
import com.dwarfeng.audit.stack.bean.entity.AuditPropertyIndicator;
import com.dwarfeng.audit.stack.bean.key.AuditPropertyIndicatorKey;
import com.dwarfeng.audit.stack.service.AuditCategoryMaintainService;
import com.dwarfeng.audit.stack.service.AuditPropertyIndicatorMaintainService;
import com.dwarfeng.familyhelper.webapi.stack.bean.audit.disp.DispAuditPropertyIndicator;
import com.dwarfeng.familyhelper.webapi.stack.service.audit.AuditPropertyIndicatorResponseService;
import com.dwarfeng.subgrade.stack.bean.dto.PagedData;
import com.dwarfeng.subgrade.stack.bean.dto.PagingInfo;
import com.dwarfeng.subgrade.stack.bean.key.StringIdKey;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class AuditPropertyIndicatorResponseServiceImpl implements AuditPropertyIndicatorResponseService {

    private final AuditPropertyIndicatorMaintainService auditPropertyIndicatorMaintainService;
    private final AuditCategoryMaintainService auditCategoryMaintainService;

    public AuditPropertyIndicatorResponseServiceImpl(
            @Qualifier("auditAuditPropertyIndicatorMaintainService")
            AuditPropertyIndicatorMaintainService auditPropertyIndicatorMaintainService,
            @Qualifier("auditAuditCategoryMaintainService") AuditCategoryMaintainService auditCategoryMaintainService
    ) {
        this.auditPropertyIndicatorMaintainService = auditPropertyIndicatorMaintainService;
        this.auditCategoryMaintainService = auditCategoryMaintainService;
    }

    @Override
    public boolean exists(AuditPropertyIndicatorKey key) throws ServiceException {
        return auditPropertyIndicatorMaintainService.exists(key);
    }

    @Override
    public AuditPropertyIndicator get(AuditPropertyIndicatorKey key) throws ServiceException {
        return auditPropertyIndicatorMaintainService.get(key);
    }

    @Override
    public AuditPropertyIndicatorKey insert(AuditPropertyIndicator auditPropertyIndicator) throws ServiceException {
        return auditPropertyIndicatorMaintainService.insert(auditPropertyIndicator);
    }

    @Override
    public void update(AuditPropertyIndicator auditPropertyIndicator) throws ServiceException {
        auditPropertyIndicatorMaintainService.update(auditPropertyIndicator);
    }

    @Override
    public void delete(AuditPropertyIndicatorKey key) throws ServiceException {
        auditPropertyIndicatorMaintainService.delete(key);
    }

    @Override
    public PagedData<AuditPropertyIndicator> all(PagingInfo pagingInfo) throws ServiceException {
        return auditPropertyIndicatorMaintainService.lookup(pagingInfo);
    }

    @Override
    public PagedData<AuditPropertyIndicator> childForAuditCategory(
            StringIdKey auditCategoryKey, PagingInfo pagingInfo
    ) throws ServiceException {
        return auditPropertyIndicatorMaintainService.lookup(
                AuditPropertyIndicatorMaintainService.CHILD_FOR_AUDIT_CATEGORY,
                new Object[]{auditCategoryKey},
                pagingInfo
        );
    }

    @Override
    public DispAuditPropertyIndicator getDisp(AuditPropertyIndicatorKey key) throws ServiceException {
        AuditPropertyIndicator auditPropertyIndicator = auditPropertyIndicatorMaintainService.get(key);
        return toDisp(auditPropertyIndicator);
    }

    @Override
    public PagedData<DispAuditPropertyIndicator> allDisp(PagingInfo pagingInfo) throws ServiceException {
        PagedData<AuditPropertyIndicator> lookup = auditPropertyIndicatorMaintainService.lookup(pagingInfo);
        return toDispPagedData(lookup);
    }

    @Override
    public PagedData<DispAuditPropertyIndicator> childForAuditCategoryDisp(
            StringIdKey auditCategoryKey, PagingInfo pagingInfo
    ) throws ServiceException {
        PagedData<AuditPropertyIndicator> lookup = auditPropertyIndicatorMaintainService.lookup(
                AuditPropertyIndicatorMaintainService.CHILD_FOR_AUDIT_CATEGORY,
                new Object[]{auditCategoryKey},
                pagingInfo
        );
        return toDispPagedData(lookup);
    }

    private DispAuditPropertyIndicator toDisp(AuditPropertyIndicator auditPropertyIndicator) throws ServiceException {
        if (Objects.isNull(auditPropertyIndicator)) {
            return null;
        }
        AuditPropertyIndicatorKey key = auditPropertyIndicator.getKey();
        StringIdKey auditCategoryKey = Objects.nonNull(key) && Objects.nonNull(key.getAuditCategoryStringId())
                ? new StringIdKey(key.getAuditCategoryStringId())
                : null;
        AuditCategory auditCategory = null;
        if (Objects.nonNull(auditCategoryKey)) {
            auditCategory = auditCategoryMaintainService.getIfExists(auditCategoryKey);
        }
        return DispAuditPropertyIndicator.of(auditPropertyIndicator, auditCategory);
    }

    private PagedData<DispAuditPropertyIndicator> toDispPagedData(PagedData<AuditPropertyIndicator> pagedData)
            throws ServiceException {
        List<DispAuditPropertyIndicator> dispAuditPropertyIndicators = new ArrayList<>(pagedData.getData().size());
        for (AuditPropertyIndicator auditPropertyIndicator : pagedData.getData()) {
            dispAuditPropertyIndicators.add(toDisp(auditPropertyIndicator));
        }
        return new PagedData<>(
                pagedData.getCurrentPage(), pagedData.getTotalPages(), pagedData.getRows(), pagedData.getCount(),
                dispAuditPropertyIndicators
        );
    }
}
