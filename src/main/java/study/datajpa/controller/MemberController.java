package study.datajpa.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import study.datajpa.dto.MemberDto;
import study.datajpa.entitiy.Member;
import study.datajpa.repository.MemberRepository;

import javax.annotation.PostConstruct;
import java.util.Optional;
import java.util.function.Function;

@RestController
@RequiredArgsConstructor
public class MemberController {

    private final MemberRepository memberRepository;

    @GetMapping("/members/{id}")
    public String findMember(@PathVariable("id") Long id){
        Member member = memberRepository.findById(id).get();

        return member.getUserName();
    }

    @GetMapping("/members2/{id}")//도메인 클래스 컨버터 알아서 찾아서 해줌 조회만 써야함 트랙섹션이 애매모호 하고
    public String findMember2(@PathVariable("id") Member member){
        return member.getUserName();
    }

    @GetMapping("/members")
    public Page<MemberDto> list(@PageableDefault(size=5) Pageable pageable){
        Page<Member> page = memberRepository.findAll(pageable);
        Page<MemberDto> map = page.map(member -> {
            return new MemberDto(member);
        });
        return map;
    }


//    @PostConstruct
//    public void init(){
//        for (int i =0; i< 100;i++) {
//            memberRepository.save(new Member("user"+i,i));
//        }
//
//    }

}
