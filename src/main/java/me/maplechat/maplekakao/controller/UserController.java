package me.maplechat.maplekakao.controller;

import me.maplechat.maplekakao.util.TemplateResponse;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
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
        TemplateResponse templateResponse = new TemplateResponse();

        System.out.println(params);
        String userInfoUrl = baseUrl + "u/"+"제오스UG";
        String title = "캐릭터 정보";
        String description = "제오스UG";



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

            json.put("imageUrl",imgUrl);

            templateResponse.addBasicCard(title,description,json);

            templateResponse.addButton("webLink","자세히 보기",userInfoUrl);


        }catch (Exception e){
            e.printStackTrace();
        }
        System.out.println(templateResponse.getPayload());

        JSONParser jsonParser = new JSONParser();
        JSONObject js = null;
        try {
            js = (JSONObject) jsonParser.parse("{\n" +
                    "  \"version\": \"2.0\",\n" +
                    "  \"template\": {\n" +
                    "    \"outputs\": [\n" +
                    "      {\n" +
                    "        \"basicCard\": {\n" +
                    "          \"title\": \"보물상자\",\n" +
                    "          \"description\": \"보물상자 안에는 뭐가 있을까\",\n" +
                    "          \"thumbnail\": {\n" +
                    "            \"imageUrl\": \"https://t1.kakaocdn.net/openbuilder/sample/lj3JUcmrzC53YIjNDkqbWK.jpg\"\n" +
                    "          },\n" +
                    "          \"profile\": {\n" +
                    "            \"imageUrl\": \"https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcT4BJ9LU4Ikr_EvZLmijfcjzQKMRCJ2bO3A8SVKNuQ78zu2KOqM\",\n" +
                    "            \"nickname\": \"보물상자\"\n" +
                    "          },\n" +
                    "          \"social\": {\n" +
                    "            \"like\": 1238,\n" +
                    "            \"comment\": 8,\n" +
                    "            \"share\": 780\n" +
                    "          },\n" +
                    "          \"buttons\": [\n" +
                    "            {\n" +
                    "              \"action\": \"message\",\n" +
                    "              \"label\": \"열어보기\",\n" +
                    "              \"messageText\": \"짜잔! 우리가 찾던 보물입니다\"\n" +
                    "            },\n" +
                    "            {\n" +
                    "              \"action\":  \"webLink\",\n" +
                    "              \"label\": \"구경하기\",\n" +
                    "              \"webLinkUrl\": \"https://e.kakao.com/t/hello-ryan\"\n" +
                    "            }\n" +
                    "          ]\n" +
                    "        }\n" +
                    "      }\n" +
                    "    ]\n" +
                    "  }\n" +
                    "}");

        } catch (ParseException e) {
            e.printStackTrace();
        }

        return js;
    }


}
