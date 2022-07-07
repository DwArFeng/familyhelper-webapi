package com.dwarfeng.familyhelper.webapi.node.configuration.clannad;

import com.dwarfeng.familyhelper.clannad.sdk.bean.entity.*;
import com.dwarfeng.familyhelper.clannad.stack.bean.entity.*;
import com.dwarfeng.familyhelper.webapi.sdk.bean.disp.clannad.FastJsonDispProfile;
import com.dwarfeng.familyhelper.webapi.sdk.bean.disp.clannad.JSFixedFastJsonDispCertificate;
import com.dwarfeng.familyhelper.webapi.sdk.bean.disp.clannad.JSFixedFastJsonDispPoce;
import com.dwarfeng.familyhelper.webapi.stack.bean.disp.clannad.DispCertificate;
import com.dwarfeng.familyhelper.webapi.stack.bean.disp.clannad.DispPoce;
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

    @Bean("clannad.certificateBeanTransformer")
    public BeanTransformer<Certificate, JSFixedFastJsonCertificate> certificateBeanTransformer() {
        return new DozerBeanTransformer<>(Certificate.class, JSFixedFastJsonCertificate.class, mapper);
    }

    @Bean("clannad.dispCertificateBeanTransformer")
    public BeanTransformer<DispCertificate, JSFixedFastJsonDispCertificate> dispCertificateBeanTransformer() {
        return new DozerBeanTransformer<>(DispCertificate.class, JSFixedFastJsonDispCertificate.class, mapper);
    }

    @Bean("clannad.certificateFileInfoBeanTransformer")
    public BeanTransformer<CertificateFileInfo, JSFixedFastJsonCertificateFileInfo>
    certificateFileInfoBeanTransformer() {
        return new DozerBeanTransformer<>(CertificateFileInfo.class, JSFixedFastJsonCertificateFileInfo.class, mapper);
    }

    @Bean("clannad.poceBeanTransformer")
    public BeanTransformer<Poce, JSFixedFastJsonPoce> poceBeanTransformer() {
        return new DozerBeanTransformer<>(Poce.class, JSFixedFastJsonPoce.class, mapper);
    }

    @Bean("clannad.dispPoceBeanTransformer")
    public BeanTransformer<DispPoce, JSFixedFastJsonDispPoce> dispPoceBeanTransformer() {
        return new DozerBeanTransformer<>(DispPoce.class, JSFixedFastJsonDispPoce.class, mapper);
    }
}
