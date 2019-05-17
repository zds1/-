class Commodity {
	int piece;// 购买数量
	double price;// 购买价格
}

public class ZuiShaoFeiYongGouWu {
	private static int MAXCODE = 999;// 商品编码的最大值
	private static int SALECOMB = 99;// 优惠商品组合数
	private static int KIND = 5; // 商品种类
	private static int QUANTITY = 5; // 购买某种商品数量的最大值
	private static int[] codes = new int[MAXCODE + 1];
	private static int[] zongjiage = new int[KIND + 1];
	private static int[] yhjiage = new int[KIND + 1];
	private static int b;// 购买商品种类数
	private static int s;// 当前优惠组合数
	private static int[] num = new int[MAXCODE + 1];// 记录商品编码与商品种类的对应关系
	private static int[] product = new int[KIND + 1];// 记录不同种类商品的购买数量
	private static int[][] offer = new int[SALECOMB + 1][KIND + 1];
	// offer[i][j]: 商品组合的优惠价(j=0)；某种优惠组合中某种商品需要购买的数量(j>0)
	private static Commodity[] purch = new Commodity[KIND + 1];// 记录不同商品的购买数量和购买价格
	private static int[][][][][] cost = new int[QUANTITY + 1][QUANTITY + 1][QUANTITY + 1][QUANTITY + 1][QUANTITY + 1];
	private static double[] a = new double[6];

	// 记录本次购买的总花费

	public void minicost() {
		int i, j, k, m, n, p, minm;// cost[i][j][k][m][n]
		minm = 0;// 最小的费用归零
		for (i = 1; i <= b; i++)// 以单价买最贵
			minm += product[i] * purch[i].price;

		for (p = 1; p <= s; p++)// 优惠方案种数
		{
			i = product[1] - offer[p][1];
			j = product[2] - offer[p][2];
			k = product[3] - offer[p][3];
			m = product[4] - offer[p][4];// 还需要买的商品数量
			n = product[5] - offer[p][5];// 比较product（不同种类的购买数）与offer（某种优惠组合中的某件商品需要购买的数量）大小
			if (i >= 0 && j >= 0 && k >= 0 && m >= 0 && n >= 0 && cost[i][j][k][m][n] + offer[p][0] < minm)
				// 如果购买数满足优惠商品的需求量，且剩下的购买数的花费加上已经优惠过的价格小于前一次最少花费
				minm = cost[i][j][k][m][n] + offer[p][0];// 最少花费则为剩下的购买数的花费加上已经优惠过的价格
		}
		cost[product[1]][product[2]][product[3]][product[4]][product[5]] = minm;//
	}

	public void init(double[] a,int aa) {

		int i, j, n, p, t, code;
		for (i = 0; i < 100; i++)
			for (j = 0; j < 6; j++)
				offer[i][j] = 0;// 每种优惠组合的商品数量都归零

		for (i = 0; i < 6; i++) {
			purch[i] = new Commodity();
			purch[i].piece = 0;
			purch[i].price = 0;
			product[i] = 0;

		}

		b = 5;// 商品种类
		for (i = 1; i <= b; i++) {
			System.out.println("请输入第" + i + "个商品编号");
			codes[i] = i;
			purch[i].piece = aa;
			purch[i].price =  a[i - 1];
			System.out.println("商品编号为" + codes[i] + "价格=" + purch[i].price + "数量=" + purch[i].piece);
			num[codes[i]] = i;
			if (codes[i] != i) {
				break;
			}
		}
		s = 1;
		for (i = 1; i <= s; i++) {
			t = i;
			for (j = 1; j <= t; j++) {
				int h = 1 + (int) (Math.random() * b);
				n = codes[h];
				p = 1 + (int) (Math.random() * purch[i].piece);
				offer[i][num[n]] = p;// 第i种优惠方案的编号为n的商品种类的数量
				zongjiage[j] = p * (int)purch[j].price - 1;
				yhjiage[j] = (int) (1 + Math.random() * zongjiage[j]);
				System.out.println("第" + j + "种优惠商品编码为" + n + "，优惠商品数量为" + p + ",优惠后的价格为" + yhjiage[j]);
			}
			offer[i][0] = yhjiage[i];// 第i中优惠方法的优惠价格
		}
	}

	public void comp(int i) {
		if (i > b) {// 说明B种商品的product[]已经确定
			minicost();
			return;
		}

		for (int j = 0; j <= purch[i].piece; j++) {
			product[i] = j;// 第i种商品取值范围为[0,purch[i]]
			comp(i + 1);// 第i+1种商品
		}
	}

	public void out ()  {
		System.out.print(cost[product[1]][product[2]][product[3]][product[4]][product[5]]);
	}
}
