package it.sevenbits.web.service.goods;

import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by awemath on 8/18/15.
 */
public class Image {
    public static void saveImage(MultipartFile image, String path) throws IOException {
        byte[] bytes = image.getBytes();


        File file = new File(path);
        BufferedOutputStream stream =
                new BufferedOutputStream(new FileOutputStream(file));
        stream.write(bytes);
        stream.close();
    }
}
