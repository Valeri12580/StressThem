package com.stressthem.app.unit.services;

import com.stressthem.app.domain.entities.Cryptocurrency;
import com.stressthem.app.domain.entities.User;
import com.stressthem.app.domain.models.service.CryptocurrencyServiceModel;
import com.stressthem.app.domain.models.service.UserServiceModel;
import com.stressthem.app.exceptions.CryptocurrencyNotFoundException;
import com.stressthem.app.repositories.CryptocurrencyRepository;
import com.stressthem.app.services.CryptocurrencyServiceImpl;
import com.stressthem.app.services.interfaces.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class CryptocurrencyServiceTest {

    @Mock
    private ModelMapper modelMapper;

    @Mock
    private CryptocurrencyRepository repository;

    @Mock
    private UserService userService;

    @InjectMocks
    private CryptocurrencyServiceImpl cryptocurrencyService;

    private Cryptocurrency cryptoEntityOne;
    private CryptocurrencyServiceModel cryptoServiceModelOne;
    private CryptocurrencyServiceModel cryptoServiceModelTwo;

    private Cryptocurrency cryptoEntityTwo;

    private User user;



    @BeforeEach
    public void init() {
        this.user = new User();
        this.user.setUsername("valeri");
        this.user.setId("1");


        this.cryptoEntityOne = new Cryptocurrency("Bitcoin",
                "Bitcoin was the first cryptocurrency to successfully record transactions on a secure, decentralized blockchain-based network. Launched in early 2009 by its pseudonymous creator Satoshi Nakamoto, Bitcoin is the largest cryptocurrency measured by market capitalization and amount of data stored on its blockchain.",
                user, LocalDateTime.now(ZoneId.systemDefault()), "https://static.coindesk.com/wp-content/uploads/2018/11/dark-bitcoin-scaled.jpg");

        this.cryptoEntityTwo = new Cryptocurrency("Ethereum", "Ethereum is open access to digital money and data-friendly services for everyone – no matter your background or location. It's a community-built technology behind the cryptocurrency Ether (ETH) and thousands of applications you can use today.",
                user, LocalDateTime.now(ZoneId.systemDefault()), "https://www.investopedia.com/thmb/eLkTSBXs8esM5-XGr2IdhEI5pi8=/735x0/shutterstock_1030451626-5bfc30d646e0fb0026026b76.jpg");

        this.cryptoServiceModelOne =new CryptocurrencyServiceModel();
        cryptoServiceModelOne.setTitle(cryptoEntityOne.getTitle());
        cryptoServiceModelOne.setAuthor(user);

        cryptoServiceModelTwo=new CryptocurrencyServiceModel();
        cryptoServiceModelTwo.setTitle(cryptoEntityTwo.getTitle());
        cryptoServiceModelTwo.setAuthor(user);
    }

    @Test
    public void getCryptocurrencyByNameShouldReturnCorrectCryptocurrency(){

        Mockito.when(repository.findByTitle("Bitcoin")).thenReturn(Optional.of(cryptoEntityOne));

        Mockito.when(modelMapper.map(cryptoEntityOne, CryptocurrencyServiceModel.class))
                .thenReturn(cryptoServiceModelOne);

        CryptocurrencyServiceModel actual=this.cryptocurrencyService.getCryptocurrencyByName("Bitcoin");

        assertEquals(cryptoServiceModelOne.getTitle(),actual.getTitle());
        assertEquals(cryptoServiceModelOne.getAuthor(),actual.getAuthor());
        Mockito.verify(repository).findByTitle("Bitcoin");
    }
    @Test
    public void getCryptocurrencyByNameShouldThrowCryptocurrencyNotFoundException(){
        Mockito.when(repository.findByTitle("Exception")).thenReturn(Optional.empty());

        assertThrows(CryptocurrencyNotFoundException.class,()->{
            this.cryptocurrencyService.getCryptocurrencyByName("Exception");
        });
    }

    @Test
    @WithMockUser(roles = {"ADMIN","ROOT"})
    public void deleteCryptocurrencyByIdShouldDelete(){
        this.cryptocurrencyService.deleteById("1");
        Mockito.verify(repository).deleteById("1");
    }

    @Test
    public void getAllCryptocurrenciesShouldReturnAllEntities(){
        Mockito.when(repository.findAll()).thenReturn(List.of(cryptoEntityTwo,cryptoEntityTwo));
        Mockito.when(modelMapper.map(repository.findAll(),CryptocurrencyServiceModel[].class))
                .thenReturn(List.of(cryptoServiceModelOne,cryptoServiceModelTwo).toArray(CryptocurrencyServiceModel[]::new));

        List<CryptocurrencyServiceModel>actual=cryptocurrencyService.getAllCryptocurrencies();

        assertEquals(2,actual.size());
        assertEquals(cryptoServiceModelOne,actual.get(0));
        assertEquals(cryptoServiceModelTwo,actual.get(1));
    }

    @Test
    public void registerCryptocurrencyShouldWork(){
        Mockito.when(userService.getUserByUsername("valeri")).thenReturn(new UserServiceModel());
        Mockito.when(modelMapper.map(userService.getUserByUsername("valeri"),User.class)).thenReturn(user);
        Mockito.when(modelMapper.map(cryptoServiceModelOne,Cryptocurrency.class)).thenReturn(cryptoEntityOne);

        cryptocurrencyService.registerCryptocurrency(cryptoServiceModelOne,"valeri");
        Mockito.verify(repository).save(cryptoEntityOne);
    }
}
