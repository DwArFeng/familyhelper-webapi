package com.dwarfeng.familyhelper.webapi.stack.bean.settingrepo.dto;

import com.dwarfeng.subgrade.stack.bean.dto.Dto;

import java.util.Arrays;

/**
 * 公共国际化节点 Mek 列表查询信息。
 *
 * @author DwArFeng
 * @since 1.7.0
 */
public class PublicIahnNodeMekListInspectInfo implements Dto {

    private static final long serialVersionUID = -3717817346307222475L;

    /**
     * 设置类别。
     *
     * <p>
     * 该参数将会经过一定规则进行处理，得到最终的设置类别。
     *
     * @since 1.8.0
     */
    private String category;

    private String[] args;

    public PublicIahnNodeMekListInspectInfo() {
    }

    public PublicIahnNodeMekListInspectInfo(String category, String[] args) {
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
        return "PublicIahnNodeMekListInspectInfo{" +
                "category='" + category + '\'' +
                ", args=" + Arrays.toString(args) +
                '}';
    }
}
