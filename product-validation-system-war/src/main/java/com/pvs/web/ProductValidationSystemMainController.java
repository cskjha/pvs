package com.pvs.web;

import com.pvs.web.constants.ProductValidationSystemWebConstants;
import com.pvs.web.constants.RedirectPaths;
import com.pvs.web.freemarker.processors.CompanyLoginProcessor;
import com.pvs.web.freemarker.processors.CompanyRegistrationProcessor;
import com.pvs.web.freemarker.processors.DisplayPlanProcessor;
import com.pvs.web.freemarker.processors.GenerateQRCodeProcessor;
import com.pvs.web.freemarker.processors.GenericErrorProcessor;
import com.pvs.web.freemarker.processors.LanguageChangeProcessor;
import com.pvs.web.freemarker.processors.LogoutProcessor;
import com.pvs.web.freemarker.processors.MyPlanProcessor;
import com.pvs.web.freemarker.processors.PlanRegistrationProcessor;
import com.pvs.web.freemarker.processors.ProductRegistrationProcessor;
import com.pvs.web.freemarker.processors.ProductTemplateRegistrationProcessor;
import com.pvs.web.freemarker.processors.ValidateProductProcesssor;
import com.pvs.web.freemarker.processors.ViewProductTemplateProcessor;

import spark.Request;
import spark.Response;
import spark.Route;
import spark.Spark;
import spark.servlet.SparkApplication;

public class ProductValidationSystemMainController implements SparkApplication {


    @Override
    public void init() {
    	
    	
    	
    	Spark.externalStaticFileLocation(ProductValidationSystemWebConstants.QR_CODE_ROOT_PATH);
    	
    	spark.Spark.get(RedirectPaths.HOME_PAGE, new Route() {
			
			@Override
			public Object handle(Request request, Response response) throws Exception {
				return CompanyLoginProcessor.getHTML(request, response);
			}
		});
    	
    	spark.Spark.get(RedirectPaths.HOME_PAGE+RedirectPaths.CHANGE_LANGUAGE, new Route() {
			
 			@Override
 			public Object handle(Request request, Response response) throws Exception {
 				return LanguageChangeProcessor.getHTML(request, response);
 			}
 	});
        
        
        spark.Spark.get(RedirectPaths.HOME_PAGE+RedirectPaths.COMPANY_REGISTER, new Route() {
			
     			@Override
     			public Object handle(Request request, Response response) throws Exception {
     				return CompanyRegistrationProcessor.getHTML(request, response);
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
 				return PlanRegistrationProcessor.getHTML(request, response);
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
 					return CompanyLoginProcessor.getHTML(request, response);
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
        
        spark.Spark.get(RedirectPaths.HOME_PAGE+RedirectPaths.REGISTER_PRODUCT_TEMPLATE, new Route() {
			
   			@Override
   			public Object handle(Request request, Response response) throws Exception {
   				return ProductTemplateRegistrationProcessor.getHTML(request, response);
   			}
          });
        
        
        spark.Spark.post(RedirectPaths.HOME_PAGE+RedirectPaths.REGISTER_PRODUCT_TEMPLATE, new Route() {
			
   			@Override
   			public Object handle(Request request, Response response) throws Exception {
   				return ProductTemplateRegistrationProcessor.postHTML(request, response);
   			}
          });
        
        spark.Spark.get(RedirectPaths.HOME_PAGE+RedirectPaths.VIEW_PRODUCT_TEMPLATES, new Route() {
			
   			@Override
   			public Object handle(Request request, Response response) throws Exception {
   				return ViewProductTemplateProcessor.getHTML(request, response);
   			}
          });
        
        spark.Spark.get(RedirectPaths.HOME_PAGE+RedirectPaths.REGISTER_PRODUCT, new Route() {
			
   			@Override
   			public Object handle(Request request, Response response) throws Exception {
   				return ProductRegistrationProcessor.getHTML(request, response);
   			}
          });
        
        
        spark.Spark.post(RedirectPaths.HOME_PAGE+RedirectPaths.REGISTER_PRODUCT, new Route() {
			
   			@Override
   			public Object handle(Request request, Response response) throws Exception {
   				return ProductRegistrationProcessor.postHTML(request, response);
   			}
          });
        
        
		spark.Spark.get(RedirectPaths.HOME_PAGE+RedirectPaths.VALIDATE_PRODUCT, new Route() {
					
		   			@Override
		   			public Object handle(Request request, Response response) throws Exception {
		   				return ValidateProductProcesssor.getJSON(request, response);
		   			}
		          });

		spark.Spark.post(RedirectPaths.HOME_PAGE+RedirectPaths.VALIDATE_PRODUCT, new Route() {
			
				@Override
				public Object handle(Request request, Response response) throws Exception {
					return ValidateProductProcesssor.postJSON(request, response);
				}
		  });
        
        
        
        spark.Spark.get(RedirectPaths.HOME_PAGE+RedirectPaths.LOGOUT, new Route() {
			
   			@Override
   			public Object handle(Request request, Response response) throws Exception {
   				return LogoutProcessor.getHTML(request, response);
   			}
          });
        
        spark.Spark.get(RedirectPaths.HOME_PAGE+RedirectPaths.GENERIC_ERROR_PAGE, new Route() {
			
   			@Override
   			public Object handle(Request request, Response response) throws Exception {
   				return GenericErrorProcessor.getHTML(request, response);
   			}
          });
        
        spark.Spark.get(RedirectPaths.HOME_PAGE+RedirectPaths.GENERATE_QR_CODE, new Route() {
			
   			@Override
   			public Object handle(Request request, Response response) throws Exception {
   				return GenerateQRCodeProcessor.getHTML(request, response);
   			}
          });
        
        spark.Spark.post(RedirectPaths.HOME_PAGE+RedirectPaths.GENERATE_QR_CODE, new Route() {
			
   			@Override
   			public Object handle(Request request, Response response) throws Exception {
   				return GenerateQRCodeProcessor.getHTML(request, response);
   			}
          });
        
    }

}