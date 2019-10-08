package pack1;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JTextArea;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.awt.event.ActionEvent;

public class OpenSavesCopyDelete {

	private JFrame frame;
	
	public JFileChooser fc;
	public FileReader fr = null;
	public BufferedReader br = null; // đọc dữ liệu từng dòng từ file
	public FileWriter fw = null;
	public BufferedWriter bw = null; // ghi dữ liệu đã đọc vào file

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					OpenSavesCopyDelete window = new OpenSavesCopyDelete();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public OpenSavesCopyDelete() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 430, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JTextArea textArea = new JTextArea();
		textArea.setBounds(10, 11, 394, 205);
		frame.getContentPane().add(textArea);
		
		JButton btnOpen = new JButton("Open");
		btnOpen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String s;
				try {
					fc = new JFileChooser();
					int openDialog = fc.showOpenDialog(null); // hiển thị hộp tuỳ chọn
					if(openDialog == JFileChooser.APPROVE_OPTION) {
						String path = fc.getSelectedFile().getAbsolutePath(); // chọn đường dẫn của file
						fr = new FileReader(path);
						br = new BufferedReader(fr);
						while((s = br.readLine())!= null) {
							textArea.append(s + "\n"); // đưa dữ liệu đã đọc vào text area
						}
					}
					br.close();
					fr.close();
				} catch (Exception ex) {
					// TODO: handle exception
					System.out.println("Lỗi không thể mở File !");
				}
			}
		});
		btnOpen.setBounds(10, 227, 89, 23);
		frame.getContentPane().add(btnOpen);
		
		// hàm lưu file mới hoặc lưu file cũ
		// open file, sau đó có thể chỉnh sửa thông tin trên textArea rồi bấm nút Save As và đặt tên file mới
		// trường hợp muốn chỉnh sửa và lưu lại file cũ thì bấm nút Save As rồi kích vào chọn tên file cũ và nhấn nút Save
		JButton btnSaveAs = new JButton("Save As");
		btnSaveAs.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					fc = new JFileChooser();
					int saveDialog = fc.showSaveDialog(null);
					if(saveDialog == JFileChooser.APPROVE_OPTION) {
						String path = fc.getSelectedFile().getAbsolutePath();
						fw = new FileWriter(path);
						bw = new BufferedWriter(fw);
						bw.write(textArea.getText());
						bw.flush(); // xoá hết dữ liệu trong bufferder
						fw.close();
						bw.close();
					}
				} catch (Exception ex) {
					// TODO: handle exception
					System.out.println("Lỗi không thể lưu File !");
				}
			}
		});
		btnSaveAs.setBounds(109, 227, 89, 23);
		frame.getContentPane().add(btnSaveAs);
		
		// hàm copy
		/* open file cần copy, sau đó có thể chỉnh sửa thông tin trên textArea rồi bấm nút Copy,
		sau đó kích chọn file cần copy và nhấn nút save */
		JButton btnCopy = new JButton("Copy");
		btnCopy.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					fc = new JFileChooser();
					int openDialog = fc.showSaveDialog(null); // hiển thị hộp tuỳ chọn
					if(openDialog == JFileChooser.APPROVE_OPTION) {
						String path = fc.getSelectedFile().getAbsolutePath(); // chọn đường dẫn của file
						fw = new FileWriter(path);
						bw = new BufferedWriter(fw);
						bw.write(textArea.getText());
						JOptionPane.showMessageDialog(null, "Copy thành công !");
						bw.flush(); // xoá hết dữ liệu trong bufferder
					}
				} catch (Exception ex) {
					// TODO: handle exception
					System.out.println("Lỗi không thể Copy File !");
				}
			}
		});
		btnCopy.setBounds(208, 227, 89, 23);
		frame.getContentPane().add(btnCopy);
		
		// hàm xoá file
		// open file cần xoá, sau đó bấm delete sẽ hiện thông báo thành công hoặc thất bại
		JButton btnDelete = new JButton("Delete");
		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					String chooseFileDel = fc.getSelectedFile().getAbsolutePath(); // chọn file cần xoá
					File delFile = new File(chooseFileDel);
					if(delFile.delete()) {
						JOptionPane.showMessageDialog(null, "Xoá File thành công !");
					} else {
						JOptionPane.showMessageDialog(null, "Xoá File thất bại !");
					}
				} catch(Exception ex) {
					System.out.print("Lỗi không thể xoá File !");
				}
			}
		});
		btnDelete.setBounds(307, 227, 89, 23);
		frame.getContentPane().add(btnDelete);
	}
}
