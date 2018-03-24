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
 * 添加新联系人信息
 * 
 * @author anjiadoo
 *
 */
public class AddCustomerFrame extends CustomerFrame{
	private MainFrame frame;              // 父窗体
	private DefaultTableModel tableModel; // 表格数据模型
	private JTable table;                 // 信息表格
	private Dao dao;                      // 数据库接口
	private JTextField nameText;          // 姓名输入框
	private JTextField workUnitText;      // 工作单位输入框
	private JTextField roleText;          // 职位输入框
	private JTextField workAddressText;   // 工作地址输入框
	private JTextField homeText;          // 家庭地址输入框
	private JTextField birthText;         // 生日输入框
	private JButton canceBtn;             
	private JButton saveBtn;
	private JComboBox<String>sexComboBox; // 性别下拉框
	private JButton addRowBtn;
	private JButton delRowBtn;
	
	public AddCustomerFrame(JFrame frame) {
		super(frame, CustomerFrame.ADD);
		this.frame = (MainFrame) frame;
		setTitle("添加联系人");
		dao = DaoFactory.getDao();        // 初始化数据库接口
		
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
		
		delRowBtn = new JButton("删除行");
		leftBtnJPanel.add(delRowBtn);
		
		addRowBtn = new JButton("添加行");
		leftBtnJPanel.add(addRowBtn);
		
		JPanel rightBtnJPanel = new JPanel();
		rightBtnJPanel.setLayout(layout);
		btnJPanel.add(rightBtnJPanel);
		
		saveBtn = new JButton("保存");
		rightBtnJPanel.add(saveBtn);
		
		canceBtn = new JButton("关闭");
		rightBtnJPanel.add(canceBtn);
		
		addAction();
	}
	/**
	 * 添加组件监听
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
					tableModel.removeRow(selectRow);// 从表格模型当中删除指定行
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
					table.getCellEditor().stopCellEditing();// 如果表格正在编辑就让表格停止编辑
				}
				if (checkInfo()) {
					// 创建联系人对象
					Customer updateCus = new Customer(
							nameText.getText().trim(),
							(String)sexComboBox.getSelectedItem(),
							birthText.getText().trim(),
							workUnitText.getText().trim(),
							workAddressText.getText().trim(),
							homeText.getText().trim(),
							roleText.getText().trim());
					dao.addCustomer(updateCus, MainFrame.getUser());// 向数据库添加此联系人
					
					int rowCount = table.getRowCount();
					for (int i = 0; i < rowCount; i++) {
						// 注：0索引编号被隐藏，不做任何操作
						String offic_num = (String)table.getValueAt(i, 1);
						String mobile_num = (String)table.getValueAt(i, 2);
						String emain_num = (String)table.getValueAt(i, 3);
						String qq_num = (String)table.getValueAt(i, 4);
						
						// 创建通信信息对象
						Communication updateCom = new Communication(updateCus,offic_num, mobile_num, emain_num, qq_num);
						dao.addCommunication(updateCom, MainFrame.getUser());// 向数据库添加通信信息数据
					}
					JOptionPane.showMessageDialog(AddCustomerFrame.this, "添加成功");
					dispose();
				}
			}
		});
	}
	/**
	 * 校验信息
	 * 
	 * @return
	 */
	private boolean checkInfo() {
		boolean result = true;// 校验结果变量，默认为true
		StringBuilder sb = new StringBuilder();// 提示信息字符串
		
		String name = nameText.getText();
		if ("".equals(name) || null == name) {
			result = false;
			sb.append("名字不能为空！\n");
		}
		
		String sex = (String)sexComboBox.getSelectedItem();
		if ("".equals(sex) || null == sex) {
			result = false;
			sb.append("性别不能为空！\n");
		}
		
		String regex = "[0-9]{4}-[0-9]{2}-[0-9]{2}"; // 创建出生日期格式正则表达式
		String birth = birthText.getText();
		if (!(birth.matches(regex) || birth.equals(""))) {
			result = false;
			sb.append("日期格式错误！\n");
		}
		
		int rowCount = table.getRowCount();
		for (int i = 0; i < rowCount; i++) {
			// 注：索引0编号被隐藏，不做任何操作
			String offic_num = (String)table.getValueAt(i, 1);
			String mobile_num = (String)table.getValueAt(i, 2);
			String email_num = (String)table.getValueAt(i, 3);
			String qq_num = (String)table.getValueAt(i, 4);
			
			if (!offic_num.matches("[0-9]+") && !offic_num.equals("")) {
				result = false;
				sb.append("办公电话必须是数字！\n");
			}
			if (!mobile_num.matches("[0-9]+") && !mobile_num.equals("")) {
				result = false;
				sb.append("移动电话必须是数字！\n");
			}
			if (!email_num.matches("\\w+@\\w+(\\.\\w{2,3})*\\.\\w{2,3}") && !email_num.equals("")) {
				result = false;
				sb.append("电子邮箱格式错误！\n");
			}
			if (!qq_num.matches("[0-9]{5,10}") && !qq_num.equals("")) {
				result = false;
				sb.append("QQ号码存在错误内容！\n");
			}
		}
		if (!result) {
			JOptionPane.showMessageDialog(this, sb.toString());
		}
		return result;
	}
	/**
	 * 销毁窗体
	 */
	public void dispose() {
		super.dispose();
		frame.initTable();
	}
}

