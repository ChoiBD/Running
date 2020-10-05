import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class MSNorth extends JPanel {

	// North �������� �г�
	JPanel center = new JPanel(new BorderLayout());
		JLabel upperLbl = new JLabel("ftowards ��  /  2020-08-27 ");
		JLabel groundLbl = new JLabel("8�� ���� �Ÿ� : 123km / 8�� ���� ���� : 8ȸ / 8�� �Ҹ� Į�θ� : 10,525cal");
			
	// east ��� Ű
	JPanel east = new JPanel(new BorderLayout());
		JButton logoutBtn = new JButton("�α׾ƿ�");
		JButton exitBtn = new JButton("���α׷� ����");
		JButton refreshBtn = new JButton("���� ��ħ");
		
	String id ; // �α��� id
	Calendar cal ;
	Font fnt = new Font("���� ���", 1, 15);
	Font fnt2 = new Font("���� ���", 1, 12);
	
	Setting set ;

	public MSNorth() {}
	
	public MSNorth(String id, Setting set) {
		this.id = id;
		
		this.set = set;

		setLayout(new BorderLayout());
		
		center.add("North",upperLbl);
			upperLbl.setPreferredSize(new Dimension(0,50));
		center.add("Center", groundLbl);
		add("Center",center);
		
		setUpperLbl();
		setGroundLbl();
		
			east.add("North",logoutBtn);
				logoutBtn.setPreferredSize(new Dimension(0,50));
			east.add("Center", exitBtn);	
			east.add("West", refreshBtn);	set.setBtnStyle(refreshBtn, 1 ,15);
				
		add("East", east);
		
		set.setPaneStyle(center);
		set.setLblStyle(upperLbl, 1, 15);
		set.setLblStyle(groundLbl, 1, 15);
		
		set.setBtnStyle(exitBtn);	set.setBtnStyle(logoutBtn);
	}
	
	// ��� �� ���ڿ� ����
	public void setUpperLbl() {
		upperLbl.setText("   " + id + " ��  /  " + setDate());
	}
	
	// �ϴ� �� ���ڿ� ����
	public void setGroundLbl() {
		String month = (String.valueOf(cal.get(Calendar.MONTH)+1).length()) == 1 ? "0" + String.valueOf(cal.get(Calendar.MONTH)+1) : String.valueOf(cal.get(Calendar.MONTH)+1);

		DecimalFormat format = new DecimalFormat("#,##0");
		// ���� ���� �Ÿ� / Į�θ�
		RecordDAO recDAO = RecordDAO.getInstance();	
		double[] personalMonthData = recDAO.getPersonalMonthRecord(id, month);
		double monthRun = personalMonthData[0];
		int monthMeet = (int) personalMonthData[1];
		String monthCal =format.format(personalMonthData[2]);
		// ���� ���� Ƚ��
		
		groundLbl.setText("  [" + month + "�� ���] ���� �Ÿ� : " + monthRun + "km / ���� ���� : "+monthMeet+"ȸ / �Ҹ� Į�θ� " + monthCal +"kcal" );
	}
	
	// ���� ���� > ���ڿ�
	public String setDate() {
		cal = Calendar.getInstance();
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy. MM. dd");
		return sdf.format(cal.getTime());
	}
}
