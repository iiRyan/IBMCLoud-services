package com.ibmcloud.rest.model;

public class StorageObject {
    private String objectName;

    public StorageObject(String objectName) {
        this.objectName = objectName;
    }

    public StorageObject() {
    }

    public String getObjectName() {
        return objectName;
    }

    public void setObjectName(String objectName) {
        this.objectName = objectName;
    }

    @Override
    public String toString() {
        return "StorageObject [objectName=" + objectName + "]";
    }

    
    
}
