CREATE TABLE Users (PID varchar(16), UserName varchar(50),  Password varchar not null, isAdmin BOOL); /* add accounts */

CREATE TABLE Accounts (AID varchar(16), accountName varchar(100), cash varchar(16), PRIMARY KEY(AID), FOREIGN KEY(HID), Transactions); /* add trasaction history */

CREATE TABLE Transactions (HID varchar(16), history varchar(16));