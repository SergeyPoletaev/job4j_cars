package ru.job4j.cars.controller;

import lombok.AllArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ru.job4j.cars.model.Post;
import ru.job4j.cars.service.PostService;
import ru.job4j.cars.util.HttpHelper;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.NoSuchElementException;
import java.util.Objects;

@Controller
@AllArgsConstructor
@RequestMapping("/post")
public class PostController {
    private final PostService postService;

    @GetMapping("/posts")
    public String posts(Model model, HttpSession httpSession, @RequestParam(required = false) Boolean status) {
        HttpHelper.addUserToModel(model, httpSession);
        String page = "/post/posts";
        if (status == null) {
            model.addAttribute("posts", postService.findAll(HttpHelper.getCurrentUser(httpSession)));
            return page;
        }
        model.addAttribute("posts", postService.findByStatus(status));
        return page;
    }

    @GetMapping("/add")
    public String add(Model model, HttpSession httpSession) {
        HttpHelper.addUserToModel(model, httpSession);
        return "/post/add";
    }

    @PostMapping("/create")
    public String create(HttpServletRequest req, HttpSession httpSession, RedirectAttributes attr) {
        try {
            postService.createPostFromRequestParam(req, httpSession);
            return "redirect:/post/posts";
        } catch (IllegalStateException | NumberFormatException ex) {
            attr.addFlashAttribute("error_message", ex.getMessage());
            return "redirect:/error/fail";
        } catch (Exception ex) {
            attr.addFlashAttribute("error_message", "При сохранении данных произошла ошибка");
            return "redirect:/error/fail";
        }
    }

    @PostMapping("/select")
    public String select(@RequestParam int postId, RedirectAttributes attr) {
        try {
            attr.addFlashAttribute("post", postService.findById(postId));
            return "redirect:/post/poster";
        } catch (NoSuchElementException ex) {
            attr.addFlashAttribute("error_message", ex.getMessage());
            return "redirect:/error/fail";
        }
    }

    @GetMapping("/poster")
    public String select(Model model, HttpSession httpSession) {
        HttpHelper.addUserToModel(model, httpSession);
        return "/post/poster";
    }

    @PostMapping("/update")
    public String update(@ModelAttribute Post post,
                         RedirectAttributes att,
                         HttpSession httpSession,
                         @RequestParam("attachment") MultipartFile file) {
        try {
            post.setPhoto(file.getBytes());
            postService.update(post, httpSession);
            return "redirect:/post/posts";
        } catch (IllegalArgumentException ex) {
            att.addFlashAttribute("error_message", ex.getMessage());
            return "redirect:/error/fail";
        } catch (Exception ex) {
            att.addFlashAttribute("error_message", "При обновлении данных произошла ошибка");
            return "redirect:/error/fail";
        }
    }

    @GetMapping("/update/{id}")
    public String update(@PathVariable int id, Model model, HttpSession httpSession, RedirectAttributes attr) {
        HttpHelper.addUserToModel(model, httpSession);
        try {
            model.addAttribute("post", postService.findById(id));
            return "/post/update";
        } catch (NoSuchElementException ex) {
            attr.addFlashAttribute("error_message", ex.getMessage());
            return "redirect:/error/fail";
        }
    }

    @GetMapping("/photo/{postId}")
    public String getPost(@PathVariable("postId") Integer postId, RedirectAttributes attr) {
        try {
            attr.addFlashAttribute("post", postService.findById(postId));
            return "redirect:/post/photo/resource";
        } catch (NoSuchElementException ex) {
            attr.addFlashAttribute("error_message", ex.getMessage());
            return "redirect:/error/fail";
        }
    }

    @GetMapping("/photo/resource")
    public ResponseEntity<Resource> download(Model model) {
        Post post = (Post) model.getAttribute("post");
        return ResponseEntity.ok()
                .headers(new HttpHeaders())
                .contentLength(Objects.requireNonNull(post).getPhoto().length)
                .contentType(MediaType.parseMediaType("application/octet-stream"))
                .body(new ByteArrayResource(post.getPhoto()));
    }
}
