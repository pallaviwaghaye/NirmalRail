package com.webakruti.nirmalrail.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by DELL on 9/28/2018.
 */

public class MyRequestStatusResponse {


    public class Datum {

        @SerializedName("complaint_date")
        @Expose
        private String complaintDate;
        @SerializedName("id")
        @Expose
        private Integer id;
        @SerializedName("at_platform")
        @Expose
        private String atPlatform;
        @SerializedName("before_img_url")
        @Expose
        private String beforeImgUrl;
        @SerializedName("after_img_url")
        @Expose
        private String afterImgUrl;
        @SerializedName("description")
        @Expose
        private String description;
        @SerializedName("status")
        @Expose
        private String status;
        @SerializedName("stationname")
        @Expose
        private String stationname;
        @SerializedName("servicename")
        @Expose
        private String servicename;

        public String getComplaintDate() {
            return complaintDate;
        }

        public void setComplaintDate(String complaintDate) {
            this.complaintDate = complaintDate;
        }

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public String getAtPlatform() {
            return atPlatform;
        }

        public void setAtPlatform(String atPlatform) {
            this.atPlatform = atPlatform;
        }

        public String getBeforeImgUrl() {
            return beforeImgUrl;
        }

        public void setBeforeImgUrl(String beforeImgUrl) {
            this.beforeImgUrl = beforeImgUrl;
        }

        public String getAfterImgUrl() {
            return afterImgUrl;
        }

        public void setAfterImgUrl(String afterImgUrl) {
            this.afterImgUrl = afterImgUrl;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getStationname() {
            return stationname;
        }

        public void setStationname(String stationname) {
            this.stationname = stationname;
        }

        public String getServicename() {
            return servicename;
        }

        public void setServicename(String servicename) {
            this.servicename = servicename;
        }

    }

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

        @SerializedName("status")
        @Expose
        private Boolean status;
        @SerializedName("data")
        @Expose
        private List<Datum> data = null;

        public Boolean getStatus() {
            return status;
        }

        public void setStatus(Boolean status) {
            this.status = status;
        }

        public List<Datum> getData() {
            return data;
        }

        public void setData(List<Datum> data) {
            this.data = data;
        }

    }
}
