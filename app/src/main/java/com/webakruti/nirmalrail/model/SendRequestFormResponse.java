package com.webakruti.nirmalrail.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

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



    public class Place {

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

    public class Platform {

        @SerializedName("1")
        @Expose
        private List<_1> _1 = null;
        @SerializedName("2")
        @Expose
        private List<_2> _2 = null;
        @SerializedName("3")
        @Expose
        private List<_3> _3 = null;
        @SerializedName("4")
        @Expose
        private List<_4> _4 = null;
        @SerializedName("5")
        @Expose
        private List<_5> _5 = null;
        @SerializedName("6")
        @Expose
        private List<_6> _6 = null;
        @SerializedName("7")
        @Expose
        private List<_7> _7 = null;
        @SerializedName("8")
        @Expose
        private List<_8> _8 = null;
        @SerializedName("9")
        @Expose
        private List<_9> _9 = null;
        @SerializedName("10")
        @Expose
        private List<_10> _10 = null;
        @SerializedName("11")
        @Expose
        private List<_11> _11 = null;
        @SerializedName("12")
        @Expose
        private List<_12> _12 = null;
        @SerializedName("13")
        @Expose
        private List<_13> _13 = null;
        @SerializedName("14")
        @Expose
        private List<_14> _14 = null;
        @SerializedName("15")
        @Expose
        private List<_15> _15 = null;
        @SerializedName("16")
        @Expose
        private List<_16> _16 = null;
        @SerializedName("17")
        @Expose
        private List<_17> _17 = null;
        @SerializedName("18")
        @Expose
        private List<_18> _18 = null;
        @SerializedName("19")
        @Expose
        private List<_19> _19 = null;
        @SerializedName("20")
        @Expose
        private List<_20> _20 = null;

        public List<_1> get1() {
            return _1;
        }

        public void set1(List<_1> _1) {
            this._1 = _1;
        }

        public List<_2> get2() {
            return _2;
        }

        public void set2(List<_2> _2) {
            this._2 = _2;
        }

        public List<_3> get3() {
            return _3;
        }

        public void set3(List<_3> _3) {
            this._3 = _3;
        }

        public List<_4> get4() {
            return _4;
        }

        public void set4(List<_4> _4) {
            this._4 = _4;
        }

        public List<_5> get5() {
            return _5;
        }

        public void set5(List<_5> _5) {
            this._5 = _5;
        }

        public List<_6> get6() {
            return _6;
        }

        public void set6(List<_6> _6) {
            this._6 = _6;
        }

        public List<_7> get7() {
            return _7;
        }

        public void set7(List<_7> _7) {
            this._7 = _7;
        }

        public List<_8> get8() {
            return _8;
        }

        public void set8(List<_8> _8) {
            this._8 = _8;
        }

        public List<_9> get9() {
            return _9;
        }

        public void set9(List<_9> _9) {
            this._9 = _9;
        }

        public List<_10> get10() {
            return _10;
        }

        public void set10(List<_10> _10) {
            this._10 = _10;
        }

        public List<_11> get11() {
            return _11;
        }

        public void set11(List<_11> _11) {
            this._11 = _11;
        }

        public List<_12> get12() {
            return _12;
        }

        public void set12(List<_12> _12) {
            this._12 = _12;
        }

        public List<_13> get13() {
            return _13;
        }

        public void set13(List<_13> _13) {
            this._13 = _13;
        }

        public List<_14> get14() {
            return _14;
        }

        public void set14(List<_14> _14) {
            this._14 = _14;
        }

        public List<_15> get15() {
            return _15;
        }

        public void set15(List<_15> _15) {
            this._15 = _15;
        }

        public List<_16> get16() {
            return _16;
        }

        public void set16(List<_16> _16) {
            this._16 = _16;
        }

        public List<_17> get17() {
            return _17;
        }

        public void set17(List<_17> _17) {
            this._17 = _17;
        }

        public List<_18> get18() {
            return _18;
        }

        public void set18(List<_18> _18) {
            this._18 = _18;
        }

        public List<_19> get19() {
            return _19;
        }

        public void set19(List<_19> _19) {
            this._19 = _19;
        }

        public List<_20> get20() {
            return _20;
        }

        public void set20(List<_20> _20) {
            this._20 = _20;
        }

    }

    public class Station {

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

    }

    public class Success {

        @SerializedName("places")
        @Expose
        private List<Place> places = null;
        @SerializedName("station")
        @Expose
        private List<Station> station = null;
        @SerializedName("platform")
        @Expose
        private Platform platform;

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

        public Platform getPlatform() {
            return platform;
        }

        public void setPlatform(Platform platform) {
            this.platform = platform;
        }

    }

    public class _1 {

        @SerializedName("platform")
        @Expose
        private String platform;

        public String getPlatform() {
            return platform;
        }

        public void setPlatform(String platform) {
            this.platform = platform;
        }

    }

    public class _10 {

        @SerializedName("platform")
        @Expose
        private String platform;

        public String getPlatform() {
            return platform;
        }

        public void setPlatform(String platform) {
            this.platform = platform;
        }

    }


    public class _11 {

        @SerializedName("platform")
        @Expose
        private String platform;

        public String getPlatform() {
            return platform;
        }

        public void setPlatform(String platform) {
            this.platform = platform;
        }

    }

    public class _12 {

        @SerializedName("platform")
        @Expose
        private String platform;

        public String getPlatform() {
            return platform;
        }

        public void setPlatform(String platform) {
            this.platform = platform;
        }

    }

    public class _13 {

        @SerializedName("platform")
        @Expose
        private String platform;

        public String getPlatform() {
            return platform;
        }

        public void setPlatform(String platform) {
            this.platform = platform;
        }

    }


    public class _14 {

        @SerializedName("platform")
        @Expose
        private String platform;

        public String getPlatform() {
            return platform;
        }

        public void setPlatform(String platform) {
            this.platform = platform;
        }

    }

    public class _15 {

        @SerializedName("platform")
        @Expose
        private String platform;

        public String getPlatform() {
            return platform;
        }

        public void setPlatform(String platform) {
            this.platform = platform;
        }

    }


    public class _16 {

        @SerializedName("platform")
        @Expose
        private String platform;

        public String getPlatform() {
            return platform;
        }

        public void setPlatform(String platform) {
            this.platform = platform;
        }

    }


    public class _17 {

        @SerializedName("platform")
        @Expose
        private String platform;

        public String getPlatform() {
            return platform;
        }

        public void setPlatform(String platform) {
            this.platform = platform;
        }

    }


    public class _18 {

        @SerializedName("platform")
        @Expose
        private String platform;

        public String getPlatform() {
            return platform;
        }

        public void setPlatform(String platform) {
            this.platform = platform;
        }

    }

    public class _19 {

        @SerializedName("platform")
        @Expose
        private String platform;

        public String getPlatform() {
            return platform;
        }

        public void setPlatform(String platform) {
            this.platform = platform;
        }

    }


    public class _2 {

        @SerializedName("platform")
        @Expose
        private String platform;

        public String getPlatform() {
            return platform;
        }

        public void setPlatform(String platform) {
            this.platform = platform;
        }

    }

    public class _20 {

        @SerializedName("platform")
        @Expose
        private String platform;

        public String getPlatform() {
            return platform;
        }

        public void setPlatform(String platform) {
            this.platform = platform;
        }

    }

    public class _3 {

        @SerializedName("platform")
        @Expose
        private String platform;

        public String getPlatform() {
            return platform;
        }

        public void setPlatform(String platform) {
            this.platform = platform;
        }

    }

    public class _4 {

        @SerializedName("platform")
        @Expose
        private String platform;

        public String getPlatform() {
            return platform;
        }

        public void setPlatform(String platform) {
            this.platform = platform;
        }

    }

    public class _5 {

        @SerializedName("platform")
        @Expose
        private String platform;

        public String getPlatform() {
            return platform;
        }

        public void setPlatform(String platform) {
            this.platform = platform;
        }

    }

    public class _6 {

        @SerializedName("platform")
        @Expose
        private String platform;

        public String getPlatform() {
            return platform;
        }

        public void setPlatform(String platform) {
            this.platform = platform;
        }

    }

    public class _7 {

        @SerializedName("platform")
        @Expose
        private String platform;

        public String getPlatform() {
            return platform;
        }

        public void setPlatform(String platform) {
            this.platform = platform;
        }

    }

    public class _8 {

        @SerializedName("platform")
        @Expose
        private String platform;

        public String getPlatform() {
            return platform;
        }

        public void setPlatform(String platform) {
            this.platform = platform;
        }

    }


    public class _9 {

        @SerializedName("platform")
        @Expose
        private String platform;

        public String getPlatform() {
            return platform;
        }

        public void setPlatform(String platform) {
            this.platform = platform;
        }

    }
}
