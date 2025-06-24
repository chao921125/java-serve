package service.impl;

import entity.SysDepartment;
import java.util.List;
import mapper.SysDepartmentMapper;
    import service.SysDepartmentService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import jakarta.annotation.Resource;
import org.apache.ibatis.annotations.Param;

/**
* <p>
    * 部门表 服务实现类
    * </p>
*
* @author cc
* @since 2025-06-23 18:27:36
*/
@Service
    public class SysDepartmentServiceImpl extends ServiceImpl<SysDepartmentMapper, SysDepartment> implements SysDepartmentService {
    @Resource
    private SysDepartmentMapper sysDepartmentMapper;

    /**
    *  查询表sys_department所有信息
    */
    @Override
    public List<SysDepartment> selectAllSysDepartment() { return sysDepartmentMapper.selectAllSysDepartment();}

            /**
            *  根据主键id查询表sys_department信息
            *  @param id
            */
            @Override
            public SysDepartment selectSysDepartmentByid(@Param("id") Long id) { return sysDepartmentMapper.selectSysDepartmentByid(id);}

    /**
    *  根据条件查询表sys_department信息
    *  @param sysDepartment
    */
    @Override
    public List<SysDepartment> selectSysDepartmentByCondition(SysDepartment sysDepartment) { return sysDepartmentMapper.selectSysDepartmentByCondition(sysDepartment);}

            /**
            *  根据主键id查询表sys_department信息
            *  @param id
            */
            @Override
            public Integer deleteSysDepartmentByid(@Param("id") Long id) { return sysDepartmentMapper.deleteSysDepartmentByid(id);}

            /**
            *  根据主键id更新表sys_department信息
            *  @param sysDepartment
            */
            @Override
            public Integer updateSysDepartmentByid(SysDepartment sysDepartment) { return sysDepartmentMapper.updateSysDepartmentByid(sysDepartment);}

            /**
            *  新增表sys_department信息
            *  @param sysDepartment
            */
            @Override
            public Integer insertSysDepartment(SysDepartment sysDepartment) { return sysDepartmentMapper.insertSysDepartment(sysDepartment);}
    }
