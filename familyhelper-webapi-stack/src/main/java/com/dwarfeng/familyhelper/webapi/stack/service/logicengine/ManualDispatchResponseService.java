package com.dwarfeng.familyhelper.webapi.stack.service.logicengine;

import com.dwarfeng.logicengine.stack.bean.dto.ManualDispatchInfo;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import com.dwarfeng.subgrade.stack.service.Service;

/**
 * 手动调度响应服务。
 *
 * @author DwArFeng
 * @since 2.4.0
 */
public interface ManualDispatchResponseService extends Service {

    /**
     * 手动调度指定部件。
     *
     * <p>
     * 调度请求将提交给逻辑引擎当前正在使用的调度器。方法正常返回时表示调度器已接受请求，
     * 不表示部件已在接收端执行完成。
     *
     * @param info 手动调度信息。
     * @throws ServiceException 服务异常。
     */
    void dispatch(ManualDispatchInfo info) throws ServiceException;
}