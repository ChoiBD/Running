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
import java.util.List;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

public class MeetingInfo extends JFrame implements ItemListener, ActionListener{
	String id ;
	int meetNo ;
	
											//	�Է�  			����
//	private int meetingNo;   / ���� �� ������   �ο��̺� 
//	private String date; 			�Է� �����Ϻ�����  // ���� �� ���� ������ �˶�
//	private String time;           �޺��ڽ� 2�� 24 / 10�� ����
//	private String location;      �ؽ�Ʈ �ڽ�
//	private String id;				������ id
//	private int nop ;				    ������ ���� 2�� �̻�  // ���� ������ ���Ϸ� ���� �Ұ���
//	private String code;			4�ڸ� �ڵ�
//	private String state;			���� �� 1 : ���� // 2: �Ϸ� // 3: ���
	
	// ���� �� ������ ��� �г� �ʿ�
	
	//�����
	JLabel margin1 = new JLabel(" "); JLabel margin2 = new JLabel(" ");
	JLabel margin3 = new JLabel(" "); JLabel margin4 = new JLabel(" ");
	
	JPanel centerPane = new JPanel(new BorderLayout());
		JPanel centerLbl = new JPanel(new GridLayout(7,1));
			JPanel blank1 = new JPanel();
				JLabel lbl1 = new JLabel("�Ϸù�ȣ",JLabel.RIGHT);
			JPanel blank2 = new JPanel();
				JLabel lbl2 = new JLabel("����*",JLabel.RIGHT);
			JPanel blank3 = new JPanel();
				JLabel lbl3 = new JLabel("�ð�*",JLabel.RIGHT);
			JPanel blank4 = new JPanel();
				JLabel lbl4 = new JLabel("���*",JLabel.RIGHT);
			JPanel blank5 = new JPanel();
				JLabel lbl5 = new JLabel("������",JLabel.RIGHT);
			JPanel blank6 = new JPanel();
				JLabel lbl6 = new JLabel("�����ο�* (2�� �̻�)",JLabel.RIGHT);
			JPanel blank7 = new JPanel();
				JLabel lbl7 = new JLabel("�����ڵ� (4�� �̳�)",JLabel.RIGHT);
				
		JPanel centerInput = new JPanel(new GridLayout(7,1));
			JPanel blank11 = new JPanel();
				JTextField noTf = new JTextField(5);
			JPanel blank12 = new JPanel();
				DefaultComboBoxModel<String> yc = new DefaultComboBoxModel<String>();
				JComboBox<String> yyCb= new JComboBox<String>(yc);
				JTextField yyTf = new JTextField(4);
					JLabel txt1 = new JLabel("��");
				DefaultComboBoxModel<String> mc = new DefaultComboBoxModel<String>();
				JComboBox<String> mmCb = new JComboBox<String>(mc);
				JTextField mmTf = new JTextField(3);
					JLabel txt2 = new JLabel("��");
				DefaultComboBoxModel<String> dc = new DefaultComboBoxModel<String>();
				JComboBox<String> ddCb = new JComboBox<String>(dc);
				JTextField ddTf = new JTextField(3);
					JLabel txt3 = new JLabel("��");
			JPanel blank13 = new JPanel();
				DefaultComboBoxModel<String> hc = new DefaultComboBoxModel<String>();
				JComboBox<String> hhCb = new JComboBox<String>(hc);
				JTextField hhTf = new JTextField(3);
					JLabel txt4 = new JLabel("��");
				DefaultComboBoxModel<String> mic = new DefaultComboBoxModel<String>();
				JComboBox<String> miCb = new JComboBox<String>(mic);
				JTextField miTf = new JTextField(3);
					JLabel txt5 = new JLabel("��");
			JPanel blank14 = new JPanel();
				JTextField locTf = new JTextField(20);
			JPanel blank15 = new JPanel();
				JTextField idTf = new JTextField(8);
			JPanel blank16 = new JPanel();
				DefaultComboBoxModel<Integer> nopC  = new DefaultComboBoxModel<Integer>();
				JComboBox<Integer> nopCb = new JComboBox<Integer>(nopC);
				JTextField nopTf = new JTextField(3);
			JPanel blank17 = new JPanel();
				JTextField codeTf = new JTextField(5);
				
			JLabel caution = new JLabel("       * �ʼ��׸��Դϴ�.");
			
