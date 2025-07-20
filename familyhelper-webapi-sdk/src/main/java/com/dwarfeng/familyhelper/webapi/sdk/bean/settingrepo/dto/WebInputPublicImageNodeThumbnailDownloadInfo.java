package com.dwarfeng.familyhelper.webapi.sdk.bean.settingrepo.dto;

import com.alibaba.fastjson.annotation.JSONField;
import com.dwarfeng.familyhelper.webapi.stack.bean.settingrepo.dto.PublicImageNodeThumbnailDownloadInfo;
import com.dwarfeng.subgrade.stack.bean.dto.Dto;

import javax.validation.constraints.NotNull;
import java.util.Arrays;
import java.util.Objects;

/**
 * WebInput 公共图片节点缩略图下载信息。
 *
 * @author DwArFeng
 * @since 1.7.0
 */
public class WebInputPublicImageNodeThumbnailDownloadInfo implements Dto {

    private static final long serialVersionUID = 764600831233291821L;

    public static PublicImageNodeThumbnailDownloadInfo toStackBean(
            WebInputPublicImageNodeThumbnailDownloadInfo webInput
    ) {
        if (Objects.isNull(webInput)) {
            return null;
        } else {
            return new PublicImageNodeThumbnailDownloadInfo(
                    webInput.getArgs()
            );
        }
    }

    @JSONField(name = "args")
    @NotNull
    private String[] args;

    public WebInputPublicImageNodeThumbnailDownloadInfo() {
    }

    public String[] getArgs() {
        return args;
    }

    public void setArgs(String[] args) {
        this.args = args;
    }

    @Override
    public String toString() {
        return "WebInputPublicImageNodeThumbnailDownloadInfo{" +
                "args=" + Arrays.toString(args) +
                '}';
    }
}
