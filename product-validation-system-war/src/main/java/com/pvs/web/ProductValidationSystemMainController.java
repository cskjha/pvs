package com.pvs.web;

import com.pvs.web.constants.ProductValidationSystemWebConstants;
import com.pvs.web.constants.RedirectPaths;
import com.pvs.web.freemarker.processors.CompanyLoginProcessor;
import com.pvs.web.freemarker.processors.CompanyRegistrationProcessor;
import com.pvs.web.freemarker.processors.DisplayPlanProcessor;
import com.pvs.web.freemarker.processors.DisplayUserListProcessor;
import com.pvs.web.freemarker.processors.DownloadProductFormatProcessor;
import com.pvs.web.freemarker.processors.GenerateQRCodeProcessor;
import com.pvs.web.freemarker.processors.GenericErrorProcessor;
import com.pvs.web.freemarker.processors.LanguageChangeProcessor;
import com.pvs.web.freemarker.processors.LogoutProcessor;
import com.pvs.web.freemarker.processors.MyPlanProcessor;
import com.pvs.web.freemarker.processors.PlanRegistrationProcessor;
import com.pvs.web.freemarker.processors.ProductListViewProcessor;
import com.pvs.web.freemarker.processors.ProductRegistrationProcessor;
import com.pvs.web.freemarker.processors.ProductTemplateRegistrationProcessor;
import com.pvs.web.freemarker.processors.RatingInfoProcessor;
import com.pvs.web.freemarker.processors.RemoveProductTemplateProcessor;
import com.pvs.web.freemarker.processors.RemoveUserProcessor;
import com.pvs.web.freemarker.processors.SaveProductAsStolen;
import com.pvs.web.freemarker.processors.TextFileDownloadProcessor;
import com.pvs.web.freemarker.processors.UploadProductProcessor;
import com.pvs.web.freemarker.processors.UserProductTemplateProcessor;
import com.pvs.web.freemarker.processors.ValidateProductProcesssor;
import com.pvs.web.freemarker.processors.ViewProductTemplateProcessor;

import spark.Request;
import spark.Response;
import spark.Route;
import spark.Spark;
import spark.servlet.SparkApplication;

