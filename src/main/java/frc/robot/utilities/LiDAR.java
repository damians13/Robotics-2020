package frc.robot.utilities;

import java.nio.ByteBuffer;

import edu.wpi.first.hal.I2CJNI;
import edu.wpi.first.wpilibj.I2C.Port;

/**
 * This class is adapted from team 2102's code:
 * https://github.com/Paradox2102/Artemis/blob/master/src/team2102/lib/LIDARLite.java
 */

public class LiDAR {

    private static final byte k_deviceAddress = 0x62;

	private final byte m_port;

	private final ByteBuffer m_buffer = ByteBuffer.allocateDirect(2);

	public LiDAR(Port port) {
		m_port = (byte) port.value;
		I2CJNI.i2CInitialize(m_port);
	}

	public void startMeasuring() {
		writeRegister(0x04, 0x08 | 32); // default plus bit 5
		writeRegister(0x11, 0xff);
		writeRegister(0x00, 0x04);
	}

	public void stopMeasuring() {
		writeRegister(0x11, 0x00);
	}

	public short getDistance() {
		startMeasuring();
		short dist = readShort(0x8f);
		stopMeasuring();
		return dist;
	}

	private int writeRegister(int address, int value) {
		m_buffer.put(0, (byte) address);
		m_buffer.put(1, (byte) value);

		return I2CJNI.i2CWrite(m_port, k_deviceAddress, m_buffer, (byte) 2);
	}

	private short readShort(int address) {
		m_buffer.put(0, (byte) address);
		I2CJNI.i2CWrite(m_port, k_deviceAddress, m_buffer, (byte) 1);
		I2CJNI.i2CRead(m_port, k_deviceAddress, m_buffer, (byte) 2);
		return m_buffer.getShort(0);
	}

}