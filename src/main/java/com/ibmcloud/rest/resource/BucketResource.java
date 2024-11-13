package com.ibmcloud.rest.resource;

import java.util.List;

import com.ibmcloud.rest.Exception.NoSuchBucketException;
import com.ibmcloud.rest.ibmcloud.service.cos.BucketOperation;
import com.ibmcloud.rest.ibmcloud.service.cos.BucketResourceImpl;
import com.ibmcloud.rest.model.COSBucket;

import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@Path("upload")
public class BucketResource {

    private BucketOperation service = new BucketResourceImpl();

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("")
    public List<String> getExistingBuckets() {
        return service.findAllOpBuckets();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{bucket}")
    public COSBucket getListObjects(@PathParam("bucket") String bucket)  throws NoSuchBucketException {
       
            return service.listObjects(bucket);
        
    }
}
