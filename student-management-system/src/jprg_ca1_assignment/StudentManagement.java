/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package jprg_ca1_assignment;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.JOptionPane;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.sound.sampled.*;

/**
 *
 * @author thiri
 */
public class StudentManagement {
    
    private static ArrayList<Student> students = new ArrayList<>();
    private static StudentManagement studentManagement = new StudentManagement();
    
    public StudentManagement(){
        
        // initiate an arrayList of Module object
        ArrayList<Module> modulesTakenByS1 = new ArrayList<>();
        
        modulesTakenByS1.add(new Module("ST0501", "DBS", 5, 67));
        modulesTakenByS1.add(new Module("ST0503", "JPRG", 4, 90));

        
        Student s1 = new Student("Jim", "p2345678", "DIT/FT/1B/23", modulesTakenByS1, Student.calculateGPA(modulesTakenByS1));
        
        ArrayList<Module> modulesTakenByS2 = new ArrayList<>();
        modulesTakenByS2.add(new Module("ST0507", "SEP", 3, 77));
        modulesTakenByS2.add(new Module("ST0501", "DBS", 5, 63));

        
        Student s2 = new Student("Anna", "p1234567", "DIT/FT/1B/23", modulesTakenByS2, Student.calculateGPA(modulesTakenByS2));
        
        // create an arraylist to store student object
        students = new ArrayList<>();
        
        students.add(s1);
        students.add(s2);
    }
    
    
    // add student to the student arrayList
    private void addStudent(Student student){
        students.add(student);
    }
    
    private static String getAdminNumber(Student student){
        return student.getAdminNumber();
    }
    
    // add all the students to the getAllStudents()
    private static ArrayList<Student> getAllStudents(){
        return students;
    }
    
    //---------------------------------------------------------------------
    // Basic Validations(Shared)
    //---------------------------------------------------------------------
    protected static Integer controllerValidateInputIsInteger(String input){
        try{
            return Integer.valueOf(input);  
        }catch (NumberFormatException e){
            return null;
        }
    }
        
    protected static void viewDisplayUserInputErrorNotInteger(){
        JOptionPane.showMessageDialog(null,
            "Input is not an integer. Try again",
            "Error",
            JOptionPane.ERROR_MESSAGE);
    }
    
    protected static boolean controllerValidateInputIsEmpty(String input){
        return input.isBlank();
    }
    
    protected static void viewDisplayUserInputErrorIsEmpty(){
        JOptionPane.showMessageDialog(null,
            "Input is empty or blank. Try again",
            "Error",
            JOptionPane.ERROR_MESSAGE);
    }
    
    protected static String viewDisplayAdminOptions(){
        String dialogMessage = """
                               Enter your option: 
                               
                               1. Add new student
                               2. Delete student
                               3. Add new module for student
                               4. Check class at first place!
                               5. Back to User Identification
                               6. Quit""";
        String dialogTitle = "Student Admin System";
        return JOptionPane.showInputDialog(null,
                dialogMessage,
                dialogTitle,
                JOptionPane.QUESTION_MESSAGE);
        
    }
    

    
    //---------------------------------------------------------------------
    // Check User Identity
    //---------------------------------------------------------------------

    protected static String viewGetUserIdentity(){
        String dialogMessage = """
                               Enter the number 
                               1. Admin
                               2. User""";
        return JOptionPane.showInputDialog(null, 
                dialogMessage,
                "Student Admin System",
                JOptionPane.QUESTION_MESSAGE);
    }
    
    
    //---------------------------------------------------------------------
    // To check the range when the user identity input (admin or student) is 
    // not in range
    //---------------------------------------------------------------------
    
    protected static boolean controllerValidateInputInRange(int input){
        return input == 1 || input == 2;
    }
    
    protected static void viewDisplayUserInputErrorNotInRange(){
        JOptionPane.showMessageDialog(null,
            "Input is not in range. Try again",
            "Error",
            JOptionPane.ERROR_MESSAGE);
    }

    
    //---------------------------------------------------------------------
    // Admin
    //---------------------------------------------------------------------
    
    //---------------------------------------------------------------------
    // Admin Menu
    //---------------------------------------------------------------------
    
    private static void handleAdminActions(){
        handleAdminActions(null);
    }
    
    protected static void handleAdminActions(Clip program_intro){
        String strUserOption = "";
        Integer userOption = 0;
        boolean quit = false;
        while(!quit){
                    strUserOption = StudentManagement.viewDisplayAdminOptions();
                    if(strUserOption == null){
                        menuList();
                        break;
                    }
                    
                    if(StudentManagement.controllerValidateInputIsEmpty(strUserOption)){
                        viewDisplayUserInputErrorIsEmpty();
                        continue;
                    }
                    
                    userOption = StudentManagement.controllerValidateInputIsInteger(strUserOption);
                    if(userOption == null){
                        viewDisplayUserInputErrorNotInteger();
                        continue;
                    }
                    
                    if(!StudentManagement.controllerValidateUserOptionInRange(userOption)){
                        viewDisplayUserInputErrorNotInRange();
                        continue;
                    }
                    
                    switch(userOption){
                        case 1:
                            controllerAddNewStud();
                            break;
                        case 2:
                            deleteStudentByAdmNum();
                            break;
                        case 3:
                            addModuleForStudent(getAllStudents());
                            break;
                        case 4: 
                            studentManagement.rankClassesByAverageGPA();
                            break;
                        case 5:
                            menuList();
                            break;
                        case 6: 
                            viewProgramTerminate();
                            //quit = true;
                            break;
                            
                    }
                    
                    //quit = true;
                } 
        
    }
    
