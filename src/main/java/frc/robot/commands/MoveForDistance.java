// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.util.sendable.Sendable;
import edu.wpi.first.util.sendable.SendableBuilder;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.constants.DrivetrainConstants;
import frc.robot.subsystems.Drivetrain;

/* You should consider using the more terse Command factories API instead https://docs.wpilib.org/en/stable/docs/software/commandbased/organizing-command-based.html#defining-commands */
public class MoveForDistance extends Command {
  private Drivetrain m_drivetrain;
  private double m_speed;
  private double m_targetTicks;
  private double m_error;
  private double m_initialEncoderTicks;

  private MoveForDistanceSendable m_sendable = new MoveForDistanceSendable();

  /** Creates a new MoveForDistance. */
  public MoveForDistance(Drivetrain drivetrain, double speed, double distanceInFeet) {
    m_drivetrain = drivetrain;
    m_speed = speed;
    m_targetTicks = distanceInFeet/(Math.PI*DrivetrainConstants.kWheelDiameterInFeet)*DrivetrainConstants.kTicksPerRevolution;

    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(m_drivetrain);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    m_error = m_targetTicks;
    m_initialEncoderTicks = m_drivetrain.getLeftEncoderTicks();
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    m_error = m_targetTicks - (m_drivetrain.getLeftEncoderTicks() - m_initialEncoderTicks);
    m_drivetrain.setLeftSpeed(m_speed);
    m_drivetrain.setRightSpeed(m_speed);
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
    return m_error <= 0;
  }

  public MoveForDistanceSendable getSendable() {
    return m_sendable;
  }

  private class MoveForDistanceSendable implements Sendable  {
    
    @Override
    public void initSendable(SendableBuilder builder){
      builder.setSmartDashboardType("Move For Distance");
      builder.addDoubleProperty("Speed", () -> m_speed, (double speed) -> m_speed = speed);
      builder.addDoubleProperty("Target ticks", () -> m_targetTicks, (double targetTicks) -> m_targetTicks = targetTicks);
      builder.addDoubleProperty("Current ticks", () -> m_drivetrain.getLeftEncoderTicks(), null);
      builder.addDoubleProperty("Initial ticks", () -> m_initialEncoderTicks, null);
      builder.addDoubleProperty("Error", () -> m_error, null);
    }
  }
}
