package frame;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import com.mr.contact.dao.Dao;
import com.mr.contact.dao.DaoFactory;

import pojo.User;
/**
 * 登录窗口
 * 
 * @author anjiadoo
 *
 */
public class LoginFrame extends JFrame {
	public LoginFrame() {
		setTitle("登录");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);      // 窗体关闭后停止程序
		setSize(310,210);
		Toolkit tool = Toolkit.getDefaultToolkit();          // 创建系统默认组件工具包
		Dimension d = tool.getScreenSize();                  // 获取屏幕尺寸，赋给一个二维坐标对象
		setLocation((d.width-getWidth()) / 2, (d.height - getHeight()) / 2);// 让主窗体在屏幕中间
		
		JPanel contentPanel = new JPanel();                 // 创建主容器面板
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));// 设置边框
		setContentPane(contentPanel);                       // 将主容器面板载入主容器中
		contentPanel.setLayout(new BorderLayout());         // 使用边框布局
		
		JPanel centerPanel = new JPanel(new GridLayout(2, 1));// 创建2行1列的网格布局的中部面板
		contentPanel.add(centerPanel, BorderLayout.CENTER);   // 放置在主容器中心位置
		
		FlowLayout centerLayout = new FlowLayout();// 创建流布局
		centerLayout.setHgap(10);                  // 布局中组件间隔10像素
		
		// 创建存放第一行组件的面板，并使用流布局
		JPanel aFloorpJPanel = new JPanel(centerLayout);
		centerPanel.add(aFloorpJPanel);
		
		JLabel usernaemLabel = new JLabel("账号：");
		usernaemLabel.setHorizontalAlignment(SwingConstants.CENTER);// 标签对齐方式为居中
		aFloorpJPanel.add(usernaemLabel);
		
		final JTextField usernameField = new JTextField();
		usernameField.setColumns(20);// 用户名输入框长度为20个字符
		aFloorpJPanel.add(usernameField);
		
		// 创建存放第二行组件的面板吗，并使用流布局
		JPanel bFloorpJPanel = new JPanel(centerLayout);
		centerPanel.add(bFloorpJPanel);
		
		JLabel pwdLabel = new JLabel("密码：");
		pwdLabel.setHorizontalAlignment(SwingConstants.CENTER);
		bFloorpJPanel.add(pwdLabel);
		
		final JPasswordField pwdField = new JPasswordField();
		pwdField.setColumns(20);
		bFloorpJPanel.add(pwdField);
		
		// 创建南部面板
		JPanel southPanel = new JPanel(centerLayout);
		contentPanel.add(southPanel, BorderLayout.SOUTH);
		
		final JButton loginBtn = new JButton("登录");// 创建登录按钮
		southPanel.add(loginBtn);                   // 南部面板添加按钮
		
		final JButton closeBtn = new JButton("关闭");// 创建关闭按钮
		southPanel.add(closeBtn);                   // 南部面板添加按钮
		
		closeBtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				System.exit(0);// 程序结束
			}
		});
		
		loginBtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				Dao dao = DaoFactory.getDao();// 创建数据库接口对象
				String accout = usernameField.getText().trim(); // 获取账号输入框中的内容，去掉两边空格
				String pwd = new String(pwdField.getPassword());// 获取密码框中的内容
				User user = dao.selectUser(accout, pwd);        // 提交账号密码到数据库进行判断
				if (null == user) {
					JOptionPane.showMessageDialog(null, "您输入的账号密码不正确！");
				} else {
					MainFrame.setUser(user);          // 将此用户设为当前操作用户
					MainFrame frame = new MainFrame();// 创建主窗体对象
					dispose();                        // 销毁本窗体
				}
			}
		});
		pwdField.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				loginBtn.doClick();// 触发登录按钮事件
			}
		});
	}
}
