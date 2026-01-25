package com.assignment.joojeolbackend.dto.common;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Schema(description = "공통 응답 객체")
public class ReturnMessage<T> {

    @Schema(description = "응답 코드 (성공: 0000)", example = "0000")
    private String result;

    @Schema(description = "응답 메시지", example = "SUCCESS")
    private String message;

    @Schema(description = "응답 데이터")
    private T data;

    public ReturnMessage() {
        this.result = "0000";
        this.message = "SUCCESS";
        this.data = null;
    }

    public ReturnMessage(T data) {
        this.result = "0000";
        this.message = "SUCCESS";
        this.data = data;
    }

    public ReturnMessage(String result, String message, T data) {
        this.result = result;
        this.message = message;
        this.data = data;
    }
}
