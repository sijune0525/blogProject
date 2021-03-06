package com.sijune.project.springboot.web;

import com.sijune.project.springboot.config.auth.LoginUser;
import com.sijune.project.springboot.config.auth.dto.SessionUser;
import com.sijune.project.springboot.service.PostsService;
import com.sijune.project.springboot.web.dto.PostsResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpSession;


@RequiredArgsConstructor
@Controller
public class IndexController {

    private final PostsService postsService;
    private final HttpSession httpSession;

    @GetMapping("/")
    public String index(Model model, @LoginUser SessionUser user) {
        model.addAttribute("posts", postsService.findAllDesc()); //Model: 객체 저장 가능, service에 받은 값은 화면에 전달
        //SessionUser user = (SessionUser) httpSession.getAttribute("user"); //로그인 성공 시 세션에 user를 저장해놓음
        if(user != null){
            model.addAttribute("userName", user.getName());
        }
        return "index"; //index.mustache 로 변환되어 view Resolver(이름과 객체를 맵핑한다)가 처리한다.
    }

    @GetMapping("/posts/save")
    public String postsSave() {
        return "posts-save";
    }

    @GetMapping("/posts/update/{id}")
    public String postsUpdate(@PathVariable Long id, Model model) {
        PostsResponseDto dto = postsService.findById(id);
        model.addAttribute("post", dto);
        return "posts-update";
    }
}
