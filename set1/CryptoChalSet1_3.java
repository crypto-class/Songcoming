public class CryptoChalSet1_3{

	static String hexString = "0123456789abcdef";

	static String text = "1b37373331363f78151b7f2b783431333d78397828372d363c78373e783a393b3736";

	public static char[] hexToChar(String hex){
		char[] ch = new char[hex.length() / 2];

		for(int i = 0; i < hex.length(); i += 2){
			ch[i / 2] = (char) ((char) (hexString.indexOf(hex.charAt(i))) << 4 | hexString.indexOf(hex.charAt(i + 1)));
		}

		return ch;
	}

	public static char findTheMost(char[] ch){
		int[] posiTimes = new int[ch.length];		
		int max = 0;
		int maxIndex = 0;

		for (int i = 0; i < ch.length; i++){
			for (int j = 0; j < ch.length; j++){
				if (ch[i] == ch[j]){
					posiTimes[i]++;
				}
			}
		}
		//for(int k = 0; k < posiTimes.length; k++)
		//System.out.println(posiTimes[k]);

		for (int i = 0; i < ch.length; i++){
			if (max < posiTimes[i]){
				max = posiTimes[i];
				maxIndex = i;
			}			
		}
		
		//System.out.println(max);
		//System.out.println(maxIndex);

		return ch[maxIndex];
	}

	public static void main(String[] args){
		char[] textChar = hexToChar(text);
		System.out.println(textChar);
		
		char[] messages = new char[textChar.length];
		char theRealE = findTheMost(textChar);
		System.out.println(theRealE);
		char key = (char) (' ' ^ theRealE);
		System.out.println(key);

		for (int i = 0; i < textChar.length; i++){
			messages[i] = (char) (textChar[i] ^ key);
		}

		System.out.println(messages);
	}
}