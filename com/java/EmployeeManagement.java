package com.java;

import javax.xml.transform.Result;
import java.sql.*;
import java.util.*;

public class EmployeeManagement {
    static class Employee {
        int empId;
        String name;
        String designation;
        int salary;
    }

    public static void addEmployee(Connection con) {

        Scanner scanner = new Scanner(System.in);

        Employee emp = new Employee();
        System.out.println("Enter Employee ID: ");
        emp.empId = scanner.nextInt();
        scanner.nextLine();
        System.out.println("Enter Employee Name: ");
        emp.name = scanner.nextLine();
        System.out.println("Enter Employee Designation: ");
        emp.designation = scanner.nextLine();
        System.out.println("Enter Employee Salary: ");
        emp.salary = scanner.nextInt();

        String query = "insert into emp (id,name,designation,salary) values (?,?,?,?)";
        try(PreparedStatement statement = con.prepareStatement(query)){
            statement.setInt(1,emp.empId);
            statement.setString(2,emp.name);
            statement.setString(3,emp.designation);
            statement.setInt(4,emp.salary);
            statement.executeUpdate();
        }catch(SQLException e) {
            e.printStackTrace();
        }
        System.out.println("Employee Added Successfully");
    }

    public static void listEmployee(Connection con) {
        System.out.println("List of all the employees");
        System.out.println("-------------------------");
        String query ="select * from emp";
        Employee emp = new Employee();
        try(PreparedStatement statement=con.prepareStatement(query)){
            ResultSet res = statement.executeQuery(query);
            while(res.next()){
                emp.empId = res.getInt("id");
                emp.name = res.getString("name");
                emp.designation=res.getString("designation");
                emp.salary= res.getInt("salary");
                System.out.println("  ");
                System.out.println("EmpID: "+emp.empId+" "+"EmpName: "+emp.name+" "+"EmpDesignation: "+emp.designation+" "+"EmpSalary: "+emp.salary);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void deleteEmployee(Connection con, int id) {
        String query = "delete from emp where id =?";
        try (PreparedStatement statement = con.prepareStatement(query)) {
            statement.setInt(1, id);
            int rowAffected = statement.executeUpdate();
            if (rowAffected > 0) {
                System.out.println("Employee Deleted!!");
            } else {
                System.out.println("Employee Not Found");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
        public static void updateEmployee(Connection con, int id) {
        Employee emp= new Employee();
        emp.empId = id;
        Scanner scanner= new Scanner(System.in);
        System.out.println("Enter Employee Name: ");
        emp.name= scanner.nextLine();
        System.out.println("Enter Employee Designation: ");
        emp.designation= scanner.nextLine();
        System.out.println("Enter Employee Salary: ");
        emp.salary= scanner.nextInt();

        String query = "update emp set name=? , designation=? ,salary=? where id=?";
        try(PreparedStatement statement = con.prepareStatement(query)){
            statement.setString(1,emp.name);
            statement.setString(2,emp.designation);
            statement.setInt(3,emp.salary);
            int rowAffected = statement.executeUpdate();
            if (rowAffected > 0) {
                System.out.println("Employee Updated!!");
            } else {
                System.out.println("Employee Not Found");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        }

    public static void main(String[] args) {
        int choice, id;
        Scanner scanner = new Scanner(System.in);

        try {
            //connecting the sql database
//            Class.forName("oracle.jdbc.driver.OracleDriver");
            Connection con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:orcl", "scott", "tiger");
            do {
                //the console greeting screen
                System.out.println("Welcome to the employee Management System");
                System.out.println("1. Add Employee");
                System.out.println("2. Delete Employee");
                System.out.println("3. Update Employee");
                System.out.println("4. List Employee");
                System.out.println("5. Exit");
                System.out.println(" ");
                //checking whether the choice is an integer or not
                if (scanner.hasNextInt()) {
                    choice = scanner.nextInt();
                } else {
                    System.out.println("Invalid Input");
                    break;
                }
               //checking the user choice using switch case
                switch (choice) {
                    //adding an employee
                    case 1:
                        addEmployee(con);
                        break;
                    //deleting an employee
                    case 2:
                        System.out.println("Enter Employee ID to Delete: ");
                        //checking if the employee id is Integer or not
                        if(scanner.hasNextInt()) {
                            id=scanner.nextInt();
                        }else {
                            System.out.println("Invalid Input..");
                            continue;
                        }
                        deleteEmployee(con, id);
                        break;
                     //updating an employee
                    case 3:
                        System.out.println("Enter Employee ID to Update: ");
                        //checking if the employee id is Integer or not
                        if(scanner.hasNextInt()) {
                            id=scanner.nextInt();
                        }else {
                            System.out.println("Invalid Input..");
                            continue;
                        }
                        updateEmployee(con, id);
                        break;
                    //listing all employees
                    case 4:
                        System.out.println("*****LIST OF EMPLOYEES*****");
                        listEmployee(con);
                        break;
                    case 5:
                        System.out.println("*****THANK YOU FOR USING EMS*****");
                        System.exit(0);
                    default:
                        System.out.println("!!!!!!PLEASE ENTER A VALID OPTION!!!!!!");
                }

            } while (choice != 5);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}


