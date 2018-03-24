package frame;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import com.mr.contact.dao.Dao;
import com.mr.contact.dao.DaoFactory;
import com.mr.contact.swing.CustomerFrame;

import pojo.Communication;
import pojo.Customer;

/**
 * �������ϵ����Ϣ
 * 
 * @author anjiadoo
 *
 */
public class AddCustomerFrame extends CustomerFrame{
	private MainFrame frame;              // ������
	private DefaultTableModel tableModel; // �������ģ��
	private JTable table;                 // ��Ϣ���
	private Dao dao;                      // ���ݿ�ӿ�
	private JTextField nameText;          // ���������
	private JTextField workUnitText;      // ������λ�����
	private JTextField roleText;          // ְλ�����
	private JTextField workAddressText;   // ������ַ�����
	private JTextField homeText;          // ��ͥ��ַ�����
	private JTextField birthText;         // ���������
	private JButton canceBtn;             
	private JButton saveBtn;
	private JComboBox<String>sexComboBox; // �Ա�������
	private JButton addRowBtn;
	private JButton delRowBtn;
	
	public AddCustomerFrame(JFrame frame) {
		super(frame, CustomerFrame.ADD);
		this.frame = (MainFrame) frame;
		setTitle("�����ϵ��");
		dao = DaoFactory.getDao();        // ��ʼ�����ݿ�ӿ�
		
		tableModel =getTableModel();
		table = getTable();
		nameText = getNameText();
		sexComboBox = getSexCombo();
		birthText = getBirthText();
		workUnitText = getWorkUnitText();
		workAddressText = getWorkAddressText();
		homeText = getHomeText();
		roleText = getRoleText();
		
		JPanel btnJPanel = new JPanel();
		btnJPanel.setLayout(new GridLayout(1, 2));
		getContentPane().add(btnJPanel, BorderLayout.SOUTH);
		
		FlowLayout layout = new FlowLayout();
		layout.setHgap(20);
		
		JPanel leftBtnJPanel = new JPanel();
		leftBtnJPanel.setLayout(layout);
		btnJPanel.add(leftBtnJPanel);
		
		delRowBtn = new JButton("ɾ����");
		leftBtnJPanel.add(delRowBtn);
		
		addRowBtn = new JButton("�����");
		leftBtnJPanel.add(addRowBtn);
		
		JPanel rightBtnJPanel = new JPanel();
		rightBtnJPanel.setLayout(layout);
		btnJPanel.add(rightBtnJPanel);
		
		saveBtn = new JButton("����");
		rightBtnJPanel.add(saveBtn);
		
		canceBtn = new JButton("�ر�");
		rightBtnJPanel.add(canceBtn);
		
		addAction();
	}
	/**
	 * ����������
	 */
	private void addAction() {
		canceBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		
		delRowBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int selectRow = table.getSelectedRow();
				if (selectRow != -1) {
					tableModel.removeRow(selectRow);// �ӱ��ģ�͵���ɾ��ָ����
				}
			}
		});
		
		addRowBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String[] add = {"", "", "", ""};
				tableModel.addRow(add);
			}
		});
		
		saveBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (table.isEditing()) {
					table.getCellEditor().stopCellEditing();// ���������ڱ༭���ñ��ֹͣ�༭
				}
				if (checkInfo()) {
					// ������ϵ�˶���
					Customer updateCus = new Customer(
							nameText.getText().trim(),
							(String)sexComboBox.getSelectedItem(),
							birthText.getText().trim(),
							workUnitText.getText().trim(),
							workAddressText.getText().trim(),
							homeText.getText().trim(),
							roleText.getText().trim());
					dao.addCustomer(updateCus, MainFrame.getUser());// �����ݿ���Ӵ���ϵ��
					
					int rowCount = table.getRowCount();
					for (int i = 0; i < rowCount; i++) {
						// ע��0������ű����أ������κβ���
						String offic_num = (String)table.getValueAt(i, 1);
						String mobile_num = (String)table.getValueAt(i, 2);
						String emain_num = (String)table.getValueAt(i, 3);
						String qq_num = (String)table.getValueAt(i, 4);
						
						// ����ͨ����Ϣ����
						Communication updateCom = new Communication(updateCus,offic_num, mobile_num, emain_num, qq_num);
						dao.addCommunication(updateCom, MainFrame.getUser());// �����ݿ����ͨ����Ϣ����
					}
					JOptionPane.showMessageDialog(AddCustomerFrame.this, "��ӳɹ�");
					dispose();
				}
			}
		});
	}
	/**
	 * У����Ϣ
	 * 
	 * @return
	 */
	private boolean checkInfo() {
		boolean result = true;// У����������Ĭ��Ϊtrue
		StringBuilder sb = new StringBuilder();// ��ʾ��Ϣ�ַ���
		
		String name = nameText.getText();
		if ("".equals(name) || null == name) {
			result = false;
			sb.append("���ֲ���Ϊ�գ�\n");
		}
		
		String sex = (String)sexComboBox.getSelectedItem();
		if ("".equals(sex) || null == sex) {
			result = false;
			sb.append("�Ա���Ϊ�գ�\n");
		}
		
		String regex = "[0-9]{4}-[0-9]{2}-[0-9]{2}"; // �����������ڸ�ʽ������ʽ
		String birth = birthText.getText();
		if (!(birth.matches(regex) || birth.equals(""))) {
			result = false;
			sb.append("���ڸ�ʽ����\n");
		}
		
		int rowCount = table.getRowCount();
		for (int i = 0; i < rowCount; i++) {
			// ע������0��ű����أ������κβ���
			String offic_num = (String)table.getValueAt(i, 1);
			String mobile_num = (String)table.getValueAt(i, 2);
			String email_num = (String)table.getValueAt(i, 3);
			String qq_num = (String)table.getValueAt(i, 4);
			
			if (!offic_num.matches("[0-9]+") && !offic_num.equals("")) {
				result = false;
				sb.append("�칫�绰���������֣�\n");
			}
			if (!mobile_num.matches("[0-9]+") && !mobile_num.equals("")) {
				result = false;
				sb.append("�ƶ��绰���������֣�\n");
			}
			if (!email_num.matches("\\w+@\\w+(\\.\\w{2,3})*\\.\\w{2,3}") && !email_num.equals("")) {
				result = false;
				sb.append("���������ʽ����\n");
			}
			if (!qq_num.matches("[0-9]{5,10}") && !qq_num.equals("")) {
				result = false;
				sb.append("QQ������ڴ������ݣ�\n");
			}
		}
		if (!result) {
			JOptionPane.showMessageDialog(this, sb.toString());
		}
		return result;
	}
	/**
	 * ���ٴ���
	 */
	public void dispose() {
		super.dispose();
		frame.initTable();
	}
}

