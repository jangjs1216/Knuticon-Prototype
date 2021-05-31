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
        nameMap.put("scaramelt", "스타벅스 카라멜마끼아또T 5600");

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
