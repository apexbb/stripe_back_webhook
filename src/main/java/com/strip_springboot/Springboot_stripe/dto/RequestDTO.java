package com.strip_springboot.Springboot_stripe.dto;

import com.stripe.model.Product;

public class RequestDTO {
    String customerName;
    public String getCustomerName() {
        return customerName;
    }
}