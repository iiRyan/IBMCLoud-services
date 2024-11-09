package com.ibmcloud.rest.cloud.service.cos;

import java.io.File;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import com.ibm.cloud.objectstorage.services.s3.AmazonS3;
import com.ibm.cloud.objectstorage.services.s3.model.Bucket;
import com.ibm.cloud.objectstorage.services.s3.model.ListObjectsRequest;
import com.ibm.cloud.objectstorage.services.s3.model.ObjectListing;
import com.ibm.cloud.objectstorage.services.s3.model.S3ObjectSummary;

public class BucketResourceImpl implements BucketOperation {

    private static final AmazonS3 cosClient = COSClient.INSTANCE.getCosClient();

    @Override
    public List<String> findAllOpBuckets() {
        List<Bucket> buckets = cosClient.listBuckets();

        return Optional.ofNullable(buckets).filter(Objects::nonNull).orElse(Collections.emptyList()).stream()
                .filter(Objects::nonNull).map(Bucket::getName)
                .collect(Collectors.toList());
    }

    @Override
    public List<String> listObjects(String bucketName) {

        return Optional.ofNullable(cosClient.listObjects(new ListObjectsRequest().withBucketName(bucketName)))
                .map(ObjectListing::getObjectSummaries)
                .orElse(Collections.emptyList()) 
                .stream()
                .filter(Objects::nonNull) 
                .map(S3ObjectSummary::getKey)
                .collect(Collectors.toList()); 
    }

    public static void main(String[] args) {
        BucketResourceImpl _cos = new BucketResourceImpl();
        // _cos.saveAnObject("/Users/rayan/projects/ibm-cloud-services/Dockerfile", "jax-rsbucket", "Dockerfile");
        System.out.println(_cos.listObjects("jax-rsbucket"));
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
