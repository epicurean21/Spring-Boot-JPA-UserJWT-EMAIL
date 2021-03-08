package kr.co.theplay.dto.user;

import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.util.List;

@Getter
@NoArgsConstructor
public class UserReqDto {
    PasswordEncoder passwordEncoder;

    @ApiModelProperty(value = "이메일", dataType = "String", required = true, example = "sample@sample.com")
    @NotBlank(message = "이메일을 입력해주세요")
    @Email(message = "이메일 형식을 지켜주세요")
    private String email;

    @ApiModelProperty(value = "비밀번호", dataType = "String", required = true, example = "password")
    @Pattern(regexp = "[a-zA-Z1-9]{6,12}", message = "비밀번호는 영어와 숫자로 포함해서 6~12자리 이내로 입력해주세요.")
    private String password;

    @ApiModelProperty(value = "닉네임", dataType = "String", required = true, example = "오늘 한 주")
    @NotBlank(message = "닉네임을 입력해주세요")
    private String nickname;

/*
    @ApiModelProperty(value = "종류", dataType = "String", required = true, example = "유저")
    private String roles;
 */

    @Builder
    public UserReqDto(String email, String password, String nickname) {
        this.email = email;
        this.password = password;
        this.nickname = nickname;
        //this.roles = roles;
    }
}
