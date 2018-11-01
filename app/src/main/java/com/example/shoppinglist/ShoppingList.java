package com.example.shoppinglist;

public class ShoppingList {

    private int shoppinglistNum; //ショッピングリストのナンバー
    private String product_name; //商品の名
    private String product_type; //商品のカテゴリ
    private int product_number; //商品の数

    public ShoppingList() {
    }

    public ShoppingList(int shoppinglistNum, String product_name, String product_type, int product_number) {
        this.shoppinglistNum = shoppinglistNum;
        this.product_name = product_name;
        this.product_type = product_type;
        this.product_number = product_number;
    }

    public int getShoppinglistNum() {
        return shoppinglistNum;
    }

    public void setShoppinglistNum(int shoppinglistNum) {
        this.shoppinglistNum = shoppinglistNum;
    }

    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public String getProduct_type() {
        return product_type;
    }

    public void setProduct_type(String product_type) {
        this.product_type = product_type;
    }

    public int getProduct_number() {
        return product_number;
    }

    public void setProduct_number(int product_number) {
        this.product_number = product_number;
    }

    @Override
    public String toString() {
        return "ShoppingList{" +
                "shoppinglistNum=" + shoppinglistNum +
                ", product_name='" + product_name + '\'' +
                ", product_type='" + product_type + '\'' +
                ", product_number=" + product_number +
                '}';
    }
}
