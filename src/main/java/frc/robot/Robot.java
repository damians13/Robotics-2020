/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2019 FIRST. All Rights Reserved.												*/
/* Open Source Software - may be modified and shared by FRC teams. The code	 */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.																															 */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import edu.wpi.first.wpilibj2.command.ParallelRaceGroup;
import frc.robot.commands.AutoAimLimelightSimp;
import frc.robot.commands.DetectButtonPress;
import frc.robot.commands.TogglePneumatic;
import frc.robot.commands.DetectButtonPress.Controller;
import frc.robot.commands.TogglePneumatic.Solenoids;
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
		Container = new RobotContainer();
		compressor = new Compressor(13);
		compressor.setClosedLoopControl(true);

		Container.sensors.setLimelightPipeline(1);

		Container.shooter.setStartHeight();

		// Set pistons to desired state for start of match
		Container.intake.setPistonState(Constants.SolenoidStates.DOWN);
		Container.climb.setPistonState(Constants.SolenoidStates.UP);
		Container.colourWheel.setPistonState(Constants.SolenoidStates.DOWN);

		// Lifecam server
		CameraServer.getInstance().startAutomaticCapture(); // Should work
		//CameraServer.getInstance().putVideo("Lifecam", 640, 360); // Test if needed
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

		updateSmartDashboard();
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
	}

	/**
	 * This function is called periodically during operator control.
	 */
	@Override
	public void teleopPeriodic() {
		checkButtons();
		/*
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
		
		if (Container.driverController.getYButtonPressed()) {
			AutoAimLimelightSimp autoAim = new AutoAimLimelightSimp(false);
			autoAim.schedule();
		}

		// temp
		if (Container.driverController.getStartButtonPressed()) {
			Container.shooter.increase();
		}
		if (Container.driverController.getBackButtonPressed()) {
			Container.shooter.decrease();
		}
		Container.shooter.adjustShooter();

		if (Container.secondaryController.getAButtonPressed()) {
			Container.intake.setPistonState(Constants.SolenoidStates.UP);
		}
		if (Container.secondaryController.getBButtonPressed()) {
			Container.intake.setPistonState(Constants.SolenoidStates.DOWN);
		}

		//Container.colourWheel.setMotorSpeed(Container.secondaryControllerAxisValue(Constants.ControllerConstants.Xbox_Right_Y_Axis));
		*/
	}

	private void checkButtons() {
		/**
		 * Driver controller buttons
		 *
		 * Limelight auto aim -> Y
		 * Colour wheel piston toggle -> X
		 * Climb arm piston toggle -> A
		 * Intake piston toggle -> B
		 * Back -> Manual cancel limelight auto aim
		 */

		// Limelight auto aim (Cancelled with back button)
		if (Container.driverController.getYButtonPressed()) {
			new ParallelRaceGroup(new AutoAimLimelightSimp(false), new DetectButtonPress(Controller.DRIVER, Constants.ControllerConstants.Xbox_Back_Button)).schedule();
		}

		// Colour wheel piston toggle
		if (Container.driverController.getXButtonPressed()) {
			new TogglePneumatic(Solenoids.COLOUR_WHEEL);
		}

		// Climb arm piston toggle
		if (Container.driverController.getAButtonPressed()) {
			new TogglePneumatic(Solenoids.CLIMB);
		}

		// Intake piston toggle
		if (Container.driverController.getBButtonPressed()) {
			new TogglePneumatic(Solenoids.INTAKE);
		}

		/**
		 * Driver controller axis
		 *
		 * Cartesian movement -> Left joystick x & y axis
		 * Colour wheel -> Right joystick x axis
		 * Turn right -> Right trigger
		 * Turn left -> Left trigger
		 */

		/**
		 * Secondary controller buttons
		 *
		 * Big winch toggle -> Y
		 * Intake toggle -> B
		 * Indexing toggle -> X
		 * Shooter toggle -> A
		 * Start -> Shooter height +
		 * Back -> Shooter height -
		 */

		// Big winch toggle
		if (Container.secondaryController.getYButtonReleased()) {
		}

		/**
		 * Secondary controller axis
		 *
		 * Elevator arm -> Left joystick y axis
		 */
	}

	private double deadband(double input, double deadband) {
		if (Math.abs(input) <= deadband) {
			return 0;
		} else {
			return input;
		}
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

		SmartDashboard.putNumber("TX", Container.sensors.getLimelightTX());
		SmartDashboard.putBoolean("HasTarget", Container.sensors.limelightHasTarget());
		SmartDashboard.putString("FMS Desired Colour", getColourFromFMS());

		SmartDashboard.putNumber("Limelight horizontal length", Container.sensors.getLimelightTHor());
		SmartDashboard.putNumber("Limelight target y", Container.sensors.getLimelightTY());

		SmartDashboard.putNumber("Suggested shooter height", Container.shooter.tiltFormula());
	}

	private String getColourFromFMS() {
		String gameData = DriverStation.getInstance().getGameSpecificMessage();

		if(gameData.length() > 0) {
			switch (gameData.charAt(0)) {
				case 'B':
					return "Blue";
				case 'G':
					return "Green";
				case 'R':
					return "Red";
				case 'Y':
					return "Yellow";
				default:
					return "Corrupt colour";
			}
		} else {
			return "No colour yet.";
		}
	}
}
