import org.everit.json.schema.Schema;
import org.everit.json.schema.ValidationException;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;
import utility.Counter;

/**
 * author : nirmitsrivastava
 */

public class JsonValidator {

    Counter counts;

    public Counter schemaValidate(Schema schema, Object json)  {
        counts = new Counter();
        counts.nullCount=0;
        counts.errorCount=0;
        try {
            if(json instanceof String){
                json = isValidJson(json);
            }
            if(json instanceof JSONArray){
                System.out.println("No. of elements parsed :"+((JSONArray)json).length());
            }
            if(json instanceof JSONObject){
                System.out.println("No. of elements parsed : 1");
            }
            schema.validate(json);
        }  catch (ValidationException ex) {
            ValidationMessage(ex);
            System.out.println("Total No. of exception :"+counts.errorCount);
            System.out.println("Total Null count :"+counts.nullCount);
        }
        catch (JSONException ex){
            ex.printStackTrace();
        }
        return counts;
    }

    private static Object isValidJson(Object json) {

            Object obj = new JSONTokener(json.toString()).nextValue();
            return obj;
    }

    private  void ValidationMessage(ValidationException exception){
        if(exception.getCausingExceptions().isEmpty()){
            if(exception.getErrorMessage().toString().contains("found: Null")){
                counts.nullCount++;
            }
            else {
                System.out.println(exception.getKeyword()+" exception ::"+exception.getMessage());
                counts.errorCount++;
            }
        }
        else {
            for(ValidationException singleException : exception.getCausingExceptions()){
                ValidationMessage(singleException);
            }
        }

    }
}
