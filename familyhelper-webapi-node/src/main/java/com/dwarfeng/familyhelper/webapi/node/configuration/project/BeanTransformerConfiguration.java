package com.dwarfeng.familyhelper.webapi.node.configuration.project;

import com.dwarfeng.familyhelper.project.sdk.bean.entity.FastJsonTaskTypeIndicator;
import com.dwarfeng.familyhelper.project.stack.bean.entity.TaskTypeIndicator;
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
    public BeanTransformer<TaskTypeIndicator, FastJsonTaskTypeIndicator>
    taskTypeIndicatorBeanTransformer() {
        return new DozerBeanTransformer<>(TaskTypeIndicator.class, FastJsonTaskTypeIndicator.class, mapper);
    }
}
