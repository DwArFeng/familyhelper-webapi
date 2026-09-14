package com.dwarfeng.familyhelper.webapi.node.configuration.audit;

import com.dwarfeng.audit.sdk.bean.entity.*;
import com.dwarfeng.audit.stack.bean.entity.*;
import com.dwarfeng.familyhelper.webapi.sdk.bean.audit.disp.*;
import com.dwarfeng.familyhelper.webapi.stack.bean.audit.disp.*;
import com.dwarfeng.subgrade.impl.bean.MapStructBeanTransformer;
import com.dwarfeng.subgrade.stack.bean.BeanTransformer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration("audit.beanTransformerConfiguration")
public class BeanTransformerConfiguration {

    @Bean("audit.auditCategoryBeanTransformer")
    public BeanTransformer<AuditCategory, FastJsonAuditCategory> auditCategoryBeanTransformer() {
        return new MapStructBeanTransformer<>(
                AuditCategory.class, FastJsonAuditCategory.class,
                com.dwarfeng.audit.sdk.bean.BeanMapper.class
        );
    }

    @Bean("audit.auditEntryBeanTransformer")
    public BeanTransformer<AuditEntry, JSFixedFastJsonAuditEntry> auditEntryBeanTransformer() {
        return new MapStructBeanTransformer<>(
                AuditEntry.class, JSFixedFastJsonAuditEntry.class,
                com.dwarfeng.audit.sdk.bean.BeanMapper.class
        );
    }

    @Bean("audit.auditEntryPropertyBeanTransformer")
    public BeanTransformer<AuditEntryProperty, JSFixedFastJsonAuditEntryProperty> auditEntryPropertyBeanTransformer() {
        return new MapStructBeanTransformer<>(
                AuditEntryProperty.class, JSFixedFastJsonAuditEntryProperty.class,
                com.dwarfeng.audit.sdk.bean.BeanMapper.class
        );
    }

    @Bean("audit.auditPropertyIndicatorBeanTransformer")
    public BeanTransformer<AuditPropertyIndicator, JSFixedFastJsonAuditPropertyIndicator>
    auditPropertyIndicatorBeanTransformer() {
        return new MapStructBeanTransformer<>(
                AuditPropertyIndicator.class, JSFixedFastJsonAuditPropertyIndicator.class,
                com.dwarfeng.audit.sdk.bean.BeanMapper.class
        );
    }

    @Bean("audit.inspectionBeanTransformer")
    public BeanTransformer<Inspection, JSFixedFastJsonInspection> inspectionBeanTransformer() {
        return new MapStructBeanTransformer<>(
                Inspection.class, JSFixedFastJsonInspection.class,
                com.dwarfeng.audit.sdk.bean.BeanMapper.class
        );
    }

    @Bean("audit.inspectionAlarmBeanTransformer")
    public BeanTransformer<InspectionAlarm, JSFixedFastJsonInspectionAlarm> inspectionAlarmBeanTransformer() {
        return new MapStructBeanTransformer<>(
                InspectionAlarm.class, JSFixedFastJsonInspectionAlarm.class,
                com.dwarfeng.audit.sdk.bean.BeanMapper.class
        );
    }

    @Bean("audit.inspectionAlarmTypeIndicatorBeanTransformer")
    public BeanTransformer<InspectionAlarmTypeIndicator, FastJsonInspectionAlarmTypeIndicator>
    inspectionAlarmTypeIndicatorBeanTransformer() {
        return new MapStructBeanTransformer<>(
                InspectionAlarmTypeIndicator.class, FastJsonInspectionAlarmTypeIndicator.class,
                com.dwarfeng.audit.sdk.bean.BeanMapper.class
        );
    }

    @Bean("audit.inspectionDriverInfoBeanTransformer")
    public BeanTransformer<InspectionDriverInfo, JSFixedFastJsonInspectionDriverInfo>
    inspectionDriverInfoBeanTransformer() {
        return new MapStructBeanTransformer<>(
                InspectionDriverInfo.class, JSFixedFastJsonInspectionDriverInfo.class,
                com.dwarfeng.audit.sdk.bean.BeanMapper.class
        );
    }

    @Bean("audit.inspectionDriverSupportBeanTransformer")
    public BeanTransformer<InspectionDriverSupport, FastJsonInspectionDriverSupport>
    inspectionDriverSupportBeanTransformer() {
        return new MapStructBeanTransformer<>(
                InspectionDriverSupport.class, FastJsonInspectionDriverSupport.class,
                com.dwarfeng.audit.sdk.bean.BeanMapper.class
        );
    }

    @Bean("audit.inspectionTaskBeanTransformer")
    public BeanTransformer<InspectionTask, JSFixedFastJsonInspectionTask> inspectionTaskBeanTransformer() {
        return new MapStructBeanTransformer<>(
                InspectionTask.class, JSFixedFastJsonInspectionTask.class,
                com.dwarfeng.audit.sdk.bean.BeanMapper.class
        );
    }

    @Bean("audit.inspectionTaskEventBeanTransformer")
    public BeanTransformer<InspectionTaskEvent, JSFixedFastJsonInspectionTaskEvent>
    inspectionTaskEventBeanTransformer() {
        return new MapStructBeanTransformer<>(
                InspectionTaskEvent.class, JSFixedFastJsonInspectionTaskEvent.class,
                com.dwarfeng.audit.sdk.bean.BeanMapper.class
        );
    }

