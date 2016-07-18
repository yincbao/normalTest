package com.cpw.encryption;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.security.Key;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;

import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;

/**
 * @ClassName RSAEncryption.java
 * @Description 
 * @author yin_changbao
 * @Date Jul 13, 2016 1:28:31 PM
 *
 */
public class RSAEncryption {
	public static void makekeyfile(String pubkeyfile, String privatekeyfile)  
            throws NoSuchAlgorithmException, FileNotFoundException, IOException {
		
        KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance("RSA");  
        keyPairGen.initialize(1024,new SecureRandom());  
        KeyPair keyPair = keyPairGen.generateKeyPair();  
        RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();  
        RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();  
  
        ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(  
                privatekeyfile));  
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
    public static byte[] handleData(Key k, byte[] data, int encrypt)  
            throws Exception {  
  
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
  
    public static void main(String[] args) throws Exception {  
  
        String pubfile = "d:/temp/pub.key";  
        String prifile = "d:/temp/pri.key";  
        
       mkfileIfNotExists(pubfile,prifile);
  
        makekeyfile(pubfile, prifile);  
  
        ObjectInputStream ois = new ObjectInputStream(new FileInputStream(  
                pubfile));  
        RSAPublicKey pubkey = (RSAPublicKey) ois.readObject();  
        ois.close();  
  
        ois = new ObjectInputStream(new FileInputStream(prifile));  
        RSAPrivateKey prikey = (RSAPrivateKey) ois.readObject();  
        ois.close();  
  
        // 使用公钥加密  
        String msg = "~O(∩_∩)O哈哈~";  
        String enc = "UTF-8";  
  
        // 使用公钥加密私钥解密  
        System.out.println("原文: " + msg);  
        byte[] result = handleData(pubkey, msg.getBytes(enc), 1);  
        System.out.println("加密: " + new String(result, enc));  
        byte[] deresult = handleData(prikey, result, 0);  
        System.out.println("解密: " + new String(deresult, enc));  
  
        msg = "嚯嚯";  
        // 使用私钥加密公钥解密  
        System.out.println("原文: " + msg);  
        byte[] result2 = handleData(prikey, msg.getBytes(enc), 1);  
        System.out.println("加密: " + new String(result2, enc));  
        byte[] deresult2 = handleData(pubkey, result2, 0);  
        System.out.println("解密: " + new String(deresult2, enc));  
  
    }

	/**
	 */
	private static void mkfileIfNotExists(String ...files) {
		if(files!=null&&files.length>0){
			File f = null;
			for(String file:files){
				 f = new File(file);
				 if(!f.exists())
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
