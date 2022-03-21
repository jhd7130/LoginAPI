package me.flab.loginjoinAPI.data;


import lombok.Getter;

@Getter
public class SingleResponse<T> extends CommonResponse {
   public T data;
}