		JPanel southPane = new JPanel(new BorderLayout());
			// ���� ��� �� �߰�
			JPanel southCenterPane = new JPanel(new BorderLayout());
				JPanel southCenterLblPane = new JPanel(new GridLayout(1,1));
					JPanel blank22 = new JPanel();
					JLabel partList = new JLabel("      ������ ���      ");
				JPanel southCenterContent = new JPanel();
					// ������ ��� ����Ʈ ����
					String title[] = {"������"};
					DefaultTableModel dtm = new DefaultTableModel(title, 0);
					JTable entryList = new JTable(dtm);
					JScrollPane sp = new JScrollPane(entryList);
				
			JPanel southBtnPane = new JPanel();
				JButton okBtn = new JButton("Ȯ��");
				JButton clearBtn = new JButton("�ʱ�ȭ");
				JButton closeBtn = new JButton("�ݱ�");
				// ���� ���
				JButton editBtn = new JButton("����");
				JButton delBtn = new JButton("���");
				// �Ϸ� ���
				JButton completeBtn = new JButton("�Ϸ�");
				// ������ ���
				JButton inBtn = new JButton("����");
				JButton outBtn = new JButton("���");
					
	// �ʵ�
	Calendar cal = Calendar.getInstance();
	MeetingDAO meetDAO = MeetingDAO.getInstance();
	MeetingVO meetVO ;
	EntryDAO entryDAO = EntryDAO.getInstance();
		
	Setting set ;

	public MeetingInfo() {}
	
