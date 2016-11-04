import java.io.*;

public class CryptoChalSet1_4{

	static String hexString = "0123456789abcdef";

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

	public static void readTXTFile(String filepath){

		try	{
			File f = new File(filepath);

			if (f.isFile() && f.exists()){
				InputStreamReader read = new InputStreamReader(
						new FileInputStream(f));
				BufferedReader bfr = new BufferedReader(read);
				String evrLine = null;

				while((evrLine = bfr.readLine()) != null){
					//System.out.println(evrLine);
					char[] textChar = hexToChar(evrLine);
					char[] message = new char[textChar.length];
					char theRealE = findTheMost(textChar);
					char key = (char)(' ' ^ theRealE);

					for (int i = 0; i < textChar.length; i++){
						message[i] = (char)(textChar[i] ^ key);
					} 	

					System.out.println(message);
				}
				
			}else{
				System.out.println("Cannot find the file.");
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	public static void main(String[] args){
		String f = "files\\4.txt";
		readTXTFile(f);
	}
}