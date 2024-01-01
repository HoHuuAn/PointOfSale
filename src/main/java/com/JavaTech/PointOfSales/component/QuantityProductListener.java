package com.JavaTech.PointOfSales.component;

import com.JavaTech.PointOfSales.model.QuantityProduct;
import com.google.gson.Gson;
import jakarta.persistence.PostLoad;
import jakarta.persistence.PostUpdate;
import com.google.gson.JsonObject;
import jakarta.persistence.PreUpdate;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class QuantityProductListener {

    private final SimpMessagingTemplate messagingTemplate;
    private JsonObject jsonResponse = new JsonObject();
    private Map<Long, Integer> oldQuantities = new HashMap<>();
    public QuantityProductListener(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
        this.jsonResponse = new JsonObject();
    }
    @PostLoad
    public void onPostLoad(QuantityProduct quantityProduct) {
        this.jsonResponse.addProperty("oldQuantity", quantityProduct.getQuantity());
    }

    @PostUpdate
    public void onPostUpdate(QuantityProduct quantityProduct) {
        this.jsonResponse.addProperty("productId", quantityProduct.getProduct().getId());
        this.jsonResponse.addProperty("branch", quantityProduct.getBranch().getName());
        this.jsonResponse.addProperty("quantity", quantityProduct.getQuantity());

        messagingTemplate.convertAndSend("/topic/quantity-updates", new Gson().toJson(this.jsonResponse));
    }
}