package com.zk.boots;

import com.zk.boots.mapper.HeartBeatMapper;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
class BootsApplicationTests {

  @Autowired
  private HeartBeatMapper heartBeatMapper;

  @Test
  void contextLoads() {
    int result=heartBeatMapper.dbHit();
    System.out.println(result);
  }


}
