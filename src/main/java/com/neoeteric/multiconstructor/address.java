package com.neoeteric.multiconstructor;

public class address  {
    String employname;
    int employeeage;
    int employeeid;

    public address(employee_details obj) {
        this.employname = obj.name;
        this.employeeage = obj.age;
        this.employeeid = obj.id;

    }
    void display(){
        System.out.println("employee name is "+employname);
        System.out.println("employee age is "+employeeage);
        System.out.println("employee id is "+employeeid);

    }
    public static void main(String[] args){
        employee_details objA = new employee_details("ram",44,1);
        employee_details objA2 = new employee_details("ramesh",42,2);
        address objB = new address(objA);
        address objB2 = new address(objA2);

        objB.display();
        objB2.display();

    }

}
