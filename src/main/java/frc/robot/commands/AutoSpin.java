package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Robot;

public class AutoSpin extends CommandBase {

    private boolean finished;

    public static enum Spinnables {
        SHOOTER, INTAKE, INDEXING
    }

    public AutoSpin(Spinnables spinnable) {
        this.finished = false;

        switch (spinnable) {
            case SHOOTER:
                if (Robot.Container.shooter.start()) {
                    System.out.println("Shooter started.");
                } else if (Robot.Container.shooter.stop()) {
                    System.out.println("Shooter stopped.");
                }
                this.finished = true;
                break;
            case INTAKE:
                if (Robot.Container.intake.start()) {
                    System.out.println("Intake started.");
                } else if (Robot.Container.intake.stop()) {
                    System.out.println("Intake stopped.");
                }
                this.finished = true;
                break;
            case INDEXING:
                if (Robot.Container.indexing.start()) {
                    System.out.println("Indexing started.");
                } else if (Robot.Container.indexing.stop()) {
                    System.out.println("Indexing stopped.");
                }
                this.finished = true;
                break;
        }
    }

    @Override
    public boolean isFinished() {
        return this.finished;
    }
}