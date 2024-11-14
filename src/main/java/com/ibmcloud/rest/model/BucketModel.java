package com.ibmcloud.rest.model;

public class BucketModel {
    private String bucketName;

    public BucketModel() {
    }

    public BucketModel(String bucketName) {
        this.bucketName = bucketName;
    }

    public String getBucketName() {
        return bucketName;
    }

    public void setBucketName(String bucketName) {
        this.bucketName = bucketName;
    }

    @Override
    public String toString() {
        return "Bucket [bucketName=" + bucketName + "]";
    }
    
}
