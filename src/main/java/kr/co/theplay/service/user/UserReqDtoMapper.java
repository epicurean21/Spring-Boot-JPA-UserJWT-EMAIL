package kr.co.theplay.service.user;

import kr.co.theplay.domain.user.User;
import kr.co.theplay.dto.user.UserReqDto;
import kr.co.theplay.service.MapStructMapper;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.factory.Mappers;

@Mapper(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface UserReqDtoMapper extends MapStructMapper<UserReqDto, User> {
    UserReqDtoMapper INSTANCE = Mappers.getMapper(UserReqDtoMapper.class);
}
