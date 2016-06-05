package com.topiniu;

import java.net.URL;
import java.text.DecimalFormat;
import java.util.*;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

import javax.swing.JOptionPane;


import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;

import java.awt.Dimension;
import java.awt.Toolkit;

class SwingMain extends JFrame implements MouseWheelListener, MouseListener{
	
	private static final long serialVersionUID = 1L;

	private MyPanel panel;
	
	private int cou = 90;//��ת�Ƕ�
	private double mouseStage = 4.1;//��¼���㰴��������Χ0~14
	private static Map<Double, Double> stageNum = new HashMap<Double, Double>();//�ӱ�����
	private DecimalFormat df = new DecimalFormat("######0.0");//���ָ�ʽ����
	
	private static int ppi;//ppi
	private static int ppm;//ÿ������ռ������
	

	//�����������
	private Random rand = new Random(System.currentTimeMillis());
	
	{
		//��ʼ���ӱ�����--5m
		stageNum.put(4.0, 72.72);
		stageNum.put(4.1, 57.76);
		stageNum.put(4.2, 45.88);
		stageNum.put(4.3, 36.45);
		stageNum.put(4.4, 28.95);
		stageNum.put(4.5, 23.00);
		stageNum.put(4.6, 18.27);
		stageNum.put(4.7, 14.51);
		stageNum.put(4.8, 11.53);
		stageNum.put(4.9, 9.16);
		stageNum.put(5.0, 7.27);
		stageNum.put(5.1, 5.78);
		stageNum.put(5.2, 4.59);
		stageNum.put(5.3, 3.64);
		System.out.println("�ӱ����ݳ�ʼ�����");
	}
	public SwingMain() {
		/*
		 * 1.����frame�ĳ�ʼ������
		 * 2.��panel��ӵ�frame�в�ȫ����
		 * 3.�����궯�������¼�
		 */
		
		setSize(1000,900);
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		//����frame��ʼ��λ��Ϊ��Ļ�м�
		setLocationRelativeTo(null);
		
		panel = new MyPanel();
		panel.setSize(this.getWidth(),this.getHeight());
		int initPicSize = (int)(stageNum.get(4.1)*ppm);
		panel.setW(initPicSize);
		panel.setH(initPicSize);
		add(panel);
		
		
		addMouseWheelListener(this);
		addMouseListener(this);
	}
	
	@Override
	public void mouseWheelMoved(MouseWheelEvent e) {
		if(e.getWheelRotation() == -1)//�ϻ���ʱ��Ŵ�
		{
			if(panel.getW() == 533 && panel.getH() == 665)
			{
				JOptionPane.showMessageDialog(this, "�ף������ٴ�����");
			}else{
				panel.setC(panel.getC() + Math.toRadians(cou*rand.nextInt(24)));
				panel.repaint();
			}
		}
		
		if(e.getWheelRotation() == 1)//�»�˳ʱ����С
		{
			if(panel.getW() == 21 && panel.getH() == 25)
			{
				JOptionPane.showMessageDialog(this, "�ף�������С����");
			}else{
				double dou = Math.toRadians(cou);
				System.out.println(dou);
			
				//�µ���ת�㷨��cou=90
				panel.setC(panel.getC() + Math.toRadians(cou*rand.nextInt(24)));
				System.out.println("***Ҫת���ĽǶ�Ϊ�� " + panel.getC() + " ***");
				panel.repaint();
			}
		}
		
	}
	public static void main(String[] args) {
		double screenSize = Double.parseDouble(JOptionPane.showInputDialog("�����������豸��Ļ�ߴ磺"));
		Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
		//����ppi
		ppi = (int)(Math.sqrt((dimension.width*dimension.width + dimension.height*dimension.height))/screenSize);
		ppm = (int) (ppi/25.4);
		//System.out.println(ppm);
		String screenMessage = "��Ļ��С��\t" + screenSize +
				"\n�ֱ��ʣ�\t" + dimension.width + "*" + dimension.height +
				"\nPPI��\t" + ppi;
		JOptionPane.showMessageDialog(null, screenMessage);
		new SwingMain();
	}

	@Override
	public void mouseClicked(MouseEvent e) {
			int i = e.getButton();
			if(i == MouseEvent.BUTTON1)
			{
				if(mouseStage>4.0)
				{
					//���--�Ŵ�
					mouseStage=Double.parseDouble(df.format(mouseStage-0.1));
					System.out.println("����Ŵ�  " + mouseStage);
					int t = (int)(stageNum.get(mouseStage)*ppm);
					panel.setW(t);
					panel.setH(t);
					
					//panel.setImgNum(panel.getImgNum() + 0.1);
					rePaint();
					panel.setStageInfo(mouseStage);
				}else{
					System.out.println("mouseStage=" + mouseStage);
					JOptionPane.showMessageDialog(this, "�ѵ������");
				}
				
			}else if(i == MouseEvent.BUTTON3)
			{
				if(mouseStage<5.3)
				{
					//�Ҽ�--��С
					mouseStage=Double.parseDouble(df.format(mouseStage+0.1));
					System.out.println("����Ŵ�  " + mouseStage);
					int t = (int)(stageNum.get(mouseStage)*ppm);
					panel.setW(t);
					panel.setH(t);
					//panel.setImgNum(panel.getImgNum() - 0.1);
					rePaint();
					panel.setStageInfo(mouseStage);
				}else{
					System.out.println("mouseStage=" + mouseStage);
					JOptionPane.showMessageDialog(this, "�ѵ�����С");
				}
			}
	}

