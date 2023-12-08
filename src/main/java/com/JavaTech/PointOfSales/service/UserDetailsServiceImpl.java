package com.JavaTech.PointOfSales.service;

import com.JavaTech.PointOfSales.model.User;
import com.JavaTech.PointOfSales.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.getUserByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not Found with username: " + username));
        if (!user.isActivated()) {
            throw new DisabledException("Your account is inactivated");
        }
        if (!user.isUnlocked()) {
            throw new DisabledException("Your account is locked");
        }
        String avatar = user.getAvatar();
        return new UserDetailsImpl(user, avatar);
    }
}