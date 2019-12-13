package com.squeaker.entry.controller;

import com.squeaker.entry.exception.ImageNotFoundException;
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

    private final String IMAGE_DIR = "/home/ubuntu/server/";

    @GetMapping("/{imageNAme}")
    public byte[] getImage(@PathVariable String imageNAme) {

        File file;
        if(new File(IMAGE_DIR + "user/"+imageNAme).exists()) {
            file = new File(IMAGE_DIR + "user/"+imageNAme);
        } else {
            file = new File(IMAGE_DIR + "twitt/"+imageNAme);
        }

        InputStream inputStream;
        BufferedImage bi;
        ByteArrayOutputStream bos;
        try {
            inputStream = new FileInputStream(file);
            bi = ImageIO.read(inputStream);
            bos = new ByteArrayOutputStream();
            ImageIO.write(bi, "jpg", bos);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ImageNotFoundException();
        }

        return bos.toByteArray();
    }
}
