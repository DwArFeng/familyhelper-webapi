package com.dwarfeng.familyhelper.webapi.impl.service.audit;

import com.dwarfeng.audit.sdk.util.Constants;
import com.dwarfeng.audit.stack.bean.dto.AuditEntryLookupResult;
import com.dwarfeng.audit.stack.service.AuditEntryLookupService;
import com.dwarfeng.familyhelper.webapi.stack.bean.audit.dto.AuditEntryCompositeLookupInfo;
import com.dwarfeng.familyhelper.webapi.stack.bean.audit.dto.AuditEntryGroupedLookupInfo;
import com.dwarfeng.familyhelper.webapi.stack.service.audit.AuditEntryLookupResponseService;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class AuditEntryLookupResponseServiceImpl implements AuditEntryLookupResponseService {

    private final AuditEntryLookupService auditEntryLookupService;

    public AuditEntryLookupResponseServiceImpl(
            @Qualifier("auditAuditEntryLookupService") AuditEntryLookupService auditEntryLookupService
    ) {
        this.auditEntryLookupService = auditEntryLookupService;
    }

    @Override
    public AuditEntryLookupResult lookupComposite(AuditEntryCompositeLookupInfo info) throws ServiceException {
        if (info == null) {
            return auditEntryLookupService.lookupComposite(null);
        }
        return auditEntryLookupService.lookupComposite(
                new com.dwarfeng.audit.stack.bean.dto.AuditEntryCompositeLookupInfo(
                        info.getPagingInfo(),
                        info.getCategoryKey(),
                        info.getAuditEntryKey(),
                        info.getStartCreatedDate(),
                        info.getEndCreatedDate(),
                        toStackCompositeItems(info.getCompositeItems())
                )
        );
    }

    @Override
    public AuditEntryLookupResult lookupGrouped(AuditEntryGroupedLookupInfo info) throws ServiceException {
        if (info == null) {
            return auditEntryLookupService.lookupGrouped(null);
        }
        return auditEntryLookupService.lookupGrouped(
                new com.dwarfeng.audit.stack.bean.dto.AuditEntryGroupedLookupInfo(
                        info.getPagingInfo(),
                        info.getLogicOperator(),
                        toStackLookupItems(info.getLookupItems()),
                        toStackQueryGroups(info.getQueryGroups())
                )
        );
    }

    private List<com.dwarfeng.audit.stack.bean.dto.AuditEntryCompositeLookupInfo.CompositeItem> toStackCompositeItems(
            List<AuditEntryCompositeLookupInfo.CompositeItem> compositeItems
    ) {
        if (compositeItems == null) {
            return null;
        }
        List<com.dwarfeng.audit.stack.bean.dto.AuditEntryCompositeLookupInfo.CompositeItem> result =
                new ArrayList<>(compositeItems.size());
        for (AuditEntryCompositeLookupInfo.CompositeItem compositeItem : compositeItems) {
            result.add(new com.dwarfeng.audit.stack.bean.dto.AuditEntryCompositeLookupInfo.CompositeItem(
                    compositeItem.getPropertyId(),
                    compositeItem.getPropertyType(),
                    toValue(compositeItem.getPropertyType(), compositeItem.getFirstConditionString()),
                    toValue(compositeItem.getPropertyType(), compositeItem.getSecondConditionString()),
                    compositeItem.isEnabled()
            ));
        }
        return result;
    }

    private List<com.dwarfeng.audit.stack.bean.dto.AuditEntryGroupedLookupInfo.LookupItem> toStackLookupItems(
            List<AuditEntryGroupedLookupInfo.LookupItem> lookupItems
    ) {
        if (lookupItems == null) {
            return null;
        }
        List<com.dwarfeng.audit.stack.bean.dto.AuditEntryGroupedLookupInfo.LookupItem> result =
                new ArrayList<>(lookupItems.size());
        for (AuditEntryGroupedLookupInfo.LookupItem lookupItem : lookupItems) {
            result.add(new com.dwarfeng.audit.stack.bean.dto.AuditEntryGroupedLookupInfo.LookupItem(
                    lookupItem.getCategoryKey(),
                    lookupItem.getAuditEntryKey(),
                    lookupItem.getStartCreatedDate(),
                    lookupItem.getEndCreatedDate(),
                    toStackGroupedCompositeItems(lookupItem.getCompositeItems()),
                    lookupItem.isEnabled()
            ));
        }
        return result;
    }

    private List<com.dwarfeng.audit.stack.bean.dto.AuditEntryGroupedLookupInfo.QueryGroup> toStackQueryGroups(
            List<AuditEntryGroupedLookupInfo.QueryGroup> queryGroups
    ) {
        if (queryGroups == null) {
            return null;
        }
        List<com.dwarfeng.audit.stack.bean.dto.AuditEntryGroupedLookupInfo.QueryGroup> result =
                new ArrayList<>(queryGroups.size());
        for (AuditEntryGroupedLookupInfo.QueryGroup queryGroup : queryGroups) {
            result.add(new com.dwarfeng.audit.stack.bean.dto.AuditEntryGroupedLookupInfo.QueryGroup(
                    queryGroup.getLogicOperator(),
                    toStackLookupItems(queryGroup.getLookupItems()),
                    toStackQueryGroups(queryGroup.getQueryGroups()),
                    queryGroup.isEnabled()
            ));
        }
        return result;
    }

    private List<com.dwarfeng.audit.stack.bean.dto.AuditEntryGroupedLookupInfo.CompositeItem>
    toStackGroupedCompositeItems(List<AuditEntryGroupedLookupInfo.CompositeItem> compositeItems) {
        if (compositeItems == null) {
            return null;
        }
        List<com.dwarfeng.audit.stack.bean.dto.AuditEntryGroupedLookupInfo.CompositeItem> result =
                new ArrayList<>(compositeItems.size());
        for (AuditEntryGroupedLookupInfo.CompositeItem compositeItem : compositeItems) {
            result.add(new com.dwarfeng.audit.stack.bean.dto.AuditEntryGroupedLookupInfo.CompositeItem(
                    compositeItem.getPropertyId(),
                    compositeItem.getPropertyType(),
                    toValue(compositeItem.getPropertyType(), compositeItem.getFirstConditionString()),
                    toValue(compositeItem.getPropertyType(), compositeItem.getSecondConditionString()),
                    compositeItem.isEnabled()
            ));
        }
        return result;
    }

    private Object toValue(int propertyType, String valueString) {
        if (valueString == null) {
            return null;
        }
        switch (propertyType) {
            case Constants.PROPERTY_TYPE_LONG:
                return Long.parseLong(valueString);
            case Constants.PROPERTY_TYPE_DOUBLE:
                return Double.parseDouble(valueString);
            case Constants.PROPERTY_TYPE_BOOLEAN:
                return Boolean.parseBoolean(valueString);
            case Constants.PROPERTY_TYPE_DATE:
                return new Date(Long.parseLong(valueString));
            default:
                return valueString;
        }
    }
}
