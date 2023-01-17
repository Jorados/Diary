package com.jorados.diary.exception;

public class DuplicateException extends SeongjinException{
    private static final String MESSAGE = "이미 존재하는 회원아이디입니다.";

    public DuplicateException() {
        super(MESSAGE);
    }

    @Override
    public int getStatusCode(){
        return 404;
    }
}
