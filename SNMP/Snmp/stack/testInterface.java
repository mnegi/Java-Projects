
import java.awt.*; 
import javax.swing.*;
import java.util.*;
import java.awt.event.*;

import java.beans.*;

public class testInterface extends JComponent 
        implements ActionListener, PropertyChangeListener
{

    GridBagLayout gridBagLayout1 = new GridBagLayout();
    JLabel label1 = new JLabel(" ");
    JLabel label2 = new JLabel(" ");
    JLabel label3 = new JLabel(" ");
    JTextField hostText = new JTextField();
    JTextField portText = new JTextField();
    JLabel interfaceLabel = new JLabel(" ");
    java.awt.List interfaceList = new java.awt.List();
    InterfaceIndexesBean interfaceBean = new InterfaceIndexesBean();
    IsHostReachableBean reachableBean = new IsHostReachableBean();

    JLabel label5 = new JLabel(" ");
    JTextField communityText = new JTextField();
    JButton tryButton = new JButton();
    JLabel messageLabel = new JLabel(" ");
    JButton showButton = new JButton();
    JLabel label7 = new JLabel(" ");
    JTextField intervalText = new JTextField();
    JButton actionButton = new JButton();
public String hostname;
    //Construct the application
    
    public testInterface()
    {
    }
//Initialize the application
    
    public void init()
    {
        try
        {
            jbInit();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

//Component initialization
    
    private void jbInit() throws Exception
    {
        label1.setText(" Host");
        label2.setText(" Port");
        label3.setText(" # Interfaces");
        hostText.setBackground(Color.white);
        hostText.setText(hostname);
        hostText.addActionListener(this);
        portText.setBackground(Color.white);
        portText.setText("" + SnmpContextBasisFace.DEFAULT_PORT);
        portText.addActionListener(this);
        interfaceBean.setPort(SnmpContextBasisFace.DEFAULT_PORT);
        interfaceBean.setHost(hostname);
        interfaceList.addActionListener(this);
        label5.setText(" Community");
        communityText.setBackground(Color.white);
        communityText.setText("public");
        communityText.addActionListener(this);
        tryButton.setText("Try connection");
        tryButton.addActionListener(this);
        interfaceLabel.setBackground(Color.white);
        interfaceList.setBackground(Color.yellow);
        messageLabel.setBackground(Color.white);
        interfaceLabel.setOpaque(true);
        messageLabel.setOpaque(true);
        showButton.setText("Show Interface");
        showButton.addActionListener(this);
        label7.setText(" Interval");
        intervalText.setBackground(Color.white);
        intervalText.setText("2000");
        actionButton.setText("Action");
        actionButton.addActionListener(this);
        intervalText.addActionListener(this);
        reachableBean.addPropertyChangeListener(this);
        interfaceBean.addPropertyChangeListener(this);
        this.setLayout(gridBagLayout1);
        this.setSize(400,300);

        this.add(label1, 
            getGridBagConstraints2(0, 0, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, 
            new Insets(0, 0, 0, 0), 0, 0));
        this.add(label2, 
            getGridBagConstraints2(0, 1, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, 
            new Insets(0, 0, 0, 0), 0, 0));
        this.add(label5, 
            getGridBagConstraints2(0, 2, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, 
            new Insets(0, 0, 0, 0), 0, 0));
        this.add(label7, 
            getGridBagConstraints2(0, 3, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, 
            new Insets(0, 0, 0, 0), 0, 0));
        this.add(tryButton, 
            getGridBagConstraints2(0, 4, 1, 1, 1.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, 
            new Insets(10, 10, 10, 10), 0, 0));
        this.add(label3, 
            getGridBagConstraints2(0, 5, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.BOTH, 
            new Insets(0, 0, 0, 0), 0, 0));
        this.add(interfaceList, 
            getGridBagConstraints2(0, 6, 2, 1, 1.0, 1.0
            ,GridBagConstraints.CENTER, GridBagConstraints.BOTH, 
            new Insets(0, 0, 0, 0), 0, 0));
        this.add(showButton, 
            getGridBagConstraints2(0, 7, 2, 1, 1.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.NONE, 
            new Insets(10, 10, 10, 10), 0, 0));
        this.add(messageLabel, 
            getGridBagConstraints2(0, 8, 2, 1, 1.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, 
            new Insets(0, 0, 10, 0), 0, 0));

        this.add(hostText, 
            getGridBagConstraints2(1, 0, 1, 1, 1.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, 
            new Insets(0, 0, 0, 0), 0, 0));
        this.add(portText, 
            getGridBagConstraints2(1, 1, 1, 1, 1.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, 
            new Insets(0, 0, 0, 0), 0, 0));
        this.add(communityText, 
            getGridBagConstraints2(1, 2, 1, 1, 1.0, 0.0
            ,GridBagConstraints.EAST, GridBagConstraints.BOTH, 
            new Insets(0, 0, 0, 0), 0, 0));
        this.add(intervalText, 
            getGridBagConstraints2(1, 3, 1, 1, 1.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, 
            new Insets(0, 0, 0, 0), 0, 0));
        this.add(actionButton, 
            getGridBagConstraints2(1, 4, 1, 1, 1.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, 
            new Insets(10, 10, 10, 10), 0, 0));
        this.add(interfaceLabel, 
            getGridBagConstraints2(1, 5, 1, 1, 1.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.BOTH, 
            new Insets(0, 0, 0, 0), 0, 0));
    }



    public void initialize(String hn)
    {
        testInterface application = new testInterface();
        application.hostname=hn;
        application.init();

        JFrame frame = new JFrame();
        frame.setTitle(application.getClass().getName());
        frame.getContentPane().add(application, BorderLayout.CENTER);
        frame.setSize(400,320);
        Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
        frame.setLocation((d.width - frame.getSize().width) / 2, (d.height - frame.getSize().height) / 2);
        frame.setVisible(true);
        
    }
    
    public void actionPerformed(ActionEvent e)
    {
        Object src = e.getSource();
        if (src == hostText)
        {
            hostText_actionPerformed(e);
        }
        else if (src == portText)
        {
                portText_actionPerformed(e);
        }
        else if (src == communityText)
        {
                communityText_actionPerformed(e);
        }
        else if (src == interfaceList)
        {
                interfaceList_actionPerformed(e);
        }
        else if (src == tryButton)
        {
                tryButton_actionPerformed(e);
        }
        else if (src == showButton)
        {
                showButton_actionPerformed(e);
        }
        else if (src == actionButton)
        {
                actionButton_actionPerformed(e);
        }
        else if (src == intervalText)
        {
                intervalText_actionPerformed(e);
        }
    }

    void hostText_actionPerformed(ActionEvent e)
    {
        interfaceBean.setHost(hostText.getText());
    }

    void portText_actionPerformed(ActionEvent e)
    {
        interfaceBean.setPort(portText.getText());
    }

    void communityText_actionPerformed(ActionEvent e)
    {
        interfaceBean.setCommunityName(communityText.getText());
    }

    void intervalText_actionPerformed(ActionEvent e)
    {
        interfaceBean.setUpdateInterval(intervalText.getText());
    }

    void tryButton_actionPerformed(ActionEvent e)
    {
        messageLabel.setText(" ");
        reachableBean.setHost(hostText.getText());
        reachableBean.setPort(portText.getText());
        reachableBean.setCommunityName(communityText.getText());

        try
        {
            reachableBean.action();
        }
        catch (java.io.IOException exc)
        {
            messageLabel.setText("IOException " + exc.getMessage());
        }
        catch (PduException exc)
        {
            messageLabel.setText("PduException " + exc.getMessage());
        }
    }

    public void propertyChange(PropertyChangeEvent e)
    {
        Object src = e.getSource();
        if (src == reachableBean)
        {
            reachableBean_propertyChange(e);
        }
        else if (src == interfaceBean)
        {
            interfaceBean_propertyChange(e);
        }
    }

    void interfaceBean_propertyChange(PropertyChangeEvent e)
    {
        Enumeration values = interfaceBean.getInterfaceIndexes();
        int nr = interfaceBean.getInterfaceCount();

        interfaceLabel.setText(String.valueOf(nr));
        if (interfaceList.getItemCount() > 0)
        {
            interfaceList.removeAll();
        }

        while (values.hasMoreElements())
        {
            String index = (String) values.nextElement();
            interfaceList.add(index);
        }
    }

    void reachableBean_propertyChange(PropertyChangeEvent e)
    {
        messageLabel.setText(reachableBean.getMessage());
    }

    private void showOneInterface()
    {
        String itemStr = interfaceList.getSelectedItem();
        if (itemStr != null)
        {
            int index = Integer.valueOf(itemStr).intValue();

            OneInterface oneInterface = new
            OneInterface(testNcdPerfDataBean.getFrame(this), 
                hostText.getText(), portText.getText(), 
                communityText.getText(), index, intervalText.getText());
        }
    }

    void interfaceList_actionPerformed(ActionEvent e)
    {
        showOneInterface();
    }

    void showButton_actionPerformed(ActionEvent e)
    {
        showOneInterface();
    }

    void actionButton_actionPerformed(ActionEvent e)
    {
        messageLabel.setText(" ");
        interfaceLabel.setText(" ");
        if (interfaceList.getItemCount() > 0)
        {
            interfaceList.removeAll();
        }

        interfaceBean.setHost(hostText.getText());
        interfaceBean.setPort(portText.getText());
        interfaceBean.setCommunityName(communityText.getText());
        interfaceBean.setUpdateInterval(intervalText.getText());

        interfaceBean.action();
    }

    GridBagConstraints getGridBagConstraints2(
            int x, int y, int w, int h, double wx, double wy,
            int anchor, int fill,
            Insets ins, int ix, int iy)
    {
        GridBagConstraints gc = new GridBagConstraints();
        gc.gridx = x;
        gc.gridy = y;
        gc.gridwidth = w;
        gc.gridheight = h;
        gc.weightx = wx;
        gc.weighty = wy;
        gc.anchor = anchor;
        gc.fill = fill;
        gc.insets = ins;
        gc.ipadx = ix;
        gc.ipady = iy;

        return gc;
    }


 
}
