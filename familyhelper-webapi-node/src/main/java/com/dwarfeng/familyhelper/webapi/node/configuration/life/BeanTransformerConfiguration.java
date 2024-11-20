package com.dwarfeng.familyhelper.webapi.node.configuration.life;

import com.dwarfeng.familyhelper.life.sdk.bean.entity.*;
import com.dwarfeng.familyhelper.life.stack.bean.entity.*;
import com.dwarfeng.familyhelper.webapi.sdk.bean.disp.life.*;
import com.dwarfeng.familyhelper.webapi.stack.bean.disp.life.*;
import com.dwarfeng.subgrade.impl.bean.MapStructBeanTransformer;
import com.dwarfeng.subgrade.stack.bean.BeanTransformer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration("life.beanTransformerConfiguration")
public class BeanTransformerConfiguration {

    @Bean("life.pbSetBeanTransformer")
    public BeanTransformer<PbSet, JSFixedFastJsonPbSet> pbSetBeanTransformer() {
        return new MapStructBeanTransformer<>(PbSet.class, JSFixedFastJsonPbSet.class, FastJsonMapper.class);
    }

    @Bean("life.dispPbSetBeanTransformer")
    public BeanTransformer<DispPbSet, JSFixedFastJsonDispPbSet> dispPbSetBeanTransformer() {
        return new MapStructBeanTransformer<>(DispPbSet.class, JSFixedFastJsonDispPbSet.class, FastJsonMapper.class);
    }

    @Bean("life.popbBeanTransformer")
    public BeanTransformer<Popb, JSFixedFastJsonPopb> popbBeanTransformer() {
        return new MapStructBeanTransformer<>(Popb.class, JSFixedFastJsonPopb.class, FastJsonMapper.class);
    }

    @Bean("life.dispPopbBeanTransformer")
    public BeanTransformer<DispPopb, JSFixedFastJsonDispPopb> dispPopbBeanTransformer() {
        return new MapStructBeanTransformer<>(DispPopb.class, JSFixedFastJsonDispPopb.class, FastJsonMapper.class);
    }

    @Bean("life.pbNodeBeanTransformer")
    public BeanTransformer<PbNode, JSFixedFastJsonPbNode> pbNodeBeanTransformer() {
        return new MapStructBeanTransformer<>(PbNode.class, JSFixedFastJsonPbNode.class, FastJsonMapper.class);
    }

    @Bean("life.dispPbNodeBeanTransformer")
    public BeanTransformer<DispPbNode, JSFixedFastJsonDispPbNode> dispPbNodeBeanTransformer() {
        return new MapStructBeanTransformer<>(DispPbNode.class, JSFixedFastJsonDispPbNode.class, FastJsonMapper.class);
    }

    @Bean("life.pbItemBeanTransformer")
    public BeanTransformer<PbItem, JSFixedFastJsonPbItem> pbItemBeanTransformer() {
        return new MapStructBeanTransformer<>(PbItem.class, JSFixedFastJsonPbItem.class, FastJsonMapper.class);
    }

    @Bean("life.dispPbItemBeanTransformer")
    public BeanTransformer<DispPbItem, JSFixedFastJsonDispPbItem> dispPbItemBeanTransformer() {
        return new MapStructBeanTransformer<>(DispPbItem.class, JSFixedFastJsonDispPbItem.class, FastJsonMapper.class);
    }

    @Bean("life.pbRecordBeanTransformer")
    public BeanTransformer<PbRecord, JSFixedFastJsonPbRecord> pbRecordBeanTransformer() {
        return new MapStructBeanTransformer<>(PbRecord.class, JSFixedFastJsonPbRecord.class, FastJsonMapper.class);
    }

    @Bean("life.pbFileInfoBeanTransformer")
    public BeanTransformer<PbFileInfo, JSFixedFastJsonPbFileInfo> pbFileInfoBeanTransformer() {
        return new MapStructBeanTransformer<>(PbFileInfo.class, JSFixedFastJsonPbFileInfo.class, FastJsonMapper.class);
    }

