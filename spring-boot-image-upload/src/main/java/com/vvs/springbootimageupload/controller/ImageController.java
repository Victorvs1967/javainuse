package com.vvs.springbootimageupload.controller;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Optional;
import java.util.zip.DataFormatException;
import java.util.zip.Deflater;
import java.util.zip.Inflater;

import com.vvs.springbootimageupload.model.ImageModel;
import com.vvs.springbootimageupload.repository.ImageRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import lombok.extern.java.Log;

@Log
@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/image")
public class ImageController {
  
  @Autowired
  ImageRepository imageRepository;

  @PostMapping("/upload")
  public ResponseEntity<?> uploadImage(@RequestParam("imageFile") MultipartFile file) throws IOException {
    String msg = String.format("Original image byte size - %s", file.getBytes().length);
    log.info(msg);
    ImageModel img = new ImageModel(file.getOriginalFilename(), file.getContentType(), compressBytes(file.getBytes()));
    imageRepository.save(img);
    return ResponseEntity.ok().build();
  }

  @GetMapping("/get/{imageName}")
  public ImageModel getImage(@PathVariable("imageName") String imageName) throws IOException {
    ImageModel img;
    final Optional<ImageModel> retrievedImage = imageRepository.findByName(imageName);

    if (retrievedImage.isPresent()) {
      img =  new ImageModel(retrievedImage.get().getName(), retrievedImage.get().getType(), decompressBytes(retrievedImage.get().getPicByte()));
    } else {
      throw new IOException("File not found.");
    }
    return img;
  }

  // compress the inage bytes before storing it in the database
  public static byte[] compressBytes(byte[] data) {
    Deflater deflater = new Deflater();
    deflater.setInput(data);
    deflater.finish();

    ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length);
    byte[] buffer = new byte[1024];
    while (!deflater.finished()) {
      int count = deflater.deflate(buffer);
      outputStream.write(buffer, 0, count);
    }
    try {
      outputStream.close();
    } catch (IOException e) {
      log.warning(e.getMessage());
    }
    String msg = String.format("Compressed Image Byte Size - %s", outputStream.toByteArray().length);
    log.info(msg);
    return outputStream.toByteArray();
  }

  // uncompress the image bytes before returning it to the angular application
  public static byte[] decompressBytes(byte[] data) {
    Inflater inflater = new Inflater();
    inflater.setInput(data);
    ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length);
    byte[] buffer = new byte[1024];
    try {
      while (!inflater.finished()) {
        int count = inflater.inflate(buffer);
        outputStream.write(buffer, 0, count);
      }
      outputStream.close();
    } catch (IOException ioe) {    
      log.warning(ioe.getMessage());  
    } catch (DataFormatException e) {
      log.warning(e.getMessage());
    }
    return outputStream.toByteArray();
  }
}
