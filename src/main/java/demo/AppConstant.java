package demo;

/**
 * Created by ttnd on 24/11/16.
 */

public class AppConstant {

    public static final String APP_PREFERENCE = "HDFC";
    public static final String KEY_SIP_DATA = "SIPDATA";
    public static final String KEY_IS_DELETE = "isDelete";
    public static final String KEY_ARN_CODE = "arnCode";
    public static final String IS_LOGIN = "isLogin";
    public static final String BRAND_LOGO = "brandlogo";
    public static final String PROFILE_IMAGE = "profileImage";
    public static final String DISTRIBUTOR_NAME = "distributorname";
    public static final String KEY_INVESTOR_NAME = "name";
    public static final String KEY_INVESTOR_ID = "investorId";
    public static final String KEY_FOLIO_ID = "folio_number";
    public static final String KEY_IS_MINOR = "isMinor";
    public static final String KEY_BANK_INFO = "bankInfo";
    public static final String KEY_ADD_NEW_INV_DETAILS = "addNewInvestor";
    public static final String KEY_COUNTRIES = "countries";
    public static final String KEY_ADD_NOMINEE_DATA = "nominedata";
    public static final String KEY_PAN_DATA = "panData";
    public static final String KEY_IS_ADD_INV = "addInv";
    public static final String KEY_DOB = "dob";
    public static final String KEY_IS_ADD_FOLIO = "isAddFolio";
    public static final String KEY_PAN_NO = "panNo";
    public static final String KEY_INVESTOR_INFO = "investorInfo";
    //Key for clearing the back navigation icon .
    public static final String LABEL_NO_NAVIGATION_ICON = "noNavigationIcon";
    //Key for the date formatter .
    public static final String DATE_FORMATTER = "dd-MMM-yyyy";
    public static final String SCHEME_NAME = "schemeName";
    public static final String SCHEME_CODE = "schemeCode";
    public static final String SCHEME_VALUES = "schemeValues";
    public static final String IS_MINOR = "isMinor";
    public static final String REQUEST_CODE = "REQUEST_CODE";
    public static final String TRANSACTION_TYPE = "transactionType";
    public static final String SCHEME_MODEL = "schemeModel";
    public static final String KEY_TRANSACTION_ID = "transactionId";
    public static final String KEY_TYPE = "type";
    public static final String KEY_INVESTOR_UUID = "investorUUID";
    public static final String KEY_REINITIATE_PURCHASE_DATA = "reInitiatePurchase";
    public static final String KEY_TRAN_DETAILS = "transDetails";
    public static final String KEY_HTML_PAGE_NAME = "htmlPageName";
    public static final Object KEY_CATEGORY = "category";
    public static final Object KEY_SUGGESTION = "suggestion";
    public static final String KEY_SELECTED_WIDGETS = "selectedWidgets";
    public static final String KEY_TOKEN = "token";
    public static final String KEY_VIDEO_ID = "VIDEO_ID";
    public static final String KEY_FLOW_TYPE = "flowType";
    public static final String KEY_INVESTMENT_TYPE = "investmentType";
    public static final String KEY_TRANSACTION_MODE = "transactionMode";
    public static final String KEY_NEW_PASSWORD = "newPassword";
    public static final String KEY_OLD_PASSWORD = "oldPassword";
    public static final String KEY_LAUNCH_HOME_SCREEN = "launchUploadImage";
    public static final String KEY_SCHEME_DATA = "schemeData";
    public static final String KEY_SIPORPURCHASEDATA = "siporpurchasedata";


    //Values to be used for authentication .Set as value for the Key authorization
    public static String BASIC_AUTHORIZATION = "Basic ZGlzdHJpYnV0b3ItY2xpZW50OnNlY3JldA==";
    public static String APP_AUTHORIZATION = "Basic ZGlzdHJpYnV0b3ItY2xpZW50OnNlY3JldA==";

    public static String AUTHORIZATION = "Authorization";
    public static String BASE_URL = "https://hdfc-qa-kong.tothenew.com/authorization/";
    //public static String BASE_URL = "https://hdfc-qa-kong.tothenew.com/authorization/";
    //public static String BASE_URL = "https://api.hdfcfund.com/authorization/";
    //public static String BASE_URL = "http://hdfc-uat-kong.tothenew.com/authorization/";
    //public static final String AUTHORIZATION_URL = "http://hdfc-uat-kong.tothenew.com/authorization/";
    public static boolean IS_HEADER_UPDATE = false;
    //Reinitaite Constants
    public static final String REINITIATE_REDEEM_DATA = "reInitiateRedeem";
    public static final String REINITIATE_SWP_DATA = "reInitiateSwp";
    public static final String REINITIATE_STP_DATA = "reInitiateStp";
    public static final String REINITIATE_SWITCH_DATA = "reInitiateSwitch";
    public static final String KEY_FOLIO_NUMBER = "folio_number";
    public static final String KEY_FOLIONUMBER = "folioNumber";
    public static final String KEY_REINITIATE_SIP_DATA = "reInitiateSipData";
    //Flag for checking first time installation
    public static boolean IS_FIRST_TIME_USER = false;
    public static String FIRST_TIME_USER = "firstTimeUser";
    public static final String TO_DO_WIDGET_ID = "493616ec-b6d4-11e6-80f5-76304dec7eb9";
    public static final String IS_PASSWORD_CHANGE_BEGUN = "passwordChangeBegun";
    public static final String IS_PASSWORD_CHANGE_ENDED = "passwordChangeEnded";
    public static final String KEY_UUID = "uuid";

    public enum DividendOption {
        Y, N, Z
    }

    //REQUEST CODE CONSTANTS
    public static final int REQ_AUM = 1001;

    //Flag to set weather euin is mandatory or not .
    public static boolean isEuinMandatory = false;

    public static final String EUINOPTEDYES = "Y";
    public static final String EUINOPTEDNO = "N";

    public static final String YOUTUBE_API_KEY = "AIzaSyCHNQhpOcRAWRNI0il62YpBB0BxbvKv4B4";


}
