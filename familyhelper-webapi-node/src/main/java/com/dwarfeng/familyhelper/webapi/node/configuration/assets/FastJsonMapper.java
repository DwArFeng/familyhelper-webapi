package com.dwarfeng.familyhelper.webapi.node.configuration.assets;

import com.dwarfeng.familyhelper.assets.sdk.bean.entity.*;
import com.dwarfeng.familyhelper.assets.stack.bean.entity.*;
import com.dwarfeng.familyhelper.webapi.sdk.bean.disp.assets.JSFixedFastJsonDispAssetCatalog;
import com.dwarfeng.familyhelper.webapi.sdk.bean.disp.assets.JSFixedFastJsonDispItem;
import com.dwarfeng.familyhelper.webapi.sdk.bean.disp.assets.JSFixedFastJsonDispPoac;
import com.dwarfeng.familyhelper.webapi.stack.bean.disp.assets.DispAssetCatalog;
import com.dwarfeng.familyhelper.webapi.stack.bean.disp.assets.DispItem;
import com.dwarfeng.familyhelper.webapi.stack.bean.disp.assets.DispPoac;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;

/**
 * FastJson Bean 映射器。
 *
 * @author DwArFeng
 * @since 1.4.0
 */
@Mapper
public interface FastJsonMapper {

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
