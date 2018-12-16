package team.redrock.prize.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import team.redrock.prize.bean.StudentA;
import team.redrock.prize.bean.StudentB;

import java.util.List;

@Mapper
@Repository
@Transactional
public interface SpecifiedTypeMapper {

    @Insert("Insert into specified_type (stuname,college,stuid,telephone,reward,actid,openid,add_time,status) value(#{stuname},#{college},#{stuid},#{telephone},"+
            "#{reward},#{actid},#{openid},#{add_time},#{status})ON DUPLICATE KEY UPDATE college=#{college}," +
            "telephone=#{telephone},reward=#{reward},actid=#{actid},add_time=#{add_time},status=#{status}")
    void insert(StudentA student);

    @Select("select * from specified_type where actid = #{actid}")
    List<StudentA> findStudentA(String actid);

    @Select("select * from non_specified_type where  actid = #{actid}") // 查询所有
    List<StudentB> findStudentB(String actid);







}
