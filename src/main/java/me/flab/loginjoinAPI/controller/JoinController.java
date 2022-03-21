package me.flab.loginjoinAPI.controller;

import lombok.extern.slf4j.Slf4j;
import me.flab.loginjoinAPI.service.ResponseProviderService;
import me.flab.loginjoinAPI.data.SingleResponse;
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
    private ResponseProviderService responseProviderService;

    public JoinController(JoinService joinService, ResponseProviderService responseProviderService){
        this.joinService = joinService;
        this.responseProviderService = responseProviderService;
    }

    // page 열기
    @GetMapping("/get/page")
    public String openPage(){
        return "회원가입페이지";
    }

    // id 중복체크
    @GetMapping("/get/email")
    public SingleResponse<Member> getId(String email){
        return responseProviderService.getSingleResponse(joinService.getMember(email));
    }


    //회원가입
    @PostMapping("/put/member")
    public SingleResponse<Integer> putMember(@RequestBody  Member mem){
           log.info("[Member Information] request ::: mem ={}", mem.toString());
        return responseProviderService.getSingleResponse(joinService.putMember(mem));
    }
}
