package me.maplechat.maplekakao.controller;

import org.json.simple.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

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
                System.out.println(e.select("img.character-image").attr("src")); //Avatar Image
                System.out.println(e.select("b.align-middle").get(0).text()); // username

                System.out.println(e.select("ul.user-summary-list").select("li.user-summary-item").get(0).text()); // Lv
                System.out.println(e.select("ul.user-summary-list").select("li.user-summary-item").get(1).text()); // 직업
                System.out.println(e.select("ul.user-summary-list").select("li.user-summary-item").get(2).text()); // 인기도

                System.out.println(e.select("div.col-lg-8").select("a.text-yellow").text()); //길드 명

                System.out.println(e.select("div.col-lg-8").select("span").get(2).text());// 종합랭킹
                System.out.println(e.select("div.col-lg-8").select("span").get(3).text());// 월드 랭킹
                System.out.println(e.select("div.col-lg-8").select("span").get(4).text());// 직업 랭킹(월드)
                System.out.println(e.select("div.col-lg-8").select("span").get(5).text());// 직업 랭킹(전체)
                imgUrl =e.select("img.character-image").attr("src");
                 username1 = e.select("b.align-middle").get(0).text();
                 level = e.select("ul.user-summary-list").select("li.user-summary-item").get(0).text();
                 work= e.select("ul.user-summary-list").select("li.user-summary-item").get(1).text();
                 popular = e.select("ul.user-summary-list").select("li.user-summary-item").get(2).text();
                 guild = e.select("div.col-lg-8").select("a.text-yellow").text();
                 totalRank = e.select("div.col-lg-8").select("span").get(2).text();
                 worldRank = e.select("div.col-lg-8").select("span").get(3).text();
                 workRankWorld = e.select("div.col-lg-8").select("span").get(4).text();
                 workRankAll = e.select("div.col-lg-8").select("span").get(5).text();


            }
            json.put("imgUrl",imgUrl);
            json.put("username1",username1);
            json.put("level",level);
        }catch (Exception e){
            e.printStackTrace();
        }

        return json;
    }


}
