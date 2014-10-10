/*
 * Copyright MalchroSoft - Aymeric MALCHROWICZ. All right reserved.
 * The source code that contains this comment is an intellectual property
 * of MalchroSoft [Aymeric MALCHROWICZ]. Use is subject to licence terms.
 */
package com.malchrosoft.utils.text;

import com.malchrosoft.debug.Log;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Aymeric Malchrowicz
 */
public class RSA
{
	private static final int[] primes =
	{
		4507, 4513, 4517, 4519, 4523, 4547, 4549, 4561, 4567, 4583, 4591, 4597,
		4603, 4621, 4637, 4639, 4643, 4649, 4651, 4657, 4663, 4673, 4679, 4691, 4703, 4721, 4723, 4729, 4733, 4751,
		4759, 4783, 4787, 4789, 4793, 4799, 4801, 4813, 4817, 4831, 4861, 4871, 4877, 4889, 4903, 4909, 4919, 4931,
		4933, 4937, 4943, 4951, 4957, 4967, 4969, 4973, 4987, 4993, 4999, 5003, 5009, 5011, 5021, 5023, 5039, 5051,
		5059, 5077, 5081, 5087, 5099, 5101, 5107, 5113, 5119, 5147, 5153, 5167, 5171, 5179, 5189, 5197, 5209, 5227,
		5231, 5233, 5237, 5261, 5273, 5279, 5281, 5297, 5303, 5309, 5323, 5333, 5347, 5351, 5381, 5387, 5393, 5399,
		5407, 5413, 5417, 5419, 5431, 5437, 5441, 5443, 5449, 5471, 5477, 5479, 5483, 5501, 5503, 5507, 5519, 5521,
		5527, 5531, 5557, 5563, 5569, 5573, 5581, 5591, 5623, 5639, 5641, 5647, 5651, 5653, 5657, 5659, 5669, 5683,
		5689, 5693, 5701, 5711, 5717, 5737, 5741, 5743, 5749, 5779, 5783, 5791, 5801, 5807, 5813, 5821, 5827, 5839,
		5843, 5849, 5851, 5857, 5861, 5867, 5869, 5879, 5881, 5897, 5903, 5923, 5927, 5939, 5953, 5981, 5987, 6007,
		6011, 6029, 6037, 6043, 6047, 6053, 6067, 6073, 6079, 6089, 6091, 6101, 6113, 6121, 6131, 6133, 6143, 6151,
		6163, 6173, 6197, 6199, 6203, 6211, 6217, 6221, 6229, 6247, 6257, 6263, 6269, 6271, 6277, 6287, 6299, 6301,
		6311, 6317, 6323, 6329, 6337, 6343, 6353, 6359, 6361, 6367, 6373, 6379, 6389, 6397, 6421, 6427, 6449, 6451,
		6469, 6473, 6481, 6491, 6521, 6529, 6547, 6551, 6553, 6563, 6569, 6571, 6577, 6581, 6599, 6607, 6619, 6637,
		6653, 6659, 6661, 6673, 6679, 6689, 6691, 6701, 6703, 6709, 6719, 6733, 6737, 6761, 6763, 6779, 6781, 6791,
		6793, 6803, 6823, 6827, 6829, 6833, 6841, 6857, 6863, 6869, 6871, 6883, 6899, 6907, 6911, 6917, 6947, 6949,
		6959, 6961, 6967, 6971, 6977, 6983, 6991, 6997, 7001, 7013, 7019, 7027, 7039, 7043, 7057, 7069, 7079, 7103,
		7109, 7121, 7127, 7129, 7151, 7159, 7177, 7187, 7193, 7207, 7211, 7213, 7219, 7229, 7237, 7243, 7247, 7253,
		7283, 7297, 7307, 7309, 7321, 7331, 7333, 7349, 7351, 7369, 7393, 7411, 7417, 7433, 7451, 7457, 7459, 7477,
		7481, 7487, 7489, 7499, 7507, 7517, 7523, 7529, 7537, 7541, 7547, 7549, 7559, 7561, 7573, 7577, 7583, 7589,
		7591, 7603, 7607, 7621, 7639, 7643, 7649, 7669, 7673, 7681, 7687, 7691, 7699, 7703, 7717, 7723, 7727, 7741,
		7753, 7757, 7759, 7789, 7793, 7817, 7823, 7829, 7841, 7853, 7867, 7873, 7877, 7879, 7883, 7901, 7907, 7919,
		7927, 7933, 7937, 7949, 7951, 7963, 7993, 8009, 8011, 8017, 8039, 8053, 8059, 8069, 8081, 8087, 8089, 8093,
		8101, 8111, 8117, 8123, 8147, 8161, 8167, 8171, 8179, 8191, 8209, 8219, 8221, 8231, 8233, 8237, 8243, 8263,
		8269, 8273, 8287, 8291, 8293, 8297, 8311, 8317, 8329, 8353, 8363, 8369, 8377, 8387, 8389, 8419, 8423, 8429,
		8431, 8443, 8447, 8461, 8467, 8501, 8513, 8521, 8527, 8537, 8539, 8543, 8563, 8573, 8581, 8597, 8599, 8609,
		8623, 8627, 8629, 8641, 8647, 8663, 8669, 8677, 8681, 8689, 8693, 8699, 8707, 8713, 8719, 8731, 8737, 8741,
		8747, 8753, 8761, 8779, 8783, 8803, 8807, 8819, 8821, 8831, 8837, 8839, 8849, 8861, 8863, 8867, 8887, 8893,
		8923, 8929, 8933, 8941, 8951, 8963, 8969, 8971, 8999, 9001, 9007, 9011, 9013, 9029, 9041, 9043, 9049, 9059,
		9067, 9091, 9103, 9109, 9127, 9133, 9137, 9151, 9157, 9161, 9173, 9181, 9187, 9199, 9203, 9209, 9221, 9227,
		9239, 9241, 9257, 9277, 9281, 9283, 9293, 9311, 9319, 9323, 9337, 9341, 9343, 9349, 9371, 9377, 9391, 9397,
		9403, 9413, 9419, 9421, 9431, 9433, 9437, 9439, 9461, 9463, 9467, 9473, 9479, 9491, 9497, 9511, 9521, 9533
	};
	private int maxPrimes = primes.length - 1;

