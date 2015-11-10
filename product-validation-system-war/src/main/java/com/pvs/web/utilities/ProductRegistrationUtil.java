package com.pvs.web.utilities;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;

import org.apache.log4j.Logger;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.NotFoundException;
import com.google.zxing.Result;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import com.pvs.enums.ProductTypes;
import com.pvs.web.constants.ProductValidationSystemWebConstants;
import com.pvs.web.constants.RedirectPaths;

public class ProductRegistrationUtil {
	static final Logger log = Logger.getLogger(ProductRegistrationUtil.class);
	
	
	public static String generateProductScanCode(String productId, String productType) {
		String productScanId = null;
		if(productId != null && productType != null) {
			productScanId = productType+productId;	
		}
		else {
			log.debug("One or More parameters are null : productId : "+productId+" :productType: "+productType);
		}
		return productScanId;
	}
	public static String getProductIdFromProductScanCode(String productScanId) {
		String productId = null;		
		if(productScanId != null) {
			int index = productScanId.indexOf(ProductTypes.FOOD_PRODUCTS.getProductType());
			if(index >= 0) {
				productId = productScanId.substring(index+ProductTypes.FOOD_PRODUCTS.getProductType().length()-1);
				log.debug("productScanId : "+productScanId+" productId : "+productId);
				return productId;
			}
			index = productScanId.indexOf(ProductTypes.NON_FOOD_PRODUCTS.getProductType());
			if(index >= 0) {
				productId = productScanId.substring(index+ProductTypes.NON_FOOD_PRODUCTS.getProductType().length()-1);
				log.debug("productScanId : "+productScanId+" productId : "+productId);
				return productId;
			}
		}
		else {
			log.debug("One or More parameters are null : productScanId : "+productScanId);
		}
		return productId;
	}
	public static String getProductTypeFromProductScanCode(String productScanId) {
		String productType = null;
		int index = productScanId.indexOf(ProductTypes.FOOD_PRODUCTS.getProductType());
		if(index >= 0) {
			return ProductTypes.FOOD_PRODUCTS.getProductType();
		}
		index = productScanId.indexOf(ProductTypes.NON_FOOD_PRODUCTS.getProductType());
		if(index >= 0) {
			return ProductTypes.NON_FOOD_PRODUCTS.getProductType();
		}
		return productType;
	}
	
	
	public static String generateQRCode(String websiteURL, String productId, String productType) {
		String productScanCode = null;
		String filePath = null;
		try {
		if(productId != null && productType != null) {
			productScanCode = generateProductScanCode(productId, productType);
			String fileDirectory = ProductValidationSystemWebConstants.QR_CODE_ROOT_PATH;
			File imageDirectory = new File(fileDirectory);
			if(!imageDirectory.exists()) {
				imageDirectory.mkdirs();
			}			
			String qrCodeData = websiteURL+"/"+RedirectPaths.VALIDATE_PRODUCT+"?productScanCode="+productScanCode;
			filePath = imageDirectory+"/"+productScanCode+".png";
			String charset = "UTF-8"; // or "ISO-8859-1"
			Map hintMap = new HashMap();
			hintMap.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.L);			
			createQRCode(qrCodeData, filePath, charset, hintMap, ProductValidationSystemWebConstants.QR_CODE_HEIGHT,
						ProductValidationSystemWebConstants.QR_CODE_WIDTH);
			log.debug("QR Code image created successfully!");
			log.debug("Data read from QR Code: "+ readQRCode(filePath, charset, hintMap));	
		}
		else {
			log.debug("One or More parameters are null : productId : "+productId+" :productType: "+productType);
		}
		} catch (WriterException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (NotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return productScanCode;
	}
	
	private static void createQRCode(String qrCodeData, String filePath,
			String charset, Map hintMap, int qrCodeheight, int qrCodewidth)
			throws WriterException, IOException {
		BitMatrix matrix = new MultiFormatWriter().encode(
				new String(qrCodeData.getBytes(charset), charset),
				BarcodeFormat.QR_CODE, qrCodewidth, qrCodeheight, hintMap);
		MatrixToImageWriter.writeToFile(matrix, filePath.substring(filePath
				.lastIndexOf('.') + 1), new File(filePath));
	}
	
	private static String readQRCode(String filePath, String charset, Map hintMap)
			throws FileNotFoundException, IOException, NotFoundException {
		BinaryBitmap binaryBitmap = new BinaryBitmap(new HybridBinarizer(
				new BufferedImageLuminanceSource(
						ImageIO.read(new FileInputStream(filePath)))));
		Result qrCodeResult = new MultiFormatReader().decode(binaryBitmap,
				hintMap);
		return qrCodeResult.getText();
	}

}
