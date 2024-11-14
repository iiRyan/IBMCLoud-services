package com.ibmcloud.rest.ibmcloud.service.cloudant;

import java.util.ArrayList;
import java.util.List;

import com.ibm.cloud.cloudant.v1.model.Document;
import com.ibm.cloud.cloudant.v1.model.DocumentResult;
import com.ibm.cloud.cloudant.v1.model.PostDocumentOptions;
import com.ibmcloud.rest.model.COSBucket;
import com.ibmcloud.rest.model.StorageObject;

public class CloudantOperation {

    private CloudantDBManager dbManager = new CloudantDBManager();

    public CloudantOperation() {
    }

    // Insert Bucket's objects into CloudantDB.
    public boolean save(COSBucket bucketsObject) {
        if (!dbManager.isInitialized()) {
            dbManager.init();
        }

        DocumentResult result = null;

        try {
            Document document = new Document();
            document.put("bucketName", bucketsObject.getName());
            document.put("storageObjects", bucketsObject.getStorageObjects());

            PostDocumentOptions createDocumentOptions = new PostDocumentOptions.Builder()
                    .db(CloudantDBManager.TABLE_BUCKET)
                    .document(document)
                    .build();

            result = CloudantClient.INSTANCE.getCloudantClient()
                    .postDocument(createDocumentOptions)
                    .execute()
                    .getResult();
            return result.isOk();
        } catch (Exception e) {
    
            System.out.println("Error ocurred: " + e);
            return false;
        }

    }
}
