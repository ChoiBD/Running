import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTree;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.TreeCellRenderer;
import javax.swing.tree.TreePath;

public class MainScreen extends JFrame implements ActionListener, TreeSelectionListener {
	String id ; // �α��� �� ���̵� �ޱ�
	
	JPanel northPane = new JPanel(new BorderLayout());
	
	// West �޴���
	JPanel westPane = new JPanel();
		DefaultMutableTreeNode tRec = new DefaultMutableTreeNode("���");
			DefaultMutableTreeNode recCre = new DefaultMutableTreeNode("�Է�");
			DefaultMutableTreeNode recSer = new DefaultMutableTreeNode("�˻�");
			
		DefaultMutableTreeNode tMeet = new DefaultMutableTreeNode("����");
			DefaultMutableTreeNode meetCre = new DefaultMutableTreeNode("����");
			DefaultMutableTreeNode meetSer = new DefaultMutableTreeNode("�˻�");
			DefaultMutableTreeNode meetCalendar = new DefaultMutableTreeNode("�޷�");
			DefaultMutableTreeNode meetRank = new DefaultMutableTreeNode("����");
			
		DefaultMutableTreeNode tMem = new DefaultMutableTreeNode("ȸ�� ����");
			DefaultMutableTreeNode memEdit = new DefaultMutableTreeNode("���� ����");
			DefaultMutableTreeNode memSet = new DefaultMutableTreeNode("ȯ�� ����");

		
		DefaultMutableTreeNode menu = new DefaultMutableTreeNode("�޴�");
		JTree menuTree = new JTree(menu);
	
	// Center ������ ǥ��â
	JTabbedPane display = new JTabbedPane();
		
		// Meeting Table
		String[] titleMt = {"�Ϸù�ȣ", "�Ͻ�", "���", "������", "�����ο�", "�ܿ��ο�", "����"};
		DefaultTableModel dtmM = new DefaultTableModel(titleMt, 0);
		JTable mtTabel = new JTable(dtmM);
		
		//
	JSplitPane jsp2 = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, westPane, display);
	JSplitPane jsp1 = new JSplitPane(JSplitPane.VERTICAL_SPLIT, northPane, jsp2);
	
	Font fnt = new Font("���� ���" , 1, 15);
	
	MSNorth inNorthPane; 
	RecordTab recordTab;
	NoticePane noticeTab;
	MeetingTab meetingTab;
	CalendarMeeting meetingCal ;
	MonthlyKing rankTab;
	
	Setting set ;
	
	public MainScreen() {}
	
	public MainScreen(String id) {
		setTitle("�Բ� �޸���");
		this.id = id;
		this.set = new Setting(id);
		
		recordTab = new RecordTab(id, set);
		meetingTab = new MeetingTab(id, set);
		meetingCal = new CalendarMeeting(id, set);
		rankTab = new MonthlyKing(id, set);
		
		try { 
		// ��� �г� ��ü ȣ��
		northPane.setPreferredSize(new Dimension(700,100));
		inNorthPane = new MSNorth(id, set);
		northPane.add(inNorthPane);
		
		setMenu();
		add(jsp1);
		
		// �α��� �� �˸��� ���
		noticeTab = new NoticePane(id);
		display.add("�˸�", noticeTab);
				
		setBounds(100,100,800,600);
		setVisible(true);
		setResizable(false);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		
		inNorthPane.logoutBtn.addActionListener(this);
		inNorthPane.exitBtn.addActionListener(this);
		inNorthPane.refreshBtn.addActionListener(this);
		menuTree.addTreeSelectionListener(this);
		
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void setMenu() {
		DefaultTreeCellRenderer cr = new DefaultTreeCellRenderer();
		
		menuTree.setCellRenderer(cr);
		
		tRec.add(recCre); tRec.add(recSer);
		tMeet.add(meetCre);  tMeet.add(meetSer);	tMeet.add(meetCalendar);	tMeet.add(meetRank);
		tMem.add(memEdit); tMem.add(memSet);
		
		menu.add(tRec); menu.add(tMeet); menu.add(tMem);
		westPane.add(menuTree);
		westPane.setPreferredSize(new Dimension(130,400));
		
		menuTree.setBackground(set.bgColor);
		menuTree.setForeground(set.fntColor);
		menuTree.setFont(fnt);
		
		set.setPaneStyle(westPane);
		
		menuTree.setCellRenderer(new MyCellRendere());
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		String ac = e.getActionCommand();
		
		if (ac.equals("�α׾ƿ�")) {
			int logoutQ = JOptionPane.showConfirmDialog(this, "�α׾ƿ� �Ͻðڽ��ϱ�", "", JOptionPane.YES_NO_OPTION);
			if (logoutQ == JOptionPane.YES_OPTION) {
				new Login();
				dispose();
			}
		} else if (ac.equals("���α׷� ����")) {
			int exitQ = JOptionPane.showConfirmDialog(this, "���α׷��� �����Ͻðڽ��ϱ�", "", JOptionPane.YES_NO_OPTION);
			if (exitQ == JOptionPane.YES_OPTION) {
				dispose();
				System.exit(0);
			}
		} else if (ac.equals("���� ��ħ")) {
			inNorthPane.setGroundLbl();			
		}
	}
	
	@Override
	public void valueChanged(TreeSelectionEvent e) {
		TreePath tp = e.getPath();
		Object obj = tp.getLastPathComponent();

		if (obj == recCre) {
			// �� ��� ����
			new RecordInfo(id, set);
		} else if (obj == recSer) {
			addTab(recordTab, "��� �˻�");
		} else if (obj == meetCre) {
			// ���� ����
			new MeetingInfo(id, set);
		} else if (obj == meetSer) {
			// ���� �˻� 			
			addTab(meetingTab, "���� �˻�");
		} else if (obj == meetCalendar) {
			// ���� �޷�	
			addTab(meetingCal, "���� �޷�");
		} else if (obj == meetRank) {
			// ����
			addTab(rankTab, "�̴��� ����");
		} else if (obj == memEdit) {
			// ȸ�� ���� ����
			new MemberInfo(id, set);
		} else if (obj == memSet) {
			//ȯ�� ����
			new SettingPane(id, set);
		}
	}
	
	public void addTab(Component obj , String title) {
		int indexTab = display.indexOfTab(title);
		if (indexTab < 0) {
			// �ǿ� ��� �˻� �г��� ���� ��� ����
			display.add(title, obj);
			
			// ��� �˻��� �ε����� �˻��Ͽ� Ȱ��ȭ
			indexTab = display.indexOfTab(title);
			display.setSelectedIndex(indexTab);
		} else { 
			// �̹� ������ ��� �˻� �г��� ���� ���
			display.setSelectedIndex(indexTab);
		}
	}
	
	class MyCellRendere extends DefaultTreeCellRenderer{
		@Override
		public Color getBackgroundNonSelectionColor() {return set.bgColor;}
		@Override
		public Color getBackgroundSelectionColor() {return null;}
		@Override
		public Color getBackground() {return set.bgColor;}
		@Override
		public Color getForeground() {return set.fntColor;}
	}

	public static void main(String args[]) {
		new MainScreen("ftowards");
	}
}
