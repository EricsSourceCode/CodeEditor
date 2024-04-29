// Copyright Eric Chauvin 2022 - 2024.



// This is licensed under the GNU General
// Public License (GPL).  It is the
// same license that Linux has.
// https://www.gnu.org/licenses/gpl-3.0.html




// black, blue, cyan, darkGray, gray, green,
// lightGray, magenta, orange, pink, red,
// white, yellow.



import javax.swing.event.*;
import java.util.Arrays;
import javax.swing.JDialog;
import java.awt.Container;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Font;
import java.awt.Frame;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import javax.swing.WindowConstants;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.KeyEvent;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JList;
import javax.swing.JButton;
import java.awt.Dimension;
// import java.awt.List;
import java.io.File;
import javax.swing.JScrollPane;



public final class FilePickDialog extends JDialog
                   implements ActionListener,
                   ListSelectionListener
           // ImageObserver, MenuContainer,
           // Serializable, Accessible,
           // RootPaneContainer, WindowConstants
  {
  // It needs to have a version UID since it's
  // serializable.
  public static final long serialVersionUID = 1;
  private MainApp mApp;
  private Font mainFont;
  private JTextField textField;
  private JButton cancelButton;
  private JButton openButton;
  private JButton scrollUpButton;
  private JButton scrollDownButton;
  private JList<String> fileList;
  private String parentPath = "";
  private int fileListLength = 0;


  public FilePickDialog( Frame useFrame,
                         MainApp useApp,
                         Font useFont,
                         int setWidth,
                         int setHeight )
    {
    super( useFrame, true );

    mApp = useApp;
    mainFont = useFont;

    addComponents( getContentPane() );
    // setContentPane( );

    parentPath = "" + File.separatorChar;
    // upButton.setText( "Up: " + parentPath );
    addFilesToList();

    setSize( setWidth, setHeight );

    // pack();

    setTitle( "File Picker" );

    setDefaultCloseOperation(
                WindowConstants.HIDE_ON_CLOSE );
    // DISPOSE_ON_CLOSE, DO_NOTHING_ON_CLOSE,
    // EXIT_ON_CLOSE

    addWindowListener( new WindowAdapter()
      {
      public void windowClosing( WindowEvent we )
        {

        }
      });

    addComponentListener( new ComponentAdapter()
      {
      public void componentShown(
                              ComponentEvent ce )
        {
        // textField.requestFocusInWindow();
        }
      });

    // Center it.
    setLocationRelativeTo( null );
    }



  public void actionPerformed( ActionEvent event )
    {
    try
    {
    // String paramS = event.paramString();

    String command = event.getActionCommand();

    if( command == null )
      {
      mApp.showStatus(
               "ActionEvent Command is null." );
      // keyboardTimerEvent();
      return;
      }

    mApp.showStatus( "ActionEvent Command is: "
                                    + command );

    if( command == "cancelButton" )
      {
      parentPath = "";
      textField.setText( "" );
      setVisible( false );
      return;
      }

    if( command == "openButton" )
      {
      if( !parentPath.endsWith( "" +
                           File.separatorChar ))
          parentPath += File.separatorChar;

      String fullPathName = parentPath +
                                  getFileName();

      File dirFile = new File( fullPathName );
      if( dirFile == null )
        {
        mApp.showStatus(
                  "dirFile full path was null." );
        return;
        }

      if( dirFile.isDirectory())
        {
        parentPath = fullPathName;

        // upButton.setText( "Up: " + parentPath );
        addFilesToList();
        return;
        }

      // mApp.showStatus(
        //          "It is not a directory." );
      mApp.showStatus( fullPathName );

      // Then it's a file to open.
      setVisible( false );
      return;
      }

    int scrollBy = 3;

    if( command == "scrollUpButton" )
      {
      int where = fileList.getFirstVisibleIndex();
      if( (where - scrollBy) > 0 )
        fileList.ensureIndexIsVisible(
                           where - scrollBy );
      else
        fileList.ensureIndexIsVisible( 0 );
      }

    if( command == "scrollDownButton" )
      {
      int where = fileList.getLastVisibleIndex();
      if( (where + scrollBy) < fileListLength )
        fileList.ensureIndexIsVisible(
                            where + scrollBy );
      else
        fileList.ensureIndexIsVisible(
                            fileListLength - 1 );
      }

    if( command == "upButton" )
      {
      // I would need a stack of previous up
      // directories to pop off of the stack.
      mApp.showStatus( "upButton was clicked." );
      parentPath = "" + File.separatorChar;
      addFilesToList();
      }

/*
    if( command == "TextFieldEnter" )
      {
      // mApp.showStatus( "Enter was pressed." );
      setVisible( false );
      return;
      }
*/
    }
    catch( Exception e )
      {
      mApp.showStatus(
            "Exception in actionPerformed()." );
      mApp.showStatus( e.getMessage() );
      }
    }


  public String getFileName()
    {
    return textField.getText().trim();
    }


  public String getFilePathName()
    {
    if( !parentPath.endsWith( "" +
                           File.separatorChar ))
      parentPath += File.separatorChar;

    return parentPath + getFileName();
    }



  private void addComponents( Container pane )
    {
    pane.setBackground( Color.green );
    // pane.setForeground( Color.red );

    // The default layout manager for a JPanel is
    // FlowLayout.  But it's left to right.

    JPanel mainPanel = new JPanel();
    mainPanel.setLayout(
                   new LayoutSimpleVertical());

    mainPanel.setBackground( Color.black );

/*
    upButton = new JButton( "Up" );
    upButton.setMnemonic( KeyEvent.VK_O );
    upButton.setActionCommand( "upButton" );
    upButton.addActionListener( this );
    // okButton.setAlignmentX(
              // Component.CENTER_ALIGNMENT );
    upButton.setFont( mainFont );
    upButton.setBackground( Color.black );
    upButton.setForeground( Color.white );
    mainPanel.add( upButton );
*/

    fileList = new JList<String>();
    fileList.setFont( mainFont );
    fileList.setBackground( Color.black );
    fileList.setForeground( Color.white );

// black, blue, cyan, darkGray, gray, green,
// lightGray, magenta, orange, pink, red,
// white, yellow.

    fileList.setSelectionBackground( Color.blue );
    fileList.setSelectionForeground(
                                  Color.white );

    fileList.setPreferredSize( new Dimension(
      100, LayoutSimpleVertical.FixedHeightMax ));

    fileList.addListSelectionListener( this );

    JScrollPane fileScrollPane = new
                        JScrollPane( fileList );

    fileScrollPane.setVerticalScrollBarPolicy(
         JScrollPane.VERTICAL_SCROLLBAR_ALWAYS );

    fileScrollPane.setPreferredSize( new
                             Dimension(
      100, LayoutSimpleVertical.FixedHeightMax ));

    mainPanel.add( fileScrollPane );

/*
    JLabel label = new JLabel(
                 "Enter Name:               " );
    label.setFont( mainFont );
    label.setBackground( Color.black );
    label.setForeground( Color.white );

    mainPanel.add( label );
*/

    scrollUpButton = new JButton(
                               "Scroll Up" );
    // scrollDownButton.setMnemonic(
               // KeyEvent.VK_O );
    scrollUpButton.setActionCommand(
                            "scrollUpButton" );
    scrollUpButton.addActionListener( this );
    // okButton.setAlignmentX(
              // Component.CENTER_ALIGNMENT );
    scrollUpButton.setFont( mainFont );
    scrollUpButton.setBackground( Color.black );
    scrollUpButton.setForeground( Color.white );
    mainPanel.add( scrollUpButton );

    scrollDownButton = new JButton(
                               "Scroll Down" );
    // scrollDownButton.setMnemonic( KeyEvent.VK_O );
    scrollDownButton.setActionCommand(
                            "scrollDownButton" );
    scrollDownButton.addActionListener( this );
    // okButton.setAlignmentX(
              // Component.CENTER_ALIGNMENT );
    scrollDownButton.setFont( mainFont );
    scrollDownButton.setBackground( Color.black );
    scrollDownButton.setForeground( Color.white );
    mainPanel.add( scrollDownButton );

    openButton = new JButton( "Open" );
    // openButton.setMnemonic( KeyEvent.VK_O );
    openButton.setActionCommand( "openButton" );
    openButton.addActionListener( this );
    // okButton.setAlignmentX(
              // Component.CENTER_ALIGNMENT );
    openButton.setFont( mainFont );
    openButton.setBackground( Color.black );
    openButton.setForeground( Color.white );
    mainPanel.add( openButton );

    textField = new JTextField();
    textField.setFont( mainFont );
    textField.setBackground( Color.black );
    textField.setForeground( Color.white );
    textField.setActionCommand( "TextFieldEnter" );
    textField.addActionListener( this );

    mainPanel.add( textField );

    cancelButton = new JButton( "Cancel" );
    // cancelButton.setMnemonic( KeyEvent.VK_O );
    cancelButton.setActionCommand(
                               "cancelButton" );
    cancelButton.addActionListener( this );
    cancelButton.setFont( mainFont );
    cancelButton.setBackground( Color.black );
    cancelButton.setForeground( Color.white );
    mainPanel.add( cancelButton );

    pane.add( mainPanel );
    }


  public void setTextField( String setTo )
    {
    textField.setText( setTo );
    }



  public void addFilesToList()
    {
    try
    {
    File dirFile = new File( parentPath );
    if( dirFile == null )
      {
      mApp.showStatus( "dirFile was null." );
      return;
      }

    String[] filesArray = dirFile.list();
    if( filesArray == null )
      {
      mApp.showStatus( "filesArray was null." );
      return;
      }

    fileListLength = filesArray.length;

    // Things like lower case get sorted after
    // upper case, and things like that.

    Arrays.sort( filesArray, 0,
                           fileListLength - 1 );

// getParent()

/*
String getName()
String getParent()
Returns the pathname string of this abstract
 pathname's parent, or null if this pathname does not name a parent directory.
File getParentFile()
Returns the abstract pathname of this abstract pathname's parent, or null if this pathname does not name a parent directory.
String getPath()
Converts this abstract pathname into a pathname string.
*/

    fileList.setListData( filesArray );
    fileList.ensureIndexIsVisible( 0 );
    }
    catch( Exception e )
      {
      mApp.showStatus(
            "Exception in addFilesToList().\n" +
            e.getMessage() );
      }
    }



  // Event for list selection changing.
  public void valueChanged(
                          ListSelectionEvent e )
    {
    textField.setText(
                  fileList.getSelectedValue());

    }


  }




