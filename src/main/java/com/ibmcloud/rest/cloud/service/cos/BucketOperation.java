package com.ibmcloud.rest.cloud.service.cos;

import java.util.List;

public interface BucketOperation {

    	List<String> findAllOpBuckets();
        List<String> listObjects(String bucketName);
        void saveAnObject(String path,String bucketName,String fileName);
} 