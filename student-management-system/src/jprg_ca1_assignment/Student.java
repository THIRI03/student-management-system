/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package jprg_ca1_assignment;

import java.util.ArrayList;

/**
 *
 * @author thiri
 */
public class Student {
    private String name;
    private String adminNumber;
    private String className;
    private ArrayList<Module> modules;
    private double gpa;
    
    
    public Student(String name, String adminNumber, String className, ArrayList<Module>modules, double gpa){
        this.name = name;
        this.adminNumber = adminNumber;
        this.className = className;
        this.modules = modules;
        this.gpa = gpa;
    }
    
    public Student(){
        this.name = "";
        this.adminNumber = "";
        this.className = "";
        this.modules = new ArrayList<>();
        this.gpa = calculateGPA(modules);
    }
    
    //---------------------------------------------------------------
    // getter and setter
    //---------------------------------------------------------------

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAdminNumber() {
        return adminNumber;
    }

    public void setAdminNumber(String adminNumber) {
        this.adminNumber = adminNumber;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public ArrayList<Module> getModules() {
        return modules;
    }

    public void setModules(ArrayList<Module> modules) {
        this.modules = modules;
    }

    public double getGpa() {
        return gpa;
    }

    public void setGpa(double gpa) {
        this.gpa = gpa;
    }
    
    public void getModuleInfo(){
        for(Module module : modules){
            System.out.println("\t" + module.getModuleCode() + " - " + module.getModuleName() + " (" + module.getCreditUnit() + " credits)");
        }
    }
    
    public String getStudentInfo() {
        StringBuilder info = new StringBuilder();
        info.append("Name: ").append(this.name).append("\n");
        info.append("Student ID: ").append(this.adminNumber).append("\n");
        info.append("Class ID: ").append(this.className).append("\n");
        info.append("GPA: ").append(this.gpa).append("\n");
        info.append("Modules:\n");
        for (Module module : modules) {
            info.append(" - ").append(module.getModuleInfo()).append("\n");
        }
        return info.toString();
    }
    
    public void addModule(Module module) {
        modules.add(module);
    }

    

    
    //---------------------------------------------------------------
    // getter and setter
    //---------------------------------------------------------------
    
    // calculating gpa
    public static double calculateGPA(ArrayList<Module>modules) {
        double totalGradePoints = 0.0;
        int totalCreditUnits = 0;
        
        for (Module module : modules) {
            totalGradePoints += module.getCreditUnit()* module.getGradePoints(module.getStudentMarks());
            totalCreditUnits += module.getCreditUnit();
        }
        return totalCreditUnits == 0 ? 0 : totalGradePoints / totalCreditUnits;
    }
    
}
