package com.ibmcloud.rest.model;

import java.util.List;

public class COSBucket {

    private BucketModel name;
    private List<StorageObject> storageObjects;
    public COSBucket() {
    }
    public COSBucket(BucketModel name, List<StorageObject> storageObjects) {
        this.name = name;
        this.storageObjects = storageObjects;
    }
    public BucketModel getName() {
        return name;
    }
    public void setName(BucketModel name) {
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
        return "COSBucket [name=" + name + ", storageObjects=" + storageObjects + "]";
    }

    

}
