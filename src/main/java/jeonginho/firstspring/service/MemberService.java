package jeonginho.firstspring.service;

import jeonginho.firstspring.domain.Member;
import jeonginho.firstspring.repository.MemberRepository;
import jeonginho.firstspring.repository.MemoryMemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Transactional
public class MemberService {

    private final MemberRepository memberRepository;

    //@Autowired // 스프링 컨테이너에서 생성자 호출
    public MemberService(MemberRepository memberRepository) { // 외부에서 메모리 멤버 리포지토리를 넣어준다(di)
        this.memberRepository = memberRepository;
    } // 리포지토리가 필요하기 때문에 서비스에 메모리멤버 리포지토리를 서비스에 주입해준다. (연결됨)

    /**
     * 회원 가입
     */
    public Long join(Member member) {
        // 중복 회원 x
        // 옵셔널을 바로 반환하는 건 좋지 않다.
        // + findbyname으로 로직이 나오면 메서드로 뽑는게 좋다 ctrl+T

        validateDuplicateMember(member); // 중복 회원 검증
        memberRepository.save(member); // 멤버 리포지토리에 save메서드를 호출한다.
        return member.getId(); // 호출하여 기입된 값을 반환한다.
    }

    private void validateDuplicateMember(Member member) {
        memberRepository.findByName(member.getName())
                .ifPresent(m -> { // 만약 값이 있으면
                    throw new IllegalStateException("이미 존재하는 회원입니다.");
                }); // 옵셔널로 한번 감싸면 옵셔널 안에 멤버 객체가 있어 그걸 사용할 수 있다.
    }

    /**
     * 전체 회원 조회
     */
    public List<Member> findMembers() { // 서비스 클래스는 비즈니스적인 이름을 써야한다.
        return memberRepository.findAll();
    }

    public Optional<Member> findOne(Long memberId) {
        return memberRepository.findById(memberId); // findById를 했을 때, 멤버 id를 넘겨서 반환한다.
    }

}
