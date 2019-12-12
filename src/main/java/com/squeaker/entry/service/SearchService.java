package com.squeaker.entry.service;

import com.squeaker.entry.domain.payload.response.SearchUserResponse;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface SearchService {
    List<SearchUserResponse> searchUser(String token, String userName);
}
