package com.dwarfeng.familyhelper.webapi.stack.bean.settingrepo.dto;

import com.dwarfeng.subgrade.stack.bean.dto.Dto;

import java.util.Arrays;

/**
 * 公共国际化节点消息表查询信息。
 *
 * @author DwArFeng
 * @since 1.7.0
 */
public class PublicIahnNodeMessageTableInspectInfo implements Dto {

    private static final long serialVersionUID = 8773197955817605096L;

    private String[] args;

    public PublicIahnNodeMessageTableInspectInfo() {
    }

    public PublicIahnNodeMessageTableInspectInfo(String[] args) {
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
        return "PublicIahnNodeMessageTableInspectInfo{" +
                "args=" + Arrays.toString(args) +
                '}';
    }
}
