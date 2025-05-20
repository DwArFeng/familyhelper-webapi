package com.dwarfeng.familyhelper.webapi.node.configuration.life;

import com.dwarfeng.familyhelper.life.sdk.bean.entity.*;
import com.dwarfeng.familyhelper.life.stack.bean.entity.*;
import com.dwarfeng.familyhelper.webapi.sdk.bean.life.BeanMapper;
import com.dwarfeng.familyhelper.webapi.sdk.bean.life.disp.*;
import com.dwarfeng.familyhelper.webapi.stack.bean.life.disp.*;
import com.dwarfeng.subgrade.impl.bean.MapStructBeanTransformer;
import com.dwarfeng.subgrade.stack.bean.BeanTransformer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration("life.beanTransformerConfiguration")
public class BeanTransformerConfiguration {

    @Bean("life.pbSetBeanTransformer")
    public BeanTransformer<PbSet, JSFixedFastJsonPbSet> pbSetBeanTransformer() {
        return new MapStructBeanTransformer<>(PbSet.class, JSFixedFastJsonPbSet.class, BeanMapper.class);
    }

    @Bean("life.dispPbSetBeanTransformer")
    public BeanTransformer<DispPbSet, JSFixedFastJsonDispPbSet> dispPbSetBeanTransformer() {
        return new MapStructBeanTransformer<>(DispPbSet.class, JSFixedFastJsonDispPbSet.class, BeanMapper.class);
    }

    @Bean("life.popbBeanTransformer")
    public BeanTransformer<Popb, JSFixedFastJsonPopb> popbBeanTransformer() {
        return new MapStructBeanTransformer<>(Popb.class, JSFixedFastJsonPopb.class, BeanMapper.class);
    }

    @Bean("life.dispPopbBeanTransformer")
    public BeanTransformer<DispPopb, JSFixedFastJsonDispPopb> dispPopbBeanTransformer() {
        return new MapStructBeanTransformer<>(DispPopb.class, JSFixedFastJsonDispPopb.class, BeanMapper.class);
    }

    @Bean("life.pbNodeBeanTransformer")
    public BeanTransformer<PbNode, JSFixedFastJsonPbNode> pbNodeBeanTransformer() {
        return new MapStructBeanTransformer<>(PbNode.class, JSFixedFastJsonPbNode.class, BeanMapper.class);
    }

    @Bean("life.dispPbNodeBeanTransformer")
    public BeanTransformer<DispPbNode, JSFixedFastJsonDispPbNode> dispPbNodeBeanTransformer() {
        return new MapStructBeanTransformer<>(DispPbNode.class, JSFixedFastJsonDispPbNode.class, BeanMapper.class);
    }

    @Bean("life.pbItemBeanTransformer")
    public BeanTransformer<PbItem, JSFixedFastJsonPbItem> pbItemBeanTransformer() {
        return new MapStructBeanTransformer<>(PbItem.class, JSFixedFastJsonPbItem.class, BeanMapper.class);
    }

    @Bean("life.dispPbItemBeanTransformer")
    public BeanTransformer<DispPbItem, JSFixedFastJsonDispPbItem> dispPbItemBeanTransformer() {
        return new MapStructBeanTransformer<>(DispPbItem.class, JSFixedFastJsonDispPbItem.class, BeanMapper.class);
    }

    @Bean("life.pbRecordBeanTransformer")
    public BeanTransformer<PbRecord, JSFixedFastJsonPbRecord> pbRecordBeanTransformer() {
        return new MapStructBeanTransformer<>(PbRecord.class, JSFixedFastJsonPbRecord.class, BeanMapper.class);
    }

    @Bean("life.pbFileInfoBeanTransformer")
    public BeanTransformer<PbFileInfo, JSFixedFastJsonPbFileInfo> pbFileInfoBeanTransformer() {
        return new MapStructBeanTransformer<>(PbFileInfo.class, JSFixedFastJsonPbFileInfo.class, BeanMapper.class);
    }

