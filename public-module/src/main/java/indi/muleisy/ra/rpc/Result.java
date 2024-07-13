package indi.muleisy.ra.rpc;

import lombok.Data;

@Data
public class Result {
    private Integer code;
    private String message;
    private Object data;

    public Result(ResultCode resultCode,Object data) {
        this.code = resultCode.getCode();
        this.message = resultCode.getMessage();
        this.data = data;
    }

    public Result(ResultCode resultCode){
        this.code = resultCode.getCode();
        this.message = resultCode.getMessage();
    }


    public static Result success(){
        return new Result(ResultCode.SUCCESS);
    }

    public static Result success(Object data){
        return new Result(ResultCode.SUCCESS, data);
    }

    public static Result failure(ResultCode resultCode){
        return new Result(resultCode);
    }

    public static Result failure(ResultCode resultCode,Object data){
        return new Result(resultCode, data);
    }
}
