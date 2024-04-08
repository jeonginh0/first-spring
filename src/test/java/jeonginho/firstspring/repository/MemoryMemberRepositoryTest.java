package jeonginho.firstspring.repository;

import jeonginho.firstspring.domain.Member;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;

public class MemoryMemberRepositoryTest {

    MemoryMemberRepository repository = new MemoryMemberRepository();


    @AfterEach
    public void afterEach() { // Test를 하고 나면 데이터를 클리어 해줘야 함.
        repository.clearStore();
    }   // 테스트가 실행이 되고 끝날때마다 저장소를 한번씩 청소한다.

    @Test
    public void save() {
        Member member = new Member();
        member.setName("spring"); // 이름 세팅

        repository.save(member); // 리포지토리에 멤버를 세이브한다.

        Member result = repository.findById(member.getId()).get();

        // Assertions.assertEquals(member, result); // result 와 member를 똑같이 확인할 수 있다.
        assertThat(member).isEqualTo(result); // 최근 많이 쓰는 옵션
    }

    @Test
    public void findByName() { // spring1, spring2 라는 회원이 가입이 됨.
        Member member1 = new Member();
        member1.setName("spring1");
        repository.save(member1);

        Member member2 = new Member();
        member2.setName("spring2");
        repository.save(member2);

        Member result = repository.findByName("spring1").get();

        assertThat(result).isEqualTo(member1);
    }

    @Test
    public void findAll() { // spring 1,2가 이미 findAll() 메서드에서 저장이
        Member member1 = new Member();
        member1.setName("spring1");
        repository.save(member1);

        Member member2 = new Member();
        member2.setName("spring2");
        repository.save(member2);

        List<Member> result = repository.findAll();

        assertThat(result.size()).isEqualTo(2);
    }
}
