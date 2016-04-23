package main;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.security.Provider;
import java.security.SecureRandom;
import java.security.Security;
import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.CipherOutputStream;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.PBEParameterSpec;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

/**
 * @author laki
 */
public class Encryptor {

    private static final Provider provider;
    private static final byte[] salt;
    private static final String algorithm;
    private static File result;

    static {
        provider = new BouncyCastleProvider();
        Security.addProvider(provider);
        salt = new byte[8];
        algorithm = "PBEWithMD5AndDES";
    }

    public static void encrypt(File fileToEncrypt, String password) throws Exception {
        result = new File((fileToEncrypt.getAbsolutePath() + "_encrypted"));
        result.createNewFile();
        SecureRandom random = new SecureRandom();
        random.nextBytes(salt);
        int count = 1024;

        PBEParameterSpec pbeParamSpec = new PBEParameterSpec(salt, count);
        PBEKeySpec pbeKeySpec = new PBEKeySpec(password.toCharArray(), salt, count);
        SecretKeyFactory keyFac = SecretKeyFactory.getInstance(algorithm);
        SecretKey pbeKey = keyFac.generateSecret(pbeKeySpec);
        Cipher pbeCipher = Cipher.getInstance(algorithm);

        pbeCipher.init(Cipher.ENCRYPT_MODE, pbeKey, pbeParamSpec);
        FileInputStream in = new FileInputStream(fileToEncrypt);
        FileOutputStream out = new FileOutputStream(result);
        CipherOutputStream cipherOutputStream = new CipherOutputStream(out, pbeCipher);
        out.write(salt, 0, salt.length);
        while (true) {
            byte[] byteArray = new byte[256];
            int numberOfBytes = in.read(byteArray);
            if (numberOfBytes == -1) {
                break;
            }
            cipherOutputStream.write(byteArray, 0, numberOfBytes);
        }
        cipherOutputStream.close();
        in.close();
    }

    public static void decrypt(File fileToDecrypt, String password) throws Exception {
        result = new File(fileToDecrypt.getAbsolutePath() + "_decrypted");
        result.createNewFile();
        FileInputStream in = new FileInputStream(fileToDecrypt);
        FileOutputStream out = new FileOutputStream(result);

        in.read(salt, 0, salt.length);
        int count = 1024;

        PBEParameterSpec pbeParamSpec = new PBEParameterSpec(salt, count);
        PBEKeySpec pbeKeySpec = new PBEKeySpec(password.toCharArray(), salt, count);
        SecretKeyFactory keyFac = SecretKeyFactory.getInstance(algorithm);
        SecretKey pbeKey = keyFac.generateSecret(pbeKeySpec);

        Cipher pbeCipher = Cipher.getInstance(algorithm);
        pbeCipher.init(Cipher.DECRYPT_MODE, pbeKey, pbeParamSpec);

        CipherInputStream cipherInputStream = new CipherInputStream(in, pbeCipher);
        while (true) {
            byte[] byteArray = new byte[256];
            int numberOfBytes = cipherInputStream.read(byteArray);
            if (numberOfBytes == -1) {
                break;
            }
            out.write(byteArray, 0, numberOfBytes);
        }
        cipherInputStream.close();
        out.close();

    }

}
