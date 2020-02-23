/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.PWMVictorSPX;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the TimedRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the build.gradle file in the
 * project.
 */
public class Robot extends TimedRobot {
  private Command m_autonomousCommand;
  private static final PWMVictorSPX victor = new PWMVictorSPX(5);
  

  private static Joystick kol;
  private static DoubleSolenoid solenoid;
  private static Compressor c;
  private static SpeedControllerGroup victor_R;
  private static SpeedControllerGroup victor_L;
  private static DifferentialDrive robotDrive;
  private static JoystickButton Frw;
  private static JoystickButton Rev;
  private static JoystickButton but1;
  private static JoystickButton but2;
  private static JoystickButton but3;
  /**
   * This function is run when the robot is first started up and should be used for any
   * initialization code.
   */
  @Override
  public void robotInit() {
    // Instantiate our RobotContainer.  This will perform all our button bindings, and put our
    // autonomous chooser on the dashboard.
    victor_R = new SpeedControllerGroup(new PWMVictorSPX(3), new PWMVictorSPX(0));
    victor_L = new SpeedControllerGroup(new PWMVictorSPX(2), new PWMVictorSPX(1));
    solenoid = new DoubleSolenoid(4, 5);
    robotDrive = new DifferentialDrive(victor_L, victor_R);
    c = new Compressor(14);
    kol = new Joystick(0);
     Frw = new JoystickButton(new Joystick(0), 7);
     Rev = new JoystickButton(new Joystick(0), 9);
     but1 = new JoystickButton(new Joystick(0), 8);
     but2 = new JoystickButton(new Joystick(0), 10);
     but3 = new JoystickButton(new Joystick(0), 12);
     

    c.setClosedLoopControl(false);
  }

  /**
   * This function is called every robot packet, no matter the mode. Use this for items like
   * diagnostics that you want ran during disabled, autonomous, teleoperated and test.
   *
   * <p>This runs after the mode specific periodic functions, but before
   * LiveWindow and SmartDashboard integrated updating.
   */
  @Override
  public void robotPeriodic() {
    // Runs the Scheduler.  This is responsible for polling buttons, adding newly-scheduled
    // commands, running already-scheduled commands, removing finished or interrupted commands,
    // and running subsystem periodic() methods.  This must be called from the robot's periodic
    // block in order for anything in the Command-based framework to work.
    CommandScheduler.getInstance().run(); 
    if(but1.get()) c.setClosedLoopControl(true);
    else if(but2.get()) solenoid.set(Value.kForward);
    else if(but3.get()) solenoid.set(Value.kReverse);
    else c.setClosedLoopControl(false);
     
    robotDrive.arcadeDrive(kol.getRawAxis(1), kol.getRawAxis(0));
  }

  /**
   * This function is called once each time the robot enters Disabled mode.
   */
  @Override
  public void disabledInit() {
  }

  @Override
  public void disabledPeriodic() {
  }

  /**
   * This autonomous runs the autonomous command selected by your {@link RobotContainer} class.
   */
  @Override
  public void autonomousInit() {

    // schedule the autonomous command (example)
    if (m_autonomousCommand != null) {
      m_autonomousCommand.schedule();
    }
  }

  /**
   * This function is called periodically during autonomous.
   */
  @Override
  public void autonomousPeriodic() {
  }

  @Override
  public void teleopInit() {
    // This makes sure that the autonomous stops running when
    // teleop starts running. If you want the autonomous to
    // continue until interrupted by another command, remove
    // this line or comment it out.
    if (m_autonomousCommand != null) {
      m_autonomousCommand.cancel();
    }
  }

  /**
   * This function is called periodically during operator control.
   */
  @Override
  public void teleopPeriodic() {
    if(Frw.get()) victor.set(0.4);
    else if (Rev.get())victor.set(-0.4);
    else victor.set(0);
  }

  @Override
  public void testInit() {
    // Cancels all running commands at the start of test mode.
    CommandScheduler.getInstance().cancelAll();
  }

  /**
   * This function is called periodically during test mode.
   */
  @Override
  public void testPeriodic() {
  }
}
