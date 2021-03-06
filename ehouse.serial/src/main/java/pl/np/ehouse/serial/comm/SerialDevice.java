package pl.np.ehouse.serial.comm;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.TooManyListenersException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import gnu.io.SerialPort;
import gnu.io.SerialPortEventListener;

/**
 * @author Bartek
 */
@Service
public class SerialDevice {

	private final Logger log = LoggerFactory.getLogger(SerialDevice.class);

	private final SerialPort serialPort;

	@Autowired
	SerialDevice(SerialPort serialPort) {
		this.serialPort = serialPort;
	}

	/**
	 * @throws IOException -
	 */
	void startSend() throws IOException {
		log.debug("Start sending message");
		serialPort.setRTS(false);
		OutputStream output = serialPort.getOutputStream();
		output.write(0);
		output.flush();
	}

	/**
	 * 
	 */
	public void stopSend() {
		try {
			OutputStream output = serialPort.getOutputStream();
			output.write(0);
			output.flush();
			serialPort.setRTS(true);
		} catch (IOException e) {
			log.error("Error during serial device stoping", e);
		}
		log.debug("Stop sending message");
	}

	/**
	 * @return -
	 * @throws IOException
	 */
	public InputStream getInputStream() throws IOException {
		return serialPort.getInputStream();
	}

	/**
	 * @return -
	 * @throws IOException
	 */
	public OutputStream getOutputStream() throws IOException {
		return serialPort.getOutputStream();
	}

	/**
	 * @param listener -
	 * @throws TooManyListenersException -
	 */
	public void addEventListener(SerialPortEventListener listener) throws TooManyListenersException {
		serialPort.addEventListener(listener);
	}

}
