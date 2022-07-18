package com.dwarfeng.familyhelper.webapi.node.configuration.life;

import com.dwarfeng.familyhelper.life.sdk.bean.entity.JSFixedFastJsonPbItem;
import com.dwarfeng.familyhelper.life.sdk.bean.entity.JSFixedFastJsonPbNode;
import com.dwarfeng.familyhelper.life.sdk.bean.entity.JSFixedFastJsonPbSet;
import com.dwarfeng.familyhelper.life.sdk.bean.entity.JSFixedFastJsonPopb;
import com.dwarfeng.familyhelper.life.stack.bean.entity.PbItem;
import com.dwarfeng.familyhelper.life.stack.bean.entity.PbNode;
import com.dwarfeng.familyhelper.life.stack.bean.entity.PbSet;
import com.dwarfeng.familyhelper.life.stack.bean.entity.Popb;
import com.dwarfeng.familyhelper.webapi.sdk.bean.disp.life.JSFixedFastJsonDispPbItem;
import com.dwarfeng.familyhelper.webapi.sdk.bean.disp.life.JSFixedFastJsonDispPbNode;
import com.dwarfeng.familyhelper.webapi.sdk.bean.disp.life.JSFixedFastJsonDispPbSet;
import com.dwarfeng.familyhelper.webapi.sdk.bean.disp.life.JSFixedFastJsonDispPopb;
import com.dwarfeng.familyhelper.webapi.stack.bean.disp.life.DispPbItem;
import com.dwarfeng.familyhelper.webapi.stack.bean.disp.life.DispPbNode;
import com.dwarfeng.familyhelper.webapi.stack.bean.disp.life.DispPbSet;
import com.dwarfeng.familyhelper.webapi.stack.bean.disp.life.DispPopb;
import com.dwarfeng.subgrade.impl.bean.DozerBeanTransformer;
import com.dwarfeng.subgrade.stack.bean.BeanTransformer;
import org.dozer.Mapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration("life.beanTransformerConfiguration")
public class BeanTransformerConfiguration {

    private final Mapper mapper;

    public BeanTransformerConfiguration(Mapper mapper) {
        this.mapper = mapper;
    }

    @Bean("life.pbSetBeanTransformer")
    public BeanTransformer<PbSet, JSFixedFastJsonPbSet> pbSetBeanTransformer() {
        return new DozerBeanTransformer<>(PbSet.class, JSFixedFastJsonPbSet.class, mapper);
    }

    @Bean("life.dispPbSetBeanTransformer")
    public BeanTransformer<DispPbSet, JSFixedFastJsonDispPbSet> dispPbSetBeanTransformer() {
        return new DozerBeanTransformer<>(DispPbSet.class, JSFixedFastJsonDispPbSet.class, mapper);
    }

    @Bean("life.popbBeanTransformer")
    public BeanTransformer<Popb, JSFixedFastJsonPopb> popbBeanTransformer() {
        return new DozerBeanTransformer<>(Popb.class, JSFixedFastJsonPopb.class, mapper);
    }

    @Bean("life.dispPopbBeanTransformer")
    public BeanTransformer<DispPopb, JSFixedFastJsonDispPopb> dispPopbBeanTransformer() {
        return new DozerBeanTransformer<>(DispPopb.class, JSFixedFastJsonDispPopb.class, mapper);
    }

    @Bean("life.pbNodeBeanTransformer")
    public BeanTransformer<PbNode, JSFixedFastJsonPbNode> pbNodeBeanTransformer() {
        return new DozerBeanTransformer<>(PbNode.class, JSFixedFastJsonPbNode.class, mapper);
    }

    @Bean("life.dispPbNodeBeanTransformer")
    public BeanTransformer<DispPbNode, JSFixedFastJsonDispPbNode> dispPbNodeBeanTransformer() {
        return new DozerBeanTransformer<>(DispPbNode.class, JSFixedFastJsonDispPbNode.class, mapper);
    }

    @Bean("life.pbItemBeanTransformer")
    public BeanTransformer<PbItem, JSFixedFastJsonPbItem> pbItemBeanTransformer() {
        return new DozerBeanTransformer<>(PbItem.class, JSFixedFastJsonPbItem.class, mapper);
    }

    @Bean("life.dispPbItemBeanTransformer")
    public BeanTransformer<DispPbItem, JSFixedFastJsonDispPbItem> dispPbItemBeanTransformer() {
        return new DozerBeanTransformer<>(DispPbItem.class, JSFixedFastJsonDispPbItem.class, mapper);
    }
}
