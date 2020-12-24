package com.stressthem.app.integrational;

import com.stressthem.app.integrational.base.ControllerTestBase;
import com.stressthem.app.repositories.CommentRepository;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.test.context.support.WithMockUser;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class AboutControllerTest extends ControllerTestBase {

    @Autowired
    private CommentRepository commentRepository;


    @Test
    public void testAboutPage_ShouldLoad() throws Exception {
        super.mockMvc.perform(get("/about"))
                .andExpect(view().name("about-us"))
                .andExpect(model().attribute("comments", Matchers.hasSize(2)))
                .andExpect(status().isOk());

    }

    @Test
    @WithMockUser(username = "valeri12580", roles = {"ADMIN", "ROOT"})
    public void deleteComment_ShouldThrowException_CommentNotFound() throws Exception {
        super.mockMvc.perform(get("/about/comments/delete/{id}", "12312312"))
                .andExpect(status().isBadRequest())
                .andExpect(view().name("error"))
                .andExpect(model().attribute("message", "The comment is not found!"));

    }

    @Test
    @WithMockUser(username = "valeri12580", roles = {"ADMIN", "ROOT"})
    public void deleteComment_ShouldWorkCorrectly() throws Exception {
        String id = commentRepository.findAll().get(0).getId();
        super.mockMvc.perform(get("/about/comments/delete/{id}", id))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/about?favicon=%5Cassets%5Cimg%5Cfavicon.png#comments"));


        assertEquals(1, commentRepository.count());
    }

    @Test
    public void addCommentPageShouldLoad() throws Exception {
        super.mockMvc.perform(get("/about/comments/create"))
                .andExpect(view().name("comment-add"))
                .andExpect(model().attributeExists("comment"))
                .andExpect(status().isOk());
    }

}
