package com.dwarfeng.familyhelper.webapi.node.configuration.clannad;

import com.dwarfeng.familyhelper.clannad.sdk.bean.entity.FastJsonNickname;
import com.dwarfeng.familyhelper.clannad.sdk.bean.entity.FastJsonProfile;
import com.dwarfeng.familyhelper.clannad.sdk.bean.entity.FastJsonProfileTypeIndicator;
import com.dwarfeng.familyhelper.clannad.sdk.bean.entity.JSFixedFastJsonNotification;
import com.dwarfeng.familyhelper.clannad.stack.bean.entity.Nickname;
import com.dwarfeng.familyhelper.clannad.stack.bean.entity.Notification;
import com.dwarfeng.familyhelper.clannad.stack.bean.entity.Profile;
import com.dwarfeng.familyhelper.clannad.stack.bean.entity.ProfileTypeIndicator;
import com.dwarfeng.familyhelper.webapi.sdk.bean.disp.clannad.FastJsonDispProfile;
import com.dwarfeng.familyhelper.webapi.stack.bean.disp.clannad.DispProfile;
import com.dwarfeng.subgrade.impl.bean.DozerBeanTransformer;
import com.dwarfeng.subgrade.stack.bean.BeanTransformer;
import org.dozer.Mapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration("clannad.beanTransformerConfiguration")
public class BeanTransformerConfiguration {

    private final Mapper mapper;

    public BeanTransformerConfiguration(Mapper mapper) {
        this.mapper = mapper;
    }

    @Bean("clannad.profileBeanTransformer")
    public BeanTransformer<Profile, FastJsonProfile> profileBeanTransformer() {
        return new DozerBeanTransformer<>(Profile.class, FastJsonProfile.class, mapper);
    }

    @Bean("clannad.dispProfileBeanTransformer")
    public BeanTransformer<DispProfile, FastJsonDispProfile> dispProfileBeanTransformer() {
        return new DozerBeanTransformer<>(DispProfile.class, FastJsonDispProfile.class, mapper);
    }

    @Bean("clannad.profileTypeIndicatorBeanTransformer")
    public BeanTransformer<ProfileTypeIndicator, FastJsonProfileTypeIndicator>
    profileTypeIndicatorBeanTransformer() {
        return new DozerBeanTransformer<>(ProfileTypeIndicator.class, FastJsonProfileTypeIndicator.class, mapper);
    }

    @Bean("clannad.nicknameBeanTransformer")
    public BeanTransformer<Nickname, FastJsonNickname> nicknameBeanTransformer() {
        return new DozerBeanTransformer<>(Nickname.class, FastJsonNickname.class, mapper);
    }

    @Bean("clannad.notificationBeanTransformer")
    public BeanTransformer<Notification, JSFixedFastJsonNotification> notificationBeanTransformer() {
        return new DozerBeanTransformer<>(Notification.class, JSFixedFastJsonNotification.class, mapper);
    }
}
