package kr.co.theplay.springsecurityjwt.security.user;

import kr.co.theplay.domain.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CustomUserDetailService implements UserDetailsService {
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String userPk) {
        return userRepository.findById(Long.valueOf(userPk)).orElseThrow(() -> new UsernameNotFoundException("사용자가 존재하지 않습니다."));
    }

    /*@Override
    public UserDetails loadUserByEmail(String email) throws UsernameNotFoundException {
        return userRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("사용자가 존재하지 않습니다."));
    }*/
}
