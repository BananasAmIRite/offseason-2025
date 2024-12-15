package frc.robot.subsystems.arm;

import edu.wpi.first.epilogue.Logged;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.system.plant.DCMotor;
import edu.wpi.first.math.system.plant.LinearSystemId;
import edu.wpi.first.wpilibj.simulation.DCMotorSim;
import edu.wpi.first.wpilibj.simulation.SingleJointedArmSim;
import frc.robot.subsystems.arm.Arm.ArmConfig;

@Logged
public class ArmIOSim implements ArmIO {

    public static final ArmConfig config = new ArmConfig(0.03, 0, 0, 0); 

    private SingleJointedArmSim pivot;

    public ArmIOSim() {
        pivot = new SingleJointedArmSim(
            DCMotor.getNEO(2), 
            Arm.GEARING,
            Arm.MOI,
            Arm.LENGTH_METERS, 
            -90, 
            Math.toRadians(90), 
            true, 
            0
        ); 
    }

    @Override
    public void updateInputs(ArmInputs inputs) {
        pivot.update(0.02);

        inputs.absoluteAngle = Rotation2d.fromRadians(pivot.getAngleRads());
        inputs.velocityMetersPerSecond = Math.toDegrees(pivot.getVelocityRadPerSec()); 
    }

    @Override
    public void setVoltage(double volts) {
        pivot.setInputVoltage(volts);
    }
}
