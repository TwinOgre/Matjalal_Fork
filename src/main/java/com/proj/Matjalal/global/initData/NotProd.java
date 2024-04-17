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
            articleService.create(user1, "맛있는 샌드위치", "샌드위치 맛있어요",null, "subway");
            articleService.create(user1, "피클만 넣은 샌드위치", "피클 좋아",null, "subway");
            articleService.create(user2, "버블3배 밀크티", "버블 좋아",null, "gongcha");
            articleService.create(user2, "치즈폼 타로밀크티", "타로 맜있어",null, "gongcha");
            articleService.create(admin, "공차슈페너에 버블 추가", "맛있음ㄹㅇ",null, "gongcha");

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
            ingredientService.create("미트 추가", typeName); // 주재료(e.g 고기) 2배
            ingredientService.create("에그마요", typeName);
            ingredientService.create("베이컨", typeName);
            ingredientService.create("치즈 추가", typeName); // 치즈 2배
            ingredientService.create("에그 슬라이스", typeName);
            ingredientService.create("아보카도", typeName);
            ingredientService.create("오믈렛", typeName);
            ingredientService.create("페퍼로니", typeName);
            // [치즈]
            typeName = "cheese";
            ingredientService.create("아메리칸 치즈", typeName);
            ingredientService.create("슈레드 치즈", typeName);
            ingredientService.create("모짜렐라 치즈", typeName);
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
            // [메뉴]
            typeName = "subwayMenu";
            ingredientService.create("에그 슬라이스", typeName);
            ingredientService.create("스테이크 & 치즈", typeName);
            ingredientService.create("치킨 베이컨 아보카도", typeName);
            ingredientService.create("스파이시 쉬림프", typeName);
            ingredientService.create("쉬림프", typeName);
            ingredientService.create("로스트 치킨", typeName);
            ingredientService.create("로티셰리 바비큐 치킨", typeName);
            ingredientService.create("K-바비큐", typeName);
            ingredientService.create("풀드 포크 바비큐", typeName);
            ingredientService.create("써브웨이 클럽", typeName);
            ingredientService.create("치킨 데리야끼", typeName);
            ingredientService.create("스파이시 이탈리안", typeName);
            ingredientService.create("이탈리안 비엠티", typeName);
            ingredientService.create("치킨 슬라이스", typeName);
            ingredientService.create("참치", typeName);
            ingredientService.create("햄", typeName);
            ingredientService.create("에그마요", typeName);
            ingredientService.create("베지", typeName);

            //Gong cha
            // [토핑] 기본(찬음료): 최대3개, 스파클링: 화이트펄, 알로에, 코코넛 중 택1, 미니펄:최대1개
            typeName = "gongChaTopping";
            ingredientService.create("밀크폼", typeName);
            ingredientService.create("펄(타피오카)", typeName);
            ingredientService.create("코코넛", typeName);
            ingredientService.create("알로에", typeName);
            ingredientService.create("화이트펄", typeName);
            ingredientService.create("치즈폼", typeName);
            // [당도]
            typeName = "sweet";
            ingredientService.create("0%", typeName);
            ingredientService.create("30%", typeName);
            ingredientService.create("50%", typeName);
            ingredientService.create("70%", typeName);
            ingredientService.create("100%", typeName);
            // [얼음량]
            typeName = "ice";
            ingredientService.create("적음", typeName);
            ingredientService.create("보통", typeName);
            ingredientService.create("많음", typeName);
            // [공차메뉴] : '차가운 공차메뉴', '스파클링 공차메뉴', '따뜻한 공차메뉴'로 나뉨.
            // [차가운 공차메뉴]
            typeName = "coldGongChaMenu";
            // >밀크티
            ingredientService.create("브라운슈가 쥬얼리 밀크티", typeName);
            ingredientService.create("블랙 밀크티", typeName);
            ingredientService.create("얼그레이 밀크티", typeName);
            ingredientService.create("우롱 밀크티", typeName);
            ingredientService.create("딸기 쥬얼리 밀크티", typeName);
            ingredientService.create("타로 밀크티", typeName);
            ingredientService.create("초콜렛 밀크티", typeName);
            ingredientService.create("제주 그린 밀크티", typeName);
            // >스무디
            ingredientService.create("딸기 쿠키 스무디", typeName);
            ingredientService.create("초코멜로 블랙티 스무디", typeName);
            ingredientService.create("제주 그린 스무디", typeName);
            ingredientService.create("청포도 스무디", typeName);
            ingredientService.create("망고 스무디", typeName);
            ingredientService.create("초콜렛 쿠키 스무디", typeName);
            ingredientService.create("딸기 쥬얼리 요구르트 크러쉬", typeName);
            // >오리지널 티
            ingredientService.create("블랙티", typeName);
            ingredientService.create("얼그레이티", typeName);
            ingredientService.create("우롱티", typeName);
            ingredientService.create("자스민 그린티", typeName);
            // >프룻티&믹스
            ingredientService.create("망고 요구르트", typeName);
            ingredientService.create("망고 주스", typeName);
            ingredientService.create("자몽 그린티", typeName);
            ingredientService.create("청포도 그린티", typeName);
            ingredientService.create("패션 프룻 히비스커스", typeName);
            // >커피
            ingredientService.create("아메리카노", typeName);
            ingredientService.create("카페라떼", typeName);
            ingredientService.create("바닐라 카페라떼", typeName);
            ingredientService.create("공차슈페너", typeName);
            // >NEW 시즌 메뉴
            ingredientService.create("미니펄 망고 밀크", typeName);
            ingredientService.create("미니펄 딸기 밀크티", typeName);
            ingredientService.create("미니펄 망고 크러쉬", typeName);
            ingredientService.create("미니펄 딸기 크러쉬", typeName);
            ingredientService.create("타로 스무디", typeName);
            ingredientService.create("레몬 요구르트 스무디", typeName);
            // [스파클링 공차메뉴] (따로 나눈이유: 스파클링 공차메뉴는 토핑이 화이트펄, 알로에, 코코넛 중 한 개만 가능.
            // => 토핑 component를 다르게 설정해야 해서.)
            typeName = "sparklingGongChaMenu";
            ingredientService.create("트로피칼 스파클링 티", typeName);
            ingredientService.create("청귤 스파클링 티", typeName);
            ingredientService.create("오리지널 콤부차", typeName);
            ingredientService.create("패션 프룻 히비스커스 콤부차", typeName);
            ingredientService.create("블랙 스파클링 티", typeName);
            ingredientService.create("얼그레이 스파클링 티", typeName);
            ingredientService.create("자스민 그린 스파클링 티", typeName);
            ingredientService.create("우롱 스파클링 티", typeName);
            // [따뜻한 공차메뉴] (따로 나눈이유: 따뜻한 음료는 얼음 설정이 없어서)
            typeName = "hotGongChaMenu";
            // >(HOT)밀크티
            ingredientService.create("(HOT)브라운슈가 쥬얼리 밀크티", typeName);
            ingredientService.create("(HOT)블랙 밀크티", typeName);
            ingredientService.create("(HOT)얼그레이 밀크티", typeName);
            ingredientService.create("(HOT)우롱 밀크티", typeName);
            ingredientService.create("(HOT)타로 밀크티", typeName);
            ingredientService.create("(HOT)초콜렛 밀크티", typeName);
            ingredientService.create("(HOT)제주 그린 밀크티", typeName);
            // >(HOT)오리지널 티
            ingredientService.create("(HOT)블랙티", typeName);
            ingredientService.create("(HOT)얼그레이티", typeName);
            ingredientService.create("(HOT)우롱티", typeName);
            ingredientService.create("(HOT)자스민 그린티", typeName);
            // >(HOT) 커피
            ingredientService.create("(HOT)아메리카노", typeName);
            ingredientService.create("(HOT)카페라떼", typeName);
            ingredientService.create("(HOT)바닐라 카페라떼", typeName);

        };
    }

}
