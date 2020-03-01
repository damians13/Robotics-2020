package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.commands.AutoSpin.Spinnables;
import frc.robot.commands.TogglePneumatic.Solenoids;

public class AutoPlan2 extends SequentialCommandGroup {

    public AutoPlan2() {
        addCommands(
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
    }
}