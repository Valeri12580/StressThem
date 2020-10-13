package com.stressthem.app.services;

import com.stressthem.app.domain.entities.Comment;
import com.stressthem.app.domain.models.service.CommentServiceModel;
import com.stressthem.app.repositories.CommentRepository;
import com.stressthem.app.services.interfaces.CommentService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {
    private CommentRepository commentRepository;
    private ModelMapper modelMapper;

    @Autowired
    public CommentServiceImpl(CommentRepository commentRepository, ModelMapper modelMapper) {
        this.commentRepository = commentRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public List<CommentServiceModel> getAllCommentsSortedByRatingDesc() {
        List<CommentServiceModel> tt = List.of(this.modelMapper.map(commentRepository.findAllByOrderByRateDesc()
                , CommentServiceModel[].class));
        return tt;
    }

    @Override
    public void deleteCommentById(String id) {
        Comment comment=commentRepository.findById(id).orElseThrow(()->{
         throw new EntityNotFoundException("The comment is already deleted!");
        });

        commentRepository.delete(comment);

    }
}
