package com.ibmcloud.rest.ibmcloud.service.cos;



import com.ibm.cloud.objectstorage.ClientConfiguration;
import com.ibm.cloud.objectstorage.auth.AWSCredentials;
import com.ibm.cloud.objectstorage.auth.AWSStaticCredentialsProvider;
import com.ibm.cloud.objectstorage.client.builder.AwsClientBuilder;
import com.ibm.cloud.objectstorage.oauth.BasicIBMOAuthCredentials;
import com.ibm.cloud.objectstorage.services.s3.AmazonS3;
import com.ibm.cloud.objectstorage.services.s3.AmazonS3ClientBuilder;


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
