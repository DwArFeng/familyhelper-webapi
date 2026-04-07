package com.dwarfeng.familyhelper.webapi.stack.bean.settingrepo.dto;

import com.dwarfeng.subgrade.stack.bean.dto.Dto;

import java.util.Arrays;

/**
 * 公共键值对节点条目查看信息。
 *
 * @author DwArFeng
 * @since 2.0.1
 */
public class PublicKvNodeItemInspectInfo implements Dto {

    private static final long serialVersionUID = -6173355152114116625L;

    /**
     * 设置类别。
     *
     * <p>
     * 该参数将会经过一定规则进行处理，得到最终的设置类别。
     */
    private String category;

    private String[] args;

    private String itemStringId;

    public PublicKvNodeItemInspectInfo() {
    }

    public PublicKvNodeItemInspectInfo(String category, String[] args, String itemStringId) {
        this.category = category;
        this.args = args;
        this.itemStringId = itemStringId;
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

    public String getItemStringId() {
        return itemStringId;
    }

    public void setItemStringId(String itemStringId) {
        this.itemStringId = itemStringId;
    }

    @Override
    public String toString() {
        return "PublicKvNodeItemInspectInfo{" +
                "category='" + category + '\'' +
                ", args=" + Arrays.toString(args) +
                ", itemStringId='" + itemStringId + '\'' +
                '}';
    }
}
