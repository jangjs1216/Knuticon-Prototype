package com.example.firebase1;

import java.util.HashMap;
import java.util.Map;

public class NameChanger {
    HashMap<String, String> nameMap = new HashMap<String, String>();

    NameChanger()
    {
        //Category - clothes
        nameMap.put("girlclothe", "여성패션");
        nameMap.put("manclothe", "남성패션");
        nameMap.put("clothe", "남녀 공용 의류");
        nameMap.put("childclothe", "유아동패션");

        //Category - foods
        nameMap.put("food", "건강식품");
        nameMap.put("fruit", "과일");
        nameMap.put("vegetable", "채소");
        nameMap.put("water", "생수/음료");
        nameMap.put("snack", "과자");
        nameMap.put("noodle", "면/통조림");

        //Category - digitals
        nameMap.put("laptop", "노트북");
        nameMap.put("desktop", "데스크탑");
        nameMap.put("monitor", "모니터");
        nameMap.put("smartphone", "스마트폰");

        //Category - livings
        nameMap.put("detergent", "세탁세제");
        nameMap.put("clean", "수납/정리");
        nameMap.put("health", "건강/의료용품");

        //Category - books
        nameMap.put("book", "도서");
        nameMap.put("music", "음반");
        nameMap.put("dvd", "DVD");

        //Category - offices
        nameMap.put("pencil", "문구류");
        nameMap.put("card", "카드/엽서");
        nameMap.put("album", "앨범");
    }

    public HashMap<String, String> getNameMap() {
        return nameMap;
    }

    public void setNameMap(HashMap<String, String> nameMap) {
        this.nameMap = nameMap;
    }

    public String getChangedName(String key)
    {
        if(nameMap.get(key) != null)
            return nameMap.get(key);
        else
            return key;
    }
}
