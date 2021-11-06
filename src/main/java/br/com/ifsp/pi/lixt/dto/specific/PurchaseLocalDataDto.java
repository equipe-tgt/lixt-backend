package br.com.ifsp.pi.lixt.dto.specific;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class PurchaseLocalDataDto {

    private String purchaseLocalName;

    private Integer purchaseAmount;

    private LocalDateTime firstPurchase;

    private LocalDateTime lastPurchase;

    private BigDecimal averageValue;

    private BigDecimal totalValue;
}
