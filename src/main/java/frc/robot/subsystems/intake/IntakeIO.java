package frc.robot.subsystems.intake;

import static edu.wpi.first.units.Units.Meters;
import static edu.wpi.first.units.Units.MetersPerSecond;

import edu.wpi.first.units.Distance;
import edu.wpi.first.units.Measure;
import edu.wpi.first.units.Velocity;
import edu.wpi.first.units.Voltage;


public interface IntakeIO {
    /**
     * In the IO system model, inputs are the data that is read from the robot's sensors at every iteration
     * outputs then are the computed power/voltage values for hardware to run based on robot code
     * The IO system facilitates simulation by offering an extra layer to swap out hardware for simulation systems
     */
    public class Inputs {
        public Measure<Velocity<Distance>> velocity;
        public boolean hasNote;
    }

    /*
     * logging is still up in the air, but this is also where inputs would be updated for the next robot loop
     */
    default void updateInputs(Inputs inputs) {}

    default void setVoltage(Measure<Voltage> voltage) {}
}