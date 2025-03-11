package com.caa.kata.model.product;



import java.math.BigDecimal;

public record Product(
        /**
         * label
         */
        String label,
        /**
         * price
         */
        BigDecimal price,
        /**
         * family product
         * Ex : Basic, Book, Other
         */
        ProductFamily productFamily,
        /**
         * is imported
         * indicates if the product has been imported
         */
        Boolean imported
) {
}
