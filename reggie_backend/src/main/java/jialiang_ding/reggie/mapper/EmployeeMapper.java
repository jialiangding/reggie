package jialiang_ding.reggie.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import jialiang_ding.reggie.entity.Employee;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface EmployeeMapper  extends BaseMapper<Employee> {
}
