package cn.studycarbon.dao;

import cn.studycarbon.domain.StudyCarbonInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface StudyCarbonInfoDao {
    @Select("select * from studycarbon_info where id=1")
    public StudyCarbonInfo getInfo();
}
