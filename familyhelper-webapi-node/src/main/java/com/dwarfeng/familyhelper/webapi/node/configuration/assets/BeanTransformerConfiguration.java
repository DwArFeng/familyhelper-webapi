package com.dwarfeng.familyhelper.webapi.node.configuration.assets;

import com.dwarfeng.familyhelper.assets.sdk.bean.entity.*;
import com.dwarfeng.familyhelper.assets.stack.bean.entity.*;
import com.dwarfeng.familyhelper.webapi.sdk.bean.assets.disp.JSFixedFastJsonDispAssetCatalog;
import com.dwarfeng.familyhelper.webapi.sdk.bean.assets.disp.JSFixedFastJsonDispItem;
import com.dwarfeng.familyhelper.webapi.sdk.bean.assets.disp.JSFixedFastJsonDispPoac;
import com.dwarfeng.familyhelper.webapi.stack.bean.assets.disp.DispAssetCatalog;
import com.dwarfeng.familyhelper.webapi.stack.bean.assets.disp.DispItem;
import com.dwarfeng.familyhelper.webapi.stack.bean.assets.disp.DispPoac;
import com.dwarfeng.subgrade.impl.bean.MapStructBeanTransformer;
import com.dwarfeng.subgrade.stack.bean.BeanTransformer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration("assets.beanTransformerConfiguration")
public class BeanTransformerConfiguration {

    @Bean("assets.assetCatalogBeanTransformer")
    public BeanTransformer<AssetCatalog, JSFixedFastJsonAssetCatalog> assetCatalogBeanTransformer() {
        return new MapStructBeanTransformer<>(
                AssetCatalog.class, JSFixedFastJsonAssetCatalog.class, FastJsonMapper.class
        );
    }

    @Bean("assets.dispAssetCatalogBeanTransformer")
    public BeanTransformer<DispAssetCatalog, JSFixedFastJsonDispAssetCatalog> dispAssetCatalogBeanTransformer() {
        return new MapStructBeanTransformer<>(
                DispAssetCatalog.class, JSFixedFastJsonDispAssetCatalog.class, FastJsonMapper.class
        );
    }

    @Bean
    public BeanTransformer<ItemTypeIndicator, FastJsonItemTypeIndicator> itemTypeIndicatorBeanTransformer() {
        return new MapStructBeanTransformer<>(
                ItemTypeIndicator.class, FastJsonItemTypeIndicator.class, FastJsonMapper.class
        );
    }

    @Bean("assets.poacBeanTransformer")
    public BeanTransformer<Poac, JSFixedFastJsonPoac> poacBeanTransformer() {
        return new MapStructBeanTransformer<>(Poac.class, JSFixedFastJsonPoac.class, FastJsonMapper.class);
    }

    @Bean("assets.dispPoacBeanTransformer")
    public BeanTransformer<DispPoac, JSFixedFastJsonDispPoac> dispPoacBeanTransformer() {
        return new MapStructBeanTransformer<>(DispPoac.class, JSFixedFastJsonDispPoac.class, FastJsonMapper.class);
    }

    @Bean("assets.itemBeanTransformer")
    public BeanTransformer<Item, JSFixedFastJsonItem> itemBeanTransformer() {
        return new MapStructBeanTransformer<>(Item.class, JSFixedFastJsonItem.class, FastJsonMapper.class);
    }

    @Bean("assets.dispItemBeanTransformer")
    public BeanTransformer<DispItem, JSFixedFastJsonDispItem> dispItemBeanTransformer() {
        return new MapStructBeanTransformer<>(DispItem.class, JSFixedFastJsonDispItem.class, FastJsonMapper.class);
    }

    @Bean
    public BeanTransformer<ItemLabel, FastJsonItemLabel>
    itemLabelBeanTransformer() {
        return new MapStructBeanTransformer<>(ItemLabel.class, FastJsonItemLabel.class, FastJsonMapper.class);
    }

    @Bean("assets.itemCoverInfoBeanTransformer")
    public BeanTransformer<ItemCoverInfo, JSFixedFastJsonItemCoverInfo> itemCoverInfoBeanTransformer() {
        return new MapStructBeanTransformer<>(
                ItemCoverInfo.class, JSFixedFastJsonItemCoverInfo.class, FastJsonMapper.class
        );
    }

    @Bean("assets.itemFileInfoBeanTransformer")
    public BeanTransformer<ItemFileInfo, JSFixedFastJsonItemFileInfo> itemFileInfoBeanTransformer() {
        return new MapStructBeanTransformer<>(
                ItemFileInfo.class, JSFixedFastJsonItemFileInfo.class, FastJsonMapper.class
        );
    }
}
