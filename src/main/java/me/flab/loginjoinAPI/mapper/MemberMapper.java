package me.flab.loginjoinAPI.mapper;
import me.flab.loginjoinAPI.data.dto.Member;
import me.flab.loginjoinAPI.data.dto.SignInRequest;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;
import java.util.Optional;


@Mapper
public interface MemberMapper {
    List<Member> selectList();

    int inesertMember();

    SignInRequest getMember(String email);

    int putMember(Member mem);

}
