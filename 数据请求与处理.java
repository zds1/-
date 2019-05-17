import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;//����java.io���µ�IOException��
import java.io.InputStream;//����java.io���µ�InputStream��
import java.io.InputStreamReader;//����java.io���µ�InputStreamReader��
import java.net.MalformedURLException;//����java.net���µ�MalformedURLException��
import java.net.URL;//����java.net���µ�URL��
import java.net.URLConnection;//����java.net���µ�URLConnection��
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class test1 extends Thread {// ����һ����̬��ReadByGet��̳���Thread��

	public void run() {// run���������������Ҫִ�е�����
		try {// try����飬�������쳣ʱ��ת��catch�������
			String u = "https://ai.taobao.com/search/index.htm?pid=mm_10011550_0_0&unid=&source_id=search&key=%E8%AF%9D%E8%B4%B9%E5%85%85%E5%80%BC&b=sousuo_ssk&clk1=&prepvid=200_11.27.75.94_386_1558071760186&spm=a231o.7076277%2Fb.1998559105.1";
			URL url = new URL(u);// ����һ��URL���ʵ������ָ����ַ
			URLConnection connection = url.openConnection();// ����ʵ������ָ��URL�ϵ�����
			InputStream is = connection.getInputStream();// ��ȡ���ݵ��ֽ���
			InputStreamReader isr = new InputStreamReader(is, "utf-8");// ���ֽ�����װΪ�ַ����������ƶ������ʽΪutf-8
			BufferedReader br = new BufferedReader(isr);// ����һ��ʵ���������ת������ַ�
			String line;// ����һ���ַ������ͱ���
			StringBuilder builder = new StringBuilder();// ����ʵ��
			while ((line = br.readLine()) != null) {// ��ȡ��Ϣ�����ҵ���Ϣ��Ϊ��ʱ
				builder.append(line);// append����ʹbuilder����line�е�������Ϣ

			}
			br.close();// �ر���
			isr.close();// �ر���
			is.close();// �ر���

			String a = builder.toString();
			int start = a.indexOf("����ҳ��");
			int end = a.indexOf("ɸѡ����");
			String s = a.substring(start, end);
			int starts = s.indexOf("p4ptop");
			int ends = s.indexOf("searchRetCode");
			String ss = s.substring(starts + 8, ends - 2);

			// ת����json
			JSONArray jsonarray = JSONArray.fromObject(ss);
			Object[] os = jsonarray.toArray();
			System.out.println("������:"+os.length+"������,�����뿴���������");

			String[] lenprice = new String[os.length];
			String[] lenurl = new String[os.length];
			String[] lenname = new String[os.length];

			for (int i = 0; i < os.length; i++) {
				JSONObject jo = jsonarray.getJSONObject(i);
				String id = jo.getString("title");
				String price = jo.getString("salePrice");
				String EURL = jo.getString("eurl");
				String oprice = jo.getString("goodsPrice");
				// ���Ƽ۸�
				if (price.equals("")) {

					lenprice[i] = Integer.toString(Integer.parseInt(oprice) / 100);
				} else {

					lenprice[i] = price;
				}
				lenurl[i] = deleteString0(EURL, "amp;");
				lenname[i] = id;

			}
			// ����priceд������
			try {
				File writeName = new File("./output.txt"); // ���·�������û����Ҫ����һ���µ�output.txt�ļ�
				writeName.createNewFile(); // �������ļ�,��ͬ�����ļ��Ļ�ֱ�Ӹ���
				try (FileWriter writer = new FileWriter(writeName); BufferedWriter out = new BufferedWriter(writer)) {

					for (int i = 0; i < lenprice.length; i++) {
						out.write(lenprice[i] + ","); // \r\n��Ϊ����
					}
					out.flush(); // �ѻ���������ѹ���ļ�
				}
			} catch (IOException e) {
				e.printStackTrace();
			}

			
			try {
				File writeNames = new File("./outputname.txt"); // ���·�������û����Ҫ����һ���µ�output.txt�ļ�
				writeNames.createNewFile(); // �������ļ�,��ͬ�����ļ��Ļ�ֱ�Ӹ���
				try (FileWriter writer = new FileWriter(writeNames); BufferedWriter outs = new BufferedWriter(writer)) {

					for (int i = 0; i < lenname.length; i++) {
						outs.write(lenname[i] + ","); // \r\n��Ϊ����
					}
					outs.flush(); // �ѻ���������ѹ���ļ�
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		} catch (MalformedURLException e) {// ��try��������쳣ʱת��catch�����
			e.printStackTrace();// ��ӡ�쳣����λ�ü�ԭ��
		} catch (IOException e) {// ��try��������쳣ʱת��catch�����
			e.printStackTrace();// ��ӡ�쳣����λ�ü�ԭ��
		}
	}

	public static String deleteString0(String str, String delstring) {
		String delStr = "";
		delStr = str.replaceAll(delstring, "");
		return delStr;
	}
}