	public MeetingInfo(String id, Setting set) {
		this.id = id;
		setTitle("���� ����");
		
		this.set = new Setting(id);
		
		// ���̾ƿ� ����
		// ����
		setLayout(new BorderLayout());
//		add("North", margin1); add("East", margin2); add("South", margin3); add("West",margin4);
//		style1(margin1);	style1(margin2);	style1(margin3);	style1(margin4);
		
		// �߾� �г�
		add(centerPane);
			centerPane.add("West", centerLbl);
				set.setPaneStyle(centerLbl);
				centerLbl.add(blank1);			blank1.add(lbl1);		set.setPaneStyle(blank1);	set.setLblStyle(lbl1);
				centerLbl.add(blank2);			blank2.add(lbl2);	set.setPaneStyle(blank2);	set.setLblStyle(lbl2);
				centerLbl.add(blank3);			blank3.add(lbl3);	set.setPaneStyle(blank3);	set.setLblStyle(lbl3);
				centerLbl.add(blank4);			blank4.add(lbl4);	set.setPaneStyle(blank4);	set.setLblStyle(lbl4);
				centerLbl.add(blank5);			blank5.add(lbl5);	set.setPaneStyle(blank5);	set.setLblStyle(lbl5);
				centerLbl.add(blank6);			blank6.add(lbl6);	set.setPaneStyle(blank6);	set.setLblStyle(lbl6);
				centerLbl.add(blank7);			blank7.add(lbl7);	set.setPaneStyle(blank7);	set.setLblStyle(lbl7);
			
			centerPane.add(centerInput);
				set.setPaneStyle(centerInput);
				centerInput.add(blank11);
					blank11.add(noTf); set.setPaneStyle(blank11);
						noTf.setEditable(false);										
				centerInput.add(blank12);	set.setPaneStyle(blank12);
					blank12.add(yyCb); blank12.add(yyTf); blank12.add(txt1);			set.setLblStyle(txt1);
					blank12.add(mmCb); blank12.add(mmTf);	blank12.add(txt2);	set.setLblStyle(txt2);
					blank12.add(ddCb); blank12.add(ddTf);	blank12.add(txt3);		set.setLblStyle(txt3);
				centerInput.add(blank13);	set.setPaneStyle(blank13);
					blank13.add(hhCb); blank13.add(hhTf);blank13.add(txt4);			set.setLblStyle(txt4);
					blank13.add(miCb); blank13.add(miTf); blank13.add(txt5);			set.setLblStyle(txt5);
				centerInput.add(blank14);
					blank14.add(locTf);		set.setPaneStyle(blank14);
				centerInput.add(blank15);
					blank15.add(idTf);		set.setPaneStyle(blank15);
						idTf.setEditable(false);
				centerInput.add(blank16);	set.setPaneStyle(blank16);
					blank16.add(nopCb); blank16.add(nopTf);
				centerInput.add(blank17);	set.setPaneStyle(blank17);
					blank17.add(codeTf);
			
			centerPane.add("South", caution);
				set.setLblStyle(caution);
				caution.setForeground(Color.RED);
				
		// �ϴ� / ������ ��� + ��ư�г�
		add("South", southPane);
			southPane.add(southCenterPane);
				southCenterPane.add("West",southCenterLblPane);
					southCenterLblPane.add(blank22);
						blank22.add(partList);	set.setPaneStyle(blank22); set.setLblStyle(partList);
				southCenterPane.add(southCenterContent);
					southCenterContent.add(sp);
					set.setPaneStyle(southCenterContent);
					sp.setPreferredSize(new Dimension(200,120));
				
			southPane.add("South",southBtnPane);
			set.setPaneStyle(southBtnPane);
				southBtnPane.add(okBtn);				set.setBtnStyle(okBtn);
				southBtnPane.add(editBtn);			set.setBtnStyle(editBtn);
				southBtnPane.add(clearBtn);			set.setBtnStyle(clearBtn);
				southBtnPane.add(delBtn);			set.setBtnStyle(delBtn);
				southBtnPane.add(inBtn);				set.setBtnStyle(inBtn);
				southBtnPane.add(outBtn);			set.setBtnStyle(outBtn);
				southBtnPane.add(completeBtn);	set.setBtnStyle(completeBtn);
				southBtnPane.add(closeBtn);			set.setBtnStyle(closeBtn);
		
		// ���� �� �г� �κ�����
		yyTf.setVisible(false); 		mmTf.setVisible(false); 		ddTf.setVisible(false);
		hhTf.setVisible(false);		miTf.setVisible(false);
		nopTf.setVisible(false);
		editBtn.setVisible(false); 	delBtn.setVisible(false); completeBtn.setVisible(false);
		inBtn.setVisible(false); 		outBtn.setVisible(false);
		
		southCenterPane.setVisible(false);
				
		// �Է� ����	
		codeTf.setDocument(new LimitDocument(4));
		
		// �ʱⰪ ����
		setComboList();
		setDefault();
		
		// ��ư �̺�Ʈ ���
		okBtn.addActionListener(this);
		editBtn.addActionListener(this);
		clearBtn.addActionListener(this);
		delBtn.addActionListener(this);
		closeBtn.addActionListener(this);
		inBtn.addActionListener(this);
		outBtn.addActionListener(this);
		completeBtn.addActionListener(this);
		entryList.addMouseListener(new MouseAdapter() {
			public void mouseReleased(MouseEvent me) {
				int mouse = me.getButton();
				if (mouse == 1) {
					cancelByHost();					
				}
			}
		});
		
		setBounds(300,300,400,350);
		setVisible(true);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);		
	}
	
	public void cancelByHost() {
		String selectedId = (String) dtm.getValueAt(entryList.getSelectedRow(), 0);
		if(!selectedId.equals(id)) {
			String msg = selectedId + "���� ���� ��û�� ����Ͻðڽ��ϱ�?";
			int cancelQ = JOptionPane.showConfirmDialog(this , msg, "", JOptionPane.YES_NO_OPTION);
			if( cancelQ == JOptionPane.YES_OPTION) {
				// ��� DAO
				//	   ��Ƽ��, ��Ʈ�� ����
				if (entryDAO.outByHost(selectedId, meetNo) == 1) {
					msg = selectedId + "���� ���� ��û�� ����Ͽ����ϴ�.";
					JOptionPane.showMessageDialog(this, msg );
					//��Ʈ�� ����
					setEntry(meetNo);
				} 
			}
		}
	}
	
	public MeetingInfo(String id, int no, Setting set) {
		this(id, set);
		this.meetNo = no;
		
		setInput(meetNo);
		setEntry(meetNo);

		// ���� �Ϸù�ȣ�� ������ â�� ������ ���
		if (meetVO.getState().equals("1")) {
			if (idTf.getText().equals(this.id)) {
				// 1. �ڽ��� ������ ��� ���� >>
				if (getTime() > 0) {
					// ���� �ð����� ���� ������ ��� >> ���� ���
					setTitle("���� ����");
					setModeEdit();
				} else { 
					// ���� �ð��� ���� �ð����� ���� ��� >> �Ϸ� ���
					setTitle("���� �Ϸ�");
					setModeComplete();
					delBtn.setVisible(true);
				}
			} else {
			// 2. Ÿ���� ������ ���  // ���� / ��� / �ݱ�
				setTitle("���� ����");
				setModeView();
				
				// �ش� ���ӿ� ���� ���ο� ���� ���� ��û / ��� ��ư ����
				inOutCheck();
				
				// ���� ���� �ð��� ���� �ð��̶�� ���� ��û ��ư�� �������� ����.
				// ��� ��ư�� ����
				if(getTime() < 0) {
					inBtn.setVisible(false);
				} 
			}
		} else {
			setTitle("���� ����");
			setModeComplete();
			completeBtn.setVisible(false);
			entryList.setEnabled(false);
		}
	}
	
	public void setComboList() {
		// �޺� �ڽ� ������ ����
		for (int i = 2019 ; i <= 2030 ; i++) {
			yc.addElement(String.valueOf(i));
		}
		
		for (int m = 1 ; m <=12 ; m++ ) {
			if ( m< 10)
				mc.addElement("0"+m);
			else 
				mc.addElement(String.valueOf(m));
		}
		
		for (int h = 0 ; h <= 23 ; h++) {
			if ( h< 10)
				hc.addElement("0"+h);
			else
				hc.addElement(String.valueOf(h));
		}
		
		for (int mi = 0 ; mi <=55 ; mi+=5) {
			if (mi < 10)
				mic.addElement("0"+mi);
			else
				mic.addElement(String.valueOf(mi));
		} 
		
		for (int p = 2 ; p <= 30 ; p++) {
			nopC.addElement(p);
		}
	}
	
	public void setDefault() {
			
		yyCb.setSelectedItem(String.valueOf(cal.get(Calendar.YEAR)));
		mmCb.setSelectedIndex(cal.get(Calendar.MONTH));
		setDayComboBoxModel(yyCb, mmCb, dc);
		ddCb.setSelectedIndex(cal.get(Calendar.DAY_OF_MONTH)-1);
		
		hhCb.setSelectedIndex(0);
		miCb.setSelectedIndex(0);
		nopCb.setSelectedIndex(0);
		
		idTf.setText(id);
		locTf.setText("");
		codeTf.setText("");
	}
	
	public void setModeView() {
		
		yyCb.setVisible(false); yyTf.setVisible(true);
		mmCb.setVisible(false); mmTf.setVisible(true);
		ddCb.setVisible(false); ddTf.setVisible(true);
		hhCb.setVisible(false); hhTf.setVisible(true);
		miCb.setVisible(false); miTf.setVisible(true);
		nopCb.setVisible(false); nopTf.setVisible(true);
		
		okBtn.setVisible(false); clearBtn.setVisible(false);
		
		yyTf.setEditable(false); mmTf.setEditable(false); ddTf.setEditable(false);
		hhTf.setEditable(false); miTf.setEditable(false);
		locTf.setEditable(false);
		nopTf.setEditable(false);
		
		caution.setVisible(false);
		entryList.setEnabled(false);
		
		southCenterPane.setVisible(true);
		setBounds(300,300,400,450);
		lbl2.setText("����");
		lbl3.setText("�ð�");
		lbl4.setText("���");
		lbl6.setText("�����ο�");
	}
	
	public void setModeEdit() {
		okBtn.setVisible(false); clearBtn.setVisible(false);
		editBtn.setVisible(true); delBtn.setVisible(true);
			
		codeTf.setText(meetVO.getCode());
		
		southCenterPane.setVisible(true);
		setBounds(300,300,400,450);
	}
	
	public void setModeComplete() {
		yyCb.setVisible(false); yyTf.setVisible(true); yyTf.setEditable(false);
		mmCb.setVisible(false); mmTf.setVisible(true);mmTf.setEditable(false);
		ddCb.setVisible(false); ddTf.setVisible(true);ddTf.setEditable(false);
		hhCb.setVisible(false); hhTf.setVisible(true);hhTf.setEditable(false);
		miCb.setVisible(false); miTf.setVisible(true);miTf.setEditable(false);
		nopCb.setVisible(false); nopTf.setVisible(true);nopTf.setEditable(false);
		locTf.setEditable(false); codeTf.setEditable(false);
		
		okBtn.setVisible(false); clearBtn.setVisible(false);
		completeBtn.setVisible(true);
		
		southCenterPane.setVisible(true);
		setBounds(300,300,400,450);
	}
	
	public void setDayComboBoxModel(JComboBox<String>combo1, JComboBox<String> combo2, DefaultComboBoxModel<String> model) {
		try {
			Calendar temp = Calendar.getInstance();
			int year = Integer.parseInt(String.valueOf(combo1.getSelectedItem()));
			int month = Integer.parseInt(String.valueOf(combo2.getSelectedItem()));
			temp.set(year, month,1);
			temp.add(Calendar.DAY_OF_MONTH, -1);
			
			int lastDay = temp.get(Calendar.DAY_OF_MONTH);
			for (int d = 1 ; d <= lastDay; d++) {
				if (d < 10) 
					model.addElement("0"+String.valueOf(d));
				else
					model.addElement(String.valueOf(d));
			}		
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public long getTime() {
		int year = Integer.parseInt(String.valueOf(yyCb.getSelectedItem()));
		int month = Integer.parseInt(String.valueOf(mmCb.getSelectedItem()));
		int day = Integer.parseInt(String.valueOf(ddCb.getSelectedItem()));
		int hour = Integer.parseInt(String.valueOf(hhCb.getSelectedItem()));
		int min = Integer.parseInt(String.valueOf(miCb.getSelectedItem()));
		
		cal = Calendar.getInstance();
		Calendar meetDay = Calendar.getInstance();
		meetDay.set(year,  month-1, day, hour, min);
		
		long diff = meetDay.getTimeInMillis() - cal.getTimeInMillis();
		long diffHour = diff/(1000); // 1���� Ŀ�� ��û�� ���� 1�ð� = 3600��
		
		return diffHour;
	}
	
	public String getDateInput() {
		String year = yc.getElementAt(yyCb.getSelectedIndex());
		String month = mc.getElementAt(mmCb.getSelectedIndex());
		String day = dc.getElementAt(ddCb.getSelectedIndex());
		
		return year+month+day;
	}
	
	public String getTimeInput() {
		String hour = hc.getElementAt(hhCb.getSelectedIndex());
		String min = mic.getElementAt(miCb.getSelectedIndex());
		
		return hour+min;
	}
	
	public MeetingVO getVO() {
		MeetingVO vo = new MeetingVO();
		
		vo.setMeetingNo(noTf.getText().equals("")?0:Integer.parseInt(noTf.getText()));
		vo.setDate(getDateInput());
		vo.setTime(getTimeInput());
		vo.setLocation(locTf.getText());
		vo.setId(idTf.getText());
		vo.setNop(nopC.getElementAt(nopCb.getSelectedIndex()));
		vo.setCode(codeTf.getText());
		
		return vo;
	}
	
	public void setInput(int no) {
		meetVO = meetDAO.getMeeting(no);
		
		noTf.setText(String.valueOf(no));
		yyTf.setText(meetVO.getDate().substring(0,4));
		mmTf.setText(meetVO.getDate().substring(4,6));
		ddTf.setText(meetVO.getDate().substring(6));
		hhTf.setText(meetVO.getTime().substring(0,2));
		miTf.setText(meetVO.getTime().substring(2));
		idTf.setText(meetVO.getId());
		locTf.setText(meetVO.getLocation());
		nopTf.setText(String.valueOf(meetVO.getNop()));
		
		// ȣ��Ʈ��
		yyCb.setSelectedItem(meetVO.getDate().substring(0,4));
		mmCb.setSelectedItem(meetVO.getDate().substring(4,6));
		ddCb.setSelectedItem(meetVO.getDate().substring(6));
		hhCb.setSelectedItem(meetVO.getTime().substring(0,2));
		miCb.setSelectedItem(meetVO.getTime().substring(2));
		nopCb.setSelectedIndex((meetVO.getNop())-2);
	}
	
	public void setEntry(int meetNo) {
		dtm.setRowCount(0);
		EntryDAO enDAO = EntryDAO.getInstance();
		
		List<EntryVO> entry = enDAO.entryList(meetNo);
		
		for (int i  = 0 ; i <entry.size() ; i++) {
			EntryVO vo = entry.get(i);
			dtm.addRow(new String[]{vo.getId()});
		}
	}
	
	public boolean entryCheck() {
		for ( int i = 0 ; i < entryList.getRowCount() ; i++) {
			if (this.id.equals(String.valueOf(entryList.getValueAt(i, 0)))) {
				return true;
			}
		}
		return false;
	}
	
	public void inOutCheck() {
		if  (entryCheck()){
			// �̹� �ش� ���ӿ� ������ ���
			inBtn.setVisible(false);
			outBtn.setVisible(true);
			codeTf.setEnabled(false);
		} else {
			// �ش� ���ӿ� ������ �� ���� ���
			inBtn.setVisible(true);
			outBtn.setVisible(false);
		}
	}
	
	@Override
	public void itemStateChanged(ItemEvent e) {
		try {
			Object obj = e.getSource();
			if (obj == yyCb || obj == mmCb) {
				dc.removeAllElements();
				setDayComboBoxModel(yyCb, mmCb, dc);
			} else if (obj == nopCb ) {
				if (nopC.getElementAt(nopCb.getSelectedIndex()) < entryList.getRowCount()) {
					JOptionPane.showMessageDialog(this,"�̹� ������ �ο����� ���� ���� �ο��� ���� ������ �� �����ϴ�.");
					nopCb.setSelectedItem(entryList.getRowCount());
				}
			}
		} catch(Exception ee) {
			ee.printStackTrace();
		}
	}
	
	@Override
	public void actionPerformed(ActionEvent ae) {
		Object event = ae.getSource();
		try {
			if(event == okBtn) {
				createMeeting();
			} else if (event == clearBtn) {
				setDefault();
			} else if (event == closeBtn) {
				dispose();
			} else if (event == editBtn) {
				editMeeting();
			} else if (event == delBtn) {
				deleteMeeting();
			} else if (event == inBtn) {
				inMeeting();
			} else if (event == outBtn) {
				outMeeting();
			} else if (event == completeBtn) {
				completeMeeting();
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void createMeeting() {
		// �ʼ� �׸� �Է��� �� �Ǿ��� �� üũ
		// ���� ���� / �ð��� ���� �ð� + 1�ð� ���� ������ üũ
		if( getTime() < 3600) {
			JOptionPane.showMessageDialog(this,"���� �ð��� ���� �ð����� 1�ð� ���� ���� ���� �����մϴ�.");
		} else if (locTf.getText().equals("")) {
			JOptionPane.showMessageDialog(this,"���� ��Ҹ� �Է����ּ���.");
		} else {
			// DAO ���� DB �� ����
			if (meetDAO.createMeeting(getVO()) == 1) {
				JOptionPane.showMessageDialog(this,"���ο� ������ �����Ǿ����ϴ�.");
				setDefault();
			} else {
				JOptionPane.showMessageDialog(this,"���� ������ �����Ͽ����ϴ�.");
			}
		}
	}
	
	public void editMeeting() {
		// �ʼ� �׸� �Է��� �� �Ǿ��� �� üũ
		// ���� ���� / �ð��� ���� �ð� + 1�ð� ���� ������ üũ

		// ���� �����ڰ� �ִ� ���� �޼��� �ڽ�
		// nop �� ������ ���ں��� ���� ������ �Ұ����ϵ��� �̰� selceted listener ���� ���� �����Ѱ�
		int editQ = JOptionPane.showConfirmDialog(this,"���� �ش� ���ӿ� �����ڰ� �ֽ��ϴ�.\n���� ������ �����Ͻðڽ��ϱ�?","",JOptionPane.YES_NO_OPTION);
		if (editQ == JOptionPane.YES_OPTION) {
			if( getTime() < 3600) {
				JOptionPane.showMessageDialog(this,"���� �ð��� ���� �ð����� 1�ð� ���� ���� ���� �����մϴ�.");
			} else if (locTf.getText().equals("")) {
				JOptionPane.showMessageDialog(this,"���� ��Ҹ� �Է����ּ���.");
			} else {
				// DAO ���� DB �� ����
				if(entryList.getRowCount() == 1 && meetDAO.editMeeting(getVO()) == 1) {
					JOptionPane.showMessageDialog(this,"���� ������ ����Ǿ����ϴ�.");
					setInput(meetNo);
				} else {
					if (meetDAO.editMeeting(getVO()) == entryList.getRowCount()-1) {
						JOptionPane.showMessageDialog(this,"���� ������ ����Ǿ����ϴ�.");
						setInput(meetNo);
					} else {
						JOptionPane.showMessageDialog(this,"���� ���� ���濡 �����Ͽ����ϴ�.");
					}
				}
			}
		}
	}
	
	public void deleteMeeting() {
		int delQ = JOptionPane.showConfirmDialog(this,"���� �ش� ���ӿ� �����ڰ� �ֽ��ϴ�.\n������ ����Ͻðڽ��ϱ�?","",JOptionPane.YES_NO_OPTION);
		if (delQ == JOptionPane.YES_OPTION) {
			if (meetDAO.cancelMeeting(meetNo, idTf.getText()) == 1) {
				JOptionPane.showMessageDialog(this,"������ ��ҵǾ����ϴ�.");
				dispose(); } }
	}
	
	public void inMeeting() {
		// if ( ���� �ο��� �� á�� ���)
		if (!codeCheck()) {
			JOptionPane.showMessageDialog(this, "���� �ڵ尡 Ʋ�Ƚ��ϴ�.\n�ٽ� �Է����ֽʽÿ�.");
		} else if (Integer.parseInt(nopTf.getText()) <= entryList.getRowCount()) {
		// ���� �Ұ�
			JOptionPane.showMessageDialog(this, "���̻� ������ �Ұ����� �����Դϴ�.");
		} else {			
			// ��Ʈ���� �ش� id �߰�
			// ������ ���̵� ��Ƽ�� �߰�
			if(entryDAO.inMeeting(this.id, meetNo, idTf.getText()) ==1 ) {
				JOptionPane.showMessageDialog(this,"���� ��û�� �Ϸ� �Ǿ����ϴ�.");
				setEntry(meetNo);
				dispose();
			}
		}
	}
	
	public void outMeeting() {
		int quitQ = JOptionPane.showConfirmDialog(this, "���� ��û�� ����Ͻðڽ��ϱ�?","", JOptionPane.YES_NO_OPTION);
		if (quitQ == JOptionPane.YES_OPTION) {
			// 	��Ʈ������ �ش� id ����
			// ������ ���̵� ��Ƽ�� �߰�
			if (entryDAO.outMeeting(this.id, meetNo, idTf.getText()) == 1) { 
				JOptionPane.showMessageDialog(this,"���� ��û�� ��ҵǾ����ϴ�.");
				setEntry(meetNo);
				dispose();
			}
		}
	}
	
	public void completeMeeting() {
		// 1. ������ ����Ʈ�� ������ ����Ʈ�� �´� �� Ȯ�� ��û
		int entryQ = JOptionPane.showConfirmDialog(this, "���� ���� ���¸� �Ϸ�� �ѱ�� �Ǹ� ���� ������ ����Ʈ ��� ���� ó���� �˴ϴ�."
																+ "\n������ ����Ʈ�� �����Ͻðڽ��ϱ�?","",JOptionPane.YES_NO_OPTION);
		if (entryQ == 0) {
			// 2. �ҿ� �ð� ����
			String time = JOptionPane.showInputDialog(this,"���� �ҿ�ð��� �ð�/������ ���ڸ� �Է��ϼ���. ex) 1�ð� 30�� >> '0130'");
			if (timeCheck(time)) {
				JOptionPane.showMessageDialog(this, "�ҿ� �ð� �Է��� �߸� �Ǿ����ϴ�.");
			} else {
				String date = yyTf.getText() + mmTf.getText() + ddTf.getText();
				RecordDAO recDAO = RecordDAO.getInstance();
				int complete = 0;
				for (int i = 0 ; i < entryList.getRowCount() ; i++) {
					String id = (String) entryList.getValueAt(i, 0);
					recDAO.createRecByMeeting(id, date, time, meetNo);
					complete++;
				}
				if(complete == entryList.getRowCount()) {
					JOptionPane.showMessageDialog(this, "�����ڵ��� ��� ������ �Ϸ�Ǿ����ϴ�.");
					meetDAO.statusChange(2, meetNo);
					dispose();
				}
			}
		}
	}
	
	public boolean timeCheck(String time) {
		if(time.length() < 4 || time.length() >5) {
			return true;
		} else if (Integer.parseInt(time.substring(2)) < 0  || Integer.parseInt(time.substring(2)) > 59) {
			return true;
		} else if (Integer.parseInt(time) < 0) {
			return true;
		}
		return false;
	}
	
	public boolean codeCheck() {
		if (meetVO.getCode() == null) {
			if (codeTf.getText().equals("")) {
				return true;			} 
			else {
				return false;			}
		} else {
			if (meetVO.getCode().equals(codeTf.getText())) {
				return true;			} 
		}
		return false;
	}
}
