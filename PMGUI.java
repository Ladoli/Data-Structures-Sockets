import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.text.DefaultCaret;


public class PMGUI extends JFrame {
	
	private JTextArea pmArea = new JTextArea();
	private JScrollPane scroller = new JScrollPane(pmArea, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
	private JButton sendButton = new JButton("Send");
	private JTextField sendInput = new JTextField();
	private String target;
	ClientGUI gui;

	public static void main(String[] args)
	{
		PMGUI test = new PMGUI();
		test.setVisible(true);
	}
	
	public PMGUI()
	{
		setSize(500,300);
		setLayout(null);
		attach(scroller, 10, 10, 450, 200);
		attach(sendButton, 400,220, 80,30);
	    sendButton.addActionListener(new sendButtonListener());
	    attach(sendInput, 10, 220, 380, 30);
	}
	
	public PMGUI(String targetName, ClientGUI gui)
	{
		this.gui = gui;
		target = targetName;
		setSize(500,300);
		setLayout(null);
		attach(scroller, 10, 10, 450, 200);
		attach(sendButton, 400,220, 80,30);
	    sendButton.addActionListener(new sendButtonListener());
	}
	
	public void attach(Component a, int b, int c, int d, int e )
	{
		a.setBounds(b,c,d,e);
		add(a);
	}
	
	private class sendButtonListener implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			String userInput = sendInput.getText();
			if (!userInput.equals(""))
			{
				
					gui.sendPM(target,userInput);
				
			}
		}
	}
	
	void receivePM(String a)
	{
		pmArea.append(a);
	}

}
