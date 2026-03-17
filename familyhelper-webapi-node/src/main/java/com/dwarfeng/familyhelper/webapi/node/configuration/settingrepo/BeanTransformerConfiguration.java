package com.dwarfeng.familyhelper.webapi.node.configuration.settingrepo;

import com.dwarfeng.familyhelper.webapi.sdk.bean.settingrepo.disp.JSFixedFastJsonDispNavigationNodeItem;
import com.dwarfeng.familyhelper.webapi.stack.bean.settingrepo.disp.DispNavigationNodeItem;
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
        return new MapStructBeanTransformer<>(
                SettingNode.class, FastJsonSettingNode.class,
                com.dwarfeng.settingrepo.sdk.bean.BeanMapper.class
        );
    }

    @Bean("settingrepo.settingCategoryBeanTransformer")
    public BeanTransformer<SettingCategory, FastJsonSettingCategory> settingCategoryBeanTransformer() {
        return new MapStructBeanTransformer<>(
                SettingCategory.class, FastJsonSettingCategory.class,
                com.dwarfeng.settingrepo.sdk.bean.BeanMapper.class
        );
    }

    @Bean("settingrepo.formatterSupportBeanTransformer")
    public BeanTransformer<FormatterSupport, FastJsonFormatterSupport> formatterSupportBeanTransformer() {
        return new MapStructBeanTransformer<>(
                FormatterSupport.class, FastJsonFormatterSupport.class,
                com.dwarfeng.settingrepo.sdk.bean.BeanMapper.class
        );
    }

    @Bean("settingrepo.textNodeBeanTransformer")
    public BeanTransformer<TextNode, FastJsonTextNode> textNodeBeanTransformer() {
        return new MapStructBeanTransformer<>(
                TextNode.class, FastJsonTextNode.class,
                com.dwarfeng.settingrepo.sdk.bean.BeanMapper.class
        );
    }

    @Bean("settingrepo.imageNodeBeanTransformer")
    public BeanTransformer<ImageNode, FastJsonImageNode> imageNodeBeanTransformer() {
        return new MapStructBeanTransformer<>(
                ImageNode.class, FastJsonImageNode.class,
                com.dwarfeng.settingrepo.sdk.bean.BeanMapper.class
        );
    }

    @Bean("settingrepo.imageListNodeBeanTransformer")
    public BeanTransformer<ImageListNode, FastJsonImageListNode> imageListNodeBeanTransformer() {
        return new MapStructBeanTransformer<>(
                ImageListNode.class, FastJsonImageListNode.class,
                com.dwarfeng.settingrepo.sdk.bean.BeanMapper.class
        );
    }

    @Bean("settingrepo.iahnNodeBeanTransformer")
    public BeanTransformer<IahnNode, FastJsonIahnNode> iahnNodeBeanTransformer() {
        return new MapStructBeanTransformer<>(
                IahnNode.class, FastJsonIahnNode.class,
                com.dwarfeng.settingrepo.sdk.bean.BeanMapper.class
        );
    }

    @Bean("settingrepo.fileListNodeBeanTransformer")
    public BeanTransformer<FileListNode, FastJsonFileListNode> fileListNodeBeanTransformer() {
        return new MapStructBeanTransformer<>(
                FileListNode.class, FastJsonFileListNode.class,
                com.dwarfeng.settingrepo.sdk.bean.BeanMapper.class
        );
    }

    @Bean("settingrepo.fileNodeBeanTransformer")
    public BeanTransformer<FileNode, FastJsonFileNode> fileNodeBeanTransformer() {
        return new MapStructBeanTransformer<>(
                FileNode.class, FastJsonFileNode.class,
                com.dwarfeng.settingrepo.sdk.bean.BeanMapper.class
        );
    }

    @Bean("settingrepo.NavigationNodeBeanTransformer")
    public BeanTransformer<NavigationNode, FastJsonNavigationNode> navigationNodeBeanTransformer() {
        return new MapStructBeanTransformer<>(
                NavigationNode.class, FastJsonNavigationNode.class,
                com.dwarfeng.settingrepo.sdk.bean.BeanMapper.class
        );
    }

    @Bean("settingrepo.navigationNodeItemBeanTransformer")
    public BeanTransformer<NavigationNodeItem, FastJsonNavigationNodeItem> navigationNodeItemBeanTransformer() {
        return new MapStructBeanTransformer<>(
                NavigationNodeItem.class, FastJsonNavigationNodeItem.class,
                com.dwarfeng.settingrepo.sdk.bean.BeanMapper.class
        );
    }

    @Bean("settingrepo.dispNavigationNodeItemBeanTransformer")
    public BeanTransformer<DispNavigationNodeItem, JSFixedFastJsonDispNavigationNodeItem>
    dispNavigationNodeItemBeanTransformer() {
        return new MapStructBeanTransformer<>(
                DispNavigationNodeItem.class, JSFixedFastJsonDispNavigationNodeItem.class,
                com.dwarfeng.familyhelper.webapi.sdk.bean.settingrepo.BeanMapper.class
        );
    }
}
