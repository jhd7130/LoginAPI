package me.flab.loginjoinAPI.service;

import lombok.extern.slf4j.Slf4j;
import me.flab.loginjoinAPI.data.dto.Member;
import me.flab.loginjoinAPI.mapper.MemberMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class LoginService {
    private MemberMapper memberMapper;

    public boolean checkLogin(String email, String pw) {
        List<Member> members = memberMapper.getMember(email);
        // 혹시 발생할 수 있는 이메일 중복 체크
        if(members.size() != 1){
            return false;
        }
        //패스워드 체크
        if(members.get(1).getPw().equals(pw)){
            return true;
        }

        return false;
    }
}
