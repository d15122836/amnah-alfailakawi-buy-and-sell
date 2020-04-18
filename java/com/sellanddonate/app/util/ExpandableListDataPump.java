package com.sellanddonate.app.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ExpandableListDataPump {
    public static HashMap<String, List<String>> getData() {
        HashMap<String, List<String>> expandableListDetail = new HashMap<String, List<String>>();

        List<String> detail1 = new ArrayList<String>();
        detail1.add("Yes, you can sell and donate both");


        List<String> detail2 = new ArrayList<String>();
        detail2.add("Yes, you can sell and buy simultaneously");


        List<String> detail3 = new ArrayList<String>();
        detail3.add("Its easy and fast");
        detail3.add("secure encryption using firebase");
        detail3.add("Large communty Support");

        List<String> detail4= new ArrayList<String>();
        detail4.add("Final Year Project");
        detail4.add("Develop by: Amna");
        detail4.add("Large community Support");

        List<String> detail5= new ArrayList<String>();
        detail5.add("Beta Version 1.0.0");


        expandableListDetail.put("Can I sell and donate in this app ?", detail1);
        expandableListDetail.put("Can I sell and buy both simultaneously ?", detail2);
        expandableListDetail.put("What are advantages of using this app", detail3);
        expandableListDetail.put("About App", detail4);
        expandableListDetail.put("Version", detail5);
        return expandableListDetail;
    }
}
