/**
 * Created by oskar on 2016-11-17.
 */

import com.phidgets.*;

import java.io.IOException;

public class Input {

    private InterfaceKitPhidget ik;
    private int[] sensors = new int[7];

    public Input() {
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

        initiateListener();
        createListener();
    }

    private void initiateListener() {
        try {
            sensors[InputConstants.JOYX]     = ik.getSensorValue(InputConstants.JOYX);
            sensors[InputConstants.JOYY]     = ik.getSensorValue(InputConstants.JOYY);
            sensors[InputConstants.DIAL]     = ik.getSensorValue(InputConstants.DIAL);
            sensors[InputConstants.LIGHT]    = ik.getSensorValue(InputConstants.LIGHT);
            sensors[InputConstants.SLIDER]   = ik.getSensorValue(InputConstants.SLIDER);
        } catch (PhidgetException e) {
            System.err.println("Some input to the kit is malfunctioning");
            e.printStackTrace();
        }
    }

    private void createListener() {
        ik.addSensorChangeListener(se -> {
            //Insert your code here
            System.out.println("sensor[" + se.getIndex() + "] = " + se.getValue());
            sensors[se.getIndex()] = se.getValue();
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



    public int[] sensorData() {
        return sensors;
    }
}

