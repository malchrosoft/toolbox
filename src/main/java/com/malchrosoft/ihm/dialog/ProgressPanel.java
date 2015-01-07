/*
 * Copyright MalchroSoft - Aymeric MALCHROWICZ. All right reserved.
 * The source code that contains this comment is an intellectual property
 * of MalchroSoft [Aymeric MALCHROWICZ]. Use is subject to licence terms.
 */
package com.malchrosoft.ihm.dialog;

import com.malchrosoft.graphics.MalchroSoftColors;
import com.malchrosoft.graphics.components.TranslucentPanel;
import com.malchrosoft.graphics.utils.PositioningManager;
import java.awt.BorderLayout;
import java.awt.Dimension;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import org.pushingpixels.lafwidget.contrib.blogofbug.swing.components.GradientPanel;

/**
 *
 * @author Aymeric Malchrowicz
 */
public class ProgressPanel extends TranslucentPanel
{
	private JProgressBar bar;
	private TranslucentPanel labelPanel;
	private JLabel label;

	private boolean isModal;

	public ProgressPanel(boolean isModal)
	{
		super(0, false);
		this.bar = new JProgressBar();
		this.bar.setMinimum(0);
		this.bar.setMaximum(100);
		this.bar.setPreferredSize(new Dimension(50, 20));
		this.bar.setMinimumSize(new Dimension(50, 20));
		this.bar.setMaximumSize(new Dimension(5000, 200));

		this.labelPanel = new TranslucentPanel(0, true);
		this.labelPanel.setOpaque(false);
		this.labelPanel.setLayout(new BorderLayout());
		JPanel pan = new JPanel();
		pan.setPreferredSize(new Dimension(4, 4));
		pan.setMaximumSize(new Dimension(4, 4));
		pan.setOpaque(false);
		this.labelPanel.add(pan, BorderLayout.WEST);

		this.label = new JLabel("default");
		this.labelPanel.add(this.label, BorderLayout.CENTER);

		this.setLayout(new BorderLayout());
		this.add(this.bar, BorderLayout.CENTER);

		this.add(this.labelPanel, BorderLayout.NORTH);
		this.setSize(300, 30);
		this.setVisible(false);
		this.isModal = isModal;
	}

	public void setIndeterminate(boolean b)
	{
		this.bar.setIndeterminate(b);
	}

	public void setBarStringPainted(boolean b)
	{
		this.bar.setStringPainted(b);
	}

	public void setValue(int v)
	{
		this.setIndeterminate(false);
		this.bar.setValue(v);
	}

	public void setBarString(String s)
	{
		this.bar.setString(s);
	}

	public void setLabel(String s)
	{
		this.label.setText(s);
	}

	public String getLabel()
	{
		return this.label.getText();
	}

	public void setModal(boolean isModal)
	{
		this.isModal = isModal;
		this.setVisible(this.isVisible());
	}

	@Override
	public final void setVisible(boolean b)
	{
		if (this.isModal) this.getParent().setEnabled(!b);
		super.setVisible(b);
	}

	public JProgressBar getBar()
	{
		return bar;
	}

	public static ProgressDialog buildProgressDialog(JFrame parent, boolean isModal)
	{
		return new ProgressDialog(parent, isModal)
		{
			@Override
			protected void action()
			{
				// nothing
			}

		};
	}

	public static abstract class ProgressDialog extends JFrame
	{
		private ProgressPanel pp;
		private JFrame parent;
		private boolean isModal;
		private GradientPanel gradientBack;
		private boolean waiting;

		public ProgressDialog(JFrame parent, boolean isModal)
		{
			super();
			this.parent = parent;
			this.isModal = isModal;
			this.pp = new ProgressPanel(true);
			this.gradientBack = new GradientPanel();
			this.setContentPane(this.gradientBack);
			this.getContentPane().setLayout(new BorderLayout());
			setPreferredSize(new Dimension(350, 60));
			this.setResizable(false);
			this.getContentPane().add(this.pp, BorderLayout.CENTER);
			this.pp.setVisible(true);
			if (this.parent != null) PositioningManager.center(this, this.parent);
			this.pack();
		}

