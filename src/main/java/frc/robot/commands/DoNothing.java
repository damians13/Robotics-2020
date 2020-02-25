package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;

public class DoNothing extends CommandBase {

    private int timesToSkip;
    private int current;

    /**
     * To skip a number of seconds, add multiples of 50
     */
    public DoNothing(int timesToSkip) {
        this.timesToSkip = timesToSkip;
        this.current = 0;
    }
    
    @Override
    public void execute() {
        if (this.current < this.timesToSkip) {
            this.current++;
        } else {
            this.end(false);
        }
    }
    
    @Override
    public void end(boolean interrupted) {
        System.out.println(current + "cycles skipped. (This is not an error.)");
    }

}