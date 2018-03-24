package frame;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.table.DefaultTableModel;

import com.mr.contact.dao.Dao;
import com.mr.contact.dao.DaoFactory;
import com.mr.contact.swing.CustomerFrame;
import com.mr.contact.swing.FixedTable;

import pojo.Communication;
import pojo.Customer;
/**
 * �޸���ϵ����Ϣ
 * 
 * @author anjiadoo
 *
 */
public class UpdateCustomerFrame extends CustomerFrame{
	private final byte ALLSOUR = 0x5a;    // ��ȡȫ������
	private final byte USABLESOUR = 0x1a; // ��ȡ��Ч����
	private Dao dao;                      // ���ݿ�ӿ�
	private Customer cust;                // ���޸ĵ���ϵ��
	private MainFrame frame;              // ������
	private FixedTable table;             // ͨѶ��Ϣ���
	private DefaultTableModel tableModel; // ͨѶ��Ϣ����ģ��
	private JTextField nameText;          // ���������
	private JTextField workUnitText;      // ������λ�����
	private JTextField roleText;          // ְλ�����
	private JTextField workAddressText;   // ������ַ�����
	private JTextField homeText;          // ��ͥסַ�����
	private JTextField birthText;         // �������������
	private JButton cancelBtn;            // �رհ�ť
	private JButton saveBtn;              // ���水ť
	private JComboBox<String> sexComboBox;// �Ա�������
	private JButton delRowBtn;            // ɾ���а�ť
	private JButton addRowBtn;            // ����а�ť
	private JCheckBox chckbxNewCheckBox;  // ����ʧЧ��Ϣ��ѡ��
	
	/**
	 * ���췽��
	 * 
	 * @param cust - ���޸ĵ���ϵ��
	 * @param frame - ������
	 */
	public UpdateCustomerFrame(Customer cust, JFrame frame) {
		super(frame, CustomerFrame.UPDATE);
		this.cust = cust;
		this.frame = (MainFrame)frame;
		setTitle("�޸���ϵ����Ϣ");
		dao = DaoFactory.getDao();
		init();
		addAction();
	}
	/**
	 * �����ʼ��
	 */
	private void init() {
		cust = dao.selectCustomer(cust.getId());// ��cust�־û�
		setTableModelSource(USABLESOUR);        // ��ȡ�������
		table = getTable();                     // ����ָ�����ģ�͵ı��
		table.setModel(tableModel);             // ���ر������
		table.setCellEditable(true);            // �����Ա༭
		table.hiddenFirstColumn();              // ���ص�һ��

		nameText = getNameText();
		nameText.setText(cust.getName());

		sexComboBox = getSexCombo();            // ��ȡ�Ա�������
		if (null == cust.getSex()) {            // ����Ա�Ϊnull
			sexComboBox.setSelectedIndex(0);    // ѡ���һ��
		} else if (cust.getSex().equals("Ů")) {
			sexComboBox.setSelectedIndex(2);    // ѡ�������
		} else {
			sexComboBox.setSelectedIndex(1);    // ѡ��ڶ���
		}

		birthText = getBirthText();
		if (null != cust.getBirth()) {
			birthText.setText(cust.getBirth()); 
		} 

		workUnitText = getWorkUnitText(); 
		if (null != cust.getWork_unit()) {
			workUnitText.setText(cust.getWork_unit());
		} 

		roleText = getRoleText();
		if (null != cust.getRole()) {
			roleText.setText(cust.getRole());
		}

		workAddressText = getWorkAddressText();
		if (null != cust.getWork_addr()) {
			workAddressText.setText(cust.getWork_addr());
		}

		homeText = getHomeText();
		if (null != cust.getHome_addr()) {
			homeText.setText(cust.getHome_addr());
		}

		chckbxNewCheckBox = getChckbxNewCheckBox();// ��ȡ����ʧЧ��Ϣѡ���

		JPanel southPanel = new JPanel();// �����ϲ����
		southPanel.setLayout(new GridLayout(1, 2));// ���ʹ��1��2�е����񲼾�
		getContentPane().add(southPanel, BorderLayout.SOUTH);// �������ŵ����������ϲ�

		FlowLayout p4layout = new FlowLayout();
		p4layout.setHgap(20);// ˮƽ���20����

		JPanel leftButtonPanel = new JPanel(); // ������ఴť���
		leftButtonPanel.setLayout(p4layout);
		southPanel.add(leftButtonPanel);

		delRowBtn = new JButton("ɾ����");
		leftButtonPanel.add(delRowBtn); 

		addRowBtn = new JButton("�����");
		leftButtonPanel.add(addRowBtn);

		JPanel rightButtonPanel = new JPanel();// �����Ҳఴť���
		rightButtonPanel.setLayout(p4layout);
		southPanel.add(rightButtonPanel);

		saveBtn = new JButton("����");
		rightButtonPanel.add(saveBtn);

		cancelBtn = new JButton("ȡ��");
		rightButtonPanel.add(cancelBtn);
	}
	
