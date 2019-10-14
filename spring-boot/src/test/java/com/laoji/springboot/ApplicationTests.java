package com.laoji.springboot;
import java.util.Date;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.laoji.springboot.domain.TbUser;
import com.laoji.springboot.mapper.TbUserMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
@Rollback
public class ApplicationTests {
    @Resource
    TbUserMapper mapper;

    @Test
    public void contextLoads() {
        System.out.println("???");
    }
    @Test
    public void test1(){
        List<TbUser> list = mapper.selectAll();
        for (TbUser l:list
             ) {
            System.out.println(l.toString());
        }

    }
    @Test
    public void insertTest(){
        TbUser tbUser=new TbUser();
        tbUser.setUsername("laoji");
        tbUser.setPassword("123456");
        tbUser.setPhone("15625762942");
        tbUser.setEmail("1026165407@qq.com");
        tbUser.setCreated(new Date());
        tbUser.setUpdated(new Date());
        mapper.insert(tbUser);
    }
    @Test
    public void updateTest(){
        TbUser tbUser = mapper.selectByPrimaryKey(37L);
        tbUser.setUsername("laoji666");
        mapper.updateByPrimaryKey(tbUser);
    }
    @Test
    public void testDelete(){
        mapper.deleteByPrimaryKey(37L);
    }
    @Test
    public void testPage(){
        PageHelper.startPage(1,5);
        PageInfo<TbUser> pageInfo=new PageInfo<>(mapper.selectAll());
        System.out.println(pageInfo.getList());
        pageInfo.getList().forEach(tbUser->{
            System.out.println(tbUser);
        });
    }
}