    private static boolean controllerValidateUserOptionInRange(int input){
        return input >= 1 && input <= 5;
    }
    

    //---------------------------------------------------------------------
    // 1. Add new students
    //---------------------------------------------------------------------
    
    // get student's name
    
    private static String viewGetStudentNameToAdd(){
        return JOptionPane.showInputDialog(null, 
                "Enter name:",
                "Student Admin System",
                JOptionPane.QUESTION_MESSAGE);
    }
    
    // get student's admin number
    private static String viewGetStudentAdminNumToAdd(){
        return JOptionPane.showInputDialog(null,
                "Enter Admin Number of student:",
                "Student Admin System",
                JOptionPane.QUESTION_MESSAGE);
    }
    
    // get student's class
    private static String viewGetStudentClassToAdd(){
        return JOptionPane.showInputDialog(null,
                "Enter Class :",
                "Student Admin System",
                JOptionPane.QUESTION_MESSAGE);
    }
    
    // validate class format
    private static boolean controllerValidateStudentClassIsInFormat(String input){
        String regex = "^[A-Z]{2,5}/(FT|PT)/[1-4][AB]/(?:0[1-9]|[12][0-9]|30)$";
        
        Pattern pattern = Pattern.compile(regex);
        
        Matcher matcher = pattern.matcher(input);
        
        return matcher.matches();
    }
    
    private static void viewDisplayStudentClassNotInFormat(){
        JOptionPane.showMessageDialog(null, 
                "Input is not in the correct format. Please try again with the valid format."
                        + "e.g. DIT/FT/2B/23",
                "Student Admin System",
                JOptionPane.INFORMATION_MESSAGE);
    }
    
    // get the number of modules the student will be taking
    // use this return String to loop the module informations
    private static String viewGetNumOfModulesToAdd(){
        return JOptionPane.showInputDialog(null,
                "Enter number of Modules Taken:",
                "Student Admin System",
                JOptionPane.QUESTION_MESSAGE);
    };
    
    private static int viewGetConfirmationForModuleNum(int moduleNum){
        return JOptionPane.showConfirmDialog(null,
                String.format("Are you sure to add %d", moduleNum),
                "Student Admin System",
                JOptionPane.YES_NO_OPTION);
    }
    
    private static boolean controllerCheckModuleNumInRange(int moduleNum){
        return moduleNum >= 10;
    }
    
    // =============================================
    // Module Info (code, name, credit unit, marks)
    // =============================================
   
    // module should be looping according to the number of modules the student 
    // will be taking
    
    private static String viewGetModuleCodeToAdd(Integer numOfModules){
        return JOptionPane.showInputDialog(null,
                String.format("Enter Module Code for module %d", numOfModules),
                "Student Admin System",
                JOptionPane.QUESTION_MESSAGE);
    };
    
    private static boolean controllerValidateModuleCodeIsInFormat(String input){
        String regex = "^[A-Z]{2}[0-9]{4,5}$";
        
        Pattern pattern = Pattern.compile(regex);
        
        Matcher matcher = pattern.matcher(input);
        
        return matcher.matches();
    }
    
    private static void viewModuleCodeNotInFormat(){
        JOptionPane.showMessageDialog(null, """
                                            Module Code is not in format. 
                                            Please try again. e.g. ST0506""",
                "Student Admin System",
                JOptionPane.ERROR_MESSAGE);
    }
    
    private static String viewGetModuleNameToAdd(Integer numOfModules){
        return JOptionPane.showInputDialog(null,
                String.format("Enter Module Name for module %d", numOfModules),
                "Student Admin System",
                JOptionPane.QUESTION_MESSAGE);
    };
    
    private static String viewGetModuleCreditUnitToAdd(Integer numOfModules){
        return JOptionPane.showInputDialog(null,
                String.format("Enter Credit  Unit for module %d", numOfModules),
                "Student Admin System",
                JOptionPane.QUESTION_MESSAGE);
    };
    
    private static String viewGetModuleMarksToAdd(Integer numOfModules){
        return JOptionPane.showInputDialog(null,
                String.format("Enter Module Marks for module %d", numOfModules),
                "Student Admin System",
                JOptionPane.QUESTION_MESSAGE);
    };
    
    private static boolean controllerValidateStudentNameIsInFormat(String input){
        String regex = "^[a-zA-Z]+( [A-Za-z]+){1,2}$";
        
        Pattern pattern = Pattern.compile(regex);
        
        Matcher matcher = pattern.matcher(input);
        
        return matcher.matches();
    }
    
