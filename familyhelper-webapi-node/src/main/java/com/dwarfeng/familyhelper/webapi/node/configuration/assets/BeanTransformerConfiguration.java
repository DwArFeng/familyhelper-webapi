package com.dwarfeng.familyhelper.webapi.node.configuration.assets;

import com.dwarfeng.familyhelper.assets.sdk.bean.entity.*;
import com.dwarfeng.familyhelper.assets.stack.bean.entity.*;
import com.dwarfeng.familyhelper.webapi.sdk.bean.disp.assets.JSFixedFastJsonDispAssetCatalog;
import com.dwarfeng.familyhelper.webapi.sdk.bean.disp.assets.JSFixedFastJsonDispItem;
import com.dwarfeng.familyhelper.webapi.sdk.bean.disp.assets.JSFixedFastJsonDispPoac;
import com.dwarfeng.familyhelper.webapi.stack.bean.disp.assets.DispAssetCatalog;
import com.dwarfeng.familyhelper.webapi.stack.bean.disp.assets.DispItem;
import com.dwarfeng.familyhelper.webapi.stack.bean.disp.assets.DispPoac;
import com.dwarfeng.subgrade.impl.bean.DozerBeanTransformer;
import com.dwarfeng.subgrade.stack.bean.BeanTransformer;
import org.dozer.Mapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration("assets.beanTransformerConfiguration")
public class BeanTransformerConfiguration {

    private final Mapper mapper;

    public BeanTransformerConfiguration(Mapper mapper) {
        this.mapper = mapper;
    }

    @Bean("assets.assetCatalogBeanTransformer")
    public BeanTransformer<AssetCatalog, JSFixedFastJsonAssetCatalog> assetCatalogBeanTransformer() {
        return new DozerBeanTransformer<>(AssetCatalog.class, JSFixedFastJsonAssetCatalog.class, mapper);
    }

    @Bean("assets.dispAssetCatalogBeanTransformer")
    public BeanTransformer<DispAssetCatalog, JSFixedFastJsonDispAssetCatalog> dispAssetCatalogBeanTransformer() {
        return new DozerBeanTransformer<>(DispAssetCatalog.class, JSFixedFastJsonDispAssetCatalog.class, mapper);
    }

    @Bean
    public BeanTransformer<ItemTypeIndicator, FastJsonItemTypeIndicator>
    itemTypeIndicatorBeanTransformer() {
        return new DozerBeanTransformer<>(ItemTypeIndicator.class, FastJsonItemTypeIndicator.class, mapper);
    }

    @Bean("assets.poacBeanTransformer")
    public BeanTransformer<Poac, JSFixedFastJsonPoac> poacBeanTransformer() {
        return new DozerBeanTransformer<>(Poac.class, JSFixedFastJsonPoac.class, mapper);
    }

    @Bean("assets.dispPoacBeanTransformer")
    public BeanTransformer<DispPoac, JSFixedFastJsonDispPoac> dispPoacBeanTransformer() {
        return new DozerBeanTransformer<>(DispPoac.class, JSFixedFastJsonDispPoac.class, mapper);
    }

    @Bean("assets.itemBeanTransformer")
    public BeanTransformer<Item, JSFixedFastJsonItem> itemBeanTransformer() {
        return new DozerBeanTransformer<>(Item.class, JSFixedFastJsonItem.class, mapper);
    }

    @Bean("assets.dispItemBeanTransformer")
    public BeanTransformer<DispItem, JSFixedFastJsonDispItem> dispItemBeanTransformer() {
        return new DozerBeanTransformer<>(DispItem.class, JSFixedFastJsonDispItem.class, mapper);
    }

    @Bean
    public BeanTransformer<ItemLabel, FastJsonItemLabel>
    itemLabelBeanTransformer() {
        return new DozerBeanTransformer<>(ItemLabel.class, FastJsonItemLabel.class, mapper);
    }

    @Bean("assets.itemCoverInfoBeanTransformer")
    public BeanTransformer<ItemCoverInfo, JSFixedFastJsonItemCoverInfo> itemCoverInfoBeanTransformer() {
        return new DozerBeanTransformer<>(ItemCoverInfo.class, JSFixedFastJsonItemCoverInfo.class, mapper);
    }

    @Bean("assets.itemFileInfoBeanTransformer")
    public BeanTransformer<ItemFileInfo, JSFixedFastJsonItemFileInfo> itemFileInfoBeanTransformer() {
        return new DozerBeanTransformer<>(ItemFileInfo.class, JSFixedFastJsonItemFileInfo.class, mapper);
    }
}
