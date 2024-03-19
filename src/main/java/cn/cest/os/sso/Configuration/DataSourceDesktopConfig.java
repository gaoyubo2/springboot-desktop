//package cn.cest.os.sso.Configuration;
//
//import com.baomidou.mybatisplus.extension.spring.MybatisSqlSessionFactoryBean;
//import org.apache.ibatis.session.SqlSessionFactory;
//import org.mybatis.spring.SqlSessionFactoryBean;
//import org.mybatis.spring.SqlSessionTemplate;
//import org.mybatis.spring.annotation.MapperScan;
//import org.springframework.beans.factory.annotation.Qualifier;
//import org.springframework.boot.context.properties.ConfigurationProperties;
//import org.springframework.boot.jdbc.DataSourceBuilder;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.context.annotation.Primary;
//import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
//
//import javax.sql.DataSource;
//
//@Configuration
//@MapperScan(basePackages = "cn.cest.os.sso.mapper.desktop",sqlSessionFactoryRef = "desktopSqlSessionFactory")
//public class DataSourceDesktopConfig {
//
//
//    @Bean(name = "desktopDataSource")
//    @ConfigurationProperties(prefix = "spring.datasource.desktop")
//    public DataSource getDesktopSource() {
//        System.out.println("初始化desktop的数据源");
//        return DataSourceBuilder.create().build();
//    }
//
//    @Bean(name = "desktopSqlSessionFactory")
//    public SqlSessionFactory desktopSqlSessionFactory(@Qualifier("desktopDataSource") DataSource dataSource) throws Exception {
//        MybatisSqlSessionFactoryBean bean = new MybatisSqlSessionFactoryBean();
//        bean.setDataSource(dataSource);
//        // mapper的xml形式文件位置必须要配置，不然将报错：no statement （这种错误也可能是mapper的xml中，namespace与项目的路径不一致导致）
//        bean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath:mapper/desktop/*.xml"));
//        return bean.getObject();
//    }
//
//    @Bean(name = "desktopSqlSessionTemplate")
//    public SqlSessionTemplate testSqlSessionTemplate(@Qualifier("desktopSqlSessionFactory") SqlSessionFactory sqlSessionFactory) throws Exception {
//        return new SqlSessionTemplate(sqlSessionFactory);
//    }
//
//
//}
