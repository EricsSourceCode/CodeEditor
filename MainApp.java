// Copyright Eric Chauvin 2019 - 2023.



import javax.swing.SwingUtilities;
import java.io.File;



public class MainApp implements Runnable
  {
  public static final String versionDate =
                              "10/2/2023";
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
