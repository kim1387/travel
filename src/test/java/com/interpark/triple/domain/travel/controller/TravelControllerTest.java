package com.interpark.triple.domain.travel.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.interpark.triple.domain.travel.controller.document.TravelRestDocument;
import com.interpark.triple.domain.travel.dto.TravelCreateRequest;
import com.interpark.triple.domain.travel.dto.TravelInfo;
import com.interpark.triple.domain.travel.dto.TravelUpdateRequest;
import com.interpark.triple.domain.travel.service.TravelService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
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

import static java.time.LocalDateTime.now;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(RestDocumentationExtension.class)
@WebMvcTest(controllers = TravelController.class)
class TravelControllerTest {
  @MockBean private TravelService travelService;

  private ObjectMapper objectMapper;

  private MockMvc mockMvc;

  @BeforeEach
  void setUp(
      WebApplicationContext webApplicationContext,
      RestDocumentationContextProvider restDocumentationContextProvider) {
    objectMapper = new ObjectMapper();
    objectMapper.registerModule(new JavaTimeModule());
    mockMvc =
        MockMvcBuilders.webAppContextSetup(webApplicationContext)
            .addFilter(new CharacterEncodingFilter(StandardCharsets.UTF_8.name(), true))
            .apply(documentationConfiguration(restDocumentationContextProvider))
            .build();
  }

  @Test
  @DisplayName("여행 생성 api test")
  void createTravelTest() throws Exception {
    // given
    TravelInfo returnTravelInfo =
        TravelInfo.builder()
            .cityName("서을")
            .userName("김기현")
            .startTravelAt(now())
            .endTravelAt(now().plusDays(2))
            .build();
    TravelCreateRequest createRequest =
        TravelCreateRequest.builder()
            .userId(1L)
            .cityId(1L)
            .travelStartAt(now())
            .travelEndAt(now().plusDays(2))
            .build();
    // when
    when(travelService.createTravel(any())).thenReturn(returnTravelInfo);

    // then
    mockMvc
        .perform(
            RestDocumentationRequestBuilders.post("/api/v1/travel")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(createRequest)))
        .andExpect(status().isOk())
        .andDo(print())
        .andDo(TravelRestDocument.getCreateTravelInfoDocument());
  }

  @Test
  @DisplayName("여행 계획 삭제 api")
  void deleteTravelTest() throws Exception {
    // given

    // when
    Mockito.doNothing().when(travelService).deleteTravel(any());

    // then
    mockMvc
        .perform(
            RestDocumentationRequestBuilders.delete("/api/v1/travel/{id}", 1L)
                .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andDo(print())
        .andDo(TravelRestDocument.getDeleteTravelDocument());
  }

  @Test
  @DisplayName("여행 계획 수정 api")
  void updateTravelTest() throws Exception {
    // given
    TravelInfo returnTravelInfo =
        TravelInfo.builder()
            .cityName("서을")
            .userName("김기현")
            .startTravelAt(now())
            .endTravelAt(now().plusDays(2))
            .build();
    TravelUpdateRequest updateRequest =
        TravelUpdateRequest.builder()
            .travelId(1L)
            .userId(1L)
            .cityId(1L)
            .travelStartAt(now())
            .travelEndAt(now().plusDays(2))
            .build();
    // when
    when(travelService.updateTravel(any())).thenReturn(returnTravelInfo);

    // then
    mockMvc
        .perform(
            RestDocumentationRequestBuilders.put("/api/v1/travel")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updateRequest)))
        .andExpect(status().isOk())
        .andDo(print())
        .andDo(TravelRestDocument.getUpdateTravelInfoDocument());
  }

  @Test
  @DisplayName("여행 계획 단일 조회 api")
  void findOneTravelByIdTest() throws Exception {
    // given
    TravelInfo returnTravelInfo =
        TravelInfo.builder()
            .cityName("서을")
            .userName("김기현")
            .startTravelAt(now())
            .endTravelAt(now().plusDays(2))
            .build();
    // when
    when(travelService.findOneTravelById(any())).thenReturn(returnTravelInfo);

    // then
    mockMvc
        .perform(
            RestDocumentationRequestBuilders.get("/api/v1/travel/{id}", 1L)
                .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andDo(print())
        .andDo(TravelRestDocument.getOneTravelInfoWithIdDocument());
  }
}
