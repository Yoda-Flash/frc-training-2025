// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.constants.DrivetrainConstants;

public class Drivetrain extends SubsystemBase {
  private TalonSRX m_leftPrimary = new TalonSRX(DrivetrainConstants.kLeftPrimaryID);
  private VictorSPX m_leftSecondary = new VictorSPX(DrivetrainConstants.kLeftSecondaryID);
  private TalonSRX m_rightPrimary = new TalonSRX(DrivetrainConstants.kRightPrimaryID);
  private VictorSPX m_rightSecondary = new VictorSPX(DrivetrainConstants.kRightSecondaryID);

  /** Creates a new Drivetrain. */
  public Drivetrain() {
    m_leftSecondary.follow(m_leftPrimary);
    m_rightSecondary.follow(m_rightPrimary);

    m_rightPrimary.setInverted(true);
    m_rightSecondary.setInverted(true);

    m_leftPrimary.setNeutralMode(NeutralMode.Brake);
    m_leftSecondary.setNeutralMode(NeutralMode.Brake);
    m_rightPrimary.setNeutralMode(NeutralMode.Brake);
    m_rightSecondary.setNeutralMode(NeutralMode.Brake);
  }

  public void setLeftSpeed(double speed) {
    m_leftPrimary.set(ControlMode.PercentOutput, speed);
  }

  public void setRightSpeed(double speed) {
    m_rightPrimary.set(ControlMode.PercentOutput, speed);
  }

  public double getLeftSpeed() {
    return m_leftPrimary.getMotorOutputPercent();
  }

  public double getRightSpeed() {
    return m_rightPrimary.getMotorOutputPercent();
  }

  public double getLeftEncoderTicks() {
    return m_leftPrimary.getSelectedSensorPosition();
  }

  public double getRightEncoderTicks() {
    return m_rightPrimary.getSelectedSensorPosition();
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
    SmartDashboard.putNumber("Left Speed", getLeftSpeed());
    SmartDashboard.putNumber("Right Speed", getRightSpeed());
    SmartDashboard.putNumber("Ticks", getLeftEncoderTicks());
  }
}
