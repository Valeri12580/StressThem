package com.stressthem.app.unit.services;

import com.stressthem.app.domain.entities.Comment;
import com.stressthem.app.domain.models.service.CommentServiceModel;
import com.stressthem.app.domain.models.service.UserServiceModel;
import com.stressthem.app.exceptions.CommentNotFoundException;
import com.stressthem.app.repositories.CommentRepository;
import com.stressthem.app.services.CommentServiceImpl;
import com.stressthem.app.services.interfaces.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.function.Executable;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;

import javax.persistence.EntityNotFoundException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;


@ExtendWith(MockitoExtension.class)
public class CommentServiceTest {

    @Mock
    private ModelMapper modelMapper;

    @Mock
    private CommentRepository commentRepository;

    @Mock
    private UserService userService;


    @InjectMocks
    private CommentServiceImpl commentService;

    private CommentServiceModel commentServiceModelOne;

    private CommentServiceModel commentServiceModelTwo;


    private Comment comment;

    private UserServiceModel user;


    @BeforeEach
    public void init() {
        commentServiceModelOne = new CommentServiceModel(4, "Amazing site,the best", null);
        commentServiceModelTwo = new CommentServiceModel(2, "The worst thing ever", null);
        comment = new Comment();
        user=new UserServiceModel();
        user.setComment(commentServiceModelOne);
    }


    @Test
    public void getAllCommentsShouldReturnAllComments() {
        List<Comment> comments = List.of(comment);
        Mockito.when(commentRepository.findAllByOrderByRateDesc()).thenReturn(comments);
        Mockito.when(modelMapper.map(comments, CommentServiceModel[].class))
                .thenReturn(Arrays.asList(commentServiceModelOne, commentServiceModelTwo).toArray(CommentServiceModel[]::new));


        List<CommentServiceModel> result = commentService.getAllCommentsSortedByRatingDesc();
        assertEquals(2, result.size());
    }

    @Test

    public void deleteCommentByIdShouldDeleteComment() {
        Mockito.when(commentRepository.findById("1")).thenReturn(Optional.of(comment));
        commentService.deleteCommentById("1");
        Mockito.verify(commentRepository).delete(comment);
    }

    @Test

    public void deleteCommentByIdShouldThrowException_CommentNotFound() {

        assertThrows(CommentNotFoundException.class, new Executable() {
            @Override
            public void execute() throws Throwable {
                commentService.deleteCommentById("1");
            }
        });
    }

    @Test
    public void registerCommentShouldWork() {
        Mockito.when(userService.getUserByUsername("valeri12580")).thenReturn(user);
        Mockito.when(modelMapper.map(commentServiceModelOne,Comment.class)).thenReturn(comment);
        commentService.registerComment(commentServiceModelOne, "valeri12580");

        Mockito.verify(commentRepository).save(comment);
    }

    @Test
    public void hasUserAlreadyCommentedShouldWork(){
        Mockito.when(userService.getUserByUsername("valeri12580")).thenReturn(user);

        assertTrue(commentService.hasUserAlreadyCommented("valeri12580"));
    }


}