    private static boolean controllerValidateNameIsInFormat(String input){
        String regex = "^[a-zA-Z]$";
        
        Pattern pattern = Pattern.compile(regex);
        
        Matcher matcher = pattern.matcher(input);
        
        return matcher.matches();
    }
    
    private static void viewModuleNameIsInvalid(){
        JOptionPane.showMessageDialog(null, 
                "Module Name cannot start with a number. Please try again.",
                "Student Admin System",
                JOptionPane.ERROR_MESSAGE);
    }
    
    private static String controllerValidateStudentNameToAdd(){
        boolean quit = false;
        String studentName = "";
        while(!quit){
            studentName = viewGetStudentNameToAdd();
            
            if(studentName == null){
                break;
            }
            
            if(controllerValidateInputIsEmpty(studentName)){
                viewDisplayUserInputErrorIsEmpty();
                continue;
            }
            
            if(!controllerValidateStudentNameIsInFormat(studentName)){
                viewStudentNameIsInvalid();
                continue;
            }
            quit = true;
        }
        return studentName;
    }
    



        
    
    public String getAdminNumberForStudent(String name) {
    for (Student student : students) {
        if (student.getName().equals(name)) {
            return student.getAdminNumber();
        }
    }
    return null; // Return null if student with given name is not found
}

    private static String controllerValidateStudentAdminNum(){
        boolean quit = false;
        String studentAdminNum = "";
        while(!quit){
            studentAdminNum = viewGetStudentAdminNumToAdd();
            if(studentAdminNum == null){
                break;
            }
            
            if (checkAdminNumberExists(studentAdminNum)) {
                viewDisplayAdmNumExist();
                continue;
            }
            
            
            if(controllerValidateInputIsEmpty(studentAdminNum)){
                viewDisplayUserInputErrorIsEmpty();
                continue;
            }

            if(!controllerValidateStudentAdminNumberIsInFormat(studentAdminNum)){
                viewAdmNumIsInvalid();
                continue;
            }
           
            
            // more validations needed
            quit = true;
        }
        return studentAdminNum;
    }
    
    private static String controllerValidateStudentAdminNumForDelete(){
        boolean quit = false;
        String studentAdminNum = "";
        while(!quit){
            studentAdminNum = viewGetStudentAdminNumToAdd();
            if(studentAdminNum == null){
                break;
            }

            if(controllerValidateInputIsEmpty(studentAdminNum)){
                viewDisplayUserInputErrorIsEmpty();
                continue;
            }

            if(!controllerValidateStudentAdminNumberIsInFormat(studentAdminNum)){
                viewAdmNumIsInvalid();
                continue;
            }

            // more validations needed
            quit = true;
        }
        return studentAdminNum;
    }
    
    private static String controllerValidateClassNumToAdd(){
        boolean quit = false;
        String classNum = "";
        while(!quit){
            classNum = viewGetStudentClassToAdd();
            if(classNum == null){
                break;
            }
            
            if(controllerValidateInputIsEmpty(classNum)){
                viewDisplayUserInputErrorIsEmpty();
                continue;
            }
            
            if(!controllerValidateStudentClassIsInFormat(classNum)){
                viewDisplayStudentClassNotInFormat();
                continue;
            }
            
            // more validations needed
            quit = true;
        }
        
        
        return classNum;
    }
    
    private static Integer controllerValidateModuleNumToAdd(){
        boolean quit = false;
        String strNumOfModules = "";
        Integer numOfModules = 0;
        while(!quit){
            strNumOfModules = viewGetNumOfModulesToAdd();
            
            if(strNumOfModules == null){
                break;
            }
            
            if(controllerValidateInputIsEmpty(strNumOfModules)){
                viewDisplayUserInputErrorIsEmpty();
                continue;
            }
            
            numOfModules = controllerValidateInputIsInteger(strNumOfModules);
            if(numOfModules == null){
                viewDisplayUserInputErrorNotInteger();
                continue;
            }
            
            // ask if the user really want to add more than 10 modules
            if(controllerCheckModuleNumInRange(numOfModules)){
                int reply = 0;
                reply = viewGetConfirmationForModuleNum(numOfModules);
                if(reply == JOptionPane.YES_OPTION){
                    //quit = true;
                }else{
                    continue;
                }
            }
            
            
            quit = true;
        }
        return numOfModules;
    }
    
    private static String controllerValidateModuleCodeToAdd(Integer i){
        boolean quit = false;
        String moduleCode = "";
        
        while(!quit){
            moduleCode = viewGetModuleCodeToAdd(i);
            if(moduleCode == null){
                break;
            }
            if(controllerValidateInputIsEmpty(moduleCode)){
                viewDisplayUserInputErrorIsEmpty();
                continue;
            }
            
            if(!controllerValidateModuleCodeIsInFormat(moduleCode)){
                viewModuleCodeNotInFormat();
                continue;
            }
            
            quit = true;
        }
        return moduleCode;
    }
    
