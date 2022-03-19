package me.flab.loginjoinAPI.data.dto;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class Member {
    private int id;
    private String email; // 필수
    private String name;  // 필수
    private String phone; // 선택
    private String pw;    // 필수

        static class Builder{
            private int id;
            private String email; // 필수
            private String name;  // 필수
            private String phone; // 선택
            private String pw;    // 필수

            public Builder(String email, String name, String pw){
                this.email = email;
                this.name = name;
                this.pw = pw;
            }

            public Builder phone(String phone){
                this.phone = phone;
                        return this;
            }

            public Member build(){
                return new Member(this);
            }
        }

        public Member(Builder builder){
            this.email = builder.email;
            this.name = builder.name;
            this.phone = builder.phone;
            this.pw = builder.pw;
        }
}