    @Bean("audit.inspectorInfoBeanTransformer")
    public BeanTransformer<InspectorInfo, JSFixedFastJsonInspectorInfo> inspectorInfoBeanTransformer() {
        return new MapStructBeanTransformer<>(
                InspectorInfo.class, JSFixedFastJsonInspectorInfo.class,
                com.dwarfeng.audit.sdk.bean.BeanMapper.class
        );
    }

    @Bean("audit.inspectorSupportBeanTransformer")
    public BeanTransformer<InspectorSupport, FastJsonInspectorSupport> inspectorSupportBeanTransformer() {
        return new MapStructBeanTransformer<>(
                InspectorSupport.class, FastJsonInspectorSupport.class,
                com.dwarfeng.audit.sdk.bean.BeanMapper.class
        );
    }

    @Bean("audit.inspectorVariableBeanTransformer")
    public BeanTransformer<InspectorVariable, JSFixedFastJsonInspectorVariable> inspectorVariableBeanTransformer() {
        return new MapStructBeanTransformer<>(
                InspectorVariable.class, JSFixedFastJsonInspectorVariable.class,
                com.dwarfeng.audit.sdk.bean.BeanMapper.class
        );
    }

    @Bean("audit.dispAuditEntryBeanTransformer")
    public BeanTransformer<DispAuditEntry, JSFixedFastJsonDispAuditEntry> dispAuditEntryBeanTransformer() {
        return new MapStructBeanTransformer<>(
                DispAuditEntry.class, JSFixedFastJsonDispAuditEntry.class,
                com.dwarfeng.familyhelper.webapi.sdk.bean.audit.BeanMapper.class
        );
    }

    @Bean("audit.dispAuditEntryPropertyBeanTransformer")
    public BeanTransformer<DispAuditEntryProperty, JSFixedFastJsonDispAuditEntryProperty>
    dispAuditEntryPropertyBeanTransformer() {
        return new MapStructBeanTransformer<>(
                DispAuditEntryProperty.class, JSFixedFastJsonDispAuditEntryProperty.class,
                com.dwarfeng.familyhelper.webapi.sdk.bean.audit.BeanMapper.class
        );
    }

    @Bean("audit.dispAuditPropertyIndicatorBeanTransformer")
    public BeanTransformer<DispAuditPropertyIndicator, JSFixedFastJsonDispAuditPropertyIndicator>
    dispAuditPropertyIndicatorBeanTransformer() {
        return new MapStructBeanTransformer<>(
                DispAuditPropertyIndicator.class, JSFixedFastJsonDispAuditPropertyIndicator.class,
                com.dwarfeng.familyhelper.webapi.sdk.bean.audit.BeanMapper.class
        );
    }

    @Bean("audit.dispInspectionAlarmBeanTransformer")
    public BeanTransformer<DispInspectionAlarm, JSFixedFastJsonDispInspectionAlarm>
    dispInspectionAlarmBeanTransformer() {
        return new MapStructBeanTransformer<>(
                DispInspectionAlarm.class, JSFixedFastJsonDispInspectionAlarm.class,
                com.dwarfeng.familyhelper.webapi.sdk.bean.audit.BeanMapper.class
        );
    }

    @Bean("audit.dispInspectionDriverInfoBeanTransformer")
    public BeanTransformer<DispInspectionDriverInfo, JSFixedFastJsonDispInspectionDriverInfo>
    dispInspectionDriverInfoBeanTransformer() {
        return new MapStructBeanTransformer<>(
                DispInspectionDriverInfo.class, JSFixedFastJsonDispInspectionDriverInfo.class,
                com.dwarfeng.familyhelper.webapi.sdk.bean.audit.BeanMapper.class
        );
    }

    @Bean("audit.dispInspectionTaskBeanTransformer")
    public BeanTransformer<DispInspectionTask, JSFixedFastJsonDispInspectionTask>
    dispInspectionTaskBeanTransformer() {
        return new MapStructBeanTransformer<>(
                DispInspectionTask.class, JSFixedFastJsonDispInspectionTask.class,
                com.dwarfeng.familyhelper.webapi.sdk.bean.audit.BeanMapper.class
        );
    }

    @Bean("audit.dispInspectionTaskEventBeanTransformer")
    public BeanTransformer<DispInspectionTaskEvent, JSFixedFastJsonDispInspectionTaskEvent>
    dispInspectionTaskEventBeanTransformer() {
        return new MapStructBeanTransformer<>(
                DispInspectionTaskEvent.class, JSFixedFastJsonDispInspectionTaskEvent.class,
                com.dwarfeng.familyhelper.webapi.sdk.bean.audit.BeanMapper.class
        );
    }

    @Bean("audit.dispInspectorInfoBeanTransformer")
    public BeanTransformer<DispInspectorInfo, JSFixedFastJsonDispInspectorInfo>
    dispInspectorInfoBeanTransformer() {
        return new MapStructBeanTransformer<>(
                DispInspectorInfo.class, JSFixedFastJsonDispInspectorInfo.class,
                com.dwarfeng.familyhelper.webapi.sdk.bean.audit.BeanMapper.class
        );
    }

    @Bean("audit.dispInspectorVariableBeanTransformer")
    public BeanTransformer<DispInspectorVariable, JSFixedFastJsonDispInspectorVariable>
    dispInspectorVariableBeanTransformer() {
        return new MapStructBeanTransformer<>(
                DispInspectorVariable.class, JSFixedFastJsonDispInspectorVariable.class,
                com.dwarfeng.familyhelper.webapi.sdk.bean.audit.BeanMapper.class
        );
    }
}
