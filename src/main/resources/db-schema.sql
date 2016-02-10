/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 * Author:  Ivan
 * Created: Feb 10, 2016
 */

create table Account (
    id long not null primary key auto_increment,
    name varchar(80) not null,
    balance long not null
);
create table Transfers (
    id long not null primary key auto_increment,
    sender varchar(80) not null,
    reciever varchar(80) not null,
    amount long not null
);
