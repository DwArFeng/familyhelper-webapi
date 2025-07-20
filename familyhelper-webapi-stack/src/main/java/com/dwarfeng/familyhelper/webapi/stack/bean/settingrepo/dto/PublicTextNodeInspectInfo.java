package com.dwarfeng.familyhelper.webapi.stack.bean.settingrepo.dto;

import com.dwarfeng.subgrade.stack.bean.dto.Dto;

import java.util.Arrays;

/**
 * 公共文本节点查看信息。
 *
 * @author DwArFeng
 * @since 1.7.0
 */
public class PublicTextNodeInspectInfo implements Dto {

    private static final long serialVersionUID = 6215707367292260741L;

    private String[] args;

    public PublicTextNodeInspectInfo() {
    }

    public PublicTextNodeInspectInfo(String[] args) {
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
        return "PublicTextNodeInspectInfo{" +
                "args=" + Arrays.toString(args) +
                '}';
    }
}