		/**
		 * Shows the progress dialog and execute the action.
		 * @return true at the end of the action
		 */
		public boolean showAndRun()
		{
			this.waiting = true;
			this.start();
			while (this.waiting)
			{
				try
				{
					for (int i = 0; i < 10 && this.waiting; i++)
						Thread.sleep(100);
				} catch (InterruptedException ie)
				{ /* do nothing */

				}
			}
			return true;
		}

		private synchronized void start()
		{
			final Runnable display = new Runnable()
			{
				@Override
				public void run()
				{
					ProgressDialog.this.setVisible(true);
				}

			};
			final Runnable hide = new Runnable()
			{
				@Override
				public void run()
				{
					ProgressDialog.this.setVisible(false);
				}

			};
			new Thread(new Runnable()
			{
				@Override
				public void run()
				{
					while (!isVisible())
					{
						try
						{
							Thread.sleep(50);
							/* waiting */
						} catch (InterruptedException ex)
						{
						}
					}
					action();
					waiting = false;
					SwingUtilities.invokeLater(hide);
				}

			}).start();

//            Log.debug("" + SwingUtilities.isEventDispatchThread());
			SwingUtilities.invokeLater(display);
		}

		protected abstract void action();

		protected boolean isWaiting()
		{
			return waiting;
		}

		public GradientPanel getGradientBack()
		{
			return gradientBack;
		}

		public ProgressPanel getProgressPanel()
		{
			return pp;
		}

		@Override
		public void setVisible(boolean b)
		{
			if (this.parent != null && this.isModal)
				this.parent.setEnabled(!b);
			this.setEnabled(!b);
			super.setVisible(b);
		}

		public boolean isModal()
		{
			return isModal;
		}

		@Override
		public JFrame getParent()
		{
			return this.parent;
		}

	}

	public static void main(String args[])
	{
		try
		{
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			for (int i = 0; i < UIManager.getInstalledLookAndFeels().length; i++)
			{
				if (UIManager.getInstalledLookAndFeels()[i].getName().contains(
					"Nimbus"))
				{
					// Some problems with Nimbus
					UIManager.setLookAndFeel(
						UIManager.getInstalledLookAndFeels()[i].getClassName());
				}
			}
		} catch (Exception ex)
		{
			ex.printStackTrace();
		}
		ProgressDialog f = ProgressPanel.buildProgressDialog(null, false);
		f.setTitle("ProgressDialog - TEST");
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setSize(400, 67);
		f.setBounds(200, 200, 400, 67);
		f.setVisible(true);

		ProgressPanel pp = f.getProgressPanel();
		pp.setLocation(20, 20);
		pp.setBackground(MalchroSoftColors.LAVENDER);
		f.add(pp);
		pp.setLabel("DEBUT...");
		pp.setValue(0);
		pp.setIndeterminate(true);
		try
		{
			Thread.sleep(500);
		} catch (InterruptedException ie)
		{
		}
		pp.setVisible(true);
		try
		{
			Thread.sleep(3000);
		} catch (InterruptedException ie)
		{
		}
		pp.setIndeterminate(false);
		for (int c = 0; c < 100; c += 1)
		{
			try
			{
				Thread.sleep(135 - c);
			} catch (InterruptedException ie)
			{
			}
			if (c >= 100) c = 100;
			pp.setValue(c);
		}
		pp.setValue(0);
		pp.setIndeterminate(true);
		pp.setBarStringPainted(true);
		pp.setLabel("Autres tests");
		try
		{
			Thread.sleep(3000);
		} catch (InterruptedException ie)
		{
		}
		for (int c = 0; c < 100; c += 1)
		{
			try
			{
				Thread.sleep(5 + c);
			} catch (InterruptedException ie)
			{
			}
			if (c >= 100) c = 100;
			pp.setValue(c);
		}
		pp.setValue(100);
		pp.setLabel("Essai de progression... FIN");
		try
		{
			Thread.sleep(1000);
		} catch (InterruptedException ie)
		{
		}
		pp.setVisible(false);
		try
		{
			Thread.sleep(1000);
		} catch (InterruptedException ie)
		{
		}
		f.dispose();
		System.exit(0);
	}

}
