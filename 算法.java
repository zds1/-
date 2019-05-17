class Commodity {
	int piece;// ��������
	double price;// ����۸�
}

public class ZuiShaoFeiYongGouWu {
	private static int MAXCODE = 999;// ��Ʒ��������ֵ
	private static int SALECOMB = 99;// �Ż���Ʒ�����
	private static int KIND = 5; // ��Ʒ����
	private static int QUANTITY = 5; // ����ĳ����Ʒ���������ֵ
	private static int[] codes = new int[MAXCODE + 1];
	private static int[] zongjiage = new int[KIND + 1];
	private static int[] yhjiage = new int[KIND + 1];
	private static int b;// ������Ʒ������
	private static int s;// ��ǰ�Ż������
	private static int[] num = new int[MAXCODE + 1];// ��¼��Ʒ��������Ʒ����Ķ�Ӧ��ϵ
	private static int[] product = new int[KIND + 1];// ��¼��ͬ������Ʒ�Ĺ�������
	private static int[][] offer = new int[SALECOMB + 1][KIND + 1];
	// offer[i][j]: ��Ʒ��ϵ��Żݼ�(j=0)��ĳ���Ż������ĳ����Ʒ��Ҫ���������(j>0)
	private static Commodity[] purch = new Commodity[KIND + 1];// ��¼��ͬ��Ʒ�Ĺ��������͹���۸�
	private static int[][][][][] cost = new int[QUANTITY + 1][QUANTITY + 1][QUANTITY + 1][QUANTITY + 1][QUANTITY + 1];
	private static double[] a = new double[6];

	// ��¼���ι�����ܻ���

	public void minicost() {
		int i, j, k, m, n, p, minm;// cost[i][j][k][m][n]
		minm = 0;// ��С�ķ��ù���
		for (i = 1; i <= b; i++)// �Ե��������
			minm += product[i] * purch[i].price;

		for (p = 1; p <= s; p++)// �Żݷ�������
		{
			i = product[1] - offer[p][1];
			j = product[2] - offer[p][2];
			k = product[3] - offer[p][3];
			m = product[4] - offer[p][4];// ����Ҫ�����Ʒ����
			n = product[5] - offer[p][5];// �Ƚ�product����ͬ����Ĺ���������offer��ĳ���Ż�����е�ĳ����Ʒ��Ҫ�������������С
			if (i >= 0 && j >= 0 && k >= 0 && m >= 0 && n >= 0 && cost[i][j][k][m][n] + offer[p][0] < minm)
				// ��������������Ż���Ʒ������������ʣ�µĹ������Ļ��Ѽ����Ѿ��Żݹ��ļ۸�С��ǰһ�����ٻ���
				minm = cost[i][j][k][m][n] + offer[p][0];// ���ٻ�����Ϊʣ�µĹ������Ļ��Ѽ����Ѿ��Żݹ��ļ۸�
		}
		cost[product[1]][product[2]][product[3]][product[4]][product[5]] = minm;//
	}

	public void init(double[] a,int aa) {

		int i, j, n, p, t, code;
		for (i = 0; i < 100; i++)
			for (j = 0; j < 6; j++)
				offer[i][j] = 0;// ÿ���Ż���ϵ���Ʒ����������

		for (i = 0; i < 6; i++) {
			purch[i] = new Commodity();
			purch[i].piece = 0;
			purch[i].price = 0;
			product[i] = 0;

		}

		b = 5;// ��Ʒ����
		for (i = 1; i <= b; i++) {
			System.out.println("�������" + i + "����Ʒ���");
			codes[i] = i;
			purch[i].piece = aa;
			purch[i].price =  a[i - 1];
			System.out.println("��Ʒ���Ϊ" + codes[i] + "�۸�=" + purch[i].price + "����=" + purch[i].piece);
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
				offer[i][num[n]] = p;// ��i���Żݷ����ı��Ϊn����Ʒ���������
				zongjiage[j] = p * (int)purch[j].price - 1;
				yhjiage[j] = (int) (1 + Math.random() * zongjiage[j]);
				System.out.println("��" + j + "���Ż���Ʒ����Ϊ" + n + "���Ż���Ʒ����Ϊ" + p + ",�Żݺ�ļ۸�Ϊ" + yhjiage[j]);
			}
			offer[i][0] = yhjiage[i];// ��i���Żݷ������Żݼ۸�
		}
	}

	public void comp(int i) {
		if (i > b) {// ˵��B����Ʒ��product[]�Ѿ�ȷ��
			minicost();
			return;
		}

		for (int j = 0; j <= purch[i].piece; j++) {
			product[i] = j;// ��i����Ʒȡֵ��ΧΪ[0,purch[i]]
			comp(i + 1);// ��i+1����Ʒ
		}
	}

	public void out ()  {
		System.out.print(cost[product[1]][product[2]][product[3]][product[4]][product[5]]);
	}
}
