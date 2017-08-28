package com.nhance.technician.networking.util;

import org.bouncycastle.crypto.BufferedBlockCipher;
import org.bouncycastle.crypto.engines.DESedeEngine;
import org.bouncycastle.crypto.modes.CBCBlockCipher;
import org.bouncycastle.crypto.params.KeyParameter;
import org.bouncycastle.util.encoders.Base64;

import java.io.UnsupportedEncodingException;

public class EncryptionUtil {
	private static final BufferedBlockCipher cipher = new BufferedBlockCipher(new CBCBlockCipher(new DESedeEngine()));
	private static KeyParameter key;

	static {
		try {
			key = new KeyParameter("FIGKEYTestedKeyR".getBytes());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static String encryptPayLoad(byte[] data) {
		try {
			return new String(Base64.encode(encrypt(padISO9797M2(data))));
		} catch (Exception e) {

		}
		return null;
	}

	public static byte[] decryptPayLoad(byte[] encryptedData) {
		try {

			return unPadISO9797M2(decrypt(Base64.decode(encryptedData)));
		} catch (Exception e) {

		}
		return null;
	}

	// ISO9797 Padding Method 2
	private static byte[] padISO9797M2(byte[] data) {
		int j = data.length;
		int k = j % 8;
		if (k != 0)
			k = 8 - k;
		else
			k = 8;
		byte[] abyte1 = new byte[j + k];
		System.arraycopy(data, 0, abyte1, 0, j);
		abyte1[j] = (byte) 0x80;
		return abyte1;
	}

	private static byte[] unPadISO9797M2(byte[] data) throws UnsupportedEncodingException {
		byte[] abyte1 = null;
		for (int i = data.length - 1; i >= 0; i--) {
			if ((data[i] & 0xFF) == 0x80) {
				abyte1 = new byte[i];
				System.arraycopy(data, 0, abyte1, 0, i);
				break;
			} else if (data[i] != 0) {
				throw new UnsupportedEncodingException("Improper padding");
			}
		}
		return abyte1;
	}

	/**
	 * This method helps to encrypt.
	 * 
	 * @param data
	 *            the data
	 * @return the byte[]
	 * @throws Exception
	 *             the exception
	 */
	public static byte[] encrypt(byte[] data) throws Exception {
		cipher.init(true, key);
		byte[] output = new byte[cipher.getOutputSize(data.length)];
		int outLen = cipher.processBytes(data, 0, data.length, output, 0);
		cipher.doFinal(output, outLen);
		return output;
	}

	/**
	 * This method helps to decrypt.
	 * 
	 * @param encryptedData
	 *            the encrypted data
	 * @return the byte[]
	 * @throws Exception
	 *             the exception
	 */
	public static byte[] decrypt(byte[] encryptedData) throws Exception {
		cipher.init(false, key);
		byte[] output = new byte[cipher.getOutputSize(encryptedData.length)];
		int outLen = cipher.processBytes(encryptedData, 0, encryptedData.length, output, 0);
		cipher.doFinal(output, outLen);
		return output;
	}

	public static void test() {
		String data = "This is Test Data";
		System.out.println(data);
		String encData = encryptPayLoad(data.getBytes());
		System.out.println(encData);
		data = new String(decryptPayLoad(encData.getBytes()));
		System.out.println(data);
	}

}
