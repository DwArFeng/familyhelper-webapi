package com.dwarfeng.familyhelper.webapi.stack.bean.settingrepo.dto;

import com.dwarfeng.subgrade.stack.bean.dto.Dto;

import java.util.Arrays;

/**
 * 公共文件列表节点文件下载信息。
 *
 * @author DwArFeng
 * @since 1.8.2
 */
public class PublicFileListNodeFileDownloadInfo implements Dto {

    private static final long serialVersionUID = 9159948987475896552L;

    /**
     * 设置类别。
     *
     * <p>
     * 该参数将会经过一定规则进行处理，得到最终的设置类别。
     */
    private String category;

    private String[] args;
    private int index;

    public PublicFileListNodeFileDownloadInfo() {
    }

    public PublicFileListNodeFileDownloadInfo(String category, String[] args, int index) {
        this.category = category;
        this.args = args;
        this.index = index;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
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
        return "PublicFileListNodeFileDownloadInfo{" +
                "category='" + category + '\'' +
                ", args=" + Arrays.toString(args) +
                ", index=" + index +
                '}';
    }
}
