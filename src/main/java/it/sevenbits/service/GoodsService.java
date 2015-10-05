package it.sevenbits.service;

import it.sevenbits.core.exceptions.GoodsRepositoryException;
import it.sevenbits.core.repository.GoodsRepository;
import it.sevenbits.core.repository.RepositoryException;
import it.sevenbits.domain.Goods;
import it.sevenbits.domain.Image;
import it.sevenbits.domain.User;
import it.sevenbits.service.exceptions.GoodsException;
import it.sevenbits.service.exceptions.ImageServiceException;
import it.sevenbits.service.exceptions.UserServiceException;
import it.sevenbits.web.forms.GoodsForm;
import it.sevenbits.web.validators.AddNewGoodsFormValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;

/**
 * Created by awemath on 7/8/15.
 */
@Service
public class GoodsService {
    @Autowired
    @Qualifier(value = "goodsInPostgreSQLrepository")
    private GoodsRepository repository;

    private static final String TRANSACTION_NAME = "transactionService";
    @Autowired
    private PlatformTransactionManager transactionManager;
    private DefaultTransactionDefinition customTransaction;

    @Autowired
    private ImageService imageService;

    public GoodsService() {
        this.customTransaction = new DefaultTransactionDefinition();
        this.customTransaction.setName(TRANSACTION_NAME);
        this.customTransaction.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW);
    }

    @Value("${resources.default-announcement-image}")
    private String defaultImage;


    @Autowired
    private AddNewGoodsFormValidator validator;

    @Value("${resources.path}")
    private String resourcesPath;
    @Value("${resources.images}")
    private String imagesPath;

    @Autowired
    private UserService userService;

    public void save(final GoodsForm form) throws GoodsException, UserServiceException {
        final Goods goods = new Goods();
        User user = userService.getUser(SecurityContextHolder.getContext().getAuthentication().getName());
        goods.setTitle(form.getTitle());
        goods.setAuthorId(user.getId());
        goods.setAuthorPhone(user.getPhone());
        goods.setAuthor(user.getFirstName());
        goods.setDescription(form.getDescription());
        goods.setPledge(form.getPledge());
        goods.setPricePerHour(Double.valueOf(form.getPricePerHour()));
        goods.setPricePerDay(Double.valueOf(form.getPricePerDay()));
        goods.setPricePerWeek(Double.valueOf(form.getPricePerWeek()));
        try {
            repository.save(goods);
        } catch (RepositoryException e) {
            throw new GoodsException("An error occurred while saving subscription: " + e.getMessage(), e);
        }
    }

    public List<Goods> getGoodsByAuthorId(final long id) {
        List<Goods> goods = repository.getGoodsByAuthorId(id);
        for (int i = 0; i < goods.size(); i++) {
            List<String> images = new LinkedList<String>();
            Image image = getImageForGoods(goods.get(i).getId());
            String url = null;
            if (image != null) {
                url = image.getUrl();
            }
            if (url != null) {
                images.add(url);
            }
            if (images.size() == 0 || images.get(0) == null) {
                images.add(defaultImage);
            }
            goods.get(i).setImageUrl(images);
            checkStatus(goods.get(i));
        }
        goods.sort(new Comparator<Goods>() {
            @Override
            public int compare(final Goods o1, final Goods o2) {
                return o1.getId() < o2.getId() ? 1 : -1;
            }
        });
        return goods;
    }

    public List<Goods> findAll() throws GoodsException {
        try {
            List<Goods> goods = repository.findAll();
            goods.sort(new Comparator<Goods>() {
                @Override
                public int compare(final Goods o1, final Goods o2) {
                    return o1.getId() < o2.getId() ? 1 : -1;
                }
            });
            for (int i = 0; i < goods.size(); i++) {
                List<String> images = new LinkedList<String>();
                Image image = getImageForGoods(goods.get(i).getId());
                if (image != null) {
                    images.add(image.getUrl());
                }
                if (images.size() == 0) {
                    images.add(defaultImage);
                }
                goods.get(i).setImageUrl(images);
                checkStatus(goods.get(i));
            }
            return goods;
        } catch (RepositoryException e) {
            throw new GoodsException("An error occurred while retrieving all goods: " + e.getMessage(), e);
        }

    }

    public Goods getGoods(final long id) throws GoodsException, UserServiceException {
        Goods goods;
        try {
            goods = repository.getGoods(id);
            User user = userService.getUser(goods.getAuthorId());
            goods.setAuthor(user.getFirstName());
            goods.setAuthorPhone(user.getPhone());

            List<Image> images = getImagesForGoods(goods.getId());
            List<String> imagesUrl = new LinkedList<String>();
            for (int i = 0; i < images.size(); i++) {
                imagesUrl.add(images.get(i).getUrl());
            }
            int bufSize = images.size();
            for (int i = 0; i < 3 - bufSize; i++) {
                imagesUrl.add(defaultImage);
            }
            checkStatus(goods);

            goods.setImageUrl(imagesUrl);

        } catch (RepositoryException e) {
            throw new GoodsException("Ann error occurred while retrieving one goods with id " + id + ": "
                    + e.getMessage(), e);
        }
        return goods;
    }

    public void addImage(final long goodsId, final String url) {
        repository.addImage(goodsId, url);
    }


    public void updateImage(final long announcementId, final String nameForBase, final Image image) {
        repository.updateImage(nameForBase, image);
    }

    public void update(final Goods goods)  {
        repository.update(goods);
    }

    public List<Image> getImagesForGoods(final long id) {
        List<Image> images = repository.imageUrl(id);
        images.sort(new Comparator<Image>() {
            @Override
            public int compare(final Image o1, final Image o2) {
                return o1.getUrl() == defaultImage ? -1 : 1;
            }
        });
        return images;
    }

    public Image getImageForGoods(final long id) {
        return repository.getImageForGoods(id);
    }

    public void delete(final Long id) {
        repository.delete(id);
    }

    public void save(final Goods goods) throws GoodsException {
        try {
            repository.save(goods);
        } catch (RepositoryException e) {
            throw new GoodsException("An error occurred while saving subscription: " + e.getMessage(), e);
        }
    }

    public void checkStatus(final Goods goods) {
        goods.setStatus(repository.checkStatus(goods));
    }

    public long submitGoods(final Goods goods,
                            final List<MultipartFile> images)
            throws GoodsException {
        TransactionStatus status;
        //start transaction
        status = transactionManager.getTransaction(customTransaction);

        String userName = SecurityContextHolder.getContext().getAuthentication().getName();
        boolean isAuth = userName != "anonymousUser";
        String hash = getHash();
        if (isAuth) {
            save(goods);
        }
        try {
            imageService.saveImages(images, hash, goods);
            if (isAuth) {
                for (String bufImageUrl : goods.getImageUrl()) {
                    addImage(goods.getId(), bufImageUrl);
                }
            }
            transactionManager.commit(status);
        } catch (ImageServiceException e) {
            transactionManager.rollback(status);
            e.printStackTrace();
            return -1;
        }

        //end transaction
        if (!isAuth) {
            return 0;
        }
        return goods.getId();
    }

    public void updateAnnouncement(final List<MultipartFile> images,
                                   final boolean[] deletedImages,
                                   final Goods goods,
                                   final long announcementId) throws GoodsException {
        TransactionStatus status;
        //start transaction
        status = transactionManager.getTransaction(customTransaction);

        goods.setId(announcementId);
        List<Image> currentImages = getImagesForGoods(announcementId);
        for (int i = 0; i < images.size(); i++) {
            if (deletedImages[i]) {
                if (currentImages.size() > i) {
                    updateImage(announcementId, defaultImage, currentImages.get(i));
                } else {
                    addImage(announcementId, defaultImage);
                }

            }
        }
        try {
            for (int i = 0; i < images.size(); i++) {
                if (images.get(i) != null && !images.get(i).isEmpty()) {
                    String fileName = resourcesPath + imagesPath + announcementId + "_"
                            + i + images.get(i).getOriginalFilename();
                    String nameForBase = imagesPath + announcementId + "_" + i + images.get(i).getOriginalFilename();
                    imageService.saveImage(images.get(i), fileName);
                    if (currentImages.size() > i) {
                        updateImage(announcementId, nameForBase, currentImages.get(i));
                    } else {
                        addImage(announcementId, nameForBase);
                    }

                }
            }
            update(goods);
        } catch (ImageServiceException e) {
            transactionManager.rollback(status);
            throw new GoodsException("An error appeared on saving image", e);
        }


        transactionManager.commit(status);

    }


    public String getHash() {
        return UUID.randomUUID().toString();
    }

    public boolean isAuthor(final Long announcementId)
            throws GoodsException, UserServiceException {

        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        Goods goods = getGoods(announcementId);
        if (name != "anonymousUser") {
            User user = userService.getUser(name);
            return user.getId().equals(goods.getAuthorId());
        } else {
            return false;
        }

    }

}
