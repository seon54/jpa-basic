package jpabook.jpashop.domain;

import javax.persistence.*;

@Entity
@Table(name = "ORDERS")
public class Order {

    @Id
    @GeneratedValue
    private Long id;
    private int orderAmount;

    @Embedded
    private Address addres;

    @ManyToOne
    @JoinColumn(name = "PRODUCT_ID")
    private Product product;

}
