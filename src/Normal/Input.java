/*
 * Created by oskar on 2016-11-17.
 * Handles all phidget inputs, ie sensors and digital inputs
 * for LCD things, look into the class Lcd
 */

package Normal;

import com.phidgets.*;
import java.io.IOException;

public class Input {

    private InterfaceKitPhidget ik;
    private int[] sensors = new int[8];
    private boolean[] digital = new boolean[8];

    public Input() {
        findPhidget();
        initiateSensors();
        createListeners();
    }

    private void initiateSensors() {
        try {

            sensors[InputConstants.P1_SLIDE]     = ik.getSensorValue(InputConstants.P1_SLIDE);
            sensors[InputConstants.P2_SLIDE]     = ik.getSensorValue(InputConstants.P2_SLIDE);
            sensors[InputConstants.P3_SLIDE]     = ik.getSensorValue(InputConstants.P3_SLIDE);
            sensors[InputConstants.P4_SLIDE]     = ik.getSensorValue(InputConstants.P4_SLIDE);


        } catch (PhidgetException e) {
            System.err.println("Some input to the kit is malfunctioning");
            e.printStackTrace();
        }
    }

    private void createListeners() {
        ik.addSensorChangeListener(se -> {
            //Insert your code here
            System.out.println("sensor[" + se.getIndex() + "] = " + se.getValue());
            sensors[se.getIndex()] = se.getValue();
        });



        ik.addInputChangeListener(ic -> {
            //Insert your code here
            System.out.println("digital[" + ic.getIndex() + "] = " + ic.getState());
            digital[ic.getIndex()] = ic.getState();
        });
    }

    public void close() {
        try {
            ik.close();
        } catch (PhidgetException e) {
            System.err.println("Exception when closing InterfaceKit");
            e.printStackTrace();
        }
        ik = null;
    }

    public static void main(String[] args) {

        Input input = new Input();

        System.out.println("Press enter to quit");
        try {
            System.in.read();
        } catch (IOException e) {
            e.printStackTrace();
        }
        input.close();
    }

    public boolean[] digitalData() { return digital; }

    public int[] sensorData() {
        return sensors;
    }

    public void findPhidget() {
        try {
            ik = new InterfaceKitPhidget();
            ik.openAny();
            System.out.println("Waiting for attachment of InterfaceKit...");
            ik.waitForAttachment();
        } catch (PhidgetException e) {
            System.err.println("Exception when creating InterfaceKit object");
            e.printStackTrace();
        }

        System.out.println("Found InterfaceKit");
    }
}

