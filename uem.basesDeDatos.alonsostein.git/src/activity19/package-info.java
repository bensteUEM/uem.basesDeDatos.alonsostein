/**
Java Programming

http://www.tutorialspoint.com/sqlite/sqlite_java.htm


Assignment PBES 19


Billing Application with Database

Create a new version of the Billing application based on the last one. Customer data will be stored in a database.  Specifications of the previous PBES Project iteration apply unless otherwise specified in the current specifications.


Novel Specifications

Create the database that you designed for this project, by executing SQL commands from the Command Line Interface.
All SQL commands, other than those required for database creation and initialization of sample customers, will be issued from the Java program.
Customer data will be exclusively stored in the database. This includes billing information. 
The functionality of showing customer's data includes the billing information.
The bill will include detailed information about the calls performed during the program's execution as well as previous calls.
Research Java's Logger class, and use it to create a log file that contain the detailed description of the program's operation.
Specifications in green are optional, and intended for groups aiming to get the top grade.
 
Clarifications

A functioning program built according to good programming style deserves higher grade.
After loading the customers' data from the database, the user will be able to create new customers through de GUI.
The first group activity in this project should be to discuss the work to be done and to organize how it will be done. Specifically:  distribution of tasks among the members of the group, timeline for development of parts, integration of parts and testing of the final product. All this information will be summarized on a separate document  (1 page at most) named "workload_distribution_and_planning.pdf". This document will be uploaded by using a specific link that will appear on the Online Campus.


The Basic Executable Demonstration

Insert 5 sample customer records in the database by typing SQL commands in the Command Line Interface of SQLite.
Create a sample MS-Excel sheet with one real value (the "minimum consumption").
Launch your program.
Read "minimum consumption" from the Excel sheet.
Show the data of an existing customer.
Let this customer place 2 calls.
Generate the monthly bill for this customer and store it in the database.
Read data of this customer from the database and show it on the GUI.
End execution.


Prof. Nacho Giráldez, PhD
ignacio.giraldez@uem.es

 *
 */
package activity19;