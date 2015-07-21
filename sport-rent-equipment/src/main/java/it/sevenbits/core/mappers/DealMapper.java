package it.sevenbits.core.mappers;

import it.sevenbits.web.domain.Deal;
import org.apache.ibatis.annotations.Insert;

/**
 * Created by awemath on 7/21/15.
 */
public interface DealMapper {
    @Insert("INSERT INTO deals (landlord_id, renting_id, goods, is_accepted)" +
            " VALUES (#{landlordId}, #{rentingId}, #{goodsId}, #{isAccepted})")
    void save(final Deal deal);
}
