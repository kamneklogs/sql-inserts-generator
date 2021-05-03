
-- Consulta 1: Generar una lista de empleados

SELECT * FROM EMPLOYEE;


-- Consulta 2: Generar una lista de mujeres empleadasg

SELECT * FROM EMPLOYEE WHERE SEX ='F';



-- Consulta 3: Listar nombres y direcciones de empleados jefes


SELECT FNAME, ADDRESS FROM EMPLOYEE WHERE POSITION='Jefe';


 --Consulta 4: Nombres y direcciones de empleados de un departamento


SELECT FNAME, ADDRESS FROM EMPLOYEE WHERE DEPTNO='DEP000';


--Consulta 5: Empleados que trabajan en un proyecto en particular

SELECT * FROM EMPLOYEE WHERE empNo=
(SELECT empNo FROM WORKSON WHERE projNo='PRO000');


-- Consulta 6: Jefes para jubilar

SELECT * FROM EMPLOYEE WHERE (((EMPLOYEE.DOB BETWEEN TO_DATE('01/01/1950','dd/mm/yyyy') AND TO_DATE('01/01/1964','dd/mm/yyyy')) AND SEX='F' AND POSITION='Jefe') OR ((EMPLOYEE.DOB BETWEEN TO_DATE('01/01/1950','dd/mm/yyyy') AND TO_DATE('01/01/1959','dd/mm/yyyy')) AND SEX='M' AND POSITION='Jefe'));


-- Consulta 7: Empleados subordinados por un jefe en particular teniendo en cuenta que solo hay un jefe por departamento
SELECT * FROM EMPLOYEE WHERE POSITION != 'Jefe' AND DEPTNO='DEP005';




