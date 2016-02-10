/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package application.mappers;

import application.pojos.Transfer;
import java.util.Collection;

/**
 *
 * @author Ivan
 */
public interface TransferMapper 
{
    public void insertTransfer(Transfer transfer);
    public Collection<Transfer> getByName(String name); 
}
