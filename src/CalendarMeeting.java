import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Calendar;
import java.util.HashMap;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;

public class CalendarMeeting extends JPanel implements ActionListener, ItemListener{
	
	JPanel northPane = new JPanel();
		JButton leftYear = new JButton("��");
		DefaultComboBoxModel<String> yearCB = new DefaultComboBoxModel<String>();
		JComboBox<String> yearTf = new JComboBox<String>(yearCB);
		JButton rightYear = new JButton("��");
		JLabel yearLbl = new JLabel("��  ");
		JButton leftMonth = new JButton("��");
		DefaultComboBoxModel<String> monthCB = new DefaultComboBoxModel<String>();
		JComboBox<String> monthTf = new JComboBox<String>(monthCB);
		JButton rightMonth = new JButton("��");
		JLabel monthLbl = new JLabel("��");
		
	JPanel calPane = new JPanel(new GridLayout(0,7));
	
	JPanel eastPane = new JPanel();
		JLabel txt1 = new JLabel(" ���� ���� ��");
		JLabel txt2 = new JLabel(" ���� ���� ��");
		JLabel txt3 = new JLabel(" ���� ���� ��");
		
		
	String id ;
	Setting set ; 
	
	Calendar cal = Calendar.getInstance();
	int year ; 
	int month ;
	MeetingDAO meetDAO = MeetingDAO.getInstance(); 
	
	public CalendarMeeting() {}
	
	public CalendarMeeting(String id, Setting set) {
		this.id = id;
		this.set = set;
		
		setLayout(new BorderLayout());
		
		add("North", northPane);	set.setPaneStyle(northPane);
			northPane.add(leftYear);		set.setBtnStyle(leftYear);
			northPane.add(yearTf);				
			northPane.add(yearLbl);		set.setLblStyle(yearLbl);
			northPane.add(rightYear);		set.setBtnStyle(rightYear);
			
			northPane.add(leftMonth);		set.setBtnStyle(leftMonth);
			northPane.add(monthTf);
			northPane.add(monthLbl);		set.setLblStyle(monthLbl);
			northPane.add(rightMonth);	set.setBtnStyle(rightMonth);
			
			
		
		add(calPane);
			calPane.setBackground(Color.lightGray);
			calPane.setOpaque(true);
		
		setCB();
		setTfFirst();
		
		setCalendar();
		
		yearTf.addItemListener(this);
		monthTf.addItemListener(this);
		leftYear.addActionListener(this);
		rightYear.addActionListener(this);
		leftMonth.addActionListener(this);
		rightMonth.addActionListener(this);
		
		
		setSize(600,520);
		setVisible(true);
	}
	
	public void setCB() {
		for (int y = 2010 ; y <= 2030 ; y++) {
			yearCB.addElement(String.valueOf(y));
		}
		
		for (int m = 1 ; m <= 12 ; m++) {
			if (m < 10) 
				monthCB.addElement("0"+m);
			else
				monthCB.addElement(String.valueOf(m));
		}
	}
	
	public void setTfFirst() {
		year = cal.get(Calendar.YEAR);
		month = cal.get(Calendar.MONTH);

		yearTf.setSelectedItem(String.valueOf(year));
		monthTf.setSelectedIndex(month);
	}
	
	public void setWeekTitle(){
		// Ÿ��Ʋ ���� 
		String[] week = {"��", "��","ȭ","��","��","��","��"};
		for (int i  = 0 ; i<week.length ; i++) {
			JLabel title = new JLabel(week[i],JLabel.CENTER);
			set.setLblStyle(title, 1, 16);
			if(i==0) {
				title.setForeground(Color.RED);
			} else if(i==6) {
				title.setForeground(Color.BLUE);
			}
			calPane.add(title);
		}
	}
	
	public void setCalendar() {
		setWeekTitle();
		//�޷� �����ϱ�
		// ��¥ ���ϱ�
		cal.set(year, month, 1);
		int firstWeek = cal.get(Calendar.DAY_OF_WEEK);
		
		cal.set(year, month+1, 1);
		cal.add(Calendar.DAY_OF_MONTH, -1);
		int lastDay = cal.get(Calendar.DAY_OF_MONTH);
		
		for(int i = 1 ; i < firstWeek ; i++) {
			JLabel blank = new JLabel("  ");
			blank.setBackground(Color.lightGray);
			blank.setOpaque(true);
			calPane.add(blank);
		}
		
		for(int day = 1 ; day <= lastDay ; day++) {
			JPanel dayPane = setDayPane(day);
			dayPane.setName(String.valueOf(day));
			calPane.add(dayPane);
			dayPane.addMouseListener(new MouseAdapter() {
				public void mouseClicked(MouseEvent me) {
					JPanel obj = (JPanel)me.getSource();
					String searchDate = obj.getName();
					// Ŭ���� ������ ���� ���� ��������
					popList(searchDate);
				}
			});
		}
	}
	