    @Bean("life.activityTypeIndicatorBeanTransformer")
    public BeanTransformer<ActivityTypeIndicator, FastJsonActivityTypeIndicator>
    activityTypeIndicatorBeanTransformer() {
        return new MapStructBeanTransformer<>(
                ActivityTypeIndicator.class, FastJsonActivityTypeIndicator.class, FastJsonMapper.class
        );
    }

    @Bean("finance.activityTemplateDriverInfoBeanTransformer")
    public BeanTransformer<ActivityTemplateDriverInfo, JSFixedFastJsonActivityTemplateDriverInfo>
    activityTemplateDriverInfoBeanTransformer() {
        return new MapStructBeanTransformer<>(
                ActivityTemplateDriverInfo.class, JSFixedFastJsonActivityTemplateDriverInfo.class, FastJsonMapper.class
        );
    }

    @Bean("finance.activityTemplateDriverSupportBeanTransformer")
    public BeanTransformer<ActivityTemplateDriverSupport, FastJsonActivityTemplateDriverSupport>
    activityTemplateDriverSupportBeanTransformer() {
        return new MapStructBeanTransformer<>(
                ActivityTemplateDriverSupport.class, FastJsonActivityTemplateDriverSupport.class, FastJsonMapper.class
        );
    }

    @Bean("life.activityDataSetBeanTransformer")
    public BeanTransformer<ActivityDataSet, JSFixedFastJsonActivityDataSet> activityDataSetBeanTransformer() {
        return new MapStructBeanTransformer<>(
                ActivityDataSet.class, JSFixedFastJsonActivityDataSet.class, FastJsonMapper.class
        );
    }

    @Bean("life.dispActivityDataSetBeanTransformer")
    public BeanTransformer<DispActivityDataSet, JSFixedFastJsonDispActivityDataSet>
    dispActivityDataSetBeanTransformer() {
        return new MapStructBeanTransformer<>(
                DispActivityDataSet.class, JSFixedFastJsonDispActivityDataSet.class, FastJsonMapper.class
        );
    }

    @Bean("life.poadBeanTransformer")
    public BeanTransformer<Poad, JSFixedFastJsonPoad> poadBeanTransformer() {
        return new MapStructBeanTransformer<>(Poad.class, JSFixedFastJsonPoad.class, FastJsonMapper.class);
    }

    @Bean("life.dispPoadBeanTransformer")
    public BeanTransformer<DispPoad, JSFixedFastJsonDispPoad> dispPoadBeanTransformer() {
        return new MapStructBeanTransformer<>(DispPoad.class, JSFixedFastJsonDispPoad.class, FastJsonMapper.class);
    }

    @Bean("life.activityDataNodeBeanTransformer")
    public BeanTransformer<ActivityDataNode, JSFixedFastJsonActivityDataNode> activityDataNodeBeanTransformer() {
        return new MapStructBeanTransformer<>(
                ActivityDataNode.class, JSFixedFastJsonActivityDataNode.class, FastJsonMapper.class
        );
    }

    @Bean("life.dispActivityDataNodeBeanTransformer")
    public BeanTransformer<DispActivityDataNode, JSFixedFastJsonDispActivityDataNode>
    dispActivityDataNodeBeanTransformer() {
        return new MapStructBeanTransformer<>(
                DispActivityDataNode.class, JSFixedFastJsonDispActivityDataNode.class, FastJsonMapper.class
        );
    }

    @Bean("life.activityDataItemBeanTransformer")
    public BeanTransformer<ActivityDataItem, JSFixedFastJsonActivityDataItem> activityDataItemBeanTransformer() {
        return new MapStructBeanTransformer<>(
                ActivityDataItem.class, JSFixedFastJsonActivityDataItem.class, FastJsonMapper.class
        );
    }

    @Bean("life.dispActivityDataItemBeanTransformer")
    public BeanTransformer<DispActivityDataItem, JSFixedFastJsonDispActivityDataItem>
    dispActivityDataItemBeanTransformer() {
        return new MapStructBeanTransformer<>(
                DispActivityDataItem.class, JSFixedFastJsonDispActivityDataItem.class, FastJsonMapper.class
        );
    }

