package com.example.exprsdemo.datastore;

import com.example.exprsdemo.model.IProduct;
import com.example.exprsdemo.model.Product;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
@Scope("application")// we would like to store the product info in local storage and should be available as log as the application is running
public class ProductDataStore {

    //Treating products hashmap as actual storage
    private HashMap<Integer, IProduct> products = new HashMap<>();

    //Taking additional data structures to store references to support different kinds of queries
    private HashMap<String, List<IProduct>> subCategoryIndex = new HashMap<>();
    private SortedSet<IProduct> productSortedOnPopularity = new TreeSet<>(new ProductPopularityComparator());
    private SortedSet<IProduct> productSortedOnPrice = new TreeSet<>(new ProductPriceComparator());


    //Price comparator
    private class ProductPriceComparator implements Comparator<IProduct> {
        @Override
        public int compare(IProduct p1, IProduct p2) {
            return p2.getPrice() - p1.getPrice();
        }

    }

    //Popularity comparator
    private class ProductPopularityComparator implements Comparator<IProduct> {
        @Override
        public int compare(IProduct p1, IProduct p2) {
            return p2.getPopularity() - p1.getPopularity();
        }

    }

    //Adding a product to data store
    public IProduct add(IProduct p) {
        if(p==null)
            throw new IllegalArgumentException("Product cannot be null");
        if(p.getId() == null)
            throw new IllegalStateException("Product id cannot be null");
        if(p.getSubCategory() == null)
            throw new IllegalStateException("Product sub category cannot be null");

        //To avoid escape references, create new object and store it
        p = new Product(p);

        products.put(p.getId(), p);

        //update reference at subCategoryIndex and sorted sets
        List<IProduct> subCategoryProducts = subCategoryIndex.get(p.getSubCategory());
        if(subCategoryProducts == null)
            subCategoryProducts = new LinkedList<>();
        subCategoryProducts.add(p);
        subCategoryIndex.put(p.getSubCategory(), subCategoryProducts);

        //update reference with popularity
        productSortedOnPopularity.add(p);

        //update reference with price
        productSortedOnPrice.add(p);

        return p;
    }

    public IProduct remove(IProduct p) {
        if(p==null)
            throw new IllegalArgumentException("Product cannot be null");
        if(p.getId() == null)
            throw new IllegalStateException("Product id cannot be null");

        //remove reference from subCategoryIndex and all other collections
        if(p.getSubCategory() != null) {
            List<IProduct> subCategoryProducts = subCategoryIndex.get(p.getSubCategory());
            if (subCategoryProducts != null) {
                subCategoryProducts.remove(p.getId());
                subCategoryIndex.put(p.getSubCategory(), subCategoryProducts);
            }
        }

        //update reference with popularity
        productSortedOnPopularity.remove(p);

        //update reference with price
        productSortedOnPrice.remove(p);

        return products.remove(p);
    }

    //return non editable list as response
    public Collection<IProduct> getAll() {
        return Collections.unmodifiableCollection(products.values());
    }

    public Collection<IProduct> getAllSortByPrice() {
        return Collections.unmodifiableCollection(productSortedOnPrice);
    }

    public Collection<IProduct> getAllSortByPopularity() {
        return Collections.unmodifiableCollection(productSortedOnPopularity);
    }

    public IProduct getById(Integer id) {
        return products.get(id);
    }

    public Collection<IProduct> getBySubCategory(String subCategory) {
        if(subCategory==null)
            throw new IllegalArgumentException("subCategory cannot be null");
        return Collections.unmodifiableCollection(subCategoryIndex.getOrDefault(subCategory, new ArrayList<>()));
    }

}
