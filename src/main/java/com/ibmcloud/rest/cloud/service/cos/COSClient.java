package com.ibmcloud.rest.cloud.service.cos;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import com.ibm.cloud.objectstorage.ClientConfiguration;
import com.ibm.cloud.objectstorage.auth.AWSCredentials;
import com.ibm.cloud.objectstorage.auth.AWSStaticCredentialsProvider;
import com.ibm.cloud.objectstorage.client.builder.AwsClientBuilder;
import com.ibm.cloud.objectstorage.oauth.BasicIBMOAuthCredentials;
import com.ibm.cloud.objectstorage.services.s3.AmazonS3;
import com.ibm.cloud.objectstorage.services.s3.AmazonS3ClientBuilder;
import com.ibm.cloud.objectstorage.services.s3.model.Bucket;

public enum COSClient {
    // Singleton
    INSTANCE;

    private final String cosRegion = "us-east";
    private static final String API_KEY_ENV = "APIKEY";
    private static final String INSTANCE_ID = "INSTANCE_ID";
    COSClient() {
    }

    public AmazonS3 getCosClient() {
        String apikey = System.getenv(API_KEY_ENV);
        String serviceInstanceId = System.getenv(INSTANCE_ID);
        String endpointUrl = "https://s3." + cosRegion + ".cloud-object-storage.appdomain.cloud";

        AWSCredentials authenticator = new BasicIBMOAuthCredentials(apikey, serviceInstanceId);
        ClientConfiguration clientConfig = new ClientConfiguration();
        AmazonS3 cosClient = AmazonS3ClientBuilder.standard()
                .withCredentials(new AWSStaticCredentialsProvider(authenticator))
                .withEndpointConfiguration(new AwsClientBuilder.EndpointConfiguration(endpointUrl, cosRegion))
                .withClientConfiguration(clientConfig).build();
        return cosClient;
    }
}
