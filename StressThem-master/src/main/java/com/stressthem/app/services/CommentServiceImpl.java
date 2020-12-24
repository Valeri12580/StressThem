package com.stressthem.app.services;

import com.stressthem.app.domain.entities.Comment;
import com.stressthem.app.domain.models.service.CommentServiceModel;
import com.stressthem.app.domain.models.service.UserServiceModel;
import com.stressthem.app.exceptions.CommentNotFoundException;
import com.stressthem.app.repositories.CommentRepository;
import com.stressthem.app.services.interfaces.CommentService;
import com.stressthem.app.services.interfaces.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {
    private CommentRepository commentRepository;
    private ModelMapper modelMapper;
    private UserService userService;

    @Autowired
    public CommentServiceImpl(CommentRepository commentRepository, ModelMapper modelMapper, UserService userService) {
        this.commentRepository = commentRepository;
        this.modelMapper = modelMapper;
        this.userService = userService;
    }

    @Override
    public List<CommentServiceModel> getAllCommentsSortedByRatingDesc() {
        List<Comment> comments = commentRepository.findAllByOrderByRateDesc();
        CommentServiceModel[] mapped = this.modelMapper.map(comments
                , CommentServiceModel[].class);
        return List.of(mapped);
    }

    @Override
    public void deleteCommentById(String id) {
        Comment comment=commentRepository.findById(id).orElseThrow(()->{
         throw new CommentNotFoundException("The comment is not found!");
        });

        commentRepository.delete(comment);

    }

    @Override
    public void registerComment(CommentServiceModel commentServiceModel,String username) {
        UserServiceModel user = this.userService.getUserByUsername(username);

        commentServiceModel.setAuthor(user);

        Comment comment=this.modelMapper.map(commentServiceModel,Comment.class);

        commentRepository.save(comment);
    }

    @Override
    public boolean hasUserAlreadyCommented(String username) {
        return this.userService.getUserByUsername(username).getComment()!=null;
    }
}
