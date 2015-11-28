package com.pvs.web.freemarker.processors;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.pvs.web.constants.ProductValidationSystemWebConstants;
import com.pvs.web.constants.RedirectPaths;
import com.pvs.web.utilities.ProcessorUtil;
import com.pvs.web.utilities.ProductRegistrationUtil;

import spark.Request;
import spark.Response;
import spark.Session;

public class GenerateQRCodeProcessor {
	final static Logger log = Logger.getLogger(ProductRegistrationProcessor.class);

	public static String getHTML(Request request, Response response) {
		String htmlOutput = null;
		Session session = request.session(false);
		if (session == null) {
			response.redirect(RedirectPaths.COMPANY_LOGIN);
			return null;
		}
		String productScanCode = request.queryParams("productScanCode");
		log.debug("productScanCode : " + productScanCode);
		String userName = request.session().attribute("companyName");
		Map<String, Object> dynamicValues = new HashMap<String, Object>();
		ProcessorUtil.populateDynamicValues(dynamicValues);
		dynamicValues.put("companyName", userName);
		try {
			String hostName = request.host();
			String contextRoot = request.contextPath();
			String productId = ProductRegistrationUtil.getProductIdFromProductScanCode(productScanCode);
			String productType = ProductRegistrationUtil.getProductTypeFromProductScanCode(productScanCode);
			response.header("Content-Disposition", ("attachment;filename="+productScanCode+"."+ProductValidationSystemWebConstants.QR_CODE_FILE_EXTENSION));
			BitMatrix qrCodeBitMatrix = (BitMatrix)ProductRegistrationUtil.generateQRCode(hostName+contextRoot, productId, productType);
			MatrixToImageWriter.writeToStream(qrCodeBitMatrix, ProductValidationSystemWebConstants.QR_CODE_FILE_EXTENSION, response.raw().getOutputStream());
//			dynamicValues.put("qrCodeImagefilePath", qrCodeImagefilePath+".png");
//			htmlOutput = ProcessorUtil.populateTemplate(TemplatePaths.GENERATE_QR_CODE_GET, dynamicValues,
//					ProductRegistrationProcessor.class);
		} 
//		catch (TemplateException e) {
//			e.printStackTrace();
//		}
		
		catch (IOException e) {
			e.printStackTrace();
		}
		return htmlOutput;
	}

	public static String postHTML(Request request, Response response) {
		return getHTML(request, response);
	}
}