    private static String controllerValidateModuleNameToAdd(Integer numOfModules){
        boolean quit = false;
        String moduleName = "";
        
        while(!quit){
            moduleName = viewGetModuleNameToAdd(numOfModules);
            if(moduleName == null){
                break;
            }
            if(controllerValidateInputIsEmpty(moduleName)){
                viewDisplayUserInputErrorIsEmpty();
                continue;
            }
            
            if(!controllerValidateModuleNameIsInFormat(moduleName)){
                viewModuleNameIsInvalid();
                continue;
            }
            
            quit = true;
        }
        return moduleName;
    }
    
    private static Integer controllerValidateCreditUnitToAdd(Integer numOfModules){
        boolean quit = false;
        String strCreditUnit = "";
        Integer creditUnit = 0;
        while(!quit){
            strCreditUnit = viewGetModuleCreditUnitToAdd(numOfModules);
            
            if(strCreditUnit == null){
                break;
            }
            
            if(controllerValidateInputIsEmpty(strCreditUnit)){
                viewDisplayUserInputErrorIsEmpty();
                continue;
            }
            
            creditUnit = controllerValidateInputIsInteger(strCreditUnit);
            if(creditUnit == null){
                viewDisplayUserInputErrorNotInteger();
                continue;
            }
            
            if(!controllerValidateCreditUnitInRange(creditUnit)){
                viewDisplayCreditUnitIsNotInRange();
                continue;
            }
            // more validations needed
            quit = true;
        }
        return creditUnit;
    }
    
    private static Integer controllerValidateModuleMarksToAdd(Integer numOfModules){
        boolean quit = false;
        String strModuleMarks = "";
        Integer moduleMarks = 0;
        while(!quit){
            strModuleMarks = viewGetModuleMarksToAdd(numOfModules);
            if(strModuleMarks == null){
                break;
            }
            
            if(controllerValidateInputIsEmpty(strModuleMarks)){
                viewDisplayUserInputErrorIsEmpty();
                continue;
            }
            
            moduleMarks = controllerValidateInputIsInteger(strModuleMarks);
            if(moduleMarks == null){
                viewDisplayUserInputErrorNotInteger();
                continue;
            }
            
            if(!controllerValidateModuleMarksInRange(moduleMarks)){
                viewDisplayModuleMarksIsNotInRange();
                continue;
            }
            quit = true;
        }
        return moduleMarks;
    }

//    private static Module controllerAddModuleForNewStudent(int moduleNumber, String modCode, String modName, int credit, int marks) {
//        return new Module(modCode, modName, credit, marks);
//    }
    
    private boolean isStudentInSystem(Student studentToCheck) {
        
        for (Student student : students) {
            
            if (student.getAdminNumber().equals(studentToCheck.getAdminNumber())) {
                return true; // Student found
            }
        }
        return false; // Student not found
    }
    
    private static void controllerAddNewStud(){
        
        String name = "";
        String admNum = "";
        String classNum = "";
        Integer numOfModules = 0;
        ArrayList<Module> modules = new ArrayList<>();
        double gpa = Student.calculateGPA(modules);
        String moduleCode = "";
        String moduleName = "";
        Integer creditUnits = 0;
        Integer moduleMarks = 0;
        boolean quit = false;
        while(!quit){
        
            name = controllerValidateStudentNameToAdd();
            if (name == null) {
                return;
            }
            admNum = controllerValidateStudentAdminNum();
            if (admNum == null) {
                return;
            }
            classNum = controllerValidateClassNumToAdd();
            if (classNum == null) {
                return;
            }
            numOfModules = controllerValidateModuleNumToAdd();
            if (numOfModules == null) {
                return;
            }

            for(int i = 0; i < numOfModules; i++){
                moduleCode = controllerValidateModuleCodeToAdd(i+1);
               if (moduleCode == null) {
                    return;
                }

                moduleName = controllerValidateModuleNameToAdd(i+1);

                if (moduleName == null) {
                    return;
                }
                creditUnits = controllerValidateCreditUnitToAdd(i+1);

                if (creditUnits == null) {
                    return;
                }
                moduleMarks = controllerValidateModuleMarksToAdd(i+1);

                if (moduleMarks == null) {
                    return;
                }
                modules.add(new Module(moduleCode, moduleName, creditUnits, moduleMarks));

            }
            Student newStudent = new Student(name, admNum, classNum, modules, Student.calculateGPA(modules));
            studentManagement.addStudent(newStudent);


            // check if the student is added
            if (studentManagement.isStudentInSystem(newStudent)) {
                System.out.println("New student added successfully: " + newStudent.getName());
            } else {
                System.out.println("Failed to add new student: " + newStudent.getName());
            }
            newStudent.getStudentInfo();
            System.out.println("New student added successfully: " + newStudent.getName());

            System.out.println(students.size());  
            quit = true;
        }
    }
    
    //---------------------------------------------------------------------
    // 2. Delete Student
    //---------------------------------------------------------------------
    
    // delete status
    private static void viewDeletedStatus(){
        JOptionPane.showMessageDialog(null,
                "Student Deleted",
                "Student Admin System",
                JOptionPane.INFORMATION_MESSAGE);
    };
    
