package team.redrock.prize.mapper;

import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import team.redrock.prize.bean.Activity;
import team.redrock.prize.bean.UserInfo;

@Mapper
@Repository
@Transactional
public interface ActivityMapper {

    @Options(keyProperty="actid", keyColumn="id")
    @Insert("Insert into activity (actname,founder,url,type,status,time,actid) value(#{actname},#{founder},#{url},#{type},#{status},#{time},#{actid})")
    int insert(Activity activity);

    @Delete("Delete from activity where actname = #{actname}")
    void delete(String actname);

    @Select("Select ifnull((Select actid from activity where actname = #{actname}),'0')")
    String SelectActivityId(@Param("actname") String actname);

    @Select("Select ifnull((Select actname from activity where actid = #{actid}),'')")
    String SelectActname(@Param("actid") String actnid);

}
