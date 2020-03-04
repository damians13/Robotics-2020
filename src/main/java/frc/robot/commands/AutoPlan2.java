package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.ParallelRaceGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.commands.AutoSpin.Spinnables;
import frc.robot.commands.TogglePneumatic.Solenoids;

public class AutoPlan2 extends SequentialCommandGroup {

/**
 * 
 */

    public AutoPlan2() {
        addCommands(
            new DoNothing(50),
            new TogglePneumatic(Solenoids.CLIMB),
            new TogglePneumatic(Solenoids.INTAKE),
            new ParallelRaceGroup(
                new DoNothing(50),
                new SetWheelSpeed(0, -0.3, 0)),
            new ParallelRaceGroup(
                new DoNothing(1),
                new SetWheelSpeed(0, 0, 0)),
            new AutoAimLimelightSimp(true),
            new AutoSpin(Spinnables.SHOOTER),
            new DoNothing(50),
            new AutoSpin(Spinnables.INDEXING),
            new DoNothing(400),
            new AutoSpin(Spinnables.INDEXING),
            new AutoSpin(Spinnables.SHOOTER)
        );
    }
}