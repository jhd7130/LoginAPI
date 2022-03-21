package me.flab.loginjoinAPI.data;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class CommonResponse {
    public boolean success;
    public int code;
    public  String message;

}
