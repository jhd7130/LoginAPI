package me.flab.loginjoinAPI.service;

import lombok.extern.slf4j.Slf4j;
import me.flab.loginjoinAPI.data.dto.Member;
import me.flab.loginjoinAPI.mapper.MemberMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class JoinService {

    private Logger LOGGER = LoggerFactory.getLogger(this.getClass());
    private MemberMapper memberMapper;

    public JoinService(MemberMapper memberMapper){
        this.memberMapper = memberMapper;
    }

    public List<Member> getUserList(){
        return memberMapper.selectList();
    }

    public String getMember(String email){
        log.info("[JoinService] Request :::: email ={}",email);
        if(memberMapper.getMember(email) == null){
            return "Success";
        }
        return   "Fail";
    }

    public String putMember(Member mem){
        if(memberMapper.putMember(mem) > 0 ){
            return "Success";
        }
        return "Fail";
    }


}
