package com.dwarfeng.familyhelper.webapi.stack.bean.settingrepo.dto;

import com.dwarfeng.subgrade.stack.bean.dto.Dto;

import java.util.Arrays;

/**
 * 公共设置节点查看信息。
 *
 * @author DwArFeng
 * @since 1.7.0
 */
public class PublicSettingNodeInspectInfo implements Dto {

    private static final long serialVersionUID = 8372494435872520190L;

    private String[] args;

    public PublicSettingNodeInspectInfo() {
    }

    public PublicSettingNodeInspectInfo(String[] args) {
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
        return "PublicSettingNodeInspectInfo{" +
                "args=" + Arrays.toString(args) +
                '}';
    }
}
