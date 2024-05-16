package com.strip_springboot.Springboot_stripe.controller;

import com.stripe.exception.SignatureVerificationException;
import com.stripe.model.Event;
import com.stripe.model.EventDataObjectDeserializer;
import com.stripe.model.StripeObject;
import com.stripe.net.Webhook;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api")
@RestController
public class WebhookController {
    @Value("${WEBHOOK_SECRET}")
    String webHk_secret;

    @PostMapping(value = "/webhook")
    public ResponseEntity<String> webhook(@RequestBody String payload, @RequestHeader("Stripe-Signature") String sigHeader) {
        Event event = null;
        try {
            event = Webhook.constructEvent(payload, sigHeader, webHk_secret);
        } catch (SignatureVerificationException e) {
            System.out.println("Failed signature verification");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }

        EventDataObjectDeserializer dataObjectDeserializer = event.getDataObjectDeserializer();
        StripeObject stripeObject = null;

        if (dataObjectDeserializer.getObject().isPresent()) {
            stripeObject = dataObjectDeserializer.getObject().get();
        } else {
            // Deserialization failed, probably due to an API version mismatch.
            // Refer to the Javadoc documentation on `EventDataObjectDeserializer` for
            // instructions on how to handle this case, or return an error here.
        }

        switch (event.getType()) {
            //TODO event for delievery
            case "payment_intent.succeeded":
                System.out.println("payment intent succeed");
                // ...
                break;
            case "payment_method.attached":
                System.out.println("payment intent attached");
                // ...
                break;
            // ... handle other event types
            case "checkout.session.completed":
                System.out.println("Event Object here"+event.getData().getObject());
            default:
                // Unexpected event type
                System.out.println("event_type" + event.getType().toString());
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
        return new ResponseEntity<>("Success", HttpStatus.OK);
    }
}