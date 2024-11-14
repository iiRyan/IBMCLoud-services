package com.ibmcloud.rest.ibmcloud.service.cos;

import java.util.List;

import com.ibmcloud.rest.Exception.NoSuchBucketException;
import com.ibmcloud.rest.model.BucketModel;
import com.ibmcloud.rest.model.COSBucket;

public interface BucketOperation {

    	List<BucketModel> findAllOpBuckets();
        COSBucket listObjects(String bucketName) throws NoSuchBucketException;
        void saveBucketsObject(COSBucket cosBucket);
} 