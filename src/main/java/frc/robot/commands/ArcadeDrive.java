// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.constants.DrivetrainConstants;
import frc.robot.constants.IOConstants;
import frc.robot.subsystems.Drivetrain;

/* You should consider using the more terse Command factories API instead https://docs.wpilib.org/en/stable/docs/software/commandbased/organizing-command-based.html#defining-commands */
public class ArcadeDrive extends Command {
  private Drivetrain m_drivetrain;
  private Joystick m_joystick;

  private double m_speed;
  private double m_turn;
  private double m_left;
  private double m_right;

  /** Creates a new ArcadeDrive. */
  public ArcadeDrive(Drivetrain drivetrain, Joystick joystick) {
    m_drivetrain = drivetrain;
    m_joystick = joystick;

    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(m_drivetrain);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {}

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    m_speed = m_joystick.getRawAxis(IOConstants.kSpeedControlAxis);
    m_speed *= DrivetrainConstants.kSpeedMultiplier;
    m_turn = m_joystick.getRawAxis(IOConstants.kTurnControlAxis);
    m_turn *= DrivetrainConstants.kTurnMultiplier;

    m_left = m_speed + m_turn;
    m_right = m_speed - m_turn;

    m_drivetrain.setLeftSpeed(m_left);
    m_drivetrain.setRightSpeed(m_right);
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    m_drivetrain.setLeftSpeed(0);
    m_drivetrain.setRightSpeed(0);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
