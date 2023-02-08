package com.kim1387.travel.domain.city.controller.document;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;

import org.springframework.restdocs.mockmvc.RestDocumentationResultHandler;
import org.springframework.restdocs.operation.preprocess.Preprocessors;
import org.springframework.restdocs.payload.JsonFieldType;

public class CityRestDocument {
  public static RestDocumentationResultHandler getCreateCityInfoDocument() {
    return document(
        "/city/create",
        Preprocessors.preprocessRequest(Preprocessors.prettyPrint()),
        Preprocessors.preprocessResponse(Preprocessors.prettyPrint()),
        requestFields(
            fieldWithPath("userId").type(JsonFieldType.NUMBER).description("userId"),
            fieldWithPath("cityName").type(JsonFieldType.STRING).description("city 이름"),
            fieldWithPath("cityIntroContent").type(JsonFieldType.STRING).description("city 소개")),
        responseFields(
            fieldWithPath("code").type(JsonFieldType.STRING).description("Business code"),
            fieldWithPath("message").type(JsonFieldType.STRING).description("response message"),
            fieldWithPath("data.name").type(JsonFieldType.STRING).description("city 이름"),
            fieldWithPath("data.introContent")
                .type(JsonFieldType.STRING)
                .description("city 에 대한 소개"),
            fieldWithPath("data.createdAt").type(JsonFieldType.STRING).description("city 내용 생성 일자"),
            fieldWithPath("data.updatedAt")
                .type(JsonFieldType.STRING)
                .description("city 내용 수정 일자")));
  }

  public static RestDocumentationResultHandler getDeleteCityDocument() {
    return document(
        "/city/delete",
        Preprocessors.preprocessRequest(Preprocessors.prettyPrint()),
        Preprocessors.preprocessResponse(Preprocessors.prettyPrint()),
        pathParameters(parameterWithName("id").description("삭제할 city id")),
        responseFields(
            fieldWithPath("code").type(JsonFieldType.STRING).description("Business code"),
            fieldWithPath("message").type(JsonFieldType.STRING).description("response message"),
            fieldWithPath("data").type(JsonFieldType.STRING).description("empty String")));
  }

  public static RestDocumentationResultHandler getCityInfoListByUserIdDocument() {
    return document(
        "/city/users/get",
        Preprocessors.preprocessRequest(Preprocessors.prettyPrint()),
        Preprocessors.preprocessResponse(Preprocessors.prettyPrint()),
        pathParameters(parameterWithName("userId").description("조회할 user id")),
        responseFields(
            fieldWithPath("code").type(JsonFieldType.STRING).description("Business code"),
            fieldWithPath("message").type(JsonFieldType.STRING).description("response message"),
            fieldWithPath("data.cityInfos.[].name")
                .type(JsonFieldType.STRING)
                .description("city 이름"),
            fieldWithPath("data.cityInfos.[].introContent")
                .type(JsonFieldType.STRING)
                .description("city 에 대한 소개"),
            fieldWithPath("data.cityInfos.[].createdAt").description("city 내용 생성 일자"),
            fieldWithPath("data.cityInfos.[].updatedAt").description("city 내용 수정 일자")));
  }

  public static RestDocumentationResultHandler getUpdateCityInfoByIdDocument() {
    return document(
        "/city/update",
        Preprocessors.preprocessRequest(Preprocessors.prettyPrint()),
        Preprocessors.preprocessResponse(Preprocessors.prettyPrint()),
        requestFields(
            fieldWithPath("cityId").type(JsonFieldType.NUMBER).description("city id"),
            fieldWithPath("cityName").type(JsonFieldType.STRING).description("city 이름"),
            fieldWithPath("cityIntroContent").description("city 소개")),
        responseFields(
            fieldWithPath("code").type(JsonFieldType.STRING).description("Business code"),
            fieldWithPath("message").type(JsonFieldType.STRING).description("response message"),
            fieldWithPath("data.name").type(JsonFieldType.STRING).description("city 이름"),
            fieldWithPath("data.introContent")
                .type(JsonFieldType.STRING)
                .description("city 에 대한 소개"),
            fieldWithPath("data.createdAt").description("city 내용 생성 일자"),
            fieldWithPath("data.updatedAt").description("city 내용 수정 일자")));
  }

  public static RestDocumentationResultHandler getOneCityInfoByIdDocument() {
    return document(
        "/city/get/one",
        Preprocessors.preprocessRequest(Preprocessors.prettyPrint()),
        Preprocessors.preprocessResponse(Preprocessors.prettyPrint()),
        pathParameters(parameterWithName("id").description("조회할 city id")),
        responseFields(
            fieldWithPath("code").type(JsonFieldType.STRING).description("Business code"),
            fieldWithPath("message").type(JsonFieldType.STRING).description("response message"),
            fieldWithPath("data.name").type(JsonFieldType.STRING).description("city 이름"),
            fieldWithPath("data.introContent")
                .type(JsonFieldType.STRING)
                .description("city 에 대한 소개"),
            fieldWithPath("data.createdAt").description("city 내용 생성 일자"),
            fieldWithPath("data.updatedAt").description("city 내용 수정 일자")));
  }
}
