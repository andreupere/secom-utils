package cat.uib.secom.utils.crypto.pkc;

import java.util.Arrays;

//import org.bouncycastle.openssl.PasswordFinder;


public class DefaultPasswordFinder {// implements PasswordFinder {

    private final char [] password;

    public DefaultPasswordFinder(char [] password) {
        this.password = password;
    }

    public char[] getPassword() {
        //return Arrays.copyOf(password, password.length); // not supported by Android before API level 9
    	return password;
    }
} 