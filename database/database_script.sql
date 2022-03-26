create database appteczka;
use appteczka;

drop table login_data;
create table login_data(
email  varchar(85) primary key,
password varchar(50) not null
);

create table drugs_dates(
id int primary key auto_increment,
drug_name varchar(85) not null,
expiration_date date not null,
email_drug varchar(85) not null,
foreign key (email_drug) references login_data(email)
);

drop table dosage;
drop view user_drugs;
create table dosage(
drug int auto_increment,
FOREIGN KEY (drug) REFERENCES drugs_dates(ID),
intake_hour time not null,
drug_amount smallint not null,
no_eating bit
);

create view user_drugs as select login_data.email as email, 
drugs_dates.drug_name as user_drug_name,
drugs_dates.expiration_date as user_expiration_date,
dosage.intake_hour as user_intake_hour,
dosage.drug_amount as user_drug_amount,
dosage.no_eating as user_no_eating
from drugs_dates join dosage on dosage.drug = drugs_dates.id
join login_data on login_data.email = drugs_dates.email_drug;

select * from user_drugs;
drop user 'AdminAPP'@'localhost';
create user 'AdminAPP'@'localhost' identified by 'admin';
GRANT SELECT, INSERT, UPDATE, DELETE on 
appteczka.* to 'AdminAPP'@'localhost';
flush privileges;
show grants for 'AdminAPP'@'localhost';
show grants for 'root'@'localhost';