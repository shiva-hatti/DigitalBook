package com.user.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.google.gson.Gson;
import com.user.payload.request.BookList;
import com.user.payload.request.BookRequest;
import com.user.payload.response.MessageResponse;
import com.user.util.BookRoutings;
import com.user.util.CommanConstant;

@Service
public class CallBookServices {
	
	@Autowired
	private RestTemplate restTemplate;
	
	HttpHeaders headers = new HttpHeaders();
	Gson gson = new Gson();
	
	public MessageResponse bookServiceCall(@Valid BookRequest bookRequest, String type) {
		String json = null;
		MessageResponse resp = null;
		try {
			headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
			HttpEntity<BookRequest> entity = new HttpEntity<BookRequest>(bookRequest, headers);
			if(type.equals(CommanConstant.CREATE.getName())) {
				json = restTemplate.exchange(BookRoutings.CREATE_BOOK, HttpMethod.POST, entity, String.class).getBody();
			} else {
				json = restTemplate.exchange(BookRoutings.UPDATE_BOOK, HttpMethod.PUT, entity, String.class).getBody();
			}
			resp = gson.fromJson(json, MessageResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resp;
	}

	public MessageResponse blockUnblockServiceCall(Long authorId, Long bookId, String block) {
		String json = null;
		MessageResponse resp = null;
		try {
			headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
			HttpEntity<String> entity = new HttpEntity<String>(headers);
			json = restTemplate.exchange(BookRoutings.BOOK_BLOCK_UNBLOCK+"/"+authorId+"/books/"+bookId+"/"+block, HttpMethod.GET, entity, String.class).getBody();
			resp = gson.fromJson(json, MessageResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resp;
	}

	public List<BookRequest> searchBook(BookRequest bookRequest) {
		String json = null;
		List<BookRequest> respList = new ArrayList<>();
		try {
			headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
			HttpEntity<BookRequest> entity = new HttpEntity<BookRequest>(bookRequest, headers);
			json = restTemplate.exchange(BookRoutings.SEARCH_BOOK, HttpMethod.POST, entity, String.class).getBody();
			BookList bookList = gson.fromJson(json, BookList.class );
			respList = bookList.getBookList();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return respList;
	}

}
