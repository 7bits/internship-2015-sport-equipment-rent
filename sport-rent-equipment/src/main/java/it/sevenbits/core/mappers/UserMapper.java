package it.sevenbits.core.mappers;

import it.sevenbits.web.domain.User;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * Created by awemath on 7/14/15.
 */
public interface UserMapper {
    @Select("SELECT id, title, description, price_per_hour, price_per_day, price_per_week FROM Goods")
    @Results({
            @Result(column = "id", property = "id"),
            @Result(column = "title", property = "title"),
            @Result(column="description", property="description"),
            @Result(column = "price_per_hour", property = "pricePerHour"),
            @Result(column = "price_per_day", property = "pricePerDay"),
            @Result(column = "price_per_week", property = "pricePerWeek")
    })
    List<User> findAll();

    @Select("SELECT * FROM users where email = #{email}")
    @Results({
            @Result(column = "id", property = "id"),
            @Result(column = "pass", property = "pass"),
            @Result(column = "first_name", property = "firstName"),
            @Result(column = "second_name", property = "secondName"),
            @Result(column = "phone", property = "phone"),
    })
    User getUser(String email);

    @Select("SELECT * FROM users where id = #{id}")
    @Results({
            @Result(column = "id", property = "id"),
            @Result(column = "pass", property = "pass"),
            @Result(column = "first_name", property = "firstName"),
            @Result(column = "phone", property = "phone"),
    })
    User getUserById(Long id);

    @Select("SELECT count(*) FROM users where email=#{email}")
    int getCountOfUserWithThatEmail(String email);


    @Insert("INSERT INTO users (first_name, second_name, pass, phone, email, users_role)" +
            " VALUES (#{firstName}, 'null', #{pass}, #{phone}, #{email}, 'USER')")
    void save(final User user);


    @Update("UPDATE users SET (first_name, phone, image_url)=+\n" +
            "(#{firstName}, #{phone}, #{imageUrl})+\n" +
            "  where id=#{id}")
    void update(User user);
}