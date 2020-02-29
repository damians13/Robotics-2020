package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Robot;

public class StartMeasuringLidar extends CommandBase {

    private boolean finished;

    @Override
    public void initialize() {
        this.finished = false;
    }

    @Override
    public void execute() {
        Robot.Container.sensors.startMeasuringLidar();
        this.finished = true;
    }

    @Override
    public boolean isFinished() {
        return this.finished;
    }
}