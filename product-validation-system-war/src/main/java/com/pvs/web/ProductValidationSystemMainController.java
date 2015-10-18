package com.pvs.web;

import com.pvs.ProductValidationSystemReadService;

import spark.Request;
import spark.Response;
import spark.Route;
import spark.servlet.SparkApplication;

public class ProductValidationSystemMainController implements SparkApplication {


    @Override
    public void init() {

        spark.Spark.get("/helloworld", new Route() {
			
			@Override
			public Object handle(Request request, Response response) throws Exception {
				return ProductValidationSystemReadService.getHelloWorld();
			}
		});
    }

}