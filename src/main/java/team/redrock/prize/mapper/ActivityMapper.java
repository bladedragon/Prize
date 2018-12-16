package team.redrock.prize.mapper;

import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import team.redrock.prize.bean.Activity;
import team.redrock.prize.bean.ShowAct;
import team.redrock.prize.bean.UserInfo;

import java.util.List;

@Mapper
@Repository
@Transactional
public interface ActivityMapper {

    @Options(keyProperty="actid", keyColumn="id")
    @Insert("Insert into activity (actname,founder,url,status,time,actid,reward) value(#{actname},#{founder},#{url},#{status},#{time},#{actid},#{reward})ON DUPLICATE KEY UPDATE actname=#{actname},founder=#{founder},status=#{status},actid=#{actid},time=#{time}")
    int insert(Activity activity);

    @Delete("Delete from activity where actname = #{actname}")
    void delete(String actname);

    @Select("Select actid from activity where actname = #{actname}")
    List<String> SelectActivityId(@Param("actname") String actname);

    @Select("Select ifnull((Select actname from activity where actid = #{actid}),'')")
    String SelectActname(@Param("actid") String actnid);

    @Select("Select actname,founder,url,status,time,actid from activity group by actname")
    List<ShowAct> SelectActAll();

}