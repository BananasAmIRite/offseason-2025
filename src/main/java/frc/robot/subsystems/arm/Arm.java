package frc.robot.subsystems.arm;

import java.util.function.DoubleSupplier;

import edu.wpi.first.epilogue.Logged;
import edu.wpi.first.math.controller.ArmFeedforward;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.wpilibj.RobotBase;
import edu.wpi.first.wpilibj.util.Color;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

@Logged
public class Arm extends SubsystemBase {

    // configuration data that are dependent on each arm implementation (eg. a simulated arm might have different PID values from a real arm)
    public record ArmConfig(double kP, double kI, double kD, double kG) {}    

    // Arm IO (Input-Output), handles input-output for any type of arm we want to control
    private ArmIO io; 
    private ArmInputs inputs; 

    private PIDController armController;
    private ArmFeedforward armFeedforward; 

    public static final double GEARING = 5 * 4 * 5; 
    public static final double MOI = 1.06; 
    public static final double LENGTH_METERS = 0.5588; 

    private final ArmVisualizer desiredVisualizer = new ArmVisualizer(Color.kRed); 
    private final ArmVisualizer measuredVisualizer = new ArmVisualizer(Color.kGreen); 

    private Rotation2d desiredPosition = new Rotation2d(); 

    public static Arm create() {
        return RobotBase.isReal() ? 
            new Arm(new ArmIOReal(), ArmIOReal.config) : 
            new Arm(new ArmIOSim(), ArmIOSim.config); 
    }

    
    public static Arm disable() {
        return new Arm(new ArmIOIdeal(), ArmIOIdeal.config); 
    }


    public Arm(ArmIO io, ArmConfig config) {
        this.io = io; 
        this.inputs = new ArmInputs();
        
        this.armController = new PIDController(config.kP, config.kI, config.kD); 
        this.armFeedforward = new ArmFeedforward(0, config.kG, 0); 
        
    }

    @Override
    public void periodic() {
        io.updateInputs(inputs);
        measuredVisualizer.update(inputs.absoluteAngle);
        desiredVisualizer.update(desiredPosition);
    }

    // TODO: I'm leaning towards not using the Trigger structure since we can wrap around it anytime we want? idk I prefer using raw values instead. 4/10 leaning towards not using Triggers. 
    public boolean atPosition() {
        return this.armController.atSetpoint(); 
    }

    public Command goToAngle(double targetDegrees) {
        return goToAngle(() -> targetDegrees); 
    }

    public Command goToAngle(DoubleSupplier angleSupp) {
        return run(() -> {
            this.desiredPosition = Rotation2d.fromDegrees(angleSupp.getAsDouble()); 
            double pidOutput = armController.calculate(inputs.absoluteAngle.getDegrees(), angleSupp.getAsDouble()) + this.armFeedforward.calculate(angleSupp.getAsDouble(), 0); 
            io.setVoltage(pidOutput);
        });


    }

    public Command runVolts(double volts) {
        return run(() -> {
            io.setVoltage(volts);
        });
    }

    public Command tune() {
        // TODO: redo this with the implementation-independent config
        // LoggedDashboardNumber kP = new LoggedDashboardNumber("Arm/kP", io.getConfig().kP()); 
        // LoggedDashboardNumber kI = new LoggedDashboardNumber("Arm/kI", io.getConfig().kI()); 
        // LoggedDashboardNumber kD = new LoggedDashboardNumber("Arm/kD", io.getConfig().kD());
        // LoggedDashboardNumber kG = new LoggedDashboardNumber("Arm/kG", io.getConfig().kG());
        // TODO: is this a "full test"?
        // return runOnce(() -> {
        //     armController.setPID(kP.get(), kI.get(), kD.get());
        //     armFeedforward = new ArmFeedforward(0, kG.get(), 0); 
        // })
        // .andThen(goToAngle(20).until(this::atPosition))
        // .andThen(goToAngle(80).until(this::atPosition))
        // .andThen(goToAngle(40).until(this::atPosition)); 
        return Commands.none(); 
    }

}
