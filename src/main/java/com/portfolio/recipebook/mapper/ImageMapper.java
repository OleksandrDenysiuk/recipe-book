package com.portfolio.recipebook.mapper;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public class ImageMapper {
    public static Byte[] toByteArray(MultipartFile image){
        Byte[] byteObjects ;
        try {

            byteObjects = new Byte[image.getBytes().length];

            int i = 0;

            for (byte b : image.getBytes()){
                byteObjects[i++] = b;
            }

            return byteObjects;
        } catch (IOException e) {
            e.printStackTrace();
            return new Byte[0];
        }
    }
}
