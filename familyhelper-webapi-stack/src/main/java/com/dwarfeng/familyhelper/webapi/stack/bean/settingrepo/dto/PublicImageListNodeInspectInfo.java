package com.dwarfeng.familyhelper.webapi.stack.bean.settingrepo.dto;

import com.dwarfeng.subgrade.stack.bean.dto.Dto;

import java.util.Arrays;

/**
 * 公共图片列表节点查看信息。
 *
 * @author DwArFeng
 * @since 1.7.0
 */
public class PublicImageListNodeInspectInfo implements Dto {

    private static final long serialVersionUID = 4934381695340173044L;

    private String[] args;

    public PublicImageListNodeInspectInfo() {
    }

    public PublicImageListNodeInspectInfo(String[] args) {
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
        return "PublicImageListNodeInspectInfo{" +
                "args=" + Arrays.toString(args) +
                '}';
    }
}
