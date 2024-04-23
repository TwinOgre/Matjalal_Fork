package com.proj.Matjalal.domain.imageData.controller;


import com.proj.Matjalal.domain.article.DTO.ArticleDTO;
import com.proj.Matjalal.domain.article.controller.ApiV1ArticleController;
import com.proj.Matjalal.domain.article.entity.Article;
import com.proj.Matjalal.domain.article.service.ArticleService;
import com.proj.Matjalal.domain.imageData.entity.ImageData;
import com.proj.Matjalal.domain.imageData.service.ImageDataService;
import com.proj.Matjalal.global.RsData.RsData;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/image-data")
@Tag(name = "이미지", description = "이미지 관련 API")
public class ApiV1ImageDataController {
    private final ImageDataService imageDataService;
    private final ArticleService articleService;

    @PostMapping("")
    public ResponseEntity<?> uploadImage(@RequestParam("image") MultipartFile file) throws IOException {
        String uploadImage = imageDataService.uploadImageToFileSystem(file);
        return ResponseEntity.status(HttpStatus.OK)
                .body(uploadImage);
    }

    @PostMapping("/articles")
    public ResponseEntity<?> uploadImageByArticle(@RequestParam("subject") String subject, @RequestParam("content") String content, @RequestParam("image") MultipartFile file) throws IOException {

        Optional<Article> optionalArticle =  this.articleService.getArticleBySubjectAndContent(subject, content);
        if(optionalArticle.isEmpty()){
            return null;
        }
        String uploadImage = imageDataService.uploadImageToFileSystemWithArticle(file, optionalArticle.get());
        Optional<ImageData> optionalImageData =  this.imageDataService.findByArticle(optionalArticle.get());
        if(optionalArticle.isEmpty()){
            return null;
        }
        // 만든 이미지 게시글에 더하기
        this.articleService.addImageData(optionalArticle.get(), optionalImageData.get());
        return ResponseEntity.status(HttpStatus.OK)
                .body(uploadImage);
    }

    @Getter
    @AllArgsConstructor
    public static class ImageByArticleResponse {
        private final ImageData imageData;
    }

    //단건 조회
    @GetMapping("/{articleId}/articles")
    @Operation(summary = "이미지 게시글 별 조회", description = "이미지 게시글 별 조회: {id} 이미지 하나 조회. ")
    public RsData<ImageByArticleResponse> getImageByArticle(@Parameter(description = "조회할 게시글의 아이디", example = "article_id") @PathVariable(value = "articleId") Long articleId) {
        Optional<Article> optionalArticle = this.articleService.getArticle(articleId);
        if(optionalArticle.isEmpty()){
            return null;
        }

        return this.imageDataService.findByArticle(optionalArticle.get()).map(imageData -> RsData.of(
                "SIm-3",
                "성공",
                new ImageByArticleResponse(imageData)
        )).orElseGet(() -> RsData.of(
                "FIm-3"
                , " 이미지가 존재하지 않습니다.".formatted(),
                null
        ));
    }
}
