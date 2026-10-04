package com.atguigu.common;

import lombok.Data;

/**
 * @author : LiuJie
 * @description : TODO
 * @date : 2026/9/10
 */
@Data
public class R {

    private Integer code;
    private String msg;
    private Object data;

    public static R ok(){
        R r =new R();
        r.code=200;
        return r;
    }

    public static R ok(String msg,Object data){
        R r =new R();
        r.code=200;
        r.setMsg(msg);
        r.setData(data);
        return r;
    }

    public static R error(){
        R r =new R();
        r.setCode(500);
        return r;
    }

    public static R error(Integer code,String msg){
        R r =new R();
        r.setCode(code);
        r.setMsg(msg);
        return r;
    }

}
