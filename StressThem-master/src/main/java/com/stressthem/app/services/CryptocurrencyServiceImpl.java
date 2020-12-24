package com.stressthem.app.services;

import com.stressthem.app.domain.entities.Cryptocurrency;
import com.stressthem.app.domain.entities.User;
import com.stressthem.app.domain.models.service.CryptocurrencyServiceModel;
import com.stressthem.app.exceptions.CryptocurrencyNotFoundException;
import com.stressthem.app.repositories.CryptocurrencyRepository;
import com.stressthem.app.services.interfaces.CryptocurrencyService;
import com.stressthem.app.services.interfaces.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Arrays;
import java.util.List;

@Service
public class CryptocurrencyServiceImpl implements CryptocurrencyService {
    private ModelMapper modelMapper;
    private CryptocurrencyRepository cryptocurrencyRepository;
    private UserService userService;

    @Autowired
    public CryptocurrencyServiceImpl(ModelMapper modelMapper, CryptocurrencyRepository cryptocurrencyRepository, @Lazy UserService userService) {
        this.modelMapper = modelMapper;
        this.cryptocurrencyRepository = cryptocurrencyRepository;
        this.userService = userService;
    }


    @Override
    public List<CryptocurrencyServiceModel> getAllCryptocurrencies() { ;
        return Arrays.asList(this.modelMapper.map(this.cryptocurrencyRepository.findAll(),CryptocurrencyServiceModel[].class));
    }

    @Override
    public CryptocurrencyServiceModel getCryptocurrencyByName(String name) {
        return this.modelMapper.map(this.cryptocurrencyRepository.findByTitle(name).orElseThrow(()->new CryptocurrencyNotFoundException("Cryptocurrency is not found")),CryptocurrencyServiceModel.class);
    }

    @Override
    public CryptocurrencyServiceModel registerCryptocurrency(CryptocurrencyServiceModel cryptocurrencyServiceModel, String username) {
        User user=this.modelMapper.map(this.userService.getUserByUsername(username),User.class);

        Cryptocurrency cryptocurrency=this.modelMapper.map(cryptocurrencyServiceModel,Cryptocurrency.class);
        cryptocurrency.setAuthor(user);
        cryptocurrency.setAddedOn(LocalDateTime.now(ZoneId.systemDefault()));
        this.cryptocurrencyRepository.save(cryptocurrency);
        return this.modelMapper.map(cryptocurrency,CryptocurrencyServiceModel.class);
    }

    @Override
    public void deleteById(String id) {
        this.cryptocurrencyRepository.deleteById(id);
    }
}
