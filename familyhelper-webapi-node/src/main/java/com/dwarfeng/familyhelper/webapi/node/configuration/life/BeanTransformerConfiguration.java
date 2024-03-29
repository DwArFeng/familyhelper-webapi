package com.dwarfeng.familyhelper.webapi.node.configuration.life;

import com.dwarfeng.familyhelper.life.sdk.bean.entity.*;
import com.dwarfeng.familyhelper.life.stack.bean.entity.*;
import com.dwarfeng.familyhelper.webapi.sdk.bean.disp.life.*;
import com.dwarfeng.familyhelper.webapi.stack.bean.disp.life.*;
import com.dwarfeng.subgrade.impl.bean.DozerBeanTransformer;
import com.dwarfeng.subgrade.stack.bean.BeanTransformer;
import org.dozer.Mapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration("life.beanTransformerConfiguration")
public class BeanTransformerConfiguration {

    private final Mapper mapper;

    public BeanTransformerConfiguration(Mapper mapper) {
        this.mapper = mapper;
    }

    @Bean("life.pbSetBeanTransformer")
    public BeanTransformer<PbSet, JSFixedFastJsonPbSet> pbSetBeanTransformer() {
        return new DozerBeanTransformer<>(PbSet.class, JSFixedFastJsonPbSet.class, mapper);
    }

    @Bean("life.dispPbSetBeanTransformer")
    public BeanTransformer<DispPbSet, JSFixedFastJsonDispPbSet> dispPbSetBeanTransformer() {
        return new DozerBeanTransformer<>(DispPbSet.class, JSFixedFastJsonDispPbSet.class, mapper);
    }

    @Bean("life.popbBeanTransformer")
    public BeanTransformer<Popb, JSFixedFastJsonPopb> popbBeanTransformer() {
        return new DozerBeanTransformer<>(Popb.class, JSFixedFastJsonPopb.class, mapper);
    }

    @Bean("life.dispPopbBeanTransformer")
    public BeanTransformer<DispPopb, JSFixedFastJsonDispPopb> dispPopbBeanTransformer() {
        return new DozerBeanTransformer<>(DispPopb.class, JSFixedFastJsonDispPopb.class, mapper);
    }

    @Bean("life.pbNodeBeanTransformer")
    public BeanTransformer<PbNode, JSFixedFastJsonPbNode> pbNodeBeanTransformer() {
        return new DozerBeanTransformer<>(PbNode.class, JSFixedFastJsonPbNode.class, mapper);
    }

    @Bean("life.dispPbNodeBeanTransformer")
    public BeanTransformer<DispPbNode, JSFixedFastJsonDispPbNode> dispPbNodeBeanTransformer() {
        return new DozerBeanTransformer<>(DispPbNode.class, JSFixedFastJsonDispPbNode.class, mapper);
    }

    @Bean("life.pbItemBeanTransformer")
    public BeanTransformer<PbItem, JSFixedFastJsonPbItem> pbItemBeanTransformer() {
        return new DozerBeanTransformer<>(PbItem.class, JSFixedFastJsonPbItem.class, mapper);
    }

    @Bean("life.dispPbItemBeanTransformer")
    public BeanTransformer<DispPbItem, JSFixedFastJsonDispPbItem> dispPbItemBeanTransformer() {
        return new DozerBeanTransformer<>(DispPbItem.class, JSFixedFastJsonDispPbItem.class, mapper);
    }

    @Bean("life.pbRecordBeanTransformer")
    public BeanTransformer<PbRecord, JSFixedFastJsonPbRecord> pbRecordBeanTransformer() {
        return new DozerBeanTransformer<>(PbRecord.class, JSFixedFastJsonPbRecord.class, mapper);
    }

    @Bean("life.pbFileInfoBeanTransformer")
    public BeanTransformer<PbFileInfo, JSFixedFastJsonPbFileInfo> pbFileInfoBeanTransformer() {
        return new DozerBeanTransformer<>(PbFileInfo.class, JSFixedFastJsonPbFileInfo.class, mapper);
    }

