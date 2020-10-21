package com.stressthem.app.web.controllers;

import com.stressthem.app.domain.models.binding.CommentBindingModel;
import com.stressthem.app.domain.models.service.CommentServiceModel;
import com.stressthem.app.domain.models.view.CommentViewModel;
import com.stressthem.app.services.interfaces.CommentService;
import com.stressthem.app.web.annotations.PageTitle;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping(value = "/about")
public class AboutController {

    private CommentService commentService;
    private ModelMapper modelMapper;

    @Autowired
    public AboutController(CommentService commentService, ModelMapper modelMapper) {
        this.commentService = commentService;
        this.modelMapper = modelMapper;
    }

    @PageTitle("About us")
    @GetMapping
    public String about(Model model){
        model.addAttribute("comments", List.of(this.modelMapper.map(
                commentService.getAllCommentsSortedByRatingDesc(), CommentViewModel[].class)
        ));
        return "about-us";
    }

    @GetMapping("/comments/delete/{id}")
    public String deleteComment(@PathVariable String id){
        commentService.deleteCommentById(id);
        return "redirect:/about#comments";
    }

    @PageTitle("Create comment")
    @PreAuthorize("(not @commentServiceImpl.hasUserAlreadyCommented(authentication.name))" +
            " and @transactionServiceImpl.hasUserTransactions(authentication.name) ")
    @GetMapping("/comments/create")
    public String createComment(Model model){
        if(!model.containsAttribute("comment")){
            model.addAttribute("comment",new CommentBindingModel());
        }
        return "comment-add";
    }

    @PostMapping("/comments/create")
    public String createComment(@Valid CommentBindingModel commentBindingModel, BindingResult bindingResult, RedirectAttributes redirectAttributes
    , Principal principal){

        if(bindingResult.hasErrors()){
            redirectAttributes.addFlashAttribute("comment",commentBindingModel);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.comment",bindingResult);
            return "redirect:/about/comments/create";
        }

        commentService.registerComment(this.modelMapper.map(commentBindingModel, CommentServiceModel.class)
        ,principal.getName());
        return "redirect:/about#comments";
    }
}
