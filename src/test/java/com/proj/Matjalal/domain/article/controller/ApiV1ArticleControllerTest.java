package com.proj.Matjalal.domain.article.controller;

import jakarta.transaction.Transactional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@SpringBootTest
@Transactional
@ActiveProfiles("test")
public class ApiV1ArticleControllerTest {
    @Autowired
    private MockMvc mvc;

    @Test
    @DisplayName("GET /articles/1")
    void test1() throws Exception {
        ResultActions resultActions = mvc
                .perform(
                        get("/api/v1/aritcles/1")
                )
                .andDo(print());

        //Then
    }

}
