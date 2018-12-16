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
public interface GetPrizerMapper {


    @Update("update specified_type set status =#{status} where openid = #{openid}")
    Boolean updateSpecified_type(@Param(value = "openid") String openid,@Param(value = "status") int status);


    @Select("Select * from specified_type where (openid = #{openid}and actid = #{actid}and reward =#{reward})")
    StudentA findStudentA(@Param(value = "openid") String openid, @Param(value = "actid") String actid,@Param(value = "reward") String reward);

    @Insert("Insert into non_specified_type (stuname,stuid,actid,openid,add_time,reward) value(#{stuname},#{stuid},"+
            "#{actid},#{openid},#{add_time},#{reward})")
    void insertNonSpecified_type(StudentB student);

    @Select("Select * from non_specified_type where openid = #{openid} and actid = #{actid}and reward = #{reward}")
    StudentB findStudentB(@Param(value = "openid") String openid,@Param(value = "actid") String actid,@Param(value = "reward") String reward);





}



