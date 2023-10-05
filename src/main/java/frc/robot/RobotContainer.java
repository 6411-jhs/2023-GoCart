// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

//Necessities
package frc.robot;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
//Controlling
import edu.wpi.first.wpilibj.XboxController;
//Subsystems
import frc.robot.subsystems.DriveTrain;
import frc.robot.subsystems.DriverControls;

/**
 * Main container of robot code; everything from commands, subsystems to
 * sensing. All functonality leads here to then be sent to the robot class.
 */
public class RobotContainer {
   public static XboxController m_xboxController;

   public static DriveTrain m_driveTrain;
   public static DriverControls m_driverControls;

   public RobotContainer() {
      m_xboxController = new XboxController(Constants.XBOX_USB_NUM);
      m_driveTrain = new DriveTrain();

      m_driverControls = new DriverControls(m_driveTrain, m_xboxController);

      CommandScheduler.getInstance().setDefaultCommand(m_driverControls, m_driverControls.selectedDriveMode());
   }
}
