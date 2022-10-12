package com.dwarfeng.familyhelper.webapi.node.configuration.notify;

import com.dwarfeng.familyhelper.plugin.notify.bean.entity.FastJsonSendExecutorSupport;
import com.dwarfeng.familyhelper.plugin.notify.bean.entity.SendExecutorSupport;
import com.dwarfeng.familyhelper.webapi.sdk.bean.vo.notify.FastJsonTopic;
import com.dwarfeng.familyhelper.webapi.sdk.bean.vo.notify.JSFixedFastJsonNotifySetting;
import com.dwarfeng.familyhelper.webapi.stack.bean.vo.notify.NotifySetting;
import com.dwarfeng.familyhelper.webapi.stack.bean.vo.notify.Topic;
import com.dwarfeng.subgrade.impl.bean.DozerBeanTransformer;
import com.dwarfeng.subgrade.stack.bean.BeanTransformer;
import org.dozer.Mapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration("notify.beanTransformerConfiguration")
public class BeanTransformerConfiguration {

    @SuppressWarnings("FieldCanBeLocal")
    private final Mapper mapper;

    public BeanTransformerConfiguration(Mapper mapper) {
        this.mapper = mapper;
    }

    @Bean("notify.notifySettingBeanTransformer")
    public BeanTransformer<NotifySetting, JSFixedFastJsonNotifySetting> notifySettingBeanTransformer() {
        return new DozerBeanTransformer<>(NotifySetting.class, JSFixedFastJsonNotifySetting.class, mapper);
    }

    @Bean("notify.topicBeanTransformer")
    public BeanTransformer<Topic, FastJsonTopic> topicBeanTransformer() {
        return new DozerBeanTransformer<>(Topic.class, FastJsonTopic.class, mapper);
    }

    @Bean("notify.sendExecutorSupportBeanTransformer")
    public BeanTransformer<SendExecutorSupport, FastJsonSendExecutorSupport> sendExecutorSupportBeanTransformer() {
        return new DozerBeanTransformer<>(SendExecutorSupport.class, FastJsonSendExecutorSupport.class, mapper);
    }
}
