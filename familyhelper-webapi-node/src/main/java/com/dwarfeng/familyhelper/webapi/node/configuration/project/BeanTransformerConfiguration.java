package com.dwarfeng.familyhelper.webapi.node.configuration.project;

import com.dwarfeng.familyhelper.project.sdk.bean.entity.*;
import com.dwarfeng.familyhelper.project.stack.bean.entity.*;
import com.dwarfeng.familyhelper.webapi.sdk.bean.disp.project.JSFixedFastJsonDispPop;
import com.dwarfeng.familyhelper.webapi.sdk.bean.disp.project.JSFixedFastJsonDispProject;
import com.dwarfeng.familyhelper.webapi.sdk.bean.disp.project.JSFixedFastJsonDispTask;
import com.dwarfeng.familyhelper.webapi.stack.bean.disp.project.DispPop;
import com.dwarfeng.familyhelper.webapi.stack.bean.disp.project.DispProject;
import com.dwarfeng.familyhelper.webapi.stack.bean.disp.project.DispTask;
import com.dwarfeng.subgrade.impl.bean.MapStructBeanTransformer;
import com.dwarfeng.subgrade.stack.bean.BeanTransformer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration("project.beanTransformerConfiguration")
public class BeanTransformerConfiguration {

    @Bean("project.projectBeanTransformer")
    public BeanTransformer<Project, JSFixedFastJsonProject> projectBeanTransformer() {
        return new MapStructBeanTransformer<>(Project.class, JSFixedFastJsonProject.class, FastJsonMapper.class);
    }

    @Bean("project.dispProjectBeanTransformer")
    public BeanTransformer<DispProject, JSFixedFastJsonDispProject> dispProjectBeanTransformer() {
        return new MapStructBeanTransformer<>(
                DispProject.class, JSFixedFastJsonDispProject.class, FastJsonMapper.class
        );
    }

    @Bean("project.popBeanTransformer")
    public BeanTransformer<Pop, JSFixedFastJsonPop> popBeanTransformer() {
        return new MapStructBeanTransformer<>(Pop.class, JSFixedFastJsonPop.class, FastJsonMapper.class);
    }

    @Bean("project.dispPopBeanTransformer")
    public BeanTransformer<DispPop, JSFixedFastJsonDispPop> dispPopBeanTransformer() {
        return new MapStructBeanTransformer<>(DispPop.class, JSFixedFastJsonDispPop.class, FastJsonMapper.class);
    }

    @Bean("project.taskTypeIndicatorBeanTransformer")
    public BeanTransformer<TaskTypeIndicator, FastJsonTaskTypeIndicator>
    taskTypeIndicatorBeanTransformer() {
        return new MapStructBeanTransformer<>(
                TaskTypeIndicator.class, FastJsonTaskTypeIndicator.class, FastJsonMapper.class
        );
    }

    @Bean("project.taskBeanTransformer")
    public BeanTransformer<Task, JSFixedFastJsonTask> taskBeanTransformer() {
        return new MapStructBeanTransformer<>(Task.class, JSFixedFastJsonTask.class, FastJsonMapper.class);
    }

    @Bean("project.dispTaskBeanTransformer")
    public BeanTransformer<DispTask, JSFixedFastJsonDispTask> dispTaskBeanTransformer() {
        return new MapStructBeanTransformer<>(DispTask.class, JSFixedFastJsonDispTask.class, FastJsonMapper.class);
    }

    @Bean("project.memoBeanTransformer")
    public BeanTransformer<Memo, JSFixedFastJsonMemo> memoBeanTransformer() {
        return new MapStructBeanTransformer<>(Memo.class, JSFixedFastJsonMemo.class, FastJsonMapper.class);
    }

    @Bean("project.memoFileInfoBeanTransformer")
    public BeanTransformer<MemoFileInfo, JSFixedFastJsonMemoFileInfo> memoFileInfoBeanTransformer() {
        return new MapStructBeanTransformer<>(
                MemoFileInfo.class, JSFixedFastJsonMemoFileInfo.class, FastJsonMapper.class
        );
    }

    @Bean("project.memoRemindDriverInfoBeanTransformer")
    public BeanTransformer<MemoRemindDriverInfo, JSFixedFastJsonMemoRemindDriverInfo>
    memoRemindDriverInfoBeanTransformer() {
        return new MapStructBeanTransformer<>(
                MemoRemindDriverInfo.class, JSFixedFastJsonMemoRemindDriverInfo.class, FastJsonMapper.class
        );
    }

    @Bean("project.memoRemindDriverSupportBeanTransformer")
    public BeanTransformer<MemoRemindDriverSupport, FastJsonMemoRemindDriverSupport>
    memoRemindDriverSupportBeanTransformer() {
        return new MapStructBeanTransformer<>(
                MemoRemindDriverSupport.class, FastJsonMemoRemindDriverSupport.class, FastJsonMapper.class
        );
    }
}
