package jeonginho.firstspring.service;

import jeonginho.firstspring.domain.Member;
import jeonginho.firstspring.repository.MemoryMemberRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

class MemberServiceTest {

    MemberService memberService;
    MemoryMemberRepository memberRepository;

    @BeforeEach
    public void beforeEach() {
        memberRepository = new MemoryMemberRepository(); // MemoryMemberRepository를 만들고 그 값을 넣어두고
        memberService = new MemberService(memberRepository); // 넣어둔 멤버 리포지토리를 멤버 서비스에서 멤버 서비스에 넣어준다.
    }

    @AfterEach
    public void afterEach() { // Test를 하고 나면 데이터를 클리어 해줘야 함.
        memberRepository.clearStore();
    }   // 테스트가 실행이 되고 끝날때마다 저장소를 한번씩 청소한다.

    @Test
    void 회원가입() {
        //given
        Member member = new Member();
        member.setName("spring");

        //when
        Long saveId = memberService.join(member);

        //then
        Member findMember = memberService.findOne(saveId).get();
        assertThat(member.getName()).isEqualTo(findMember.getName());/* 이름 검증
        멤버의 이름이 isEqualTo, find 멤버의 이름과 같은가?*/
    }

    @Test
    public void 중복_회원_예외 () {
        //given
        Member member1 = new Member();
        member1.setName("spring");

        Member member2 = new Member();
        member2.setName("spring");


        //when
        memberService.join(member1);
        IllegalStateException e = assertThrows(IllegalStateException.class, () -> memberService.join(member2));

        assertThat(e.getMessage()).isEqualTo("이미 존재하는 회원입니다.");


        // 멤버 2 를 넣으면 예외가 터져야 한다.


        /*try {
            memberService.join(member2);
            fail();
        } catch (IllegalStateException e) {
            assertThat(e.getMessage()).isEqualTo("이미 존재하는 회원입니다.");
        }*/
        //then
    }

    @Test
    void findMembers() {

    }

    @Test
    void findOne() {
    }
}