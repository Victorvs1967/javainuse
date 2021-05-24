package com.vvs.springbootimageupload.repository;

import java.util.Optional;

import com.vvs.springbootimageupload.model.ImageModel;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ImageRepository extends MongoRepository<ImageModel, ObjectId> {
  Optional<ImageModel> findByName(String name);
}