	/**
	 * ��ȡ�û�ͨ����Ϣ
	 * 
	 * @param type - ��ȡ��������
	 */
	private void setTableModelSource(byte type) {
		if (tableModel == null) {// ����������ģ�Ͳ��ǿյ�
			
			String[] columnNames = { "���", "�칫�绰", "�ƶ��绰", "��������", "QQ", "�Ƿ���Ч" };// ��������������
			tableModel = new DefaultTableModel();        // ʵ�����������ģ��
			tableModel.setColumnIdentifiers(columnNames);// ����������
		}
		if (tableModel.getRowCount() > 0) {     // ����������������������0
			tableModel.getDataVector().clear(); // ��ձ�����
			tableModel.fireTableDataChanged();  // ���»��Ʊ������
		}

		List<Communication> usableList = null;  // ��������չʾ��ͨѶ��Ϣ����
		if (type == ALLSOUR) {// ���������������������
			usableList = dao.selectCustmerCommunicationAll(cust);   // ��ȡ����ϵ�˵�����������Ϣ
			
		} else if (type == USABLESOUR) {// ���������������Ч����
			usableList = dao.selectCustmerCommunicationUsable(cust);// ��ȡ����ϵ�˵���Ч������Ϣ
			
		} else {
			return;
		}
		int comCount = usableList.size();          // ��ȡͨѶ��Ϣ���ϳ���
		String[] tableValues = new String[6];      // ��������һ��ͨѶ��Ϣ���ݵ�����
		for (int i = 0; i < comCount; i++) {       // ����ͨѶ��Ϣ����
			Communication com = usableList.get(i); // ��ȡ�����е�ͨѶ��Ϣ����
			tableValues[0] = "" + com.getId();     // ��¼ID���ַ���ֵ
			tableValues[1] = com.getOffice_phone();// ��¼�칫�绰
			tableValues[2] = com.getMobile_phone();// ��¼�ƶ��绰
			tableValues[3] = com.getEmail();       // ��¼��������
			tableValues[4] = com.getQq();          // ��¼QQ
			tableValues[5] = com.getAvailable();   // ��¼�Ƿ���Ч
			tableModel.addRow(tableValues);        // �������ģ�����һ������
		}
	}

	/**
	 * �����������¼�����
	 */
	private void addAction() {
		
		cancelBtn.addActionListener(new ActionListener() {// ȡ����ť
			public void actionPerformed(ActionEvent e) {
				dispose();// ���ٴ���
			}
		});

		addRowBtn.addActionListener(new ActionListener() {        // ����а�ť
			public void actionPerformed(ActionEvent e) {
				Communication addCom = new Communication();       // ������ͨѶ��Ϣ
				addCom.setCust(cust);                             // ��Ϣ������ϵ��Ϊcust
				addCom.setAvailable("Y");                         // Ĭ�Ͽ���
				dao.addCommunication(addCom, MainFrame.getUser());// ���ݿ����һ���ռ�¼
				String id = "" + addCom.getId();                  // ��ȡ��¼ID
				String[] add = { id, "", "", "", "", "Y" };       // ����������
				tableModel.addRow(add);                           // �������ģ�����һ��
			}
		});

		delRowBtn.addActionListener(new ActionListener() {// ɾ���а�ť
			public void actionPerformed(ActionEvent e) {
				int selectedRow = table.getSelectedRow();// ��ñ�ѡ���е�����
				if (selectedRow != -1) { // �ж��Ƿ���ڱ�ѡ����
					
					String id = (String) table.getValueAt(selectedRow, 0);// ��ȡ���ѡ���еĵ�һ������
					Communication del = new Communication();              // ������ͨѶ��Ϣ
					del.setId(Integer.parseInt(id));                      // ��Ϣ�ı��Ϊid
					
					dao.deleteCommunication(del, MainFrame.getUser());    // �������ݿ�������������ϢʧЧ
					tableModel.removeRow(selectedRow);                    // �������ģ��ɾ��ѡ����
				}
			}
		});

		saveBtn.addActionListener(new ActionListener() {// ���水ť
			public void actionPerformed(ActionEvent e) {
				if (table.isEditing()) {
					table.getCellEditor().stopCellEditing();// �ñ��ֹͣ�༭״̬ 
				}
				if (checkInfo()) {// ���������ϢУ��ϸ�
					// ����������е���Ϣ����׼�����µ���ϵ�˶���
					Customer updateCust = new Customer(
							nameText.getText().trim(),              // ����
							(String) sexComboBox.getSelectedItem(), // �Ա�
							birthText.getText().trim(),             // ��������
							workUnitText.getText().trim(),          // ������λ
							workAddressText.getText().trim(),       // �����ص�
							homeText.getText().trim(),              // ��ͥסַ
							roleText.getText().trim());             // ְλ
					
					updateCust.setId(cust.getId());// ����ID
					updateCust.setAvailable("Y");  // ���ÿ���
					dao.updateCustomer(updateCust, MainFrame.getUser());// ������ϵ����Ϣ

					int rowcount = table.getRowCount();  // ��ȡ�������
					for (int i = 0; i < rowcount; i++) { // ����������
						String id = (String) table.getValueAt(i, 0);       // ͨѶ��ϢID
						String officNum = (String) table.getValueAt(i, 1); // �칫�绰
						String mobileNum = (String) table.getValueAt(i, 2);// �ƶ��绰
						String emailStr = (String) table.getValueAt(i, 3); // ��������
						String qqNum = (String) table.getValueAt(i, 4);    // QQ��
						String available = (String) table.getValueAt(i, 5);// �Ƿ���Ч
						// ���ݱ�������ݣ�����ͨѶ��Ϣ����
						Communication updateCom = new Communication(cust, officNum, mobileNum, emailStr, qqNum);
						updateCom.setId(Integer.parseInt(id));                  // ����ͨѶ��Ϣ���
						updateCom.setAvailable(available);                      // ����ͨѶ��Ϣ�Ƿ���Ч
						dao.updateCommunication(updateCom, MainFrame.getUser());// ����ͨѶ��Ϣ
					} 
						
					JOptionPane.showMessageDialog(UpdateCustomerFrame.this, "����ɹ���");
					dispose();
				}
			}
		});

		chckbxNewCheckBox.addChangeListener(new ChangeListener() {// �Ƿ�����ʧЧ��Ϣ��ѡ��
			public void stateChanged(ChangeEvent e) {        // ��ѡ��ʱ
				if (chckbxNewCheckBox.isSelected()) {        // �����ѡ��ѡ��
					setTableModelSource(USABLESOUR);         // ��ȡ��Ч����
				} else {
					setTableModelSource(ALLSOUR);            // ��ȡ��������
				}
				table.setModel(tableModel);                  // �����������ģ��
			}
		});
	}

