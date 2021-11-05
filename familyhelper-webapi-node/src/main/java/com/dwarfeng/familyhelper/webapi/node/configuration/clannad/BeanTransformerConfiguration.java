package com.dwarfeng.familyhelper.webapi.node.configuration.clannad;

import com.dwarfeng.familyhelper.clannad.sdk.bean.entity.FastJsonProfile;
import com.dwarfeng.familyhelper.clannad.sdk.bean.entity.FastJsonProfileTypeIndicator;
import com.dwarfeng.familyhelper.clannad.stack.bean.entity.Profile;
import com.dwarfeng.familyhelper.clannad.stack.bean.entity.ProfileTypeIndicator;
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
    public BeanTransformer<Profile, FastJsonProfile> profileBeanTransformer() {
        return new DozerBeanTransformer<>(Profile.class, FastJsonProfile.class, mapper);
    }

    @Bean
    public BeanTransformer<ProfileTypeIndicator, FastJsonProfileTypeIndicator>
    profileTypeIndicatorBeanTransformer() {
        return new DozerBeanTransformer<>(ProfileTypeIndicator.class, FastJsonProfileTypeIndicator.class, mapper);
    }
}
