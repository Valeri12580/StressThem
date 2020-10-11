package com.stressthem.app.services.interfaces;

import com.stressthem.app.domain.models.service.CommentServiceModel;

import java.util.List;

public interface CommentService {

    List<CommentServiceModel> getAllCommentsSortedByRatingDesc();
}
