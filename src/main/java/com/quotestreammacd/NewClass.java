/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.quotestreammacd;

import java.util.HashMap;

public class NewClass {

    private final String color;

    public NewClass(String color) {
        this.color = color;
    }

    @Override
    public int hashCode() {
        return this.color.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof NewClass)) {
            return false;
        }
        if (obj == this) {
            return true;
        }
        return this.color.equals(((NewClass) obj).color);
    }

    public static void main(String[] args) {
        NewClass a1 = new NewClass("green");
        NewClass a2 = new NewClass("red");
        //hashMap stores apple type and its quantity
        HashMap<NewClass, Integer> m = new HashMap<>();
        m.put(a1, 10);
        m.put(a2, 20);
        System.out.println(m.get(new NewClass("green")));
    }
}
