package com.proj.Matjalal.domain.imageData.service;

import com.proj.Matjalal.domain.article.entity.Article;
import com.proj.Matjalal.domain.imageData.entity.ImageData;
import com.proj.Matjalal.domain.imageData.repository.ImageDataRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ImageDataService {
    private final ImageDataRepository imageDataRepository;
    private final String FOLDER_PATH = "C:\\Users\\SBS\\IdeaProjects\\Matjalal\\frontapp\\my-app\\public\\";
    private final String UPLOAD_PATH = "images";

    public Optional<ImageData> findByArticle(Article article) {
        return  this.imageDataRepository.findByArticle(article);


    }

    public String uploadImageToFileSystem(MultipartFile file) throws IOException {
        String filePath = FOLDER_PATH + UPLOAD_PATH + "\\" + file.getOriginalFilename();
        String uploadPath = UPLOAD_PATH + "\\" + file.getOriginalFilename();
        ImageData imageData = imageDataRepository.save(
                ImageData.builder()
                        .name(file.getOriginalFilename())
                        .type(file.getContentType())
                        .filePath(filePath)
                        .uploadPath(uploadPath)
                        .build()
        );

        // 파일 결로
        file.transferTo(new File(filePath));

        if (imageData != null) {
            return "file uploaded successfully! filePath : " + filePath;
        }

        return null;
    }
    public String uploadImageToFileSystemWithArticle(MultipartFile file, Article article) throws IOException {
        String filePath = FOLDER_PATH + UPLOAD_PATH + "\\" + file.getOriginalFilename();
        String uploadPath = UPLOAD_PATH + "/" + file.getOriginalFilename();
        ImageData imageData = imageDataRepository.save(
                ImageData.builder()
                        .name(file.getOriginalFilename())
                        .type(file.getContentType())
                        .filePath(filePath)
                        .uploadPath(uploadPath)
                        .article(article)
                        .build()

        );

        // 파일 결로
        file.transferTo(new File(filePath));

        if (imageData != null) {
            return "file uploaded successfully! filePath : " + filePath;
        }

        return null;
    }

}
