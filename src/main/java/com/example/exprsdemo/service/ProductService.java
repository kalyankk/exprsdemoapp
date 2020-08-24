package com.example.exprsdemo.service;

import com.example.exprsdemo.model.IProduct;

import java.util.Collection;

public interface ProductService {

    public IProduct addProduct(IProduct p);

    public IProduct removeProduct(Integer id);

    public IProduct removeProduct(IProduct p);

    public Collection<IProduct> getAll();

    public Collection<IProduct> getAllSortByPopularity();

    public Collection<IProduct> getAllSortByPrice();

    public IProduct getById(Integer id);

    public Collection<IProduct> getBySubCategory(String subCategory);

}
