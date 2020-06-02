package com.helpit.foundation.controllers;


import com.helpit.foundation.model.Foundation;
import com.helpit.foundation.model.Post;
import com.helpit.foundation.model.User;
import com.helpit.foundation.repositories.FoundationRepository;
import com.helpit.foundation.repositories.PostRepository;
import com.helpit.foundation.repositories.UserRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@Controller
public class SubmitPostController {
    private PostRepository post_repository;
    private UserRepository user_repository;
    private FoundationRepository foundation_repository;

    public SubmitPostController(PostRepository post_repository, UserRepository user_repository, FoundationRepository foundation_repository) {
        this.post_repository = post_repository;
        this.user_repository = user_repository;
        this.foundation_repository = foundation_repository;
    }

    @RequestMapping("/add_post/submit")
    public String getComments(Model model,
                              @RequestParam String login,
                              @RequestParam  String foundation,
                              @RequestParam  String content)
    {
        Post c = new Post();
        c.setContent(content);
        post_repository.save(c);

        Foundation f = new Foundation();
        f.setName(foundation);
        foundation_repository.save(f);

        User u = new User();
        u.setLogin(login);
        user_repository.save(u);

        c.setUser(u);
        c.setFoundation(f);

        u.getPosts().add(c);
        f.getPost().add(c);

        foundation_repository.save(f);
        user_repository.save(u);
        post_repository.save(c);

        model.addAttribute("comments", post_repository.findAll());
        return "redirect:/add_post/list";
    }


    @RequestMapping("/add_post/{id}/submit_2_foundation")
    public String addPost(@PathVariable String id,
                          @RequestParam String login,
                          @RequestParam  String content,
                          Model model)
    {
        Post c = new Post();
        c.setContent(content);
        post_repository.save(c);


        User u = new User();
        u.setLogin(login);
        user_repository.save(u);

        Optional<Foundation> f = foundation_repository.findById(Long.valueOf(id));
        if (f.isPresent()) {

            c.setUser(u);
            c.setFoundation(f.get());
            u.getPosts().add(c);
            f.get().getPost().add(c);
            foundation_repository.save(f.get());
        }



        user_repository.save(u);
        post_repository.save(c);

        model.addAttribute("comments", post_repository.findAll());
        return "redirect:/foundation/" + id + "/show";
    }
}

