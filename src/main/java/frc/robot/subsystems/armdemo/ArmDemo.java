package frc.robot.subsystems.armdemo;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class ArmDemo extends SubsystemBase {

    private PIDController armController; 
    
    private ArmDemoIO io; 
    private ArmDemoInputs inputs; 

    public ArmDemo(ArmDemoIO io) {
        this.io = io; 
        this.inputs = new ArmDemoInputs();
    }

    public void periodic() {
        io.updateInputs(inputs);
    }

    public void goToAngle(double desiredAngle) {
        double currentAngle = this.inputs.angle; 
        double pidOutput = armController.calculate(currentAngle, desiredAngle);
        io.setPower(pidOutput); 
    }



    
}
