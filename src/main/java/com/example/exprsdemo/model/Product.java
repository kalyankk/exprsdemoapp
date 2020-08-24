package com.example.exprsdemo.model;

//Product Implementation
public class Product implements IProduct, Comparable<Product>{
    private Integer id;
    private String title;
    private String subCategory;
    private Integer price;
    private Integer popularity;

    public Product() {

    }

    public Product(IProduct other) {
        this.id = other.getId();
        this.title = other.getTitle();
        this.subCategory = other.getSubCategory();
        this.price = other.getPrice();
        this.popularity = other.getPopularity();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSubCategory() {
        return subCategory;
    }

    public void setSubCategory(String subCategory) {
        this.subCategory = subCategory;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Integer getPopularity() {
        return popularity;
    }

    public void setPopularity(Integer popularity) {
        this.popularity = popularity;
    }

    @Override
    public boolean equals(Object obj)
    {
        // check object references
        if(this == obj)
            return true;

        // check object type
        if(obj == null || obj.getClass()!= this.getClass())
            return false;

        // Actual comparing
        Product p = (Product) obj;

        //Why compare id only ? No duplicate id exists
        return (p.id == this.id);
    }

    @Override
    public int hashCode()
    {
        //returning id as hascode as there is no duplicate id's exists
        return this.id;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", subCategory='" + subCategory + '\'' +
                ", price=" + price +
                ", popularity=" + popularity +
                '}';
    }

    @Override
    public int compareTo(Product o) {
        return o.id - this.id;
    }
}
