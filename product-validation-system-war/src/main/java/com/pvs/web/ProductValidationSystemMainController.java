package com.pvs.web;

import com.pvs.web.freemarker.processors.MyPlanProcessor;
import com.pvs.web.freemarker.processors.CompanyLoginProcessor;
import com.pvs.web.freemarker.processors.CompanyRegistrationProcessor;
import com.pvs.web.freemarker.processors.DisplayPlanProcessor;
import com.pvs.web.freemarker.processors.LogoutProcessor;
import com.pvs.web.freemarker.processors.ProductRegistrationProcessor;
import com.pvs.web.freemarker.processors.ValidateProductProcesssor;

import spark.Request;
import spark.Response;
import spark.Route;
import spark.Session;
import spark.servlet.SparkApplication;

public class ProductValidationSystemMainController implements SparkApplication {


    @Override
    public void init() {

        spark.Spark.get("/", new Route() {
			
			@Override
			public Object handle(Request request, Response response) throws Exception {
				return CompanyLoginProcessor.getHTML(request);
			}
		});
        
        
        spark.Spark.get("/companyregister", new Route() {
			
     			@Override
     			public Object handle(Request request, Response response) throws Exception {
     				return CompanyRegistrationProcessor.getHTML(request);
     			}
     	}); 
        spark.Spark.post("/companyregister", new Route() {
			
 			@Override
 			public Object handle(Request request, Response response) throws Exception {
 				return CompanyRegistrationProcessor.postHTML(request, response);
 			}
        });
        
        
        spark.Spark.get("/companylogin", new Route() {
			
 			@Override
 			public Object handle(Request request, Response response) throws Exception {
 					return CompanyLoginProcessor.getHTML(request);
 			}
        });
        spark.Spark.post("/companylogin", new Route() {
			
 			@Override
 			public Object handle(Request request, Response response) throws Exception {
 					return CompanyLoginProcessor.postHTML(request, response);
 			}
        });
        
        
        
        spark.Spark.post("/displayplan", new Route() {
			
   			@Override
   			public Object handle(Request request, Response response) throws Exception {
   				return DisplayPlanProcessor.getHTML(request, response);
   			}
          });
        spark.Spark.get("/displayplan", new Route() {
			
   			@Override
   			public Object handle(Request request, Response response) throws Exception {
   				return DisplayPlanProcessor.postHTML(request, response);
   			}
          });
        
        
        
        spark.Spark.post("/myplan", new Route() {
			
   			@Override
   			public Object handle(Request request, Response response) throws Exception {
   				return MyPlanProcessor.getHTML(request, response);
   			}
          });
        
        
        spark.Spark.get("/registerproduct", new Route() {
			
   			@Override
   			public Object handle(Request request, Response response) throws Exception {
   				return ProductRegistrationProcessor.getHTML(request);
   			}
          });
        
        
        spark.Spark.post("/registerproduct", new Route() {
			
   			@Override
   			public Object handle(Request request, Response response) throws Exception {
   				return ProductRegistrationProcessor.postHTML(request);
   			}
          });
        
		spark.Spark.get("/validateproduct", new Route() {
					
		   			@Override
		   			public Object handle(Request request, Response response) throws Exception {
		   				return ValidateProductProcesssor.getJSON(request);
		   			}
		          });

		spark.Spark.post("/validateproduct", new Route() {
			
				@Override
				public Object handle(Request request, Response response) throws Exception {
					return ValidateProductProcesssor.getJSON(request);
				}
		  });
        
        
        
        spark.Spark.get("/logout", new Route() {
			
   			@Override
   			public Object handle(Request request, Response response) throws Exception {
   				return LogoutProcessor.getHTML(request);
   			}
          });
        
    }

}