package cn.wangjiahang.whimsy.authorization.application;

import cn.wangjiahang.whimsy.authorization.application.command.RegisterClientCreateCommand;

/**
 * @author jh.wang
 * @since 2023/10/15
 */

public interface ClientApplicationService {
    void createClient(RegisterClientCreateCommand registerClientCommand);
}
