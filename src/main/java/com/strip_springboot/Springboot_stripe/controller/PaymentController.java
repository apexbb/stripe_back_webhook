package com.strip_springboot.Springboot_stripe.controller;

import com.strip_springboot.Springboot_stripe.dto.RequestDTO;
import com.strip_springboot.Springboot_stripe.util.CustomerUtil;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.Customer;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/pay")
@CrossOrigin(origins = "http://localhost:3456")
@RestController
public class PaymentController {
    @Value("${STRIPE_API_KEY}")
    private String myApikey;

    @Value("${CLIENT_BASE_URL}")
    private String client_url;

    @PostMapping("/checkout/hosted")
    String hostedCheckout(@RequestBody RequestDTO requestDTO) throws StripeException {
        Stripe.apiKey = myApikey;
        String clientBaseURL = client_url;
        Customer customer = CustomerUtil.getCustomerName(requestDTO.getCustomerName());
        System.out.println("return customer"+ customer);
                SessionCreateParams params =
                SessionCreateParams.builder()
                        .setMode(SessionCreateParams.Mode.PAYMENT)
                        .setCustomer(requestDTO.getCustomerName())
                        .setSuccessUrl(clientBaseURL + "/success?session_id={CHECKOUT_SESSION_ID}")
                        .setCancelUrl(clientBaseURL + "/failure")
                        .addLineItem(
                            SessionCreateParams.LineItem.builder()
                                .setQuantity(1L)
                                .setPriceData(
                                    SessionCreateParams.LineItem.PriceData.builder()
                                        .setCurrency("usd")
                                        .setUnitAmount(2000L)
                                        .setProductData(
                                                SessionCreateParams.LineItem.PriceData.ProductData.builder()
                                                        .setName("T-shirt")
                                                        .build())
                                        .build())
                                .build())
                            .build();
// Product from Dashboard
//        for (Product product : requestDTO.getItems()) {
//            paramsBuilder.addLineItem(
//                    SessionCreateParams.LineItem.builder()
//                            .setQuantity(1L)
//                            .setPriceData(
//                                    PriceData.builder()
//                                            .setProductData(
//                                                    PriceData.ProductData.builder()
//                                                            .putMetadata("app_id", product.getId())
//                                                            .setName(product.getName())
//                                                            .build()
//                                            )
//                                            .setCurrency(ProductDAO.getProduct(product.getId()).getDefaultPriceObject().getCurrency())
//                                            .setUnitAmountDecimal(ProductDAO.getProduct(product.getId()).getDefaultPriceObject().getUnitAmountDecimal())
//                                            .build())
//                            .build());
//        }
//    }
        Session session = Session.create(params);
        return session.getUrl();
    }
}
