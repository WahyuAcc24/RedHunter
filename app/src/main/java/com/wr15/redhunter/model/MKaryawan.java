package com.wr15.redhunter.model;

public class MKaryawan {
    private boolean status;
    private String message;
    private DataUser data_user;
    private DataUser data_hrd;
    private DataUser data_ceo;

    private DataUser data_ga;


    public DataUser getData_ga() {
        return data_ga;
    }

    public void setData_ga(DataUser data_ga) {
        this.data_ga = data_ga;
    }

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

    public DataUser getData_user() {
        return data_user;
    }

    public void setData_user(DataUser data_user) {
        this.data_user = data_user;
    }

    public DataUser getData_hrd() {
        return data_hrd;
    }

    public void setData_hrd(DataUser data_hrd) {
        this.data_hrd = data_hrd;
    }

    public DataUser getData_ceo() {
        return data_ceo;
    }

    public void setData_ceo(DataUser data_ceo) {
        this.data_ceo = data_ceo;
    }

    public class DataUser {
        private String id;
        private String id_user;
        private String nama_user;
        private String nama_hrd;
        private String nama_ceo;
        private String nama_ga;
        private String jk;
        private String jenis_kelamin;
        private String divisi;
        private String password;


        public String getId_user() {
            return id_user;
        }

        public void setId_user(String id_user) {
            this.id_user = id_user;
        }

        public String getNama_ga() {
            return nama_ga;
        }

        public void setNama_ga(String nama_ga) {
            this.nama_ga = nama_ga;
        }

        public String getNama_ceo() {
            return nama_ceo;
        }

        public void setNama_ceo(String nama_ceo) {
            this.nama_ceo = nama_ceo;
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

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getNama_user() {
            return nama_user;
        }

        public void setNama_user(String nama_user) {
            this.nama_user = nama_user;
        }

        public String getJenis_kelamin() {
            return jenis_kelamin;
        }

        public void setJenis_kelamin(String jenis_kelamin) {
            this.jenis_kelamin = jenis_kelamin;
        }

        public String getDivisi() {
            return divisi;
        }

        public void setDivisi(String divisi) {
            this.divisi = divisi;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

    }
}
