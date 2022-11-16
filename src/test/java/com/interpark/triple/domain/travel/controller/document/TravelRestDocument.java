package com.interpark.triple.domain.travel.controller.document;

import org.springframework.restdocs.mockmvc.RestDocumentationResultHandler;
import org.springframework.restdocs.operation.preprocess.Preprocessors;
import org.springframework.restdocs.payload.JsonFieldType;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;

public class TravelRestDocument {
  public static RestDocumentationResultHandler getTravelInfoDocument() {
    return document(
        "/api/v1/travel",
        Preprocessors.preprocessRequest(Preprocessors.prettyPrint()),
        Preprocessors.preprocessResponse(Preprocessors.prettyPrint()),
        responseFields(
            fieldWithPath("code").type(JsonFieldType.STRING).description("Business code"),
            fieldWithPath("message").type(JsonFieldType.STRING).description("response message"),
            fieldWithPath("data.cityName").type(JsonFieldType.STRING).description("city 이름"),
            fieldWithPath("data.userName").type(JsonFieldType.STRING).description("여행 등록한 사용자 이름"),
            fieldWithPath("data.startTravelAt").description("travel 시작 일자"),
            fieldWithPath("data.endTravelAt").description("travel 끝나는 일자")));
  }
  public static RestDocumentationResultHandler getDeleteTravelDocument() {
    return document(
            "/api/v1/travel/{id}",
            Preprocessors.preprocessRequest(Preprocessors.prettyPrint()),
            Preprocessors.preprocessResponse(Preprocessors.prettyPrint()),
            pathParameters(parameterWithName("id").description("삭제할 travel id")),
            responseFields(
                    fieldWithPath("code").type(JsonFieldType.STRING).description("Business code"),
                    fieldWithPath("message").type(JsonFieldType.STRING).description("response message"),
                    fieldWithPath("data").description("empty String")));
  }

  public static RestDocumentationResultHandler getOneTravelInfoWithIdDocument() {
    return document(
            "/api/v1/travel",
            Preprocessors.preprocessRequest(Preprocessors.prettyPrint()),
            Preprocessors.preprocessResponse(Preprocessors.prettyPrint()),
            pathParameters(parameterWithName("id").description("조회할 travel id")),
            responseFields(
                    fieldWithPath("code").type(JsonFieldType.STRING).description("Business code"),
                    fieldWithPath("message").type(JsonFieldType.STRING).description("response message"),
                    fieldWithPath("data.cityName").type(JsonFieldType.STRING).description("city 이름"),
                    fieldWithPath("data.userName").type(JsonFieldType.STRING).description("여행 등록한 사용자 이름"),
                    fieldWithPath("data.startTravelAt").description("travel 시작 일자"),
                    fieldWithPath("data.endTravelAt").description("travel 끝나는 일자")));
  }
}
