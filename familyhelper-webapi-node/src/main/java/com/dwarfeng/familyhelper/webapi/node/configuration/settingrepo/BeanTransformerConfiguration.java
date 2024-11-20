package com.dwarfeng.familyhelper.webapi.node.configuration.settingrepo;

import com.dwarfeng.settingrepo.sdk.bean.entity.*;
import com.dwarfeng.settingrepo.stack.bean.entity.*;
import com.dwarfeng.subgrade.impl.bean.MapStructBeanTransformer;
import com.dwarfeng.subgrade.stack.bean.BeanTransformer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration("settingrepo.beanTransformerConfiguration")
public class BeanTransformerConfiguration {

    @Bean("settingrepo.settingNodeBeanTransformer")
    public BeanTransformer<SettingNode, FastJsonSettingNode> settingNodeBeanTransformer() {
        return new MapStructBeanTransformer<>(SettingNode.class, FastJsonSettingNode.class, FastJsonMapper.class);
    }

    @Bean("settingrepo.settingCategoryBeanTransformer")
    public BeanTransformer<SettingCategory, FastJsonSettingCategory> settingCategoryBeanTransformer() {
        return new MapStructBeanTransformer<>(
                SettingCategory.class, FastJsonSettingCategory.class, FastJsonMapper.class
        );
    }

    @Bean("settingrepo.formatterSupportBeanTransformer")
    public BeanTransformer<FormatterSupport, FastJsonFormatterSupport> formatterSupportBeanTransformer() {
        return new MapStructBeanTransformer<>(
                FormatterSupport.class, FastJsonFormatterSupport.class, FastJsonMapper.class
        );
    }

    @Bean("settingrepo.textNodeBeanTransformer")
    public BeanTransformer<TextNode, FastJsonTextNode> textNodeBeanTransformer() {
        return new MapStructBeanTransformer<>(TextNode.class, FastJsonTextNode.class, FastJsonMapper.class);
    }

    @Bean("settingrepo.imageNodeBeanTransformer")
    public BeanTransformer<ImageNode, FastJsonImageNode> imageNodeBeanTransformer() {
        return new MapStructBeanTransformer<>(ImageNode.class, FastJsonImageNode.class, FastJsonMapper.class);
    }

    @Bean("settingrepo.imageListNodeBeanTransformer")
    public BeanTransformer<ImageListNode, FastJsonImageListNode> imageListNodeBeanTransformer() {
        return new MapStructBeanTransformer<>(ImageListNode.class, FastJsonImageListNode.class, FastJsonMapper.class);
    }
}
