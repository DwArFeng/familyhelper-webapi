package com.dwarfeng.familyhelper.webapi.stack.bean.settingrepo.dto;

import com.dwarfeng.subgrade.stack.bean.dto.Dto;

import java.util.Arrays;

/**
 * 公共导航节点大小信息。
 *
 * @author DwArFeng
 * @since 1.8.2
 */
public class PublicNavigationNodeSizeInfo implements Dto {

    private static final long serialVersionUID = -5976080028598918181L;

    /**
     * 设置类别。
     *
     * <p>
     * 该参数将会经过一定规则进行处理，得到最终的设置类别。
     */
    private String category;

    private String[] args;

    public PublicNavigationNodeSizeInfo() {
    }

    public PublicNavigationNodeSizeInfo(String category, String[] args) {
        this.category = category;
        this.args = args;
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

    @Override
    public String toString() {
        return "PublicNavigationNodeSizeInfo{" +
                "category='" + category + '\'' +
                ", args=" + Arrays.toString(args) +
                '}';
    }
}