    @Bean("life.activityTypeIndicatorBeanTransformer")
    public BeanTransformer<ActivityTypeIndicator, FastJsonActivityTypeIndicator>
    activityTypeIndicatorBeanTransformer() {
        return new MapStructBeanTransformer<>(
                ActivityTypeIndicator.class, FastJsonActivityTypeIndicator.class, BeanMapper.class
        );
    }

    @Bean("finance.activityTemplateDriverInfoBeanTransformer")
    public BeanTransformer<ActivityTemplateDriverInfo, JSFixedFastJsonActivityTemplateDriverInfo>
    activityTemplateDriverInfoBeanTransformer() {
        return new MapStructBeanTransformer<>(
                ActivityTemplateDriverInfo.class, JSFixedFastJsonActivityTemplateDriverInfo.class, BeanMapper.class
        );
    }

    @Bean("finance.activityTemplateDriverSupportBeanTransformer")
    public BeanTransformer<ActivityTemplateDriverSupport, FastJsonActivityTemplateDriverSupport>
    activityTemplateDriverSupportBeanTransformer() {
        return new MapStructBeanTransformer<>(
                ActivityTemplateDriverSupport.class, FastJsonActivityTemplateDriverSupport.class, BeanMapper.class
        );
    }

    @Bean("life.activityDataSetBeanTransformer")
    public BeanTransformer<ActivityDataSet, JSFixedFastJsonActivityDataSet> activityDataSetBeanTransformer() {
        return new MapStructBeanTransformer<>(
                ActivityDataSet.class, JSFixedFastJsonActivityDataSet.class, BeanMapper.class
        );
    }

    @Bean("life.dispActivityDataSetBeanTransformer")
    public BeanTransformer<DispActivityDataSet, JSFixedFastJsonDispActivityDataSet>
    dispActivityDataSetBeanTransformer() {
        return new MapStructBeanTransformer<>(
                DispActivityDataSet.class, JSFixedFastJsonDispActivityDataSet.class, BeanMapper.class
        );
    }

    @Bean("life.poadBeanTransformer")
    public BeanTransformer<Poad, JSFixedFastJsonPoad> poadBeanTransformer() {
        return new MapStructBeanTransformer<>(Poad.class, JSFixedFastJsonPoad.class, BeanMapper.class);
    }

    @Bean("life.dispPoadBeanTransformer")
    public BeanTransformer<DispPoad, JSFixedFastJsonDispPoad> dispPoadBeanTransformer() {
        return new MapStructBeanTransformer<>(DispPoad.class, JSFixedFastJsonDispPoad.class, BeanMapper.class);
    }

    @Bean("life.activityDataNodeBeanTransformer")
    public BeanTransformer<ActivityDataNode, JSFixedFastJsonActivityDataNode> activityDataNodeBeanTransformer() {
        return new MapStructBeanTransformer<>(
                ActivityDataNode.class, JSFixedFastJsonActivityDataNode.class, BeanMapper.class
        );
    }

    @Bean("life.dispActivityDataNodeBeanTransformer")
    public BeanTransformer<DispActivityDataNode, JSFixedFastJsonDispActivityDataNode>
    dispActivityDataNodeBeanTransformer() {
        return new MapStructBeanTransformer<>(
                DispActivityDataNode.class, JSFixedFastJsonDispActivityDataNode.class, BeanMapper.class
        );
    }

    @Bean("life.activityDataItemBeanTransformer")
    public BeanTransformer<ActivityDataItem, JSFixedFastJsonActivityDataItem> activityDataItemBeanTransformer() {
        return new MapStructBeanTransformer<>(
                ActivityDataItem.class, JSFixedFastJsonActivityDataItem.class, BeanMapper.class
        );
    }

    @Bean("life.dispActivityDataItemBeanTransformer")
    public BeanTransformer<DispActivityDataItem, JSFixedFastJsonDispActivityDataItem>
    dispActivityDataItemBeanTransformer() {
        return new MapStructBeanTransformer<>(
                DispActivityDataItem.class, JSFixedFastJsonDispActivityDataItem.class, BeanMapper.class
        );
    }

