package com.dwarfeng.familyhelper.webapi.node.configuration.project;

import com.dwarfeng.familyhelper.project.sdk.bean.entity.FastJsonTaskTypeIndicator;
import com.dwarfeng.familyhelper.project.sdk.bean.entity.JSFixedFastJsonPop;
import com.dwarfeng.familyhelper.project.sdk.bean.entity.JSFixedFastJsonProject;
import com.dwarfeng.familyhelper.project.stack.bean.entity.Pop;
import com.dwarfeng.familyhelper.project.stack.bean.entity.Project;
import com.dwarfeng.familyhelper.project.stack.bean.entity.TaskTypeIndicator;
import com.dwarfeng.familyhelper.webapi.sdk.bean.disp.project.JSFixedFastJsonDispPop;
import com.dwarfeng.familyhelper.webapi.sdk.bean.disp.project.JSFixedFastJsonDispProject;
import com.dwarfeng.familyhelper.webapi.stack.bean.disp.project.DispPop;
import com.dwarfeng.familyhelper.webapi.stack.bean.disp.project.DispProject;
import com.dwarfeng.subgrade.impl.bean.DozerBeanTransformer;
import com.dwarfeng.subgrade.stack.bean.BeanTransformer;
import org.dozer.Mapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration("projectBeanTransformerConfiguration")
public class BeanTransformerConfiguration {

    private final Mapper mapper;

    public BeanTransformerConfiguration(Mapper mapper) {
        this.mapper = mapper;
    }

    @Bean
    public BeanTransformer<Project, JSFixedFastJsonProject> projectBeanTransformer() {
        return new DozerBeanTransformer<>(Project.class, JSFixedFastJsonProject.class, mapper);
    }

    @Bean
    public BeanTransformer<DispProject, JSFixedFastJsonDispProject> dispProjectBeanTransformer() {
        return new DozerBeanTransformer<>(DispProject.class, JSFixedFastJsonDispProject.class, mapper);
    }

    @Bean
    public BeanTransformer<Pop, JSFixedFastJsonPop> popBeanTransformer() {
        return new DozerBeanTransformer<>(Pop.class, JSFixedFastJsonPop.class, mapper);
    }

    @Bean
    public BeanTransformer<DispPop, JSFixedFastJsonDispPop> dispPopBeanTransformer() {
        return new DozerBeanTransformer<>(DispPop.class, JSFixedFastJsonDispPop.class, mapper);
    }

    @Bean
    public BeanTransformer<TaskTypeIndicator, FastJsonTaskTypeIndicator>
    taskTypeIndicatorBeanTransformer() {
        return new DozerBeanTransformer<>(TaskTypeIndicator.class, FastJsonTaskTypeIndicator.class, mapper);
    }
}
