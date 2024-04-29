// Copyright Eric Chauvin 2019 - 2024.



// This is licensed under the GNU General
// Public License (GPL).  It is the
// same license that Linux has.
// https://www.gnu.org/licenses/gpl-3.0.html



import javax.swing.SwingUtilities;
import java.io.File;

// For Serializable:
import java.io.Serializable;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.IOException;



public class MainApp implements Runnable,
                                Serializable
  {
  public static final String versionDate =
                              "4/29/2024";

  public static final long serialVersionUID = 1;

  public static final char pathSepChar =
                            File.separatorChar;
  private MainWindow mainWin;
  public ConfigureFile mainConfigFile;
  public ConfigureFile projectConfigFile;
  private String[] argsArray;
  private String programDirectory = "";



  public static void main( String[] args )
    {
    MainApp mApp = new MainApp( args );
    SwingUtilities.invokeLater( mApp );
    }


  // For Serializable:
  private void writeObject(
                   ObjectOutputStream stream )
                           throws IOException
    {
    stream.defaultWriteObject();
    }

  private void readObject(
                     ObjectInputStream stream )
                     throws IOException,
                     ClassNotFoundException
    {
    stream.defaultReadObject();
    }



  public String getProgramDirectory()
    {
    return programDirectory;
    }



  @Override
  public void run()
    {
    setupProgram();
    }


  private MainApp()
    {
    }


  public MainApp( String[] args )
    {
    argsArray = args;
    }



  private void setupProgram()
    {
    try
    {
    // checkSingleInstance()

    // All programs need to have an argument
    // to give it the program directory.
    programDirectory = "";

    // int length = argsArray.length;
    // if( length > 0 )
      // programDirectory = argsArray[0];

    String currentPath = new java.io.File(
                      "." ).getCanonicalPath();

    programDirectory = currentPath;

    String userDir = System.getProperty(
                                "user.dir" );

    String mainConfigFileName = programDirectory +
                          pathSepChar +
                          "MainConfigure.txt";

    mainConfigFile = new ConfigureFile( this,
                          mainConfigFileName );

    String currentProjectFileName =
            mainConfigFile.getString(
                        "CurrentProjectFile" );

    if( currentProjectFileName.length() < 4 )
      {
      currentProjectFileName = programDirectory +
                           pathSepChar +
                           "ProjectOptions.txt";

      mainConfigFile.setString(
                       "CurrentProjectFile",
                       currentProjectFileName,
                       true );

      // mainConfigFile.writeToTextFile();
      }

    projectConfigFile = new ConfigureFile( this,
                       currentProjectFileName );

    mainWin = new MainWindow( this,
                            "Code Editor 4" );
    mainWin.initialize();

    // Can't do showStatus() until mainWin is
    // initialized.

    showStatus( "userDir:\n" + userDir );

    // This editor is meant to run on one of
    // these two.
    if( pathSepChar == '/' )
      showStatus( "Running on Linux." );
    else
      showStatus( "Running on Windows." );

    showStatus( " " );
    showStatus( "currentProjectFileName:\n" +
                   currentProjectFileName );

    // showStatus( argsArray[count] );

    showStatus( " " );
    showStatus( "Program Directory:" );
    showStatus( programDirectory );

    }
    catch( Exception e )
      {
      showStatus(
            "Exception in setupProgram().\n" +
            e.getMessage() );
      }

    }




  public void showStatus( String toShow )
    {
    if( mainWin == null )
      return;

    mainWin.showStatusAsync( toShow );
    }


  public void clearStatus()
    {
    if( mainWin == null )
      return;

    mainWin.clearStatus();
    }




  }