    @Bean("life.activityDataRecordBeanTransformer")
    public BeanTransformer<ActivityDataRecord, JSFixedFastJsonActivityDataRecord> activityDataRecordBeanTransformer() {
        return new MapStructBeanTransformer<>(
                ActivityDataRecord.class, JSFixedFastJsonActivityDataRecord.class, BeanMapper.class
        );
    }

    @Bean("life.dispActivityDataRecordBeanTransformer")
    public BeanTransformer<DispActivityDataRecord, JSFixedFastJsonDispActivityDataRecord>
    dispActivityDataRecordBeanTransformer() {
        return new MapStructBeanTransformer<>(
                DispActivityDataRecord.class, JSFixedFastJsonDispActivityDataRecord.class, BeanMapper.class
        );
    }

    @Bean("life.activityTemplateBeanTransformer")
    public BeanTransformer<ActivityTemplate, JSFixedFastJsonActivityTemplate> activityTemplateBeanTransformer() {
        return new MapStructBeanTransformer<>(
                ActivityTemplate.class, JSFixedFastJsonActivityTemplate.class, BeanMapper.class
        );
    }

    @Bean("life.dispActivityTemplateBeanTransformer")
    public BeanTransformer<DispActivityTemplate, JSFixedFastJsonDispActivityTemplate>
    dispActivityTemplateBeanTransformer() {
        return new MapStructBeanTransformer<>(
                DispActivityTemplate.class, JSFixedFastJsonDispActivityTemplate.class, BeanMapper.class
        );
    }

    @Bean("life.poatBeanTransformer")
    public BeanTransformer<Poat, JSFixedFastJsonPoat> poatBeanTransformer() {
        return new MapStructBeanTransformer<>(Poat.class, JSFixedFastJsonPoat.class, BeanMapper.class);
    }

    @Bean("life.dispPoatBeanTransformer")
    public BeanTransformer<DispPoat, JSFixedFastJsonDispPoat> dispPoatBeanTransformer() {
        return new MapStructBeanTransformer<>(DispPoat.class, JSFixedFastJsonDispPoat.class, BeanMapper.class);
    }

    @Bean("life.poatacBeanTransformer")
    public BeanTransformer<Poatac, JSFixedFastJsonPoatac> poatacBeanTransformer() {
        return new MapStructBeanTransformer<>(Poatac.class, JSFixedFastJsonPoatac.class, BeanMapper.class);
    }

    @Bean("life.dispPoatacBeanTransformer")
    public BeanTransformer<DispPoatac, JSFixedFastJsonDispPoatac> dispPoatacBeanTransformer() {
        return new MapStructBeanTransformer<>(DispPoatac.class, JSFixedFastJsonDispPoatac.class, BeanMapper.class);
    }

    @Bean("life.activityTemplateCoverInfoBeanTransformer")
    public BeanTransformer<ActivityTemplateCoverInfo, JSFixedFastJsonActivityTemplateCoverInfo>
    activityTemplateCoverInfoBeanTransformer() {
        return new MapStructBeanTransformer<>(
                ActivityTemplateCoverInfo.class, JSFixedFastJsonActivityTemplateCoverInfo.class, BeanMapper.class
        );
    }

    @Bean("life.activityTemplateDataInfoBeanTransformer")
    public BeanTransformer<ActivityTemplateDataInfo, JSFixedFastJsonActivityTemplateDataInfo>
    activityTemplateDataInfoBeanTransformer() {
        return new MapStructBeanTransformer<>(
                ActivityTemplateDataInfo.class, JSFixedFastJsonActivityTemplateDataInfo.class, BeanMapper.class
        );
    }

    @Bean("life.dispActivityTemplateDataInfoBeanTransformer")
    public BeanTransformer<DispActivityTemplateDataInfo, JSFixedFastJsonDispActivityTemplateDataInfo>
    dispActivityTemplateDataInfoBeanTransformer() {
        return new MapStructBeanTransformer<>(
                DispActivityTemplateDataInfo.class,
                JSFixedFastJsonDispActivityTemplateDataInfo.class,
                BeanMapper.class
        );
    }

