package it.sevenbits.core.mappers;

import it.sevenbits.web.domain.Goods;
import it.sevenbits.web.domain.Image;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * Created by awemath on 7/8/15.
 */
public interface GoodsMapper {
    @Select("SELECT id, title, description, price_per_hour, price_per_day, price_per_week, author_id FROM Goods")
    @Results({
            @Result(column = "id", property = "id"),
            @Result(column = "title", property = "title"),
            @Result(column="description", property="description"),
            @Result(column = "price_per_hour", property = "pricePerHour"),
            @Result(column = "price_per_day", property = "pricePerDay"),
            @Result(column = "price_per_week", property = "pricePerWeek"),
            @Result(column = "author_id", property = "authorId")
    })
    List<Goods> findAll();

    @Select("SELECT * FROM Goods where id = #{id}")
    @Results({
            @Result(column = "id", property = "id"),
            @Result(column = "title", property = "title"),
            @Result(column="description", property="description"),
            @Result(column = "pledge", property = "pledge"),
            @Result(column = "price_per_hour", property = "pricePerHour"),
            @Result(column = "price_per_day", property = "pricePerDay"),
            @Result(column = "price_per_week", property = "pricePerWeek"),
            @Result(column = "status", property = "status"),
            @Result(column = "author_id", property = "authorId")
    })
    Goods getGoods(long id);


    @Select("SELECT * FROM Goods where author_id = #{id}")
    @Results({
            @Result(column = "id", property = "id"),
            @Result(column = "title", property = "title"),
            @Result(column="description", property="description"),
            @Result(column = "pledge", property = "pledge"),
            @Result(column = "price_per_hour", property = "pricePerHour"),
            @Result(column = "price_per_day", property = "pricePerDay"),
            @Result(column = "price_per_week", property = "pricePerWeek"),
            @Result(column = "status", property = "status"),
            @Result(column = "author_id", property = "authorId")
    })
    List<Goods> getGoodsByAuthorId(long id);

    @Options(useGeneratedKeys = true)
    @Insert("INSERT INTO goods (title, description, pledge, price_per_hour, price_per_day, price_per_week, status, author_id)" +
            " VALUES (#{title}, #{description}, #{pledge}, #{pricePerHour}, #{pricePerDay}, #{pricePerWeek}, true, #{authorId})")
    void save(final Goods goods);

    @Update("UPDATE goods SET (title, description, pledge, price_per_hour, price_per_day, price_per_week)=" +
            "(#{title}, #{description}, #{pledge}, #{pricePerHour}, #{pricePerDay}, #{pricePerWeek})" +
            "  where id=#{id}")
    void update(Goods goods);


    @Delete("DELETE FROM goods WHERE id=#{id}")
    void delete(Long id);

    //goods images
    @Select("SELECT * from announcement_image where goods_id=#{goodsId}")
    @Results({@Result(column = "image_url", property = "url"),
                @Result(column = "id", property = "id"),
                @Result(column = "goods_id", property = "goodsId")})
    List<Image> getImages(long goodsId);

    @Select("SELECT * FROM announcement_image where goods_id = #{id} ORDER BY id LIMIT 1")
    @Results({
            @Result(column = "image_url", property = "url"),
            @Result(column = "id", property = "id"),
            @Result(column = "goods_id", property = "goodsId")
    })
    Image getImageForGoods(long id);

    @Insert("INSERT INTO announcement_image (goods_id, image_url) VALUES (#{goodsId}, #{imageUrl})")
    void addImage(@Param("goodsId")  long goodsId, @Param("imageUrl")  String imageUrl);

    @Update("UPDATE announcement_image SET (image_url)=(#{newPath}) where id=#{id}")
    void updateImage(@Param("newPath") String newPath, @Param("id")long id);

}
