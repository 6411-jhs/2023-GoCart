package frc.robot.subsystems;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.motorcontrol.MotorControllerGroup;
import com.ctre.phoenix.motorcontrol.ControlMode;


import frc.robot.Constants;

/** Drive train container; holds all functionality for the robot drive train. */
public class DriveTrain extends SubsystemBase {
   //Motors
   private WPI_VictorSPX leftBackMotor;
   private WPI_VictorSPX rightFrontMotor;
   private WPI_VictorSPX rightBackMotor;
   private WPI_VictorSPX leftFrontMotor;
   //Grouping the motors
   private MotorControllerGroup rightMotors;
   private MotorControllerGroup leftMotors;
   private DifferentialDrive drive;

   /** Creates a new DriveTrain. */
   public DriveTrain() {
      //Defines the individual motors
      leftFrontMotor = new WPI_VictorSPX(Constants.FRONT_LEFT_DRIVE_MOTOR_ID);
      rightFrontMotor = new WPI_VictorSPX(Constants.FRONT_RIGHT_DRIVE_MOTOR_ID);
      leftBackMotor = new WPI_VictorSPX(Constants.BACK_LEFT_DRIVE_MOTOR_ID);
      rightBackMotor = new WPI_VictorSPX(Constants.BACK_RIGHT_DRIVE_MOTOR_ID);
      //Groups the motors
      rightMotors = new MotorControllerGroup(rightFrontMotor, rightBackMotor);
      leftMotors = new MotorControllerGroup(leftBackMotor, leftFrontMotor);
      rightMotors.setInverted(true);
      //Creates a drive train from the motor groups
      drive = new DifferentialDrive(rightMotors, leftMotors);
   }

   //EXTRA DRIVE CONTROL
   /** Set right side motor speed */
   public void setRightMotors(double speed) {
      rightFrontMotor.set(ControlMode.PercentOutput, speed * Constants.DRIVE_SPEED_MULTIPLER);
      rightBackMotor.set(ControlMode.PercentOutput, speed * Constants.DRIVE_SPEED_MULTIPLER);
   }

   /** Set left side motor speed */
   public void setLeftMotors(double speed) {
      leftFrontMotor.set(ControlMode.PercentOutput, speed * Constants.DRIVE_SPEED_MULTIPLER);
      leftBackMotor.set(ControlMode.PercentOutput, speed * Constants.DRIVE_SPEED_MULTIPLER);
   }

   //DRIVE METHODS
   /** Uses DifferentialDrive to create arcade drive */
   public void arcadeDrive(double speed, double turn) {
      drive.arcadeDrive(speed, turn);
   }
   /** Uses DifferentialDrive to create tank drive */
   public void tankDrive(double left, double right) {
      drive.tankDrive(left, right);
   }
}
