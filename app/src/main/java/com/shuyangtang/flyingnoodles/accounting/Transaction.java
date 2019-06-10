package com.shuyangtang.flyingnoodles.accounting;

public class Transaction {
    private String  _category;
    private String _productname;
    private double _price;
    public Transaction(String category, String productName, double price) {
        this._category = category;
        this._productname = productName;
        this._price=price;
    }

    public double get_price() {
        return _price;
    }

    public void set_price(double _price) {
        this._price = _price;
    }

    public String get_productname() {
        return _productname;
    }

    public void set_productname(String _productname) {
        this._productname = _productname;
    }

    public String get_category() {
        return _category;
    }

    public void set_category(String _category){
        this._category = _category;
    }

}
