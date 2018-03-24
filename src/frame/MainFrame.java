package frame;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.table.DefaultTableModel;

import com.mr.contact.dao.Dao;
import com.mr.contact.dao.DaoFactory;
import com.mr.contact.swing.ContactFrame;
import com.mr.contact.swing.FixedTable;

import pojo.Customer;
import pojo.User;

/**
 * ������
 * 
 * @author anjiadoo
 *
 */
public class MainFrame extends ContactFrame {
	private Dao dao;                     // ���ݿ�ӿ�
	private FixedTable table;            // ͨѶ¼���
	private DefaultTableModel tableModel;// ���������ģ��
	static private User user;            // ��ǰ��¼�û�
	private JButton updateBtn;           // �޸İ�ť
	private JButton addBtn;              // ��Ӱ��o
	private JButton delBtn;              // �h�����o
	
 	/**
	 * ���췽��
	 */
	public MainFrame() {
		setTitle("Contacts");
		init();
		validate(); // ���¼������ 
		addAction();
	}

	/**
	 * �����ʼ��
	 */
	protected void init() {
		super.init();                  // ���ø���init()����
		dao = DaoFactory.getDao();     // ʵ�������ݿ�ӿ�
		table = getTable();            // ����ָ�����ģ�͵ı��
		table.setCellEditable(false);  // ���ñ�񲻿ɱ༭
		initTable();                   // ��ʼ���������
		updateBtn = new JButton("update");
		addBtn = new JButton("add");
		delBtn = new JButton("delete");
		
		if (user.getStatus().equals(User.ADMIN)) {
			JPanel bottomPanel = new JPanel();           // �����ײ����
			FlowLayout bottomLayout = new FlowLayout();  // ʹ��������
			bottomLayout.setHgap(20);                    // ����20����
			bottomLayout.setAlignment(FlowLayout.RIGHT); // �Ҷ���
			bottomPanel.setLayout(bottomLayout);         // ����������
			bottomPanel.add(updateBtn);
			bottomPanel.add(addBtn);
			bottomPanel.add(delBtn);
			getContentPane().add(bottomPanel, BorderLayout.SOUTH);
		}
	}
	
	/**
	 * ��ʼ���������
	 */
	public void initTable() {
		tableModel = getUsableModelSoure();// ��ȡ������Ч��ϵ����Ϣ
		table.setModel(tableModel);        // ��ϵ����Ϣ����������ģ��
	}
	
	/**
	 * ��ѯ������Ч��ϵ����Ϣ
	 * 
	 * @return�������ģ��
	 */
	private DefaultTableModel getUsableModelSoure() {
		List<Customer> usableList = dao.selectUsableCustomer();// ��ȡ������Ч��ϵ��
		return collectionModel(usableList);                    // ����������Ч��ϵ�˱������ģ��
	}
	
	/**
	 * ���ݲ�ͬ����ϵ�˼��ϻ�ȡ��Ӧ�ı������ģ��
	 * 
	 * @param usableList -��ϵ�˼���
	 * @return�������ģ��
	 */
	private DefaultTableModel collectionModel(List<Customer> usableList) {
		int CustomerCount = usableList.size();// ��ȡ������Ч��ϵ��
		String[] columnNames = {
				"���", "����", "�Ա�", "��������", "������λ", "ְλ", "�����ص�", "��ͥסַ"};// �����ͷ����
		String[][] tableValues = new String[CustomerCount][8];// ���������������
		 
		for (int i = 0; i < CustomerCount; i++) {             // �������������
			Customer cust = usableList.get(i);                // ��ȡ���û�����
			tableValues[i][0] = "" + cust.getId();            // ���
			tableValues[i][1] = "" + cust.getName();          // ����
			tableValues[i][2] = "" + cust.getSex();           // �Ա�
			tableValues[i][3] = "" + cust.getBirth();         // ��������
			tableValues[i][4] = "" + cust.getWork_unit();     // ��˾����
			tableValues[i][5] = "" + cust.getRole();          // ְλ
			tableValues[i][6] = "" + cust.getWork_addr();     // ��˾��ַ
			tableValues[i][7] = "" + cust.getHome_addr();     // ��ͥ��ַ
		}
		DefaultTableModel tmp = new DefaultTableModel(tableValues, columnNames);// �����������ģ��:����(�������飬��������)
		return tmp;
	}
	
	/**
	 * ����������
	 */
	private void addAction(){
		table.addMouseListener(new MouseAdapter() { // ����������¼�����
			public void mouseClicked(MouseEvent e) {// ����ʱ
				if (e.getClickCount() == 2) {       // ���˫���¼�
					
					String id = (String)table.getValueAt(table.getSelectedRow(), 0);// ���ѡ���еĵ�һ�����ݣ���ֵΪid
					
					Customer cust = dao.selectCustomer(Integer.parseInt(id));       // ��ô�id�ĳ־û���ϵ����Ϣ����
					
					cust.setId(Integer.parseInt(id));         // ��idֵת��intֵ��������ϵ�˶���
					ShowInfoFrame info = new ShowInfoFrame(cust, MainFrame.this);   // ����ϸ��Ϣչʾ����
					info.setVisible(true);                    // ���ô���ɼ�
				}
			}
		});
		
		updateBtn.addActionListener(new ActionListener() { // �޸İ�ť��Ӷ����¼�����
			public void actionPerformed(ActionEvent e) {
				int rowindex = table.getSelectedRow();     // ��ȡ��ǰ���ѡ�е�������
				if (rowindex > -1) {                       // �����ѡ�е�ֵ
					String id = (String) table.getValueAt(rowindex, 0);// ��ȡ����һ�е�ֵ��Ϊid
					
					Customer update = dao.selectCustomer(Integer.parseInt(id));// ��ȡ���ݿ������id��ͬ����ϵ������
					
					UpdateCustomerFrame show = new UpdateCustomerFrame(update, MainFrame.this);// �����޸Ĵ���
					show.setVisible(true);
				}
			}
		});
		
		addBtn.addActionListener(new ActionListener() { // ��Ӱ�ť��Ӷ����¼�����
			public void actionPerformed(ActionEvent e) {
				AddCustomerFrame add = new AddCustomerFrame(MainFrame.this);
				add.setVisible(true);
			}
		});
		
		delBtn.addActionListener(new ActionListener() { // ɾ����ť��Ӷ����¼�����
			public void actionPerformed(ActionEvent e) {
				int rowIndex = table.getSelectedRow();
				if (rowIndex > -1) {
					// ������ʾ�Ի�����ʾ��ϵ����������ȡ�������صĽ��
					int i = JOptionPane.showConfirmDialog(MainFrame.this,
							"ȷ���Ƿ�ɾ��" + table.getValueAt(rowIndex, 1) + "?",
							"ע��!", JOptionPane.YES_NO_OPTION);
					// �����YES
					if (i == JOptionPane.YES_OPTION) {
						Customer del = new Customer();
						String id = (String)table.getValueAt(rowIndex, 0);
						del.setId(Integer.parseInt(id));
						dao.deleteCustomer(del, user);
						tableModel.removeRow(table.getSelectedRow());
					}
				}
			}
		});
	}
	
	/**
	 * ��ȡ��ǰ�����û�
	 * 
	 * @return
	 */
	static public User getUser() {
		return user;
	}
	
	/**
	 * ���õ�ǰ������
	 * 
	 * @param user - ��ǰ������
	 */
	static public void setUser(User user) {
		MainFrame.user = user;
	}
}
