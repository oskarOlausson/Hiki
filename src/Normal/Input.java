/*
 * Created by oskar on 2016-11-17.
 * Handles all phidget inputs, ie sensors and digital inputs
 * for LCD things, look into the class Lcd
 */

package Normal;

import com.phidgets.*;

import java.awt.event.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Input implements KeyListener {

    private InterfaceKitPhidget ik;
    private int[] sensors = new int[8];
    private boolean[] digital = new boolean[8];
    private boolean hasKit;
    private List<Integer> keys = new ArrayList<>();
    private final int[] allowedKeys = {KeyEvent.VK_1, KeyEvent.VK_2, KeyEvent.VK_3, KeyEvent.VK_4};
    private boolean restart = false;
    private List<PlayerController> controllers = setController();

    public Input() {
        hasKit = true;
        findPhidget();
        initiateSensors();
        createListeners();
    }

    private void findPhidget() {
        try {
            ik = new InterfaceKitPhidget();
            ik.openAny();
            System.out.println("Waiting for attachment of InterfaceKit...");
            ik.waitForAttachment(200);
        } catch (PhidgetException e) {
            System.err.println("No InterfaceKit found");
            hasKit = false;
        }

        if (hasKit) System.out.println("Found InterfaceKit");
    }

    private void initiateSensors() {
        if (!hasKit) {
            sensors[InputConstants.P1_SLIDE]    = 100;
            sensors[InputConstants.P2_SLIDE]    = 300;
            sensors[InputConstants.P3_SLIDE]    = 560;
            sensors[InputConstants.P4_SLIDE]    = 900;
            return;
        }

        try {
            sensors[InputConstants.P1_SLIDE]    = ik.getSensorValue(InputConstants.P1_SLIDE);
            sensors[InputConstants.P2_SLIDE]    = ik.getSensorValue(InputConstants.P2_SLIDE);
            sensors[InputConstants.P3_SLIDE]    = ik.getSensorValue(InputConstants.P3_SLIDE);
            sensors[InputConstants.P4_SLIDE]    = ik.getSensorValue(InputConstants.P4_SLIDE);
        } catch (PhidgetException e) {
            System.err.println("Some input to the kit is malfunctioning");
            e.printStackTrace();
        }

        controllers.forEach(c -> c.update(sensorData()));
    }

    private void createListeners() {

        if (!hasKit) return;

        ik.addSensorChangeListener(se -> {
            sensors[se.getIndex()] = se.getValue();
            controllers.get(se.getIndex()).update(se.getValue());
        });

        ik.addInputChangeListener(ic -> {
            if (ic.getState()) {
                digital[ic.getIndex()] = true;
                controllers.get(ic.getIndex()).update(true);
            }

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

    public boolean[] digitalData() {
        if (hasKit) return digital;
        else {
            boolean[] arr = new boolean[4];
            for (int i = 0; i < 4; i++) {
                if (keys.contains(allowedKeys[i])) {
                    arr[i] = true;
                }
            }
            return arr;
        }
    }

    public int[] sensorData() {
        return sensors;
    }

    public void reset() {

        if (ik == null) {
            controllers.forEach(c -> c.update(false));
            return;
        }

        try {
            if (!ik.isAttached()) {
                controllers.forEach(c -> c.update(false));
                return;
            }
        } catch (PhidgetException e) {
            e.printStackTrace();
        }

        for (int i = 0; i < digital.length; i++) {
            try {
                digital[i] = ik.getInputState(i);
            } catch (PhidgetException e) {
                System.err.println("Could not get inputState of index: " + Integer.toString(i));
                digital[i] = false;
            }
        }

        controllers.forEach(c -> c.update(digitalData()));
    }

    public void restart() {
        keys = new ArrayList<>();
        initiateSensors();
        reset();
        restart = false;
    }

    @Override
    public void keyTyped(KeyEvent e) {
        //nothing
    }

    @Override
    public void keyPressed(KeyEvent e) {

        if (e.getKeyCode() == KeyEvent.VK_R) {
            restart = true;
        }
        else if (e.getKeyCode() == KeyEvent.VK_1) {
            controllers.get(0).update(true);
        }
        else if (e.getKeyCode() == KeyEvent.VK_2) {
            controllers.get(1).update(true);
        }
        else if (e.getKeyCode() == KeyEvent.VK_3) {
            controllers.get(2).update(true);
        }
        else if (e.getKeyCode() == KeyEvent.VK_4) {
            controllers.get(3).update(true);
        }


        else keys.add(e.getKeyCode());
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int key;
        for (int i = 0; i< keys.size(); i++){
            key = keys.get(i);
            if (key == e.getKeyCode()){
                keys.remove(i);
                i -= 1;
            }
        }
    }

    public boolean keyPressed(int value) {
        return keys.contains(value);
    }

    public boolean ifRestart() {
        return restart;
    }

    public List<PlayerController> setController() {
        List<PlayerController> list = new ArrayList<>();

        list.add(new PlayerController(new Lcd(141799, TextLCDPhidget.PHIDGET_TEXTLCD_SCREEN_4x20), 0, 0));
        list.add(new PlayerController(new Lcd(141627, TextLCDPhidget.PHIDGET_TEXTLCD_SCREEN_4x16), 1, 1));
        list.add(new PlayerController(new Lcd(141787, TextLCDPhidget.PHIDGET_TEXTLCD_SCREEN_4x20), 2, 2));
        list.add(new PlayerController(new Lcd(141568, TextLCDPhidget.PHIDGET_TEXTLCD_SCREEN_4x16), 3, 3));

        return list;
    }

    public PlayerController getController(int index) {
        return controllers.get(index);
    }

    public List<PlayerController> getControllers() {
        return controllers;
    }
}

