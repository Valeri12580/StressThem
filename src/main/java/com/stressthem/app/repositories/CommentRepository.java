package com.stressthem.app.repositories;

import com.stressthem.app.domain.entities.Comment;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment,String> {

    List<Comment> findAllByOrderByRateDesc();
}
