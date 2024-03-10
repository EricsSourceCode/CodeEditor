// Copyright Eric Chauvin 2022.



public class FileTreeInfo
  {
  // filePath is the unique key.
  public String filePath = "";
  public String fileName = "";


  public FileTreeInfo( String useFilePath,
                   String useFileName )
    {
    filePath = useFilePath;
    fileName = useFileName;
    }

  public String toString()
    {
    return fileName;
    }


  }
