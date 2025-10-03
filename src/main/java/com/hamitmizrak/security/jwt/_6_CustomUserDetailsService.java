package com.hamitmizrak.security.jwt;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

// LOMBOK
@RequiredArgsConstructor

@Service
public class _6_CustomUserDetailsService implements UserDetailsService {

    //INJECTION
    private final _5_IUserRepository iUserRepository;

    // Kullanıcı var mı yok mu
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        // Entity Kullalncıları bulmak
        //Optional<_4_UserEntity> userEntity= iUserRepository.findByUsername(username); // 1.YOL
        _4_UserEntity userEntity= iUserRepository.findByUsername(username).orElseThrow(
                ()-> new UsernameNotFoundException("Username not found")
        );

        // Eğer kullanıcı varsa
        // Security datayı bizim belirlediğim user bilgileriene göre set etmek
         UserDetails userDetails= User.builder()
                 .username(userEntity.getUsername())
                 .password(userEntity.getPassword())
                 //.password("{noop}"+userEntity.getPassword())
                 //.password("{noop}root")
                 //  .roles(String.valueOf(userEntity.getRole())) // 1.YOL
                 .roles(userEntity.getRole().toString()) //2.YOL
                 .build();
         return userDetails;
    }
}
