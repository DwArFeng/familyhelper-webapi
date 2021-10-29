package com.dwarfeng.familyhelper.webapi.node.configuration.clannad;

import com.dwarfeng.familyhelper.clannad.sdk.bean.entity.FastJsonGenderTypeIndicator;
import com.dwarfeng.familyhelper.clannad.stack.bean.entity.GenderTypeIndicator;
import com.dwarfeng.subgrade.impl.bean.DozerBeanTransformer;
import com.dwarfeng.subgrade.stack.bean.BeanTransformer;
import org.dozer.Mapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration("clannadBeanTransformerConfiguration")
public class BeanTransformerConfiguration {

    private final Mapper mapper;

    public BeanTransformerConfiguration(Mapper mapper) {
        this.mapper = mapper;
    }

    @Bean
    public BeanTransformer<GenderTypeIndicator, FastJsonGenderTypeIndicator>
    genderTypeIndicatorBeanTransformer() {
        return new DozerBeanTransformer<>(GenderTypeIndicator.class, FastJsonGenderTypeIndicator.class, mapper);
    }
}
