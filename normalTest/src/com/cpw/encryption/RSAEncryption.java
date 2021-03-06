package com.cpw.encryption;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.security.Key;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.SecureRandom;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;

/**
 * @ClassName RSAEncryption.java
 * @Description
 * @author yin_changbao
 * @Date Jul 13, 2016 1:28:31 PM
 * 获取Key 的byte数组，getEncode方法
 * byte[] 可以转hex，也可直接base64转码成string字符串，写文件。
 *
 */
public class RSAEncryption {

	public static String bytes2Hex(byte[] src) {

		char[] res = new char[src.length * 2];
		final char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F' };
		for (int i = 0, j = 0; i < src.length; i++) {
			res[j++] = hexDigits[src[i] >>> 4 & 0x0f];
			res[j++] = hexDigits[src[i] & 0x0f];
		}

		return new String(res);
	}

	public static byte[] hex2Bytes(String src) {
		byte[] res = new byte[src.length() / 2];
		char[] chs = src.toCharArray();
		for (int i = 0, c = 0; i < chs.length; i += 2, c++) {
			res[c] = (byte) (Integer.parseInt(new String(chs, i, 2), 16));
		}

		return res;
	}
	public static void tinyFileWrite(String destFile, String content) {
		BufferedWriter out = null;
		try {
			File file = new File(destFile);
			if (!file.exists())
				file.createNewFile();
			out = new BufferedWriter(new FileWriter(file));
			out.write(content);
		} catch (IOException e) {
		} finally {
			try {
				if (out != null)
					out.close();
			} catch (IOException e) {
			}
		}

	}
	public static void makeHexKeyFile(String pubkeyfile, String privatekeyfile) throws NoSuchAlgorithmException, IOException {

		KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance("RSA");
		keyPairGen.initialize(1024, new SecureRandom());
		KeyPair keyPair = keyPairGen.generateKeyPair();
		RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();
		RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
		
		tinyFileWrite(privatekeyfile,bytes2Hex(privateKey.getEncoded()));
		tinyFileWrite(pubkeyfile,bytes2Hex(publicKey.getEncoded()));

	}

	public static void makekeyfile(String pubkeyfile, String privatekeyfile) throws NoSuchAlgorithmException, FileNotFoundException, IOException {

		KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance("RSA");
		keyPairGen.initialize(1024, new SecureRandom());
		KeyPair keyPair = keyPairGen.generateKeyPair();
		RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();
		RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();

		ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(privatekeyfile));
		oos.writeObject(privateKey);
		oos.flush();
		oos.close();

		oos = new ObjectOutputStream(new FileOutputStream(pubkeyfile));
		oos.writeObject(publicKey);
		oos.flush();
		oos.close();

		System.out.println("make file ok!");
	}

	/**
	 * 
	 * @param k
	 * @param data
	 * @param encrypt
	 *            1 加密 0解密
	 * @return
	 * @throws NoSuchPaddingException
	 * @throws Exception
	 */
	public static byte[] handleData(Key k, byte[] data, int encrypt) throws Exception {

		if (k != null) {

			Cipher cipher = Cipher.getInstance("RSA");

			if (encrypt == 1) {
				cipher.init(Cipher.ENCRYPT_MODE, k);
				byte[] resultBytes = cipher.doFinal(data);
				return resultBytes;
			} else if (encrypt == 0) {
				cipher.init(Cipher.DECRYPT_MODE, k);
				byte[] resultBytes = cipher.doFinal(data);
				return resultBytes;
			} else {
				System.out.println("参数必须为: 1 加密 0解密");
			}
		}
		return null;
	}
	public static String tinyFileRead(File file) {
		BufferedReader in = null;
		try {
			if (!file.exists())
				file.createNewFile();
			in = new BufferedReader(new FileReader(file));
			StringBuilder sb = new StringBuilder();
			String temp = null;
			while ((temp = in.readLine()) != null)
				sb.append(temp);
			return sb.toString();
		} catch (IOException e) {
		} finally {
			try {
				if (in != null)
					in.close();
			} catch (IOException e) {
			}
		}
		return null;
	}

	
	private static Key pubkeyEncodeByteToKey(byte[] encodeed) throws NoSuchAlgorithmException, InvalidKeySpecException{
		X509EncodedKeySpec keySpec = new X509EncodedKeySpec(encodeed);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        RSAPublicKey pubKey = (RSAPublicKey)keyFactory.generatePublic(keySpec);
        return pubKey;
	}
	
	/**
	 * @param hex2Bytes
	 * @return
	 * @throws NoSuchAlgorithmException 
	 * @throws InvalidKeySpecException 
	 */
	private static Key prikeyEncodeByteToKey(byte[] hex2Bytes) throws NoSuchAlgorithmException, InvalidKeySpecException {
		 PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(hex2Bytes);
         KeyFactory keyFactory = KeyFactory.getInstance("RSA");
         PrivateKey privateKey = keyFactory.generatePrivate(keySpec);
         return privateKey;
	}
	public static void main(String[] args) throws Exception {

		String pubfile = "d:/temp/pub.key";
		String prifile = "d:/temp/pri.key";

		mkfileIfNotExists(pubfile, prifile);

		makeHexKeyFile(pubfile, prifile);
		
		
		String pubkey = tinyFileRead(new File(pubfile));
		
		String prikey = tinyFileRead(new File(prifile));
		
		RSAPublicKey pubKey = (RSAPublicKey) pubkeyEncodeByteToKey( hex2Bytes(pubkey));
		RSAPrivateKey priKey = (RSAPrivateKey) prikeyEncodeByteToKey( hex2Bytes(prikey));

		
		/*ObjectInputStream ois = new ObjectInputStream(new FileInputStream(pubfile));
		RSAPublicKey pubkey = (RSAPublicKey) ois.readObject();
		ois.close();

		ois = new ObjectInputStream(new FileInputStream(prifile));
		RSAPrivateKey prikey = (RSAPrivateKey) ois.readObject();
		ois.close();*/

		// 使用公钥加密
		String msg = "~O(∩_∩)O哈哈~";
		String enc = "UTF-8";

		// 使用公钥加密私钥解密
		System.out.println("原文: " + msg);
		byte[] result = handleData(pubKey, msg.getBytes(enc), 1);
		System.out.println("加密: " + new String(result, enc));
		byte[] deresult = handleData(priKey, result, 0);
		System.out.println("解密: " + new String(deresult, enc));

		msg = "嚯嚯";
		// 使用私钥加密公钥解密
		System.out.println("原文: " + msg);
		byte[] result2 = handleData(priKey, msg.getBytes(enc), 1);
		System.out.println("加密: " + new String(result2, enc));
		byte[] deresult2 = handleData(pubKey, result2, 0);
		System.out.println("解密: " + new String(deresult2, enc));

	}



	/**
	 */
	private static void mkfileIfNotExists(String... files) {
		if (files != null && files.length > 0) {
			File f = null;
			for (String file : files) {
				f = new File(file);
				f.deleteOnExit();
				if (!f.exists())
					try {
						f.getParentFile().mkdirs();
						f.createNewFile();
					} catch (IOException e) {
						e.printStackTrace();
					}
									
			}

		}

	}
}
