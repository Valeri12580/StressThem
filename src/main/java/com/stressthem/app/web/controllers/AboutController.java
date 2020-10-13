package com.stressthem.app.web.controllers;

import com.stressthem.app.services.interfaces.CommentService;
import com.stressthem.app.web.annotations.PageTitle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "/about")
public class AboutController {

    private CommentService commentService;

    @Autowired
    public AboutController(CommentService commentService) {
        this.commentService = commentService;
    }

    @PageTitle("About us")
    @GetMapping
    public String about(Model model){
        model.addAttribute("comments",commentService.getAllCommentsSortedByRatingDesc());
        return "about-us";
    }

    @GetMapping("/comments/delete/{id}")
    public String deleteComment(@PathVariable String id){
        commentService.deleteCommentById(id);
        return "redirect:/about";
    }

    @PageTitle("Create comment")
    @GetMapping("/comments/create")
    public String createComment(Model model){

        return "comment-add";
    }
}
