package jeonginho.firstspring.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController { // 기존 정적 리소스는 무시된다.
    @GetMapping("/") // localhost:8080 을 들어오면 호출된다.
    public String home() {
        return "home";
    }
}
