package com.interpark.triple.domain.travel.controller.document;

import org.springframework.restdocs.mockmvc.RestDocumentationResultHandler;
import org.springframework.restdocs.operation.preprocess.Preprocessors;
import org.springframework.restdocs.payload.JsonFieldType;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;

public class TravelRestDocument {
  public static RestDocumentationResultHandler getCreateTravelInfoDocument() {
    return document(
        "/travel/create",
        Preprocessors.preprocessRequest(Preprocessors.prettyPrint()),
        Preprocessors.preprocessResponse(Preprocessors.prettyPrint()),
        requestFields(
                fieldWithPath("cityId").type(JsonFieldType.NUMBER).description("city id"),
                fieldWithPath("userId").type(JsonFieldType.NUMBER).description("user id"),
                fieldWithPath("travelStartAt").type(JsonFieldType.STRING).description("travel 시작 일자"),
                fieldWithPath("travelEndAt").type(JsonFieldType.STRING).description("travel 끝나는 일자")
        ),
        responseFields(
            fieldWithPath("code").type(JsonFieldType.STRING).description("Business code"),
            fieldWithPath("message").type(JsonFieldType.STRING).description("response message"),
            fieldWithPath("data.cityName").type(JsonFieldType.STRING).description("city 이름"),
            fieldWithPath("data.userName").type(JsonFieldType.STRING).description("여행 등록한 사용자 이름"),
            fieldWithPath("data.startTravelAt").type(JsonFieldType.STRING).description("travel 시작 일자"),
            fieldWithPath("data.endTravelAt").type(JsonFieldType.STRING).description("travel 끝나는 일자")));
  }
  public static RestDocumentationResultHandler getUpdateTravelInfoDocument() {
    return document(
            "/travel/update",
            Preprocessors.preprocessRequest(Preprocessors.prettyPrint()),
            Preprocessors.preprocessResponse(Preprocessors.prettyPrint()),
            requestFields(
                    fieldWithPath("travelId").type(JsonFieldType.NUMBER).description("travel id"),
                    fieldWithPath("cityId").type(JsonFieldType.NUMBER).description("city id"),
                    fieldWithPath("userId").type(JsonFieldType.NUMBER).description("user id"),
                    fieldWithPath("travelStartAt").description("travel 시작 일자"),
                    fieldWithPath("travelEndAt").description("travel 끝나는 일자")
            ),
            responseFields(
                    fieldWithPath("code").type(JsonFieldType.STRING).description("Business code"),
                    fieldWithPath("message").type(JsonFieldType.STRING).description("response message"),
                    fieldWithPath("data.cityName").type(JsonFieldType.STRING).description("city 이름"),
                    fieldWithPath("data.userName").type(JsonFieldType.STRING).description("여행 등록한 사용자 이름"),
                    fieldWithPath("data.startTravelAt").type(JsonFieldType.STRING).description("travel 시작 일자"),
                    fieldWithPath("data.endTravelAt").type(JsonFieldType.STRING).description("travel 끝나는 일자")));
  }
  public static RestDocumentationResultHandler getDeleteTravelDocument() {
    return document(
            "/travel/delete",
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
            "/travel/get/one",
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
