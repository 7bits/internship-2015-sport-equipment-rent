package it.sevenbits.core.mappers;

import it.sevenbits.web.domain.Goods;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * Created by awemath on 7/8/15.
 */
public interface GoodsMapper {
    @Select("SELECT id, title, description, price_per_hour, price_per_day, price_per_week FROM Goods")
    @Results({
            @Result(column = "id", property = "id"),
            @Result(column = "title", property = "title"),
            @Result(column="description", property="description"),
            @Result(column = "price_per_hour", property = "pricePerHour"),
            @Result(column = "price_per_day", property = "pricePerDay"),
            @Result(column = "price_per_week", property = "pricePerWeek")
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

    @Insert("INSERT INTO goods (title, description, pledge, price_per_hour, price_per_day, price_per_week, status, author_id)" +
            " VALUES (#{title}, #{description}, #{pledge}, #{pricePerHour}, #{pricePerDay}, #{pricePerWeek}, true, #{authorId})")
    void save(final Goods goods);
}
