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
    private boolean hasKit;

    public Input() {
        hasKit = true;
        findPhidget();
        if (hasKit) {
            initiateSensors();
            createListeners();
        }
    }

    private void findPhidget() {
        try {
            ik = new InterfaceKitPhidget();
            ik.openAny();
            System.out.println("Waiting for attachment of InterfaceKit...");
            ik.waitForAttachment(20);
        } catch (PhidgetException e) {
            System.err.println("No InterfaceKit found");
            hasKit = false;
        }

        if (hasKit) System.out.println("Found InterfaceKit");
    }

    private void initiateSensors() {
        if (!hasKit) return;

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

        if (!hasKit) return;

        ik.addSensorChangeListener(se -> {
            //Insert your code here
            System.out.println("sensor[" + se.getIndex() + "] = " + se.getValue());
            sensors[se.getIndex()] = se.getValue();
        });

        ik.addInputChangeListener(ic -> {
            //Insert your code here
            System.out.println("digital[" + ic.getIndex() + "] = " + ic.getState());
            if (ic.getState()) digital[ic.getIndex()] = true;
        });
    }

    public void close() {

        if (!hasKit) return;

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

    public void reset() {
        for (int i = 0; i < digital.length; i++) {
            try {
                digital[i] = ik.getInputState(i);
            } catch (PhidgetException e) {
                //System.err.println("Could not get inputState of index: " + Integer.toString(i));
                digital[i] = false;
            }
        }
    }
}

