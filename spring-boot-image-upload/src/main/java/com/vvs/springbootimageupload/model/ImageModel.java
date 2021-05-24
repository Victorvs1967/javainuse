package com.vvs.springbootimageupload.model;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;

import lombok.Data;

@Data
public class ImageModel {
  
  @Id
  private ObjectId id;

  private String name;
  private String type;

  private byte[] picByte;

  public ImageModel() {
    super();
  }

  public ImageModel(String name, String type, byte[] picByte) {

    this.name = name;
    this.type = type;
    this.picByte = picByte;
  
  }

}