    private static void viewStudentNotFoundStatus(){
        JOptionPane.showMessageDialog(null,
                "Student not found!",
                "Student Admin System",
                JOptionPane.INFORMATION_MESSAGE);
    };
    
    private static void deleteStudentByAdmNum() {
        // get the admin num
        final String studentAdmNum = controllerValidateStudentAdminNumForDelete();
        if(studentAdmNum == null){
            return;
        }
        // check the students from the instance
        //StudentManagement smt = new StudentManagement();
        
        ArrayList<Student> studentsList = getAllStudents();

        // Check if student with given admin number exists and remove
        boolean removed = studentsList.removeIf(student -> student.getAdminNumber().equalsIgnoreCase(studentAdmNum));

        // if removed, removed = true, shows deleted status
        if (removed) {
            viewDeletedStatus();
        } else {
            viewStudentNotFoundStatus();
        }
    }
    
    //---------------------------------------------------------------------
    // 3. Add New Module For Student
    //---------------------------------------------------------------------
    
    // asking admin num is shared with adding
    //---------------------------------------------------------------------
    private static boolean checkAdminNumberExists(String adminNumber) {
        ArrayList <Student> students = getAllStudents();

        for(Student student : students){
            if(student.getAdminNumber().equals(adminNumber))
            return true;
        }
        return false;
    }
    
    private static boolean checkModuleCodeExists(String moduleCode) {
        ArrayList <Student> students = getAllStudents();

        for(Student student : students){
            for(Module module: student.getModules()){
                if(module.getModuleCode().equals(moduleCode))
                    return true;
            }
        }
        return false;
    }
    
    private static void viewDisplayModuleCodeExist(){
        JOptionPane.showMessageDialog(null, """
                                            Module Code Exists. Please add a unique module code. 
                                            Thank you.""",
                "Student Admin System",
                JOptionPane.ERROR_MESSAGE);
    }

    
    
    private static String viewGetAdminNumber(){
        return JOptionPane.showInputDialog(null,
                "Enter Student Admin Number: ",
                "Student Admin System",
                JOptionPane.QUESTION_MESSAGE);
                
    }
    private static void viewDisplayAdmNumIsNotFound(){
        JOptionPane.showMessageDialog(null,
                "Admin Number Not Found. Student does not exist.",
                "Student Admin System",
                JOptionPane.ERROR_MESSAGE);   
    }
    
    private static void viewDisplayAdmNumExist(){
        JOptionPane.showMessageDialog(null, """
                                            Admin Number Exists. Please add a unique number. 
                                            Thank you.""",
                "Student Admin System",
                JOptionPane.ERROR_MESSAGE);
    }
    
    private static void viewAdmNumIsInvalid(){
        String dialogMessage = "Input is not in format. Please type in the correct format. (e.g. p2345678)";
        JOptionPane.showMessageDialog(null, 
                dialogMessage, 
                "Student Admin System",
                JOptionPane.ERROR_MESSAGE);
    }
    
    // small letter "p" and 7 numbers
    private static boolean controllerValidateStudentAdminNumberIsInFormat(String input){
        String regex = "^p\\d{7}$";
        
        Pattern pattern = Pattern.compile(regex);
        
        Matcher matcher = pattern.matcher(input);
        
        return matcher.matches();
    }

    

    //---------------------------------------------------------------------

    private static String viewGetModuleCode(){
        return JOptionPane.showInputDialog(null,
                "Enter Module Code for module: ",
                "Student Admin System",
                JOptionPane.QUESTION_MESSAGE);
    }
    
    private static void viewModuleCodeIsInvalid(){
        String dialogMessage = "Input is not in format. Please type in the correct format. (e.g. ST0506)";
        JOptionPane.showMessageDialog(null, 
                dialogMessage, 
                "Student Admin System",
                JOptionPane.ERROR_MESSAGE);
    }
    
    private static void viewModuleCodeExist(String moduleCode){
        String dialogMessage = moduleCode + " is already in the module list. Please try a unique module code";
        JOptionPane.showMessageDialog(null, 
                dialogMessage, 
                "Student Admin System",
                JOptionPane.ERROR_MESSAGE);
    }
    
    
    private static boolean checkModuleExists(String moduleCode){
        boolean foundModule = false;
        Student student = new Student();
        
        for(Student s: students){
            
            if (s.getAdminNumber().equals(moduleCode)) {
                    student = s;
                    foundModule = true;
                    break;
                }
        }
        return foundModule;
    }
    
    
    private static String controllerValidateModuleCode(){
        String moduleCode = "";
        boolean quit = false;
        
        while(!quit){
            moduleCode = viewGetModuleCode();
            
            if(moduleCode == null){
                break;
            }
            
            if(controllerValidateInputIsEmpty(moduleCode)){
                viewDisplayUserInputErrorIsEmpty();
                continue;
            }
            
            if(checkModuleCodeExists(moduleCode)){
                viewDisplayModuleCodeExist();
                continue;
            }
            
            if(!controllerValidateModuleCodeIsInFormat(moduleCode)){
                viewModuleCodeIsInvalid();
                continue;
            }
            
            
            
            quit = true;
        }
        
        return moduleCode; 
    }
    
