package com.dwarfeng.familyhelper.webapi.node.configuration.logicengine;

import com.dwarfeng.familyhelper.webapi.sdk.bean.logicengine.disp.*;
import com.dwarfeng.familyhelper.webapi.stack.bean.logicengine.disp.*;
import com.dwarfeng.logicengine.sdk.bean.entity.*;
import com.dwarfeng.logicengine.stack.bean.entity.*;
import com.dwarfeng.subgrade.impl.bean.MapStructBeanTransformer;
import com.dwarfeng.subgrade.stack.bean.BeanTransformer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration("logicengine.beanTransformerConfiguration")
public class BeanTransformerConfiguration {

    @Bean("logicengine.driverInfoBeanTransformer")
    public BeanTransformer<DriverInfo, JSFixedFastJsonDriverInfo> driverInfoBeanTransformer() {
        return new MapStructBeanTransformer<>(
                DriverInfo.class, JSFixedFastJsonDriverInfo.class,
                com.dwarfeng.logicengine.sdk.bean.BeanMapper.class
        );
    }

    @Bean("logicengine.driverSupportBeanTransformer")
    public BeanTransformer<DriverSupport, FastJsonDriverSupport> driverSupportBeanTransformer() {
        return new MapStructBeanTransformer<>(
                DriverSupport.class, FastJsonDriverSupport.class,
                com.dwarfeng.logicengine.sdk.bean.BeanMapper.class
        );
    }

    @Bean("logicengine.guarderInfoBeanTransformer")
    public BeanTransformer<GuarderInfo, JSFixedFastJsonGuarderInfo> guarderInfoBeanTransformer() {
        return new MapStructBeanTransformer<>(
                GuarderInfo.class, JSFixedFastJsonGuarderInfo.class,
                com.dwarfeng.logicengine.sdk.bean.BeanMapper.class
        );
    }

    @Bean("logicengine.guarderSupportBeanTransformer")
    public BeanTransformer<GuarderSupport, FastJsonGuarderSupport> guarderSupportBeanTransformer() {
        return new MapStructBeanTransformer<>(
                GuarderSupport.class, FastJsonGuarderSupport.class,
                com.dwarfeng.logicengine.sdk.bean.BeanMapper.class
        );
    }

    @Bean("logicengine.performerInfoBeanTransformer")
    public BeanTransformer<PerformerInfo, JSFixedFastJsonPerformerInfo> performerInfoBeanTransformer() {
        return new MapStructBeanTransformer<>(
                PerformerInfo.class, JSFixedFastJsonPerformerInfo.class,
                com.dwarfeng.logicengine.sdk.bean.BeanMapper.class
        );
    }

    @Bean("logicengine.performerSupportBeanTransformer")
    public BeanTransformer<PerformerSupport, FastJsonPerformerSupport> performerSupportBeanTransformer() {
        return new MapStructBeanTransformer<>(
                PerformerSupport.class, FastJsonPerformerSupport.class,
                com.dwarfeng.logicengine.sdk.bean.BeanMapper.class
        );
    }

    @Bean("logicengine.sectionBeanTransformer")
    public BeanTransformer<Section, JSFixedFastJsonSection> sectionBeanTransformer() {
        return new MapStructBeanTransformer<>(
                Section.class, JSFixedFastJsonSection.class,
                com.dwarfeng.logicengine.sdk.bean.BeanMapper.class
        );
    }

    @Bean("logicengine.stateBeanTransformer")
    public BeanTransformer<State, JSFixedFastJsonState> stateBeanTransformer() {
        return new MapStructBeanTransformer<>(
                State.class, JSFixedFastJsonState.class,
                com.dwarfeng.logicengine.sdk.bean.BeanMapper.class
        );
    }

    @Bean("logicengine.taskBeanTransformer")
    public BeanTransformer<Task, JSFixedFastJsonTask> taskBeanTransformer() {
        return new MapStructBeanTransformer<>(
                Task.class, JSFixedFastJsonTask.class,
                com.dwarfeng.logicengine.sdk.bean.BeanMapper.class
        );
    }

