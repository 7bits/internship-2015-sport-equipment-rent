package it.sevenbits.service;

import it.sevenbits.domain.Goods;
import it.sevenbits.service.exceptions.ImageServiceException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

/**
 * Created by awemath on 8/18/15.
 */
@Service
public class ImageService {
    @Value("${resources.path}")
    private  String resourcesPath;
    @Value("${resources.images}")
    private  String imagesPath;

    public static void saveImage(
            final MultipartFile image,
            final String path) throws ImageServiceException {
        try {
            byte[] bytes = image.getBytes();


            File file = new File(path);
            BufferedOutputStream stream =
                    new BufferedOutputStream(new FileOutputStream(file));
            stream.write(bytes);
            stream.close();
        } catch (IOException e) {
            throw new ImageServiceException("An error appeared on saving image", e);
        }
    }
    public void saveImages(
            final List<MultipartFile> images,
            final String hash,
            final Goods goods) throws ImageServiceException {
        for (MultipartFile i : images) {
            if (i != null && !i.isEmpty()) {
                String imagePath = imagesPath + hash + i.getOriginalFilename();

                ImageService.saveImage(i, resourcesPath + imagePath);

                goods.addImageUrl(imagePath);

            }
        }
    }
}
