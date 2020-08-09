package com.stressthem.app.integrational;


import com.stressthem.app.domain.models.service.CryptocurrencyServiceModel;
import com.stressthem.app.domain.models.view.CryptocurrencyViewModel;
import com.stressthem.app.integrational.base.ControllerTestBase;
import com.stressthem.app.services.interfaces.CryptocurrencyService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class CurrencyControllerTest extends ControllerTestBase {

    @MockBean
    private CryptocurrencyService cryptocurrencyService;

    @MockBean
    private ModelMapper modelMapper;


    @Test
    public void testAllCurrencies() throws Exception {
        Mockito.when(this.cryptocurrencyService.getAllCryptocurrencies()).thenReturn(List.of(new CryptocurrencyServiceModel(), new CryptocurrencyServiceModel()));
        Mockito.when(modelMapper.map(this.cryptocurrencyService.getAllCryptocurrencies(), CryptocurrencyViewModel[].class)).thenReturn(
                new CryptocurrencyViewModel[]{new CryptocurrencyViewModel(), new CryptocurrencyViewModel()}
        );

        super.mockMvc.perform(get("/currencies"))
                .andExpect(status().isOk()).andExpect(model().attributeExists("cryptos"))
                .andExpect(view().name("currencies"));
    }

    @Test
    @WithMockUser(username = "valeri", authorities = {"ROOT"})
    public void testDeleteCryptocurrency() throws Exception {
        super.mockMvc.perform(get("/currencies/delete/1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/currencies?favicon=%5Cassets%5Cimg%5Cfavicon.png"));

    }

}
