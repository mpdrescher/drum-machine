import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JToggleButton;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.JCheckBox;

public class DrumMachine extends JFrame implements KeyListener
{
	JPanel contentPane;
	Sequencer sequencer;
	JSlider speedSlider;
	JCheckBox lengthBox;
	JLabel bpmLabel = new JLabel("");
	
	boolean running = true;
	boolean playing = false;
	int msWaitTime = 500;

	public static void main(String[] args)
	{
		new DrumMachine();
	}
	
	public DrumMachine() 
	{
		super("Mini Drum Machine");
		
		sequencer = new Sequencer();
		
		setIconImage(new BufferedImage(16, 16, BufferedImage.TYPE_4BYTE_ABGR));
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 180);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		sequencer.addKeyListener(this);
		
		JPanel panel = new JPanel();
		contentPane.add(panel, BorderLayout.NORTH);
		panel.setLayout(new BorderLayout(0, 0));
		
		JLabel lblSpeed = new JLabel("Speed:  ");
		panel.add(lblSpeed, BorderLayout.NORTH);
		
		speedSlider = new JSlider();
		panel.add(speedSlider, BorderLayout.CENTER);
		speedSlider.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				setSpeed();
			}
		});
		speedSlider.setValue(120);
		speedSlider.setMaximum(200);
		speedSlider.setMinimum(40);
		
		this.bpmLabel = new JLabel("120 BPM");
		panel.add(bpmLabel, BorderLayout.EAST);
		
		JPanel panel_1 = new JPanel();
		sequencer.setBounds(12, 43, 418, 52);
		panel_1.add(sequencer);
		contentPane.add(panel_1, BorderLayout.CENTER);
		panel_1.setLayout(null);
		
		JToggleButton tglbtnNewToggleButton = new JToggleButton("Play/Pause");
		tglbtnNewToggleButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				togglePlay();
			}
		});
		tglbtnNewToggleButton.setBounds(12, 12, 123, 25);
		panel_1.add(tglbtnNewToggleButton);
		
		JButton btnNewButton = new JButton("Reset");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				reset();
			}
		});
		btnNewButton.setBounds(147, 12, 80, 25);
		panel_1.add(btnNewButton);
		
		lengthBox = new JCheckBox("16/32");
		lengthBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setLength();
			}
		});
		lengthBox.setBounds(327, 13, 72, 23);
		panel_1.add(lengthBox);
		
		JButton btnNewButton_1 = new JButton("Next");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				next();
			}
		});
		btnNewButton_1.setBounds(239, 12, 80, 25);
		panel_1.add(btnNewButton_1);
		
		JLabel lblNewLabel = new JLabel("[use number keys 0-9]");
		lblNewLabel.setBounds(12, 95, 418, 15);
		panel_1.add(lblNewLabel);
		setVisible(true);
		this.requestFocus();
		
		while (running)
		{
			long timeBefore = System.currentTimeMillis();
			while (System.currentTimeMillis() - msWaitTime < timeBefore){}
			if (playing)
			{
				sequencer.step();
			}
			sequencer.requestFocusInWindow();
		}
	}
	
	public void togglePlay()
	{
		playing = !playing;
	}
	
	public void reset()
	{
		sequencer.resetSeq();
	}
	
	public void next()
	{
		sequencer.step();
	}
	
	public void setSpeed()
	{
		int bpm = speedSlider.getValue();
		msWaitTime = (int) (60.0/bpm*0.25*1000.0);
		bpmLabel.setText(bpm + " BPM");
	}
	
	public void setLength()
	{
		if (lengthBox.isSelected())
		{
			sequencer.length = 16;
		}
		else
		{
			sequencer.length = 32;
		}
	}
	
	public void keyPressed(KeyEvent e) 
	{
		int soundID = getSoundID(e.getKeyCode());
		if (soundID == -1)
		{
			return;
		}
		sequencer.insert(soundID);
	}

	public void keyReleased(KeyEvent e) 
	{
		
	}

	public void keyTyped(KeyEvent e) 
	{
		
	}
	
	public int getSoundID(int keycode)
	{
		if (keycode >= 48 && keycode <= 57)
		{
			return keycode - 48;
		}
		return -1;
	}
}
