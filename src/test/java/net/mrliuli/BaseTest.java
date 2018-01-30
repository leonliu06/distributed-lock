package net.mrliuli;

import org.junit.Ignore;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by leon on 2018/1/30.
 */

/**
 * @RunWith 用于指定junit运行环境，是junit提供给其他框架测试环境接口扩展，
 * 为了便于使用spring的依赖注入，spring提供了 org.springframework.test.context.junit4.SpringJUnit4ClassRunner 作为Junit测试环境。
 *
 * @ContextConfiguration 导入配置文件
 *
 * @Ignore 忽略该类的测试方法执行，否则 SpringJUnit4ClassRunner 框架会在该类中找不到测试方法时报错。
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring/*.xml"})
@Ignore
public class BaseTest {
}
