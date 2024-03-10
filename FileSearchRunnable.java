// Copyright Eric Chauvin 2020 - 2022.



import java.io.File;




public class FileSearchRunnable implements Runnable
  {
  private MainApp mApp;
  private String directory = "";
  private boolean recursive = true;
  private String textToFind = "";



  private FileSearchRunnable()
    {
    }



  public FileSearchRunnable( MainApp appToUse,
                             String dirToUse,
                             boolean useRecursive,
                             String useText )
    {
    mApp = appToUse;
    directory = dirToUse;
    recursive = useRecursive;
    textToFind = useText.toLowerCase();
    }



  public void run()
    {
    try
    {
    // showStatus calls showStatusAsync().

    listFiles( directory );
    mApp.showStatus( "\nFinished listing files." );
    }
    catch( Exception e )
      {
      mApp.showStatus( "Exception in FileSearchRunnable.run()." );
      mApp.showStatus( e.getMessage() );
      }
    }



  private void listFiles( String dir )
    {
    // mApp.showStatus( " " );
    // mApp.showStatus( "Listing: " + dir );
    // mApp.showStatus( " " );

    File dirFile = new File( dir );
    String[] filesArray = dirFile.list();
    int max = filesArray.length;
    for( int count = 0; count < max; count++ )
      {
      // Thread.sleep( 1000 );
      if( Thread.interrupted() )
        {
        mApp.showStatus( "Thread interrupted." );
        return;
        }

      String fileName = dir + File.separatorChar +
                             filesArray[count];
/*
      if( fileToFind.length() > 0 )
        {
        if( !fileName.contains( fileToFind ))
          continue;

        }
*/

      File listFile = new File( fileName );
      if( listFile.isDirectory())
        {
        // mApp.showStatusAsync( " " );
        // mApp.showStatusAsync( "// dir: " + fileName );
        // mApp.showStatusAsync( " " );

        if( recursive )
          {
          listFiles( fileName );
          }

        continue;
        }

      String fileS = FileUtility.
                 readFileToString( mApp,
                                   fileName,
                                   false );

      if( fileS.toLowerCase().contains(
                                textToFind ))
        mApp.showStatus( "\nFound it in: " +
                                 fileName );

      // if( fileName.contains( fileToFind ))
        // mApp.showStatus( fileName );

      }

    // mApp.showStatus( "\nList files is done." );
    }



  }
