package edu.xcc.smartcommunity.common;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Response<T> {
    private Integer code;
    private String message;
    private T data;

    public Response(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public static <T> Response<T> success(T data) {
        Response<T> response = new Response<>();
        response.setCode(200);
        response.setMessage("success");
        response.setData(data);
        return response;
    }

    public static <T> Response<T> failed() {
        Response<T> response = new Response<>();
        response.setCode(500);
        response.setMessage("failed");
        return response;
    }

    public static <T> Response<T> failed(String message) {
        Response<T> response = new Response<>();
        response.setCode(500);
        response.setMessage(message);
        return response;
    }
}