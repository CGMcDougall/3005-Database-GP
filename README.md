# 3005-Database-GP
A database management system, for project 2 of class 3005.
Group Members: Connor McDougall and Oliver Przednowek.

Walkthorugh Youtube link: https://youtu.be/Q_5Gos1vXb8

Project report link: https://docs.google.com/document/d/14Gp7K4MYid9zVK_CLVaWkUkj67YhxxrmzPNmxB-QgoM
(Also located in repository as Project Report)

Description:
  A program made for users to interact with a database system desinged for a gym. 

Instructions:
  To make work on users system, they must create a postgresDB and initilize it and load it with the SQL code provided. Then to have the code work, change the login infomation (Strings: url, user, pass) in the SQL manager (on somewhere between lines 30-38ish) to be in line with you database. 


Files:

  - SQL/ 
      - Contains the SQL code for the database init and sample data. 
  - src/ 
      - Notable classes
        - Member 
          - Contains the functionality for all member class requirments
             - Made by Connor
        - Admin
           - Contains the functionality for all admin class requirments
             - Made by Oliver
        - Trainer
           - Contains the functionality for all trainer class requirments
             - Made by Connor
        - Session
          - Contains the functionailty for all the Schedule requirments
             - Made by Oliver
        - Control
          - The control class manages the overall program and flow
        - View
          - Manages the console interface, and takes input from console and supplies it to control class.
        - SQLManager
          - Manages all interaction between code and sql database.
  - Models/
     - Contains both an ER diagram and relational schema  
      
      
