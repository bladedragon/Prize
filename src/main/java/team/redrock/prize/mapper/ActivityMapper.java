package team.redrock.prize.mapper;

import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import team.redrock.prize.bean.Activity;
import team.redrock.prize.bean.Acturl;
import team.redrock.prize.bean.ShowAct;
import team.redrock.prize.bean.UserInfo;

import java.util.List;
import java.util.Map;

@Mapper
@Repository
@Transactional
public interface ActivityMapper {

    @Options(keyProperty="actid", keyColumn="id")
    @Insert("Insert into activity (actname,founder,status,time,actid,reward,rewardID,mark) value(#{actname},#{founder},#{status},#{time},#{actid},#{reward},#{rewardID},#{mark})ON DUPLICATE KEY UPDATE actname=#{actname},founder=#{founder},status=#{status},actid=#{actid},time=#{time},rewardID=#{rewardID},mark=#{mark}")
    int insert(Activity activity);

    @Delete("Delete from activity where actid = #{actid}")
    void deleteAct(String actid);

    @Delete("Delete from activity where status = 2 and actid = #{actid}")
    void deleteActTemp(String actid);

    @Select("Select actid from activity where actname = #{actname}")
    List<String> SelectActivityId(@Param("actname") String actname);

//    @Select("Select  ifnull((Select actname from activity where actid = #{actid}),'')")
//    String SelectActname(@Param("actid") String actnid);


    @Results({
            @Result(property = "actname", column = "actname"),
            @Result(property = "urls",column = "actname",
                    many =@Many(select = "team.redrock.prize.mapper.ActivityMapper.SelectUrl"))
    })
    @Select("Select actname,founder,status,time,actid from activity group by actname,founder,status,time,actid")
    List<ShowAct> SelectActAll();

    @Delete("Delete from specified_type where actid = #{actid}")
    void deleteSpecifiedType(String actid);

    @Delete("Delete from non_specified_type where actid = #{actid}")
    void deleteNoSpecifiedType(String actid);

    @Select("Select ifnull((Select reward from activity where actid = #{actid}and rewardID = #{rewardID}),'')")
    String SelectReward(@Param("actid")String actid,@Param("rewardID")String rewardID);

    @Update("update activity set url =#{url} where actid = #{actid} and rewardID=#{rewardID}")
    int UpdateActUrl(@Param("actid") String actid,@Param("url") String acturl,@Param("rewardID") String rewardID);


    @Select("select reward ,url from activity where actname = #{actname}")
     Acturl SelectUrl(@Param("actname")String actname);

}
