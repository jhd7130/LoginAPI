package me.flab.loginjoinAPI.mapper;
import me.flab.loginjoinAPI.data.dto.Member;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;
import java.util.Optional;


@Mapper
public interface MemberMapper {
    List<Member> selectList();

    int inesertMember();

    List<Member> getMember(String email);

    int putMember(Member mem);

}