    @Bean("life.activityDataRecordBeanTransformer")
    public BeanTransformer<ActivityDataRecord, JSFixedFastJsonActivityDataRecord> activityDataRecordBeanTransformer() {
        return new MapStructBeanTransformer<>(
                ActivityDataRecord.class, JSFixedFastJsonActivityDataRecord.class, FastJsonMapper.class
        );
    }

    @Bean("life.dispActivityDataRecordBeanTransformer")
    public BeanTransformer<DispActivityDataRecord, JSFixedFastJsonDispActivityDataRecord>
    dispActivityDataRecordBeanTransformer() {
        return new MapStructBeanTransformer<>(
                DispActivityDataRecord.class, JSFixedFastJsonDispActivityDataRecord.class, FastJsonMapper.class
        );
    }

    @Bean("life.activityTemplateBeanTransformer")
    public BeanTransformer<ActivityTemplate, JSFixedFastJsonActivityTemplate> activityTemplateBeanTransformer() {
        return new MapStructBeanTransformer<>(
                ActivityTemplate.class, JSFixedFastJsonActivityTemplate.class, FastJsonMapper.class
        );
    }

    @Bean("life.dispActivityTemplateBeanTransformer")
    public BeanTransformer<DispActivityTemplate, JSFixedFastJsonDispActivityTemplate>
    dispActivityTemplateBeanTransformer() {
        return new MapStructBeanTransformer<>(
                DispActivityTemplate.class, JSFixedFastJsonDispActivityTemplate.class, FastJsonMapper.class
        );
    }

    @Bean("life.poatBeanTransformer")
    public BeanTransformer<Poat, JSFixedFastJsonPoat> poatBeanTransformer() {
        return new MapStructBeanTransformer<>(Poat.class, JSFixedFastJsonPoat.class, FastJsonMapper.class);
    }

    @Bean("life.dispPoatBeanTransformer")
    public BeanTransformer<DispPoat, JSFixedFastJsonDispPoat> dispPoatBeanTransformer() {
        return new MapStructBeanTransformer<>(DispPoat.class, JSFixedFastJsonDispPoat.class, FastJsonMapper.class);
    }

    @Bean("life.poatacBeanTransformer")
    public BeanTransformer<Poatac, JSFixedFastJsonPoatac> poatacBeanTransformer() {
        return new MapStructBeanTransformer<>(Poatac.class, JSFixedFastJsonPoatac.class, FastJsonMapper.class);
    }

    @Bean("life.dispPoatacBeanTransformer")
    public BeanTransformer<DispPoatac, JSFixedFastJsonDispPoatac> dispPoatacBeanTransformer() {
        return new MapStructBeanTransformer<>(DispPoatac.class, JSFixedFastJsonDispPoatac.class, FastJsonMapper.class);
    }

    @Bean("life.activityTemplateCoverInfoBeanTransformer")
    public BeanTransformer<ActivityTemplateCoverInfo, JSFixedFastJsonActivityTemplateCoverInfo>
    activityTemplateCoverInfoBeanTransformer() {
        return new MapStructBeanTransformer<>(
                ActivityTemplateCoverInfo.class, JSFixedFastJsonActivityTemplateCoverInfo.class, FastJsonMapper.class
        );
    }

    @Bean("life.activityTemplateDataInfoBeanTransformer")
    public BeanTransformer<ActivityTemplateDataInfo, JSFixedFastJsonActivityTemplateDataInfo>
    activityTemplateDataInfoBeanTransformer() {
        return new MapStructBeanTransformer<>(
                ActivityTemplateDataInfo.class, JSFixedFastJsonActivityTemplateDataInfo.class, FastJsonMapper.class
        );
    }

    @Bean("life.dispActivityTemplateDataInfoBeanTransformer")
    public BeanTransformer<DispActivityTemplateDataInfo, JSFixedFastJsonDispActivityTemplateDataInfo>
    dispActivityTemplateDataInfoBeanTransformer() {
        return new MapStructBeanTransformer<>(
                DispActivityTemplateDataInfo.class,
                JSFixedFastJsonDispActivityTemplateDataInfo.class,
                FastJsonMapper.class
        );
    }