	/**
	 * ��������
	 */
	public void dispose() {
		super.dispose();   // ���ø��ര�����ٷ���
		frame.initTable(); // ��������±������
	}
	
	/**
	 * ����û���Ϣ�Ƿ���ϸ�ʽ
	 *
	 * @return �Ƿ�ϸ�
	 */
	private boolean checkInfo() {
		boolean result = true;                 // ����������������Ĭ��ͨ��
		StringBuilder sb = new StringBuilder();// ������־�ַ���

		String name = nameText.getText();
		if ("".equals(name) || null == name) {
			result = false;
			sb.append("��������Ϊ�գ�\n");        // ��¼������־
		}
		
		String sex = (String) sexComboBox.getSelectedItem();
		if ("".equals(sex) || null == sex) {
			result = false;
			sb.append("�Ա���Ϊ�գ�\n");
		}
		
		String regex = "[0-9]{4}-[0-9]{2}-[0-9]{2}";      // �����������ڸ�ʽ������ʽ
		String birth = birthText.getText();
		if (!(birth.matches(regex) || birth.equals(""))) {
			result = false;
			sb.append("���ڸ�ʽ����\n");
		}

		int rowcount = tableModel.getRowCount();// ��ȡ���������
		for (int i = 0; i < rowcount; i++) {
			
			String offic_num = (String) tableModel.getValueAt(i, 1); // ��ȡ�ڶ��еİ칫�绰
			String mobile_num = (String) tableModel.getValueAt(i, 2);// ��ȡ�����е��ƶ��绰
			String email_str = (String) tableModel.getValueAt(i, 3); // ��ȡ�����еĵ����ʼ�
			String qq_num = (String) tableModel.getValueAt(i, 4);    // ��ȡ�����е�QQ����
			String available = (String) tableModel.getValueAt(i, 5); // ��ȡ�����е���Ч��־
			
			if (!(offic_num.matches("[0-9]+") || offic_num.equals(""))) { // ����칫�绰��Ϊ�գ��Ҳ�����Ч����
				result = false;
				sb.append("�칫�绰���ڴ������ݣ�\n");
			}
			if (!(mobile_num.matches("[0-9]+") || mobile_num.equals(""))) {// ����ƶ��绰��Ϊ�գ��Ҳ�����Ч����
				result = false;
				sb.append("�ƶ��绰���ڴ������ݣ�\n");
			}
			if (!(email_str.matches("\\w+@\\w+(\\.\\w{2,3})*\\.\\w{2,3}") || email_str.equals(""))) {// ������������ַ��Ϊ�գ��Ҳ�����Ч�����ʽ
				result = false;
				sb.append("���������ʽ����\n");
			}
			if (!(qq_num.matches("[0-9]{5,10}") || qq_num.equals(""))) {   // ���QQ���벻Ϊ�գ��Ҳ�����ЧQQ����
				result = false;
				sb.append("QQ������ڴ������ݣ�\n");
			}
			if (!("Y".equals(available) || "N".equals(available))) {       // �����Ч��־����YҲ����N
				result = false;
				sb.append("��Ϣ��Ч��ֵֻ��ѡ��Y��N��\n");
			}
		}
		if (!result) {// У����Ϊ��ͨ��
			
			JOptionPane.showMessageDialog(null, sb.toString());// �����Ի���չʾ��־��Ϣ
		}
		return result;
	}
}

