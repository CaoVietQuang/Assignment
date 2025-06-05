package org.ecommerce.system.domain.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "oder")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "code")
    private String code;

    @Column(name = "origin_money")
    private Float originMoney;

    @Column(name = "reducel_money")
    private Float reduceMoney;

    @Column(name = "total_money")
    private Float totalMoney;

    @Column(name = "shipping_money")
    private Float shippingMoney;

    @Column(name = "confirmation_date")
    private Long confirmationDate;

    @Column(name = "expected_delivery_date")
    private Long expectedDeliveryDate;

    @Column(name = "delivery_start_date")
    private Long deliveryStartDate;

    @Column(name = "received_date")
    private Long receivedDate;

    @Column(name = "type")
    private Integer type;

    @Column(name = "order_status")
    private Integer orderStatus;
    
    @Column(name = "note", length = 256)
    private String note;

    @OneToMany(mappedBy = "order", fetch = FetchType.LAZY)
    List<OrderDetailEntity> orderDetails;

    @JoinColumn(name = "user_id", referencedColumnName = "id")
    @ManyToOne(fetch = FetchType.LAZY)
    private UserEntity user;

    @JoinColumn(name = "voucher_id", referencedColumnName = "id")
    @ManyToOne(fetch = FetchType.LAZY)
    private VoucherEntity voucher;
}