    //---------------------------------------------------------------------
    
    private static String viewGetModuleName(){
        return JOptionPane.showInputDialog(null,
                "Enter Module Name for module: ",
                "Student Admin System",
                JOptionPane.QUESTION_MESSAGE);
    }
    
    private static void viewStudentNameIsInvalid(){
        String dialogMessage = "Please enter the full name. Try again.";
        JOptionPane.showMessageDialog(null, 
                dialogMessage, 
                "Student Admin System",
                JOptionPane.ERROR_MESSAGE);
    }
    
    private static boolean controllerValidateModuleNameIsInFormat(String input){
        String regex = "^[a-zA-Z]{0,10}";
        
        Pattern pattern = Pattern.compile(regex);
        
        Matcher matcher = pattern.matcher(input);
        
        return matcher.matches();
    }
    

    
    private static String controllerValidateModuleName(){
        String moduleName = "";
        boolean quit = false;
        
        while(!quit){
            moduleName = viewGetModuleName();
            if(moduleName == null){
                break;
            }
            
            if(controllerValidateInputIsEmpty(moduleName)){
                viewDisplayUserInputErrorIsEmpty();
                continue;
            }
            
            if(!controllerValidateModuleNameIsInFormat(moduleName)){
                viewStudentNameIsInvalid();
                continue;
            }
            quit = true;
        }
        
        return moduleName; 
    }
    
    //---------------------------------------------------------------------
    private static String viewGetModuleCreditUnits(){
        return JOptionPane.showInputDialog(null,
                "Enter Credit Unit for module: ",
                "Student Admin System",
                JOptionPane.QUESTION_MESSAGE);
    }
    
    private static void viewDisplayCreditUnitIsNotInRange(){
        JOptionPane.showMessageDialog(null, 
                "Credit Units can only be between 1 to 10.",
                "Student Admin System",
                JOptionPane.ERROR_MESSAGE);
    }
    
    private static boolean controllerValidateCreditUnitInRange(int input){
        return input >= 1 && input <= 10;
    }
    
    private static Integer controllerValidateCreditUnit(){
        String strCreditUnit = "";
        Integer creditUnit = 0;
        boolean quit = false;
        
        while(!quit){
            strCreditUnit = viewGetModuleCreditUnits();
            
            if(strCreditUnit == null){
                break;
            }
            
            if(controllerValidateInputIsEmpty(strCreditUnit)){
                viewDisplayUserInputErrorIsEmpty();
                continue;
            }
            
            creditUnit = controllerValidateInputIsInteger(strCreditUnit);
            if(creditUnit == null){
                viewDisplayUserInputErrorNotInteger();
                continue;
            }
            
            
            if(!controllerValidateCreditUnitInRange(creditUnit)){
                viewDisplayCreditUnitIsNotInRange();
                continue;
            }
            
            quit = true;
        }
        
        return creditUnit; 
    }
    
    //---------------------------------------------------------------------
    private static String viewGetModuleMarks(){
        return JOptionPane.showInputDialog(null,
                "Enter Module Marks for module: ",
                "Student Admin System",
                JOptionPane.QUESTION_MESSAGE);
    }
    
    private static void viewDisplayModuleMarksIsNotInRange(){
        JOptionPane.showMessageDialog(null, 
                "Module Marks can only be between 1 to 100.",
                "Student Admin System",
                JOptionPane.ERROR_MESSAGE);
    }
    
    private static boolean controllerValidateModuleMarksInRange(Integer input){
        return input >= 1 && input <= 100;
    }
    
    private static Integer controllerValidateModuleMarks(){
        String strModuleMarks = "";
        Integer moduleMarks = 0;
        boolean quit = false;
        
        while(!quit){
            strModuleMarks = viewGetModuleMarks();
            
            if(strModuleMarks == null){
                break;
            }
            
            if(controllerValidateInputIsEmpty(strModuleMarks)){
                viewDisplayUserInputErrorIsEmpty();
                continue;
            }
            
            moduleMarks = controllerValidateInputIsInteger(strModuleMarks);
            if(moduleMarks == null){
                viewDisplayUserInputErrorNotInteger();
                continue;
            }
            
            if(!controllerValidateModuleMarksInRange(moduleMarks)){
                viewDisplayModuleMarksIsNotInRange();
                continue;
            }
            
            quit = true;
        }
        
        return moduleMarks; 
    }
    
    private static String viewGetLoopModules(){
        return JOptionPane.showInputDialog(null,
                    "Do you want to add more modules?(yes/no): ",
                    "Student Admin System",
                    JOptionPane.QUESTION_MESSAGE);
    }
    
    private static void viewDisplayModuleAddedSuccessfulMessage(){
        JOptionPane.showMessageDialog(null, 
                "Module added successfully.",
                "Student Admin System",
                JOptionPane.INFORMATION_MESSAGE);
    }
    //---------------------------------------------------------------------

