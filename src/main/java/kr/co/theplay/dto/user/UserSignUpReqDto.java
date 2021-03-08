package kr.co.theplay.dto.user;

import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Getter
@NoArgsConstructor
public class UserSignUpReqDto {
    PasswordEncoder passwordEncoder;

    @ApiModelProperty(value = "이메일", dataType = "String", required = true, example = "sample@sample.com")
    @NotBlank(message = "이메일을 입력해주세요")
    @Email(message = "이메일 형식을 지켜주세요")
    private String email;

    @ApiModelProperty(value = "비밀번호", dataType = "String", required = true, example = "password")
    @Pattern(regexp = "[a-zA-Z1-9]{6,12}", message = "비밀번호는 영어와 숫자로 포함해서 6~12자리 이내로 입력해주세요.")
    private String password;

    @ApiModelProperty(value = "비밀번호 확인", dataType = "String", required = true, example = "passwordCheck")
    @Pattern(regexp = "[a-zA-Z1-9]{6,12}", message = "비밀번호는 영어와 숫자로 포함해서 6~12자리 이내로 입력해주세요.")
    private String passwordCheck;

    @ApiModelProperty(value = "닉네임", dataType = "String", required = true, example = "오늘 한 주")
    @NotBlank(message = "닉네임을 입력해주세요")
    private String nickname;

    @Builder
    public UserSignUpReqDto(String email, String password, String passwordCheck, String nickname) {
        this.email = email;
        this.password = password;
        this.passwordCheck = passwordCheck;
        this.nickname = nickname;
    }
}
