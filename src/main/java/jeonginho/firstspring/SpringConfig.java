package jeonginho.firstspring;

import jakarta.persistence.EntityManager;
import jeonginho.firstspring.aop.TimeTraceAop;
import jeonginho.firstspring.repository.*;
import jeonginho.firstspring.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class SpringConfig {

    private final MemberRepository memberRepository;

    @Autowired
    public SpringConfig(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @Bean
    public MemberService memberService() {
        return new MemberService(memberRepository); // 15행 인젝션 받은 것을 의존관계 설정
    }// 스프링이 뜰 때, 컴피규레이션을 읽고 스프링빈에 등록해준다.

//    @Bean
//    public MemberRepository memberRepository() {
//    return new MemoryMemberRepository();
//    return new JdbcMemberRepository(dataSource);
//    return new JdbcTemplateMemberRepository(dataSource);
//    return new JpaMemberRepository(em);
//    }
}