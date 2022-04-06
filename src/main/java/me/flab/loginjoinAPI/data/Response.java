package me.flab.loginjoinAPI.data;

import lombok.Getter;

@Getter
// default : success
public class Response<T> {
    private String message = "Success";
    private int status = 200;
    private T data;

    Response(String message, int status, T data) {
        this.message = message;
        this.status = status;
        this.data = data;
    }

    public static <T> ResponseBuilder<T> builder() {
        return new ResponseBuilder<T>();
    }

    public static class ResponseBuilder<T> {
        private String message = "Success";
        private int status = 200;
        private T data;

        ResponseBuilder() {}

        public ResponseBuilder<T> message(String message) {
            this.message = message;
            return this;
        }

        public ResponseBuilder<T> status(int status) {
            this.status = status;
            return this;
        }

        public ResponseBuilder<T> data(T data) {
            this.data = data;
            return this;
        }

        public Response<T> build() {
            return new Response<T>(message, status, data);
        }

        public String toString() {
            return "Response.ResponseBuilder(message=" + this.message + ", status=" + this.status + ", data=" + this.data + ")";
        }
    }
}
