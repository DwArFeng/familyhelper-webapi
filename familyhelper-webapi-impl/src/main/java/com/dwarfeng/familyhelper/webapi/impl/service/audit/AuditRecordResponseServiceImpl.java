package com.dwarfeng.familyhelper.webapi.impl.service.audit;

import com.dwarfeng.audit.sdk.util.Constants;
import com.dwarfeng.audit.stack.service.AuditRecordService;
import com.dwarfeng.familyhelper.webapi.stack.bean.audit.dto.AuditRecordInfo;
import com.dwarfeng.familyhelper.webapi.stack.bean.audit.dto.AuditRecordInfo.PropertyItem;
import com.dwarfeng.familyhelper.webapi.stack.service.audit.AuditRecordResponseService;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class AuditRecordResponseServiceImpl implements AuditRecordResponseService {

    private final AuditRecordService auditRecordService;

    public AuditRecordResponseServiceImpl(
            @Qualifier("auditAuditRecordService") AuditRecordService auditRecordService
    ) {
        this.auditRecordService = auditRecordService;
    }

    @Override
    public void record(AuditRecordInfo auditRecordInfo)
            throws ServiceException {
        if (auditRecordInfo == null) {
            auditRecordService.record(null);
            return;
        }

        Map<String, Object> properties = new HashMap<>();
        if (auditRecordInfo.getPropertyItems() != null) {
            for (PropertyItem propertyItem : auditRecordInfo.getPropertyItems()) {
                String valueString = propertyItem.getValueString();
                Object value;
                switch (propertyItem.getValueType()) {
                    case Constants.PROPERTY_TYPE_LONG:
                        value = Long.parseLong(valueString);
                        break;
                    case Constants.PROPERTY_TYPE_DOUBLE:
                        value = Double.parseDouble(valueString);
                        break;
                    case Constants.PROPERTY_TYPE_BOOLEAN:
                        value = Boolean.parseBoolean(valueString);
                        break;
                    case Constants.PROPERTY_TYPE_DATE:
                        value = new Date(Long.parseLong(valueString));
                        break;
                    default:
                        value = valueString;
                }
                properties.put(propertyItem.getId(), value);
            }
        }
        auditRecordService.record(
                new com.dwarfeng.audit.stack.bean.dto.AuditRecordInfo(
                        auditRecordInfo.getCategoryKey(), properties
                )
        );
    }
}
