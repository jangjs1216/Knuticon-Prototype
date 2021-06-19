package com.example.firebase1;

import java.util.HashMap;
import java.util.Map;

public class NameChanger {
    HashMap<String, String> nameMap = new HashMap<String, String>();

    NameChanger()
    {
        //Category - cafe
        nameMap.put("angelinus", "엔젤리너스");
        nameMap.put("ediya", "이디야");
        nameMap.put("gongcha", "공차");
        nameMap.put("hollys", "할리스커피");

        //Starbucks
        nameMap.put("starbucks", "스타벅스");
        nameMap.put("samericanot", "스타벅스 카페아메리카노T 4100");
        nameMap.put("scafelattet", "스타벅스 카페라떼T 4600");
        nameMap.put("scafemochat", "스타벅스 카페모카T 5100");
        nameMap.put("ssigchocot", "스타벅스 시그니처초콜릿T 5300");
        nameMap.put("scaramelt", "스타벅스 카라멜마끼아또T 5600");
        nameMap.put("sjavat", "스타벅스 자바칩프라푸치노T 6100");
        nameMap.put("sfreeallt", "스타벅스 무료음료쿠폰T 7100");

        nameMap.put("twosome", "투썸플레이스");
        nameMap.put("icedamericano", "아이스아메리카노");


        //Category - convenient
        //GS25
        nameMap.put("gs25", "GS25");
        nameMap.put("sgiftcard5000", "GS25상품권 5000원권");

        //CU
        nameMap.put("cu", "CU");
        nameMap.put("cgiftcard5000", "CU25상품권 5000원권");

        //Ministop
        nameMap.put("ministop", "미니스탑");
        nameMap.put("mgiftcard5000", "미니스탑 상품권 5000원권");

        //세븐일레븐
        nameMap.put("seven", "세븐일레븐");
        nameMap.put("sgiftcard5000", "세븐일레븐 상품권 5000원권");

        //Category - fastfood
        //롯데리아
        nameMap.put("lotteria", "롯데리아");

        //버거킹
        nameMap.put("burgurking", "버거킹");

        //맥도날드
        nameMap.put("macs", "맥도날드");

        //맘스터치
        nameMap.put("moms", "맘스터치");

        //도미노피자
        nameMap.put("domino", "도미노피자");

        //미스터피자
        nameMap.put("mister", "미스터피자");

        //BHC
        nameMap.put("bhc", "BHC");

        //BBQ
        nameMap.put("bbq", "BBQ");

        //KFC
        nameMap.put("kfc", "KFC");

        //Category - giftcard
        //휴대폰 상품권
        nameMap.put("phone", "데이터");

        //올리브영
        nameMap.put("olive", "올리브영");

        //토니모리
        nameMap.put("tonimoly", "토니모리");

        //미샤
        nameMap.put("missha", "미샤");

        //랄라블라
        nameMap.put("lalavla", "랄라블라");

        //Category - movie
        //CGV
        nameMap.put("cgv", "CGV");

        //메가박스
        nameMap.put("mega", "메가박스");

        //롯데시네마
        nameMap.put("lottesine", "롯데시네마");

        //Category - icecream
        //배스킨라빈스
        nameMap.put("baskin", "배스킨라빈스");

        //하겐다즈
        nameMap.put("hagen", "하겐다즈");

        //나뚜루
        nameMap.put("naturr", "나뚜루");
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
