package pl.eHouse.web.common.client.comet.messages;

import pl.eHouse.web.common.client.comet.CometMessage;

public class CometBootStartMessage extends CometMessage {

	private static final long serialVersionUID = 3207605656279677975L;
	
	private String serial;
	private String hardwareType;
	private String memorySize;
	private String pageSize;
	private String status;
	
	public CometBootStartMessage() {
		super();
	}

	public CometBootStartMessage(String serial, String hardwareType,
			String memorySize, String pageSize, String status) {
		super();
		this.serial = serial;
		this.hardwareType = hardwareType;
		this.memorySize = memorySize;
		this.pageSize = pageSize;
		this.status = status;
	}

	public String getSerial() {
		return serial;
	}

	public void setSerial(String serial) {
		this.serial = serial;
	}

	public String getHardwareType() {
		return hardwareType;
	}

	public void setHardwareType(String hardwareType) {
		this.hardwareType = hardwareType;
	}

	public String getMemorySize() {
		return memorySize;
	}

	public void setMemorySize(String memorySize) {
		this.memorySize = memorySize;
	}

	public String getPageSize() {
		return pageSize;
	}

	public void setPageSize(String pageSize) {
		this.pageSize = pageSize;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "CometBootStartMessage [serial=" + serial + ", hardwareType="
				+ hardwareType + ", memorySize=" + memorySize + ", pageSize="
				+ pageSize + ", status=" + status + "]";
	}

	
	
}