    @Bean("life.activityTypeIndicatorBeanTransformer")
    public BeanTransformer<ActivityTypeIndicator, FastJsonActivityTypeIndicator>
    activityTypeIndicatorBeanTransformer() {
        return new DozerBeanTransformer<>(ActivityTypeIndicator.class, FastJsonActivityTypeIndicator.class, mapper);
    }

    @Bean("finance.activityTemplateDriverInfoBeanTransformer")
    public BeanTransformer<ActivityTemplateDriverInfo, JSFixedFastJsonActivityTemplateDriverInfo>
    activityTemplateDriverInfoBeanTransformer() {
        return new DozerBeanTransformer<>(
                ActivityTemplateDriverInfo.class, JSFixedFastJsonActivityTemplateDriverInfo.class, mapper
        );
    }

    @Bean("finance.activityTemplateDriverSupportBeanTransformer")
    public BeanTransformer<ActivityTemplateDriverSupport, FastJsonActivityTemplateDriverSupport>
    activityTemplateDriverSupportBeanTransformer() {
        return new DozerBeanTransformer<>(
                ActivityTemplateDriverSupport.class, FastJsonActivityTemplateDriverSupport.class, mapper
        );
    }

    @Bean("life.activityDataSetBeanTransformer")
    public BeanTransformer<ActivityDataSet, JSFixedFastJsonActivityDataSet> activityDataSetBeanTransformer() {
        return new DozerBeanTransformer<>(ActivityDataSet.class, JSFixedFastJsonActivityDataSet.class, mapper);
    }

    @Bean("life.dispActivityDataSetBeanTransformer")
    public BeanTransformer<DispActivityDataSet, JSFixedFastJsonDispActivityDataSet>
    dispActivityDataSetBeanTransformer() {
        return new DozerBeanTransformer<>(DispActivityDataSet.class, JSFixedFastJsonDispActivityDataSet.class, mapper);
    }

    @Bean("life.poadBeanTransformer")
    public BeanTransformer<Poad, JSFixedFastJsonPoad> poadBeanTransformer() {
        return new DozerBeanTransformer<>(Poad.class, JSFixedFastJsonPoad.class, mapper);
    }

    @Bean("life.dispPoadBeanTransformer")
    public BeanTransformer<DispPoad, JSFixedFastJsonDispPoad> dispPoadBeanTransformer() {
        return new DozerBeanTransformer<>(DispPoad.class, JSFixedFastJsonDispPoad.class, mapper);
    }

    @Bean("life.activityDataNodeBeanTransformer")
    public BeanTransformer<ActivityDataNode, JSFixedFastJsonActivityDataNode> activityDataNodeBeanTransformer() {
        return new DozerBeanTransformer<>(ActivityDataNode.class, JSFixedFastJsonActivityDataNode.class, mapper);
    }

    @Bean("life.dispActivityDataNodeBeanTransformer")
    public BeanTransformer<DispActivityDataNode, JSFixedFastJsonDispActivityDataNode>
    dispActivityDataNodeBeanTransformer() {
        return new DozerBeanTransformer<>(
                DispActivityDataNode.class, JSFixedFastJsonDispActivityDataNode.class, mapper
        );
    }

    @Bean("life.activityDataItemBeanTransformer")
    public BeanTransformer<ActivityDataItem, JSFixedFastJsonActivityDataItem> activityDataItemBeanTransformer() {
        return new DozerBeanTransformer<>(ActivityDataItem.class, JSFixedFastJsonActivityDataItem.class, mapper);
    }

    @Bean("life.dispActivityDataItemBeanTransformer")
    public BeanTransformer<DispActivityDataItem, JSFixedFastJsonDispActivityDataItem>
    dispActivityDataItemBeanTransformer() {
        return new DozerBeanTransformer<>(
                DispActivityDataItem.class, JSFixedFastJsonDispActivityDataItem.class, mapper
        );
    }

