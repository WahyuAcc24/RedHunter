package com.wr15.redhunter.model;

public class MHrd {
    private boolean status;
    private String message;
    private DataUser data_hrd;


    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public DataUser getData_hrd() {
        return data_hrd;
    }

    public class DataUser {
        private String id;
        private String nama_hrd;
        private String jk;


        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getNama_hrd() {
            return nama_hrd;
        }

        public void setNama_hrd(String nama_hrd) {
            this.nama_hrd = nama_hrd;
        }

        public String getJk() {
            return jk;
        }

        public void setJk(String jk) {
            this.jk = jk;
        }
    }
}
