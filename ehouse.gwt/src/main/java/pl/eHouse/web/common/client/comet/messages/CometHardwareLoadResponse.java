package pl.eHouse.web.common.client.comet.messages;

import pl.eHouse.web.common.client.comet.CometMessage;

public class CometHardwareLoadResponse extends CometMessage {

	private static final long serialVersionUID = -3264740979351883877L;

	private String serial;
	private String software;
	private String hardware;
	private String address;
	private String description;

	public CometHardwareLoadResponse() {
		super();
	}

	public CometHardwareLoadResponse(String serial, String software, String hardware) {
		super();
		this.serial = serial;
		this.software = software;
		this.hardware = hardware;
	}

	public String getSerial() {
		return serial;
	}

	public void setSerial(String serial) {
		this.serial = serial;
	}

	public String getSoftware() {
		return software;
	}

	public void setSoftware(String software) {
		this.software = software;
	}

	public String getHardware() {
		return hardware;
	}

	public void setHardware(String hardware) {
		this.hardware = hardware;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public String toString() {
		return "CometHardwareLoadResponse [serial=" + serial + ", software="
				+ software + ", hardware=" + hardware + ", address=" + address
				+ ", description=" + description + "]";
	}

}
