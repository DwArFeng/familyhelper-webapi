package com.dwarfeng.familyhelper.webapi.sdk.bean.assets;

import com.dwarfeng.familyhelper.assets.sdk.bean.entity.*;
import com.dwarfeng.familyhelper.assets.stack.bean.entity.*;
import com.dwarfeng.familyhelper.webapi.sdk.bean.assets.disp.JSFixedFastJsonDispAssetCatalog;
import com.dwarfeng.familyhelper.webapi.sdk.bean.assets.disp.JSFixedFastJsonDispItem;
import com.dwarfeng.familyhelper.webapi.sdk.bean.assets.disp.JSFixedFastJsonDispPoac;
import com.dwarfeng.familyhelper.webapi.stack.bean.assets.disp.DispAssetCatalog;
import com.dwarfeng.familyhelper.webapi.stack.bean.assets.disp.DispItem;
import com.dwarfeng.familyhelper.webapi.stack.bean.assets.disp.DispPoac;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;

/**
 * Bean 映射器。
 *
 * <p>
 * 该映射器中包含了 <code>sdk</code> 模块中所有实体与 <code>stack</code> 模块中对应实体的映射方法。
 *
 * @author DwArFeng
 * @since 1.6.0
 */
@Mapper
public interface BeanMapper {

    JSFixedFastJsonAssetCatalog assetCatalogToJsFixedFastJson(AssetCatalog assetCatalog);

    @InheritInverseConfiguration
    AssetCatalog assetCatalogFromJsFixedFastJson(JSFixedFastJsonAssetCatalog jsFixedFastJsonAssetCatalog);

    JSFixedFastJsonDispAssetCatalog dispAssetCatalogToJsFixedFastJson(DispAssetCatalog dispAssetCatalog);

    @InheritInverseConfiguration
    DispAssetCatalog dispAssetCatalogFromJsFixedFastJson(
            JSFixedFastJsonDispAssetCatalog jsFixedFastJsonDispAssetCatalog
    );

    FastJsonItemTypeIndicator itemTypeIndicatorToFastJson(ItemTypeIndicator itemTypeIndicator);

    @InheritInverseConfiguration
    ItemTypeIndicator itemTypeIndicatorFromFastJson(FastJsonItemTypeIndicator fastJsonItemTypeIndicator);

    JSFixedFastJsonPoac poacToJsFixedFastJson(Poac poac);

    @InheritInverseConfiguration
    Poac poacFromJsFixedFastJson(JSFixedFastJsonPoac jsFixedFastJsonPoac);

    JSFixedFastJsonDispPoac dispPoacToJsFixedFastJson(DispPoac dispPoac);

    @InheritInverseConfiguration
    DispPoac dispPoacFromJsFixedFastJson(JSFixedFastJsonDispPoac jsFixedFastJsonDispPoac);

    JSFixedFastJsonItem itemToJsFixedFastJson(Item item);

    @InheritInverseConfiguration
    Item itemFromJsFixedFastJson(JSFixedFastJsonItem jsFixedFastJsonItem);

    JSFixedFastJsonDispItem dispItemToJsFixedFastJson(DispItem dispItem);

    @InheritInverseConfiguration
    DispItem dispItemFromJsFixedFastJson(JSFixedFastJsonDispItem jsFixedFastJsonDispItem);

    FastJsonItemLabel itemLabelToFastJson(ItemLabel itemLabel);

    @InheritInverseConfiguration
    ItemLabel itemLabelFromFastJson(FastJsonItemLabel fastJsonItemLabel);

    JSFixedFastJsonItemCoverInfo itemCoverInfoToJsFixedFastJson(ItemCoverInfo itemCoverInfo);

    @InheritInverseConfiguration
    ItemCoverInfo itemCoverInfoFromJsFixedFastJson(JSFixedFastJsonItemCoverInfo jsFixedFastJsonItemCoverInfo);

    JSFixedFastJsonItemFileInfo itemFileInfoToJsFixedFastJson(ItemFileInfo itemFileInfo);

    @InheritInverseConfiguration
    ItemFileInfo itemFileInfoFromJsFixedFastJson(JSFixedFastJsonItemFileInfo jsFixedFastJsonItemFileInfo);
}
