package com.finwin.chosenvolks.custmate.sign_up.otp.pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Data {

@SerializedName("msg")
@Expose
private String msg;

public String getMsg() {
return msg;
}

public void setMsg(String msg) {
this.msg = msg;
}

}