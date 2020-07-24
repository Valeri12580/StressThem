package com.stressthem.app.services;

import com.stressthem.app.domain.entities.Transaction;
import com.stressthem.app.domain.models.service.TransactionServiceModel;
import com.stressthem.app.repositories.TransactionRepository;
import com.stressthem.app.services.interfaces.TransactionService;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TransactionServiceImpl implements TransactionService {
    private TransactionRepository repository;
    private ModelMapper modelMapper;

    @Autowired
    public TransactionServiceImpl(TransactionRepository repository, ModelMapper modelMapper) {
        this.repository = repository;
        this.modelMapper = modelMapper;
    }

    @Override
    public void saveTransaction(TransactionServiceModel transactionServiceModel) {
        Transaction transaction=this.modelMapper.map(transactionServiceModel, Transaction.class);

        this.repository.save(transaction);



    }
}
