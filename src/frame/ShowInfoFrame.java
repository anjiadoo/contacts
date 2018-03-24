package frame;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import org.hibernate.property.Setter;

import com.mr.contact.dao.Dao;
import com.mr.contact.dao.DaoFactory;
import com.mr.contact.swing.CustomerFrame;
import com.mr.contact.swing.FixedTable;

import pojo.Communication;
import pojo.Customer;

/**
 * չʾ��ϵ����ϸ��Ϣ
 * @author anjiadoo
 *
 */
public class ShowInfoFrame extends CustomerFrame{
	private Dao dao;                     // ���ݿ�ӿ�
	private MainFrame frame;             // ������
	private Customer cust;               // Ҫչʾ��Ϣ����ϵ��
	private FixedTable table;            // ͨ����Ϣ��
	private JTextField nameText;         // ���������
	private JTextField workUnitText;     // ������λ�����
	private JTextField roleText;         // ְλ�����
	private JTextField workAddressText;  // ������ַ�����
	private JTextField homeText;         // ��ͥ��ַ�����
	private JTextField birthText;        // ���������
	private JTextField sexText;          // �Ա������
	private DefaultTableModel tableModel;// ��Ϣ�������ģ��
	/**
	 * ���췽��
	 * 
	 * @param cust - Ҫչʾ��Ϣ����ϵ��
	 * @param frame - ������
	 */
	public  ShowInfoFrame(Customer cust, JFrame frame) {
		super(frame, CustomerFrame.SHOW);// ���ø��ഴ����ǰ����Ĺ��췽��
		this.cust = cust;
		this.frame = (MainFrame)frame;
		setTitle("��ϸ��Ϣ");
		dao = DaoFactory.getDao();       // ʵ�������ݿ�ӿڶ���
		
		table = getTable();              // ��ȡ������
		table.setCellEditable(false);    // ��񲻿ɱ༭
		tableModel = getTableModel();    // ��ȡ������ģ��
		initTableModel();                // ������ݳ�ʼ������
		
		nameText = getNameText();        // ��ȡ���������
		nameText.setText(cust.getName());// չʾ��ϵ������
		
		sexText = getSexText();
		sexText.setText(cust.getSex());
		
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
		
		FlowLayout btnPaneLayout = new FlowLayout(FlowLayout.RIGHT);// �Ҷ���������
		JPanel btnPanel = new JPanel(btnPaneLayout);                // ���������ֵİ�ť���
		getContentPane().add(btnPanel,BorderLayout.SOUTH);          // �������ӵ�����
		
		JButton btnNewbButton = new JButton("�ر�");
		btnPanel.add(btnNewbButton);
		btnNewbButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {// ���ʱ����
				dispose();// ���ٴ���
			}
		});
	}
	
	/**
	 * ������ݳ�ʼ��
	 */
	private void initTableModel() {
		if (tableModel.getRowCount() > 0) {    // ����������������0
			tableModel.getDataVector().clear();// ��ձ������
			tableModel.fireTableDataChanged(); // ���»��Ʊ������
		}
		List<Communication> usableList = dao.selectCustmerCommunicationUsable(cust);// ��ȡ����Ҫչʾ����ϵ�˵�������Ϣ
		String[] tableValues = new String[5];          // ����ͨ����Ϣ�ַ������飬���ڸ��������ģ�͸�ֵ
		for (Communication com : usableList) {         // ����ͨ����Ϣ����
			if (com.getAvailable().endsWith("Y")) {    // �������Ч����
				tableValues[0] = "" + com.getId();     // ID
				tableValues[1] = com.getOffice_phone();// �칫�绰
				tableValues[2] = com.getMobile_phone();// �ƶ��绰
				tableValues[3] = com.getEmail();       // Email
				tableValues[4] = com.getQq();          // QQ
				tableModel.addRow(tableValues);
			}
		}
	}
}
