package com.dwarfeng.familyhelper.webapi.stack.service.clannad;

import com.dwarfeng.familyhelper.clannad.stack.bean.entity.Poce;
import com.dwarfeng.familyhelper.clannad.stack.bean.key.PoceKey;
import com.dwarfeng.familyhelper.webapi.stack.bean.clannad.disp.DispPoce;
import com.dwarfeng.subgrade.stack.bean.dto.PagedData;
import com.dwarfeng.subgrade.stack.bean.dto.PagingInfo;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.bean.key.StringIdKey;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import com.dwarfeng.subgrade.stack.service.Service;

/**
 * 证件权限响应服务。
 *
 * @author DwArFeng
 * @since 1.0.7
 */
public interface PoceResponseService extends Service {

    boolean exists(PoceKey key) throws ServiceException;

    Poce get(PoceKey key) throws ServiceException;

    DispPoce getDisp(PoceKey key, StringIdKey inspectAccountKey) throws ServiceException;

    PagedData<Poce> childForCertificate(LongIdKey certificateKey, PagingInfo pagingInfo) throws ServiceException;

    PagedData<DispPoce> childForCertificateDisp(
            LongIdKey certificateKey, PagingInfo pagingInfo, StringIdKey inspectAccountKey
    ) throws ServiceException;
}
