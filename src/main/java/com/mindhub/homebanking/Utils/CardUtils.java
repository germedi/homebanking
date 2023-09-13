package com.mindhub.homebanking.Utils;

import com.mindhub.homebanking.models.CardColor;
import com.mindhub.homebanking.models.Client;

import java.util.Random;

public final class CardUtils {


    // Método para verificar si un cliente ya tiene una tarjeta del mismo color
    public static boolean hasCardWithColor(Client client, CardColor color) {
        return client.getCards().stream().anyMatch(card -> card.getColor() == color);
    }
    // Método para generar un número de tarjeta aleatorio
    public static String generateCardNumber() {
        Random random = new Random();
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                sb.append(random.nextInt(10));
            }
            if (i < 3) {
                sb.append("-");
            }
        }

        return sb.toString();
    }
    // Método para generar un número CVV aleatorio
    public static int generateCvv() {
        Random random = new Random();
        return random.nextInt(900) + 100;
    }

}
