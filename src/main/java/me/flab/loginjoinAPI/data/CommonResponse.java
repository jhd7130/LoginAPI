package me.flab.loginjoinAPI.data;

import lombok.Builder;
import lombok.Getter;

@Getter
public class CommonResponse {
    boolean success;
    int code;
    String message;
}
