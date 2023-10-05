package frc.robot.subsystems;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

import edu.wpi.first.wpilibj.XboxController;
import frc.robot.Constants;

/**
 * Class containing all control modes for the robot. 
 * Currently only compatible for Xbox controller integration.
 * Use Constants.PRIMARY_JOYSTICK to set which joystick you want as the main functionality for some of the modes
 * note:  setting the primary joystick still needs testing
 */
public class DriverControls extends SubsystemBase {
   private XboxController xbox;
   private DriveTrain drive;

   private double driveSpeedCycle[] = Constants.SPEED_MULTIPLIER_SWITCHING;
   private int driveSpeedSelect = 1;

   public DriverControls(DriveTrain drive, XboxController xbox){
      this.xbox = xbox;
      this.drive = drive;
   }

   public CommandBase selectedDriveMode(){
      switch (Constants.DRIVE_MODE){
         case "arcadeSingleJoystick": return arcadeSingleJoystick();
         case "arcadeDuelJoystick": return arcadeDuelJoystick();
         case "tankDuelJoystick": return tankDuelJoystick();
         case "tankDuelTrigger": return tankDuelTrigger();
         default: return joystickTriggerHybrid();
      }
   }

   /** Uses the primary joystick for all direction and speed. */
   public CommandBase arcadeSingleJoystick(){
      return this.runOnce(() -> {
         drive.arcadeDrive(-xbox.getLeftY() * Constants.DRIVE_SPEED_MULTIPLER, -xbox.getLeftX() * Constants.DRIVE_SPEED_MULTIPLER);
         if (Constants.SPEED_SWITCHING_ENABLED) otherControls();
      });
   }
   /**
    * This arcade mode is a bit different from the original arcade mode. The y value of the primary stick controls
    * forward and back (arcade speed) and the x value fo the secondary stick controls direction (arcade turn)
    */
   public CommandBase arcadeDuelJoystick(){
      return this.runOnce(() -> {
         drive.arcadeDrive(-xbox.getLeftY() * Constants.DRIVE_SPEED_MULTIPLER, -xbox.getRightX() * Constants.DRIVE_SPEED_MULTIPLER);
         if (Constants.SPEED_SWITCHING_ENABLED) otherControls();
      });
   }
   /** Left stick controls left wheels and right stick controls right wheels. */
   public CommandBase tankDuelJoystick(){
      return this.runOnce(() -> {
         drive.tankDrive(-xbox.getLeftY() * Constants.DRIVE_SPEED_MULTIPLER, -xbox.getRightY() * Constants.DRIVE_SPEED_MULTIPLER);
         if (Constants.SPEED_SWITCHING_ENABLED) otherControls();
      });
   }

   /** Use the trigger instead of joysticks for tank driving. */
   public CommandBase tankDuelTrigger(){
      return this.runOnce(() -> {
         double triggerCalc = (xbox.getLeftTriggerAxis() + xbox.getRightTriggerAxis()) / 2;
         double turnCalc = xbox.getLeftTriggerAxis() - xbox.getRightTriggerAxis();
         drive.arcadeDrive(triggerCalc * Constants.DRIVE_SPEED_MULTIPLER, turnCalc * Constants.DRIVE_SPEED_MULTIPLER);
         if (Constants.SPEED_SWITCHING_ENABLED) otherControls();
      });
   }

   /** Uses primary stick X as the arcade turn and uses the right and left trigger for forward and back. */
   public CommandBase joystickTriggerHybrid(){
      return this.runOnce(() -> {
         drive.arcadeDrive(xbox.getRightTriggerAxis() * driveSpeedCycle[driveSpeedSelect] - (xbox.getLeftTriggerAxis() * driveSpeedCycle[driveSpeedSelect]), xbox.getLeftX() * driveSpeedCycle[driveSpeedSelect]);
         if (Constants.SPEED_SWITCHING_ENABLED) otherControls();
      });
   }

   private void otherControls(){
      if (Constants.SPEED_SWITCH_MODE == "Button"){
         if (xbox.getYButton()){
            driveSpeedSelect = 2;
         } else if (xbox.getXButton()){
            driveSpeedSelect = 1;
         } else if (xbox.getAButton()){
            driveSpeedSelect = 0;
         }
      } else if (Constants.SPEED_SWITCH_MODE == "Bumper"){
         if (xbox.getRightBumper()){
            driveSpeedSelect++;
            if (driveSpeedSelect > 2) driveSpeedSelect = 0;
         } else if (xbox.getLeftBumper()){
            driveSpeedSelect--;
            if (driveSpeedSelect < 0) driveSpeedSelect = 2;
         }
      }
   }
}
