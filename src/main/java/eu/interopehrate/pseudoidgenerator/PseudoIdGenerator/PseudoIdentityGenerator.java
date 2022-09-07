package eu.interopehrate.pseudoidgenerator.PseudoIdGenerator;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;

public class PseudoIdentityGenerator {

    private static final Charset UTF_8 =  StandardCharsets.UTF_8;

    public static String generatePseudoIdentity(String prefix) throws SQLException {

        DatabaseHandler databaseHandler = new DatabaseHandler();
        int incremental_number = databaseHandler.getIncrementalNumber(prefix);
        String input = prefix + incremental_number;
        String randomValue = sha(input);
        String pseudo_identity = prefix + incremental_number + randomValue;
        //System.out.println("Pseudo-identity: " + pseudo_identity);

        return pseudo_identity;
    }

    private static String sha(String input) {
        MessageDigest md;
        try {
            md = MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalArgumentException(e);
        }
        byte[] result = md.digest(input.getBytes(UTF_8));

        StringBuilder sb = new StringBuilder();
        for (byte b : result) {
            sb.append(String.format("%02x", b));
        }
        //System.out.println(sb.toString().length());
        //System.out.println("Hash of " + input + " is " + sb.toString() +".");
        return sb.toString();

    }
}
