package jeonginho.firstspring.controller;

import org.springframework.ui.Model;
import jeonginho.firstspring.domain.Member;
import jeonginho.firstspring.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class MemberController {

    // new를 하면 다른 컨트롤러들도 멤버서비스를 가져다 쓸 수 있는 문제가 있다.
    //private final MemberService memberService = new MemberService();

    private final MemberService memberService;

    @Autowired  // 멤버서비스를 스프링이 스프링 컨테이너에 있는 멤버서비스를 가져와 연결 시켜준다.
    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @GetMapping("/members/new")
    public String craeateForm() {
        return "members/createMemberForm";
    }

    @PostMapping("/members/new")
    public String create(MemberForm form){
        Member member = new Member();
        member.setName(form.getName());

        memberService.join(member);

        return "redirect:/"; // 홈 화면으로 보낸다.
    }

    @GetMapping("/members")
    public String list(Model model) {
        List<Member> members = memberService.findMembers();
        model.addAttribute("members", members);
        return "members/memberList";
    }
}
