package com.ibmcloud.rest.cloud.service.cos;

import java.util.List;

import com.ibmcloud.rest.Exception.NoSuchBucketException;

public interface BucketOperation {

    	List<String> findAllOpBuckets();
        List<String> listObjects(String bucketName) throws NoSuchBucketException;
        void saveAnObject(String path,String bucketName,String fileName);
} 