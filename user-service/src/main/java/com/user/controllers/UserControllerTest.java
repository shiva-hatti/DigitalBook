package com.user.controllers;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import com.user.models.User;
import com.user.service.UserService;
import com.user.service.UserServiceMock;

@RunWith(SpringRunner.class)

@SpringBootTest(webEnvironment = WebEnvironment.MOCK, classes = { UserController.class })
public class UserControllerTest {
	@MockBean 
	private UserServiceMock UserServiceMock;
	@Mock
	UserService userService;

	private MockMvc mockMvc;

	@Autowired
	private WebApplicationContext webApplicationContext;

	@Before
	public void setUp() {
		this.mockMvc = webAppContextSetup(webApplicationContext).build();
	}
	
	@Test
	public void should_CreateUser_When_ValidRequest() throws Exception {
		//Set<String> set = new HashSet<>();
		//set.add("AUTHOR");
		//SignupRequest signup = new SignupRequest("fdgfd","shiva_h@gmail.com",set,"fsdfsdf");
		User user = new User();
		//user.setUsername("shiva_r");
		//user.setEmail("shiva_r@gmail.com");
		Optional<User> users = Optional.of(user);
		when(userService.findById(Mockito.anyLong())).thenReturn(users);
		mockMvc.perform(get("/api/auth/getUserById/16")
				   .contentType(MediaType.APPLICATION_JSON)
				  // .content("{ \"username\": \"shiva_r\", \"email\": \"shiva_r@gmail.com\" }")	
				   .accept(MediaType.APPLICATION_JSON))
				   //.andExpect(status().isCreated())  //
				   .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
				   .andExpect(jsonPath("$.username").value("shiva_r"))			   
				   .andExpect(jsonPath("$.email").value("shiva_r@gmail.com"));
	}
}
