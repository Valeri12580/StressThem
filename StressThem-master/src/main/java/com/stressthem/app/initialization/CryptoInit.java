package com.stressthem.app.initialization;

import com.stressthem.app.domain.entities.Cryptocurrency;
import com.stressthem.app.domain.entities.User;
import com.stressthem.app.repositories.CryptocurrencyRepository;
import com.stressthem.app.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

@Component
public class CryptoInit implements CommandLineRunner {
    private UserRepository userRepository;
    private CryptocurrencyRepository cryptocurrencyRepository;

    @Autowired
    public CryptoInit(UserRepository userRepository, CryptocurrencyRepository cryptocurrencyRepository) {
        this.userRepository = userRepository;
        this.cryptocurrencyRepository = cryptocurrencyRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        User user = this.userRepository.findUserByUsername("valeri12580").get();
        Cryptocurrency bitcoin = new Cryptocurrency("Bitcoin",
                "Bitcoin was the first cryptocurrency to successfully record transactions on a secure, decentralized blockchain-based network. Launched in early 2009 by its pseudonymous creator Satoshi Nakamoto, Bitcoin is the largest cryptocurrency measured by market capitalization and amount of data stored on its blockchain.",
                user, LocalDateTime.now(ZoneId.systemDefault()), "https://static.coindesk.com/wp-content/uploads/2018/11/dark-bitcoin-scaled.jpg");

        Cryptocurrency ethereum = new Cryptocurrency("Ethereum", "Ethereum is open access to digital money and data-friendly services for everyone – no matter your background or location. It's a community-built technology behind the cryptocurrency Ether (ETH) and thousands of applications you can use today.",
                user, LocalDateTime.now(ZoneId.systemDefault()), "https://www.investopedia.com/thmb/eLkTSBXs8esM5-XGr2IdhEI5pi8=/735x0/shutterstock_1030451626-5bfc30d646e0fb0026026b76.jpg");

        this.cryptocurrencyRepository.saveAll(List.of(ethereum, bitcoin));

    }
}
