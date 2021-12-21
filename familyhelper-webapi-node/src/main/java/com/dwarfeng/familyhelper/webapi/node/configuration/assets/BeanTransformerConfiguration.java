package com.dwarfeng.familyhelper.webapi.node.configuration.assets;

import com.dwarfeng.familyhelper.assets.sdk.bean.entity.JSFixedFastJsonAssetCatalog;
import com.dwarfeng.familyhelper.assets.sdk.bean.entity.JSFixedFastJsonPoac;
import com.dwarfeng.familyhelper.assets.stack.bean.entity.AssetCatalog;
import com.dwarfeng.familyhelper.assets.stack.bean.entity.Poac;
import com.dwarfeng.familyhelper.webapi.sdk.bean.disp.assets.JSFixedFastJsonDispAssetCatalog;
import com.dwarfeng.familyhelper.webapi.sdk.bean.disp.assets.JSFixedFastJsonDispPoac;
import com.dwarfeng.familyhelper.webapi.stack.bean.disp.assets.DispAssetCatalog;
import com.dwarfeng.familyhelper.webapi.stack.bean.disp.assets.DispPoac;
import com.dwarfeng.subgrade.impl.bean.DozerBeanTransformer;
import com.dwarfeng.subgrade.stack.bean.BeanTransformer;
import org.dozer.Mapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration("assetsBeanTransformerConfiguration")
public class BeanTransformerConfiguration {

    private final Mapper mapper;

    public BeanTransformerConfiguration(Mapper mapper) {
        this.mapper = mapper;
    }

    @Bean
    public BeanTransformer<AssetCatalog, JSFixedFastJsonAssetCatalog> assetCatalogBeanTransformer() {
        return new DozerBeanTransformer<>(AssetCatalog.class, JSFixedFastJsonAssetCatalog.class, mapper);
    }

    @Bean
    public BeanTransformer<DispAssetCatalog, JSFixedFastJsonDispAssetCatalog> dispAssetCatalogBeanTransformer() {
        return new DozerBeanTransformer<>(DispAssetCatalog.class, JSFixedFastJsonDispAssetCatalog.class, mapper);
    }

    @Bean
    public BeanTransformer<Poac, JSFixedFastJsonPoac> poacBeanTransformer() {
        return new DozerBeanTransformer<>(Poac.class, JSFixedFastJsonPoac.class, mapper);
    }

    @Bean
    public BeanTransformer<DispPoac, JSFixedFastJsonDispPoac> dispPoacBeanTransformer() {
        return new DozerBeanTransformer<>(DispPoac.class, JSFixedFastJsonDispPoac.class, mapper);
    }
}
