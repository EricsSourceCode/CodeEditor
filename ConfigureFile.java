// Copyright Eric Chauvin 2019 - 2024.



// This is licensed under the GNU General
// Public License (GPL).  It is the
// same license that Linux has.
// https://www.gnu.org/licenses/gpl-3.0.html



// For Serializable:
import java.io.Serializable;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.IOException;



public class ConfigureFile implements
                           Serializable
  {
  public static final long serialVersionUID = 1;
  private MainApp mApp;
  private StringDictionary strDictionary;
  private String fileName;




  public ConfigureFile( MainApp useApp,
                         String fileToUseName )
    {
    mApp = useApp;

    fileName = fileToUseName;
    strDictionary = new StringDictionary( mApp,
                                         false );
    readFromTextFile();
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



  public String getString( String keyWord )
    {
    keyWord = keyWord.trim().toLowerCase();
    return strDictionary.getString( keyWord );
    }



  public void setString( String keyWord,
                         String value,
                         boolean writeFile )
    {
    if( keyWord == "" )
      return;

    // The StringDictionary is not
    // case-sensitive, but this makes all keys
    // lower case.
    keyWord = keyWord.trim().toLowerCase();
    strDictionary.setString( keyWord, value );

    if( writeFile )
      writeToTextFile();

    }




  private void readFromTextFile()
    {
    try
    {
    strDictionary.clear();

    String fileS = FileUtility.
                        readFileToString( mApp,
                        fileName,
                        true );

    if( fileS.length() < 2 )
      return;

    String[] lines = fileS.split( "\n" );
    for( int count = 0; count < lines.length; count++ )
      {
      String oneLine = lines[count];
      if( oneLine.length() < 3 )  // key, tab, value.
        continue;

      String[] lineParts = oneLine.split( "\t" );
      if( lineParts.length < 2 )
        continue;

      String key = lineParts[0].trim();
      if( key.length() < 1 )
        continue;

      String value = lineParts[1];
      // KeyWord = KeyWord.Replace( "\"", "" );
      // Value = Value.Replace( "\"", "" );

      strDictionary.setString( key, value );
      }

    }
    catch( Exception e )
      {
      mApp.showStatus( "Could not read the file: \n" + fileName );
      mApp.showStatus( e.getMessage() );
      }
    }




  public void writeToTextFile()
    {
    try
    {
    String fileStr = strDictionary.makeKeysValuesString();
    // mApp.showStatus( "fileStr: " + fileStr );
    if( fileStr.trim().length() < 1 )
      return;

    FileUtility.writeStringToFile( mApp,
                                   fileName,
                                   fileStr,
                                   true );

    }
    catch( Exception e )
      {
      mApp.showStatus( "Could not write the file: \n" + fileName );
      mApp.showStatus( e.getMessage() );
      }
    }




  /*
  private int GetIntegerValue( string Key )
    {
    try
    {
    return Int32.Parse( Config.GetString( Key ));
    }
    catch( Exception ) // Except )
      {
      // MForm.ShowStatus( "Exception in GetIntegerValue():" );
      // MForm.ShowStatus( Except.Message );
      return -1;
      }
    }
    */


  /*
  private void SetIntegerValue( string Key, int ToSet )
    {
    SetString( Key, ToSet.ToString() );
    WriteToTextFile();
    }
    */



  }
