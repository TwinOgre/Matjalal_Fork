package com.proj.Matjalal.global.initData;

import com.proj.Matjalal.domain.article.service.ArticleService;
import com.proj.Matjalal.domain.ingredient.service.IngredientService;
import com.proj.Matjalal.domain.member.entity.Member;
import com.proj.Matjalal.domain.member.service.MemberService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@Profile({"dev", "test"})
public class NotProd {
    @Bean
    CommandLineRunner initData(ArticleService articleService, MemberService memberService, IngredientService ingredientService, PasswordEncoder
            passwordEncoder) {
        String password = passwordEncoder.encode("1234");
        return args -> {
            // 회원 3명 추가
            Member user1 = memberService.join("user1", password, "test@test.com");
            Member user2 = memberService.join("user2", password, "test@test.com");
            Member admin = memberService.join("admin", password, "admin@test.com");

            // 작성자 회원 추가
            articleService.create(user1, "제목 1", "내용 1");
            articleService.create(user1, "제목 2", "내용 2");
            articleService.create(user2, "제목 3", "내용 3");
            articleService.create(user2, "제목 4", "내용 4");
            articleService.create(admin, "제목 5", "내용 5");

            //재료 생성
            // Subway
            // [빵]
            String typeName = "bread";
            ingredientService.create("화이트", typeName);
            ingredientService.create("하티", typeName);
            ingredientService.create("파마산오레가노", typeName);
            ingredientService.create("위트", typeName);
            ingredientService.create("허니오트", typeName);
            ingredientService.create("플랫브레드", typeName);
            // [추가 토핑]
            typeName = "subwayTopping";
            // 주재료(e.g 고기) 2배
            ingredientService.create("미트 추가", typeName);
            ingredientService.create("에그마요", typeName);
            ingredientService.create("베이컨", typeName);
            // 치즈 2배
            ingredientService.create("치즈 추가", typeName);
            ingredientService.create("에그 슬라이스", typeName);
            ingredientService.create("아보카도", typeName);
            ingredientService.create("오믈렛", typeName);
            ingredientService.create("페퍼로니", typeName);
            // [치즈]
            typeName = "cheese";
            ingredientService.create("양상추", typeName);
            ingredientService.create("토마토", typeName);
            ingredientService.create("오이", typeName);
            // [야채]
            typeName = "vegetable";
            ingredientService.create("양상추", typeName);
            ingredientService.create("토마토", typeName);
            ingredientService.create("오이", typeName);
            ingredientService.create("피망", typeName);
            ingredientService.create("양파", typeName);
            ingredientService.create("피클", typeName);
            ingredientService.create("올리브", typeName);
            ingredientService.create("할라피뇨", typeName);
            // [소스]
            typeName = "sauce";
            ingredientService.create("랜치", typeName);
            ingredientService.create("스위트 어니언", typeName);
            ingredientService.create("마요네즈", typeName);
            ingredientService.create("스위트 칠리", typeName);
            ingredientService.create("스모크 바비큐", typeName);
            ingredientService.create("핫 칠리", typeName);
            ingredientService.create("허니 머스타드", typeName);
            ingredientService.create("사우스웨스트 치폴레", typeName);
            ingredientService.create("홀스래디쉬", typeName);
            ingredientService.create("머스타드", typeName);
            ingredientService.create("엑스트라 버진 올리브 오일", typeName);
            ingredientService.create("레드 와인 식초", typeName);
            ingredientService.create("소금", typeName);
            ingredientService.create("후추", typeName);

            //Gong cha
            typeName = "gongChaToppings";
            // 토핑
            ingredientService.create("밀크폼", typeName);
            ingredientService.create("펄(타피오카)", typeName);
            ingredientService.create("코코넛", typeName);
            ingredientService.create("알로에", typeName);
            ingredientService.create("화이트펄", typeName);
            ingredientService.create("치즈폼", typeName);
            // 당도
            typeName = "sweet";
            ingredientService.create("0%", typeName);
            ingredientService.create("25%", typeName);
            ingredientService.create("50%", typeName);
            ingredientService.create("75%", typeName);
            ingredientService.create("100%", typeName);
            // 얼음량
            typeName = "ice";
            ingredientService.create("적음", typeName);
            ingredientService.create("보통", typeName);
            ingredientService.create("많음", typeName);
        };
    }

}
