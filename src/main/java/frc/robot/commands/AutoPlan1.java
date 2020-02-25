package frc.robot.commands;

import edu.wpi.first.wpilibj.geometry.Rotation2d;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.Constants;
import frc.robot.commands.AutoSpin.Spinnables;

public class AutoPlan1 extends SequentialCommandGroup {

    /**
    * Straight back 10 ft with intake spinning
    * Forward 5 ft
    * Turn off intake
    * Over 24 ft
    * Back 5 ft
    * AutoAimLimelight
    * Spin up shooter
    * Run indexing
    * Stop spinning shooter and indexing
    */

    public AutoPlan1() {
        addCommands(
            new DoNothing(100), // 2 seconds
            new AutoSpin(Spinnables.INTAKE),
            new AutoMove(10 * Constants.UnitConversions.IN_TO_CM * Constants.UnitConversions.FT_TO_IN, 0, Rotation2d.fromDegrees(0)),
            new DoNothing(25),
            new AutoMove(-5 * Constants.UnitConversions.IN_TO_CM * Constants.UnitConversions.FT_TO_IN, 0, Rotation2d.fromDegrees(90)),
            new AutoSpin(Spinnables.INTAKE),
            new AutoMove(10 * Constants.UnitConversions.IN_TO_CM * Constants.UnitConversions.FT_TO_IN, 0, Rotation2d.fromDegrees(0)),
            new AutoMove(0, 0, Rotation2d.fromDegrees(-90)),
            //new AutoAimLimelight(),
            new AutoAimLimelightSimp(),
            new AutoSpin(Spinnables.SHOOTER),
            new AutoSpin(Spinnables.INDEXING),
            new DoNothing(150), // 3 seconds
            new AutoSpin(Spinnables.INDEXING),
            new AutoSpin(Spinnables.SHOOTER)
        );
    }
}