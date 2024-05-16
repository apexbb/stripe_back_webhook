package com.strip_springboot.Springboot_stripe.util;

import com.stripe.exception.StripeException;
import com.stripe.model.Customer;
import com.stripe.model.CustomerSearchResult;
import com.stripe.param.CustomerCreateParams;
import com.stripe.param.CustomerSearchParams;

public class CustomerUtil {
    public static Customer getCustomerName(String name)     throws  StripeException{
        CustomerSearchParams params =
                CustomerSearchParams
                        .builder()
//                        .setQuery("email:'" + "ppl1@nogmail.com" + "'")
                        .setQuery("name:'" + name + "'")
                        .build();

        CustomerSearchResult result = Customer.search(params);
        System.out.println("CustomerSearchResult"+result);
        Customer customer = result.getData().get(0);
        return customer;
        // If no existing customer was found, create a new record
//        if (result.getData().size() == 0) {

    }
    public static Customer findOrCreateCustomer(String email) throws StripeException {

        Customer customer;
        CustomerCreateParams customerCreateParams = CustomerCreateParams.builder()
                .setName("name1")
                .build();
        customer = Customer.create(customerCreateParams);
        return customer;
    }
}