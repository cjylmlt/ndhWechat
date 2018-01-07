package com.cjy.WechatHome;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.cjy.WechatHome.web.model.TopRecord;
import com.cjy.WechatHome.web.service.RecordService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class WechatHomeApplicationTests {
@Autowired
RecordService recordServie;
	@Test
	public void contextLoads() {
//		List<TopRecord> topRecordList = recordServie.selectTopRecord();
//		System.out.println();
	}

}
