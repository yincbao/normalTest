package com.cpw.obd;

public class CRCUtil {
	private static int FCS_START = 65535;
	private static int FCS_FINAL = 61624;
	private static int[] FCSTAB = { 0, 4489, 8978, 12955, 17956, 22445, 25910, 29887, 35912, 40385, 44890, 48851,
			51820, 56293, 59774, 63735, 4225, 264, 13203, 8730, 22181, 18220, 30135, 25662, 40137, 36160, 49115, 44626,
			56045, 52068, 63999, 59510, 8450, 12427, 528, 5017, 26406, 30383, 17460, 21949, 44362, 48323, 36440, 40913,
			60270, 64231, 51324, 55797, 12675, 8202, 4753, 792, 30631, 26158, 21685, 17724, 48587, 44098, 40665, 36688,
			64495, 60006, 55549, 51572, 16900, 21389, 24854, 28831, 1056, 5545, 10034, 14011, 52812, 57285, 60766,
			64727, 34920, 39393, 43898, 47859, 21125, 17164, 29079, 24606, 5281, 1320, 14259, 9786, 57037, 53060,
			64991, 60502, 39145, 35168, 48123, 43634, 25350, 29327, 16404, 20893, 9506, 13483, 1584, 6073, 61262,
			65223, 52316, 56789, 43370, 47331, 35448, 39921, 29575, 25102, 20629, 16668, 13731, 9258, 5809, 1848,
			65487, 60998, 56541, 52564, 47595, 43106, 39673, 35696, 33800, 38273, 42778, 46739, 49708, 54181, 57662,
			61623, 2112, 6601, 11090, 15067, 20068, 24557, 28022, 31999, 38025, 34048, 47003, 42514, 53933, 49956,
			61887, 57398, 6337, 2376, 15315, 10842, 24293, 20332, 32247, 27774, 42250, 46211, 34328, 38801, 58158,
			62119, 49212, 53685, 10562, 14539, 2640, 7129, 28518, 32495, 19572, 24061, 46475, 41986, 38553, 34576,
			62383, 57894, 53437, 49460, 14787, 10314, 6865, 2904, 32743, 28270, 23797, 19836, 50700, 55173, 58654,
			62615, 32808, 37281, 41786, 45747, 19012, 23501, 26966, 30943, 3168, 7657, 12146, 16123, 54925, 50948,
			62879, 58390, 37033, 33056, 46011, 41522, 23237, 19276, 31191, 26718, 7393, 3432, 16371, 11898, 59150,
			63111, 50204, 54677, 41258, 45219, 33336, 37809, 27462, 31439, 18516, 23005, 11618, 15595, 3696, 8185,
			63375, 58886, 54429, 50452, 45483, 40994, 37561, 33584, 31687, 27214, 22741, 18780, 15843, 11370, 7921,
			3960 };

	public static byte[] makeCrcToBytes(byte[] buf) {
		String hex = encodeHex(makeCrc(buf));
		String l = hex.substring(hex.length() - 2);
		String h = hex.substring(hex.length() - 4, hex.length() - 2);
		byte[] b = new byte[2];
		b[0] = Integer.valueOf(h, 16).byteValue();
		b[1] = Integer.valueOf(l, 16).byteValue();
		return b;
	}

	public static int makeCrc(byte[] buf) {
		int crc = 0;
		crc = FCS_START;
		for (int i = 0; i < buf.length; ++i) {
			crc = getFcs(crc, buf[i]);
		}
		crc ^= FCS_START;
		return crc;
	}

	public static boolean checkCrc(byte[] buf) {
		int crc = 0;
		boolean result = false;
		crc = FCS_START;

		for (int i = 0; i < buf.length; ++i) {
			crc = getFcs(crc, buf[i]);
		}
		if (FCS_FINAL != crc)
			result = false;
		else {
			result = true;
		}
		return result;
	}

	public static String encodeHex(int value) {
		StringBuffer buf = new StringBuffer(4);
		String hex = Long.toString(value & 0xFFFF, 16);
		for (int i = 0; i < 4 - hex.length(); ++i) {
			buf.append("0");
		}
		buf.append(Long.toString(value & 0xFFFF, 16));
		return buf.toString();
	}

	private static int getFcs(int fcs, int src) {
		int xor = 0;
		int iresult = 0;
		xor = fcs;
		xor ^= src;
		iresult = fcs >> 8 ^ FCSTAB[(xor & 0xFF)];
		return iresult;
	}

	public static void main(String[] args) {

		String s = "40407F000431303031313132353239393837000000000000001001C1F06952FDF069529C91110000000000698300000C0000000000036401014C00030001190A0D04121A1480D60488C5721800000000AF4944445F3231364730325F532056312E322E31004944445F3231364730325F482056312E322E31000000DF64";
		int[] orgHex = { 0x40, 0x40, 0x7F, 0x00, 0x04, 0x31, 0x30, 0x30, 0x31, 0x31, 0x31, 0x32, 0x35, 0x32, 0x39,
				0x39, 0x38, 0x37, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x10, 0x01, 0xC1, 0xF0, 0x69, 0x52, 0xFD,
				0xF0, 0x69, 0x52, 0x9C, 0x91, 0x11, 0x00, 0x00, 0x00, 0x00, 0x00, 0x69, 0x83, 0x00, 0x00, 0x0C, 0x00,
				0x00, 0x00, 0x00, 0x00, 0x03, 0x64, 0x01, 0x01, 0x4C, 0x00, 0x03, 0x00, 0x01, 0x19, 0x0A, 0x0D, 0x04,
				0x12, 0x1A, 0x14, 0x80, 0xD6, 0x04, 0x88, 0xC5, 0x72, 0x18, 0x00, 0x00, 0x00, 0x00, 0xAF, 0x49, 0x44,
				0x44, 0x5F, 0x32, 0x31, 0x36, 0x47, 0x30, 0x32, 0x5F, 0x53, 0x20, 0x56, 0x31, 0x2E, 0x32, 0x2E, 0x31,
				0x00, 0x49, 0x44, 0x44, 0x5F, 0x32, 0x31, 0x36, 0x47, 0x30, 0x32, 0x5F, 0x48, 0x20, 0x56, 0x31, 0x2E,
				0x32, 0x2E, 0x31, 0x00, 0x00, 0x00, 0xDF, 0x64, 0x0D, 0x0A };
		byte[] packageData = { 64, 64, 127, 0, 4, 49, 48, 48, 49, 49, 49, 50 };
		// byte[] crc = packageData;
		
		System.out.println(Integer.toHexString(makeCrc(packageData)));
		// byte[] in = {(byte) 160,0x10};
		// int crcInt = makeCrc(in);
		// System.out.println(encodeHex(crcInt));
	}
}