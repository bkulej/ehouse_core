package pl.np.ehouse.serial.network;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author Bartek
 */
class NetworkSenderTest {

    /**
     * @param args -
     */
    public static void main(String[] args) {
        try (Socket socket = new Socket("127.0.0.1", 4000);
             ObjectOutputStream output = new ObjectOutputStream(socket.getOutputStream());
             ObjectInputStream input = new ObjectInputStream(socket.getInputStream())) {
            prepareReceiver(input);
            sendMessages(output);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /*
     *
     */
    private static void prepareReceiver(ObjectInputStream input) {
        new Thread(() -> {
            while (true) {
                try {
                    System.out.println(new Date() + " received -> " + input.readObject());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    /*
     *
     */
    private static void sendMessages(ObjectOutputStream output) throws IOException, InterruptedException {
        List<Integer> list = prepareExploreMessage();
        for (int i = 0; i < 100; i++) {
            output.writeObject(list);
            output.flush();
            Thread.sleep(10000);
        }
    }

    /*
     *
     */
    private static List<Integer> prepareExploreMessage() {
        List<Integer> list = new ArrayList<>();
        list.add(0);
        String message = "00000000" + "01" + "03";
        for (byte data : message.getBytes()) {
            list.add((int) data);
        }
        return list;
    }

}