    @Bean("life.activityDataRecordBeanTransformer")
    public BeanTransformer<ActivityDataRecord, JSFixedFastJsonActivityDataRecord> activityDataRecordBeanTransformer() {
        return new DozerBeanTransformer<>(ActivityDataRecord.class, JSFixedFastJsonActivityDataRecord.class, mapper);
    }

    @Bean("life.dispActivityDataRecordBeanTransformer")
    public BeanTransformer<DispActivityDataRecord, JSFixedFastJsonDispActivityDataRecord>
    dispActivityDataRecordBeanTransformer() {
        return new DozerBeanTransformer<>(
                DispActivityDataRecord.class, JSFixedFastJsonDispActivityDataRecord.class, mapper
        );
    }

    @Bean("life.activityTemplateBeanTransformer")
    public BeanTransformer<ActivityTemplate, JSFixedFastJsonActivityTemplate> activityTemplateBeanTransformer() {
        return new DozerBeanTransformer<>(ActivityTemplate.class, JSFixedFastJsonActivityTemplate.class, mapper);
    }

    @Bean("life.dispActivityTemplateBeanTransformer")
    public BeanTransformer<DispActivityTemplate, JSFixedFastJsonDispActivityTemplate>
    dispActivityTemplateBeanTransformer() {
        return new DozerBeanTransformer<>(
                DispActivityTemplate.class, JSFixedFastJsonDispActivityTemplate.class, mapper
        );
    }

    @Bean("life.poatBeanTransformer")
    public BeanTransformer<Poat, JSFixedFastJsonPoat> poatBeanTransformer() {
        return new DozerBeanTransformer<>(Poat.class, JSFixedFastJsonPoat.class, mapper);
    }

    @Bean("life.dispPoatBeanTransformer")
    public BeanTransformer<DispPoat, JSFixedFastJsonDispPoat> dispPoatBeanTransformer() {
        return new DozerBeanTransformer<>(DispPoat.class, JSFixedFastJsonDispPoat.class, mapper);
    }

    @Bean("life.poatacBeanTransformer")
    public BeanTransformer<Poatac, JSFixedFastJsonPoatac> poatacBeanTransformer() {
        return new DozerBeanTransformer<>(Poatac.class, JSFixedFastJsonPoatac.class, mapper);
    }

    @Bean("life.dispPoatacBeanTransformer")
    public BeanTransformer<DispPoatac, JSFixedFastJsonDispPoatac> dispPoatacBeanTransformer() {
        return new DozerBeanTransformer<>(DispPoatac.class, JSFixedFastJsonDispPoatac.class, mapper);
    }

    @Bean("life.activityTemplateCoverInfoBeanTransformer")
    public BeanTransformer<ActivityTemplateCoverInfo, JSFixedFastJsonActivityTemplateCoverInfo>
    activityTemplateCoverInfoBeanTransformer() {
        return new DozerBeanTransformer<>(
                ActivityTemplateCoverInfo.class, JSFixedFastJsonActivityTemplateCoverInfo.class, mapper
        );
    }

    @Bean("life.activityTemplateDataInfoBeanTransformer")
    public BeanTransformer<ActivityTemplateDataInfo, JSFixedFastJsonActivityTemplateDataInfo>
    activityTemplateDataInfoBeanTransformer() {
        return new DozerBeanTransformer<>(
                ActivityTemplateDataInfo.class, JSFixedFastJsonActivityTemplateDataInfo.class, mapper
        );
    }

    @Bean("life.dispActivityTemplateDataInfoBeanTransformer")
    public BeanTransformer<DispActivityTemplateDataInfo, JSFixedFastJsonDispActivityTemplateDataInfo>
    dispActivityTemplateDataInfoBeanTransformer() {
        return new DozerBeanTransformer<>(
                DispActivityTemplateDataInfo.class, JSFixedFastJsonDispActivityTemplateDataInfo.class, mapper
        );
    }

