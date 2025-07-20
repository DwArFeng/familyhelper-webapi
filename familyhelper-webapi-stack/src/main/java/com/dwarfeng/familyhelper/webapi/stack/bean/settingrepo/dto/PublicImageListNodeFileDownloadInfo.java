package com.dwarfeng.familyhelper.webapi.stack.bean.settingrepo.dto;

import com.dwarfeng.subgrade.stack.bean.dto.Dto;

import java.util.Arrays;

/**
 * 公共图片列表节点文件下载信息。
 *
 * @author DwArFeng
 * @since 1.7.0
 */
public class PublicImageListNodeFileDownloadInfo implements Dto {

    private static final long serialVersionUID = 4851862667080258448L;

    private String[] args;
    private int index;

    public PublicImageListNodeFileDownloadInfo() {
    }

    public PublicImageListNodeFileDownloadInfo(String[] args, int index) {
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
        return "PublicImageListNodeFileDownloadInfo{" +
                "args=" + Arrays.toString(args) +
                ", index=" + index +
                '}';
    }
}