	private RSA()
	{
	}

	public static int powMod(int base, int exp, int modulo)
	{
		BigInteger bd = new BigInteger(base + "");
		return bd.modPow(new BigInteger(exp + ""), new BigInteger(modulo + "")).intValue();
	}

	public static String rsaEncrypt(String text, int publicKey, int modulo)
	{
		List<Integer> asci = new ArrayList<Integer>();
		String tmpasci;
		String tmpStr;
		for (int i = 0; i < text.length(); i+=3)
		{
			tmpasci = "1";
			for (int j=0; j < 3; j++)
			{
				if (i+j < text.length())
				{
					tmpStr = (text.substring(i+j, i+j+1).charAt(0) - 30) + "";
					if (tmpStr.length() < 2) tmpStr = "0"+tmpStr;
				}
				else break;
				tmpasci += tmpStr;
			}
			asci.add(Integer.parseInt(tmpasci+"1"));
		}

		String result = "";
		//Each number is then encrypted using the RSA formula: block ^E mod N
		for (Integer s : asci)
		{
			result += powMod(s.intValue(), publicKey, modulo) + " ";
		}
		return result;
	}

	public static String rsaDecrypt(String encryptedText, int privateKey, int modulo)
	{
		//Strip the blank spaces from the ecrypted text and store it in an array
		String[] encryptedArray = encryptedText.split(" ");
		List<String> encryptedList = new ArrayList<String>();
		for (int i = 0; i < encryptedArray.length; i++)
		{
			if (!encryptedArray[i].equals("")) encryptedList.add(encryptedArray[i]);
		}
		String decrypted = "";
		int resultMod;
		//Each number is then decrypted using the RSA formula: block ^D mod N
		for (String s : encryptedList)
		{
			resultMod = powMod(Integer.parseInt(s), privateKey, modulo);
			//remove leading and trailing '1' digits
			decrypted += ("" + resultMod).substring(1, ("" + resultMod).length() - 2);
		}

		String decryptedString = "";
		//Each ASCII code number + 30 in the message is represented as its letter
		for (int i = 0; i < decrypted.length(); i+=2)
		{
			decryptedString += (char) (Integer.parseInt(decrypted.substring(i, i+1) + "") + 30);
		}

		return decryptedString;
	}

	public static void main(String[] args)
	{
		Log.get().setLevel(Log.Level.DEBUG);
		String text = "EMAIL:aymeric.malchrowicz@malchrosoft.com,LICENSE NUMBER:1234-5678-2H2M-ABCD,MACID:#p:x86WindowsVista6.0windowsFRfr#proc:2#name:PCP-Aymeric#mac:eth2:00188BC731DB";
		String key =
			"22896204 49975708 8540000 1016559 13665506 12381480 53435591 21359562 52101213 38461285 48620300 39310229 23076956 5704454 30235387 15730870 50494384 57764800 36343302 22198132 9730572 79593 11403365 18359445 25433141 41186535 35413355 31330118 18359445 44378995 52180806 10806970 1576619 39165515 21756028 13604190 6492676 45152853 39898269 9649500 57089042 29078502 23288488 29879594 11880955 5886718";


		Log.debug(text + "\n" + key);
		Log.debug("A".charAt(0) + " = " + ((int) "A".charAt(0)));
		
		Log.debug(rsaEncrypt("A", 6421, 59930557));
		Log.debug(RSA.rsaDecrypt(rsaEncrypt("A", 6421, 59930557), 32985341, 59930557));


		Log.debug(rsaEncrypt(text, 6421, 59930557));
		Log.debug(RSA.rsaDecrypt(rsaEncrypt(text, 6421, 59930557), 32985341, 59930557));
		Log.debug(RSA.rsaDecrypt(key, 32985341, 59930557));

		String obf = TextObfuscator.obfuscate(text, 59930557+"");
		Log.debug("\n\nTextObfuscator : " + obf);
		Log.debug(TextObfuscator.clarify(obf, 59930557+""));
		Log.debug(TextObfuscator.clarify(obf, "autreClé"));


	}
}