	public void popList(String date) {
		MeetingTab tab = new MeetingTab(id, set);
		JFrame pop = new JFrame();
		JPanel btnPane = new JPanel();
			JButton closeBtn = new JButton("�ݱ�");
		
		pop.setLayout(new BorderLayout());
		pop.add(tab);
				
		pop.add("South",btnPane);	set.setPaneStyle(btnPane);
			btnPane.add(closeBtn); 	set.setBtnStyle(closeBtn);
			closeBtn.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					pop.dispose();
				}
			});			
					
		tab.upperPane.setVisible(false);
		
		// list �����ϱ�
		tab.meetList = meetDAO.searchMeetingDate(getTerms(date));
		tab.setTable();
		tab.sp.setPreferredSize(new Dimension(550,270));
		tab.meetingList.getColumnModel().getColumn(3).setPreferredWidth(100);

		
		pop.setBounds(350,350,550,350);
		pop.setVisible(true);
	}
	
	public String[] getTerms(String date){
		String[] terms = new String[4];
		
		terms[0] = getDateString(Integer.parseInt(date));
		terms[1] = getDateString(Integer.parseInt(date));
		terms[2] = "%%";
		terms[3] = "%%";
		
		return terms;		
	}
	
	
	public JPanel setDayPane(int day) {
		Font fnt1 = new Font("���� ���", 1, 12);
		Font fnt2 = new Font("���� ���", 1, 10);
		JPanel dayPane = new JPanel(new GridLayout(4,2));
			dayPane.setBackground(Color.lightGray);
			dayPane.setBorder(new LineBorder(Color.black));
		JPanel empty = new JPanel(); dayPane.add(empty);
			empty.setOpaque(false);
		JLabel dayLbl = new JLabel(String.valueOf(day),JLabel.CENTER);	
			dayPane.add(dayLbl);
			dayLbl.setFont(fnt1);
		
		HashMap<String, Integer> cnt = meetDAO.calendarSet(getDateString(day));
			
		JLabel onLbl = new JLabel("���� :", JLabel.CENTER);		
			dayPane.add(onLbl);
			onLbl.setFont(fnt2);
		// �ش� ������ ���� ���� ���� ����
		String lblTxt = String.valueOf(cnt.get("1")==null ? 0 : cnt.get("1"))+" ��";
			JLabel onNum = new JLabel(lblTxt, JLabel.CENTER);	
			dayPane.add(onNum);
			onNum.setFont(fnt2);
	
		JLabel comLbl = new JLabel("�Ϸ� : ",JLabel.CENTER);	
			dayPane.add(comLbl);
			comLbl.setFont(fnt2);
		// �ش� ������ �Ϸ� ���� ���� ����
		String lblTxt2 = String.valueOf(cnt.get("2")==null ? 0 : cnt.get("2"))+" ��";	
			JLabel comNum = new JLabel(lblTxt2, JLabel.CENTER);
			dayPane.add(comNum);
			comNum.setFont(fnt2);

		JLabel canLbl = new JLabel("��� : ",JLabel.CENTER);		
			dayPane.add(canLbl);
			canLbl.setFont(fnt2);
		// �ش� ������ ĵ�� �� ���� ���� ����
		String lblTxt3 = String.valueOf(cnt.get("3")==null ? 0 : cnt.get("3"))+" ��";	
			JLabel canNum = new JLabel(lblTxt3, JLabel.CENTER);				
			dayPane.add(canNum);
			canNum.setFont(fnt2);
		
		return dayPane;
	}
	
	public String getDateString(int day){
		String date ;
		
		if(day<10) {
			date = String.valueOf(yearTf.getSelectedItem())+String.valueOf(monthTf.getSelectedItem())+"0"+String.valueOf(day);
		} else {
			date = String.valueOf(yearTf.getSelectedItem())+String.valueOf(monthTf.getSelectedItem())+String.valueOf(day);
		}
		
		return date;
	}
	

	@Override
	public void actionPerformed(ActionEvent e) {
		Object obj = e.getSource();

		if(obj == leftYear) {
			int idx = yearTf.getSelectedIndex();
			if(idx <= 0) {
				yearTf.setSelectedIndex(0);
			} else {
				yearTf.setSelectedIndex(idx-1);
			}
		} else if(obj == rightYear) {
			int idx = yearTf.getSelectedIndex();
			if(idx >= yearCB.getSize()-1) {
				yearTf.setSelectedIndex(yearCB.getSize()-1);
			} else {
				yearTf.setSelectedIndex(idx+1);
			}
		} else if(obj == leftMonth) {
			int idx = monthTf.getSelectedIndex();
			if(idx <= 0) {
				int idxYear = yearTf.getSelectedIndex();
				if(idxYear > 0) {
					monthTf.setSelectedIndex(11);
					yearTf.setSelectedIndex(idxYear-1);
				}
			} else {
				monthTf.setSelectedIndex(idx-1);
			}
		} else if(obj == rightMonth) {
			int idx = monthTf.getSelectedIndex();
			if(idx >= 11) {
				int idxYear = yearTf.getSelectedIndex();
				if(idxYear < yearCB.getSize()-1) {
					monthTf.setSelectedIndex(0);
					yearTf.setSelectedIndex(idxYear+1);
				}
			} else {
				monthTf.setSelectedIndex(idx+1);
			}
		}
	}

	@Override
	public void itemStateChanged(ItemEvent e) {
		year = Integer.parseInt(String.valueOf(yearTf.getSelectedItem()));
		month = monthTf.getSelectedIndex();

		calPane.removeAll();
		setCalendar();
		calPane.updateUI();
	}

}