    private static void addModuleForStudent(ArrayList<Student> students) {
    String adminNumber;
    String moduleCode;
    String moduleName;
    Integer creditUnits;
    Integer moduleMarks;
    String userInput;
    boolean quit = false;
    while (!quit) {
        adminNumber = viewGetAdminNumber();
        
        if (adminNumber == null) {
            return;
        }

        Student student = null;
        for (Student s : students) {
            if (s.getAdminNumber().equals(adminNumber)) {
                student = s;
                break;
            }
        }

        if (student == null) {
            viewDisplayAdmNumIsNotFound();
            continue;
        }

        if (!controllerValidateStudentAdminNumberIsInFormat(adminNumber)) {
            viewAdmNumIsInvalid();
            continue;
        }

        do {
            moduleCode = controllerValidateModuleCode();
            if(moduleCode == null){
                return;
            }
            moduleName = controllerValidateModuleName();
            if(moduleName == null){
                return;
            }
            creditUnits = controllerValidateCreditUnit();
            if(creditUnits == null){
                return;
            }
            moduleMarks = controllerValidateModuleMarks();
            if(moduleMarks == null){
                return;
            }

            Module module = new Module(moduleCode, moduleName, creditUnits, moduleMarks);
            student.addModule(module); 

            userInput = viewGetLoopModules();
            if(userInput == null){
                return;
            }

            if (userInput.equalsIgnoreCase("no") || userInput.equalsIgnoreCase("n")) {
                break;
            }
        } while (true);
        quit = true;
        
    }
}


    
    //---------------------------------------------------------------------
    // 4. Program Terminate
    //---------------------------------------------------------------------
    
    protected static void viewProgramTerminate(){
        String dialogMessage = "Program terminated.\n Thank You!";
        JOptionPane.showMessageDialog(null,
                dialogMessage,
                "Message",
                JOptionPane.INFORMATION_MESSAGE);
    };
    
    //---------------------------------------------------------------------
    // USER IDENTITY 2 (STUDENT)
    //---------------------------------------------------------------------
    private static void handleStudentActions(){
        handleStudentActions(null);
    }
    
