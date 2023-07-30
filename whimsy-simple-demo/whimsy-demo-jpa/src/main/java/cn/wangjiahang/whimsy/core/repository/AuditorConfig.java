package cn.wangjiahang.whimsy.core.repository;

import org.springframework.data.domain.AuditorAware;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class AuditorConfig implements AuditorAware<Long> {

    /**
     * 返回操作员标志信息
     *
     * @return
     */
    @Override
    public Optional<Long> getCurrentAuditor() {
        // 这里应根据实际业务情况获取具体信息
        return Optional.of(1L);
    }

}
