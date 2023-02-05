package com.kim1387.travel.domain.city.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.kim1387.travel.domain.city.controller.document.CityRestDocument;
import com.kim1387.travel.domain.city.dto.CityInfo;
import com.kim1387.travel.domain.city.dto.CityInfoList;
import com.kim1387.travel.domain.city.dto.CityRegisterRequest;
import com.kim1387.travel.domain.city.dto.CityUpdateRequest;
import com.kim1387.travel.domain.city.service.CityService;
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
import java.util.ArrayList;
import java.util.List;

import static java.time.LocalDateTime.now;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
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
    objectMapper.registerModule(new JavaTimeModule());
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
    CityInfo cityInfo =
        CityInfo.builder()
            .name("한국")
            .introContent("간단한 한국 소개")
            .createdAt(now())
            .updatedAt(now())
            .build();
    CityRegisterRequest cityRegisterRequest =
        CityRegisterRequest.builder()
            .userId(1L)
            .cityName("한국")
            .cityIntroContent("간단한 한국 소개")
            .build();
    // when
    when(cityService.registerCity(any())).thenReturn(cityInfo);

    // then
    mockMvc
        .perform(
            RestDocumentationRequestBuilders.post("/api/v1/city")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(cityRegisterRequest)))
        .andExpect(status().isOk())
        .andDo(print())
        .andDo(CityRestDocument.getCreateCityInfoDocument());
  }

  @Test
  @DisplayName("city 삭제 api")
  void deleteCity() throws Exception {
    // given

    // when
    doNothing().when(cityService).deleteCity(any());

    // then
    mockMvc
        .perform(RestDocumentationRequestBuilders.delete("/api/v1/city/{id}", 1L))
        .andExpect(status().isOk())
        .andDo(print())
        .andDo(CityRestDocument.getDeleteCityDocument());
  }

  @Test
  @DisplayName("city 조회 api")
  void readOneCity() throws Exception {
    // given
    CityInfo cityInfo =
        CityInfo.builder()
            .name("한국")
            .introContent("간단한 한국 소개")
            .createdAt(now())
            .updatedAt(now())
            .build();
    // when
    when(cityService.findCityInfoById(any())).thenReturn(cityInfo);

    // then
    mockMvc
        .perform(
            RestDocumentationRequestBuilders.get("/api/v1/city/{id}", 1L)
                .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andDo(print())
        .andDo(CityRestDocument.getOneCityInfoByIdDocument());
  }

  @Test
  @DisplayName("사용자 별 조회 api")
  void readCityByUser() throws Exception {
    // given
    List<CityInfo> expectedCityInfoList = createCityInfos();
    // when
    when(cityService.findCityInfoByUserId(any()))
        .thenReturn(new CityInfoList(expectedCityInfoList));

    // then
    mockMvc
        .perform(
            RestDocumentationRequestBuilders.get("/api/v1/city/users/{userId}", 1L)
                .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andDo(print())
        .andDo(CityRestDocument.getCityInfoListByUserIdDocument());
  }

  @Test
  @DisplayName("city 수정 api")
  void updateCityByUser() throws Exception {
    // given
    CityInfo cityInfo =
        CityInfo.builder()
            .name("한국")
            .introContent("간단한 한국 소개")
            .createdAt(now())
            .updatedAt(now())
            .build();
    CityUpdateRequest cityUpdateRequest =
        CityUpdateRequest.builder().cityId(1L).cityName("한국").cityIntroContent("간단한 한국 소개").build();
    // when
    when(cityService.updateCityInfo(any())).thenReturn(cityInfo);

    // then
    mockMvc
        .perform(
            RestDocumentationRequestBuilders.put("/api/v1/city")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(cityUpdateRequest)))
        .andExpect(status().isOk())
        .andDo(print())
        .andDo(CityRestDocument.getUpdateCityInfoByIdDocument());
  }

  private static List<CityInfo> createCityInfos() {
    List<String> cityList = List.of("서울", "수원", "판교", "서울", "수원", "판교", "인천", "안양", "강릉", "원주");
    List<CityInfo> newCityList = new ArrayList<>(10);
    for (String city : cityList) {
      newCityList.add(new CityInfo(city, "여기는 " + city, now(), now()));
    }
    return newCityList;
  }
}
