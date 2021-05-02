  CREATE TABLE Department(
    deptNo VARCHAR(20),
    deptName VARCHAR(30),
    empNo VARCHAR(20),
    PRIMARY KEY(deptNo)
    );
    
 
 CREATE TABLE Employee(
   empNo VARCHAR(20),
   fName VARCHAR(30),
   lName VARCHAR(30),
   address VARCHAR(30),
   DOB DATE,
   sex CHAR,
   position VARCHAR(30),
   deptNo VARCHAR(20),
   PRIMARY KEY (empNo),
   FOREIGN KEY (deptNo) REFERENCES Department,
   CHECK(sex IN('M','F'))
   );
   
   ALTER TABLE Department
   ADD CONSTRAINT fk
     FOREIGN KEY (empNo) REFERENCES Employee(empNo);
  
  CREATE TABLE Project (
    projNo VARCHAR(20) NOT NULL,
    projName VARCHAR(20),
    deptNo VARCHAR(20),
    
    PRIMARY KEY(projNo),
    FOREIGN KEY(deptNo) REFERENCES Department
    );
    
    CREATE TABLE WorksOn( 
    empNo VARCHAR(20) NOT NULL,
    projNo VARCHAR(20) NOT NULL,
    dateWorked DATE,
    hoursWorked SMALLINT,
    FOREIGN KEY (empNo) REFERENCES Employee(empNo),
    FOREIGN KEY (projNo) REFERENCES Project(projNo)
    );