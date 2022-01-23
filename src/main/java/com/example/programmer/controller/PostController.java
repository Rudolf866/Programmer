package com.example.programmer.controller;

import com.example.programmer.entity.Post;
import com.example.programmer.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Optional;

@Controller
public class PostController {

    @Autowired
    private PostRepository repository;


    @GetMapping("/")
    public String homeController(Model model) {

        model.addAttribute("title", "Главная страница");
        model.addAttribute("name", "Kovalevskiy Rudolf Alexandrovich");
        return "blog";

    }
    @GetMapping("/books")
    public String booksController(Model model) {
        model.addAttribute("title", "Страница с книгами");
        model.addAttribute("name", "Books for programming");
        return "books";

    }
    @GetMapping("/smert")
    public String smertController(Model model) {
        model.addAttribute("title", "Страница о нас");
        return "smert";
    }


    @GetMapping("/about")
    public String aboutController(Model model) {
        model.addAttribute("title", "Страница о нас");
        model.addAttribute("name", "text suprise");
        return "about";
    }

    @GetMapping("/blog")
    public String blogMain(Model model) {
        Iterable<Post> post = repository.findAll();
        model.addAttribute("post", post);
        return "blog-main";
    }

    @GetMapping("/blog/add")
    public String blogadd(Model model) {
        model.addAttribute("title", "Добавление статьи");
        return "blog-add";
    }

    @PostMapping("/blog/add")
    public String blogPostadd(
            @RequestParam String title,
            @RequestParam String anons,
            @RequestParam String full_text, Model model
    ) {
        Post post = new Post(title, anons, full_text);
        repository.save(post);
        return "redirect:/blog";
    }

    @GetMapping("/blog/{id}")
    public String blogDetail(@PathVariable(value="id") Long id, Model model) {
        if(!repository.existsById(id)){
            return "redirect:/blog";
        }
        Optional<Post> posts = repository.findById(id);
        ArrayList<Post> res = new ArrayList<>();
        posts.ifPresent(res::add);
        model.addAttribute("posts", res);
        return "blog-details";
    }

    @GetMapping("/blog/{id}/edit")
    public String blogEdit(@PathVariable(value="id") Long id, Model model) {
        if(!repository.existsById(id)){
            return "redirect:/blog";
        }
        Optional<Post> posts = repository.findById(id);
        ArrayList<Post> res = new ArrayList<>();
        posts.ifPresent(res::add);
        model.addAttribute("posts", res);
        return "blog-edit";
    }

    @PostMapping("/blog/{id}/edit")
    public String blogPostUpdate(
            @PathVariable(value="id") Long id,
            @RequestParam String title,
            @RequestParam String anons,
            @RequestParam String full_text, Model model
    ) throws ChangeSetPersister.NotFoundException {
        Post post = repository.findById(id).orElseThrow(ChangeSetPersister.NotFoundException::new);
        post.setTitle(title);
        post.setAnons(anons);
        post.setFull_text(full_text);
        repository.save(post);
        return "redirect:/blog";
    }

    @PostMapping("/blog/{id}/remove")
    public String blogPostDelete(@PathVariable(value="id") Long id, Model model) throws ChangeSetPersister.NotFoundException {
        Post post = repository.findById(id).orElseThrow(ChangeSetPersister.NotFoundException::new);
        repository.delete(post);
        return "redirect:/blog";
    }
}

