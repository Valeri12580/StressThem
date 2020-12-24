package com.stressthem.app.unit.services;

import com.stressthem.app.domain.entities.Transaction;
import com.stressthem.app.domain.models.service.TransactionServiceModel;
import com.stressthem.app.repositories.TransactionRepository;
import com.stressthem.app.services.TransactionServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;

@ExtendWith(MockitoExtension.class)
public class TransactionServiceTest {
    @Mock
    private TransactionRepository repository;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private TransactionServiceImpl transactionService;


    @Test
    public void saveTransactionShouldSaveUpcomingTransactions(){
        TransactionServiceModel transactionServiceModel=new TransactionServiceModel();
        Transaction transaction=new Transaction();
        Mockito.when(modelMapper.map(transactionServiceModel,Transaction.class)).thenReturn(transaction);
        transactionService.saveTransaction(transactionServiceModel);
        Mockito.verify(repository).save(transaction);

    }
}