    protected static void handleStudentActions(Clip program_intro) {
        boolean studentQuit = false;
        while (!studentQuit) {
            String option = getStudentLogin();
             if (controllerValidateInputIsEmpty(option)) {
                viewDisplayUserInputErrorIsEmpty();
                continue;
            }
//             else if (option.equals("5")) {
//                JOptionPane.showMessageDialog(null, "Program terminaated.\nThank You!","Message",JOptionPane.INFORMATION_MESSAGE);
//                studentQuit = true;
////                System.exit(0);
//            } 
//             else if (option.equals("4")) {
//               return; 
//            }
             else {
                switch (option) {
                    case "1":
                        displayAllStudents();
                        break;
                    case "2":
                        searchStudentByClass();
                        break;
                    case "3":
                        searchStudentByName();
                        break;
                    case "4":
                        menuList();
                        studentQuit = true;
                        break;
                    case "5":
                        
                        JOptionPane.showMessageDialog(null, "Program terminated. Thank you!");
                        studentQuit = true;
                        System.exit(0);
                        break;
                    default:
                        JOptionPane.showMessageDialog(null, "Invalid option. Try again.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }

        }
    }
    
    private static String getStudentLogin(){
        String dialogMessageStudent = "Enter your option:\n\n1.Display all students\n2.Search student by class\n3.Search student by name\n4.Back to User Identification \n5.Quit";
        String dialogTitleStudent = "Student Enquiry System";
        return JOptionPane.showInputDialog(null,
                  dialogMessageStudent,
                  dialogTitleStudent,
                  JOptionPane.QUESTION_MESSAGE);
    }
    private static void displayAllStudents() {
        //StudentManagement smt = new StudentManagement();
        ArrayList <Student> students = getAllStudents();
        if (students == null || students.isEmpty()) {
            JOptionPane.showMessageDialog(null, "No students to display.", "All Student Report", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        
        

        StringBuilder result = new StringBuilder();
        int studentCount = 1;
        
        for (Student student : students) {
            result.append("Student ").append(studentCount).append(":\n")
                    .append("Name: ").append(student.getName()).append("\n")
                    .append("Admin Number: ").append(student.getAdminNumber()).append("\n")
                    .append("Class: ").append(student.getClassName()).append("\n")
                    .append("Modules Taken:\n");
            
            int moduleCount = 1;
            for (Module module : student.getModules()) {
                result.append(moduleCount).append(". ")
                        .append(module.getModuleCode()).append("/")
                        .append(module.getModuleName()).append("/")
                        .append(module.getCreditUnit()).append(": ")
                        .append(module.getGrade()).append("\n");
                moduleCount++;
            }
            result.append("GPA: ").append(String.format("%.2f", student.getGpa())).append("\n\n");
            studentCount++;
        }
        JOptionPane.showMessageDialog(null, result.toString(), "All Student Report", JOptionPane.INFORMATION_MESSAGE);
    }

    
    private static void searchStudentByClass() {
        boolean validClassName = false;
    String className = null;

    while (!validClassName) {
        String classNameInput = JOptionPane.showInputDialog(null, "Enter class name to search (Cancel to go back):");

        // Check if classNameInput is null (user cancelled)
        if (classNameInput == null) {
            return; // Return to handleStudentActions() without performing further operations
        }

        // Validate classNameInput against the specified format
        if (controllerValidateStudentClassIsInFormat(classNameInput)) {
            className = classNameInput.toUpperCase();
            validClassName = true; // Exit loop if valid format
        } else {
            JOptionPane.showMessageDialog(null, "Invalid class name format. Please enter in the format: _ _ _ / FT / 1A / 01\n- 2 to 4 uppercase letters\n- Followed by '/FT' or '/PT'\n- Followed by '/1' or '/2' and 'A' or 'B'\n- Ends with a number in the range 1-30", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
        int studentCount = 0;
        double totalGpa = 0.0;
        
        ArrayList<Student> students = getAllStudents();

        for (Student student : students) {
            if (student.getClassName().equals(className)) {
                studentCount++;
                totalGpa += student.getGpa();
            }
        }
        
        if (studentCount > 0){
          double averageGpa = totalGpa / studentCount;
          String result = "Number pf student(s) in "+ className + ": " + studentCount + "\n" 
                          + "Average GPA: "+ String.format("%.2f", averageGpa);
          JOptionPane.showMessageDialog(null, result, "Class Summary", JOptionPane.INFORMATION_MESSAGE);
        }else {
            JOptionPane.showMessageDialog(null, "No student found from class!", "Class Summary", JOptionPane.INFORMATION_MESSAGE);
        }
    }
    
    private static void searchStudentByName() {
        String name = JOptionPane.showInputDialog(null, "Enter student name to search:");
        StringBuilder result = new StringBuilder();
        boolean found = false;
          
        ArrayList<Student> students = getAllStudents();

        for (Student student : students) {
            if (student.getName().equalsIgnoreCase(name)) {
                result.append("Name: ").append(student.getName()).append("\n")
                        .append("Admin Number: ").append(student.getAdminNumber()).append("\n")
                        .append("Class: ").append(student.getClassName()).append("\n")
                        .append("Modules Taken:\n ");
                
                int moduleCount = 1;
                for (Module module : student.getModules()) {
                    result.append(moduleCount).append(". ")
                        .append(module.getModuleCode()).append("/")
                        .append(module.getModuleName()).append("/")
                        .append(module.getCreditUnit()).append(": ")
                        .append(module.getGrade()).append("\n");
                moduleCount++;
                }
                result.append("GPA: ").append(student.getGpa()).append("\n\n");
                found = true;
                
            }
        }
        if (found) {
            JOptionPane.showMessageDialog(null, result.toString(), "Student Info", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(null, "Cannot find the student \"" + name + "\"!!", "Info", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private static void menuList(){
     String strUserIdentityInput = "";
        Integer userInput = 0;
        String UserOption = "";
        boolean quit = false;
        
        // ---------------------------------------------------------
        // Get User Input
        // ---------------------------------------------------------
        while(!quit){
            strUserIdentityInput = viewGetUserIdentity();
            if(strUserIdentityInput == null){
                viewProgramTerminate();
                quit = true;
                return;
            }
            
            // [0] Validate if input is empty or blanks
            if (controllerValidateInputIsEmpty(strUserIdentityInput)){
                viewDisplayUserInputErrorIsEmpty();
                continue;
            }
            
            // [1] validation if input is an integer
            userInput = controllerValidateInputIsInteger(strUserIdentityInput);
            if (userInput == null){
                viewDisplayUserInputErrorNotInteger();
                continue; 
            }
            
            // [2] validate if input is in range
            if (!controllerValidateInputInRange(userInput)){
                viewDisplayUserInputErrorNotInRange();
                continue;
            }
            switch(userInput) {
            case 1:
                handleAdminActions();
                break;
            case 2:
                handleStudentActions();
                break;
            default:
                JOptionPane.showMessageDialog(null, "Invalid option. Try again.", "Error",JOptionPane.ERROR_MESSAGE);
                break;
            }
            
            quit = true;
            
        }
        
    }
    
    public ArrayList<Student> getStudents() {
        return students;
    }
    
    public void rankClassesByAverageGPA() {
        Map<String, Class> classMap = new HashMap<>();

        for (Student student : students) {
            String className = student.getClassName();
            classMap.putIfAbsent(className, new Class(className));
            classMap.get(className).addStudent(student);
        }

        ArrayList<Class> classes = new ArrayList<>(classMap.values());
        classes.sort((Class c1, Class c2) -> Double.compare(c2.calculateClassAverageGPA(), c1.calculateClassAverageGPA()));

        viewGetRankingOfClass(classes);
    }
    
    private static void viewGetRankingOfClass(ArrayList<Class> classes) {
        StringBuilder message = new StringBuilder();
        message.append("Ranking of classes based on average GPA:\n\n");
        
        for (int i = 0; i < classes.size(); i++) {
            message.append((i + 1)).append(". ").append(classes.get(i).getClassInfo()).append("\n");
        }

        JOptionPane.showMessageDialog(null, message.toString());
    }
}
