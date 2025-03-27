package com.neoeteric.multiconstructor.constructors;

class Default {

     Default() {
        System.out.println("default constructor");

    }

static class Parameters {
         String name;
         int id;
     Parameters(String name,int id ){
         this.name = name;
         this.id = id;

     }
     }
    public static void main(String[] agrs){

        Default hello = new Default();
        Parameters employeedetails = new Parameters("Pranav",007);
        System.out.println("employee name is "+employeedetails.name+" and employee id is "+employeedetails.id);
    }
}

