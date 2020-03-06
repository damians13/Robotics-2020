/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.geometry.Translation2d;

/**
 * The Constants class provides a convenient place for teams to hold robot-wide numerical or boolean
 * constants.  This class should not be used for any other purpose.  All constants should be
 * declared globally (i.e. public static).  Do not put anything functional in this class.
 *
 * <p>It is advised to statically import this class (or one of its inner classes) wherever the
 * constants are needed, to reduce verbosity.
 */
public final class Constants {
    // For each section, create a new public class and add constant values inside that class.

    public static enum SolenoidStates {
        UP, DOWN, OFF;
    }

    public static class ControlConstants {
        // Control variables
        public static final double MAX_ROBOT_SPEED = 1;
        public static final double MIN_ROBOT_SPEED = -1;
        public static final double MAX_TURN_SPEED = 0.7;
        public static final double ROTATION_DEADBAND = 0.1;
        public static final double GYRO_DEADBAND = 0.005;
        public static final double JOY_DEADBAND = 0.15;
        public static final double GYRO_TOGGLE = 0.01;
        public static final double INTAKE_SPEED = 0.75;
        public static final double ROTATION_MULT = 0.65;
        //public static final double MAX_LINEAR_ACTUATOR_INPUT = 0.7;
        //public static final double MIN_LINEAR_ACTUATOR_INPUT = 0.5;

        public static final double BIG_WINCH_SPEED = 0.45;

        // Locations of mecanum wheels in relation to the center of the robot
        public static final Translation2d frontLeftLocation = new Translation2d(-0.3, 0.3);
        public static final Translation2d frontRightLocation = new Translation2d(0.3, 0.3);
        public static final Translation2d backLeftLocation = new Translation2d(-0.3, -0.3);
        public static final Translation2d backRightLocation = new Translation2d(0.3, -0.3);
    }

    public static class ControllerConstants {
        // XBox Controller
        public static final int Xbox_Left_Y_Axis = 1;
        public static final int Xbox_Right_Y_Axis = 5;
        public static final int Xbox_Left_X_Axis = 0;
        public static final int Xbox_Right_X_Axis = 4;
        public static final int Xbox_Right_Trigger = 3;
        public static final int Xbox_Left_Trigger = 2;
        public static final int Xbox_Right_Bumper = 6;
        public static final int Xbox_Left_Bumper = 5;
        public static final int Xbox_A_Button = 1;
        public static final int Xbox_B_Button = 2;
        public static final int Xbox_X_Button = 3;
        public static final int Xbox_Y_Button = 4;
        public static final int Xbox_Back_Button = 7;
        public static final int Xbox_Start_Button = 8;

        // Logitech Generic Controller
        public static final int Controller2_Right_Trigger = 8;
        public static final int Controller2_Left_Trigger = 7;
        public static final int Controller2_A_Button = 2;
        public static final int Controller2_B_Button = 3;
        public static final int Controller2_X_Button = 1;
        public static final int Controller2_Y_Button = 4;

        // Logitech Joystick
        public static final int Joy_Y_Axis = 1;
        public static final int Joy_Z_Axis = 2;
        public static final int Joy_X_Axis = 0;
        public static final int Joy_Throttle = 3;

        public static final int Joy_Button_10 = 10;
        public static final int Joy_Button_11 = 11;
        public static final int Joy_Button_12 = 12;
        public static final int Joy_Button_7 = 7;
        public static final int Joy_Button_3 = 3;
        public static final int Joy_Button_1 = 1;
    }

    public static class UnitConversions {
        public static final double IN_TO_CM = 2.54;
        public static final double CM_TO_IN = 1 / 2.54;
        public static final double FT_TO_IN = 12;
        public static final double IN_TO_FT = 1 / 12;
        public static final double FT_TO_M = 0.3048;
        public static final double M_TO_FT = 1 / 0.3048;
        public static final double M_TO_CM = 100;
        public static final double CM_TO_M = 1 / 100;
        public static final double IN_TO_M = 0.0254;
        public static final double M_TO_IN = 1 / 0.0254;

        public static final double ODOMETRY_TO_M = 2314;
        //public static final double ODOMETRY_TO_M = 1;
    }
}
