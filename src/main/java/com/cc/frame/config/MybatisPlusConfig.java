package com.cc.frame.config;

//import com.baomidou.mybatisplus.annotation.DbType;
//import com.baomidou.mybatisplus.extension.plugins.inner.BlockAttackInnerInterceptor;
//import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
//import org.mybatis.spring.annotation.MapperScan;
//import org.springframework.context.annotation.Configuration;


//@Configuration
//@MapperScan("com.cc.server.mapper")
public class MybatisPlusConfig {
	/**
	 * 分页插件 3.5.X
	 */
//    @Bean
//    public PaginationInnerInterceptor paginationInnerInterceptor() {
//        PaginationInnerInterceptor paginationInterceptor = new PaginationInnerInterceptor();
//        // 设置最大单页限制数量，默认 500 条，-1 不受限制
//        paginationInterceptor.setMaxLimit(-1L);
//        paginationInterceptor.setDbType(DbType.MYSQL);
//        return paginationInterceptor;
//    }

	/**
	 * 阻止恶意全表更新删除
	 *
	 * @return {@code MybatisPlusInterceptor}
	 */
//    @Bean
//    public MybatisPlusInterceptor mybatisPlusInterceptor() {
//        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
//        interceptor.addInnerInterceptor(new IllegalSQLInnerInterceptor());
//        interceptor.addInnerInterceptor(new BlockAttackInnerInterceptor());
//        return interceptor;
//    }
}