    @Bean("logicengine.taskEventBeanTransformer")
    public BeanTransformer<TaskEvent, JSFixedFastJsonTaskEvent> taskEventBeanTransformer() {
        return new MapStructBeanTransformer<>(
                TaskEvent.class, JSFixedFastJsonTaskEvent.class,
                com.dwarfeng.logicengine.sdk.bean.BeanMapper.class
        );
    }

    @Bean("logicengine.taskVariableBeanTransformer")
    public BeanTransformer<TaskVariable, JSFixedFastJsonTaskVariable> taskVariableBeanTransformer() {
        return new MapStructBeanTransformer<>(
                TaskVariable.class, JSFixedFastJsonTaskVariable.class,
                com.dwarfeng.logicengine.sdk.bean.BeanMapper.class
        );
    }

    @Bean("logicengine.dispDriverInfoBeanTransformer")
    public BeanTransformer<DispDriverInfo, JSFixedFastJsonDispDriverInfo> dispDriverInfoBeanTransformer() {
        return new MapStructBeanTransformer<>(
                DispDriverInfo.class, JSFixedFastJsonDispDriverInfo.class,
                com.dwarfeng.familyhelper.webapi.sdk.bean.logicengine.BeanMapper.class
        );
    }

    @Bean("logicengine.dispGuarderInfoBeanTransformer")
    public BeanTransformer<DispGuarderInfo, JSFixedFastJsonDispGuarderInfo> dispGuarderInfoBeanTransformer() {
        return new MapStructBeanTransformer<>(
                DispGuarderInfo.class, JSFixedFastJsonDispGuarderInfo.class,
                com.dwarfeng.familyhelper.webapi.sdk.bean.logicengine.BeanMapper.class
        );
    }

    @Bean("logicengine.dispPerformerInfoBeanTransformer")
    public BeanTransformer<DispPerformerInfo, JSFixedFastJsonDispPerformerInfo> dispPerformerInfoBeanTransformer() {
        return new MapStructBeanTransformer<>(
                DispPerformerInfo.class, JSFixedFastJsonDispPerformerInfo.class,
                com.dwarfeng.familyhelper.webapi.sdk.bean.logicengine.BeanMapper.class
        );
    }

    @Bean("logicengine.dispStateBeanTransformer")
    public BeanTransformer<DispState, JSFixedFastJsonDispState> dispStateBeanTransformer() {
        return new MapStructBeanTransformer<>(
                DispState.class, JSFixedFastJsonDispState.class,
                com.dwarfeng.familyhelper.webapi.sdk.bean.logicengine.BeanMapper.class
        );
    }

    @Bean("logicengine.dispTaskBeanTransformer")
    public BeanTransformer<DispTask, JSFixedFastJsonDispTask> dispTaskBeanTransformer() {
        return new MapStructBeanTransformer<>(
                DispTask.class, JSFixedFastJsonDispTask.class,
                com.dwarfeng.familyhelper.webapi.sdk.bean.logicengine.BeanMapper.class
        );
    }

    @Bean("logicengine.dispTaskEventBeanTransformer")
    public BeanTransformer<DispTaskEvent, JSFixedFastJsonDispTaskEvent> dispTaskEventBeanTransformer() {
        return new MapStructBeanTransformer<>(
                DispTaskEvent.class, JSFixedFastJsonDispTaskEvent.class,
                com.dwarfeng.familyhelper.webapi.sdk.bean.logicengine.BeanMapper.class
        );
    }

    @Bean("logicengine.dispTaskVariableBeanTransformer")
    public BeanTransformer<DispTaskVariable, JSFixedFastJsonDispTaskVariable> dispTaskVariableBeanTransformer() {
        return new MapStructBeanTransformer<>(
                DispTaskVariable.class, JSFixedFastJsonDispTaskVariable.class,
                com.dwarfeng.familyhelper.webapi.sdk.bean.logicengine.BeanMapper.class
        );
    }
}
