package com.stressthem.app.services.interfaces;

import com.stressthem.app.domain.models.service.CryptocurrencyServiceModel;

import java.util.List;

public interface CryptocurrencyService {

    List<CryptocurrencyServiceModel> getAllCryptocurrencies();

    CryptocurrencyServiceModel getCryptocurrencyByName(String name);

    void deleteById(String id);


}
