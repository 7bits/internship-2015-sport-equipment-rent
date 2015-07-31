package it.sevenbits.core.mappers;

import it.sevenbits.web.domain.Deal;
import org.apache.ibatis.annotations.*;

/**
 * Created by awemath on 7/21/15.
 */
public interface DealMapper {
    @Insert("INSERT INTO deals (landlord_id, renting_id, goods_id, handed, is_answered, estimated_start_date, " +
            "estimated_end_date, is_closed, accepted_renting, accepted_return)" +
            " VALUES (#{landlordId}, #{rentingId}, #{goodsId}, #{isHanded}, #{isAnswered}, #{estimateStartDate}, #{estimateEndDate}, #{isClosed}" +
            ", #{acceptedRenting}, #{acceptedReturn})")

    void save(final Deal deal);

    @Update("UPDATE deals SET (landlord_id, renting_id,  goods_id, handed, is_answered, is_closed)=(#{landlordId}, #{rentingId}, #{goodsId}, #{isHanded}, #{isAnswered}, #{isClosed})" +
            " WHERE id = #{id}")
    void update(final Deal deal);

    @Select("SELECT * FROM deals where id=#{dealId}")
    @Results({
            @Result(column = "id", property = "id"),
            @Result(column = "renting_id", property = "rentingId"),
            @Result(column = "landlord_id", property = "landlordId"),
            @Result(column = "goods_id", property = "goodsId"),
            @Result(column = "is_answered", property = "isAnswered"),
            @Result(column = "estimated_start_date", property = "estimateStartDate"),
            @Result(column = "estimated_end_date", property = "estimateEndDate"),
            @Result(column = "real_start_date", property = "realStartDate"),
            @Result(column = "real_end_date", property = "realEndDate"),
            @Result(column = "handed", property = "isHanded"),
            @Result(column = "accepted_renting", property = "acceptedRenting"),
            @Result(column = "accepted_return", property = "acceptedReturn"),
            @Result(column = "is_closed", property = "isClosed")
    })
    Deal getDeal(long dealId);

    @Select("SELECT id FROM deals where landlord_id=#{landlordId} and goods_id=#{goodsId} and renting_id=#{rentingId} and is_answered=#{isAnswered}")
    long getId(Deal deal);

    @Select("SELECT count(id) FROM deals where landlord_id=#{landlordId} and goods_id=#{goodsId} and renting_id=#{rentingId} and is_answered=#{isAnswered}")
    long isExist(Deal deal);

    @Delete("DELETE FROM deals where goods_id=#{goodsId}")
    void deleteAllOnGoods(long goodsId);

    @Update("UPDATE deals SET real_start_date=clock_timestamp() where id=#{dealId}")
    void updateRealStartDate(long dealId);

    @Update("UPDATE deals SET real_end_date=clock_timestamp() where id=#{dealId}")
    void updateRealEndDate(long dealId);
}
