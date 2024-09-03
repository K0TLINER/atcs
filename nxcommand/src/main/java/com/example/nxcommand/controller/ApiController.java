package com.example.nxcommand.controller;

import com.example.nxcommand.data.info.GameInfo;
import com.example.nxcommand.dto.SuccessResponse;
import com.example.nxcommand.service.ApiService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/nxcommand")
@RequiredArgsConstructor
@Slf4j
public class ApiController {
    private final ApiService apiService;

    @RequestMapping(path = "", method = RequestMethod.GET)
    public ResponseEntity<SuccessResponse> callApi(
            @RequestHeader("X-Info-Game") GameInfo gameInfo,
            @RequestHeader("X-Authentication-Id") String memberId
    ) {
        log.info(String.format("request \"/nxcommand\" GameInfo : %s, MemberId : %s", gameInfo, memberId));
        apiService.checkApiCall(gameInfo, memberId);
        return ResponseEntity.ok().build();
    }
}
