import java.io.*;
import sun.misc.BASE64Decoder;

public class CryptoChalSet1_6{

	String hexString = "0123456789abcdef";

	static String text1 = "this is a test";
	static String text2 = "wokka wokka!!!";
	static String f = "files\\6.txt";

	public static char[] str2Char(String text){
		char[] result = new char[text.length()];

		for (int i = 0; i < text.length(); i++){
			result[i] = text.charAt(i);
		}

		return result;
	}

	public static String chra2Binstr(char[] charText){
		String resultBinstr = "";
		String realBlockBin = "";
		

		for (int i = 0; i < charText.length; i++){
			String blockBin = "";
			realBlockBin = Integer.toBinaryString(charText[i]);
			
			if (realBlockBin.length() != 8){
				for (int j = 0; j < 8 - realBlockBin.length(); j++){
				    blockBin += "0";
				}
			}
			blockBin += realBlockBin;
			resultBinstr += blockBin;
		}

		return resultBinstr;
	}

	public static int hamDistance(String text1, String text2){
		int count = 0; 
		if (text1.length() == text2.length()){
			String strBinText1 = chra2Binstr(str2Char(text1));
			String strBinText2 = chra2Binstr(str2Char(text2));
			//System.out.println(strBinText1);
			//System.out.println(strBinText2);
	
			for (int i = 0; i < strBinText1.length(); i++){
				if (strBinText1.charAt(i) != strBinText2.charAt(i)){
					count++;
				}
			}
		}else{
			System.out.println("The length of the inputs is not equal.");
		}

		return count;
	}

	public static byte[] readFiles(String filepath){
		File f = new File(filepath);
		String filetext = "";
		String blockText = "";
		byte[] byteText = null;

		try{
			if (f.isFile() && f.exists()){
				InputStreamReader read = new InputStreamReader(
						new FileInputStream(f));
				BufferedReader bfr = new BufferedReader(read);

				while ((blockText = bfr.readLine()) != null){
					filetext += blockText;
				}

				BASE64Decoder decoder = new BASE64Decoder();
				byteText = decoder.decodeBuffer(filetext); 
			}else{
				System.out.println("File is not exist.");
			}	
		}catch(Exception e){
			e.printStackTrace();
		}

		return byteText;
	}

	public static int findSmallestHam(byte[] filetext){
		double[] hamOfKeySize = new double[39];
		String filestr = new String(filetext);
		//System.out.println(filestr.length());
		int fileLength = filetext.length;
		int indexOfMin = 0;

		for (int i = 2; i < 41; i++){
			String[] blocks = new String[filestr.length() / i];
			//System.out.println(blocks.length);
			int hamSum = 0;
			int count = 0;


			for(int j = 0; j < blocks.length; j++){
				blocks[j] = filestr.substring(j * i, (j + 1) * i);
				//System.out.println(blocks[j]);
			}

			for (int k = 0; k < fileLength / i - 1; k++){
				for (int l = k + 1; l < fileLength / i; l++){
					hamSum += hamDistance(blocks[k], blocks[l]);
					count++;
				}
			}

			hamOfKeySize[i - 2] = ((double)hamSum / count) / i;
		}

		//return hamOfKeySize;
		for (int i = 1; i < hamOfKeySize.length; i++){
			if (hamOfKeySize[i] < hamOfKeySize[indexOfMin]){
				indexOfMin = i;
			}
		}

		//System.out.println(hamOfKeySize[indexOfMin]);
		return indexOfMin + 2;
	}

	public static byte[][] splitCipher(byte[] filetext, int numOfBlocks){
		byte[][] cipherBlock = new byte[numOfBlocks][filetext.length / numOfBlocks];
		for (int j = 0; j < (filetext.length / numOfBlocks) * numOfBlocks; j++){
			cipherBlock[j % numOfBlocks][j / numOfBlocks] = filetext[j];
		}

		return cipherBlock;
	}

	public static byte[] findTheMost(byte[][] cipherBlock){
		byte[] result = new byte[cipherBlock.length];

		for (int k = 0; k < cipherBlock.length; k++){
			int[] posiTimes = new int[cipherBlock[k].length];		
			int max = 0;
			int maxIndex = 0;

			for (int i = 0; i < cipherBlock[k].length; i++){
				for (int j = 0; j < cipherBlock[k].length; j++){
					if (cipherBlock[k][i] == cipherBlock[k][j]){
						posiTimes[i]++;
					}
				}
			}

			for (int i = 0; i < cipherBlock[k].length; i++){
				if (max < posiTimes[i]){
					max = posiTimes[i];
					maxIndex = i;
				}			
			}

			result[k] = cipherBlock[k][maxIndex];
		}

		return result;
	}

	public static byte[] computeKey(byte[] result, int keySize){
		byte[] key = new byte[keySize];
		for (int i = 0; i < keySize; i++){
			key[i] = (byte) (result[i] ^ ' ');
		}

		return key;
	}

	public static byte[] decription(byte[] key, byte[] filetext, int keySize){
		byte[] plaintext = new byte[filetext.length];
		for (int i = 0; i < filetext.length; i++){
			plaintext[i] = (byte) (filetext[i] ^ key[i % keySize]);
		}

		return plaintext;
	}

	public static void main(String[] args){
		//System.out.println(hamDistance(text1, text2));
		byte[] filetext = readFiles(f);
		int keySize = findSmallestHam(filetext);
		System.out.println("The key size is: " + keySize + "\n");
		byte[] key = computeKey(findTheMost(splitCipher(filetext, keySize)), keySize);
		System.out.print("The key is: ");

		for (int i = 0; i < keySize; i++){
			System.out.print((char)key[i]);
		}
		
		System.out.print("\n\nThe plaintext is: ");

		byte[] plaintext = decription(key, filetext, keySize);

		for (int i = 0; i < filetext.length; i++){
			System.out.print((char)plaintext[i]);
		}
		/*byte[] filetext = {'a','b','c','d','a','b','c','d'};
		byte[][] key = splitCipher(filetext, 2);
		for (byte[] a : key){
			for (byte d : a
					){
				System.out.print((char)d);
			}
			
			System.out.print("\n");
		}*/
	}
}