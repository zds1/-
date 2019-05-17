import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;//导入java.io包下的IOException类
import java.io.InputStream;//导入java.io包下的InputStream类
import java.io.InputStreamReader;//导入java.io包下的InputStreamReader类
import java.net.MalformedURLException;//导入java.net包下的MalformedURLException类
import java.net.URL;//导入java.net包下的URL类
import java.net.URLConnection;//导入java.net包下的URLConnection类
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class test1 extends Thread {// 定义一个静态的ReadByGet类继承于Thread类

	public void run() {// run方法，里面包含需要执行的任务
		try {// try代码块，当发生异常时会转到catch代码块中
			String u = "https://ai.taobao.com/search/index.htm?pid=mm_10011550_0_0&unid=&source_id=search&key=%E8%AF%9D%E8%B4%B9%E5%85%85%E5%80%BC&b=sousuo_ssk&clk1=&prepvid=200_11.27.75.94_386_1558071760186&spm=a231o.7076277%2Fb.1998559105.1";
			URL url = new URL(u);// 创建一个URL类的实例，并指定网址
			URLConnection connection = url.openConnection();// 创建实例连接指定URL上的内容
			InputStream is = connection.getInputStream();// 获取内容的字节流
			InputStreamReader isr = new InputStreamReader(is, "utf-8");// 将字节流包装为字符串流，并制定编码格式为utf-8
			BufferedReader br = new BufferedReader(isr);// 创建一个实例用来存放转换后的字符
			String line;// 定义一个字符串类型变量
			StringBuilder builder = new StringBuilder();// 创建实例
			while ((line = br.readLine()) != null) {// 读取信息，并且当信息不为空时
				builder.append(line);// append方法使builder包含line中的所有信息

			}
			br.close();// 关闭流
			isr.close();// 关闭流
			is.close();// 关闭流

			String a = builder.toString();
			int start = a.indexOf("搜索页面");
			int end = a.indexOf("筛选工具");
			String s = a.substring(start, end);
			int starts = s.indexOf("p4ptop");
			int ends = s.indexOf("searchRetCode");
			String ss = s.substring(starts + 8, ends - 2);

			// 转化成json
			JSONArray jsonarray = JSONArray.fromObject(ss);
			Object[] os = jsonarray.toArray();
			System.out.println("搜索到:"+os.length+"条数据,详情请看搜索结果！");

			String[] lenprice = new String[os.length];
			String[] lenurl = new String[os.length];
			String[] lenname = new String[os.length];

			for (int i = 0; i < os.length; i++) {
				JSONObject jo = jsonarray.getJSONObject(i);
				String id = jo.getString("title");
				String price = jo.getString("salePrice");
				String EURL = jo.getString("eurl");
				String oprice = jo.getString("goodsPrice");
				// 控制价格
				if (price.equals("")) {

					lenprice[i] = Integer.toString(Integer.parseInt(oprice) / 100);
				} else {

					lenprice[i] = price;
				}
				lenurl[i] = deleteString0(EURL, "amp;");
				lenname[i] = id;

			}
			// 爬的price写到本地
			try {
				File writeName = new File("./output.txt"); // 相对路径，如果没有则要建立一个新的output.txt文件
				writeName.createNewFile(); // 创建新文件,有同名的文件的话直接覆盖
				try (FileWriter writer = new FileWriter(writeName); BufferedWriter out = new BufferedWriter(writer)) {

					for (int i = 0; i < lenprice.length; i++) {
						out.write(lenprice[i] + ","); // \r\n即为换行
					}
					out.flush(); // 把缓存区内容压入文件
				}
			} catch (IOException e) {
				e.printStackTrace();
			}

			
			try {
				File writeNames = new File("./outputname.txt"); // 相对路径，如果没有则要建立一个新的output.txt文件
				writeNames.createNewFile(); // 创建新文件,有同名的文件的话直接覆盖
				try (FileWriter writer = new FileWriter(writeNames); BufferedWriter outs = new BufferedWriter(writer)) {

					for (int i = 0; i < lenname.length; i++) {
						outs.write(lenname[i] + ","); // \r\n即为换行
					}
					outs.flush(); // 把缓存区内容压入文件
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		} catch (MalformedURLException e) {// 当try代码块有异常时转到catch代码块
			e.printStackTrace();// 打印异常所在位置及原因
		} catch (IOException e) {// 当try代码块有异常时转到catch代码块
			e.printStackTrace();// 打印异常所在位置及原因
		}
	}

	public static String deleteString0(String str, String delstring) {
		String delStr = "";
		delStr = str.replaceAll(delstring, "");
		return delStr;
	}
}