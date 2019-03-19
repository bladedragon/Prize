package team.redrock.prize.mapper;

import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import team.redrock.prize.bean.StudentA;
import team.redrock.prize.bean.StudentB;

import java.util.List;

@Mapper
@Repository
@Transactional
public interface SpecifiedTypeMapper {

    @Insert("Insert into specified_type (stuname,college,stuid,telephone,reward,actid,openid,add_time,status,rewardID) value(#{stuname},#{college},#{stuid},#{telephone},"+
            "#{reward},#{actid},#{openid},#{add_time},#{status},#{rewardID})ON DUPLICATE KEY UPDATE college=#{college}," +
            "telephone=#{telephone},reward=#{reward},actid=#{actid},add_time=#{add_time},status=#{status},rewardID=#{rewardID}")
    void insert(StudentA student);

    @Select("select * from specified_type where actid = #{actid}")
    List<StudentA> findStudentA(String actid);

    @Select("select * from non_specified_type where  actid = #{actid}") // 查询所有
    List<StudentB> findStudentB(String actid);

    @Update("update specified_type set push_status =#{push_status} where actid = #{actid} and rewardID = #{rewardID} and stuid = #{stuid}")
    Boolean updatePush_status(@Param(value = "push_status") int push_status, @Param(value = "actid") String actid,@Param(value = "rewardID")String rewardID,@Param(value = "stuid")String stuid );






}
