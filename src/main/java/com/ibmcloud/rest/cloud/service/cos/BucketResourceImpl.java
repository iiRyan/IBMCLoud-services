package com.ibmcloud.rest.cloud.service.cos;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import com.ibm.cloud.objectstorage.services.s3.AmazonS3;
import com.ibm.cloud.objectstorage.services.s3.model.Bucket;

public class BucketResourceImpl implements BucketResource {

    @Override
    public String findAllOpBuckets() {
         AmazonS3 cos = COSClient.INSTANCE.getCosClient();
        List<Bucket> buckets = cos.listBuckets();

        return buckets.stream()
               .filter(Objects::nonNull)
               .map(Bucket::getName)
               .collect(Collectors.joining(", "));
    }
}