    @Bean("life.activityTemplateParticipantBeanTransformer")
    public BeanTransformer<ActivityTemplateParticipant, JSFixedFastJsonActivityTemplateParticipant>
    activityTemplateParticipantBeanTransformer() {
        return new DozerBeanTransformer<>(
                ActivityTemplateParticipant.class, JSFixedFastJsonActivityTemplateParticipant.class, mapper
        );
    }

    @Bean("life.dispActivityTemplateParticipantBeanTransformer")
    public BeanTransformer<DispActivityTemplateParticipant, JSFixedFastJsonDispActivityTemplateParticipant>
    dispActivityTemplateParticipantBeanTransformer() {
        return new DozerBeanTransformer<>(
                DispActivityTemplateParticipant.class, JSFixedFastJsonDispActivityTemplateParticipant.class, mapper
        );
    }

    @Bean("life.activityTemplateFileInfoBeanTransformer")
    public BeanTransformer<ActivityTemplateFileInfo, JSFixedFastJsonActivityTemplateFileInfo>
    activityTemplateFileInfoBeanTransformer() {
        return new DozerBeanTransformer<>(
                ActivityTemplateFileInfo.class, JSFixedFastJsonActivityTemplateFileInfo.class, mapper
        );
    }

    @Bean("life.activityBeanTransformer")
    public BeanTransformer<Activity, JSFixedFastJsonActivity> activityBeanTransformer() {
        return new DozerBeanTransformer<>(Activity.class, JSFixedFastJsonActivity.class, mapper);
    }

    @Bean("life.dispActivityBeanTransformer")
    public BeanTransformer<DispActivity, JSFixedFastJsonDispActivity>
    dispActivityBeanTransformer() {
        return new DozerBeanTransformer<>(DispActivity.class, JSFixedFastJsonDispActivity.class, mapper);
    }

    @Bean("life.poacBeanTransformer")
    public BeanTransformer<Poac, JSFixedFastJsonPoac> poacBeanTransformer() {
        return new DozerBeanTransformer<>(Poac.class, JSFixedFastJsonPoac.class, mapper);
    }

    @Bean("life.dispPoacBeanTransformer")
    public BeanTransformer<DispPoac, JSFixedFastJsonDispPoac> dispPoacBeanTransformer() {
        return new DozerBeanTransformer<>(DispPoac.class, JSFixedFastJsonDispPoac.class, mapper);
    }

    @Bean("life.activityCoverInfoBeanTransformer")
    public BeanTransformer<ActivityCoverInfo, JSFixedFastJsonActivityCoverInfo>
    activityCoverInfoBeanTransformer() {
        return new DozerBeanTransformer<>(ActivityCoverInfo.class, JSFixedFastJsonActivityCoverInfo.class, mapper);
    }

    @Bean("life.activityParticipantBeanTransformer")
    public BeanTransformer<ActivityParticipant, JSFixedFastJsonActivityParticipant>
    activityParticipantBeanTransformer() {
        return new DozerBeanTransformer<>(
                ActivityParticipant.class, JSFixedFastJsonActivityParticipant.class, mapper
        );
    }

    @Bean("life.dispActivityParticipantBeanTransformer")
    public BeanTransformer<DispActivityParticipant, JSFixedFastJsonDispActivityParticipant>
    dispActivityParticipantBeanTransformer() {
        return new DozerBeanTransformer<>(
                DispActivityParticipant.class, JSFixedFastJsonDispActivityParticipant.class, mapper
        );
    }

    @Bean("life.activityFileInfoBeanTransformer")
    public BeanTransformer<ActivityFileInfo, JSFixedFastJsonActivityFileInfo> activityFileInfoBeanTransformer() {
        return new DozerBeanTransformer<>(ActivityFileInfo.class, JSFixedFastJsonActivityFileInfo.class, mapper);
    }
}
