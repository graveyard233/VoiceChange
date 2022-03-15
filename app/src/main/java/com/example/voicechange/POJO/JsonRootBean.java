package com.example.voicechange.POJO;

public class JsonRootBean {

//    private String message;
//    private int status_code;
//    private Data1 data;
//    public void setMessage(String message) {
//        this.message = message;
//    }
//    public String getMessage() {
//        return message;
//    }
//
//    public void setStatus_code(int status_code) {
//        this.status_code = status_code;
//    }
//    public int getStatus_code() {
//        return status_code;
//    }
//
//    public void setData(Data1 data) {
//        this.data = data;
//    }
//    public Data1 getData() {
//        return data;
//    }

    private int status_code;//状态码
    private  String message;//信息
    private  DataBean data;

    public int getStatus_code() {
        return status_code;
    }

    public void setStatus_code(int status_code) {
        this.status_code = status_code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }


    public static class DataBean{

        private String participant_name;//参会人姓名
        private int participant_id;//参会人ID
        private  String department;//参会人部门
        private int total;//议程ID
        private int mid;//会议ID
        private int rid;//会议室ID

        private String token;//token
        private String status;//签到状态
        private int equipment_id;//设备ID


        public int getEquipment_id() {
            return equipment_id;
        }

        public void setEquipment_id(int equipment_id) {
            this.equipment_id = equipment_id;
        }


        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }


        public int getMid() {
            return mid;
        }

        public void setMid(int mid) {
            this.mid = mid;
        }

        public int getRid() {
            return rid;
        }

        public void setRid(int rid) {
            this.rid = rid;
        }



        public int getTotal() {
            return total;
        }

        public void setTotal(int total) {
            this.total = total;
        }

        public String getParticipant_name() {
            return participant_name;
        }

        public void setParticipant_name(String participant_name) {
            this.participant_name = participant_name;
        }

        public int getParticipant_id() {
            return participant_id;
        }

        public void setParticipant_id(int participant_id) {
            this.participant_id = participant_id;
        }

        public String getDepartment() {
            return department;
        }

        public void setDepartment(String department) {
            this.department = department;
        }


        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }
    }
}
