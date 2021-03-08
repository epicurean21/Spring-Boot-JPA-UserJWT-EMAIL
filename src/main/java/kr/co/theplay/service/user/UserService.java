package kr.co.theplay.service.user;

import kr.co.theplay.domain.user.User;
import kr.co.theplay.domain.user.UserRepository;
import kr.co.theplay.dto.user.UserReqDto;
import kr.co.theplay.dto.user.UserUpdateNicknameReqDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class UserService {
    private final UserRepository userRepository;

    @Transactional
    public void saveUser(UserReqDto userReqDto) {
        User user = UserReqDtoMapper.INSTANCE.toEntity(userReqDto);
        userRepository.save(user);
    }

    @Transactional
    public void updateUserNickname(String email, UserUpdateNicknameReqDto userUpdateNicknameReqDto) {
        User user = userRepository.findByEmail(email).orElseThrow(() -> new IllegalArgumentException("해당 이메일이 존재하지 않습니다" + email));
        user.updateUserNickname(userUpdateNicknameReqDto.getNickname());
        userRepository.save(user);
    }

    public boolean userEmailCheck(String email) {
        User user = userRepository.findByEmail(email).orElseThrow(() -> new IllegalArgumentException("해당 이메일이 존재하지 않습니다" + email));
        if (user != null)
            return true;
        else
            return false;
    }

    @Transactional
    public void updateUserPassword(String email, String password) {
        User user = userRepository.findByEmail(email).orElseThrow(() -> new IllegalArgumentException("해당 이메일이 존재하지 않습니다" + email));
        user.updateUserPassword(password);
        userRepository.save(user);
    }
}
