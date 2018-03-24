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
 * 修改联系人信息
 * 
 * @author anjiadoo
 *
 */
public class UpdateCustomerFrame extends CustomerFrame{
	private final byte ALLSOUR = 0x5a;    // 获取全部数据
	private final byte USABLESOUR = 0x1a; // 获取有效数据
	private Dao dao;                      // 数据库接口
	private Customer cust;                // 被修改的联系人
	private MainFrame frame;              // 父窗体
	private FixedTable table;             // 通讯信息表格
	private DefaultTableModel tableModel; // 通讯信息数据模型
	private JTextField nameText;          // 姓名输入框
	private JTextField workUnitText;      // 工作单位输入框
	private JTextField roleText;          // 职位输入框
	private JTextField workAddressText;   // 工作地址输入框
	private JTextField homeText;          // 家庭住址输入框
	private JTextField birthText;         // 出生日期输入框
	private JButton cancelBtn;            // 关闭按钮
	private JButton saveBtn;              // 保存按钮
	private JComboBox<String> sexComboBox;// 性别下拉框
	private JButton delRowBtn;            // 删除行按钮
	private JButton addRowBtn;            // 添加行按钮
	private JCheckBox chckbxNewCheckBox;  // 隐藏失效信息单选框
	
	/**
	 * 构造方法
	 * 
	 * @param cust - 被修改的联系人
	 * @param frame - 父窗体
	 */
	public UpdateCustomerFrame(Customer cust, JFrame frame) {
		super(frame, CustomerFrame.UPDATE);
		this.cust = cust;
		this.frame = (MainFrame)frame;
		setTitle("修改联系人信息");
		dao = DaoFactory.getDao();
		init();
		addAction();
	}
	/**
	 * 组件初始化
	 */
	private void init() {
		cust = dao.selectCustomer(cust.getId());// 将cust持久化
		setTableModelSource(USABLESOUR);        // 获取表格数据
		table = getTable();                     // 创建指定表格模型的表格
		table.setModel(tableModel);             // 加载表格数据
		table.setCellEditable(true);            // 表格可以编辑
		table.hiddenFirstColumn();              // 隐藏第一列

		nameText = getNameText();
		nameText.setText(cust.getName());

		sexComboBox = getSexCombo();            // 获取性别下拉框
		if (null == cust.getSex()) {            // 如果性别为null
			sexComboBox.setSelectedIndex(0);    // 选择第一项
		} else if (cust.getSex().equals("女")) {
			sexComboBox.setSelectedIndex(2);    // 选择第三项
		} else {
			sexComboBox.setSelectedIndex(1);    // 选择第二项
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

		chckbxNewCheckBox = getChckbxNewCheckBox();// 获取隐藏失效信息选择框

		JPanel southPanel = new JPanel();// 创建南部面板
		southPanel.setLayout(new GridLayout(1, 2));// 面板使用1行2列的网格布局
		getContentPane().add(southPanel, BorderLayout.SOUTH);// 将次面板放到主容器在南部

		FlowLayout p4layout = new FlowLayout();
		p4layout.setHgap(20);// 水平间隔20像素

		JPanel leftButtonPanel = new JPanel(); // 创建左侧按钮面板
		leftButtonPanel.setLayout(p4layout);
		southPanel.add(leftButtonPanel);

		delRowBtn = new JButton("删除行");
		leftButtonPanel.add(delRowBtn); 

		addRowBtn = new JButton("添加行");
		leftButtonPanel.add(addRowBtn);

		JPanel rightButtonPanel = new JPanel();// 创建右侧按钮面板
		rightButtonPanel.setLayout(p4layout);
		southPanel.add(rightButtonPanel);

		saveBtn = new JButton("保存");
		rightButtonPanel.add(saveBtn);

		cancelBtn = new JButton("取消");
		rightButtonPanel.add(cancelBtn);
	}
	
	/**
	 * 获取用户通信信息
	 * 
	 * @param type - 获取数据类型
	 */
	private void setTableModelSource(byte type) {
		if (tableModel == null) {// 如果表格数据模型不是空的
			
			String[] columnNames = { "编号", "办公电话", "移动电话", "电子邮箱", "QQ", "是否有效" };// 定义表格列名数组
			tableModel = new DefaultTableModel();        // 实例化表格数据模型
			tableModel.setColumnIdentifiers(columnNames);// 载入表格列名
		}
		if (tableModel.getRowCount() > 0) {     // 如果表格中有数据行数大于0
			tableModel.getDataVector().clear(); // 清空表数据
			tableModel.fireTableDataChanged();  // 重新绘制表格数据
		}

		List<Communication> usableList = null;  // 创建用于展示的通讯信息集合
		if (type == ALLSOUR) {// 如果数据类型是所有数据
			usableList = dao.selectCustmerCommunicationAll(cust);   // 获取此联系人的所有数据信息
			
		} else if (type == USABLESOUR) {// 如果数据类型是有效数据
			usableList = dao.selectCustmerCommunicationUsable(cust);// 获取此联系人的有效数据信息
			
		} else {
			return;
		}
		int comCount = usableList.size();          // 获取通讯信息集合长度
		String[] tableValues = new String[6];      // 创建保存一行通讯信息数据的数组
		for (int i = 0; i < comCount; i++) {       // 遍历通讯信息集合
			Communication com = usableList.get(i); // 获取集合中的通讯信息对象
			tableValues[0] = "" + com.getId();     // 记录ID的字符串值
			tableValues[1] = com.getOffice_phone();// 记录办公电话
			tableValues[2] = com.getMobile_phone();// 记录移动电话
			tableValues[3] = com.getEmail();       // 记录电子邮箱
			tableValues[4] = com.getQq();          // 记录QQ
			tableValues[5] = com.getAvailable();   // 记录是否有效
			tableModel.addRow(tableValues);        // 表格数据模型添加一行数据
		}
	}

	/**
	 * 添加组件动作事件监听
	 */
	private void addAction() {
		
		cancelBtn.addActionListener(new ActionListener() {// 取消按钮
			public void actionPerformed(ActionEvent e) {
				dispose();// 销毁窗体
			}
		});

		addRowBtn.addActionListener(new ActionListener() {        // 添加行按钮
			public void actionPerformed(ActionEvent e) {
				Communication addCom = new Communication();       // 创建空通讯信息
				addCom.setCust(cust);                             // 信息所属联系人为cust
				addCom.setAvailable("Y");                         // 默认可用
				dao.addCommunication(addCom, MainFrame.getUser());// 数据库添加一个空记录
				String id = "" + addCom.getId();                  // 获取记录ID
				String[] add = { id, "", "", "", "", "Y" };       // 创建空数据
				tableModel.addRow(add);                           // 表格数据模型添加一行
			}
		});

		delRowBtn.addActionListener(new ActionListener() {// 删除行按钮
			public void actionPerformed(ActionEvent e) {
				int selectedRow = table.getSelectedRow();// 获得被选中行的索引
				if (selectedRow != -1) { // 判断是否存在被选中行
					
					String id = (String) table.getValueAt(selectedRow, 0);// 获取表格选中行的第一列数据
					Communication del = new Communication();              // 创建空通讯信息
					del.setId(Integer.parseInt(id));                      // 信息的编号为id
					
					dao.deleteCommunication(del, MainFrame.getUser());    // 调用数据库结果，将该行信息失效
					tableModel.removeRow(selectedRow);                    // 表格数据模型删除选中行
				}
			}
		});

		saveBtn.addActionListener(new ActionListener() {// 保存按钮
			public void actionPerformed(ActionEvent e) {
				if (table.isEditing()) {
					table.getCellEditor().stopCellEditing();// 让表格停止编辑状态 
				}
				if (checkInfo()) {// 如果所有信息校验合格
					// 根据输入框中的信息创建准备更新的联系人对象
					Customer updateCust = new Customer(
							nameText.getText().trim(),              // 姓名
							(String) sexComboBox.getSelectedItem(), // 性别
							birthText.getText().trim(),             // 出生日期
							workUnitText.getText().trim(),          // 工作单位
							workAddressText.getText().trim(),       // 工作地点
							homeText.getText().trim(),              // 家庭住址
							roleText.getText().trim());             // 职位
					
					updateCust.setId(cust.getId());// 设置ID
					updateCust.setAvailable("Y");  // 设置可用
					dao.updateCustomer(updateCust, MainFrame.getUser());// 更新联系人信息

					int rowcount = table.getRowCount();  // 获取表格行数
					for (int i = 0; i < rowcount; i++) { // 遍历所有行
						String id = (String) table.getValueAt(i, 0);       // 通讯信息ID
						String officNum = (String) table.getValueAt(i, 1); // 办公电话
						String mobileNum = (String) table.getValueAt(i, 2);// 移动电话
						String emailStr = (String) table.getValueAt(i, 3); // 电子邮箱
						String qqNum = (String) table.getValueAt(i, 4);    // QQ号
						String available = (String) table.getValueAt(i, 5);// 是否有效
						// 根据表格中数据，创建通讯信息对象
						Communication updateCom = new Communication(cust, officNum, mobileNum, emailStr, qqNum);
						updateCom.setId(Integer.parseInt(id));                  // 设置通讯信息编号
						updateCom.setAvailable(available);                      // 设置通讯信息是否有效
						dao.updateCommunication(updateCom, MainFrame.getUser());// 更新通讯信息
					} 
						
					JOptionPane.showMessageDialog(UpdateCustomerFrame.this, "保存成功！");
					dispose();
				}
			}
		});

		chckbxNewCheckBox.addChangeListener(new ChangeListener() {// 是否隐藏失效信息单选框
			public void stateChanged(ChangeEvent e) {        // 当选择时
				if (chckbxNewCheckBox.isSelected()) {        // 如果单选框被选中
					setTableModelSource(USABLESOUR);         // 获取有效数据
				} else {
					setTableModelSource(ALLSOUR);            // 获取所有数据
				}
				table.setModel(tableModel);                  // 表格载入数据模型
			}
		});
	}

	/**
	 * 窗体销毁
	 */
	public void dispose() {
		super.dispose();   // 调用父类窗体销毁方法
		frame.initTable(); // 父窗体更新表格数据
	}
	
	/**
	 * 检查用户信息是否符合格式
	 *
	 * @return 是否合格
	 */
	private boolean checkInfo() {
		boolean result = true;                 // 创建检验结果变量，默认通过
		StringBuilder sb = new StringBuilder();// 错误日志字符串

		String name = nameText.getText();
		if ("".equals(name) || null == name) {
			result = false;
			sb.append("姓名不能为空！\n");        // 记录错误日志
		}
		
		String sex = (String) sexComboBox.getSelectedItem();
		if ("".equals(sex) || null == sex) {
			result = false;
			sb.append("性别不能为空！\n");
		}
		
		String regex = "[0-9]{4}-[0-9]{2}-[0-9]{2}";      // 创建出生日期格式正则表达式
		String birth = birthText.getText();
		if (!(birth.matches(regex) || birth.equals(""))) {
			result = false;
			sb.append("日期格式错误！\n");
		}

		int rowcount = tableModel.getRowCount();// 获取表格行数量
		for (int i = 0; i < rowcount; i++) {
			
			String offic_num = (String) tableModel.getValueAt(i, 1); // 获取第二列的办公电话
			String mobile_num = (String) tableModel.getValueAt(i, 2);// 获取第三列的移动电话
			String email_str = (String) tableModel.getValueAt(i, 3); // 获取第四列的电子邮件
			String qq_num = (String) tableModel.getValueAt(i, 4);    // 获取第五列的QQ号码
			String available = (String) tableModel.getValueAt(i, 5); // 获取第六列的有效标志
			
			if (!(offic_num.matches("[0-9]+") || offic_num.equals(""))) { // 如果办公电话不为空，且不是有效数字
				result = false;
				sb.append("办公电话存在错误内容！\n");
			}
			if (!(mobile_num.matches("[0-9]+") || mobile_num.equals(""))) {// 如果移动电话不为空，且不是有效数字
				result = false;
				sb.append("移动电话存在错误内容！\n");
			}
			if (!(email_str.matches("\\w+@\\w+(\\.\\w{2,3})*\\.\\w{2,3}") || email_str.equals(""))) {// 如果电子邮箱地址不为空，且不是有效邮箱格式
				result = false;
				sb.append("电子邮箱格式错误！\n");
			}
			if (!(qq_num.matches("[0-9]{5,10}") || qq_num.equals(""))) {   // 如果QQ号码不为空，且不是有效QQ号码
				result = false;
				sb.append("QQ号码存在错误内容！\n");
			}
			if (!("Y".equals(available) || "N".equals(available))) {       // 如果有效标志不是Y也不是N
				result = false;
				sb.append("信息有效性值只能选择Y或N！\n");
			}
		}
		if (!result) {// 校验结果为不通过
			
			JOptionPane.showMessageDialog(null, sb.toString());// 弹出对话框，展示日志信息
		}
		return result;
	}
}

