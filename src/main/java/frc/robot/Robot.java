/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2019 FIRST. All Rights Reserved.												*/
/* Open Source Software - may be modified and shared by FRC teams. The code	 */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.																															 */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import frc.robot.utilities.MiscUtils;

/**
 * The VM is configured to automatically run this class, and to call the functions corresponding to
 * each mode, as described in the TimedRobot documentation. If you change the name of this class or
 * the package after creating this project, you must also update the build.gradle file in the
 * project.
 */
public class Robot extends TimedRobot {
	private Command m_autonomousCommand;

	public static RobotContainer Container;
	private static Compressor compressor;

	/**
	 * This function is run when the robot is first started up and should be used for any
	 * initialization code.
	 */
	@Override
	public void robotInit() {
		// Instantiate our RobotContainer.	This will perform all our button bindings, and put our
		// autonomous chooser on the dashboard.
		Container = new RobotContainer();
		compressor = new Compressor(13);
		compressor.setClosedLoopControl(true);
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
		// Runs the Scheduler.	This is responsible for polling buttons, adding newly-scheduled
		// commands, running already-scheduled commands, removing finished or interrupted commands,
		// and running subsystem periodic() methods.	This must be called from the robot's periodic
		// block in order for anything in the Command-based framework to work.
		CommandScheduler.getInstance().run();
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
		m_autonomousCommand = Container.getAutonomousCommand();

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

		updateSmartDashboard();
	}

	/**
	 * This function is called periodically during operator control.
	 */
	@Override
	public void teleopPeriodic() {
		if (Container.driverController.getAButtonPressed()) {
			if (Container.shooter.start()) {
				System.out.println("Shooter started.");
			} else if (Container.shooter.stop()) {
				System.out.println("Shooter stopped.");
			}
		}
		
		if (Container.driverController.getBButtonPressed()) {
			if (Container.intake.start()) {
				System.out.println("Intake started.");
			} else if (Container.intake.stop()) {
				System.out.println("Intake stopped.");
			}
		}
		
		if (Container.driverController.getXButtonPressed()) {
			if (Container.indexing.start()) {
				System.out.println("Indexing started.");
			} else if (Container.indexing.stop()) {
				System.out.println("Indexing stopped.");
			}
		}

		compressor.start();
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

	private void updateSmartDashboard() {
		
		SmartDashboard.putNumber("ta", Container.sensors.getLimelightTA());
		SmartDashboard.putNumber("tx", Container.sensors.getLimelightTX());
		SmartDashboard.putNumber("ty", Container.sensors.getLimelightTY());
		SmartDashboard.putBoolean("Limelight has target", Container.sensors.limelightHasTarget());
		
		SmartDashboard.putNumber("Limelight Detected Area", MiscUtils.limelightPointsToDistance(Container.sensors.getLimelightCornersX(), Container.sensors.getLimelightCornersY()));

		//SmartDashboard.putNumber("RED", Container.sensors.getColourSensorRed());
		//SmartDashboard.putNumber("GREEN", Container.sensors.getColourSensorGreen());
		//SmartDashboard.putNumber("BLUE", Container.sensors.getColourSensorBlue());
	}
}
