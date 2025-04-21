package com.example.kenya.model;

import java.util.List;

public class ApiResponseList {
    private boolean success;
    private List<Producto> data;

    public boolean isSuccess() {
        return success;
    }
    public void setSuccess(boolean success) {
        this.success = success;
    }
    public List<Producto> getData() {
        return data;
    }
    public void setData(List<Producto> data) {
        this.data = data;
    }
}
