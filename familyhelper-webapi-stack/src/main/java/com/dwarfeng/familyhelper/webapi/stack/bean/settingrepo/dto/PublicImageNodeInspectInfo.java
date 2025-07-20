package com.dwarfeng.familyhelper.webapi.stack.bean.settingrepo.dto;

import com.dwarfeng.subgrade.stack.bean.dto.Dto;

import java.util.Arrays;

/**
 * 公共图片节点查看信息。
 *
 * @author DwArFeng
 * @since 1.7.0
 */
public class PublicImageNodeInspectInfo implements Dto {

    private static final long serialVersionUID = 1024468286816613946L;

    private String[] args;

    public PublicImageNodeInspectInfo() {
    }

    public PublicImageNodeInspectInfo(String[] args) {
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
        return "PublicImageNodeInspectInfo{" +
                "args=" + Arrays.toString(args) +
                '}';
    }
}
