package com.dwarfeng.familyhelper.webapi.node.configuration.clannad;

import com.dwarfeng.familyhelper.clannad.sdk.bean.entity.*;
import com.dwarfeng.familyhelper.clannad.stack.bean.entity.*;
import com.dwarfeng.familyhelper.webapi.sdk.bean.disp.clannad.FastJsonDispProfile;
import com.dwarfeng.familyhelper.webapi.sdk.bean.disp.clannad.JSFixedFastJsonDispCertificate;
import com.dwarfeng.familyhelper.webapi.sdk.bean.disp.clannad.JSFixedFastJsonDispPoce;
import com.dwarfeng.familyhelper.webapi.stack.bean.disp.clannad.DispCertificate;
import com.dwarfeng.familyhelper.webapi.stack.bean.disp.clannad.DispPoce;
import com.dwarfeng.familyhelper.webapi.stack.bean.disp.clannad.DispProfile;
import com.dwarfeng.subgrade.impl.bean.MapStructBeanTransformer;
import com.dwarfeng.subgrade.stack.bean.BeanTransformer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration("clannad.beanTransformerConfiguration")
public class BeanTransformerConfiguration {

    @Bean("clannad.profileBeanTransformer")
    public BeanTransformer<Profile, FastJsonProfile> profileBeanTransformer() {
        return new MapStructBeanTransformer<>(Profile.class, FastJsonProfile.class, FastJsonMapper.class);
    }

    @Bean("clannad.dispProfileBeanTransformer")
    public BeanTransformer<DispProfile, FastJsonDispProfile> dispProfileBeanTransformer() {
        return new MapStructBeanTransformer<>(DispProfile.class, FastJsonDispProfile.class, FastJsonMapper.class);
    }

    @Bean("clannad.profileTypeIndicatorBeanTransformer")
    public BeanTransformer<ProfileTypeIndicator, FastJsonProfileTypeIndicator>
    profileTypeIndicatorBeanTransformer() {
        return new MapStructBeanTransformer<>(
                ProfileTypeIndicator.class, FastJsonProfileTypeIndicator.class, FastJsonMapper.class
        );
    }

    @Bean("clannad.nicknameBeanTransformer")
    public BeanTransformer<Nickname, FastJsonNickname> nicknameBeanTransformer() {
        return new MapStructBeanTransformer<>(Nickname.class, FastJsonNickname.class, FastJsonMapper.class);
    }

    @Bean("clannad.notificationBeanTransformer")
    public BeanTransformer<Notification, JSFixedFastJsonNotification> notificationBeanTransformer() {
        return new MapStructBeanTransformer<>(
                Notification.class, JSFixedFastJsonNotification.class, FastJsonMapper.class
        );
    }

    @Bean("clannad.certificateBeanTransformer")
    public BeanTransformer<Certificate, JSFixedFastJsonCertificate> certificateBeanTransformer() {
        return new MapStructBeanTransformer<>(
                Certificate.class, JSFixedFastJsonCertificate.class, FastJsonMapper.class
        );
    }

    @Bean("clannad.dispCertificateBeanTransformer")
    public BeanTransformer<DispCertificate, JSFixedFastJsonDispCertificate> dispCertificateBeanTransformer() {
        return new MapStructBeanTransformer<>(
                DispCertificate.class, JSFixedFastJsonDispCertificate.class, FastJsonMapper.class
        );
    }

    @Bean("clannad.certificateFileInfoBeanTransformer")
    public BeanTransformer<CertificateFileInfo, JSFixedFastJsonCertificateFileInfo>
    certificateFileInfoBeanTransformer() {
        return new MapStructBeanTransformer<>(
                CertificateFileInfo.class, JSFixedFastJsonCertificateFileInfo.class, FastJsonMapper.class
        );
    }

    @Bean("clannad.poceBeanTransformer")
    public BeanTransformer<Poce, JSFixedFastJsonPoce> poceBeanTransformer() {
        return new MapStructBeanTransformer<>(Poce.class, JSFixedFastJsonPoce.class, FastJsonMapper.class);
    }

    @Bean("clannad.dispPoceBeanTransformer")
    public BeanTransformer<DispPoce, JSFixedFastJsonDispPoce> dispPoceBeanTransformer() {
        return new MapStructBeanTransformer<>(DispPoce.class, JSFixedFastJsonDispPoce.class, FastJsonMapper.class);
    }
}
