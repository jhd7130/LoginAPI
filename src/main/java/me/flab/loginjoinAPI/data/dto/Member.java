package me.flab.loginjoinAPI.data.dto;

import lombok.*;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Member {
    //빌더 패턴으로 만들어 봤지만 request 가 json으로 넘어왔을 때 문제 발생 따라서 builder 어노테이션에
    // 추가적으로 매개변수가 없는 생성자와 모든 변수를 매개변수를 받는 생성자 어노테이션 추가
    private int id;
    private String email; // 필수
    private String name;  // 선택
    private String phone; // 선택
    private String pw;    // 필수

    public static MemberBuilder builder(String email, String pw) {
        return new MemberBuilder().email(email).pw(pw);
    }
}
