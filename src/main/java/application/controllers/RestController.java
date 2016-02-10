/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package application.controllers;

import application.pojos.Account;
import application.pojos.Transfer;
import application.services.MyBatisService;
import java.util.Collection;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 *
 * @author Ivan
 */
@org.springframework.web.bind.annotation.RestController
public class RestController 
{
    static final String STATUS = "Online";
    @Autowired
    SqlSessionFactory sql;
    @Autowired
    MyBatisService myBatisService;
    
    @RequestMapping(value="/online")
    public String test()
    {
        return STATUS;
    }
    @RequestMapping(value="/signup" , method =RequestMethod.PUT)
    public ResponseEntity<?> newAccount(@RequestBody String account)
    {
        if(myBatisService.getByName(account)!=null)
        {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
        else
        {
            Account newAccount = new Account();
            newAccount.setName(account);
            newAccount.setBalance(1000);
            myBatisService.insertAccount(newAccount);
        }
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
    @RequestMapping(value="/transfer" , method = RequestMethod.POST)
    public ResponseEntity<?> newTransfer(@RequestBody Transfer transfer)
    {
        Account sender = myBatisService.getByName(transfer.getSender());
        Account reciever = myBatisService.getByName(transfer.getReciever());
        if(reciever==null||sender==null||transfer.getAmount()<=0||sender.getBalance()<transfer.getAmount())
        {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        myBatisService.transferFunds(sender, reciever, transfer);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }
    @RequestMapping(value="/account" , method = RequestMethod.POST)
    public Collection<Transfer> getLog(@RequestBody String name)
    {
        return myBatisService.getTransferByName(name);
    }
}
