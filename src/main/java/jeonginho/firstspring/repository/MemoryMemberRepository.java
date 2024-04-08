package jeonginho.firstspring.repository;

import jeonginho.firstspring.domain.Member;
import org.springframework.stereotype.Repository;

import java.sql.Array;
import java.util.*;

//@Repository
public class MemoryMemberRepository implements MemberRepository{

    private static Map<Long, Member> store = new HashMap<>();
    private static long sequence = 0L;

    @Override
    public Member save(Member member) { // 이름은 넘어온 상태
        member.setId(++sequence);   // store에 넣기 전에 멤버에 id값을 세팅해주고
        store.put(member.getId(), member);  // store에 저장을하여 MAP에 저장이 됨을 알 수 있따.
        return member;  // 스펙에 따라 저장된 결과를 반환한다.
    }

    @Override
    public Optional<Member> findById(Long id) {
        return Optional.ofNullable(store.get(id)); // store에서 get()을 꺼내어 id를 넣는다.
    } // 반환될 값이 null이어도 정상적으로 반환한다.

    @Override
    public Optional<Member> findByName(String name) {
        return store.values().stream() // 루프
                .filter(member -> member.getName().equals(name)) // 멤버.getName이 파라미터로 넘어온 name과 같은지 확인
                .findAny(); // 그 중에서 찾으면 반환한다.
    }

    @Override
    public List<Member> findAll() {
        return new ArrayList<>(store.values()); // List를 많이 쓴다. 루프를 돌리기 편함
    }   // 멤버들이 반환된다.

    public void clearStore() {
        store.clear();
    }
}
