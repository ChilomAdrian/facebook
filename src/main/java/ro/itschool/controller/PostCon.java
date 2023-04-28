package ro.itschool.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ro.itschool.entity.Post;
import ro.itschool.service.PostService;

@Controller
@RequiredArgsConstructor
@RequestMapping("/post")
public class PostCon {
    private final PostService postService;

    @GetMapping(value = "/all")
    public String getAllPosts(Model model) {
        model.addAttribute("posts", postService.getPostsFromFollowedUsers());
        return "posts";
    }

    @RequestMapping(value = "/delete/{id}")
    public String deletePost(@PathVariable Integer id) {
        postService.deleteById(id);
        return "redirect:/post/all";
    }

    @GetMapping(value = "/own")
    public String getMyPosts(Model model) {
        model.addAttribute("posts", postService.getMyPosts());
        return "my-posts";
    }

    @GetMapping(value = "/add")
    public String addPost(Model model) {
        Post post = new Post();
        model.addAttribute("post", post);
        return "add-post";
    }

    @PostMapping(value = "/add")
    public String addPost(@ModelAttribute("post") @RequestBody Post post) {
        postService.save(post);
        return "redirect:/post/own";
    }

    @RequestMapping(value = "/copy/{id}")
    public String copyPost(@PathVariable Integer id) {
        postService.copyPost(id);
        return "redirect:/post/own";
    }

    @GetMapping(value = "/mentions")
    public String getPostWithMentions(Model model) {
        model.addAttribute("posts", postService.getPostsWithMention());
        return "post-mentions";
    }

    @RequestMapping(value = "/like/{id}")
    public String likePost(@PathVariable Integer id) {
        postService.likePost(id);
        return "redirect:/post/all";
    }

    @RequestMapping(value = "/unlike/{id}")
    public String unlikePost(@PathVariable Integer id) {
        postService.unlikePost(id);
        return "redirect:/post/all";
    }
}
