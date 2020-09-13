package dev.naman.userservice.service.security;

import dev.naman.userservice.model.Permission;
import dev.naman.userservice.model.Role;
import dev.naman.userservice.model.User;
import dev.naman.userservice.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
@Service
public class CustomUserDetailsService implements UserDetailsService {
    @Autowired
    UserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        User user=userRepository.findByEmail(s);
        if(user==null) throw new UsernameNotFoundException("No user found with that name");
        return new org.springframework.security.core.userdetails.User(
                user.getEmail(),
                user.getPassword(),
                user.isActive(),
                true,
                true,
                true,
                getAuthorities(user.getRoles())
        );

    }
    private List<GrantedAuthority> getAuthorities(List<Role> userRoles){
        List<Permission>permissions=new ArrayList<>();
        List<GrantedAuthority>authorities=new ArrayList<>();
        for(Role role:userRoles){
            authorities.add((new SimpleGrantedAuthority(role.getName())));
            for(Permission permission:role.getPermissions()){
                authorities.add(new SimpleGrantedAuthority(permission.getName()));
            }
        }
        return authorities;
    }
}
