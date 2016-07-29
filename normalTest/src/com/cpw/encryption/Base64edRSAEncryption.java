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

import org.apache.commons.codec.binary.Base64;

/**
 * @ClassName RSAEncryption.java
 * @Description
 * @author yin_changbao
 * @Date Jul 13, 2016 1:28:31 PM 获取Key 的byte数组，getEncode方法 byte[]
 *       可以转hex，也可直接base64转码成string字符串，写文件。
 *
 */
public class Base64edRSAEncryption {

	public static String getKeyString(Key key) throws Exception {
		byte[] kb = key.getEncoded();
		Base64 base64 = new Base64(0);
		return base64.encodeToString(kb);
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

	public static void makeHexKeyFile(String pubkeyfile, String privatekeyfile) throws Exception {

		KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance("RSA");
		keyPairGen.initialize(1024, new SecureRandom());
		KeyPair keyPair = keyPairGen.generateKeyPair();
		RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();
		RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();

		tinyFileWrite(privatekeyfile, getKeyString(privateKey));
		tinyFileWrite(pubkeyfile, getKeyString(publicKey));

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

	private static Key pubkeyEncodeByteToKey(String base64edpubkey) throws NoSuchAlgorithmException, InvalidKeySpecException {
		X509EncodedKeySpec keySpec = new X509EncodedKeySpec(new Base64(0).decode(base64edpubkey));
		KeyFactory keyFactory = KeyFactory.getInstance("RSA");
		RSAPublicKey pubKey = (RSAPublicKey) keyFactory.generatePublic(keySpec);
		return pubKey;
	}

	/**
	 * @param hex2Bytes
	 * @return
	 * @throws NoSuchAlgorithmException
	 * @throws InvalidKeySpecException
	 */
	private static Key prikeyEncodeByteToKey(String base64edprikey) throws NoSuchAlgorithmException, InvalidKeySpecException {
		PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(new Base64(0).decode(base64edprikey));
		KeyFactory keyFactory = KeyFactory.getInstance("RSA");
		PrivateKey privateKey = keyFactory.generatePrivate(keySpec);
		return privateKey;
	}

	public static void main(String[] args) throws Exception {

		String pubfile = "d:/temp/pub1.key";
		String prifile = "d:/temp/pri1.key";

		mkfileIfNotExists(pubfile, prifile);

		makeHexKeyFile(pubfile, prifile);

		String base64edpubkey = tinyFileRead(new File(pubfile));

		String base64edprikey = tinyFileRead(new File(prifile));

		RSAPublicKey pubKey = (RSAPublicKey) pubkeyEncodeByteToKey(base64edpubkey);
		RSAPrivateKey priKey = (RSAPrivateKey) prikeyEncodeByteToKey(base64edprikey);

		/*
		 * ObjectInputStream ois = new ObjectInputStream(new
		 * FileInputStream(pubfile)); RSAPublicKey pubkey = (RSAPublicKey)
		 * ois.readObject(); ois.close();
		 * 
		 * ois = new ObjectInputStream(new FileInputStream(prifile));
		 * RSAPrivateKey prikey = (RSAPrivateKey) ois.readObject(); ois.close();
		 */

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
