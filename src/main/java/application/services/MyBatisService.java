/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package application.services;

import application.mappers.AccountMapper;
import application.mappers.TransferMapper;
import application.pojos.Account;
import application.pojos.Transfer;
import java.util.Collection;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Ivan
 */
@Service
@Transactional
public class MyBatisService 
{
    @Autowired
    private SqlSession sqlSession;
    public void insertAccount(Account account)
    {
        AccountMapper am = sqlSession.getMapper(AccountMapper.class);
        am.insertAccount(account);
    }
    public Account getByName(String name)
    {
        AccountMapper am = sqlSession.getMapper(AccountMapper.class);
        return am.getByName(name);
    }
    public Collection<Transfer> getTransferByName(String name)
    {
        TransferMapper tm = sqlSession.getMapper(TransferMapper.class);
        return tm.getByName(name);
    }
    public void transferFunds(Account sender , Account reciever , Transfer transfer)
    {
        TransferMapper tm = sqlSession.getMapper(TransferMapper.class);
        tm.insertTransfer(transfer);
        sender.setBalance(sender.getBalance()-transfer.getAmount());
        reciever.setBalance(reciever.getBalance()+transfer.getAmount());
        AccountMapper am = sqlSession.getMapper(AccountMapper.class);
        am.updateAccount(sender);
        am.updateAccount(reciever);
    }
}
