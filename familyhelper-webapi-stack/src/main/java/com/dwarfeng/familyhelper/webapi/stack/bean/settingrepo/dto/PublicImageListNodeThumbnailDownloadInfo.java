package com.dwarfeng.familyhelper.webapi.stack.bean.settingrepo.dto;

import com.dwarfeng.subgrade.stack.bean.dto.Dto;

import java.util.Arrays;

/**
 * 公共图片列表节点缩略图下载信息。
 *
 * @author DwArFeng
 * @since 1.7.0
 */
public class PublicImageListNodeThumbnailDownloadInfo implements Dto {

    private static final long serialVersionUID = -5268842714954223770L;

    private String[] args;
    private int index;

    public PublicImageListNodeThumbnailDownloadInfo() {
    }

    public PublicImageListNodeThumbnailDownloadInfo(String[] args, int index) {
        this.args = args;
        this.index = index;
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
        return "PublicImageListNodeThumbnailDownloadInfo{" +
                "args=" + Arrays.toString(args) +
                ", index=" + index +
                '}';
    }
}
