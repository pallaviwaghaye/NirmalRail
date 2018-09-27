package com.webakruti.nirmalrail.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;
import java.util.Map;

import okhttp3.internal.platform.Platform;

/**
 * Created by DELL on 9/27/2018.
 */

public class SendRequestFormResponse {

    @SerializedName("success")
    @Expose
    private Success success;

    public Success getSuccess() {
        return success;
    }

    public void setSuccess(Success success) {
        this.success = success;
    }


    public class Success {

        @SerializedName("places")
        @Expose
        private List<Place> places = null;
        @SerializedName("station")
        @Expose
        private List<Station> station = null;


        // For dyanamic values
        @SerializedName("platform")
        Map<String, List<PlatformList>> platformMap;

        public Map<String, List<PlatformList>> getPlatformMap() {
            return platformMap;
        }

        public void setPlatformMap(Map<String, List<PlatformList>> platformMap) {
            this.platformMap = platformMap;
        }

        public List<Place> getPlaces() {
            return places;
        }

        public void setPlaces(List<Place> places) {
            this.places = places;
        }

        public List<Station> getStation() {
            return station;
        }

        public void setStation(List<Station> station) {
            this.station = station;
        }


    /*    public PlatFormOBJ getPlatform() {
            return platform;
        }

        public void setPlatform(PlatFormOBJ platform) {
            this.platform = platform;
        }*/
    }

    public static class Station {

        @SerializedName("id")
        @Expose
        private Integer id;
        @SerializedName("name")
        @Expose
        private String name;

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }


        @Override
        public String toString() {
            return name;
        }
    }

    public static class Place {

        @SerializedName("id")
        @Expose
        private Integer id;
        @SerializedName("name")
        @Expose
        private String name;
        @SerializedName("at_plateform")
        @Expose
        private String atPlateform;
        @SerializedName("parent")
        @Expose
        private String parent;

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getAtPlateform() {
            return atPlateform;
        }

        public void setAtPlateform(String atPlateform) {
            this.atPlateform = atPlateform;
        }

        public String getParent() {
            return parent;
        }

        public void setParent(String parent) {
            this.parent = parent;
        }

    }


    public static class PlatFormOBJ {

        List<Map<String, List<PlatformList>>> platformMap;

        public List<Map<String, List<PlatformList>>> getPlatformMap() {
            return platformMap;
        }

        public void setPlatformMap(List<Map<String, List<PlatformList>>> platformMap) {
            this.platformMap = platformMap;
        }
    }

    public static class PlatformList {


        private String platform;

        public String getPlatform() {
            return platform;
        }

        public void setPlatform(String platform) {
            this.platform = platform;
        }

        @Override
        public String toString() {
            return platform;
        }
    }
}
