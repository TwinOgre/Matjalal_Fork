package com.proj.Matjalal.domain.member.controller;


import com.proj.Matjalal.domain.member.dto.MemberDTO;
import com.proj.Matjalal.domain.member.entity.Member;
import com.proj.Matjalal.domain.member.service.MemberService;
import com.proj.Matjalal.global.RsData.RsData;
import com.proj.Matjalal.global.exception.GlobalException;
import com.proj.Matjalal.global.rq.Rq;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/v1/members")
@RequiredArgsConstructor
@Tag(name = "회원", description = "회원 관련 API")
public class ApiV1MemberController {
    private final MemberService memberService;
    private final Rq rq;

    @Getter
    public static class LoginRequestBody {
        @NotBlank
        private String username;
        @NotBlank
        private String password;
    }

    @Getter
    @AllArgsConstructor
    public static class LoginResponseBody {
        private MemberDTO memberDto;
    }

    @PostMapping("/login")
    @Operation(summary = "회원 로그인", description = "회원 로그인: 아이디(username)와 비밀번호(password) 필요")
    public RsData<LoginResponseBody> login(@Valid @RequestBody LoginRequestBody loginRequestBody) {
        RsData<MemberService.AuthAndMakeTokensResponseBody> rsData = this.memberService.authAndMakeToken(loginRequestBody.getUsername(), loginRequestBody.getPassword());

        // 쿠키에 accessToken, refreshToken 넣기
        rq.setCrossDomainCookie("accessToken", rsData.getData().getAccessToken());
        rq.setCrossDomainCookie("refreshToken", rsData.getData().getRefreshToken());
        return RsData.of(
                rsData.getResultCode(),
                rsData.getMsg(),
                new LoginResponseBody(new MemberDTO(rsData.getData().getMember()))
        );
    }

    @AllArgsConstructor
    @Getter
    public static class MeResponse {
        private final MemberDTO memberDTO;
    }

    @GetMapping("/me")
    @Operation(summary = "현재 로그인한 회원 조회", description = "현재 로그인한 회원 정보 불러오기")
    public RsData<MeResponse> me() {
        Member member = rq.getMember();

        return RsData.of(
                "S-6",
                "성공",
                new MeResponse(new MemberDTO(member))
        );
    }

    @PostMapping("/logout")
    @Operation(summary = "로그아웃", description = "로그인 상태에서 요청 시 회원 상태에서 비회원 상태로 전환")
    public RsData<Void> logout() {
        rq.removeCrossDomainCookie("accessToken");
        rq.removeCrossDomainCookie("refreshToken");

        return RsData.of("200", "로그아웃 성공");
    }

    @GetMapping("/{id}")
    public RsData<MemberDTO> getUserInfo(@PathVariable(value = "id")Long id){
        Member member = this.memberService.findById(id).orElseThrow( () -> new GlobalException("400-1", "회원이 존재하지 않습니다."));
        return RsData.of("200","성공",new MemberDTO(member));
    }
}


