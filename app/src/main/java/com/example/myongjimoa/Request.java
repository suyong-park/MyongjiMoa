package com.example.myongjimoa;


import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.TimeZone;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class Request { // 중복 코드 static메소드로바꿈

    static HashMap<String, HashMap<String, String>> myMap = initFilterMap();

    static HashMap<String, HashMap<String, String>> initFilterMap() {

        HashMap<String, HashMap<String, String>> map = new HashMap<>();

        HashMap<String, String> 시 = new HashMap();
        HashMap<String, String> 병 = new HashMap();
        HashMap<String, String> 씨 = new HashMap();
        HashMap<String, String> 새 = new HashMap();
        HashMap<String, String> 니 = new HashMap();
        HashMap<String, String> 좆 = new HashMap();
        HashMap<String, String> 애 = new HashMap();

        시.put("시발", "나쁜놈");
        시.put("시벌", "나쁜놈");

        병.put("병신", "아픈");
        병.put("병싄", "아픈");

        씨.put("씨발", "나쁜");

        좆.put("좆", "그곳");

        새.put("새끼", "아가");
        새.put("새기", "아가");

        map.put("시", 시);
        map.put("병", 병);
        map.put("씨", 씨);
        map.put("새", 새);
        map.put("좆", 좆);

        return map;
    }

    static String filter(String text) {
        // 12593 ~ 12622 초성 유니코드
        HashMap<String, String> temp = null;
        String str = "";
        int count = 0;
        for(int i=0; i<text.length(); i++) {
            if((44032 <= (int)text.charAt(i) && (int)text.charAt(i) <= 55203)) { // 한글 유니코드 범위
                if(str.equals("")) {
                    count = 0;
                    temp = myMap.get(text.charAt(i) + ""); // 첫글자가 욕설 중 하나에 해당할때만 수행
                }
                if(temp != null) {
                    str += text.charAt(i);
                    if(temp.get(str) != null) { // 욕설이면 욕설 처리. 순화어 길이차이만큼 계산, 필요없는 인덱스 추가
                        text = text.substring(0, i - count - str.length() + 1) + temp.get(str) + text.substring(i + 1);
                        i = i - count + temp.get(str).length() - str.length(); // key와 value의 길이차이 넘어감
                        str = "";
                    } else if (!str.equals(text.charAt(i) + "") && myMap.get(text.charAt(i) + "") != null) { // 단어가 새로운 욕의 시작이면 temp값 수정, str 초기화 - 첫번째 if문에서 욕설처리가되었다면 그냥 넘어감.
                        temp = myMap.get(text.charAt(i) + "");
                        str = text.charAt(i) + "";
                    }
                }
            }  else count++;
        }
        return text;
    }

    static Retrofit getRetrofitScalars() { // 문자열로 받을 때
        return new Retrofit.Builder()
                .baseUrl(ConnectDB.Base_URL)
                .addConverterFactory(ScalarsConverterFactory.create())
                .build();
    }

    static Retrofit getRetrofitGson() {
        return new Retrofit.Builder()
                .baseUrl(ConnectDB.Base_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    static String getTime() {
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        sdf.setTimeZone(TimeZone.getTimeZone("Asia/Seoul"));
        return sdf.format(date);
    }

    // 다이얼로그 만드는 메소드


    // 갤러리 여는 메소드


    // aws 빌드 메소드

}