    @Bean("life.activityTemplateParticipantBeanTransformer")
    public BeanTransformer<ActivityTemplateParticipant, JSFixedFastJsonActivityTemplateParticipant>
    activityTemplateParticipantBeanTransformer() {
        return new MapStructBeanTransformer<>(
                ActivityTemplateParticipant.class,
                JSFixedFastJsonActivityTemplateParticipant.class,
                FastJsonMapper.class
        );
    }

    @Bean("life.dispActivityTemplateParticipantBeanTransformer")
    public BeanTransformer<DispActivityTemplateParticipant, JSFixedFastJsonDispActivityTemplateParticipant>
    dispActivityTemplateParticipantBeanTransformer() {
        return new MapStructBeanTransformer<>(
                DispActivityTemplateParticipant.class,
                JSFixedFastJsonDispActivityTemplateParticipant.class,
                FastJsonMapper.class
        );
    }

    @Bean("life.activityTemplateFileInfoBeanTransformer")
    public BeanTransformer<ActivityTemplateFileInfo, JSFixedFastJsonActivityTemplateFileInfo>
    activityTemplateFileInfoBeanTransformer() {
        return new MapStructBeanTransformer<>(
                ActivityTemplateFileInfo.class, JSFixedFastJsonActivityTemplateFileInfo.class, FastJsonMapper.class
        );
    }

    @Bean("life.activityBeanTransformer")
    public BeanTransformer<Activity, JSFixedFastJsonActivity> activityBeanTransformer() {
        return new MapStructBeanTransformer<>(Activity.class, JSFixedFastJsonActivity.class, FastJsonMapper.class);
    }

    @Bean("life.dispActivityBeanTransformer")
    public BeanTransformer<DispActivity, JSFixedFastJsonDispActivity>
    dispActivityBeanTransformer() {
        return new MapStructBeanTransformer<>(
                DispActivity.class, JSFixedFastJsonDispActivity.class, FastJsonMapper.class
        );
    }

    @Bean("life.poacBeanTransformer")
    public BeanTransformer<Poac, JSFixedFastJsonPoac> poacBeanTransformer() {
        return new MapStructBeanTransformer<>(Poac.class, JSFixedFastJsonPoac.class, FastJsonMapper.class);
    }

    @Bean("life.dispPoacBeanTransformer")
    public BeanTransformer<DispPoac, JSFixedFastJsonDispPoac> dispPoacBeanTransformer() {
        return new MapStructBeanTransformer<>(DispPoac.class, JSFixedFastJsonDispPoac.class, FastJsonMapper.class);
    }

    @Bean("life.activityCoverInfoBeanTransformer")
    public BeanTransformer<ActivityCoverInfo, JSFixedFastJsonActivityCoverInfo>
    activityCoverInfoBeanTransformer() {
        return new MapStructBeanTransformer<>(
                ActivityCoverInfo.class, JSFixedFastJsonActivityCoverInfo.class, FastJsonMapper.class
        );
    }

    @Bean("life.activityParticipantBeanTransformer")
    public BeanTransformer<ActivityParticipant, JSFixedFastJsonActivityParticipant>
    activityParticipantBeanTransformer() {
        return new MapStructBeanTransformer<>(
                ActivityParticipant.class, JSFixedFastJsonActivityParticipant.class, FastJsonMapper.class
        );
    }

    @Bean("life.dispActivityParticipantBeanTransformer")
    public BeanTransformer<DispActivityParticipant, JSFixedFastJsonDispActivityParticipant>
    dispActivityParticipantBeanTransformer() {
        return new MapStructBeanTransformer<>(
                DispActivityParticipant.class, JSFixedFastJsonDispActivityParticipant.class, FastJsonMapper.class
        );
    }

    @Bean("life.activityFileInfoBeanTransformer")
    public BeanTransformer<ActivityFileInfo, JSFixedFastJsonActivityFileInfo> activityFileInfoBeanTransformer() {
        return new MapStructBeanTransformer<>(
                ActivityFileInfo.class, JSFixedFastJsonActivityFileInfo.class, FastJsonMapper.class
        );
    }
}
