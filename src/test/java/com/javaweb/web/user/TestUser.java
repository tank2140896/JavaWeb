package com.javaweb.web.user;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.HashMap;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.javaweb.entity.rbac.User;
import com.javaweb.web.rbac.dao.UserDao;
import com.javaweb.web.rbac.service.UserService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:config/spring/spring-common-config.xml",
								   "classpath:config/spring/spring-mvc-config.xml"})
@WebAppConfiguration
public class TestUser {
	
	private MockMvc mockMvc;
	
	@Autowired
	private WebApplicationContext wac;
	
	@Before
	public void setUp() {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
	}
	
	@Autowired
	private UserDao userDao;
	
	@Autowired
	private UserService userService;
	
	@Test
	public void testController() throws Exception {
		mockMvc.perform(get("/a/b")
			   .contentType(MediaType.APPLICATION_JSON)
			   .characterEncoding("UTF-8")
			   .accept(MediaType.APPLICATION_JSON))
			   .andDo(print())
			   .andExpect(status().isOk());	
	}
	
	@Test
	public void testService() throws Exception {
		User user = userService.getUserByUsernameAndPassword(new HashMap<>());
		System.out.println(user);
	}
	
	@Test
	public void testDao() throws Exception {
		User user = userDao.getUserByUsernameAndPassword(new HashMap<>());
		System.out.println(user);
	}
	
	/**
	@Test
	public void testSearchUserInfo() throws Exception {
		//模拟get和delete
		mockMvc.perform(get("/api/test/get/1")
			   .contentType(MediaType.APPLICATION_JSON)
			   .characterEncoding("UTF-8")
			   .accept(MediaType.APPLICATION_JSON))
			   .andDo(print())
			   .andExpect(status().isOk());	
	}
	
	@Test
	public void testSearchUser() throws Exception {
		//模拟post和update
		String requestBody = "{\"id\":1,\"username\":\"a\",\"password\":\"b\"}";
		mockMvc.perform(post("/api/test/post")
			   .contentType(MediaType.APPLICATION_JSON)
			   .content(requestBody)
			   .characterEncoding("UTF-8")
			   .accept(MediaType.APPLICATION_JSON))
			   .andDo(print())
			   .andExpect(status().isOk());
	}
	*/

}
