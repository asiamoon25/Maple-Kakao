package me.maplechat.maplekakao.controller;

import jdk.nashorn.internal.parser.JSONParser;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@RestController
@CrossOrigin("*")
public class UserController {

    @Value("${mapple.baseUrl}")
    String baseUrl;

    @GetMapping("/")
    public String hello() {

        return "hello";
    }

    @PostMapping("/")
    public JSONObject userInfo(@RequestBody JSONObject params){
        System.out.println(params.toJSONString());
        String userInfoUrl = baseUrl + "u/"+"제오스UG";
        JSONObject json = new JSONObject();
        try{
            Document doc = Jsoup.connect(userInfoUrl).get();

            Elements elem = doc.select("div.user-profile");
            String imgUrl = null;
            String username1 = null;
            String level = null;
            String work;
            String popular;
            String guild ;
            String totalRank ;
            String worldRank ;
            String workRankWorld ;
            String workRankAll;
            for (Element e : elem) {

                 imgUrl =e.select("img.character-image").attr("src"); // 아바타 이미지
                 username1 = e.select("b.align-middle").get(0).text();// 유저 네임
                 level = e.select("ul.user-summary-list").select("li.user-summary-item").get(0).text();// 레벨
                 work= e.select("ul.user-summary-list").select("li.user-summary-item").get(1).text(); //직업
                 popular = e.select("ul.user-summary-list").select("li.user-summary-item").get(2).text(); // 인기도
                 guild = e.select("div.col-lg-8").select("a.text-yellow").text(); // 길드
                 totalRank = e.select("div.col-lg-8").select("span").get(2).text(); // 전체 랭킹
                 worldRank = e.select("div.col-lg-8").select("span").get(3).text(); // 월드 랭킹
                 workRankWorld = e.select("div.col-lg-8").select("span").get(4).text(); // 월드 직업 랭킹
                 workRankAll = e.select("div.col-lg-8").select("span").get(5).text(); // 전체 직업 랭킹


            }
          //json 에 json array(JSON) 그안에 json
            JSONObject basicCard = new JSONObject();
            JSONObject buttons = new JSONObject();
            JSONObject response2 = new JSONObject();
            JSONObject response3 = new JSONObject();
            JSONObject response4 = new JSONObject();
            JSONObject templates = new JSONObject();

            JSONArray outputs = new JSONArray();

            JSONArray buttons1 = new JSONArray();

            response4.put("action","webLink");
            response4.put("label","메이플 gg 이동");
            response4.put("webLinkUrl",baseUrl);

            buttons1.add(response4);

            response3.put("imageUrl",imgUrl);

            response2.put("thumbnail",response3);
            response2.put("description",username1);
            response2.put("title","캐릭터 정보");




            basicCard.put("basicCard",response2);
            basicCard.put("buttons",buttons1);

            outputs.add(basicCard);



            templates.put("outputs",outputs);

            json.put("templates",templates);
            json.put("version","2.0");

        }catch (Exception e){
            e.printStackTrace();
        }
        System.out.println(json.toJSONString());
        return json;
    }


}
