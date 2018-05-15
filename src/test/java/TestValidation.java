import org.everit.json.schema.Schema;
import org.everit.json.schema.loader.SchemaLoader;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.junit.Assert;
import org.junit.Test;
import utility.Counter;


import java.io.InputStream;
import java.net.URL;
import java.util.Scanner;

/**
 * author : nirmitsrivastava
 */
public class TestValidation {
    @Test
    public void jsonArrayTest() throws Exception {
            InputStream inputStream = getClass().getResourceAsStream("jsonSchema");
            JSONObject rawSchema = new JSONObject(new JSONTokener(inputStream));
            Schema schema = SchemaLoader.load(rawSchema);
            inputStream = getClass().getResourceAsStream("jsonArray");
            JSONArray json = new JSONArray(new JSONTokener(inputStream));
            Counter count =new JsonValidator().schemaValidate(schema,json);
            Assert.assertEquals(count.nullCount,4);
            Assert.assertEquals(count.errorCount,0);
    }

    @Test
    public void jsonObjectTest() throws Exception{
        InputStream inputStream = getClass().getResourceAsStream("jsonSchema");
        JSONObject rawSchema = new JSONObject(new JSONTokener(inputStream));
        Schema schema = SchemaLoader.load(rawSchema);
        inputStream = getClass().getResourceAsStream("jsonNode");
        JSONObject json = new JSONObject(new JSONTokener(inputStream));
        Counter count =new JsonValidator().schemaValidate(schema,json);
        Assert.assertEquals(count.nullCount,0);
        Assert.assertEquals(count.errorCount,1);

    }

    @Test
    public void nullTest() throws Exception {
        InputStream inputStream = getClass().getResourceAsStream("jsonSchema");
        JSONObject rawSchema = new JSONObject(new JSONTokener(inputStream));
        Schema schema = SchemaLoader.load(rawSchema);
        Counter count =new JsonValidator().schemaValidate(schema,null);
        Assert.assertEquals(count.nullCount,0);
        Assert.assertEquals(count.errorCount,1);
    }

    @Test
    public void firstURLTest() throws Exception{
        InputStream inputStream = getClass().getResourceAsStream("jsonSchema");
        JSONObject rawSchema = new JSONObject(new JSONTokener(inputStream));
        Schema schema = SchemaLoader.load(rawSchema);
        String url = "https://gist.githubusercontent.com/ab9-er/27a903b8636e745fb820a7d310177c46/raw/9315383f0a0f8da2f0931ba69ab7be2d1f7b95b5/world_universities_and_domains.json";
        inputStream = new URL(url).openStream();
        String text = new Scanner( inputStream).useDelimiter("\\A").next();
        Counter count =new JsonValidator().schemaValidate(schema,text);
        Assert.assertEquals(count.nullCount,9659);
        Assert.assertEquals(count.errorCount,0);
    }

    @Test
    public void secondURLTest() throws Exception{
        InputStream inputStream = getClass().getResourceAsStream("jsonSchema");
        JSONObject rawSchema = new JSONObject(new JSONTokener(inputStream));
        Schema schema = SchemaLoader.load(rawSchema);
        String url = "https://gist.githubusercontent.com/ab9-er/27a903b8636e745fb820a7d310177c46/raw/1263a2e60ce6576da2e0812b37f775e89f50d744/world_universities_and_domains.json";
        inputStream = new URL(url).openStream();
        String text = new Scanner( inputStream).useDelimiter("\\A").next();
        Counter count =new JsonValidator().schemaValidate(schema,text);
        Assert.assertEquals(count.nullCount,9659);
        Assert.assertEquals(count.errorCount,24);
    }

    @Test
    public void invalidURLTest() throws Exception{
        InputStream inputStream = getClass().getResourceAsStream("jsonSchema");
        JSONObject rawSchema = new JSONObject(new JSONTokener(inputStream));
        Schema schema = SchemaLoader.load(rawSchema);
        String url = "https://www.google.com";
        inputStream = new URL(url).openStream();
        String text = new Scanner( inputStream).useDelimiter("\\A").next();
        Counter count =new JsonValidator().schemaValidate(schema,text);
        Assert.assertEquals(count.nullCount,0);
        Assert.assertEquals(count.errorCount,1);
    }

    @Test
    public void emptyJsonArrayTest() throws Exception{
        InputStream inputStream = getClass().getResourceAsStream("jsonSchema");
        JSONObject rawSchema = new JSONObject(new JSONTokener(inputStream));
        Schema schema = SchemaLoader.load(rawSchema);
        Counter count =new JsonValidator().schemaValidate(schema,new JSONArray());
        Assert.assertEquals(count.nullCount,0);
        Assert.assertEquals(count.errorCount,0);

    }

    @Test
    public void invalidTypeTest() throws Exception{
        InputStream inputStream = getClass().getResourceAsStream("jsonSchema");
        JSONObject rawSchema = new JSONObject(new JSONTokener(inputStream));
        Schema schema = SchemaLoader.load(rawSchema);
        Counter count =new JsonValidator().schemaValidate(schema,true);
        Assert.assertEquals(count.nullCount,0);
        Assert.assertEquals(count.errorCount,1);
    }

    @Test
    public void missingKeys() throws Exception{
        String json ="[\n" +
                "  {\n" +
                "    \"web_pages\": [\n" +
                "      \"https://www.cstj.qc.ca\",\n" +
                "      \"https://ccmt.cstj.qc.ca\",\n" +
                "      \"https://ccml.cstj.qc.ca\"\n" +
                "    ],\n" +
                "    \"state-province\": null,\n" +
                "    \"domains\": [\n" +
                "      \"cstj.qc.ca\"\n" +
                "    ],\n" +
                "    \"country\": \"Canada\"\n" +
                "  }]";
        InputStream inputStream = getClass().getResourceAsStream("jsonSchemaWithRequiredKeys");
        JSONObject rawSchema = new JSONObject(new JSONTokener(inputStream));
        Schema schema = SchemaLoader.load(rawSchema);
        Counter count =new JsonValidator().schemaValidate(schema,json);
        Assert.assertEquals(count.nullCount,1);
        Assert.assertEquals(count.errorCount,2);
    }

    @Test
    public void jsonStringTest() throws Exception{
        String json ="Hello World";
        InputStream inputStream = getClass().getResourceAsStream("jsonSchema");
        JSONObject rawSchema = new JSONObject(new JSONTokener(inputStream));
        Schema schema = SchemaLoader.load(rawSchema);
        Counter count = new JsonValidator().schemaValidate(schema,json);
        Assert.assertEquals(count.nullCount,0);
        Assert.assertEquals(count.errorCount,1);
    }
}
