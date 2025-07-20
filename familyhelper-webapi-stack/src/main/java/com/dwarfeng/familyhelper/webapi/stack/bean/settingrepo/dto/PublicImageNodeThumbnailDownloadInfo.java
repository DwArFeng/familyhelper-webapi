package com.dwarfeng.familyhelper.webapi.stack.bean.settingrepo.dto;

import com.dwarfeng.subgrade.stack.bean.dto.Dto;

import java.util.Arrays;

/**
 * 公共图片节点缩略图下载信息。
 *
 * @author DwArFeng
 * @since 1.7.0
 */
public class PublicImageNodeThumbnailDownloadInfo implements Dto {

    private static final long serialVersionUID = -5192630069871464463L;

    private String[] args;

    public PublicImageNodeThumbnailDownloadInfo() {
    }

    public PublicImageNodeThumbnailDownloadInfo(String[] args) {
        this.args = args;
    }

    public String[] getArgs() {
        return args;
    }

    public void setArgs(String[] args) {
        this.args = args;
    }

    @Override
    public String toString() {
        return "PublicImageNodeThumbnailDownloadInfo{" +
                "args=" + Arrays.toString(args) +
                '}';
    }
}
