package com.ibmcloud.rest.cloud.service.cos;

import java.io.File;
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
import com.ibmcloud.rest.model.COSBucket;
import com.ibmcloud.rest.model.StorageObject;

public class BucketResourceImpl implements BucketOperation {

    private static final AmazonS3 cosClient = COSClient.INSTANCE.getCosClient();

    @Override
    public List<String> findAllOpBuckets() {
        List<Bucket> buckets = cosClient.listBuckets();

        return Optional.ofNullable(buckets)
                .filter(Objects::nonNull)
                .orElse(Collections.emptyList())
                .stream()
                .filter(Objects::nonNull).map(Bucket::getName)
                .collect(Collectors.toList());
    }

    @Override
    public COSBucket listObjects(String bucketName) throws NoSuchBucketException {
        COSBucket theBucket = new COSBucket();
        theBucket.setName(bucketName); // Set the bucket name

        try {
            System.out.println("Bucket name: " + bucketName);
            cosClient.headBucket(new HeadBucketRequest(bucketName));
        } catch (Exception e) {
            throw new NoSuchBucketException("Bucket '" + bucketName + "' does not exist.");
        }

        // Retrieve object summaries and populate COSBucket
        List<StorageObject> storageObjects = Optional.ofNullable(
                cosClient.listObjects(new ListObjectsRequest().withBucketName(bucketName)))
                .map(ObjectListing::getObjectSummaries)
                .orElse(Collections.emptyList())
                .stream()
                .filter(Objects::nonNull) // Filter out null objects
                .map(S3ObjectSummary::getKey) // Get object names (keys)
                .map(StorageObject::new) // Create StorageObject instances
                .toList(); // Collect into a List

        theBucket.setStorageObjects(storageObjects); // Set the list of StorageObjects
        return theBucket; // Return the populated COSBucket
    }
    @Override
    public void saveAnObject(String path, String bucketName, String fileName) {
        cosClient.putObject(
                bucketName,
                fileName, // the object key
                new File(path) // the file name and path of the object to be uploaded
        );
    }
}
