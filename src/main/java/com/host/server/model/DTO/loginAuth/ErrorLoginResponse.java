package com.host.server.model.DTO.loginAuth;

public class ErrorLoginResponse {

    private String message;

    private int status;

    private long timeStamp;

    public String getMessage() {return message;}
    public void setMessage(String message1) {this.message = message1;}

    public int getStatus() {return status;}
    public void setStatus(int status1) {this.status = status1;}

    public long getTimeStamp() {return timeStamp;}
    public void setTimeStamp(long timeStamp1) {this.timeStamp = timeStamp1;}
}
