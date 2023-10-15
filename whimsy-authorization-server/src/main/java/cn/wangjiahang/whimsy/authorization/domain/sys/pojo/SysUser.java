package cn.wangjiahang.whimsy.authorization.domain.sys.pojo;

import cn.wangjiahang.whimsy.authorization.core.constants.CommonConstan;
import cn.wangjiahang.whimsy.authorization.core.jpa.BaseEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.Comment;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Date;

/**
 * @author jh.wang
 * @since 2023/7/30
 */
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Entity
@Table(name = "sys_user")
public class SysUser extends BaseEntity implements UserDetails {

    /**
     * 用户名&登录账号
     */
    @Column(name = "username", nullable = false, length = 50)
    @Comment("用户名&登录账号")
    private String username;

    /**
     * 昵称
     */
    @Column(name = "nickname", nullable = false, length = 50)
    @Comment("昵称")
    private String nickname;

    /**
     * 昵称
     */
    @Column(name = "avatar", nullable = false, length = 200)
    @Comment("头像")
    private String avatar;

    /**
     * 密码
     */
    @Column(name = "password", nullable = false, length = 200)
    @JsonIgnore
    @Comment("密码")
    private String password;

    /**
     * 手机号码
     */
    @Column(name = "mobile", length = 20)
    @Comment("手机号码")
    private String mobile;

    /**
     * 邮箱
     */
    @Column(name = "email", length = 20)
    @Comment("邮箱")
    private String email;

    /**
     * 入职日期
     */
    @Column(name = "entryDate", nullable = false, columnDefinition = "datetime")
    @Comment("入职日期")
    private Date entryDate;

    /**
     * 离职日期
     */
    @Column(name = "departureDate", nullable = false, columnDefinition = "datetime")
    @Comment("离职日期")
    private Date departureDate;

    /**
     * 账号启用状态
     */
    @Column(name = "enabled", nullable = false, columnDefinition = "tinyint default 1")
    @Comment("启用状态 启用:1 未启用:0")
    private Integer enabled;

    /**
     * 账号锁定状态
     */
    @Column(name = "locked", nullable = false, columnDefinition = "tinyint default 1")
    @Comment("账号锁定状态 锁定:1 未锁定:0")
    private Integer locked;


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getPassword() {
        return username;
    }

    @Override
    public String getUsername() {
        return password;
    }

    @Override
    public boolean isAccountNonExpired() {
        return departureDate != null && departureDate.before(new Date());
    }

    @Override
    public boolean isAccountNonLocked() {
        return locked != null && CommonConstan.ENABLED.compareTo(locked) == 0;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return enabled != null && CommonConstan.ENABLED.compareTo(enabled) == 0;
    }
}
