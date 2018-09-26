package com.webakruti.nirmalrail.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SaveComplaintResponse {

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
        @SerializedName("msg")
        @Expose
        private String msg;

        public Boolean getStatus() {
            return status;
        }

        public void setStatus(Boolean status) {
            this.status = status;
        }

        public String getMsg() {
            return msg;
        }

        public void setMsg(String msg) {
            this.msg = msg;
        }

    }
}
