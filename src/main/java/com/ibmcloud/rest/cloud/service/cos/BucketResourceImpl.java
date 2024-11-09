package com.ibmcloud.rest.cloud.service.cos;

import java.io.File;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import com.ibm.cloud.objectstorage.services.s3.AmazonS3;
import com.ibm.cloud.objectstorage.services.s3.model.Bucket;
import com.ibm.cloud.objectstorage.services.s3.model.ListObjectsRequest;
import com.ibm.cloud.objectstorage.services.s3.model.ObjectListing;
import com.ibm.cloud.objectstorage.services.s3.model.S3ObjectSummary;

public class BucketResourceImpl implements BucketResource {

    private static final AmazonS3 cosClient = COSClient.INSTANCE.getCosClient();

    @Override
    public String findAllOpBuckets() {
        List<Bucket> buckets = cosClient.listBuckets();

        return buckets.stream()
                .filter(Objects::nonNull)
                .map(Bucket::getName)
                .collect(Collectors.joining(", "));
    }

    @Override
    public void listObjects(String bucketName) {
        System.out.println("Listing objects in bucket " + bucketName);
        ObjectListing objectListing = cosClient.listObjects(new ListObjectsRequest().withBucketName(bucketName));
        for (S3ObjectSummary objectSummary : objectListing.getObjectSummaries()) {
            System.out.println(" - " + objectSummary.getKey() + "  " + "(size = " + objectSummary.getSize() + ")");
        }
        System.out.println();
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
