package com.interpark.triple.domain.city.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.interpark.triple.domain.city.controller.document.CityRestDocument;
import com.interpark.triple.domain.city.dto.CityInfoResponse;
import com.interpark.triple.domain.city.dto.CityRegisterRequest;
import com.interpark.triple.domain.city.service.CityService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(RestDocumentationExtension.class)
@WebMvcTest(controllers = CityController.class)
class CityControllerTest {

  @MockBean private CityService cityService;

  private ObjectMapper objectMapper;

  private MockMvc mockMvc;

  @BeforeEach
  void setUp(
      WebApplicationContext webApplicationContext,
      RestDocumentationContextProvider restDocumentationContextProvider) {
    objectMapper = new ObjectMapper();
    mockMvc =
        MockMvcBuilders.webAppContextSetup(webApplicationContext)
            .addFilter(new CharacterEncodingFilter(StandardCharsets.UTF_8.name(), true))
            .apply(documentationConfiguration(restDocumentationContextProvider))
            .build();
  }

  @Test
  @DisplayName("city 등록 api")
  void registerCity() throws Exception {
    // given
    CityInfoResponse cityInfoResponse =
        CityInfoResponse.builder()
            .name("한국")
            .introContent("간단한 한국 소개")
            .createdAt(LocalDateTime.now())
            .updatedAt(LocalDateTime.now())
            .build();
    CityRegisterRequest cityRegisterRequest =
        CityRegisterRequest.builder().cityName("한국").cityIntroContent("간단한 한국 소개").build();
    // when
    when(cityService.registerCity(any())).thenReturn(cityInfoResponse);

    // then
    mockMvc
        .perform(
            RestDocumentationRequestBuilders.post("/api/v1/city")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(cityRegisterRequest)))
        .andExpect(status().isOk())
        .andDo(print())
        .andDo(CityRestDocument.getRegisterCityDocument());
  }
}
