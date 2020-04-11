package com.j2eefast.common.core.exception;

public class CsizgAbstractException extends RuntimeException {
    private static final long serialVersionUID = -4860178440143846804L;
    private String errorCode;
    private Object[] args;

    public CsizgAbstractException(String errorCode, Exception e)
    {
        super(e);
        this.errorCode = errorCode;
    }

    public CsizgAbstractException(String errorCode, String msg) {
        super(msg);
        this.errorCode = errorCode;
    }
    public CsizgAbstractException(String errorCode, Object[] args) {
        this.errorCode = errorCode;
        this.args = args;
    }

    public CsizgAbstractException(String msg) {
        super(msg);
        this.errorCode = msg;
    }

    protected Object[] getArgs()
    {
        return this.args;
    }

    protected void setArgs(Object[] args) {
        this.args = args;
    }

    public String getErrorCode() {
        return this.errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }
}