    @Bean("life.activityTemplateParticipantBeanTransformer")
    public BeanTransformer<ActivityTemplateParticipant, JSFixedFastJsonActivityTemplateParticipant>
    activityTemplateParticipantBeanTransformer() {
        return new MapStructBeanTransformer<>(
                ActivityTemplateParticipant.class,
                JSFixedFastJsonActivityTemplateParticipant.class,
                BeanMapper.class
        );
    }

    @Bean("life.dispActivityTemplateParticipantBeanTransformer")
    public BeanTransformer<DispActivityTemplateParticipant, JSFixedFastJsonDispActivityTemplateParticipant>
    dispActivityTemplateParticipantBeanTransformer() {
        return new MapStructBeanTransformer<>(
                DispActivityTemplateParticipant.class,
                JSFixedFastJsonDispActivityTemplateParticipant.class,
                BeanMapper.class
        );
    }

    @Bean("life.activityTemplateFileInfoBeanTransformer")
    public BeanTransformer<ActivityTemplateFileInfo, JSFixedFastJsonActivityTemplateFileInfo>
    activityTemplateFileInfoBeanTransformer() {
        return new MapStructBeanTransformer<>(
                ActivityTemplateFileInfo.class, JSFixedFastJsonActivityTemplateFileInfo.class, BeanMapper.class
        );
    }

    @Bean("life.activityBeanTransformer")
    public BeanTransformer<Activity, JSFixedFastJsonActivity> activityBeanTransformer() {
        return new MapStructBeanTransformer<>(Activity.class, JSFixedFastJsonActivity.class, BeanMapper.class);
    }

    @Bean("life.dispActivityBeanTransformer")
    public BeanTransformer<DispActivity, JSFixedFastJsonDispActivity>
    dispActivityBeanTransformer() {
        return new MapStructBeanTransformer<>(
                DispActivity.class, JSFixedFastJsonDispActivity.class, BeanMapper.class
        );
    }

    @Bean("life.poacBeanTransformer")
    public BeanTransformer<Poac, JSFixedFastJsonPoac> poacBeanTransformer() {
        return new MapStructBeanTransformer<>(Poac.class, JSFixedFastJsonPoac.class, BeanMapper.class);
    }

    @Bean("life.dispPoacBeanTransformer")
    public BeanTransformer<DispPoac, JSFixedFastJsonDispPoac> dispPoacBeanTransformer() {
        return new MapStructBeanTransformer<>(DispPoac.class, JSFixedFastJsonDispPoac.class, BeanMapper.class);
    }

    @Bean("life.activityCoverInfoBeanTransformer")
    public BeanTransformer<ActivityCoverInfo, JSFixedFastJsonActivityCoverInfo>
    activityCoverInfoBeanTransformer() {
        return new MapStructBeanTransformer<>(
                ActivityCoverInfo.class, JSFixedFastJsonActivityCoverInfo.class, BeanMapper.class
        );
    }

    @Bean("life.activityParticipantBeanTransformer")
    public BeanTransformer<ActivityParticipant, JSFixedFastJsonActivityParticipant>
    activityParticipantBeanTransformer() {
        return new MapStructBeanTransformer<>(
                ActivityParticipant.class, JSFixedFastJsonActivityParticipant.class, BeanMapper.class
        );
    }

    @Bean("life.dispActivityParticipantBeanTransformer")
    public BeanTransformer<DispActivityParticipant, JSFixedFastJsonDispActivityParticipant>
    dispActivityParticipantBeanTransformer() {
        return new MapStructBeanTransformer<>(
                DispActivityParticipant.class, JSFixedFastJsonDispActivityParticipant.class, BeanMapper.class
        );
    }

    @Bean("life.activityFileInfoBeanTransformer")
    public BeanTransformer<ActivityFileInfo, JSFixedFastJsonActivityFileInfo> activityFileInfoBeanTransformer() {
        return new MapStructBeanTransformer<>(
                ActivityFileInfo.class, JSFixedFastJsonActivityFileInfo.class, BeanMapper.class
        );
    }
}
