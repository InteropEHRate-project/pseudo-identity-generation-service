package eu.interopehrate.pseudoidgenerator.PseudoIdGenerator;

import org.json.JSONObject;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.sql.SQLException;
import java.util.regex.Pattern;

@RestController
@Validated
public class PseudoIdentityController {

    @GetMapping("/pseudo_identity")
    @ResponseBody
    public String generatePseudoIdentity(@Valid @RequestParam("prefix") String prefix) throws SQLException {

        String message;
        int status;
        String response;

        if(prefix == null || prefix.equals("")) {
            message = "Prefix parameter should be provided.";
            status = 400;
            response = generateResponse("", "", message, status);
        } else if(!Pattern.matches("^[A-Za-z0-9_-]+$", prefix)) {
            message = "Prefix should contain only letters, numbers, underscores and dashes [a-z, A-Z, 0-9, _, -].";
            status = 400;
            response = generateResponse("", "", message, status);
        } else {
            PseudoIdentityGenerator pseudoIdentityGenerator = new PseudoIdentityGenerator();
            String pseudo_identity = pseudoIdentityGenerator.generatePseudoIdentity(prefix);
            //message = "The pseudo-identity for the prefix '"+prefix+"' is: "+pseudo_identity+".";
            message = "The pseudo-identity was generated successfully.";
            status = 200;
            response = generateResponse(prefix, pseudo_identity, message, status);
        }
        return response;
    }

    private static String generateResponse(String prefix, String pseudo_identity, String message, int status){

        JSONObject json = new JSONObject();

        if(!prefix.equals("")){
            json.put("prefix", prefix);
            json.put("pseudo-identity", pseudo_identity);
        }
        json.put("message", message);
        json.put("status", status);

        return json.toString();
    }
}




