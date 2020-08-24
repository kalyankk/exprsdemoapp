package com.example.exprsdemo;

import com.example.exprsdemo.model.Product;
import com.example.exprsdemo.service.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.client.RestTemplate;
import sun.jvm.hotspot.utilities.Assert;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

@SpringBootApplication
public class ExprsdemoApplication {

	Logger logger = LoggerFactory.getLogger(ExprsdemoApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(ExprsdemoApplication.class, args);
	}


	//URL of the initial data available at properties file
	@Value("${com.example.exprsdemo.dataUrl}")
	private String dataUrl;

	@Autowired
	private ProductService productService;

	/** TASK - 1
	 * Once the spring application is ready,
	 * Get JSON data with the http of http request
	 * And store the data inside ProductDataStore with the help of ProductService
	 **/
	@PostConstruct
	public void init(){
		logger.debug("Load data from "+dataUrl);
		HashMap<String, Object> products = null;
		try {
			RestTemplate restTemplate = new RestTemplate();

			products = restTemplate.getForObject(dataUrl, HashMap.class);
		}
		catch (Exception e) {
			logger.error("Unable to load JSON data from given URL");
			throw e;
		}

		//Integer count = (Integer)products.get("count");

		HashMap<String, Object> productList = (HashMap<String, Object>)products.get("products");

		for(Map.Entry<String, Object> entry: productList.entrySet()) {
			Product p = new Product();
			p.setId(Integer.parseInt(entry.getKey()));
			HashMap<String, String> productProperties = (HashMap<String, String>) entry.getValue();

			p.setTitle(productProperties.get("title"));
			p.setSubCategory(productProperties.get("subcategory"));
			p.setPrice(Integer.parseInt(productProperties.get("price")));
			p.setPopularity(Integer.parseInt(productProperties.get("popularity")));

			productService.addProduct(p);
		}

		//Assert.that(productService.getAll().size()==1000, "Failed to add or retrieve 1000 items");
		logger.debug("Save to local datastore completed");

		logger.debug("Total mobile products: "+productService.getBySubCategory("mobile").size());

		logger.debug("Total tablet products: "+productService.getBySubCategory("tablet").size());

		logger.debug("Total smart-watches products: "+productService.getBySubCategory("smart-watches").size());

		logger.debug("Total fitness-tracker products: "+productService.getBySubCategory("fitness-tracker").size());


	}

}
