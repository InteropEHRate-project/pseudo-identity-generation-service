package eu.interopehrate.pseudoidgenerator.PseudoIdGenerator;

import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;

@Controller
@RequestMapping(path="/pseudo_identity")
public class PseudoIdentityController {

    @GetMapping("")
    @ResponseBody
    public String generatePseudoIdentity(@RequestParam String prefix) throws SQLException {

        String message;
        int status;
        String response;

        if(prefix == null || prefix.equals("")) {
            message = "Error: At least one parameter is invalid or not provided.";
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




