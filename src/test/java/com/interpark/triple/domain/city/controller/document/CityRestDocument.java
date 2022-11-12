package com.interpark.triple.domain.city.controller.document;

import org.springframework.restdocs.mockmvc.RestDocumentationResultHandler;
import org.springframework.restdocs.operation.preprocess.Preprocessors;
import org.springframework.restdocs.payload.JsonFieldType;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;

public class CityRestDocument {
    public static RestDocumentationResultHandler getRegisterCityDocument() {
        return document("/api/v1/city",
                Preprocessors.preprocessRequest(Preprocessors.prettyPrint()),
                Preprocessors.preprocessResponse(Preprocessors.prettyPrint()),
                responseFields(
                        fieldWithPath("code").type(JsonFieldType.STRING).description("Business code"),
                        fieldWithPath("message").type(JsonFieldType.STRING).description("response message"),
                        fieldWithPath("data.name").type(JsonFieldType.STRING).description("city 이름"),
                        fieldWithPath("data.introContent").type(JsonFieldType.STRING).description("city에 대한 소개"),
                        fieldWithPath("data.createdAt").description("city 내용 생성 일자"),
                        fieldWithPath("data.updatedAt").description("city 내용 수정 일자")
                )
        );
    }
}
