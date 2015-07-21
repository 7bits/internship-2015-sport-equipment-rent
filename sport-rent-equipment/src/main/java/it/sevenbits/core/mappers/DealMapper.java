package it.sevenbits.core.mappers;

import it.sevenbits.web.domain.Deal;
import org.apache.ibatis.annotations.*;

/**
 * Created by awemath on 7/21/15.
 */
public interface DealMapper {
    @Insert("INSERT INTO deals (landlord_id, renting_id, goods_id, is_accepted, is_answered)" +
            " VALUES (#{landlordId}, #{rentingId}, #{goodsId}, #{isAccepted}, #{isAnswered})")
    void save(final Deal deal);

    @Update("UPDATE deals SET (renting_id, landlord_id, goods_id, is_accepted, is_answered)=(#{landlordId}, #{rentingId}, #{goodsId}, #{isAccepted}, #{isAnswered})" +
            " WHERE id = #{id}")
    void update(final Deal deal);

    @Select("SELECT * FROM deals where id=#{dealId}")
    @Results({
            @Result(column = "id", property = "id"),
            @Result(column = "renting_id", property = "rentingId"),
            @Result(column = "landlord_id", property = "landlordId"),
            @Result(column = "goods_id", property = "goodsId"),
            @Result(column = "is_accepted", property = "isAccepted"),
            @Result(column = "is_answered", property = "isAnswered")
    })
    Deal getDeal(long dealId);



}
