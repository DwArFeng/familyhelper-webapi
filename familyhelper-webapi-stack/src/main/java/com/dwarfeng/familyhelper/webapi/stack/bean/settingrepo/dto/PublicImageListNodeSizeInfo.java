package com.dwarfeng.familyhelper.webapi.stack.bean.settingrepo.dto;

import com.dwarfeng.subgrade.stack.bean.dto.Dto;

import java.util.Arrays;

/**
 * 公共图片列表节点大小信息。
 *
 * @author DwArFeng
 * @since 1.7.0
 */
public class PublicImageListNodeSizeInfo implements Dto {

    private static final long serialVersionUID = 4935751439362617423L;

    private String[] args;

    public PublicImageListNodeSizeInfo() {
    }

    public PublicImageListNodeSizeInfo(String[] args) {
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
        return "PublicImageListNodeSizeInfo{" +
                "args=" + Arrays.toString(args) +
                '}';
    }
}
