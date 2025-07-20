package com.dwarfeng.familyhelper.webapi.sdk.bean.settingrepo.dto;

import com.alibaba.fastjson.annotation.JSONField;
import com.dwarfeng.familyhelper.webapi.stack.bean.settingrepo.dto.PublicImageListNodeThumbnailDownloadInfo;
import com.dwarfeng.subgrade.stack.bean.dto.Dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import java.util.Arrays;
import java.util.Objects;

/**
 * WebInput 公共图片列表节点缩略图下载信息。
 *
 * @author DwArFeng
 * @since 1.7.0
 */
public class WebInputPublicImageListNodeThumbnailDownloadInfo implements Dto {

    private static final long serialVersionUID = -6441825276786854484L;

    public static PublicImageListNodeThumbnailDownloadInfo toStackBean(
            WebInputPublicImageListNodeThumbnailDownloadInfo webInput
    ) {
        if (Objects.isNull(webInput)) {
            return null;
        } else {
            return new PublicImageListNodeThumbnailDownloadInfo(
                    webInput.getArgs(),
                    webInput.getIndex()
            );
        }
    }

    @JSONField(name = "args")
    @NotNull
    private String[] args;

    @JSONField(name = "index")
    @PositiveOrZero
    private int index;

    public WebInputPublicImageListNodeThumbnailDownloadInfo() {
    }

    public String[] getArgs() {
        return args;
    }

    public void setArgs(String[] args) {
        this.args = args;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    @Override
    public String toString() {
        return "WebInputPublicImageListNodeThumbnailDownloadInfo{" +
                "args=" + Arrays.toString(args) +
                ", index=" + index +
                '}';
    }
}
