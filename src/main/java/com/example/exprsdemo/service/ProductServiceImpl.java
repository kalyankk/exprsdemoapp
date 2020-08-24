package com.example.exprsdemo.service;

import com.example.exprsdemo.datastore.ProductDataStore;
import com.example.exprsdemo.model.IProduct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductDataStore productDataStore;

    @Override
    public IProduct addProduct(IProduct p) {
        return productDataStore.add(p);
    }

    @Override
    public IProduct removeProduct(Integer id) {
        if(id == null)
            throw new IllegalStateException("Product id cannot be null");
        return this.removeProduct(getById(id));
    }

    @Override
    public IProduct removeProduct(IProduct p) {
        return productDataStore.remove(p);
    }

    @Override
    public Collection<IProduct> getAll() {
        return productDataStore.getAll();
    }

    @Override
    public Collection<IProduct> getAllSortByPopularity() {
        return productDataStore.getAllSortByPopularity();
    }

    @Override
    public Collection<IProduct> getAllSortByPrice() {
        return productDataStore.getAllSortByPrice();
    }

    @Override
    public IProduct getById(Integer id) {
        return productDataStore.getById(id);
    }

    @Override
    public Collection<IProduct> getBySubCategory(String subCategory) {
        return productDataStore.getBySubCategory(subCategory);
    }


}
