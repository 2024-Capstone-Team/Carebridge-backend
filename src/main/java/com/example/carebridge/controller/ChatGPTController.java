package com.example.carebridge.controller;

import com.example.carebridge.dto.ChatCompletionDto;
import com.example.carebridge.service.ChatGPTService;
import com.example.carebridge.service.HospitalInformationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * ChatGPT API 컨트롤러
 * ChatGPT API 와의 통신을 담당하는 컨트롤러 클래스입니다.
 */
@Slf4j
@RestController
@RequestMapping(value = "/api/chat-gpt")
public class ChatGPTController {

    private final ChatGPTService chatGPTService;
    private final HospitalInformationService hospitalInformationService;

    // ChatGPTService 와 HospitalInformationService 를 주입받는 생성자
    public ChatGPTController(ChatGPTService chatGPTService, HospitalInformationService hospitalInformationService) {
        this.chatGPTService = chatGPTService;
        this.hospitalInformationService = hospitalInformationService;
    }

    /**
     * [API] ChatGPT 프롬프트 명령어를 수행합니다. : gpt-4, gpt-4 turbo, gpt-3.5-turbo
     *
     * @param chatCompletionDto 프롬프트 요청 데이터
     * @return 프롬프트 응답 데이터
     */
    @PostMapping("/prompt")
    public ResponseEntity<Map<String, Object>> selectPrompt(@RequestBody ChatCompletionDto chatCompletionDto) {
        Map<String, Object> result = chatGPTService.prompt(chatCompletionDto);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    /**
     * [API] 프롬프트 내용을 기반으로 카테고리를 분류합니다.
     *
     * @param chatCompletionDto 프롬프트 요청 데이터
     * @return 카테고리 분류 응답 데이터
     */
    @PostMapping("/category")
    public ResponseEntity<Map<String, Object>> selectCategoryPrompt(@RequestBody ChatCompletionDto chatCompletionDto) {
        chatCompletionDto.getMessages().forEach(message ->
                message.setContent("다음 메시지를 다음 범주 중 하나로 분류하라: {정보성 질문, 긴급 요청, 의료진 요청} 메시지 : " + message.getContent())
        );

        Map<String, Object> result = chatGPTService.prompt(chatCompletionDto);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    /**
     * [API] 프롬프트 내용을 기반으로 데이터베이스의 내용과 가장 유사한 내용을 바탕으로 프롬프트 명령어 수행
     *
     * @param chatCompletionDto 프롬프트 요청 데이터
     * @param hospital_id 병원 ID
     * @return 프롬프트 응답 데이터
     */
    @PostMapping("/prompt-data/{hospital_id}")
    public ResponseEntity<Map<String, Object>> selectPromptData(@RequestBody ChatCompletionDto chatCompletionDto, @PathVariable int hospital_id) {
        String prompt = chatCompletionDto.getMessages().get(0).getContent();
        String mostSimilarInfo = hospitalInformationService.findMostSimilarHospitalInformation(prompt, hospital_id).getInformation();

        chatCompletionDto.getMessages().forEach(message ->
                message.setContent("너는 병원에 소속되어 있고, 다음 내용을 기반으로 답변하라 " + mostSimilarInfo + " 답변해야할 메시지 : " + message.getContent())
        );

        Map<String, Object> result = chatGPTService.prompt(chatCompletionDto);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    /**
     * [API] 프롬프트 내용을 기반으로 메시지를 문법, 띄어쓰기 등을 수정합니다.
     *
     * @param chatCompletionDto 프롬프트 요청 데이터
     * @return 수정된 메시지 응답 데이터
     */
    @PostMapping("/trim")
    public ResponseEntity<Map<String, Object>> selectTrim(@RequestBody ChatCompletionDto chatCompletionDto) {
        chatCompletionDto.getMessages().forEach(message ->
                message.setContent("병원 형식에 맞게 메시지를 문법, 띄어쓰기 등을 수정하라. 메시지 : " + message.getContent())
        );

        Map<String, Object> result = chatGPTService.prompt(chatCompletionDto);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}