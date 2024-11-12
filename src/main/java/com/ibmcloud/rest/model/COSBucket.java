package com.ibmcloud.rest.model;

import java.util.List;

public class COSBucket {
    private String name;
    private List<StorageObject> storageObjects;

    public COSBucket(String name, List<StorageObject> storageObjects) {
        this.name = name;
        this.storageObjects = storageObjects;
    }

    public COSBucket() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<StorageObject> getStorageObjects() {
        return storageObjects;
    }

    public void setStorageObjects(List<StorageObject> storageObjects) {
        this.storageObjects = storageObjects;
    }

    @Override
    public String toString() {
        return "Bucket [name=" + name + ", storageObjects=" + storageObjects + "]";
    }

}
