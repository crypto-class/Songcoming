public class CryptoChalSet1_2{


	static String hexString = "0123456789abcdef";

	static String textHex1 = "1c0111001f010100061a024b53535009181c";
	static String textHex2 = "686974207468652062756c6c277320657965";


	public static char[] hexToChar(String hex){

		char[] ch = new char[hex.length() / 2];

		for(int i = 0; i < hex.length(); i += 2){
			ch[i / 2] = (char) ((char) (hexString.indexOf(hex.charAt(i))) << 4 | hexString.indexOf(hex.charAt(i + 1)));
		}

		return ch;
	}


	public static char[] charXorChar(char[] text1, char[] text2){

		char[] xorResult = new char[text1.length];

		for(int i = 0; i < text1.length; i++){
			xorResult[i] = (char) (text1[i] ^ text2[i]);
		}

		return xorResult;
	}


	public static String charToHex(char[] myChar){
		char[] resultChar = new char[textHex1.length()];

		for(int i = 0; i < textHex1.length(); i += 2){
			resultChar[i] = hexString.charAt((int)(myChar[i / 2] >> 4));
			resultChar[i + 1] = hexString.charAt((int)(myChar[i / 2] & 15));
		}

		String resultStr = String.valueOf(resultChar);

		return resultStr;
	}

	public static void main(String[] args){
		System.out.println(charToHex(charXorChar(hexToChar(textHex1),hexToChar(textHex2))));
	}

}