/*
File.

boolean canExecute()
boolean canRead()
boolean canWrite()
int compareTo(File pathname)
boolean createNewFile()
boolean delete()
void deleteOnExit()
boolean exists()
File getAbsoluteFile()
String getAbsolutePath()
File getCanonicalFile()
String getCanonicalPath()
long getFreeSpace()
String getName()
String getParent()
Returns the pathname string of this abstract
 pathname's parent, or null if this pathname does not name a parent directory.
File getParentFile()
Returns the abstract pathname of this abstract pathname's parent, or null if this pathname does not name a parent directory.
String getPath()
Converts this abstract pathname into a pathname string.
long getTotalSpace()
boolean isDirectory()
Tests whether the file denoted by this abstract pathname is a directory.
boolean isFile()
Tests whether the file denoted by this abstract pathname is a normal file.
boolean isHidden()
Tests whether the file named by this abstract pathname is a hidden file.
long lastModified()
Returns the time that the file denoted by this abstract pathname was last modified.
long length()
Returns the length of the file denoted by this abstract pathname.
String[] list()
Returns an array of strings naming the files and directories in the directory denoted by this abstract pathname.
String[] list(FilenameFilter filter)
Returns an array of strings naming the files and directories in the directory denoted by this abstract pathname that satisfy the specified filter.
File[] listFiles()
Returns an array of abstract pathnames denoting the files in the directory denoted by this abstract pathname.
File[] listFiles(FileFilter filter)
Returns an array of abstract pathnames denoting the files and directories in the directory denoted by this abstract pathname that satisfy the specified filter.
File[] listFiles(FilenameFilter filter)
Returns an array of abstract pathnames denoting the files and directories in the directory denoted by this abstract pathname that satisfy the specified filter.
static File[] listRoots()
List the available filesystem roots.
boolean mkdir()
Creates the directory named by this abstract pathname.
boolean mkdirs()
Creates the directory named by this abstract pathname, including any necessary but nonexistent parent directories.
boolean renameTo(File dest)
Renames the file denoted by this abstract pathname.
boolean setExecutable(boolean executable)
A convenience method to set the owner's execute permission for this abstract pathname.
boolean setExecutable(boolean executable, boolean ownerOnly)
Sets the owner's or everybody's execute permission for this abstract pathname.
boolean setLastModified(long time)
Sets the last-modified time of the file or directory named by this abstract pathname.
boolean setReadable(boolean readable)
A convenience method to set the owner's read permission for this abstract pathname.
boolean setReadable(boolean readable, boolean ownerOnly)
Sets the owner's or everybody's read permission for this abstract pathname.
boolean setReadOnly()
Marks the file or directory named by this abstract pathname so that only read operations are allowed.
boolean setWritable(boolean writable)
A convenience method to set the owner's write permission for this abstract pathname.
boolean setWritable(boolean writable, boolean ownerOnly)
Sets the owner's or everybody's write permission for this abstract pathname.
Path toPath()
Returns a java.nio.file.Path object constructed from the this abstract path.
String toString()
Returns the pathname string of this abstract pathname.
URI toURI()
Constructs a file: URI that represents this abstract pathname.
URL toURL()
Deprecated.
This method does not automatically escape characters that are illegal in URLs. It is recommended that new code convert an abstract pathname into a URL by first converting it into a URI, via the toURI method, and then converting the URI into a URL via the URI.toURL method.
Methods inherited from class java.lang.Object
clone, finalize, getClass, notify, notifyAll, wait, wait, wait
Field Detail
separatorChar
public static final char separatorChar
The system-dependent default name-separator character. This field is initialized to contain the first character of the value of the system property file.separator. On UNIX systems the value of this field is '/'; on Microsoft Windows systems it is '\\'.
See Also:
System.getProperty(java.lang.String)
separator
*/
