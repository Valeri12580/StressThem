package com.stressthem.app.initialization;

import com.stressthem.app.domain.entities.Article;
import com.stressthem.app.domain.entities.User;
import com.stressthem.app.repositories.ArticleRepository;
import com.stressthem.app.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Component
public class ArticleInit implements CommandLineRunner {
    private ArticleRepository articleRepository;
    private UserRepository userRepository;

    @Autowired
    public ArticleInit(ArticleRepository articleRepository, UserRepository userRepository) {
        this.articleRepository = articleRepository;
        this.userRepository = userRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        User user = this.userRepository.findUserByUsername("valeri12580").orElse(null);
        Article articleOne = new Article("Our website is launched!", "Welcome to our website.Here you can find various services that will help you test your resources or something else ;)"
                , user, LocalDateTime.now(ZoneId.systemDefault()), "https://images.idgesg.net/images/article/2020/05/server_racks_close-up_perspective_shot_by_monsitj_gettyimages-918951042_cso_nw_2400x1600-100841600-large.jpg");

        Article articleTwo = new Article("Lorem Ipsum", "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Maecenas sed enim eu nunc dignissim pretium. Vivamus eu blandit sem. Vivamus elit ipsum, vehicula id augue in, rhoncus volutpat odio. Nulla hendrerit sem a purus condimentum, id tincidunt tortor sagittis. Nulla auctor posuere lacus, sit amet condimentum magna imperdiet a. Pellentesque ultricies ac sapien sed semper. Mauris blandit enim auctor nibh facilisis, sed bibendum tellus imperdiet. Donec odio est, pharetra ut interdum in, commodo eu velit. Praesent sem eros, tempus nec laoreet a, semper eu arcu. Duis sagittis fringilla sapien a rhoncus. Maecenas at sapien id orci feugiat rhoncus eu vel nibh. Nulla suscipit ultrices est. Nullam diam ipsum, viverra eget turpis eget, dignissim sollicitudin odio.",
                user, LocalDateTime.now(ZoneId.systemDefault()).plus(1, ChronoUnit.HOURS), null);

        articleRepository.saveAll(List.of(articleOne, articleTwo));

    }
}
