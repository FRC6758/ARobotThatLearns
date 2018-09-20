/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.team6758;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.Scanner;

import static java.lang.Thread.sleep;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the build.properties file in the
 * project.
 */
// If you rename or move this class, update the build.properties file in the project root
public class Robot extends IterativeRobot 
{
    private static final String fileName = "";
    private static final String DEFAULT_AUTO = "Default";
    private static final String CUSTOM_AUTO = "My Auto";
    private String autoSelected;
    private Joystick stick;

    private FileWriter writer;
    private PrintWriter fileOut;
    private Scanner inFile;
    private boolean check1;

    private SendableChooser<String> chooser = new SendableChooser<>();

    /**
     * This function is run when the robot is first started up and should be
     * used for any initialization code.
     */
    @Override
    public void robotInit()
    {

        stick = new Joystick(0);

        try {
            check1 = true;
            writer = new FileWriter(fileName);
            fileOut = new PrintWriter(writer);
        }catch(Exception e){
            check1 = false;
            System.out.println("FILE NOT SET. JOYSTICK RECORD WILL NOT WORK!");
        }

        chooser.addDefault("Default Auto", DEFAULT_AUTO);
        chooser.addObject("My Auto", CUSTOM_AUTO);
        SmartDashboard.putData("Auto choices", chooser);
    }

    /**
     * This autonomous (along with the chooser code above) shows how to select
     * between different autonomous modes using the dashboard. The sendable
     * chooser code works with the Java SmartDashboard. If you prefer the
     * LabVIEW Dashboard, remove all of the chooser code and uncomment the
     * getString line to get the auto name from the text box below the Gyro
     *
     * <p>You can add additional auto modes by adding additional comparisons to
     * the switch structure below with additional strings. If using the
     * SendableChooser make sure to add them to the chooser code above as well.
     */
    @Override
    public void autonomousInit() 
    {
        try{
            inFile = new Scanner(new FileReader(fileName));
        } catch (Exception e){
            System.out.println("Couldn't open file, reverting to other auton code.");
        }

        autoSelected = chooser.getSelected();
        // autoSelected = SmartDashboard.getString("Auto Selector",
        // defaultAuto);
        System.out.println("Auto selected: " + autoSelected);
    }

    /**
     * This function is called periodically during autonomous.
     */
    @Override
    public void autonomousPeriodic() 
    {
        switch (autoSelected) 
        {
            case CUSTOM_AUTO:
                // Put custom auto code here
                break;
            case DEFAULT_AUTO:
            default:
                // Put default auto code here
                break;
        }
    }

    /**
     * This function is called periodically during operator control.
     */
    @Override
    public void teleopPeriodic() 
    {
        //drive code will go here
        boolean run = true;
        //learning code will go here
        Thread learningThread = new Thread(new Runnable() {
            @Override
            public void run() {
                while(run) {
                    if (Thread.interrupted()) break;
                    if (check1) {
                        fileOut.print(stick.getY() + " " + stick.getTwist() + ",");
                    }
                }

            }
        });
        learningThread.start();
        Thread.sleep(2000);
    }

    /**
     * This function is called periodically during test mode.
     */
    @Override
    public void testPeriodic() 
    {
        
    }
}
