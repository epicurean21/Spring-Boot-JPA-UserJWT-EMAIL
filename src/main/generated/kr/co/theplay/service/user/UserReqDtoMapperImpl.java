package kr.co.theplay.service.user;

import javax.annotation.Generated;
import kr.co.theplay.domain.user.User;
import kr.co.theplay.dto.user.UserReqDto;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2021-03-08T19:44:16+0900",
    comments = "version: 1.4.2.Final, compiler: javac, environment: Java 15.0.1 (AdoptOpenJDK)"
)
public class UserReqDtoMapperImpl implements UserReqDtoMapper {

    @Override
    public User toEntity(UserReqDto dto) {
        if ( dto == null ) {
            return null;
        }

        User user = new User();

        return user;
    }

    @Override
    public UserReqDto toDto(User entity) {
        if ( entity == null ) {
            return null;
        }

        UserReqDto userReqDto = new UserReqDto();

        return userReqDto;
    }
}
