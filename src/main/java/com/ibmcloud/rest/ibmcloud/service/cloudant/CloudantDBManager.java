package com.ibmcloud.rest.ibmcloud.service.cloudant;

import java.util.Optional;

import com.ibm.cloud.cloudant.v1.Cloudant;
import com.ibm.cloud.cloudant.v1.model.DatabaseInformation;
import com.ibm.cloud.cloudant.v1.model.GetDatabaseInformationOptions;
import com.ibm.cloud.cloudant.v1.model.Ok;
import com.ibm.cloud.cloudant.v1.model.PutDatabaseOptions;
import com.ibm.cloud.sdk.core.service.exception.NotFoundException;
import com.ibm.cloud.sdk.core.service.exception.ServiceResponseException;
import com.ibmcloud.rest.Exception.NoSuchBucketException;

public class CloudantDBManager {

    public static String TABLE_BUCKET = "buckets";
    private static Cloudant client = CloudantClient.INSTANCE.getCloudantClient();
    private boolean initialized = false;

    public CloudantDBManager(){}
    public void init()  {
        initDB(TABLE_BUCKET);
        initialized = true;
    }

    private void initDB(String dbName)  {
        Optional.ofNullable(getDbInfo(dbName))
                .ifPresentOrElse(
                        s -> System.out.println("Database already exists: " + dbName),
                        () -> {
                            System.out.println("Database " + dbName + " does not exist, creating db...");
                            try {
                                createDb(dbName);
                            } catch (Exception e) {
                                System.out.println("Failed to create database '" + dbName + "': " + e.getMessage());
                            }
                        });
    }

    public String getDbInfo(String dbName)  {
        try {

            GetDatabaseInformationOptions dbInfoOptions = new GetDatabaseInformationOptions.Builder(dbName)
                    .build();
            DatabaseInformation dbInfo = client.getDatabaseInformation(dbInfoOptions).execute().getResult();
            return dbInfo.toString();

        } catch (NotFoundException e) {
            return null;
        } catch (Exception e) {
           System.out.println("Err: " + e);
        }
        return null;
    }

    public boolean createDb(String dbName) throws NoSuchBucketException {
        try {
            PutDatabaseOptions putDbOptions = new PutDatabaseOptions.Builder().db(dbName).build();

            // Try to create the database
            Ok putDatabaseResult = client.putDatabase(putDbOptions).execute().getResult();

            if (putDatabaseResult.isOk()) {
                System.out.println(dbName + " Database created!");
                return true;
            }

        } catch (ServiceResponseException e) {
            if (e.getStatusCode() == 412) {
                System.out.println("Cannot create '" + dbName + "' database, it already exists.");
            } else {
                System.out.println("Error Message: " + e.getMessage());
                throw new NoSuchBucketException(e.getMessage());
            }
        }
        return false;
    }

    public boolean isInitialized() {
        return initialized;
    }

}
