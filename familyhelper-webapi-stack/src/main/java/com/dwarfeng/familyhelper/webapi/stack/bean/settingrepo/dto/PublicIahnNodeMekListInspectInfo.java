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

    private static final long serialVersionUID = 1746873212765911374L;

    private String[] args;

    public PublicIahnNodeMekListInspectInfo() {
    }

    public PublicIahnNodeMekListInspectInfo(String[] args) {
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
        return "PublicIahnNodeMekListInspectInfo{" +
                "args=" + Arrays.toString(args) +
                '}';
    }
}
