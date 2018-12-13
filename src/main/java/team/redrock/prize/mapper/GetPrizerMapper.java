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


    @Select("Select * from specified_type where (openid = #{openid}and actid = #{actid})")
    List<StudentA> findStudentA(@Param(value = "openid") String openid, @Param(value = "actid") String actid);

    @Insert("Insert into non_specified_type (stuname,college,stuid,telephone,reward,actid,openid,add_time) value(#{stuname},#{college},#{stuid},#{telephone},"+
            "#{reward},#{actid},#{openid},#{add_time}")
    void insertNonSpecified_type(StudentB student);

    @Select("Select * from non_specified_type where openid = #{openid},activity = #{actid}")
    StudentB findStudentB(@Param(value = "openid") String openid,@Param(value = "actid") String actid);





}



