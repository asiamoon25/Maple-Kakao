package me.maplechat.maplekakao.util;


import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

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
        payload.put("version",version);
        payload.put("template",templates);
        templates.put("outputs",outputs);
        templates.put("buttons", buttons);
    }

    // for create card template
    public void addBasicCard(String title, String description, JSONObject imageUrl ) {

        JSONObject basicCard = new JSONObject();

        JSONObject basicCardField = new JSONObject();

        JSONObject thumbnailFiled = new JSONObject();

        thumbnailFiled.put("imageUrl",imageUrl);

        basicCardField.put("title",title);
        basicCardField.put("description", description);
        basicCardField.put("thumbnail",thumbnailFiled);

        basicCard.put("basicCard",basicCardField);

        this.outputs.add(basicCard);
    }

    //web link buttons
    public void addButton (String action, String label, String messageText, String webLinkUrl){
        JSONObject field = new JSONObject();

        field.put("action",action);
        field.put("label",label);
        field.put("messageText",messageText);
        field.put("webLinkUrl",webLinkUrl);

        this.buttons.add(field);

    }
    // message buttons
    public void addButton(String action,String label,String messageText){

        JSONObject field = new JSONObject();

        field.put("action",action);
        field.put("label",label);
        field.put("messageText",messageText);

        this.buttons.add(field);

    }

    public void addOutput () {
        this.outputs.add(this.buttons);
    }

    public JSONObject getPayload() {
        if(outputs.size() == 0){
            throw new IllegalStateException("output을 넣으세요");
        }
        return payload;
    }

}