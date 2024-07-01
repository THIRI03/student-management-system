/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package jprg_ca1_assignment;


import java.io.File;
import java.io.IOException;
import javax.sound.sampled.*;

/**
 *
 * @author thiri
 */
public class StudentUser {
    
    public static void main(String [] args)throws UnsupportedAudioFileException, IOException, LineUnavailableException{
        File file = new File("programIntro.wav");
        AudioInputStream audioStream = AudioSystem.getAudioInputStream(file);
        Clip program_intro = AudioSystem.getClip();
        program_intro.open(audioStream);
        
        String strUserIdentity = "";
        Integer userIdentity = -1;
        
        String strUserOption = "";
        Integer userOption = 0;
        boolean quit = false;
        
        
        
        while(!quit){
            program_intro.start();
            strUserIdentity = StudentManagement.viewGetUserIdentity();
            
            if(strUserIdentity == null){
                StudentManagement.viewProgramTerminate();
                return;
            }
            
            if (StudentManagement.controllerValidateInputIsEmpty(strUserIdentity)){
                StudentManagement.viewDisplayUserInputErrorIsEmpty();
                continue;
            }
       
            
            userIdentity = StudentManagement.controllerValidateInputIsInteger(strUserIdentity);
            if(userIdentity == null){
                StudentManagement.viewDisplayUserInputErrorNotInteger();
                continue;
            }
            
            if(!StudentManagement.controllerValidateInputInRange(userIdentity)){
                StudentManagement.viewDisplayUserInputErrorNotInRange();
                continue;
            }
                     
            
            if(userIdentity.equals(1)){
                StudentManagement.handleAdminActions(program_intro);
                
            }
            
            if(userIdentity.equals(2)){
                StudentManagement.handleStudentActions(program_intro);
            }
            
            quit = true;
        }
        
    }
        
}
