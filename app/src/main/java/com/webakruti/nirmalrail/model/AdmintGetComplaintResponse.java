package com.webakruti.nirmalrail.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AdmintGetComplaintResponse {

    @SerializedName("success")
    @Expose
    private Success success;

    public Success getSuccess() {
        return success;
    }

    public void setSuccess(Success success) {
        this.success = success;
    }


    public static class Success {

        @SerializedName("status")
        @Expose
        private Boolean status;
        @SerializedName("complaint")
        @Expose
        private Complaint complaint;

        public Boolean getStatus() {
            return status;
        }

        public void setStatus(Boolean status) {
            this.status = status;
        }

        public Complaint getComplaint() {
            return complaint;
        }

        public void setComplaint(Complaint complaint) {
            this.complaint = complaint;
        }

    }


    public static class Complaint {

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
        @SerializedName("comment")
        @Expose
        private String comment;
        @SerializedName("type")
        @Expose
        private String type;
        @SerializedName("stationname")
        @Expose
        private String stationname;
        @SerializedName("servicename")
        @Expose
        private String servicename;
        @SerializedName("colonyname")
        @Expose
        private Object colonyname;
        @SerializedName("name")
        @Expose
        private String name;
        @SerializedName("mobile")
        @Expose
        private String mobile;
        @SerializedName("email")
        @Expose
        private Object email;

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

        public String getComment() {
            return comment;
        }

        public void setComment(String comment) {
            this.comment = comment;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
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

        public Object getColonyname() {
            return colonyname;
        }

        public void setColonyname(Object colonyname) {
            this.colonyname = colonyname;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public Object getEmail() {
            return email;
        }

        public void setEmail(Object email) {
            this.email = email;
        }

    }

}
