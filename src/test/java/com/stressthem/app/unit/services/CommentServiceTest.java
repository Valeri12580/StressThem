package com.stressthem.app.unit.services;

import com.stressthem.app.domain.entities.Comment;
import com.stressthem.app.domain.models.service.CommentServiceModel;
import com.stressthem.app.repositories.CommentRepository;
import com.stressthem.app.services.CommentServiceImpl;
import com.stressthem.app.services.interfaces.CommentService;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class CommentServiceTest {

    @Mock
    private ModelMapper modelMapper;

    @Mock
    private CommentRepository commentRepository;

    @InjectMocks
    private CommentServiceImpl commentService;

    private CommentServiceModel commentOne;

    private CommentServiceModel commentTwo;

    @BeforeEach
    public void init(){
        commentOne=new CommentServiceModel(4,"Amazing site,the best",null);
        commentTwo=new CommentServiceModel(2,"The worst thing ever",null);
    }


    @Test
    public void getAllCommentsShouldReturnAllComments(){
        List<Comment> comments = List.of(new Comment(), new Comment());
        Mockito.when(commentRepository.findAllByOrderByRateDesc()).thenReturn(comments);
        Mockito.when(modelMapper.map(commentRepository.findAllByOrderByRateDesc(),CommentServiceModel[].class))
                .thenReturn(List.of(commentOne,commentTwo).toArray(CommentServiceModel[]::new));


        List<CommentServiceModel> result =commentService.getAllCommentsSortedByRatingDesc();
        Assertions.assertEquals(2,result.size());
    }


}
