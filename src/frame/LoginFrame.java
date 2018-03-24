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
 * ��¼����
 * 
 * @author anjiadoo
 *
 */
public class LoginFrame extends JFrame {
	public LoginFrame() {
		setTitle("��¼");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);      // ����رպ�ֹͣ����
		setSize(310,210);
		Toolkit tool = Toolkit.getDefaultToolkit();          // ����ϵͳĬ��������߰�
		Dimension d = tool.getScreenSize();                  // ��ȡ��Ļ�ߴ磬����һ����ά�������
		setLocation((d.width-getWidth()) / 2, (d.height - getHeight()) / 2);// ������������Ļ�м�
		
		JPanel contentPanel = new JPanel();                 // �������������
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));// ���ñ߿�
		setContentPane(contentPanel);                       // �����������������������
		contentPanel.setLayout(new BorderLayout());         // ʹ�ñ߿򲼾�
		
		JPanel centerPanel = new JPanel(new GridLayout(2, 1));// ����2��1�е����񲼾ֵ��в����
		contentPanel.add(centerPanel, BorderLayout.CENTER);   // ����������������λ��
		
		FlowLayout centerLayout = new FlowLayout();// ����������
		centerLayout.setHgap(10);                  // ������������10����
		
		// ������ŵ�һ���������壬��ʹ��������
		JPanel aFloorpJPanel = new JPanel(centerLayout);
		centerPanel.add(aFloorpJPanel);
		
		JLabel usernaemLabel = new JLabel("�˺ţ�");
		usernaemLabel.setHorizontalAlignment(SwingConstants.CENTER);// ��ǩ���뷽ʽΪ����
		aFloorpJPanel.add(usernaemLabel);
		
		final JTextField usernameField = new JTextField();
		usernameField.setColumns(20);// �û�������򳤶�Ϊ20���ַ�
		aFloorpJPanel.add(usernameField);
		
		// ������ŵڶ������������𣬲�ʹ��������
		JPanel bFloorpJPanel = new JPanel(centerLayout);
		centerPanel.add(bFloorpJPanel);
		
		JLabel pwdLabel = new JLabel("���룺");
		pwdLabel.setHorizontalAlignment(SwingConstants.CENTER);
		bFloorpJPanel.add(pwdLabel);
		
		final JPasswordField pwdField = new JPasswordField();
		pwdField.setColumns(20);
		bFloorpJPanel.add(pwdField);
		
		// �����ϲ����
		JPanel southPanel = new JPanel(centerLayout);
		contentPanel.add(southPanel, BorderLayout.SOUTH);
		
		final JButton loginBtn = new JButton("��¼");// ������¼��ť
		southPanel.add(loginBtn);                   // �ϲ������Ӱ�ť
		
		final JButton closeBtn = new JButton("�ر�");// �����رհ�ť
		southPanel.add(closeBtn);                   // �ϲ������Ӱ�ť
		
		closeBtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				System.exit(0);// �������
			}
		});
		
		loginBtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				Dao dao = DaoFactory.getDao();// �������ݿ�ӿڶ���
				String accout = usernameField.getText().trim(); // ��ȡ�˺�������е����ݣ�ȥ�����߿ո�
				String pwd = new String(pwdField.getPassword());// ��ȡ������е�����
				User user = dao.selectUser(accout, pwd);        // �ύ�˺����뵽���ݿ�����ж�
				if (null == user) {
					JOptionPane.showMessageDialog(null, "��������˺����벻��ȷ��");
				} else {
					MainFrame.setUser(user);          // �����û���Ϊ��ǰ�����û�
					MainFrame frame = new MainFrame();// �������������
					dispose();                        // ���ٱ�����
				}
			}
		});
		pwdField.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				loginBtn.doClick();// ������¼��ť�¼�
			}
		});
	}
}
