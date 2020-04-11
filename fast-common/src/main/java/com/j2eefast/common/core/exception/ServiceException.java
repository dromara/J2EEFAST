package com.j2eefast.common.core.exception;


public class ServiceException extends CsizgAbstractException {
    private static final long serialVersionUID = -952104276921501332L;

    public ServiceException(String errorCode, Exception e){
        super(errorCode, e);
    }

    public ServiceException(String errorCode, String msg) {
        super(errorCode, msg);
    }

    public ServiceException(String msg) {
        super(msg);
    }

    public ServiceException(String errorCode, Object[] args) {
        super(errorCode, args);
    }

    public String getServiceErrorCode() {
        return super.getErrorCode();
    }

    public Object[] getServiceArgs() {
        return super.getArgs();
    }
}
