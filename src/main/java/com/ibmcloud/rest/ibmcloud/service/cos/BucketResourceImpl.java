package com.ibmcloud.rest.ibmcloud.service.cos;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import com.ibm.cloud.objectstorage.services.s3.AmazonS3;
import com.ibm.cloud.objectstorage.services.s3.model.Bucket;
import com.ibm.cloud.objectstorage.services.s3.model.HeadBucketRequest;
import com.ibm.cloud.objectstorage.services.s3.model.ListObjectsRequest;
import com.ibm.cloud.objectstorage.services.s3.model.ObjectListing;
import com.ibm.cloud.objectstorage.services.s3.model.S3ObjectSummary;
import com.ibmcloud.rest.Exception.NoSuchBucketException;
import com.ibmcloud.rest.ibmcloud.service.cloudant.CloudantOperation;
import com.ibmcloud.rest.model.BucketModel;
import com.ibmcloud.rest.model.COSBucket;
import com.ibmcloud.rest.model.StorageObject;

public class BucketResourceImpl implements BucketOperation {

    private static final AmazonS3 cosClient = COSClient.INSTANCE.getCosClient();
    private CloudantOperation cloudantOperation = new CloudantOperation();

    @Override
    public List<BucketModel> findAllOpBuckets() {
        // Get the list of buckets from the COS client
        List<Bucket> buckets = cosClient.listBuckets();

        
        return Optional.ofNullable(buckets)
                .orElse(Collections.emptyList()) 
                .stream()
                .filter(Objects::nonNull)
                .map(bucket -> new BucketModel(bucket.getName()))
                .collect(Collectors.toList());
    }

    @Override
    public COSBucket listObjects(String bucketName) throws NoSuchBucketException {
        COSBucket theBucket = new COSBucket();
        BucketModel bucketModel = new BucketModel();
        bucketModel.setBucketName(bucketName); // Set the bucket name

        try {
            System.out.println("Bucket name: " + bucketName);
            cosClient.headBucket(new HeadBucketRequest(bucketName));
        } catch (Exception e) {
            throw new NoSuchBucketException("Bucket '" + bucketName + "' does not exist.");
        }

        
        List<StorageObject> storageObjects = Optional.ofNullable(
                cosClient.listObjects(new ListObjectsRequest().withBucketName(bucketName)))
                .map(ObjectListing::getObjectSummaries)
                .orElse(Collections.emptyList())
                .stream()
                .filter(Objects::nonNull) 
                .map(S3ObjectSummary::getKey) 
                .map(StorageObject::new) 
                .toList();

        theBucket.setStorageObjects(storageObjects); 
        return theBucket;
    }

    @Override
    public void saveBucketsObject(COSBucket cosBucket) {
        cloudantOperation.save(cosBucket);
    }
}
