/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package application.mappers;

import application.pojos.Account;

/**
 *
 * @author Ivan
 */
public interface AccountMapper 
{
    public void insertAccount(Account account);
    public Account getByName(String name);
    public void updateAccount(Account account);
}
