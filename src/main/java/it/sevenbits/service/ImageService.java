package it.sevenbits.service;

import it.sevenbits.domain.Goods;
import it.sevenbits.service.exceptions.ImageServiceException;
import it.sevenbits.web.forms.GoodsForm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * Created by awemath on 8/18/15.
 */
@Service
public class ImageService {
    @Value("${resources.path}")
    private  String resourcesPath;
    @Value("${resources.images}")
    private  String imagesPath;

    public static void saveImage(MultipartFile image, String path) throws IOException {

        byte[] bytes = image.getBytes();


        File file = new File(path);
        BufferedOutputStream stream =
                new BufferedOutputStream(new FileOutputStream(file));
        stream.write(bytes);
        stream.close();
    }
    public void saveImages(List<MultipartFile> images, String hash, Goods goods) throws ImageServiceException {
        for (MultipartFile i : images) {
            if (i != null && !i.isEmpty()) {
                String imagePath = imagesPath + hash + i.getOriginalFilename();
                try {
                    ImageService.saveImage(i, resourcesPath + imagePath);
                } catch (IOException e) {
                    throw new ImageServiceException("Exception at the saving images", e);
                }
                goods.addImageUrl(imagePath);

            }
        }
    }
}
