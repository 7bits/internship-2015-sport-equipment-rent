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
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by awemath on 7/8/15.
 */
@Service
public class GoodsService {
    @Autowired
    @Qualifier(value="goodsInPostgreSQLrepository")
    private GoodsRepository repository;


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
                images.add("resources/images/photo-ico.png");
            }
            goods.get(i).setImageUrl(images);
        }
        goods.sort(new Comparator<Goods>() {
            @Override
            public int compare(Goods o1, Goods o2) {
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
            for(int i=0;i<goods.size();i++){
                List<String> images = new LinkedList<String>();
                Image image =getImageForGoods(goods.get(i).getId());
                if(image!=null)
                    images.add(image.getUrl());
                if(images.size()==0){
                    images.add("resources/images/photo-ico.png");
                }
                goods.get(i).setImageUrl(images);
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
                imagesUrl.add("resources/images/photo-ico.png");
            }

            goods.setImageUrl(imagesUrl);

        } catch (RepositoryException e) {
            throw new GoodsException("Ann error occurred while retrieving one goods with id "+id+": "+e.getMessage(), e);
        }
        return goods;
    }

    public void addImage(long goodsId, String url){
        repository.addImage(goodsId, url);
    }


    public void updateImage(long announcementId, String nameForBase, Image image) {
        repository.updateImage(nameForBase ,image);
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
        return repository.imageUrl(id);
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
}
