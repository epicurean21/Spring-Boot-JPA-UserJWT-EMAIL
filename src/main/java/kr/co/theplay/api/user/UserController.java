package kr.co.theplay.api.user;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import kr.co.theplay.domain.user.User;
import kr.co.theplay.domain.user.UserRepository;
import kr.co.theplay.dto.user.MailResDto;
import kr.co.theplay.dto.user.UserSignUpReqDto;
import kr.co.theplay.dto.user.UserUpdateNicknameReqDto;
import kr.co.theplay.dto.user.UserUpdatePasswordReqDto;
import kr.co.theplay.service.common.ResponseService;
import kr.co.theplay.service.common.model.CommonResult;
import kr.co.theplay.service.common.model.SingleResult;
import kr.co.theplay.service.user.SendEmailService;
import kr.co.theplay.service.user.UserService;
import kr.co.theplay.springsecurityjwt.config.security.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Collections;
import java.util.Map;

@Api(tags = {"999. ZZZ (예시 API)"})
@RequestMapping(value = "/v1")
@Slf4j(topic = "user Logger")
@RequiredArgsConstructor
@RestController
public class UserController {
    private final ResponseService responseService;
    private final UserService userService;

    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
    private final UserRepository userRepository;

    private final SendEmailService sendEmailService;

    /*
    @ApiOperation(value = "회원가입", notes = "새로운 회원을 추가한다.")
    @PostMapping("/sign-up")
    public ResponseEntity<CommonResult> saveUser(@RequestBody @Valid UserReqDto userReqDto) {
        userService.saveUser(userReqDto); // validation을 위해 parameter에 @valid 포함
        return new ResponseEntity<>(responseService.getSuccessResult(), HttpStatus.OK);
    }
    */
    @ApiOperation(value = "회원가입", notes = "새로운 회원을 추가한다.")
    @PostMapping("/sign-up")
    public ResponseEntity<CommonResult> saveUser(@RequestBody @Valid UserSignUpReqDto userSignUpReqDto) {
        if (userSignUpReqDto.getPassword().equals(userSignUpReqDto.getPasswordCheck()) == false) {
            System.out.println("error");
        } else {
            userRepository.save(User.builder()
                    .email(userSignUpReqDto.getEmail())
                    .password(passwordEncoder.encode(userSignUpReqDto.getPassword()))
                    .nickname(userSignUpReqDto.getNickname())
                    .roles(Collections.singletonList("ROLE_USER"))
                    .build());
        }
        return new ResponseEntity<>(responseService.getSuccessResult(), HttpStatus.OK);
    }

    @ApiOperation(value = "로그인", notes = "회원 로그인")
    @PostMapping("/sign-in")
    public SingleResult<String> signIn(@RequestBody Map<String, String> input) {
        User user = userRepository.findByEmail(input.get("email")).orElseThrow(() -> new IllegalArgumentException("가입되지 않은 사용자입니다."));
        if (!passwordEncoder.matches(input.get("password"), user.getPassword()))
            throw new IllegalArgumentException("잘못 된 비밀번호입니다.");

        return responseService.getSingleResult(jwtTokenProvider.createToken(String.valueOf(user.getId()), user.getRoles()));
    }

    /*
    @ApiOperation(value = "로그인", notes = "회원 로그인")
    @PostMapping("/sign-in")
    public String login(@RequestBody Map<String, String> user) {
        User member = userRepository.findByEmail(user.get("email"))
                .orElseThrow(() -> new IllegalArgumentException("가입되지 않은 사용자입니다."));
        if (!passwordEncoder.matches(user.get("password"), member.getPassword())) {
            throw new IllegalArgumentException("잘못된 비밀번호 입니다.");
        }
        return jwtTokenProvider.createToken(member.getUsername(), member.getRoles());
    }
     */


    // 토큰 이용
    @ApiImplicitParams({
            @ApiImplicitParam(name = "X-ACCESS-TOKEN", value = "로그인 성공 후 ACCESS TOKEN", required = true, dataType = "String", paramType = "header")
    })
    @ApiOperation(value = "회원 단일 정보 조회", notes = "회원번호 (id)로 조회한다")
    @GetMapping(value = "/user")
    public SingleResult<User> findUser() {
        // SecurityContext에서 인증받은 회원의 정보를 얻어온다.
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();

        // 결과데이터가 단일건인경우 getSingleResult를 이용해서 결과를 출력한다.
        return responseService.getSingleResult(userRepository.findByEmail(email).orElseThrow(() -> new IllegalArgumentException("가입되지 않은 사용자입니다.")));
    }

    @ApiImplicitParams({
            @ApiImplicitParam(name = "X-ACCESS-TOKEN", value = "로그인 성공 후 Access Token", required = true, dataType = "String", paramType = "header")
    })
    @ApiOperation(value = "회원 수정", notes = "회원 정보를 수정한다")
    @PutMapping(value = "/user")
    public ResponseEntity<CommonResult> updateUserNickname(@RequestBody UserUpdateNicknameReqDto userUpdateNicknameReqDto) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        userService.updateUserNickname(email, userUpdateNicknameReqDto);
        return new ResponseEntity<>(responseService.getSuccessResult(), HttpStatus.OK);
    }

    // 이메일 전송 controller
    @PostMapping(value = "/find_password")
    public void sendEmail(@RequestBody UserUpdatePasswordReqDto userUpdatePasswordReqDto) {
        MailResDto mailResDto = sendEmailService.createMailAndChangePassword(userUpdatePasswordReqDto.getEmail());
        sendEmailService.mailSend(mailResDto);
    }
}
