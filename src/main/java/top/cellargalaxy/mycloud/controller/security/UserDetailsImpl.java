package top.cellargalaxy.mycloud.controller.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.LinkedList;

/**
 * @author cellargalaxy
 * @time 2018/7/30
 */
public class UserDetailsImpl implements UserDetails {
    private final String username;
    private final String password;
    private final Collection<GrantedAuthority> grantedAuthorities;

    public UserDetailsImpl(String username, String password) {
        this.username = username;
        this.password = password;
        grantedAuthorities = new LinkedList<>();
    }

    @Override
    public Collection<GrantedAuthority> getAuthorities() {
        return grantedAuthorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