public class ProductValidationSystemMainController implements SparkApplication {


    
    public void init() {
    	
    	
    	
    	//Spark.externalStaticFileLocation(ProductValidationSystemWebConstants.QR_CODE_ROOT_PATH);
    	Spark.externalStaticFileLocation(ProductValidationSystemWebConstants.PRODUCT_IMAGE_ROOT_PATH);
    	
    	spark.Spark.get(RedirectPaths.HOME_PAGE, new Route() {
			
			public Object handle(Request request, Response response) throws Exception {
				return CompanyLoginProcessor.getHTML(request, response);
			}
		});
    	
    	spark.Spark.get(RedirectPaths.HOME_PAGE+RedirectPaths.CHANGE_LANGUAGE, new Route() {
			
 			
 			public Object handle(Request request, Response response) throws Exception {
 				return LanguageChangeProcessor.getHTML(request, response);
 			}
 	});
        
        
        spark.Spark.get(RedirectPaths.HOME_PAGE+RedirectPaths.COMPANY_REGISTER, new Route() {
			
     			
     			public Object handle(Request request, Response response) throws Exception {
     				return CompanyRegistrationProcessor.getHTML(request, response);
     			}
     	}); 
        spark.Spark.post(RedirectPaths.HOME_PAGE+RedirectPaths.COMPANY_REGISTER, new Route() {
			
 			
 			public Object handle(Request request, Response response) throws Exception {
 				return CompanyRegistrationProcessor.postHTML(request, response);
 			}
        });
        
        spark.Spark.get(RedirectPaths.HOME_PAGE+RedirectPaths.PLAN_REGISTER, new Route() {
			
 			
 			public Object handle(Request request, Response response) throws Exception {
 				return PlanRegistrationProcessor.getHTML(request, response);
 			}
	 	}); 
	    spark.Spark.post(RedirectPaths.HOME_PAGE+RedirectPaths.PLAN_REGISTER, new Route() {
		
			
			public Object handle(Request request, Response response) throws Exception {
				return PlanRegistrationProcessor.postHTML(request, response);
			}
    });
        
        
        spark.Spark.get(RedirectPaths.HOME_PAGE+RedirectPaths.COMPANY_LOGIN, new Route() {
			
 			
 			public Object handle(Request request, Response response) throws Exception {
 					return CompanyLoginProcessor.getHTML(request, response);
 			}
        });
        spark.Spark.post(RedirectPaths.HOME_PAGE+RedirectPaths.COMPANY_LOGIN, new Route() {
			
 			
 			public Object handle(Request request, Response response) throws Exception {
 					return CompanyLoginProcessor.postHTML(request, response);
 			}
        });
        
        
        
        spark.Spark.post(RedirectPaths.HOME_PAGE+RedirectPaths.DISPLAY_PLAN, new Route() {
			
   			
   			public Object handle(Request request, Response response) throws Exception {
   				return DisplayPlanProcessor.postHTML(request, response);
   			}
          });
        spark.Spark.get(RedirectPaths.HOME_PAGE+RedirectPaths.DISPLAY_PLAN, new Route() {
			
   			
   			public Object handle(Request request, Response response) throws Exception {
   				return DisplayPlanProcessor.getHTML(request, response);
   			}
          });
        
        
        
        spark.Spark.post(RedirectPaths.HOME_PAGE+RedirectPaths.MY_PLAN, new Route() {
			
   			
   			public Object handle(Request request, Response response) throws Exception {
   				return MyPlanProcessor.postHTML(request, response);
   			}
          });
        spark.Spark.get(RedirectPaths.HOME_PAGE+RedirectPaths.MY_PLAN, new Route() {
			
   			
   			public Object handle(Request request, Response response) throws Exception {
   				return MyPlanProcessor.getHTML(request, response);
   			}
          });
        
        spark.Spark.get(RedirectPaths.HOME_PAGE+RedirectPaths.REGISTER_PRODUCT_TEMPLATE, new Route() {
			
   			
   			public Object handle(Request request, Response response) throws Exception {
   				return ProductTemplateRegistrationProcessor.getHTML(request, response);
   			}
          });
        
        
        spark.Spark.post(RedirectPaths.HOME_PAGE+RedirectPaths.REGISTER_PRODUCT_TEMPLATE, new Route() {
			
   			
   			public Object handle(Request request, Response response) throws Exception {
   				return ProductTemplateRegistrationProcessor.postHTML(request, response);
   			}
          });
        
        spark.Spark.get(RedirectPaths.HOME_PAGE+RedirectPaths.VIEW_PRODUCT_TEMPLATES, new Route() {
			
   			
   			public Object handle(Request request, Response response) throws Exception {
   				return ViewProductTemplateProcessor.getHTML(request, response);
   			}
          });
        
        spark.Spark.get(RedirectPaths.HOME_PAGE+RedirectPaths.DOWNLOAD_PRODUCT_FORMAT, new Route() {
			
   			
     			public Object handle(Request request, Response response) throws Exception {
     				return DownloadProductFormatProcessor.getHTML(request, response);
     			}
            });
        
        spark.Spark.get(RedirectPaths.HOME_PAGE+RedirectPaths.UPLOAD_PRODUCT, new Route() {
			
   			
     			public Object handle(Request request, Response response) throws Exception {
     				return UploadProductProcessor.getHTML(request, response);
     			}
            });
        spark.Spark.post(RedirectPaths.HOME_PAGE+RedirectPaths.UPLOAD_PRODUCT, new Route() {
			
   			
 			public Object handle(Request request, Response response) throws Exception {
 				return UploadProductProcessor.postHTML(request, response);
 			}
        });
        
        spark.Spark.get(RedirectPaths.HOME_PAGE+RedirectPaths.REGISTER_PRODUCT, new Route() {
			
   			
   			public Object handle(Request request, Response response) throws Exception {
   				return ProductRegistrationProcessor.getHTML(request, response);
   			}
          });
        
        
        spark.Spark.post(RedirectPaths.HOME_PAGE+RedirectPaths.REGISTER_PRODUCT, new Route() {
			
   			
   			public Object handle(Request request, Response response) throws Exception {
   				return ProductRegistrationProcessor.postHTML(request, response);
   			}
          });
        
        spark.Spark.get(RedirectPaths.HOME_PAGE+RedirectPaths.REMOVE_PRODUCT_TEMPLATE, new Route() {
			
   			
   			public String handle(Request request, Response response) throws Exception {
   				return RemoveProductTemplateProcessor.getHTML(request, response);
   			}
          });
        
		spark.Spark.get(RedirectPaths.HOME_PAGE+RedirectPaths.VALIDATE_PRODUCT, new Route() {
					
		   			
		   			public Object handle(Request request, Response response) throws Exception {
		   				return ValidateProductProcesssor.getJSONORHTML(request, response);
		   			}
		          });

		spark.Spark.post(RedirectPaths.HOME_PAGE+RedirectPaths.VALIDATE_PRODUCT, new Route() {
			
				
				public Object handle(Request request, Response response) throws Exception {
					return ValidateProductProcesssor.postJSON(request, response);
				}
		  });
        
        
        
        spark.Spark.get(RedirectPaths.HOME_PAGE+RedirectPaths.LOGOUT, new Route() {
			
   			
   			public Object handle(Request request, Response response) throws Exception {
   				return LogoutProcessor.getHTML(request, response);
   			}
          });
        
        spark.Spark.get(RedirectPaths.HOME_PAGE+RedirectPaths.GENERIC_ERROR_PAGE, new Route() {
			
   			
   			public Object handle(Request request, Response response) throws Exception {
   				return GenericErrorProcessor.getHTML(request, response);
   			}
          });
        
        spark.Spark.get(RedirectPaths.HOME_PAGE+RedirectPaths.GENERATE_QR_CODE, new Route() {
			
   			
   			public Object handle(Request request, Response response) throws Exception {
   				return GenerateQRCodeProcessor.getHTML(request, response);
   			}
          });
        
        spark.Spark.post(RedirectPaths.HOME_PAGE+RedirectPaths.GENERATE_QR_CODE, new Route() {
			
   			
   			public Object handle(Request request, Response response) throws Exception {
   				return GenerateQRCodeProcessor.getHTML(request, response);
   			}
          });
		  
		    spark.Spark.get(RedirectPaths.HOME_PAGE+RedirectPaths.LIST_PRODUCTS, new Route() {
			
   			public Object handle(Request request, Response response) throws Exception {
   				return ProductListViewProcessor.getHTML(request, response);
   			}
          });
		  
		    spark.Spark.get(RedirectPaths.HOME_PAGE+RedirectPaths.TEXT_FILE_DOWNLOAD, new Route() {
			
   			public Object handle(Request request, Response response) throws Exception {
   				return TextFileDownloadProcessor.getHTML(request, response);
   			}
          });
		    spark.Spark.get(RedirectPaths.HOME_PAGE+RedirectPaths.RATING_INFO, new Route() {
				
	   			public Object handle(Request request, Response response) throws Exception {
	   				return RatingInfoProcessor.postHTML(request, response);
	   			}
	        });
		    spark.Spark.get(RedirectPaths.HOME_PAGE+RedirectPaths.DISPLAY_USERLIST, new Route() {
				
	   			public String handle(Request request, Response response) throws Exception {
	   				return DisplayUserListProcessor.getHTML(request, response);
	   			}
	        });
		    spark.Spark.post(RedirectPaths.HOME_PAGE+RedirectPaths.DISPLAY_USERLIST, new Route() {
				
	   			public String handle(Request request, Response response) throws Exception {
	   				return DisplayUserListProcessor.postHTML(request, response);
	   			}
	        });
		    spark.Spark.get(RedirectPaths.HOME_PAGE+RedirectPaths.USER_PRODUCT_TEMPLATE , new Route() {
				
	   			public String handle(Request request, Response response) throws Exception {
	   				return UserProductTemplateProcessor.getHTML(request, response);
	   			}
	        });
		    spark.Spark.post(RedirectPaths.HOME_PAGE+RedirectPaths.UPDATE_STOLEN_PRODUCT_RESPONSECODE, new Route() {
				
	   			public String handle(Request request, Response response) throws Exception {
	   				return SaveProductAsStolen.postHTML(request, response);
	   			}
	        });
        
    }

}