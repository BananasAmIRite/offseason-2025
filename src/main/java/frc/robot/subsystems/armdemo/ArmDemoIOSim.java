package frc.robot.subsystems.armdemo;

import com.revrobotics.AbsoluteEncoder;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.SparkLowLevel.MotorType;

import edu.wpi.first.math.system.plant.DCMotor;
import edu.wpi.first.math.system.plant.LinearSystemId;
import edu.wpi.first.wpilibj.simulation.DCMotorSim;

public class ArmDemoIOSim implements ArmDemoIO {
    private DCMotorSim simulatedMotor = new DCMotorSim(
        LinearSystemId.createDCMotorSystem(DCMotor.getNEO(2), 1, 1), 
        DCMotor.getNEO(2)
    ); // don't worry about this; we're just creating a simulated motor

    @Override
    public void setPower(double power) {
        simulatedMotor.setInputVoltage(power);
    }

    @Override
    public void updateInputs(ArmDemoInputs inputs) {
        inputs.angle = simulatedMotor.getAngularPositionRad(); 
        inputs.velocity = simulatedMotor.getAngularVelocityRadPerSec();
    } 
}
