package com.ibmcloud.rest.Exception;

public class NoSuchBucketException extends Exception {
    private static final long serialVersionUID = 7501251668604922363L;

    public NoSuchBucketException(){}

    public NoSuchBucketException(String message){
        super(message);
    }
    
}
