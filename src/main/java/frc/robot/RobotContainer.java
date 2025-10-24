// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.util.sendable.SendableRegistry;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.commands.ArcadeDrive;
import frc.robot.commands.ArcadeMotor;
import frc.robot.commands.MoveForDistance;
import frc.robot.commands.MoveForTime;
import frc.robot.constants.DrivetrainConstants;
import frc.robot.constants.IOConstants;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.Motor;

public class RobotContainer {
  private Joystick m_joystick = new Joystick(IOConstants.kJoystickPort);

  private Motor m_motor = new Motor();
  private ArcadeMotor m_arcadeMotor = new ArcadeMotor(m_motor, m_joystick);

  private Drivetrain m_drivetrain = new Drivetrain();
  private ArcadeDrive m_arcadeDrive = new ArcadeDrive(m_drivetrain, m_joystick);
  private MoveForTime m_moveForTime = new MoveForTime(m_drivetrain, DrivetrainConstants.kSpeed, DrivetrainConstants.kTimeInSeconds);
  private MoveForDistance m_moveForDistance = new MoveForDistance(m_drivetrain, DrivetrainConstants.kSpeed, DrivetrainConstants.kDistance);

  public RobotContainer() {
    m_motor.setDefaultCommand(m_arcadeMotor);
    m_drivetrain.setDefaultCommand(m_arcadeDrive);

    SendableRegistry.add(m_moveForDistance.getSendable(), "Move For Distance");
    Shuffleboard.getTab("SmartDashboard").add(m_moveForDistance.getSendable()).withWidget("Move For Distance");

    configureBindings();
  }

  private void configureBindings() {}

  public Command getAutonomousCommand() {
    return m_moveForDistance;
  }
}
