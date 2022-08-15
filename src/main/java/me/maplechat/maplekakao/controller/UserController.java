package me.maplechat.maplekakao.controller;

import jdk.nashorn.internal.parser.JSONParser;
import me.maplechat.maplekakao.util.TemplateResponse;
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
        TemplateResponse templateResponse = new TemplateResponse();

        System.out.println(params.toJSONString());
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

            templateResponse.addOutput();

        }catch (Exception e){
            e.printStackTrace();
        }
        System.out.println(templateResponse.getPayload());
        return templateResponse.getPayload();
    }


}
