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
 * 展示联系人详细信息
 * @author anjiadoo
 *
 */
public class ShowInfoFrame extends CustomerFrame{
	private Dao dao;                     // 数据库接口
	private MainFrame frame;             // 父窗体
	private Customer cust;               // 要展示信息的联系人
	private FixedTable table;            // 通信信息表
	private JTextField nameText;         // 姓名输入框
	private JTextField workUnitText;     // 工作单位输入框
	private JTextField roleText;         // 职位输入框
	private JTextField workAddressText;  // 工作地址输入框
	private JTextField homeText;         // 家庭地址输入框
	private JTextField birthText;        // 生日输入框
	private JTextField sexText;          // 性别输入框
	private DefaultTableModel tableModel;// 信息表格数据模型
	/**
	 * 构造方法
	 * 
	 * @param cust - 要展示信息的联系人
	 * @param frame - 父窗体
	 */
	public  ShowInfoFrame(Customer cust, JFrame frame) {
		super(frame, CustomerFrame.SHOW);// 调用父类创建当前窗体的构造方法
		this.cust = cust;
		this.frame = (MainFrame)frame;
		setTitle("详细信息");
		dao = DaoFactory.getDao();       // 实例化数据库接口对象
		
		table = getTable();              // 获取窗体表格
		table.setCellEditable(false);    // 表格不可编辑
		tableModel = getTableModel();    // 获取窗体表格模型
		initTableModel();                // 表格数据初始化方法
		
		nameText = getNameText();        // 获取姓名输入框
		nameText.setText(cust.getName());// 展示联系人姓名
		
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
		
		FlowLayout btnPaneLayout = new FlowLayout(FlowLayout.RIGHT);// 右对齐流布局
		JPanel btnPanel = new JPanel(btnPaneLayout);                // 采用流布局的按钮面板
		getContentPane().add(btnPanel,BorderLayout.SOUTH);          // 将面板添加到容器
		
		JButton btnNewbButton = new JButton("关闭");
		btnPanel.add(btnNewbButton);
		btnNewbButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {// 点击时触发
				dispose();// 销毁窗体
			}
		});
	}
	
	/**
	 * 表格数据初始化
	 */
	private void initTableModel() {
		if (tableModel.getRowCount() > 0) {    // 如果表格中行数大于0
			tableModel.getDataVector().clear();// 清空表格数据
			tableModel.fireTableDataChanged(); // 重新绘制表格数据
		}
		List<Communication> usableList = dao.selectCustmerCommunicationUsable(cust);// 获取所有要展示的联系人的所有信息
		String[] tableValues = new String[5];          // 创建通信信息字符串数组，用于给表格数据模型赋值
		for (Communication com : usableList) {         // 遍历通信信息集合
			if (com.getAvailable().endsWith("Y")) {    // 如果是有效数据
				tableValues[0] = "" + com.getId();     // ID
				tableValues[1] = com.getOffice_phone();// 办公电话
				tableValues[2] = com.getMobile_phone();// 移动电话
				tableValues[3] = com.getEmail();       // Email
				tableValues[4] = com.getQq();          // QQ
				tableModel.addRow(tableValues);
			}
		}
	}
}
