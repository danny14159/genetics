package orm.daoutils;


/**���� ������
 * @author ����
 *
 */
public class Utils {

	/**��ӡ����
	 * @param list
	 */
//	public static void print(List<?> list){
//		for(Object obj:list){
//			System.out.print(obj+",");
//		}
//		System.out.println();
//	}
	
	/**�������е�Ԫ���м���token�ַ�����
	 * @param strs
	 * @param token
	 * @return
	 */
	public static String joinString(String[] strs, String token) {

		String result = "";
		for (int i = 0, len = strs.length; i < len; i++) {

			result += (i == 0 ? "" : token) + strs[i];
		}

		return result;
	}
}
