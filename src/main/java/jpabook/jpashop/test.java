package jpabook.jpashop;

import jpabook.jpashop.domain.Address;

public class test {

    public static void main(String[] args) {
        int a = 10;
        int b = 10;

        Address address1 = new Address("city", "street", "12");
        Address address2 = new Address("city", "street", "12");

        System.out.println(System.identityHashCode(a));
        System.out.println(System.identityHashCode(b));
        System.out.println(System.identityHashCode(address1));
        System.out.println(System.identityHashCode(address2));
        System.out.println(a == b);
        System.out.println(address1 == address2);
        System.out.println(address1.equals(address2));

    }
}
