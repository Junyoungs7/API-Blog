package com.jun.blog.user.controller;

import com.jun.blog.user.dto.MemberSignupRequestDTO;
import com.jun.blog.user.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@Controller
@RequiredArgsConstructor
@RequestMapping("/auth")
public class MemberController {
    private final MemberService memberService;

    @GetMapping("/login")
    public String getUserLogin(){
        return "/user/loginForm";
    }

    @GetMapping("/signup")
    public String getSignup(Model model){
        MemberSignupRequestDTO memberSignupRequestDTO = new MemberSignupRequestDTO();
        model.addAttribute("memberSignupRequestDTO", memberSignupRequestDTO);
        return "/user/signupForm";
    }

    @PostMapping("/signup")
    public String postSignup(@Valid MemberSignupRequestDTO memberSignupRequestDTO, BindingResult bindingResult){
        if (bindingResult.hasErrors()) {
            return "/user/signupForm";
        }

        if (!memberSignupRequestDTO.getPassword1().equals(memberSignupRequestDTO.getPassword2())) {
            bindingResult.rejectValue("password2", "passwordInCorrect", "패스워드가 일치하지 않습니다.");
            return "/user/signupForm";
        }
        try {
            memberService.create(memberSignupRequestDTO.getEmail(), memberSignupRequestDTO.getUsername(), memberSignupRequestDTO.getPassword1());
        } catch(DataIntegrityViolationException e) {
            e.printStackTrace();
            bindingResult.reject("signupFailed", "이미 등록된 사용자입니다.");
            return "/user/signupForm";
        }catch(Exception e) {
            e.printStackTrace();
            bindingResult.reject("signupFailed", e.getMessage());
            return "/user/signupForm";
        }
        return "redirect:/";
    }
}
