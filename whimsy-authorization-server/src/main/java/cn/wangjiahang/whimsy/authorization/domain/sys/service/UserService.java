package cn.wangjiahang.whimsy.authorization.domain.sys.service;

import cn.wangjiahang.whimsy.authorization.infrastructure.repository.SysUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * @author jh.wang
 * @since 2023/10/22
 */
@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {
    private final SysUserRepository sysUserRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return sysUserRepository.getByUsername(username);
    }

}
