package frc.robot.subsystems.armdemo;

import com.revrobotics.AbsoluteEncoder;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.SparkLowLevel.MotorType;

public class ArmDemoIOReal implements ArmDemoIO {
    private SparkMax leftMotor = new SparkMax(0, MotorType.kBrushless);
    private SparkMax rightMotor = new SparkMax(1, MotorType.kBrushless);
    private AbsoluteEncoder absoluteEncoder = leftMotor.getAbsoluteEncoder(); 

    @Override
    public void setPower(double power) {
        leftMotor.setVoltage(power);
        rightMotor.setVoltage(power);
    }

    @Override
    public void updateInputs(ArmDemoInputs inputs) {
        inputs.angle = absoluteEncoder.getPosition(); 
        inputs.velocity = absoluteEncoder.getVelocity();
    } 
}
