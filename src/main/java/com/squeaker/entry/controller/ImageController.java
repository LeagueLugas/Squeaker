package com.squeaker.entry.controller;

import com.squeaker.entry.exception.ImageNotFoundException;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;

@RestController
@RequestMapping("/api/v1/image")
public class ImageController {

    private final String IMAGE_DIR = "D:/Squeaker/";

    @GetMapping(value = "/{imageName:.+}", produces = {MediaType.IMAGE_JPEG_VALUE, MediaType.IMAGE_GIF_VALUE, MediaType.IMAGE_PNG_VALUE})
    public byte[] getImage(@PathVariable String imageName) {
        File file;
        if(new File(IMAGE_DIR + "user/"+imageName).exists()) {
            file = new File(IMAGE_DIR + "user/"+imageName);
        } else {
            file = new File(IMAGE_DIR + "twitt/"+imageName);
        }

        InputStream inputStream;
        BufferedImage bi;
        ByteArrayOutputStream bos;
        try {
            inputStream = new FileInputStream(file);
            bi = ImageIO.read(inputStream);
            bos = new ByteArrayOutputStream();
            ImageIO.write(bi, file.getName().substring(file.getName().lastIndexOf(".")+1), bos);
        } catch (Exception e) {
            // e.printStackTrace();
            throw new ImageNotFoundException();
        }

        return bos.toByteArray();
    }
}
