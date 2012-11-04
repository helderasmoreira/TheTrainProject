package pt.traincompany.utility;

public class Configurations {
	
	final public static String AUTHORITY = "192.168.10.3:3000";
	final public static String SCHEME = "http";
	final public static String FORMAT = "json";
	
	final public static String GETSTATIONS = "stops";
	final public static String GETROUTE = "get_route";
	final public static String LOGIN = "login";
	final public static String SIGNUP = "signup";
	final public static String GETCARDSBYID = "getCardsByUserId";
	final public static String ADDCARD = "addCardToUser";
	final public static String REMOVECARD = "removeCard";
	
	public static String username;
	public static String name;
	public static int userId = 0;
	public static boolean cardsLoaded = false;

	final public static String QRCODE = "https://chart.googleapis.com/chart?chs=250x250&cht=qr&chl=";
}
