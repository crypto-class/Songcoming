import sun.misc.BASE64Encoder;

public class CryptoChalSet1_1{

	static String hexString = "0123456789abcdef";
	static String text = "49276d206b696c6c696e6720796f757220627261696e206c696b65206120706f69736f6e6f7573206d757368726f6f6d";

	public static byte[] hexToByte(String hex){

		byte[] myByte = new byte[hex.length() / 2];

		for(int i = 0; i < text.length(); i += 2){
			myByte[i / 2] = (byte) (hexString.indexOf(hex.charAt(i)) << 4 | hexString.indexOf(hex.charAt(i + 1))); 
		}

		return myByte;
	} 


	public static void main(String[] args){

		BASE64Encoder encoder = new BASE64Encoder(); 

		System.out.println(encoder.encode(hexToByte(text))); 
	}
}