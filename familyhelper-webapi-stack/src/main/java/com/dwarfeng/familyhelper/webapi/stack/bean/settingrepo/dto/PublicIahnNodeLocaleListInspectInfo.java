package com.dwarfeng.familyhelper.webapi.stack.bean.settingrepo.dto;

import com.dwarfeng.subgrade.stack.bean.dto.Dto;

import java.util.Arrays;

/**
 * 公共国际化节点地区列表查询信息。
 *
 * @author DwArFeng
 * @since 1.7.0
 */
public class PublicIahnNodeLocaleListInspectInfo implements Dto {

    private static final long serialVersionUID = -7345509011742701451L;

    private String[] args;

    public PublicIahnNodeLocaleListInspectInfo() {
    }

    public PublicIahnNodeLocaleListInspectInfo(String[] args) {
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
        return "PublicIahnNodeLocaleListInspectInfo{" +
                "args=" + Arrays.toString(args) +
                '}';
    }
}
