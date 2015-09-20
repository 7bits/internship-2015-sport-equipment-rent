package it.sevenbits.web.service.goods;

import it.sevenbits.core.repository.RepositoryException;
import it.sevenbits.core.repository.goodsrepository.GoodsRepository;
import it.sevenbits.web.domain.Goods;
import it.sevenbits.web.domain.GoodsForm;
import it.sevenbits.web.domain.Image;
import it.sevenbits.web.domain.User;
import it.sevenbits.web.service.users.UserService;
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

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.*;

/**
 * Created by awemath on 7/8/15.
 */
@Service
public class GoodsService {
    @Autowired
    @Qualifier(value="goodsInPostgreSQLrepository")
    private GoodsRepository repository;

    private static final String TRANSACTION_NAME = "transactionService";
    @Autowired
    private PlatformTransactionManager transactionManager;
    private DefaultTransactionDefinition customTransaction;

    public GoodsService() {
        this.customTransaction = new DefaultTransactionDefinition();
        this.customTransaction.setName(TRANSACTION_NAME);
        this.customTransaction.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW);
    }

    @Value("${resources.default-announcement-image}")
    private String defaultImage;


    @Autowired
    AddNewGoodsFormValidator validator;

    @Value("${resources.path}")
    private String resourcesPath;
    @Value("${resources.images}")
    private String imagesPath;

    @Autowired
    private UserService userService;

    public void save(final GoodsForm form) throws GoodsException {
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
        } catch (Exception e) {
            throw new GoodsException("An error occurred while saving subscription: " + e.getMessage(), e);
        }
    }

    public List<Goods> getGoodsByAuthorId(long id){
        List<Goods> goods = repository.getGoodsByAuthorId(id);
        for(int i=0;i<goods.size();i++){
            List<String> images = new LinkedList<String>();
            Image image =getImageForGoods(goods.get(i).getId());
            String url = null;
            if(image!=null)
                url = image.getUrl();
            if(url!=null)
                images.add(url);
            if(images.size()==0 || images.get(0)==null){
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
                public int compare(Goods o1, Goods o2) {
                    return o1.getId() < o2.getId() ? 1 : -1;
                }
            });
            for(int i = 0; i < goods.size(); i++){
                List<String> images = new LinkedList<String>();
                Image image =getImageForGoods(goods.get(i).getId());
                if(image!=null)
                    images.add(image.getUrl());
                if(images.size()==0){
                    images.add(defaultImage);
                }
                goods.get(i).setImageUrl(images);
                checkStatus(goods.get(i));
            }
            return goods;
        } catch (Exception e) {
            throw new GoodsException("An error occurred while retrieving all goods: " + e.getMessage(), e);
        }

    }

    public Goods getGoods(long id) throws GoodsException{
        Goods goods;
        try {
            goods = repository.getGoods(id);
            User user = userService.getUser(goods.getAuthorId());
            goods.setAuthor(user.getFirstName());
            goods.setAuthorPhone(user.getPhone());

            List<Image> images = getImagesForGoods(goods.getId());
            List<String> imagesUrl = new LinkedList<String>();
            for(int i=0;i<images.size();i++){
                imagesUrl.add(images.get(i).getUrl());
            }
            int bufSize = images.size();
            for(int i=0;i<3-bufSize; i++) {
                imagesUrl.add(defaultImage);
            }
            checkStatus(goods);

            goods.setImageUrl(imagesUrl);

        } catch (RepositoryException e) {
            throw new GoodsException("Ann error occurred while retrieving one goods with id "+id+": "
                    +e.getMessage(), e);
        }
        return goods;
    }

    public void addImage(long goodsId, String url){
        repository.addImage(goodsId, url);
    }


    public void updateImage(long announcementId, String nameForBase, Image image) {
        repository.updateImage(nameForBase, image);
    }

    public void update(GoodsForm form) throws GoodsException {
        final Goods goods = new Goods();
        User user = userService.getUser(SecurityContextHolder.getContext().getAuthentication().getName());
        goods.setId(form.getId());
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
            repository.update(goods);
        } catch (Exception e) {
            throw new GoodsException("An error occurred while saving subscription: " + e.getMessage(), e);
        }
    }

    public List<Image> getImagesForGoods(long id){
        List<Image> images = repository.imageUrl(id);
        images.sort(new Comparator<Image>() {
            @Override
            public int compare(Image o1, Image o2) {
                return o1.getUrl() == defaultImage ? -1 : 1;
            }
        });
        return images;
    }

    public Image getImageForGoods(long id){
        return repository.getImageForGoods(id);
    }

    public void delete(Long id) {
        repository.delete(id);
    }

    public void save(Goods goods) throws GoodsException {
        try {
            repository.save(goods);
        } catch (RepositoryException e) {
            throw new GoodsException("An error occurred while saving subscription: " + e.getMessage(), e);
        }
    }

    public void checkStatus(Goods goods){
        goods.setStatus(repository.checkStatus(goods));
    }

    public long submitGoods(GoodsForm goodsForm, List<MultipartFile> images, Map<String, String> errors, HttpSession session) throws GoodsException {
        TransactionStatus status;
        //start transaction
        status  = transactionManager.getTransaction(customTransaction);

        String userName = SecurityContextHolder.getContext().getAuthentication().getName();
        boolean isAuth =  userName != "anonymousUser";
        errors = validator.validate(goodsForm);
        User user = userService.getUser(userName);
        String hash = getHash();
        Goods goods = null;
        if (isAuth) {
            goods = goodsForm.toGoods(user);
            save(goods);
        }
        try {
            ImageService.saveImages(images, hash, goodsForm, errors);
            if(!isAuth) {
                session.setAttribute("addNewGoods", goodsForm);
            }
            if(isAuth) {
                for(String bufImageUrl:goodsForm.getImageUrl()) {
                    addImage(goods.getId(), bufImageUrl);
                }
            }
            transactionManager.commit(status);
        } catch (IOException e) {
            transactionManager.rollback(status);
            e.printStackTrace();
            errors.put("Извините, у нас возникли проблемы. Попробуйте позже", "Извините, у нас возникли проблемы. Попробуйте позже");
            return -1;
        }

        //end transaction
        if(!isAuth) {
            return 0;
        }
        return goods.getId();
    }


    public String getHash() {
        Random random = new Random();
        char[] bufArray = new char[32];
        for(int i=0; i<32; i++) {
            int buf =(48+random.nextInt(122-48));
            while((buf < 65 && buf > 57) || (buf > 90 && buf < 97)) {
                buf = (48 + random.nextInt(122 - 48));
            }
            bufArray[i] = (char) buf;
        }
        return String.valueOf(bufArray);
    }

}
