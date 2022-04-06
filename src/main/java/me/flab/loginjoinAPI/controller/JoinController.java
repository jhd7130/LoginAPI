package me.flab.loginjoinAPI.controller;

import lombok.extern.slf4j.Slf4j;
import me.flab.loginjoinAPI.data.Response;
import me.flab.loginjoinAPI.data.dto.Member;
import me.flab.loginjoinAPI.service.JoinService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
public class JoinController {
    private final Logger LOGGER = LoggerFactory.getLogger(JoinController.class);

    private JoinService joinService;

    public JoinController(JoinService joinService){
        this.joinService = joinService;
    }

    // page 열기
    @GetMapping("/get/page")
    public String openPage(){
        return "회원가입페이지";
    }

    // id 중복체크
    @GetMapping("/get/email")
    public Response getId(String email){
        log.info("[JoinService] Request email={}",email);
        return Response.builder().message(joinService.getMember(email)).build();
    }


    //회원가입
    @PostMapping("/put/member")
    public Response putMember(@RequestBody  Member mem){
           log.info("[Member Information] request ::: mem ={}", mem.toString());
       // joinService.putMember(mem)
        return Response.builder().message(joinService.putMember(mem)).build();
    }
}
