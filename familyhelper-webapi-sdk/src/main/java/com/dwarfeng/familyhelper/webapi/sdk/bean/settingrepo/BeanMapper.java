package com.dwarfeng.familyhelper.webapi.sdk.bean.settingrepo;

import com.dwarfeng.familyhelper.webapi.sdk.bean.settingrepo.disp.JSFixedFastJsonDispNavigationNodeItem;
import com.dwarfeng.familyhelper.webapi.sdk.bean.settingrepo.dto.*;
import com.dwarfeng.familyhelper.webapi.stack.bean.settingrepo.disp.DispNavigationNodeItem;
import com.dwarfeng.familyhelper.webapi.stack.bean.settingrepo.dto.*;
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

    // region Familyhelper-webapi Disp

    JSFixedFastJsonDispNavigationNodeItem dispNavigationNodeItemToFastJson(
            DispNavigationNodeItem dispNavigationNodeItem
    );

    @InheritInverseConfiguration
    DispNavigationNodeItem dispNavigationNodeItemFromFastJson(
            JSFixedFastJsonDispNavigationNodeItem JSFixedFastJsonDispNavigationNodeItem
    );

    // endregion

    // region Familyhelper-webapi DTO

    WebInputPublicFileListNodeFileDownloadInfo publicFileListNodeFileDownloadInfoToWebInput(
            PublicFileListNodeFileDownloadInfo publicFileListNodeFileDownloadInfo
    );

    @InheritInverseConfiguration
    PublicFileListNodeFileDownloadInfo publicFileListNodeFileDownloadInfoFromWebInput(
            WebInputPublicFileListNodeFileDownloadInfo webInputPublicFileListNodeFileDownloadInfo
    );

    WebInputPublicFileListNodeInspectInfo publicFileListNodeInspectInfoToWebInput(
            PublicFileListNodeInspectInfo publicFileListNodeInspectInfo
    );

    @InheritInverseConfiguration
    PublicFileListNodeInspectInfo publicFileListNodeInspectInfoFromWebInput(
            WebInputPublicFileListNodeInspectInfo webInputPublicFileListNodeInspectInfo
    );

    WebInputPublicFileListNodeSizeInfo publicFileListNodeSizeInfoToWebInput(
            PublicFileListNodeSizeInfo publicFileListNodeSizeInfo
    );

    @InheritInverseConfiguration
    PublicFileListNodeSizeInfo publicFileListNodeSizeInfoFromWebInput(
            WebInputPublicFileListNodeSizeInfo webInputPublicFileListNodeSizeInfo
    );

    WebInputPublicFileListNodeThumbnailDownloadInfo publicFileListNodeThumbnailDownloadInfoToWebInput(
            PublicFileListNodeThumbnailDownloadInfo publicFileListNodeThumbnailDownloadInfo
    );

    @InheritInverseConfiguration
    PublicFileListNodeThumbnailDownloadInfo publicFileListNodeThumbnailDownloadInfoFromWebInput(
            WebInputPublicFileListNodeThumbnailDownloadInfo webInputPublicFileListNodeThumbnailDownloadInfo
    );

    WebInputPublicFileNodeFileDownloadInfo publicFileNodeFileDownloadInfoToWebInput(
            PublicFileNodeFileDownloadInfo publicFileNodeFileDownloadInfo
    );

    @InheritInverseConfiguration
    PublicFileNodeFileDownloadInfo publicFileNodeFileDownloadInfoFromWebInput(
            WebInputPublicFileNodeFileDownloadInfo webInputPublicFileNodeFileDownloadInfo
    );

    WebInputPublicFileNodeInspectInfo publicFileNodeInspectInfoToWebInput(
            PublicFileNodeInspectInfo publicFileNodeInspectInfo
    );

    @InheritInverseConfiguration
    PublicFileNodeInspectInfo publicFileNodeInspectInfoFromWebInput(
            WebInputPublicFileNodeInspectInfo webInputPublicFileNodeInspectInfo
    );

    WebInputPublicFileNodeThumbnailDownloadInfo publicFileNodeThumbnailDownloadInfoToWebInput(
            PublicFileNodeThumbnailDownloadInfo publicFileNodeThumbnailDownloadInfo
    );

    @InheritInverseConfiguration
    PublicFileNodeThumbnailDownloadInfo publicFileNodeThumbnailDownloadInfoFromWebInput(
            WebInputPublicFileNodeThumbnailDownloadInfo webInputPublicFileNodeThumbnailDownloadInfo
    );

    WebInputPublicIahnNodeLocaleListInspectInfo publicIahnNodeLocaleListInspectInfoToWebInput(
            PublicIahnNodeLocaleListInspectInfo publicIahnNodeLocaleListInspectInfo
    );

    @InheritInverseConfiguration
    PublicIahnNodeLocaleListInspectInfo publicIahnNodeLocaleListInspectInfoFromWebInput(
            WebInputPublicIahnNodeLocaleListInspectInfo webInputPublicIahnNodeLocaleListInspectInfo
    );

    WebInputPublicIahnNodeMekListInspectInfo publicIahnNodeMekListInspectInfoToWebInput(
            PublicIahnNodeMekListInspectInfo publicIahnNodeMekListInspectInfo
    );

    @InheritInverseConfiguration
    PublicIahnNodeMekListInspectInfo publicIahnNodeMekListInspectInfoFromWebInput(
            WebInputPublicIahnNodeMekListInspectInfo webInputPublicIahnNodeMekListInspectInfo
    );

    WebInputPublicIahnNodeMessageInspectByLocaleInfo publicIahnNodeMessageInspectByLocaleInfoToWebInput(
            PublicIahnNodeMessageInspectByLocaleInfo publicIahnNodeMessageInspectByLocaleInfo
    );

    @InheritInverseConfiguration
    PublicIahnNodeMessageInspectByLocaleInfo publicIahnNodeMessageInspectByLocaleInfoFromWebInput(
            WebInputPublicIahnNodeMessageInspectByLocaleInfo webInputPublicIahnNodeMessageInspectByLocaleInfo
    );

    WebInputPublicIahnNodeMessageInspectInfo publicIahnNodeMessageInspectInfoToWebInput(
            PublicIahnNodeMessageInspectInfo publicIahnNodeMessageInspectInfo
    );

    @InheritInverseConfiguration
    PublicIahnNodeMessageInspectInfo publicIahnNodeMessageInspectInfoFromWebInput(
            WebInputPublicIahnNodeMessageInspectInfo webInputPublicIahnNodeMessageInspectInfo
    );

    WebInputPublicIahnNodeMessageTableInspectInfo publicIahnNodeMessageTableInspectInfoToWebInput(
            PublicIahnNodeMessageTableInspectInfo publicIahnNodeMessageTableInspectInfo
    );

    @InheritInverseConfiguration
    PublicIahnNodeMessageTableInspectInfo publicIahnNodeMessageTableInspectInfoFromWebInput(
            WebInputPublicIahnNodeMessageTableInspectInfo webInputPublicIahnNodeMessageTableInspectInfo
    );

    WebInputPublicImageListNodeFileDownloadInfo publicImageListNodeFileDownloadInfoToWebInput(
            PublicImageListNodeFileDownloadInfo publicImageListNodeFileDownloadInfo
    );

    @InheritInverseConfiguration
    PublicImageListNodeFileDownloadInfo publicImageListNodeFileDownloadInfoFromWebInput(
            WebInputPublicImageListNodeFileDownloadInfo webInputPublicImageListNodeFileDownloadInfo
    );

    WebInputPublicImageListNodeInspectInfo publicImageListNodeInspectInfoToWebInput(
            PublicImageListNodeInspectInfo publicImageListNodeInspectInfo
    );

    @InheritInverseConfiguration
    PublicImageListNodeInspectInfo publicImageListNodeInspectInfoFromWebInput(
            WebInputPublicImageListNodeInspectInfo webInputPublicImageListNodeInspectInfo
    );

    WebInputPublicImageListNodeSizeInfo publicImageListNodeSizeInfoToWebInput(
            PublicImageListNodeSizeInfo publicImageListNodeSizeInfo
    );

    @InheritInverseConfiguration
    PublicImageListNodeSizeInfo publicImageListNodeSizeInfoFromWebInput(
            WebInputPublicImageListNodeSizeInfo webInputPublicImageListNodeSizeInfo
    );

    WebInputPublicImageListNodeThumbnailDownloadInfo publicImageListNodeThumbnailDownloadInfoToWebInput(
            PublicImageListNodeThumbnailDownloadInfo publicImageListNodeThumbnailDownloadInfo
    );

    @InheritInverseConfiguration
    PublicImageListNodeThumbnailDownloadInfo publicImageListNodeThumbnailDownloadInfoFromWebInput(
            WebInputPublicImageListNodeThumbnailDownloadInfo webInputPublicImageListNodeThumbnailDownloadInfo
    );

    WebInputPublicImageNodeFileDownloadInfo publicImageNodeFileDownloadInfoToWebInput(
            PublicImageNodeFileDownloadInfo publicImageNodeFileDownloadInfo
    );

    @InheritInverseConfiguration
    PublicImageNodeFileDownloadInfo publicImageNodeFileDownloadInfoFromWebInput(
            WebInputPublicImageNodeFileDownloadInfo webInputPublicImageNodeFileDownloadInfo
    );

    WebInputPublicImageNodeInspectInfo publicImageNodeInspectInfoToWebInput(
            PublicImageNodeInspectInfo publicImageNodeInspectInfo
    );

    @InheritInverseConfiguration
    PublicImageNodeInspectInfo publicImageNodeInspectInfoFromWebInput(
            WebInputPublicImageNodeInspectInfo webInputPublicImageNodeInspectInfo
    );

    WebInputPublicImageNodeThumbnailDownloadInfo publicImageNodeThumbnailDownloadInfoToWebInput(
            PublicImageNodeThumbnailDownloadInfo publicImageNodeThumbnailDownloadInfo
    );

    @InheritInverseConfiguration
    PublicImageNodeThumbnailDownloadInfo publicImageNodeThumbnailDownloadInfoFromWebInput(
            WebInputPublicImageNodeThumbnailDownloadInfo webInputPublicImageNodeThumbnailDownloadInfo
    );

    WebInputPublicNavigationNodeInspectInfo publicNavigationNodeInspectInfoToWebInput(
            PublicNavigationNodeInspectInfo publicNavigationNodeInspectInfo
    );

    @InheritInverseConfiguration
    PublicNavigationNodeInspectInfo publicNavigationNodeInspectInfoFromWebInput(
            WebInputPublicNavigationNodeInspectInfo webInputPublicNavigationNodeInspectInfo
    );

    WebInputPublicNavigationNodeSizeInfo publicNavigationNodeSizeInfoToWebInput(
            PublicNavigationNodeSizeInfo publicNavigationNodeSizeInfo
    );

    @InheritInverseConfiguration
    PublicNavigationNodeSizeInfo publicNavigationNodeSizeInfoFromWebInput(
            WebInputPublicNavigationNodeSizeInfo webInputPublicNavigationNodeSizeInfo
    );

    WebInputPublicSettingNodeInspectInfo publicSettingNodeInspectInfoToWebInput(
            PublicSettingNodeInspectInfo publicSettingNodeInspectInfo
    );

    @InheritInverseConfiguration
    PublicSettingNodeInspectInfo publicSettingNodeInspectInfoFromWebInput(
            WebInputPublicSettingNodeInspectInfo webInputPublicSettingNodeInspectInfo
    );

    WebInputPublicTextNodeInspectInfo publicTextNodeInspectInfoToWebInput(
            PublicTextNodeInspectInfo publicTextNodeInspectInfo
    );

    @InheritInverseConfiguration
    PublicTextNodeInspectInfo publicTextNodeInspectInfoFromWebInput(
            WebInputPublicTextNodeInspectInfo webInputPublicTextNodeInspectInfo
    );

    WebInputPublicKvNodeCountInfo publicKvNodeCountInfoToWebInput(PublicKvNodeCountInfo publicKvNodeCountInfo);

    @InheritInverseConfiguration
    PublicKvNodeCountInfo publicKvNodeCountInfoFromWebInput(
            WebInputPublicKvNodeCountInfo webInputPublicKvNodeCountInfo
    );

    WebInputPublicKvNodeInspectInfo publicKvNodeInspectInfoToWebInput(PublicKvNodeInspectInfo publicKvNodeInspectInfo);

    @InheritInverseConfiguration
    PublicKvNodeInspectInfo publicKvNodeInspectInfoFromWebInput(
            WebInputPublicKvNodeInspectInfo webInputPublicKvNodeInspectInfo
    );

    WebInputPublicKvNodeItemInspectInfo publicKvNodeItemInspectInfoToWebInput(
            PublicKvNodeItemInspectInfo publicKvNodeItemInspectInfo
    );

    @InheritInverseConfiguration
    PublicKvNodeItemInspectInfo publicKvNodeItemInspectInfoFromWebInput(
            WebInputPublicKvNodeItemInspectInfo webInputPublicKvNodeItemInspectInfo
    );

    // endregion
}
