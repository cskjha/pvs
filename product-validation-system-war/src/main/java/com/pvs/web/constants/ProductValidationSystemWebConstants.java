package com.pvs.web.constants;

import freemarker.template.Configuration;
import freemarker.template.Version;

public class ProductValidationSystemWebConstants {
	public static final String LOGIN_FAILURE_MESSAGE = "Userid or Password is not valid";
	public static final Version FREEMARKER_VERSION = Configuration.VERSION_2_3_23;
	public static final String QR_CODE_ROOT_PATH = "/generated/content/qrcodes/";
	public static final String PRODUCT_IMAGE_ROOT_PATH = "/content/productimages/";
	public static final int QR_CODE_HEIGHT = 200;
	public static final int QR_CODE_WIDTH = 200;
	public static final String QR_CODE_FILE_EXTENSION = "png";
	public static final String EXCEL_EXTENSION = "xlsx";
	public static final String JSON="json";
	public static final String LOGIN_ADMIN_FAILURE_MESSAGE="admin is not allowed to signIn here";
	public static final String INITIAL_PRODUCT_RESPONSE_CODE = "200";
	public static final String STOLEN_PRODUCT_RESPONSE_CODE = "205";
}
