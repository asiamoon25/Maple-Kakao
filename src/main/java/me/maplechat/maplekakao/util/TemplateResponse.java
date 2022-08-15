package me.maplechat.maplekakao.util;



import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/*
    반환할 template 등을 처리하는 클래스
    @author wjddustkd45@gmail.com
 */

public class TemplateResponse {

    private final String version = "2.0";
    private final JSONObject payload = new JSONObject();
    private final JSONObject templates = new JSONObject();
    private final JSONArray outputs = new JSONArray();
    private final JSONArray buttons = new JSONArray();

    public TemplateResponse() {
        payload.put("version", version);
        payload.put("template", templates);
        templates.put("outputs", outputs);
    }

    // for create card template
    public void addBasicCard(String title, String description, JSONObject imageUrl) {

        JSONObject basicCard = new JSONObject();

        JSONObject basicCardField = new JSONObject();

        JSONObject thumbnailFiled = new JSONObject();


        basicCardField.put("title", title);
        basicCardField.put("description", description);
        basicCardField.put("thumbnail", imageUrl);

        basicCard.put("basicCard", basicCardField);

        basicCard.put("buttons", this.buttons);

        this.outputs.add(basicCard);
    }

    //web link buttons
    public void addButton(String action, String label, String webLinkUrl) {
        JSONObject fieldParams = new JSONObject();

        fieldParams.put("action", action);
        fieldParams.put("label", label);
        fieldParams.put("webLinkUrl", webLinkUrl);

        this.buttons.add(fieldParams);
    }
    // message buttons
   public JSONObject convertFromStringToJSON(String title,String username,String imgUrl, String webLinkUrl){

        JSONObject json = new JSONObject();

        String s = "{\n" +
                "  \"version\": \"2.0\",\n" +
                "  \"template\": {\n" +
                "    \"outputs\": [\n" +
                "      {\n" +
                "        \"basicCard\": {\n" +
                "          \"title\": \"캐릭터 정보\",\n" +
                "          \"description\": \"유저네임\",\n" +
                "          \"thumbnail\": {\n" +
                "            \"imageUrl\": \"userProfileImg\"\n" +
                "          },\n" +
                "          \"buttons\": [\n" +
                "            {\n" +
                "              \"action\":  \"webLink\",\n" +
                "              \"label\": \"구경하기\",\n" +
                "              \"webLinkUrl\": \"https:\\/\\/avatar.maplestory.nexon.com\\/Character\\/ALHHNHIKEBNCPNNIBOOFCFLBGDNIBFOMHKHFFOEEBDKCOIIDACHLIDMIIMBPMIBOMBOAMOEIOLIBAONAOFFFNHKMIKBHHKKAAJMMGOINHAGCENFAGFGHPIECGDHNENAPNACEHGMFGOLNEOLDJDNNPIFFLEDFJCLLFCJHLDBBPAEDMFKNJJKOEIOMHPFIKBAIEGJBEOMHCAMJJJPFICBHLEKOFMEJOIDODKIGMHEBPHPMODEAACLIDKHAAAHCIOGA.png\"\n" +
                "            }\n" +
                "          ]\n" +
                "        }\n" +
                "      }\n" +
                "    ]\n" +
                "  }\n" +
                "}";

       JSONParser jsonParser = new JSONParser();
       try {
         json = (JSONObject)jsonParser.parse(s);
       } catch (ParseException e) {
           e.printStackTrace();
       }
        return json;
   }

    public JSONObject getPayload() {
        if(outputs.size() == 0){
            throw new IllegalStateException("output을 넣으세요");
        }
        return payload;
    }

}
