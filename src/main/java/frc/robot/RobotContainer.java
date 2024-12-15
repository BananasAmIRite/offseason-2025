// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.epilogue.Logged;
import edu.wpi.first.epilogue.Logged.Strategy;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import frc.robot.subsystems.arm.Arm;
import frc.robot.subsystems.armdemo.ArmDemo;
import frc.robot.subsystems.armdemo.ArmDemoIOReal;
import frc.robot.subsystems.armdemo.ArmDemoIOSim;
import frc.robot.subsystems.intake.Intake;

@Logged
public class RobotContainer {

  private final ArmDemo demoArm; 

  public RobotContainer() {

    demoArm = new ArmDemo(new ArmDemoIOReal()); // when you're controlling a real arm
    // demoArm = new ArmDemo(new ArmDemoIOSim()); // for a simulated arm

    configureBindings();
  }

  private void configureBindings() {
  }

  public Command getAutonomousCommand() {
    return Commands.none(); 
  }
}
