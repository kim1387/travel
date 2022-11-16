package com.interpark.triple.domain.city.controller.document;

import org.springframework.restdocs.mockmvc.RestDocumentationResultHandler;
import org.springframework.restdocs.operation.preprocess.Preprocessors;
import org.springframework.restdocs.payload.JsonFieldType;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;

public class CityRestDocument {
  public static RestDocumentationResultHandler getCityInfoDocument() {
    return document(
        "/api/v1/city",
        Preprocessors.preprocessRequest(Preprocessors.prettyPrint()),
        Preprocessors.preprocessResponse(Preprocessors.prettyPrint()),
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

  public static RestDocumentationResultHandler getDeleteCityDocument() {
    return document(
        "/api/v1/city/{id}",
        Preprocessors.preprocessRequest(Preprocessors.prettyPrint()),
        Preprocessors.preprocessResponse(Preprocessors.prettyPrint()),
        pathParameters(parameterWithName("id").description("삭제할 city id")),
        responseFields(
            fieldWithPath("code").type(JsonFieldType.STRING).description("Business code"),
            fieldWithPath("message").type(JsonFieldType.STRING).description("response message"),
            fieldWithPath("data").description("empty String")));
  }
  public static RestDocumentationResultHandler getCityInfoListByUserIdDocument() {
    return document(
            "/api/v1/city/users/{userId}",
            Preprocessors.preprocessRequest(Preprocessors.prettyPrint()),
            Preprocessors.preprocessResponse(Preprocessors.prettyPrint()),
            pathParameters(parameterWithName("userId").description("조회할 user id")),
            responseFields(
                    fieldWithPath("code").type(JsonFieldType.STRING).description("Business code"),
                    fieldWithPath("message").type(JsonFieldType.STRING).description("response message"),
                    fieldWithPath("data.cityInfos.[].name").type(JsonFieldType.STRING).description("city 이름"),
                    fieldWithPath("data.cityInfos.[].introContent")
                            .type(JsonFieldType.STRING)
                            .description("city 에 대한 소개"),
                    fieldWithPath("data.cityInfos.[].createdAt").description("city 내용 생성 일자"),
                    fieldWithPath("data.cityInfos.[].updatedAt").description("city 내용 수정 일자")));
  }

  public static RestDocumentationResultHandler getOneCityInfoByIdDocument() {
    return document(
            "/api/v1/city/{id}",
            Preprocessors.preprocessRequest(Preprocessors.prettyPrint()),
            Preprocessors.preprocessResponse(Preprocessors.prettyPrint()),
            pathParameters(parameterWithName("id").description("조회할 city id")),
            responseFields(
                    fieldWithPath("code").type(JsonFieldType.STRING).description("Business code"),
                    fieldWithPath("message").type(JsonFieldType.STRING).description("response message"),
                    fieldWithPath("data.name").type(JsonFieldType.STRING).description("city 이름"),
                    fieldWithPath("data.introContent")
                            .type(JsonFieldType.STRING)
                            .description("city에 대한 소개"),
                    fieldWithPath("data.createdAt").description("city 내용 생성 일자"),
                    fieldWithPath("data.updatedAt").description("city 내용 수정 일자")));
  }
}
