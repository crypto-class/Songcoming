public class CryptoChalSet1_5{

	static String hexString = "0123456789abcdef";

	static String plaintext1 = "Burning 'em, if you ain't quick and nimble"
			+ "\nI go crazy when I hear a cymbal";
	//static String plaintext2 = "I go crazy when I hear a cymbal";

	public static char[] xorWithICE(String text){
		char[] ice = {'I', 'C', 'E'};
		char[] xorResult = new char[text.length()];

		for (int i = 0; i < text.length(); i++){
			xorResult[i] = (char) (text.charAt(i) ^ ice[i % 3]);
		}

		return xorResult;
	}

	public static String charToHex(char[] myChar){
		char[] resultChar = new char[myChar.length * 2];

		for(int i = 0; i < resultChar.length; i += 2){
			resultChar[i] = hexString.charAt((int)(myChar[i / 2] >> 4));
			resultChar[i + 1] = hexString.charAt((int)(myChar[i / 2] & 15));
		}

		String resultStr = String.valueOf(resultChar);

		return resultStr;
	}

	public static void main(String[] args){
		System.out.println(charToHex(xorWithICE(plaintext1)));
		//System.out.println(charToHex(xorWithICE(plaintext2)));
	}
}