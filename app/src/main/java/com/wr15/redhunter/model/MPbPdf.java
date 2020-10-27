package com.wr15.redhunter.model;

public class MPbPdf {
    private boolean status;
    private String message;


    private DataBrg data_brg;



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

    public DataBrg getData_brg() {
        return data_brg;
    }

    public void setData_brg(DataBrg data_brg) {
        this.data_brg = data_brg;
    }

    public class DataBrg {
        private String id;
        private String id_user;
        private String nama_user;
        private String nama_brg;
        private String satuan_brg;
        private String harga_brg;
        private String jumlah_brg;
        private String alasan;
        private String divisi;
        private String tanggal;
        private String status;


        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getId_user() {
            return id_user;
        }

        public void setId_user(String id_user) {
            this.id_user = id_user;
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

        public String getNama_brg() {
            return nama_brg;
        }

        public void setNama_brg(String nama_brg) {
            this.nama_brg = nama_brg;
        }

        public String getSatuan_brg() {
            return satuan_brg;
        }

        public void setSatuan_brg(String satuan_brg) {
            this.satuan_brg = satuan_brg;
        }

        public String getHarga_brg() {
            return harga_brg;
        }

        public void setHarga_brg(String harga_brg) {
            this.harga_brg = harga_brg;
        }

        public String getJumlah_brg() {
            return jumlah_brg;
        }

        public void setJumlah_brg(String jumlah_brg) {
            this.jumlah_brg = jumlah_brg;
        }

        public String getAlasan() {
            return alasan;
        }

        public void setAlasan(String alasan) {
            this.alasan = alasan;
        }

        public String getDivisi() {
            return divisi;
        }

        public void setDivisi(String divisi) {
            this.divisi = divisi;
        }

        public String getTanggal() {
            return tanggal;
        }

        public void setTanggal(String tanggal) {
            this.tanggal = tanggal;
        }
    }
}
