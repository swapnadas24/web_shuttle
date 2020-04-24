package com.commutec.qa.dao;

import java.util.concurrent.ThreadLocalRandom;

import com.commutec.qa.model.ShuttleData;

public class ShuttleDao {

    private static String[] cityAreaList = { "chembur", "vashi", "aroli", "kalyan", "andheri", "goregaon", "jogeshwari",
            "kharghar" };
    private static String[] landmarkAreaList = { "railwaystation", "hdfcbank", "icicibank", "townschool", "itpark" };

    public ShuttleData prepareShuttleRouteData() {

        int stopAreaIndex = ThreadLocalRandom.current().nextInt(0, cityAreaList.length);
        int locationIndex = ThreadLocalRandom.current().nextInt(0, cityAreaList.length);
        int landmarkIndex = ThreadLocalRandom.current().nextInt(0, landmarkAreaList.length);
        int localityIndex = ThreadLocalRandom.current().nextInt(0, landmarkAreaList.length);

        ShuttleData shuttleObj = new ShuttleData();

        shuttleObj.setStopArea(cityAreaList[stopAreaIndex]);
        shuttleObj.setLocationArea(cityAreaList[locationIndex]);
        shuttleObj.setLandmarkArea(landmarkAreaList[landmarkIndex]);
        shuttleObj.setLocalityArea(landmarkAreaList[localityIndex]);

        return shuttleObj;
    }
}