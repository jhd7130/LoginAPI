package me.flab.loginjoinAPI.controller;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping
public class JoinController {

    private final Logger LOGGER = LoggerFactory.getLogger(JoinController.class);

    @GetMapping("/get/page")
    public String getPage(){
        //사이 로직이 걸린 시간 체크
        long startTime = System.currentTimeMillis();



        LOGGER.info("[JoinController] Response :: ResponseTime = {}",System.currentTimeMillis() - startTime);
        return "String";
    }

    @PostMapping("/get/token")
    public String getToken(){
        return "ok here is your token";
    }

    @GetMapping("/get/id")
    public String getId(){
        return"유효한/유효하지 않은 id";
    }

    @PostMapping("/put/member")
    public String putMember(){
        return "";
    }
}
