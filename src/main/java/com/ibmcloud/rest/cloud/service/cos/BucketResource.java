package com.ibmcloud.rest.cloud.service.cos;


public interface BucketResource {

    	String findAllOpBuckets();
        void listObjects(String bucketName);
} 