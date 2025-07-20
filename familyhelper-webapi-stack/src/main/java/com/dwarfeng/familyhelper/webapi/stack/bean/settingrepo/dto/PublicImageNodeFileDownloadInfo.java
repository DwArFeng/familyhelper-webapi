package com.dwarfeng.familyhelper.webapi.stack.bean.settingrepo.dto;

import com.dwarfeng.subgrade.stack.bean.dto.Dto;

import java.util.Arrays;

/**
 * 公共图片节点文件下载信息。
 *
 * @author DwArFeng
 * @since 1.7.0
 */
public class PublicImageNodeFileDownloadInfo implements Dto {

    private static final long serialVersionUID = 506951678978213091L;

    private String[] args;

    public PublicImageNodeFileDownloadInfo() {
    }

    public PublicImageNodeFileDownloadInfo(String[] args) {
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
        return "PublicImageNodeFileDownloadInfo{" +
                "args=" + Arrays.toString(args) +
                '}';
    }
}
