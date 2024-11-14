package com.ibmcloud.rest.resource;

import java.util.List;

import com.ibmcloud.rest.Exception.NoSuchBucketException;
import com.ibmcloud.rest.ibmcloud.service.cos.BucketOperation;
import com.ibmcloud.rest.ibmcloud.service.cos.BucketResourceImpl;
import com.ibmcloud.rest.model.BucketModel;
import com.ibmcloud.rest.model.COSBucket;

import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@Path("buckets")
public class BucketResource {

    private BucketOperation service = new BucketResourceImpl();

    // This endpoint responsible to list all buckets the instance has.
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<BucketModel> getExistingBuckets() {
        return service.findAllOpBuckets();
    }

    // List all objects in a specific bucket.
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{bucket}")
    public COSBucket getListObjects(@PathParam("bucket") String bucket)  throws NoSuchBucketException {
            return service.listObjects(bucket);
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{bucket}")
    public COSBucket saveObjects(@PathParam("bucket") String bucket) throws NoSuchBucketException{
        COSBucket cosBucket = service.listObjects(bucket);
        service.saveBucketsObject(cosBucket);
        return cosBucket;
    }
}
