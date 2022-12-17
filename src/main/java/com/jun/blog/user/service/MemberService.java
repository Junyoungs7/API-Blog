package com.jun.blog.user.service;

import com.jun.blog.user.model.Member;
import com.jun.blog.user.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService{

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    public boolean validateDuplicate(String email){
        return memberRepository.existsByEmail(email);
    }

    public void create(String email, String username, String password) throws Exception {
//        if (validateDuplicate(email)) {
//            throw new IllegalStateException("이미 가입된 아이디입니다.");
//        } else {
//            String encodePassword = passwordEncoder.encode(password);
//            Member member = Member.builder().email(email).username(username).password(encodePassword).build();
//            memberRepository.save(member);
//        }

        String encodePassword = passwordEncoder.encode(password);
        Member member = Member.builder().email(email).username(username).password(encodePassword).build();
        memberRepository.save(member);
    }


}
