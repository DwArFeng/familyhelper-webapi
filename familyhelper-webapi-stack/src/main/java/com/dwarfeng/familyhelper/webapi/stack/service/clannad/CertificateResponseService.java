package com.dwarfeng.familyhelper.webapi.stack.service.clannad;

import com.dwarfeng.familyhelper.clannad.stack.bean.dto.CertificateCreateInfo;
import com.dwarfeng.familyhelper.clannad.stack.bean.dto.CertificatePermissionRemoveInfo;
import com.dwarfeng.familyhelper.clannad.stack.bean.dto.CertificatePermissionUpsertInfo;
import com.dwarfeng.familyhelper.clannad.stack.bean.dto.CertificateUpdateInfo;
import com.dwarfeng.familyhelper.clannad.stack.bean.entity.Certificate;
import com.dwarfeng.familyhelper.webapi.stack.bean.disp.clannad.DispCertificate;
import com.dwarfeng.subgrade.stack.bean.dto.PagedData;
import com.dwarfeng.subgrade.stack.bean.dto.PagingInfo;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.bean.key.StringIdKey;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import com.dwarfeng.subgrade.stack.service.Service;

/**
 * 证件响应服务。
 *
 * @author DwArFeng
 * @since 1.0.7
 */
public interface CertificateResponseService extends Service {

    boolean exists(LongIdKey key) throws ServiceException;

    Certificate get(LongIdKey key) throws ServiceException;

    PagedData<Certificate> all(PagingInfo pagingInfo) throws ServiceException;

    DispCertificate getDisp(LongIdKey key, StringIdKey inspectAccountKey) throws ServiceException;

    PagedData<DispCertificate> allPermittedDisp(StringIdKey accountKey, PagingInfo pagingInfo) throws ServiceException;

    PagedData<DispCertificate> allOwnedDisp(StringIdKey accountKey, PagingInfo pagingInfo) throws ServiceException;

    LongIdKey createCertificate(StringIdKey userKey, CertificateCreateInfo certificateCreateInfo)
            throws ServiceException;

    void updateCertificate(StringIdKey userKey, CertificateUpdateInfo certificateUpdateInfo) throws ServiceException;

    void removeCertificate(StringIdKey userKey, LongIdKey certificateKey) throws ServiceException;

    void upsertPermission(StringIdKey userKey, CertificatePermissionUpsertInfo certificatePermissionUpsertInfo)
            throws ServiceException;

    void removePermission(StringIdKey userKey, CertificatePermissionRemoveInfo certificatePermissionRemoveInfo)
            throws ServiceException;
}
