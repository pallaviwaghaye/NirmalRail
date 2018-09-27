package com.webakruti.nirmalrail.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class RailwayCategoryResponse implements Serializable{

    public RailwayCategoryResponse() {
    }

    public RailwayCategoryResponse(Success success) {
        this.success = success;
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


    public static class Success {

        @SerializedName("status")
        @Expose
        private Boolean status;
        @SerializedName("data")
        @Expose
        private List<Category> data = null;

        public Boolean getStatus() {
            return status;
        }

        public void setStatus(Boolean status) {
            this.status = status;
        }

        public List<Category> getData() {
            return data;
        }

        public void setData(List<Category> data) {
            this.data = data;
        }

    }

    public static class Category {

        @SerializedName("id")
        @Expose
        private Integer id;
        @SerializedName("name")
        @Expose
        private String name;
        @SerializedName("icon_path_one")
        @Expose
        private String iconPathOne;
        @SerializedName("icon_path_two")
        @Expose
        private String iconPathTwo;
        @SerializedName("at_plateform")
        @Expose
        private String atPlateform;

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

        public String getIconPathOne() {
            return iconPathOne;
        }

        public void setIconPathOne(String iconPathOne) {
            this.iconPathOne = iconPathOne;
        }

        public String getIconPathTwo() {
            return iconPathTwo;
        }

        public void setIconPathTwo(String iconPathTwo) {
            this.iconPathTwo = iconPathTwo;
        }

        public String getAtPlateform() {
            return atPlateform;
        }

        public void setAtPlateform(String atPlateform) {
            this.atPlateform = atPlateform;
        }

    }


}
