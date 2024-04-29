// Copyright Eric Chauvin 2022.



import java.awt.Color;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeSelectionModel;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import java.awt.Dimension;
import java.awt.Font;
import java.io.File;



public final class TreePanel extends JPanel
                implements TreeSelectionListener
  {
  // It needs to have a version UID since it's
  // serializable.
  public static final long serialVersionUID = 1;

  private JTree tree;
  private Font mainFont;
  private FilePickDialog pickDialog;



  private TreePanel()
    {
    super( new LayoutSimpleVertical());
    }



  public TreePanel( Font useFont,
                  FilePickDialog usePickDialog )
    {
    super( new LayoutSimpleVertical());

    mainFont = useFont;
    pickDialog = usePickDialog;

    // Create the nodes.
    DefaultMutableTreeNode top =
          new DefaultMutableTreeNode(
                                 "Root Dir");

    // The root directory.
    String path = "" + File.separatorChar;
    createNodes( top, path );

    tree = new JTree( top );

    tree.setCellRenderer(
                new FileTreeCellRender(
                                   mainFont ) );

    tree.setBackground( Color.black );
    // tree.setForeground( Color.red );

    tree.setRootVisible( false );
    tree.setShowsRootHandles( true );
    tree.setEditable( false );


    tree.getSelectionModel().setSelectionMode
                        (TreeSelectionModel.
                        SINGLE_TREE_SELECTION );

    tree.addTreeSelectionListener( this );

    JScrollPane treeView = new JScrollPane(
                                          tree );

    treeView.setPreferredSize( new Dimension(
      100, LayoutSimpleVertical.FixedHeightMax ));

    add( treeView );
    }



  public void valueChanged(TreeSelectionEvent e)
    {
    DefaultMutableTreeNode node =
             (DefaultMutableTreeNode)
             tree.getLastSelectedPathComponent();

    if (node == null)
      return;

    Object nodeInfo = node.getUserObject();
    if (node.isLeaf())
      {
      FileTreeInfo fileInfo =
                       (FileTreeInfo)nodeInfo;

      pickDialog.setTextField(
                          fileInfo.filePath );

      createNodes( node, fileInfo.filePath );
      }

    }



  private void createNodes(
                    DefaultMutableTreeNode top,
                    String path )
    {
    File dirFile = new File( path );
    String[] filesArray = dirFile.list();
    int max = filesArray.length;
    if( max > 10 )
      max = 10;

    for( int count = 0; count < max; count++ )
      {
      if( filesArray[count] == null )
        continue;

      if( filesArray[count].length() < 1 )
        continue;

      if( filesArray[count].equals( "." ))
        continue;

      FileTreeInfo aFile = new FileTreeInfo(
           path + filesArray[count],
           filesArray[count] );

      DefaultMutableTreeNode aNode =
                 new DefaultMutableTreeNode(
                                        aFile );
      top.add( aNode );
      }
    }

  }


/*
DefaultTreeModel model = (DefaultTreeModel)
 tree.getModel();
DefaultMutableTreeNode root =
 (DefaultMutableTreeNode) model.getRoot();
model.insertNodeInto(
new DefaultMutableTreeNode(
"another_child"), root, root.getChildCount());

*/
