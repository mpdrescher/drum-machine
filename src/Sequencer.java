import java.applet.Applet;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

public class Sequencer extends Applet implements MouseListener
{
	SoundManager sound;
	
	Image dbImage;
	Graphics dbg;
	
	int state = 0;
	int length = 32;
	
	ArrayList<Integer>[] queues = new ArrayList[32];
	
	public Sequencer()
	{
		this.addMouseListener(this);
		sound = new SoundManager();
		for (int i = 0; i < queues.length; i++)
		{
			queues[i] = new ArrayList<Integer>();
		}
	}
	
	public void resetSeq()
	{
		for (int i = 0; i < queues.length; i++)
		{
			queues[i].clear();
		}
		state = 0;
		repaint();
	}
	
	public void update (Graphics g)
	{
	    if (dbImage == null)
	    {
	        dbImage = createImage (this.getSize().width, this.getSize().height);
	        dbg = dbImage.getGraphics ();
	    }
	    dbg.setColor (getBackground ());
	    dbg.fillRect (0, 0, this.getSize().width, this.getSize().height);
	    dbg.setColor (getForeground());
	    paint (dbg);
	    g.drawImage (dbImage, 0, 0, this);
	}
	
	public void step()
	{
		state ++;
		if (state >= length)
		{
			state = 0;
		}
		ArrayList<Integer> stepQueues = queues[state];
		for (int j = 0; j < stepQueues.size(); j++)
		{
			sound.play(stepQueues.get(j));
		}
		repaint();
	}
	
	public void insert(int id)
	{
		boolean exists = false;
		for (int i = 0; i < queues[state].size(); i++)
		{
			if (queues[state].get(i) == id)
			{
				exists = true;
			}
		}
		if (exists == false)
		{
			queues[state].add(id);
		}
		sound.play(id);
	}
	
	public void paint(Graphics g)
	{
		for (int x = 0; x < 16; x++)
		{
			g.setColor(Color.BLACK);
			if (x/4%2 == 1)
			{
				g.setColor(Color.GRAY);
			}
			if (state == x)
			{
				g.setColor(Color.RED);
			}
			g.fillOval(x*25+15, 5, 10, 10);
		}
		for (int x = 0; x < 16; x++)
		{
			g.setColor(Color.BLACK);
			if (x/4%2 == 1)
			{
				g.setColor(Color.GRAY);
			}
			if (state - 16 == x)
			{
				g.setColor(Color.RED);
			}
			g.fillOval(x*25+15, 30, 10, 10);
		}
	}
	
	public void mousePressed(MouseEvent e) 
	{
		int posX = e.getX();
		int posY = e.getY();
		for (int x = 0; x < 16; x++)
		{
			if (posX > x*25+15 && posX < x*25+25)
			{
				if (posY > 5 && posY < 15)
				{
					state = x;
				}
				else if (posY > 30 && posY < 40)
				{
					state = x+16;
				}
			}
		}
		repaint();
	}

	public void mouseClicked(MouseEvent e) {}
	public void mouseEntered(MouseEvent e) {}
	public void mouseExited(MouseEvent e) {}
	public void mouseReleased(MouseEvent e) {}
}