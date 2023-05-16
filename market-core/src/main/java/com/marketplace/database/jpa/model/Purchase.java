package com.marketplace.database.jpa.model;

import com.marketplace.constant.OrderStatus;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@Entity
@SequenceGenerator(name = "purchase_gen", sequenceName = "purchase_gen",  initialValue = 100000)
@Table(name = "t_purchase")
public class Purchase {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "purchase_gen")
    private Long id;

    @Column(name = "price")
    private Double price;

    @Column(name = "item")
    private String item;

    @Column(name = "name")
    private String name;

    @Column(name = "instruction")
    private String instruction;

    @Column(name = "product_id")
    private Long productId;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    @ManyToOne
    @JoinColumn(name = "buyer_id")
    private User buyer;

    @ManyToOne
    @JoinColumn(name = "seller_id")
    private User seller;

    @Column
    private Date date;

    public com.marketplace.entity.Purchase toIntegrationPurchase() {
        com.marketplace.entity.Purchase integrationPurchase = new com.marketplace.entity.Purchase();
        integrationPurchase.setId(getId());
        integrationPurchase.setPrice(getPrice());
        integrationPurchase.setItem(getItem());
        integrationPurchase.setName(getName());
        integrationPurchase.setInstruction(getInstruction());
        integrationPurchase.setProductId(getProductId());
        integrationPurchase.setStatus(getStatus());
        integrationPurchase.setBuyer(getBuyer().toIntegrationUser());
        integrationPurchase.setSeller(getSeller().toIntegrationUser());
        return integrationPurchase;
    }
}