	public void rePaint()
	{
		double dou = Math.toRadians(cou);
		System.out.println(dou);
	
		//�µ���ת�㷨��cou=90
		panel.setC(panel.getC() + Math.toRadians(cou*rand.nextInt(24)));
		
		//����E�ֶ�Ӧֵ
		panel.setStageInfo(panel.getStageInfo()-0.5);
		System.out.println("***Ҫת���ĽǶ�Ϊ�� " + panel.getC() + " ***");
		panel.repaint();
	}
	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
}

class MyPanel extends JPanel{
	private static final long serialVersionUID = 1L;
	/*
	 * c--��ת�Ƕ�
	 * w��h--ͼƬ�Ŀ��(����)
	 * screenW��screenH--JPanel�Ŀ��(����)
	 * stageInfo--��ǰ�ӱ��Ӧ�Ĳο���ֵ
	 */
	private double c=0;
	private int w,h;
	private int screenH=0;
	
	private double stageInfo=4.1;

	@Override
	protected void paintComponent(Graphics g) {
		System.out.println("*** JPanel��СΪ:" + this.getWidth()+ "  " + this.getHeight()+ " ***");
		//��ȡͼƬ��Դ·��
		URL imgUrl = SwingMain.class.getResource("/img/main.png");
		g.setColor(Color.RED);
		g.fillRect(0, 0, 160, this.getHeight());
		g.setColor(Color.WHITE);
		
		g.setFont(new Font("����", Font.BOLD, 40));
		g.drawString("��ǰֵ��",0,50);
		
		g.setFont(new Font("����", Font.BOLD, 90));
		g.drawString(String.valueOf(stageInfo),0,130);

		g.setFont(new Font("����", Font.BOLD, 30));
		g.drawString("�ο����룺",0,180);
		
		g.setFont(new Font("����", Font.BOLD, 90));
		g.drawString("5m",0,250);
		
		g.setFont(new Font("����", Font.BOLD, 15));
		g.drawString("  *���ݿ��ܻ���ƫ��", 0, this.getHeight()-10);
		
		Graphics2D d2 = (Graphics2D)g;
		
		d2.setColor(Color.white);
		d2.fillRect(160,0,this.getWidth(),this.getHeight());
		d2.rotate(c,((this.getWidth()+160)/2),(this.getHeight()/2));
		//System.out.println("*** ��ת����X=:" + (w/2)+((this.getWidth()+160)/2) + " Y=" + (h/2)+(this.getHeight()/2)+ " ***");
		
		Image image = new ImageIcon(imgUrl).getImage();
		
		/*
		 * drawImage(Image,x,y,w,h,ImageObserver )����
		 * img - Ҫ���Ƶ�ָ��ͼ����� img Ϊ null����˷�����ִ���κζ���
		 *	x - x ����
		 *	y - y ����
		 *	width - ���εĿ��
		 *	height - ���εĸ߶�
		 *	observer - ��ת���˸���ͼ��ʱҪ֪ͨ�Ķ���
		 */
		d2.drawImage(image,((this.getWidth()+160)/2)-w/2, (this.getHeight()/2)-h/2, w, h,this);
		//g.drawString(String.valueOf(stageInfo),120,30);
		System.out.println(String.valueOf(stageInfo));
		//System.out.println("*** draeImage����Ϊ: X=" + (this.getWidth()/2)-300 + " Y= " + (this.getHeight()/2)-305 + " ***");
		System.out.println("***ͼƬ��С��w=" +w  + "  h=" + h+ "***");
		d2.dispose();
	}
	public double getC() {
		return c;
	}
	public void setC(double c) {
		this.c = c;
	}
	public int getW() {
		return w;
	}
	public void setW(int w) {
		this.w = w;
	}
	public int getH() {
		return h;
	}
	public void setH(int h) {
		this.h = h;
	}
	
	public int getScreenH() {
		return screenH;
	}
	public void setScreenH(int screenH) {
		this.screenH = screenH;
	}
	public double getStageInfo() {
		return stageInfo;
	}
	public void setStageInfo(double stageInfo) {
		this.stageInfo = stageInfo;
	}
}







