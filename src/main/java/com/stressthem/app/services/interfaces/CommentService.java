package com.stressthem.app.services.interfaces;

import com.stressthem.app.domain.models.service.CommentServiceModel;

import java.util.List;

public interface CommentService {

    List<CommentServiceModel> getAllCommentsSortedByRatingDesc();

    void deleteCommentById(String id);

    void registerComment(CommentServiceModel commentServiceModel,String username);

    boolean hasUserAlreadyCommented(String username);
}
