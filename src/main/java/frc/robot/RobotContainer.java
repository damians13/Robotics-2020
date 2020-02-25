/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.XboxController;
import frc.robot.commands.AutoPlan1;
import frc.robot.subsystems.DriveTrainMecanum;
import frc.robot.subsystems.Indexing;
import frc.robot.subsystems.Sensors;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.Shooter;
import frc.robot.subsystems.Climb;
import edu.wpi.first.wpilibj2.command.Command;

/**
 * This class is where the bulk of the robot should be declared.  Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls).  Instead, the structure of the robot
 * (including subsystems, commands, and button mappings) should be declared here.
 */
@SuppressWarnings("unused")
public class RobotContainer {

	public final DriveTrainMecanum driveTrain;
	public final Sensors sensors;
	public final Intake intake;
	public final Shooter shooter;
	public final Climb climb;
	public final Indexing indexing;
	
	public XboxController driverController;

	public RobotContainer() {
		// Configure controller(s)
		driverController = new XboxController(0);
		configureButtonBindings();

		// Initialize subsystems
		driveTrain = new DriveTrainMecanum(this);
		sensors = new Sensors(this);
		intake = new Intake();
		shooter = new Shooter();
		climb = new Climb();
		indexing = new Indexing();
	}

	/**
	 * Use this method to define your button->command mappings.  Buttons can be created by
	 * instantiating a {@link GenericHID} or one of its subclasses ({@link
	 * edu.wpi.first.wpilibj.Joystick} or {@link XboxController}), and then passing it to a
	 * {@link edu.wpi.first.wpilibj2.command.button.JoystickButton}.
	 */
	private void configureButtonBindings() {
		// Map the spinning of the shooter to the A button on the joystick
	}

	public double driverControllerAxisValue(int axis) {
		return driverController.getRawAxis(axis);
	}


	/**
	 * Use this to pass the autonomous command to the main {@link Robot} class.
	 *
	 * @return the command to run in autonomous
	 */
	public Command getAutonomousCommand() {
		return new AutoPlan1();
	}
}
