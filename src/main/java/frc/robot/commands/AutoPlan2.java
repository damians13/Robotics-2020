package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandGroupBase;
import frc.robot.commands.AutoSpin.Spinnables;
import frc.robot.commands.TogglePneumatic.Solenoids;

public class AutoPlan2 extends CommandGroupBase {

/**
 * addCommands(
            new DoNothing(50),
            new ParallelCommandGroup(
                new TogglePneumatic(Solenoids.CLIMB),
                new TogglePneumatic(Solenoids.INTAKE)),
            new SetWheelSpeed(-0.3, 0, 0),
            new DoNothing(50),
            new SetWheelSpeed(0, 0, 0),
            new AutoAimLimelightSimp(),
            new AutoSpin(Spinnables.SHOOTER),
            new DoNothing(50),
            new AutoSpin(Spinnables.INDEXING),
            new DoNothing(150),
            new ParallelCommandGroup(
                new AutoSpin(Spinnables.SHOOTER),
                new AutoSpin(Spinnables.INDEXING))
        );
 */

    public AutoPlan2() {
        sequence(new DoNothing(50),
                 new TogglePneumatic(Solenoids.CLIMB),
                 new TogglePneumatic(Solenoids.INTAKE),
                 new SetWheelSpeed(-0.3, 0, 0),
                 new DoNothing(50),
                 new SetWheelSpeed(0, 0, 0),
                 new AutoAimLimelightSimp(),
                 new AutoSpin(Spinnables.SHOOTER),
                 new DoNothing(50),
                 new AutoSpin(Spinnables.INDEXING),
                 new DoNothing(150),
                 new AutoSpin(Spinnables.SHOOTER),
                 new AutoSpin(Spinnables.INDEXING));
    }

	@Override
	public void addCommands(Command... commands) {
		// TODO Auto-generated method stub
		
	}
}