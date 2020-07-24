package com.stressthem.app.services;

import com.stressthem.app.domain.models.service.CryptocurrencyServiceModel;
import com.stressthem.app.repositories.CryptocurrencyRepository;
import com.stressthem.app.services.interfaces.CryptocurrencyService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class CryptocurrencyServiceImpl implements CryptocurrencyService {
    private ModelMapper modelMapper;
    private CryptocurrencyRepository cryptocurrencyRepository;

    @Autowired
    public CryptocurrencyServiceImpl(ModelMapper modelMapper, CryptocurrencyRepository cryptocurrencyRepository) {
        this.modelMapper = modelMapper;
        this.cryptocurrencyRepository = cryptocurrencyRepository;
    }


    @Override
    public List<CryptocurrencyServiceModel> getAllCryptocurrencies() { ;
        return Arrays.asList(this.modelMapper.map(this.cryptocurrencyRepository.findAll(),CryptocurrencyServiceModel[].class));
    }

    @Override
    public CryptocurrencyServiceModel getCryptocurrencyByName(String name) {
        return this.modelMapper.map(this.cryptocurrencyRepository.findByTitle(name),CryptocurrencyServiceModel.class);
    }

    @Override
    public void deleteById(String id) {
        this.cryptocurrencyRepository.deleteById(id);
    }
}
