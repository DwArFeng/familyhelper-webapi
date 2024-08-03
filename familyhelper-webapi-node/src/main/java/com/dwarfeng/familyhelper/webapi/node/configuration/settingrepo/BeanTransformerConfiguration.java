package com.dwarfeng.familyhelper.webapi.node.configuration.settingrepo;

import com.dwarfeng.settingrepo.sdk.bean.entity.*;
import com.dwarfeng.settingrepo.stack.bean.entity.*;
import com.dwarfeng.subgrade.impl.bean.DozerBeanTransformer;
import com.dwarfeng.subgrade.stack.bean.BeanTransformer;
import org.dozer.Mapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration("settingrepo.beanTransformerConfiguration")
public class BeanTransformerConfiguration {

    private final Mapper mapper;

    public BeanTransformerConfiguration(Mapper mapper) {
        this.mapper = mapper;
    }

    @Bean("settingrepo.settingNodeBeanTransformer")
    public BeanTransformer<SettingNode, FastJsonSettingNode> settingNodeBeanTransformer() {
        return new DozerBeanTransformer<>(SettingNode.class, FastJsonSettingNode.class, mapper);
    }

    @Bean("settingrepo.settingCategoryBeanTransformer")
    public BeanTransformer<SettingCategory, FastJsonSettingCategory> settingCategoryBeanTransformer() {
        return new DozerBeanTransformer<>(SettingCategory.class, FastJsonSettingCategory.class, mapper);
    }

    @Bean("settingrepo.formatterSupportBeanTransformer")
    public BeanTransformer<FormatterSupport, FastJsonFormatterSupport> formatterSupportBeanTransformer() {
        return new DozerBeanTransformer<>(FormatterSupport.class, FastJsonFormatterSupport.class, mapper);
    }

    @Bean("settingrepo.textNodeBeanTransformer")
    public BeanTransformer<TextNode, FastJsonTextNode> textNodeBeanTransformer() {
        return new DozerBeanTransformer<>(TextNode.class, FastJsonTextNode.class, mapper);
    }

    @Bean("settingrepo.imageNodeBeanTransformer")
    public BeanTransformer<ImageNode, FastJsonImageNode> imageNodeBeanTransformer() {
        return new DozerBeanTransformer<>(ImageNode.class, FastJsonImageNode.class, mapper);
    }

    @Bean("settingrepo.imageListNodeBeanTransformer")
    public BeanTransformer<ImageListNode, FastJsonImageListNode> imageListNodeBeanTransformer() {
        return new DozerBeanTransformer<>(ImageListNode.class, FastJsonImageListNode.class, mapper);
    }
}
