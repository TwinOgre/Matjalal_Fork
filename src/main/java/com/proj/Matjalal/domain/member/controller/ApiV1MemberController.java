package com.proj.Matjalal.domain.member.controller;


import com.proj.Matjalal.domain.member.dto.MemberDTO;
import com.proj.Matjalal.domain.member.entity.Member;
import com.proj.Matjalal.domain.member.service.MemberService;
import com.proj.Matjalal.global.RsData.RsData;
import com.proj.Matjalal.global.rq.Rq;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/members")
@RequiredArgsConstructor
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
    public RsData<MeResponse> me() {
        Member member = rq.getMember();

        return RsData.of(
                "S-6",
                "성공",
                new MeResponse(new MemberDTO(member))
        );
    }

    @PostMapping("/logout")
    public RsData<Void> logout() {
        rq.removeCrossDomainCookie("accessToken");
        rq.removeCrossDomainCookie("refreshToken");

        return RsData.of("200", "로그아웃 성공");
    }


}


