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
 * 主窗体
 * 
 * @author anjiadoo
 *
 */
public class MainFrame extends ContactFrame {
	private Dao dao;                     // 数据库接口
	private FixedTable table;            // 通讯录表格
	private DefaultTableModel tableModel;// 定义表格对象模型
	static private User user;            // 当前登录用户
	private JButton updateBtn;           // 修改按钮
	private JButton addBtn;              // 添加按鈕
	private JButton delBtn;              // 刪除按鈕
	
 	/**
	 * 构造方法
	 */
	public MainFrame() {
		setTitle("Contacts");
		init();
		validate(); // 重新加载组件 
		addAction();
	}

	/**
	 * 组件初始化
	 */
	protected void init() {
		super.init();                  // 调用父类init()方法
		dao = DaoFactory.getDao();     // 实例化数据库接口
		table = getTable();            // 创建指定表格模型的表格
		table.setCellEditable(false);  // 设置表格不可编辑
		initTable();                   // 初始化表格数据
		updateBtn = new JButton("update");
		addBtn = new JButton("add");
		delBtn = new JButton("delete");
		
		if (user.getStatus().equals(User.ADMIN)) {
			JPanel bottomPanel = new JPanel();           // 创建底部面板
			FlowLayout bottomLayout = new FlowLayout();  // 使用流布局
			bottomLayout.setHgap(20);                    // 横间距20像素
			bottomLayout.setAlignment(FlowLayout.RIGHT); // 右对齐
			bottomPanel.setLayout(bottomLayout);         // 载入流布局
			bottomPanel.add(updateBtn);
			bottomPanel.add(addBtn);
			bottomPanel.add(delBtn);
			getContentPane().add(bottomPanel, BorderLayout.SOUTH);
		}
	}
	
	/**
	 * 初始化表格数据
	 */
	public void initTable() {
		tableModel = getUsableModelSoure();// 获取所有有效联系人信息
		table.setModel(tableModel);        // 联系人信息表格加载数据模型
	}
	
	/**
	 * 查询所有有效联系人信息
	 * 
	 * @return表格数据模型
	 */
	private DefaultTableModel getUsableModelSoure() {
		List<Customer> usableList = dao.selectUsableCustomer();// 获取所有有效联系人
		return collectionModel(usableList);                    // 返回所有有效联系人表格数据模型
	}
	
	/**
	 * 根据不同的联系人集合获取相应的表格数据模型
	 * 
	 * @param usableList -联系人集合
	 * @return表格数据模型
	 */
	private DefaultTableModel collectionModel(List<Customer> usableList) {
		int CustomerCount = usableList.size();// 获取所有有效联系人
		String[] columnNames = {
				"编号", "姓名", "性别", "出生日期", "工作单位", "职位", "工作地点", "家庭住址"};// 定义表头数组
		String[][] tableValues = new String[CustomerCount][8];// 创建表格数据数组
		 
		for (int i = 0; i < CustomerCount; i++) {             // 遍历表格所有行
			Customer cust = usableList.get(i);                // 获取行用户对象
			tableValues[i][0] = "" + cust.getId();            // 编号
			tableValues[i][1] = "" + cust.getName();          // 姓名
			tableValues[i][2] = "" + cust.getSex();           // 性别
			tableValues[i][3] = "" + cust.getBirth();         // 出生日期
			tableValues[i][4] = "" + cust.getWork_unit();     // 公司名称
			tableValues[i][5] = "" + cust.getRole();          // 职位
			tableValues[i][6] = "" + cust.getWork_addr();     // 公司地址
			tableValues[i][7] = "" + cust.getHome_addr();     // 家庭地址
		}
		DefaultTableModel tmp = new DefaultTableModel(tableValues, columnNames);// 创建表格数据模型:参数(数据数组，列名数组)
		return tmp;
	}
	
	/**
	 * 添加组件监听
	 */
	private void addAction(){
		table.addMouseListener(new MouseAdapter() { // 表格添加鼠标事件监听
			public void mouseClicked(MouseEvent e) {// 单击时
				if (e.getClickCount() == 2) {       // 鼠标双击事件
					
					String id = (String)table.getValueAt(table.getSelectedRow(), 0);// 获得选中行的第一列数据，赋值为id
					
					Customer cust = dao.selectCustomer(Integer.parseInt(id));       // 获得此id的持久化联系人信息对象
					
					cust.setId(Integer.parseInt(id));         // 将id值转成int值并赋给联系人对象
					ShowInfoFrame info = new ShowInfoFrame(cust, MainFrame.this);   // 打开详细信息展示窗体
					info.setVisible(true);                    // 设置窗体可见
				}
			}
		});
		
		updateBtn.addActionListener(new ActionListener() { // 修改按钮添加动作事件监听
			public void actionPerformed(ActionEvent e) {
				int rowindex = table.getSelectedRow();     // 获取当前表格选中的行索引
				if (rowindex > -1) {                       // 如果有选中的值
					String id = (String) table.getValueAt(rowindex, 0);// 获取表格第一列的值作为id
					
					Customer update = dao.selectCustomer(Integer.parseInt(id));// 获取数据库中与此id相同的联系人数据
					
					UpdateCustomerFrame show = new UpdateCustomerFrame(update, MainFrame.this);// 創建修改窗体
					show.setVisible(true);
				}
			}
		});
		
		addBtn.addActionListener(new ActionListener() { // 添加按钮添加动作事件监听
			public void actionPerformed(ActionEvent e) {
				AddCustomerFrame add = new AddCustomerFrame(MainFrame.this);
				add.setVisible(true);
			}
		});
		
		delBtn.addActionListener(new ActionListener() { // 删除按钮添加动作事件监听
			public void actionPerformed(ActionEvent e) {
				int rowIndex = table.getSelectedRow();
				if (rowIndex > -1) {
					// 弹出提示对话框，提示联系人姓名，获取操作返回的结果
					int i = JOptionPane.showConfirmDialog(MainFrame.this,
							"确认是否删除" + table.getValueAt(rowIndex, 1) + "?",
							"注意!", JOptionPane.YES_NO_OPTION);
					// 如果是YES
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
	 * 获取当前操作用户
	 * 
	 * @return
	 */
	static public User getUser() {
		return user;
	}
	
	/**
	 * 设置当前操作者
	 * 
	 * @param user - 当前操作者
	 */
	static public void setUser(User user) {
		MainFrame.user = user;
	}
}
