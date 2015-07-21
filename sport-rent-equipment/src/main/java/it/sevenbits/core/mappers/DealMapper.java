package it.sevenbits.core.mappers;

import it.sevenbits.web.domain.Deal;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

/**
 * Created by awemath on 7/21/15.
 */
public interface DealMapper {
    @Insert("INSERT INTO deals (landlord_id, renting_id, goods, is_accepted)" +
            " VALUES (#{landlordId}, #{rentingId}, #{goodsId}, #{isAccepted})")
    void save(final Deal deal);

    @Select("SELECT * FROM deals where id=#{dealId}")
    @Results({
            @Result(column = "id", property = "id"),
            @Result(column = "renting_id", property = "rentingId"),
            @Result(column = "landlors_id", property = "landlordId"),
            @Result(column = "goods_id", property = "goodsId"),
            @Result(column = "is_accepted", property = "isAccepted")
    })
    Deal getDeal(long dealId);


}
