package kr.co.theplay.service.user;

import kr.co.theplay.domain.user.UserRepository;
import kr.co.theplay.dto.user.MailResDto;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class SendEmailService {
    @Autowired
    UserRepository userRepository;
    UserService userService;
    PasswordEncoder passwordEncoder;

    private JavaMailSender mailSender;
    private static final String FROM_ADDRESS = "14jaeminc@gmail.com";

    public MailResDto createMailAndChangePassword(String email) {
        String str = getTempPassword();
        MailResDto mailResDto = new MailResDto();
        mailResDto.setEmail(email);
        mailResDto.setTitle("[오늘 한 주]" + email + "님의 '오늘 한 주' 임시 비밀번호 안내 이메일입니다.");
        mailResDto.setMessage("안녕하세요, 오늘 한 주 임시 비밀번호 안내 관련 이메일 입니다.\n" + "[" + email
                + "] 님의 임시 비밀번호는 \n Password: [" + str + "] 입니다.\n" + "해당 비밀번호로 로그인 하시고 비밀번호를 변경하여 주시기 바랍니다.");
        updatePassword(str, email);
        return mailResDto;
    }

    public void updatePassword(String str, String email) {
        String password = passwordEncoder.encode(str);
        userService.updateUserPassword(email, password);
    }

    public String getTempPassword() {
        char[] charSet = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F',
                'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'};

        String str = "";

        int idx = 0;
        for (int i = 0; i < 10; i++) {
            idx = (int) (charSet.length * Math.random());
            str += charSet[idx];
        }
        return str;
    }

    public void mailSend(MailResDto mailResDto) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(mailResDto.getEmail());
        message.setFrom(SendEmailService.FROM_ADDRESS);
        message.setSubject(mailResDto.getTitle());
        message.setText(mailResDto.getMessage());
        mailSender.send(message);
        System.out.println("이메일 전송 완료");
    }
}
