import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class Demo extends JFrame implements ActionListener {

	JPanel jp1, jp2, jp3, jp4, jp5, jp6, jp7;
	JLabel JL_produce, JL_Num, JL_result, JL_Plan;
	JButton jb1, jb2;
	JTextField produce, price;
	JTextArea JTresult, JTplan;

	public Demo() {
		this.setTitle("Demo");
		this.setSize(560, 750);
		this.setLocation(1100, 200);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		// -----------------------
		jp1 = new JPanel();// 产品界面
		jp2 = new JPanel();// 数量界面
		jp3 = new JPanel();// 确定按钮
		jp4 = new JPanel();// 爬取结果
		jp5 = new JPanel();// 推荐方案
		jp6 = new JPanel();// 爬取结果标签
		jp7 = new JPanel();// 推荐方案标签
		// ------------------------
		JL_produce = new JLabel("产品:");
		JL_Num = new JLabel("数量:");
		JL_result = new JLabel("搜索结果:");
		jb1 = new JButton("确定");
		JTresult = new JTextArea(35, 22);
		JTresult.setLineWrap(true);// 自动换行
		JTresult.setWrapStyleWord(true);
		JTresult.setEditable(false);
		produce = new JTextField(18);
		produce.setText("话费充值");
		produce.setEditable(false);
		price = new JTextField(18);
		// ------------------------
		JL_produce.setBounds(10, 10, 10, 10);
		JL_produce.setBounds(10, 10, 10, 10);
		JL_result.setBounds(0, 0, 10, 10);
		JTresult.setBounds(0, 40, 60, 80);
		
		produce.setBounds(30, 10, 150, 100);
		price.setBounds(30, 50, 150, 100);
		// -------------------------
		jp1.setBounds(0, 30, 260, 30);
		jp2.setBounds(0, 70, 260, 30);
		jp3.setBounds(45, 110, 200, 30);
		jp4.setBounds(280, 35, 245, 650);
		jp5.setBounds(50, 200, 180, 300);
		jp6.setBounds(230, 10, 100, 100);
		jp7.setBounds(0, 170, 100, 100);
		// 设置布局管理器
		this.setLayout(null);
		// 加入各个组件
		jp1.add(JL_produce);
		jp1.add(produce);
		jp2.add(JL_Num);
		jp2.add(price);
		jp3.add(jb1);
		jp4.add(JTresult);
		jp6.add(JL_result);
		// 设置窗口
		this.add(jp1);
		this.add(jp2);
		this.add(jp3);
		this.add(jp4);
		this.add(jp5);
		this.add(jp6);
		this.add(jp7);
		// -----------------------
		JScrollPane jsp = new JScrollPane(JTresult);// 添加滚动条
		jsp.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		jp4.add(jsp);
		// ------------------------
		jb1.addActionListener(this);
		this.setVisible(true);
	}

	public static void main(String[] args) {
		Demo mainView = new Demo();
		test1 a = new test1();
		a.start();

	}

	public static String readFileprice() {
		String pathname = "./output.txt";
		StringBuilder result = new StringBuilder();
		try (FileReader reader = new FileReader(pathname); BufferedReader br = new BufferedReader(reader)) {
			String line;

			while ((line = br.readLine()) != null) {
				// 一次读入一行数据
				result.append(line + System.lineSeparator());
			}
			br.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return result.toString();
	}

	public static String readFilename() {
		String pathname = "./outputname.txt";
		StringBuilder result = new StringBuilder();
		try (FileReader reader = new FileReader(pathname); BufferedReader br = new BufferedReader(reader)) {
			String line;

			while ((line = br.readLine()) != null) {
				// 一次读入一行数据
				result.append(line + System.lineSeparator());
			}
			br.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return result.toString();
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		String ss = price.getText();
		int produce_num = Integer.parseInt(ss);
		String s1 = readFileprice();
		if (e.getSource().equals(jb1)) {

			String[] splitAddress = s1.split(",");
			double[] prices = new double[splitAddress.length - 1];
			for (int i = 0; i < splitAddress.length - 1; i++) {
				prices[i] = Double.valueOf(splitAddress[i]);
				// System.out.println(prices[i]);
			}

			ZuiShaoFeiYongGouWu shop = new ZuiShaoFeiYongGouWu();
			shop.init(prices, produce_num);
			shop.comp(1);
			shop.out();			
		}
        
		JTresult.append(readFilename());
		
	}
}
