package com.ibmcloud.rest.ibmcloud.service.cos;

import java.util.List;

import com.ibmcloud.rest.Exception.NoSuchBucketException;
import com.ibmcloud.rest.model.COSBucket;

public interface BucketOperation {

    	List<String> findAllOpBuckets();
        COSBucket listObjects(String bucketName) throws NoSuchBucketException;
        void saveAnObject(String path,String bucketName,String fileName);
} 