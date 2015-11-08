package com.pvs.web;

import com.pvs.web.constants.RedirectPaths;
import com.pvs.web.freemarker.processors.CompanyLoginProcessor;
import com.pvs.web.freemarker.processors.CompanyRegistrationProcessor;
import com.pvs.web.freemarker.processors.DisplayPlanProcessor;
import com.pvs.web.freemarker.processors.LogoutProcessor;
import com.pvs.web.freemarker.processors.MyPlanProcessor;
import com.pvs.web.freemarker.processors.PlanRegistrationProcessor;
import com.pvs.web.freemarker.processors.ProductRegistrationProcessor;
import com.pvs.web.freemarker.processors.ValidateProductProcesssor;

import spark.Request;
import spark.Response;
import spark.Route;
import spark.servlet.SparkApplication;

public class ProductValidationSystemMainController implements SparkApplication {


    @Override
    public void init() {

        spark.Spark.get(RedirectPaths.HOME_PAGE, new Route() {
			
			@Override
			public Object handle(Request request, Response response) throws Exception {
				return CompanyLoginProcessor.getHTML(request);
			}
		});
        
        
        spark.Spark.get(RedirectPaths.HOME_PAGE+RedirectPaths.COMPANY_REGISTER, new Route() {
			
     			@Override
     			public Object handle(Request request, Response response) throws Exception {
     				return CompanyRegistrationProcessor.getHTML(request);
     			}
     	}); 
        spark.Spark.post(RedirectPaths.HOME_PAGE+RedirectPaths.COMPANY_REGISTER, new Route() {
			
 			@Override
 			public Object handle(Request request, Response response) throws Exception {
 				return CompanyRegistrationProcessor.postHTML(request, response);
 			}
        });
        
        spark.Spark.get(RedirectPaths.HOME_PAGE+RedirectPaths.PLAN_REGISTER, new Route() {
			
 			@Override
 			public Object handle(Request request, Response response) throws Exception {
 				return PlanRegistrationProcessor.getHTML(request);
 			}
	 	}); 
	    spark.Spark.post(RedirectPaths.HOME_PAGE+RedirectPaths.PLAN_REGISTER, new Route() {
		
			@Override
			public Object handle(Request request, Response response) throws Exception {
				return PlanRegistrationProcessor.postHTML(request, response);
			}
    });
        
        
        spark.Spark.get(RedirectPaths.HOME_PAGE+RedirectPaths.COMPANY_LOGIN, new Route() {
			
 			@Override
 			public Object handle(Request request, Response response) throws Exception {
 					return CompanyLoginProcessor.getHTML(request);
 			}
        });
        spark.Spark.post(RedirectPaths.HOME_PAGE+RedirectPaths.COMPANY_LOGIN, new Route() {
			
 			@Override
 			public Object handle(Request request, Response response) throws Exception {
 					return CompanyLoginProcessor.postHTML(request, response);
 			}
        });
        
        
        
        spark.Spark.post(RedirectPaths.HOME_PAGE+RedirectPaths.DISPLAY_PLAN, new Route() {
			
   			@Override
   			public Object handle(Request request, Response response) throws Exception {
   				return DisplayPlanProcessor.postHTML(request, response);
   			}
          });
        spark.Spark.get(RedirectPaths.HOME_PAGE+RedirectPaths.DISPLAY_PLAN, new Route() {
			
   			@Override
   			public Object handle(Request request, Response response) throws Exception {
   				return DisplayPlanProcessor.getHTML(request, response);
   			}
          });
        
        
        
        spark.Spark.post(RedirectPaths.HOME_PAGE+RedirectPaths.MY_PLAN, new Route() {
			
   			@Override
   			public Object handle(Request request, Response response) throws Exception {
   				return MyPlanProcessor.postHTML(request, response);
   			}
          });
        spark.Spark.get(RedirectPaths.HOME_PAGE+RedirectPaths.MY_PLAN, new Route() {
			
   			@Override
   			public Object handle(Request request, Response response) throws Exception {
   				return MyPlanProcessor.getHTML(request, response);
   			}
          });
        
        
        spark.Spark.get(RedirectPaths.HOME_PAGE+RedirectPaths.REGISTER_PRODUCT, new Route() {
			
   			@Override
   			public Object handle(Request request, Response response) throws Exception {
   				return ProductRegistrationProcessor.getHTML(request);
   			}
          });
        
        
        spark.Spark.post(RedirectPaths.HOME_PAGE+RedirectPaths.REGISTER_PRODUCT, new Route() {
			
   			@Override
   			public Object handle(Request request, Response response) throws Exception {
   				return ProductRegistrationProcessor.postHTML(request);
   			}
          });
        
		spark.Spark.get(RedirectPaths.HOME_PAGE+RedirectPaths.VALIDATE_PRODUCT, new Route() {
					
		   			@Override
		   			public Object handle(Request request, Response response) throws Exception {
		   				return ValidateProductProcesssor.getJSON(request);
		   			}
		          });

		spark.Spark.post(RedirectPaths.HOME_PAGE+RedirectPaths.VALIDATE_PRODUCT, new Route() {
			
				@Override
				public Object handle(Request request, Response response) throws Exception {
					return ValidateProductProcesssor.getJSON(request);
				}
		  });
        
        
        
        spark.Spark.get(RedirectPaths.HOME_PAGE+RedirectPaths.LOGOUT, new Route() {
			
   			@Override
   			public Object handle(Request request, Response response) throws Exception {
   				return LogoutProcessor.getHTML(request, response);
   			}
          });
        
    }

}