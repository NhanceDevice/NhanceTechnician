package com.nhance.technician.networking.sec;

import com.nhance.technician.exception.NhanceException;

public interface EncryptionProvider {

	byte[] encryptPayLoad(byte[] data) throws NhanceException;

	byte[] decryptPayLoad(byte[] encryptedData) throws NhanceException;

	void unwrapDbKey(byte[] wrappedDbKey) throws NhanceException;

}
