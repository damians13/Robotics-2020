package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

public class GetLidarDistance extends SequentialCommandGroup {

    public GetLidarDistance() {
        addCommands(
            new StartMeasuringLidar(),
            new DoNothing(100),
            new MeasureLidarDistance(),
            new StopMeasuringLidar()
        );
    }
}